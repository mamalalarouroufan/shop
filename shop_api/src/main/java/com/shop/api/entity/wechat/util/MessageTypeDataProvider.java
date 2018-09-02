package com.shop.api.entity.wechat.util;

public class MessageTypeDataProvider {

	/**
	 * 请求消息类型: 文本
	 */
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 请求消息类型: 图片
	 */
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";

	/**
	 * 请求消息类型: 链接
	 */
	public static final String REQ_MESSAGE_TYPE_LINK = "link";

	/**
	 * 请求消息类型: 地理位置
	 */
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";
	/**
	 * 请求消息类型: 音频
	 */
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";
	/**
	 * 请求消息类型: 小视频
	 */
	public static final String REQ_MESSAGE_TYPE_SHORTVIDEO = "shortvideo";
	/**
	 * 请求消息类型: 视频
	 */
	public static final String REQ_MESSAGE_TYPE_VEDIO = "video";

	/**
	 * 请求消息类型: 推送
	 */
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";

	/**
	 * 事件类型: subscribe(订阅)
	 */
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";

	/**
	 * 事件类型: unsubscribe(取消订阅)
	 */
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";

	/**
	 * 事件类型: CLICK(自定义菜单点击事件)
	 */
	public static final String EVENT_TYPE_CLICK = "CLICK";

	/**
	 * 事件类型: SCAN(扫描带参数二维码事件)
	 */
	public static final String EVENT_TYPE_SCAN = "SCAN";

	/**
	 * 事件类型: VIEW(点击菜单跳转链接时的事件推送)
	 */
	public static final String EVENT_TYPE_VIEW = "VIEW";
	
	/**
	 * 事件类型: LOCATION(上报地理位置事件)
	 */
	public static final String EVENT_TYPE_LOCATION = "LOCATION";
	
	/**
	 * 事件类型: QUALIFICATION_VERIFY_SUCCESS(资质认证成功)
	 */
	public static final String EVENT_TYPE_QUALIFICATION_VERIFY_SUCCESS = "qualification_verify_success";
	
	/**
	 * 事件类型: QUALIFICATION_VERIFY_FAIL(资质认证失败)
	 */
	public static final String EVENT_TYPE_QUALIFICATION_VERIFY_FAIL = "qualification_verify_fail";
	
	/**
	 * 事件类型: ANNUAL_RENEW(提醒公众号需要去年审通知)
	 */
	public static final String EVENT_TYPE_ANNUAL_RENEW = "annual_renew";
	
	/**
	 * 事件类型: VERIFY_EXPIRED(认证过期失效通知审通知)
	 */
	public static final String EVENT_TYPE_VERIFY_EXPIRED = "verify_expired";
	
	/**
	 * 事件类型: NAMING_VERIFY_FAIL(名称认证失败)
	 */
	public static final String EVENT_TYPE_NAMING_VERIFY_FAIL = "naming_verify_fail";
	
	/**
	 * 事件类型: NAMING_VERIFY_SUCCESS(名称认证成功)
	 */
	public static final String EVENT_TYPE_NAMING_VERIFY_SUCCESS = "naming_verify_success";

	/**
	 * 事件类型: TEMPLATESENDJOBFINISH(模版消息发送任务完成后事件推送)
	 */
	public static final String EVENT_TYPE_TEMPLATESENDJOBFINISH = "TEMPLATESENDJOBFINISH";

	/**
	 * 返回消息类型: 文本
	 */
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";

	/**
	 * 返回消息类型: 音乐
	 */
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";

	/**
	 * 返回消息类型: 图文
	 */
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";

	/**
	 * 返回消息类型: 语音
	 */
	public static final String RESP_MESSAGE_TYPE_VOICE = "voice";
	/**
	 * 返回消息类型: 视频
	 */
	public static final String RESP_MESSAGE_TYPE_VIDEO = "video";

}