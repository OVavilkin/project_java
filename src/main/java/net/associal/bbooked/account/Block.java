package net.associal.bbooked.account;

import net.associal.bbooked.entity.User;
import net.associal.bbooked.provider.Provider;
import net.associal.bbooked.provider.ProviderImpl;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

@WebServlet("/account/block")
public class Block extends HttpServlet {
  private static final long serialVersionUID = 1L;
  private static final Logger logger = LogManager.getLogger(Provider.class);
  private Provider jdbc;


  public Block() {
    this.jdbc = new ProviderImpl();
  }

  /**
   * @param jdbc
   * we can provide custom jdbc
   * mainly for test purposes
   */
  public Block(Provider jdbc) {
    this.jdbc = jdbc;
  }

  /**
   * @param request
   * @param response
   * @throws ServletException
   * @throws IOException
   * Blocks a user based on ID
   * root permission required to make a call
   * reads "id" parameter of the GET call
   */
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    BasicConfigurator.configure();

    HttpSession session = request.getSession();
    User currentUser = jdbc.getUser(session.getId());

    // call by a not logged-in user
    if (currentUser == null || !currentUser.getRole().equals("root")) {
      logger.debug("NULL, sessionId: [" + session.getId() + "]");
      return;
    }

    int id = Integer.valueOf(request.getParameter("id"));
    logger.info("(Un)blocking user id[" + id + "]");
    jdbc.blockUser(id);
  }

}
