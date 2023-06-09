package edu.hotel.pms.user;

import dagger.Binds;
import dagger.Module;

@Module
public interface UserModule {
  @Binds
  UserDAO bindUserDAO(JdbcUserDAO dao);
}
