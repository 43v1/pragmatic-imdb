package com.imdb.controller;

import com.imdb.exceptions.HttpForbiddenException;
import com.imdb.model.MovieModel;
import com.imdb.service.MovieService;
import org.springframework.stereotype.Component;

@Component
public class MovieAccessValidator {
    private final MovieService movieService;

    public MovieAccessValidator(final MovieService movieService) {
        this.movieService = movieService;
    }

    public void validateUserCanEditMovie(final String userId, final String movieId) {
        if (userId == null || movieId == null) {
            throw new HttpForbiddenException();
        }

        final MovieModel movieModel = movieService.getMovieById(movieId);

        if (!userId.equals(movieModel.getUser().getId())) {
            final String message = String
                    .format("Movie with id: %s does not belong to user with id: %s", movieId, userId);
            throw new HttpForbiddenException(message);
        }
    }

}
