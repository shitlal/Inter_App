package com.cgtsi.application;

import com.cgtsi.mcgs.MCGFDetails;
import java.io.Serializable;
import java.util.Date;

public class Application implements Serializable
{
  public String getExposurelmtAmt() {
		return exposurelmtAmt;
  }	
  private String mliID;
  private String mliBranchName;
  private String mliBranchCode;
  private String exposurelmtAmt;
  private double UnseqLoanportion=0.0d;
  private double UnLoanPortionExcludCgtCovered=0.0d;
  private String ssiConstitution;
  private String mliRefNo;
  private BorrowerDetails borrowerDetails;
  private ProjectOutlayDetails projectOutlayDetails;
  private String rehabilitation;
  private String compositeLoan;
  private String loanType;
  private String applicationType;
  private String scheme;
  private TermLoan termLoan;
  private WorkingCapital wc;
  private double approvedAmount;
  private double sanctionedAmount;
  private double reapprovedAmount;
  private double enhancementAmount;
  private String docRefNo;
  private String reapprovalRemarks;
  private String cgpan;
  private String cgpanReference;
  private String userId;
  private String bankId;
  private String zoneId;
  private String branchId;
  private String appRefNo;
  private String wcAppRefNo;
  private String regionId;
  private String NPA;
  private String ssiRef;
  private String collateralSecDtls;//CHECK
  private double outstandingAmount;
  private String ITPAN;
  private int subsidyProvided;
  private Date submittedDate;
  private Date sanctionedDate;
  private String activity;
  private Date approvedDate;
  private Date guaranteeStartDate;
  private Date appExpiryDate;
  private String remarks;
  private String prevSSI;
  private String existSSI;
  private String status;
  private int projectType;
  private RepaymentDetail theRepaymentDetail;
  private Securitization securitization;
  private MCGFDetails mcgfDetails;
  private double guaranteeFee;
  private String subSchemeName;
  private String existingRemarks;
  private boolean additionalTC;
  private boolean wcEnhancement;
  private boolean wcRenewal;
  private boolean isVerified;
  private String zoneName;
  private String coFinanceTaken1;
  private String district;
  private String state;
  private String sex;
  private String socialCategory;
  private String internalRate;
  private String externalRate;
  private String handiCrafts;
  private String dcHandicrafts;
  private String icardNo;
  private Date icardIssueDate;
  private String jointFinance;
  private String jointcgpan;
  private String activityConfirm;
  private String handiCraftsStatus;
  private String dcHandicraftsStatus;
  private String dcHandlooms;
  private String dcHandloomsStatus;
  private String WeaverCreditScheme;
  private String handloomchk;
//bhu 17/04/2015
  private String  isPrimarySecurity;
  
  private String handloomSchName;
  private String internalRating;
  private String internalratingProposal;
  private String investmentGrade;
  private String subsidyType;
  private String subsidyOther;
  private String udyogAdharNo;
  private String bankAcNo; 
  private String FBammtlimit; 
  
  
  private String gstNo;
  private String gstState;
  private String stateCode;
  private String gst;
  private String exposureFbId;
  private String exposureFbIdY;
  private String exposureFbIdN;

	private String hybridSecurity;
	private Double movCollateratlSecurityAmt = 0.0d;
	private Double immovCollateratlSecurityAmt = 0.0d;
	private Double totalMIcollatSecAmt = 0.0d;
    private Long proMobileNo;
    

	// add 30 column 
     private String promDirDefaltFlg="";       
     private int credBureKeyPromScor=0;       
     private int credBurePromScor2=0;         
     private int credBurePromScor3=0;         
     private int credBurePromScor4=0;         
     private int credBurePromScor5=0;         
     private String credBureName1="";                 
     private String credBureName2="";          	   
     private String credBureName3="";       		   
     private String credBureName4="";        		   
     private String credBureName5="";       		   
     private int cibilFirmMsmeRank=0;        
     private int expCommerScor=0;             
     private float promBorrNetWorth=0.0f;        
     private int promContribution=0;          
     private String promGAssoNPA1YrFlg;     
     private int promBussExpYr=0;             
     private float salesRevenue=0.0f;             
     private float taxPBIT=0.0f;                  
     private float interestPayment=0.0f;          
     private float taxCurrentProvisionAmt=0.0f;   
     private float totCurrentAssets=0.0f;         
     private float totCurrentLiability=0.0f;      
     private float totTermLiability=0.0f;         
     private float exuityCapital=0.0f;            
     private float preferenceCapital=0.0f;        
     private float reservesSurplus=0.0f;          
     private float repaymentDueNyrAmt=0.0f;       
     private String existGreenFldUnitType="";               				
     private float opratIncome=0.0f;               			
     private float profAftTax=0.0f;                           
     private float networth=0.0f;       
     private float debitEqtRatioUnt=0.0f;
     private float debitSrvCoverageRatioTl=0.0f;
     private float currentRatioWc=0.0f;    					
     private float debitEqtRatio=0.0f;
     private float debitSrvCoverageRatio=0.0f;		
     private float currentRatios=0.0f;	   					
     private int creditBureauChiefPromScor=0;			
     private float totalAssets=0.0f;	
     
     
     private String equivStandaredRating="";          
     private String ivConfirmInvestGrad="";          
     private String restructConfirmation="";   // DKR 2021 ENHANCEMENT
     private Double  wcEnhanceLimitSanctioned=0.0d;
     private Double  wcEnhanceLimitGauranteeAmt=0.0d;
   
         
     public Double getWcEnhanceLimitSanctioned() {
		return wcEnhanceLimitSanctioned;
	}

	public void setWcEnhanceLimitSanctioned(Double wcEnhanceLimitSanctioned) {
		this.wcEnhanceLimitSanctioned = wcEnhanceLimitSanctioned;
	}

	public Double getWcEnhanceLimitGauranteeAmt() {
		return wcEnhanceLimitGauranteeAmt;
	}

	public void setWcEnhanceLimitGauranteeAmt(Double wcEnhanceLimitGauranteeAmt) {
		this.wcEnhanceLimitGauranteeAmt = wcEnhanceLimitGauranteeAmt;
	}

	public String getEquivStandaredRating() {
		return equivStandaredRating;
	}

	public void setEquivStandaredRating(String equivStandaredRating) {
		this.equivStandaredRating = equivStandaredRating;
	}

	public String getIvConfirmInvestGrad() {
		return ivConfirmInvestGrad;
	}

	public void setIvConfirmInvestGrad(String ivConfirmInvestGrad) {
		this.ivConfirmInvestGrad = ivConfirmInvestGrad;
	}
	private Date outstandingDate;     
		public Date getOutstandingDate() {
		return outstandingDate;
	}

	public void setOutstandingDate(Date outstandingDate) {
		this.outstandingDate = outstandingDate;
	}

	public String getSsiConstitution() {
		return ssiConstitution;
	}

	public void setSsiConstitution(String ssiConstitution) {
		this.ssiConstitution = ssiConstitution;
	}

