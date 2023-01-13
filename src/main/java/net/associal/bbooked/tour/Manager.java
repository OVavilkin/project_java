package net.associal.bbooked.tour;

import net.associal.bbooked.entity.Tour;
import net.associal.bbooked.entity.User;
import net.associal.bbooked.entity.searchPattern.ManagerPattern;
import net.associal.bbooked.entity.searchPattern.SearchPattern;
import net.associal.bbooked.entity.searchPattern.TourPattern;
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

@WebServlet("/manager")
public class Manager extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private Provider jdbc;
  private static final Logger logger = LogManager.getLogger(Provider.class);

  public Manager() {
    this.jdbc = new ProviderImpl();
  }

  /**
   * @param jdbc
   * we can provide custom jdbc
   * mainly for test purposes
   */
  public Manager(Provider jdbc) {
    this.jdbc = jdbc;
  }

  /**
   *
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException
   *
   * Requires root or manager role to action.
   * Displays a list of tours that can be managed
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    BasicConfigurator.configure();

    // is user logged in?
    User user = jdbc.getUser(request.getSession().getId());
    RequestDispatcher rd;
    if (user == null) {
      logger.debug("User not found");
      rd = request.getRequestDispatcher("notAuthorized.jsp");
    } else if (user.getRole().equals("root") || user.getRole().equals("manager")) {

      Collection<SearchPattern> spatterns = new ArrayList<>();
      SearchPattern tour = new TourPattern();
      spatterns.add(tour);

      SearchPattern managerPattern = new ManagerPattern();
      spatterns.add(managerPattern);

      Tour[] tours = jdbc.getAllTours(0, spatterns);
      jdbc.close();

      for (Tour atour : tours) {
        if (atour.getPercent() > 0) {
          atour.setPrice(atour.getPrice() * (1 - atour.getPercent() / 100));
        }
      }

      request.setAttribute("tours", tours);

      rd = request.getRequestDispatcher("manager.jsp");

    } else {
      rd = request.getRequestDispatcher("notAuthorized.jsp");

    }

    rd.forward(request, response);
  }

  /**
   *
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException
   *
   * Perform changes to a tour based on tour ID
   * changes status, discout percent and/or user booked.
   * Redirects to /manager to prevent data resend on page update.
   * Requries root or manager role.
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    BasicConfigurator.configure();

    User user = jdbc.getUser(request.getSession().getId());
    RequestDispatcher rd;
    if (user == null) {
      logger.debug("User not found");
      response.sendRedirect(request.getContextPath() + "./notAuthorized.jsp");
    } else if (user.getRole().equals("root") || user.getRole().equals("manager")) {

      String status = request.getParameter("status");
      if (status.equals("Change Status")) {
        status = null;
      }
      String id = request.getParameter("id");
      String percent = request.getParameter("percent");

      // test if we can get a number from the percent
      try {
        Double.valueOf(percent);
      } catch (NumberFormatException e) {
        percent = null;
      }

      if (status != null) {
        jdbc.tourStatusUpdate(id, status);
      }

      if (percent != null) {
        jdbc.tourPercentUpdate(id, percent);
      }

    } else {
      response.sendRedirect(request.getContextPath() + "./notAuthorized.jsp");
    }
    response.sendRedirect(request.getContextPath() + "/manager");
  }
}
