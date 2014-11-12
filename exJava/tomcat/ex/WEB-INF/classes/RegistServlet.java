import java.io.IOException;
import java.io.PrintWriter;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class RegistServlet extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    response.setContentType("text/html");

    PrintWriter out = response.getWriter();
    out.println("<html>");
    out.println("<body>");

    String name = request.getParameter("name");
    String addr = request.getParameter("addr");

    if (name != null && addr != null) {
      out.println("name = " + name + "<br>");
      out.println("addr = " + addr);
    }

    out.println("<P>");
    out.print("<form action=\"02-02\" method=POST>");
    out.println("<input type=text size=20 name=name placeholder=name>");
    out.println("<br>");
    out.println("<input type=text size=20 name=addr placeholder=address>");
    out.println("<br>");
    out.println("<input type=submit>");
    out.println("</form>");

    out.println("</body>");
    out.println("</html>");
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    doGet(request, response);
  }

}
