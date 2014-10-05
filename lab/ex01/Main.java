import java.net.*;
import java.io.*;
import java.nio.channels.*;
import java.util.*;
import java.util.regex.*;
import net.htmlparser.jericho.*;

public class Main {

  public static void main(String[] args) throws Exception {

    URL url;
    InputStream is = null;
    BufferedReader br ;

    String line, html = "";

    br = new BufferedReader(
      new InputStreamReader(
        new URL("https://www.apple.com/").openStream()));

    while((line = br.readLine()) != null) {
      html += line + "\n";
    }


    File file = new File("output");
    if (!file.mkdir()) {
      System.out.println("Error to create output folder");
      return;
    }
    
    /*file = new File("output/src");
    if (!file.mkdir()) {
      System.out.println("Error to create src folder");
      return;
    }*/

    Writer out;
    out = new BufferedWriter(
      new OutputStreamWriter(
        new FileOutputStream("output/index.html"),
        "UTF-8"));
    out.write(html);
    out.close();

    Source src = new Source(new File("output/index.html"));

    List<Element> el = src.getAllElements(HTMLElementName.LINK);
    for (Element e : el) {
      String rel = e.getAttributeValue("rel");
      String href = e.getAttributeValue("href");
      String pathf = "", path = "";
      Pattern p;
      System.out.println("rel: [" + rel + "], href: [" + href + "]");

      if (rel.equals("stylesheet")) {
        if (!href.matches("^https?.*$")) {
          continue;
        }
        url = new URL(href);
        
        // the dot actually grabs any char
        pathf = href.replaceFirst("^https?://(www.)?apple.com/(.*)$", "output/$2");
        path = pathf.replaceFirst("^(.+/)[^/]+$", "$1");
        file = new File(path);
        if (file.exists() == false && file.mkdirs() == false) {
          System.out.println("Error to create [" + path + "] folder.");
        }

        System.out.println("pathf: [" + pathf + "]");
        
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream(pathf);
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);

        System.out.println("------------");

        // .css
      } else if (rel.equals("alternate")) {

        // .rss
      }
    }


    el = src.getAllElements(HTMLElementName.SCRIPT);
    for (Element e : el) {
      String a_src = e.getAttributeValue("src");
      if (!a_src.equals("")) {

      } 
    }

    el = src.getAllElements(HTMLElementName.IMG);
    for (Element e : el) {
      String a_src = e.getAttributeValue("src");

    }
    
    
    
    System.out.println("===========\n"+src.getCacheDebugInfo());

  }

}
