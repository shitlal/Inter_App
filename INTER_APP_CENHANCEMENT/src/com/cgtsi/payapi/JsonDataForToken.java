package com.cgtsi.payapi;
/**
 * Description : JsonDataForToken.java
 * Created Date: 06-10-2021
 * Created By  : Deepak Kr Ranjan
 * 
 * */
public class JsonDataForToken {
	private String requestType="";	
	private String msgid="";
	private PaymentRequest data;
	public String getRequestType() {
		return requestType;
	}
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	public String getMsgid() {
		return msgid;
	}
	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	public PaymentRequest getData() {
		return data;
	}
	public void setData(PaymentRequest data) {
		this.data = data;
	}
	@Override
	public String toString() {
		return "JsonDataForToken [requestType=" + requestType + ", msgid=" + msgid + ", data=" + data + "]";
	}
	
}
