package net.associal.bbooked.account;

import net.associal.bbooked.account.Block;
import net.associal.bbooked.provider.ProviderImpl;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Vector;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class testBlock {

  @Test
  public void testServlet() throws ServletException, IOException {
    ProviderImpl jdbc = new ProviderImpl("jdbc:mysql://localhost:3306/bbooked_test",
      "bbooked", "bbooked");

    final Block servlet = new Block(jdbc);
    final HttpServletRequest request =  mock(HttpServletRequest.class);
    final HttpServletResponse response = mock(HttpServletResponse.class);
    final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
    final HttpSession session = mock(HttpSession.class);

    when(request.getSession()).thenReturn(session);
    when(request.getSession().getId()).thenReturn("42");
    when(request.getParameter("id")).thenReturn("2");

    jdbc.setSession(1, "42"); // this way when we ask for user with session "42" we get "user"

    servlet.doGet(request, response);

    jdbc.blockUser(2); // unblock the user

  }

}
