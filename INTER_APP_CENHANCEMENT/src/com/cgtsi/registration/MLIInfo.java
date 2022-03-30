//Source file: C:\\CGTSI\\Vss\\com\\cgtsi\\registration\\MLIInfo.java

package com.cgtsi.registration;

import java.io.Serializable;


/**
 * This class holds all the data related to an MLI.
 */
public class MLIInfo implements Serializable
{
   
   /**
    * Id of the Bank that is associated with the CGTSI as a MLI.
    */
   private String bankId;
   private String gstNo;

   private String schemeFlag;// ADDED BY DKR FOR CO-LANDING 2022
   /**
    * Zone Id of the zone where the bank is located
    */
   private String zoneId;
   
   /**
    * Id of the bank branch.
    */
   private String branchId;
   
   /**
    * Id of the Collecting Bank to which the MLI is associated with.
    */
   private String collectingBankId;
   private String collectingBankName;
   
   /**
    * Name of the bank which has registered itself with CGTSI as MLI
    */
   private String bankName;
   //For changes made in screen
   //NOv 12, 2003
   //Ramesh rp14480
   private String shortName;
   private String supportMCGF;
   private String mail;
   private String eMail;
   private String hardCopy;
   private String reportingZone;
   private String[] danDelivery;
   /**
    * Name of the zone where the bank is located
    */
   private String zoneName;
   
   /**
    * Name of the branch bank.
    */
   private String branchName;
   
   /**
    * Address where the bank is located
    */
   private String address;
   
   /**
    * City where the bank is located
    */
   private String city;
   
   /**
    * Pincode of the bank
    */
   private String pin;
   
   /**
    * Phone Number of the bank
    */
   private String phone;
   private String phoneStdCode;
   /**
    * Fax number of the bank
    */
   private String fax;
   private String faxStdCode;
   
   /**
    * status of whether the MLI is active or not with respect to CGTSI.
    */
   private String status;
   
   /**
    * email Id of the contact person at the bank.
    */
   private String email;
   private String emailId;
   private String mcgf;
   
   /**
    * This attribute is to identify whether the bank is the head office, zonal, or 
    * branch office.
    * 0 - head office
    * 1 - zonal office
    * 2 - branch office
    */
   private Integer bankType;
   private String state;
   private String stateCode;
   private String stateName;
   private String gstState;

	public String getSchemeFlag() {
	return schemeFlag;
}

public void setSchemeFlag(String schemeFlag) {
	this.schemeFlag = schemeFlag;
}

public String getGstNo() {
	return gstNo;
}

	public String getGstState() {
	return gstState;
}

public void setGstState(String gstState) {
	this.gstState = gstState;
}

public String getStateName() {
	return stateName;
}

public void setStateName(String stateName) {
	this.stateName = stateName;
}

private String district;
   private String reportingZoneID;
   public String geteMail() {
	return eMail;
}

public void seteMail(String eMail) {
	this.eMail = eMail;
}

public String getFlag() {
	return flag;
}

public void setFlag(String flag) {
	this.flag = flag;
}
public String getStateCode() {
	return stateCode;
}
public void setGstNo(String gstNo) {
	this.gstNo = gstNo;
}
public String getgstNo() {
	return gstNo;
}
public void setStateCode(String stateCode) {
	this.stateCode = stateCode;
}

private String flag;
   
   
   
   public String getUserid() {
	return userid;
}

public void setUserid(String userid) {
	this.userid = userid;
}

public String getContPerson() {
	return contPerson;
}

public void setContPerson(String contPerson) {
	this.contPerson = contPerson;
}

public String getDepartment() {
	return department;
}

public void setDepartment(String department) {
	this.department = department;
}

public String getQueryDesc() {
	return queryDesc;
}

public void setQueryDesc(String queryDesc) {
	this.queryDesc = queryDesc;
}

private String userid;
   private String contPerson;
   private String department;
   private String queryDesc;
   
   
   //private String flag;
   /**
    * @roseuid 39BA2B5203C3
    */
   public MLIInfo() 
   {
    
   }
  /* 
    public String getFlag() 
   {
      return flag;
   }
   */
 
  /* public void setFlag(String aFlag) 
   {
      flag = aFlag;
   }
   */
   /**
    * Access method for the bankId property.
    * 
    * @return   the current value of the bankId property
    */
   public String getBankId() 
   {
      return bankId;
   }
   
