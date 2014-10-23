import java.lang.Thread;
import java.net.*;
import java.io.*;
import java.nio.channels.*;
import java.util.*;
import java.util.regex.*;
import net.htmlparser.jericho.*;

public class Main {

  public static void main(String[] args) throws Exception {
    MicrosoftConditionalCommentTagTypes.register();
    StartTagType.COMMENT.register();
    MicrosoftConditionalCommentTagTypes.DOWNLEVEL_REVEALED_VALIDATING_IF.register(); 
    MicrosoftConditionalCommentTagTypes.DOWNLEVEL_REVEALED_VALIDATING_ENDIF.register();

    LinkStack ls = new LinkStack(); // downloading threads linked-stack 
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    while(true) {

      System.out.println("\n\n~~~~~~~~~~~~~~~~~~~~~~~~\n\nCtrl+C to exit.");

      System.out.print("insert the url [http://apple.com/]: ");
      String website = reader.readLine();
      if (website.equals("")) {
        website = "http://apple.com/";
      }

      System.out.print("how deep [1]: ");
      String sint = reader.readLine();
      if (sint.equals("")) {
        sint = "1";
      }
      int depth = Integer.parseInt(sint);

      System.out.print("the base folder [offline]: ");
      String base = reader.readLine();
      if (base.equals("")) {
        base = "offline/";
      }

      System.out.print("also download resources from .css [y/N]: ");
      String css = reader.readLine();
      Boolean bcss = css.equals("y") ? true : false;

      System.out.print("how many downloading threads? [1]: ");
      sint = reader.readLine();
      if (sint.equals("")) {
        sint = "1";
      }
      int nthreads = Integer.parseInt(sint);
      ls.head = new SaveFile(ls, 0); // at least 1 downloading thread
      new Thread(ls.head).start();
      SaveFile sf;
      for (int i = 1; i < nthreads; i++) {
        sf = new SaveFile(ls, i);
        sf.next = ls.head;
        ls.head = sf;
        new Thread(sf).start();
      }

      new PageDown(website, base, depth, bcss, ls);
    }
  }

}
 
class PageDown {

  private String webExtensions = "com|net|org|co|jp";
  private String httpReg = "(?:https?://www\\.|https?://|www\\.)";

  private String pre, back, baseReg;
  private LinkStack ls;

