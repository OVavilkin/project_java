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

public class testOrder {

  @Test
  public void testServlet() throws ServletException, IOException {
    ProviderImpl jdbc = new ProviderImpl("jdbc:mysql://localhost:3306/bbooked_test",
      "bbooked", "bbooked");

    final Order servlet = new Order(jdbc);
    final HttpServletRequest request =  mock(HttpServletRequest.class);
    final HttpServletResponse response = mock(HttpServletResponse.class);
    final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
    final HttpSession session = mock(HttpSession.class);

    jdbc.setSession(2, "43"); // this way when we ask for user with session "42" we get "user"

    when(request.getRequestDispatcher("order.jsp")).thenReturn(dispatcher);
    when(request.getParameter("id")).thenReturn("9");

    when(request.getSession()).thenReturn(session);
    when(request.getSession().getId()).thenReturn("43");

    servlet.doGet(request, response);

    jdbc.setSession(2, "0");
  }

}
