package com.shop.api.base.entity;

import java.io.Serializable;
import java.util.Date;

public class BaseObject implements Serializable {

	private static final long serialVersionUID = 329430609755626516L;

	/** 主键 */
	private Long id;

	/** 删除标识：0、未删除 1、已删除 */
	private int isDel;

	/** 版本号 */
	private Long versionNum;

	/** 创建人 */
	private String createUserId;

	/** 创建时间 */
	private Date createTime;

	/** 修改人 */
	private String updateUserId;

	/** 修改时间 */
	private Date updateTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getIsDel() {
		return isDel;
	}

	public void setIsDel(int isDel) {
		this.isDel = isDel;
	}

	public Long getVersionNum() {
		return versionNum;
	}

	public void setVersionNum(Long versionNum) {
		this.versionNum = versionNum;
	}

	public String getCreateUserId() {
		return createUserId;
	}

	public void setCreateUserId(String createUserId) {
		this.createUserId = createUserId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getUpdateUserId() {
		return updateUserId;
	}

	public void setUpdateUserId(String updateUserId) {
		this.updateUserId = updateUserId;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}
