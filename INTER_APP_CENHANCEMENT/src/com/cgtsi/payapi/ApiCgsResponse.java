package com.cgtsi.payapi;
/**
 * Description : ApiCgsResponse.java
 * Created Date: 06-10-2021
 * Created By  : Deepak Kr Ranjan
 * 
 **/
import org.json.JSONObject;

public class ApiCgsResponse {
	String data="";
	String msgtime="";
    String msgid ="";
    String channelName="";
    String status="";		    
    String token ="";
    String errorMsg ="";
    String msgrrn="";
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getMsgtime() {
		return msgtime;
	}
	public void setMsgtime(String msgtime) {
		this.msgtime = msgtime;
	}
	public String getMsgid() {
		return msgid;
	}
	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	public String getChannelName() {
		return channelName;
	}
	public void setChannelName(String channelName) {
		this.channelName = channelName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
	public String getMsgrrn() {
		return msgrrn;
	}
	public void setMsgrrn(String msgrrn) {
		this.msgrrn = msgrrn;
	}
	@Override
	public String toString() {
		return "AurthoTokenResponse [data=" + data + ", msgtime=" + msgtime + ", msgid=" + msgid + ", channelName="
				+ channelName + ", status=" + status + ", token=" + token + ", errorMsg=" + errorMsg + ", msgrrn="
				+ msgrrn + "]";
	}   
	    
}
