package com.cgtsi.payapi;
/**
 * Description : JsonAurthToken.java
 * Created Date: 06-10-2021
 * Created By  : Deepak Kr Ranjan
 * 
 * */
public class JsonAurthToken {
	private String username="";	
	private String password="";
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "JsonAurthToken [username=" + username + ", password=" + password + "]";
	}
	
}
