package net.associal.bbooked.provider;

import net.associal.bbooked.entity.Tour;
import net.associal.bbooked.entity.User;
import net.associal.bbooked.entity.searchPattern.SQLString;
import net.associal.bbooked.entity.searchPattern.SearchPattern;

import java.sql.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

public class ProviderImpl implements Provider {

  private static Connection con;
  private String url;
  private String user;
  private String password;
  private static final Logger logger = LogManager.getLogger(Provider.class);

  private DBManagerInterface dbManager;


  /**
   * Constructor used by default
   * creates a connection to DB Pool
   */
  public ProviderImpl() {

    dbManager = new DBManagerImplPool();
    BasicConfigurator.configure();
  }

  /**
   *
   * @param url
   * @param user
   * @param password
   *
   * Constructor used by tests
   */
  public ProviderImpl(String url, String user, String password) {
    this.dbManager = new DBManagerImplJDBC(url, user, password);
    BasicConfigurator.configure();
  }


  private synchronized Connection getConnection() throws SQLException {
    if(con == null) {
      con = dbManager.getConnection();
    }
    return con;
  }

  /**
   *
   * @param sql
   * @param args - parameters for sql above
   * @return ResultSet
   * A wrapper for DB calls, takes sql string and a list of parameters
   */
  private ResultSet runQuery(String sql, String... args) {

    try {
      con = dbManager.getConnection();
      PreparedStatement pstmt = getStatement(con, sql, args);
      logger.debug("SQL STATEMENT: " + pstmt.toString());
      ResultSet res = pstmt.executeQuery();
      return res;

    } catch (SQLException e) {
      e.printStackTrace();
      return null;
    }
  }

  /**
   *
   * @param con
   * @param sql
   * @param args
   * @return prepared statement
   * @throws SQLException
   *
   * A small hack not to call PrepatedStatement.setString() for each parameter
   * currently supports up to 5 arguments
   */
  private PreparedStatement getStatement(Connection con, String sql, String... args) throws SQLException {
    PreparedStatement pstmt = con.prepareStatement(sql);
    logger.debug("SQL IS [" + sql + "]");
    if (args == null || args.length == 0) {

    } else {
      logger.debug("ARGS LENGTH: " + args.length);

      switch (args.length) {
        case 6:
          pstmt.setString(6, args[5]);
        case 5:
          pstmt.setString(5, args[4]);
        case 4:
          pstmt.setString(4, args[3]);
        case 3:
          pstmt.setString(3, args[2]);
        case 2:
          pstmt.setString(2, args[1]);
        case 1:
          try {
            int number = Integer.valueOf(args[0]);
            pstmt.setInt(1, number);
          } catch (NumberFormatException e) {
            pstmt.setString(1, args[0]);
          }
          break;
      }
    }

    return pstmt;
  }

  private boolean runUpdate(String sql, String... args) {

    try {
      con = dbManager.getConnection();
      PreparedStatement pstmt = getStatement(con, sql, args);
      boolean result = pstmt.execute();
      pstmt.close();
      return result;
    } catch (SQLException e) {
      e.printStackTrace();
      return false;
    }
  }


