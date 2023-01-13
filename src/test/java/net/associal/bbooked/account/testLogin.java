package net.associal.bbooked.account;

import net.associal.bbooked.provider.ProviderImpl;
import org.junit.jupiter.api.Test;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class testLogin {

  @Test
  public void testServlet() throws ServletException, IOException {
    ProviderImpl jdbc = new ProviderImpl("jdbc:mysql://localhost:3306/bbooked_test",
      "bbooked", "bbooked");

    final Login servlet = new Login(jdbc);
    final HttpServletRequest request =  mock(HttpServletRequest.class);
    final HttpServletResponse response = mock(HttpServletResponse.class);
    final RequestDispatcher dispatcher = mock(RequestDispatcher.class);
    final HttpSession session = mock(HttpSession.class);

    when(request.getSession()).thenReturn(session);
    when(request.getSession().getId()).thenReturn("42");
    BufferedReader bf = new BufferedReader(new FileReader("in.txt"));
    when(request.getReader()).thenReturn(bf);
    PrintWriter pw = new PrintWriter(new FileWriter("out.txt"));
    when(response.getWriter()).thenReturn(new PrintWriter(pw));

    jdbc.setSession(2, "42"); // this way when we ask for user with session "42" we get "user"

    servlet.doPost(request, response);
    jdbc.setSession(2, "0"); // remove manually stored session

  }

}
