package net.associal.bbooked.entity.searchPattern;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collection;

public class SQLStringTest {

    @Test
    void tourOnly() {
        SearchPattern tour = new TourPattern();
        Collection<SearchPattern> searchPatterns = new ArrayList<>();
        searchPatterns.add(tour);
        SQLString sqlString = new SQLString(searchPatterns);
        assertEquals(
                "select Tour.id, Tour.name, Tour.description, Tour.user, Status.status, Tour.hot, Tour.hotel, Tour.person, Tour.price, Tour.percent from Tour inner join Status on Tour.status = Status.id  LIMIT ?, 100;",
                sqlString.generateSql().trim()
        );
    }

    @Test
    void tourAndTag() {
        SearchPattern tour = new TourPattern();
        Collection<SearchPattern> searchPatterns = new ArrayList<>();
        searchPatterns.add(tour);
        SearchPattern tag = new TagPattern(new String[] { "excursion", "shopping" }); // Don't forget single quotes!
        searchPatterns.add(tag);
        SQLString sqlString = new SQLString(searchPatterns);
        //System.out.println(sqlString.generateSql());
        assertEquals(
                "select Tour.id, Tour.name, Tour.description, Tour.user, Status.status, Tour.hot, Tour.hotel, Tour.person, Tour.price, Tour.percent from Tour inner join Status on Tour.status = Status.id  where Tour.id in (select tour from TourTag inner join Tag on Tag.id = TourTag.tag where Tag.tag in ('excursion', 'shopping')) LIMIT ?, 100;",
                sqlString.generateSql().trim()
        );

    }

    @Test
    void tourAndPrice() {
        SearchPattern tour = new TourPattern();
        Collection<SearchPattern> searchPatterns = new ArrayList<>();
        searchPatterns.add(tour);
        SearchPattern price = new PricePattern(2000, 8000); // Don't forget single quotes!
        searchPatterns.add(price);
        SQLString sqlString = new SQLString(searchPatterns);
        //System.out.println(sqlString.generateSql());
        assertEquals(
                "select Tour.id, Tour.name, Tour.description, Tour.user, Status.status, Tour.hot, Tour.hotel, Tour.person, Tour.price, Tour.percent from Tour inner join Status on Tour.status = Status.id  where price >= 2000 and price <= 8000 LIMIT ?, 100;",
                sqlString.generateSql().trim()
        );

    }

    @Test
    void tourAndPriceAndHotel() {
        SearchPattern tour = new TourPattern();
        Collection<SearchPattern> searchPatterns = new ArrayList<>();
        searchPatterns.add(tour);
        SearchPattern price = new PricePattern(2000, 8000); // Don't forget single quotes!
        searchPatterns.add(price);
        SearchPattern hotel = new HotelPattern(3);
        searchPatterns.add(hotel);
        SQLString sqlString = new SQLString(searchPatterns);
        //System.out.println(sqlString.generateSql());
        assertEquals(
                "select Tour.id, Tour.name, Tour.description, Tour.user, Status.status, Tour.hot, Tour.hotel, Tour.person, Tour.price, Tour.percent from Tour inner join Status on Tour.status = Status.id  where price >= 2000 and price <= 8000 and hotel >= 3 LIMIT ?, 100;",
                sqlString.generateSql().trim()
        );

    }

  @Test
  void managerPattern() {
    SearchPattern manager = new ManagerPattern();
    Collection<SearchPattern> searchPatterns = new ArrayList<>();
    searchPatterns.add(manager);
    SQLString sqlString = new SQLString(searchPatterns);
    //System.out.println(sqlString.generateSql());
    assertEquals(
      "select from Tour  where Status.status in ('registered', 'paid', 'cancelled') LIMIT ?, 100;",
      sqlString.generateSql().trim()
    );

  }

  @Test
  void personPattern() {
    SearchPattern person = new PersonPattern(1);
    Collection<SearchPattern> searchPatterns = new ArrayList<>();
    searchPatterns.add(person);
    SQLString sqlString = new SQLString(searchPatterns);
    //System.out.println(sqlString.generateSql());
    assertEquals(
      "select from Tour  where person >= 1 LIMIT ?, 100;",
      sqlString.generateSql().trim()
    );

  }

  @Test
  void userPattern() {
    SearchPattern user = new UserPattern(1);
    Collection<SearchPattern> searchPatterns = new ArrayList<>();
    searchPatterns.add(user);
    SQLString sqlString = new SQLString(searchPatterns);
    //System.out.println(sqlString.generateSql());
    assertEquals(
      "select from Tour  where Tour.user = 1 LIMIT ?, 100;",
      sqlString.generateSql().trim()
    );

  }
}
