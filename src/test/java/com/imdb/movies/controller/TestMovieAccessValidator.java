package com.imdb.movies.controller;

import com.imdb.controller.MovieAccessValidator;
import com.imdb.exceptions.HttpForbiddenException;
import com.imdb.model.MovieModel;
import com.imdb.model.UserModel;
import com.imdb.service.MovieService;
import com.imdb.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.jupiter.api.Assertions.assertThrows;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TestMovieAccessValidator {
    @Autowired
    private UserService userService;

    @Autowired
    private MovieAccessValidator movieAccessValidator;

    @Autowired
    private MovieService movieService;

    @Test
    public void testCarAccessValidator() {
        final MovieModel movieModel = addMovie();

        final MovieModel created = movieService.addMovie(movieModel);

        movieAccessValidator.validateUserCanEditMovie(created.getUser().getId(), created.getId());

        assertThrows(
                HttpForbiddenException.class,
                () -> movieAccessValidator.validateUserCanEditMovie("bad id", created.getId()));
    }

    private MovieModel addMovie() {
        final UserModel userModel = new UserModel(null, "anton", "1234", "Anton", "Cholakov");
        final UserModel createdUser = userService.registerUser(userModel);

        final MovieModel movie = new MovieModel();
        movie.setName("Fast and Furious");
        movie.setGenre("action");
        movie.setYear(2001);
        movie.setUser(createdUser);
        return movie;
    }
}
