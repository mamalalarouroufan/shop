package com.shop.service.User;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.shop.api.entity.user.UserEntity;
import com.shop.api.entity.user.service.UserEntityService;
import com.shop.service.User.mapper.UserEntityMapper;
import com.shop.service.key.FactoryAboutKey;

@Service("userEntityService")
public class UserEntityServiceImpl implements UserEntityService {
	private static final Logger LOGGER = LoggerFactory.getLogger(UserEntityServiceImpl.class);

	@Resource
	private UserEntityMapper userEntityMapper;

	@Override
	public UserEntity getUserEntityByPhone(String phone) {
		return userEntityMapper.getUserEntityByPhone(phone);
	}

	@Override
	public UserEntity getUserEntityById(String Id) {
		return userEntityMapper.getUserEntityById(Id);
	}

	@Override
	public UserEntity saveUserEntity(UserEntity userEntity) {
		userEntity.setId(FactoryAboutKey.getKeyByTblNameTypeLong("S_USER_T"));
		userEntity.setCreateTime(new Date());
		userEntity.setUpdateTime(new Date());
		userEntity.setIsDel(0);
		userEntity.setVersionNum(0L);
		LOGGER.debug("UserEntityServiceImpl.saveUserEntity 保存用户信息传入参数:{}", JSONObject.toJSONString(userEntity));
		userEntityMapper.saveUserEntity(userEntity);
		return userEntity;
	}

}
