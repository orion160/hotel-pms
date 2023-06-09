package edu.hotel.pms;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import java.sql.SQLException;

public class Main {
  public static void main(String[] args) {
    final var config = new HikariConfig();
    config.setJdbcUrl("jdbc:postgresql://postgres:5432/hotel");
    config.setUsername("admin");
    config.setPassword("password");

    try (final var dataSource = new HikariDataSource(config);
        final var c = dataSource.getConnection()) {
      final var meta = c.getMetaData();
      System.out.println(meta.getDatabaseProductName());
      System.out.println(meta.getDatabaseProductVersion());
      System.out.println(meta.getDriverName());
      System.out.println(meta.getDriverVersion());
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
