import java.net.*;
import java.io.*;
import net.htmlparser.jericho.*;

public class Main {

  public static void main(String[] args) {

    URL url;
    InputStream is = null;
    BufferedReader br ;

    String line, html = "";


    try {
      url = new URL("https://www.apple.com/");
      is = url.openStream();
      br = new BufferedReader(new InputStreamReader(is));

      while((line = br.readLine()) != null) {

        html += line + "\n";
      }

      //System.out.println(html);

    } catch (MalformedURLException mue) {


    } catch (IOException ioe) {
    
    
    } finally {

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
    try {
    out = new BufferedWriter(
      new OutputStreamWriter(
        new FileOutputStream("output/index.html"),
        "UTF-8"));
    out.write(html);
    out.close();
    
    } catch (FileNotFoundException fnfe) {
      System.out.println("Error to find file output/index.html @ FileOutputStream");
    } catch (UnsupportedEncodingException uee) {
      System.out.println("Error to encode file output/index.html @ OutputStreamWriter");

    } catch (IOException ioe) {
      System.out.println("Error to write/close file output/index.html");
    }     


  }



}
