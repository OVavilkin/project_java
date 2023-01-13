package net.associal.bbooked.account;

import com.google.gson.Gson;
import net.associal.bbooked.entity.User;
import net.associal.bbooked.provider.Provider;
import net.associal.bbooked.provider.ProviderImpl;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/account/getUsers")
public class GetUsers extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static final Logger logger = LogManager.getLogger(Provider.class);
  private Provider jdbc;

  public GetUsers() {
    this.jdbc = new ProviderImpl();
  }

  /**
   * @param jdbc
   * we can provide custom jdbc
   * mainly for test purposes
   */
  public GetUsers(Provider jdbc) {
    this.jdbc = jdbc;
  }

  /**
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException
   *
   * Returns all users registered
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    HttpSession session = request.getSession();
    User currentUser = jdbc.getUser(session.getId());

    // call by a not logged-in user
    if (currentUser == null || !currentUser.getRole().equals("root")) {
      return;
    }

    List<User> users = jdbc.getUsers(0);

    String userJson = new Gson().toJson(users);
    System.out.println(userJson);

    response.setContentType("text/plain");
    response.getWriter().write(userJson);
  }

}
