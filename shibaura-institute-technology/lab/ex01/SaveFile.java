import java.lang.Thread;
import java.net.*;
import java.io.*;
import java.nio.channels.*;
import java.util.*;
import java.util.regex.*;
import net.htmlparser.jericho.*;

public class SaveFile implements Runnable {
  private String pathf, href;
  public SaveFile next; // for linked stack 
  public LinkStack ls;
  private int id; // sysout only

  SaveFile(LinkStack ls, int id) {
    this.ls = ls;
    this.id = id; // sysout only
  }

  void reset(String pathf, String href) {
    this.pathf = pathf;
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
      System.out.println("THREAD ERROR 01. id: " + id);
      System.out.print("--");
      System.out.print(" <" + href + ">");
      System.out.print(" <" + pathf + ">");
      System.exit(1);
    }
  }

  public boolean run2() {
		InputStream is;
    try {
      is = new URL(href).openStream();
    } catch(Exception e) {
      finish();
      return false;
    }

    FileOutputStream fos = null;
    try {
      fos = new FileOutputStream(pathf);
      ReadableByteChannel rbc = Channels.newChannel(is);
      fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
    } catch (Exception e) {
      System.out.print("x");
        System.out.println("THREAD ERROR. id 02: " + id);
        System.out.print("--");
        System.out.print(" <" + href + ">");
        System.out.print(" <" + pathf + ">");
        System.exit(1);
    } finally {
      System.out.print("<" + id + ">");
      finish();
      try {fos.close();} catch(Exception e) 
      {
        System.out.println("THREAD ERROR. id 03: " + id);
        System.out.print("--");
        System.out.print(" <" + href + ">");
        System.out.print(" <" + pathf + ">");
        System.exit(1);
      }
      return true;
    }
  }

  public void finish() {
    pathf = null;
    next = ls.head;
    ls.head = this;
  }

}
