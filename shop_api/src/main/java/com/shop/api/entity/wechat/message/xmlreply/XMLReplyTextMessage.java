package com.shop.api.entity.wechat.message.xmlreply;

public class XMLReplyTextMessage extends XMLReplyMessage{

	private String content;
	
	public XMLReplyTextMessage(String toUserName, String fromUserName,String content) {
		super(toUserName, fromUserName, "text");
		this.content = content;
	}


	@Override
	public String subXML() {
		return "<Content><![CDATA["+content+"]]></Content>";
	}

	
}
