package com.shop.service.User.mapper;

import com.shop.api.entity.user.UserEntity;

public interface UserEntityMapper {

	UserEntity getUserEntityByPhone(String phone);

	UserEntity getUserEntityById(String id);

	Integer saveUserEntity(UserEntity userEntity);

}
