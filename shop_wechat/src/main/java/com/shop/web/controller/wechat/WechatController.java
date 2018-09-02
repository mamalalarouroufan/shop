package com.shop.web.controller.wechat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletInputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.shop.api.base.controller.BaseController;
import com.shop.api.entity.wechat.WechatService;
import com.shop.api.entity.wechat.bean.ReceiveEventMessage;
import com.shop.api.entity.wechat.bean.UserWechat;
import com.shop.api.entity.wechat.config.WechatConfigEntity;
import com.shop.api.entity.wechat.config.service.WechatConfigService;
import com.shop.api.entity.wechat.message.xmlreply.XMLReplyTextMessage;
import com.shop.api.entity.wechat.util.MessageTypeDataProvider;
import com.shop.api.utils.httpclient.XMLConverUtil;
import com.shop.api.utils.sha1.SHA1Util;

@Controller
public class WechatController extends BaseController {
	private static final Logger LOGGER = LoggerFactory.getLogger(WechatController.class);

	@Resource
	private WechatConfigService wechatConfigService;
	@Resource
	private WechatService wechatService;

	@RequestMapping("/wechat/wechatQrCode/{code}")
	public void wechatQrCode(@PathVariable("code") String code) throws Exception {
		//WechatQRCode qrCode = wechatService.createForeverSttrTicket(code, "bus");
		// String url =
		// "https://mp.weixin.qq.com/cgi-bin/showqrcode?ticket=gQEz8DwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyWHZhanNuLVBlcWwxMDAwMHcwN1gAAgT56FpbAwQAAAAA";
		// HttpGet httpGet = new HttpGet(url);
		// String filePath = LocalHttpClient.executeDownloadFile(httpGet,
		// ".//image", null);
		//
		// FileInputStream fis = null;
		// OutputStream os = null;
		// try {
		// fis = new FileInputStream(filePath);
		// os = getResponse().getOutputStream();
		// int count = 0;
		// byte[] buffer = new byte[1024 * 8];
		// while ((count = fis.read(buffer)) != -1) {
		// os.write(buffer, 0, count);
		// os.flush();
		// }
		// } catch (Exception e) {
		// LOGGER.error("Exception:{}",e);
		// }
		// try {
		// fis.close();
		// os.close();
		// } catch (IOException e) {
		// LOGGER.error("Exception:{}",e);
		// }

	}

	/**
	 * 
	 * @Title: wechatServerAddressMethod   
	 * @Description: (微信公众号服务器配置访问地址和接收消息回复地址)   
	 * @param Id
	 * @throws IOException      
	 * @return void  
	 * @author 王杰
	 * @date 2018年7月27日 下午12:33:49   
	 * @throws
	 */
	@RequestMapping("/wechat/wechatServer/{code}")
	public void wechatServerAddressMethod(@PathVariable("code") String code) throws IOException {
		LOGGER.info("WechatController.wechatServerAddressMethod 传入参数code:{}", code);
		boolean isGet = this.getRequest().getMethod().toLowerCase().equals("get");
		this.getRequest().setAttribute("code", code);

		// get请求代表微信公众号接入认证,post请求代表微信公众号消息

		if (isGet) {
			this.checkOut();
		} else {
			this.AcceptAndReturnMessage();
		}

	}

