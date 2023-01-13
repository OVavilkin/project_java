package net.associal.bbooked.provider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DBManagerInterface implementation (JDBC)
 * used by tests
 */
public class DBManagerImplJDBC implements DBManagerInterface {

  private final String url;
  private final String user;
  private final String password;

  public DBManagerImplJDBC(String url, String user, String password) {
    try {
      Class.forName("com.mysql.cj.jdbc.Driver");
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }
    this.url = url;
    this.user = user;
    this.password = password;
  }

  @Override
  public Connection getConnection() throws SQLException {
    return DriverManager.getConnection(this.url, this.user, this.password);
  }
}
