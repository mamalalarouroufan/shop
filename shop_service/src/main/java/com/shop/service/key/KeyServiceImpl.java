package com.shop.service.key;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shop.api.entity.key.Key;
import com.shop.api.entity.key.service.KeyService;
import com.shop.service.key.mapper.KeyMapper;

/**
 * 
 * 主键生成
 *
 * 
 */
@Service
public class KeyServiceImpl implements KeyService {

	@Resource
	private KeyMapper keyMapper;

	@Value("${hikari.dataSource.offline.schema}")
	private String database;

	/**
	 * 查询key列表
	 * 
	 * @return list
	 */
	@Override
	public List<Key> queryKeyList(List<Key> tbllist) {
		for (Key key : tbllist) {
			key.setTblName(key.getTblName().toLowerCase());
			if (key.getMachineCode() != null) {
				key.setMachineCode(key.getMachineCode().toLowerCase());
			}
		}
		return keyMapper.queryKeyList(tbllist);
	}

	/**
	 * @return 返回key集合(只存储表名)
	 */
	@Override
	public List<Key> queryTblList() {
		return keyMapper.queryTblList(database);
	}
}
