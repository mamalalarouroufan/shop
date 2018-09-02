package com.shop.api.entity.wechat.message.xmlreply;

public class XMLReplyVoiceMessage extends XMLReplyMessage{

	private String mediaId;
	
	public XMLReplyVoiceMessage(String toUserName, String fromUserName,String mediaId) {
		super(toUserName, fromUserName, "voice");
		this.mediaId = mediaId;
	}


	@Override
	public String subXML() {
		return "<Voice><MediaId><![CDATA["+mediaId+"]]></MediaId></Voice>";
	}

	
}
