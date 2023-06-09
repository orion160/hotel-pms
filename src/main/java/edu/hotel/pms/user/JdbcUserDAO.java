package edu.hotel.pms.user;

import javax.inject.Inject;

public class JdbcUserDAO implements UserDAO {
  @Inject
  public JdbcUserDAO() {}

  @Override
  public String getPassword(String username) {
    return null;
  }
}
