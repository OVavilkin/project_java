package net.associal.bbooked.account;

import com.google.gson.Gson;
import net.associal.bbooked.entity.User;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;

import net.associal.bbooked.provider.Provider;
import net.associal.bbooked.provider.ProviderImpl;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


@WebServlet("/account/login")
public class Login extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static final Logger logger = LogManager.getLogger(Provider.class);
  private Provider jdbc;

  public Login() {
    this.jdbc = new ProviderImpl();
  }

  /**
   * @param jdbc
   * we can provide custom jdbc
   * mainly for test purposes
   */
  public Login(Provider jdbc) {
    this.jdbc = jdbc;
  }


  /**
   *
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException
   *
   * Takes username/password as JSON from POST request
   * if Ok logs in
   * otherwise returns error message
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    BasicConfigurator.configure();

    BufferedReader reader = request.getReader();
    User iuser = new Gson().fromJson(reader, User.class);
    HttpSession session = request.getSession();
    logger.info("Session ID: " + session.getId());
    logger.debug("CAME:" + iuser.getUsername() + " ]");

    User user = jdbc.getUser(iuser.getUsername(), iuser.getPassword(), session.getId());

    String userJson;

    if (user == null || user.isBlocked()) {
      userJson = new Gson().toJson(new Object() {
        String message = "User not found / blocked";
      });
    } else {
      userJson = new Gson().toJson(user);
      //System.out.println(userJson);
      session.setAttribute("role", user.getRole());

    }
    response.setContentType("text/plain");
    response.getWriter().write(userJson);

  }

}
