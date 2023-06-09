package edu.hotel.pms.db;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import dagger.Module;
import dagger.Provides;
import javax.inject.Singleton;
import javax.sql.DataSource;

@Module
public interface DatabaseModule {
  @Singleton
  @Provides
  static HikariConfig provideConfig() {
    // TODO: Instead of environment variables use properties file
    // Or any other format, and encrypt it

    final var config = new HikariConfig();
    final var jdbc = System.getenv("DB_JDBC");
    final var host = System.getenv("DB_HOSTNAME");
    final var port = System.getenv("DB_PORT");
    final var dbName = System.getenv("DB_NAME");

    config.setJdbcUrl(String.format("%s://%s:%s/%s", jdbc, host, port, dbName));

    final var user = System.getenv("DB_USER");
    config.setUsername(user);

    final var password = System.getenv("DB_PASSWORD");
    config.setPassword(password);

    return config;
  }

  @Provides
  @Singleton
  static DataSource provideDatasource(HikariConfig config) {
    return new HikariDataSource(config);
  }
}
