package com.imdb.service.impl;

import com.imdb.convertor.MovieConverter;
import com.imdb.entity.MovieEntity;
import com.imdb.exceptions.HttpBadRequestException;
import com.imdb.model.MovieModel;
import com.imdb.repository.MovieRepository;
import com.imdb.service.MovieService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final MovieConverter movieConverter;

    public MovieServiceImpl(final MovieRepository movieRepository,
                          final MovieConverter movieConverter) {
        this.movieRepository = movieRepository;
        this.movieConverter = movieConverter;
    }
    @Transactional
    @Override
    public MovieModel addMovie(final MovieModel movieModel) {
        log.info("Add movie BEGIN: {}", movieModel);

        final MovieEntity movieEntity = movieConverter.convertToEntity(movieModel);
//нещо не е както трябва!!!!!!!!
        final MovieEntity movie = movieRepository.save(movieEntity);

        final MovieModel added = movieConverter.convertToModel(movie);

        log.info("Add movie END: {}", added);

        return added;
    }

    @Override
    public void deleteMovie(final String id) {
        log.info("Delete movie by id BEGIN: {}", id);

        movieRepository.deleteById(id);

        log.info("Delete movie by id END: {}", id);
    }
    @Transactional
    @Override
    public MovieModel updateMovie(final MovieModel movieModel) {
        log.info("Update movie BEGIN: {}", movieModel);

        if (!movieRepository.existsById(movieModel.getId())) {
            throw new HttpBadRequestException("Movie entity does not exist for id: " + movieModel.getId());
        }

        final MovieEntity movieEntity = movieConverter.convertToEntity(movieModel);

        final MovieModel updated = movieConverter.convertToModel(movieRepository.save(movieEntity));

        log.info("Update movie END: {}", updated);

        return updated;
    }

    @Override
    public MovieModel getMovieById(String id) {
        log.info("Get movie by id BEGIN: {}", id);

        final Optional<MovieEntity> movieOpt = movieRepository.findById(id);

        MovieModel movieModel = null;
        if (movieOpt.isPresent()) {
            movieModel = movieConverter.convertToModel(movieOpt.get());
        }

        log.info("Get movie by id END: {} {}", id, movieModel);

        return movieModel;
    }

    @Override
    public List<MovieModel> getAllMovies() {
        log.info("Get all cars BEGIN: ");

        final List<MovieEntity> all = movieRepository.findAll();

        final List<MovieModel> movies = movieConverter.convertToModels(all);

        log.info("Get all cars END: {}", movies);

        return movies;
    }
}
