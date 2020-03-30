package com.imdb.controller;

import com.imdb.model.MovieModel;
import com.imdb.service.MovieService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movies")
public class MovieController {
    private final MovieService movieService;
    private final MovieAccessValidator movieAccessValidator;

    public MovieController(final MovieService movieService,
                         final MovieAccessValidator movieAccessValidator) {
        this.movieService = movieService;
        this.movieAccessValidator = movieAccessValidator;
    }

    @PostMapping
    public MovieModel addMovie(@RequestBody final MovieModel movieModel) {
        return movieService.addMovie(movieModel);
    }

    @GetMapping("/all")
    public List<MovieModel> getAllMovies() {
        return movieService.getAllMovies();
    }

    @PutMapping
    public MovieModel updateMovie(@RequestBody final MovieModel movieModel) {
        movieAccessValidator.validateUserCanEditMovie(movieModel.getUser().getId(), movieModel.getId());

        return movieService.updateMovie(movieModel);
    }

    @DeleteMapping("/{id}/{userId}")
    public void deleteMovie(@PathVariable final String id, @PathVariable final String userId) {
        movieAccessValidator.validateUserCanEditMovie(userId, id);

        movieService.deleteMovie(id);
    }
}
