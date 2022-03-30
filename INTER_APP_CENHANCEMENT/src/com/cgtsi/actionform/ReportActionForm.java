package com.cgtsi.actionform;

import com.cgtsi.guaranteemaintenance.LegalSuitDetail;
import com.cgtsi.guaranteemaintenance.NPADetails;
import com.cgtsi.guaranteemaintenance.RecoveryProcedure;
import com.cgtsi.reports.QueryBuilderFields;
//import com.itextpdf.text.List;

import java.util.ArrayList;
import java.util.List;

import org.apache.struts.validator.ValidatorActionForm;

//import org.apache.struts.validator.ValidatorActionForm;

public class ReportActionForm extends ValidatorActionForm
{
  private QueryBuilderFields queryBuilderFields = new QueryBuilderFields();
  private ArrayList queryReport = new ArrayList();
  private String memberId;
  private String zoneId;
  private String bankId;
  public String getBankId() {
	return bankId;
}
public void setBankId(String bankId) {
	this.bankId = bankId;
}
public String getZoneId() {
	return zoneId;
}
public void setZoneId(String zoneId) {
	this.zoneId = zoneId;
}
private String borrowerId;
  private String cgpan;
  private String borrowerName;
  private String payId;
  private ArrayList borrowerDetailsForPIReport = new ArrayList();

  private ArrayList osPeriodicInfoDetails = new ArrayList();
  private ArrayList disbPeriodicInfoDetails = new ArrayList();
  private ArrayList repayPeriodicInfoDetails = new ArrayList();
  private NPADetails npaDetails = new NPADetails();
  private LegalSuitDetail legalSuitDetail = new LegalSuitDetail();
  private RecoveryProcedure recoveryProcedure = new RecoveryProcedure();
  private ArrayList recoveryProcedures = new ArrayList();
  private ArrayList recoveryDetails = new ArrayList();
  private List colletralCoulmnName = new ArrayList();
	private List colletralCoulmnValue = new ArrayList();
  public List getColletralCoulmnName() {
		return colletralCoulmnName;
	}
	public void setColletralCoulmnName(List colletralCoulmnName) {
		this.colletralCoulmnName = colletralCoulmnName;
	}
	public List getColletralCoulmnValue() {
		return colletralCoulmnValue;
	}
	public void setColletralCoulmnValue(List colletralCoulmnValue) {
		this.colletralCoulmnValue = colletralCoulmnValue;
	}
private String reportType;
  private java.util.Date dateOfTheDocument20;
	private java.util.Date dateOfTheDocument21;
  private List bulkUploadReportName = new ArrayList();
	private List bulkUploadReportValue = new ArrayList();
	private List reportTypeList=new ArrayList();
	public List getReportTypeList() {
		return reportTypeList;
	}
	public void setReportTypeList(List reportTypeList) {
		this.reportTypeList = reportTypeList;
	}
	private String reportTypeList2;
	private String reportTypeList1;
	public String getReportTypeList1() {
		return reportTypeList1;
	}
	public void setReportTypeList1(String reportTypeList1) {
		this.reportTypeList1 = reportTypeList1;
	}
	public List getBulkUploadReportName() {
		return bulkUploadReportName;
	}
	public void setBulkUploadReportName(List bulkUploadReportName) {
		this.bulkUploadReportName = bulkUploadReportName;
	}
	public List getBulkUploadReportValue() {
		return bulkUploadReportValue;
	}
	public void setBulkUploadReportValue(List bulkUploadReportValue) {
		this.bulkUploadReportValue = bulkUploadReportValue;
	}
	public String getReportTypeList2() {
		return reportTypeList2;
	}
	public void setReportTypeList2(String reportTypeList2) {
		this.reportTypeList2 = reportTypeList2;
	}
	public String getReportType() {
		return reportType;
	}
	public void setReportType(String reportType) {
		this.reportType = reportType;
	}

  public void setPayId(String payId)
  {
    this.payId = payId;
  }