  @Override
  public User getUser(int id) {
    String sql = "SELECT User.id, User.username, Role.role, User.email, User.blocked FROM User INNER JOIN Role on User.role=Role.id  WHERE User.id =?";
    ResultSet res = runQuery(sql, String.valueOf(id));
    String role = "";
    String username = "";
    String email = "";
    boolean blocked = false;

    try {
      if (res != null) {
        while (res.next()) {
          role = res.getString("role");
          email = res.getString("email");
          username = res.getString("username");
          blocked = res.getBoolean("blocked");
          res.close();
          break;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return new User(id, username, "HIDDEN", role, email, blocked);

  }

  @Override
  public User getUser(String username, String password, String sessionId) {
    String sql = "SELECT User.id, Role.role, User.email, User.blocked FROM User INNER JOIN Role on User.role=Role.id  WHERE username=? and password=?";
    ResultSet res = runQuery(sql, username, password);
    int id = 0;
    String role = "guest";
    String email = "";
    boolean blocked = false;
    try {
      if (res != null) {
        while (res.next()) {
          id = res.getInt("id");
          role = res.getString("role");
          email = res.getString("email");
          blocked = res.getBoolean("blocked");
          res.close();
          break;
        }
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    if (id > 0) {
      sql = "UPDATE User set session=? where id=?";
      runUpdate(sql, sessionId, String.valueOf(id));
    } else {
      return null;
    }

    return new User(id, username, "HIDDEN", role, email, blocked);
  }

  @Override
  public User getUser(String sessionId) {
    String sql = "SELECT User.id, User.username, Role.role, User.email, User.blocked FROM User INNER JOIN Role on User.role=Role.id  WHERE session=?";
    ResultSet res = runQuery(sql, sessionId);
    int id = 0;
    String username = "";
    String role = "";
    String email = "";
    boolean blocked = false;
    try {
      while (res.next()) {
        id = res.getInt("id");
        username = res.getString("username");
        role = res.getString("role");
        email = res.getString("email");
        blocked = res.getBoolean("blocked");
        res.close();
        break;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    if (id == 0) {
      return null;
    }

    return new User(id, username, "HIDDEN", role, email, blocked);
  }

  @Override
  public void logout(String sessionId) {
    String sql = "UPDATE User SET session='' WHERE session=?";
    runUpdate(sql, sessionId);
  }

  @Override
  public void setSession(int id, String session) {
    String sql = "UPDATE User SET session=? where id=?;";
    runUpdate(sql, session, String.valueOf(id));
  }

  public void close() {
    if(con != null) {
      try {
        con.close();
      } catch (SQLException e) {
        e.printStackTrace();
      }
    }
    con = null;
  }

  @Override
  public void editTour(Tour tour) {
    try {
      con = dbManager.getConnection();
      con.setAutoCommit(false);
      String sql = "update Tour set name=?, description=?, person=?, hotel=?, hot=? where id=?;";
      PreparedStatement pstmt = getStatement(
        con,
        sql,
        tour.getName(),
        tour.getDescription(),
        String.valueOf(tour.getPerson()),
        String.valueOf(tour.getHotel()),
        tour.isHot() ? "1" : "0",
        String.valueOf(tour.getId()) );
      logger.debug("SQL STRING: [" + pstmt.toString() + "]");
      pstmt.execute();
      sql = "delete from TourTag where tour=?;";
      pstmt = getStatement(con, sql, String.valueOf(tour.getId()));
      pstmt.execute();

      if(tour.getTags() != null) {
        for (String tag : tour.getTags()) {
          sql = "select id from Tag where tag=?;";
          pstmt = getStatement(con, sql, tag);
          ResultSet rs = pstmt.executeQuery();
          int id = 0;
          while (rs.next()) {
            id = rs.getInt("id");
            break;
          }

          sql = "insert into TourTag(tour, tag) values(?, ?);";
          pstmt = getStatement(con, sql,
            String.valueOf(tour.getId()),
            String.valueOf(id));
          pstmt.execute();
        }
      }

      con.commit();

    } catch (SQLException e) {
      try {
        con.rollback();
      } catch (SQLException ex) {
        ex.printStackTrace();
      }
      e.printStackTrace();
    }

  }

  @Override
  public void orderTour(int userId, int tourId) {
    String sql="update Tour set user=?, status=(select id from Status where status='registered') where id=?;";
    runUpdate(sql, String.valueOf(userId), String.valueOf(tourId));
  }

  @Override
  public void setHot(String tourId) {
    Tour tour = getTour(Integer.valueOf(tourId));
    String sql;
    if(tour.isHot()) {
      sql = "update Tour set hot=0 where id=?;";
    } else {
      sql = "update Tour set hot=1 where id=?;";
    }
    runUpdate(sql, String.valueOf(tourId));
  }

  @Override
  public void tourStatusUpdate(String id, String status) {
    String sql = "update Tour set status=(select id from Status where status='" +
      status + "') where id=?;";
    runUpdate(sql, id);
  }

  @Override
  public void tourPercentUpdate(String id, String percent) {
    String sql = "update Tour set percent=" + percent + " where id=?";
    runUpdate(sql, id);
  }

  @Override
  public User addUser(String username, String password, String email, String sessionid) {
    String sql = "insert into User (username, password, email, role, session) values (?, ?, ?, 2, ?);";
    runUpdate(sql, username, password, email, sessionid);
    return getUser(sessionid);
  }

  @Override
  public boolean existingUser(String username) {
    String sql = "select id from User where username=?;";
    ResultSet res = runQuery(sql, username);
    int id = 0;

    try {
      while (res.next()) {
        id = res.getInt("id");
        res.close();
        break;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    if(id != 0) {
      return true;
    }
    return false;
  }

  @Override
  public int addTour(Tour tour) {
    String sql = "insert into Tour(name, description, user, status, hotel, person, price) " +
      "values(?, ?, 1, 1, ?, ?, ?);";
    runUpdate(sql,
      tour.getName(),
      tour.getDescription(),
      String.valueOf(tour.getHotel()),
      String.valueOf(tour.getPerson()),
      String.valueOf(tour.getPrice()));

    sql = "SELECT LAST_INSERT_ID();";
    ResultSet res = runQuery(sql);

    int id = 0;

    try {
      while (res.next()) {
        id = res.getInt("LAST_INSERT_ID()");
        res.close();
        break;
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return id;
  }

  @Override
  public List<User> getUsers(int limit) {
    List<User> users = new ArrayList<>();

    String sql = "select id from User where id > 1 LIMIT ?, 100";

    ResultSet res = runQuery(sql, String.valueOf(limit));

    try {
      while (res.next()) {
        int id = res.getInt("id");
        users.add(getUser(id));

      }
    } catch (SQLException e) {
      e.printStackTrace();
    }

    return users;
  }

  @Override
  public void blockUser(int id) {
    User user = getUser(id);
    String sql = "update User set blocked=";
    if(user.isBlocked()) {
      sql += "0";
    } else {
      sql += "1";
    }
    sql += " where id=?;";
    runUpdate(sql, String.valueOf(id));
  }

  private Tour getTour(ResultSet res) throws SQLException {
    int id = res.getInt("id");
    String name = res.getString("name");
    String description = res.getString("description");

    boolean hot = res.getBoolean("hot");
    int hotel = res.getInt("hotel");
    int person = res.getInt("person");
    String status = res.getString("status");
    int userId = res.getInt("user");
    double price = res.getDouble("price");
    double percent = res.getDouble("percent");

    String sql = "select * from User where id=?";
    res = runQuery(sql, String.valueOf(userId));
    res.next();
    String username = res.getString("username");
    res.close();
    // TODO: get additional data from user when we have it (email address etc.?)
    User user = new User(userId, username);

    sql = "select Tag.tag from Tag inner join TourTag on Tag.id = TourTag.tag where TourTag.tour = ?";
    //res.next();
    res = runQuery(sql, String.valueOf(id));
    Collection<String> cTags = new ArrayList();
    while (res.next()) {
      cTags.add(res.getString("tag"));
    }
    res.close();

    String[] tags = new String[0];
    Tour tour = new Tour(id, name, description, user, (String[]) cTags.toArray(tags), status, hot, hotel, person, price, percent);

    return tour;
  }

  @Override
  public Tour getTour(int id) {
    String sql = "SELECT t.id, t.name, t.description, t.hot, t.hotel, t.person, s.status, t.user, t.price, t.percent FROM Tour AS t INNER JOIN Status AS s on t.status=s.id WHERE t.id=?";
    ResultSet res = runQuery(sql, String.valueOf(id));
    Tour tour = null;
    try {
      res.next();
      tour = getTour(res);
      res.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return tour;
  }

  @Override
  public Tour[] getAllTours(int start) {
    String sql = "SELECT t.id, t.name, t.description, t.hot, t.hotel, t.person, s.status, t.user, t.price, t.percent FROM Tour AS t INNER JOIN Status AS s on t.status=s.id LIMIT ?, 100";

    ResultSet res = runQuery(sql, String.valueOf(start));

    Collection<Tour> tours = new ArrayList();
    try {
      while (res.next()) {
        tours.add(getTour(res));
      }
      res.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    Tour[] aTours = new Tour[0];

    return (Tour[]) tours.toArray(aTours);
  }

  @Override
  public Tour[] getAllTours(int start, Collection<SearchPattern> spatterns) {

    SQLString sqlString = new SQLString(spatterns);
    String sql = sqlString.generateSql();
    logger.debug("SQL STRING: " + sql);
    ResultSet res = runQuery(sql, String.valueOf(start));

    Collection<Tour> tours = new ArrayList();

    try {
      if (res.isBeforeFirst()) {
        while (res.next()) {
          tours.add(getTour(res));
        }
      }
      res.close();
    } catch (SQLException e) {
      e.printStackTrace();
    }

    Collection<Tour> hotted = new ArrayList<>();
    Collection<Tour> notHot = new ArrayList<>();

    for(Tour tour: tours) {
      if(tour.isHot()) {
        notHot.add(tour);
      } else {
        hotted.add(tour);
      }
    }

    notHot.addAll(hotted);

    tours = notHot;

    Tour[] aTours = new Tour[0];

    return (Tour[]) tours.toArray(aTours);

  }


}
