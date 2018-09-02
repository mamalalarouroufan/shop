package com.shop.service.wechat.message;

import javax.annotation.Resource;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.shop.api.entity.wechat.WechatService;
import com.shop.api.entity.wechat.base.BaseResult;
import com.shop.api.entity.wechat.base.BaseURI;
import com.shop.api.entity.wechat.bean.message.template.Template;
import com.shop.api.entity.wechat.bean.message.template.TemplateResult;
import com.shop.api.entity.wechat.message.service.TemplateMessageService;
import com.shop.api.utils.httpclient.LocalHttpClient;

@Service("templateMessageService")
public class TemplateMessageServiceImpl implements TemplateMessageService {

	private static final Logger LOGGER = LoggerFactory.getLogger(TemplateMessageServiceImpl.class);

	@Resource
	private WechatService wechatService;

	@Override
	public void setUpIndustry(String code) {
		// TODO Auto-generated method stub

	}

	@Override
	public String getIndustryInformation(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getTemplateId(String code) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getAllPrivateTemplate(String code) {
		String accessToken = wechatService.getAccessTokenByCacheRedis(code).getAccess_token();
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BaseURI.BASE_URI + "/cgi-bin/template/del_private_template")
				.addParameter("access_token", accessToken).build();
		 try {
			String result = LocalHttpClient.executeJsonResult(httpUriRequest,String.class);
			LOGGER.info("获取模板列表 code:{} , result:{}",code,result);
			return result;
		} catch (Exception e) {
			LOGGER.error("获取模板列表 --->",e);
		}
		return null;
	}

	@Override
	public void delPrivateTemplate(String code, String templateId) {
		LOGGER.info("删除模板start code:{} templateId:{}",code,templateId);
		String accessToken = wechatService.getAccessTokenByCacheRedis(code).getAccess_token();
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BaseURI.BASE_URI + "/cgi-bin/template/del_private_template")
				.addParameter("access_token", accessToken)
				.addParameter("template_id", templateId).build();
		 try {
			BaseResult result = LocalHttpClient.executeJsonResult(httpUriRequest,BaseResult.class);
			LOGGER.info("删除模板end 成功信息:{}",JSONObject.toJSONString(result));
		} catch (Exception e) {
			LOGGER.error("删除模板异常 --->",e);
		}
	}

	@Override
	public void sendTemplateMessage(String code, Template template) throws Exception {
		String accessToken = wechatService.getAccessTokenByCacheRedis(code).getAccess_token();
		HttpUriRequest httpUriRequest = RequestBuilder.post()
				.setUri(BaseURI.BASE_URI + "/cgi-bin/message/template/send")
				.addParameter("access_token", accessToken)
				.setEntity(new StringEntity(JSONObject.toJSONString(template),"utf-8")).build();
		TemplateResult result = LocalHttpClient.executeJsonResult(httpUriRequest, TemplateResult.class);
		LOGGER.info("发送模板消息返回状态:{}",JSONObject.toJSONString(result));
	}

}
