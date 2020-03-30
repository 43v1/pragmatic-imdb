package com.imdb.users.service;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;


import com.imdb.controller.LoginResponse;
import com.imdb.exceptions.HttpUnauthorizedException;
import com.imdb.model.UserModel;
import com.imdb.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TestUserService {

    @Autowired
    private UserService userService;

    @Test
    public void testRegisterUser() {
        final UserModel userModel = new UserModel(null, "anton3", "1234", "Anton", "Cholakov");

        final UserModel created = userService.registerUser(userModel);

        assertEquals(userModel.getUsername(), created.getUsername());
        assertEquals(userModel.getFirstName(), created.getFirstName());
        assertEquals(userModel.getLastName(), created.getLastName());
    }

    @Test
    public void testLoginUser() {
        final UserModel userModel = new UserModel(null, "anton4", "1234", "Anton", "Cholakov");

        final UserModel created = userService.registerUser(userModel);

        assertThrows(
                HttpUnauthorizedException.class,
                () -> userService.loginUser(created.getUsername(), "root"));

        final LoginResponse petkoLogin = userService.loginUser(created.getUsername(), "password");
        assertNotNull(petkoLogin.getUser());
        assertNotNull(petkoLogin.getJwtToken());
    }
}