	public void setExposurelmtAmt(String exposurelmtAmt) {
		this.exposurelmtAmt = exposurelmtAmt;
	} 

    public float getDebitEqtRatioUnt() {
		return debitEqtRatioUnt;
	}

	public void setDebitEqtRatioUnt(float debitEqtRatioUnt) {
		this.debitEqtRatioUnt = debitEqtRatioUnt;
	}

	public float getDebitSrvCoverageRatioTl() {
		return debitSrvCoverageRatioTl;
	}

	public void setDebitSrvCoverageRatioTl(float debitSrvCoverageRatioTl) {
		this.debitSrvCoverageRatioTl = debitSrvCoverageRatioTl;
	}

	public float getCurrentRatioWc() {
		return currentRatioWc;
	}

	public void setCurrentRatioWc(float currentRatioWc) {
		this.currentRatioWc = currentRatioWc;
	}

	public float getDebitEqtRatio() {
		return debitEqtRatio;
	}

	public void setDebitEqtRatio(float debitEqtRatio) {
		this.debitEqtRatio = debitEqtRatio;
	}

	public float getDebitSrvCoverageRatio() {
		return debitSrvCoverageRatio;
	}

	public void setDebitSrvCoverageRatio(float debitSrvCoverageRatio) {
		this.debitSrvCoverageRatio = debitSrvCoverageRatio;
	}

	public float getCurrentRatios() {
		return currentRatios;
	}

	public void setCurrentRatios(float currentRatios) {
		this.currentRatios = currentRatios;
	}

	public String getExistGreenFldUnitType() {
		return existGreenFldUnitType;
	}

	public void setExistGreenFldUnitType(String existGreenFldUnitType) {
		this.existGreenFldUnitType = existGreenFldUnitType;
	}

	
	public double getUnseqLoanportion() {
		return UnseqLoanportion;
	}

	public void setUnseqLoanportion(double unseqLoanportion) {
		UnseqLoanportion = unseqLoanportion;
	}
		/*public String getExistUnit() {
		return existUnit;
	}

	public void setExistUnit(String existUnit) {
		this.existUnit = existUnit;
	}

	public String getGreenUnit() {
		return GreenUnit;
	}

	public void setGreenUnit(String greenUnit) {
		GreenUnit = greenUnit;
	}*/

	public double getUnLoanPortionExcludCgtCovered() {
		return UnLoanPortionExcludCgtCovered;
	}

	public void setUnLoanPortionExcludCgtCovered(
			double unLoanPortionExcludCgtCovered) {
		UnLoanPortionExcludCgtCovered = unLoanPortionExcludCgtCovered;
	}

	public float getOpratIncome() {
		return opratIncome;
	}

	public void setOpratIncome(float opratIncome) {
		this.opratIncome = opratIncome;
	}

	public float getProfAftTax() {
		return profAftTax;
	}

	public void setProfAftTax(float profAftTax) {
		this.profAftTax = profAftTax;
	}

	public float getNetworth() {
		return networth;
	}

	public void setNetworth(float networth) {
		this.networth = networth;
	}

	

	public void setDebitSrvCoverageRatio(int debitSrvCoverageRatio) {
		this.debitSrvCoverageRatio = debitSrvCoverageRatio;
	}

	

	public void setDebitSrvCoverageRatioTl(int debitSrvCoverageRatioTl) {
		this.debitSrvCoverageRatioTl = debitSrvCoverageRatioTl;
	}

	

	public void setCurrentRatios(int currentRatios) {
		this.currentRatios = currentRatios;
	}



	public void setCurrentRatioWc(int currentRatioWc) {
		this.currentRatioWc = currentRatioWc;
	}

	public int getCreditBureauChiefPromScor() {
		return creditBureauChiefPromScor;
	}

	public void setCreditBureauChiefPromScor(int creditBureauChiefPromScor) {
		this.creditBureauChiefPromScor = creditBureauChiefPromScor;
	}

	public float getTotalAssets() {
		return totalAssets;
	}

	public void setTotalAssets(float totalAssets) {
		this.totalAssets = totalAssets;
	}

	public MCGFDetails getMcgfDetails() {
		return mcgfDetails;
	}

	public void setMcgfDetails(MCGFDetails mcgfDetails) {
		this.mcgfDetails = mcgfDetails;
	}

	public double getGuaranteeFee() {
		return guaranteeFee;
	}

	public void setGuaranteeFee(double guaranteeFee) {
		this.guaranteeFee = guaranteeFee;
	}

	public String getFBammtlimit() {
		return FBammtlimit;
	}

	public void setFBammtlimit(String fBammtlimit) {
		FBammtlimit = fBammtlimit;
	}

	public String getPromDirDefaltFlg() {
		return promDirDefaltFlg;
	}

	public void setPromDirDefaltFlg(String promDirDefaltFlg) {
		this.promDirDefaltFlg = promDirDefaltFlg;
	}

	public int getCredBureKeyPromScor() {
		return credBureKeyPromScor;
	}

	public void setCredBureKeyPromScor(int credBureKeyPromScor) {
		this.credBureKeyPromScor = credBureKeyPromScor;
	}

	public int getCredBurePromScor2() {
		return credBurePromScor2;
	}

	public void setCredBurePromScor2(int credBurePromScor2) {
		this.credBurePromScor2 = credBurePromScor2;
	}

	public int getCredBurePromScor3() {
		return credBurePromScor3;
	}

	public void setCredBurePromScor3(int credBurePromScor3) {
		this.credBurePromScor3 = credBurePromScor3;
	}

	public int getCredBurePromScor4() {
		return credBurePromScor4;
	}

	public void setCredBurePromScor4(int credBurePromScor4) {
		this.credBurePromScor4 = credBurePromScor4;
	}

	public int getCredBurePromScor5() {
		return credBurePromScor5;
	}

	public void setCredBurePromScor5(int credBurePromScor5) {
		this.credBurePromScor5 = credBurePromScor5;
	}

	public String getCredBureName1() {
		return credBureName1;
	}

	public void setCredBureName1(String credBureName1) {
		this.credBureName1 = credBureName1;
	}

	public String getCredBureName2() {
		return credBureName2;
	}

	public void setCredBureName2(String credBureName2) {
		this.credBureName2 = credBureName2;
	}

	public String getCredBureName3() {
		return credBureName3;
	}

	public void setCredBureName3(String credBureName3) {
		this.credBureName3 = credBureName3;
	}

	public String getCredBureName4() {
		return credBureName4;
	}

	public void setCredBureName4(String credBureName4) {
		this.credBureName4 = credBureName4;
	}

	public String getCredBureName5() {
		return credBureName5;
	}

	public void setCredBureName5(String credBureName5) {
		this.credBureName5 = credBureName5;
	}

	public int getCibilFirmMsmeRank() {
		return cibilFirmMsmeRank;
	}

	public void setCibilFirmMsmeRank(int cibilFirmMsmeRank) {
		this.cibilFirmMsmeRank = cibilFirmMsmeRank;
	}