  PageDown(String url, String pre, int depth, boolean cssImg, LinkStack ls) throws Exception {
    if(depth <= 0) return;
    System.out.print("\n++");
    System.out.print(" <" + url + ">");
    System.out.print(" <" + depth + ">");
    if (url.matches("^http.*$") == false) {
      url = url.replaceFirst("^(.+)$", "http://$1");
    }
    if (url.matches(".*\\.html$") == false) {
      url = url.replaceFirst("^(.+?)/?$", "$1/index.html");
    }

    this.pre = pre;
    this.ls = ls;

    // "https://www.apple.com/index.html" => "apple.com/" 
    String base = url.replaceFirst("^" + httpReg + "(.+?\\.(?:" + 
      webExtensions + "))/?(?:.*)$", "$1/");
    System.out.print(" <" + base + ">");
    System.out.print(" / <" + url + ">");
    System.out.println(" ++");
    // "apple.com/" => "apple\.com/"
    baseReg = base.replaceAll("\\.", "\\\\.");

    // index.html
    String pathf = downURL(url, null, true); // downloads the index
    if (pathf == null){
      return;
    }
    
    // we add a couple of "../" on this pages links
    back = pathf.replaceAll("(?:^\\w+/)|[^/]", "").replaceAll("/", "../");

    // now we start downloading the page resources
    Source src = new Source(new File(pathf)); // jericho init
    String src_original = src.toString();
    String src_ie_tag = src_original.replaceAll("\\[if gte IE (.*?)\\]><!-->", "[if IE $1]>");
    src_ie_tag = src_ie_tag.replaceAll("<!--<!\\[endif\\]-->", "<![endif]-->");
    src = new Source(src_ie_tag);
    List<Element> el;

    // .js and img
    el = src.getAllElements(HTMLElementName.SCRIPT); 
    el.addAll(src.getAllElements(HTMLElementName.IMG));
    for (Element e : el) {
      if (downURL(e.getAttributeValue("src"), null, false) == null) {
        //System.out.println("++ Cancelled download: " + e);
      }
    }

    // .css & .rss
    {
      // grab every img url inside the .css 
      el = src.getAllElements(HTMLElementName.LINK); 
      for (Element e : el) {
        if (e.getAttributeValue("rel").matches("alternate")) { // .rss
          downURL(e.getAttributeValue("href"), null, false);
        } else if (e.getAttributeValue("rel").matches("stylesheet")) { 
          String href = e.getAttributeValue("href");
          String pathf2 = downURL(href, null, cssImg);
          if (cssImg && pathf2 != null) {
            imgFromCss(href, pathf2);
          }
        } 
      }

      String s = src_original;
      // .. href="/blabla/" .. => .. href="http://www.apple.com/blabla/" ..
      s = s.replaceAll("href=\"/(.*?)\"", "href=\"http://www." + base + "$1\"");
      // .. src=" .. ? ..  " .. => .. src=" .. %3F  .." ..
      s = s.replaceAll("src=\"(.*?)\\?(.*?)\"", "src=\"$1%3F$2\"");
      src = new Source(s);

      // other .html
      {
        depth--;
        el = src.getAllElements(HTMLElementName.A); 
        for (Element e : el) {
          String href = e.getAttributeValue("href");
          if (href != null) {
            new PageDown(href, pre, depth, cssImg, ls);
          }
        }
      }

      // href="..../" => href="..../index.html"
      s = s.replaceAll("href=\"([^\"]*)/\"", "href=\"$1/index.html\"");
      replaceSave(s, pathf, back);

    }
  }

  String downURL(String href, String pathf, boolean blocking) throws Exception {
    if (href == null || !href.matches("^" + httpReg + ".*$")) { // just to be sure its a link
      return null;
    }
    if (pathf == null) {
      // "http://site.com/a/b.file" => "offline/site.com/a/b.file"
      pathf = href.replaceFirst("^" + httpReg + "(.*)$", pre + "$1"); 
    }

    // "output/a/b.file" => "output/a/"
    String path = pathf.replaceFirst("^(.+/)[^/]+$", "$1"); 

    File file = new File(path);
    if (file.exists() == false && file.mkdirs() == false) {
      System.out.println("Error to create [" + path + "] folder.");
      System.exit(1);
    }
    
    file = new File(pathf);
    if (file.exists() == false) {
      return transfer(pathf, href, null, blocking);
    }
    return null;
  }

  String transfer(String pathf, String href, InputStream is, boolean blocking) throws Exception {
    SaveFile sf = null;
    try {
      while(ls.head == null) {
        Thread.sleep(10);
      }
    } catch (Exception e) {
      System.out.println("transfer wait exception.");
      System.exit(1);
    }
    sf = ls.head;
    ls.head = sf.next;
    sf.reset(pathf, href, is);
    if (blocking == false) {

      sf.interrupt();
    } else {
      if (sf.run2() == false) {
        System.out.print("--");
        System.out.print(" <" + href + ">");
        System.out.print(" <" + pathf + ">");
        //System.out.println("\n-excpetion: " + e);
        //System.out.println("-msg: ");  e.printStackTrace();

        if (href.matches("^http.*$") == false) {
          return downURL(href.replaceFirst("^(.+)$", "http://$1"), pathf, blocking);
        } else if (href.matches(".*\\.html$")) {
          System.out.println("----------");
          return downURL(href.replaceFirst("^(.+/)[^/]+$", "$1"), pathf, blocking);
        } else {
          System.out.print(" <giving up on this link>");
          System.out.print("--");
          return null;
        }
      }
    } 
    return pathf;
  }


