package com.shop.service.wechat.WechatConfig;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.shop.api.entity.wechat.config.WechatConfigEntity;
import com.shop.api.entity.wechat.config.service.WechatConfigService;
import com.shop.api.utils.constant.ConstantKey;
import com.shop.api.utils.redis.RedisUtil;
import com.shop.service.key.FactoryAboutKey;
import com.shop.service.wechat.WechatConfig.mapper.WechatConfigMapper;

@Service("wechatConfigService")
public class WechatConfigServiceImpl implements WechatConfigService {

	private static final Logger LOGGER = LoggerFactory.getLogger(WechatConfigServiceImpl.class);
	@Resource
	private WechatConfigMapper wechatConfigMapper;
	@Resource
	private RedisUtil redisUtil;

	@Override
	public WechatConfigEntity getWechatConfigEntityById(long Id) {
		return wechatConfigMapper.getWechatConfigEntityById(Id);
	}
	
	@Override
	public WechatConfigEntity getWechatConfigEntityByCode(String code) {
		return wechatConfigMapper.getWechatConfigEntityByCode(code);
	}

	@Override
	public WechatConfigEntity saveWechatConfigEntity(WechatConfigEntity wechatConfigEntity) {
		wechatConfigEntity.setId(FactoryAboutKey.getKeyByTblNameTypeLong("S_WECHAT_CONFIG_T"));
		wechatConfigEntity.setCreateTime(new Date());
		wechatConfigEntity.setUpdateTime(new Date());
		wechatConfigEntity.setIsDel(0);
		wechatConfigEntity.setVersionNum(0L);
		LOGGER.debug("WechatConfigServiceImpl.saveWechatConfigEntity 保存微信配置信息传入参数:{}",
				JSONObject.toJSONString(wechatConfigEntity));
		wechatConfigMapper.saveWechatConfigEntity(wechatConfigEntity);
		return wechatConfigEntity;
	}

	@Override
	public WechatConfigEntity getWechatConfigEntityByCacheRedis(String code) {
		// 从redis中获取配置信息
		WechatConfigEntity configEntity = redisUtil
				.get(String.format(ConstantKey.WECHAT_CONFIG_ENTITY_KEY, code), WechatConfigEntity.class);
		if (null == configEntity) {
			// redis里面不存在时从数据库中获取,并且放在redis缓存中
			configEntity = this.getWechatConfigEntityByCode(code);
			redisUtil.set(String.format(ConstantKey.WECHAT_CONFIG_ENTITY_KEY, code), configEntity);
		}
		return configEntity;
	}




}
