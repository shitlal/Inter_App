package com.cgtsi.payapi;
/**
 * Description : PaymentRequest.java
 * Created Date: 06-10-2021
 * Created By  : Deepak Kr Ranjan
 * 
 **/
public class PaymentRequest {
	private String dealerCode="";	
	private String virtualAccountNumber="";
	private String amount="0.00";
	private String amountGeneratedOn="";
	
	public String getDealerCode() {
		return dealerCode;
	}

	public void setDealerCode(String dealerCode) {
		this.dealerCode = dealerCode;
	}

	public String getVirtualAccountNumber() {
		return virtualAccountNumber;
	}

	public void setVirtualAccountNumber(String virtualAccountNumber) {
		this.virtualAccountNumber = virtualAccountNumber;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getAmountGeneratedOn() {
		return amountGeneratedOn;
	}

	public void setAmountGeneratedOn(String amountGeneratedOn) {
		this.amountGeneratedOn = amountGeneratedOn;
	}

	@Override
	public String toString() {
		return "PaymentRequest [dealerCode=" + dealerCode + ", virtualAccountNumber=" + virtualAccountNumber
				+ ", amount=" + amount + ", amountGeneratedOn=" + amountGeneratedOn + "]";
	}

	
	
}
