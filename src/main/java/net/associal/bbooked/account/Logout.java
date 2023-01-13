package net.associal.bbooked.account;

import net.associal.bbooked.provider.Provider;
import net.associal.bbooked.provider.ProviderImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/account/logout")
public class Logout extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private Provider jdbc;

  public Logout() {
    this.jdbc = new ProviderImpl();
  }

  /**
   * @param jdbc we can provide custom jdbc
   *             mainly for test purposes
   */
  public Logout(Provider jdbc) {
    this.jdbc = jdbc;
  }

  /**
   *
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException
   *
   * Logs user out, cleans session in DB and resets user role
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    HttpSession session = request.getSession();
    jdbc.logout(session.getId());
    session.setAttribute("role", "guest");

  }

}
