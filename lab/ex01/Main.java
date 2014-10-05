import java.net.*;
import java.io.*;
import java.nio.channels.*;
import java.util.*;
import java.util.regex.*;
import net.htmlparser.jericho.*;

public class Main {

  public static void main(String[] args) throws Exception {
    String pre = "output/";
    downURL("https://www.apple.com/index.html", pre); // downloads the index

    Source src = new Source(new File(pre + "index.html")); // jericho init
    String txt, pathf;
    List<Element> el;

    // .css & .rss
    {
      // grab every img url inside the .css 
      Pattern p = Pattern.compile("image:url\\(\"((?!data:image).*?)\"\\)");
      Matcher m;
      String path, href, css;
      el = src.getAllElements(HTMLElementName.LINK); 
      for (Element e : el) {
        if (e.getAttributeValue("rel").matches("stylesheet")) { 
          href = e.getAttributeValue("href");
          pathf = downURL(href, pre);

          if (pathf == null || pathf.equals("")) { 
            // when trying to download the font, downURL returns "" 
            // so the font stills broken
            continue;
          }
          System.out.println();

          css = new Source(new File(pathf)).toString();
          m = p.matcher(css);

          int i = -1;
          while(m.find()) {
            i = 0;
            txt = m.group(1); // "http://www.apple.com/img.png" or "../a/img.png"

            while(txt.matches("^\\.\\./.*$")) { // if "../a/img.png"
              // "../../a/img.png" => "../a/img.png"
              txt = txt.replaceFirst("^\\.\\./(.*)$", "$1"); 
              i++;
            }

            if (i > 0) { // its a relative path
              // example: we were in "http://www.apple.com/a/b/c.css"
              // and we got an img "../c/d.png" inside that .css
              // (actually, now this img is already "c/d.png", and i = 1
              // then that img => "http://www.apple.com/a/c/d.png"
              txt = href.replaceFirst("^(.+/)(.+/){" + i + "}[^/]+$", "$1") + txt;  
            } 

            downURL(txt, pre);
          }
          
          if (i != -1) {
            replaceSave(css.toString(), pathf, 
              pathf.replaceAll("(?:^\\w+/)|[^/]", "").replaceAll("/", "../"));
            // we add a couple of "../" on the non-relatives images 
          }

        } else if (e.getAttributeValue("rel").matches("alternate")) { // .rss
          downURL(e.getAttributeValue("href"), pre);
        }
      }
    }

    // .js
    el = src.getAllElements(HTMLElementName.SCRIPT); 
    for (Element e : el) {
      System.out.println();
      downURL(e.getAttributeValue("src"), pre);
    }

    // img
    el = src.getAllElements(HTMLElementName.IMG); 
    for (Element e : el) {
      System.out.println();
      downURL(e.getAttributeValue("src"), pre);
    }
    
    replaceSave(src.toString(), pre + "index.html", "");
  }

  static String downURL(String href, String pre) throws Exception {
    if (!href.matches("^https?.*$")) { // just to be sure its a link
      return "";
    }

    // "http://apple.com/a/b.file" => "output/a/b.file"
    String pathf = href.replaceFirst(
      "^https?://(?:www\\.)?apple\\.com/(.*)$", pre + "$1"); 

    // "output/a/b.file" => "output/a/"
    String path = pathf.replaceFirst("^(.+/)[^/]+$", "$1"); 

    File file = new File(path);
    if (file.exists() == false && file.mkdirs() == false) {
      System.out.println("Error to create [" + path + "] folder.");
    }
    
    saveFile(pathf, new URL(href).openStream());
    return pathf;
  }

  static void saveFile(String pathf, InputStream is) throws Exception {
    System.out.print("<");
    ReadableByteChannel rbc = Channels.newChannel(is);
    FileOutputStream fos = new FileOutputStream(pathf);
    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    fos.close();
    System.out.print(">");
    //System.out.println("new file: [" + pathf + "]");
  }

  static void replaceSave(String s, String pathf, String pre) throws Exception {
    // for relative = 0:
    // "http://www.apple.com/a.html" => "a.html".
    //
    // for relative > 0:
    // "http://www.apple.com/a/b/c/d.png" 
    // in the "http://www.apple.com/a/b/e.css => // "../../a/b/c/d.png"
    // because the img inside the css looks for paths in relation to the .css path
    
    s = s.replaceAll("\"https?://(?:www\\.)?apple\\.com/(.*?)\"", "\"" + pre + "$1\"");
    saveFile(pathf, new ByteArrayInputStream(s.getBytes()));
  }
}
