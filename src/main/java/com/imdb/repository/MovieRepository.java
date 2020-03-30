package com.imdb.repository;

import com.imdb.entity.MovieEntity;
import com.imdb.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovieRepository extends JpaRepository<MovieEntity, String> {

        List<MovieEntity> findAllByUser(UserEntity user);}