   /**
    * Sets the value of the bankId property.
    * 
    * @param aBankId the new value of the bankId property
    */
   public void setBankId(String aBankId) 
   {
      bankId = aBankId;
   }
   
   /**
    * Access method for the zoneId property.
    * 
    * @return   the current value of the zoneId property
    */
   public String getZoneId() 
   {
      return zoneId;
   }
   
   /**
    * Sets the value of the zoneId property.
    * 
    * @param aZoneId the new value of the zoneId property
    */
   public void setZoneId(String aZoneId) 
   {
      zoneId = aZoneId;
   }
   
   /**
    * Access method for the branchId property.
    * 
    * @return   the current value of the branchId property
    */
   public String getBranchId() 
   {
      return branchId;
   }
   
   /**
    * Sets the value of the branchId property.
    * 
    * @param aBranchId the new value of the branchId property
    */
   public void setBranchId(String aBranchId) 
   {
      branchId = aBranchId;
   }
   
   /**
    * Access method for the collectingBankId property.
    * 
    * @return   the current value of the collectingBankId property
    */
   public String getCollectingBankId() 
   {
      return collectingBankId;
   }
   
   /**
    * Sets the value of the collectingBankId property.
    * 
    * @param aCollectingBankId the new value of the collectingBankId property
    */
   public void setCollectingBankId(String aCollectingBankId) 
   {
      collectingBankId = aCollectingBankId;
   }
   
   /**
    * Access method for the collectingBankName property.
    * 
    * @return   the current value of the collectingBankName property
    */
   public String getCollectingBankName() 
   {
      return collectingBankName;
   }
   
   /**
    * Sets the value of the collectingBankName property.
    * 
    * @param aCollectingBankName the new value of the collectingBankName property
    */
   public void setCollectingBankName(String aCollectingBankName) 
   {
      collectingBankName = aCollectingBankName;
   }
   
   /**
    * Access method for the bankName property.
    * 
    * @return   the current value of the bankName property
    */
   public String getBankName() 
   {
      return bankName;
   }
   
   /**
    * Sets the value of the bankName property.
    * 
    * @param aBankName the new value of the bankName property
    */
   public void setBankName(String aBankName) 
   {
      bankName = aBankName;
   }
   
   /**
    * Access method for the zoneName property.
    * 
    * @return   the current value of the zoneName property
    */
   public String getZoneName() 
   {
      return zoneName;
   }
   
   /**
    * Sets the value of the zoneName property.
    * 
    * @param aZoneName the new value of the zoneName property
    */
   public void setZoneName(String aZoneName) 
   {
      zoneName = aZoneName;
   }
   
   /**
    * Access method for the branchName property.
    * 
    * @return   the current value of the branchName property
    */
   public String getBranchName() 
   {
      return branchName;
   }
   
   /**
    * Sets the value of the branchName property.
    * 
    * @param aBranchName the new value of the branchName property
    */
   public void setBranchName(String aBranchName) 
   {
      branchName = aBranchName;
   }
   
   /**
    * Access method for the address property.
    * 
    * @return   the current value of the address property
    */
   public String getAddress() 
   {
      return address;
   }
   
   /**
    * Sets the value of the address property.
    * 
    * @param aAddress the new value of the address property
    */
   public void setAddress(String aAddress) 
   {
      address = aAddress;
   }
   
   /**
    * Access method for the city property.
    * 
    * @return   the current value of the city property
    */
   public String getCity() 
   {
      return city;
   }
   
   /**
    * Sets the value of the city property.
    * 
    * @param aCity the new value of the city property
    */
   public void setCity(String aCity) 
   {
      city = aCity;
   }
   
   /**
    * Access method for the pin property.
    * 
    * @return   the current value of the pin property
    */
   public String getPin() 
   {
      return pin;
   }
   
   /**
    * Sets the value of the pin property.
    * 
    * @param aPin the new value of the pin property
    */
   public void setPin(String aPin) 
   {
      pin = aPin;
   }
   
   /**
    * Access method for the phone property.
    * 
    * @return   the current value of the phone property
    */
   public String getPhone() 
   {
      return phone;
   }
   
   /**
    * Sets the value of the phone property.
    * 
    * @param aPhone the new value of the phone property
    */
   public void setPhone(String aPhone) 
   {
      phone = aPhone;
   }
   
   /**
    * Access method for the fax property.
    * 
    * @return   the current value of the fax property
    */
   public String getFax() 
   {
      return fax;
   }
   
