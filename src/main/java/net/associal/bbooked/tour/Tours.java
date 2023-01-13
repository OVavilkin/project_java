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
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@WebServlet("/tours")
public class Tours extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static final Logger logger = LogManager.getLogger(Provider.class);
  private Provider jdbc;

  public Tours() {
    this.jdbc = new ProviderImpl();
  }

  /**
   * @param jdbc
   * we can provide custom jdbc
   * mainly for test purposes
   */
  public Tours(Provider jdbc) {
    this.jdbc = jdbc;
  }

  /**
   *
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException
   *
   * List all tours, HOT tours on top
   * Also based on options selected (price/people/tags etc) applies
   * a search pattern to be used during the search.
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    BasicConfigurator.configure();

    Collection<String> tags = new ArrayList<>();
    String hotel = null;
    String person = null;
    String min = null;
    String max = null;

    Collection<SearchPattern> spatterns = new ArrayList<>();
    SearchPattern tour = new TourPattern();
    spatterns.add(tour);

    Enumeration pNames = request.getParameterNames();
    while (pNames.hasMoreElements()) {

      String paramName = (String) pNames.nextElement();
      paramName = paramName.trim();

      logger.debug("PARAMNAME: [" + paramName + "]");


      if (paramName.equals("tags")) {
        //System.out.println("Working with tags");
        String[] cTags = request.getParameterValues("tags");
        for (String tag : cTags) {
          tag = tag.trim();
          logger.debug("Current tag is [" + tag + "]");
          Pattern pattern = Pattern.compile("^(excursion|rest|shopping)$");
          Matcher matcher = pattern.matcher(tag);
          if (matcher.matches()) {
            tags.add(tag);
          }
        }

        if (tags.size() > 0) {
          spatterns.add(new TagPattern((String[]) tags.toArray(new String[0])));
          logger.debug("TagPattern: [" + tags.toArray() + "]");
        }
      }

      String paramValue = request.getParameter(paramName);
      //System.out.println("PARAMVALUE: [" + paramValue + "]");

      if ( paramName.equals("hotel") ) {
        logger.debug("Current param is: [" + paramName + "]");
        Pattern pattern = Pattern.compile("^(\\d+)-star$");
        Matcher matcher = pattern.matcher(paramValue);
        if (matcher.matches()) {
          hotel = matcher.group(1);
          logger.debug("Pattern matched [" + hotel + "]");
          spatterns.add(new HotelPattern(Integer.valueOf(hotel)));
        }
      }

      if ( paramName.equals("person") ) {
        Pattern pattern = Pattern.compile("^(\\d+) (person|persons)$");
        Matcher matcher = pattern.matcher(paramValue);
        if (matcher.matches()) {
          person = matcher.group(1);
          spatterns.add(new PersonPattern(Integer.valueOf(person)));
          logger.debug("PersonPattern " + person);
        } else {
          // ignore everything
          return;
        }
      }

      if ( paramName.equals("min") ) {
        Pattern pattern = Pattern.compile("^(\\d+)$");
        Matcher matcher = pattern.matcher(paramValue);
        if (matcher.matches()) {
          min = matcher.group(1);
          logger.debug("min" + min);
        } else {
          // ignore everything
          //return;
        }
      }

      if ( paramName.equals("max") ) {
        Pattern pattern = Pattern.compile("^(\\d+)$");
        Matcher matcher = pattern.matcher(paramValue);
        if (matcher.matches()) {
          max = matcher.group(1);
          logger.debug("max" + max);
        } else {
          // ignore everything
          //return;
        }
      }

      if (min != null && max != null) {
        spatterns.add(new PricePattern(Integer.valueOf(min), Integer.valueOf(max)));
        logger.debug("PricePattern[" + min + "," + max + "]");
      }
    }

    // is user logged in?
    User user = jdbc.getUser(request.getSession().getId());

    logger.debug("Checking for user with session id [" + request.getSession().getId() + "]");
    if (user == null) {
      logger.debug("User not found");
      request.getSession().setAttribute("role", "guest");
    } else {
      logger.debug("User found, username=" + user.getUsername());
      request.getSession().setAttribute("role", user.getRole());
    }

    Tour[] tours = jdbc.getAllTours(0, spatterns);

    if (user != null && user.getRole().equals("root")) {
      request.setAttribute("tours", tours);

    } else {
      // display only "new" tours for "user"
      Collection<Tour> newTours = new ArrayList<>();
      for (Tour theTour : tours) {
        if (theTour.getStatus().equals("new")) {
          newTours.add(theTour);
        }
      }

      for (Tour aTour : newTours) {
        logger.debug("Tour id: [" + aTour.getId() + "]");
      }

      request.setAttribute("tours", null);
      request.setAttribute("tours", (Tour[]) newTours.toArray(new Tour[newTours.size()]));


    }


    RequestDispatcher rd = request.getRequestDispatcher("tours.jsp");
    rd.forward(request, response);
  }
}