	public int getExpCommerScor() {
		return expCommerScor;
	}

	public void setExpCommerScor(int expCommerScor) {
		this.expCommerScor = expCommerScor;
	}

	public float getPromBorrNetWorth() {
		return promBorrNetWorth;
	}

	public void setPromBorrNetWorth(float promBorrNetWorth) {
		this.promBorrNetWorth = promBorrNetWorth;
	}

	public int getPromContribution() {
		return promContribution;
	}

	public void setPromContribution(int promContribution) {
		this.promContribution = promContribution;
	}

	public String getPromGAssoNPA1YrFlg() {
		return promGAssoNPA1YrFlg;
	}

	public void setPromGAssoNPA1YrFlg(String promGAssoNPA1YrFlg) {
		this.promGAssoNPA1YrFlg = promGAssoNPA1YrFlg;
	}

	public int getPromBussExpYr() {
		return promBussExpYr;
	}

	public void setPromBussExpYr(int promBussExpYr) {
		this.promBussExpYr = promBussExpYr;
	}

	public float getSalesRevenue() {
		return salesRevenue;
	}

	public void setSalesRevenue(float salesRevenue) {
		this.salesRevenue = salesRevenue;
	}

	public float getTaxPBIT() {
		return taxPBIT;
	}

	public void setTaxPBIT(float taxPBIT) {
		this.taxPBIT = taxPBIT;
	}

	public float getInterestPayment() {
		return interestPayment;
	}

	public void setInterestPayment(float interestPayment) {
		this.interestPayment = interestPayment;
	}

	public float getTaxCurrentProvisionAmt() {
		return taxCurrentProvisionAmt;
	}

	public void setTaxCurrentProvisionAmt(float taxCurrentProvisionAmt) {
		this.taxCurrentProvisionAmt = taxCurrentProvisionAmt;
	}

	public float getTotCurrentAssets() {
		return totCurrentAssets;
	}

	public void setTotCurrentAssets(float totCurrentAssets) {
		this.totCurrentAssets = totCurrentAssets;
	}

	public float getTotCurrentLiability() {
		return totCurrentLiability;
	}

	public void setTotCurrentLiability(float totCurrentLiability) {
		this.totCurrentLiability = totCurrentLiability;
	}

	public float getTotTermLiability() {
		return totTermLiability;
	}

	public void setTotTermLiability(float totTermLiability) {
		this.totTermLiability = totTermLiability;
	}

	public float getExuityCapital() {
		return exuityCapital;
	}

	public void setExuityCapital(float exuityCapital) {
		this.exuityCapital = exuityCapital;
	}

	public float getPreferenceCapital() {
		return preferenceCapital;
	}

	public void setPreferenceCapital(float preferenceCapital) {
		this.preferenceCapital = preferenceCapital;
	}

	public float getReservesSurplus() {
		return reservesSurplus;
	}

	public void setReservesSurplus(float reservesSurplus) {
		this.reservesSurplus = reservesSurplus;
	}

	public float getRepaymentDueNyrAmt() {
		return repaymentDueNyrAmt;
	}

	public void setRepaymentDueNyrAmt(float repaymentDueNyrAmt) {
		this.repaymentDueNyrAmt = repaymentDueNyrAmt;
	}

	public void setVerified(boolean isVerified) {
		this.isVerified = isVerified;
	}
  
    
		public Long getProMobileNo() {
		return proMobileNo;
	}

	public void setProMobileNo(Long proMobileNo) {
		this.proMobileNo = proMobileNo;
	}

	public String getHybridSecurity() {
		return hybridSecurity;
	}

	public void setHybridSecurity(String hybridSecurity) {
		this.hybridSecurity = hybridSecurity;
	}

	public Double getMovCollateratlSecurityAmt() {
		return movCollateratlSecurityAmt;
	}

	public void setMovCollateratlSecurityAmt(Double movCollateratlSecurityAmt) {
		this.movCollateratlSecurityAmt = movCollateratlSecurityAmt;
	}

	public Double getImmovCollateratlSecurityAmt() {
		return immovCollateratlSecurityAmt;
	}

	public void setImmovCollateratlSecurityAmt(Double immovCollateratlSecurityAmt) {
		this.immovCollateratlSecurityAmt = immovCollateratlSecurityAmt;
	}

	public Double getTotalMIcollatSecAmt() {
		return totalMIcollatSecAmt;
	}

	public void setTotalMIcollatSecAmt(Double totalMIcollatSecAmt) {
		this.totalMIcollatSecAmt = totalMIcollatSecAmt;
	}

	

  public String getExposureFbIdY() {
	return exposureFbIdY;
}

public void setExposureFbIdY(String exposureFbIdY) {
	this.exposureFbIdY = exposureFbIdY;
}

public String getExposureFbIdN() {
	return exposureFbIdN;
}

public void setExposureFbIdN(String exposureFbIdN) {
	this.exposureFbIdN = exposureFbIdN;
}

public String getExposureFbId() {
	return exposureFbId;
}

public void setExposureFbId(String exposureFbId) {
	this.exposureFbId = exposureFbId;
}

public String getItpan() {
	return itpan;
}

public void setItpan(String itpan) {
	this.itpan = itpan;
}

public String getSsiUnitName() {
	return ssiUnitName;
}

public void setSsiUnitName(String ssiUnitName) {
	this.ssiUnitName = ssiUnitName;
}

public String getGurAmt() {
	return gurAmt;
}

public void setGurAmt(String gurAmt) {
	this.gurAmt = gurAmt;
}

public String getNpaDate() {
	return npaDate;
}

public void setNpaDate(String npaDate) {
	this.npaDate = npaDate;
}

private String itpan;
  private String ssiUnitName;
  private String gurAmt;
  private String npaDate;
  
  
private String adharNo;//raju
 

public String getAdharNo() {
	return adharNo;
}

public void setAdharNo(String adharNo) {
	this.adharNo = adharNo;
}



public String getGst() {
	return gst;
}

public void setGst(String gst) {
	this.gst = gst;
}

public String getStateCode() {
	return stateCode;
}

public void setStateCode(String stateCode) {
	this.stateCode = stateCode;
}

public String getGstNo() {
	return gstNo;
}

public void setGstNo(String gstNo) {
	this.gstNo = gstNo;
}

public String getGstState() {
	return gstState;
}

public void setGstState(String gstState) {
	this.gstState = gstState;
}

public String getSubsidyType() {
	return subsidyType;
}

public void setSubsidyType(String subsidyType) {
	this.subsidyType = subsidyType;
}

public String getSubsidyOther() {
	return subsidyOther;
}

public void setSubsidyOther(String subsidyOther) {
	this.subsidyOther = subsidyOther;
}

public String getHandloomSchName() {
	return handloomSchName;
}

public void setHandloomSchName(String handloomSchName) {
	this.handloomSchName = handloomSchName;
}

public String getInternalRating() {
	return internalRating;
}

public void setInternalRating(String internalRating) {
	this.internalRating = internalRating;
}

public String getInternalratingProposal() {
	return internalratingProposal;
}

public void setInternalratingProposal(String internalratingProposal) {
	this.internalratingProposal = internalratingProposal;
}

public String getInvestmentGrade() {
	return investmentGrade;
}

public void setInvestmentGrade(String investmentGrade) {
	this.investmentGrade = investmentGrade;
}

public String getIsPrimarySecurity() {
	return isPrimarySecurity;
}

public void setIsPrimarySecurity(String isPrimarySecurity) {
	this.isPrimarySecurity = isPrimarySecurity;
}

public void setSsiRef(String ssiRef)
  {
    this.ssiRef = ssiRef;
  }

