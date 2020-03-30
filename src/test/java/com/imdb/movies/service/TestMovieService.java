package com.imdb.movies.service;

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

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles("test")
public class TestMovieService {
    @Autowired
    private MovieService movieService;

    @Autowired
    private UserService userService;

    @Test
    public void testCRUDMovie() {
        final MovieModel movie = addMovie();

        final MovieModel created = movieService.addMovie(movie);

        assertEquals(movie.getName(), created.getName());
        assertEquals(movie.getGenre(), created.getGenre());
        assertEquals(movie.getYear(), created.getYear());
        assertEquals(movie.getUser().getId(), created.getUser().getId());


        final MovieModel byId = movieService.getMovieById(created.getId());
        assertNotNull(byId);

        byId.setGenre("comedy");
        final MovieModel updated = movieService.updateMovie(byId);
        assertEquals(byId.getGenre(), updated.getGenre());

        final List<MovieModel> allMovies = movieService.getAllMovies();
        assertTrue(allMovies.size() > 0);

        movieService.deleteMovie(updated.getId());

        final MovieModel deleted = movieService.getMovieById(updated.getId());
        assertNull(deleted);
    }

    private MovieModel addMovie() {
        final UserModel userModel = new UserModel(null, "anton2", "1234", "Anton", "Cholakov");
        final UserModel createdUser = userService.registerUser(userModel);

        final MovieModel movie = new MovieModel();
        movie.setName("Fast and Furious");
        movie.setGenre("action");
        movie.setYear(2001);
        movie.setUser(createdUser);
        return movie;
    }
}
