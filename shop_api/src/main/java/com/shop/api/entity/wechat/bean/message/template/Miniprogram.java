package com.shop.api.entity.wechat.bean.message.template;
/**
 * 
 * @ClassName  Miniprogram   
 * @Description (微信模板消息跳转小程序实体)   
 * @author: 王杰
 * @date:   2018年8月10日 下午1:38:51   
 *     
 * @Copyright: 2018 www.xiaxiangdian.com Inc. All rights reserved. 
 * 注意：本内容仅限于霞湘店内部传阅，禁止外泄以及用于其他的商业目
 */
public class Miniprogram {

	/** 所需跳转到的小程序appid（该小程序appid必须与发模板消息的公众号是绑定关联关系，暂不支持小游戏） */
	private String appid;
	
	/** 所需跳转到小程序的具体页面路径，支持带参数,（示例index?foo=bar），暂不支持小游戏 */
	private String pagepath;

	public String getAppid() {
		return appid;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public String getPagepath() {
		return pagepath;
	}

	public void setPagepath(String pagepath) {
		this.pagepath = pagepath;
	}
	
	
}
