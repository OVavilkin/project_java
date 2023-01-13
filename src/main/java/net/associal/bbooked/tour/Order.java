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

@WebServlet("/order")
public class Order extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private Provider jdbc;

  public Order() {
    this.jdbc = new ProviderImpl();
  }

  /**
   * @param jdbc
   * we can provide custom jdbc
   * mainly for test purposes
   */
  public Order(Provider jdbc) {
    this.jdbc = jdbc;
  }

  /**
   *
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException
   *
   * Lets user book a tour
   * Checks if user has logged in
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    User user = jdbc.getUser( request.getSession().getId() );
    RequestDispatcher rd;
    if(user == null) {
      rd = request.getRequestDispatcher("notAuthorized.jsp");
      rd.forward(request, response);
      return;
    }
    if(user.getRole().equals("user")) {
      String id = request.getParameter("id");
      jdbc.orderTour(user.getId(), Integer.valueOf(id));
      rd = request.getRequestDispatcher("order.jsp");
      rd.forward(request, response);
    }

  }

}
