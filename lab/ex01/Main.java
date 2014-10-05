import java.net.*;
import java.io.*;
import java.nio.channels.*;
import java.util.*;
import java.util.regex.*;
import net.htmlparser.jericho.*;

public class Main {

  public static void main(String[] args) throws Exception {
    downURL("https://www.apple.com/index.html"); // downloads the index
    Source src = new Source(new File("output/index.html")); // jericho init

    List<Element> el = src.getAllElements(HTMLElementName.LINK); // .css|.rss
    for (Element e : el) {
      if (e.getAttributeValue("rel").matches("stylesheet|alternate")) {
        downURL(e.getAttributeValue("href"));
      }
    }

    el = src.getAllElements(HTMLElementName.SCRIPT); // .js
    for (Element e : el) {
      downURL(e.getAttributeValue("src"));
    }

    el = src.getAllElements(HTMLElementName.IMG); // img
    for (Element e : el) {
      downURL(e.getAttributeValue("src"));
    }
    
    // just replace every "http://apple.com/" by "output".
    String html = src.toString().replaceAll("\"https?://(www.)?apple.com/(.*?)\"", "\"$2\"");
    //System.out.println(html);
    
    saveFile("output/index.html", new ByteArrayInputStream(html.getBytes()));
  }

  static void downURL(String href) throws Exception {
    if (!href.matches("^https?.*$")) { // just to be sure its a link
      return;
    }

    // "http://apple.com/a/b.file" becomes "output/a/b.file"
    // obs. the dot grabs anything. its wrong but it works.
    String pathf = href.replaceFirst(
      "^https?://(www.)?apple.com/(.*)$", "output/$2"); 
    // "output/a/b.file" becomes "output/a/"
    String path = pathf.replaceFirst("^(.+/)[^/]+$", "$1"); 

    File file = new File(path);
    if (file.exists() == false && file.mkdirs() == false) {
      System.out.println("Error to create [" + path + "] folder.");
    }
    
    saveFile(pathf, new URL(href).openStream());
  }

  static void saveFile(String pathf, InputStream is) throws Exception {
    ReadableByteChannel rbc = Channels.newChannel(is);
    FileOutputStream fos = new FileOutputStream(pathf);
    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    fos.close();
    System.out.println("file: [" + pathf + "]");
  }

}
