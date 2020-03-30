package com.imdb.service.impl;

import com.auth0.jwt.JWT;
import com.imdb.controller.LoginResponse;
import com.imdb.convertor.UserConverter;
import com.imdb.entity.UserEntity;
import com.imdb.exceptions.HttpUnauthorizedException;
import com.imdb.model.UserModel;
import com.imdb.repository.UserRepository;
import com.imdb.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;
import static com.imdb.config.security.PasswordEncoder.checkPassword;
import static com.imdb.config.security.PasswordEncoder.hashPassword;
import static com.imdb.config.security.SecurityConstants.EXPIRATION_TIME;
import static com.imdb.config.security.SecurityConstants.SECRET;

@Service
@Log4j2
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserConverter userConverter;

    public UserServiceImpl(final UserRepository userRepository,
                           final UserConverter userConverter) {
        this.userRepository = userRepository;
        this.userConverter = userConverter;
    }
    @Override
    public UserModel registerUser(UserModel userModel) {
        log.info("Register user BEGIN: {}", userModel);

        userModel.setPassword(hashPassword(userModel.getPassword()));

        final UserEntity userEntity = userConverter.convertToEntity(userModel);

        final UserEntity saved = userRepository.save(userEntity);

        log.info("Register user END: {}", saved);

        return userConverter.convertToModel(saved);
    }

    @Override
    public LoginResponse loginUser(String username, String password) {
        log.info("Login user BEGIN: {}", username);

        final UserEntity userEntity = getUser(username);

        if (!checkPassword(password, userEntity.getPassword())) {
            throw new HttpUnauthorizedException();
        }

        final String jwtToken = JWT.create()
                .withSubject(username)
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .sign(HMAC512(SECRET.getBytes()));

        final UserModel userModel = userConverter.convertToModel(userEntity);

        final LoginResponse response = new LoginResponse(userModel, jwtToken);

        log.info("Login user END: {}", response);

        return response;
    }

    private UserEntity getUser(final String username) {
        final Optional<UserEntity> userOpt = userRepository
                .findByUsername(username);

        return userOpt.orElse(null);
    }

}
