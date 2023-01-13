package net.associal.bbooked.account;

import com.google.gson.Gson;
import net.associal.bbooked.entity.User;
import net.associal.bbooked.provider.Provider;
import net.associal.bbooked.provider.ProviderImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.BufferedReader;
import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;


@WebServlet("/account/register")
public class Register extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static final Logger logger = LogManager.getLogger(Provider.class);
  private Provider jdbc;

  public Register() {
    this.jdbc = new ProviderImpl();
  }

  /**
   * @param jdbc
   * we can provide custom jdbc
   * mainly for test purposes
   */
  public Register(Provider jdbc) {
    this.jdbc = jdbc;
  }

  /**
   *
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException
   *
   * Takes username/email/password as JSON from POST request
   * if user exists - returns error message
   * otherwise creates user and logs in.
   */
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    BasicConfigurator.configure();

    BufferedReader reader = request.getReader();
    User iuser = new Gson().fromJson(reader, User.class);
    HttpSession session = request.getSession();
    logger.debug("CAME:" + iuser.getUsername() + " ][ " + iuser.getEmail() + "]");

    String userJson;

    if (!jdbc.existingUser(iuser.getUsername())) {
      // ok, no duplicate user found
      User user = jdbc.addUser(iuser.getUsername(), iuser.getPassword(), iuser.getEmail(), session.getId());
      userJson = new Gson().toJson(user);
      session.setAttribute("role", user.getRole());

    } else {
      userJson = new Gson().toJson(new Object() {
        String error = "Such user exists";
      });
    }

    response.setContentType("text/plain");
    response.getWriter().write(userJson);

  }

}