   /**
    * Sets the value of the fax property.
    * 
    * @param aFax the new value of the fax property
    */
   public void setFax(String aFax) 
   {
      fax = aFax;
   }
   
   /**
    * Access method for the status property.
    * 
    * @return   the current value of the status property
    */
   public String getStatus() 
   {
      return status;
   }
   
   /**
    * Sets the value of the status property.
    * 
    * @param aStatus the new value of the status property
    */
   public void setStatus(String aStatus) 
   {
      status = aStatus;
   }
   
   /**
    * Access method for the email property.
    * 
    * @return   the current value of the email property
    */
   public String getEmail() 
   {
      return email;
   }
   
   /**
    * Sets the value of the email property.
    * 
    * @param aEmail the new value of the email property
    */
   public void setEmail(String aEmail) 
   {
      email = aEmail;
   }
   
   /**
    * Access method for the mcgf property.
    * 
    * @return   the current value of the mcgf property
    */
   public String getMcgf() 
   {
      return mcgf;
   }
   
   /**
    * Sets the value of the mcgf property.
    * 
    * @param aMcgf the new value of the mcgf property
    */
   public void setMcgf(String aMcgf) 
   {
      mcgf = aMcgf;
   }
   
   /**
    * Access method for the bankType property.
    * 
    * @return   the current value of the bankType property
    */
   public Integer getBankType() 
   {
      return bankType;
   }
   
   /**
    * Sets the value of the bankType property.
    * 
    * @param aBankType the new value of the bankType property
    */
   public void setBankType(Integer aBankType) 
   {
      bankType = aBankType;
   }
   
   /**
    * Access method for the state property.
    * 
    * @return   the current value of the state property
    */
   public String getState() 
   {
      return state;
   }
   
   /**
    * Sets the value of the state property.
    * 
    * @param aState the new value of the state property
    */
   public void setState(String aState) 
   {
      state = aState;
   }
   
   /**
    * Access method for the district property.
    * 
    * @return   the current value of the district property
    */
   public String getDistrict() 
   {
      return district;
   }
   
   /**
    * Sets the value of the district property.
    * 
    * @param aDistrict the new value of the district property
    */
   public void setDistrict(String aDistrict) 
   {
      district = aDistrict;
   }
   
   /**
    * Access method for the reportingZoneID property.
    * 
    * @return   the current value of the reportingZoneID property
    */
   public String getReportingZoneID() 
   {
      return reportingZoneID;
   }
   
   /**
    * Sets the value of the reportingZoneID property.
    * 
    * @param aReportingZoneID the new value of the reportingZoneID property
    */
   public void setReportingZoneID(String aReportingZoneID) 
   {
      reportingZoneID = aReportingZoneID;
   }
   
   /**
    * This method is used to return the email id of the member.
    * @return String
    * @roseuid 39BA2B5203AF
    */
   public String getEmailId() 
   {
	    return emailId;
   }
/**
 * @param string
 */
public void setEmailId(String string) {
	emailId = string;
}

/**
 * @return
 */
public String getFaxStdCode() {
	return faxStdCode;
}

/**
 * @return
 */
public String getPhoneStdCode() {
	return phoneStdCode;
}

/**
 * @param string
 */
public void setFaxStdCode(String string) {
	faxStdCode = string;
}

/**
 * @param string
 */
public void setPhoneStdCode(String string) {
	phoneStdCode = string;
}

/**
 * @return
 */
public String getShortName() {
	return shortName;
}

/**
 * @param string
 */
public void setShortName(String string) {
	shortName = string;
}



/**
 * @return
 */
public String getSupportMCGF() {
	return supportMCGF;
}


/**
 * @param string
 */
public void setSupportMCGF(String string) {
	supportMCGF = string;
}

/**
 * @return
 */


/**
 * @return
 */
public String getHardCopy() {
	return hardCopy;
}

/**
 * @return
 */
public String getMail() {
	return mail;
}

/**
 * @param string
 */


/**
 * @param string
 */
public void setHardCopy(String string) {
	hardCopy = string;
}

/**
 * @param string
 */
public void setMail(String string) {
	mail = string;
}

/**
 * @return
 */
public String getReportingZone() {
	return reportingZone;
}

/**
 * @param string
 */
public void setReportingZone(String string) {
	reportingZone = string;
}

/**
 * @return
 */
public String[] getDanDelivery() {
	return danDelivery;
}

/**
 * @param strings
 */
public void setDanDelivery(String[] strings) {
	danDelivery = strings;
}



}
