import java.lang.Thread;
import java.net.*;
import java.io.*;
import java.nio.channels.*;
import java.util.*;
import java.util.regex.*;
import net.htmlparser.jericho.*;

public class Main {

  public static void main(String[] args) throws Exception {
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

      new PageDown(website, base, depth, ls);
    }
  }

}
 