  void replaceSave(String s, String pathf, String pre) throws Exception {
    // for relative = 0:
    // "http://www.site.com/a.html" => "a.html".
    //
    // for relative > 0:
    // "http://www.site.com/a/b/c/d.png" 
    // in the "http://www.site.com/a/b/e.css => // "../../a/b/c/d.png"
    // because the img inside the css looks for 
    // paths in relation to the .css path
    
    // "http://site.com/a/b.file" => "offline/a/b.file"
    s = s.replaceAll("\"" + httpReg + "(.*?)\"", 
      "\"" + pre + "$1\"");
    transfer(pathf, null, new ByteArrayInputStream(s.getBytes()), true);
  }


  void imgFromCss(String href, String pathf) throws Exception {
    
    Pattern p = Pattern.compile("image:url\\(\"((?!data:image).*?)\"\\)");
    Matcher m;
    String path, css, txt;

    if (pathf == null || pathf.equals("")) { 
      // when trying to download the font, downURL returns "" 
      // so the font stills broken
      return;
    }

    css = new Source(new File(pathf)).toString();
    m = p.matcher(css);
     int i = -1;
    while(m.find()) {
      i = 0;
      txt = m.group(1); // "http://www.site.com/img.png" or "../a/img.png"
       while(txt.matches("^\\.\\./.*$")) { // if "../a/img.png"
        // "../../a/img.png" => "../a/img.png"
        txt = txt.replaceFirst("^\\.\\./(.*)$", "$1"); 
        i++;
      }
       if (i > 0) { // its a relative path
        // example: we were in "http://www.site.com/a/b/c.css"
        // and we got an img "../c/d.png" inside that .css
        // (actually, now this img is already "c/d.png", and i = 1
        // then that img => "http://www.site.com/a/c/d.png"
        txt = href.replaceFirst("^(.+/)(.+/){" + i + "}[^/]+$", "$1") + txt;  
      } 
      downURL(txt, null, false);
    }
    
    if (i != -1) {

      replaceSave(css.toString(), pathf, 
        pathf.replaceAll("(?:^\\w+/)|[^/]", "").replaceAll("/", "../"));
      // we add a couple of "../" on the non-relatives images 
    }

  } 
}

class LinkStack {
  public SaveFile head;
}

class SaveFile implements Runnable {
  private String pathf, href;
  private InputStream is;
  public SaveFile next; // for linked stack 
  public LinkStack ls;
  private int id; // sysout only

  SaveFile(LinkStack ls, int id) {
    this.ls = ls;
    this.id = id; // sysout only
  }

  void reset(String pathf, String href, InputStream is) {
    this.pathf = pathf;
    this.is = is;
    this.href = href;
  }

  public synchronized void interrupt() {
    notify();
  }

  public void run() {
    try {
      while(true) {
        synchronized (this) {
          while(pathf == null) {
            wait();
          }
        }
        run2();
      }
    } catch(Exception e) {
      System.out.println("THREAD ERROR. id: " + id);
      System.exit(1);
    }
  }

  public boolean run2() {
    if (is == null) {
      try {
        is = new URL(href).openStream();
      } catch(Exception e) {
        finish();
        return false;
      }
    }

    FileOutputStream fos = null;
    try {
      fos = new FileOutputStream(pathf);
      ReadableByteChannel rbc = Channels.newChannel(is);
      fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    } catch (Exception e) {
      System.out.print("x");
        System.out.println("THREAD ERROR. id: " + id);
        System.out.print("--");
        System.out.print(" <" + href + ">");
        System.out.print(" <" + pathf + ">");
        System.exit(1);
    } finally {
      System.out.print("<" + id + ">");
      finish();
      try {fos.close();} catch(Exception e) 
      {
        System.out.println("THREAD ERROR. id: " + id);
        System.exit(1);
      }
      return true;
    }
  }

  public void finish() {
    pathf = null;
    is = null;
    next = ls.head;
    ls.head = this;
  }

}


