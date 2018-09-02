package com.shop.api.entity.user;

import java.util.Date;
import com.shop.api.base.entity.BaseObject;

/**
 * 
 * @ClassName: UserEntity
 * @Description:(用户表(s_user_t)实体类)
 * @author: 王杰
 * @date: 2018年7月25日 上午1:27:17
 * 
 * @Copyright: 2018 www.xiaxiangdian.com Inc. All rights reserved.
 *             注意：本内容仅限于霞湘店内部传阅，禁止外泄以及用于其他的商业目
 */
public class UserEntity extends BaseObject {

	private static final long serialVersionUID = 8713375536323809887L;

	/** 用户昵称 */
	private String nickName;

	/** 用户姓名 */
	private String name;

	/** 用户密码 */
	private String password;

	/** 用户性别 */
	private String sex;

	/** 用户手机号 */
	private String phone;

	/** 用户备用手机号 */
	private String sparePhone;

	/** 手机号区域(86为中国大陆) */
	private String phoneArea;

	/** QQ号 */
	private String QQ;

	/** 电子邮件地址 */
	private String email;

	/** 用户生日 */
	private String birthDay;

	/** 用户注册日期 */
	private Date registerDate;

	/** 证件类型 */
	private String certType;

	/** 证件号 */
	private String certNumber;

	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getSparePhone() {
		return sparePhone;
	}

	public void setSparePhone(String sparePhone) {
		this.sparePhone = sparePhone;
	}

	public String getPhoneArea() {
		return phoneArea;
	}

	public void setPhoneArea(String phoneArea) {
		this.phoneArea = phoneArea;
	}

	public String getQQ() {
		return QQ;
	}

	public void setQQ(String qQ) {
		QQ = qQ;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getBirthDay() {
		return birthDay;
	}

	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}

	public Date getRegisterDate() {
		return registerDate;
	}

	public void setRegisterDate(Date registerDate) {
		this.registerDate = registerDate;
	}

	public String getCertType() {
		return certType;
	}

	public void setCertType(String certType) {
		this.certType = certType;
	}

	public String getCertNumber() {
		return certNumber;
	}

	public void setCertNumber(String certNumber) {
		this.certNumber = certNumber;
	}

}
