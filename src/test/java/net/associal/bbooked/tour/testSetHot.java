package net.associal.bbooked.tour;

import net.associal.bbooked.provider.ProviderImpl;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static org.mockito.Mockito.*;

public class testSetHot {

  @Test
  public void testServlet() throws ServletException, IOException {
    ProviderImpl jdbc = new ProviderImpl("jdbc:mysql://localhost:3306/bbooked_test",
      "bbooked", "bbooked");

    final SetHot servlet = new SetHot(jdbc);
    final HttpServletRequest request =  mock(HttpServletRequest.class);
    final HttpServletResponse response = mock(HttpServletResponse.class);
    final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
    final HttpSession session = mock(HttpSession.class);
    when(request.getRequestDispatcher("./tours")).thenReturn(dispatcher);

    when(request.getSession()).thenReturn(session);
    when(request.getSession().getId()).thenReturn("42");
    when(request.getParameter("id")).thenReturn("8");

    jdbc.setSession(3, "42"); // this way when we ask for user with session "42" we get "manager"

    servlet.doGet(request, response);

    verify(dispatcher).forward(request, response);

    jdbc.setSession(3, "0");

  }

}
