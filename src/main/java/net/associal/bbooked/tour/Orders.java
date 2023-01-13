package net.associal.bbooked.tour;

import net.associal.bbooked.entity.Tour;
import net.associal.bbooked.entity.User;
import net.associal.bbooked.entity.searchPattern.*;
import net.associal.bbooked.provider.Provider;
import net.associal.bbooked.provider.ProviderImpl;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@WebServlet("/orders")
public class Orders extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static final Logger logger = LogManager.getLogger(Provider.class);
  private Provider jdbc;

  public Orders() {
    this.jdbc = new ProviderImpl();
  }

  /**
   * @param jdbc
   * we can provide custom jdbc
   * mainly for test purposes
   */
  public Orders(Provider jdbc) {
    this.jdbc = jdbc;
  }

  /**
   *
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException
   *
   * List orders booked by the user
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    BasicConfigurator.configure();

    // is user logged in?
    User user = jdbc.getUser(request.getSession().getId());
    RequestDispatcher rd;
    if (user == null) {
      logger.debug("User not found");
      rd = request.getRequestDispatcher("notAuthorized.jsp");
      rd.forward(request, response);
      return;
    }


    Collection<SearchPattern> spatterns = new ArrayList<>();
    SearchPattern tour = new TourPattern();
    spatterns.add(tour);

    SearchPattern userPattern = new UserPattern(user.getId());
    spatterns.add(userPattern);

    Tour[] tours = jdbc.getAllTours(0, spatterns);

    for (Tour atour : tours) {
      if (atour.getPercent() > 0) {
        atour.setPrice(atour.getPrice() * (1 - atour.getPercent() / 100));
      }
    }

    request.setAttribute("tours", tours);

    rd = request.getRequestDispatcher("orders.jsp");
    rd.forward(request, response);
  }

}
