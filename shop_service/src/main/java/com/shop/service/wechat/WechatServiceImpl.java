package com.shop.service.wechat;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.shop.api.entity.wechat.WechatService;
import com.shop.api.entity.wechat.base.BaseURI;
import com.shop.api.entity.wechat.bean.AccessToken;
import com.shop.api.entity.wechat.bean.UserWechat;
import com.shop.api.entity.wechat.bean.WechatQRCode;
import com.shop.api.entity.wechat.config.WechatConfigEntity;
import com.shop.api.entity.wechat.config.service.WechatConfigService;
import com.shop.api.utils.constant.ConstantKey;
import com.shop.api.utils.httpclient.LocalHttpClient;
import com.shop.api.utils.redis.RedisUtil;

@Service("wechatService")
public class WechatServiceImpl implements WechatService {

	private static final Logger LOGGER = LoggerFactory.getLogger(WechatServiceImpl.class);

	@Resource
	private WechatConfigService wechatConfigService;
	@Resource
	private RedisUtil redisUtil;

	@Override
	public AccessToken getAccessToken(String code) {
		WechatConfigEntity wechatConfigEntity = wechatConfigService.getWechatConfigEntityByCacheRedis(code);
		AccessToken token = null;
		HttpUriRequest httpUriRequest = RequestBuilder.get().setUri(BaseURI.BASE_URI + "/cgi-bin/token")
				.addParameter("grant_type", "client_credential").addParameter("appid", wechatConfigEntity.getAppId())
				.addParameter("secret", wechatConfigEntity.getAppSecret()).build();
		try {
			token = LocalHttpClient.executeJsonResult(httpUriRequest, AccessToken.class);
			if (null != token && null != token.getAccess_token()) {
				redisUtil.set(String.format(ConstantKey.WECHAT_APPID_ACCESSTOKEN_KEY, wechatConfigEntity.getAppId()),
						token, token.getExpires_in());
			} else {
				LOGGER.error("APPID:{} 获取AccessToken失败:{}", wechatConfigEntity.getAppId(),
						JSONObject.toJSONString(token));
			}
		} catch (Exception e) {
			LOGGER.error("APPID:{} Exception获取AccessToken失败:{}", wechatConfigEntity.getAppId(), e);
		}
		return token;
	}

	@Override
	public AccessToken getAccessTokenByCacheRedis(String code) {
		WechatConfigEntity wechatConfigEntity = wechatConfigService.getWechatConfigEntityByCacheRedis(code);
		// 从缓存中获取accessToken
		AccessToken accessToken = redisUtil.get(
				String.format(ConstantKey.WECHAT_APPID_ACCESSTOKEN_KEY, wechatConfigEntity.getAppId()),
				AccessToken.class);
		// 如果accessToken为空时需要重新获取,accessToken时效性为7200秒(两小时)
		if (null == accessToken) {
			accessToken = this.getAccessToken(code);
		}
		return accessToken;
	}

	@Override
	public UserWechat getUserWechat(String code, String openid) {
		UserWechat userWechat = null;
		WechatConfigEntity wechatConfigEntity = wechatConfigService.getWechatConfigEntityByCacheRedis(code);
		AccessToken accessToken = this.getAccessTokenByCacheRedis(code);
		HttpUriRequest httpUriRequest = RequestBuilder.get().setUri(BaseURI.BASE_URI + "/cgi-bin/user/info")
				.addParameter("access_token", accessToken.getAccess_token()).addParameter("openid", openid)
				.addParameter("lang", "zh_CN").build();
		try {
			userWechat = LocalHttpClient.executeJsonResult(httpUriRequest, UserWechat.class);
			if (null != userWechat && null != userWechat.getOpenid()) {
				redisUtil.set(String.format(ConstantKey.WECHAT_APPID_USERWECHAT_KEY, wechatConfigEntity.getAppId(),
						userWechat.getOpenid()), userWechat);
			} else {
				LOGGER.error("APPID:{} 拉取微信公众号获取用户基本信息失败:{}", wechatConfigEntity.getAppId(),
						JSONObject.toJSONString(userWechat));
			}
		} catch (Exception e) {
			LOGGER.error("APPID:{} Exception拉取微信公众号获取用户基本信息失败:{}", wechatConfigEntity.getAppId(), e);
		}
		return userWechat;
	}

