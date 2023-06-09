package edu.hotel.pms.auth;

import edu.hotel.pms.user.UserDAO;
import org.slf4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;

public class LoginService {
  private static final int MAX_PASSWORD_LENGTH = 128;
  private final UserDAO userDAO;

  private final PasswordEncoder passwordEncoder;

  private final Logger logger;

  public LoginService(
      final UserDAO userDAO, final PasswordEncoder passwordEncoder, final Logger logger) {
    this.userDAO = userDAO;
    this.passwordEncoder = passwordEncoder;
    this.logger = logger;
  }

  public boolean login(String username, String password) {
    logger.debug("===");
    logger.debug("Login process initiated. Username: {}", username);

    validateInputs(username, password);

    if (password.length() > MAX_PASSWORD_LENGTH) {
      logger.warn("Password exceeds maximum length. Username: {}", username);
      throw new IllegalArgumentException("Password exceeds maximum length");
    }

    logger.debug("Validation successful. Username: {}", username);

    final var encodedPassword = userDAO.getPassword(username);
    final var result = passwordEncoder.matches(password, encodedPassword);

    if (result) {
      logger.info("Login successful. Username: {}", username);
    } else {
      logger.warn("Login failed. Invalid credentials. Username: {}", username);
    }

    logger.debug("Login process completed. Username: {}", username);
    logger.debug("===");
    return result;
  }

  private void validateInputs(String username, String password) {
    if (username == null && password == null) {
      logger.warn("Username and password are null");
      throw new IllegalArgumentException("Username and password cannot be null");
    } else if (username == null) {
      logger.warn("Username is null");
      throw new IllegalArgumentException("Username cannot be null");
    } else if (password == null) {
      logger.warn("Password is null. Username: {}", username);
      throw new IllegalArgumentException("Password cannot be null");
    }

    if (username.isBlank() || password.isBlank()) {
      logger.warn("Username or password is blank. Username: {}", username);
      throw new IllegalArgumentException("Username or password cannot be blank");
    }
  }
}
