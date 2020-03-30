package com.imdb.convertor;

import com.imdb.entity.UserEntity;
import org.springframework.stereotype.Component;
import com.imdb.model.UserModel;

@Component
public class UserConverter {

    public UserModel convertToModel(final UserEntity userEntity) {
        if (userEntity == null) {
            return null;
        }

        final UserModel userModel = new UserModel();
        userModel.setId(userEntity.getId());
        userModel.setUsername(userEntity.getUsername());
        userModel.setFirstName(userEntity.getFirstName());
        userModel.setLastName(userEntity.getLastName());

        return userModel;
    }

    public UserEntity convertToEntity(final UserModel userModel){
        if(userModel == null){
            return null;
        }

        final UserEntity userEntity = new UserEntity();
        userEntity.setId(userModel.getId());
        userEntity.setUsername(userModel.getUsername());
        userEntity.setPassword(userModel.getPassword());
        userEntity.setFirstName(userModel.getFirstName());
        userEntity.setLastName(userModel.getLastName());

        return userEntity;
    }
}