	/*
	 * 校验服务器地址有效性
	 */
	private void checkOut() {
		WechatConfigEntity wechatConfigEntity = wechatConfigService
				.getWechatConfigEntityByCacheRedis(this.getRequest().getAttribute("code").toString());
		// 微信加密签名
		String signature = this.getRequest().getParameter("signature");
		// 时间戳
		String timestamp = this.getRequest().getParameter("timestamp");
		// 随机数
		String nonce = this.getRequest().getParameter("nonce");
		// 随机字符串
		String echostr = this.getRequest().getParameter("echostr");
		List<String> params = new ArrayList<String>();
		params.add(wechatConfigEntity.getToken());
		params.add(timestamp);
		params.add(nonce);
		LOGGER.info("微信公众号ID:{} 校验服务器地址传入参数 signature:{},timestamp:{},nonce:{},echostr:{}", wechatConfigEntity.getId(),
				signature, timestamp, nonce, echostr);
		// 1. 将token、timestamp、nonce三个参数进行字典序排序
		Collections.sort(params, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return o1.compareTo(o2);
			}
		});
		// 2. 将三个参数字符串拼接成一个字符串进行sha1加密
		String temp = SHA1Util.getSha1(params.get(0) + params.get(1) + params.get(2));
		if (temp.equals(signature)) {
			try {
				this.getResponse().getWriter().write(echostr);
			} catch (IOException e) {
				LOGGER.error("校验失败-->", e);
			}
		} else {
			LOGGER.error("校验失败 微信公众号ID:{}", wechatConfigEntity.getId());
		}
	}

	/*
	 * 微信公众号被动接收并回复消息
	 */
	private void AcceptAndReturnMessage() throws IOException {
		// 处理接收消息
		ServletInputStream in = this.getRequest().getInputStream();
		ReceiveEventMessage inputMsg = XMLConverUtil.convertToObject(ReceiveEventMessage.class, in);
		LOGGER.info("接收到的对象信息:{}", inputMsg);

		// 取得消息类型
		String msgType = inputMsg.getMsgType();

		switch (msgType) {
			// 请求消息类型: 文本
			case MessageTypeDataProvider.REQ_MESSAGE_TYPE_TEXT: {
				this.ReplyTextMsg(inputMsg, null);
				break;
			}
			// 请求消息类型: 图片
			case MessageTypeDataProvider.REQ_MESSAGE_TYPE_IMAGE: {
				break;
			}
			// 请求消息类型: 链接
			case MessageTypeDataProvider.REQ_MESSAGE_TYPE_LINK: {
				break;
			}
			// 请求消息类型: 地理位置
			case MessageTypeDataProvider.REQ_MESSAGE_TYPE_LOCATION: {
				break;
			}
			// 请求消息类型: 音频
			case MessageTypeDataProvider.REQ_MESSAGE_TYPE_VOICE: {
				break;
			}
			// 请求消息类型: 小视频
			case MessageTypeDataProvider.REQ_MESSAGE_TYPE_SHORTVIDEO: {
				break;
			}
			// 请求消息类型: 事件推送
			case MessageTypeDataProvider.REQ_MESSAGE_TYPE_EVENT: {
				this.ReplyRventMsg(inputMsg);
				break;
			}
			default:
				break;
		}

	}

	/**
	 * 
	 * @Title: ReplyTextMsg   
	 * @Description: (被动回复文本消息)   
	 * @param inputMsg
	 * @param content
	 * @throws IOException      
	 * @return void  
	 * @author 王杰
	 * @date 2018年8月1日 下午4:07:05   
	 * @throws
	 */
	public void ReplyTextMsg(ReceiveEventMessage inputMsg, String content) throws IOException {
		String servername = inputMsg.getToUserName();// 服务端
		String custermname = inputMsg.getFromUserName();// 客户端
		if (null == content) {
			content = "openId为:\n/:sun/:moon" + custermname + "/:sun/:moon\n的用户---> 你好,欢迎访问测试公众号！！！"
					+ "\n 官方网址<a href=\"http://www.baidu.com\">霞湘店</a>";
		}
		XMLReplyTextMessage text = new XMLReplyTextMessage(custermname, servername, content);
		LOGGER.info("被动回复文本消息:{}", text.toXML());
		this.getResponse().getWriter().print(text.toXML());
	}

	/**
	 * 
	 * @Title: ReplyRventMsg   
	 * @Description: (被动回复事件消息)   
	 * @param inputMsg
	 * @throws IOException      
	 * @return void  
	 * @author 王杰
	 * @date 2018年8月1日 下午4:07:14   
	 * @throws
	 */
	public void ReplyRventMsg(ReceiveEventMessage inputMsg) throws IOException {
		String event = inputMsg.getEvent();
		switch (event) {
			// 用户关注事件
			case MessageTypeDataProvider.EVENT_TYPE_SUBSCRIBE: {
				String code = (String) this.getRequest().getAttribute("code");
				String openid = inputMsg.getFromUserName();
				UserWechat userWechat = wechatService.getUserWechatByCacheRedis(code, openid);
				String eventKey = inputMsg.getEventKey();
				LOGGER.info("eventKey:{}", eventKey);
				if (null == eventKey || "".equals(eventKey)) {
					LOGGER.info("普通关注公众号事件");
					// 普通关注公众号事件
					String nickName = userWechat != null ? "“" + userWechat.getNickname() + "”" : "";
					String content = "亲爱的" + nickName + "用户你好,欢迎你关注本公众号...\n<a href=\"http://www.baidu.com\">霞湘店网址</a>";
					this.ReplyTextMsg(inputMsg, content);
				} else {
					// 扫描带参数二维码事件
					LOGGER.info("扫描带参数二维码事件,用户来源:{}",inputMsg.getEventKey());
				}
				break;
			}
			// 用户取消关注事件
			case MessageTypeDataProvider.EVENT_TYPE_UNSUBSCRIBE: {
				LOGGER.info("用户Id:{} 开发者微信号:{} 取消关注事件", inputMsg.getFromUserName(), inputMsg.getToUserName());
				break;
			}
			// 点击菜单拉取消息时的事件推送
			case MessageTypeDataProvider.EVENT_TYPE_CLICK: {

				break;
			}
			// 点击菜单跳转链接时的事件推送
			case MessageTypeDataProvider.EVENT_TYPE_VIEW: {

				break;
			}
			// 上报地理位置事件
			case MessageTypeDataProvider.EVENT_TYPE_LOCATION: {
				LOGGER.info("用户上报地理位置 纬度:{};经度:{};精度:{}", inputMsg.getLatitude(), inputMsg.getLongitude(),
						inputMsg.getPrecision());
				break;
			}
			// 资质认证成功事件
			case MessageTypeDataProvider.EVENT_TYPE_QUALIFICATION_VERIFY_SUCCESS: {

				break;
			}
			// 资质认证失败事件
			case MessageTypeDataProvider.EVENT_TYPE_QUALIFICATION_VERIFY_FAIL: {

				break;
			}
			// 提醒公众号需要去年审通知事件
			case MessageTypeDataProvider.EVENT_TYPE_ANNUAL_RENEW: {

				break;
			}
			// 认证过期失效通知审通知事件
			case MessageTypeDataProvider.EVENT_TYPE_VERIFY_EXPIRED: {

				break;
			}
			// 名称认证成功事件
			case MessageTypeDataProvider.EVENT_TYPE_NAMING_VERIFY_SUCCESS: {

				break;
			}
			// 名称认证失败事件
			case MessageTypeDataProvider.EVENT_TYPE_NAMING_VERIFY_FAIL: {

				break;
			}
			
			// 模版消息发送任务完成后事件推送
			case MessageTypeDataProvider.EVENT_TYPE_TEMPLATESENDJOBFINISH: {
				LOGGER.info("模版消息发送任务完成后事件推送 用户openId:{} Status:{} 消息Id:{}",inputMsg.getFromUserName(),inputMsg.getStatus(),inputMsg.getMsgID());
				break;
			}

			default:
				break;
		}
	}

	/**
	 * 
	 * @Title: ReplyRventNews   
	 * @Description: (被动回复图文消息)   
	 * @param inputMsg      
	 * @return void  
	 * @author 王杰
	 * @date 2018年8月1日 下午4:08:31   
	 * @throws
	 */
	public void ReplyRventNews(ReceiveEventMessage inputMsg){
		
	}
	
	/**
	 * 
	 * @Title: ReplyRventMusic   
	 * @Description: (被动回复音乐消息)   
	 * @param inputMsg      
	 * @return void  
	 * @author 王杰
	 * @date 2018年8月1日 下午4:11:15   
	 * @throws
	 */
	public void ReplyRventMusic(ReceiveEventMessage inputMsg){
		
	}
	
	/**
	 * 
	 * @Title: ReplyRventVideo   
	 * @Description: (被动回复视频消息)   
	 * @param inputMsg      
	 * @return void  
	 * @author 王杰
	 * @date 2018年8月1日 下午4:11:02   
	 * @throws
	 */
	public void ReplyRventVideo(ReceiveEventMessage inputMsg){
		
	}

	/**
	 * 
	 * @Title: ReplyRventVoice   
	 * @Description: (被动回复语音消息)   
	 * @param inputMsg      
	 * @return void  
	 * @author 王杰
	 * @date 2018年8月1日 下午4:10:45   
	 * @throws
	 */
	public void ReplyRventVoice(ReceiveEventMessage inputMsg){
		
	}
	
	/**
	 * 
	 * @Title: ReplyRventImage   
	 * @Description: (被动回复图片消息)   
	 * @param inputMsg      
	 * @return void  
	 * @author 王杰
	 * @date 2018年8月1日 下午4:10:21   
	 * @throws
	 */
	public void ReplyRventImage(ReceiveEventMessage inputMsg){
		
	}
}
