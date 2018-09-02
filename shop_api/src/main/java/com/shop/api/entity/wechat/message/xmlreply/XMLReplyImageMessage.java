package com.shop.api.entity.wechat.message.xmlreply;

public class XMLReplyImageMessage extends XMLReplyMessage{

	private String mediaId;
	
	public XMLReplyImageMessage(String toUserName, String fromUserName,String mediaId) {
		super(toUserName, fromUserName, "image");
		this.mediaId = mediaId;
	}


	@Override
	public String subXML() {
		return "<Image><MediaId><![CDATA["+mediaId+"]]></MediaId></Image>";
	}

	
}
