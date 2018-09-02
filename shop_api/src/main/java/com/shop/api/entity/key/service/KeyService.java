package com.shop.api.entity.key.service;

import java.util.List;

import com.shop.api.entity.key.Key;

public interface KeyService {
	/**
	 * 
	 * @param tbllist
	 * @return 返回key集合
	 */
	List<Key> queryKeyList(List<Key> tbllist);

	/**
	 * @return 返回key集合(只存储表名)
	 */
	List<Key> queryTblList();

}