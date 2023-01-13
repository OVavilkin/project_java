package net.associal.bbooked.provider;

import net.associal.bbooked.entity.Tour;
import net.associal.bbooked.entity.User;
import net.associal.bbooked.entity.searchPattern.SearchPattern;

import java.util.Collection;
import java.util.List;

/**
 * Declaration of all DB-related activities
 */
public interface Provider extends AutoCloseable {

  /**
   *
   * @param id
   * @return user
   *
   * Pick user based on ID
   */
  User getUser(int id);

  /**
   *
   * @param username
   * @param password
   * @param sessionId
   * @return user
   *
   * Pick user using username and password
   * if found - store a new session in DB for future call by getUser(String sessionId)
   */
  User getUser(String username, String password, String sessionId);

  /**
   *
   * @param sessionId
   * @return user
   *
   * Pick user using sessionId stored in DB
   * null otherwise
   */
  User getUser(String sessionId);

  /**
   *
   * @param sessionId
   * Unsets session stored in DB
   */
  void logout(String sessionId);

  void setSession(int id, String session);

  /**
   *
   * @param id
   * @return Tour
   * Picks tour based on ID from DB
   */
  Tour getTour(int id);

  /**
   *
   * @param start
   * @return Tour[]
   * Get first 50 tours from DB starting from "start"
   */
  Tour[] getAllTours(int start);

  /**
   *
   * @param i
   * @param spatterns
   * @return Tour[]
   * Get first 50 tours from DB staring from "start" based on search pattern
   */
  Tour[] getAllTours(int i, Collection<SearchPattern> spatterns);

  /**
   * Close the connection to DB
   */
  void close();

  /**
   *
   * @param tour
   * Save a new version of the tour in DB
   */
  void editTour(Tour tour);

  /**
   *
   * @param userId
   * @param tourId
   *
   * Change tour status to "orcered" and set the user who made the order
   */
  void orderTour(int userId, int tourId);

  /**
   *
   * @param tourId
   * (Un)set tour as hot
   */
  void setHot(String tourId);

  /**
   *
   * @param id
   * @param status
   *
   * Update tour status
   */
  void tourStatusUpdate(String id, String status);

  /**
   *
   * @param id
   * @param percent
   *
   * Update tour discount percent
   */
  void tourPercentUpdate(String id, String percent);

  /**
   *
   * @param username
   * @param password
   * @param email
   * @param id
   * @return User
   * Add a new user and return it
   */
  User addUser(String username, String password, String email, String id);

  /**
   *
   * @param username
   * @return boolean
   * Check if user already exists based on username
   */
  boolean existingUser(String username);

  /**
   *
   * @param tour
   * @return id
   * Add a new tour
   */
  int addTour(Tour tour);

  /**
   *
   * @param i
   * @return users
   * Get first 50 users staring with "i"
   */
  List<User> getUsers(int i);

  /**
   *
   * @param id
   *
   * (Un)block user
   */
  void blockUser(int id);
}