  public String getSsiRef()
  {
    return this.ssiRef;
  }

  public void setActivity(String act)
  {
    this.activity = act;
  }

  public String getActivity()
  {
    return this.activity;
  }

  public void setZoneName(String name)
  {
    this.zoneName = name;
  }

  public String getZoneName()
  {
    return this.zoneName;
  }

  public void setCoFinanceTaken1(String coFinanceTaken1)
  {
    this.coFinanceTaken1 = coFinanceTaken1;
  }

  public String getCoFinanceTaken1()
  {
    return this.coFinanceTaken1;
  }

  public void setIcardIssueDate(Date icardIssueDate)
  {
    this.icardIssueDate = icardIssueDate;
  }

  public Date getIcardIssueDate()
  {
    return this.icardIssueDate;
  }

  public void setIcardNo(String icardNo)
  {
    this.icardNo = icardNo;
  }

  public String getIcardNo()
  {
    return this.icardNo;
  }

  public void setHandiCrafts(String handiCrafts)
  {
    this.handiCrafts = handiCrafts;
  }

  public String getHandiCrafts()
  {
    return this.handiCrafts;
  }

  public void setJointFinance(String jointFinance)
  {
    this.jointFinance = jointFinance;
  }

  public String getJointFinance()
  {
    return this.jointFinance;
  }

  public void setDcHandicrafts(String dcHandicrafts)
  {
    this.dcHandicrafts = dcHandicrafts;
  }

  public String getDcHandicrafts()
  {
    return this.dcHandicrafts;
  }

  public void setInternalRate(String internalRate)
  {
    this.internalRate = internalRate;
  }

  public String getInternalRate()
  {
    return this.internalRate;
  }

  public void setExternalRate(String externalRate)
  {
    this.externalRate = externalRate;
  }

  public String getExternalRate()
  {
    return this.externalRate;
  }

  public void setDistrict(String district)
  {
    this.district = district;
  }

  public String getDistrict()
  {
    return this.district;
  }

  public void setState(String state)
  {
    this.state = state;
  }

  public String getState()
  {
    return this.state;
  }

  public void setSex(String sex)
  {
    this.sex = sex;
  }

  public String getSex()
  {
    return this.sex;
  }

  public void setSocialCategory(String socialCategory)
  {
    this.socialCategory = socialCategory;
  }

  public String getSocialCategory()
  {
    return this.socialCategory;
  }
  

  public Application()
  {
    this.borrowerDetails = null;
    this.projectOutlayDetails = null;
    this.loanType = null;
    this.applicationType = null;
    this.termLoan = null;
    this.wc = null;
    this.approvedAmount = 0.0D;
    this.sanctionedAmount = 0.0D;
    this.reapprovedAmount = 0.0D;
    this.enhancementAmount = 0.0D;
    this.docRefNo = null;
    this.reapprovalRemarks = null;
    this.cgpan = null;
    this.cgpanReference = null;
    this.userId = null;
    this.bankId = null;
    this.zoneId = null;
    this.branchId = null;
    this.appRefNo = null;
    this.wcAppRefNo = null;
    this.regionId = null;
    this.collateralSecDtls = null;
    this.subsidyProvided = 0;
    this.submittedDate = null;
    this.sanctionedDate = null;
    this.approvedDate = null;
    this.guaranteeStartDate = null;
    this.appExpiryDate = null;
    this.remarks = null;
    this.prevSSI = null;
    this.existSSI = null;
    this.status = null;
    this.projectType = 0;
    this.securitization = null;
    this.mcgfDetails = null;
    this.guaranteeFee = 0.0D;
    this.coFinanceTaken1 = "N";
    this.district = "";
    this.state = "";
    this.sex = "";
    this.socialCategory = "";
    this.internalRate = null;
    this.externalRate = null;
    this.handiCrafts = null;
    this.dcHandicrafts = null;
    this.icardNo = null;
    this.icardIssueDate = null;
    this.jointFinance = null;
    this.jointcgpan = null;
    this.activityConfirm = null;
  //  this.isPrimarySecurity = null;
  }

  public String getMliID()
  {
    return this.mliID;
  }

  public void setMliID(String aMliID)
  {
    this.mliID = aMliID;
  }

  public String getMliBranchName()
  {
    return this.mliBranchName;
  }

  public void setMliBranchName(String aMliBranchName)
  {
    this.mliBranchName = aMliBranchName;
  }

  public BorrowerDetails getBorrowerDetails()
  {
    return this.borrowerDetails;
  }

  public void setBorrowerDetails(BorrowerDetails aBorrowerDetails)
  {
    this.borrowerDetails = aBorrowerDetails;
  }

  public TermLoan getTermLoan()
  {
    return this.termLoan;
  }

  public void setTermLoan(TermLoan aTermLoan)
  {
    this.termLoan = aTermLoan;
  }

  public WorkingCapital getWc()
  {
    return this.wc;
  }

  public void setWc(WorkingCapital aWc)
  {
    this.wc = aWc;
  }

  public String getLoanType()
  {
    return this.loanType;
  }

  public void setLoanType(String aLoanType)
  {
    this.loanType = aLoanType;
  }

  public String getCgpan()
  {
    return this.cgpan;
  }

  public void setCgpan(String aCgpan)
  {
    this.cgpan = aCgpan;
  }

  public String getUserId()
  {
    return this.userId;
  }

  public void setUserId(String aUserId)
  {
    this.userId = aUserId;
  }

  public String getBankId()
  {
    return this.bankId;
  }

  public void setBankId(String aBankId)
  {
    this.bankId = aBankId;
  }

  public String getZoneId()
  {
    return this.zoneId;
  }

  public void setZoneId(String aZoneId)
  {
    this.zoneId = aZoneId;
  }

  public String getBranchId()
  {
    return this.branchId;
  }

  public void setBranchId(String aBranchId)
  {
    this.branchId = aBranchId;
  }

  public String getAppRefNo()
  {
    return this.appRefNo;
  }

  public void setAppRefNo(String aAppRefNo)
  {
    this.appRefNo = aAppRefNo;
  }

  public String getWcAppRefNo()
  {
    return this.wcAppRefNo;
  }

  public void setWcAppRefNo(String aWcAppRefNo)
  {
    this.wcAppRefNo = aWcAppRefNo;
  }

  public String getNPA()
  {
    return this.NPA;
  }

  public void setNPA(String aNPA)
  {
    this.NPA = aNPA;
  }

