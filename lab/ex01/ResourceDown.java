import java.io.*;

public class ResourceDown implements Downloader {

	public String download (LinkStack ls, String href, String pathf, boolean blocking, String httpReg, String pre) throws Exception {
    if (href == null || !href.matches("^" + httpReg + ".*$")) { // just to be sure its a link
      return null;
    }

    if (pathf == null) {
      pathf = href.replaceFirst("^" + httpReg + "(.*)$", pre + "$1"); // "http://site.com/a/b.file" => "offline/site.com/a/b.file" 
    }

    String path = pathf.replaceFirst("^(.+/)[^/]+$", "$1"); // folder path

    File file = new File(path);
    if (file.exists() == false && file.mkdirs() == false) {
			return null; // folder creation error.
    }

    file = new File(pathf);
    if (file.exists() == false) {
      return transfer(ls, pathf, href, blocking, httpReg, pre);
    }

		return null;
	}
	
  String transfer(LinkStack ls, String pathf, String href, boolean blocking, String httpReg, String pre) throws Exception {
    SaveFile sf = null;
    while(ls.head == null) {
      Thread.sleep(10);
    }
    sf = ls.head;
    ls.head = sf.next;
    sf.reset(pathf, href);
    if (blocking == false) {
      sf.interrupt();
    } else {
      if (sf.run2() == false) {
        System.out.print("-- <" + href + "> <" + pathf + ">");
        //System.out.println("\n-excpetion: " + e + "-msg: ");
        //e.printStackTrace();

        if (href.matches("^http.*$") == false) {
          return download(ls, href.replaceFirst("^(.+)$", "http://$1"), pathf, blocking, httpReg, pre);
        } else if (href.matches(".*\\.html$")) {
          return download(ls, href.replaceFirst("^(.+/)[^/]+$", "$1"), pathf, blocking, httpReg, pre);
        } else {
          System.out.print(" <giving up on this link> --");
          return null;
        }
      }
    } 
    return pathf;
  }
}
