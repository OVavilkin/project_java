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
import java.util.Set;
import java.util.Vector;

import static org.mockito.Mockito.*;

public class testEditTour {

  @Test
  public void testServlet() throws ServletException, IOException {
    ProviderImpl jdbc = new ProviderImpl("jdbc:mysql://localhost:3306/bbooked_test",
      "bbooked", "bbooked");

    final EditTour servlet = new EditTour(jdbc);
    final HttpServletRequest request =  mock(HttpServletRequest.class);
    final HttpServletResponse response = mock(HttpServletResponse.class);
    final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
    final HttpSession session = mock(HttpSession.class);
    when(request.getRequestDispatcher("editTour.jsp")).thenReturn(dispatcher);

    when(request.getSession()).thenReturn(session);
    when(request.getSession().getId()).thenReturn("42");
    Vector<String> pNamesVector = new Vector();
    pNamesVector.add("id");
    pNamesVector.add("name");
    pNamesVector.add("description");
    pNamesVector.add("hotel");
    pNamesVector.add("person");
    pNamesVector.add("price");
    pNamesVector.add("tags");

    Enumeration<String> pNames = pNamesVector.elements();

    String[] cTags = new String[]{"rest", "shopping"};

    when(request.getParameterNames()).thenReturn(pNames);
    when(request.getParameter("id")).thenReturn("9");
    when(request.getParameter("name")).thenReturn("NAME");
    when(request.getParameter("description")).thenReturn("DESCRIPTION-MOD");
    when(request.getParameter("hotel")).thenReturn("1");
    when(request.getParameter("person")).thenReturn("1");
    when(request.getParameter("price")).thenReturn("1");
    when(request.getParameterValues("tags")).thenReturn(cTags);
    jdbc.setSession(1, "42"); // this way when we ask for user with session "42" we get "root"

    servlet.doGet(request, response);
    verify(dispatcher).forward(request, response);
    servlet.doPost(request, response);

    jdbc.setSession(1, "0");
  }

}
