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

        html += line;
      }

      System.out.println(html);

    } catch (MalformedURLException mue) {


    } catch (IOException ioe) {
    
    
    } finally {

    }

  }



}
