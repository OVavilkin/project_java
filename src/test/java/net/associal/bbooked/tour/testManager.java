package net.associal.bbooked.tour;

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

import static org.mockito.Mockito.*;

public class testManager {

  @Test
  public void testServlet() throws ServletException, IOException {
    ProviderImpl jdbc = new ProviderImpl("jdbc:mysql://localhost:3306/bbooked_test",
      "bbooked", "bbooked");

    final Manager servlet = new Manager(jdbc);
    final HttpServletRequest request =  mock(HttpServletRequest.class);
    final HttpServletResponse response = mock(HttpServletResponse.class);
    final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
    final HttpSession session = mock(HttpSession.class);
    when(request.getRequestDispatcher("manager.jsp")).thenReturn(dispatcher);
    when(request.getParameter("status")).thenReturn("paid");
    when(request.getParameter("id")).thenReturn("9");
    when(request.getParameter("percent")).thenReturn("0.05");

    when(request.getSession()).thenReturn(session);
    when(request.getSession().getId()).thenReturn("42");
    jdbc.setSession(1, "42"); // this way when we ask for user with session "42" we get "root"

    servlet.doGet(request, response);
    verify(dispatcher).forward(request, response);
    servlet.doPost(request, response);

    jdbc.setSession(1, "0");
  }

}
