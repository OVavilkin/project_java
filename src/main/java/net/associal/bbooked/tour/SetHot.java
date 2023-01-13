package net.associal.bbooked.tour;

import net.associal.bbooked.entity.User;
import net.associal.bbooked.provider.Provider;
import net.associal.bbooked.provider.ProviderImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/setHot")
public class SetHot extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private Provider jdbc;

  public SetHot() {
    this.jdbc = new ProviderImpl();
  }

  /**
   * @param jdbc
   * we can provide custom jdbc
   * mainly for test purposes
   */
  public SetHot(Provider jdbc) {
    this.jdbc = jdbc;
  }

  /**
   *
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException
   *
   * The "set hot" button implementation
   * Checks if user is root or manager and (un)sets tour as hot
   * Thus moving it to the top of the list
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    // is user logged in?
    User user = jdbc.getUser(request.getSession().getId());
    RequestDispatcher rd;

    if (user == null) {
      System.out.println("User not found");
      rd = request.getRequestDispatcher("notAuthorized.jsp");
      rd.forward(request, response);
      return;
    }

    if (user.getRole().equals("manager") || user.getRole().equals("root")) {
      String tourId = request.getParameter("id");
      jdbc.setHot(tourId);
    }

    rd = request.getRequestDispatcher("./tours");
    rd.forward(request, response);
  }
}
