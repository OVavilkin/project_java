package net.associal.bbooked.provider;

import org.apache.log4j.BasicConfigurator;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * DBManagerInterface implementation (Pool)
 * called by servlets default (no-args) constructor
 */
public class DBManagerImplPool implements DBManagerInterface {

  private DBManager dbManager;

  public DBManagerImplPool() {
    dbManager = DBManager.getInstance();
  }

  @Override
  public Connection getConnection() throws SQLException {
    return dbManager.getConnection();
  }
}
