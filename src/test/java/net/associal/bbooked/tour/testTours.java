package net.associal.bbooked.tour;

import net.associal.bbooked.provider.Provider;
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

public class testTours {

  @Test
  public void testServlet() throws ServletException, IOException {
    ProviderImpl jdbc = new ProviderImpl("jdbc:mysql://localhost:3306/bbooked_test",
      "bbooked", "bbooked");

    final Tours servlet = new Tours(jdbc);
    final HttpServletRequest request =  mock(HttpServletRequest.class);
    final HttpServletResponse response = mock(HttpServletResponse.class);
    final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
    final HttpSession session = mock(HttpSession.class);
    when(request.getRequestDispatcher("tours.jsp")).thenReturn(dispatcher);

    Vector<String> pNamesVector = new Vector();

    pNamesVector.add("tags");
    pNamesVector.add("hotel");
    pNamesVector.add("person");
    pNamesVector.add("min");
    pNamesVector.add("max");

    Enumeration<String> pNames = pNamesVector.elements();

    String[] cTags = new String[]{"rest", "shopping"};

    when(request.getParameterNames()).thenReturn(pNames);
    when(request.getParameter("hotel")).thenReturn("1");
    when(request.getParameter("person")).thenReturn("1 person");
    when(request.getParameter("min")).thenReturn("1");
    when(request.getParameter("max")).thenReturn("99999");
    when(request.getParameterValues("tags")).thenReturn(cTags);


    when(request.getSession()).thenReturn(session);
    when(request.getSession().getId()).thenReturn("42");
    when(request.getParameter("id")).thenReturn("9");

    jdbc.setSession(2, "42"); // this way when we ask for user with session "42" we get "user"

    servlet.doGet(request, response);

    jdbc.setSession(2, "0");

  }

}