  public String getPayId()
  {
    return this.payId;
  }

  public QueryBuilderFields getQueryBuilderFields()
  {
    return this.queryBuilderFields;
  }

  public void setQueryBuilderFields(QueryBuilderFields fields)
  {
    this.queryBuilderFields = fields;
  }

  public ArrayList getQueryReport()
  {
    return this.queryReport;
  }

  public void setQueryReport(ArrayList report)
  {
    this.queryReport = report;
  }

  public String getBorrowerId()
  {
    return this.borrowerId;
  }

  public String getBorrowerName()
  {
    return this.borrowerName;
  }

  public String getCgpan()
  {
    return this.cgpan;
  }

  public void setBorrowerId(String string)
  {
    this.borrowerId = string;
  }

  public void setBorrowerName(String string)
  {
    this.borrowerName = string;
  }

  public void setCgpan(String string)
  {
    this.cgpan = string;
  }

  public String getMemberId()
  {
    return this.memberId;
  }

  public void setMemberId(String string)
  {
    this.memberId = string;
  }

  public ArrayList getDisbPeriodicInfoDetails()
  {
    return this.disbPeriodicInfoDetails;
  }

  public ArrayList getOsPeriodicInfoDetails()
  {
    return this.osPeriodicInfoDetails;
  }

  public ArrayList getRepayPeriodicInfoDetails()
  {
    return this.repayPeriodicInfoDetails;
  }

  public void setDisbPeriodicInfoDetails(ArrayList list)
  {
    this.disbPeriodicInfoDetails = list;
  }

  public void setOsPeriodicInfoDetails(ArrayList list)
  {
    this.osPeriodicInfoDetails = list;
  }

  public void setRepayPeriodicInfoDetails(ArrayList list)
  {
    this.repayPeriodicInfoDetails = list;
  }

  public ArrayList getBorrowerDetailsForPIReport()
  {
    return this.borrowerDetailsForPIReport;
  }

  public void setBorrowerDetailsForPIReport(ArrayList list)
  {
    this.borrowerDetailsForPIReport = list;
  }

  public NPADetails getNpaDetails()
  {
    return this.npaDetails;
  }

  public ArrayList getRecoveryDetails()
  {
    return this.recoveryDetails;
  }

  public void setNpaDetails(NPADetails details)
  {
    this.npaDetails = details;
  }

  public void setRecoveryDetails(ArrayList list)
  {
    this.recoveryDetails = list;
  }

  public LegalSuitDetail getLegalSuitDetail()
  {
    return this.legalSuitDetail;
  }

  public RecoveryProcedure getRecoveryProcedure()
  {
    return this.recoveryProcedure;
  }

  public ArrayList getRecoveryProcedures()
  {
    return this.recoveryProcedures;
  }

  public void setLegalSuitDetail(LegalSuitDetail detail)
  {
    this.legalSuitDetail = detail;
  }

  public void setRecoveryProcedure(RecoveryProcedure procedure)
  {
    this.recoveryProcedure = procedure;
  }

  public void setRecoveryProcedures(ArrayList list)
  {
    this.recoveryProcedures = list;
  }
	public java.util.Date getDateOfTheDocument20() {
		return dateOfTheDocument20;
	}
	public void setDateOfTheDocument20(java.util.Date dateOfTheDocument20) {
		this.dateOfTheDocument20 = dateOfTheDocument20;
	}
	public java.util.Date getDateOfTheDocument21() {
		return dateOfTheDocument21;
	}
	public void setDateOfTheDocument21(java.util.Date dateOfTheDocument21) {
		this.dateOfTheDocument21 = dateOfTheDocument21;
	}
	private String checkValue;
	private String itpan;
	
	public String getItpan() {
		return itpan;
	}
	public void setItpan(String itpan) {
		this.itpan = itpan;
	}
	public String getCheckValue() {
		return checkValue;
	}
	public void setCheckValue(String checkValue) {
		this.checkValue = checkValue;
	}
	
}