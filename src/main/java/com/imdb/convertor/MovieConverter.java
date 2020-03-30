package com.imdb.convertor;

import com.imdb.entity.MovieEntity;
import com.imdb.model.MovieModel;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class MovieConverter {
    private final UserConverter userConverter;

    public MovieConverter(final UserConverter userConverter) {
        this.userConverter = userConverter;
            }

    public MovieModel convertToModel(final MovieEntity movieEntity) {
        if (movieEntity == null) {
            return null;
        }

        final MovieModel movieModel = new MovieModel();
        movieModel.setId(movieEntity.getId());
        movieModel.setName(movieEntity.getName());
        movieModel.setYear(movieEntity.getYear());
        movieModel.setGenre(movieEntity.getGenre());
        movieModel.setUser(userConverter.convertToModel(movieEntity.getUser()));
        return movieModel;
    }
    public MovieEntity convertToEntity(final MovieModel movieModel) {
        if (movieModel == null) {
            return null;
        }

        final MovieEntity movieEntity = new MovieEntity();
        movieEntity.setId(movieModel.getId());
        movieEntity.setName(movieModel.getName());
        movieEntity.setYear(movieModel.getYear());
        movieEntity.setGenre(movieModel.getGenre());
        movieEntity.setUser(userConverter.convertToEntity(movieModel.getUser()));

        return movieEntity;
    }

    public List<MovieModel> convertToModels(final List<MovieEntity> movieEntities) {
        if (movieEntities == null || movieEntities.isEmpty()) {
            return new ArrayList<>();
        }

        return movieEntities.stream().map(this::convertToModel).collect(toList());
    }


}
