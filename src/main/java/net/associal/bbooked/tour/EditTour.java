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
import java.util.Enumeration;

@WebServlet("/editTour")
public class EditTour extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private Provider jdbc;

  public EditTour() {
    this.jdbc = new ProviderImpl();
  }

  /**
   * @param jdbc
   * we can provide custom jdbc
   * mainly for test purposes
   */
  public EditTour(Provider jdbc) {
    this.jdbc = jdbc;
  }

  /**
   *
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException
   *
   * Display the tour based on ID provided
   * POST request below used to store the data
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    User user = jdbc.getUser(request.getSession().getId());
    System.out.println("User with session id [" + request.getSession().getId() + "] logged in as " + user.getUsername() + ", " + user.getRole() + ";");
    RequestDispatcher rd;
    if (user == null) {
      rd = request.getRequestDispatcher("notAuthorized.jsp");
      rd.forward(request, response);
      return;
    }
    if (user.getRole().equals("root")) {
      String id = request.getParameter("id");
      request.setAttribute("tour", jdbc.getTour(Integer.parseInt(id)));
      rd = request.getRequestDispatcher("editTour.jsp");
      rd.forward(request, response);
    }
  }

  /**
   *
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException
   *
   * Modify existing tour, requires root role.
   * When called sends redirect to "editTour" to prevent multiple data sent on
   * page refresh
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

      Enumeration pNames = request.getParameterNames();
      if(pNames != null) {
        while (pNames.hasMoreElements()) {

          String paramName = (String) pNames.nextElement();
          paramName = paramName.trim();

          if (paramName.equals("tags")) {
            String[] cTags = request.getParameterValues("tags");
            tour.setTags(cTags);
          }

          String paramValue = request.getParameter(paramName);

          if (paramName.equals("id")) {
            tour.setId(Integer.valueOf(paramValue));
          }

          if (paramName.equals("name")) {
            tour.setName(paramValue);
          }

          if (paramName.equals("description")) {
            tour.setDescription(paramValue);
          }

          if (paramName.equals("hotel")) {
            tour.setHotel(Integer.valueOf(paramValue));
          }

          if (paramName.equals("person")) {
            tour.setPerson(Integer.valueOf(paramValue));
          }

          if (paramName.equals("hot")) {
            tour.setHot(true);
          }
        }
      }

      if (tour.getId() != 0) {
        System.out.println("Tour: " + tour.toString());
        jdbc.editTour(tour);
      } else {
        System.out.println("No tour id?");
      }

      request.setAttribute("tour", jdbc.getTour(tour.getId()));
      rd = request.getRequestDispatcher("editTour.jsp");
      response.sendRedirect(request.getContextPath() + "/editTour?id=" + tour.getId());
    }
  }
}
