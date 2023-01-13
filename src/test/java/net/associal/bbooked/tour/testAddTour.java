package net.associal.bbooked.tour;

import net.associal.bbooked.provider.ProviderImpl;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import java.io.IOException;

public class testAddTour {

  @Test
  public void testServlet() throws ServletException, IOException {
    ProviderImpl jdbc = new ProviderImpl("jdbc:mysql://localhost:3306/bbooked_test",
      "bbooked", "bbooked");

    final AddTour servlet = new AddTour(jdbc);
    final HttpServletRequest request =  mock(HttpServletRequest.class);
    final HttpServletResponse response = mock(HttpServletResponse.class);
    final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
    final HttpSession session = mock(HttpSession.class);
    when(request.getRequestDispatcher("addTour.jsp")).thenReturn(dispatcher);

    servlet.doGet(request, response);

    verify(dispatcher).forward(request, response);

    when(request.getSession()).thenReturn(session);
    when(request.getSession().getId()).thenReturn("42");
    when(request.getParameter("name")).thenReturn("NAME");
    when(request.getParameter("description")).thenReturn("DESCRIPTION");
    when(request.getParameter("hotel")).thenReturn("1");
    when(request.getParameter("person")).thenReturn("1");
    when(request.getParameter("price")).thenReturn("1");
    jdbc.setSession(1, "42"); // this way when we ask for user with session "42" we get "root"

    servlet.doPost(request, response);
  }

}