  public String getCompositeLoan()
  {
    return this.compositeLoan;
  }

  public void setCompositeLoan(String aCompositeLoan)
  {
    this.compositeLoan = aCompositeLoan;
  }

  public String getCollateralSecDtls()
  {
    return this.collateralSecDtls;
  }

  public void setCollateralSecDtls(String aCollateralSecDtls)
  {
    this.collateralSecDtls = aCollateralSecDtls;
  }

  public int getSubsidyProvided()
  {
    return this.subsidyProvided;
  }

  public void setSubsidyProvided(int aSubsidyProvided)
  {
    this.subsidyProvided = aSubsidyProvided;
  }

  public Date getSubmittedDate()
  {
    return this.submittedDate;
  }

  public void setSubmittedDate(Date aSubmittedDate)
  {
    this.submittedDate = aSubmittedDate;
  }

  public Date getSanctionedDate()
  {
    return this.sanctionedDate;
  }

  public void setSanctionedDate(Date aSanctionedDate)
  {
    this.sanctionedDate = aSanctionedDate;
  }

  public String getRehabilitation()
  {
    return this.rehabilitation;
  }

  public void setRehabilitation(String aRehabilitation)
  {
    this.rehabilitation = aRehabilitation;
  }

  public Date getApprovedDate()
  {
    return this.approvedDate;
  }

  public void setApprovedDate(Date aApprovedDate)
  {
    this.approvedDate = aApprovedDate;
  }

  public Date getGuaranteeStartDate()
  {
    return this.guaranteeStartDate;
  }

  public Date getAppExpiryDate()
  {
    return this.appExpiryDate;
  }

  public void setAppExpiryDate(Date bappExpiryDate)
  {
    this.appExpiryDate = bappExpiryDate;
  }

  public void setGuaranteeStartDate(Date aGuaranteeStartDate)
  {
    this.guaranteeStartDate = aGuaranteeStartDate;
  }

  public String getRemarks()
  {
    return this.remarks;
  }

  public void setRemarks(String aRemarks)
  {
    this.remarks = aRemarks;
  }

  public String getStatus()
  {
    return this.status;
  }

  public void setStatus(String aStatus)
  {
    this.status = aStatus;
  }

  public int getProjectType()
  {
    return this.projectType;
  }

  public void setProjectType(int aProjectType)
  {
    this.projectType = aProjectType;
  }

  public double getOutstandingAmount()
  {
    return this.outstandingAmount;
  }

  public void setOutstandingAmount(double aOutstandingAmount)
  {
    this.outstandingAmount = aOutstandingAmount;
  }

  public double getApprovedAmount()
  {
    return this.approvedAmount;
  }

  public void setApprovedAmount(double aApprovedAmount)
  {
    this.approvedAmount = aApprovedAmount;
  }

  public double getSantionedAmount()
  {
    return this.sanctionedAmount;
  }

  public void setSanctionedAmount(double aSanctionedAmount)
  {
    this.sanctionedAmount = aSanctionedAmount;
  }

  public String getITPAN()
  {
    return this.ITPAN;
  }

  public void setITPAN(String aITPAN)
  {
    this.ITPAN = aITPAN;
  }

  public double getEnhancementAmount()
  {
    return this.enhancementAmount;
  }

  public void setEnhancementAmount(double aEnhancementAmount)
  {
    this.enhancementAmount = aEnhancementAmount;
  }

  public double getReapprovedAmount()
  {
    return this.reapprovedAmount;
  }

  public void setReapprovedAmount(double aReapprovedAmount)
  {
    this.reapprovedAmount = aReapprovedAmount;
  }

  public String getDocRefNo()
  {
    return this.docRefNo;
  }

  public void setDocRefNo(String aDocRefNo)
  {
    this.docRefNo = aDocRefNo;
  }

  public String getReapprovalRemarks()
  {
    return this.reapprovalRemarks;
  }

  public void setReapprovalRemarks(String aReapprovalRemarks)
  {
    this.reapprovalRemarks = aReapprovalRemarks;
  }

  public ProjectOutlayDetails getProjectOutlayDetails()
  {
    return this.projectOutlayDetails;
  }

  public double getSanctionedAmount()
  {
    return this.sanctionedAmount;
  }

  public String getScheme()
  {
    return this.scheme;
  }

  public RepaymentDetail getTheRepaymentDetail()
  {
    return this.theRepaymentDetail;
  }

  public void setProjectOutlayDetails(ProjectOutlayDetails details)
  {
    this.projectOutlayDetails = details;
  }

  public void setScheme(String string)
  {
    this.scheme = string;
  }

  public void setTheRepaymentDetail(RepaymentDetail detail)
  {
    this.theRepaymentDetail = detail;
  }

  public String getRegionId()
  {
    return this.regionId;
  }

  public void setRegionId(String aRegionId)
  {
    this.regionId = aRegionId;
  }

  public String getMliBranchCode()
  {
    return this.mliBranchCode;
  }

  public void setMliBranchCode(String aMliBranchCode)
  {
    this.mliBranchCode = aMliBranchCode;
  }

  public void setGuaranteeAmount(double guaranteeFee)
  {
    this.guaranteeFee = guaranteeFee;
  }

  public double getGuaranteeAmount()
  {
    return this.guaranteeFee;
  }

  public String getMliRefNo()
  {
    return this.mliRefNo;
  }

  public void setMliRefNo(String aMliRefNo)
  {
    this.mliRefNo = aMliRefNo;
  }

  public Securitization getSecuritization()
  {
    return this.securitization;
  }

  public void setSecuritization(Securitization aSecuritization)
  {
    this.securitization = aSecuritization;
  }

  public MCGFDetails getMCGFDetails()
  {
    return this.mcgfDetails;
  }

  public void setMCGFDetails(MCGFDetails aMCGFDetails)
  {
    this.mcgfDetails = aMCGFDetails;
  }

  public String getSubSchemeName()
  {
    return this.subSchemeName;
  }

  public void setSubSchemeName(String aSubSchemeName)
  {
    this.subSchemeName = aSubSchemeName;
  }

  public String getExistingRemarks()
  {
    return this.existingRemarks;
  }

  public void setExistingRemarks(String aExistingRemarks)
  {
    this.existingRemarks = aExistingRemarks;
  }

  public boolean getAdditionalTC()
  {
    return this.additionalTC;
  }

  public void setAdditionalTC(boolean aAdditionalTC)
  {
    this.additionalTC = aAdditionalTC;
  }

  public boolean getWcEnhancement()
  {
    return this.wcEnhancement;
  }

  public void setWcEnhancement(boolean aWcEnhancement)
  {
    this.wcEnhancement = aWcEnhancement;
  }

  public boolean getWcRenewal()
  {
    return this.wcRenewal;
  }

  public void setWcRenewal(boolean aWcRenewal)
  {
    this.wcRenewal = aWcRenewal;
  }

  public String getCgpanReference()
  {
    return this.cgpanReference;
  }

