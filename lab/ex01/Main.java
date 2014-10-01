import java.net.*;
import java.io.*;
import java.util.*;
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
    
    file = new File("output/src");
    if (!file.mkdir()) {
      System.out.println("Error to create src folder");
      return;
    }

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

      if (rel.equals("stylesheet")) {
        System.out.println("------------");
        url = new URL(href);
        System.out.println("------------");
        ; // name
        ReadableByteChannel rbc = Channels.newChannel(url.openStream());
        FileOutputStream fos = new FileOutputStream("destName");
        fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);


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
