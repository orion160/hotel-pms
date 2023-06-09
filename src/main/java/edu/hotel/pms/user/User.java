package edu.hotel.pms.user;

import dagger.Component;

@Component(modules = UserModule.class)
public interface User {
  UserDAO userDAO();
}
