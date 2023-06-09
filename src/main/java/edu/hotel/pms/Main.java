package edu.hotel.pms;

import edu.hotel.pms.db.DaggerDatabase;
import java.sql.SQLException;

public class Main {
  public static void main(String[] args) {
    final var db = DaggerDatabase.create();
    final var ds = db.datasource();

    try (final var c = ds.getConnection()) {
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
