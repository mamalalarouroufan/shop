package com.shop.api.entity.dict;
/**
 * 
 * @ClassName  DictVo   
 * @Description (字典项前端显示vo)   
 * @author: 王杰
 * @date:   2018年7月31日 下午10:30:40   
 *     
 * @Copyright: 2018 www.xiaxiangdian.com Inc. All rights reserved. 
 * 注意：本内容仅限于霞湘店内部传阅，禁止外泄以及用于其他的商业目
 */
public class DictVo {

	//字典项编码
	private Integer dictCode;
	//字典项名称
	private String dictName;
	public Integer getDictCode() {
		return dictCode;
	}
	public void setDictCode(Integer dictCode) {
		this.dictCode = dictCode;
	}
	public String getDictName() {
		return dictName;
	}
	public void setDictName(String dictName) {
		this.dictName = dictName;
	}
	
	
}
