import java.io.IOException;
import java.io.PrintWriter;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class JConfirmServlet extends HttpServlet {

  public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {


    String mail = request.getParameter("mail");
    String pass = request.getParameter("pass");

    request.setAttribute("mail", mail);
    request.setAttribute("pass", pass);

    if (name != null && addr != null) {
    } else {


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
