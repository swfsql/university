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

      new PageDown(website, base, depth, bcss);
    }
  }

}
 
class PageDown {

  private String webExtensions = "com|net|org|co|jp";
  private String httpReg = "(?:https?://www\\.|https?://|www\\.)";

  private String pre, back, baseReg;

  PageDown(String url, String pre, int depth, boolean cssImg) throws Exception {
    if(depth <= 0) return;
    System.out.println("++++++++++");
    System.out.println("+url: " + url);
    if (url.matches("^http.*$") == false) {
      url = url.replaceFirst("^(.+)$", "http://$1");
    }
    if (url.matches(".*\\.html$") == false) {
      url = url.replaceFirst("^(.+?)/?$", "$1/index.html");
    }
    System.out.println("+url: " + url);
    System.out.println("+dep: " + depth);

    this.pre = pre;

    // "https://www.apple.com/index.html" => "apple.com/" 
    String base = url.replaceFirst("^" + httpReg + "(.+?\\.(?:" + 
      webExtensions + "))/?(?:.*)$", "$1/");
    System.out.println("+base: " + base);
    System.out.println("++++++++++");
    // "apple.com/" => "apple\.com/"
    baseReg = base.replaceAll("\\.", "\\\\.");

    // index.html
    String pathf = downURL(url); // downloads the index
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
      if (downURL(e.getAttributeValue("src")) == null) {
        //System.out.println("++ Cancelled download: " + e);
      }
    }

    // .css & .rss
    {
      // grab every img url inside the .css 
      el = src.getAllElements(HTMLElementName.LINK); 
      for (Element e : el) {
        if (e.getAttributeValue("rel").matches("alternate")) { // .rss
          downURL(e.getAttributeValue("href"));
        } else if (e.getAttributeValue("rel").matches("stylesheet")) { 
          String href = e.getAttributeValue("href");
          String pathf2 = downURL(href);
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
    replaceSave(s, pathf, back);

    // other .html
    {
      depth--;
      el = src.getAllElements(HTMLElementName.A); 
      for (Element e : el) {
        String href = e.getAttributeValue("href");
        new PageDown(href, "offline/", depth, true);
      }
    }
  }
}

  String downURL(String href) throws Exception {
    if (href == null || !href.matches("^" + httpReg + ".*$")) { // just to be sure its a link
      return null;
    }

    // "http://site.com/a/b.file" => "offline/site.com/a/b.file"
    String pathf = href.replaceFirst(
      "^" + httpReg + "(.*)$", pre + "$1"); 
    
    return downURL(href, pathf);
  }

  String downURL(String href, String pathf) throws Exception {
    if (!href.matches("^" + httpReg + ".*$")) { // just to be sure its a link
      return null;
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
      try {
        saveFile(pathf, new URL(href).openStream());
      } catch (Exception e) {
        System.out.println("---------- ops");
        System.out.println("-href: " + href);
        System.out.println("-pathf: " + pathf);
        System.out.println("-path: " + path);
        System.out.println("-excpetion: " + e);
        if (href.matches("^http.*$") == false) {
          return downURL(href.replaceFirst("^(.+)$", "http://$1"), pathf);
        } else if (href.matches(".*\\.html$")) {
          System.out.println("----------");
          return downURL(href.replaceFirst("^(.+/)[^/]+$", "$1"), pathf);
        } else {
          System.out.println("-giving up on this link");
          System.out.println("----------");
          return null;
        }
      }
      return pathf;
    }
    return null;
  }

  void saveFile(String pathf, InputStream is) throws Exception {
    System.out.print("<");
    FileOutputStream fos = new FileOutputStream(pathf);
    try {
      ReadableByteChannel rbc = Channels.newChannel(is);
      fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    } finally {
      fos.close();
    }
    //System.out.print(pathf);
    System.out.print(">");
    //System.out.print("\n");
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
    saveFile(pathf, new ByteArrayInputStream(s.getBytes()));
  
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
       downURL(txt);
    }
    
    if (i != -1) {

      replaceSave(css.toString(), pathf, 
        pathf.replaceAll("(?:^\\w+/)|[^/]", "").replaceAll("/", "../"));
      // we add a couple of "../" on the non-relatives images 
    }

  } 
}


