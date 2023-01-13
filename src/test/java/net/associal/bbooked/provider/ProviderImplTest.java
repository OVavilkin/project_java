package net.associal.bbooked.provider;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import net.associal.bbooked.entity.Tour;
import net.associal.bbooked.entity.User;
import net.associal.bbooked.entity.searchPattern.SearchPattern;
import net.associal.bbooked.entity.searchPattern.TourPattern;
import net.associal.bbooked.entity.searchPattern.UserPattern;
import org.junit.jupiter.api.Test;

import javax.naming.NoInitialContextException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class ProviderImplTest {

  @Test
  void getTour() {
    Provider jdbc = new ProviderImpl("jdbc:mysql://localhost:3306/bbooked_test",
      "bbooked", "bbooked");
    Tour tour = jdbc.getTour(1);
    assertNotNull(tour);
    assertEquals(1, tour.getId());
    assertEquals("Monday tour", tour.getName());
    assertEquals("A good shopping walk with a celebrity", tour.getDescription());
    assertEquals(false, tour.isHot());
    jdbc.close();
  }

  @Test
  void getTours() {
    Provider jdbc = new ProviderImpl("jdbc:mysql://localhost:3306/bbooked_test",
      "bbooked", "bbooked");
    Tour[] tours = jdbc.getAllTours(0);
    assertNotNull(tours);
    assertEquals(1, tours[0].getId());
    assertEquals("Monday tour", tours[0].getName());
    assertEquals("A good shopping walk with a celebrity", tours[0].getDescription());
    assertEquals(false, tours[0].isHot());
    assertEquals(1, tours[0].getId());

    System.out.println(Arrays.deepToString(tours));
    assertEquals(2, tours[1].getId());
    assertEquals("Tuesday tour", tours[1].getName());
    assertEquals("Travel to Kyshyniv, massage at SPA saloon included", tours[1].getDescription());
    assertEquals(false, tours[1].isHot());
    jdbc.close();
  }

  @Test
  void getUser() {
    ProviderImpl jdbc = new ProviderImpl("jdbc:mysql://localhost:3306/bbooked_test",
      "bbooked", "bbooked");
    User user = jdbc.getUser(1);
    assertEquals(1, user.getId());
    user = jdbc.getUser("root", "admin", "42");
    assertEquals(1, user.getId());
    user = jdbc.getUser("43");
    assertEquals(null, user);
    jdbc.close();
  }

  @Test
  void util() {
    ProviderImpl jdbc = new ProviderImpl("jdbc:mysql://localhost:3306/bbooked",
      "bbooked", "bbooked");
    jdbc.setSession(1, "42");
    User user = jdbc.getUser("42");
    assertEquals(1, user.getId());
    jdbc.logout("42");
    jdbc.orderTour(3, 8);
    jdbc.setHot("1"); // set hot
    jdbc.setHot("1"); // unset hot
    jdbc.tourStatusUpdate("8", "registered");
    jdbc.tourPercentUpdate("8", "0.5");
    // uncomment will spam users on test run
    //jdbc.addUser("user1", "user1", "user1.email", "42");
    boolean exUser = jdbc.existingUser("user");
    assertEquals(exUser, true);
    exUser = jdbc.existingUser("noUser");
    assertEquals(exUser, false);
    List<User> users = jdbc.getUsers(0);
    jdbc.blockUser(4); // block user
    jdbc.blockUser(4); // unblock user
    jdbc.close();
  }

  @Test
  void tours() {
    ProviderImpl jdbc = new ProviderImpl("jdbc:mysql://localhost:3306/bbooked",
      "bbooked", "bbooked");

    Tour tour = jdbc.getTour(1);
    String name = tour.getName();
    tour.setName("unnamed");
    jdbc.editTour(tour);
    tour.setName(name);
    jdbc.editTour(tour);
    jdbc.addTour(tour);
    Tour[] tours = jdbc.getAllTours(0);
    SearchPattern tourPattern = new TourPattern();
    Collection<SearchPattern> searchPatterns = new ArrayList<>();
    searchPatterns.add(tourPattern);

    tours = jdbc.getAllTours(0, searchPatterns);
    jdbc.close();

  }

  @Test
  void other() {
    try {
      DBManagerInterface dbManagerInterface = new DBManagerImplPool();
      dbManagerInterface.getConnection();
    } catch (IllegalStateException | SQLException e) {
      // do nothing: expected
    }
  }
}
