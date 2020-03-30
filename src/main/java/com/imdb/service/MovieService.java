package com.imdb.service;

import com.imdb.model.MovieModel;

import java.util.List;

public interface MovieService {
    MovieModel addMovie(MovieModel model);

    void deleteMovie(String id);

    MovieModel updateMovie(MovieModel model);

    MovieModel getMovieById(String id);

    List<MovieModel> getAllMovies();
}
