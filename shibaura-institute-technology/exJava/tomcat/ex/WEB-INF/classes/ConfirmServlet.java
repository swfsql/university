import java.io.IOException;
import java.io.PrintWriter;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ConfirmServlet extends HttpServlet {

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
    out.print("<form action=\"02-03?name="+ name + "&addr=" + addr  + "\" method=POST>");
    out.println("<input type=submit value=Confirm>");
    out.println("</form>");

    out.println("</body>");
    out.println("</html>");
  }

  public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
    doGet(request, response);
  }

}
