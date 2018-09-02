package com.shop.service.dict.mapper;

import java.util.List;

import com.shop.api.entity.dict.DictVo;

public interface DictMapper {

	List<DictVo> getDictVoList(String dictkey);

}