	@Override
	public UserWechat getUserWechatByCacheRedis(String code, String openid) {
		WechatConfigEntity wechatConfigEntity = wechatConfigService.getWechatConfigEntityByCacheRedis(code);
		UserWechat userWechat = redisUtil.get(
				String.format(ConstantKey.WECHAT_APPID_USERWECHAT_KEY, wechatConfigEntity.getAppId(), openid),
				UserWechat.class);
		if (null == userWechat || null == userWechat.getOpenid()) {
			userWechat = this.getUserWechat(code, openid);
		}
		return userWechat;
	}


	@Override
	public WechatQRCode createTempTicket(String code, String expireSeconds, int sceneId) {
		AccessToken accessToken = this.getAccessTokenByCacheRedis(code);
		Map<String, Integer> intMap = new HashMap<>();
		intMap.put("scene_id", sceneId);
		Map<String, Map<String, Integer>> mapMap = new HashMap<>();
		mapMap.put("scene", intMap);
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("expire_seconds", expireSeconds);
		paramsMap.put("action_name", "QR_SCENE");
		paramsMap.put("action_info", mapMap);
		String data = JSONObject.toJSONString(paramsMap);
		return this.getWechatQRCodeHttpClietBind(accessToken.getAccess_token(), data);
	}

	@Override
	public WechatQRCode createTempTicket(String code, String expireSeconds, String sceneStr) {
		AccessToken accessToken = this.getAccessTokenByCacheRedis(code);
		Map<String, String> intMap = new HashMap<>();
		intMap.put("scene_str", sceneStr);
		Map<String, Map<String, String>> mapMap = new HashMap<>();
		mapMap.put("scene", intMap);
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("expire_seconds", expireSeconds);
		paramsMap.put("action_name", "QR_STR_SCENE");
		paramsMap.put("action_info", mapMap);
		String data = JSONObject.toJSONString(paramsMap);
		return this.getWechatQRCodeHttpClietBind(accessToken.getAccess_token(), data);
	}

	@Override
	public WechatQRCode createForeverTicket(String code, int sceneId) {
		AccessToken accessToken = this.getAccessTokenByCacheRedis(code);
		Map<String, Integer> intMap = new HashMap<>();
		intMap.put("scene_id", sceneId);
		Map<String, Map<String, Integer>> mapMap = new HashMap<>();
		mapMap.put("scene", intMap);
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("action_name", "QR_LIMIT_SCENE");
		paramsMap.put("action_info", mapMap);
		String data = JSONObject.toJSONString(paramsMap);
		return this.getWechatQRCodeHttpClietBind(accessToken.getAccess_token(), data);
	}

	@Override
	public WechatQRCode createForeverSttrTicket(String code, String sceneStr) {
		AccessToken accessToken = this.getAccessTokenByCacheRedis(code);
		Map<String, String> intMap = new HashMap<>();
		intMap.put("scene_str", sceneStr);
		Map<String, Map<String, String>> mapMap = new HashMap<>();
		mapMap.put("scene", intMap);
		Map<String, Object> paramsMap = new HashMap<>();
		paramsMap.put("action_name", "QR_LIMIT_STR_SCENE");
		paramsMap.put("action_info", mapMap);
		String data = JSONObject.toJSONString(paramsMap);
		return this.getWechatQRCodeHttpClietBind(accessToken.getAccess_token(), data);
	}

	private WechatQRCode getWechatQRCodeHttpClietBind(String accessToken, String data) {
		WechatQRCode wechatQRCode = null;
		HttpUriRequest httpUriRequest;
		try {
			httpUriRequest = RequestBuilder.post().setUri(BaseURI.BASE_URI + "/cgi-bin/qrcode/create")
					.addParameter("access_token", accessToken).setEntity(new StringEntity(data)).build();
			wechatQRCode = LocalHttpClient.executeJsonResult(httpUriRequest, WechatQRCode.class);
		} catch (Exception e) {
			LOGGER.error("Exception生成带参数的二维码失败:{}", e);
		}
		return wechatQRCode;
	}

}