  public void setCgpanReference(String aCgpanReference)
  {
    this.cgpanReference = aCgpanReference;
  }

  public boolean getIsVerified()
  {
    return this.isVerified;
  }

  public void setIsVerified(boolean b)
  {
    this.isVerified = b;
  }

  public void setJointcgpan(String jointcgpan)
  {
    this.jointcgpan = jointcgpan;
  }

  public String getJointcgpan()
  {
    return this.jointcgpan;
  }

  public void setApplicationType(String applicationType)
  {
    this.applicationType = applicationType;
  }

  public String getApplicationType()
  {
    return this.applicationType;
  }

  public void setActivityConfirm(String activityConfirm)
  {
    this.activityConfirm = activityConfirm;
  }

  public String getActivityConfirm()
  {
    return this.activityConfirm;
  }

  public void setPrevSSI(String prevSSI)
  {
    this.prevSSI = prevSSI;
  }

  public String getPrevSSI()
  {
    return this.prevSSI;
  }

  public void setExistSSI(String existSSI)
  {
    this.existSSI = existSSI;
  }

  public String getExistSSI()
  {
    return this.existSSI;
  }

  public void setHandiCraftsStatus(String handiCraftsStatus)
  {
    this.handiCraftsStatus = handiCraftsStatus;
  }

  public String getHandiCraftsStatus()
  {
    return this.handiCraftsStatus;
  }

  public void setDcHandicraftsStatus(String dcHandicraftsStatus) {
    this.dcHandicraftsStatus = dcHandicraftsStatus;
  }

  public String getDcHandicraftsStatus()
  {
    return this.dcHandicraftsStatus;
  }

  public void setDcHandlooms(String dcHandlooms) {
    this.dcHandlooms = dcHandlooms;
  }

  public String getDcHandlooms()
  {
    return this.dcHandlooms;
  }

  public void setDcHandloomsStatus(String dcHandloomsStatus) {
    this.dcHandloomsStatus = dcHandloomsStatus;
  }

  public String getDcHandloomsStatus()
  {
    return this.dcHandloomsStatus;
  }

  public void setWeaverCreditScheme(String weaverCreditScheme) {
    this.WeaverCreditScheme = weaverCreditScheme;
  }

  public String getWeaverCreditScheme()
  {
    return this.WeaverCreditScheme;
  }

  public void setHandloomchk(String handloomchk) {
    this.handloomchk = handloomchk;
  }

  public String getHandloomchk()
  {
    return this.handloomchk;
  }
  public String getUdyogAdharNo() {
		return udyogAdharNo;
	}

	public void setUdyogAdharNo(String udyogAdharNo) {
		this.udyogAdharNo = udyogAdharNo;
	}

	public String getBankAcNo() {
		return bankAcNo;
	}

	public void setBankAcNo(String bankAcNo) {
		this.bankAcNo = bankAcNo;
	}

	public String getRestructConfirmation() {
		return restructConfirmation;
	}

	public void setRestructConfirmation(String restructConfirmation) {
		this.restructConfirmation = restructConfirmation;
	}

