package com.shop.api.entity.key;

import java.io.Serializable;


/**
 * 
 * @ClassName: Key
 * @Description:(主键生成)
 * @author: 王杰
 * @date: 2018年7月25日 下午2:37:26
 * 
 * @Copyright: 2018 www.xiaxiangdian.com Inc. All rights reserved.
 *             注意：本内容仅限于霞湘店内部传阅，禁止外泄以及用于其他的商业目
 */
public class Key implements Serializable {

	private static final long serialVersionUID = -7682316505620174086L;
	
	private String id;

	/** 表名 */
	private String tblName;

	/** 机器码 */
	private String machineCode;

	public String getMachineCode() {
		return machineCode;
	}

	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}

	public String getTblName() {
		return tblName;
	}

	public void setTblName(String tblName) {
		this.tblName = tblName;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

}