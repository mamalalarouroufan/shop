package com.shop.service.key.mapper;

import java.util.List;
import com.shop.api.entity.key.Key;

public interface KeyMapper {
	
	/**
	 * @return	返回key集合
	 */
	List<Key> queryKeyList(List<Key> tbllist);
	
	
	/**
	 * @return	返回key集合(只存储表名)
	 */
	List<Key> queryTblList(String database);
}
