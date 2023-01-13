package net.associal.bbooked.provider;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.sql.*;

public class DBManager {

	private static DBManager instance;
  private final DataSource ds;

  /**
   * Singleton, returns only instance existing
   * or creates new if none exist at the moment
   * @return
   */
	public static synchronized DBManager getInstance() {
		if (instance == null) {
			instance = new DBManager();
		}
		return instance;
	}

  /**
   * Called by getInstance()
   * private
   */
	private DBManager() {
		try {
			Context initContext = new InitialContext();
			Context envContext  = (Context)initContext.lookup("java:/comp/env");
			ds = (DataSource)envContext.lookup("jdbc/bbooked");
		} catch (NamingException ex) {
      //ex.printStackTrace();
			throw new IllegalStateException("Cannot obtain a data source", ex);
		}
	}

	public Connection getConnection() throws SQLException {
    return ds.getConnection();
  }

}
