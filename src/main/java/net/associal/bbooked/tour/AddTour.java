package net.associal.bbooked.tour;

import net.associal.bbooked.entity.Tour;
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

@WebServlet("/addTour")
public class AddTour extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private Provider jdbc;

  public AddTour() {
    this.jdbc = new ProviderImpl();
  }

  /**
   * @param jdbc
   * we can provide custom jdbc
   * mainly for test purposes
   */
  public AddTour(Provider jdbc) {
    this.jdbc = jdbc;
  }

  /**
   *
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException
   *
   * Defence from "continious refresh" so data not resent to server.
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    RequestDispatcher rd;
    rd = request.getRequestDispatcher("addTour.jsp");
    rd.forward(request, response);
  }

  /**
   *
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException
   *
   * Creates a new tour based on parameters provided by GET request:
   * tags, name, description, hotel, person, price;
   * requires a root role, sends to "not authorized" page otherwise
   *
   * When complete sends redirect.
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    response.setContentType("text/html; charset=UTF-8");
    response.setCharacterEncoding("UTF-8");
    request.setCharacterEncoding("UTF-8");

    User user = jdbc.getUser(request.getSession().getId());
    RequestDispatcher rd;
    if (user == null) {
      rd = request.getRequestDispatcher("notAuthorized.jsp");
      rd.forward(request, response);
      return;
    }
    if (user.getRole().equals("root")) {

      Tour tour = new Tour();

      String[] cTags = request.getParameterValues("tags");
      tour.setTags(cTags);

      tour.setName(request.getParameter("name"));
      tour.setDescription(request.getParameter("description"));
      tour.setHotel(Integer.valueOf(request.getParameter("hotel")));
      tour.setPerson(Integer.valueOf(Integer.valueOf(request.getParameter("person"))));
      tour.setPrice(Double.valueOf(request.getParameter("price")));


      int id = 0;
      if (tour.getName() != null && tour.getDescription() != null) {
        id = jdbc.addTour(tour);
        //tour = jdbc.getTour(id);
      } else {
        System.out.println("No tour?");
      }

      response.sendRedirect(request.getContextPath() + "/tours");
    }

    jdbc.close();

  }

}
