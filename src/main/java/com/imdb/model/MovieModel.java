package com.imdb.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MovieModel {

    private String id;

    private String name;

    private int year;

    private String genre;

    private UserModel user;

}