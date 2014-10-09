import java.net.*;
import java.io.*;
import java.nio.channels.*;
import java.util.*;
import java.util.regex.*;
import net.htmlparser.jericho.*;

public class Main {

  public static void main(String[] args) throws Exception {
    PageDown page = new PageDown("http://www.apple.com/index.html", "offline/", 1, true);
  }

}
 
class PageDown {

  private String webExtensions = "com|net|org|co|jp";


  private String pre, back, baseReg;
  private int depth;

  PageDown(String url, String pre, int depth, boolean cssImg) throws Exception {

    this.depth = depth;
    this.pre = pre;

    // "https://www.apple.com/" => "apple.com/" 
    String base = url.replaceFirst("^https?://(?:www\\.)?(.+?\\.(?:" + 
      webExtensions + "))/?(?:.*)$", "$1/");
    
    // "apple.com/" => "apple\.com/"
    baseReg = base.replaceAll("\\.", "\\\\.");

    // index.html
    String pathf = downURL(url); // downloads the index
    
    // we add a couple of "../" on this pages links
    back = pathf.replaceAll("(?:^\\w+/)|[^/]", "").replaceAll("/", "../");

    // now we start downloading the page resources
    Source src = new Source(new File(pathf)); // jericho init
    List<Element> el;

    // .js and img
    el = src.getAllElements(HTMLElementName.SCRIPT); 
    el.addAll(src.getAllElements(HTMLElementName.IMG));
    for (Element e : el) {
      downURL(e.getAttributeValue("src"));
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
          if (cssImg) {
            imgFromCss(href, pathf2);
          }
        } 
    }
    
    replaceSave(src.toString(), pathf, back);
  }
}

  String downURL(String href) throws Exception {
    if (!href.matches("^https?.*$")) { // just to be sure its a link
      return "";
    }

    // "http://site.com/a/b.file" => "offline/site.com/a/b.file"
    String pathf = href.replaceFirst(
      "^https?://(?:www\\.)?(.*)$", pre + "$1"); 
    
    // "output/a/b.file" => "output/a/"
    String path = pathf.replaceFirst("^(.+/)[^/]+$", "$1"); 

    File file = new File(path);
    if (file.exists() == false && file.mkdirs() == false) {
      System.out.println("Error to create [" + path + "] folder.");
    }
    
    saveFile(pathf, new URL(href).openStream());
    return pathf;
  }

  void saveFile(String pathf, InputStream is) throws Exception {
    System.out.print("<");
    ReadableByteChannel rbc = Channels.newChannel(is);
    FileOutputStream fos = new FileOutputStream(pathf);
    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    fos.close();
    System.out.print(pathf);
    System.out.print(">");
    System.out.print("\n");
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
    s = s.replaceAll("\"https?://(?:www\\.)?(.*?)\"", 
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


