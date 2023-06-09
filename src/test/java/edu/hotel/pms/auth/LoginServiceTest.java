package edu.hotel.pms.auth;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

import edu.hotel.pms.user.UserDAO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class LoginServiceTest {
  @Mock UserDAO userDAO;

  @Mock PasswordEncoder passwordEncoder;

  @Mock Logger logger;

  @InjectMocks LoginService loginService;

  @Test
  void loginShouldReturnTrueIfValidCredentialsProvided() {
    final var username = "testuser";
    final var password = "password";
    final var encodedPassword = "encodedPassword";

    when(userDAO.getPassword(username)).thenReturn(encodedPassword);
    when(passwordEncoder.matches(password, encodedPassword)).thenReturn(true);

    final var result = loginService.login(username, password);

    assertTrue(result, "Login should succeed with valid credentials");

    verify(userDAO).getPassword(username);
    verify(passwordEncoder).matches(password, encodedPassword);

    verify(logger, times(2)).debug("===");
    verify(logger).debug("Login process initiated. Username: {}", username);
    verify(logger).info("Login successful. Username: {}", username);
    verify(logger).debug("Login process completed. Username: {}", username);
  }

  @Test
  void loginShouldReturnFalseIfInvalidCredentialsProvided() {
    final var username = "testuser";
    final var password = "password";
    final var encodedPassword = "encodedPassword";

    when(userDAO.getPassword(username)).thenReturn(encodedPassword);
    when(passwordEncoder.matches(password, encodedPassword)).thenReturn(false);

    final var result = loginService.login(username, password);

    assertFalse(result, "Login should succeed with valid credentials");

    verify(userDAO).getPassword(username);
    verify(passwordEncoder).matches(password, encodedPassword);

    verify(logger, times(2)).debug("===");
    verify(logger).debug("Login process initiated. Username: {}", username);
    verify(logger).warn("Login failed. Invalid credentials. Username: {}", username);
    verify(logger).debug("Login process completed. Username: {}", username);
  }

  @Test
  void loginShouldThrowExceptionWhenUsernameIsNull() {
    final var password = "password";

    Assertions.assertThrows(
        IllegalArgumentException.class, () -> loginService.login(null, password));
  }

  @Test
  void loginShouldThrowExceptionWhenPasswordIsNull() {
    String username = "testuser";

    Assertions.assertThrows(
        IllegalArgumentException.class, () -> loginService.login(username, null));
  }

  @Test
  void loginShouldThrowExceptionWhenBothUsernameAndPasswordAreNull() {
    Assertions.assertThrows(IllegalArgumentException.class, () -> loginService.login(null, null));
  }

  @Test
  void loginShouldThrowExceptionWhenUsernameIsBlank() {
    String username = "   ";
    String password = "testpassword";

    Assertions.assertThrows(
        IllegalArgumentException.class, () -> loginService.login(username, password));
  }

  @Test
  void loginShouldThrowExceptionWhenPasswordIsBlank() {
    String username = "testuser";
    String password = "   ";

    Assertions.assertThrows(
        IllegalArgumentException.class, () -> loginService.login(username, password));
  }

  @Test
  void loginShouldThrowExceptionWhenBothUsernameAndPasswordAreBlank() {
    String username = "   ";
    String password = "   ";

    Assertions.assertThrows(
        IllegalArgumentException.class, () -> loginService.login(username, password));
  }

  @Test
  void loginShouldThrowExceptionWhenPasswordExceedsMaxLength() {
    String username = "testuser";
    String password = "x".repeat(129);

    Assertions.assertThrows(
        IllegalArgumentException.class, () -> loginService.login(username, password));
  }
}
