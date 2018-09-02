package com.shop.api.entity.wechat.message.xmlreply;

import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shop.api.entity.wechat.ase.AesException;
import com.shop.api.entity.wechat.ase.WXBizMsgCrypt;



public abstract class XMLReplyMessage {
	
	private String toUserName; //用户的openId
	private String fromUserName; //微信公众号
	private String msgType; //消息类型
	private static final Logger LOGGER = LoggerFactory.getLogger(XMLReplyMessage.class);
	protected XMLReplyMessage(String toUserName, String fromUserName, String msgType) {
		super();
		this.toUserName = toUserName;
		this.fromUserName = fromUserName;
		this.msgType = msgType;
	}

	/**
	 * 子类自定义XML
	 * @return
	 */
	public abstract String subXML();

	public String toXML(){
		StringBuilder sb = new StringBuilder();
		sb.append("<xml>");
		sb.append("<ToUserName><![CDATA["+toUserName+"]]></ToUserName>");
		sb.append("<FromUserName><![CDATA["+fromUserName+"]]></FromUserName>");
		sb.append("<CreateTime>"+System.currentTimeMillis()/1000+"</CreateTime>");
		sb.append("<MsgType><![CDATA["+msgType+"]]></MsgType>");
		sb.append(subXML());
		sb.append("</xml>");
		return sb.toString();
	}

	public boolean outputStreamWrite(OutputStream outputStream){
		try {
			outputStream.write(toXML().getBytes("utf-8"));
			outputStream.flush();
		} catch (UnsupportedEncodingException e) {
			LOGGER.error("-->",e);;
			return false;
		} catch (IOException e) {
			LOGGER.error("-->",e);;
			return false;
		}
		return true;
	}

	public boolean outputStreamWrite(OutputStream outputStream,WXBizMsgCrypt bizMsgCrypt){
		if(bizMsgCrypt != null){
			try {
				String outputStr = bizMsgCrypt.encryptMsg(toXML(), System.currentTimeMillis()+"",UUID.randomUUID().toString());
				outputStream.write(outputStr.getBytes("utf-8"));
				outputStream.flush();
			} catch (UnsupportedEncodingException e) {
				LOGGER.error("-->",e);;
				return false;
			} catch (IOException e) {
				LOGGER.error("-->",e);;
				return false;
			} catch (AesException e) {
				LOGGER.error("-->",e);;
				return false;
			}
			return true;
		}else{
			return outputStreamWrite(outputStream);
		}
	}
}
