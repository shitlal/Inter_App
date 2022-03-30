package com.cgtsi.payapi;
/**
 * Description : JsonDataForToken.java
 * Created Date: 06-10-2021
 * Created By  : Deepak Kr Ranjan
 * 
 * */
public class JsonDataForAutho {
	private String requestType="";	
	private String msgId="";
	private JsonAurthToken data;
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getMsgId() {
		return msgId;
	}
	public void setMsgId(String msgId) {
		this.msgId = msgId;
	}
	public JsonAurthToken getData() {
		return data;
	}
	public void setData(JsonAurthToken data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "JsonDataForToken2 [requestType=" + requestType + ", msgId=" + msgId + ", data=" + data
				+ ", getRequestType()=" + getRequestType() + ", getMsgId()=" + getMsgId() + ", getData()=" + getData()
				+ ", getClass()=" + getClass() + ", hashCode()=" + hashCode() + ", toString()=" + super.toString()
				+ "]";
	}
	
}
