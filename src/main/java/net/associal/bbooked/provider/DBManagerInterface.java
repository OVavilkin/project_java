package net.associal.bbooked.provider;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * The only interface to connect to DB
 */
public interface DBManagerInterface {

  Connection getConnection() throws SQLException;
}
