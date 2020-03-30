package com.imdb.service;

import com.imdb.controller.LoginResponse;
import com.imdb.model.UserModel;

public interface UserService {
    UserModel registerUser(UserModel model);

    LoginResponse loginUser(String username, String password);

}