	@Override
	public String toString() {
		return "Application [mliID=" + mliID + ", mliBranchName=" + mliBranchName + ", mliBranchCode=" + mliBranchCode
				+ ", exposurelmtAmt=" + exposurelmtAmt + ", UnseqLoanportion=" + UnseqLoanportion
				+ ", UnLoanPortionExcludCgtCovered=" + UnLoanPortionExcludCgtCovered + ", ssiConstitution="
				+ ssiConstitution + ", mliRefNo=" + mliRefNo + ", borrowerDetails=" + borrowerDetails
				+ ", projectOutlayDetails=" + projectOutlayDetails + ", rehabilitation=" + rehabilitation
				+ ", compositeLoan=" + compositeLoan + ", loanType=" + loanType + ", applicationType=" + applicationType
				+ ", scheme=" + scheme + ", termLoan=" + termLoan + ", wc=" + wc + ", approvedAmount=" + approvedAmount
				+ ", sanctionedAmount=" + sanctionedAmount + ", reapprovedAmount=" + reapprovedAmount
				+ ", enhancementAmount=" + enhancementAmount + ", docRefNo=" + docRefNo + ", reapprovalRemarks="
				+ reapprovalRemarks + ", cgpan=" + cgpan + ", cgpanReference=" + cgpanReference + ", userId=" + userId
				+ ", bankId=" + bankId + ", zoneId=" + zoneId + ", branchId=" + branchId + ", appRefNo=" + appRefNo
				+ ", wcAppRefNo=" + wcAppRefNo + ", regionId=" + regionId + ", NPA=" + NPA + ", ssiRef=" + ssiRef
				+ ", collateralSecDtls=" + collateralSecDtls + ", outstandingAmount=" + outstandingAmount + ", ITPAN="
				+ ITPAN + ", subsidyProvided=" + subsidyProvided + ", submittedDate=" + submittedDate
				+ ", sanctionedDate=" + sanctionedDate + ", activity=" + activity + ", approvedDate=" + approvedDate
				+ ", guaranteeStartDate=" + guaranteeStartDate + ", appExpiryDate=" + appExpiryDate + ", remarks="
				+ remarks + ", prevSSI=" + prevSSI + ", existSSI=" + existSSI + ", status=" + status + ", projectType="
				+ projectType + ", theRepaymentDetail=" + theRepaymentDetail + ", securitization=" + securitization
				+ ", mcgfDetails=" + mcgfDetails + ", guaranteeFee=" + guaranteeFee + ", subSchemeName=" + subSchemeName
				+ ", existingRemarks=" + existingRemarks + ", additionalTC=" + additionalTC + ", wcEnhancement="
				+ wcEnhancement + ", wcRenewal=" + wcRenewal + ", isVerified=" + isVerified + ", zoneName=" + zoneName
				+ ", coFinanceTaken1=" + coFinanceTaken1 + ", district=" + district + ", state=" + state + ", sex="
				+ sex + ", socialCategory=" + socialCategory + ", internalRate=" + internalRate + ", externalRate="
				+ externalRate + ", handiCrafts=" + handiCrafts + ", dcHandicrafts=" + dcHandicrafts + ", icardNo="
				+ icardNo + ", icardIssueDate=" + icardIssueDate + ", jointFinance=" + jointFinance + ", jointcgpan="
				+ jointcgpan + ", activityConfirm=" + activityConfirm + ", handiCraftsStatus=" + handiCraftsStatus
				+ ", dcHandicraftsStatus=" + dcHandicraftsStatus + ", dcHandlooms=" + dcHandlooms
				+ ", dcHandloomsStatus=" + dcHandloomsStatus + ", WeaverCreditScheme=" + WeaverCreditScheme
				+ ", handloomchk=" + handloomchk + ", isPrimarySecurity=" + isPrimarySecurity + ", handloomSchName="
				+ handloomSchName + ", internalRating=" + internalRating + ", internalratingProposal="
				+ internalratingProposal + ", investmentGrade=" + investmentGrade + ", subsidyType=" + subsidyType
				+ ", subsidyOther=" + subsidyOther + ", udyogAdharNo=" + udyogAdharNo + ", bankAcNo=" + bankAcNo
				+ ", FBammtlimit=" + FBammtlimit + ", gstNo=" + gstNo + ", gstState=" + gstState + ", stateCode="
				+ stateCode + ", gst=" + gst + ", exposureFbId=" + exposureFbId + ", exposureFbIdY=" + exposureFbIdY
				+ ", exposureFbIdN=" + exposureFbIdN + ", hybridSecurity=" + hybridSecurity
				+ ", movCollateratlSecurityAmt=" + movCollateratlSecurityAmt + ", immovCollateratlSecurityAmt="
				+ immovCollateratlSecurityAmt + ", totalMIcollatSecAmt=" + totalMIcollatSecAmt + ", proMobileNo="
				+ proMobileNo + ", promDirDefaltFlg=" + promDirDefaltFlg + ", credBureKeyPromScor="
				+ credBureKeyPromScor + ", credBurePromScor2=" + credBurePromScor2 + ", credBurePromScor3="
				+ credBurePromScor3 + ", credBurePromScor4=" + credBurePromScor4 + ", credBurePromScor5="
				+ credBurePromScor5 + ", credBureName1=" + credBureName1 + ", credBureName2=" + credBureName2
				+ ", credBureName3=" + credBureName3 + ", credBureName4=" + credBureName4 + ", credBureName5="
				+ credBureName5 + ", cibilFirmMsmeRank=" + cibilFirmMsmeRank + ", expCommerScor=" + expCommerScor
				+ ", promBorrNetWorth=" + promBorrNetWorth + ", promContribution=" + promContribution
				+ ", promGAssoNPA1YrFlg=" + promGAssoNPA1YrFlg + ", promBussExpYr=" + promBussExpYr + ", salesRevenue="
				+ salesRevenue + ", taxPBIT=" + taxPBIT + ", interestPayment=" + interestPayment
				+ ", taxCurrentProvisionAmt=" + taxCurrentProvisionAmt + ", totCurrentAssets=" + totCurrentAssets
				+ ", totCurrentLiability=" + totCurrentLiability + ", totTermLiability=" + totTermLiability
				+ ", exuityCapital=" + exuityCapital + ", preferenceCapital=" + preferenceCapital + ", reservesSurplus="
				+ reservesSurplus + ", repaymentDueNyrAmt=" + repaymentDueNyrAmt + ", existGreenFldUnitType="
				+ existGreenFldUnitType + ", opratIncome=" + opratIncome + ", profAftTax=" + profAftTax + ", networth="
				+ networth + ", debitEqtRatioUnt=" + debitEqtRatioUnt + ", debitSrvCoverageRatioTl="
				+ debitSrvCoverageRatioTl + ", currentRatioWc=" + currentRatioWc + ", debitEqtRatio=" + debitEqtRatio
				+ ", debitSrvCoverageRatio=" + debitSrvCoverageRatio + ", currentRatios=" + currentRatios
				+ ", creditBureauChiefPromScor=" + creditBureauChiefPromScor + ", totalAssets=" + totalAssets
				+ ", equivStandaredRating=" + equivStandaredRating + ", ivConfirmInvestGrad=" + ivConfirmInvestGrad
				+ ", restructConfirmation=" + restructConfirmation + ", wcEnhanceLimitSanctioned="
				+ wcEnhanceLimitSanctioned + ", wcEnhanceLimitGauranteeAmt=" + wcEnhanceLimitGauranteeAmt
				+ ", outstandingDate=" + outstandingDate + ", itpan=" + itpan + ", ssiUnitName=" + ssiUnitName
				+ ", gurAmt=" + gurAmt + ", npaDate=" + npaDate + ", adharNo=" + adharNo + ", getExposurelmtAmt()="
				+ getExposurelmtAmt() + ", getWcEnhanceLimitSanctioned()=" + getWcEnhanceLimitSanctioned()
				+ ", getWcEnhanceLimitGauranteeAmt()=" + getWcEnhanceLimitGauranteeAmt()
				+ ", getEquivStandaredRating()=" + getEquivStandaredRating() + ", getIvConfirmInvestGrad()="
				+ getIvConfirmInvestGrad() + ", getOutstandingDate()=" + getOutstandingDate()
				+ ", getSsiConstitution()=" + getSsiConstitution() + ", getDebitEqtRatioUnt()=" + getDebitEqtRatioUnt()
				+ ", getDebitSrvCoverageRatioTl()=" + getDebitSrvCoverageRatioTl() + ", getCurrentRatioWc()="
				+ getCurrentRatioWc() + ", getDebitEqtRatio()=" + getDebitEqtRatio() + ", getDebitSrvCoverageRatio()="
				+ getDebitSrvCoverageRatio() + ", getCurrentRatios()=" + getCurrentRatios()
				+ ", getExistGreenFldUnitType()=" + getExistGreenFldUnitType() + ", getUnseqLoanportion()="
				+ getUnseqLoanportion() + ", getUnLoanPortionExcludCgtCovered()=" + getUnLoanPortionExcludCgtCovered()
				+ ", getOpratIncome()=" + getOpratIncome() + ", getProfAftTax()=" + getProfAftTax() + ", getNetworth()="
				+ getNetworth() + ", getCreditBureauChiefPromScor()=" + getCreditBureauChiefPromScor()
				+ ", getTotalAssets()=" + getTotalAssets() + ", getMcgfDetails()=" + getMcgfDetails()
				+ ", getGuaranteeFee()=" + getGuaranteeFee() + ", getFBammtlimit()=" + getFBammtlimit()
				+ ", getPromDirDefaltFlg()=" + getPromDirDefaltFlg() + ", getCredBureKeyPromScor()="
				+ getCredBureKeyPromScor() + ", getCredBurePromScor2()=" + getCredBurePromScor2()
				+ ", getCredBurePromScor3()=" + getCredBurePromScor3() + ", getCredBurePromScor4()="
				+ getCredBurePromScor4() + ", getCredBurePromScor5()=" + getCredBurePromScor5()
				+ ", getCredBureName1()=" + getCredBureName1() + ", getCredBureName2()=" + getCredBureName2()
				+ ", getCredBureName3()=" + getCredBureName3() + ", getCredBureName4()=" + getCredBureName4()
				+ ", getCredBureName5()=" + getCredBureName5() + ", getCibilFirmMsmeRank()=" + getCibilFirmMsmeRank()
				+ ", getExpCommerScor()=" + getExpCommerScor() + ", getPromBorrNetWorth()=" + getPromBorrNetWorth()
				+ ", getPromContribution()=" + getPromContribution() + ", getPromGAssoNPA1YrFlg()="
				+ getPromGAssoNPA1YrFlg() + ", getPromBussExpYr()=" + getPromBussExpYr() + ", getSalesRevenue()="
				+ getSalesRevenue() + ", getTaxPBIT()=" + getTaxPBIT() + ", getInterestPayment()="
				+ getInterestPayment() + ", getTaxCurrentProvisionAmt()=" + getTaxCurrentProvisionAmt()
				+ ", getTotCurrentAssets()=" + getTotCurrentAssets() + ", getTotCurrentLiability()="
				+ getTotCurrentLiability() + ", getTotTermLiability()=" + getTotTermLiability()
				+ ", getExuityCapital()=" + getExuityCapital() + ", getPreferenceCapital()=" + getPreferenceCapital()
				+ ", getReservesSurplus()=" + getReservesSurplus() + ", getRepaymentDueNyrAmt()="
				+ getRepaymentDueNyrAmt() + ", getProMobileNo()=" + getProMobileNo() + ", getHybridSecurity()="
				+ getHybridSecurity() + ", getMovCollateratlSecurityAmt()=" + getMovCollateratlSecurityAmt()
				+ ", getImmovCollateratlSecurityAmt()=" + getImmovCollateratlSecurityAmt()
				+ ", getTotalMIcollatSecAmt()=" + getTotalMIcollatSecAmt() + ", getExposureFbIdY()="
				+ getExposureFbIdY() + ", getExposureFbIdN()=" + getExposureFbIdN() + ", getExposureFbId()="
				+ getExposureFbId() + ", getItpan()=" + getItpan() + ", getSsiUnitName()=" + getSsiUnitName()
				+ ", getGurAmt()=" + getGurAmt() + ", getNpaDate()=" + getNpaDate() + ", getAdharNo()=" + getAdharNo()
				+ ", getGst()=" + getGst() + ", getStateCode()=" + getStateCode() + ", getGstNo()=" + getGstNo()
				+ ", getGstState()=" + getGstState() + ", getSubsidyType()=" + getSubsidyType() + ", getSubsidyOther()="
				+ getSubsidyOther() + ", getHandloomSchName()=" + getHandloomSchName() + ", getInternalRating()="
				+ getInternalRating() + ", getInternalratingProposal()=" + getInternalratingProposal()
				+ ", getInvestmentGrade()=" + getInvestmentGrade() + ", getIsPrimarySecurity()="
				+ getIsPrimarySecurity() + ", getSsiRef()=" + getSsiRef() + ", getActivity()=" + getActivity()
				+ ", getZoneName()=" + getZoneName() + ", getCoFinanceTaken1()=" + getCoFinanceTaken1()
				+ ", getIcardIssueDate()=" + getIcardIssueDate() + ", getIcardNo()=" + getIcardNo()
				+ ", getHandiCrafts()=" + getHandiCrafts() + ", getJointFinance()=" + getJointFinance()
				+ ", getDcHandicrafts()=" + getDcHandicrafts() + ", getInternalRate()=" + getInternalRate()
				+ ", getExternalRate()=" + getExternalRate() + ", getDistrict()=" + getDistrict() + ", getState()="
				+ getState() + ", getSex()=" + getSex() + ", getSocialCategory()=" + getSocialCategory()
				+ ", getMliID()=" + getMliID() + ", getMliBranchName()=" + getMliBranchName()
				+ ", getBorrowerDetails()=" + getBorrowerDetails() + ", getTermLoan()=" + getTermLoan() + ", getWc()="
				+ getWc() + ", getLoanType()=" + getLoanType() + ", getCgpan()=" + getCgpan() + ", getUserId()="
				+ getUserId() + ", getBankId()=" + getBankId() + ", getZoneId()=" + getZoneId() + ", getBranchId()="
				+ getBranchId() + ", getAppRefNo()=" + getAppRefNo() + ", getWcAppRefNo()=" + getWcAppRefNo()
				+ ", getNPA()=" + getNPA() + ", getCompositeLoan()=" + getCompositeLoan() + ", getCollateralSecDtls()="
				+ getCollateralSecDtls() + ", getSubsidyProvided()=" + getSubsidyProvided() + ", getSubmittedDate()="
				+ getSubmittedDate() + ", getSanctionedDate()=" + getSanctionedDate() + ", getRehabilitation()="
				+ getRehabilitation() + ", getApprovedDate()=" + getApprovedDate() + ", getGuaranteeStartDate()="
				+ getGuaranteeStartDate() + ", getAppExpiryDate()=" + getAppExpiryDate() + ", getRemarks()="
				+ getRemarks() + ", getStatus()=" + getStatus() + ", getProjectType()=" + getProjectType()
				+ ", getOutstandingAmount()=" + getOutstandingAmount() + ", getApprovedAmount()=" + getApprovedAmount()
				+ ", getSantionedAmount()=" + getSantionedAmount() + ", getITPAN()=" + getITPAN()
				+ ", getEnhancementAmount()=" + getEnhancementAmount() + ", getReapprovedAmount()="
				+ getReapprovedAmount() + ", getDocRefNo()=" + getDocRefNo() + ", getReapprovalRemarks()="
				+ getReapprovalRemarks() + ", getProjectOutlayDetails()=" + getProjectOutlayDetails()
				+ ", getSanctionedAmount()=" + getSanctionedAmount() + ", getScheme()=" + getScheme()
				+ ", getTheRepaymentDetail()=" + getTheRepaymentDetail() + ", getRegionId()=" + getRegionId()
				+ ", getMliBranchCode()=" + getMliBranchCode() + ", getGuaranteeAmount()=" + getGuaranteeAmount()
				+ ", getMliRefNo()=" + getMliRefNo() + ", getSecuritization()=" + getSecuritization()
				+ ", getMCGFDetails()=" + getMCGFDetails() + ", getSubSchemeName()=" + getSubSchemeName()
				+ ", getExistingRemarks()=" + getExistingRemarks() + ", getAdditionalTC()=" + getAdditionalTC()
				+ ", getWcEnhancement()=" + getWcEnhancement() + ", getWcRenewal()=" + getWcRenewal()
				+ ", getCgpanReference()=" + getCgpanReference() + ", getIsVerified()=" + getIsVerified()
				+ ", getJointcgpan()=" + getJointcgpan() + ", getApplicationType()=" + getApplicationType()
				+ ", getActivityConfirm()=" + getActivityConfirm() + ", getPrevSSI()=" + getPrevSSI()
				+ ", getExistSSI()=" + getExistSSI() + ", getHandiCraftsStatus()=" + getHandiCraftsStatus()
				+ ", getDcHandicraftsStatus()=" + getDcHandicraftsStatus() + ", getDcHandlooms()=" + getDcHandlooms()
				+ ", getDcHandloomsStatus()=" + getDcHandloomsStatus() + ", getWeaverCreditScheme()="
				+ getWeaverCreditScheme() + ", getHandloomchk()=" + getHandloomchk() + ", getUdyogAdharNo()="
				+ getUdyogAdharNo() + ", getBankAcNo()=" + getBankAcNo() + ", getRestructConfirmation()="
				+ getRestructConfirmation() + ", getClass()=" + getClass() + ", hashCode()=" + hashCode()
				+ ", toString()=" + super.toString() + "]";
	}
	
}