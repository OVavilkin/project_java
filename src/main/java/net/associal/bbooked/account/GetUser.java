package net.associal.bbooked.account;

import com.google.gson.Gson;
import net.associal.bbooked.entity.User;
import net.associal.bbooked.provider.Provider;
import net.associal.bbooked.provider.ProviderImpl;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


@WebServlet("/account/getUser")
public class GetUser extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static final Logger logger = LogManager.getLogger(Provider.class);
  private Provider jdbc;

  public GetUser() {
    this.jdbc = new ProviderImpl();
  }

  /**
   * @param jdbc
   * we can provide custom jdbc
   * mainly for test purposes
   */
  public GetUser(Provider jdbc) {
    this.jdbc = jdbc;
  }

  /**
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException
   *
   * Returns user based on "id" read from GET request
   * otherwise returns the user ("who am I" call)
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    BasicConfigurator.configure();

    HttpSession session = request.getSession();
    User currentUser = jdbc.getUser(session.getId());

    // call by a not logged-in user
    if (currentUser == null) {
      logger.debug("NULL, sessionId: [" + session.getId() + "]");
      return;
    }

    User user;
    String userId = request.getParameter("id");
    if (userId == null || "".equals(userId)) {
      // we just call getUser without user id
      // so return current youser (like "who am i")
      user = currentUser;
    } else {
      userId = userId.trim();
      // get user by id
      int id = Integer.valueOf(userId);
      user = jdbc.getUser(id);
    }

    String userJson = new Gson().toJson(user);
    System.out.println(userJson);

    response.setContentType("text/plain");
    response.getWriter().write(userJson);
  }

}
