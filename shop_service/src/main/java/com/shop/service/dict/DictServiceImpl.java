package com.shop.service.dict;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.shop.api.entity.dict.DictVo;
import com.shop.api.entity.dict.service.DictService;
import com.shop.api.utils.constant.ConstantKey;
import com.shop.api.utils.redis.RedisUtil;
import com.shop.service.dict.mapper.DictMapper;

@Service("dictService")
public class DictServiceImpl implements DictService {

	private static final Logger LOGGER = LoggerFactory.getLogger(DictServiceImpl.class);

	@Resource
	private DictMapper dictMapper;

	@Resource
	private RedisUtil redisUtil;

	@Override
	public List<DictVo> getDictVoList(String dictkey) {
		LOGGER.info("DictServiceImpl.getDictVoList() 传入参数:{}", dictkey);
		List<DictVo> list = redisUtil.getListObject(ConstantKey.DICT_KEY + dictkey, DictVo.class);
		if (null == list) {
			list = dictMapper.getDictVoList(dictkey);
			redisUtil.set(ConstantKey.DICT_KEY + dictkey, list);
		}
		return list;
	}

}
