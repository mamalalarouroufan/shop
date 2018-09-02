package com.shop.api.entity.dict.service;

import java.util.List;

import com.shop.api.entity.dict.DictVo;

public interface DictService {

	/**
	 * 
	 * @Title: getDictVoList   
	 * @Description: (获取dictVo列表)   
	 * @param dictkey
	 * @return      
	 * @return List<DictVo>  
	 * @author 王杰
	 * @date 2018年7月31日 下午10:32:51   
	 * @throws
	 */
	List<DictVo> getDictVoList(String dictkey);
}
