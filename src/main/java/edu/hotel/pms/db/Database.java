package edu.hotel.pms.db;

import dagger.Component;
import javax.inject.Singleton;
import javax.sql.DataSource;

@Singleton
@Component(modules = DatabaseModule.class)
public interface Database {
  DataSource datasource();
}
