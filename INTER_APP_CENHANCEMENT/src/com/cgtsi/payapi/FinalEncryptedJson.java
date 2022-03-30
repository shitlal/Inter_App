package com.cgtsi.payapi;
/**
 * Description : FinalEncryptedJson.java
 * Created Date: 06-10-2021
 * Created By  : Deepak Kr Ranjan
 * 
 * */
public class FinalEncryptedJson {
	private String reqdata="";	
	private String msgid="";
	public String getReqdata() {
		return reqdata;
	}
	public void setReqdata(String reqdata) {
		this.reqdata = reqdata;
	}
	public String getMsgid() {
		return msgid;
	}
	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	@Override
	public String toString() {
		return "FinalEncryptedJson [reqdata=" + reqdata + ", msgid=" + msgid + "]";
	}
	
	
}
