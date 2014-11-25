import java.lang.Thread;
import java.net.*;
import java.io.*;
import java.nio.channels.*;
import java.util.*;
import java.util.regex.*;
import net.htmlparser.jericho.*;

public class PageDown {

  private String webExtensions = "com|net|org|co|jp";
  private String httpReg = "(?:https?://www\\.|https?://|www\\.)";

  private String pre, back, baseReg;
  private LinkStack ls;

  PageDown(String url, String pre, int depth, LinkStack ls) throws Exception {
    if(depth-- <= 0 || url == null) return;	// if our download range has finished
		this.ls = ls; this.pre = pre;
    System.out.print("\n++ <" + url + "> <" + depth + ">");
	  if (url.matches("^http.*$") == false) url = url.replaceFirst("^(.+)$", "http://$1"); // inserts "http" if it doesn't starts with one
    if (url.matches(".*\\.html$") == false) url = url.replaceFirst("^(.+?)/?$", "$1/index.html");	// inserts "html" if it doesn't ends with one
    String base = url.replaceFirst("^" + httpReg + "(.+?\\.(?:" + webExtensions + "))/?(?:.*)$", "$1/"); // "https://www.site.com/index.html" => "site.com/" 
    System.out.print(" <" + base + "> / <" + url + "> ++");
    baseReg = base.replaceAll("\\.", "\\\\."); // "site.com/" => "site\.com/"

		DownFactory downFactory = new DownFactory();

		String pathf = downFactory.create().download(ls, url, null, true, httpReg, pre); // index.html
    if (pathf == null) return; // download fail
    
    back = pathf.replaceAll("(?:^\\w+/)|[^/]", "").replaceAll("/", "../"); // we will add a couple of "../" on this pages links

    // soon we'll start downloading the page resources
    Source src = new Source(new File(pathf)); // jericho init
    String s = src.toString();
    s = s.replaceAll("href=\"/(.*?)\"", "href=\"http://www." + base + "$1\""); // href="/blabla/" => href="http://www.site.com/blabla/"
    s = s.replaceAll("src=\"(.*?)\\?(.*?)\"", "src=\"$1%3F$2\""); // src="..?.." => src="..%3F.."
    replaceSave(s.replaceAll("href=\"([^\"]*)/\"", "href=\"$1/index.html\""), pathf, back); // href="..../" => href="..../index.html"

		downElements(downFactory, src, HTMLElementName.SCRIPT, "src");
		downElements(downFactory, src, HTMLElementName.IMG, "src");
		downElements(downFactory, src, HTMLElementName.LINK, "href");

    List<Element> el = new Source(s).getAllElements(HTMLElementName.A);
    for (Element e : el) new PageDown(e.getAttributeValue("href"), pre, depth, ls);
  }

	void downElements (DownFactory downFactory, Source src, String html, String attr) throws Exception {
    List<Element> el = src.getAllElements(html);
    for (Element e : el) downFactory.create().download(ls, e.getAttributeValue(attr), null, false, httpReg, pre);
	}

  void replaceSave(String s, String pathf, String pre) throws Exception {
    FileOutputStream fos = null;
    s = s.replaceAll("\"" + httpReg + "(.*?)\"", "\"" + pre + "$1\"");
		InputStream is = new ByteArrayInputStream(s.getBytes());
    fos = new FileOutputStream(pathf);
    ReadableByteChannel rbc = Channels.newChannel(is);
    fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
		fos.close();
  }
}
