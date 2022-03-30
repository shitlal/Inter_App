package com.cgtsi.action;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;

import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.validator.GenericValidator;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;
import org.apache.struts.upload.FormFile;
import org.apache.struts.validator.DynaValidatorActionForm;

import com.cgtsi.actionform.APForm;
import com.cgtsi.actionform.ClaimActionForm;
import com.cgtsi.actionform.GMActionForm;
import com.cgtsi.actionform.MLIDeatils;
import com.cgtsi.actionform.NPADateModificationActionForm;
import com.cgtsi.actionform.NPAForm;
import com.cgtsi.actionform.RPActionForm;
import com.cgtsi.admin.Administrator;
import com.cgtsi.admin.MenuOptions;
import com.cgtsi.admin.ParameterMaster;
import com.cgtsi.admin.User;
import com.cgtsi.application.Application;
import com.cgtsi.application.ApplicationDAO;
import com.cgtsi.application.ApplicationProcessor;
import com.cgtsi.application.BorrowerDetails;
import com.cgtsi.application.LogClass;
import com.cgtsi.application.SSIDetails;
import com.cgtsi.claim.CPDAO;
import com.cgtsi.claim.ClaimConstants;
import com.cgtsi.claim.ClaimsProcessor;
import com.cgtsi.claim.ExportFailedException;
import com.cgtsi.common.Constants;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.Log;
import com.cgtsi.common.MailerException;
import com.cgtsi.common.MessageException;
import com.cgtsi.common.NoDataException;
import com.cgtsi.guaranteemaintenance.BorrowerInfo;
import com.cgtsi.guaranteemaintenance.CgpanDetail;
import com.cgtsi.guaranteemaintenance.CgpanInfo;
import com.cgtsi.guaranteemaintenance.ClosureDetail;
import com.cgtsi.guaranteemaintenance.Disbursement;
import com.cgtsi.guaranteemaintenance.DisbursementAmount;
import com.cgtsi.guaranteemaintenance.GMConstants;
import com.cgtsi.guaranteemaintenance.GMDAO;
import com.cgtsi.guaranteemaintenance.GMProcessor;
import com.cgtsi.guaranteemaintenance.NPADetails;
import com.cgtsi.guaranteemaintenance.OutstandingAmount;
import com.cgtsi.guaranteemaintenance.OutstandingDetail;
import com.cgtsi.guaranteemaintenance.PeriodicInfo;
import com.cgtsi.guaranteemaintenance.Recovery;
import com.cgtsi.guaranteemaintenance.Repayment;
import com.cgtsi.guaranteemaintenance.RepaymentAmount;
import com.cgtsi.guaranteemaintenance.RepaymentSchedule;
import com.cgtsi.receiptspayments.DANSummary;
import com.cgtsi.receiptspayments.MissingDANDetailsException;
import com.cgtsi.receiptspayments.RpDAO;
import com.cgtsi.receiptspayments.RpProcessor;
import com.cgtsi.registration.MLIInfo;
import com.cgtsi.registration.NoMemberFoundException;
import com.cgtsi.registration.Registration;
import com.cgtsi.reports.ApplicationReport;
import com.cgtsi.reports.GeneralReport;
import com.cgtsi.reports.ReportDAO;
import com.cgtsi.reports.ReportManager;
import com.cgtsi.util.BulkUpload;
import com.cgtsi.util.CustomisedDate;
import com.cgtsi.util.DBConnection;
import com.cgtsi.util.DateHelper;
import com.cgtsi.util.NpaBulkUpload;
import com.cgtsi.util.PropertyLoader;
import com.cgtsi.util.TableDetailBean;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.reflect.TypeToken;


public class GMAction extends BaseAction {
    public OutstandingDetail getOutstandingDetailsForCgpan(String cgpan) throws NoDataException, 
                                                                                DatabaseException, 
                                                                                SQLException {
        Log.log(4, "GMDAO", "getOutstandingDetailsForCgpan", "Entered");
        GMProcessor gmProcessor = new GMProcessor(); 
        OutstandingDetail outstandingDetail = null;
        outstandingDetail = gmProcessor.getOutstandingDetailsForCgpan(cgpan);
        Log.log(4, "GMDAO", "getOutstandingDetailsForCgpan", "Exited");

        return outstandingDetail;
    } 

    public Disbursement getDisbursementDetailsForCgpan(String cgpan) throws DatabaseException {
        Log.log(4, "GMProcessor", "getDisbursementDetailsForCgpan", "Entered");
        Disbursement disbursement = null;
        GMProcessor gmProcessor = new GMProcessor();
        disbursement = gmProcessor.getDisbursementDetailsForCgpan(cgpan);
        Log.log(4, "GMProcessor", "getDisbursementDetailsForCgpan", "Exited");

        return disbursement;
    }

    public ActionForward GetBidCgpanDisbursement(ActionMapping mapping, 
                                                 ActionForm form, 
                                                 HttpServletRequest request, 
                                                 HttpServletResponse response) throws Exception {

        Log.log(Log.INFO, "GMAction", "GetBidCgpanDisbursement", "Entered");
        GMProcessor gmProcessor = new GMProcessor();

        GMActionForm gmActionForm = (GMActionForm)form;
        gmActionForm.setBorrowerId("");
        gmActionForm.setCgpan("");
        gmActionForm.setBorrowerName("");

        User user = getUserInformation(request);

        String bankId = user.getBankId();
        String zoneId = user.getZoneId();
        String branchId = user.getBranchId();
        String memberId = bankId.concat(zoneId).concat(branchId);

        gmActionForm.setPeriodicBankId(bankId);
        if (bankId.equals(Constants.CGTSI_USER_BANK_ID)) {
            memberId = "";
        }
        gmActionForm.setMemberId(memberId);

        Log.log(Log.INFO, "GMAction", "GetBidCgpanDisbursement", "Exited");
        //dynaActionForm.initialize(mapping) ;
        return mapping.findForward(Constants.SUCCESS);

    }

  /*  public ActionForward saveNPAUpgradationDetails(ActionMapping mapping, 
            ActionForm form, 
            HttpServletRequest request, 
            HttpServletResponse response) throws Exception 
    {
    	
    	//System.out.println("saveNPAUpgradationDetails method");
    	try
    	{
    		Connection connection = DBConnection.getConnection(false);
    		
    		Statement statement = null;
    		ResultSet rs = null;
    		String emailId="";
    	
    	NPAForm objNPAForm= (NPAForm) form;
    	//System.out.println("saveNPAUpgradationDetails called"+objNPAForm.getNpaupgradationDate());
    	//LogClass.StepWritter("saveNPAUpgradationDetails called"+objNPAForm.getNpaupgradationDate());
    	User user = getUserInformation(request);
		
		String bankId = user.getBankId();
		String zoneId = user.getZoneId(); 
		String branchId = user.getBranchId();
		String memberId = bankId.concat(zoneId).concat(branchId);
		
    	//System.out.println("saveNPAUpgradationDetails cgpan"+objNPAForm.getNPAformType());
    	//System.out.println("saveNPAUpgradationDetails npaUpgradationDate"+objNPAForm.getStrCgpan());
    	//System.out.println("saveNPAUpgradationDetails remark"+objNPAForm.getNpaupgradationDate());
    	
    	GMProcessor gmProcessor = new GMProcessor();
    	gmProcessor.saveNPADetails(objNPAForm.getStrCgpan(), memberId, objNPAForm.getNpaupgradationDate(), objNPAForm.getRemark(),user.getUserId(),objNPAForm.getNPAformType(),objNPAForm.getNewNpaDate());
    	
    	//koteswar mail start
    	

		String subject = "Npa Upgradations Pending Alert";
		
		String mailBody = "Dear User, \n \n Request for Npa Upgradations is pending for  action to be taken at your end.\nYou are requested to Approve/Reject on the transactions by using the below path\n" +
				"Guarantee Maintenance ---->Submission for Npa UPgradation in our CGTMSE portal.\n"
			+ " \n \n Regards, \n CGTMSE.   ";
    	
		String emailQry = "select distinct nvl(USR_EMAIL_ID,'support@cgtmse.in')  from mli_checker_info where  mem_bnk_id||mem_zne_id||mem_brn_id='"
			+ memberId + "'";
	// System.out.println("GMA emailQry : "+emailQry);
		statement = connection.createStatement();
	rs = statement.executeQuery(emailQry);
	while (rs.next()) {
		 emailId = rs.getString(1);
	}
		
		
    	
    	String host = "192.168.10.118";
		boolean sessionDebug = false;
		Properties props = System.getProperties();
		props.put("mail.host", host);
		props.put("mail.transport.protocol", "smtp");
		props.put("mail.smtp.host", host);
		props.put("mail.from", "support@cgtmse.in");

		Session session1 = null;
		session1 = Session.getDefaultInstance(props, null);
		session1.setDebug(sessionDebug);

		javax.mail.internet.MimeMessage msg = new javax.mail.internet.MimeMessage(
				session1);
		msg.setFrom(new javax.mail.internet.InternetAddress(
				"support@cgtmse.in"));
		//System.out.println("GMA emailid send mail : " + emailId);
		javax.mail.internet.InternetAddress[] Toaddress = { new javax.mail.internet.InternetAddress(
				emailId) };
		msg.setRecipients(javax.mail.Message.RecipientType.TO,
				Toaddress);
		msg.setSubject(subject);
		msg.setSentDate(new Date());

			msg.setText(mailBody);
		
		Transport.send(msg);
    	
    
		
    	
    	//System.out.println("saveNPAUpgradationDetails end =");
    	//System.out.println("saveNPAUpgradationDetails end"+Constants.SUCCESS);
    	request.setAttribute("message", "NPA Upgradation Details saved Successfully.");
    	}
    	catch(Exception e)
    	{
    		e.printStackTrace();
    	}
    	return mapping.findForward(Constants.SUCCESS);
    }*/
    
    public ActionForward saveNPAUpgradationDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
	{
 // System.out.println("saveNPAUpgradationDetails method");
    	Connection connection =null;
    try
    {
        connection = DBConnection.getConnection(false);
        Statement statement = null;
        ResultSet rs = null;
        String emailId = "";
        NPAForm objNPAForm = (NPAForm)form;
        User user = getUserInformation(request);
        String bankId = user.getBankId();
        String zoneId = user.getZoneId();
        String branchId = user.getBranchId();
        String memberId = bankId.concat(zoneId).concat(branchId);
      //  System.out.println((new StringBuilder("saveNPAUpgradationDetails cgpan")).append(objNPAForm.getNPAformType()).toString());
      //  System.out.println((new StringBuilder("saveNPAUpgradationDetails npaUpgradationDate")).append(objNPAForm.getStrCgpan()).toString());
       // System.out.println((new StringBuilder("saveNPAUpgradationDetails remark")).append(objNPAForm.getNpaupgradationDate()).toString());
        GMProcessor gmProcessor = new GMProcessor();
        gmProcessor.saveNPADetails(objNPAForm.getStrCgpan(), memberId, objNPAForm.getNpaupgradationDate(), objNPAForm.getRemark(), user.getUserId(), objNPAForm.getNPAformType(), objNPAForm.getNewNpaDate());
    //    String subject = "Npa Upgradations Pending Alert";
     //   String mailBody = "Dear User, \n \n Request for Npa Upgradations is pending for  action to be taken at your end.\nYou are requested to Approve/Reject on the transactions by using the below path\nGuarantee Maintenance ---->Submission for Npa UPgradation in our CGTMSE portal.\n \n \n Regards, \n CGTMSE.   ";
       // String emailQry = (new StringBuilder("select distinct nvl(USR_EMAIL_ID,'support@cgtmse.in')  from mli_checker_info where  mem_bnk_id||mem_zne_id||mem_brn_id='")).append(memberId).append("'").toString();
     //   statement = connection.createStatement();
      /*  for(rs = statement.executeQuery(emailQry); rs.next();)
            emailId = rs.getString(1);
*/
        //String host = "192.168.10.118";
        //boolean sessionDebug = false;
       // Properties props = System.getProperties();
       // props.put("mail.host", host);
     //   props.put("mail.transport.protocol", "smtp");
     //   props.put("mail.smtp.host", host);
     //   props.put("mail.from", "support@cgtmse.in");
       // Session session1 = null;
      //  session1 = Session.getDefaultInstance(props, null);
        //session1.setDebug(sessionDebug);
       // MimeMessage msg = new MimeMessage(session1);
      //  msg.setFrom(new InternetAddress("support@cgtmse.in"));
      /*  InternetAddress Toaddress[] = {
            new InternetAddress(emailId)
        };
        msg.setRecipients(javax.mail.Message.RecipientType.TO, Toaddress);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        msg.setText(mailBody);
        Transport.send(msg);*/
        request.setAttribute("message", "NPA Upgradation Details saved Successfully.");
    }
    catch(Exception e)
    {
        e.printStackTrace();
    }
    finally {
		DBConnection.freeConnection(connection);

	}
    return mapping.findForward("success");
}
  

    public ActionForward getCGDetailsFromCGPAN(ActionMapping mapping, 
            ActionForm form, 
            HttpServletRequest request, 
            HttpServletResponse response) throws Exception 
    {
		//System.out.println("getCGDetailsFromCGPAN");
		Log.log(Log.INFO, "GMAction", "GetBidCgpanDisbursement", "Entered");
		GMProcessor gmProcessor = new GMProcessor();
//		
		NPAForm gmActionForm = (NPAForm)form;
//		gmActionForm.setBorrowerId("");
//		gmActionForm.setCgpan("");
//		gmActionForm.setBorrowerName("");
//		
		// ClaimsProcessor processor = new ClaimsProcessor();
		// System.out.println("CGPAN exist result"+processor.getBorowwerForCGPAN(gmActionForm.getCgpan()));
		 
		User user = getUserInformation(request);
//		
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId.concat(zoneId).concat(branchId);
		//System.out.println(Constants.SUCCESS+"=getCGDetailsFromCGPAN memberId"+gmActionForm.getCgpan());
		
		NPADateModificationActionForm objNPADateModificationActionForm[]=null;
		ArrayList<NPADateModificationActionForm> arrayListNPADateModificationActionForm= new ArrayList<NPADateModificationActionForm>();
		arrayListNPADateModificationActionForm=gmProcessor.getNPADetails(gmActionForm.getCgpan(),memberId);
	
		
		//System.out.println("getCGDetailsFromCGPAN "+arrayListNPADateModificationActionForm.size());
		//System.out.println("formType ==111 "+gmActionForm.getNPAformType());
		request.setAttribute("NPAUpgradationDetails", arrayListNPADateModificationActionForm);
		request.setAttribute("cgpan",gmActionForm.getCgpan());
		request.setAttribute("formTypeforJsp","upgradation");
//		
//		
//		gmActionForm.setPeriodicBankId(bankId);
//		if (bankId.equals(Constants.CGTSI_USER_BANK_ID)) {
//		memberId = "";
//		}
//		gmActionForm.setMemberId(memberId);
//		
//		Log.log(Log.INFO, "GMAction", "GetBidCgpanDisbursement", "Exited");
		//dynaActionForm.initialize(mapping) ;
		return mapping.findForward(Constants.SUCCESS);

}
    
    public Repayment getRepaymentDetailsForCgpan(String cgpan) throws DatabaseException {
        Log.log(4, "GMProcessor", "getRepaymentDetailsForCgpan", "Entered");
        Repayment repayment = null;
        GMProcessor gmProcessor = new GMProcessor();
        repayment = gmProcessor.getRepaymentDetailsForCgpan(cgpan);
        Log.log(4, "GMProcessor", "getRepaymentDetailsForCgpan", "Exited");

        return repayment;
    }

    public ActionForward exceptionalNpaUpdateInput(ActionMapping mapping, 
                                                   ActionForm form, 
                                                   HttpServletRequest request, 
                                                   HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "exceptionalNpaUpdateInput", "Entered");
        GMProcessor gmProcessor = new GMProcessor();
        GMActionForm gmActionForm = (GMActionForm)form;
        User user = getUserInformation(request);
        String bankId = user.getBankId();
        String zoneId = user.getZoneId();
        String branchId = user.getBranchId();
        String memberId = bankId.concat(zoneId).concat(branchId);
        gmActionForm.setBankId(bankId);
        if (bankId.equals("0000"))
            memberId = "";
        gmActionForm.setMemberId(memberId);
        gmActionForm.setCgpan("");
        gmActionForm.setRemarks("");
        Log.log(4, "GMAction", "exceptionalNpaUpdateInput", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward exceptionalNpaUpdate(ActionMapping mapping, 
                                              ActionForm form, 
                                              HttpServletRequest request, 
                                              HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "exceptionalNpaUpdate", "Entered");
        GMProcessor gmProcessor = new GMProcessor();
        GMActionForm gmActionForm = (GMActionForm)form;
        ApplicationReport appReport = new ApplicationReport();
        ReportManager reportManager = new ReportManager();
        String memberId = gmActionForm.getMemberId();
        String cgpan = gmActionForm.getCgpan().toUpperCase();
        gmActionForm.setMemberId(memberId);
        gmActionForm.setCgpan(cgpan);
        appReport = reportManager.getApplicationReportForCgpan(cgpan);
        String mliId = appReport.getMemberId();
        if (!mliId.equals(memberId)) {
            throw new NoMemberFoundException("Entered Cgpan " + cgpan + 
                                             " does not belongs to the entered Member Id :" + 
                                             memberId);
        }

        gmActionForm.setRemarks("");
        gmActionForm.setApplicationReport(appReport);
        Log.log(4, "GMAction", "exceptionalNpaUpdate", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward submitexceptionalNpaUpdaterequest(ActionMapping mapping, 
                                                           ActionForm form, 
                                                           HttpServletRequest request, 
                                                           HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "submitexceptionalNpaUpdaterequest", "Entered");
        GMProcessor gmProcessor = new GMProcessor();
        GMDAO gmDAO = new GMDAO();
        ApplicationProcessor appProcessor = new ApplicationProcessor();
        ClosureDetail closureDtl = new ClosureDetail();
        HashMap closureDetails = null;
        GMActionForm gmActionForm = (GMActionForm)form;
        HttpSession session = request.getSession(false);
        User user = getUserInformation(request);
        String userId = user.getUserId();
        String memberId = gmActionForm.getMemberId();
        String cgpan = gmActionForm.getCgpan().toUpperCase();
        String remarks = gmActionForm.getRemarks();
        String ssiRefNo = 
            gmActionForm.getApplicationReport().getSsiReferenceNumber();
        int type = 0;
        String forward = "";
        ClaimsProcessor processor = new ClaimsProcessor();
        Vector memberids = processor.getAllMemberIds();
        if (!memberids.contains(memberId))
            throw new NoMemberFoundException("Member Id :" + memberId + 
                                             " does not exist in the database.");
        ArrayList borrowerIds = new ArrayList();
        borrowerIds = gmProcessor.getBorrowerIds(memberId);
        if (!cgpan.equals("")) {
            type = 1;
            String bIdForThisCgpan = processor.getBorowwerForCGPAN(cgpan);
            Log.log(5, "GMAction", "submitClosureDetails", 
                    " Bid For Pan - " + bIdForThisCgpan);
            if (!borrowerIds.contains(bIdForThisCgpan))
                throw new NoDataException(cgpan + "is not a valid Cgpan for " + 
                                          "the Member Id :" + memberId + 
                                          ". Please enter correct Cgpan");
            int claimCount = appProcessor.getClaimCount(bIdForThisCgpan);
            if (claimCount > 0)
                throw new MessageException("Application cannot be Updated for this borrower since Claim Application has been submitted");
            Application application = new Application();
            try {
                application = appProcessor.getAppForCgpan(memberId, cgpan);
            } catch (DatabaseException databaseException) {
                if (databaseException.getMessage().equals("Application does not exist."))
                    throw new DatabaseException("The application is not a live application");
            }
            if (application.getStatus().equals("CL"))
                throw new DatabaseException("The application has already been closed");
            forward = "success";
        }
        Log.log(4, "GMAction", "submitexceptionalNpaUpdaterequest", "Exited");
        gmDAO.submitexceptionalNpaUpdaterequest(memberId, cgpan, remarks, 
                                                userId, ssiRefNo);
        request.setAttribute("message", 
                             "<b>Provision for  " + cgpan + " for update  Npa details request added successfully.<b><br>");

        return mapping.findForward(forward);
    }

    public ActionForward ApplicationInfo(ActionMapping mapping, 
                                         ActionForm form, 
                                         HttpServletRequest request, 
                                         HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "ApplicationInfo", "Entered");

        DynaActionForm dynaActionForm = (DynaActionForm)form;
        dynaActionForm.set("borrowerIdForModifyBorrDtl", "");
        dynaActionForm.set("cgpanForModifyBorrDtl", "");
        dynaActionForm.set("borrowerNameForModifyBorrDtl", "");

        User user = getUserInformation(request);

        String bankId = user.getBankId();
        String branchId = user.getBranchId();
        String zoneId = user.getZoneId();
        String memberId = bankId.concat(zoneId).concat(branchId);

        if (bankId.equals("0000")) {
            memberId = "";
        }
        dynaActionForm.set("memberIdForModifyBorrDtl", memberId);
        dynaActionForm.set("bankId", bankId);
        Log.log(4, "GMAction", "ApplicationInfo", "Exited");
       // HttpSession session = request.getSession(false);
       // session.setAttribute("BORROWER_FLAG", "MODIFY");

        return mapping.findForward("success");
    }

    public ActionForward showModifiedBorrowerDetails(ActionMapping mapping, 
                                                     ActionForm form, 
                                                     HttpServletRequest request, 
                                                     HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "showModifiedBorrowerDetails", "Entered");
System.out.println("showModifiedBorrowerDetails called");
        GMActionForm gmActionForm = (GMActionForm)form;
      
       // gmActionForm.setBorrowerApprovalRemarks(null);
       // gmActionForm.setApproveBorrowerFlag(null);
       // gmActionForm.setBidsList(null);
        String forward = "";

        User user = getUserInformation(request);

        String bankId = user.getBankId();
        String branchId = user.getBranchId();
        String zoneId = user.getZoneId();
        
        String memberId = bankId.concat(zoneId).concat(branchId);
        
        try
        {
        GMProcessor gmProcessor = new GMProcessor();
        TreeMap bidsList = gmProcessor.getBidsForApproval(memberId);
        System.out.println("showModifiedBorrowerDetails bidsList"+bidsList);
        if ((bidsList.isEmpty()) || (bidsList.size() == 0)) {
         
            request.setAttribute("message", 
                                 "No Borrower Details available for Approval");
            forward = "success";
        } else {
      //  	 System.out.println("showModifiedBorrowerDetails in else"+bidsList);
            gmActionForm.setBidsList(bidsList);
           // request.setAttribute("bidsList", bidsList);
            forward = "bidList";
        }
        }
        catch(Exception e)
        {
        	e.printStackTrace();
        }
        
        return mapping.findForward(forward);
    }

    public ActionForward showApprovalPeriodicInfo(ActionMapping mapping, 
                                                  ActionForm form, 
                                                  HttpServletRequest request, 
                                                  HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "showApprovalPeriodicInfo", "Entered");

        GMActionForm gmActionForm = (GMActionForm)form;

        String forward = "";

        GMProcessor gmProcessor = new GMProcessor();
        TreeMap bidsList = gmProcessor.getBidsForPerInfoApproval();
        if ((bidsList.isEmpty()) || (bidsList.size() == 0)) {
            Log.log(4, "GMAction", "showModifiedBorrowerDetails", 
                    "emty bid list");
            request.setAttribute("message", 
                                 "No Periodic Info Details available for Approval");
            forward = "success";
        } else {
            gmActionForm.setBidsList(bidsList);
            forward = "memberBidList";
        }

        return mapping.findForward(forward);
    }

    public ActionForward backToApproval(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "backToApproval", "Entered");

        DynaActionForm dynaForm = (DynaActionForm)form;

        HttpSession session = request.getSession(false);
        session.setAttribute("BORROWER_FLAG", null);

        return mapping.findForward("success");
    }
    public ActionForward CheckNPAUpgradationDateValidation(ActionMapping mapping, ActionForm form, 
            HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
    	
    	//System.out.println("CheckNPAUpgradationDateValidation executed");
		GMDAO gmdao= new GMDAO();		
		String message=gmdao.CheckNPAUpgradationDateValidation(request.getParameter("cgpan"),request.getParameter("NPAUpgradationDate"),request.getParameter("LastNPAReportingDate"));
		PrintWriter out= response.getWriter();
		out.println(message);
		return null;
}
    
  /*  public ActionForward modifyBorrowerDetails(ActionMapping mapping, 
                                               ActionForm form, 
                                               HttpServletRequest request, 
                                               HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "ModifyBorrowerDetails", "Entered");

        DynaActionForm dynaActionForm = (DynaActionForm)form;

        GMProcessor gmProcessor = new GMProcessor();
        BorrowerDetails borrowerDetails = null;
        SSIDetails ssiDetails = null;
        Administrator admin = new Administrator();

        ApplicationProcessor appProcessor = new ApplicationProcessor();

        ArrayList states = null;
        ArrayList districts = null;
        String state = "";

        String forward = "";

        String memberId = 
            ((String)dynaActionForm.get("memberIdForModifyBorrDtl")).toUpperCase();
        String cgpan = 
            ((String)dynaActionForm.get("cgpanForModifyBorrDtl")).toUpperCase();
        String borrowerId = 
            ((String)dynaActionForm.get("borrowerIdForModifyBorrDtl")).toUpperCase();
        String borrowerName = 
            ((String)dynaActionForm.get("borrowerNameForModifyBorrDtl")).toUpperCase();

        states = admin.getAllStates();
        dynaActionForm.set("states", states);

        ClaimsProcessor processor = new ClaimsProcessor();
        Vector memberids = processor.getAllMemberIds();
        if (!memberids.contains(memberId)) {
            throw new NoMemberFoundException("Member Id :" + memberId + 
                                             " does not exist in the database.");
        }

        ArrayList borrowerIds = new ArrayList();
        borrowerIds = gmProcessor.getBorrowerIds(memberId);

        int type = 0;
        if ((!borrowerId.equals("")) && (cgpan.equals("")) && 
            (borrowerName.equals(""))) {
            type = 0;
            if (!borrowerIds.contains(borrowerId)) {
                throw new NoDataException(borrowerId + 
                                          " is not among the borrower" + 
                                          " Ids for the Member Id :" + 
                                          memberId + ". Please enter correct" + 
                                          " Member Id and Borrower Id.");
            }

            int claimCount = appProcessor.getClaimCount(borrowerId);
            if (claimCount > 0) {
                throw new MessageException("Borrower Details for this borrower cannot be modified since Claim Application has been submitted");
            }

            borrowerDetails = 
                    gmProcessor.viewBorrowerDetails(memberId, borrowerId, 
                                                    type);

            forward = "success";
        } else if ((!cgpan.equals("")) && (borrowerId.equals("")) && 
                   (borrowerName.equals(""))) {
            type = 1;
            String bIdForThisCgpan = processor.getBorowwerForCGPAN(cgpan);
            Log.log(5, "GMAction", "modifyBorrowerDetails", 
                    " Bid For Pan - " + bIdForThisCgpan);
            if (!borrowerIds.contains(bIdForThisCgpan)) {
                throw new NoDataException(cgpan + "is not a valid Cgpan for " + 
                                          "the Member Id :" + memberId + 
                                          ". Please enter correct Cgpan");
            }

            dynaActionForm.set("borrowerIdForModifyBorrDtl", bIdForThisCgpan);

            int claimCount = appProcessor.getClaimCount(bIdForThisCgpan);
            if (claimCount > 0) {
                throw new MessageException("Borrower Details for this borrower cannot be modified since Claim Application has been submitted");
            }

            borrowerDetails = 
                    gmProcessor.viewBorrowerDetails(memberId, cgpan, type);

            forward = "success";
        } else if ((!borrowerName.equals("")) && (borrowerId.equals("")) && 
                   (cgpan.equals(""))) {
            type = 2;
            ArrayList bIdForBorrowerName = 
                gmProcessor.getBorrowerIdForBorrowerName(borrowerName, 
                                                         memberId);

            if ((bIdForBorrowerName.size() == 0) || 
                (bIdForBorrowerName == null)) {
                throw new NoDataException("No Data Exists for this combination,  Enter any other value");
            }

            dynaActionForm.set("borrowerIds", bIdForBorrowerName);
            forward = "bidList";
        }

        if (borrowerDetails != null) {
            int borrowerRefNo = 
                borrowerDetails.getSsiDetails().getBorrowerRefNo();

            Integer intRefNo = new Integer(borrowerRefNo);
            dynaActionForm.set("borrowerRefNo", intRefNo);

            ssiDetails = borrowerDetails.getSsiDetails();

            BeanUtils.copyProperties(dynaActionForm, ssiDetails);
            BeanUtils.copyProperties(dynaActionForm, borrowerDetails);

            state = ssiDetails.getState();
            Log.log(5, "GMAction", "modifyBorrowerDetails", "state " + state);

            ArrayList districtList = admin.getAllDistricts(state);
            dynaActionForm.set("districts", districtList);

            String districtName = ssiDetails.getDistrict();
            Log.log(5, "GMAction", "modifyBorrowerDetails", 
                    "districtName " + districtName);

            if (districtList.contains(districtName)) {
                Log.log(5, "GMAction", "modifyBorrowerDetails", 
                        "setting in dyna form districtName " + districtName);
                dynaActionForm.set("district", districtName);
            } else {
                Log.log(5, "GMAction", "modifyBorrowerDetails", 
                        "districtName " + districtName);
                dynaActionForm.set("districtOthers", districtName);
                dynaActionForm.set("district", "Others");
            }

            String constitutionVal = ssiDetails.getConstitution();
            if ((!constitutionVal.equals("proprietary")) && 
                (!constitutionVal.equals("partnership")) && 
                (!constitutionVal.equals("private")) && 
                (!constitutionVal.equals("public"))) {
                dynaActionForm.set("constitutionOther", constitutionVal);
                dynaActionForm.set("constitution", "Others");
            } else {
                dynaActionForm.set("constitution", constitutionVal);
            }

            String legalIDString = ssiDetails.getCpLegalID();
            if ((legalIDString != null) && (!legalIDString.equals(""))) {
                if ((!legalIDString.equals("VoterIdentityCard")) && 
                    (!legalIDString.equals("RationCardnumber")) && 
                    (!legalIDString.equals("PASSPORT")) && 
                    (!legalIDString.equals("Driving License"))) {
                    dynaActionForm.set("otherCpLegalID", legalIDString);
                    dynaActionForm.set("cpLegalID", "Others");
                } else {
                    dynaActionForm.set("cpLegalID", legalIDString);
                }
            }

            ArrayList socialList = getSocialCategory();
            dynaActionForm.set("socialCategoryList", socialList);

            ArrayList industryNatureList = admin.getAllIndustryNature();
            dynaActionForm.set("industryNatureList", industryNatureList);

            String industryNature = ssiDetails.getIndustryNature();

            Log.log(4, "GMAction", "ModifyBorrowerDetails", 
                    "industry nature :" + industryNature);

            if ((industryNature != null) && (!industryNature.equals("")) && 
                (!industryNature.equals("OTHERS"))) {
                ArrayList industrySectors = 
                    admin.getIndustrySectors(industryNature);
                dynaActionForm.set("industrySectors", industrySectors);
            } else {
                ArrayList industrySectors = new ArrayList();
                String industrySector = ssiDetails.getIndustrySector();
                industrySectors.add(industrySector);
                dynaActionForm.set("industrySectors", industrySectors);
            }

            Log.log(4, "GMAction", "ModifyBorrowerDetails", "Exited");

            states = null;
            districts = null;

            borrowerIds = null;
            memberids = null;
        }

        return mapping.findForward(forward);
    } */
    
	public ActionForward modifyBorrowerDetails(ActionMapping mapping,
		ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		String forward = "";
		try
		{
		//System.out.println("modifyBorrowerDetails");
		DynaActionForm dynaActionForm = (DynaActionForm) form;

		GMProcessor gmProcessor = new GMProcessor();
		BorrowerDetails borrowerDetails = null;
		SSIDetails ssiDetails = null;
		APForm apform = new APForm();

		Administrator admin = new Administrator();

		ArrayList states = null;
		ArrayList districts = null;
		String state = "";

		
		
			String memberId = 
			((String) dynaActionForm.get("memberIdForModifyBorrDtl")).toUpperCase();
		String cgpan = ((String) dynaActionForm.get("cgpanForModifyBorrDtl"))
				.toUpperCase();

		String borrowerName = ((String) dynaActionForm
				.get("borrowerNameForModifyBorrDtl")).toUpperCase();

		states = admin.getAllStates();
		dynaActionForm.set("states", states);

		ClaimsProcessor processor = new ClaimsProcessor();

		if ((!cgpan.equals(""))) {

			String bIdForThisCgpan = processor.getBorowwerForCGPAN(cgpan);
			
			dynaActionForm.set("borrowerIdForModifyBorrDtl", bIdForThisCgpan);

			borrowerDetails = gmProcessor
					.viewBorrowerDetailsForModifyBorrowerDetails(memberId,
							cgpan, 1);

			forward = "success";
		}
		
		if (borrowerDetails != null) {
			int borrowerRefNo = borrowerDetails.getSsiDetails()
					.getBorrowerRefNo();

			Integer intRefNo = new Integer(borrowerRefNo);
			dynaActionForm.set("borrowerRefNo", intRefNo);

			ssiDetails = borrowerDetails.getSsiDetails();

			apform = borrowerDetails.getApform();

			BeanUtils.copyProperties(dynaActionForm, ssiDetails);
			BeanUtils.copyProperties(dynaActionForm, borrowerDetails);
			BeanUtils.copyProperties(dynaActionForm, apform);

			//System.out.println("udyog adhar no. " + apform.getUdyogAdharNo());
			//System.out.println("bank acc no. " + apform.getBankAcNo());

			state = ssiDetails.getState();
			

			ArrayList districtList = admin.getAllDistricts(state);
			dynaActionForm.set("districts", districtList);

			String districtName = ssiDetails.getDistrict();
			
			if (districtList.contains(districtName)) {
				dynaActionForm.set("district", districtName);
			} else {
				dynaActionForm.set("districtOthers", districtName);
				dynaActionForm.set("district", "Others");
			}

			String constitutionVal = ssiDetails.getConstitution();
		//	System.out.println("constitutionVal "+constitutionVal);
			if ((constitutionVal != null)) {
				if ((!constitutionVal.equals("proprietary"))
						&& (!constitutionVal.equals("partnership"))
						&& (!constitutionVal.equals("private"))
						&& (!constitutionVal.equals("public"))) {
					dynaActionForm.set("constitutionOther", constitutionVal);
					dynaActionForm.set("constitution", "Others");
				} else {
					dynaActionForm.set("constitution", constitutionVal);
				}
			}

			String legalIDString = ssiDetails.getCpLegalID();
			if ((legalIDString != null) && (!legalIDString.equals(""))) {
				if ((!legalIDString.equals("VoterIdentityCard"))
						&& (!legalIDString.equals("RationCardnumber"))
						&& (!legalIDString.equals("PASSPORT"))
						&& (!legalIDString.equals("Driving License"))) {
					dynaActionForm.set("otherCpLegalID", legalIDString);
					dynaActionForm.set("cpLegalID", "Others");
				} else {
					dynaActionForm.set("cpLegalID", legalIDString);
				}
			}

			ArrayList socialList = getSocialCategory();
			dynaActionForm.set("socialCategoryList", socialList);
 
			ArrayList industryNatureList = admin.getAllIndustryNature();
			dynaActionForm.set("industryNatureList", industryNatureList);

			String industryNature = ssiDetails.getIndustryNature();
			
			if ((industryNature != null) && (!industryNature.equals(""))
					&& (!industryNature.equals("OTHERS"))) {
				ArrayList industrySectors = admin
						.getIndustrySectors(industryNature);
				dynaActionForm.set("industrySectors", industrySectors);
			} else {
				ArrayList industrySectors = new ArrayList();
				String industrySector = ssiDetails.getIndustrySector();
				industrySectors.add(industrySector);
				dynaActionForm.set("industrySectors", industrySectors);
			}
			states = null;
			districts = null;

		}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			LogClass.StepWritter("Exception in modifyBorrowerDetails method "+e.getMessage());
			LogClass.writeExceptionOnFile(e);
		}
		return mapping.findForward(forward);
	}


    public ActionForward showBorrowerDetailsLink(ActionMapping mapping, 
                                                 ActionForm form, 
                                                 HttpServletRequest request, 
                                                 HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "showBorrowerDetailsLink", "Entered");

        HttpSession session = request.getSession(false);

        GMActionForm gmActionForm = (GMActionForm)form;

        GMProcessor gmProcessor = new GMProcessor();
        BorrowerDetails borrowerDetails = null;

        String formFlag = request.getParameter("formFlag");

        Log.log(4, "GMAction", "showBorrowerDetailsLink", 
                "formFlag :" + formFlag);

        String memberId = null;

        if (formFlag.equalsIgnoreCase("periodic")) {
            memberId = gmActionForm.getMemberId();
        } else if (formFlag.equalsIgnoreCase("closure")) {
            Log.log(4, "GMAction", "showBorrowerDetailsLink", "closure");
            memberId = (String)session.getAttribute("closureMemberId");
            Log.log(4, "GMAction", "showBorrowerDetailsLink", 
                    "memberId :" + memberId);
        } else if (formFlag.equalsIgnoreCase("schedule")) {
            memberId = (String)session.getAttribute("scheduleMemberId");
            Log.log(4, "GMAction", "showBorrowerDetailsLink", "Schedule");
            Log.log(4, "GMAction", "showBorrowerDetailsLink", 
                    "memberId :" + memberId);
        } else if (formFlag.equalsIgnoreCase("clNotPaid")) {
            Log.log(4, "GMAction", "showBorrowerDetailsLink", 
                    "inside clNot Paid: ");
            memberId = (String)session.getAttribute("clNotPaid");
            Log.log(4, "GMAction", "showBorrowerDetailsLink", 
                    "memberId :" + memberId);
        }

        String borrowerId = request.getParameter("bidLink");

        Log.log(4, "GMAction", "showBorrowerDetailsLink", 
                "borrowerId :" + borrowerId);

        borrowerDetails = 
                gmProcessor.viewBorrowerDetails(memberId, borrowerId, 0);

        gmActionForm.setBorrowerDetails(borrowerDetails);

        Log.log(4, "GMAction", "showBorrowerDetailsLink", "Exited");

        return mapping.findForward("success");
    }

    private ArrayList getSocialCategory() {
        ArrayList socialCategoryList = null;
        try {
            Administrator admin = new Administrator();
            socialCategoryList = admin.getAllSocialCategories();
            admin = null;
        } catch (Exception e) {
            Log.log(2, "GMAction", "getSocialCategoryList", e.getMessage());
            Log.logException(e);
        }

        return socialCategoryList;
    }

    public ActionForward getDistricts(ActionMapping mapping, ActionForm form, 
                                      HttpServletRequest request, 
                                      HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "getAllDistricts", "Entered");

        DynaActionForm dynaActionForm = (DynaActionForm)form;

        Administrator admin = new Administrator();
        String state = (String)dynaActionForm.get("state");

        ArrayList districts = admin.getAllDistricts(state);
        dynaActionForm.set("districts", districts);

        Log.log(4, "GMAction", "getAllDistricts", "Exited");

        districts = null;

        request.setAttribute("GM_FOCUS_FIELD", "GMDistrict");

        return mapping.findForward("success");
    }

    public ActionForward getIndustrySectors(ActionMapping mapping, 
                                            ActionForm form, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "getIndustrySectors", "Entered");

        DynaActionForm dynaActionForm = (DynaActionForm)form;

        Administrator admin = new Administrator();
        String industryNature = (String)dynaActionForm.get("industryNature");

        ArrayList industrySectors = admin.getIndustrySectors(industryNature);
        dynaActionForm.set("industrySectors", industrySectors);

        Log.log(4, "GMAction", "getIndustrySectors", "Exited");

        industrySectors = null;

        request.setAttribute("GM_FOCUS_FIELD", "GMSector");

        return mapping.findForward("success");
    }

    public ActionForward saveBorrowerDetails(ActionMapping mapping, 
                                             ActionForm form, 
                                             HttpServletRequest request, 
                                             HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "BorrowerDetailsSaved", "Entered");

        DynaActionForm dynaActionForm = (DynaActionForm)form;

        int borrowerRefNo = 
            ((Integer)dynaActionForm.get("borrowerRefNo")).intValue();
        GMProcessor gmProcessor = new GMProcessor();
        SSIDetails ssiDetails = new SSIDetails();
        BorrowerDetails borrowerDetails = new BorrowerDetails();
        String message = "Borrower Details Saved Successfully";
        BeanUtils.populate(ssiDetails, dynaActionForm.getMap());
        ssiDetails.setBorrowerRefNo(borrowerRefNo);

        String constitutionValue = 
            (String)dynaActionForm.get("constitutionOther");
        if (dynaActionForm.get("constitution").equals("Others")) {
            ssiDetails.setConstitution(constitutionValue);
        }

        String districtOthersValue = 
            (String)dynaActionForm.get("districtOthers");
        Log.log(5, "GMAction", "BorrowerDetailsSaved", 
                "dist other val " + districtOthersValue);
        if (dynaActionForm.get("district").equals("Others")) {
            Log.log(5, "GMAction", "BorrowerDetailsSaved", 
                    "dist other val " + districtOthersValue);
            ssiDetails.setDistrict(districtOthersValue);
        }

        String otherLegalIdValue = 
            (String)dynaActionForm.get("otherCpLegalID");
        if (dynaActionForm.get("cpLegalID").equals("Others")) {
            ssiDetails.setCpLegalID(otherLegalIdValue);
        }
        borrowerDetails.setSsiDetails(ssiDetails);
        BeanUtils.populate(borrowerDetails, dynaActionForm.getMap());
        User user = getUserInformation(request);
        String userId = user.getUserId();
        Log.log(5, "GMAction", "BorrowerDetailsSaved", 
                "ref no" + borrowerDetails.getSsiDetails().getBorrowerRefNo());
        gmProcessor.updateBorrowerDetails(borrowerDetails, userId);

        request.setAttribute("message", message);
        Log.log(4, "GMAction", "BorrowerDetailsSaved", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward getMemberForShiftingrequest(ActionMapping mapping, 
                                                     ActionForm form, 
                                                     HttpServletRequest request, 
                                                     HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "getMemberForShiftingrequest", "Entered");

        User user = getUserInformation(request);
        String bankId = user.getBankId();

        HttpSession session = request.getSession(false);

        GMActionForm gmActionForm = (GMActionForm)form;
        gmActionForm.setMemberIdForShifting("");
        gmActionForm.setBorrowerIdForShifting("");
        gmActionForm.setCgpanForShifting("");
        gmActionForm.setMemberIdToShift("");

        if (bankId.equalsIgnoreCase("0000")) {
            gmActionForm.setSelectMember("");

            session.setAttribute("TARGET_URL", 
                                 "selectGMMember.do?method=getShiftingCgpans");

            return mapping.findForward("memberInfo");
        }

        request.setAttribute("pageValue", "1");

        Log.log(4, "GMAction", "getMemberForShiftingrequest", "Exited");

        getShiftingCgpans(mapping, form, request, response);

        return mapping.findForward("danSummary");
    }

    public ActionForward getShiftingCgpans(ActionMapping mapping, 
                                           ActionForm form, 
                                           HttpServletRequest request, 
                                           HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "getShiftingCgpans", "Entered");

        User user = getUserInformation(request);
        String bankId = user.getBankId();
        String zoneId = user.getZoneId();
        String branchId = user.getBranchId();
        String memberId = "";

        GMActionForm gmActionForm = (GMActionForm)form;

        HttpSession session = request.getSession(false);

        Log.log(5, "GMAction", "getShiftingCgpans", "Bank Id : " + bankId);
        Log.log(5, "GMAction", "getShiftingCgpans", "Zone Id : " + zoneId);
        Log.log(5, "GMAction", "getShiftingCgpans", "Branch Id : " + branchId);

        if (bankId.equals("0000")) {
            memberId = gmActionForm.getSelectMember();

            if ((memberId == null) || (memberId.equals(""))) {
                memberId = gmActionForm.getMemberId();
            }

            Log.log(5, "GMAction", "getShiftingCgpans", "mliId = " + memberId);

            if ((memberId == null) || (memberId.equals(""))) {
                session.setAttribute("TARGET_URL", 
                                     "selectGMMember.do?method=getShiftingCgpans");

                return mapping.findForward("memberInfo");
            }

            bankId = memberId.substring(0, 4);
            zoneId = memberId.substring(4, 8);
            branchId = memberId.substring(8, 12);

            ClaimsProcessor cpProcessor = new ClaimsProcessor();
            Vector memberIds = cpProcessor.getAllMemberIds();
            if (!memberIds.contains(memberId)) {
                throw new NoMemberFoundException("The Member ID does not exist");
            }

        }

        Log.log(5, "GMAction", "getShiftingCgpans", 
                "Selected Bank Id,zone and branch ids : " + bankId + "," + 
                zoneId + "," + branchId);

        GMProcessor gmProcessor = new GMProcessor();
        ArrayList danSummaries = 
            gmProcessor.displayShiftingCgpans(bankId, zoneId, branchId);

        Log.log(5, "GMAction", "getShiftingCgpans", 
                "dan summary size : " + danSummaries.size());

        if (danSummaries.size() == 0) {
            gmActionForm.setSelectMember(null);

            throw new MissingDANDetailsException("No CGPANs available for Shifting");
        }

        boolean isDanAvailable = false;
        for (int i = 0; i < danSummaries.size(); i++) {
            DANSummary danSummary = (DANSummary)danSummaries.get(i);
            if (danSummary.getAmountDue() != danSummary.getAmountPaid()) {
                isDanAvailable = true;
                break;
            }
        }
        if (!isDanAvailable) {
            gmActionForm.setSelectMember(null);

            throw new MissingDANDetailsException("No CGPANs available for Shifting");
        }
        gmActionForm.setDanSummaries(danSummaries);
        ArrayList memberList = (ArrayList)getMemberList(bankId);
        memberList.remove(memberId);
        gmActionForm.setMemberList(memberList);
        gmActionForm.setBankId(bankId);
        gmActionForm.setZoneId(zoneId);
        gmActionForm.setBranchId(branchId);

        Log.log(4, "GMAction", "getShiftingCgpans", "Exited");
        if (gmActionForm.getSelectMember() != null) {
            gmActionForm.setMemberId(gmActionForm.getSelectMember());
        } else {
            gmActionForm.setMemberId(bankId + zoneId + branchId);
        }

        gmActionForm.setSelectMember(null);

        return mapping.findForward("danSummary2");
    }

    private Collection getMemberList(String bankId) {
        ArrayList memberList = null;
        try {
            Administrator admin = new Administrator();
            memberList = admin.getMemberList(bankId);
            admin = null;
        } catch (Exception e) {
            Log.log(2, "GMAction", "getMemberList", e.getMessage());

            Log.logException(e);
        }

        return memberList;
    }

    public ActionForward submitCgpanRequests(ActionMapping mapping, 
                                             ActionForm form, 
                                             HttpServletRequest request, 
                                             HttpServletResponse response) throws Exception {
        GMActionForm gmPeriodicInfoForm = (GMActionForm)form;

        Map clearCgpan = gmPeriodicInfoForm.getClearCgpan();
        Set clearCgpanSet = clearCgpan.keySet();
        Iterator clearCgpanIterator = clearCgpanSet.iterator();
        String oldMemberId = gmPeriodicInfoForm.getMemberId();

        while (clearCgpanIterator.hasNext()) {
            String key = (String)clearCgpanIterator.next();
            String newMemberId = (String)clearCgpan.get(key);
            if (!newMemberId.equals("")) {
                request.setAttribute("message", 
                                     "<b>The Request for Cgpan Shifting make successfully.<b><br>");
            }
        }
        clearCgpan.clear();

        return mapping.findForward("success");
    }

    public ActionForward getMemberForShifting(ActionMapping mapping, 
                                              ActionForm form, 
                                              HttpServletRequest request, 
                                              HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "getMemberForShifting", "Entered");

        GMActionForm gmActionForm = (GMActionForm)form;
        gmActionForm.setMemberIdForShifting("");
        gmActionForm.setBorrowerIdForShifting("");
        gmActionForm.setCgpanForShifting("");
        gmActionForm.setMemberIdToShift("");

        Log.log(4, "GMAction", "getMemberForShifting", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward shiftCgpanOrBorrower(ActionMapping mapping, 
                                              ActionForm form, 
                                              HttpServletRequest request, 
                                              HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "shiftCgpanOrBorrower", "Entered");

        GMActionForm gmActionForm = (GMActionForm)form;
        GMProcessor gmProcessor = new GMProcessor();

        Vector noOfCgpan = null;
        String cgpan = "";
        String message = "Shift Borrower Process Completed";
        ClaimsProcessor claimsProcessor = new ClaimsProcessor();
        String borrowerId = null;
        String cgpanToShift = gmActionForm.getCgpanForShifting();

        if ((!cgpanToShift.equals("")) && (cgpanToShift != null)) {
            borrowerId = claimsProcessor.getBorowwerForCGPAN(cgpanToShift);

            Log.log(5, "GMAction", "shiftCgpanOrBorrower", 
                    "Bid for the cgpan :" + cgpanToShift + " ," + borrowerId);
        } else {
            borrowerId = gmActionForm.getBorrowerIdForShifting().toUpperCase();

            Log.log(5, "GMAction", "shiftCgpanOrBorrower", 
                    "Bid :" + borrowerId);
        }

        String srcMemberId = gmActionForm.getMemberIdForShifting();
        String srcBankId = srcMemberId.substring(0, 4);

        Log.log(5, "GMAction", "shiftCgpanOrBorrower", 
                "src member id:" + srcMemberId);

        Vector memberids = claimsProcessor.getAllMemberIds();
        if (!memberids.contains(srcMemberId)) {
            throw new NoMemberFoundException("Form Member Id :" + srcMemberId + 
                                             " does not exist in the database.");
        }

        ArrayList borrowerIds = new ArrayList();
        borrowerIds = gmProcessor.getBorrowerIds(srcMemberId);
        String memberIdforCgpan = "";
        memberIdforCgpan = gmProcessor.getMemIdforCgpan(cgpanToShift);

        String allocationStatus = "";
        allocationStatus = 
                gmProcessor.getallocationStatusforCgpan(cgpanToShift);

        if (!borrowerIds.contains(borrowerId)) {
            if (allocationStatus.equals("Y")) {
                throw new NoDataException("Borrower ID " + borrowerId + 
                                          " is not among the borrower" + 
                                          " Ids for the Member Id :" + 
                                          srcMemberId + 
                                          ". The member ID for selected CGPAN " + 
                                          cgpanToShift + " is " + 
                                          memberIdforCgpan + 
                                          ". DAN(s) for the selected CGPAN are pending" + 
                                          "for appropriation. Please appropriate and proceed");
            }

            throw new NoDataException("Borrower ID " + borrowerId + 
                                      " is not among the borrower" + 
                                      " Ids for the Member Id :" + 
                                      srcMemberId + 
                                      ". The member ID for selected CGPAN " + 
                                      cgpanToShift + " is " + 
                                      memberIdforCgpan + ".");
        }

        if (allocationStatus.equals("Y")) {
            throw new NoDataException("DAN(s) for the selected CGPAN are pendingfor appropriation. Please appropriate and proceed");
        }

        String memberId = gmActionForm.getMemberIdToShift();
        String destBankId = memberId.substring(0, 4);

        Log.log(5, "GMAction", "shiftCgpanOrBorrower", 
                "dest member Id:" + memberId);

        if (!memberids.contains(memberId)) {
            throw new NoMemberFoundException("To Member Id :" + memberId + 
                                             " does not exist in the database.");
        }

        if (srcMemberId.equals(memberId)) {
            throw new DatabaseException("The From Member ID should not be same as the To Member Id");
        }

        noOfCgpan = gmProcessor.getCGPANs(borrowerId);
        int cgpansSize = noOfCgpan.size();

        Log.log(5, "GMAction", "shiftCgpanOrBorrower", 
                "no of pans for the borrower:" + cgpansSize);

        ArrayList cgpans = new ArrayList();

        for (int i = 0; i < cgpansSize; i++) {
            HashMap cgpanDtls = (HashMap)noOfCgpan.get(i);
            cgpan = (String)cgpanDtls.get("CGPAN");

            Log.log(5, "GMAction", "shiftCgpanOrBorrower", 
                    "inside loop - the cgpan is :" + cgpan);
            if (cgpan != null) {
                cgpans.add(cgpan);
            }
        }

        User user = getUserInformation(request);
        String userId = user.getUserId();

        Log.log(5, "GMAction", "shiftCgpanOrBorrower", "user id:" + userId);

        int size = cgpans.size();

        Log.log(5, "GMAction", "shiftCgpanOrBorrower", "cgpans size :" + size);
        for (int i = 0; i < size; i++) {
            String cgpanForBorrower = (String)cgpans.get(i);

            Log.log(5, "GMAction", "shiftCgpanOrBorrower", 
                    " shifted cgpan :" + cgpanForBorrower);
            gmProcessor.shiftCgpanBorrower(srcMemberId, userId, 
                                           cgpanForBorrower, memberId);
        }

        Log.log(4, "GMAction", "shiftingDone", "Exited");

        noOfCgpan = null;
        cgpans = null;

        request.setAttribute("message", message);

        return mapping.findForward("success");
    }

    public ActionForward BorrowerIdRecovery(ActionMapping mapping, 
                                            ActionForm form, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "BorrowerIdRecovery", "Entered");

        GMActionForm gmActionForm = (GMActionForm)form;

        GMProcessor gmProcessor = new GMProcessor();
        User user = getUserInformation(request);
        String bankId = user.getBankId();
        String zoneId = user.getZoneId();
        String branchId = user.getBranchId();
        String memberId = bankId.concat(zoneId).concat(branchId);

        gmActionForm.setPeriodicBankId(bankId);
        if (bankId.equals("0000")) {
            memberId = "";
        }
        gmActionForm.setMemberId(memberId);

        gmActionForm.setBorrowerId("");
        gmActionForm.setCgpan("");
        gmActionForm.setBorrowerName("");

        Log.log(4, "GMAction", "BorrowerIdRecovery", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward getBorrowerIdForUpdateRecovery(ActionMapping mapping, 
                                                        ActionForm form, 
                                                        HttpServletRequest request, 
                                                        HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "getBorrowerIdForUpdateRecovery", "Entered");

        GMActionForm gmActionForm = (GMActionForm)form;

        GMProcessor gmProcessor = new GMProcessor();
        User user = getUserInformation(request);
        String bankId = user.getBankId();
        String zoneId = user.getZoneId();
        String branchId = user.getBranchId();
        String memberId = bankId.concat(zoneId).concat(branchId);

        gmActionForm.setPeriodicBankId(bankId);
        if (bankId.equals("0000")) {
            memberId = "";
        }
        gmActionForm.setMemberId(memberId);
        gmActionForm.setBorrowerId("");
        gmActionForm.setCgpan("");
        gmActionForm.setBorrowerName("");

        Log.log(4, "GMAction", "getBorrowerIdForUpdateRecovery", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward showRecoveryDetails(ActionMapping mapping, 
                                             ActionForm form, 
                                             HttpServletRequest request, 
                                             HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "showRecoveryDetails", "Entered");
        GMActionForm gmActionForm = (GMActionForm)form;
        GMProcessor gmProcessor = new GMProcessor();
        HttpSession session = request.getSession(false);
        String srcmainmenu = (String)session.getAttribute("mainMenu");

        if (srcmainmenu.equals(MenuOptions.getMenu("CP_CLAIM_FOR"))) {
            String memberId = (String)session.getAttribute("MEMBERID");
            String borrowerId = (String)session.getAttribute("BORROWERID");
            gmActionForm.setMemberId(memberId);
            gmActionForm.setBorrowerId(borrowerId);
        } else if ((srcmainmenu.equals(MenuOptions.getMenu("GM_PERIODIC_INFO"))) && 
                   (session.getAttribute("subMenuItem").equals(MenuOptions.getMenu("GM_PI_RECOVERY_DETAILS")))) {
            Log.log(5, "GMAction", "showRecoveryDetails", 
                    "subMenuItem inside  if " + 
                    session.getAttribute("subMenuItem"));

            String memberId = gmActionForm.getMemberId();

            ClaimsProcessor processor = new ClaimsProcessor();
            Vector memberids = processor.getAllMemberIds();
            if (!memberids.contains(memberId)) {
                throw new NoMemberFoundException("Member Id :" + memberId + 
                                                 " does not exist in the database.");
            }

            ArrayList borrowerIds = new ArrayList();
            borrowerIds = gmProcessor.getBorrowerIds(memberId);
            String borrowerId = gmActionForm.getBorrowerId().toUpperCase();
            String cgpan = gmActionForm.getCgpan().toUpperCase();
            String borrowerName = gmActionForm.getBorrowerName();

            if ((!borrowerId.equals("")) && (cgpan.equals("")) && 
                (borrowerName.equals(""))) {
                if (!borrowerIds.contains(borrowerId)) {
                    gmActionForm.setBorrowerId("");
                    throw new NoDataException(borrowerId + 
                                              " is not among the borrower" + 
                                              " Ids for the Member Id :" + 
                                              memberId + 
                                              ". Please enter correct" + 
                                              " Member Id and Borrower Id.");
                }

            } else if ((!cgpan.equals("")) && (borrowerId.equals("")) && 
                       (borrowerName.equals(""))) {
                int type = 1;

                borrowerId = processor.getBorowwerForCGPAN(cgpan);
                Log.log(5, "GMAction", "showOutstandingDetails", 
                        " Bid For Pan - " + borrowerId);
                if (!borrowerIds.contains(borrowerId)) {
                    throw new NoDataException(cgpan + 
                                              "is not a valid Cgpan for " + 
                                              "the Member Id :" + memberId + 
                                              ". Please enter correct Cgpan");
                }

                gmActionForm.setBorrowerId(borrowerId);
            } else if ((!borrowerName.equals("")) && (borrowerId.equals("")) && 
                       (cgpan.equals(""))) {
                int type = 2;
                ArrayList bIdForBorrowerName = 
                    gmProcessor.getBorrowerIdForBorrowerName(borrowerName, 
                                                             memberId);
                if ((bIdForBorrowerName == null) || 
                    (bIdForBorrowerName.size() == 0)) {
                    throw new NoDataException("There are no Borrower Ids for this member");
                }

                session.setAttribute("displayFlag", "6");
                gmActionForm.setBorrowerIds(bIdForBorrowerName);

                return mapping.findForward("bidList");
            }

        }

        gmActionForm.resetWhenRequired(mapping, request);
        Log.log(4, "GMAction", "showRecoveryDetails", "Exited");

        return mapping.findForward("insertRecovery");
    }

    public ActionForward modifyRecoveryDetails(ActionMapping mapping, 
                                               ActionForm form, 
                                               HttpServletRequest request, 
                                               HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "modifyRecoveryDetails", "Entered");

        GMActionForm gmActionForm = (GMActionForm)form;

        HttpSession session = request.getSession(false);

        GMProcessor gmProcessor = new GMProcessor();

        String memberId = gmActionForm.getMemberId();

        ClaimsProcessor processor = new ClaimsProcessor();
        Vector memberids = processor.getAllMemberIds();
        if (!memberids.contains(memberId)) {
            throw new NoMemberFoundException("Member Id :" + memberId + 
                                             " does not exist in the database.");
        }

        ArrayList borrowerIds = new ArrayList();
        borrowerIds = gmProcessor.getBorrowerIds(memberId);
        String borrowerId = gmActionForm.getBorrowerId().toUpperCase();
        String cgpan = gmActionForm.getCgpan().toUpperCase();
        String borrowerName = gmActionForm.getBorrowerName();

        if ((!borrowerId.equals("")) && (cgpan.equals("")) && 
            (borrowerName.equals(""))) {
            if (!borrowerIds.contains(borrowerId)) {
                gmActionForm.setBorrowerId("");
                throw new NoDataException(borrowerId + 
                                          " is not among the borrower" + 
                                          " Ids for the Member Id :" + 
                                          memberId + ". Please enter correct" + 
                                          " Member Id and Borrower Id.");
            }

        } else if ((!cgpan.equals("")) && (borrowerId.equals("")) && 
                   (borrowerName.equals(""))) {
            int type = 1;

            borrowerId = processor.getBorowwerForCGPAN(cgpan);
            Log.log(5, "GMAction", "showOutstandingDetails", 
                    " Bid For Pan - " + borrowerId);
            if (!borrowerIds.contains(borrowerId)) {
                throw new NoDataException(cgpan + "is not a valid Cgpan for " + 
                                          "the Member Id :" + memberId + 
                                          ". Please enter correct Cgpan");
            }

            gmActionForm.setBorrowerId(borrowerId);
        } else if ((!borrowerName.equals("")) && (borrowerId.equals("")) && 
                   (cgpan.equals(""))) {
            int type = 2;
            ArrayList bIdForBorrowerName = 
                gmProcessor.getBorrowerIdForBorrowerName(borrowerName, 
                                                         memberId);
            if ((bIdForBorrowerName == null) || 
                (bIdForBorrowerName.size() == 0)) {
                throw new NoDataException("There are no Borrower Ids for this member");
            }

            session.setAttribute("displayFlag", "7");
            gmActionForm.setBorrowerIds(bIdForBorrowerName);

            return mapping.findForward("bidList");
        }

        Log.log(4, "GMAction", "modifyRecoveryDetails", "Exited");

        ArrayList recoveryDetails = gmProcessor.getRecoveryDetails(borrowerId);

        Map recoveryMap = new HashMap();
        String recId = null;
        for (int i = 0; i < recoveryDetails.size(); i++) {
            Recovery reco = (Recovery)recoveryDetails.get(i);
            recId = reco.getRecoveryNo();
            recoveryMap.put(recId, reco);
        }

        gmActionForm.setRecoveryDetails(recoveryMap);

        return mapping.findForward("updateRecovery");
    }

    public ActionForward updateRecovery(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "updateRecovery", "Entered");

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        GMActionForm gmActionForm = (GMActionForm)form;

        GMProcessor gmProcessor = new GMProcessor();

        Map recMap = gmActionForm.getRecoveryDetails();

        String recId = request.getParameter("recoId");

        Recovery recovery = (Recovery)recMap.get(recId);
        HttpSession session = request.getSession(false);

        session.setAttribute("recoIdFromDataBase", recId);

        double amt = recovery.getAmountRecovered();
        CustomisedDate custom = new CustomisedDate();
        custom.setDate(recovery.getDateOfRecovery());

        double legalcharge = recovery.getLegalCharges();

        String rem = recovery.getRemarks();
        String isRecoveryBySaleOfAsset = recovery.getIsRecoveryBySaleOfAsset();
        String detailsOfAssetSold = recovery.getDetailsOfAssetSold();
        String isRecoveryByOTS = recovery.getIsRecoveryByOTS();

        gmActionForm.setDateOfRecovery(custom);
        gmActionForm.setAmountRecovered(amt);
        gmActionForm.setLegalCharges(legalcharge);

        gmActionForm.setRemarks(rem);
        gmActionForm.setIsRecoveryBySaleOfAsset(isRecoveryBySaleOfAsset);
        gmActionForm.setDetailsOfAssetSold(detailsOfAssetSold);
        gmActionForm.setIsRecoveryByOTS(isRecoveryByOTS);

        Log.log(4, "GMAction", "updateRecovery", "Exited");
        request.setAttribute("message", 
                             "Recovery Details updated Successfully.");

        return mapping.findForward("success");
    }

    public ActionForward saveRecoveryDetails(ActionMapping mapping, 
                                             ActionForm form, 
                                             HttpServletRequest request, 
                                             HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "saveRecoveryDetails", "Entered");
        HttpSession session = request.getSession(false);
        GMProcessor gmProcessor = new GMProcessor();
        ClaimsProcessor processor = new ClaimsProcessor();
        Recovery recovery = new Recovery();

        String recId = (String)session.getAttribute("recoIdFromDataBase");

        GMActionForm gmActionForm = (GMActionForm)form;
        String memberId = gmActionForm.getMemberId();

        java.util.Date recoveryDate = gmActionForm.getDateOfRecovery();
        double amtRecovered = gmActionForm.getAmountRecovered();
        double legalCharges = gmActionForm.getLegalCharges();
        String remarks = gmActionForm.getRemarks().trim();
        String saleOfAsset = gmActionForm.getIsRecoveryBySaleOfAsset();
        String detailsOfAsset = gmActionForm.getDetailsOfAssetSold().trim();
        String recoveryByOts = gmActionForm.getIsRecoveryByOTS();

        recovery.setRecoveryNo(recId);

        if (amtRecovered != 0.0D) {
            recovery.setAmountRecovered(amtRecovered);
        }

        if ((recoveryDate != null) && (!recoveryDate.toString().equals(""))) {
            recovery.setDateOfRecovery(recoveryDate);
        } else
            recovery.setDateOfRecovery(null);

        recovery.setDetailsOfAssetSold(detailsOfAsset);
        recovery.setIsRecoveryByOTS(recoveryByOts);
        recovery.setIsRecoveryBySaleOfAsset(saleOfAsset);
        recovery.setRemarks(remarks);

        if (legalCharges != 0.0D) {
            recovery.setLegalCharges(legalCharges);
        }

        String borrowerId = gmActionForm.getBorrowerId().toUpperCase();

        recovery.setCgbid(borrowerId);

        if (recoveryByOts.equals("Y")) {
            Log.log(4, "GMAction", "saveRecoveryDetails()", 
                    "Checking to see if OTS Details are available...");

            Vector otsdetails = processor.getOTSDetails(borrowerId);

            boolean willfulDefaulterFlagPresent = false;
            for (int i = 0; i < otsdetails.size(); i++) {
                HashMap map = (HashMap)otsdetails.elementAt(i);
                if (map != null) {
                    String willfuldefaulter = 
                        (String)map.get("WillfulDefaulter");

                    if (willfuldefaulter == null) {
                        willfulDefaulterFlagPresent = 
                                willfulDefaulterFlagPresent;
                    } else {
                        willfulDefaulterFlagPresent = 
                                (willfulDefaulterFlagPresent) || (1 != 0);
                    }
                }

            }

            if (!willfulDefaulterFlagPresent) {
                Log.log(4, "GMAction", "saveRecoveryDetails()", 
                        "Redirecting to the OTS Details Page...");
                session.setAttribute("mainMenu", 
                                     MenuOptions.getMenu("GM_PERIODIC_INFO"));

                session.setAttribute("MEMBERID", memberId);

                session.setAttribute("BORROWERID", borrowerId);
                session.setAttribute("RECOVERYOBJECT", recovery);

                return mapping.findForward("otsdetails");
            }
        }

        if ((recId != null) && (!recId.equals(""))) {
            gmProcessor.modifyRecoveryDetails(recovery);
            session.setAttribute("recoIdFromDataBase", "");
            request.setAttribute("message", 
                                 "Recovery details updated Successfully");

            return mapping.findForward("successmessage");
        }

        gmProcessor.addRecoveryDetails(recovery);

        Log.log(4, "GMAction", "saveRecoveryDetails", "Exited");
        String srcMenu = (String)session.getAttribute("mainMenu");

        String srcSubMenu = (String)session.getAttribute("subMenuItem");

        if ((srcMenu.equals(MenuOptions.getMenu("CP_CLAIM_FOR"))) && 
            (srcSubMenu.equals(MenuOptions.getMenu("CP_CLAIM_FOR_FIRST_INSTALLMENT")))) {
            return mapping.findForward("firstClaimDetails");
        }
        if ((srcMenu.equals(MenuOptions.getMenu("CP_CLAIM_FOR"))) && 
            (srcSubMenu.equals(MenuOptions.getMenu("CP_CLAIM_FOR_SECOND_INSTALLMENT")))) {
            return mapping.findForward("secondClaimDetails");
        }

        return mapping.findForward("success");
    }

    public ActionForward showAdditionalRecovery(ActionMapping mapping, 
                                                ActionForm form, 
                                                HttpServletRequest request, 
                                                HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "showAdditionalRecovery", "Entered");
        GMActionForm gmActionForm = (GMActionForm)form;

        String borrowerId = gmActionForm.getBorrowerId().toUpperCase();

        gmActionForm.setBorrowerId(borrowerId);
        gmActionForm.resetWhenRequired(mapping, request);
        Log.log(4, "GMAction", "showAdditionalRecovery", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward NpaDetailsGetBorrowerId(ActionMapping mapping, 
                                                 ActionForm form, 
                                                 HttpServletRequest request, 
                                                 HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "NpaDetailsGetBorrowerId", "Entered");

        GMActionForm gmActionForm = (GMActionForm)form;

        GMProcessor gmProcessor = new GMProcessor();
        User user = getUserInformation(request);
        String bankId = user.getBankId();
        String zoneId = user.getZoneId();
        String branchId = user.getBranchId();
        String memberId = bankId.concat(zoneId).concat(branchId);

        gmActionForm.setPeriodicBankId(bankId);
        if (bankId.equals("0000")) {
            memberId = "";
        }
        gmActionForm.setMemberId(memberId);

        gmActionForm.setBorrowerId("");
        gmActionForm.setCgpan("");
        gmActionForm.setBorrowerName("");

        Log.log(4, "GMAction", "NpaDetailsGetBorrowerId", "Exited");

        return mapping.findForward("success");
    }

    /*public ActionForward showNPADetails(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws Exception {
        //     System.out.println("---entered in showNPADetails------");
        Log.log(Log.INFO, "GMAction", "showNPADetails", "Entered");

        GMActionForm gmActionForm = (GMActionForm)form;
        gmActionForm.getRecProcedures().clear();
        String memberId = gmActionForm.getMemberId();
        String borrowerId = (gmActionForm.getBorrowerId()).toUpperCase();
        String cgpan = (gmActionForm.getCgpan());
        String borrowerName = gmActionForm.getBorrowerName();

        int type;

        GMProcessor gmProcessor = new GMProcessor();
        ArrayList borrowerIds = new ArrayList();
        borrowerIds = gmProcessor.getBorrowerIds(memberId);
        ClaimsProcessor processor = new ClaimsProcessor();
        Vector memberids = processor.getAllMemberIds();
        if (!(memberids.contains(memberId))) {
            throw new NoMemberFoundException("Member Id :" + memberId + 
                                             " does not exist in the database.");
        }

        HttpSession session = request.getSession(false);

        if ((borrowerId != null) && (!borrowerId.equals(""))) {
            if (!(borrowerIds.contains(borrowerId))) {
                gmActionForm.setBorrowerId("");

                throw new NoDataException(borrowerId + 
                                          " is not among the borrower" + 
                                          " Ids for the Member Id :" + 
                                          memberId + ". Please enter correct" + 
                                          " Member Id and Borrower Id.");
            }
            
            //diksha
            // Checking if the borrower has paid guarantee fee for all his loan
   			// accounts
   			Log.log(Log.INFO, "GMAction", "showNpaDetails",
   					"retriving the details to check if member has paid guarantee fee ......");
   			Vector gfeeDtlsVector = processor.getLockInDetails(borrowerId);
   			Vector gfeePaidCGPANS = new Vector();
   			boolean gfeepaid = false;
   			// //System.out.println("Size of the vector :" +
   			// gfeeDtlsVector.size());
   			for (int i = 0; i < gfeeDtlsVector.size(); i++) {
   				HashMap gfeedtl = (HashMap) gfeeDtlsVector.elementAt(i);
   				if (gfeedtl != null) {
   					Log.log(Log.INFO, "GMAction", "showNpaDetails",
   							"Printing gfeedtl :" + gfeedtl);
   					// //System.out.println("Printing the hashmap :" + gfeedtl);
   					String cgpn = (String) gfeedtl
   							.get(ClaimConstants.CLM_CGPAN);
   					java.util.Date gfeedate = (java.util.Date) gfeedtl
   							.get(ClaimConstants.CLM_GUARANTEE_START_DT);
   					// //System.out.println("cgpan:"+cgpn);
   					// //System.out.println("gfeedate:"+gfeedate);
   					Log.log(Log.INFO, "GMAction", "showNpaDetails",
   							"------> Printing gfeedate :" + gfeedate);
   					if (gfeedate != null) {
   						gfeepaid = true;
   						if (!gfeePaidCGPANS.contains(cgpn)) {
   							gfeePaidCGPANS.addElement(cgpn);
   						}
   					} else {
   						gfeepaid = false;
   					}
   					// Clearing the HashMap
   					// gfeedtl.clear();
   					gfeedtl = null;
   				}
   			}
   			gfeeDtlsVector = null;

			if (gfeePaidCGPANS.size() == 0) {
				throw new NoDataException(
						" Kindly update the disbursement details online before proceeding for NPA lodgment. The menu is Guarantee Maintenance-->Periodic Info--> Disbursement details. Please make sure Guarantee Fee has been paid and Disbursement Details, if any, are available for the CGPAN(s) of the Borrower.");
			}
               
       		// Check if Disbursement details are avaiable
   			Log.log(Log.INFO, "GMAction", "showNpaDetails",
   					"retrieving the details to check if disbursement details exist......");
   			Hashtable disbursementdetails = processor
   					.isDisbursementDetailsAvl(borrowerId);
   			System.out.println("disbursementdetails==="+disbursementdetails);
   			if (disbursementdetails == null) {				
   				 throw new NoDataException(
   				 "No Disbursement Details available.Please go to GuaranteeMaintenance-->PeriodicInfo-->Disbursement Details.");
   				 
   			}
   			if (disbursementdetails.size() == 0) {				
   				  throw new NoDataException(
   				  "No Disbursement Details available.Please go to GuaranteeMaintenance-->PeriodicInfo-->Disbursement Details.");
   				 
   			}
   			Enumeration cgpankeys = disbursementdetails.keys();
   			String message = "";
   			boolean isDisbursemntDtlsAvl = true;
   			while (cgpankeys.hasMoreElements()) {
   				String cgpan1 = (String) cgpankeys.nextElement();
   				String gfeecgpan = null;
   				for (int i = 0; i < gfeePaidCGPANS.size(); i++) {
   					gfeecgpan = (String) gfeePaidCGPANS.elementAt(i);
   					if ((gfeecgpan != null) && (cgpan != null)) {
   						if (gfeecgpan.equals(cgpan1)) {
   							if ("TC".equals(cgpan1.substring(cgpan1.length() - 2))) {
   								String status = (String) disbursementdetails
   										.get(cgpan1);
   								if (!(status
   										.equals(ClaimConstants.DISBRSMNT_YES_FLAG))) {
   									isDisbursemntDtlsAvl = false;
   								}
   							}
   						}
   					}
   				}
   			}
   			if (!isDisbursemntDtlsAvl) {
   				Log.log(Log.INFO,
   						"ClaimAction",
   						"forwardToNextPage",
   						"Error: Final Disbursement details not available for those CGPAN(s) whose Guarantee Fee has been paid.");
   				
   				  throw new NoDataException(
   				  "Final Disbursement details not available for those CGPAN(s) whose Guarantee Fee has been paid. Please furnish Final Disbursement Details");
   				 
   			}

   			// disbursementdetails.clear();
   			// disbursementdetails = null;
   			
   			//end

        } else if ((cgpan != null) && (!cgpan.equals(""))) {
            type = 1;

            borrowerId = processor.getBorowwerForCGPAN(cgpan);
            Log.log(Log.INFO, "GMAction", "showOutstandingDetails", 
                    " Bid For Pan - " + borrowerId);
            if (!(borrowerIds.contains(borrowerId))) {
                throw new NoDataException(cgpan + "is not a valid Cgpan for " + 
                                          "the Member Id :" + memberId + 
                                          ". Please enter correct Cgpan");
            }
            gmActionForm.setBorrowerId(borrowerId);
            
            
            
            //diksha
            // Checking if the borrower has paid guarantee fee for all his loan
   			// accounts
   			Log.log(Log.INFO, "GMAction", "showNpaDetails",
   					"retriving the details to check if member has paid guarantee fee ......");
   			Vector gfeeDtlsVector = processor.getLockInDetails(borrowerId);
   			Vector gfeePaidCGPANS = new Vector();
   			boolean gfeepaid = false;
   			// //System.out.println("Size of the vector :" +
   			// gfeeDtlsVector.size());
   			for (int i = 0; i < gfeeDtlsVector.size(); i++) {
   				HashMap gfeedtl = (HashMap) gfeeDtlsVector.elementAt(i);
   				if (gfeedtl != null) {
   					Log.log(Log.INFO, "GMAction", "showNpaDetails",
   							"Printing gfeedtl :" + gfeedtl);
   					// //System.out.println("Printing the hashmap :" + gfeedtl);
   					String cgpn = (String) gfeedtl
   							.get(ClaimConstants.CLM_CGPAN);
   					java.util.Date gfeedate = (java.util.Date) gfeedtl
   							.get(ClaimConstants.CLM_GUARANTEE_START_DT);
   					// //System.out.println("cgpan:"+cgpn);
   					// //System.out.println("gfeedate:"+gfeedate);
   					Log.log(Log.INFO, "GMAction", "showNpaDetails",
   							"------> Printing gfeedate :" + gfeedate);
   					if (gfeedate != null) {
   						gfeepaid = true;
   						if (!gfeePaidCGPANS.contains(cgpn)) {
   							gfeePaidCGPANS.addElement(cgpn);
   						}
   					} else {
   						gfeepaid = false;
   					}
   					// Clearing the HashMap
   					// gfeedtl.clear();
   					gfeedtl = null;
   				}
   			}
   			gfeeDtlsVector = null;

			if (gfeePaidCGPANS.size() == 0) {
				throw new NoDataException(
						" Kindly update the disbursement details online before proceeding for NPA lodgment. The menu is Guarantee Maintenance-->Periodic Info--> Disbursement details. Please make sure Guarantee Fee has been paid and Disbursement Details, if any, are available for the CGPAN(s) of the Borrower.");
			}
			
       		// Check if Disbursement details are avaiable
   			Log.log(Log.INFO, "GMAction", "showNpaDetails",
   					"retrieving the details to check if disbursement details exist......");
   			Hashtable disbursementdetails = processor.isDisbursementDetailsAvl(borrowerId);
   			System.out.println("disbursementdetails==="+disbursementdetails);
   			if (disbursementdetails == null) {				
   				 throw new NoDataException(
   				 "No Disbursement Details available.Please go to GuaranteeMaintenance-->PeriodicInfo-->Disbursement Details.");
   				 
   			}
   			if (disbursementdetails.size() == 0) {				
   				  throw new NoDataException(
   				  "No Disbursement Details available.Please go to GuaranteeMaintenance-->PeriodicInfo-->Disbursement Details.");
   				 
   			}
   			Enumeration cgpankeys = disbursementdetails.keys();
   			String message = "";
   			boolean isDisbursemntDtlsAvl = true;
   			while (cgpankeys.hasMoreElements()) {
   				String cgpan1 = (String) cgpankeys.nextElement();
   				String gfeecgpan = null;
   				for (int i = 0; i < gfeePaidCGPANS.size(); i++) {
   					gfeecgpan = (String) gfeePaidCGPANS.elementAt(i);
   					if ((gfeecgpan != null) && (cgpan != null)) {
   						if (gfeecgpan.equals(cgpan1)) {
   							if ("TC".equals(cgpan1.substring(cgpan1.length() - 2))) {
   								String status = (String) disbursementdetails
   										.get(cgpan1);
   								if (!(status
   										.equals(ClaimConstants.DISBRSMNT_YES_FLAG))) {
   									isDisbursemntDtlsAvl = false;
   								}
   							}
   						}
   					}
   				}
   			}
   			if (!isDisbursemntDtlsAvl) {
   				Log.log(Log.INFO,
   						"ClaimAction",
   						"forwardToNextPage",
   						"Error: Final Disbursement details not available for those CGPAN(s) whose Guarantee Fee has been paid.");
   				
   				  throw new NoDataException(
   				  "Final Disbursement details not available for those CGPAN(s) whose Guarantee Fee has been paid. Please furnish Final Disbursement Details");
   				 
   			}

   			// disbursementdetails.clear();
   			// disbursementdetails = null;
   			
   			//end

        } else if ((borrowerName != null) && (!borrowerName.equals(""))) {
            type = 2;
            ArrayList bIdForBorrowerName = 
                gmProcessor.getBorrowerIdForBorrowerName(borrowerName, 
                                                         memberId);
            
            if (bIdForBorrowerName == null || bIdForBorrowerName.size() == 0) {
                throw new NoDataException("There are no Borrower Ids for this member");
            } else {
                session.setAttribute("displayFlag", "5");
                gmActionForm.setBorrowerIds(bIdForBorrowerName);

                return mapping.findForward("bidList");
            }
        }
        
        if(gmProcessor.getCountForCGPAN(cgpan) > 0)
        {
            String status = gmProcessor.getCgpanStatus(cgpan);
            if(status.equals("CL"))
                throw new NoDataException("This CGPAN is CLOSED.");
            else
                throw new NoDataException("This CGPAN is already marked NPA.");
        } else
        {
        HashMap inputDetail = new HashMap();
        inputDetail.put("memberId", memberId);
        inputDetail.put("borrowerId", borrowerId);
        //inputDetail.put("cgpan",cgpan);
        session.setAttribute("inputDetail", inputDetail);

        return mapping.findForward(Constants.SUCCESS);
        }
    }*/
    
    public ActionForward showNPADetails(ActionMapping mapping, ActionForm form, 
            HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
//     System.out.println("---entered in showNPADetails------");
Log.log(Log.INFO, "GMAction", "showNPADetails", "Entered");

GMActionForm gmActionForm = (GMActionForm)form;
gmActionForm.getRecProcedures().clear();
String memberId = gmActionForm.getMemberId();
String borrowerId = (gmActionForm.getBorrowerId()).toUpperCase();
String cgpan = (gmActionForm.getCgpan());
String borrowerName = gmActionForm.getBorrowerName();


Connection conn = DBConnection.getConnection();

String query = "SELECT COUNT(A) FROM (select  COUNT(CGPAN) A from DISBURSEMENT_DETAIL_TEMP where  cgpan='" + cgpan + "' AND DBR_FINAL_DISBURSEMENT_FLAG='Y'UNION select  COUNT(CGPAN) A from DISBURSEMENT_DETAIL where  cgpan='" + cgpan + "' AND DBR_FINAL_DISBURSEMENT_FLAG='Y') T WHERE T.A>0 ";

System.out.println("query=="+query);
Statement stmt=null;
ResultSet rs = null;
int count=0;
if (conn != null) {
stmt = conn.createStatement();
rs = stmt.executeQuery(query);
}

if (rs.next()) {
count  = rs.getInt(1);
}
rs.close();
rs = null;
stmt.close();
stmt = null;
//later change ==
/*if  (count==0){
	throw new NoDataException("You cannot Mark NPA, Final Disbusment Details not Updated.");	
}
*/

String querynew = "SELECT App_STATUS FROM application_detail where  cgpan='" + cgpan + "'" ;

System.out.println("query=="+querynew);
//Statement stmt=null;
//ResultSet rs = null;
String statusnew="";
if (conn != null) {
stmt = conn.createStatement();
rs = stmt.executeQuery(querynew);
}

if (rs.next()) {
	statusnew  = rs.getString(1);
}
rs.close();
rs = null;
stmt.close();
stmt = null;
//later change ==
if  (statusnew.equals("EX")||statusnew.equals("CL")){
	throw new NoDataException("You cannot Mark NPA, For Expired(EX) and Closed(CL) Cases.");	
}



int type;

GMProcessor gmProcessor = new GMProcessor();
ArrayList borrowerIds = new ArrayList();
borrowerIds = gmProcessor.getBorrowerIds(memberId);
ClaimsProcessor processor = new ClaimsProcessor();
Vector memberids = processor.getAllMemberIds();
if (!(memberids.contains(memberId))) {
throw new NoMemberFoundException("Member Id :" + memberId + 
                 " does not exist in the database.");
}

HttpSession session = request.getSession(false);

if ((borrowerId != null) && (!borrowerId.equals(""))) {
if (!(borrowerIds.contains(borrowerId))) {
gmActionForm.setBorrowerId("");

throw new NoDataException(borrowerId + 
              " is not among the borrower" + 
              " Ids for the Member Id :" + 
              memberId + ". Please enter correct" + 
              " Member Id and Borrower Id.");
}

} else if ((cgpan != null) && (!cgpan.equals(""))) {
type = 1;

borrowerId = processor.getBorowwerForCGPAN(cgpan);
Log.log(Log.INFO, "GMAction", "showOutstandingDetails", 
" Bid For Pan - " + borrowerId);
if (!(borrowerIds.contains(borrowerId))) {
throw new NoDataException(cgpan + "is not a valid Cgpan for " + 
              "the Member Id :" + memberId + 
              ". Please enter correct Cgpan");
}
gmActionForm.setBorrowerId(borrowerId);

} else if ((borrowerName != null) && (!borrowerName.equals(""))) {
type = 2;
ArrayList bIdForBorrowerName = 
gmProcessor.getBorrowerIdForBorrowerName(borrowerName, 
                             memberId);
if (bIdForBorrowerName == null || bIdForBorrowerName.size() == 0) {
throw new NoDataException("There are no Borrower Ids for this member");
} else {
session.setAttribute("displayFlag", "5");
gmActionForm.setBorrowerIds(bIdForBorrowerName);

return mapping.findForward("bidList");
}
}

if(gmProcessor.getCountForCGPAN(cgpan) > 0)
{
String status = gmProcessor.getCgpanStatus(cgpan);
if(status.equals("CL"))
throw new NoDataException("This CGPAN is CLOSED.");
else
throw new NoDataException("This CGPAN is already marked NPA.");
} else
{
HashMap inputDetail = new HashMap();
inputDetail.put("memberId", memberId);
inputDetail.put("borrowerId", borrowerId);
//inputDetail.put("cgpan",cgpan);
session.setAttribute("inputDetail", inputDetail);

return mapping.findForward(Constants.SUCCESS);
}
}
    public ActionForward showNpaDetailsNew(ActionMapping mapping, 
            ActionForm form, 
            HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
//        System.out.println("---entered in showNPADetailsNew------");
Log.log(Log.INFO, "GMAction", "showNPADetails", "Entered");

//       System.out.println("taking npa details");
Connection conn = null;
try
{
HttpSession session = request.getSession(false);
if (session == null) {
throw new MessageException("Please re-login and try again.");
}
DynaValidatorActionForm gmActionForm = (DynaValidatorActionForm)form;
gmActionForm.initialize(mapping);
//        gmActionForm.reset(mapping,request);

//    System.out.println("npaDt"+gmActionForm.get("npaDt")+"  isAsPerRBI"+gmActionForm.get("isAsPerRBI")+"  npaConfirm"+gmActionForm.get("npaConfirm")+"  isAcctReconstructed"+gmActionForm.get("isAcctReconstructed"));


/*clearing npa form*/
gmActionForm.set("borrowerId", "");
gmActionForm.set("borrowerName", "");
gmActionForm.set("operationType", "");
gmActionForm.set("size", 0);
gmActionForm.set("totalApprovedAmount", 0.0);
gmActionForm.set("totalSecurityAsOnSanc", 0.0);
gmActionForm.set("totalSecurityAsOnNpa", 0.0);
gmActionForm.set("npaId", "");
gmActionForm.set("npaDt", null);
gmActionForm.set("isAsPerRBI", "");
gmActionForm.set("npaConfirm", "");
gmActionForm.set("npaReason", "");
gmActionForm.set("effortsTaken", "");
gmActionForm.set("isAcctReconstructed", "");
//gmActionForm.set("subsidyFlag", "");
//gmActionForm.set("isSubsidyRcvd", "");
//gmActionForm.set("isSubsidyAdjusted", "");
//gmActionForm.set("subsidyLastRcvdAmt", 0.0);
//gmActionForm.set("subsidyLastRcvdDt", null);
gmActionForm.set("lastInspectionDt", null);
gmActionForm.set("securityAsOnSancDt", null);
gmActionForm.set("securityAsOnNpaDt", null);
gmActionForm.set("networthAsOnSancDt", 0.0);
gmActionForm.set("networthAsOnNpaDt", 0.0);
gmActionForm.set("reasonForReductionAsOnNpaDt", "");
gmActionForm.set("cgpansVector", null);

//    System.out.println("npaDt"+gmActionForm.get("npaDt")+"  isAsPerRBI"+gmActionForm.get("isAsPerRBI")+"  npaConfirm"+gmActionForm.get("npaConfirm")+"  isAcctReconstructed"+gmActionForm.get("isAcctReconstructed"));

HashMap inputDetail = (HashMap)session.getAttribute("inputDetail");
Log.log(Log.INFO, "GMAction", "showNPADetails inputDetail value ="+inputDetail,"");
String memberId = (String)inputDetail.get("memberId");
String borrowerId = (String)inputDetail.get("borrowerId");
//        String cgpan = (String)inputDetail.get("cgpan");

session.removeAttribute("inputDetail");

gmActionForm.set("memberId", memberId);
gmActionForm.set("borrowerId", borrowerId);
//          gmActionForm.set("cgpan",cgpan);



Statement stmt = null;
ResultSet rs = null;
String unitName = null;
int noOfClaims = 0;
String query = null;
String status = null;
conn = DBConnection.getConnection();

query = 
"select ssi_unit_name from ssi_detail where bid='" + borrowerId + "'";

//                }else if(cgpan != null || cgpan != ""){
//                    query = "select ssi_unit_name from ssi_detail where ssi_reference_number=(select ssi_reference_number from application_detail where cgpan='" + cgpan + "')";
//                }

// try {
if (conn != null) {
stmt = conn.createStatement();
rs = stmt.executeQuery(query);
}

if (rs.next()) {
unitName = rs.getString("ssi_unit_name");
}
rs.close();
rs = null;
stmt.close();
stmt = null;

//query = 
//"select sum(cnt) totcnt from\n" + "(\n" + "select count(*) cnt from claim_detail_temp where bid ='" + 
//borrowerId + "'\n" + "union all\n" + 
//"select count(*) cnt from claim_detail where bid ='" + 
//borrowerId + "')";
//
//if (conn != null) {
//stmt = conn.createStatement();
//rs = stmt.executeQuery(query);
//}
//if (rs.next()) {
//noOfClaims = rs.getInt("totcnt");
//}
//rs.close();
//rs = null;
//stmt.close();
//stmt = null;
query = " select CLM_status from claim_detail_temp where bid ='" + borrowerId + "' ";
if(conn != null){
stmt = conn.createStatement();
rs = stmt.executeQuery(query);
}

if(rs.next()){
status = rs.getString(1);
}
rs.close();
rs = null;
stmt.close();
stmt = null;

//   } catch (Exception e) {
//       e.printStackTrace();
//   } finally {
//       DBConnection.freeConnection(conn);
//   }
gmActionForm.set("unitName", unitName);
ApplicationProcessor proc = new ApplicationProcessor();
noOfClaims = proc.getClaimCount(borrowerId);
if (noOfClaims > 0 && !("TC".equals(status))) {
throw new MessageException("Npa details can not be updated because claim application has already been filed.");
}

GMProcessor gmProcessor = new GMProcessor();

NPADetails npaDetails = gmProcessor.getNPADetails(borrowerId);

GMDAO dao = new GMDAO();
Vector cgpans = new Vector();
Vector tccgpans = new Vector();
Vector wccgpans = new Vector();
//       if(npaDetails == null){
Vector cgpnDetails = 
dao.getCGPANDetailsPeriodicInfo(borrowerId, memberId); // here guaranteestartdate,sanctiondate,approved_amount,loanType,appStatus

if (cgpnDetails != null) {
if (cgpnDetails.size() == 0) {
throw new NoDataException("There are no Loan Account(s) for this Borrower or the existing Loan Account(s) may have been closed.");
}
} else {
throw new NoDataException("There are no Loan Account(s) for this Borrower or the existing Loan Account(s) may have been closed.");
}

HashMap hashmap = null;
//    ApplicationDAO appDao = new ApplicationDAO();
Administrator admin = new Administrator();
ParameterMaster pm = (ParameterMaster)admin.getParameter();
//    int periodTenureExpiryLodgementClaims = pm.getPeriodTenureExpiryLodgementClaims();
//    java.util.Date currentDate = new java.util.Date();
double totalApprovedAmount = 0.0;


SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
String guardatestr = null;
String sanctiondtstr = null;
for (int i = 0; i < cgpnDetails.size(); i++) {
hashmap = (HashMap)cgpnDetails.elementAt(i);

if (hashmap != null) {


String cgpanNO = (String)hashmap.get("CGPAN");
String cgpanStatus = (String)hashmap.get("APPLICATION_STATUS");
String loanType = (String)hashmap.get("CGPAN_LOAN_TYPE");
java.util.Date guarStartDt = 
(java.util.Date)hashmap.get("GUARANTEE_START_DT"); //-------------guar date
java.util.Date sanctionDt = 
(java.util.Date)hashmap.get("SANCTION_DT");
Double approvedAmount = (Double)hashmap.get("APPROVED_AMOUNT");
Double rate = (Double)hashmap.get("RATE");
//vinayak
Double sanctionedAmount = (Double)hashmap.get("SANCTIONED_AMOUNT");
//rate=4.2;
double r = 0.0;
double appAmt = 0.0;
if (approvedAmount != null || !approvedAmount.equals("")) {
appAmt = approvedAmount.doubleValue();
}
totalApprovedAmount = totalApprovedAmount + appAmt;

if (rate != null || !("".equals(rate))) {
r = rate.doubleValue();
}

if (guarStartDt != null) {
guardatestr = sdf.format(guarStartDt);
}

if (sanctionDt != null) {
sanctiondtstr = sdf.format(sanctionDt);
}

if ((cgpanNO != null) && (!(cgpanNO.equals("")))) {
HashMap map = new HashMap();
map.put("CGPAN", cgpanNO);
map.put("CGPAN_LOAN_TYPE", loanType);
map.put("GUARANTEE_START_DT", guardatestr);
map.put("SANCTION_DT", sanctiondtstr);
map.put("APPROVED_AMOUNT", appAmt);
map.put("RATE", r);
//vinayak
map.put("SANCTIONED_AMOUNT", sanctionedAmount);

if (!cgpans.contains(cgpanNO)) {
cgpans.addElement(map);
}
}

}
}
//      System.out.println("totalApprovedAmount:"+totalApprovedAmount);
//      System.out.println("size:"+cgpans.size());


gmActionForm.set("cgpansVector", cgpans);
gmActionForm.set("totalApprovedAmount", totalApprovedAmount);
gmActionForm.set("size", cgpans.size());


String totalLandValueStr = "";
Double totalLandValDouble = 0.0;
String totalMachineValueStr = "";
Double totalMachineValDouble = 0.0;
String totalBldgValueStr = "";
Double totalBldgValueDouble = 0.0;
String totalOFMAValueStr = "";
Double totalOFMAValDouble = 0.0;
String totalCurrAssetsValueStr = "";
Double totalCurrAssetsValDouble = 0.0;
String totalOthersValueStr = "";
Double totalOthersValDouble = 0.0;
HashMap hashmap2 = null;
Vector securityVector = new Vector();
Map securityMap = new HashMap();
Map securitymapnpa = new HashMap();
double networthAsOnSancDt = 0.0;
Double totalNetWorthDouble = 0.0;
double networthAsOnNpaDt = 0.0;

double totalSecurityAsOnSanc = 0.0;
double totalSecurityAsOnNpa = 0.0;

Map securitydetails = null;

if (npaDetails == null) {
//   securitydetails = dao.getPrimarySecurity(borrowerId,memberId);
securitydetails = 
dao.getPrimarySecurityAndNetworthOfGuarantorsAsOnSanc(borrowerId, 
                                           memberId);


if (securitydetails != null) {

totalNetWorthDouble = (Double)securitydetails.get("networth");
if (totalNetWorthDouble != null) {
networthAsOnSancDt = totalNetWorthDouble.doubleValue();
}

totalLandValDouble = (Double)securitydetails.get("land");
if (totalLandValDouble != null) {
if (totalLandValDouble.doubleValue() > 0.0) {
totalLandValueStr = String.valueOf(totalLandValDouble);
totalSecurityAsOnSanc = 
 totalSecurityAsOnSanc + totalLandValDouble.doubleValue();
} else {
totalLandValueStr = "";
}
}


totalMachineValDouble = (Double)securitydetails.get("machine");
if (totalMachineValDouble != null) {
if (totalMachineValDouble.doubleValue() > 0.0) {
totalMachineValueStr = 
 String.valueOf(totalMachineValDouble);
totalSecurityAsOnSanc = 
 totalSecurityAsOnSanc + totalMachineValDouble.doubleValue();
} else {
totalMachineValueStr = "";
}
}
totalBldgValueDouble = (Double)securitydetails.get("building");
if (totalBldgValueDouble != null) {
if (totalBldgValueDouble.doubleValue() > 0.0) {
totalBldgValueStr = 
 String.valueOf(totalBldgValueDouble);
totalSecurityAsOnSanc = 
 totalSecurityAsOnSanc + totalBldgValueDouble.doubleValue();
} else {
totalBldgValueStr = "";
}
}
totalOFMAValDouble = 
(Double)securitydetails.get("fixed_mov_asset");
if (totalOFMAValDouble != null) {
if (totalOFMAValDouble.doubleValue() > 0.0) {
totalOFMAValueStr = String.valueOf(totalOFMAValDouble);
totalSecurityAsOnSanc = 
 totalSecurityAsOnSanc + totalOFMAValDouble.doubleValue();
} else {
totalOFMAValueStr = "";
}
}
totalCurrAssetsValDouble = 
(Double)securitydetails.get("current_asset");
if (totalCurrAssetsValDouble != null) {
if (totalCurrAssetsValDouble.doubleValue() > 0.0) {
totalCurrAssetsValueStr = 
 String.valueOf(totalCurrAssetsValDouble);
totalSecurityAsOnSanc = 
 totalSecurityAsOnSanc + totalCurrAssetsValDouble.doubleValue();
} else {
totalCurrAssetsValueStr = "";
}
}
totalOthersValDouble = (Double)securitydetails.get("others");
if (totalOthersValDouble != null) {
if (totalOthersValDouble.doubleValue() > 0.0) {
totalOthersValueStr = 
 String.valueOf(totalOthersValDouble);
totalSecurityAsOnSanc = 
 totalSecurityAsOnSanc + totalOthersValDouble.doubleValue();
} else {
totalOthersValueStr = "";
}
}
}
securityMap.put("LAND", totalLandValueStr);
securityMap.put("MACHINE", totalMachineValueStr);
securityMap.put("BUILDING", totalBldgValueStr);
securityMap.put("OTHER_FIXED_MOVABLE_ASSETS", totalOFMAValueStr);
securityMap.put("CUR_ASSETS", totalCurrAssetsValueStr);
securityMap.put("OTHERS", totalOthersValueStr);

securitymapnpa.put("LAND", "");
securitymapnpa.put("MACHINE", "");
securitymapnpa.put("BUILDING", "");
securitymapnpa.put("OTHER_FIXED_MOVABLE_ASSETS", "");
securitymapnpa.put("CUR_ASSETS", "");
securitymapnpa.put("OTHERS", "");

gmActionForm.set("securityAsOnSancDt", securityMap);
gmActionForm.set("networthAsOnSancDt", networthAsOnSancDt);
gmActionForm.set("totalSecurityAsOnSanc", totalSecurityAsOnSanc);

gmActionForm.set("securityAsOnNpaDt", securitymapnpa);
gmActionForm.set("networthAsOnNpaDt", 0.0);
gmActionForm.set("totalSecurityAsOnNpa", 0.0);

}


if (npaDetails == null) {
Log.log(Log.INFO, "GMAction", "showNPADetails", 
"Npa Details is Null");

//      gmActionForm.getRecProcedures().put("key-0",new RecoveryProcedureTemp());

//      gmActionForm.resetNpaDetailsPage(mapping,request);
session.setAttribute("npaAvailable", null);

session.setAttribute("recInitiated", "N");
}


if (npaDetails != null) {
String operationType = "NCU";
Date npaCreatedDate = npaDetails.getNpaCreatedDate();
if (npaCreatedDate == null) {
operationType = "OCU";
}
gmActionForm.set("npaCreatedDate", npaCreatedDate);


gmActionForm.set("operationType", operationType);
String npaId = npaDetails.getNpaId();

session.setAttribute("npaAvailable", npaId);
CustomisedDate custom = new CustomisedDate();
custom.setDate(npaDetails.getNpaDate());

gmActionForm.set("npaId", npaId);
gmActionForm.set("npaDt", custom);
gmActionForm.set("isAsPerRBI", npaDetails.getIsAsPerRBI());
gmActionForm.set("npaConfirm", npaDetails.getNpaConfirm());
gmActionForm.set("npaReason", npaDetails.getNpaReason());
gmActionForm.set("effortsTaken", npaDetails.getEffortsTaken());
gmActionForm.set("isAcctReconstructed", 
npaDetails.getIsAcctReconstructed());
gmActionForm.set("subsidyFlag", npaDetails.getSubsidyFlag());
gmActionForm.set("isSubsidyRcvd", npaDetails.getIsSubsidyRcvd());
gmActionForm.set("isSubsidyAdjusted", 
npaDetails.getIsSubsidyAdjusted());
gmActionForm.set("subsidyLastRcvdAmt", 
npaDetails.getSubsidyLastRcvdAmt());
custom = new CustomisedDate();
custom.setDate(npaDetails.getSubsidyLastRcvdDt());
gmActionForm.set("subsidyLastRcvdDt", custom);
custom = new CustomisedDate();
custom.setDate(npaDetails.getLastInspectionDt());
gmActionForm.set("lastInspectionDt", custom);

Vector allCgpans = null;

allCgpans = dao.getCgpanDetailsAsOnNpa(npaId);

CustomisedDate custom2 = null;
for (int i = 1; i <= cgpans.size(); i++) {
Map map = (Map)cgpans.get(i - 1);
String cgpanNo = (String)map.get("CGPAN");
//logic to set all properties fdd,ldd,fid,pm,im
if (allCgpans != null) {
for (int j = 0; j < allCgpans.size(); j++) {

Map cgMap = (Map)allCgpans.get(j);
String cg = (String)cgMap.get("CGPAN");
String loanType = cg.substring(cg.length() - 2);
if (cgpanNo.equals(cg)) {
gmActionForm.set("cgpan" + i, cgpanNo);
if ("TC".equals(loanType) || 
 "CC".equals(loanType)) {
 custom2 = new CustomisedDate();
 custom2.setDate((java.util.Date)cgMap.get("FIRSTDISBDT"));
 gmActionForm.set("firstDisbDt" + i, custom2);
 custom2 = new CustomisedDate();
 custom2.setDate((java.util.Date)cgMap.get("LASTDISBDT"));
 gmActionForm.set("lastDisbDt" + i, custom2);
 custom2 = new CustomisedDate();
 custom2.setDate((java.util.Date)cgMap.get("FIRSTINSTDT"));
 gmActionForm.set("firstInstDt" + i, custom2);
 gmActionForm.set("moratoriumPrincipal" + i, 
                  (Integer)cgMap.get("PRINCIPALMORATORIUM"));
 gmActionForm.set("moratoriumInterest" + i, 
                  (Integer)cgMap.get("INTERESTMORATORIUM"));

 gmActionForm.set("totalDisbAmt" + i, 
                  (Double)cgMap.get("TOTALDISBAMT"));
 gmActionForm.set("repayPrincipal" + i, 
                  (Double)cgMap.get("PRINCIPALREPAY"));
 gmActionForm.set("repayInterest" + i, 
                  (Double)cgMap.get("INTERESTREPAY"));
}
gmActionForm.set("outstandingPrincipal" + i, 
              (Double)cgMap.get("PRINCIPALOS"));
gmActionForm.set("outstandingInterest" + i, 
              (Double)cgMap.get("INTERESTOS"));
break;
}
}
}

}

GMAction gm = new GMAction();
totalLandValueStr = "";
totalMachineValueStr = "";
totalBldgValueStr = "";
totalOFMAValueStr = "";
totalCurrAssetsValueStr = "";
totalOthersValueStr = "";
securitydetails = null;

CPDAO cpdao = new CPDAO();

securitydetails = 
cpdao.getPrimarySecurityAndNetworthOfGuarantors(npaId);
HashMap sancmap = (HashMap)securitydetails.get("SAN");
HashMap npamap = (HashMap)securitydetails.get("NPA");

if (sancmap != null) {

totalNetWorthDouble = (Double)sancmap.get("networth");
if (totalNetWorthDouble != null) {
networthAsOnSancDt = totalNetWorthDouble.doubleValue();
}

totalLandValDouble = (Double)sancmap.get("LAND");
if (totalLandValDouble != null) {
if (totalLandValDouble.doubleValue() > 0.0) {
totalLandValueStr = String.valueOf(totalLandValDouble);
totalSecurityAsOnSanc = 
 totalSecurityAsOnSanc + totalLandValDouble.doubleValue();
} else {
totalLandValueStr = "";
}
}


totalMachineValDouble = (Double)sancmap.get("MACHINE");
if (totalMachineValDouble != null) {
if (totalMachineValDouble.doubleValue() > 0.0) {
totalMachineValueStr = 
 String.valueOf(totalMachineValDouble);
totalSecurityAsOnSanc = 
 totalSecurityAsOnSanc + totalMachineValDouble.doubleValue();
} else {
totalMachineValueStr = "";
}
}
totalBldgValueDouble = (Double)sancmap.get("BUILDING");
if (totalBldgValueDouble != null) {
if (totalBldgValueDouble.doubleValue() > 0.0) {
totalBldgValueStr = 
 String.valueOf(totalBldgValueDouble);
totalSecurityAsOnSanc = 
 totalSecurityAsOnSanc + totalBldgValueDouble.doubleValue();
} else {
totalBldgValueStr = "";
}
}
totalOFMAValDouble = 
(Double)sancmap.get("OTHER FIXED MOVABLE ASSETS");
if (totalOFMAValDouble != null) {
if (totalOFMAValDouble.doubleValue() > 0.0) {
totalOFMAValueStr = String.valueOf(totalOFMAValDouble);
totalSecurityAsOnSanc = 
 totalSecurityAsOnSanc + totalOFMAValDouble.doubleValue();
} else {
totalOFMAValueStr = "";
}
}
totalCurrAssetsValDouble = 
(Double)sancmap.get("CURRENT ASSETS");
if (totalCurrAssetsValDouble != null) {
if (totalCurrAssetsValDouble.doubleValue() > 0.0) {
totalCurrAssetsValueStr = 
 String.valueOf(totalCurrAssetsValDouble);
totalSecurityAsOnSanc = 
 totalSecurityAsOnSanc + totalCurrAssetsValDouble.doubleValue();
} else {
totalCurrAssetsValueStr = "";
}
}
totalOthersValDouble = (Double)sancmap.get("OTHERS");
if (totalOthersValDouble != null) {
if (totalOthersValDouble.doubleValue() > 0.0) {
totalOthersValueStr = 
 String.valueOf(totalOthersValDouble);
totalSecurityAsOnSanc = 
 totalSecurityAsOnSanc + totalOthersValDouble.doubleValue();
} else {
totalOthersValueStr = "";
}
}
}
securityMap.put("LAND", totalLandValueStr);
securityMap.put("MACHINE", totalMachineValueStr);
securityMap.put("BUILDING", totalBldgValueStr);
securityMap.put("OTHER_FIXED_MOVABLE_ASSETS", totalOFMAValueStr);
securityMap.put("CUR_ASSETS", totalCurrAssetsValueStr);
securityMap.put("OTHERS", totalOthersValueStr);

totalLandValueStr = "";
totalMachineValueStr = "";
totalBldgValueStr = "";
totalOFMAValueStr = "";
totalCurrAssetsValueStr = "";
totalOthersValueStr = "";
securitydetails = null;
String reasonReduction = "";

//        securitydetails = dao.getPrimarySecurityAndNetworthOfGuarantorsAsOnNpa(borrowerId,memberId,npaId);

if (npamap != null) {

totalNetWorthDouble = (Double)npamap.get("networth");
if (totalNetWorthDouble != null) {
networthAsOnNpaDt = totalNetWorthDouble.doubleValue();
}

totalLandValDouble = (Double)npamap.get("LAND");
if (totalLandValDouble != null) {
if (totalLandValDouble.doubleValue() > 0.0) {
totalLandValueStr = String.valueOf(totalLandValDouble);
totalSecurityAsOnNpa = 
 totalSecurityAsOnNpa + totalLandValDouble.doubleValue();
} else {
totalLandValueStr = "";
}
}


totalMachineValDouble = (Double)npamap.get("MACHINE");
if (totalMachineValDouble != null) {
if (totalMachineValDouble.doubleValue() > 0.0) {
totalMachineValueStr = 
 String.valueOf(totalMachineValDouble);
totalSecurityAsOnNpa = 
 totalSecurityAsOnNpa + totalMachineValDouble.doubleValue();
} else {
totalMachineValueStr = "";
}
}
totalBldgValueDouble = (Double)npamap.get("BUILDING");
if (totalBldgValueDouble != null) {
if (totalBldgValueDouble.doubleValue() > 0.0) {
totalBldgValueStr = 
 String.valueOf(totalBldgValueDouble);
totalSecurityAsOnNpa = 
 totalSecurityAsOnNpa + totalBldgValueDouble.doubleValue();
} else {
totalBldgValueStr = "";
}
}
totalOFMAValDouble = 
(Double)npamap.get("OTHER FIXED MOVABLE ASSETS");
if (totalOFMAValDouble != null) {
if (totalOFMAValDouble.doubleValue() > 0.0) {
totalOFMAValueStr = String.valueOf(totalOFMAValDouble);
totalSecurityAsOnNpa = 
 totalSecurityAsOnNpa + totalOFMAValDouble.doubleValue();
} else {
totalOFMAValueStr = "";
}
}
totalCurrAssetsValDouble = 
(Double)npamap.get("CURRENT ASSETS");
if (totalCurrAssetsValDouble != null) {
if (totalCurrAssetsValDouble.doubleValue() > 0.0) {
totalCurrAssetsValueStr = 
 String.valueOf(totalCurrAssetsValDouble);
totalSecurityAsOnNpa = 
 totalSecurityAsOnNpa + totalCurrAssetsValDouble.doubleValue();
} else {
totalCurrAssetsValueStr = "";
}
}
totalOthersValDouble = (Double)npamap.get("OTHERS");
if (totalOthersValDouble != null) {
if (totalOthersValDouble.doubleValue() > 0.0) {
totalOthersValueStr = 
 String.valueOf(totalOthersValDouble);
totalSecurityAsOnNpa = 
 totalSecurityAsOnNpa + totalOthersValDouble.doubleValue();
} else {
totalOthersValueStr = "";
}
}
reasonReduction = (String)npamap.get("reasonReduction");
}
securitymapnpa.put("LAND", totalLandValueStr);
securitymapnpa.put("MACHINE", totalMachineValueStr);
securitymapnpa.put("BUILDING", totalBldgValueStr);
securitymapnpa.put("OTHER_FIXED_MOVABLE_ASSETS", 
totalOFMAValueStr);
securitymapnpa.put("CUR_ASSETS", totalCurrAssetsValueStr);
securitymapnpa.put("OTHERS", totalOthersValueStr);


gmActionForm.set("securityAsOnSancDt", securityMap);
gmActionForm.set("networthAsOnSancDt", networthAsOnSancDt);
gmActionForm.set("totalSecurityAsOnSanc", totalSecurityAsOnSanc);

gmActionForm.set("securityAsOnNpaDt", securitymapnpa);
gmActionForm.set("networthAsOnNpaDt", networthAsOnNpaDt);
gmActionForm.set("reasonForReductionAsOnNpaDt", reasonReduction);
gmActionForm.set("totalSecurityAsOnNpa", totalSecurityAsOnNpa);
}


Log.log(Log.INFO, "GMAction", "showNPADetails", "Exited");

}
catch(Exception e)
{
Log.log(Log.INFO, "GMAction", "exception in showNPADetails  ="+e.getMessage(),"");
Log.logException(e);
}
finally {
DBConnection.freeConnection(conn);
}

return mapping.findForward(Constants.SUCCESS);

}
    /*
     * public ActionForward showNpaDetailsNew(ActionMapping mapping, 
                                           ActionForm form, 
                                           HttpServletRequest request, 
                                           HttpServletResponse response) throws Exception {
    	
    	 //        System.out.println("---entered in showNPADetailsNew------");
        Log.log(Log.INFO, "GMAction", "showNPADetails", "Entered");

        //       System.out.println("taking npa details");
        Connection conn = null;
        try
        {
        HttpSession session = request.getSession(false);
        if (session == null) {
            throw new MessageException("Please re-login and try again.");
        }
        DynaValidatorActionForm gmActionForm = (DynaValidatorActionForm)form;
        gmActionForm.initialize(mapping);
        //        gmActionForm.reset(mapping,request);

        //    System.out.println("npaDt"+gmActionForm.get("npaDt")+"  isAsPerRBI"+gmActionForm.get("isAsPerRBI")+"  npaConfirm"+gmActionForm.get("npaConfirm")+"  isAcctReconstructed"+gmActionForm.get("isAcctReconstructed"));


        clearing npa form
        gmActionForm.set("borrowerId", "");
        gmActionForm.set("borrowerName", "");
        gmActionForm.set("operationType", "");
        gmActionForm.set("size", 0);
        gmActionForm.set("totalApprovedAmount", 0.0);
        gmActionForm.set("totalSecurityAsOnSanc", 0.0);
        gmActionForm.set("totalSecurityAsOnNpa", 0.0);
        gmActionForm.set("npaId", "");
        gmActionForm.set("npaDt", null);
        gmActionForm.set("isAsPerRBI", "");
        gmActionForm.set("npaConfirm", "");
        gmActionForm.set("npaReason", "");
        gmActionForm.set("effortsTaken", "");
        gmActionForm.set("isAcctReconstructed", "");
        //gmActionForm.set("subsidyFlag", "");
        //gmActionForm.set("isSubsidyRcvd", "");
        //gmActionForm.set("isSubsidyAdjusted", "");
        //gmActionForm.set("subsidyLastRcvdAmt", 0.0);
        //gmActionForm.set("subsidyLastRcvdDt", null);
        gmActionForm.set("lastInspectionDt", null);
        gmActionForm.set("securityAsOnSancDt", null);
        gmActionForm.set("securityAsOnNpaDt", null);
        gmActionForm.set("networthAsOnSancDt", 0.0);
        gmActionForm.set("networthAsOnNpaDt", 0.0);
        gmActionForm.set("reasonForReductionAsOnNpaDt", "");
        gmActionForm.set("cgpansVector", null);

        //    System.out.println("npaDt"+gmActionForm.get("npaDt")+"  isAsPerRBI"+gmActionForm.get("isAsPerRBI")+"  npaConfirm"+gmActionForm.get("npaConfirm")+"  isAcctReconstructed"+gmActionForm.get("isAcctReconstructed"));
       
        HashMap inputDetail = (HashMap)session.getAttribute("inputDetail");
        Log.log(Log.INFO, "GMAction", "showNPADetails inputDetail value ="+inputDetail,"");
        String memberId = (String)inputDetail.get("memberId");
        String borrowerId = (String)inputDetail.get("borrowerId");
        //        String cgpan = (String)inputDetail.get("cgpan");

        session.removeAttribute("inputDetail");

        gmActionForm.set("memberId", memberId);
        gmActionForm.set("borrowerId", borrowerId);
        //          gmActionForm.set("cgpan",cgpan);


       
        Statement stmt = null;
        ResultSet rs = null;
        String unitName = null;
        int noOfClaims = 0;
        String query = null;
        String status = null;
        conn = DBConnection.getConnection();

        query = 
                "select ssi_unit_name from ssi_detail where bid='" + borrowerId + "'";

        //                }else if(cgpan != null || cgpan != ""){
        //                    query = "select ssi_unit_name from ssi_detail where ssi_reference_number=(select ssi_reference_number from application_detail where cgpan='" + cgpan + "')";
        //                }

       // try {
            if (conn != null) {
                stmt = conn.createStatement();
                rs = stmt.executeQuery(query);
            }

            if (rs.next()) {
                unitName = rs.getString("ssi_unit_name");
            }
            rs.close();
            rs = null;
            stmt.close();
            stmt = null;

//            query = 
//                    "select sum(cnt) totcnt from\n" + "(\n" + "select count(*) cnt from claim_detail_temp where bid ='" + 
//                    borrowerId + "'\n" + "union all\n" + 
//                    "select count(*) cnt from claim_detail where bid ='" + 
//                    borrowerId + "')";
//
//            if (conn != null) {
//                stmt = conn.createStatement();
//                rs = stmt.executeQuery(query);
//            }
//            if (rs.next()) {
//                noOfClaims = rs.getInt("totcnt");
//            }
//            rs.close();
//            rs = null;
//            stmt.close();
//            stmt = null;
            query = " select CLM_status from claim_detail_temp where bid ='" + borrowerId + "' ";
            if(conn != null){
                stmt = conn.createStatement();
                rs = stmt.executeQuery(query);
            }
            
            if(rs.next()){
                status = rs.getString(1);
            }
            rs.close();
            rs = null;
            stmt.close();
            stmt = null;
            
     //   } catch (Exception e) {
     //       e.printStackTrace();
     //   } finally {
     //       DBConnection.freeConnection(conn);
     //   }
        gmActionForm.set("unitName", unitName);
        ApplicationProcessor proc = new ApplicationProcessor();
        noOfClaims = proc.getClaimCount(borrowerId);
        if (noOfClaims > 0 && !("TC".equals(status))) {
            throw new MessageException("Npa details can not be updated because claim application has already been filed.");
        }

        GMProcessor gmProcessor = new GMProcessor();

        NPADetails npaDetails = gmProcessor.getNPADetails(borrowerId);

        GMDAO dao = new GMDAO();
        Vector cgpans = new Vector();
        Vector tccgpans = new Vector();
        Vector wccgpans = new Vector();
        //       if(npaDetails == null){
        Vector cgpnDetails = 
            dao.getCGPANDetailsPeriodicInfo(borrowerId, memberId); // here guaranteestartdate,sanctiondate,approved_amount,loanType,appStatus

        if (cgpnDetails != null) {
            if (cgpnDetails.size() == 0) {
                throw new NoDataException("There are no Loan Account(s) for this Borrower or the existing Loan Account(s) may have been closed.");
            }
        } else {
            throw new NoDataException("There are no Loan Account(s) for this Borrower or the existing Loan Account(s) may have been closed.");
        }

        HashMap hashmap = null;
        //    ApplicationDAO appDao = new ApplicationDAO();
        Administrator admin = new Administrator();
        ParameterMaster pm = (ParameterMaster)admin.getParameter();
        //    int periodTenureExpiryLodgementClaims = pm.getPeriodTenureExpiryLodgementClaims();
        //    java.util.Date currentDate = new java.util.Date();
        double totalApprovedAmount = 0.0;
        double totalDisbAmt=0.0;


        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String guardatestr = null;
        String fDbrDateStr=null;
        String lDbrDateStr=null;
        String sanctiondtstr = null;
        for (int i = 0; i < cgpnDetails.size(); i++) {
            hashmap = (HashMap)cgpnDetails.elementAt(i);

            if (hashmap != null) {


                String cgpanNO = (String)hashmap.get("CGPAN");
                String cgpanStatus = (String)hashmap.get("APPLICATION_STATUS");
                String loanType = (String)hashmap.get("CGPAN_LOAN_TYPE");
                java.util.Date guarStartDt = 
                    (java.util.Date)hashmap.get("GUARANTEE_START_DT"); //-------------guar date
                java.util.Date sanctionDt = 
                    (java.util.Date)hashmap.get("SANCTION_DT");
                Double approvedAmount = (Double)hashmap.get("APPROVED_AMOUNT");
                Double rate = (Double)hashmap.get("RATE");
                //Diksha 
                java.util.Date fDbrDt = (java.util.Date)hashmap.get("NTD_FIRST_DISBURSEMENT_DT");
                java.util.Date lDbrDt = (java.util.Date)hashmap.get("NTD_LAST_DISBURSEMENT_DT");
                Double disbAmount = (Double)hashmap.get("NTD_TOTAL_DISB_AMT");
                //End
                System.out.println("firstDbrDt=="+fDbrDt);
                System.out.println("lastDbrDt=="+lDbrDt);
                System.out.println("dbrAmt=="+disbAmount);
               
                //rate=4.2;
                double r = 0.0;
                double appAmt = 0.0;
                double dbrAmt=0.0;
                if (approvedAmount != null || !approvedAmount.equals("")) {
                    appAmt = approvedAmount.doubleValue();
                }
                totalApprovedAmount = totalApprovedAmount + appAmt;

                if (rate != null || !("".equals(rate))) {
                    r = rate.doubleValue();
                }
                
                if (disbAmount != null || !disbAmount.equals("")) {
                	dbrAmt = disbAmount.doubleValue();
                }
                totalDisbAmt = totalDisbAmt + dbrAmt;

                if (guarStartDt != null) {
                    guardatestr = sdf.format(guarStartDt);
                }
                if (fDbrDt != null) {
                	fDbrDateStr = sdf.format(fDbrDt);
                }
                if (lDbrDt != null) {
                	lDbrDateStr = sdf.format(lDbrDt);
                }

                if (sanctionDt != null) {
                    sanctiondtstr = sdf.format(sanctionDt);
                }

                if ((cgpanNO != null) && (!(cgpanNO.equals("")))) {
                    HashMap map = new HashMap();
                    map.put("CGPAN", cgpanNO);
                    map.put("CGPAN_LOAN_TYPE", loanType);
                    map.put("GUARANTEE_START_DT", guardatestr);
                    map.put("SANCTION_DT", sanctiondtstr);
                    map.put("APPROVED_AMOUNT", appAmt);
                    map.put("RATE", r);
                    map.put("NTD_FIRST_DISBURSEMENT_DT", fDbrDateStr);
                    map.put("NTD_LAST_DISBURSEMENT_DT", lDbrDateStr);
                    map.put("NTD_TOTAL_DISB_AMT", dbrAmt);

                    if (!cgpans.contains(cgpanNO)) {
                        cgpans.addElement(map);
                    }
                }

            }
        }
        //      System.out.println("totalApprovedAmount:"+totalApprovedAmount);
        //      System.out.println("size:"+cgpans.size());


        gmActionForm.set("cgpansVector", cgpans);
        gmActionForm.set("totalApprovedAmount", totalApprovedAmount);
        gmActionForm.set("size", cgpans.size());


        String totalLandValueStr = "";
        Double totalLandValDouble = 0.0;
        String totalMachineValueStr = "";
        Double totalMachineValDouble = 0.0;
        String totalBldgValueStr = "";
        Double totalBldgValueDouble = 0.0;
        String totalOFMAValueStr = "";
        Double totalOFMAValDouble = 0.0;
        String totalCurrAssetsValueStr = "";
        Double totalCurrAssetsValDouble = 0.0;
        String totalOthersValueStr = "";
        Double totalOthersValDouble = 0.0;
        HashMap hashmap2 = null;
        Vector securityVector = new Vector();
        Map securityMap = new HashMap();
        Map securitymapnpa = new HashMap();
        double networthAsOnSancDt = 0.0;
        Double totalNetWorthDouble = 0.0;
        double networthAsOnNpaDt = 0.0;

        double totalSecurityAsOnSanc = 0.0;
        double totalSecurityAsOnNpa = 0.0;

        Map securitydetails = null;

        if (npaDetails == null) {
            //   securitydetails = dao.getPrimarySecurity(borrowerId,memberId);
            securitydetails = 
                    dao.getPrimarySecurityAndNetworthOfGuarantorsAsOnSanc(borrowerId, 
                                                                          memberId);


            if (securitydetails != null) {

                totalNetWorthDouble = (Double)securitydetails.get("networth");
                if (totalNetWorthDouble != null) {
                    networthAsOnSancDt = totalNetWorthDouble.doubleValue();
                }

                totalLandValDouble = (Double)securitydetails.get("land");
                if (totalLandValDouble != null) {
                    if (totalLandValDouble.doubleValue() > 0.0) {
                        totalLandValueStr = String.valueOf(totalLandValDouble);
                        totalSecurityAsOnSanc = 
                                totalSecurityAsOnSanc + totalLandValDouble.doubleValue();
                    } else {
                        totalLandValueStr = "";
                    }
                }


                totalMachineValDouble = (Double)securitydetails.get("machine");
                if (totalMachineValDouble != null) {
                    if (totalMachineValDouble.doubleValue() > 0.0) {
                        totalMachineValueStr = 
                                String.valueOf(totalMachineValDouble);
                        totalSecurityAsOnSanc = 
                                totalSecurityAsOnSanc + totalMachineValDouble.doubleValue();
                    } else {
                        totalMachineValueStr = "";
                    }
                }
                totalBldgValueDouble = (Double)securitydetails.get("building");
                if (totalBldgValueDouble != null) {
                    if (totalBldgValueDouble.doubleValue() > 0.0) {
                        totalBldgValueStr = 
                                String.valueOf(totalBldgValueDouble);
                        totalSecurityAsOnSanc = 
                                totalSecurityAsOnSanc + totalBldgValueDouble.doubleValue();
                    } else {
                        totalBldgValueStr = "";
                    }
                }
                totalOFMAValDouble = 
                        (Double)securitydetails.get("fixed_mov_asset");
                if (totalOFMAValDouble != null) {
                    if (totalOFMAValDouble.doubleValue() > 0.0) {
                        totalOFMAValueStr = String.valueOf(totalOFMAValDouble);
                        totalSecurityAsOnSanc = 
                                totalSecurityAsOnSanc + totalOFMAValDouble.doubleValue();
                    } else {
                        totalOFMAValueStr = "";
                    }
                }
                totalCurrAssetsValDouble = 
                        (Double)securitydetails.get("current_asset");
                if (totalCurrAssetsValDouble != null) {
                    if (totalCurrAssetsValDouble.doubleValue() > 0.0) {
                        totalCurrAssetsValueStr = 
                                String.valueOf(totalCurrAssetsValDouble);
                        totalSecurityAsOnSanc = 
                                totalSecurityAsOnSanc + totalCurrAssetsValDouble.doubleValue();
                    } else {
                        totalCurrAssetsValueStr = "";
                    }
                }
                totalOthersValDouble = (Double)securitydetails.get("others");
                if (totalOthersValDouble != null) {
                    if (totalOthersValDouble.doubleValue() > 0.0) {
                        totalOthersValueStr = 
                                String.valueOf(totalOthersValDouble);
                        totalSecurityAsOnSanc = 
                                totalSecurityAsOnSanc + totalOthersValDouble.doubleValue();
                    } else {
                        totalOthersValueStr = "";
                    }
                }
            }
            securityMap.put("LAND", totalLandValueStr);
            securityMap.put("MACHINE", totalMachineValueStr);
            securityMap.put("BUILDING", totalBldgValueStr);
            securityMap.put("OTHER_FIXED_MOVABLE_ASSETS", totalOFMAValueStr);
            securityMap.put("CUR_ASSETS", totalCurrAssetsValueStr);
            securityMap.put("OTHERS", totalOthersValueStr);

            securitymapnpa.put("LAND", "");
            securitymapnpa.put("MACHINE", "");
            securitymapnpa.put("BUILDING", "");
            securitymapnpa.put("OTHER_FIXED_MOVABLE_ASSETS", "");
            securitymapnpa.put("CUR_ASSETS", "");
            securitymapnpa.put("OTHERS", "");

            gmActionForm.set("securityAsOnSancDt", securityMap);
            gmActionForm.set("networthAsOnSancDt", networthAsOnSancDt);
            gmActionForm.set("totalSecurityAsOnSanc", totalSecurityAsOnSanc);

            gmActionForm.set("securityAsOnNpaDt", securitymapnpa);
            gmActionForm.set("networthAsOnNpaDt", 0.0);
            gmActionForm.set("totalSecurityAsOnNpa", 0.0);

        }


        if (npaDetails == null) {
            Log.log(Log.INFO, "GMAction", "showNPADetails", 
                    "Npa Details is Null");

            //      gmActionForm.getRecProcedures().put("key-0",new RecoveryProcedureTemp());

            //      gmActionForm.resetNpaDetailsPage(mapping,request);
            session.setAttribute("npaAvailable", null);

            session.setAttribute("recInitiated", "N");
        }


        if (npaDetails != null) {
            String operationType = "NCU";
            Date npaCreatedDate = npaDetails.getNpaCreatedDate();
            if (npaCreatedDate == null) {
                operationType = "OCU";
            }
            gmActionForm.set("npaCreatedDate", npaCreatedDate);


            gmActionForm.set("operationType", operationType);
            String npaId = npaDetails.getNpaId();

            session.setAttribute("npaAvailable", npaId);
            CustomisedDate custom = new CustomisedDate();
            custom.setDate(npaDetails.getNpaDate());

            gmActionForm.set("npaId", npaId);
            gmActionForm.set("npaDt", custom);
            gmActionForm.set("isAsPerRBI", npaDetails.getIsAsPerRBI());
            gmActionForm.set("npaConfirm", npaDetails.getNpaConfirm());
            gmActionForm.set("npaReason", npaDetails.getNpaReason());
            gmActionForm.set("effortsTaken", npaDetails.getEffortsTaken());
            gmActionForm.set("isAcctReconstructed", 
                             npaDetails.getIsAcctReconstructed());
            gmActionForm.set("subsidyFlag", npaDetails.getSubsidyFlag());
            gmActionForm.set("isSubsidyRcvd", npaDetails.getIsSubsidyRcvd());
            gmActionForm.set("isSubsidyAdjusted", 
                             npaDetails.getIsSubsidyAdjusted());
            gmActionForm.set("subsidyLastRcvdAmt", 
                             npaDetails.getSubsidyLastRcvdAmt());
            custom = new CustomisedDate();
            custom.setDate(npaDetails.getSubsidyLastRcvdDt());
            gmActionForm.set("subsidyLastRcvdDt", custom);
            custom = new CustomisedDate();
            custom.setDate(npaDetails.getLastInspectionDt());
            gmActionForm.set("lastInspectionDt", custom);

            Vector allCgpans = null;

            allCgpans = dao.getCgpanDetailsAsOnNpa(npaId);

            CustomisedDate custom2 = null;
            for (int i = 1; i <= cgpans.size(); i++) {
                Map map = (Map)cgpans.get(i - 1);
                String cgpanNo = (String)map.get("CGPAN");
                //logic to set all properties fdd,ldd,fid,pm,im
                if (allCgpans != null) {
                    for (int j = 0; j < allCgpans.size(); j++) {

                        Map cgMap = (Map)allCgpans.get(j);
                        String cg = (String)cgMap.get("CGPAN");
                        String loanType = cg.substring(cg.length() - 2);
                        if (cgpanNo.equals(cg)) {
                            gmActionForm.set("cgpan" + i, cgpanNo);
                            if ("TC".equals(loanType) || 
                                "CC".equals(loanType)) {
                                custom2 = new CustomisedDate();
                                custom2.setDate((java.util.Date)cgMap.get("FIRSTDISBDT"));
                                gmActionForm.set("firstDisbDt" + i, custom2);
                                custom2 = new CustomisedDate();
                                custom2.setDate((java.util.Date)cgMap.get("LASTDISBDT"));
                                gmActionForm.set("lastDisbDt" + i, custom2);
                                custom2 = new CustomisedDate();
                                custom2.setDate((java.util.Date)cgMap.get("FIRSTINSTDT"));
                                gmActionForm.set("firstInstDt" + i, custom2);
                                gmActionForm.set("moratoriumPrincipal" + i, 
                                                 (Integer)cgMap.get("PRINCIPALMORATORIUM"));
                                gmActionForm.set("moratoriumInterest" + i, 
                                                 (Integer)cgMap.get("INTERESTMORATORIUM"));

                                gmActionForm.set("totalDisbAmt" + i, 
                                                 (Double)cgMap.get("TOTALDISBAMT"));
                                gmActionForm.set("repayPrincipal" + i, 
                                                 (Double)cgMap.get("PRINCIPALREPAY"));
                                gmActionForm.set("repayInterest" + i, 
                                                 (Double)cgMap.get("INTERESTREPAY"));
                            }
                            gmActionForm.set("outstandingPrincipal" + i, 
                                             (Double)cgMap.get("PRINCIPALOS"));
                            gmActionForm.set("outstandingInterest" + i, 
                                             (Double)cgMap.get("INTERESTOS"));
                            break;
                        }
                    }
                }

            }

            GMAction gm = new GMAction();
            totalLandValueStr = "";
            totalMachineValueStr = "";
            totalBldgValueStr = "";
            totalOFMAValueStr = "";
            totalCurrAssetsValueStr = "";
            totalOthersValueStr = "";
            securitydetails = null;

            CPDAO cpdao = new CPDAO();

            securitydetails = 
                    cpdao.getPrimarySecurityAndNetworthOfGuarantors(npaId);
            HashMap sancmap = (HashMap)securitydetails.get("SAN");
            HashMap npamap = (HashMap)securitydetails.get("NPA");

            if (sancmap != null) {

                totalNetWorthDouble = (Double)sancmap.get("networth");
                if (totalNetWorthDouble != null) {
                    networthAsOnSancDt = totalNetWorthDouble.doubleValue();
                }

                totalLandValDouble = (Double)sancmap.get("LAND");
                if (totalLandValDouble != null) {
                    if (totalLandValDouble.doubleValue() > 0.0) {
                        totalLandValueStr = String.valueOf(totalLandValDouble);
                        totalSecurityAsOnSanc = 
                                totalSecurityAsOnSanc + totalLandValDouble.doubleValue();
                    } else {
                        totalLandValueStr = "";
                    }
                }


                totalMachineValDouble = (Double)sancmap.get("MACHINE");
                if (totalMachineValDouble != null) {
                    if (totalMachineValDouble.doubleValue() > 0.0) {
                        totalMachineValueStr = 
                                String.valueOf(totalMachineValDouble);
                        totalSecurityAsOnSanc = 
                                totalSecurityAsOnSanc + totalMachineValDouble.doubleValue();
                    } else {
                        totalMachineValueStr = "";
                    }
                }
                totalBldgValueDouble = (Double)sancmap.get("BUILDING");
                if (totalBldgValueDouble != null) {
                    if (totalBldgValueDouble.doubleValue() > 0.0) {
                        totalBldgValueStr = 
                                String.valueOf(totalBldgValueDouble);
                        totalSecurityAsOnSanc = 
                                totalSecurityAsOnSanc + totalBldgValueDouble.doubleValue();
                    } else {
                        totalBldgValueStr = "";
                    }
                }
                totalOFMAValDouble = 
                        (Double)sancmap.get("OTHER FIXED MOVABLE ASSETS");
                if (totalOFMAValDouble != null) {
                    if (totalOFMAValDouble.doubleValue() > 0.0) {
                        totalOFMAValueStr = String.valueOf(totalOFMAValDouble);
                        totalSecurityAsOnSanc = 
                                totalSecurityAsOnSanc + totalOFMAValDouble.doubleValue();
                    } else {
                        totalOFMAValueStr = "";
                    }
                }
                totalCurrAssetsValDouble = 
                        (Double)sancmap.get("CURRENT ASSETS");
                if (totalCurrAssetsValDouble != null) {
                    if (totalCurrAssetsValDouble.doubleValue() > 0.0) {
                        totalCurrAssetsValueStr = 
                                String.valueOf(totalCurrAssetsValDouble);
                        totalSecurityAsOnSanc = 
                                totalSecurityAsOnSanc + totalCurrAssetsValDouble.doubleValue();
                    } else {
                        totalCurrAssetsValueStr = "";
                    }
                }
                totalOthersValDouble = (Double)sancmap.get("OTHERS");
                if (totalOthersValDouble != null) {
                    if (totalOthersValDouble.doubleValue() > 0.0) {
                        totalOthersValueStr = 
                                String.valueOf(totalOthersValDouble);
                        totalSecurityAsOnSanc = 
                                totalSecurityAsOnSanc + totalOthersValDouble.doubleValue();
                    } else {
                        totalOthersValueStr = "";
                    }
                }
            }
            securityMap.put("LAND", totalLandValueStr);
            securityMap.put("MACHINE", totalMachineValueStr);
            securityMap.put("BUILDING", totalBldgValueStr);
            securityMap.put("OTHER_FIXED_MOVABLE_ASSETS", totalOFMAValueStr);
            securityMap.put("CUR_ASSETS", totalCurrAssetsValueStr);
            securityMap.put("OTHERS", totalOthersValueStr);

            totalLandValueStr = "";
            totalMachineValueStr = "";
            totalBldgValueStr = "";
            totalOFMAValueStr = "";
            totalCurrAssetsValueStr = "";
            totalOthersValueStr = "";
            securitydetails = null;
            String reasonReduction = "";

            //        securitydetails = dao.getPrimarySecurityAndNetworthOfGuarantorsAsOnNpa(borrowerId,memberId,npaId);

            if (npamap != null) {

                totalNetWorthDouble = (Double)npamap.get("networth");
                if (totalNetWorthDouble != null) {
                    networthAsOnNpaDt = totalNetWorthDouble.doubleValue();
                }

                totalLandValDouble = (Double)npamap.get("LAND");
                if (totalLandValDouble != null) {
                    if (totalLandValDouble.doubleValue() > 0.0) {
                        totalLandValueStr = String.valueOf(totalLandValDouble);
                        totalSecurityAsOnNpa = 
                                totalSecurityAsOnNpa + totalLandValDouble.doubleValue();
                    } else {
                        totalLandValueStr = "";
                    }
                }


                totalMachineValDouble = (Double)npamap.get("MACHINE");
                if (totalMachineValDouble != null) {
                    if (totalMachineValDouble.doubleValue() > 0.0) {
                        totalMachineValueStr = 
                                String.valueOf(totalMachineValDouble);
                        totalSecurityAsOnNpa = 
                                totalSecurityAsOnNpa + totalMachineValDouble.doubleValue();
                    } else {
                        totalMachineValueStr = "";
                    }
                }
                totalBldgValueDouble = (Double)npamap.get("BUILDING");
                if (totalBldgValueDouble != null) {
                    if (totalBldgValueDouble.doubleValue() > 0.0) {
                        totalBldgValueStr = 
                                String.valueOf(totalBldgValueDouble);
                        totalSecurityAsOnNpa = 
                                totalSecurityAsOnNpa + totalBldgValueDouble.doubleValue();
                    } else {
                        totalBldgValueStr = "";
                    }
                }
                totalOFMAValDouble = 
                        (Double)npamap.get("OTHER FIXED MOVABLE ASSETS");
                if (totalOFMAValDouble != null) {
                    if (totalOFMAValDouble.doubleValue() > 0.0) {
                        totalOFMAValueStr = String.valueOf(totalOFMAValDouble);
                        totalSecurityAsOnNpa = 
                                totalSecurityAsOnNpa + totalOFMAValDouble.doubleValue();
                    } else {
                        totalOFMAValueStr = "";
                    }
                }
                totalCurrAssetsValDouble = 
                        (Double)npamap.get("CURRENT ASSETS");
                if (totalCurrAssetsValDouble != null) {
                    if (totalCurrAssetsValDouble.doubleValue() > 0.0) {
                        totalCurrAssetsValueStr = 
                                String.valueOf(totalCurrAssetsValDouble);
                        totalSecurityAsOnNpa = 
                                totalSecurityAsOnNpa + totalCurrAssetsValDouble.doubleValue();
                    } else {
                        totalCurrAssetsValueStr = "";
                    }
                }
                totalOthersValDouble = (Double)npamap.get("OTHERS");
                if (totalOthersValDouble != null) {
                    if (totalOthersValDouble.doubleValue() > 0.0) {
                        totalOthersValueStr = 
                                String.valueOf(totalOthersValDouble);
                        totalSecurityAsOnNpa = 
                                totalSecurityAsOnNpa + totalOthersValDouble.doubleValue();
                    } else {
                        totalOthersValueStr = "";
                    }
                }
                reasonReduction = (String)npamap.get("reasonReduction");
            }
            securitymapnpa.put("LAND", totalLandValueStr);
            securitymapnpa.put("MACHINE", totalMachineValueStr);
            securitymapnpa.put("BUILDING", totalBldgValueStr);
            securitymapnpa.put("OTHER_FIXED_MOVABLE_ASSETS", 
                               totalOFMAValueStr);
            securitymapnpa.put("CUR_ASSETS", totalCurrAssetsValueStr);
            securitymapnpa.put("OTHERS", totalOthersValueStr);


            gmActionForm.set("securityAsOnSancDt", securityMap);
            gmActionForm.set("networthAsOnSancDt", networthAsOnSancDt);
            gmActionForm.set("totalSecurityAsOnSanc", totalSecurityAsOnSanc);

            gmActionForm.set("securityAsOnNpaDt", securitymapnpa);
            gmActionForm.set("networthAsOnNpaDt", networthAsOnNpaDt);
            gmActionForm.set("reasonForReductionAsOnNpaDt", reasonReduction);
            gmActionForm.set("totalSecurityAsOnNpa", totalSecurityAsOnNpa);
        }


        Log.log(Log.INFO, "GMAction", "showNPADetails", "Exited");

    }
    catch(Exception e)
    {
    	Log.log(Log.INFO, "GMAction", "exception in showNPADetails  ="+e.getMessage(),"");
    	Log.logException(e);
    }
    finally {
               DBConnection.freeConnection(conn);
           }

        return mapping.findForward(Constants.SUCCESS);
    	
    	
    }*/


    public ActionForward addMoreRecoProcs(ActionMapping mapping, 
                                          ActionForm form, 
                                          HttpServletRequest request, 
                                          HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "addMoreRecoProcs", "Entered");

        GMActionForm gmActionForm = (GMActionForm)form;

        if (gmActionForm.getIsRecoveryInitiated().equals("N")) {
            return mapping.findForward("success");
        }

        Map recoveryProcedures = gmActionForm.getRecProcedures();

        Set recoveryProceduresSet = recoveryProcedures.keySet();

        Iterator recoveryProceduresIterator = recoveryProceduresSet.iterator();

        String count = null;

        while (recoveryProceduresIterator.hasNext()) {
            String key = (String)recoveryProceduresIterator.next();

            Log.log(5, "GMAction", "addMoreRecoProcs", " key " + key);

            count = key.substring(key.indexOf("-") + 1, key.length());

            Log.log(5, "GMAction", "addMoreRecoProcs", " count " + count);
        }

        request.setAttribute("IsRecProcRequired", new Boolean(true));

        Log.log(4, "GMAction", "addMoreRecoProcs", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward saveNpaDetails(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws Exception {


        GMProcessor gmProcessor = new GMProcessor();
        HttpSession session = (HttpSession)request.getSession(false);

        NPADetails npaDetail = new NPADetails();

        DynaValidatorActionForm dynaActionForm = (DynaValidatorActionForm)form;
        
        
        String cgpan = (String) dynaActionForm.get("cgpan1");
		Date npadate = (java.util.Date) dynaActionForm.get("npaDt"); 
		Date revivaldate=null;
         System.out.println("cgpan===for npa"+cgpan+"npadate=="+npadate);
 		Connection connection = DBConnection.getConnection();
 		Statement str1 = connection.createStatement();
 		ResultSet rs=null;
 		//int count=100; 
 		
 		String query = "select nvl(rt,'01-jan-2000') from (select max(nvl(trunc(REVIVAL_DATE) ,'01-jan-2000')) as rt   from CGTSIINTRANETUSER.revival_cgpan_data@DB_INTRA where cgpan ='"+cgpan+"' )";
 		
       
		 rs = str1.executeQuery(query);
		

		while (rs.next()) {

			 revivaldate = rs.getDate(1);
			

		}
		SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy");
	  
	    String Revdate = sm.format(revivaldate);
	    
	    
      
	        if (npadate.compareTo(revivaldate) <= 0) {
	        	throw new NoDataException("NPA date should be greater than the Revival Request date "+Revdate+".");
	        }

        String message = "NPA Details Saved Successfully.";
        //      Integer size = (Integer)request.getAttribute("size");
        Integer size = (Integer)dynaActionForm.get("size");
        int total = size.intValue();

        String borrowerId = 
            (String)dynaActionForm.get("borrowerId"); //------------------------------borrower id
        npaDetail.setCgbid(borrowerId);
        Log.log(Log.INFO, "GMAction", "saveNpaDetails", "bid-" + borrowerId);

        String unitName = (String)dynaActionForm.get("unitName");

        /*@@@@@@@@@@@@@ CGPAN DETAILS @@@@@@@@@*/

         cgpan = null;
        String guarStartDt = null;
        String sanctionDt = null;
        String firstDisbDt = null;
        String lastDisbDt = null;
        String firstInstDt = null;
        String moratoriumPrincipal = null;
        String moratoriumInterest = null;

        String totalDisbAmt = null;
        String repayPrincipal = null;
        String repayInterest = null;
        String outstandingPrincipal = null;
        String outstandingInterest = null;
        String approvedAmount = null;

        Map tcMap = null;
        Map wcMap = null;
        Vector tcVector = new Vector();
        Vector wcVector = new Vector();

        for (int i = 1; i <= total; i++) {

            cgpan = "cgpan" + i;
            //      guarStartDt = "guarStartDt"+i;
            //      sanctionDt = "sanctionDt"+i;

            outstandingPrincipal = "outstandingPrincipal" + i;
            outstandingInterest = "outstandingInterest" + i;
            //       approvedAmount = "approvedAmount"+i;

            String cgpanNo = (String)dynaActionForm.get(cgpan);
            String loanType = cgpanNo.substring(cgpanNo.length() - 2);


            if ("TC".equals(loanType)) {

                firstDisbDt = "firstDisbDt" + i;
                lastDisbDt = "lastDisbDt" + i;
                firstInstDt = "firstInstDt" + i;
                moratoriumPrincipal = "moratoriumPrincipal" + i;
                moratoriumInterest = "moratoriumInterest" + i;

                totalDisbAmt = "totalDisbAmt" + i;
                repayPrincipal = "repayPrincipal" + i;
                repayInterest = "repayInterest" + i;
                tcMap = new HashMap();
                tcMap.put("CGPAN", cgpanNo);
                tcMap.put("FIRST_DISB_DT", dynaActionForm.get(firstDisbDt));
                tcMap.put("LAST_DISB_DT", dynaActionForm.get(lastDisbDt));
                tcMap.put("FIRST_INST_DT", dynaActionForm.get(firstInstDt));
                tcMap.put("PRINCIPAL_MORATORIUM", 
                          dynaActionForm.get(moratoriumPrincipal));
                tcMap.put("INTEREST_MORATORIUM", 
                          dynaActionForm.get(moratoriumInterest));
                tcMap.put("TOTAL_DISB_AMT", dynaActionForm.get(totalDisbAmt));
                tcMap.put("PRINCIPAL_REPAY", 
                          dynaActionForm.get(repayPrincipal));
                tcMap.put("INTEREST_REPAY", dynaActionForm.get(repayInterest));
                tcMap.put("PRINCIPAL_OS", 
                          dynaActionForm.get(outstandingPrincipal));
              //System.out.println("outstandingPrincipal----------"+dynaActionForm.get(outstandingPrincipal));
              
              Object d=dynaActionForm.get(outstandingPrincipal);
              Double z=0.0;
            //  System.out.println("d-----"+d);
             // System.out.println("Z-----"+z);
             // System.out.println("z==d--"+z.equals(d));
              if(z.equals(d))
              {
            	 // System.out.println("z==d--"+z==d);
            	  throw new  MessageException("Outstanding Amount should be greater than 0");
              }
                tcMap.put("INTEREST_OS", 
                          dynaActionForm.get(outstandingInterest));

                tcVector.add(tcMap);
            } else {
                wcMap = new HashMap();
                wcMap.put("CGPAN", cgpanNo);
                wcMap.put("PRINCIPAL_OS", 
                          dynaActionForm.get(outstandingPrincipal));
                
                //System.out.println("outstandingPrincipal----------"+dynaActionForm.get(outstandingPrincipal));
                //Diksha
                Object d=dynaActionForm.get(outstandingPrincipal);
                Double z=0.0;
               // System.out.println("d-----"+d);
               // System.out.println("Z-----"+z);
               // System.out.println("z==d--"+z.equals(d));
                if(z.equals(d))
                {
                	//System.out.println("z==d--"+z==d);
              	  throw new  MessageException("Outstanding Amount should be greater than 0");
                }
                //end
                wcMap.put("INTEREST_OS", 
                          dynaActionForm.get(outstandingInterest));

                wcVector.add(wcMap);
            }
        }


        /*@@@@@@@@@@@@@ NPA DETAILS @@@@@@@@@@@*/

        Date npaTurnDate = 
            (java.util.Date)dynaActionForm.get("npaDt"); //------------------------------------------------npa date
        npaDetail.setNpaDate(npaTurnDate);

        String isAsPerRBI = (String)dynaActionForm.get("isAsPerRBI");
        npaDetail.setIsAsPerRBI(isAsPerRBI);

        String npaConfirm = (String)dynaActionForm.get("npaConfirm");
        npaDetail.setNpaConfirm(npaConfirm);

        String npaReason = 
            (String)dynaActionForm.get("npaReason"); //----------------------------------------------npa reason
        npaDetail.setNpaReason(npaReason);

        String effortsTaken = 
            (String)dynaActionForm.get("effortsTaken"); //------------------------------------------efforts taken
        npaDetail.setEffortsTaken(effortsTaken);


        String isAcctReconstructed = 
            (String)dynaActionForm.get("isAcctReconstructed");
        npaDetail.setIsAcctReconstructed(isAcctReconstructed);

        String subsidyFlag = "";
        String isSubsidyRcvd = "";
        String isSubsidyAdjusted = "";
        Date subLastRcvdDt = null;
        Double subLastRcvdAmt = 0.0;

        subsidyFlag = (String)dynaActionForm.get("subsidyFlag");
        if (!GenericValidator.isBlankOrNull(subsidyFlag) && 
            "Y".equals(subsidyFlag)) {
            isSubsidyRcvd = (String)dynaActionForm.get("isSubsidyRcvd");
            if (!GenericValidator.isBlankOrNull(isSubsidyRcvd) && 
                "Y".equals(isSubsidyRcvd)) {
                isSubsidyAdjusted = 
                        (String)dynaActionForm.get("isSubsidyAdjusted");
                if (!GenericValidator.isBlankOrNull(isSubsidyAdjusted) && 
                    "Y".equals(isSubsidyAdjusted)) {
                    subLastRcvdDt = 
                            (java.util.Date)dynaActionForm.get("subsidyLastRcvdDt");
                    subLastRcvdAmt = 
                            (java.lang.Double)dynaActionForm.get("subsidyLastRcvdAmt");
                }
            }

        }

        npaDetail.setSubsidyFlag(subsidyFlag);
        npaDetail.setIsSubsidyRcvd(isSubsidyRcvd);
        npaDetail.setIsSubsidyAdjusted(isSubsidyAdjusted);
        npaDetail.setSubsidyLastRcvdAmt(subLastRcvdAmt);
        npaDetail.setSubsidyLastRcvdDt(subLastRcvdDt);

        Date lastinspectionDt = 
            (java.util.Date)dynaActionForm.get("lastInspectionDt");
        npaDetail.setLastInspectionDt(lastinspectionDt);

        /*@@@@@@@@@ SECURITY DETAIL @@@@@@@@@*/
        Map securityMap = new HashMap();
        Map securityAsOnSancDt = null;
        Map securityAsOnNpaDt = null;
        Double networthAsOnSancDt = 0.0;
        Double networthAsOnNpaDt = 0.0;
        String reasonForReductionAsOnNpaDt = "";

        securityAsOnSancDt = 
                (java.util.Map)dynaActionForm.get("securityAsOnSancDt");
        securityAsOnNpaDt = 
                (java.util.Map)dynaActionForm.get("securityAsOnNpaDt");
        networthAsOnSancDt = 
                (java.lang.Double)dynaActionForm.get("networthAsOnSancDt");
        networthAsOnNpaDt = 
                (java.lang.Double)dynaActionForm.get("networthAsOnNpaDt");
        reasonForReductionAsOnNpaDt = 
                (String)dynaActionForm.get("reasonForReductionAsOnNpaDt");


        securityMap.put("securityAsOnSancDt", securityAsOnSancDt);
        securityMap.put("securityAsOnNpaDt", securityAsOnNpaDt);
        securityMap.put("networthAsOnSancDt", networthAsOnSancDt);
        securityMap.put("networthAsOnNpaDt", networthAsOnNpaDt);
        securityMap.put("reasonForReductionAsOnNpaDt", 
                        reasonForReductionAsOnNpaDt);

        NPADetails npaDetailsFromDB = gmProcessor.getNPADetails(borrowerId);
        String npaId = null;

        if (npaDetailsFromDB != null) {
            npaId = npaDetailsFromDB.getNpaId();
            dynaActionForm.set("npaId", npaId);
            npaDetail.setNpaId(npaId);
        }


        if (npaDetailsFromDB == null) {
            String srcMenu = (String)session.getAttribute("mainMenu");
            // System.out.println("SRC Menu :" + srcMenu);
            String srcSubMenu = (String)session.getAttribute("subMenuItem");
            // System.out.println("SRC Sub Menu :" + srcSubMenu);
            if ((srcMenu != null) && (srcSubMenu != null)) {
                // System.out.println("SRC Menu :" + srcMenu);
                // System.out.println("SRC Sub Menu :" + "srcSubMenu");
                // System.out.println("Control 1");
                if ((srcMenu.equals(MenuOptions.getMenu(MenuOptions.CP_CLAIM_FOR))) && 
                    (srcSubMenu.equals(MenuOptions.getMenu(MenuOptions.CP_CLAIM_FOR_FIRST_INSTALLMENT)))) {
                     System.out.println("Control 2"); 
                    gmProcessor.insertNPADetails(npaDetail, tcVector, wcVector, 
                                                 securityMap);
                    srcMenu = null;
                    srcSubMenu = null;

                    Log.log(Log.INFO, "GMAction", 
                            "saveNpaDetails for Claim Application", "Exited");
                    return mapping.findForward("claimdetails");
                }
            }
            Log.log(Log.INFO, "GMAction", "saveNpaDetails", 
                    "New NPA Details is Saved");
            System.out.println("Control 3"); 
            gmProcessor.insertNPADetails(npaDetail, tcVector, wcVector, 
                                         securityMap);
        } else {
            Log.log(Log.INFO, "GMAction", "saveNpaDetails", 
                    "Modified NPA Details is Saved");
            System.out.println("Control 4"); 
           gmProcessor.updateNPADetails(npaDetail, tcVector, wcVector, 
                                         securityMap);
        }

        request.setAttribute("message", message);

//        dynaActionForm.set("memberId", "");
//        dynaActionForm.set("borrowerId", "");
//        dynaActionForm.set("borrowerName", "");
//        dynaActionForm.set("cgpan", "");
//        session.removeAttribute("newNpaForm");
        Log.log(Log.INFO, "GMAction", "saveNpaDetails", "Exited");
        return mapping.findForward(Constants.SUCCESS);

    }

    public ActionForward GetBidCgpanOutstanding(ActionMapping mapping, 
                                                ActionForm form, 
                                                HttpServletRequest request, 
                                                HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "GetBidCgpanOutstanding", "Entered");

        GMActionForm gmActionForm = (GMActionForm)form;

        gmActionForm.setBorrowerId("");
        gmActionForm.setCgpan("");
        gmActionForm.setBorrowerName("");

        GMProcessor gmProcessor = new GMProcessor();

        User user = getUserInformation(request);
        String bankId = user.getBankId();
        String zoneId = user.getZoneId();
        String branchId = user.getBranchId();
        String memberId = bankId.concat(zoneId).concat(branchId);

        gmActionForm.setPeriodicBankId(bankId);
        if (bankId.equals("0000")) {
            memberId = "";
        }
        gmActionForm.setMemberId(memberId);

        Log.log(4, "GMAction", "GetBidCgpanOutstanding", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward showOutstandingDetails(ActionMapping mapping, 
                                                ActionForm form, 
                                                HttpServletRequest request, 
                                                HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "showOutstandingDetails", "Entered");

        ArrayList periodicInfoDetails = new ArrayList();

        GMProcessor gmProcessor = new GMProcessor();

        HttpSession session = request.getSession(false);

        GMActionForm gmActionForm = (GMActionForm)form;

        String memberId = gmActionForm.getMemberId();
        String cgpan = gmActionForm.getCgpan().toUpperCase();
        String borrowerId = gmActionForm.getBorrowerId().toUpperCase();
        String borrowerName = gmActionForm.getBorrowerName();

        int type = 0;

        String forward = "";

        ClaimsProcessor processor = new ClaimsProcessor();
        ApplicationProcessor appProcessor = new ApplicationProcessor();

        Vector memberids = processor.getAllMemberIds();
        if (!memberids.contains(memberId)) {
            throw new NoMemberFoundException("Member Id :" + memberId + 
                                             " does not exist in the database.");
        }

        ArrayList borrowerIds = new ArrayList();
        borrowerIds = gmProcessor.getBorrowerIds(memberId);
        GMDAO dao = new GMDAO();
        Vector cgpansDetails = null;
        if ((!borrowerId.equals("")) && (cgpan.equals("")) && 
            (borrowerName.equals(""))) {
            type = 0;
            if (!borrowerIds.contains(borrowerId)) {
                gmActionForm.setBorrowerId("");
                throw new NoDataException(borrowerId + 
                                          " is not among the borrower" + 
                                          " Ids for the Member Id :" + 
                                          memberId + ". Please enter correct" + 
                                          " Member Id and Borrower Id.");
            }

            int claimCount = appProcessor.getClaimCount(borrowerId);
            if (claimCount > 0) {
                throw new MessageException("Outstanding Details for this borrower cannot be modified since Claim Application has been submitted");
            }

            
            cgpansDetails = dao.getCGPANDetailsPeriodicInfo(borrowerId, memberId);
            if(cgpansDetails == null || cgpansDetails.size() == 0){
                throw new NoDataException("There are no Live Loan Account(s) for this borrower or Guarantee has not been started.");
            }
        
        
            periodicInfoDetails = 
                    gmProcessor.viewOutstandingDetails(borrowerId, type);

            gmActionForm.setOsPeriodicInfoDetails(periodicInfoDetails);

            forward = "success";
        } else if ((!cgpan.equals("")) && (borrowerId.equals("")) && 
                   (borrowerName.equals(""))) {
            type = 0;

            String bIdForThisCgpan = processor.getBorowwerForCGPAN(cgpan);
            Log.log(5, "GMAction", "showOutstandingDetails", 
                    " Bid For Pan - " + bIdForThisCgpan);
            if (!borrowerIds.contains(bIdForThisCgpan)) {
                throw new NoDataException(cgpan + "is not a valid Cgpan for " + 
                                          "the Member Id :" + memberId + 
                                          ". Please enter correct Cgpan");
            }

            int claimCount = appProcessor.getClaimCount(bIdForThisCgpan);
            if (claimCount > 0) {
                throw new MessageException("Outstanding Details for this borrower cannot be modified since Claim Application has been submitted");
            }

            gmActionForm.setBorrowerId(bIdForThisCgpan);
            
            cgpansDetails = dao.getCGPANDetailsPeriodicInfo(bIdForThisCgpan, memberId);
            if(cgpansDetails == null || cgpansDetails.size() == 0){
                throw new NoDataException("There are no Live Loan Account(s) for this borrower or Guarantee has not been started.");
            }
            
            periodicInfoDetails = 
                    gmProcessor.viewOutstandingDetails(bIdForThisCgpan, type);

            gmActionForm.setOsPeriodicInfoDetails(periodicInfoDetails);

            bIdForThisCgpan = null;

            forward = "success";
        } else if ((!borrowerName.equals("")) && (borrowerId.equals("")) && 
                   (cgpan.equals(""))) {
            type = 2;
            ArrayList bIdForBorrowerName = 
                gmProcessor.getBorrowerIdForBorrowerName(borrowerName, 
                                                         memberId);

            if ((bIdForBorrowerName == null) || 
                (bIdForBorrowerName.size() == 0)) {
                throw new NoDataException("There are no Borrower Ids for this member");
            }

            session.setAttribute("displayFlag", "2");
            gmActionForm.setBorrowerIds(bIdForBorrowerName);
            forward = "bidList";
        }

        Log.log(4, "GMAction", "showOutstandingDetails", "Exited");

        return mapping.findForward(forward);
    }


    public ActionForward saveOutstandingDetails(ActionMapping mapping, 
                                                ActionForm form, 
                                                HttpServletRequest request, 
                                                HttpServletResponse response) throws Exception {

        Log.log(Log.INFO, "GMAction", "saveOutstandingDetails", "Entered");

        GMActionForm gmActionForm = (GMActionForm)form;
        GMProcessor gmProcessor = new GMProcessor();

        String borrowerId = gmActionForm.getBorrowerId();

        Log.log(Log.INFO, "GMAction", "saveOutstandingDetails", 
                "borrowerId " + borrowerId);

        ArrayList tempPeriodicDtls = 
            gmProcessor.viewOutstandingDetails(borrowerId, 
                                               GMConstants.TYPE_ZERO);
        ArrayList tempPeriodicDtls1 = 
            gmProcessor.viewOutstandingDetails(borrowerId, 
                                               GMConstants.TYPE_ZERO);

        ArrayList tempOutDtls = null;

        ArrayList outDtls = null;
        ArrayList outDtls1 = null;

        ArrayList tempOutAmts = null;
        ArrayList tempOutAmts1 = null;

        ArrayList newWcOutstandingAmounts = new ArrayList();
        ArrayList modifiedWcOutstandingAmounts = new ArrayList();

        ArrayList newTcOutstandingAmounts = new ArrayList();
        ArrayList modifiedTcOutstandingAmounts = new ArrayList();

        OutstandingAmount outstandingAmount = null;

        OutstandingAmount modifiedOutstandingAmount = null;
        OutstandingAmount newOutstandingAmount = null;

        OutstandingAmount modifiedTcOutstandingAmount = null;
        OutstandingAmount newTcOutstandingAmount = null;

        java.util.Map cgpanWcMap = gmActionForm.getCgpansForWc();
        java.util.Set cgpanWcSet = cgpanWcMap.keySet();
        java.util.Iterator cgpanWcIterator = cgpanWcSet.iterator();
        Log.log(Log.INFO, "GMAction", "saveOutstandingDetails", 
                "cgpanWcMap " + cgpanWcMap.size());

        Map cgpanTcMap = gmActionForm.getCgpansForTc();
        Set cgpanTcSet = cgpanTcMap.keySet();
        Iterator cgpanTcIterator = cgpanTcSet.iterator();
        Log.log(Log.INFO, "GMAction", "saveOutstandingDetails", 
                "cgpanTcMap " + cgpanTcMap.size());

        Map wcFBPrAmountMap = gmActionForm.getWcFBPrincipalOutstandingAmount();
        Set wcFBPrAmountSet = wcFBPrAmountMap.keySet();
        Iterator wcFBPrAmountIterator = wcFBPrAmountSet.iterator();

        Map wcNFBPrAmountMap = 
            gmActionForm.getWcNFBPrincipalOutstandingAmount();
        Set wcNFBPrAmountSet = wcNFBPrAmountMap.keySet();
        Iterator wcNFBPrAmountIterator = wcNFBPrAmountSet.iterator();


        Map wcFBIntAmountMap = gmActionForm.getWcFBInterestOutstandingAmount();

        Map wcNFBIntAmountMap = 
            gmActionForm.getWcNFBInterestOutstandingAmount();

        Map tcPrAmountMap = gmActionForm.getTcPrincipalOutstandingAmount();
        Set tcPrAmountSet = tcPrAmountMap.keySet();
        Iterator tcPrAmountIterator = tcPrAmountSet.iterator();

        Map tcDateMap = gmActionForm.getTcOutstandingAsOnDate();

        Map wcFBDateMap = gmActionForm.getWcFBOutstandingAsOnDate();
        Map wcNFBDateMap = gmActionForm.getWcNFBOutstandingAsOnDate();

        Map wcoIdMap = gmActionForm.getWorkingCapitalId();
        Set wcoIdSet = wcoIdMap.keySet();
        Iterator wcoIdIterator = wcoIdSet.iterator();

        Map tcoIdMap = gmActionForm.getTermCreditId();
        Set tcoIdSet = tcoIdMap.keySet();
        Iterator tcoIdIterator = wcoIdSet.iterator();


        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        String cgpan = null;
        String wcKey = null;
        int count = 0;
        double wcFBPrAmt = 0;
        double wcFBIntAmt = 0;
        Date wcFBDate = null;

        double wcNFBPrAmt = 0;
        double wcNFBIntAmt = 0;
        Date wcNFBDate = null;

        String tcKey = null;
        double tcPrAmt = 0;
        Date tcDate = null;

        String tempWcId = null;
        String tempCgpan = null;

        String tempTcId = null;
        double tempTcPrAmt = 0;
        Date tempTcDate = null;

        double tempWcFBIntAmt = 0;
        double tempWcFBPrAmt = 0;
        Date tempWcFBDate = null;

        double tempWcNFBIntAmt = 0;
        double tempWcNFBPrAmt = 0;
        Date tempWcNFBDate = null;


        while (cgpanWcIterator.hasNext()) {
            cgpan = (String)cgpanWcMap.get(cgpanWcIterator.next());
            Log.log(Log.INFO, "GMAction", "saveOutstandingDetails", 
                    "Inside CgpanIterator cgpan  :" + cgpan);

            wcoIdIterator = wcoIdSet.iterator();
            wcFBPrAmountIterator = wcFBPrAmountSet.iterator();

            count = 0;
            while (wcFBPrAmountIterator.hasNext()) {
                //wcFBPrAmountIterator.next();
                //wcKey = cgpan+"-"+count;
                wcKey = (String)wcFBPrAmountIterator.next();

                Log.log(Log.INFO, "GMAction", "saveOutstandingDetails", 
                        "key ->" + wcKey);
                Log.log(Log.INFO, "GMAction", "saveOutstandingDetails", 
                        "contains key ->" + 
                        wcFBPrAmountMap.containsKey(wcKey));

                if (wcKey != null && !wcKey.equals("") && 
                    wcFBPrAmountMap.containsKey(wcKey)) {
                    Log.log(Log.INFO, "GMAction", "saveOutstandingDetails", 
                            "contains key entering..");
                    String wcFBPrAmtVal = (String)wcFBPrAmountMap.get(wcKey);
                    String wcNFBPrAmtVal = (String)wcNFBPrAmountMap.get(wcKey);
                    if (wcFBPrAmtVal != null && !wcFBPrAmtVal.equals("")) {
                        wcFBPrAmt = Double.parseDouble(wcFBPrAmtVal);
                        Log.log(Log.INFO, "GMAction", "saveOutstandingDetails", 
                                "Key is matched wcFBPrAmt Amount :" + 
                                wcFBPrAmt);
                    }
                    if (wcNFBPrAmtVal != null && !wcNFBPrAmtVal.equals("")) {
                        wcNFBPrAmt = Double.parseDouble(wcNFBPrAmtVal);
                    }
                    Log.log(Log.INFO, "GMAction", "saveOutstandingDetails", 
                            "wcFBPrAmt value:" + wcFBPrAmt);
                    Log.log(Log.INFO, "GMAction", "saveOutstandingDetails", 
                            "wcNFBPrAmt value:" + wcNFBPrAmt);
                    /*                              if(wcFBPrAmtVal.equals("") &&wcNFBPrAmtVal.equals("") ){

                                    Log.log(Log.INFO,"GMAction","saveOutstandingDetails","both values null");
                                    continue;
                            }
    */
                    String wcFBIntAmtVal = (String)wcFBIntAmountMap.get(wcKey);
                    String wcNFBIntAmtVal = 
                        (String)wcNFBIntAmountMap.get(wcKey);
                    if (wcFBIntAmtVal != null && !wcFBIntAmtVal.equals("")) {
                        wcFBIntAmt = Double.parseDouble(wcFBIntAmtVal);
                        Log.log(Log.INFO, "GMAction", "saveOutstandingDetails", 
                                "Key is matched wcFBIntAmt Amount :" + 
                                wcFBIntAmt);
                    }
                    if (wcNFBIntAmtVal != null && !wcNFBIntAmtVal.equals("")) {

                        wcNFBIntAmt = Double.parseDouble(wcNFBIntAmtVal);
                    }
                    /*                              if(wcFBIntAmtVal.equals("") &&wcNFBIntAmtVal.equals("") ){

                                    continue;
                            }
    */
                    String wcFBDateVal = (String)wcFBDateMap.get(wcKey);
                    String wcNFBDateVal = (String)wcNFBDateMap.get(wcKey);
                    if (wcFBDateVal != null && !wcFBDateVal.equals("")) {
                        wcFBDate = 
                                simpleDateFormat.parse(wcFBDateVal, new ParsePosition(0));
                        Log.log(Log.INFO, "GMAction", "saveOutstandingDetails", 
                                "Key is matched  wcFB Date :" + wcFBDate);
                    }

                    if (wcNFBDateVal != null && !wcNFBDateVal.equals("")) {
                        wcNFBDate = 
                                simpleDateFormat.parse(wcNFBDateVal, new ParsePosition(0));
                    }
                    Log.log(Log.INFO, "GMAction", "saveOutstandingDetails", 
                            "Key is matched  wcFB Date :" + wcNFBDate);
                    /*                              if(wcFBDateVal==null &&wcNFBDateVal==null){

                                    continue;
                            }
    */
                    Log.log(Log.INFO, "GMAction", "saveOutstandingDetails", 
                            "wckey 1->" + wcKey);

                    String wcIdKey = null;

                    while (wcoIdIterator.hasNext()) {
                        Log.log(Log.INFO, "GMAction", "saveOutstandingDetails", 
                                "wckey 2->" + wcKey);
                        wcIdKey = (String)wcoIdIterator.next();
                        Log.log(Log.INFO, "GMAction", 
                                "saveOutstandingDetails ", 
                                "WcidKey " + wcIdKey);
                        Log.log(Log.INFO, "GMAction", 
                                "saveOutstandingDetails ", "WcidKey " + wcKey);
                        Log.log(Log.INFO, "GMAction", 
                                "saveOutstandingDetails ", 
                                "WcidKey starts" + wcIdKey.startsWith(wcKey));

                        if (wcIdKey != null && !wcIdKey.equals("") && 
                            wcIdKey.startsWith(wcKey)) {
                            wcIdKey = 
                                    wcIdKey.substring(wcIdKey.lastIndexOf("-") + 
                                                      1, wcIdKey.length());

                            Log.log(Log.INFO, "GMAction", 
                                    "saveOutstandingDetails ", 
                                    "idKey obtained is " + wcIdKey);

                            break;
                        }

                        wcIdKey = null;
                    }
                    if (wcIdKey != null) //func for setting updates
                    {
                        Log.log(Log.INFO, "GMAction", 
                                "saveOutstandingDetails ", "is not null");
                        breakLabel:
                        for (int i = 0; i < tempPeriodicDtls.size(); ++i) {
                            PeriodicInfo pr = 
                                (PeriodicInfo)tempPeriodicDtls.get(i);
                            outDtls = pr.getOutstandingDetails();
                            for (int j = 0; j < outDtls.size(); ++j) {
                                OutstandingDetail outDtl = 
                                    (OutstandingDetail)outDtls.get(j);
                                tempOutAmts = outDtl.getOutstandingAmounts();
                                Log.log(Log.INFO, "GMAction", 
                                        "saveOutstandingDetails", 
                                        "2-for,Temp OutDtl" + "list size :" + 
                                        tempOutAmts.size());

                                for (int k = 0; k < tempOutAmts.size(); ++k) {
                                    OutstandingAmount tempOutAmt = 
                                        (OutstandingAmount)tempOutAmts.get(k);
                                    tempWcId = tempOutAmt.getWcoId();
                                    Log.log(Log.INFO, "GMAction", 
                                            "saveOutstandingDetails", 
                                            "Inside 3 For loop tempWcId:" + 
                                            tempWcId);

                                    tempCgpan = tempOutAmt.getCgpan();
                                    Log.log(Log.INFO, "GMAction", 
                                            "saveOutstandingDetails", 
                                            "Inside 3 For loop TempCgpan from tempOutAmt OBJ  : " + 
                                            tempCgpan);

                                    tempWcFBPrAmt = 
                                            tempOutAmt.getWcFBPrincipalOutstandingAmount();
                                    Log.log(Log.INFO, "GMAction", 
                                            "saveOutstandingDetails", 
                                            "Inside 3 For loop Temp Wc FB Pr Amount from tempOutAmt OBJ : " + 
                                            tempWcFBPrAmt);

                                    tempWcFBIntAmt = 
                                            tempOutAmt.getWcFBInterestOutstandingAmount();
                                    Log.log(Log.INFO, "GMAction", 
                                            "saveOutstandingDetails", 
                                            "Inside 3 For loop Temp WC FB Int Amount from tempOutAmt OBJ : " + 
                                            tempWcFBIntAmt);

                                    tempWcFBDate = 
                                            tempOutAmt.getWcFBOutstandingAsOnDate();
                                    Log.log(Log.INFO, "GMAction", 
                                            "saveOutstandingDetails", 
                                            "Inside 3 For loop Temp Date from tempOutAmt OBJ: " + 
                                            tempWcFBDate);

                                    tempWcNFBPrAmt = 
                                            tempOutAmt.getWcNFBPrincipalOutstandingAmount();
                                    Log.log(Log.INFO, "GMAction", 
                                            "saveOutstandingDetails", 
                                            "Inside 3 For loop Temp Wc FB Pr Amount from tempOutAmt OBJ : " + 
                                            tempWcNFBPrAmt);

                                    tempWcNFBIntAmt = 
                                            tempOutAmt.getWcNFBInterestOutstandingAmount();
                                    Log.log(Log.INFO, "GMAction", 
                                            "saveOutstandingDetails", 
                                            "Inside 3 For loop Temp WC FB Int Amount from tempOutAmt OBJ : " + 
                                            tempWcNFBIntAmt);

                                    tempWcNFBDate = 
                                            tempOutAmt.getWcNFBOutstandingAsOnDate();
                                    Log.log(Log.INFO, "GMAction", 
                                            "saveOutstandingDetails", 
                                            "Inside 3 For loop Temp Date from tempOutAmt OBJ: " + 
                                            tempWcNFBDate);


                                    if (tempWcId != null && 
                                        tempWcId.equals(wcIdKey)) {
                                        Log.log(Log.INFO, "GMAction", 
                                                "saveOutstandingDetails", 
                                                "TempOutId == Out Id ");
                                        Log.log(Log.INFO, "GMAction", 
                                                "saveOutstandingDetails", 
                                                "wcFBPrAmt :" + wcFBPrAmt);
                                        Log.log(Log.INFO, "GMAction", 
                                                "saveOutstandingDetails", 
                                                "wcNFBPrAmt:" + wcNFBPrAmt);

                                        if ((tempWcFBPrAmt != 0 && 
                                             wcFBPrAmt == 0) && 
                                            (tempWcNFBPrAmt != 0 && 
                                             wcNFBPrAmt == 0) && 
                                            ((tempWcFBDate != null && 
                                              !tempWcFBDate.toString().equals("")) && 
                                             (wcFBDate == null || 
                                              wcFBDate.toString().equals("")))) {
                                            Log.log(Log.INFO, "GMAction", 
                                                    "saveOutstandingDetails", 
                                                    "data being deleted");
                                            throw new MessageException("Existing Data cannot be deleted.It can be only modified");
                                        }
                                        if ((tempWcFBPrAmt != 0 && 
                                             tempWcFBPrAmt != wcFBPrAmt) || 
                                            (tempWcFBIntAmt != 0 && 
                                             tempWcFBIntAmt != wcFBIntAmt) || 
                                            (((tempWcFBDate != null && 
                                               tempWcFBDate.compareTo(wcFBDate) != 
                                               0) || 
                                              tempWcFBDate == null && wcFBDate != 
                                              null)) || 
                                            (tempWcNFBPrAmt != 0 && tempWcNFBPrAmt != 
                                             wcNFBPrAmt) || 
                                            (tempWcNFBIntAmt != 0 && 
                                             tempWcNFBIntAmt != wcNFBIntAmt) || 
                                            ((tempWcNFBDate != null && 
                                              tempWcNFBDate.compareTo(wcNFBDate) != 
                                              0) || 
                                             (tempWcNFBDate == null && wcNFBDate != 
                                              null))) {
                                            modifiedOutstandingAmount = 
                                                    new OutstandingAmount();
                                            modifiedOutstandingAmount.setWcoId(wcIdKey);
                                            Log.log(Log.INFO, "GMAction", 
                                                    "saveOutstandingDetails", 
                                                    "modified id =" + 
                                                    modifiedOutstandingAmount.getWcoId());

                                            modifiedOutstandingAmount.setCgpan(cgpan);
                                            Log.log(Log.INFO, "GMAction", 
                                                    "saveOutstandingDetails", 
                                                    "modified cgpan " + 
                                                    modifiedOutstandingAmount.getCgpan());

                                            modifiedOutstandingAmount.setWcFBInterestOutstandingAmount(wcFBIntAmt);
                                            Log.log(Log.INFO, "GMAction", 
                                                    "saveOutstandingDetails", 
                                                    "modifiedOutstandingAmount FB Int = " + 
                                                    modifiedOutstandingAmount.getWcFBInterestOutstandingAmount());

                                            modifiedOutstandingAmount.setWcFBPrincipalOutstandingAmount(wcFBPrAmt);
                                            Log.log(Log.INFO, "GMAction", 
                                                    "saveOutstandingDetails", 
                                                    "modifiedOutstandingAmount FB PR = " + 
                                                    modifiedOutstandingAmount.getWcFBPrincipalOutstandingAmount());

                                            modifiedOutstandingAmount.setWcFBOutstandingAsOnDate(wcFBDate);
                                            Log.log(Log.INFO, "GMAction", 
                                                    "saveOutstandingDetails", 
                                                    "modifiedOutstandingAmount Date =" + 
                                                    modifiedOutstandingAmount.getWcFBOutstandingAsOnDate());

                                            modifiedOutstandingAmount.setWcNFBInterestOutstandingAmount(wcNFBIntAmt);
                                            Log.log(Log.INFO, "GMAction", 
                                                    "saveOutstandingDetails", 
                                                    "modifiedOutstandingAmount FB Int = " + 
                                                    modifiedOutstandingAmount.getWcNFBInterestOutstandingAmount());

                                            modifiedOutstandingAmount.setWcNFBPrincipalOutstandingAmount(wcNFBPrAmt);
                                            Log.log(Log.INFO, "GMAction", 
                                                    "saveOutstandingDetails", 
                                                    "modifiedOutstandingAmount FB PR = " + 
                                                    modifiedOutstandingAmount.getWcNFBPrincipalOutstandingAmount());

                                            modifiedOutstandingAmount.setWcNFBOutstandingAsOnDate(wcNFBDate);
                                            Log.log(Log.INFO, "GMAction", 
                                                    "saveOutstandingDetails", 
                                                    "modifiedOutstandingAmount Date =" + 
                                                    modifiedOutstandingAmount.getWcNFBOutstandingAsOnDate());


                                            modifiedWcOutstandingAmounts.add(modifiedOutstandingAmount);
                                        }
                                        break breakLabel;
                                    }
                                }
                            }
                        }
                    } else {
                        if ((wcFBIntAmt != 0 && wcFBPrAmt != 0 && 
                             (wcFBDate != null && !wcFBDate.equals(""))) || 
                            (wcNFBIntAmt != 0 && wcNFBPrAmt != 0 && 
                             (wcNFBDate != null && !wcNFBDate.equals("")))) {
                            Log.log(Log.INFO, "GMAction", 
                                    "saveOutstandingDetails", 
                                    "Inside Else loop seting new OutAmt");
                            newOutstandingAmount = new OutstandingAmount();

                            newOutstandingAmount.setCgpan(cgpan);
                            Log.log(Log.INFO, "GMAction", 
                                    "saveOutstandingDetails", 
                                    "NewOutAmount CGpan=" + 
                                    newOutstandingAmount.getCgpan());

                            newOutstandingAmount.setWcFBInterestOutstandingAmount(wcFBIntAmt);
                            Log.log(Log.INFO, "GMAction", 
                                    "saveOutstandingDetails", 
                                    "NewOutAmount Amount=" + 
                                    newOutstandingAmount.getWcFBInterestOutstandingAmount());

                            newOutstandingAmount.setWcFBPrincipalOutstandingAmount(wcFBPrAmt);
                            Log.log(Log.INFO, "GMAction", 
                                    "saveOutstandingDetails", 
                                    "NewOutAmount Date=" + 
                                    newOutstandingAmount.getWcFBPrincipalOutstandingAmount());

                            newOutstandingAmount.setWcFBOutstandingAsOnDate(wcFBDate);
                            Log.log(Log.INFO, "GMAction", 
                                    "saveOutstandingDetails", 
                                    "NewOutAmount Date=" + 
                                    newOutstandingAmount.getWcFBOutstandingAsOnDate());

                            newOutstandingAmount.setWcNFBInterestOutstandingAmount(wcNFBIntAmt);
                            Log.log(Log.INFO, "GMAction", 
                                    "saveOutstandingDetails", 
                                    "NewOutAmount Amount=" + 
                                    newOutstandingAmount.getWcNFBInterestOutstandingAmount());

                            newOutstandingAmount.setWcNFBPrincipalOutstandingAmount(wcNFBPrAmt);
                            Log.log(Log.INFO, "GMAction", 
                                    "saveOutstandingDetails", 
                                    "NewOutAmount Date=" + 
                                    newOutstandingAmount.getWcNFBPrincipalOutstandingAmount());

                            newOutstandingAmount.setWcNFBOutstandingAsOnDate(wcNFBDate);
                            Log.log(Log.INFO, "GMAction", 
                                    "saveOutstandingDetails", 
                                    "NewOutAmount Date=" + 
                                    newOutstandingAmount.getWcNFBOutstandingAsOnDate());

                            newWcOutstandingAmounts.add(newOutstandingAmount);

                        }
                    }
                }
                ++count;
            }
        }

        User user = getUserInformation(request);
        String userId = user.getUserId();
        String fromId = user.getEmailId();
        String message = "Outstanding Details are Successfully Saved";
        String errorMessage = "";

        while (cgpanTcIterator.hasNext()) {
            cgpan = (String)cgpanTcMap.get(cgpanTcIterator.next());
            Log.log(Log.INFO, "GMAction", "saveOutstandingDetails", 
                    "Inside CgpanTCIterator cgpan  :" + cgpan);

            tcoIdIterator = tcoIdSet.iterator();
            tcPrAmountIterator = tcPrAmountSet.iterator();

            count = 0;
            boolean closeFlag = true;

            while (tcPrAmountIterator.hasNext()) {
                tcPrAmountIterator.next();
                tcKey = cgpan + "-" + count;
                Log.log(Log.INFO, "GMAction", "saveOutstandingDetails", 
                        "key ->" + tcKey);

                if (tcKey != null && !tcKey.equals("") && 
                    tcPrAmountMap.containsKey(tcKey)) {
                    String tcPrAmtVal = (String)tcPrAmountMap.get(tcKey);
                    if (!tcPrAmtVal.equals("")) {
                        tcPrAmt = Double.parseDouble(tcPrAmtVal);
                        Log.log(Log.INFO, "GMAction", "saveOutstandingDetails", 
                                "Key is matched for TC-pr Amt Amount :" + 
                                tcPrAmt);

                        if ((tcPrAmt == 0) && (closeFlag == true)) {
                            closeFlag = false;
                            gmProcessor.closure(cgpan, 
                                                GMConstants.CLOSURE_REASON, 
                                                GMConstants.CLOSURE_REASON, 
                                                user.getUserId());

                            try {
                                String reason = "Tenor Expired";
                                gmProcessor.sendMailForClosure(cgpan, userId, 
                                                               fromId, reason);
                            } catch (MailerException e) {
                                errorMessage = 
                                        " But Sending E-mails for TC application whose Outstanding is zero is failed.";
                            }


                        }

                    } else {
                        continue;
                    }

                    String tcDateVal = (String)tcDateMap.get(tcKey);
                    if (!tcDateVal.equals("")) {
                        tcDate = 
                                simpleDateFormat.parse(tcDateVal, new ParsePosition(0));
                        Log.log(Log.INFO, "GMAction", "saveOutstandingDetails", 
                                "Key is matched Tc- Date :" + tcDate);
                    } else {
                        continue;
                    }

                    String tcIdKey = null;

                    while (tcoIdIterator.hasNext()) {
                        tcIdKey = (String)tcoIdIterator.next();
                        Log.log(Log.INFO, "GMAction", 
                                "saveOutstandingDetails ", 
                                "TcidKey " + tcIdKey);

                        if (tcIdKey != null && !tcIdKey.equals("") && 
                            tcIdKey.startsWith(tcKey)) {
                            tcIdKey = 
                                    tcIdKey.substring(tcIdKey.lastIndexOf("-") + 
                                                      1, tcIdKey.length());

                            Log.log(Log.INFO, "GMAction", 
                                    "saveOutstandingDetails ", 
                                    "idKey obtained is " + tcIdKey);

                            break;
                        }

                        tcIdKey = null;
                    }
                    if (tcIdKey != null) //func for setting updates
                    {
                        Log.log(Log.INFO, "GMAction", 
                                "saveOutstandingDetails ", "is not null");
                        breakLabel:
                        for (int i = 0; i < tempPeriodicDtls1.size(); ++i) {
                            PeriodicInfo pr = 
                                (PeriodicInfo)tempPeriodicDtls1.get(i);
                            outDtls1 = pr.getOutstandingDetails();
                            for (int j = 0; j < outDtls1.size(); ++j) {
                                OutstandingDetail outDtl = 
                                    (OutstandingDetail)outDtls1.get(j);
                                tempOutAmts1 = outDtl.getOutstandingAmounts();
                                Log.log(Log.INFO, "GMAction", 
                                        "saveOutstandingDetails", 
                                        "2-for,Temp OutDtl" + "list size :" + 
                                        tempOutAmts1.size());

                                for (int k = 0; k < tempOutAmts1.size(); ++k) {
                                    OutstandingAmount tempOutAmt = 
                                        (OutstandingAmount)tempOutAmts1.get(k);
                                    tempTcId = tempOutAmt.getTcoId();
                                    Log.log(Log.INFO, "GMAction", 
                                            "saveOutstandingDetails", 
                                            "Inside 3 For loop tempTcId:" + 
                                            tempTcId);

                                    tempCgpan = tempOutAmt.getCgpan();
                                    Log.log(Log.INFO, "GMAction", 
                                            "saveOutstandingDetails", 
                                            "Inside 3 For loop TempCgpan from tempOutAmt OBJ  : " + 
                                            tempCgpan);

                                    tempTcPrAmt = 
                                            tempOutAmt.getTcPrincipalOutstandingAmount();
                                    Log.log(Log.INFO, "GMAction", 
                                            "saveOutstandingDetails", 
                                            "Inside 3 For loop Temp TC PR Amount from tempOutAmt OBJ : " + 
                                            tempTcPrAmt);

                                    tempTcDate = 
                                            tempOutAmt.getTcOutstandingAsOnDate();
                                    Log.log(Log.INFO, "GMAction", 
                                            "saveOutstandingDetails", 
                                            "Inside 3 For loop Temp TC Date from tempOutAmt OBJ: " + 
                                            tempTcDate);

                                    if (tempTcId != null && 
                                        tempTcId.equals(tcIdKey)) {
                                        Log.log(Log.INFO, "GMAction", 
                                                "saveOutstandingDetails", 
                                                "TempOutId == Out Id ");
                                        if ((tempTcPrAmt != tcPrAmt) || 
                                            (tempTcDate.compareTo(tcDate) != 
                                             0)) {
                                            modifiedTcOutstandingAmount = 
                                                    new OutstandingAmount();
                                            modifiedTcOutstandingAmount.setTcoId(tcIdKey);
                                            Log.log(Log.INFO, "GMAction", 
                                                    "saveOutstandingDetails", 
                                                    "modified id =" + 
                                                    modifiedTcOutstandingAmount.getTcoId());

                                            modifiedTcOutstandingAmount.setCgpan(cgpan);
                                            Log.log(Log.INFO, "GMAction", 
                                                    "saveOutstandingDetails", 
                                                    "modified cgpan " + 
                                                    modifiedTcOutstandingAmount.getCgpan());

                                            modifiedTcOutstandingAmount.setTcPrincipalOutstandingAmount(tcPrAmt);
                                            Log.log(Log.INFO, "GMAction", 
                                                    "saveOutstandingDetails", 
                                                    "modified TC OutstandingAmount = " + 
                                                    modifiedTcOutstandingAmount.getTcPrincipalOutstandingAmount());

                                            modifiedTcOutstandingAmount.setTcOutstandingAsOnDate(tcDate);
                                            Log.log(Log.INFO, "GMAction", 
                                                    "saveOutstandingDetails", 
                                                    "modifiedOutstandingAmount Date =" + 
                                                    modifiedTcOutstandingAmount.getTcOutstandingAsOnDate());

                                            modifiedTcOutstandingAmounts.add(modifiedTcOutstandingAmount);
                                        }
                                        break breakLabel;
                                    }
                                }
                            }
                        }
                    } else {
                        Log.log(Log.INFO, "GMAction", "saveOutstandingDetails", 
                                "Inside Else loop seting new OutAmt");
                        newTcOutstandingAmount = new OutstandingAmount();

                        newTcOutstandingAmount.setCgpan(cgpan);
                        Log.log(Log.INFO, "GMAction", "saveOutstandingDetails", 
                                "NewOutAmount CGpan=" + 
                                newTcOutstandingAmount.getCgpan());

                        newTcOutstandingAmount.setTcPrincipalOutstandingAmount(tcPrAmt);
                        Log.log(Log.INFO, "GMAction", "saveOutstandingDetails", 
                                "NewOutAmount Date=" + 
                                newTcOutstandingAmount.getTcPrincipalOutstandingAmount());

                        newTcOutstandingAmount.setTcOutstandingAsOnDate(tcDate);
                        Log.log(Log.INFO, "GMAction", "saveOutstandingDetails", 
                                "NewOutAmount Date=" + 
                                newTcOutstandingAmount.getTcOutstandingAsOnDate());

                        newTcOutstandingAmounts.add(newTcOutstandingAmount);
                    }
                }
                ++count;
            }

        }

        //System.out.println("List of Modified WC OutstandingAmount , size = "+modifiedWcOutstandingAmounts.size());
        //System.out.println("List of New WC OutstandingAmount , size = "+newWcOutstandingAmounts.size());

        //System.out.println("List of Modified TC OutstandingAmount , size = "+modifiedTcOutstandingAmounts.size());
        //System.out.println("List of New TC OutstandingAmount , size = "+newTcOutstandingAmounts.size());

        if (modifiedWcOutstandingAmounts.size() > 0) {
            Log.log(Log.INFO, "GMAction", "saveOutstandingDetails", 
                    "modifiedWcOutstandingAmounts Size =" + 
                    modifiedWcOutstandingAmounts.size());
            gmProcessor.updateWcOutstanding(modifiedWcOutstandingAmounts);
        }

        if (newWcOutstandingAmounts.size() > 0) {
            Log.log(Log.INFO, "GMAction", "saveOutstandingDetails", 
                    "newWcOutstandingAmounts Size =" + 
                    newWcOutstandingAmounts.size());
            gmProcessor.insertWcOutstanding(newWcOutstandingAmounts);
        }

        if (modifiedTcOutstandingAmounts.size() > 0) {
            Log.log(Log.INFO, "GMAction", "saveOutstandingDetails", 
                    "modifiedTcOutstandingAmounts Size =" + 
                    modifiedTcOutstandingAmounts.size());
            gmProcessor.updateTcOutstanding(modifiedTcOutstandingAmounts);
        }

        if (newTcOutstandingAmounts.size() > 0) {
            Log.log(Log.INFO, "GMAction", "saveOutstandingDetails", 
                    "newTcOutstandingAmounts Size =" + 
                    newTcOutstandingAmounts.size());
            gmProcessor.insertTcOutstanding(newTcOutstandingAmounts);
        }
        request.setAttribute("message", message + errorMessage);

        return mapping.findForward(Constants.SUCCESS);
    }


    public ActionForward showDisbursementDetails(ActionMapping mapping, 
                                                 ActionForm form, 
                                                 HttpServletRequest request, 
                                                 HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "showDisbursementDetails", "Entered");

        ArrayList periodicInfoDetails = new ArrayList();
        GMActionForm gmActionForm = (GMActionForm)form;

        HttpSession session = request.getSession(false);
        session.setAttribute("displayFlag", "1");

        GMProcessor gmProcessor = new GMProcessor();

        String memberId = gmActionForm.getMemberId();
        String cgpan = gmActionForm.getCgpan().toUpperCase();

        ApplicationProcessor appProcessor = new ApplicationProcessor();
        Application partApp;
        if ((cgpan != null) && (!cgpan.equals(""))) {
            partApp = appProcessor.getPartApplication(memberId, cgpan, "");
        }

        String borrowerId = gmActionForm.getBorrowerId().toUpperCase();
        String borrowerName = gmActionForm.getBorrowerName();

        gmActionForm.getDisbursementAmount().clear();
        gmActionForm.getDisbursementDate().clear();
        gmActionForm.getFinalDisbursement().clear();
        int type = 0;

        String forward = "";

        ClaimsProcessor processor = new ClaimsProcessor();
        Vector memberids = processor.getAllMemberIds();
        if (!memberids.contains(memberId)) {
            throw new NoMemberFoundException("Member Id :" + memberId + 
                                             " does not exist in the database.");
        }

        ArrayList borrowerIds = new ArrayList();
        borrowerIds = gmProcessor.getBorrowerIds(memberId);
        GMDAO dao = new GMDAO();
        Vector cgpans = null;
        if ((!borrowerId.equals("")) && (cgpan.equals("")) && 
            (borrowerName.equals(""))) {
            type = 0;
            if (!borrowerIds.contains(borrowerId)) {
                gmActionForm.setBorrowerId("");
                throw new NoDataException(borrowerId + 
                                          " is not among the borrower" + 
                                          " Ids for the Member Id :" + 
                                          memberId + ". Please enter correct" + 
                                          " Member Id and Borrower Id.");
            }

            int claimCount = appProcessor.getClaimCount(borrowerId);
            if (claimCount > 0) {
                throw new MessageException("Disbursement Details cannot be modified for this borrower since Claim Application has been submitted");
            }
            //added by upchar@path       
            cgpans = dao.getCGPANDetailsPeriodicInfo(borrowerId, memberId);
            if(cgpans == null || cgpans.size() == 0){
                throw new NoDataException("There are no Live Loan Account(s) for this borrower or Guarantee has not been started.");
            }

            periodicInfoDetails = 
                    gmProcessor.viewDisbursementDetails(borrowerId, type);

            if ((periodicInfoDetails.isEmpty()) || 
                (periodicInfoDetails == null) || 
                (periodicInfoDetails.size() == 0)) {
                Log.log(5, "GMAction", "showDisbursementDetails", "No Data");
                throw new NoDataException("There are no Disbursement Details for the combination of inputs");
            }

            gmActionForm.setDisbPeriodicInfoDetails(periodicInfoDetails);

            forward = "success";
        } else if ((!cgpan.equals("")) && (borrowerId.equals("")) && 
                   (borrowerName.equals(""))) {
            type = 0;

            String bIdForThisCgpan = processor.getBorowwerForCGPAN(cgpan);
            Log.log(5, "GMAction", "showDisbursementDetails", 
                    " Bid For Pan - " + bIdForThisCgpan);
            if (!borrowerIds.contains(bIdForThisCgpan)) {
                throw new NoDataException(cgpan + "is not a valid Cgpan for " + 
                                          "the Member Id :" + memberId + 
                                          ". Please enter correct Cgpan");
            }

            int claimCount = appProcessor.getClaimCount(bIdForThisCgpan);
            if (claimCount > 0) {
                throw new MessageException("Disbursement Details cannot be modified for this borrower since Claim Application has been submitted");
            }

            gmActionForm.setBorrowerId(bIdForThisCgpan);
            //added by upchar@path
            cgpans = dao.getCGPANDetailsPeriodicInfo(bIdForThisCgpan, memberId);
            if(cgpans == null || cgpans.size() == 0){
              throw new NoDataException("There are no Live Loan Account(s) for this borrower or Guarantee has not been started.");
            }
            periodicInfoDetails = 
                    gmProcessor.viewDisbursementDetails(bIdForThisCgpan, type);

            if ((periodicInfoDetails.isEmpty()) || 
                (periodicInfoDetails == null) || 
                (periodicInfoDetails.size() == 0)) {
                Log.log(5, "GMAction", "showDisbursementDetails", "No Data");
                throw new NoDataException("There are no Disbursement Details for the combination of inputs");
            }

            gmActionForm.setDisbPeriodicInfoDetails(periodicInfoDetails);

            forward = "success";
        } else if ((!borrowerName.equals("")) && (borrowerId.equals("")) && 
                   (cgpan.equals(""))) {
            type = 2;
            ArrayList bIdForBorrowerName = 
                gmProcessor.getBorrowerIdForBorrowerName(borrowerName, 
                                                         memberId);

            if ((bIdForBorrowerName == null) || 
                (bIdForBorrowerName.size() == 0)) {
                throw new NoDataException("There are no Borrower Ids for this member");
            }

            gmActionForm.setBorrowerIds(bIdForBorrowerName);
            forward = "bidList";
        }

        Log.log(5, "GMAction", "showDisbursementDetails", 
                "Data " + periodicInfoDetails.size());

        Log.log(4, "GMAction", "showDisbursementDetails", "Exited");

        memberids = null;
        borrowerIds = null;

        return mapping.findForward(forward);
    }

    public ActionForward saveDisbursementDetails(ActionMapping mapping, 
                                                 ActionForm form, 
                                                 HttpServletRequest request, 
                                                 HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "saveDisbursementDetails", "Entered");

        GMActionForm gmActionForm = (GMActionForm)form;
        GMProcessor gmProcessor = new GMProcessor();

        String borrowerId = gmActionForm.getBorrowerId();
        Log.log(5, "GMAction", "saveDisbursementDetails", 
                "borrowerId " + borrowerId);
        String message = "Disbursement Details Saved Successfully";
        ArrayList tempPeriodicDtls = 
            gmProcessor.viewDisbursementDetails(borrowerId, 0);

        ArrayList tempDisbAmts = null;

        Map cgpanMap = gmActionForm.getCgpans();
        Set cgpanSet = cgpanMap.keySet();
        Iterator cgpanIterator = cgpanSet.iterator();
        Log.log(5, "GMAction", "saveDisbursementDetails ", 
                "cgpan Map size = " + cgpanMap.size());

        String cgpan = null;
        java.util.Date disbursementDate = null;
        double disbAmount = 0.0D;
        String finalDisbursement = null;
        int count = 0;
        String key = null;
        String disbId = null;

        String tempCgpan = null;
        String tempDisbId = null;
        double tempDisbAmount = 0.0D;
        java.util.Date tempDisbDate = null;
        String tempFinalDisb = null;

        DisbursementAmount disbursementAmount = null;
        DisbursementAmount newDisbursementAmount = null;
        DisbursementAmount modifiedDisbursementAmount = null;

        ArrayList disbursements = null;
        ArrayList newDisbursementAmounts = null;
        ArrayList modifiedDisbursementAmounts = null;

        modifiedDisbursementAmounts = new ArrayList();
        newDisbursementAmounts = new ArrayList();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        Map disbAmtMap = gmActionForm.getDisbursementAmount();
        Set disbAmtSet = disbAmtMap.keySet();
        Iterator disbAmtIterator = disbAmtSet.iterator();

        Log.log(5, "GMAction", "saveDisbursementDetails", 
                "Amount map size = " + disbAmtMap.size());

        Map disbDateMap = gmActionForm.getDisbursementDate();
        Log.log(5, "GMAction", "saveDisbursementDetails", 
                "Date map size = " + disbDateMap.size());

        Map finalDisbursementMap = gmActionForm.getFinalDisbursement();
        Log.log(5, "GMAction", "saveDisbursementDetails", 
                "final Disb map size = " + finalDisbursementMap.size());

        Map disbIdMap = gmActionForm.getDisbursementId();
        Set disbIdSet = disbIdMap.keySet();
        Iterator disbIdIterator = disbIdSet.iterator();
        Log.log(5, "GMAction", "saveDisbursementDetails", 
                "disbursement Id map size = " + disbIdMap.size());

        while (cgpanIterator.hasNext()) {
            cgpan = (String)cgpanMap.get(cgpanIterator.next());

            Log.log(5, "GMAction", "saveDisbursementDetails", 
                    "cgpanIterator cgpan: " + cgpan);

            disbIdIterator = disbIdSet.iterator();
            disbAmtIterator = disbAmtSet.iterator();

            count = 0;

            while (disbAmtIterator.hasNext()) {
                disbAmtIterator.next();

                key = cgpan + "-" + count;
                Log.log(5, "GM Action", "saveDisbursementDetails", 
                        "Amount Iterator key : " + key);

                if ((key != null) && (!key.equals("")) && 
                    (disbAmtMap.containsKey(key))) {
                    String disbValue = (String)disbAmtMap.get(key);
                    if (!disbValue.equals("")) {
                        disbAmount = Double.parseDouble(disbValue);

                        Log.log(5, "GM Action", "saveDisbursementDetails", 
                                "Key is matched Amount :" + disbAmount);
                    } else {
                        break;
                    }
                    String dateValue = (String)disbDateMap.get(key);
                    if (!dateValue.equals("")) {
                        disbursementDate = 
                                simpleDateFormat.parse(dateValue, new ParsePosition(0));

                        Log.log(5, "GM Action", "saveDisbursementDetails", 
                                "Key is matched Date :" + disbursementDate);
                    } else {
                        break;
                    }

                    finalDisbursement = (String)finalDisbursementMap.get(key);
                    if (request.getParameter("finalDisbursement(" + key + 
                                             ")") == null) {
                        Log.log(5, "GM Action", "saveDisbursementDetails", 
                                "*****finalDisbursement = N******");
                        finalDisbursement = "N";
                    }

                    String idKey = null;

                    while (disbIdIterator.hasNext()) {
                        idKey = (String)disbIdIterator.next();
                        Log.log(5, "GMAction", "saveDisbursementDetails", 
                                "idKey " + idKey);

                        if ((idKey != null) && (!idKey.equals("")) && 
                            (idKey.startsWith(key))) {
                            idKey = 
                                    idKey.substring(idKey.lastIndexOf("-") + 1, idKey.length());

                            Log.log(5, "GMAction", "saveDisbursementDetails", 
                                    "idKey obtained is " + idKey);

                            break;
                        }

                        idKey = null;
                    }

                    if (idKey != null) {
                        Log.log(5, "GMAction", "saveDisbursementDetails", 
                                "idkey is not null");

                        for (int i = 0; i < tempPeriodicDtls.size(); i++) {
                            PeriodicInfo pr = 
                                (PeriodicInfo)tempPeriodicDtls.get(i);
                            disbursements = pr.getDisbursementDetails();
                            for (int j = 0; j < disbursements.size(); j++) {
                                Disbursement disbursement = 
                                    (Disbursement)disbursements.get(j);
                                tempDisbAmts = 
                                        disbursement.getDisbursementAmounts();
                                Log.log(5, "GMAction", 
                                        "saveDisbursementDetails", 
                                        "2-for,Temp Disbursementlist size :" + 
                                        tempDisbAmts.size());

                                for (int k = 0; k < tempDisbAmts.size(); k++) {
                                    DisbursementAmount tempDisbAmt = 
                                        (DisbursementAmount)tempDisbAmts.get(k);
                                    tempDisbId = 
                                            tempDisbAmt.getDisbursementId();
                                    Log.log(5, "GMAction", 
                                            "saveDisbursementDetails", 
                                            "Inside 3 For loop tempDisbId:" + 
                                            tempDisbId);

                                    tempCgpan = tempDisbAmt.getCgpan();
                                    Log.log(5, "GMAction", 
                                            "saveDisbursementDetails", 
                                            "Inside 3 For loop TempCgpan from tempDisbAmt OBJ  : " + 
                                            tempCgpan);

                                    tempDisbAmount = 
                                            tempDisbAmt.getDisbursementAmount();
                                    Log.log(5, "GMAction", 
                                            "saveDisbursementDetails", 
                                            "Inside 3 For loop Temp Amount from tempDisbAmt OBJ : " + 
                                            tempDisbAmount);

                                    tempDisbDate = 
                                            tempDisbAmt.getDisbursementDate();
                                    Log.log(5, "GMAction", 
                                            "saveDisbursementDetails", 
                                            "Inside 3 For loop Temp Date from tempDisbAmt OBJ: " + 
                                            tempDisbDate);

                                    tempFinalDisb = 
                                            tempDisbAmt.getFinalDisbursement();
                                    Log.log(5, "GMAction", 
                                            "saveDisbursementDetails", 
                                            "Inside 3 For loop Temp Final Disbursement from tempDisbAmt OBJ: " + 
                                            tempFinalDisb);

                                    if ((tempDisbId != null) && 
                                        (tempDisbId.equals(idKey))) {
                                        Log.log(5, "GMAction", 
                                                "saveDisbursementDetails", 
                                                "TempDisbursementId == Disb Id ");
                                        if ((tempDisbAmount != disbAmount) || 
                                            (tempDisbDate.compareTo(disbursementDate) != 
                                             0)) {
                                            modifiedDisbursementAmount = 
                                                    new DisbursementAmount();
                                            modifiedDisbursementAmount.setDisbursementId(idKey);
                                            Log.log(5, "GMAction", 
                                                    "saveDisbursementDetails", 
                                                    "modified id =" + 
                                                    modifiedDisbursementAmount.getDisbursementId());

                                            modifiedDisbursementAmount.setCgpan(cgpan);
                                            Log.log(5, "GMAction", 
                                                    "saveDisbursementDetails", 
                                                    "modified cgpan " + 
                                                    modifiedDisbursementAmount.getCgpan());

                                            modifiedDisbursementAmount.setDisbursementAmount(disbAmount);
                                            Log.log(5, "GMAction", 
                                                    "saveDisbursementDetails", 
                                                    "modified Disbursement Amount = " + 
                                                    modifiedDisbursementAmount.getDisbursementAmount());

                                            modifiedDisbursementAmount.setDisbursementDate(disbursementDate);
                                            Log.log(5, "GMAction", 
                                                    "saveDisbursementDetails", 
                                                    "modified Disbursement Date =" + 
                                                    modifiedDisbursementAmount.getDisbursementDate());

                                            modifiedDisbursementAmount.setFinalDisbursement(finalDisbursement);
                                            Log.log(5, "GMAction", 
                                                    "saveDisbursementDetails", 
                                                    "modified Disbursement Date =" + 
                                                    modifiedDisbursementAmount.getFinalDisbursement());

                                            modifiedDisbursementAmounts.add(modifiedDisbursementAmount);
                                        }
                                        Log.log(5, "GMAction", 
                                                "saveDisbursementDetails", 
                                                "####Break Label Called#####");
                                        break;
                                    }
                                }
                            }
                        }
                        Log.log(5, "GMAction", "saveDisbursementDetails", 
                                " End of if(idKey!=null) //func for setting updates");
                    } else {
                        Log.log(5, "GMAction", "saveDisbursementDetails", 
                                "Inside Else loop seting new Disbursement");
                        newDisbursementAmount = new DisbursementAmount();

                        newDisbursementAmount.setCgpan(cgpan);
                        Log.log(5, "GMAction", "saveDisbursementDetails", 
                                "NewDisbursementAmount Cgpan=" + 
                                newDisbursementAmount.getCgpan());

                        newDisbursementAmount.setDisbursementAmount(disbAmount);
                        Log.log(5, "GMAction", "saveDisbursementDetails", 
                                "NewDisbursementAmount Amount=" + 
                                newDisbursementAmount.getDisbursementAmount());

                        newDisbursementAmount.setDisbursementDate(disbursementDate);
                        Log.log(5, "GMAction", "saveDisbursementDetails", 
                                "NewDisbursementAmount Date=" + 
                                newDisbursementAmount.getDisbursementDate());

                        newDisbursementAmount.setFinalDisbursement(finalDisbursement);
                        Log.log(5, "GMAction", "saveDisbursementDetails", 
                                "NewDisbursementAmount Final Disb =" + 
                                newDisbursementAmount.getFinalDisbursement());

                        newDisbursementAmounts.add(newDisbursementAmount);
                    }
                    Log.log(4, "GMAction", "saveDisbursementDetails", 
                            "End of if(key !=null && !key.equals() && disbAmtMap.containsKey(key)");
                }
                count++;
                Log.log(4, "GMAction", "saveDisbursementDetails", 
                        "End of one While Disb Amt iterator");
            }
        }
        User user = getUserInformation(request);

        if (modifiedDisbursementAmounts.size() > 0) {
            gmProcessor.updateDisbursement(modifiedDisbursementAmounts, 
                                           user.getUserId());
        }

        if (newDisbursementAmounts.size() > 0) {
            gmProcessor.insertDisbursement(newDisbursementAmounts, 
                                           user.getUserId());
        }

        request.setAttribute("message", message);
        Log.log(4, "GMAction", "saveDisbursementDetails", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward addMoreDisbursement(ActionMapping mapping, 
                                             ActionForm form, 
                                             HttpServletRequest request, 
                                             HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "addMoreDisbursement", "Entered");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        GMActionForm gmActionForm = (GMActionForm)form;
        GMProcessor gmProcessor = new GMProcessor();

        Map cgpanMap = gmActionForm.getCgpans();
        Set cgpanSet = cgpanMap.keySet();
        Iterator cgpanIterator = cgpanSet.iterator();
        Log.log(5, "GMAction", "addMoreDisbursement", 
                "Cgpan Map size :" + cgpanMap.size());

        Map idMap = gmActionForm.getDisbursementId();
        Set idSet = idMap.keySet();
        Iterator idIterator = idSet.iterator();

        Log.log(5, "GMAction", "addMoreDisbursement", 
                "Id Map size :" + idMap.size());

        java.util.Date disbursementDate = null;
        double disburseAmount = 0.0D;
        String key = null;
        int count = 0;
        String cgpan = null;

        DisbursementAmount disbursementAmount = null;
        ArrayList disbursementAmounts = null;
        disbursementAmounts = new ArrayList();
        HashMap disbursementDtls = new HashMap();

        while (cgpanIterator.hasNext()) {
            idIterator = idSet.iterator();

            cgpan = (String)cgpanMap.get(cgpanIterator.next());
            Log.log(5, "GMAction", "addMoreDisbursement", 
                    "Inside cgpan Iterator Cgpan : " + cgpan);

            Map disbAmountMap = gmActionForm.getDisbursementAmount();
            Set disbAmountSet = disbAmountMap.keySet();
            Iterator disbAmountIterator = disbAmountSet.iterator();

            Log.log(5, "GMAction", "addMoreDisbursement", 
                    "Inside cgpan Iterator disbAmountMap size: " + 
                    disbAmountMap.size());

            Map disbDateMap = gmActionForm.getDisbursementDate();
            Log.log(5, "GMAction", "addMoreDisbursement", 
                    "Inside cgpan Iterator disbDateMap: " + 
                    disbDateMap.size());

            Map finalDisbursementMap = gmActionForm.getFinalDisbursement();
            Set finalDisbursementSet = finalDisbursementMap.keySet();
            Iterator finalDisbursementIterator = 
                finalDisbursementSet.iterator();
            Log.log(5, "GMAction", "addMoreDisbursement", 
                    "Inside cgpan Iterator finalDisbursementMap : " + 
                    finalDisbursementMap.size());

            count = 0;

            if (disbAmountMap.size() == 1) {
                count = 1;
            }

            count = 0;

            ArrayList temp = new ArrayList();
            String amount = null;
            String date = null;
            String finalDisb = null;

            while (disbAmountIterator.hasNext()) {
                key = 
(new StringBuilder()).append(cgpan).append("-").append(count).toString();
                disbAmountIterator.next();
                if (disbAmountMap.containsKey(key)) {
                    Log.log(5, "GMAction", "addMoreDisbursement", 
                            "Inside if loop Amount Map contains key");
                    amount = (String)disbAmountMap.get(key);
                    Log.log(5, "GMAction", "addMoreDisbursement", 
                            (new StringBuilder()).append("amount ").append(amount).append("and  Key ").append(key).toString());
                    if (amount == null || amount.equals(""))
                        continue;
                    disburseAmount = Double.parseDouble(amount);
                    date = (String)disbDateMap.get(key);
                    Log.log(5, "GMAction", "addMoreDisbursement", 
                            (new StringBuilder()).append("date ").append(date).append("and  Key ").append(key).toString());
                    if (date == null || date.equals(""))
                        continue;
                    disbursementDate = 
                            simpleDateFormat.parse(date, new ParsePosition(0));
                    for (String idKey = null; idIterator.hasNext(); 
                         idKey = null) {
                        idKey = (String)idIterator.next();
                        Log.log(5, "GMAction", "addMoreDisbursement", 
                                (new StringBuilder()).append("DbId Key :").append(idKey).toString());
                        if (idKey == null || idKey.equals("") || 
                            !idKey.startsWith(key))
                            continue;
                        idKey = 
                                idKey.substring(idKey.lastIndexOf("-") + 1, idKey.length());
                        Log.log(5, "GMAction", "addMoreRepayment", 
                                (new StringBuilder()).append("DbId Key obtained is :").append(idKey).toString());
                        break;
                    }

                    disbursementAmount = new DisbursementAmount();
                    disbursementAmount.setCgpan(cgpan);
                    disbursementAmount.setDisbursementAmount(disburseAmount);
                    disbursementAmount.setDisbursementDate(disbursementDate);
                    disbursementAmounts.add(disbursementAmount);
                    temp.add(disbursementAmount);
                    Log.log(5, "GMAction", "addMoreDisbursement", 
                            "END of IF loop -Amount Map contains key");
                }
                count++;
            }

            disbursementDtls.put(cgpan, temp);
        }
        ArrayList periodicInfoDetails = null;

        periodicInfoDetails = gmActionForm.getDisbPeriodicInfoDetails();

        for (int i = 0; i < periodicInfoDetails.size(); i++) {
            PeriodicInfo pr = (PeriodicInfo)periodicInfoDetails.get(i);
            ArrayList disbursements = pr.getDisbursementDetails();
            for (int j = 0; j < disbursements.size(); j++) {
                Disbursement disbursement = (Disbursement)disbursements.get(j);
                String cgpan1 = disbursement.getCgpan();
                ArrayList temp = (ArrayList)disbursementDtls.get(cgpan1);
                disbursement.setDisbursementAmounts(temp);
            }
        }

        Log.log(4, "GMAction", "addMoreDisbursement", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward GetBidCgpanRepayment(ActionMapping mapping, 
                                              ActionForm form, 
                                              HttpServletRequest request, 
                                              HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "GetBidCgpanRepayment", "Entered");
        GMProcessor gmProcessor = new GMProcessor();

        GMActionForm gmActionForm = (GMActionForm)form;
        gmActionForm.setBorrowerId("");
        gmActionForm.setCgpan("");
        gmActionForm.setBorrowerName("");

        User user = getUserInformation(request);
        String bankId = user.getBankId();
        String zoneId = user.getZoneId();
        String branchId = user.getBranchId();
        String memberId = bankId.concat(zoneId).concat(branchId);

        gmActionForm.setPeriodicBankId(bankId);
        if (bankId.equals("0000")) {
            memberId = "";
        }
        gmActionForm.setMemberId(memberId);

        Log.log(4, "GMAction", "GetBidCgpanRepayment", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward showRepaymentDetails(ActionMapping mapping, 
                                              ActionForm form, 
                                              HttpServletRequest request, 
                                              HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "showRepaymentDetails", "Entered");
        ArrayList periodicInfoDetails = new ArrayList();
        GMActionForm gmActionForm = (GMActionForm)form;

        HttpSession session = request.getSession(false);

        String memberId = gmActionForm.getMemberId();
        String cgpan = gmActionForm.getCgpan().toUpperCase();
        String borrowerId = gmActionForm.getBorrowerId().toUpperCase();
        String borrowerName = gmActionForm.getBorrowerName();
        gmActionForm.getRepaymentAmount().clear();
        gmActionForm.getRepaymentDate().clear();

        GMProcessor gmProcessor = new GMProcessor();
        ApplicationProcessor appProcessor = new ApplicationProcessor();

        int type = 0;

        String forward = "";

        ClaimsProcessor processor = new ClaimsProcessor();
        Vector memberids = processor.getAllMemberIds();
        if (!memberids.contains(memberId)) {
            throw new NoMemberFoundException("Member Id :" + memberId + 
                                             " does not exist in the database.");
        }

        ArrayList borrowerIds = new ArrayList();
        borrowerIds = gmProcessor.getBorrowerIds(memberId);
        GMDAO dao = new GMDAO();
        Vector cgpans = null;
        if ((!borrowerId.equals("")) && (cgpan.equals("")) && 
            (borrowerName.equals(""))) {
            type = 0;
            if (!borrowerIds.contains(borrowerId)) {
                gmActionForm.setBorrowerId("");
                throw new NoDataException(borrowerId + 
                                          " is not among the borrower" + 
                                          " Ids for the Member Id :" + 
                                          memberId + ". Please enter correct" + 
                                          " Member Id and Borrower Id.");
            }

            int claimCount = appProcessor.getClaimCount(borrowerId);
            if (claimCount > 0) {
                throw new MessageException("Repayment Details cannot be modified for this borrower since Claim Application has been submitted");
            }
            cgpans = dao.getCGPANDetailsPeriodicInfo(borrowerId, memberId);
            if(cgpans == null || cgpans.size() == 0){
                throw new NoDataException("There are no Live Loan Account(s) for this borrower or Guarantee has not been started.");
            }
            periodicInfoDetails = 
                    gmProcessor.viewRepaymentDetails(borrowerId, type);

            if (periodicInfoDetails == null) {
                throw new NoDataException("There are no Repayment Details for this " + 
                                          borrowerName);
            }

            gmActionForm.setRepayPeriodicInfoDetails(periodicInfoDetails);
            gmActionForm.setRepayPeriodicInfoDetailsTemp(periodicInfoDetails);

            forward = "success";
        } else if ((!cgpan.equals("")) && (borrowerId.equals("")) && 
                   (borrowerName.equals(""))) {
            type = 0;
            String bIdForThisCgpan = processor.getBorowwerForCGPAN(cgpan);
            Log.log(5, "GMAction", "showRepaymentDetails", 
                    " Bid For Pan - " + bIdForThisCgpan);
            if (!borrowerIds.contains(bIdForThisCgpan)) {
                throw new NoDataException(cgpan + "is not a valid Cgpan for " + 
                                          "the Member Id :" + memberId + 
                                          ". Please enter correct Cgpan");
            }

            int claimCount = appProcessor.getClaimCount(bIdForThisCgpan);
            if (claimCount > 0) {
                throw new MessageException("Repayment Details cannot be modified for this borrower since Claim Application has been submitted");
            }

            gmActionForm.setBorrowerId(bIdForThisCgpan);
            cgpans = dao.getCGPANDetailsPeriodicInfo(bIdForThisCgpan, memberId);
            if(cgpans == null || cgpans.size() == 0){
                throw new NoDataException("There are no Live Loan Account(s) for this borrower or Guarantee has not been started.");
            }
            periodicInfoDetails = 
                    gmProcessor.viewRepaymentDetails(bIdForThisCgpan, type);
            if (periodicInfoDetails == null) {
                throw new NoDataException("There are no Repayment Details for this " + 
                                          borrowerName);
            }

            gmActionForm.setRepayPeriodicInfoDetails(periodicInfoDetails);
            gmActionForm.setRepayPeriodicInfoDetailsTemp(periodicInfoDetails);

            forward = "success";
        } else if ((!borrowerName.equals("")) && (borrowerId.equals("")) && 
                   (cgpan.equals(""))) {
            type = 2;
            ArrayList bIdForBorrowerName = 
                gmProcessor.getBorrowerIdForBorrowerName(borrowerName, 
                                                         memberId);

            if ((bIdForBorrowerName == null) || 
                (bIdForBorrowerName.size() == 0)) {
                throw new NoDataException("There are no Borrower Ids for this member");
            }

            session.setAttribute("displayFlag", "3");
            gmActionForm.setBorrowerIds(bIdForBorrowerName);
            forward = "bidList";
        }

        for (int i = 0; i < periodicInfoDetails.size(); i++) {
            PeriodicInfo pr = (PeriodicInfo)periodicInfoDetails.get(i);
            ArrayList repayments = pr.getRepaymentDetails();

            Log.log(5, "GMAction", "showRepaymentDetails", 
                    " size of repaymentz" + repayments.size());

            for (int j = 0; j < repayments.size(); j++) {
                Repayment repayment = (Repayment)repayments.get(j);
                ArrayList repaymentAmts = repayment.getRepaymentAmounts();
                Log.log(5, "GMAction", "showRepaymentDetails", 
                        "cgpan, size of amts" + repayment.getCgpan() + "," + 
                        repaymentAmts.size());
                RepaymentAmount rpamt;
                for (int k = 0; k < repaymentAmts.size(); k++) {
                    rpamt = (RepaymentAmount)repaymentAmts.get(k);
                }

            }

        }

        Log.log(4, "GMAction", "showRepaymentDetails", "Exited");

        return mapping.findForward(forward);
    }

    public ActionForward saveRepaymentDetails(ActionMapping mapping, 
                                              ActionForm form, 
                                              HttpServletRequest request, 
                                              HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "saveRepaymentDetails", "Entered");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        GMActionForm gmActionForm = (GMActionForm)form;
        GMProcessor gmProcessor = new GMProcessor();

        String borrowerId = gmActionForm.getBorrowerId();
        String message = "Repayment Details Saved Successfully";
        Log.log(5, "GMAction", "saveRepaymentDetails", 
                "borrowerId " + borrowerId);

        ArrayList tempPeriodicDtls = 
            gmProcessor.viewRepaymentDetails(borrowerId, 0);

        ArrayList tempRepAmts = null;

        Map cgpanMap = gmActionForm.getCgpans();
        Set cgpanSet = cgpanMap.keySet();
        Iterator cgpanIterator = cgpanSet.iterator();
        Log.log(5, "GM Action", " SaveRepaymentDetail ", 
                "cgpan Map size = " + cgpanMap.size());

        java.util.Date repaymentDate = null;
        double repayAmount = 0.0D;
        String key = null;
        int count = 0;
        String cgpan = null;
        String tempCgpan = null;

        String repayId = null;
        String tempRepayId = null;
        String repayAmtKey = null;

        double tempAmount = 0.0D;
        java.util.Date tempDate = null;

        int flag = 0;

        RepaymentAmount repaymentAmount = null;
        RepaymentAmount modifiedRepaymentAmount = null;
        RepaymentAmount newRepaymentAmount = null;

        ArrayList newRepaymentAmounts = null;
        ArrayList repayments = null;
        ArrayList repaymentAmounts = null;
        ArrayList modifiedRepaymentAmounts = null;

        repaymentAmounts = new ArrayList();
        modifiedRepaymentAmounts = new ArrayList();
        newRepaymentAmounts = new ArrayList();

        Map repayAmountMap = gmActionForm.getRepaymentAmount();
        Set repayAmountSet = repayAmountMap.keySet();
        Iterator repayAmountIterator = repayAmountSet.iterator();

        Log.log(5, "GMAction", "saveRepaymentDetails", 
                "Amount map size = " + repayAmountMap.size());

        Map repayDateMap = gmActionForm.getRepaymentDate();
        Log.log(5, "GMAction", "saveRepaymentDetails", 
                "Date map size = " + repayDateMap.size());

        Map repayIdMap = gmActionForm.getRepaymentId();
        Set repayIdSet = repayIdMap.keySet();
        Iterator repayIdIterator = repayIdSet.iterator();
        Log.log(5, "GMAction", "saveRepaymentDetails", 
                "repay ID map size = " + repayIdMap.size());

        while (cgpanIterator.hasNext()) {
            cgpan = (String)cgpanMap.get(cgpanIterator.next());
            Log.log(5, "GMAction", "Inside CgpanIterator", "cgpan  :" + cgpan);

            repayIdIterator = repayIdSet.iterator();
            repayAmountIterator = repayAmountSet.iterator();

            count = 0;

            while (repayAmountIterator.hasNext()) {
                repayAmtKey = (String)repayAmountIterator.next();

                key = cgpan + "-" + count;
                Log.log(5, "GMAction", "saveRepaymentDetails", "key ->" + key);

                if ((key != null) && (!key.equals("")) && 
                    (repayAmountMap.containsKey(key))) {
                    String repamt = (String)repayAmountMap.get(key);
                    if (!repamt.equals("")) {
                        repayAmount = Double.parseDouble(repamt);
                        Log.log(5, "GMAction", "saveRepaymentDetails", 
                                "Key is matched Amount :" + repayAmount);
                    } else {
                        break;
                    }
                    String repdate = (String)repayDateMap.get(key);

                    if (!repdate.equals("")) {
                        repaymentDate = 
                                simpleDateFormat.parse(repdate, new ParsePosition(0));
                        Log.log(5, "GMAction", "saveRepaymentDetails", 
                                "Key is matched  Date :" + repaymentDate);
                    } else {
                        break;
                    }

                    String idKey = null;

                    while (repayIdIterator.hasNext()) {
                        idKey = (String)repayIdIterator.next();
                        Log.log(5, "GMAction", "saveRepaymentDetails ", 
                                "idKey " + idKey);

                        if ((idKey != null) && (!idKey.equals("")) && 
                            (idKey.startsWith(key))) {
                            idKey = 
                                    idKey.substring(idKey.lastIndexOf("-") + 1, idKey.length());

                            Log.log(5, "GMAction", "saveRepaymentDetails ", 
                                    "idKey obtained is " + idKey);

                            break;
                        }

                        idKey = null;
                    }

                    if (idKey != null) {
                        Log.log(5, "GMAction", "saveRepaymentDetails ", 
                                "is not null");

                        for (int i = 0; i < tempPeriodicDtls.size(); i++) {
                            PeriodicInfo pr = 
                                (PeriodicInfo)tempPeriodicDtls.get(i);
                            repayments = pr.getRepaymentDetails();
                            for (int j = 0; j < repayments.size(); j++) {
                                Repayment repayment = 
                                    (Repayment)repayments.get(j);
                                tempRepAmts = repayment.getRepaymentAmounts();
                                Log.log(5, "GMAction", "saveRepaymentDetails", 
                                        "2-for,Temp Repaymentlist size :" + 
                                        tempRepAmts.size());

                                for (int k = 0; k < tempRepAmts.size(); k++) {
                                    RepaymentAmount tempRpAmt = 
                                        (RepaymentAmount)tempRepAmts.get(k);
                                    tempRepayId = tempRpAmt.getRepayId();
                                    Log.log(5, "GMAction", 
                                            "saveRepaymentDetails", 
                                            "Inside 3 For loop tempRepayId:" + 
                                            tempRepayId);
                                    Log.log(5, "GMAction", 
                                            "saveRepaymentDetails", 
                                            "Inside 3 For loop repayId:" + 
                                            repayId);

                                    tempCgpan = tempRpAmt.getCgpan();
                                    Log.log(5, "GMAction", 
                                            "saveRepaymentDetails", 
                                            "Inside 3 For loop TempCgpan from tempRepayAmt OBJ  : " + 
                                            tempCgpan);

                                    tempAmount = 
                                            tempRpAmt.getRepaymentAmount();
                                    Log.log(5, "GMAction", 
                                            "saveRepaymentDetails", 
                                            "Inside 3 For loop Temp Amount from tempRepayAmt OBJ : " + 
                                            tempAmount);

                                    tempDate = tempRpAmt.getRepaymentDate();
                                    Log.log(5, "GMAction", 
                                            "saveRepaymentDetails", 
                                            "Inside 3 For loop Temp Date from tempRepayAmt OBJ: " + 
                                            tempDate);

                                    if ((tempRepayId != null) && 
                                        (tempRepayId.equals(idKey))) {
                                        Log.log(5, "GMAction", 
                                                "saveRepaymentDetails", 
                                                "TempRepaymentId == Repay Id ");
                                        if ((tempAmount != repayAmount) || 
                                            (tempDate.compareTo(repaymentDate) != 
                                             0)) {
                                            modifiedRepaymentAmount = 
                                                    new RepaymentAmount();
                                            modifiedRepaymentAmount.setRepayId(idKey);
                                            Log.log(5, "GMAction", 
                                                    "saveRepaymentDetails", 
                                                    "modified id =" + 
                                                    modifiedRepaymentAmount.getRepayId());

                                            modifiedRepaymentAmount.setCgpan(cgpan);
                                            Log.log(5, "GMAction", 
                                                    "saveRepaymentDetails", 
                                                    "modified cgpan " + 
                                                    modifiedRepaymentAmount.getCgpan());

                                            modifiedRepaymentAmount.setRepaymentAmount(repayAmount);
                                            Log.log(5, "GMAction", 
                                                    "saveRepaymentDetails", 
                                                    "modified RepaymentAmount = " + 
                                                    modifiedRepaymentAmount.getRepaymentAmount());

                                            modifiedRepaymentAmount.setRepaymentDate(repaymentDate);
                                            Log.log(5, "GMAction", 
                                                    "saveRepaymentDetails", 
                                                    "modified RepaymentDate =" + 
                                                    modifiedRepaymentAmount.getRepaymentDate());

                                            modifiedRepaymentAmounts.add(modifiedRepaymentAmount);
                                        }
                                        break;
                                    }
                                }
                            }
                        }
                    } else {
                        Log.log(5, "GMAction", "SaveRepaymentDetails", 
                                "Inside Else loop seting new Repayment");
                        newRepaymentAmount = new RepaymentAmount();

                        newRepaymentAmount.setCgpan(cgpan);
                        Log.log(5, "GMAction", "saveRepaymentDetails", 
                                "NewRepayAmount CGpan=" + 
                                newRepaymentAmount.getCgpan());

                        newRepaymentAmount.setRepaymentAmount(repayAmount);
                        Log.log(5, "GMAction", "saveRepaymentDetails", 
                                "NewRepayAmount Amount=" + 
                                newRepaymentAmount.getRepaymentAmount());

                        newRepaymentAmount.setRepaymentDate(repaymentDate);
                        Log.log(5, "GMAction", "saveRepaymentDetails", 
                                "NewRepayAmount Date=" + 
                                newRepaymentAmount.getRepaymentDate());

                        newRepaymentAmounts.add(newRepaymentAmount);
                    }
                }
                count++;
            }
        }
        User user = getUserInformation(request);

        Log.log(5, "GMAction", "saveRepaymentDetails", 
                "Printing modified repayment amounts ");

        for (int i = 0; i < modifiedRepaymentAmounts.size(); i++) {
            RepaymentAmount tempAmt = 
                (RepaymentAmount)modifiedRepaymentAmounts.get(i);

            Log.log(5, "GMAction", "saveRepaymentDetails", 
                    "id " + tempAmt.getRepayId());
            Log.log(5, "GMAction", "saveRepaymentDetails", 
                    "amount " + tempAmt.getRepaymentAmount());
            Log.log(5, "GMAction", "saveRepaymentDetails", 
                    "date  " + tempAmt.getRepaymentDate());
        }

        Log.log(5, "GMAction", "saveRepaymentDetails", 
                "Printing new  repayment amounts ");

        for (int i = 0; i < newRepaymentAmounts.size(); i++) {
            RepaymentAmount tempAmt = 
                (RepaymentAmount)newRepaymentAmounts.get(i);

            Log.log(5, "GMAction", "saveRepaymentDetails", 
                    "id " + tempAmt.getRepayId());
            Log.log(5, "GMAction", "saveRepaymentDetails", 
                    "amount " + tempAmt.getRepaymentAmount());
            Log.log(5, "GMAction", "saveRepaymentDetails", 
                    "date  " + tempAmt.getRepaymentDate());
        }

        if (modifiedRepaymentAmounts.size() != 0) {
            gmProcessor.updateRepaymentDetails(modifiedRepaymentAmounts, 
                                               user.getUserId());
        }
        if (newRepaymentAmounts.size() != 0) {
            gmProcessor.insertRepaymentDetails(newRepaymentAmounts, 
                                               user.getUserId());
        }

        request.setAttribute("message", message);
        Log.log(4, "GMAction", "saveRepaymentDetails", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward addMoreRepayment(ActionMapping mapping, 
                                          ActionForm form, 
                                          HttpServletRequest request, 
                                          HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "addMoreRepayment", "Entered");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        GMActionForm gmActionForm = (GMActionForm)form;
        GMProcessor gmProcessor = new GMProcessor();

        Map cgpanMap = gmActionForm.getCgpans();
        Set cgpanSet = cgpanMap.keySet();
        Iterator cgpanIterator = cgpanSet.iterator();

        Log.log(5, "GMAction", "addMoreRepayment", 
                "Cgpan Map size :" + cgpanMap.size());

        java.util.Date repaymentDate = null;
        double repayAmount = 0.0D;
        String key = null;
        int count = 0;
        String cgpan = null;

        RepaymentAmount repaymentAmount = null;
        ArrayList repaymentAmounts = null;
        repaymentAmounts = new ArrayList();
        HashMap repaymentDtls = new HashMap();

        Map idMap = gmActionForm.getRepaymentId();
        Set idSet = idMap.keySet();
        Iterator idIterator = idSet.iterator();

        Log.log(5, "GMAction", "addMoreRepayment", 
                "Id Map size :" + idMap.size());

        while (idIterator.hasNext()) {
            Log.log(5, "GMAction", "addMoreRepayment", 
                    "Element :" + idIterator.next());
        }

        while (cgpanIterator.hasNext()) {
            idIterator = idSet.iterator();

            cgpan = (String)cgpanMap.get(cgpanIterator.next());
            Log.log(5, "GMAction", "addMoreRepayment", 
                    "Inside cgpan Iterator -Cgpan : " + cgpan);

            Map repayAmountMap = gmActionForm.getRepaymentAmount();
            Set repayAmountSet = repayAmountMap.keySet();
            Iterator repayAmountIterator = repayAmountSet.iterator();

            Log.log(5, "GMAction", "addMoreRepayment", 
                    "Inside cgpan Iterator Amount Map size : " + 
                    repayAmountMap.size());

            Map repayDateMap = gmActionForm.getRepaymentDate();

            count = 0;
            ArrayList temp = new ArrayList();
            String amount = null;
            String date = null;

            while (repayAmountIterator.hasNext()) {
                key = 
(new StringBuilder()).append(cgpan).append("-").append(count).toString();
                Log.log(5, "GMAction", "addMoreRepayment", 
                        (new StringBuilder()).append("Inside Amount Iterator, key : ").append(key).toString());
                repayAmountIterator.next();
                if (repayAmountMap.containsKey(key)) {
                    Log.log(5, "GMAction", "addMoreRepayment", 
                            "Inside if loop Amount Map contains key");
                    amount = (String)repayAmountMap.get(key);
                    Log.log(5, "GMAction", "addMoreRepayment", 
                            (new StringBuilder()).append("Amount ").append(amount).append("and  Key ").append(key).toString());
                    if (amount == null || amount.equals(""))
                        continue;
                    repayAmount = Double.parseDouble(amount);
                    date = (String)repayDateMap.get(key);
                    Log.log(5, "GMAction", "addMoreRepayment", 
                            (new StringBuilder()).append("Date ").append(date).append("and  Key ").append(key).toString());
                    if (date == null || date.equals(""))
                        continue;
                    repaymentDate = 
                            simpleDateFormat.parse(date, new ParsePosition(0));
                    Log.log(5, "GMAction", "addMoreRepayment", 
                            (new StringBuilder()).append("Date  for the key :").append(repaymentDate).toString());
                    String idKey;
                    for (idKey = null; idIterator.hasNext(); idKey = null) {
                        idKey = (String)idIterator.next();
                        Log.log(5, "GMAction", "addMoreRepayment", 
                                (new StringBuilder()).append("RpId Key :").append(idKey).toString());
                        if (idKey == null || idKey.equals("") || 
                            !idKey.startsWith(key))
                            continue;
                        idKey = 
                                idKey.substring(idKey.lastIndexOf("-") + 1, idKey.length());
                        Log.log(5, "GMAction", "addMoreRepayment", 
                                (new StringBuilder()).append("RpId Key obtained is :").append(idKey).toString());
                        break;
                    }

                    repaymentAmount = new RepaymentAmount();
                    repaymentAmount.setRepayId(idKey);
                    repaymentAmount.setCgpan(cgpan);
                    repaymentAmount.setRepaymentAmount(repayAmount);
                    repaymentAmount.setRepaymentDate(repaymentDate);
                    repaymentAmounts.add(repaymentAmount);
                    temp.add(repaymentAmount);
                    Log.log(5, "GMAction", "addMoreRepayment", 
                            "END of IF loop -Amount Map contains key");
                }
                count++;
            }

            repaymentDtls.put(cgpan, temp);
        }
        ArrayList periodicInfoDetails = null;

        periodicInfoDetails = gmActionForm.getRepayPeriodicInfoDetails();

        for (int i = 0; i < periodicInfoDetails.size(); i++) {
            PeriodicInfo pr = (PeriodicInfo)periodicInfoDetails.get(i);
            ArrayList repayments = pr.getRepaymentDetails();
            for (int j = 0; j < repayments.size(); j++) {
                Repayment repayment = (Repayment)repayments.get(j);
                String cgpan1 = repayment.getCgpan();
                ArrayList temp = (ArrayList)repaymentDtls.get(cgpan1);
                repayment.setRepaymentAmounts(temp);
            }
        }

        Log.log(5, "GMAction", "addMoreRepayment", 
                " printing the temp periodic info details after add more...");

        Log.log(4, "GMAction", "addMoreRepayment", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward GetBidOrCgpanForSchedule(ActionMapping mapping, 
                                                  ActionForm form, 
                                                  HttpServletRequest request, 
                                                  HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "GetBidOrCgpanForSchedule", "Entered");

        GMActionForm gmActionForm = (GMActionForm)form;

        gmActionForm.setBorrowerIdForSchedule("");
        gmActionForm.setCgpanForSchedule("");
        gmActionForm.setBorrowerNameForSchedule("");

        User user = getUserInformation(request);

        String bankId = user.getBankId();
        String zoneId = user.getZoneId();
        String branchId = user.getBranchId();

        String memberId = bankId.concat(zoneId).concat(branchId);

        gmActionForm.setBankIdForSchedule(bankId);
        if (bankId.equals("0000")) {
            memberId = "";
        }

        gmActionForm.setMemberIdForSchedule(memberId);

        Log.log(4, "GMAction", "GetBidOrCgpanForSchedule", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward showRepaymentSchedule(ActionMapping mapping, 
                                               ActionForm form, 
                                               HttpServletRequest request, 
                                               HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "showRepaymentSchedule", "Entered");
        GMActionForm gmActionForm = (GMActionForm)form;
        HttpSession session = request.getSession(false);

        String memberId = gmActionForm.getMemberIdForSchedule();
        session.setAttribute("scheduleMemberId", memberId);
        Log.log(5, "GMAction", "showRepaymentSchedule", 
                "mem id -->" + memberId);

        String cgpan = gmActionForm.getCgpanForSchedule().toUpperCase();
        String borrowerId = 
            gmActionForm.getBorrowerIdForSchedule().toUpperCase();
        String borrowerName = gmActionForm.getBorrowerNameForSchedule();

        Log.log(5, "GMAction", "", "bid " + borrowerId);
        Log.log(5, "GMAction", "", "bid " + cgpan);
        ArrayList repaymentSchedules = new ArrayList();
        GMProcessor gmProcessor = new GMProcessor();
        RepaymentSchedule repaymentSchedule = new RepaymentSchedule();

        ApplicationProcessor appProcessor = new ApplicationProcessor();

        ClaimsProcessor processor = new ClaimsProcessor();
        Vector memberids = processor.getAllMemberIds();
        if (!memberids.contains(memberId)) {
            throw new NoMemberFoundException("Member Id :" + memberId + 
                                             " does not" + 
                                             " exist in the database.");
        }

        ArrayList borrowerIds = new ArrayList();
        borrowerIds = gmProcessor.getBorrowerIds(memberId);
        int type = 0;

        String forward = "";

        if ((!borrowerId.equals("")) && (cgpan.equals(""))) {
            type = 0;
            if (!borrowerIds.contains(borrowerId)) {
                gmActionForm.setBorrowerId("");
                throw new NoDataException(borrowerId + 
                                          " is not among the borrower" + 
                                          " Ids for the Member Id :" + 
                                          memberId + ". Please enter correct" + 
                                          " Member Id and Borrower Id.");
            }

            int claimCount = appProcessor.getClaimCount(borrowerId);
            if (claimCount > 0) {
                throw new MessageException("Repayment Schedule Details cannot be modified for this borrower since Claim Application has been submitted");
            }

            repaymentSchedules = 
                    gmProcessor.viewRepaymentSchedule(borrowerId, type);

            gmActionForm.setRepaymentSchedules(repaymentSchedules);

            Log.log(4, "GMAction", "showRepaymentSchedule", 
                    "repqyment schedule size :" + repaymentSchedules.size());
            if ((repaymentSchedules == null) || 
                (repaymentSchedules.size() == 0)) {
                request.setAttribute("message", 
                                     "No Repayment Schedule Details Available");
                forward = "success";
            } else {
                forward = "forwardPage";
            }

        } else if ((cgpan != null) && (!cgpan.equals("")) && 
                   ((borrowerId == null) || (borrowerId.equals("")))) {
            type = 1;

            Log.log(5, "GMAction", "showRepaymentSchedule", 
                    " cgpan - " + cgpan);

            String bIdForThisCgpan = processor.getBorowwerForCGPAN(cgpan);
            Log.log(5, "GMAction", "showRepaymentSchedule", 
                    " Bid For Pan - " + bIdForThisCgpan);
            if (!borrowerIds.contains(bIdForThisCgpan)) {
                throw new NoDataException(cgpan + "is not a valid Cgpan for " + 
                                          "the Member Id :" + memberId + 
                                          ". Please enter correct Cgpan");
            }

            gmActionForm.setBorrowerIdForSchedule(bIdForThisCgpan);

            int claimCount = appProcessor.getClaimCount(bIdForThisCgpan);
            if (claimCount > 0) {
                throw new MessageException("Repayment Schedule Details cannot be modified for this borrower since Claim Application has been submitted");
            }

            repaymentSchedules = 
                    gmProcessor.viewRepaymentSchedule(cgpan, type);

            gmActionForm.setRepaymentSchedules(repaymentSchedules);

            Log.log(4, "GMAction", "showRepaymentSchedule", 
                    "repqyment schedule size :" + repaymentSchedules.size());
            if ((repaymentSchedules == null) || 
                (repaymentSchedules.size() == 0)) {
                request.setAttribute("message", 
                                     "No Repayment Schedule Details Available");
                forward = "success";
            } else {
                forward = "forwardPage";
            }

        } else if ((!borrowerName.equals("")) && (borrowerId.equals("")) && 
                   (cgpan.equals(""))) {
            type = 2;
            ArrayList bIdForBorrowerName = 
                gmProcessor.getBorrowerIdForBorrowerName(borrowerName, 
                                                         memberId);

            if ((bIdForBorrowerName == null) || 
                (bIdForBorrowerName.size() == 0)) {
                throw new NoDataException("There are no Borrower Ids for this member");
            }

            Log.log(4, "GMAction", "showRepaymentSchedule", 
                    "bIdForBorrowerName" + bIdForBorrowerName.size());
            gmActionForm.setBorrowerIds(bIdForBorrowerName);

            Log.log(4, "GMAction", "showRepaymentSchedule", 
                    "gmActionForm" + gmActionForm.getBorrowerIds());

            forward = "bidList";
        }

        memberids = null;
        borrowerIds = null;

        Log.log(4, "GMAction", "showRepaymentSchedule", "Exited");

        return mapping.findForward(forward);
    }

    public ActionForward saveRepaymentSchedule(ActionMapping mapping, 
                                               ActionForm form, 
                                               HttpServletRequest request, 
                                               HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "repaymentScheduleSaved", "Entered");

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        GMActionForm gmActionForm = (GMActionForm)form;

        ArrayList repaymentSchedules = new ArrayList();
        GMProcessor gmProcessor = new GMProcessor();

        String message = "Repayment Schedule Saved Successfully";
        Map cgpanMap = gmActionForm.getCgpans();
        Set cgpanSet = cgpanMap.keySet();
        Iterator cgpanIterator = cgpanSet.iterator();

        Map moratoriumMap = gmActionForm.getMoratorium();
        Set moratoriumSet = moratoriumMap.keySet();
        Iterator moratoriumIterator = moratoriumSet.iterator();

        Map dueDateMap = gmActionForm.getFirstInstallmentDueDate();
        Log.log(5, "GMAction", "saveRepaymentSchedule", 
                "first installment due date " + dueDateMap.toString());
        Set dueDateSet = dueDateMap.keySet();
        Iterator dueDateIterator = dueDateSet.iterator();

        Map periodicityMap = gmActionForm.getPeriodicity();
        Log.log(5, "GMAction", "saveRepaymentSchedule", 
                "periodicity map " + periodicityMap.toString());
        Set periodicitySet = periodicityMap.keySet();
        Iterator periodicityIterator = periodicitySet.iterator();

        Map noOfInstallmentMap = gmActionForm.getNoOfInstallment();
        Set noOfInstallmentSet = noOfInstallmentMap.keySet();
        Iterator noOfInstallmentIterator = noOfInstallmentSet.iterator();

        String cgpan = null;
        java.util.Date dueDate = null;
        int moratorium = 0;
        int noOfInstallment = 0;
        String periodicity = null;
        String key = null;
        String mor = null;
        String noOfInstall = null;
        String per = null;
        String dtStr = null;

        while (cgpanIterator.hasNext()) {
            key = (String)cgpanIterator.next();
            RepaymentSchedule repaymentSchedule = new RepaymentSchedule();

            cgpan = (String)cgpanMap.get(key);
            Log.log(5, "GMAction", "saveRepaymentSchedule", "cgpan" + cgpan);
            repaymentSchedule.setCgpan(cgpan);

            mor = (String)moratoriumMap.get(key);
            if (!mor.equals("")) {
                moratorium = Integer.parseInt(mor);
                repaymentSchedule.setMoratorium(moratorium);
            }
            Log.log(5, "GMAction", "saveRepaymentSchedule", 
                    "moratorium" + moratorium);

            noOfInstall = (String)noOfInstallmentMap.get(key);
            if (!noOfInstall.equals("")) {
                noOfInstallment = Integer.parseInt(noOfInstall);
                repaymentSchedule.setNoOfInstallment(noOfInstallment);
            }
            Log.log(5, "GMAction", "saveRepaymentSchedule", 
                    "no of installment " + noOfInstallment);

            periodicity = (String)periodicityMap.get(key);
            Log.log(5, "GMAction", "saveRepaymentSchedule", 
                    "periodicity " + periodicity);
            repaymentSchedule.setPeriodicity(periodicity);

            dtStr = (String)dueDateMap.get(key);
            dueDate = simpleDateFormat.parse(dtStr, new ParsePosition(0));
            Log.log(5, "GMAction", "saveRepaymentSchedule", 
                    "due date" + dueDate);
            repaymentSchedule.setFirstInstallmentDueDate(dueDate);

            repaymentSchedules.add(repaymentSchedule);
        }
        User user = getUserInformation(request);

        gmProcessor.updateRepaymentSchedule(repaymentSchedules, 
                                            user.getUserId());

        request.setAttribute("message", message);
        Log.log(4, "GMAction", "repaymentScheduleSaved", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward showOutstandingDetailsLink(ActionMapping mapping, 
                                                    ActionForm form, 
                                                    HttpServletRequest request, 
                                                    HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "showOutstandingDetailsLink", "Entered");

        GMActionForm gmActionForm = (GMActionForm)form;

        String memberId = gmActionForm.getMemberId();
        String cgpan = gmActionForm.getCgpan().toUpperCase();
        String borrowerId = gmActionForm.getBorrowerId().toUpperCase();
        String borrowerName = gmActionForm.getBorrowerName();

        if (cgpan == null)
            gmActionForm.setCgpan("");
        else if (!cgpan.equals("")) {
            gmActionForm.setBorrowerId("");
        }

        if (borrowerName == null)
            gmActionForm.setBorrowerName("");
        else if (!borrowerName.equals("")) {
            gmActionForm.setBorrowerId("");
        }

        Log.log(4, "GMAction", "showOutstandingDetailsLink", "Exited");

        return showOutstandingDetails(mapping, form, request, response);
    }

    public ActionForward showDisbursementDetailsLink(ActionMapping mapping, 
                                                     ActionForm form, 
                                                     HttpServletRequest request, 
                                                     HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "showDisbursementDetailsLink", "Entered");

        GMActionForm gmActionForm = (GMActionForm)form;

        String memberId = gmActionForm.getMemberId();
        String cgpan = gmActionForm.getCgpan().toUpperCase();
        String borrowerId = gmActionForm.getBorrowerId().toUpperCase();

        String borrowerName = gmActionForm.getBorrowerName();

        if (cgpan == null)
            gmActionForm.setCgpan("");
        else if (!cgpan.equals("")) {
            gmActionForm.setBorrowerId("");
        }

        if (borrowerName == null)
            gmActionForm.setBorrowerName("");
        else if (!borrowerName.equals("")) {
            gmActionForm.setBorrowerId("");
        }

        Log.log(4, "GMAction", "showDisbursementDetailsLink", "Exited");

        return showDisbursementDetails(mapping, form, request, response);
    }

    public ActionForward showRepaymentDetailsLink(ActionMapping mapping, 
                                                  ActionForm form, 
                                                  HttpServletRequest request, 
                                                  HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "showRepaymentDetailsLink", "Entered");
        GMActionForm gmActionForm = (GMActionForm)form;

        String memberId = gmActionForm.getMemberId();
        String cgpan = gmActionForm.getCgpan();
        String borrowerId = gmActionForm.getBorrowerId().toUpperCase();
        String borrowerName = gmActionForm.getBorrowerName();

        if (cgpan == null)
            gmActionForm.setCgpan("");
        else if (!cgpan.equals("")) {
            gmActionForm.setBorrowerId("");
        }

        if (borrowerName == null)
            gmActionForm.setBorrowerName("");
        else if (!borrowerName.equals("")) {
            gmActionForm.setBorrowerId("");
        }

        Log.log(4, "GMAction", "showRepaymentDetailsLink", "Exited");

        return showRepaymentDetails(mapping, form, request, response);
    }

    public ActionForward showNPADetailsLink(ActionMapping mapping, 
                                            ActionForm form, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response) throws Exception {

        Log.log(Log.INFO, "GMAction", "showNPADetailsLink", "Entered");
        GMActionForm gmActionForm = (GMActionForm)form;

        String memberId = gmActionForm.getMemberId();
        String borrowerId = gmActionForm.getBorrowerId().toUpperCase();
        String cgpan = gmActionForm.getCgpan().toUpperCase();
        Log.log(Log.INFO, "GMAction", "showNPADetailsLink", "cgpan " + cgpan);
        String borrowerName = gmActionForm.getBorrowerName();
        Log.log(Log.INFO, "GMAction", "showNPADetailsLink", 
                "B name" + borrowerName);

        if (cgpan == null) {
            gmActionForm.setCgpan("");
        }
        if (borrowerName == null) {
            gmActionForm.setBorrowerName("");
        }

        Log.log(Log.INFO, "GMAction", "showNPADetailsLink", "Exited");
        return showNPADetails(mapping, form, request, response);

    }

    public ActionForward showRecoveryDetailsLink(ActionMapping mapping, 
                                                 ActionForm form, 
                                                 HttpServletRequest request, 
                                                 HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "showRecoveryDetailsLink", "Entered");

        GMActionForm gmActionForm = (GMActionForm)form;
        String memberId = gmActionForm.getMemberId();
        String borrowerId = gmActionForm.getBorrowerId().toUpperCase();
        String cgpan = gmActionForm.getCgpan();
        String borrowerName = gmActionForm.getBorrowerName();

        if (cgpan == null) {
            gmActionForm.setCgpan("");
        }
        if (borrowerName == null) {
            gmActionForm.setBorrowerName("");
        }

        Log.log(4, "GMAction", "showRecoveryDetailsLink", "Exited");

        return showRecoveryDetails(mapping, form, request, response);
    }

    public ActionForward getIdForClosure(ActionMapping mapping, 
                                         ActionForm form, 
                                         HttpServletRequest request, 
                                         HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "getIdForClosure", "Entered");

        GMProcessor gmProcessor = new GMProcessor();

        GMActionForm gmActionForm = (GMActionForm)form;

        User user = getUserInformation(request);

        String bankId = user.getBankId();
        String zoneId = user.getZoneId();
        String branchId = user.getBranchId();
        String memberId = bankId.concat(zoneId).concat(branchId);

        gmActionForm.setBankIdForClosure(bankId);
        if (bankId.equals("0000")) {
            memberId = "";
        }
        gmActionForm.setMemberIdForClosure(memberId);
        gmActionForm.setBorrowerIdForClosure("");
        gmActionForm.setBorrowerNameForClosure("");
        gmActionForm.setCgpanForClosure("");

        Log.log(4, "GMAction", "getIdForClosure", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward getIdForClosureRequest(ActionMapping mapping, 
                                                ActionForm form, 
                                                HttpServletRequest request, 
                                                HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "getIdForClosureRequest", "Entered");

        GMProcessor gmProcessor = new GMProcessor();
        GMActionForm gmActionForm = (GMActionForm)form;
        ApplicationReport appReport = new ApplicationReport();
        ReportManager reportManager = new ReportManager();

        String memberId = gmActionForm.getMemberIdForClosure();
        String cgpan = gmActionForm.getCgpanForClosure().toUpperCase();

        gmActionForm.setMemberIdForClosure(memberId);
        gmActionForm.setCgpanForClosure(cgpan);
        appReport = reportManager.getApplicationReportForCgpan(cgpan);
        gmActionForm.setClosureRemarks("");
        gmActionForm.setApplicationReport(appReport);

        Log.log(4, "GMAction", "getIdForClosureRequest", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward getIdForClosureRequestInput(ActionMapping mapping, 
                                                     ActionForm form, 
                                                     HttpServletRequest request, 
                                                     HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "getIdForClosureRequestInput", "Entered");

        GMProcessor gmProcessor = new GMProcessor();

        GMActionForm gmActionForm = (GMActionForm)form;

        User user = getUserInformation(request);

        String bankId = user.getBankId();
        String zoneId = user.getZoneId();
        String branchId = user.getBranchId();
        String memberId = bankId.concat(zoneId).concat(branchId);

        gmActionForm.setBankIdForClosure(bankId);

        if (bankId.equals("0000")) {
            memberId = "";
        }
        gmActionForm.setMemberIdForClosure(memberId);
        gmActionForm.setClosureRemarks("");
        gmActionForm.setCgpanForClosure("");

        Log.log(4, "GMAction", "getIdForClosureRequestInput", "Exited");

        return mapping.findForward("success");
    }

    //rajuk
    
    //rajuk
 /*public ActionForward showMemberForViewGSTIN(ActionMapping mapping, 
            ActionForm form, 
            HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
GMProcessor gmProcessor = new GMProcessor();

DynaActionForm dynaForm = (DynaActionForm)form;

User user = getUserInformation(request);
String bankId = user.getBankId();
String zoneId = user.getZoneId();
String brnchId = user.getBranchId();
String forwardPage = "";
if (bankId.equals("0000")) {
dynaForm.initialize(mapping);
forwardPage = "showMember";
} else {
ArrayList cgpanMapping = 
gmProcessor.getCgpanMapping(bankId + zoneId + brnchId);
dynaForm.set("cgpanMapping", cgpanMapping);
forwardPage = "showCgpan";
}

return mapping.findForward(forwardPage);
}
 */
 //rajuk
public ActionForward getIdForUpdateGSTIN(ActionMapping mapping, 
            ActionForm form, 
            HttpServletRequest request, 
			HttpServletResponse response) throws Exception {

		RPActionForm rpForm = (RPActionForm) form;
		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = "";
		String cgpanCount="";

		if (rpForm.getDanSummaries() != null) {
			rpForm.getDanSummaries().clear();
		}
		if (rpForm.getDanPanDetails() != null) {
			rpForm.getDanPanDetails().clear();
		}
		if (rpForm.getCgpans() != null) {
			rpForm.getCgpans().clear();
		}
		if (rpForm.getAllocatedFlags() != null) {
			rpForm.getAllocatedFlags().clear();
		}
		if (rpForm.getFirstDisbursementDates() != null) {
			rpForm.getFirstDisbursementDates().clear();
		}
		if (rpForm.getNotAllocatedReasons() != null) {
			rpForm.getNotAllocatedReasons().clear();
		}
		if (rpForm.getAllocatedFlags() != null) {
			rpForm.getAllocatedFlags().clear();
		}
		if (rpForm.getAppropriatedFlags() != null) {
			rpForm.getAppropriatedFlags().clear();
		}
		if (rpForm.getAmountsRaised() != null) {
			rpForm.getAmountsRaised().clear();
		}
		if (rpForm.getAmountBeingPaid() != null) {
			rpForm.getAmountBeingPaid().clear();
		}
		/*if (rpForm.getStatenames() != null) {
			rpForm.getStatenames().clear();
		}*/
		if (rpForm.getSelectedDANs() != null) {
			rpForm.getSelectedDANs().clear();
		}
		if (bankId.equals("0000")) {
			memberId = rpForm.getSelectMember();

			
			Log.log(5, "RPAction", "getNEFTPendingGFDANs", "mliId = "
					+ memberId);

			if ((memberId == null) || (memberId.equals(""))) {
				
				return mapping.findForward("liveafmember");
			}
			bankId = memberId.substring(0, 4);
			zoneId = memberId.substring(4, 8);
			branchId = memberId.substring(8, 12);

			ClaimsProcessor cpProcessor = new ClaimsProcessor();
			Vector memberIds = cpProcessor.getAllMemberIds();
			if (!memberIds.contains(memberId)) {
				throw new NoMemberFoundException("The Member ID does not exist");
			}
		}
		RpProcessor rpProcessor = new RpProcessor();

		ArrayList danSummaries = rpProcessor.displayLiveAfdansforGSTIN(bankId,
				zoneId, branchId);
		//System.out.println("danSummaries===" + danSummaries);
		cgpanCount=rpProcessor.getCgpanCount(bankId,zoneId, branchId);
		//System.out.println("cgpanCount" + cgpanCount);
		 request.setAttribute("cgpanCount", cgpanCount);
		 List allstatelist = rpProcessor.displaystatelistGSTIN(bankId, zoneId, branchId);
		//System.out.println("allstatelist" + allstatelist);
		 request.setAttribute("allstatelist", allstatelist);
	
		if (danSummaries.size() == 0) {
			rpForm.setSelectMember(null);
			throw new MissingDANDetailsException(
					"No CGPANS available for GSTIN Updation");
		}

		
		
		rpForm.setDanSummaries(danSummaries);
		
		rpForm.setBankId(bankId);
		rpForm.setZoneId(zoneId);
		rpForm.setBranchId(branchId);

		Log.log(4, "RPAction", "getNEFTPendingGFDANs", "Exited");
		if (rpForm.getSelectMember() != null) {
			rpForm.setMemberId(rpForm.getSelectMember());
		} else {
			rpForm.setMemberId(bankId + zoneId + branchId);
		}
		rpForm.setSelectMember(null);
		return mapping.findForward("liveafdansummaryGSTIN");
}

//rajuk 

public ActionForward getGSTNO(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
throws Exception
{
		
	    try{ 
	    PrintWriter out = response.getWriter();	
	    User user = getUserInformation(request);
	   	String bankId = user.getBankId();
		
		HttpSession session1 = request.getSession(false);
	    String statename=request.getParameter("value");
	 
	    String gstNo="";
	    String statecode="";
	    Connection connection = DBConnection.getConnection();

	    PreparedStatement claimsubmitduStmt = null;
	    ResultSet claimlodgedResult = null;
	    ResultSet rs = null;
	    PreparedStatement pst = null;
	    ArrayList claimArray = new ArrayList();
	   
	    ArrayList statelist = null;
	    statelist = new ArrayList();
	    ResultSet rsDanDetails = null;
		RpProcessor rpProcessor = new RpProcessor();
		//bankId="0008";
	    gstNo=rpProcessor.getGstNo(statename,bankId);
	   
	    request.setAttribute("gstinNo", gstNo);
	    request.setAttribute("statecode", statecode);
	  
	 
	    
	    out.print(gstNo);	
	    
	    }
	    catch(Exception e)
		{
			System.err.println("Exception in ApplicationProcessingAction..."+e);
			
		}        
		
      return null;
}

/*public Date formatDateCell(String actionDates )
{
	Date date=null;
	String dateSapmple=actionDates.toString();
	Map months= new HashMap();
	months.put( "Jan","1");						
	months.put("Feb","2");						
	months.put("Mar","3");						
	months.put("Apr","4");						
	months.put("May","5");						
	months.put("Jun","6");						
	months.put("Jul","7");						
	months.put("Aug","8");						
	months.put("Sep","9");						
	months.put("Oct","10");						
	months.put("Nov","11");						
	months.put("Dec","12");
	try
	{
		
		if(dateSapmple!=null && dateSapmple.length() > 7)
		{
				if(dateSapmple.startsWith("\""))
				{
					dateSapmple=dateSapmple.replace("\"", "");
				}
				
			
				if(dateSapmple.contains("-") && dateSapmple.split("-").length ==3)
				{
					String splitedStr[] =dateSapmple.split("-");				
					if(months.containsKey(splitedStr[1]))
					{
						dateSapmple=dateSapmple.replaceAll(splitedStr[1], months.get(splitedStr[1]).toString());
						String[] dateStr=dateSapmple.split("-");
						dateSapmple=dateStr[1].concat("/").concat(dateStr[0].concat("/").concat(dateStr[2]));
					
					}
					
				}
			
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				try 
				{
					//sdf.setLenient(false);
					date = sdf.parse(dateSapmple);			
				} catch (ParseException e) 
				{
					date=null;
				}
						

		}
		else{
			
			System.err.println("Invalid date format...");
		}
	}
	catch(Exception e)
	{
		
	}
	return date;
}*/
public ActionForward getIdForUpdateGSTINDetails(ActionMapping mapping, 
        ActionForm form, 
        HttpServletRequest request, 
        HttpServletResponse response) throws Exception {
Log.log(4, "GMAction", "getIdForClosureRequestInput", "Entered");

HttpSession session = request.getSession();
RPActionForm actionFormobj = (RPActionForm) form;
String action = request.getParameter("action"); 

String cgpan="";
String statecode="";
String gstupdate="";
ArrayList cgpanArray = new ArrayList();
Connection connection = DBConnection.getConnection();
RpProcessor rpProcessor = new RpProcessor();
List danSumm = actionFormobj.getDanSummaries();
int len=danSumm.size();
Statement str1 = connection.createStatement();
try{
for(int i=0;i<len ; i++){

	String rqy = request.getParameter("qryRemarks"+i);

	if(rqy!=null && rqy.equalsIgnoreCase("on")){

		DANSummary danSummary = (DANSummary) danSumm.get(i);
		
		 cgpan=danSummary.getStateName();
		
		String gst = request.getParameter("gstNo"+i);
	
		String actionDates= request.getParameter("actionDate"+i);
		
		
		
 		
		 if((!danSummary.getStateName().equals("") ||(!actionDates.equals("") ||actionDates!=null)&& (!gst.equals("") || gst!=null)))
		 {
			
		 
			     statecode=rpProcessor.getStateCode(gst);
			 
			     int gstin = 0;
			     int gstin1 = 0;
			 
		     	
		     			     		    	
	    		//String query = "update application_detail  set APP_APPROVED_AMOUNT='"+gst+"' , APP_APPROVED_DATE_TIME='"+actionDates+"'  where cgpan='"+cgpan+"' ";
	    		
	    	//	System.out.println("query=="+query);
	    		//String query1 = "update CGTSIINTRANETUSER.application_detail@DB_INTRA  set STATE_CODE='"+statecode+"',GST_NO='"+gst+"' where cgpan='"+cgpan+"' ";
	    		   		    	

	    		//gstin = str1.executeUpdate(query);
	    		//gstin1 = str1.executeUpdate(query1);
	    		
	    		
	    		connection.commit();
		 
		
		 }
		
		 
		
		  cgpanArray.add(cgpan);
		 
		  request.setAttribute("cgpanArray", cgpanArray);
	}	
}
}
catch (Exception e) {
    e.printStackTrace();
}


finally{
    
   // System.out.println("raaaaaajjjjj");
	DBConnection.freeConnection(connection);

    Log.log(Log.INFO, "ClaimAction", "getIdForUpdateGSTINDetails", "Exited");
    }








return mapping.findForward("cgpanSummary");
}

 

    public ActionForward submitClosureDetails(ActionMapping mapping, 
                                              ActionForm form, 
                                              HttpServletRequest request, 
                                              HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "submitClosureDetails", "Entered");

        GMProcessor gmProcessor = new GMProcessor();
        GMDAO gmDAO = new GMDAO();
        ApplicationProcessor appProcessor = new ApplicationProcessor();
        ClosureDetail closureDtl = new ClosureDetail();
        HashMap closureDetails = null;

        GMActionForm gmActionForm = (GMActionForm)form;
        HttpSession session = request.getSession(false);

        User user = getUserInformation(request);

        String userId = user.getUserId();

        String memberId = gmActionForm.getMemberIdForClosure();

        String cgpan = gmActionForm.getCgpanForClosure().toUpperCase();

        String closureRemarks = gmActionForm.getclosureRemarks();

        java.sql.Date startDate = null;
        java.util.Date sDate = gmActionForm.getclosureDate();
        String dateString = "01/04/2011";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        try {
            java.util.Date d = dateFormat.parse(dateString);
            dateFormat.applyPattern("yyyy-MM-dd");
            dateString = dateFormat.format(d);
        } catch (Exception e) {
            e.printStackTrace();
        }
        java.sql.Date a3 = java.sql.Date.valueOf(dateString);
        String stDate = String.valueOf(sDate);
        if ((stDate == null) || (stDate.equals("")))
            startDate = null;
        else if (stDate != null) {
            startDate = new java.sql.Date(sDate.getTime());
        }

        if ((startDate != null) && (a3.compareTo(startDate) > 0)) {
            throw new DatabaseException(" Closure date can not be less than 01/04/2011 ");
        }

        java.util.Date toDate = new java.util.Date();
        java.sql.Date sysDate = new java.sql.Date(toDate.getTime());
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
        String endDate = dateFormat1.format(toDate);
        if (startDate.compareTo(sysDate) > 0) {
            throw new DatabaseException(" Closure date can not be greater than " + 
                                        endDate);
        }

        int type = 0;

        String forward = "";

        ClaimsProcessor processor = new ClaimsProcessor();
        Vector memberids = processor.getAllMemberIds();
        if (!memberids.contains(memberId)) {
            throw new NoMemberFoundException("Member Id :" + memberId + 
                                             " does not exist in the database.");
        }

        ArrayList borrowerIds = new ArrayList();
        borrowerIds = gmProcessor.getBorrowerIds(memberId);

        if (!cgpan.equals("")) {
            type = 1;
            String bIdForThisCgpan = processor.getBorowwerForCGPAN(cgpan);
            Log.log(5, "GMAction", "submitClosureDetails", 
                    " Bid For Pan - " + bIdForThisCgpan);
            if (!borrowerIds.contains(bIdForThisCgpan)) {
                throw new NoDataException(cgpan + "is not a valid Cgpan for " + 
                                          "the Member Id :" + memberId + 
                                          ". Please enter correct Cgpan");
            }

            int claimCount = appProcessor.getClaimCount(bIdForThisCgpan);
            if (claimCount > 0) {
                throw new MessageException("Application cannot be Closed for this borrower since Claim Application has been submitted");
            }

            forward = "success";
        }

        Log.log(4, "GMAction", "submitClosureDetails", "Exited");

        gmDAO.submitClosureDetails(memberId, cgpan, startDate, closureRemarks, 
                                   userId);

        request.setAttribute("message", 
                             "<b>Closure request for " + cgpan + " updated successfully.<b><br>");

        return mapping.findForward(forward);
    }

    public ActionForward showClosureApproval(ActionMapping mapping, 
                                             ActionForm form, 
                                             HttpServletRequest request, 
                                             HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "showClosureApproval", "Entered");
        User user = getUserInformation(request);
        String bankId = user.getBankId();
        String zoneId = user.getZoneId();
        String branchId = user.getBranchId();
        String memberId = "";
        GMActionForm gmActionForm = (GMActionForm)form;

        String forward = "";

        GMProcessor gmProcessor = new GMProcessor();
        ArrayList closureDetails = 
            gmProcessor.displayRequestedForClosureApproval();

        ArrayList closureDetailsMod1 = (ArrayList)closureDetails.get(0);
        ArrayList closureDetailsMod2 = (ArrayList)closureDetails.get(1);

        if ((closureDetails.size() == 0) && (closureDetailsMod1.size() == 0) && 
            (closureDetailsMod2.size() == 0)) {
            Log.log(4, "GMAction", "showClosureApproval", "emty closure list");
            request.setAttribute("message", 
                                 "No Closure Details available for Approval");
            forward = "success";
        } else {
            gmActionForm.setDanSummaries(closureDetailsMod1);
            gmActionForm.setClosureDetailsReq(closureDetailsMod2);
            forward = "closureList";
        }

        return mapping.findForward(forward);
    }

    public ActionForward afterClosureApproval(ActionMapping mapping, 
                                              ActionForm form, 
                                              HttpServletRequest request, 
                                              HttpServletResponse response) throws Exception {
        GMActionForm gmPeriodicInfoForm = (GMActionForm)form;
        GMDAO gmDAO = new GMDAO();
        RpDAO rpDAO = new RpDAO();
        Map clearCgpan = gmPeriodicInfoForm.getClearCgpan();
        Set clearCgpanSet = clearCgpan.keySet();
        Iterator clearCgpanIterator = clearCgpanSet.iterator();

        ApplicationDAO applicationDAO = new ApplicationDAO();
        Connection connection = DBConnection.getConnection(false);
        User user = getUserInformation(request);
        String userId = user.getUserId();

        while (clearCgpanIterator.hasNext()) {
            String key = (String)clearCgpanIterator.next();
            String decision = (String)clearCgpan.get(key);
            if (!decision.equals("")) {
                if (decision.equals("AP")) {
                    DANSummary danSummary = new DANSummary();
                    danSummary = gmDAO.getRequestedForClosureApplication(key);

                    if ((danSummary.getDanId() == null) || 
                        (danSummary.getDanId().equals(""))) {
                        gmDAO.updateApplicationStatusForClosedCases(key, 
                                                                    danSummary, 
                                                                    user);
                    } else {
                        gmDAO.insertDanDetailsForClosure(danSummary.getDanId(), 
                                                         key, danSummary, user, 
                                                         connection);
                    }

                    request.setAttribute("message", 
                                         "<b>The Request for Closure application approved successfully.<b><br>");
                }

            }

        }

        Map closureCgpan = gmPeriodicInfoForm.getClosureCgpan();
        Set closureCgpanSet = closureCgpan.keySet();
        Iterator closureCgpanIterator = closureCgpanSet.iterator();
        while (closureCgpanIterator.hasNext()) {
            String key = (String)closureCgpanIterator.next();
            String decision = (String)closureCgpan.get(key);
            if (!decision.equals("")) {
                if (decision.equals("AP")) {
                    DANSummary danSummaryNew = new DANSummary();
                    danSummaryNew = 
                            gmDAO.getRequestedForClosureApplication(key);
                    String bankId = 
                        danSummaryNew.getMemberId().substring(0, 4);
                    String danId = null;
                    double currentSFee = danSummaryNew.getAmountDue();
                    double estSFee = danSummaryNew.getAmountBeingPaid();
                    if (Math.round(estSFee) <= 0L) {
                        danId = null;

                        gmDAO.updateApplicationStatusForClosedCases(key, 
                                                                    danSummaryNew, 
                                                                    user);
                    } else {
                        danId = rpDAO.getDANId("SF", bankId, connection);

                        gmDAO.insertDanDetailsForClosure(danId, key, 
                                                         danSummaryNew, user, 
                                                         connection);
                    }
                    request.setAttribute("message", 
                                         "<b>The Request for Closure application approved successfully.<b><br>");
                }

            }

        }

        clearCgpan.clear();
        closureCgpan.clear();

        return mapping.findForward("success");
    }

    public ActionForward showClosureDetails(ActionMapping mapping, 
                                            ActionForm form, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "showClosureDetails", "Entered");

        GMProcessor gmProcessor = new GMProcessor();
        ApplicationProcessor appProcessor = new ApplicationProcessor();
        ClosureDetail closureDtl = new ClosureDetail();
        HashMap closureDetails = null;

        GMActionForm gmActionForm = (GMActionForm)form;
        HttpSession session = request.getSession(false);

        String cgpan = gmActionForm.getCgpanForClosure().toUpperCase();
        String borrowerId = 
            gmActionForm.getBorrowerIdForClosure().toUpperCase();
        String memberId = gmActionForm.getMemberIdForClosure();

        gmActionForm.setMemberIdForClosure(memberId);
        session.setAttribute("closureMemberId", memberId);
        String borrowerName = gmActionForm.getBorrowerNameForClosure();
        int type = 0;

        String forward = "";

        ClaimsProcessor processor = new ClaimsProcessor();
        Vector memberids = processor.getAllMemberIds();
        if (!memberids.contains(memberId)) {
            throw new NoMemberFoundException("Member Id :" + memberId + 
                                             " does not exist in the database.");
        }

        ArrayList borrowerIds = new ArrayList();
        borrowerIds = gmProcessor.getBorrowerIds(memberId);

        if ((!borrowerId.equals("")) && (cgpan.equals("")) && 
            (borrowerName.equals(""))) {
            type = 0;
            Log.log(5, "GMAction", "showClosureDetails", "borrowerId");
            if (!borrowerIds.contains(borrowerId)) {
                throw new NoDataException(borrowerId + 
                                          " is not among the borrower" + 
                                          " Ids for the Member Id :" + 
                                          memberId + ". Please enter correct" + 
                                          " Member Id and Borrower Id.");
            }

            int claimCount = appProcessor.getClaimCount(borrowerId);
            if (claimCount > 0) {
                throw new MessageException("Application cannot be closed for this borrower since Claim Application has been submitted");
            }

            closureDetails = 
                    gmProcessor.viewClosureDetails(borrowerId, type, memberId);

            if (closureDetails.isEmpty()) {
                throw new NoDataException("There are no Closure Details for the Entered ID");
            }

            ArrayList reasons = gmProcessor.getAllReasonsForClosure();
            gmActionForm.setClosureReasons(reasons);
            gmActionForm.setClosureDetails(closureDetails);

            forward = "success";
        } else if ((!cgpan.equals("")) && (borrowerId.equals("")) && 
                   (borrowerName.equals(""))) {
            type = 1;
            String bIdForThisCgpan = processor.getBorowwerForCGPAN(cgpan);
            Log.log(5, "GMAction", "showClosureDetails", 
                    " Bid For Pan - " + bIdForThisCgpan);
            if (!borrowerIds.contains(bIdForThisCgpan)) {
                throw new NoDataException(cgpan + "is not a valid Cgpan for " + 
                                          "the Member Id :" + memberId + 
                                          ". Please enter correct Cgpan");
            }

            int claimCount = appProcessor.getClaimCount(bIdForThisCgpan);
            if (claimCount > 0) {
                throw new MessageException("Application cannot be Closed for this borrower since Claim Application has been submitted");
            }

            Application application = new Application();
            try {
                application = appProcessor.getAppForCgpan(memberId, cgpan);
            } catch (DatabaseException databaseException) {
                if (databaseException.getMessage().equals("Application does not exist.")) {
                    throw new DatabaseException("The application is not a live application");
                }

            }

            closureDetails = 
                    gmProcessor.viewClosureDetails(cgpan, type, memberId);
            if (closureDetails.isEmpty()) {
                throw new NoDataException("There are no Closure Details for the Entered ID");
            }

            ArrayList reasons = gmProcessor.getAllReasonsForClosure();
            gmActionForm.setClosureReasons(reasons);
            gmActionForm.setClosureDetails(closureDetails);

            forward = "success";
        } else if ((!borrowerName.equals("")) && (borrowerId.equals("")) && 
                   (cgpan.equals(""))) {
            type = 2;
            ArrayList bIdForBorrowerName = 
                gmProcessor.getBorrowerIdForBorrowerName(borrowerName, 
                                                         memberId);

            gmActionForm.setBorrowerIds(bIdForBorrowerName);

            forward = "bidList";
        }

        Log.log(4, "GMAction", "showClosureDetails", "Exited");

        return mapping.findForward(forward);
    }

    public ActionForward saveClosureDetails(ActionMapping mapping, 
                                            ActionForm form, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "saveClosureDetails", "Entered");
        GMActionForm gmActionForm = (GMActionForm)form;
        GMProcessor gmProcessor = new GMProcessor();
        CgpanInfo closureCgpanInfo = null;

        Enumeration parameters = request.getParameterNames();

        while (parameters.hasMoreElements()) {
            String parameter = (String)parameters.nextElement();

            String value = request.getParameter(parameter);
            Log.log(5, "GMAction", "##saveClosureDetails##", 
                    "param and value " + parameter + ", " + value);
        }

        Map closureCgpanMap = gmActionForm.getClosureCgpans();
        Set closureCgpanSet = closureCgpanMap.keySet();
        Iterator closureCgpanIterator = closureCgpanSet.iterator();

        Log.log(5, "GMAction", "saveClosureDetails", 
                "cgpan map " + closureCgpanMap.size());

        Map reasonMap = gmActionForm.getReasonForClosure();
        Log.log(5, "GMAction", "saveClosureDetails", 
                "reason map " + reasonMap.size());
        Set reasonSet = reasonMap.keySet();
        Iterator reasonIterator = reasonSet.iterator();

        Map closureFlagMap = gmActionForm.getClosureFlag();
        Log.log(5, "GMAction", "saveClosureDetails", 
                "closureFlag map " + closureFlagMap.size());
        Set closureFlagSet = closureFlagMap.keySet();
        Iterator closureFlagIterator = closureFlagSet.iterator();

        Map remarksForClosureMap = gmActionForm.getRemarksForClosure();
        Log.log(5, "GMAction", "saveClosureDetails", 
                "Remarks map " + remarksForClosureMap.size());
        Set remarksForClosureSet = remarksForClosureMap.keySet();
        Iterator remarksForClosureIterator = remarksForClosureSet.iterator();

        User user = getUserInformation(request);
        String userId = user.getUserId();
        Log.log(5, "GMAction", "saveClosureDetails", "userId " + userId);

        String fromId = user.getEmailId();
        Log.log(5, "GMAction", "saveClosureDetails", "fromId " + fromId);

        String cgpan = null;
        String reason = null;
        String closeFlag = null;
        String closureRemarks = null;
        ArrayList closureCgpanInfos = new ArrayList();

        boolean closureStatus = false;
        String message = "Application are successfully closed";
        String errorMessage = "";
        while (closureCgpanIterator.hasNext()) {
            cgpan = (String)closureCgpanMap.get(closureCgpanIterator.next());

            closureCgpanIterator.remove();

            Log.log(5, "GMAction--", "Closure", "cgpan" + cgpan);
            if (closureFlagMap.containsKey(cgpan)) {
                closeFlag = (String)closureFlagMap.get(cgpan);
                Log.log(5, "GMAction--", "Closure", "Flag" + closeFlag);

                reason = (String)reasonMap.get(cgpan);
                Log.log(5, "GMAction--", "Closure", "reason" + reason);

                closureRemarks = (String)remarksForClosureMap.get(cgpan);
                Log.log(5, "GMAction--", "Closure", 
                        "remarks" + closureRemarks);

                closureCgpanInfo = new CgpanInfo();
                closureCgpanInfo.setCgpan(cgpan);
                closureCgpanInfo.setReasonForClosure(reason);
                closureCgpanInfo.setRemarks(closureRemarks);
                closureCgpanInfos.add(closureCgpanInfo);
                try {
                    gmProcessor.sendMailForClosure(cgpan, userId, fromId, 
                                                   reason);
                } catch (MailerException e) {
                    errorMessage = " But Sending E-mails failed.";
                }

                closureStatus = 
                        gmProcessor.closure(cgpan, reason, closureRemarks, 
                                            userId);
            }
        }

        request.setAttribute("message", message + errorMessage);
        Log.log(4, "GMAction", "saveClosureDetails", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward showFilterForFeeNotPaid(ActionMapping mapping, 
                                                 ActionForm form, 
                                                 HttpServletRequest request, 
                                                 HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "showFilterForFeeNotPaid", "Entered");

        Log.log(4, "GMAction", "showFilterForFeeNotPaid", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward showDetailsForFeeNotPaid(ActionMapping mapping, 
                                                  ActionForm form, 
                                                  HttpServletRequest request, 
                                                  HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "showDetailsForFeeNotPaid", "Entered");

        GMProcessor gmProcessor = new GMProcessor();
        GMActionForm gmActionForm = (GMActionForm)form;

        String feeType = gmActionForm.getFeeNotPaid();
        Log.log(5, "GMAction", "showDetailsForFeeNotPaid", 
                "Fee Type" + feeType);

        TreeMap memberIdCgpans = 
            gmProcessor.viewMemberIdCgpansForClosure(feeType);
        gmActionForm.setMemberIdCgpans(memberIdCgpans);

        Log.log(4, "GMAction", "showDetailsForFeeNotPaid", "Exited");

        return mapping.findForward("success");
    }


    public ActionForward closeAppsForFeeNotPaid(ActionMapping mapping, 
                                                ActionForm form, 
                                                HttpServletRequest request, 
                                                HttpServletResponse response) throws Exception {

        Log.log(Log.INFO, "GMAction", "closeAppsForFeeNotPaid", "Entered");

        GMProcessor gmProcessor = new GMProcessor();
        GMActionForm gmActionForm = (GMActionForm)form;

        String feeType = gmActionForm.getFeeNotPaid();
        Log.log(Log.INFO, "GMAction", "closeAppsForFeeNotPaid", 
                "Fee Type" + feeType);

        String memberId = (String)request.getParameter("memberIdClosure");
        Log.log(Log.INFO, "GMAction", "closeAppsForFeeNotPaid", 
                "memberId " + memberId);

        HttpSession session = request.getSession(false);
        session.setAttribute("clNotPaid", memberId);

        ArrayList cgpans = gmProcessor.viewCgpansForClosure(memberId, feeType);

        int size = cgpans.size();
        Log.log(Log.INFO, "GMAction", "closeAppsForFeeNotPaid", 
                "cgpans.size " + size);
        Set memidSet = null;
        Iterator idIter = null;

        String cgpan = null;

        String memberIdCl = null;
        String bIdCl = null;
        String bNameCl = null;
        String panCl = null;
        String schemeCl = null;
        double sancAmtCl = 0;

        int panSize = 0;

        Set borrSet = null;
        Iterator borrIter = null;

        HashMap borrInfos = null;
        ArrayList cgpanInfos = null;

        BorrowerInfo borrInfo = null;
        CgpanInfo cgpanInfo = null;

        HashMap borrInfosTemp = null;
        ArrayList cgpanInfosTemp = null;
        ArrayList cgpanInfosTemp1 = new ArrayList();

        HashMap clDtls = null;
        HashMap clDtlsTemp = new HashMap();

        BorrowerInfo borrInfoTemp = null;
        CgpanInfo cgpanInfoTemp = null;

        borrInfosTemp = new HashMap();

        for (int i = 0; i < size; ++i) {
            cgpan = (String)cgpans.get(i);
            Log.log(Log.INFO, "GMAction", "closeAppsForFeeNotPaid", 
                    "cgpan " + cgpan);

            clDtls = gmProcessor.getClosureDetailsForFeeNotPaid(cgpan);

            memidSet = clDtls.keySet();
            idIter = memidSet.iterator();

            while (idIter.hasNext()) {
                memberIdCl = (String)idIter.next();
                Log.log(Log.INFO, "GMAction", "closeAppsForFeeNotPaid", 
                        "memberIdCl " + memberIdCl);

                borrInfos = (HashMap)clDtls.get(memberIdCl);

                borrSet = borrInfos.keySet();
                borrIter = borrSet.iterator();

                while (borrIter.hasNext()) {
                    bIdCl = (String)borrIter.next();
                    Log.log(Log.INFO, "GMAction", "closeAppsForFeeNotPaid", 
                            "bIdCl " + bIdCl);

                    borrInfo = (BorrowerInfo)borrInfos.get(bIdCl);

                    bNameCl = borrInfo.getBorrowerName();
                    Log.log(Log.INFO, "GMAction", "closeAppsForFeeNotPaid", 
                            "bNameCl " + bNameCl);

                    cgpanInfos = borrInfo.getCgpanInfos();
                    panSize = cgpanInfos.size();
                    Log.log(Log.INFO, "GMAction", "closeAppsForFeeNotPaid", 
                            "panSize " + panSize);

                    for (int j = 0; j < panSize; ++j) {
                        cgpanInfo = (CgpanInfo)cgpanInfos.get(j);

                        panCl = cgpanInfo.getCgpan();
                        Log.log(Log.INFO, "GMAction", "closeAppsForFeeNotPaid", 
                                "panCl " + panCl);

                        schemeCl = cgpanInfo.getScheme();
                        Log.log(Log.INFO, "GMAction", "closeAppsForFeeNotPaid", 
                                "schemeCl " + schemeCl);

                        sancAmtCl = cgpanInfo.getSanctionedAmount();
                        Log.log(Log.INFO, "GMAction", "closeAppsForFeeNotPaid", 
                                "sancAmtCl " + sancAmtCl);
                    }
                }
            }

            borrInfoTemp = new BorrowerInfo();
            cgpanInfoTemp = new CgpanInfo();
            cgpanInfosTemp = new ArrayList();

            cgpanInfoTemp.setCgpan(panCl);
            cgpanInfoTemp.setScheme(schemeCl);
            cgpanInfoTemp.setSanctionedAmount(sancAmtCl);
            cgpanInfosTemp.add(cgpanInfoTemp);

            borrInfoTemp.setBorrowerId(bIdCl);
            borrInfoTemp.setBorrowerName(bNameCl);

            if (borrInfosTemp.containsKey(bIdCl)) {
                borrInfoTemp = (BorrowerInfo)borrInfosTemp.get(bIdCl);
                cgpanInfosTemp = borrInfoTemp.getCgpanInfos();
                cgpanInfosTemp.add(cgpanInfoTemp);

                borrInfoTemp.setCgpanInfos(cgpanInfosTemp);
                borrInfosTemp.put(bIdCl, borrInfoTemp);
            } else {
                borrInfoTemp.setCgpanInfos(cgpanInfosTemp);
                borrInfosTemp.put(bIdCl, borrInfoTemp);
            }
        }
        clDtlsTemp.put(memberIdCl, borrInfosTemp);

        ArrayList reasons = gmProcessor.getAllReasonsForClosure();
        gmActionForm.setClReasons(reasons);

        gmActionForm.setClosureDetailsNotPaid(clDtlsTemp);

        Log.log(Log.INFO, "GMAction", "closeAppsForFeeNotPaid", "Exited");
        return mapping.findForward(Constants.SUCCESS);
    }


    public ActionForward closeCgpans(ActionMapping mapping, ActionForm form, 
                                     HttpServletRequest request, 
                                     HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "closeCgpans", "Entered");
        GMActionForm gmActionForm = (GMActionForm)form;
        GMProcessor gmProcessor = new GMProcessor();

        Map clCgpanMap = gmActionForm.getClCgpan();
        Set clCgpanSet = clCgpanMap.keySet();
        Iterator clCgpanIterator = clCgpanSet.iterator();
        Log.log(5, "GMAction", "closeCgpans", 
                "cgpan map " + clCgpanMap.size());

        Map reasonMap = gmActionForm.getReasonForCl();
        Log.log(5, "GMAction", "closeCgpans", 
                "reason map " + reasonMap.size());
        Set reasonSet = reasonMap.keySet();
        Iterator reasonIterator = reasonSet.iterator();

        Map clFlagMap = gmActionForm.getClFlag();
        Log.log(5, "GMAction", "closeCgpans", 
                "closureFlag map " + clFlagMap.size());
        Set clFlagSet = clFlagMap.keySet();
        Iterator clFlagIterator = clFlagSet.iterator();

        Map remarksForClMap = gmActionForm.getRemarksForCl();
        Log.log(5, "GMAction", "closeCgpans", 
                "Remarks map " + remarksForClMap.size());
        Set remarksForClSet = remarksForClMap.keySet();
        Iterator remarksForClIterator = remarksForClSet.iterator();

        User user = getUserInformation(request);
        String userId = user.getUserId();
        Log.log(5, "GMAction", "closeCgpans", "userId " + userId);

        String fromId = user.getEmailId();
        Log.log(5, "GMAction", "closeCgpans", "fromId " + fromId);

        String cgpan = null;
        String reason = null;
        String clFlag = null;
        String clRemarks = null;

        boolean clStatus = false;
        String message = "Application are successfully closed";
        String errorMessage = "";
        while (clCgpanIterator.hasNext()) {
            cgpan = (String)clCgpanMap.get(clCgpanIterator.next());
            Log.log(5, "GMAction--", "Closure", "cgpan" + cgpan);

            if (clFlagMap.containsKey(cgpan)) {
                clFlag = (String)clFlagMap.get(cgpan);
                Log.log(5, "GMAction--", "Closure", "Flag" + clFlag);

                reason = (String)reasonMap.get(cgpan);
                Log.log(5, "GMAction--", "Closure", "reason" + reason);

                clRemarks = (String)remarksForClMap.get(cgpan);
                Log.log(5, "GMAction--", "Closure", "remarks" + clRemarks);
                try {
                    gmProcessor.sendMailForClosure(cgpan, userId, fromId, 
                                                   reason);
                } catch (MailerException e) {
                    errorMessage = " But Sending E-mails failed.";
                }
                clStatus = 
                        gmProcessor.closure(cgpan, reason, clRemarks, userId);
            }

        }

        request.setAttribute("message", message + errorMessage);

        Log.log(4, "GMAction", "closeCgpans", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward getIdForExport(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "getIdForExport", "Entered");

        GMProcessor gmProcessor = new GMProcessor();

        GMActionForm gmActionForm = (GMActionForm)form;

        User user = getUserInformation(request);

        String bankId = user.getBankId();
        String zoneId = user.getZoneId();
        String branchId = user.getBranchId();
        String memberId = bankId.concat(zoneId).concat(branchId);

        if (!bankId.equalsIgnoreCase("0000")) {
            gmActionForm.setMemberIdForExport(memberId);
            Log.log(4, "GMAction", "getIdForExport", "memberId :" + memberId);
            exportPeriodicInfo(mapping, form, request, response);

            return mapping.findForward("ExportForInternet");
        }

        gmActionForm.setMemberIdForExport("");
        Log.log(4, "GMAction", "getIdForExport", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward showCgpanDetailsLink(ActionMapping mapping, 
                                              ActionForm form, 
                                              HttpServletRequest request, 
                                              HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "showCgpanDetailsLink", "Entered");

        GMProcessor gmProcessor = new GMProcessor();
        CgpanDetail cgpanDetail = null;

        GMActionForm gmActionForm = (GMActionForm)form;

        String cgpan = request.getParameter("cgpanDetail");
        Log.log(5, "GMAction", "showCgpanDetailsLink", "cgpan" + cgpan);
        cgpanDetail = gmProcessor.getCgpanDetails(cgpan);

        String bid = cgpanDetail.getBorrowerId();

        gmActionForm.setBidForCgpanLink(bid);
        Log.log(5, "GMAction", "showCgpanDetailsLink", "bID" + bid);
        String bName = cgpanDetail.getBorrowerName();
        gmActionForm.setBorrowerNameForLink(bName);
        Log.log(5, "GMAction", "showCgpanDetailsLink", "bname" + bName);
        String city = cgpanDetail.getCity();
        gmActionForm.setCityForCgpanLink(city);
        Log.log(5, "GMAction", "showCgpanDetailsLink", "city" + city);
        String prName = cgpanDetail.getChiefPromoterName();
        gmActionForm.setPromoterNameForLink(prName);
        Log.log(5, "GMAction", "showCgpanDetailsLink", "pr name " + prName);
        double amountApproved = cgpanDetail.getAmountApproved();
        gmActionForm.setAmountApprovedForLink(amountApproved);
        Log.log(5, "GMAction", "showCgpanDetailsLink", 
                "amt appd " + amountApproved);
        double tcAmountSanctioned = cgpanDetail.getTcAmountSanctioned();
        gmActionForm.setTcAmountSanctionedForLink(tcAmountSanctioned);
        Log.log(5, "GMAction", "showCgpanDetailsLink", 
                "Tc Amt Sanc" + tcAmountSanctioned);
        double wcAmountSanctioned = cgpanDetail.getWcAmountSanctioned();
        gmActionForm.setWcAmountSanctionedForLink(wcAmountSanctioned);
        Log.log(5, "GMAction", "showCgpanDetailsLink", 
                "wc Amt " + wcAmountSanctioned);
        java.util.Date guaranteeIssueDate = 
            cgpanDetail.getGuaranteeIssueDate();
        gmActionForm.setGuaranteeIssueDateForLink(guaranteeIssueDate);
        Log.log(5, "GMAction", "showCgpanDetailsLink", 
                "date" + guaranteeIssueDate);
        Log.log(4, "GMAction", "showCgpanDetailsLink", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward exportPeriodicInfo(ActionMapping mapping, 
                                            ActionForm form, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "exportPeriodicInfo", "Entered");

        GMProcessor gmProcessor = new GMProcessor();
        GMActionForm gmActionForm = (GMActionForm)form;
        HttpSession session = request.getSession(false);

        String memberId = null;

        Hashtable periodicInfos = new Hashtable();
        PeriodicInfo periodicInfo = null;

        ArrayList borrowerIds = new ArrayList();

        ArrayList osPeriodicInfoDetails = null;
        ArrayList rpPeriodicInfoDetails = null;
        ArrayList dbPeriodicInfoDetails = null;
        ArrayList recoveryDetails = null;
        NPADetails npaDetails = null;

        memberId = gmActionForm.getMemberIdForExport();

        ClaimsProcessor claimsProcessor = new ClaimsProcessor();
        Vector memberIds = claimsProcessor.getAllMemberIds();
        if (!memberIds.contains(memberId)) {
            throw new NoMemberFoundException("Member Id :" + memberId + 
                                             " does not exist in the database.");
        }
        borrowerIds = gmProcessor.getBorrowerIds(memberId);

        ArrayList osDtls = null;
        ArrayList dbDtls = null;
        ArrayList rpDtls = null;

        ClaimsProcessor cl = new ClaimsProcessor();
        String borrowerName = null;
        String borrowerId = null;

        int type = 0;

        for (int i = 0; i < borrowerIds.size(); i++) {
            periodicInfo = new PeriodicInfo();

            borrowerId = (String)borrowerIds.get(i);

            periodicInfo.setBorrowerId(borrowerId);

            com.cgtsi.claim.BorrowerInfo bInfoCl = 
                cl.getBorrowerDetails(borrowerId);

            borrowerName = bInfoCl.getBorrowerName();
            Log.log(5, "GMAction", "exportPeriodicInfo", 
                    "borrowerName " + borrowerName);
            Log.log(5, "GMAction", "exportPeriodicInfo", 
                    "borrowerId " + borrowerId);
            periodicInfo.setBorrowerName(borrowerName);

            Vector CgpansForBid = gmProcessor.getCGPANs(borrowerId);
            int vectorSize = CgpansForBid.size();

            Disbursement disbursementDetail = null;
            dbDtls = new ArrayList();

            for (int j = 0; j < vectorSize; j++) {
                HashMap cgpanDetails = (HashMap)CgpansForBid.get(j);
                Set cgpanSet = cgpanDetails.keySet();
                Iterator cgpanIterator = cgpanSet.iterator();

                while (cgpanIterator.hasNext()) {
                    String cgpan = (String)cgpanIterator.next();
                    if (cgpan.equals("CGPAN")) {
                        String cgpanValue = (String)cgpanDetails.get(cgpan);

                        int cgpanLength = cgpanValue.length();
                        int type1 = cgpanLength - 2;
                        int type2 = cgpanLength - 1;
                        String cgpanType = cgpanValue.substring(type1, type2);
                        if (cgpanType.equalsIgnoreCase("t")) {
                            disbursementDetail = 
                                    getDisbursementDetailsForCgpan(cgpanValue);
                            String scheme = disbursementDetail.getScheme();
                            double sanctionedAmt = 
                                disbursementDetail.getSanctionedAmount();
                            ArrayList disbursementAmounts = 
                                disbursementDetail.getDisbursementAmounts();

                            if ((disbursementAmounts == null) || 
                                (disbursementAmounts.size() == 0)) {
                                disbursementDetail = new Disbursement();
                                disbursementDetail.setCgpan(cgpanValue);
                                disbursementDetail.setScheme(scheme);
                                disbursementDetail.setSanctionedAmount(sanctionedAmt);
                                dbDtls.add(disbursementDetail);
                                Log.log(5, "GMAction", "exportPeriodicInfo", 
                                        "cgpan added dis dtl " + cgpanValue);
                            } else {
                                dbDtls.add(disbursementDetail);
                            }

                        }

                    } else
                        ;
                }

            }

            periodicInfo.setDisbursementDetails(dbDtls);

            Repayment repaymentDetail = null;
            rpDtls = new ArrayList();

            for (int j = 0; j < vectorSize; j++) {
                HashMap cgpanDetails = (HashMap)CgpansForBid.get(j);
                Set cgpanSet = cgpanDetails.keySet();
                Iterator cgpanIterator = cgpanSet.iterator();

                while (cgpanIterator.hasNext()) {
                    String cgpan = (String)cgpanIterator.next();
                    if (cgpan.equals("CGPAN")) {
                        String cgpanValue = (String)cgpanDetails.get(cgpan);

                        int cgpanLength = cgpanValue.length();
                        int type1 = cgpanLength - 2;
                        int type2 = cgpanLength - 1;
                        String cgpanType = cgpanValue.substring(type1, type2);
                        if (cgpanType.equalsIgnoreCase("t")) {
                            repaymentDetail = 
                                    getRepaymentDetailsForCgpan(cgpanValue);
                            String scheme = repaymentDetail.getScheme();
                            ArrayList repayAmts = 
                                repaymentDetail.getRepaymentAmounts();

                            if ((repayAmts == null) || 
                                (repayAmts.size() == 0)) {
                                repaymentDetail = new Repayment();
                                repaymentDetail.setCgpan(cgpanValue);
                                repaymentDetail.setScheme(scheme);
                                rpDtls.add(repaymentDetail);
                                Log.log(5, "GMAction", "exportPeriodicInfo", 
                                        "cgpan added dis dtl " + cgpanValue);
                            } else {
                                rpDtls.add(repaymentDetail);
                            }

                        }

                    } else
                        ;
                }

            }

            periodicInfo.setRepaymentDetails(rpDtls);

            OutstandingDetail outstandingDetail = null;
            osDtls = new ArrayList();

            for (int j = 0; j < vectorSize; j++) {
                HashMap cgpanDetails = (HashMap)CgpansForBid.get(j);
                Set cgpanSet = cgpanDetails.keySet();
                Iterator cgpanIterator = cgpanSet.iterator();

                while (cgpanIterator.hasNext()) {
                    String cgpan = (String)cgpanIterator.next();
                    if (cgpan.equals("CGPAN")) {
                        String cgpanValue = (String)cgpanDetails.get(cgpan);
                        outstandingDetail = 
                                getOutstandingDetailsForCgpan(cgpanValue);
                        String scheme = outstandingDetail.getScheme();
                        double sancAmtTc = 
                            outstandingDetail.getTcSanctionedAmount();
                        double sancAmtWcFb = 
                            outstandingDetail.getWcFBSanctionedAmount();
                        double sancAmtWcNfb = 
                            outstandingDetail.getWcNFBSanctionedAmount();
                        ArrayList outstandingAmounts = 
                            outstandingDetail.getOutstandingAmounts();

                        if ((outstandingAmounts == null) || 
                            (outstandingAmounts.size() == 0)) {
                            outstandingDetail = new OutstandingDetail();
                            outstandingDetail.setCgpan(cgpanValue);
                            outstandingDetail.setScheme(scheme);
                            outstandingDetail.setTcSanctionedAmount(sancAmtTc);
                            outstandingDetail.setWcFBSanctionedAmount(sancAmtWcFb);
                            outstandingDetail.setWcNFBSanctionedAmount(sancAmtWcNfb);
                            osDtls.add(outstandingDetail);
                            Log.log(5, "GMAction", "exportPeriodicInfo", 
                                    "cgpan added dis dtl " + cgpanValue);
                        } else {
                            osDtls.add(outstandingDetail);
                        }

                    } else
                        ;
                }

            }

            periodicInfo.setOutstandingDetails(osDtls);

            recoveryDetails = gmProcessor.getRecoveryDetails(borrowerId);
            if (recoveryDetails != null) {
                periodicInfo.setRecoveryDetails(recoveryDetails);
            }
            npaDetails = gmProcessor.getNPADetails(borrowerId);

            if (npaDetails != null) {
                periodicInfo.setNpaDetails(npaDetails);
            } else {
                NPADetails npa = new NPADetails();
                periodicInfo.setNpaDetails(npa);
            }
            periodicInfos.put(borrowerId, periodicInfo);
        }
        try {
            String contextPath1 = 
                request.getSession(false).getServletContext().getRealPath("");
            String contextPath = PropertyLoader.changeToOSpath(contextPath1);
            Log.log(5, "GMAction", "exportPeriodicInfo", 
                    "contextPAth=>" + contextPath);
            FileOutputStream fileOutputStream = null;
            ObjectOutputStream objectOutputStream = null;

            java.util.Date date = new java.util.Date();

            Format formatter = new SimpleDateFormat("ddMMyy");
            String s = formatter.format(date);
            Log.log(5, "GMAction", "exportPeriodicInfo", "date =>" + s);

            String nameOfFile = "PER.EXP";

            String fileName = 
                contextPath + File.separator + "Download" + File.separator + 
                s + "-" + memberId + "-" + nameOfFile;

            String formattedToOSPath = 
                request.getContextPath() + File.separator + "Download" + 
                File.separator + s + "-" + memberId + "-" + nameOfFile;

            session.setAttribute("fileName", formattedToOSPath);

            Log.log(5, "GMAction", "exportPeriodicInfo", 
                    "File PAth" + fileName);

            File exportFlatFile = new File(fileName);
            if (!exportFlatFile.exists()) {
                exportFlatFile.createNewFile();
            }

            fileOutputStream = new FileOutputStream(exportFlatFile);
            objectOutputStream = new ObjectOutputStream(fileOutputStream);

            objectOutputStream.writeObject(periodicInfos);
            objectOutputStream.close();
            fileOutputStream.close();
        } catch (IOException ioexception) {
            Log.log(2, "GMAction", "Export Periodic Info File", 
                    "Export failed because " + ioexception.getMessage());
            throw new ExportFailedException("Export Failed.");
        }

        Log.log(4, "GMAction", "exportPeriodicInfo", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward uploadFile(ActionMapping mapping, ActionForm form, 
                                    HttpServletRequest request, 
                                    HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "uploadFile", "Entered");

        GMProcessor gmProcessor = new GMProcessor();

        GMActionForm gmActionForm = (GMActionForm)form;

        User user = getUserInformation(request);

        Log.log(4, "GMAction", "uploadFile", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward approvePeriodicInfo(ActionMapping mapping, 
                                             ActionForm form, 
                                             HttpServletRequest request, 
                                             HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "approvePeriodicInfo", "Entered");

        GMActionForm gmActionForm = (GMActionForm)form;
        Map approveFlags = gmActionForm.getApproveBorrowerFlag();
        Set approveFlagSet = approveFlags.keySet();
        Iterator approveFlagIterator = approveFlagSet.iterator();

        GMProcessor gmProcessor = new GMProcessor();
        if ((approveFlags.isEmpty()) || (approveFlags.size() == 0)) {
            throw new MessageException("Atleast One Periodic Info Details should be approved");
        }
        while (approveFlagIterator.hasNext()) {
            String key = (String)approveFlagIterator.next();

            Log.log(4, "GMAction", "approveBorrowerDetails", "key :" + key);
            String memBorrowerId = (String)approveFlags.get(key);

            Log.log(4, "GMAction", "approveBorrowerDetails", 
                    "memBorrowerId :" + memBorrowerId);
            int index = memBorrowerId.indexOf("-");
            String memberId = memBorrowerId.substring(0, index);
            String borrowerId = 
                memBorrowerId.substring(index + 1, memBorrowerId.length());

            Log.log(4, "GMAction", "approvePeriodicInfo", 
                    "calling approve from processor");
            gmProcessor.approvePeriodicInfo(memberId, borrowerId);
            Log.log(4, "GMAction", "approvePeriodicInfo", "approved details");
        }

        request.setAttribute("message", 
                             "Periodic Info Details have been Approved Successfully");
        Log.log(4, "GMAction", "approvePeriodicInfo", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward showOutstandingsForApproval(ActionMapping mapping, 
                                                     ActionForm form, 
                                                     HttpServletRequest request, 
                                                     HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "showOutstandingsForApproval", "Entered");

        DynaActionForm dynaForm = (DynaActionForm)form;
        GMProcessor gmProcessor = new GMProcessor();

        String borrowerId = (String)dynaForm.get("borrowerId");
        String memberId = (String)dynaForm.get("memberId");

        Log.log(4, "GMAction", "showOutstandingsForApproval", 
                "borrowerId :" + borrowerId);
        Log.log(4, "GMAction", "showOutstandingsForApproval", 
                "memberId :" + memberId);

        ClaimsProcessor cpProcessor = new ClaimsProcessor();
        Vector memberIds = cpProcessor.getAllMemberIds();
        if (!memberIds.contains(memberId)) {
            throw new NoMemberFoundException("The Member ID does not exist");
        }

        if (!borrowerId.equals("")) {
            ArrayList borrowerIds = cpProcessor.getAllBorrowerIDs(memberId);
            if (!borrowerIds.contains(borrowerId)) {
                throw new NoDataException("The Borrower ID does not exist for this Member ID");
            }
        }

        ArrayList osDetails = 
            gmProcessor.getOutstandingsForBid(borrowerId, memberId);

        dynaForm.set("outstandingDetails", osDetails);

        Log.log(4, "GMAction", "showOutstandingsForApproval", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward showDisbursementsForApproval(ActionMapping mapping, 
                                                      ActionForm form, 
                                                      HttpServletRequest request, 
                                                      HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "showDisbursementsForApproval", "Entered");

        DynaActionForm dynaForm = (DynaActionForm)form;
        GMProcessor gmProcessor = new GMProcessor();

        String borrowerId = (String)dynaForm.get("borrowerId");
        String memberId = (String)dynaForm.get("memberId");

        ArrayList disDetails = 
            gmProcessor.getDisbursementsForBid(borrowerId, memberId);

        dynaForm.set("disbursementDetails", disDetails);

        Log.log(4, "GMAction", "showDisbursementsForApproval", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward showRepaymentsForApproval(ActionMapping mapping, 
                                                   ActionForm form, 
                                                   HttpServletRequest request, 
                                                   HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "showRepaymentsForApproval", "Entered");

        DynaActionForm dynaForm = (DynaActionForm)form;
        GMProcessor gmProcessor = new GMProcessor();

        String borrowerId = (String)dynaForm.get("borrowerId");
        String memberId = (String)dynaForm.get("memberId");

        ArrayList repayDetails = 
            gmProcessor.getRepaymentsForBid(borrowerId, memberId);

        dynaForm.set("repaymentDetails", repayDetails);

        Log.log(4, "GMAction", "showRepaymentsForApproval", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward showNpaForApproval(ActionMapping mapping, 
                                            ActionForm form, 
                                            HttpServletRequest request, 
                                            HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "showNpaForApproval", "Entered");

        DynaActionForm dynaForm = (DynaActionForm)form;
        GMProcessor gmProcessor = new GMProcessor();

        String borrowerId = (String)dynaForm.get("borrowerId");

        ArrayList npaDetails = 
            gmProcessor.getNpaDetailsForApproval(borrowerId);

        dynaForm.set("npaDetails", npaDetails);

        Log.log(4, "GMAction", "showNpaForApproval", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward showBorrowerDetailsForApproval(ActionMapping mapping, 
                                                        ActionForm form, 
                                                        HttpServletRequest request, 
                                                        HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "showBorrowerDetailsForApproval", "Entered");
             //  System.out.println("GMAction     showBorrowerDetailsForApproval   S");
        DynaActionForm dynaForm = (DynaActionForm)form;
        GMProcessor gmProcessor = new GMProcessor();

        String memberId = request.getParameter("memberId");
        String borrowerId = request.getParameter("borrowerId");

        ClaimsProcessor cpProcessor = new ClaimsProcessor();
       // System.out.println("GMAction  = new ClaimsProcessor();   S");
        Vector memberIds = cpProcessor.getAllMemberIds();
     //   System.out.println("GMAction  = new ClaimsProcessor();   E");
        if (!memberIds.contains(memberId)) {
            throw new NoMemberFoundException("The Member ID does not exist");
        }

        if (borrowerId!=null && !borrowerId.equals("")) {
            ArrayList borrowerIds = cpProcessor.getAllBorrowerIDs(memberId);
            if (!borrowerIds.contains(borrowerId)) {
                throw new NoDataException("The Borrower ID does not exist for this Member ID");
            }

        }
        else
        {
        	 throw new NoDataException("Incorrect Borrower ID.");
        }

        BorrowerDetails borrowerDetails = 
            gmProcessor.viewBorrowerDetails(memberId, borrowerId, 0);
    //    System.out.println("GMAction gmProcessor.viewBorrowerCgpan   S");
        String borrowerCgpan=
        	gmProcessor.viewBorrowerCgpan(borrowerId);
      //  System.out.println("GMAction  gmProcessor.viewBorrowerCgpan   E ");
     //   System.out.println("String borrowerCgpan= "+borrowerCgpan);
        	 

        int borrowerRefNo = borrowerDetails.getSsiDetails().getBorrowerRefNo();

        ArrayList modifiedBorrowerDetails = 
            gmProcessor.getBorrowerDetailsForApproval(borrowerRefNo);

        
        dynaForm.set("borrowerDetails", modifiedBorrowerDetails);
        dynaForm.set("borrowerId", borrowerId);  
        dynaForm.set("linkedCGPANS", borrowerCgpan);

        return mapping.findForward("success");
    }

    
    public ActionForward approveBorrowerDetails(ActionMapping mapping, 
                                                ActionForm form, 
                                                HttpServletRequest request, 
                                                HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "approveBorrowerDetails", "Entered");
        boolean errorFlag=true;
        String errorMessage="";
    	GMActionForm gmActionForm = (GMActionForm)form;
		GMProcessor gmProcessor = new GMProcessor();
	    User user = getUserInformation(request);
	//    System.out.println(user.getUserName()+"approveBorrowerDetails gmActionForm"+gmActionForm.getBorrowerApprovalRemarks());
	//	System.out.println(user.getUserName()+"approveBorrowerDetails gmActionForm"+gmActionForm.getBidsList());
	    
		
		
        try
        {
				    
				
			
				Map approveFlags = gmActionForm.getApproveBorrowerFlag();
				//System.out.println("approveBorrowerDetails called "+approveFlags.size());
				Set approveFlagSet = approveFlags.keySet();
				Iterator approveFlagIterator = approveFlagSet.iterator();
				
			
				
				int i=0;
			//	String remarksArray[]=gmActionForm.getBorrowerApprovalRemarks();
				//System.out.println("approveBorrowerDetails remarksArray "+remarksArray.length);
				while (approveFlagIterator.hasNext()) 
				{
				    String  memBorrowerId= (String)approveFlagIterator.next();   
				    String key = (String)approveFlags.get(memBorrowerId);    
				   // System.out.println(key+"approveBorrowerDetails called value "+memBorrowerId);
				    String SlipArrMemIDBID[]=memBorrowerId.split("-");
					if((key.equalsIgnoreCase("AP") || key.equalsIgnoreCase("RE"))&&  SlipArrMemIDBID.length==2)
					{
						//System.out.println(key+"Selected value "+SlipArrMemIDBID[0]);
						System.out.println("="+key+"=Selected value="+SlipArrMemIDBID[1]+"=");
						//System.out.println("Remarks "+remarkArray.get(i));
						if(SlipArrMemIDBID[1]!=null && key.equalsIgnoreCase("AP"))
						{
							gmProcessor.approveBorrowerDetails(SlipArrMemIDBID[1],user.getUserId());
						}
						if(SlipArrMemIDBID[1]!=null && key.equalsIgnoreCase("RE"))
						{
					//		System.out.println(i+"Remarks "+remarksArray[i]);
							gmProcessor.rejectBorrowerDetails(SlipArrMemIDBID[1],user.getUserId());
						}
					}
					      
				    i++;
				}
				
        }
        catch(Exception e)
        {
        	LogClass.StepWritter("Exception in approveBorrowerDetails method "+e.getMessage());
			LogClass.writeExceptionOnFile(e);
        	errorFlag=false;
        //	System.out.println("exception in approveBorrowerDetails");
        	errorMessage=e.getMessage();
        //	e.printStackTrace();
        }
        finally
        {
        	if(errorFlag==false)
        	{
        		 request.setAttribute("message", 
                 "Something ["+errorMessage+"] went wrong, Please contact Support[support@cgtmse.in] team)");
        	}
        	else
        	{
        	    request.setAttribute("message", 
                "Wherever cases rejected by checker, Maker may resubmit these cases to checker with correct data. "+
                                                                                   "\n Record for approved cases have been successfully modified.");
        	}
        	//System.out.println("finally in approveBorrowerDetails");
        }
       // gmActionForm.setApproveBorrowerFlag("");
        return mapping.findForward("success");
    }

    public ActionForward showMemberForViewCgpan(ActionMapping mapping, 
                                                ActionForm form, 
                                                HttpServletRequest request, 
                                                HttpServletResponse response) throws Exception {
        GMProcessor gmProcessor = new GMProcessor();

        DynaActionForm dynaForm = (DynaActionForm)form;

        User user = getUserInformation(request);
        String bankId = user.getBankId();
        String zoneId = user.getZoneId();
        String brnchId = user.getBranchId();
        String forwardPage = "";
        if (bankId.equals("0000")) {
            dynaForm.initialize(mapping);
            forwardPage = "showMember";
        } else {
            ArrayList cgpanMapping = 
                gmProcessor.getCgpanMapping(bankId + zoneId + brnchId);
            dynaForm.set("cgpanMapping", cgpanMapping);
            forwardPage = "showCgpan";
        }

        return mapping.findForward(forwardPage);
    }

    public ActionForward viewCgpanMapping(ActionMapping mapping, 
                                          ActionForm form, 
                                          HttpServletRequest request, 
                                          HttpServletResponse response) throws Exception {
        GMProcessor gmProcessor = new GMProcessor();

        DynaActionForm dynaForm = (DynaActionForm)form;
        String memberId = (String)dynaForm.get("memberId");

        ClaimsProcessor cpProcessor = new ClaimsProcessor();
        Vector members = cpProcessor.getAllMemberIds();
        if (!members.contains(memberId)) {
            throw new NoMemberFoundException("Member Id does not exist.");
        }

        ArrayList cgpanMapping = gmProcessor.getCgpanMapping(memberId);
        dynaForm.set("cgpanMapping", cgpanMapping);

        return mapping.findForward("showCgpan");
    }

    public ActionForward showRecoveryForApproval(ActionMapping mapping, 
                                                 ActionForm form, 
                                                 HttpServletRequest request, 
                                                 HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "showRecoveryForApproval", "Entered");

        DynaActionForm dynaForm = (DynaActionForm)form;
        GMProcessor gmProcessor = new GMProcessor();

        String borrowerId = (String)dynaForm.get("borrowerId");

        ArrayList recoveryDetails = 
            gmProcessor.getRecoveryForApproval(borrowerId);

        dynaForm.set("recoveryDetails", recoveryDetails);

        Log.log(4, "GMAction", "showRecoveryForApproval", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward showRepaySchForApproval(ActionMapping mapping, 
                                                 ActionForm form, 
                                                 HttpServletRequest request, 
                                                 HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "showRepaySchForApproval", "Entered");

        DynaActionForm dynaForm = (DynaActionForm)form;
        GMProcessor gmProcessor = new GMProcessor();

        String borrowerId = (String)dynaForm.get("borrowerId");
        String memberId = (String)dynaForm.get("memberId");

        ArrayList repaySchDetails = 
            gmProcessor.getRepayScheduleForApproval(borrowerId, memberId);

        dynaForm.set("repayScheduleDetails", repaySchDetails);

        Log.log(4, "GMAction", "showRepaySchForApproval", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward showDisbursementDetailsForName(ActionMapping mapping, 
                                                        ActionForm form, 
                                                        HttpServletRequest request, 
                                                        HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "showDisbursementDetailsForName", "Entered");

        ArrayList periodicInfoDetails = new ArrayList();
        GMActionForm gmActionForm = (GMActionForm)form;

        GMProcessor gmProcessor = new GMProcessor();
        ApplicationProcessor appProcessor = new ApplicationProcessor();

        String memberId = gmActionForm.getMemberId();
        String cgpan = gmActionForm.getCgpan().toUpperCase();
        String borrowerId = gmActionForm.getBorrowerId().toUpperCase();
        String borrowerName = gmActionForm.getBorrowerName();

        gmActionForm.getDisbursementAmount().clear();
        gmActionForm.getDisbursementDate().clear();
        gmActionForm.getFinalDisbursement().clear();
        //int type = 2;
         int type = 0;

        String forward = "";

        String bidName = request.getParameter("bidName");

        String Bid = "";
        String ssiName = "";

        if (!bidName.substring(0, 4).equals("null")) {
            Bid = bidName.substring(0, 9);
            ssiName = bidName.substring(10, bidName.length() - 1);

            gmActionForm.setBorrowerId(Bid);
        } else {
            Bid = "";
            ssiName = bidName.substring(5, bidName.length() - 1);

            gmActionForm.setBorrowerId(ssiName);
        }

        int claimCount = appProcessor.getClaimCount(Bid);
        if (claimCount > 0) {
            throw new MessageException("Disbursement Details cannot be modified for this borrower since Claim Application has been submitted");
        }

        Log.log(4, "GMAction", "showDisbursementDetailsForName", 
                "bid from request :" + Bid);

        Log.log(4, "GMAction", "showDisbursementDetailsForName", 
                "ssiName from request :" + ssiName);

        periodicInfoDetails = 
                gmProcessor.viewDisbursementDetails(Bid, type);
        if (periodicInfoDetails.isEmpty()) {
            Log.log(5, "GMAction", "showDisbursementDetails", "No Data");
            throw new NoDataException("There are no Disbursement Details for the combination of inputs");
        }

        gmActionForm.setDisbPeriodicInfoDetails(periodicInfoDetails);

        gmActionForm.setBorrowerName("");

        Log.log(4, "GMAction", "showDisbursementDetailsForName", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward showOutstandingDetailsForName(ActionMapping mapping, 
                                                       ActionForm form, 
                                                       HttpServletRequest request, 
                                                       HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "showOutstandingDetailsForName", "Entered");

        ArrayList periodicInfoDetails = new ArrayList();
        GMActionForm gmActionForm = (GMActionForm)form;

        GMProcessor gmProcessor = new GMProcessor();
        ApplicationProcessor appProcessor = new ApplicationProcessor();

        String memberId = gmActionForm.getMemberId();
        String cgpan = gmActionForm.getCgpan().toUpperCase();
        String borrowerId = gmActionForm.getBorrowerId().toUpperCase();
        String borrowerName = gmActionForm.getBorrowerName();

        String forward = "";
        String Bid = "";
        String ssiName = "";

        //int type = 2;
         int type = 0;

        String bidName = request.getParameter("bidName");

        if (!bidName.substring(0, 4).equals("null")) {
            Bid = bidName.substring(0, 9);
            ssiName = bidName.substring(10, bidName.length() - 1);

            gmActionForm.setBorrowerId(Bid);
        } else {
            Bid = "";
            ssiName = bidName.substring(5, bidName.length() - 1);

            gmActionForm.setBorrowerId(ssiName);
        }

        int claimCount = appProcessor.getClaimCount(Bid);
        if (claimCount > 0) {
            throw new MessageException("Outstanding Details for this borrower since Claim Application has been submitted");
        }

        Log.log(4, "GMAction", "showOutstandingDetailsForName", 
                "bid from request :" + Bid);

        Log.log(4, "GMAction", "showOutstandingDetailsForName", 
                "ssiName from request :" + ssiName);

        periodicInfoDetails = 
                gmProcessor.viewOutstandingDetails(Bid, type);
        if (periodicInfoDetails.isEmpty()) {
            Log.log(5, "GMAction", "showOutstandingDetailsForName", "No Data");
            throw new NoDataException("There are no Disbursement Details for the combination of inputs");
        }

        gmActionForm.setOsPeriodicInfoDetails(periodicInfoDetails);

        gmActionForm.setBorrowerName("");

        Log.log(4, "GMAction", "showOutstandingDetailsForName", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward showRepaymentDetailsForName(ActionMapping mapping, 
                                                     ActionForm form, 
                                                     HttpServletRequest request, 
                                                     HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "showRepaymentDetailsForName", "Entered");
        ArrayList periodicInfoDetails = new ArrayList();
        GMActionForm gmActionForm = (GMActionForm)form;

        String memberId = gmActionForm.getMemberId();
        String cgpan = gmActionForm.getCgpan().toUpperCase();
        String borrowerId = gmActionForm.getBorrowerId().toUpperCase();
        String borrowerName = gmActionForm.getBorrowerName();
        gmActionForm.getRepaymentAmount().clear();
        gmActionForm.getRepaymentDate().clear();

        GMProcessor gmProcessor = new GMProcessor();
        ApplicationProcessor appProcessor = new ApplicationProcessor();

        //int type = 2;
         int type = 0;
        String Bid = "";
        String ssiName = "";

        String bidName = request.getParameter("bidName");

        if (!bidName.substring(0, 4).equals("null")) {
            Bid = bidName.substring(0, 9);
            ssiName = bidName.substring(10, bidName.length() - 1);

            gmActionForm.setBorrowerId(Bid);
        } else {
            Bid = "";
            ssiName = bidName.substring(5, bidName.length() - 1);

            gmActionForm.setBorrowerId(ssiName);
        }

        int claimCount = appProcessor.getClaimCount(Bid);
        if (claimCount > 0) {
            throw new MessageException("Repayment Details cannot be modified for this borrower since Claim Application has been submitted");
        }

        Log.log(4, "GMAction", "showRepaymentDetailsForName", 
                "bid from request :" + Bid);

        Log.log(4, "GMAction", "showRepaymentDetailsForName", 
                "ssiName from request :" + ssiName);

        periodicInfoDetails = gmProcessor.viewRepaymentDetails(Bid, type);

        if (periodicInfoDetails == null) {
            throw new NoDataException("There are no Repayment Details for this " + 
                                      borrowerName);
        }

        gmActionForm.setRepayPeriodicInfoDetails(periodicInfoDetails);
        gmActionForm.setRepayPeriodicInfoDetailsTemp(periodicInfoDetails);

        gmActionForm.setBorrowerName("");

        Log.log(4, "GMAction", "showRepaymentDetailsForName", "Exited");

        return mapping.findForward("success");
    }

    public ActionForward showRepaymentScheduleForName(ActionMapping mapping, 
                                                      ActionForm form, 
                                                      HttpServletRequest request, 
                                                      HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "showRepaymentScheduleForName", "Entered");
        ArrayList periodicInfoDetails = new ArrayList();
        GMActionForm gmActionForm = (GMActionForm)form;

        HttpSession session = request.getSession(false);

        String forward = "";

        RepaymentSchedule repaymentSchedule = new RepaymentSchedule();
        ArrayList repaymentSchedules = new ArrayList();

        String memberId = gmActionForm.getMemberIdForSchedule();
        session.setAttribute("scheduleMemberId", memberId);
        Log.log(5, "GMAction", "showRepaymentSchedule", 
                "mem id -->" + memberId);

        String cgpan = gmActionForm.getCgpanForSchedule().toUpperCase();
        String borrowerId = 
            gmActionForm.getBorrowerIdForSchedule().toUpperCase();
        String borrowerName = gmActionForm.getBorrowerNameForSchedule();

        GMProcessor gmProcessor = new GMProcessor();
        ApplicationProcessor appProcessor = new ApplicationProcessor();

        int type = 2;
        String Bid = "";
        String ssiName = "";

        String bidName = request.getParameter("bidName");

        if (!bidName.substring(0, 4).equals("null")) {
            Bid = bidName.substring(0, 9);
            ssiName = bidName.substring(10, bidName.length() - 1);

            gmActionForm.setBorrowerId(Bid);
        } else {
            Bid = "";
            ssiName = bidName.substring(5, bidName.length() - 1);

            gmActionForm.setBorrowerId(ssiName);
        }
        int claimCount = appProcessor.getClaimCount(Bid);
        if (claimCount > 0) {
            throw new MessageException("Repayment Schedule Details cannot be modified for this borrower since Claim Application has been submitted");
        }

        Log.log(4, "GMAction", "showRepaymentScheduleForName", 
                "bid from request :" + Bid);

        Log.log(4, "GMAction", "showRepaymentScheduleForName", 
                "ssiName from request :" + ssiName);

        repaymentSchedules = gmProcessor.viewRepaymentSchedule(ssiName, type);

        gmActionForm.setRepaymentSchedules(repaymentSchedules);

        if ((repaymentSchedules == null) || (repaymentSchedules.size() == 0)) {
            throw new NoDataException("There are no Repayment Details for this " + 
                                      borrowerName);
        }

        forward = "forwardPage";

        gmActionForm.setBorrowerName("");

        Log.log(4, "GMAction", "showRepaymentScheduleForName", "Exited");

        return mapping.findForward(forward);
    }

    public ActionForward showClosureDetailsForName(ActionMapping mapping, 
                                                   ActionForm form, 
                                                   HttpServletRequest request, 
                                                   HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "showClosureDetailsForName", "Entered");
        ClosureDetail closureDtl = new ClosureDetail();
        HashMap closureDetails = null;

        GMActionForm gmActionForm = (GMActionForm)form;
        HttpSession session = request.getSession(false);

        String forward = "";

        String cgpan = gmActionForm.getCgpanForClosure().toUpperCase();
        String borrowerId = 
            gmActionForm.getBorrowerIdForClosure().toUpperCase();
        String memberId = gmActionForm.getMemberIdForClosure();

        gmActionForm.setMemberIdForClosure(memberId);
        session.setAttribute("closureMemberId", memberId);
        String borrowerName = gmActionForm.getBorrowerNameForClosure();

        GMProcessor gmProcessor = new GMProcessor();
        ApplicationProcessor appProcessor = new ApplicationProcessor();

        int type = 2;
        String Bid = "";
        String ssiName = "";

        String bidName = request.getParameter("bidName");

        if (!bidName.substring(0, 4).equals("null")) {
            Bid = bidName.substring(0, 9);
            ssiName = bidName.substring(10, bidName.length() - 1);

            gmActionForm.setBorrowerId(Bid);
        } else {
            Bid = "";
            ssiName = bidName.substring(5, bidName.length() - 1);

            gmActionForm.setBorrowerId(ssiName);
        }
        int claimCount = appProcessor.getClaimCount(Bid);
        if (claimCount > 0) {
            throw new MessageException("Application cannot be Closed for this borrower since Claim Application has been submitted");
        }

        Log.log(4, "GMAction", "showClosureDetailsForName", 
                "bid from request :" + Bid);

        Log.log(4, "GMAction", "showClosureDetailsForName", 
                "ssiName from request :" + ssiName);

        closureDetails = 
                gmProcessor.viewClosureDetails(ssiName, type, memberId);

        if (closureDetails.isEmpty()) {
            throw new NoDataException("There are no Closure Details for the Entered ID");
        }

        ArrayList reasons = gmProcessor.getAllReasonsForClosure();
        gmActionForm.setClosureReasons(reasons);
        gmActionForm.setClosureDetails(closureDetails);

        forward = "forwardPage";

        Log.log(4, "GMAction", "showRepaymentScheduleForName", "Exited");

        return mapping.findForward(forward);
    }

    public ActionForward modifyBorrowerDetailsForName(ActionMapping mapping, 
                                                      ActionForm form, 
                                                      HttpServletRequest request, 
                                                      HttpServletResponse response) throws Exception {
        Log.log(4, "GMAction", "ModifyBorrowerDetails", "Entered");

        DynaActionForm dynaActionForm = (DynaActionForm)form;

        GMProcessor gmProcessor = new GMProcessor();
        BorrowerDetails borrowerDetails = null;
        SSIDetails ssiDetails = null;
        Administrator admin = new Administrator();

        ApplicationProcessor appProcessor = new ApplicationProcessor();

        ArrayList states = null;
        ArrayList districts = null;
        String state = "";

        String forward = "";
        String Bid = "";
        String ssiName = "";

        String bidName = request.getParameter("bidName");

        if (!bidName.substring(0, 4).equals("null")) {
            Bid = bidName.substring(0, 9);
            ssiName = bidName.substring(10, bidName.length() - 1);
        } else {
            Bid = "";
            ssiName = bidName.substring(5, bidName.length() - 1);
        }

        int claimCount = appProcessor.getClaimCount(Bid);
        if (claimCount > 0) {
            throw new MessageException("Borrower Details for this borrower cannot be modified since Claim Application has been submitted");
        }

        Log.log(4, "GMAction", "showClosureDetailsForName", 
                "bid from request :" + Bid);

        states = admin.getAllStates();
        dynaActionForm.set("states", states);

        String memberId = 
            ((String)dynaActionForm.get("memberIdForModifyBorrDtl")).toUpperCase();
        if (Bid.equals("")) {
            int type = 2;
            borrowerDetails = 
                    gmProcessor.viewBorrowerDetails(memberId, ssiName, type);
         //   System.out.println("Type =2 "+borrowerDetails);
        } else {
            int type = 0;

            borrowerDetails = 
                    gmProcessor.viewBorrowerDetails(memberId, Bid, type);
         //   System.out.println("Type =0 "+borrowerDetails);
        }

        int borrowerRefNo = borrowerDetails.getSsiDetails().getBorrowerRefNo();

        Integer intRefNo = new Integer(borrowerRefNo);
        dynaActionForm.set("borrowerRefNo", intRefNo);

        ssiDetails = borrowerDetails.getSsiDetails();

        BeanUtils.copyProperties(dynaActionForm, ssiDetails);
        BeanUtils.copyProperties(dynaActionForm, borrowerDetails);

        state = ssiDetails.getState();
        Log.log(5, "GMAction", "modifyBorrowerDetails", "state " + state);

        ArrayList districtList = admin.getAllDistricts(state);
        dynaActionForm.set("districts", districtList);

        String districtName = ssiDetails.getDistrict();
        Log.log(5, "GMAction", "modifyBorrowerDetails", 
                "districtName " + districtName);

        if (districtList.contains(districtName)) {
            Log.log(5, "GMAction", "modifyBorrowerDetails", 
                    "setting in dyna form districtName " + districtName);
            dynaActionForm.set("district", districtName);
        } else {
            Log.log(5, "GMAction", "modifyBorrowerDetails", 
                    "districtName " + districtName);
            dynaActionForm.set("districtOthers", districtName);
            dynaActionForm.set("district", "Others");
        }

        String constitutionVal = ssiDetails.getConstitution();
        if ((!constitutionVal.equals("proprietary")) && 
            (!constitutionVal.equals("partnership")) && 
            (!constitutionVal.equals("private")) && 
            (!constitutionVal.equals("public"))) {
            dynaActionForm.set("constitutionOther", constitutionVal);
            dynaActionForm.set("constitution", "Others");
        } else {
            dynaActionForm.set("constitution", constitutionVal);
        }

        String legalIDString = ssiDetails.getCpLegalID();
        if ((legalIDString != null) && (!legalIDString.equals(""))) {
            if ((!legalIDString.equals("VoterIdentityCard")) && 
                (!legalIDString.equals("RationCardnumber")) && 
                (!legalIDString.equals("PASSPORT")) && 
                (!legalIDString.equals("Driving License"))) {
                dynaActionForm.set("otherCpLegalID", legalIDString);
                dynaActionForm.set("cpLegalID", "Others");
            } else {
                dynaActionForm.set("cpLegalID", legalIDString);
            }
        }

        ArrayList socialList = getSocialCategory();
        dynaActionForm.set("socialCategoryList", socialList);

        ArrayList industryNatureList = admin.getAllIndustryNature();
        dynaActionForm.set("industryNatureList", industryNatureList);

        String industryNature = ssiDetails.getIndustryNature();

        Log.log(4, "GMAction", "ModifyBorrowerDetails", 
                "industry nature :" + industryNature);

        if ((industryNature != null) && (!industryNature.equals("")) && 
            (!industryNature.equals("OTHERS"))) {
            ArrayList industrySectors = 
                admin.getIndustrySectors(industryNature);
            dynaActionForm.set("industrySectors", industrySectors);
        } else {
            ArrayList industrySectors = new ArrayList();
            String industrySector = ssiDetails.getIndustrySector();
            industrySectors.add(industrySector);
            dynaActionForm.set("industrySectors", industrySectors);
        }

        Log.log(4, "GMAction", "ModifyBorrowerDetails", "Exited");

        states = null;
        districts = null;

        return mapping.findForward("success");
    }

    public ActionForward showNpaForName(ActionMapping mapping, ActionForm form, 
                                        HttpServletRequest request, 
                                        HttpServletResponse response) throws Exception {

        GMActionForm gmActionForm = (GMActionForm)form;
        gmActionForm.getRecProcedures().clear();
        HttpSession session = request.getSession(false);

        GMProcessor gmProcessor = new GMProcessor();
        String bid = "";

        String bidName = request.getParameter("bidName");

        if (!bidName.substring(0, 4).equals("null")) {
            bid = bidName.substring(0, 9).toUpperCase();

            gmActionForm.setBorrowerId(bid);
        }

        String memberId = gmActionForm.getMemberId();

        gmActionForm.setBorrowerName("");
        gmActionForm.setMemberId(memberId);
        HashMap inputDetail = new HashMap();
        inputDetail.put("memberId", memberId);
        inputDetail.put("borrowerId", bid);
        //         inputDetail.put("cgpan",cgpan);

        session.setAttribute("inputDetail", inputDetail);

        //  return showNPADetails(mapping, form, request, response);     
        //   return mapping.findForward("success");
        return mapping.findForward("npaPage");
    }

    public ActionForward showRecoveryForName(ActionMapping mapping, 
                                             ActionForm form, 
                                             HttpServletRequest request, 
                                             HttpServletResponse response) throws Exception {
        GMActionForm gmActionForm = (GMActionForm)form;

        HttpSession session = request.getSession(false);

        String forward = "";

        GMProcessor gmProcessor = new GMProcessor();

        String Bid = "";
        String ssiName = "";

        String bidName = request.getParameter("bidName");

        if (!bidName.substring(0, 4).equals("null")) {
            ssiName = bidName.substring(0, 9);

            gmActionForm.setBorrowerId(ssiName);
        }
        gmActionForm.setBorrowerName("");

        gmActionForm.resetWhenRequired(mapping, request);
        Log.log(4, "GMAction", "showRecoveryDetails", "Exited");

        return mapping.findForward("insertRecovery");
    }

    public ActionForward showUpdateRecoveryForName(ActionMapping mapping, 
                                                   ActionForm form, 
                                                   HttpServletRequest request, 
                                                   HttpServletResponse response) throws Exception {
        GMActionForm gmActionForm = (GMActionForm)form;

        HttpSession session = request.getSession(false);

        String forward = "";

        GMProcessor gmProcessor = new GMProcessor();

        String Bid = "";
        String ssiName = "";

        String bidName = request.getParameter("bidName");

        if (!bidName.substring(0, 4).equals("null")) {
            ssiName = bidName.substring(0, 9);

            gmActionForm.setBorrowerId(ssiName);
        }

        ArrayList recoveryDetails = gmProcessor.getRecoveryDetails(ssiName);

        Map recoveryMap = new HashMap();
        String recId = null;
        for (int i = 0; i < recoveryDetails.size(); i++) {
            Recovery reco = (Recovery)recoveryDetails.get(i);
            recId = reco.getRecoveryNo();
            recoveryMap.put(recId, reco);
        }

        gmActionForm.setRecoveryDetails(recoveryMap);

        return mapping.findForward("updateRecovery");
    }

    
    //koteswar start
    public ActionForward NpaDetailsGetBorrowerId1(ActionMapping mapping, 
            ActionForm form, 
            HttpServletRequest request, 
            HttpServletResponse response) throws Exception {
Log.log(4, "GMAction", "NpaDetailsGetBorrowerId", "Entered");

GMActionForm gmActionForm = (GMActionForm)form;
//System.out.println("formType"+request.getParameter("formType"));
request.setAttribute("formType", request.getParameter("formType"));
GMProcessor gmProcessor = new GMProcessor();
User user = getUserInformation(request);
String bankId = user.getBankId();
String zoneId = user.getZoneId();
String branchId = user.getBranchId();
String memberId = bankId.concat(zoneId).concat(branchId);

gmActionForm.setPeriodicBankId(bankId);
if (bankId.equals("0000")) {
memberId = "";
}
gmActionForm.setMemberId(memberId);

gmActionForm.setBorrowerId("");
gmActionForm.setCgpan("");
gmActionForm.setBorrowerName("");

Log.log(4, "GMAction", "NpaDetailsGetBorrowerId", "Exited");

return mapping.findForward("success");
}

    
    
    public ActionForward checkNewNPADtWithExpiryDtMethod(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {		
  	  
  	  NPAForm objNPAForm=(NPAForm)form;
  	 // System.out.println("checkNewNPADtWithExpiryDtMethod called=="+objNPAForm.getStrCgpan());
  	  //System.out.println("checkNewNPADtWithExpiryDtMethod called"+request.getParameter("cgpan"));
  		GMDAO objGMDao = new GMDAO();		
  		String message=objGMDao.getNewNPADtWithExpiryDtMethod(request.getParameter("cgpan"),request.getParameter("newNPADate"));
  		//System.out.println("checkCGPanAgainstMemberID called gm action arrayList"+message);
  		String forward = "success"; 
  		PrintWriter out= response.getWriter();
  		out.print(message);
  		
  	return mapping.findForward("success1");
  }
    
    
    
    public ActionForward checkCGPanAgainstMemberID(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {		
  	 // System.out.println("checkCGPanAgainstMemberID called first");
  	  MLIInfo mliInfo = getMemberInfo(request);
        String bankName = mliInfo.getBankName();
        String bankId = mliInfo.getBankId();
        String branchId = mliInfo.getBranchId();
        String zoneId = mliInfo.getZoneId();
        String memberId = bankId + zoneId + branchId;
        GMActionForm objGMActionForm= (GMActionForm)form;

  	//  System.out.println("checkCGPanAgainstMemberID GMActionForm gm action  called"+objGMActionForm.getCgpan());
  	 // System.out.println("checkCGPanAgainstMemberID GMActionForm  gm action called=="+objGMActionForm.getNpaFormType());
  		GMDAO objGMDao = new GMDAO();		
  		String message=objGMDao.checkCGPANForNPAxxxDetail(objGMActionForm.getCgpan(), memberId,objGMActionForm.getNpaFormType());
  	//	System.out.println("checkCGPanAgainstMemberID called arrayList"+message);
  		String forward = "success"; 
  		PrintWriter out= response.getWriter();
  		out.print(message);
  		
  	//return mapping.findForward("success1");
  		return null;
  }
    
    
	
	public ActionForward showNPADetails2(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// System.out.println("---entered in showNPADetails------");
		Log.log(Log.INFO, "GMAction", "showNPADetails", "Entered");
		Connection connection =null;
try {
		GMActionForm gmActionForm = (GMActionForm) form;
		gmActionForm.getRecProcedures().clear();
		String memberId = gmActionForm.getMemberId();
		String borrowerId = (gmActionForm.getBorrowerId()).toUpperCase();
		String cgpan = (gmActionForm.getCgpan());
		String borrowerName = gmActionForm.getBorrowerName();

		int type;
		
		 Statement stmt = null;
	        ResultSet result1 = null;
	        ResultSet result2 = null;
	        
	        ResultSet result3 = null;
	        
	        ResultSet result4 = null;
	        
	        ResultSet result9 = null;
	        
ResultSet result5 = null;
	        
	        ResultSet result6 = null;
	        
	        ResultSet result7 = null;
	        
	        ResultSet result17 = null;
	        
	        connection = DBConnection.getConnection();

		GMProcessor gmProcessor = new GMProcessor();
		ArrayList borrowerIds = new ArrayList();
		borrowerIds = gmProcessor.getBorrowerIds(memberId);
		ClaimsProcessor processor = new ClaimsProcessor();
		Vector memberids = processor.getAllMemberIds();
		if (!(memberids.contains(memberId))) {
			throw new NoMemberFoundException("Member Id :" + memberId
					+ " does not exist in the database.");
		}

		HttpSession session = request.getSession(false);

		if ((borrowerId != null) && (!borrowerId.equals(""))) {
			if (!(borrowerIds.contains(borrowerId))) {
				gmActionForm.setBorrowerId("");

				throw new NoDataException(borrowerId
						+ " is not among the borrower"
						+ " Ids for the Member Id :" + memberId
						+ ". Please enter correct"
						+ " Member Id and Borrower Id.");
			}

		} else if ((cgpan != null) && (!cgpan.equals(""))) {
			type = 1;
			
			//koteswar start
			
			

            String query5 = 
                " SELECT count(*) from npa_tc_out_stnd_detl_amt_temp where cgpan='"+cgpan+"'  ";
            stmt = connection.createStatement();
            result5 = stmt.executeQuery(query5);
            
           boolean z=result5.next();
            
           int x= result5.getInt(1);
           
           
           String query6 = 
                " SELECT count(*) from npa_wc_out_stnd_detl_amt_temp where cgpan='"+cgpan+"'  ";
            stmt = connection.createStatement();
            result6 = stmt.executeQuery(query6);
            
           boolean p=result6.next();
            
           int o= result6.getInt(1);
        
           if(x>0 || o>0 )
        	   
           {
            	throw new NoDataException(
						"Already  outstanding amount is updated ");
           
            }
           
			
			
			
           String query9="select count(*) from  npa_detail where bid in (select bid from ssi_detail where ssi_reference_number in (select ssi_reference_number from application_detail where cgpan='"+cgpan+"' ))";
           
           stmt = connection.createStatement();
            result9 = stmt.executeQuery(query9);
            
            boolean f=result9.next();
            
           int no= result9.getInt(1);
           
	           
	              
	           
	           String query99="select count(*) from  npa_detail_temp where bid in (select bid from ssi_detail where ssi_reference_number in (select ssi_reference_number from application_detail where cgpan='"+cgpan+"' ))";
	           
	           stmt = connection.createStatement();
	            result9 = stmt.executeQuery(query99);
	            
	            boolean npares=result9.next();
	            
	           int noNpa= result9.getInt(1);
	           
	            
	           
	           if(no < 1 &&  noNpa < 1 )
	        	   
	           {
	            	throw new NoDataException(
							"This case is not marked NPA, can not update outstanding amount ");
	           
	            }
	             
	           
	           
	           
	           
	           
	           String queryClaim = 
	                " select count(*) from claim_detail where bid in (select bid from ssi_detail where ssi_reference_number in (select ssi_reference_number from application_detail where cgpan='"+cgpan+"'))  and clm_status='AP' ";
	            stmt = connection.createStatement();
	            result7 = stmt.executeQuery(queryClaim);
	            
	            boolean clmRes=result7.next();
	            
	           int claimNo= result7.getInt(1);
	           
	           if(claimNo==1)
	        	   
	           {
	            	throw new NoDataException(
							"claim has been already approved, you can not update outstanding amount  ");
	           
	            }
	           
	           
	           
	           
	            
	            
	            
		}
			//koteswar end
		   
			borrowerId = processor.getBorowwerForCGPAN(cgpan);
			Log.log(Log.INFO, "GMAction", "showOutstandingDetails",
					" Bid For Pan - " + borrowerId);
			
		
			  
	           String queryClaim = 
	                " select npa_id from npa_detail_temp   where bid in (select bid from ssi_detail where ssi_reference_number in (select ssi_reference_number from application_detail where cgpan='"+cgpan+"'))   ";
	            stmt = connection.createStatement();
	            result17 = stmt.executeQuery(queryClaim);
	            
	            boolean clmRes=result17.next();
	            
	           String npa_id= result17.getString(1);
	           
	           User user = getUserInformation(request);
	           String userId = user.getUserId();
	           double osAmtOnNPA = gmActionForm.getOsAmtOnNPA();
	           
	           Date toDay=new Date();
	           
	           Date date = Calendar.getInstance().getTime();
				DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
				String todysDate = formatter.format(date);
				String currentDate = DateHelper.stringToDBDate(todysDate);
	           
	          //new java.sql.Date(parsedDate.getTime())
				
				
				if(cgpan.endsWith("TC"))
				{
	           String queryClaim22 = 
	                " insert into NPA_TC_OUT_STND_DETL_AMT_temp (NPA_ID,CGPAN, NTD_NPA_OUT_STND_AMT_CUR_FY,NTD_NPA_OUT_STAND_VALID_DT,USER_ID,CREATE_DATE)  values ('"+npa_id+"','"+cgpan+"','"+osAmtOnNPA+"','"+currentDate+"','"+userId+"','"+currentDate+"') ";
	            stmt = connection.createStatement();
	            result17 = stmt.executeQuery(queryClaim22);
	            
	            boolean clmRes5=result17.next();
				}
	            
	            else
	            {
	            	 String queryClaim22 = 
	 	                " insert into NPA_wC_OUT_STND_DETL_AMT_temp (NPA_ID,CGPAN, NWD_NPA_OUT_STND_AMT_CUR_FY,NWD_NPA_OUT_STAND_VALID_DT,USER_ID,CREATE_DATE)  values ('"+npa_id+"','"+cgpan+"','"+osAmtOnNPA+"','"+currentDate+"','"+userId+"','"+currentDate+"') ";
	 	            stmt = connection.createStatement();
	 	            result17 = stmt.executeQuery(queryClaim22);
	 	            
	 	            boolean clmRes5=result17.next();
	            }
	            
	            connection.commit();
	            
	          
	
	}
	catch(Exception e)
	{
	}
	 finally {
			DBConnection.freeConnection(connection);

		}
			
			
			

		return mapping.findForward(Constants.SUCCESS);
	}

	
	
	
	
	
	
	
	//kot2
	  public ActionForward NpaDetailsGetBorrowerId2(ActionMapping mapping, 
	            ActionForm form, 
	            HttpServletRequest request, 
	            HttpServletResponse response) throws Exception {
	Log.log(4, "GMAction", "NpaDetailsGetBorrowerId", "Entered");

	GMActionForm gmActionForm = (GMActionForm)form;

	GMProcessor gmProcessor = new GMProcessor();
	User user = getUserInformation(request);
	String bankId = user.getBankId();
	String zoneId = user.getZoneId();
	String branchId = user.getBranchId();
	String memberId = bankId.concat(zoneId).concat(branchId);

	gmActionForm.setPeriodicBankId(bankId);
	if (bankId.equals("0000")) {
	memberId = "";
	}
	gmActionForm.setMemberId(memberId);

	gmActionForm.setBorrowerId("");
	gmActionForm.setCgpan("");
	gmActionForm.setBorrowerName("");

	Log.log(4, "GMAction", "NpaDetailsGetBorrowerId", "Exited");

	return mapping.findForward("success");
	}

	    //koteswar end




		//koteswar start
		
		public ActionForward showNPADetails1(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			// System.out.println("---entered in showNPADetails------");
			Log.log(Log.INFO, "GMAction", "showNPADetails", "Entered");

			GMActionForm gmActionForm = (GMActionForm) form;
			gmActionForm.getRecProcedures().clear();
			String memberId = gmActionForm.getMemberId();
			String borrowerId = (gmActionForm.getBorrowerId()).toUpperCase();
			String cgpan = (gmActionForm.getCgpan());
			String borrowerName = gmActionForm.getBorrowerName();

			int type;
			
			 Statement stmt = null;
		        ResultSet result1 = null;
		        ResultSet result2 = null;
		        
		        ResultSet result3 = null;
		        
		        ResultSet result4 = null;
		        
		        ResultSet result9 = null;
		        
	ResultSet result5 = null;
		        
		        ResultSet result6 = null;
		        
		        ResultSet result7 = null;
		        
		        
		        
		        Connection connection = DBConnection.getConnection();

			GMProcessor gmProcessor = new GMProcessor();
			ArrayList borrowerIds = new ArrayList();
			borrowerIds = gmProcessor.getBorrowerIds(memberId);
			ClaimsProcessor processor = new ClaimsProcessor();
			Vector memberids = processor.getAllMemberIds();
			if (!(memberids.contains(memberId))) {
				throw new NoMemberFoundException("Member Id :" + memberId
						+ " does not exist in the database.");
			}

			HttpSession session = request.getSession(false);

			if ((borrowerId != null) && (!borrowerId.equals(""))) {
				if (!(borrowerIds.contains(borrowerId))) {
					gmActionForm.setBorrowerId("");

					throw new NoDataException(borrowerId
							+ " is not among the borrower"
							+ " Ids for the Member Id :" + memberId
							+ ". Please enter correct"
							+ " Member Id and Borrower Id.");
				}

			} else if ((cgpan != null) && (!cgpan.equals(""))) {
				type = 1;
				
				//koteswar start
				
				

	            String query5 = 
	                " SELECT count(*) from npa_tc_out_stnd_detl_amt_temp where cgpan='"+cgpan+"'  ";
	            stmt = connection.createStatement();
	            result5 = stmt.executeQuery(query5);
	            
	           boolean z=result5.next();
	            
	           int x= result5.getInt(1);
	           
	           
	           String query6 = 
	                " SELECT count(*) from npa_wc_out_stnd_detl_amt_temp where cgpan='"+cgpan+"'  ";
	            stmt = connection.createStatement();
	            result6 = stmt.executeQuery(query6);
	            
	           boolean p=result6.next();
	            
	           int o= result6.getInt(1);
	        
	           if(x>0 || o>0 )
	        	   
	           {
	            	throw new NoDataException(
							"Already  outstanding amount is updated ");
	           
	            }
	           
				
				
				
			    
		            String query1 = 
		                " SELECT count(*) from npa_tc_detail_temp where cgpan='"+cgpan+"'  ";
		            stmt = connection.createStatement();
		            result1 = stmt.executeQuery(query1);
		            
		           boolean u=result1.next();
		            
		           int i= result1.getInt(1);
		           
		           
		           String query2 = 
		                " SELECT count(*) from npa_tc_detail where cgpan='"+cgpan+"'  ";
		            stmt = connection.createStatement();
		            result2 = stmt.executeQuery(query2);
		            
		           boolean v=result2.next();
		            
		           int j= result2.getInt(1);
		           
		           
		           String query3 = 
		                " SELECT count(*) from npa_wc_detail_temp where cgpan='"+cgpan+"'  ";
		            stmt = connection.createStatement();
		            result3 = stmt.executeQuery(query3);
		            
		            boolean g=result3.next();
		            
		           int k= result3.getInt(1);
		           
		           
		           String query4 = 
		                " SELECT count(*) from npa_wc_detail where cgpan='"+cgpan+"'  ";
		            stmt = connection.createStatement();
		            result4 = stmt.executeQuery(query4);
		            
		            boolean h=result4.next();
		            
		           int m= result4.getInt(1);
		           
		           
		              
		           
		           String query9="select count(*) from  npa_detail_temp where bid in (select bid from ssi_detail where ssi_reference_number in (select ssi_reference_number from application_detail where cgpan='"+cgpan+"' ))";
		           
		           stmt = connection.createStatement();
		            result9 = stmt.executeQuery(query9);
		            
		            boolean f=result9.next();
		            
		           int no= result9.getInt(1);
		           
		            
		           
		           if(i==0 && j==0  && k==0 && m==0 && no<1)
		        	   
		           {
		            	throw new NoDataException(
								"This case is not marked NPA, can not update outstanding amount ");
		           
		            }
		             
		           
		           
		           
		           
		           
		           String queryClaim = 
		                " select count(*) from claim_detail where bid in (select bid from ssi_detail where ssi_reference_number in (select ssi_reference_number from application_detail where cgpan='"+cgpan+"'))  and clm_status='AP' ";
		            stmt = connection.createStatement();
		            result7 = stmt.executeQuery(queryClaim);
		            
		            boolean clmRes=result7.next();
		            
		           int claimNo= result4.getInt(1);
		           
		           if(claimNo>0)
		        	   
		           {
		            	throw new NoDataException(
								"claim has been already approved, you can not update outstanding amount  ");
		           
		            }
		           
		           
		           
		           
		            
		            
		            
			}
				//koteswar end
			   
				borrowerId = processor.getBorowwerForCGPAN(cgpan);
				Log.log(Log.INFO, "GMAction", "showOutstandingDetails",
						" Bid For Pan - " + borrowerId);
				
			
			HashMap inputDetail = new HashMap();
			inputDetail.put("memberId", memberId);
			inputDetail.put("borrowerId", borrowerId);
			// inputDetail.put("cgpan",cgpan);
			session.setAttribute("inputDetail", inputDetail);

			return mapping.findForward(Constants.SUCCESS);
		}


	//kote end
	
	
	public ActionForward saveNpaDetails1(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		GMProcessor gmProcessor = new GMProcessor();
		HttpSession session = (HttpSession) request.getSession(false);

		NPADetails npaDetail = new NPADetails();

		
		DynaValidatorActionForm dynaActionForm = (DynaValidatorActionForm) form;
		
		/*String cgpan = (String) dynaActionForm.get("cgpan");
		Date npadate = (java.util.Date) dynaActionForm.get("npaDt"); 
		Date revivaldate=null;

		Connection connection = DBConnection.getConnection();
		Statement str1 = connection.createStatement();
		String query = " select nvl(REVIVAL_DATE ,'01-jan-2000')   from revival_cgpan_data where cgpan ='"+cgpan+"'";
	

		ResultSet rs = str1.executeQuery(query);
		

		while (rs.next()) {

			 revivaldate = rs.getDate(1);
			System.out.println("=npaOutStandingAmount IS====="	+ revivaldate);

		}
	
		 System.out.println("Date1 is before Date2"+revivaldate);
		 System.out.println("Date1 is before Date2"+npadate);

	        if (npadate.compareTo(revivaldate) > 0) {
	        	throw new NoDataException("No Data is available for the values entered," + 	" Please Enter Any Other Value ");
	        }
	        
	        
	        else if (npadate.compareTo(revivaldate) < 0) {
	            System.out.println("Date1 is before Date2");
	        } else if (npadate.compareTo(revivaldate) == 0) {
	            System.out.println("Date1 is equal to Date2");
	        }
	    	Calendar cal = Calendar.getInstance();
			cal.setTime(npadate);
			int npayear = cal.get(1);
			int npamonth =cal.get(2);
			int ndate = cal.get(3);
			 System.out.println("NPA YEAR"+npayear+"NPA MONTH"+npamonth+"NPA DATE"+ndate);
			 
			
			Calendar cal1 = Calendar.getInstance();
			cal1.setTime(revivaldate);
			int revyear = cal1.get(1);
			int revmonth =cal1.get(2);
			int revdate= cal1.get(3);
			System.out.println("REL YEAR"+revyear+"REL MONTH"+revmonth+"REV DATE"+revdate);
			
			if (npayear >= revyear &&  npamonth>=revmonth &&  ndate>=revdate) {
				throw new NoDataException("Your Unable to mark NPA.Please check Revival date. ");
				
			}
			
		*/

		String message = "NPA Details Saved Successfully.";
		// Integer size = (Integer)request.getAttribute("size");
		Integer size = (Integer) dynaActionForm.get("size");
		int total = size.intValue();

		String borrowerId = (String) dynaActionForm.get("borrowerId"); // ------------------------------borrower
																		// id
		npaDetail.setCgbid(borrowerId);
		Log.log(Log.INFO, "GMAction", "saveNpaDetails", "bid-" + borrowerId);

		String unitName = (String) dynaActionForm.get("unitName");

		/* @@@@@@@@@@@@@ CGPAN DETAILS @@@@@@@@@ */
		User user = getUserInformation(request);
        String userId = user.getUserId();
		
		String cgpan = null;
		String guarStartDt = null;
		String sanctionDt = null;
		String firstDisbDt = null;
		String lastDisbDt = null;
		String firstInstDt = null;
		String moratoriumPrincipal = null;
		String moratoriumInterest = null;

		String totalDisbAmt = null;
		String repayPrincipal = null;
		String repayInterest = null;
		String outstandingPrincipal = null;
		String outstandingInterest = null;
		String approvedAmount = null;

		// koteswar start
		String outStandingAmtAsonForCurTCFy = null;
		String outStandingAmtAsonForCurWCFy = null;
		// koteswar end
		Map tcMap = null;
		Map wcMap = null;
		Vector tcVector = new Vector();
		Vector wcVector = new Vector();

		for (int i = 1; i <= total; i++) {

			cgpan = "cgpan" + i;
			// guarStartDt = "guarStartDt"+i;
			// sanctionDt = "sanctionDt"+i;
			//String cgpanNo = (String) dynaActionForm.get(cgpan);
			outstandingPrincipal = "outstandingPrincipal" + i;
			outstandingInterest = "outstandingInterest" + i;
			outStandingAmtAsonForCurTCFy = "outStandingAmtAsonForCurTCFy" + i;
			outStandingAmtAsonForCurWCFy = "outStandingAmtAsonForCurWCFy" + i;
			// approvedAmount = "approvedAmount"+i;
			
			String cgpanNo = (String) dynaActionForm.get(cgpan);
			String loanType = cgpanNo.substring(cgpanNo.length() - 2);

			if ("TC".equals(loanType)) {

				firstDisbDt = "firstDisbDt" + i;
				lastDisbDt = "lastDisbDt" + i;
				firstInstDt = "firstInstDt" + i;
				moratoriumPrincipal = "moratoriumPrincipal" + i;
				moratoriumInterest = "moratoriumInterest" + i;

				totalDisbAmt = "totalDisbAmt" + i;
				repayPrincipal = "repayPrincipal" + i;
				repayInterest = "repayInterest" + i;
				tcMap = new HashMap();
				tcMap.put("CGPAN", cgpanNo);
				  tcMap.put("FIRST_DISB_DT", dynaActionForm.get(firstDisbDt));
	                tcMap.put("LAST_DISB_DT", dynaActionForm.get(lastDisbDt));
	                tcMap.put("FIRST_INST_DT", dynaActionForm.get(firstInstDt));
	                tcMap.put("PRINCIPAL_MORATORIUM", dynaActionForm.get(moratoriumPrincipal));
	                tcMap.put("INTEREST_MORATORIUM", dynaActionForm.get(moratoriumInterest));
	                tcMap.put("TOTAL_DISB_AMT", dynaActionForm.get(totalDisbAmt));
	                tcMap.put("PRINCIPAL_REPAY", dynaActionForm.get(repayPrincipal));
	                tcMap.put("INTEREST_REPAY", dynaActionForm.get(repayInterest));
	                tcMap.put("PRINCIPAL_OS", dynaActionForm.get(outstandingPrincipal));
	                tcMap.put("INTEREST_OS", dynaActionForm.get(outstandingInterest));
	                tcMap.put("OUTSTAND_FOR_TC_CUR_FY", dynaActionForm.get(outStandingAmtAsonForCurTCFy));
				// koteswar start
				tcMap.put("OUTSTAND_FOR_TC_CUR_FY",
						dynaActionForm.get(outStandingAmtAsonForCurTCFy));
				// koteswar end
				tcVector.add(tcMap);
			} else {
				 wcMap = new HashMap();
	                wcMap.put("CGPAN", cgpanNo);
	                wcMap.put("PRINCIPAL_OS", dynaActionForm.get(outstandingPrincipal));
	                wcMap.put("INTEREST_OS", dynaActionForm.get(outstandingInterest));
	                wcMap.put("OUTSTAND_FOR_WC_CUR_FY", dynaActionForm.get(outStandingAmtAsonForCurWCFy));
	                wcVector.add(wcMap);
			}
		}

		/* @@@@@@@@@@@@@ NPA DETAILS @@@@@@@@@@@ */

		Date npaTurnDate = (java.util.Date) dynaActionForm.get("npaDt"); // ------------------------------------------------npa
																			// date
		npaDetail.setNpaDate(npaTurnDate);

		String isAsPerRBI = (String) dynaActionForm.get("isAsPerRBI");
		npaDetail.setIsAsPerRBI(isAsPerRBI);

		String npaConfirm = (String) dynaActionForm.get("npaConfirm");
		npaDetail.setNpaConfirm(npaConfirm);

		String npaReason = (String) dynaActionForm.get("npaReason"); // ----------------------------------------------npa
																		// reason
		npaDetail.setNpaReason(npaReason);

		String effortsTaken = (String) dynaActionForm.get("effortsTaken"); // ------------------------------------------efforts
																			// taken
		npaDetail.setEffortsTaken(effortsTaken);

		String isAcctReconstructed = (String) dynaActionForm
				.get("isAcctReconstructed");
		npaDetail.setIsAcctReconstructed(isAcctReconstructed);

		String subsidyFlag = "";
		String isSubsidyRcvd = "";
		String isSubsidyAdjusted = "";
		Date subLastRcvdDt = null;
		Double subLastRcvdAmt = 0.0;

		subsidyFlag = (String) dynaActionForm.get("subsidyFlag");
		if (!GenericValidator.isBlankOrNull(subsidyFlag)
				&& "Y".equals(subsidyFlag)) {
			isSubsidyRcvd = (String) dynaActionForm.get("isSubsidyRcvd");
			if (!GenericValidator.isBlankOrNull(isSubsidyRcvd)
					&& "Y".equals(isSubsidyRcvd)) {
				isSubsidyAdjusted = (String) dynaActionForm
						.get("isSubsidyAdjusted");
				if (!GenericValidator.isBlankOrNull(isSubsidyAdjusted)
						&& "Y".equals(isSubsidyAdjusted)) {
					subLastRcvdDt = (java.util.Date) dynaActionForm
							.get("subsidyLastRcvdDt");
					subLastRcvdAmt = (java.lang.Double) dynaActionForm
							.get("subsidyLastRcvdAmt");
				}
			}

		}

		npaDetail.setSubsidyFlag(subsidyFlag);
		npaDetail.setIsSubsidyRcvd(isSubsidyRcvd);
		npaDetail.setIsSubsidyAdjusted(isSubsidyAdjusted);
		npaDetail.setSubsidyLastRcvdAmt(subLastRcvdAmt);
		npaDetail.setSubsidyLastRcvdDt(subLastRcvdDt);

		Date lastinspectionDt = (java.util.Date) dynaActionForm
				.get("lastInspectionDt");
		npaDetail.setLastInspectionDt(lastinspectionDt);

		/* @@@@@@@@@ SECURITY DETAIL @@@@@@@@@ */
		Map securityMap = new HashMap();
		Map securityAsOnSancDt = null;
		Map securityAsOnNpaDt = null;
		Double networthAsOnSancDt = 0.0;
		Double networthAsOnNpaDt = 0.0;
		String reasonForReductionAsOnNpaDt = "";

		securityAsOnSancDt = (java.util.Map) dynaActionForm
				.get("securityAsOnSancDt");
		securityAsOnNpaDt = (java.util.Map) dynaActionForm
				.get("securityAsOnNpaDt");
		networthAsOnSancDt = (java.lang.Double) dynaActionForm
				.get("networthAsOnSancDt");
		networthAsOnNpaDt = (java.lang.Double) dynaActionForm
				.get("networthAsOnNpaDt");
		reasonForReductionAsOnNpaDt = (String) dynaActionForm
				.get("reasonForReductionAsOnNpaDt");

		securityMap.put("securityAsOnSancDt", securityAsOnSancDt);
		securityMap.put("securityAsOnNpaDt", securityAsOnNpaDt);
		securityMap.put("networthAsOnSancDt", networthAsOnSancDt);
		securityMap.put("networthAsOnNpaDt", networthAsOnNpaDt);
		securityMap.put("reasonForReductionAsOnNpaDt",
				reasonForReductionAsOnNpaDt);

		NPADetails npaDetailsFromDB = gmProcessor.getNPADetails(borrowerId);
		String npaId = null;

		if (npaDetailsFromDB != null) {
			npaId = npaDetailsFromDB.getNpaId();
			dynaActionForm.set("npaId", npaId);
			npaDetail.setNpaId(npaId);
			
			
			Log.log(Log.INFO, "GMAction", "saveNpaDetails",
			"Modified NPA Details is Saved");
	gmProcessor.updateNPADetailsnew(userId,npaDetail, tcVector, wcVector,
			securityMap);
		}

		 
			
		

		request.setAttribute("message", message);

		// dynaActionForm.set("memberId", "");
		// dynaActionForm.set("borrowerId", "");
		// dynaActionForm.set("borrowerName", "");
		// dynaActionForm.set("cgpan", "");
		// session.removeAttribute("newNpaForm");
		Log.log(Log.INFO, "GMAction", "saveNpaDetails", "Exited");
		return mapping.findForward(Constants.SUCCESS);

	}

	// koteswar end

	
	// koteswar start
	public ActionForward showNpaDetailsNew1(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// System.out.println("---entered in showNPADetailsNew------");
		Log.log(Log.INFO, "GMAction", "showNPADetails", "Entered");

		// System.out.println("taking npa details");
		HttpSession session = request.getSession(false);
		if (session == null) {
			throw new MessageException("Please re-login and try again.");
		}
		DynaValidatorActionForm gmActionForm = (DynaValidatorActionForm) form;
		gmActionForm.initialize(mapping);
		// gmActionForm.reset(mapping,request);

		// System.out.println("npaDt"+gmActionForm.get("npaDt")+"  isAsPerRBI"+gmActionForm.get("isAsPerRBI")+"  npaConfirm"+gmActionForm.get("npaConfirm")+"  isAcctReconstructed"+gmActionForm.get("isAcctReconstructed"));

		/* clearing npa form */
		gmActionForm.set("borrowerId", "");
		gmActionForm.set("borrowerName", "");
		gmActionForm.set("operationType", "");
		gmActionForm.set("size", 0);
		gmActionForm.set("totalApprovedAmount", 0.0);
		gmActionForm.set("totalSecurityAsOnSanc", 0.0);
		gmActionForm.set("totalSecurityAsOnNpa", 0.0);
		gmActionForm.set("npaId", "");
		gmActionForm.set("npaDt", null);
		gmActionForm.set("isAsPerRBI", "");
		gmActionForm.set("npaConfirm", "");
		gmActionForm.set("npaReason", "");
		gmActionForm.set("effortsTaken", "");
		gmActionForm.set("isAcctReconstructed", "");
		// gmActionForm.set("subsidyFlag", "");
		// gmActionForm.set("isSubsidyRcvd", "");
		// gmActionForm.set("isSubsidyAdjusted", "");
		// gmActionForm.set("subsidyLastRcvdAmt", 0.0);
		// gmActionForm.set("subsidyLastRcvdDt", null);
		gmActionForm.set("lastInspectionDt", null);
		gmActionForm.set("securityAsOnSancDt", null);
		gmActionForm.set("securityAsOnNpaDt", null);
		gmActionForm.set("networthAsOnSancDt", 0.0);
		gmActionForm.set("networthAsOnNpaDt", 0.0);
		gmActionForm.set("reasonForReductionAsOnNpaDt", "");
		gmActionForm.set("cgpansVector", null);

		// System.out.println("npaDt"+gmActionForm.get("npaDt")+"  isAsPerRBI"+gmActionForm.get("isAsPerRBI")+"  npaConfirm"+gmActionForm.get("npaConfirm")+"  isAcctReconstructed"+gmActionForm.get("isAcctReconstructed"));

		HashMap inputDetail = (HashMap) session.getAttribute("inputDetail");

		String memberId = (String) inputDetail.get("memberId");
		String borrowerId = (String) inputDetail.get("borrowerId");
		// String cgpan = (String)inputDetail.get("cgpan");

		session.removeAttribute("inputDetail");

		gmActionForm.set("memberId", memberId);
		gmActionForm.set("borrowerId", borrowerId);
		// gmActionForm.set("cgpan",cgpan);

		Connection conn = null;
		Statement stmt = null;
		ResultSet rs = null;
		String unitName = null;
		int noOfClaims = 0;
		String query = null;
		String status = null;
		conn = DBConnection.getConnection();

		query = "select ssi_unit_name from ssi_detail where bid='" + borrowerId
				+ "'";

		// }else if(cgpan != null || cgpan != ""){
		// query =
		// "select ssi_unit_name from ssi_detail where ssi_reference_number=(select ssi_reference_number from application_detail where cgpan='"
		// + cgpan + "')";
		// }

		try {
			if (conn != null) {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(query);
			}

			if (rs.next()) {
				unitName = rs.getString("ssi_unit_name");
			}
			rs.close();
			rs = null;
			stmt.close();
			stmt = null;

			// query =
			// "select sum(cnt) totcnt from\n" + "(\n" +
			// "select count(*) cnt from claim_detail_temp where bid ='" +
			// borrowerId + "'\n" + "union all\n" +
			// "select count(*) cnt from claim_detail where bid ='" +
			// borrowerId + "')";
			//
			// if (conn != null) {
			// stmt = conn.createStatement();
			// rs = stmt.executeQuery(query);
			// }
			// if (rs.next()) {
			// noOfClaims = rs.getInt("totcnt");
			// }
			// rs.close();
			// rs = null;
			// stmt.close();
			// stmt = null;
			query = " select CLM_status from claim_detail_temp where bid ='"
					+ borrowerId + "' ";
			if (conn != null) {
				stmt = conn.createStatement();
				rs = stmt.executeQuery(query);
			}

			if (rs.next()) {
				status = rs.getString(1);
			}
			rs.close();
			rs = null;
			stmt.close();
			stmt = null;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DBConnection.freeConnection(conn);
		}
		gmActionForm.set("unitName", unitName);
		ApplicationProcessor proc = new ApplicationProcessor();
		noOfClaims = proc.getClaimCount(borrowerId);
		if (noOfClaims > 0 && !("TC".equals(status))) {
			throw new MessageException(
					"Npa details can not be updated because claim application has already been filed.");
		}

		GMProcessor gmProcessor = new GMProcessor();

		NPADetails npaDetails = gmProcessor.getNPADetails(borrowerId);

		GMDAO dao = new GMDAO();
		Vector cgpans = new Vector();
		Vector tccgpans = new Vector();
		Vector wccgpans = new Vector();
		// if(npaDetails == null){
		Vector cgpnDetails = dao.getCGPANDetailsPeriodicInfo(borrowerId,
				memberId); // here
							// guaranteestartdate,sanctiondate,approved_amount,loanType,appStatus

		if (cgpnDetails != null) {
			if (cgpnDetails.size() == 0) {
				throw new NoDataException(
						"There are no Loan Account(s) for this Borrower or the existing Loan Account(s) may have been closed.");
			}
		} else {
			throw new NoDataException(
					"There are no Loan Account(s) for this Borrower or the existing Loan Account(s) may have been closed.");
		}

		HashMap hashmap = null;
		// ApplicationDAO appDao = new ApplicationDAO();
		Administrator admin = new Administrator();
		ParameterMaster pm = (ParameterMaster) admin.getParameter();
		// int periodTenureExpiryLodgementClaims =
		// pm.getPeriodTenureExpiryLodgementClaims();
		// java.util.Date currentDate = new java.util.Date();
		double totalApprovedAmount = 0.0;

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		String guardatestr = null;
		String sanctiondtstr = null;
		for (int i = 0; i < cgpnDetails.size(); i++) {
			hashmap = (HashMap) cgpnDetails.elementAt(i);

			if (hashmap != null) {

				String cgpanNO = (String) hashmap.get("CGPAN");
				String cgpanStatus = (String) hashmap.get("APPLICATION_STATUS");
				String loanType = (String) hashmap.get("CGPAN_LOAN_TYPE");
				java.util.Date guarStartDt = (java.util.Date) hashmap
						.get("GUARANTEE_START_DT"); // -------------guar date
				java.util.Date sanctionDt = (java.util.Date) hashmap
						.get("SANCTION_DT");
				Double approvedAmount = (Double) hashmap.get("APPROVED_AMOUNT");
				Double rate = (Double) hashmap.get("RATE");
				double r = 0.0;
				double appAmt = 0.0;
				if (approvedAmount != null || !approvedAmount.equals("")) {
					appAmt = approvedAmount.doubleValue();
				}
				totalApprovedAmount = totalApprovedAmount + appAmt;

				if (rate != null || !("".equals(rate))) {
					r = rate.doubleValue();
				}

				if (guarStartDt != null) {
					guardatestr = sdf.format(guarStartDt);
				}

				if (sanctionDt != null) {
					sanctiondtstr = sdf.format(sanctionDt);
				}

				if ((cgpanNO != null) && (!(cgpanNO.equals("")))) {
					HashMap map = new HashMap();
					map.put("CGPAN", cgpanNO);
					map.put("CGPAN_LOAN_TYPE", loanType);
					map.put("GUARANTEE_START_DT", guardatestr);
					map.put("SANCTION_DT", sanctiondtstr);
					map.put("APPROVED_AMOUNT", appAmt);
					map.put("RATE", r);

					if (!cgpans.contains(cgpanNO)) {
						cgpans.addElement(map);
					}
				}

			}
		}
		// System.out.println("totalApprovedAmount:"+totalApprovedAmount);
		// System.out.println("size:"+cgpans.size());

		gmActionForm.set("cgpansVector", cgpans);
		gmActionForm.set("totalApprovedAmount", totalApprovedAmount);
		gmActionForm.set("size", cgpans.size());

		String totalLandValueStr = "";
		Double totalLandValDouble = 0.0;
		String totalMachineValueStr = "";
		Double totalMachineValDouble = 0.0;
		String totalBldgValueStr = "";
		Double totalBldgValueDouble = 0.0;
		String totalOFMAValueStr = "";
		Double totalOFMAValDouble = 0.0;
		String totalCurrAssetsValueStr = "";
		Double totalCurrAssetsValDouble = 0.0;
		String totalOthersValueStr = "";
		Double totalOthersValDouble = 0.0;
		HashMap hashmap2 = null;
		Vector securityVector = new Vector();
		Map securityMap = new HashMap();
		Map securitymapnpa = new HashMap();
		double networthAsOnSancDt = 0.0;
		Double totalNetWorthDouble = 0.0;
		double networthAsOnNpaDt = 0.0;

		double totalSecurityAsOnSanc = 0.0;
		double totalSecurityAsOnNpa = 0.0;

		Map securitydetails = null;

		if (npaDetails == null) {
			// securitydetails = dao.getPrimarySecurity(borrowerId,memberId);
			securitydetails = dao
					.getPrimarySecurityAndNetworthOfGuarantorsAsOnSanc(
							borrowerId, memberId);

			if (securitydetails != null) {

				totalNetWorthDouble = (Double) securitydetails.get("networth");
				if (totalNetWorthDouble != null) {
					networthAsOnSancDt = totalNetWorthDouble.doubleValue();
				}

				totalLandValDouble = (Double) securitydetails.get("land");
				if (totalLandValDouble != null) {
					if (totalLandValDouble.doubleValue() > 0.0) {
						totalLandValueStr = String.valueOf(totalLandValDouble);
						totalSecurityAsOnSanc = totalSecurityAsOnSanc
								+ totalLandValDouble.doubleValue();
					} else {
						totalLandValueStr = "";
					}
				}

				totalMachineValDouble = (Double) securitydetails.get("machine");
				if (totalMachineValDouble != null) {
					if (totalMachineValDouble.doubleValue() > 0.0) {
						totalMachineValueStr = String
								.valueOf(totalMachineValDouble);
						totalSecurityAsOnSanc = totalSecurityAsOnSanc
								+ totalMachineValDouble.doubleValue();
					} else {
						totalMachineValueStr = "";
					}
				}
				totalBldgValueDouble = (Double) securitydetails.get("building");
				if (totalBldgValueDouble != null) {
					if (totalBldgValueDouble.doubleValue() > 0.0) {
						totalBldgValueStr = String
								.valueOf(totalBldgValueDouble);
						totalSecurityAsOnSanc = totalSecurityAsOnSanc
								+ totalBldgValueDouble.doubleValue();
					} else {
						totalBldgValueStr = "";
					}
				}
				totalOFMAValDouble = (Double) securitydetails
						.get("fixed_mov_asset");
				if (totalOFMAValDouble != null) {
					if (totalOFMAValDouble.doubleValue() > 0.0) {
						totalOFMAValueStr = String.valueOf(totalOFMAValDouble);
						totalSecurityAsOnSanc = totalSecurityAsOnSanc
								+ totalOFMAValDouble.doubleValue();
					} else {
						totalOFMAValueStr = "";
					}
				}
				totalCurrAssetsValDouble = (Double) securitydetails
						.get("current_asset");
				if (totalCurrAssetsValDouble != null) {
					if (totalCurrAssetsValDouble.doubleValue() > 0.0) {
						totalCurrAssetsValueStr = String
								.valueOf(totalCurrAssetsValDouble);
						totalSecurityAsOnSanc = totalSecurityAsOnSanc
								+ totalCurrAssetsValDouble.doubleValue();
					} else {
						totalCurrAssetsValueStr = "";
					}
				}
				totalOthersValDouble = (Double) securitydetails.get("others");
				if (totalOthersValDouble != null) {
					if (totalOthersValDouble.doubleValue() > 0.0) {
						totalOthersValueStr = String
								.valueOf(totalOthersValDouble);
						totalSecurityAsOnSanc = totalSecurityAsOnSanc
								+ totalOthersValDouble.doubleValue();
					} else {
						totalOthersValueStr = "";
					}
				}
			}
			securityMap.put("LAND", totalLandValueStr);
			securityMap.put("MACHINE", totalMachineValueStr);
			securityMap.put("BUILDING", totalBldgValueStr);
			securityMap.put("OTHER_FIXED_MOVABLE_ASSETS", totalOFMAValueStr);
			securityMap.put("CUR_ASSETS", totalCurrAssetsValueStr);
			securityMap.put("OTHERS", totalOthersValueStr);

			securitymapnpa.put("LAND", "");
			securitymapnpa.put("MACHINE", "");
			securitymapnpa.put("BUILDING", "");
			securitymapnpa.put("OTHER_FIXED_MOVABLE_ASSETS", "");
			securitymapnpa.put("CUR_ASSETS", "");
			securitymapnpa.put("OTHERS", "");

			gmActionForm.set("securityAsOnSancDt", securityMap);
			gmActionForm.set("networthAsOnSancDt", networthAsOnSancDt);
			gmActionForm.set("totalSecurityAsOnSanc", totalSecurityAsOnSanc);

			gmActionForm.set("securityAsOnNpaDt", securitymapnpa);
			gmActionForm.set("networthAsOnNpaDt", 0.0);
			gmActionForm.set("totalSecurityAsOnNpa", 0.0);

		}

		if (npaDetails == null) {
			Log.log(Log.INFO, "GMAction", "showNPADetails",
					"Npa Details is Null");

			// gmActionForm.getRecProcedures().put("key-0",new
			// RecoveryProcedureTemp());

			// gmActionForm.resetNpaDetailsPage(mapping,request);
			session.setAttribute("npaAvailable", null);

			session.setAttribute("recInitiated", "N");
		}

		if (npaDetails != null) {
			String operationType = "NCU";
			Date npaCreatedDate = npaDetails.getNpaCreatedDate();
			if (npaCreatedDate == null) {
				operationType = "OCU";
			}
			gmActionForm.set("npaCreatedDate", npaCreatedDate);

			gmActionForm.set("operationType", operationType);
			String npaId = npaDetails.getNpaId();

			session.setAttribute("npaAvailable", npaId);
			CustomisedDate custom = new CustomisedDate();
			custom.setDate(npaDetails.getNpaDate());

			gmActionForm.set("npaId", npaId);
			gmActionForm.set("npaDt", custom);
			gmActionForm.set("isAsPerRBI", npaDetails.getIsAsPerRBI());
			gmActionForm.set("npaConfirm", npaDetails.getNpaConfirm());
			gmActionForm.set("npaReason", npaDetails.getNpaReason());
			gmActionForm.set("effortsTaken", npaDetails.getEffortsTaken());
			gmActionForm.set("isAcctReconstructed",
					npaDetails.getIsAcctReconstructed());
			gmActionForm.set("subsidyFlag", npaDetails.getSubsidyFlag());
			gmActionForm.set("isSubsidyRcvd", npaDetails.getIsSubsidyRcvd());
			gmActionForm.set("isSubsidyAdjusted",
					npaDetails.getIsSubsidyAdjusted());
			gmActionForm.set("subsidyLastRcvdAmt",
					npaDetails.getSubsidyLastRcvdAmt());
			custom = new CustomisedDate();
			custom.setDate(npaDetails.getSubsidyLastRcvdDt());
			gmActionForm.set("subsidyLastRcvdDt", custom);
			custom = new CustomisedDate();
			custom.setDate(npaDetails.getLastInspectionDt());
			gmActionForm.set("lastInspectionDt", custom);

			Vector allCgpans = null;

			allCgpans = dao.getCgpanDetailsAsOnNpa(npaId);

			CustomisedDate custom2 = null;
			for (int i = 1; i <= cgpans.size(); i++) {
				Map map = (Map) cgpans.get(i - 1);
				String cgpanNo = (String) map.get("CGPAN");
				// logic to set all properties fdd,ldd,fid,pm,im
				if (allCgpans != null) {
					for (int j = 0; j < allCgpans.size(); j++) {

						Map cgMap = (Map) allCgpans.get(j);
						String cg = (String) cgMap.get("CGPAN");
						String loanType = cg.substring(cg.length() - 2);
						if (cgpanNo.equals(cg)) {
							gmActionForm.set("cgpan" + i, cgpanNo);
							if ("TC".equals(loanType) || "CC".equals(loanType)) {
								custom2 = new CustomisedDate();
								custom2.setDate((java.util.Date) cgMap
										.get("FIRSTDISBDT"));
								gmActionForm.set("firstDisbDt" + i, custom2);
								custom2 = new CustomisedDate();
								custom2.setDate((java.util.Date) cgMap
										.get("LASTDISBDT"));
								gmActionForm.set("lastDisbDt" + i, custom2);
								custom2 = new CustomisedDate();
								custom2.setDate((java.util.Date) cgMap
										.get("FIRSTINSTDT"));
								gmActionForm.set("firstInstDt" + i, custom2);
								gmActionForm.set("moratoriumPrincipal" + i,
										(Integer) cgMap
												.get("PRINCIPALMORATORIUM"));
								gmActionForm.set("moratoriumInterest" + i,
										(Integer) cgMap
												.get("INTERESTMORATORIUM"));

								gmActionForm.set("totalDisbAmt" + i,
										(Double) cgMap.get("TOTALDISBAMT"));
								gmActionForm.set("repayPrincipal" + i,
										(Double) cgMap.get("PRINCIPALREPAY"));
								gmActionForm.set("repayInterest" + i,
										(Double) cgMap.get("INTERESTREPAY"));
							}
							gmActionForm.set("outstandingPrincipal" + i,
									(Double) cgMap.get("PRINCIPALOS"));
							gmActionForm.set("outstandingInterest" + i,
									(Double) cgMap.get("INTERESTOS"));
							break;
						}
					}
				}

			}

			GMAction gm = new GMAction();
			totalLandValueStr = "";
			totalMachineValueStr = "";
			totalBldgValueStr = "";
			totalOFMAValueStr = "";
			totalCurrAssetsValueStr = "";
			totalOthersValueStr = "";
			securitydetails = null;

			CPDAO cpdao = new CPDAO();

			securitydetails = cpdao
					.getPrimarySecurityAndNetworthOfGuarantors(npaId);
			HashMap sancmap = (HashMap) securitydetails.get("SAN");
			HashMap npamap = (HashMap) securitydetails.get("NPA");

			if (sancmap != null) {

				totalNetWorthDouble = (Double) sancmap.get("networth");
				if (totalNetWorthDouble != null) {
					networthAsOnSancDt = totalNetWorthDouble.doubleValue();
				}

				totalLandValDouble = (Double) sancmap.get("LAND");
				if (totalLandValDouble != null) {
					if (totalLandValDouble.doubleValue() > 0.0) {
						totalLandValueStr = String.valueOf(totalLandValDouble);
						totalSecurityAsOnSanc = totalSecurityAsOnSanc
								+ totalLandValDouble.doubleValue();
					} else {
						totalLandValueStr = "";
					}
				}

				totalMachineValDouble = (Double) sancmap.get("MACHINE");
				if (totalMachineValDouble != null) {
					if (totalMachineValDouble.doubleValue() > 0.0) {
						totalMachineValueStr = String
								.valueOf(totalMachineValDouble);
						totalSecurityAsOnSanc = totalSecurityAsOnSanc
								+ totalMachineValDouble.doubleValue();
					} else {
						totalMachineValueStr = "";
					}
				}
				totalBldgValueDouble = (Double) sancmap.get("BUILDING");
				if (totalBldgValueDouble != null) {
					if (totalBldgValueDouble.doubleValue() > 0.0) {
						totalBldgValueStr = String
								.valueOf(totalBldgValueDouble);
						totalSecurityAsOnSanc = totalSecurityAsOnSanc
								+ totalBldgValueDouble.doubleValue();
					} else {
						totalBldgValueStr = "";
					}
				}
				totalOFMAValDouble = (Double) sancmap
						.get("OTHER FIXED MOVABLE ASSETS");
				if (totalOFMAValDouble != null) {
					if (totalOFMAValDouble.doubleValue() > 0.0) {
						totalOFMAValueStr = String.valueOf(totalOFMAValDouble);
						totalSecurityAsOnSanc = totalSecurityAsOnSanc
								+ totalOFMAValDouble.doubleValue();
					} else {
						totalOFMAValueStr = "";
					}
				}
				totalCurrAssetsValDouble = (Double) sancmap
						.get("CURRENT ASSETS");
				if (totalCurrAssetsValDouble != null) {
					if (totalCurrAssetsValDouble.doubleValue() > 0.0) {
						totalCurrAssetsValueStr = String
								.valueOf(totalCurrAssetsValDouble);
						totalSecurityAsOnSanc = totalSecurityAsOnSanc
								+ totalCurrAssetsValDouble.doubleValue();
					} else {
						totalCurrAssetsValueStr = "";
					}
				}
				totalOthersValDouble = (Double) sancmap.get("OTHERS");
				if (totalOthersValDouble != null) {
					if (totalOthersValDouble.doubleValue() > 0.0) {
						totalOthersValueStr = String
								.valueOf(totalOthersValDouble);
						totalSecurityAsOnSanc = totalSecurityAsOnSanc
								+ totalOthersValDouble.doubleValue();
					} else {
						totalOthersValueStr = "";
					}
				}
			}
			securityMap.put("LAND", totalLandValueStr);
			securityMap.put("MACHINE", totalMachineValueStr);
			securityMap.put("BUILDING", totalBldgValueStr);
			securityMap.put("OTHER_FIXED_MOVABLE_ASSETS", totalOFMAValueStr);
			securityMap.put("CUR_ASSETS", totalCurrAssetsValueStr);
			securityMap.put("OTHERS", totalOthersValueStr);

			totalLandValueStr = "";
			totalMachineValueStr = "";
			totalBldgValueStr = "";
			totalOFMAValueStr = "";
			totalCurrAssetsValueStr = "";
			totalOthersValueStr = "";
			securitydetails = null;
			String reasonReduction = "";

			// securitydetails =
			// dao.getPrimarySecurityAndNetworthOfGuarantorsAsOnNpa(borrowerId,memberId,npaId);

			if (npamap != null) {

				totalNetWorthDouble = (Double) npamap.get("networth");
				if (totalNetWorthDouble != null) {
					networthAsOnNpaDt = totalNetWorthDouble.doubleValue();
				}

				totalLandValDouble = (Double) npamap.get("LAND");
				if (totalLandValDouble != null) {
					if (totalLandValDouble.doubleValue() > 0.0) {
						totalLandValueStr = String.valueOf(totalLandValDouble);
						totalSecurityAsOnNpa = totalSecurityAsOnNpa
								+ totalLandValDouble.doubleValue();
					} else {
						totalLandValueStr = "";
					}
				}

				totalMachineValDouble = (Double) npamap.get("MACHINE");
				if (totalMachineValDouble != null) {
					if (totalMachineValDouble.doubleValue() > 0.0) {
						totalMachineValueStr = String
								.valueOf(totalMachineValDouble);
						totalSecurityAsOnNpa = totalSecurityAsOnNpa
								+ totalMachineValDouble.doubleValue();
					} else {
						totalMachineValueStr = "";
					}
				}
				totalBldgValueDouble = (Double) npamap.get("BUILDING");
				if (totalBldgValueDouble != null) {
					if (totalBldgValueDouble.doubleValue() > 0.0) {
						totalBldgValueStr = String
								.valueOf(totalBldgValueDouble);
						totalSecurityAsOnNpa = totalSecurityAsOnNpa
								+ totalBldgValueDouble.doubleValue();
					} else {
						totalBldgValueStr = "";
					}
				}
				totalOFMAValDouble = (Double) npamap
						.get("OTHER FIXED MOVABLE ASSETS");
				if (totalOFMAValDouble != null) {
					if (totalOFMAValDouble.doubleValue() > 0.0) {
						totalOFMAValueStr = String.valueOf(totalOFMAValDouble);
						totalSecurityAsOnNpa = totalSecurityAsOnNpa
								+ totalOFMAValDouble.doubleValue();
					} else {
						totalOFMAValueStr = "";
					}
				}
				totalCurrAssetsValDouble = (Double) npamap
						.get("CURRENT ASSETS");
				if (totalCurrAssetsValDouble != null) {
					if (totalCurrAssetsValDouble.doubleValue() > 0.0) {
						totalCurrAssetsValueStr = String
								.valueOf(totalCurrAssetsValDouble);
						totalSecurityAsOnNpa = totalSecurityAsOnNpa
								+ totalCurrAssetsValDouble.doubleValue();
					} else {
						totalCurrAssetsValueStr = "";
					}
				}
				totalOthersValDouble = (Double) npamap.get("OTHERS");
				if (totalOthersValDouble != null) {
					if (totalOthersValDouble.doubleValue() > 0.0) {
						totalOthersValueStr = String
								.valueOf(totalOthersValDouble);
						totalSecurityAsOnNpa = totalSecurityAsOnNpa
								+ totalOthersValDouble.doubleValue();
					} else {
						totalOthersValueStr = "";
					}
				}
				reasonReduction = (String) npamap.get("reasonReduction");
			}
			securitymapnpa.put("LAND", totalLandValueStr);
			securitymapnpa.put("MACHINE", totalMachineValueStr);
			securitymapnpa.put("BUILDING", totalBldgValueStr);
			securitymapnpa.put("OTHER_FIXED_MOVABLE_ASSETS", totalOFMAValueStr);
			securitymapnpa.put("CUR_ASSETS", totalCurrAssetsValueStr);
			securitymapnpa.put("OTHERS", totalOthersValueStr);

			gmActionForm.set("securityAsOnSancDt", securityMap);
			gmActionForm.set("networthAsOnSancDt", networthAsOnSancDt);
			gmActionForm.set("totalSecurityAsOnSanc", totalSecurityAsOnSanc);

			gmActionForm.set("securityAsOnNpaDt", securitymapnpa);
			gmActionForm.set("networthAsOnNpaDt", networthAsOnNpaDt);
			gmActionForm.set("reasonForReductionAsOnNpaDt", reasonReduction);
			gmActionForm.set("totalSecurityAsOnNpa", totalSecurityAsOnNpa);
		}

		Log.log(Log.INFO, "GMAction", "showNPADetails", "Exited");

		return mapping.findForward(Constants.SUCCESS);

	}
	
	
	
	
	
	
	
	public ActionForward showMemberListForNpaSubmit(ActionMapping maping,ActionForm form,
			HttpServletRequest request,HttpServletResponse response)throws Exception
	{
		Log.log(4, "GMAction", "showMemberListForNpaSubmit", "Entered");
		HttpSession session = request.getSession(false);
		User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId+zoneId+branchId;				
				
		GMActionForm gmActionForm = (GMActionForm) form;
		String FormType = request.getParameter("formType");
		String forward = "";
		
		ArrayList npaApprovalDetail = displayRequestedForNPAApproval(memberId,FormType);		
		if (npaApprovalDetail == null || npaApprovalDetail.size() == 0){				
			Log.log(Log.INFO, "GMAction", "showTenureApproval",
					"Emty NPA Updation Approval list");
			request.setAttribute("message","No NPA Updation Details available for Approval");
			forward = "success";
		}
		else 
		{			
			session.setAttribute("FORMNAME", FormType);
			gmActionForm.setNpaUpgraDetailReq(npaApprovalDetail);		
			forward = "npaSubmitLst";
		}		
		Log.log(4, "GMAction", "showMemberListForNpaSubmit", "Exited");		
		return maping.findForward(forward);				
	}
	
	
	

	public ActionForward npaSubmitSave(ActionMapping maping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(Log.INFO, "GMAction", "npaSubmitSave", "Entered");
		String updateFormName = request.getParameter("formName");
		String updateQryForModNUpg = "";
		Connection connection = connection = DBConnection.getConnection(false);
		// CallableStatement callableStmt = null;
		Statement stmt = null;
		GMActionForm gmActionForm = (GMActionForm) form;
		User user = getUserInformation(request);
		String userId = user.getUserId();
		
		String arr[] = gmActionForm.getTextarea();
		String commCgpanVal = null;
		String checkKey = null;
		
		String bank_Id = user.getBankId();
		String zone_Id = user.getZoneId();
		String branch_Id = user.getBranchId();
		String memberId = bank_Id + zone_Id + branch_Id;
		Map commentCgpan = gmActionForm.getCommentCgpan();
	//	String arr[] = gmActionForm.getTextarea();
	
		try {

			for (int i = 0; i < arr.length; i++) {
				checkKey = arr[i];
				commCgpanVal = (String) commentCgpan.get(checkKey);
				
				CallableStatement callable = connection.prepareCall("{?=call "
						+ "Funcupgnpastand(?,?,?,?,?)}");
				callable.registerOutParameter(1, Types.INTEGER);
				callable.setString(2, memberId);
				callable.setString(3, checkKey);
				callable.setString(4, commCgpanVal);
				callable.setString(5, userId);
				callable.registerOutParameter(6, Types.VARCHAR);
				callable.execute();
				int errorCode = callable.getInt(1);
				String error = callable.getString(6);
				// System.out.println("Error:"+error);

				Log.log(Log.DEBUG, "GMDAO", "submitUpgradationDetails",
						"error code and error" + errorCode + "," + error);

				if (errorCode == Constants.FUNCTION_FAILURE) {
					Log.log(Log.ERROR, "GMDAO", "submitUpgradationDetails", error);

					callable.close();
					callable = null;
					throw new DatabaseException(error);
				}

				callable.close();
				callable = null;
			}
		}catch (SQLException e) {
				Log.log(Log.ERROR, "GMDAO", "submitUpgradationDetails",
						e.getMessage());

				Log.logException(e);

				throw new DatabaseException("Unable to delete the NPA Details.");//kuldeep

			} finally {
				DBConnection.freeConnection(connection);
			}
		
		
			connection.commit();
			request.setAttribute("message",
					"<b>The Request for Upgradation of NPA  Successfully.<b><br>");
		
		return maping.findForward("success");	
	}
	
	

 

/*	public ActionForward npaSubmitSaveOLD18032015(ActionMapping maping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(Log.INFO, "GMAction", "npaSubmitSave", "Entered");
		String updateFormName = request.getParameter("formName");
		String updateQryForModNUpg = "";
		Connection connection = connection = DBConnection.getConnection(false);
		// CallableStatement callableStmt = null;
		Statement stmt = null;
		GMActionForm gmActionForm = (GMActionForm) form;

		User user = getUserInformation(request);
		String bank_Id = user.getBankId();
		String zone_Id = user.getZoneId();
		String branch_Id = user.getBranchId();
		String memberId = bank_Id + zone_Id + branch_Id;

		int queryStatus = 0;
		String queryException = "";
		String userId = user.getUserId();
		Map commentCgpan = gmActionForm.getCommentCgpan();
		String arr[] = gmActionForm.getTextarea();
		String commCgpanVal = null;
		String checkKey = null;

		String npaupdateQry = "";
		String npadeleteQry = "";
		String npatcdeleteQry = "";
		String npawcdeleteQry = "";

		String npabackQry = "";
		String npatcbackQry = "";
		String npawcbackQry = "";

		String claimDetQry = "";

		String claimInsertQry = "";

		String claimDeltQry = "";

		String claimtcdeleteQry = "";
		String claimwcdeleteQry = "";

		String claimbackQry = "";
		String claimtcbackQry = "";
		String claimwcbackQry = "";
		String addAppRemarksQry = "";

		String claimAppliedAmtbackQry = "";

		String claimRecryDetbackQry = "";

		String claimAppliedAmtdelQry = "";
		String claimRecryDetdelQry = "";
		
		  Date currentDt = new Date();

		try {

			for (int i = 0; i < arr.length; i++) {
				checkKey = arr[i];
				commCgpanVal = (String) commentCgpan.get(checkKey);
				// System.out.println("GMA checkKey : "+checkKey+
				// "\t commCgpanVal : "+commCgpanVal);

				if (updateFormName.equals("Upgradation")) {

					claimDetQry = "select count(*) from  claim_detail_temp  where bid in (select distinct bid from ssi_detail where ssi_reference_number"
							+ " in (select distinct ssi_reference_number  from application_detail where cgpan='"
							+ checkKey + "'))";

					stmt = connection.createStatement();
					ResultSet rs = stmt.executeQuery(claimDetQry);

					while (rs.next()) {
						if (rs.getInt(1) > 0)

						{

							
							
							if(checkKey.endsWith("TC"))
								
							{
								

								npatcbackQry = " insert into  npa_tc_detail_temp_canc select * from npa_tc_detail_temp where cgpan= '"
										+ checkKey + "'";
								
								claimtcbackQry = " insert into  claim_tc_detail_temp_canc select * from claim_tc_detail_temp where cgpan= '"
									+ checkKey + "'";
								

							
							npabackQry = " insert into  npa_detail_temp_canc select * from npa_detail_temp where bid in (select distinct bid from ssi_detail where "
								+ "ssi_reference_number in (select distinct ssi_reference_number  from application_detail where cgpan= '"
								+ checkKey + "'  ))";
							
							

							claimbackQry = " insert into  claim_detail_temp_canc select * from claim_detail_temp where bid in (select distinct bid from ssi_detail where "
									+ "ssi_reference_number in (select distinct ssi_reference_number  from application_detail where cgpan= '"
									+ checkKey + "'  ))";

							
							
							claimAppliedAmtbackQry = " insert into  claim_appl_amount_temp_can select * from claim_application_amount_temp where cgpan= '"
									+ checkKey + "'";

							claimRecryDetbackQry = " insert into  claim_rec_detail_temp_canc select * from claim_recovery_detail_temp where cgpan= '"
									+ checkKey + "'";


							npaupdateQry = "update NPA_UPGRADATION_DETAIL set NUD_UPGRADE_CHANG_FLAG = 'LA', NUD_MLI_LWR_LEV_AP_BY='"+userId+"' ,NUD_MLI_LWR_LEV_AP_DT=sysdate, NUD_MLI_HIGHER_LVL_REMARKS = '"
								+ commCgpanVal
								+ "' where cgpan = '"
								+ checkKey + "'"; // Upgradation";
								
								npatcdeleteQry = " delete  npa_tc_detail_temp  where cgpan= '"
										+ checkKey + "'";
								
								claimtcdeleteQry = " delete  claim_tc_detail_temp  where cgpan= '"
									+ checkKey + "'";
								
								npadeleteQry = " delete  npa_detail_temp  where bid in (select distinct bid from ssi_detail where "
									+ "ssi_reference_number in (select distinct ssi_reference_number  from application_detail where cgpan= '"
									+ checkKey + "' ))";
								

								claimAppliedAmtdelQry = " delete  claim_application_amount_temp  where cgpan= '"
										+ checkKey + "'";

								claimRecryDetdelQry = " delete  claim_recovery_detail_temp  where cgpan= '"
										+ checkKey + "'";

								addAppRemarksQry = "update application_detail set app_remarks=app_remarks||' npa and claim upgradated  on '||sysdate  where cgpan= '"
										+ checkKey + "'";

							
								

								stmt.addBatch(npaupdateQry);
								stmt.addBatch(npabackQry);
								stmt.addBatch(npatcbackQry);								
								stmt.addBatch(npadeleteQry);
								stmt.addBatch(npatcdeleteQry);								
								stmt.addBatch(claimbackQry);
								stmt.addBatch(claimtcbackQry);							
								// stmt.addBatch(claimDeltQry);
								stmt.addBatch(claimtcdeleteQry);								
								stmt.addBatch(addAppRemarksQry);
								stmt.addBatch(claimAppliedAmtbackQry);
								stmt.addBatch(claimRecryDetbackQry);
								stmt.addBatch(claimAppliedAmtdelQry);
								stmt.addBatch(claimRecryDetdelQry);

								int[] counter = stmt.executeBatch();
								
								
							}
							
							else
							{
								

								npawcbackQry = " insert into  npa_wc_detail_temp_canc select * from npa_wc_detail_temp where cgpan= '"
										+ checkKey + "'";
								
								claimwcbackQry = " insert into  claim_wc_detail_temp_canc select * from claim_wc_detail_temp where cgpan= '"
									+ checkKey + "'";
							
							
							npabackQry = " insert into  npa_detail_temp_canc select * from npa_detail_temp where bid in (select distinct bid from ssi_detail where "
								+ "ssi_reference_number in (select distinct ssi_reference_number  from application_detail where cgpan= '"
								+ checkKey + "'  ))";
							

							claimbackQry = " insert into  claim_detail_temp_canc select * from claim_detail_temp where bid in (select distinct bid from ssi_detail where "
									+ "ssi_reference_number in (select distinct ssi_reference_number  from application_detail where cgpan= '"
									+ checkKey + "'  ))";

							
							
							claimAppliedAmtbackQry = " insert into  claim_appl_amount_temp_can select * from claim_application_amount_temp where cgpan= '"
									+ checkKey + "'";

							claimRecryDetbackQry = " insert into  claim_rec_detail_temp_canc select * from claim_recovery_detail_temp where cgpan= '"
									+ checkKey + "'";

							
							
							npaupdateQry = "update NPA_UPGRADATION_DETAIL set NUD_UPGRADE_CHANG_FLAG = 'LA',NUD_MLI_LWR_LEV_AP_BY='"+user.getUserId()+"',NUD_MLI_LWR_LEV_AP_DT=sysdate, NUD_MLI_HIGHER_LVL_REMARKS = '"
								+ commCgpanVal
								+ "' where cgpan = '"
								+ checkKey + "'"; // Upgradation";

								npawcdeleteQry = " delete  npa_wc_detail_temp  where cgpan= '"
									+ checkKey + "'";

								claimwcdeleteQry = " delete  claim_wc_detail_temp  where cgpan= '"
									+ checkKey + "'";
								
								npadeleteQry = " delete  npa_detail_temp  where bid in (select distinct bid from ssi_detail where "
									+ "ssi_reference_number in (select distinct ssi_reference_number  from application_detail where cgpan= '"
									+ checkKey + "' ))";
								
								

								claimAppliedAmtdelQry = " delete  claim_application_amount_temp  where cgpan= '"
										+ checkKey + "'";

								claimRecryDetdelQry = " delete  claim_recovery_detail_temp  where cgpan= '"
										+ checkKey + "'";

								addAppRemarksQry = "update application_detail set app_remarks=app_remarks||' npa and claim upgrated on '||sysdate||' approved by the '|| '"+userId+"'  where cgpan= '"
								+ checkKey + "'";
								stmt.addBatch(npaupdateQry);
								stmt.addBatch(npabackQry);

								stmt.addBatch(npawcbackQry);
								stmt.addBatch(npadeleteQry);

								stmt.addBatch(npawcdeleteQry);
								stmt.addBatch(claimbackQry);

								stmt.addBatch(claimwcbackQry);
								// stmt.addBatch(claimDeltQry);

								stmt.addBatch(claimwcdeleteQry);
								stmt.addBatch(addAppRemarksQry);
								stmt.addBatch(claimAppliedAmtbackQry);
								stmt.addBatch(claimRecryDetbackQry);
								stmt.addBatch(claimAppliedAmtdelQry);
								stmt.addBatch(claimRecryDetdelQry);

								int[] counter = stmt.executeBatch();
								
							}
							


						} else {

							npaupdateQry = "update NPA_UPGRADATION_DETAIL set NUD_UPGRADE_CHANG_FLAG = 'LA',NUD_MLI_LWR_LEV_AP_BY='"+user.getUserId()+"',NUD_MLI_LWR_LEV_AP_DT=sysdate, NUD_MLI_HIGHER_LVL_REMARKS = '"
									+ commCgpanVal
									+ "' where cgpan = '"
									+ checkKey + "'"; // Upgradation";
							// System.out.println("GMA deleteQry : "+deleteQry);
							// request.setAttribute("message","<b>The Request for Upgradation of NPA Date Delete Successfully.<b><br>");

							npabackQry = " insert into  npa_detail_temp_canc select * from npa_detail_temp where bid in (select distinct bid from ssi_detail where "
									+ "ssi_reference_number in (select distinct ssi_reference_number  from application_detail where cgpan= '"
									+ checkKey + "'  ))";

							npatcbackQry = " insert into  npa_tc_detail_temp_canc select * from npa_tc_detail_temp where cgpan= '"
									+ checkKey + "'";

							npawcbackQry = " insert into  npa_wc_detail_temp_canc select * from npa_wc_detail_temp where cgpan= '"
									+ checkKey + "'";

							npadeleteQry = " delete  npa_detail_temp  where bid in (select distinct bid from ssi_detail where "
									+ "ssi_reference_number in (select distinct ssi_reference_number  from application_detail where cgpan= '"
									+ checkKey + "' ))";

							npatcdeleteQry = " delete  npa_tc_detail_temp  where cgpan= '"
									+ checkKey + "'";

							npawcdeleteQry = " delete  npa_wc_detail_temp  where cgpan= '"
									+ checkKey + "'";

							addAppRemarksQry = "update application_detail set app_remarks=app_remarks||' npa upgrated on '||sysdate||' approved by the '|| '"+userId+"'  where cgpan= '"
									+ checkKey + "'";

							System.out.println(npaupdateQry);
							
							System.out.println(npabackQry);
							
							System.out.println(npatcbackQry);
							
							System.out.println(npawcbackQry);
							
							System.out.println(npadeleteQry);
							
							System.out.println(npatcdeleteQry);
							
							System.out.println(npawcdeleteQry);
							
							System.out.println(addAppRemarksQry);
							
							
							
							stmt.addBatch(npaupdateQry);
							stmt.addBatch(npabackQry);
							stmt.addBatch(npatcbackQry);
							stmt.addBatch(npawcbackQry);
							stmt.addBatch(npadeleteQry);
							stmt.addBatch(npatcdeleteQry);
							stmt.addBatch(npawcdeleteQry);
							stmt.addBatch(addAppRemarksQry);

							int[] counter = stmt.executeBatch();
							// System.out.println("GMA counter : "+counter);
							// if(counter.length > 0)
							// {
							// connection.commit();
							// request.setAttribute("message","<b>The Request for Upgradation of NPA Date Delete Successfully.<b><br>");
							// }
							// else
							// {
							// connection.rollback();
							// }

						}
					}

				}

			}

			for (int i = 0; i < arr.length; i++) {
				checkKey = arr[i];
				commCgpanVal = (String) commentCgpan.get(checkKey);

				claimDetQry = "select count(*) from  claim_detail_temp  where bid in (select distinct bid from ssi_detail where ssi_reference_number"
						+ " in (select distinct ssi_reference_number  from application_detail where cgpan='"
						+ checkKey + "'))";

				stmt = connection.createStatement();
				ResultSet rs = stmt.executeQuery(claimDetQry);

				while (rs.next()) {
					if (rs.getInt(1) > 0)

					{
						claimDeltQry = " delete  claim_detail_temp  where bid in (select distinct bid from ssi_detail where "
								+ "ssi_reference_number in (select distinct ssi_reference_number  from application_detail where cgpan= '"
								+ checkKey + "' ))";

						stmt.addBatch(claimDeltQry);

						int[] counter = stmt.executeBatch();

					}
				}
			}
			connection.commit();
			request.setAttribute("message",
					"<b>The Request for Upgradation of NPA  Successfully.<b><br>");

			
			 * for(int i=0; i<arr.length; i++) { checkKey = arr[i]; commCgpanVal
			 * = (String)commentCgpan.get(checkKey);
			 * //System.out.println("GMA checkKey : "+checkKey+
			 * "\t commCgpanVal : "+commCgpanVal);
			 * 
			 * if(updateFormName.equals("Upgradation")) { callableStmt =
			 * connection
			 * .prepareCall("{?= call FuncConvertNpaToStandard(?,?,?,?,?)}");
			 * callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			 * callableStmt.setString(2, memberId); callableStmt.setString(3,
			 * checkKey); callableStmt.setString(4, commCgpanVal);
			 * callableStmt.setString(5, userId);
			 * callableStmt.registerOutParameter(6, java.sql.Types.VARCHAR);
			 * callableStmt.executeQuery();
			 * 
			 * queryStatus = callableStmt.getInt(1); queryException =
			 * callableStmt.getString(6);
			 * 
			 * if(queryStatus == Constants.FUNCTION_SUCCESS) {
			 * connection.commit(); callableStmt.close(); callableStmt = null;
			 * request.setAttribute("message",
			 * "<b>The Request for Upgradation of NPA Date Approved Successfully.<b><br>"
			 * ); } else if(queryStatus == Constants.FUNCTION_FAILURE) {
			 * connection.rollback(); callableStmt.close(); callableStmt = null;
			 * request.setAttribute("message",
			 * "<b>The Request for Upgradation of NPA Date Approved Failed.<b><br>"
			 * ); } } }
			 
		} catch (Exception e) {
			request.setAttribute("message",
					"<b>A Problem Occured While Submitting The Records :.<b><br>"
							+ e.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}
		commentCgpan.clear();
		Log.log(Log.INFO, "GMAction", "npaSubmitSave", "Exited");
		return maping.findForward("success");
	}
	
*/
	public ActionForward npaDeleteRecord(ActionMapping mapping,ActionForm form,
				HttpServletRequest request,HttpServletResponse response)throws Exception
		{
			Log.log(Log.INFO, "GMAction", "npaDeleteRecord", "Entered");
			GMActionForm gmDeleteForm = (GMActionForm)form;
			String delForm = request.getParameter("formName");
			String deleteQry = "";
			Connection connection = connection = DBConnection.getConnection(false);
			Statement stmt = null;
			User user = getUserInformation(request);
			String userId = user.getUserId();
			Map commentCgpan = gmDeleteForm.getCommentCgpan();
			//System.out.println("GMA modifCgpan.size : "+commentCgpan.size());
			String arr[]=gmDeleteForm.getTextarea();
			String commCgpanVal = null;
			String checkKey = null;	
			String npadeleteQry = "";
			String npatcdeleteQry = "";
			String npawcdeleteQry = "";
			
			String npabackQry = "";
			String npatcbackQry = "";
			String npawcbackQry = "";
			try
			{
				for(int i=0; i<arr.length; i++)
				{
					checkKey =  arr[i];
					commCgpanVal = (String)commentCgpan.get(checkKey);
					//System.out.println("GMA checkKey : "+checkKey+ "\t commCgpanVal : "+commCgpanVal);
					
					if(delForm.equals("Upgradation"))
					{
					//	deleteQry = "update NPA_UPGRADATION_DETAIL set NUD_UPGRADE_CHANG_FLAG = 'LR', NUD_CGTMSE_REMARKS = '"+commCgpanVal+"' where cgpan = '"+checkKey+"'"; // Upgradation";
						deleteQry = "delete NPA_UPGRADATION_DETAIL  where cgpan = '"+checkKey+"'"; // Upgradation";
						//System.out.println("GMA deleteQry : "+deleteQry);
						//request.setAttribute("message","<b>The Request for Upgradation of NPA Date Delete Successfully.<b><br>");
						
						
					
						stmt = connection.createStatement();
						
						
						int counter = stmt.executeUpdate(deleteQry);
						//System.out.println("GMA counter : "+counter);
						if(counter > 0)
						{							
							connection.commit();
							request.setAttribute("message","<b>The Request for Upgradation of NPA  Delete Successfully.<b><br>");
						}
						else
						{							
							connection.rollback();
						}
					}
				}
			}
			catch(Exception e)
			{
				request.setAttribute("message","<b>A Problem Occured While Deleting The Records.<b><br>"+e.getMessage());
			}
			finally
			{
				DBConnection.freeConnection(connection);
			}
			commentCgpan.clear();
			Log.log(Log.INFO, "GMAction", "npaDeleteRecord", "Exited");
			return mapping.findForward("success");
		}


	public ArrayList displayRequestedForNPAApproval(String memberId,String formType)throws DatabaseException
		{
			Log.log(Log.INFO, "GMAction", "displayRequestedForNPAApproval", "Entered");				
			GMActionForm gmActionForm = null;
			ArrayList npaUpdationList = new ArrayList();
			Connection connection = null;
			Statement stmt = null,stmt1;
			ResultSet rs = null , rs1=null;
			try
			{
				connection = DBConnection.getConnection(false);				
				if(formType.equals("modification"))
				{
					String modificationQuery = " SELECT DISTINCT d.cgpan, ssi_unit_name, mem_bank_name, m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id, " +
					" to_char(c.NPA_EFFECTIVE_DT,'dd-mm-yyyy'), NPA_REASONS_TURNING_NPA, to_char(NDC_UPGRADE_DT,'dd-mm-yyyy'), " +
					" to_char(NDC_NEW_NPA_EFFECTIVE_DT,'dd-mm-yyyy'), to_char(NDC_MLI_LWR_LEV_IN_DT,'dd-mm-yyyy'), NdC_USER_REMARKS" +
					" FROM application_detail a, ssi_detail b, npa_detail_temp c, NPA_DATE_CHANGE_DETAIL d, member_info m" +
					" WHERE a.ssi_reference_number = b.ssi_reference_number AND b.bid = c.bid" +
					" AND c.npa_id = d.npa_id AND d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id = m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id" +
					" AND NDC_NPA_DT_CHANG_FLAG = 'LN' AND m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id = '"+memberId+"'";
					
					stmt = connection.createStatement();
					rs = stmt.executeQuery(modificationQuery);
					while(rs.next())
					{
						gmActionForm=new GMActionForm();
						gmActionForm.setCgpan(rs.getString(1)); //cgpan
						//System.out.println("GMA CGPAN : "+rs.getString(1));
						
						gmActionForm.setUnitName(rs.getString(2)); // unit name
						//System.out.println("GMA unit name : "+rs.getString(2));
						
						gmActionForm.setBankName(rs.getString(3)); //bank name
						//System.out.println("GMA bank name : "+rs.getString(3));
						
						gmActionForm.setMemberId(rs.getString(4)); //memberId
						//System.out.println("GMA memberId : "+rs.getString(4));
						
						gmActionForm.setNpaEffDt(rs.getString(5)); // npaEff Data
						//System.out.println("GMA nap eff dt : "+rs.getString(5));
						
						gmActionForm.setNpaReason(rs.getString(6)); // reason turn npa
						//System.out.println("GMA reason turn npa : "+rs.getString(6));
						
						gmActionForm.setNpaUpgraDt(rs.getString(7)); // npa upgradation date
						//System.out.println("GMA upgradation dt : "+rs.getString(7));
						
						gmActionForm.setNewNpaEffDt(rs.getString(8)); // new npa eff dt
						//System.out.println("GMA new npa eff dt : "+rs.getString(8));
						
						gmActionForm.setNpaLwrlevInDt(rs.getString(9)); // npa low lev in dt
						//System.out.println("GMA npa low lev in dt : "+rs.getString(9));
						
						gmActionForm.setNpaUserRemark(rs.getString(10)); // user remarks
						//System.out.println("GMA user remarks : "+rs.getString(10));
						
						npaUpdationList.add(gmActionForm);
					}			
								
					connection.commit();
					rs.close();
					rs = null;
					stmt.close();
					stmt = null;
				}
				
				else if(formType.equals("Upgradation"))
				{
					String upgradationQuery = "SELECT DISTINCT d.cgpan, ssi_unit_name, mem_bank_name, m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id, to_char(c.NPA_EFFECTIVE_DT,'dd-mm-yyyy')," +
							" NPA_REASONS_TURNING_NPA, to_char(NPA_UPGRADE_DT,'dd-mm-yyyy'), to_char(NUD_MLI_LWR_LEV_IN_DT,'dd-mm-yyyy'), NUD_USER_REMARKS" +
							" FROM application_detail a, ssi_detail b, npa_detail_temp c, NPA_UPGRADATION_DETAIL d, member_info m" +
							" WHERE     a.ssi_reference_number = b.ssi_reference_number AND b.bid = c.bid AND c.npa_id = d.npa_id" +
							" AND d.mem_bnk_id || d.mem_zne_id || d.mem_brn_id = m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id" +
							" AND NUD_UPGRADE_CHANG_FLAG = 'LN' AND m.mem_bnk_id || m.mem_zne_id || m.mem_brn_id = '"+memberId+"'";
					
					stmt = connection.createStatement();
					rs = stmt.executeQuery(upgradationQuery);
					while(rs.next()) 
					{
						gmActionForm=new GMActionForm();
						gmActionForm.setCgpan(rs.getString(1)); //cgpan
						//System.out.println("GMA CGPAN : "+rs.getString(1));
						
						gmActionForm.setUnitName(rs.getString(2)); // unit name
						//System.out.println("GMA unit name : "+rs.getString(2));
						
						gmActionForm.setBankName(rs.getString(3)); //bank name
						//System.out.println("GMA bank name : "+rs.getString(3));
						
						gmActionForm.setMemberId(rs.getString(4)); //memberId
						//System.out.println("GMA memberId : "+rs.getString(4));
						
						gmActionForm.setNpaEffDt(rs.getString(5)); // npaEff Data
						//System.out.println("GMA nap eff dt : "+rs.getString(5));
						
						gmActionForm.setNpaReason(rs.getString(6)); // reason turn npa
						//System.out.println("GMA reason turn npa : "+rs.getString(6));
						
						gmActionForm.setNpaUpgraDt(rs.getString(7)); // npa upgradation date
						//System.out.println("GMA upgradation dt : "+rs.getString(7));
						
						gmActionForm.setNpaLwrlevInDt(rs.getString(8)); // npa low lev in dt
						//System.out.println("GMA npa low lev in dt : "+rs.getString(8));
						
						gmActionForm.setNpaUserRemark(rs.getString(9)); // user remarks
						//System.out.println("GMA user remarks : "+rs.getString(9));
						
						npaUpdationList.add(gmActionForm);
						//System.out.println("GMA npaUpdationList : "+npaUpdationList.size());
					}
					connection.commit();
					rs.close();
					rs = null;
					stmt.close();
					stmt = null;
				}
				else
				{				
					throw new MessageException("A Problem Occured While Fetching Records According to Form");
				}
			}
			catch(Exception e)
			{
				e.printStackTrace();
				try { connection.rollback(); } 
				catch (SQLException ignore) { }
				throw new DatabaseException("A Problem Occured While Getting Records : "+e.getMessage());
			}
			finally
			{
				DBConnection.freeConnection(connection);
			}			
			Log.log(Log.INFO, "GMAction", "displayRequestedForNPAApproval", "Exited");
			return npaUpdationList;
		}
	
	
	// Added By Niteen Singh
	
	 public ActionForward showTenureApproval(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
	     // System.out.println("showTenureApproval............S");
			
			User user = getUserInformation(request);
			String bankId = user.getBankId();
			String zoneId = user.getZoneId();
			String branchId = user.getBranchId();
			String memberId = bankId.concat(zoneId).concat(branchId);
			//System.out.println("gmActionForm............S");
			GMActionForm gmActionForm = (GMActionForm) form;
			//System.out.println("gmActionForm..........E");
			String forward = "tenureList";

			GMProcessor gmProcessor = new GMProcessor();
	 
		/*	ArrayList tenureApprovalDetail = displayRequestedForTenureApproval(memberId);
		    ArrayList tenureApprovalDetailMod1 = (ArrayList) tenureApprovalDetail
					.get(0);

			if (tenureApprovalDetail.size() == 0
					&& tenureApprovalDetailMod1.size() == 0) {
				//System.out.println("if (tenureApprovalDetail.size()");
				
				request.setAttribute("message",
						"No Tenure Details available for Approval");
				forward = "success";
			} else {
	               //  System.out.println("under else");
				gmActionForm.setClosureDetailsReq(tenureApprovalDetailMod1);
				forward = "tenureList";
			}
			tenureApprovalDetail=null;
			tenureApprovalDetailMod1=null;*/
			//System.out.println("showTenureApproval E");
			return mapping.findForward(forward);

		}
	    
	    
	    public ArrayList displayRequestedForTenureApproval(String memberId, String remark)
		throws DatabaseException 
		{

	    Connection connection = null;		
		CallableStatement getDanDetailsStmt;
		MLIDeatils objMLIDeatils = null;
		int getSchemesStatus = 0;
		ArrayList mliDetailsArrayList = new ArrayList();
		connection = DBConnection.getConnection(false);

		try {
			
			getDanDetailsStmt = connection
					.prepareCall("{?= call Packgettenuremoddetail.funcGetTenureModDet(?,?,?,?)}");
			
			getDanDetailsStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			getDanDetailsStmt.setString(2, memberId);
			getDanDetailsStmt.setString(3, remark);
			getDanDetailsStmt.registerOutParameter(4, Constants.CURSOR);
			getDanDetailsStmt.registerOutParameter(5, java.sql.Types.VARCHAR);

			getDanDetailsStmt.execute();
			getSchemesStatus = getDanDetailsStmt.getInt(1);

			if (getSchemesStatus == Constants.FUNCTION_FAILURE) 
			{				
				String error = getDanDetailsStmt.getString(6);
				getDanDetailsStmt.close();
				getDanDetailsStmt = null;
				connection.rollback();

				throw new DatabaseException(
						"Something ["
								+ error
								+ "] went wrong, Please contact Support[support@cgtmse.in] team");
			} 
			else 
			{
				
				ResultSet rsPaidDetails = (ResultSet) getDanDetailsStmt.getObject(4);
				System.out.println("getFetchSize ");
				while (rsPaidDetails.next()) 
				{

					objMLIDeatils = new MLIDeatils();					
					objMLIDeatils.setMemberID(rsPaidDetails.getString(1));
					objMLIDeatils.setCgPan((rsPaidDetails.getString(2)));
					objMLIDeatils.setUnitName((rsPaidDetails.getString(3)));
					objMLIDeatils.setApplicationStatus((rsPaidDetails.getString(4)));
					objMLIDeatils.setExistingTenure((rsPaidDetails.getInt(5)));
					objMLIDeatils.setReviseTenure((rsPaidDetails.getInt(7)));
					objMLIDeatils.setSanctionDate((rsPaidDetails.getString(8)));
					objMLIDeatils.setReviseExpiryDate((rsPaidDetails.getString(9)));
					objMLIDeatils.setModificationRemark((rsPaidDetails.getString(10)));
					mliDetailsArrayList.add(objMLIDeatils);
					System.out.println("getMemberID "+objMLIDeatils.getMemberID());
					System.out.println("getUnitName "+objMLIDeatils.getUnitName());
					System.out.println("getApplicationStatus "+objMLIDeatils.getApplicationStatus());
					System.out.println("getExistingTenure "+objMLIDeatils.getExistingTenure());
					System.out.println("getReviseTenure "+objMLIDeatils.getReviseTenure());
					System.out.println("getSanctionDate "+objMLIDeatils.getSanctionDate());
					System.out.println("getModificationRemark "+objMLIDeatils.getModificationRemark());

				}
				rsPaidDetails.close();
				rsPaidDetails = null;
				getDanDetailsStmt.close();
				getDanDetailsStmt = null;

			}
			connection.commit();

			} catch (Exception exception) {
				exception.printStackTrace();
				try {
					connection.rollback();
				} catch (SQLException ignore) {
			}

			throw new DatabaseException(
					"Something ["
							+ exception.getMessage()
							+ "] went wrong, Please contact Support[support@cgtmse.in] team");

			} finally {
				DBConnection.freeConnection(connection);
			}
System.out.println("mliDetailsArrayList="+mliDetailsArrayList.size());
			return mliDetailsArrayList;
		}
	    
	    
	    
	    public ActionForward afterTenureApproval(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception 
		{	    	
			GMActionForm gmPeriodicInfoForm = (GMActionForm) form;

			try
			{		
			//	Connection connection = DBConnection.getConnection(false);
				User user = getUserInformation(request);
				Map modifCgpan = gmPeriodicInfoForm.getClosureCgpan();			
				Set modifCgpanSet = modifCgpan.keySet();	 
		 		Iterator modifCgpanIterator = modifCgpanSet.iterator();
			
			  while (modifCgpanIterator.hasNext()) 
			  {
					String key = (String) modifCgpanIterator.next();				
					String decision = (String) modifCgpan.get(key);
					
					if (!(decision.equals(""))) 
					{						
						if(decision.length() > 2)
						{
							decision=decision.substring(0, 2);
							System.out.println("CGPAN="+key+"=DECISION="+decision+"=");
						//	if (decision
						//			.equals(ApplicationConstants.APPLICATION_APPROVED_STATUS)) {
								DANSummary danSummaryNew = new DANSummary();
								danSummaryNew = getRequestedForTenureApplication(key);
								updateApplicationStatusForTenureCases(key, danSummaryNew,
										user , decision);
								
								request.setAttribute("message",
										"<b>Wherever cases rejected by checker, Maker may resubmit these cases to checker with correct data.<br> Record for approved cases have been successfully modified<b><br>");
						//	}
						}
	
					}
				}
		  		modifCgpan.clear();
			}
			catch(Exception e)
			{
				LogClass.StepWritter("Exception in afterTenureApproval method "+e.getMessage());
				LogClass.writeExceptionOnFile(e);
			}
			return mapping.findForward("success");
		}

   public DANSummary getRequestedForTenureApplication(String cgpan)
		throws DatabaseException {
 
	   System.out.println("getRequestedForTenureApplication called");
	Connection connection = null;
	ResultSet rsDanDetails = null;
	ResultSet rsPaidDetails = null;
	CallableStatement getTenureDetailsStmt;
	DANSummary Summary1 = null;
	int getSchemesStatus = 0;
	String getDanDetailsErr = "";
	connection = DBConnection.getConnection(false);
	try {
	       
		getTenureDetailsStmt = connection
				.prepareCall("{?= call Packgettenuremoddetail.funcGetCgpanTenureDetails(?,?,?,?,?,?,?,?,?,?,?,?,?)}");
		 
		getTenureDetailsStmt
				.registerOutParameter(1, java.sql.Types.INTEGER);
		getTenureDetailsStmt.setString(2, cgpan);
		getTenureDetailsStmt
				.registerOutParameter(3, java.sql.Types.VARCHAR);
		getTenureDetailsStmt
				.registerOutParameter(4, java.sql.Types.VARCHAR);
		getTenureDetailsStmt
				.registerOutParameter(5, java.sql.Types.VARCHAR);
		getTenureDetailsStmt
				.registerOutParameter(6, java.sql.Types.VARCHAR);
		getTenureDetailsStmt
				.registerOutParameter(7, java.sql.Types.VARCHAR);
		getTenureDetailsStmt
				.registerOutParameter(8, java.sql.Types.VARCHAR);
		getTenureDetailsStmt
				.registerOutParameter(9, java.sql.Types.VARCHAR);
		getTenureDetailsStmt.registerOutParameter(10,
				java.sql.Types.VARCHAR);
		getTenureDetailsStmt.registerOutParameter(11,
				java.sql.Types.VARCHAR);
		getTenureDetailsStmt.registerOutParameter(12,
				java.sql.Types.VARCHAR);
		getTenureDetailsStmt.registerOutParameter(13,
				java.sql.Types.VARCHAR);
		getTenureDetailsStmt.registerOutParameter(14,
				java.sql.Types.VARCHAR);
		getTenureDetailsStmt.execute();

		getSchemesStatus = getTenureDetailsStmt.getInt(1);
		if (getSchemesStatus == 0) {
		 
			System.out.println("getRequestedForTenureApplication called getSchemesStatus==0");
			Summary1 = new DANSummary();
			Summary1.setMemberId(getTenureDetailsStmt.getString(3));
			Summary1.setCgpan(getTenureDetailsStmt.getString(4));
			Summary1.setAppStatus(getTenureDetailsStmt.getString(5));
			Summary1.setOriginalTenure(getTenureDetailsStmt.getInt(6));
			Summary1.setAppExpiryDate(getTenureDetailsStmt.getString(7));
			Summary1.setRevisedTenure(getTenureDetailsStmt.getInt(8));
			Summary1.setTermAmountSanctionedtDate(getTenureDetailsStmt
					.getString(9));
			Summary1.setLastDateOfRePayment(getTenureDetailsStmt
					.getString(10));
			Summary1.setReason(getTenureDetailsStmt.getString(11));
			Summary1.setRequestCreatedUserId(getTenureDetailsStmt
					.getString(12));
			Summary1.setRequestCreatedDate(getTenureDetailsStmt
					.getString(13));

			getTenureDetailsStmt.close();
			getTenureDetailsStmt = null;

		} else {
			 
			getDanDetailsErr = getTenureDetailsStmt.getString(12);

			getTenureDetailsStmt.close();
			getTenureDetailsStmt = null;

			connection.rollback();

			throw new DatabaseException(getDanDetailsErr);
		}

		connection.commit();

	} catch (Exception exception) {
		try {
			connection.rollback();
		} catch (SQLException ignore) {
		}

		throw new DatabaseException(exception.getMessage());

	} finally {
		DBConnection.freeConnection(connection);
	}
 
	return Summary1;
	}
	    
	    
	public void updateApplicationStatusForTenureCases(String cgpan,
				DANSummary danSummary, User user , String status) throws DatabaseException {
	    	
		Connection connection = DBConnection.getConnection(false);
		String error="";
		CallableStatement updateCgpanTenureDetails = null;
		int updateStatus = 0;

		String userId = user.getUserId();

		try {
			//System.out.println("In try Block 2");
			updateCgpanTenureDetails = connection
					.prepareCall("{?=call Packgettenuremoddetail.funcupdtenuredetails(?,?,to_date(?,'dd/mm/yyyy'),?,to_date(?,'dd/mm/yyyy'),?,to_date(?,'dd/mm/yyyy'),?,?,?,?)}");

			updateCgpanTenureDetails.registerOutParameter(1,
					java.sql.Types.INTEGER);
			updateCgpanTenureDetails.setString(2, danSummary.getCgpan());
			updateCgpanTenureDetails.setInt(3, danSummary.getOriginalTenure());
			updateCgpanTenureDetails
					.setString(4, danSummary.getAppExpiryDate());
			updateCgpanTenureDetails.setInt(5, danSummary.getRevisedTenure());
			updateCgpanTenureDetails.setString(6,
					danSummary.getLastDateOfRePayment());
			updateCgpanTenureDetails.setString(7,
					danSummary.getRequestCreatedUserId());
			updateCgpanTenureDetails.setString(8,
					danSummary.getRequestCreatedDate());
			updateCgpanTenureDetails.setString(9, danSummary.getReason());
			updateCgpanTenureDetails.setString(10, user.getUserId());
			updateCgpanTenureDetails.setString(11, status);
			updateCgpanTenureDetails.registerOutParameter(12,
					java.sql.Types.VARCHAR);
			updateCgpanTenureDetails.executeQuery();
			updateStatus = updateCgpanTenureDetails.getInt(1);
			error = updateCgpanTenureDetails.getString(12);
//System.out.println("updateApplicationStatusForTenureCases "+updateStatus);
			if (updateStatus == Constants.FUNCTION_SUCCESS) {

				updateCgpanTenureDetails.close();
				updateCgpanTenureDetails = null;
				connection.commit();

			} else if (updateStatus == Constants.FUNCTION_FAILURE) {

				updateCgpanTenureDetails.close();
				updateCgpanTenureDetails = null;

				connection.rollback();

				throw new DatabaseException("Something ["+error+"] went wrong, Please contact Support[support@cgtmse.in] team");

			}
		} catch (Exception exception) {
		
			LogClass.StepWritter("Exception in updateApplicationStatusForTenureCases method "+exception.getMessage()+" SP exception is "+error);
			LogClass.writeExceptionOnFile(exception);
			try {
				error=exception.getMessage();
				connection.rollback();

			} catch (SQLException ignore) {
			}

			throw new DatabaseException("Something ["+error+"] went wrong, Please contact Support[support@cgtmse.in] team");
		} finally {

			
			DBConnection.freeConnection(connection);
		}

		}

	
	
	 public ActionForward requestModifyTenure(
	            ActionMapping mapping,
	            ActionForm form,
	            HttpServletRequest request,
	            HttpServletResponse response)
	            throws Exception {            
	            
	        
	        GMActionForm gmActionForm = (GMActionForm)form;

	        User user = getUserInformation(request);

	        String bankId = user.getBankId();
	        String zoneId = user.getZoneId();
	        String branchId = user.getBranchId();
	        String memberId = bankId.concat(zoneId).concat(branchId);

	        gmActionForm.setBankIdForClosure(bankId);

	        if (bankId.equals(Constants.CGTSI_USER_BANK_ID)) {
	            memberId = "";
	        }
	        gmActionForm.setMemberIdForClosure(memberId);	      
	        gmActionForm.setCgpanForClosure("");
	        return mapping.findForward("requestModifyClosure");

	    }
	 
	 
	 public ActionForward tenureModificationCGPanValidation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	  {		
	  	  System.out.println("tenureModificationLastPayDateCalcMethod called first"+request.getParameter("tenure"));
	  	 String message="";
	    	String cgpan = null;
	    	GMActionForm gmActionForm = (GMActionForm)form;
	    
	    	PrintWriter out= response.getWriter();
	    	String memberId = gmActionForm.getMemberIdForClosure();

	    	cgpan = gmActionForm.getCgpanForClosure();

	    	if ((memberId == null) || (memberId.equals(""))) {
	    	//	System.out.println("getUnitForTenureRequest memberId inside loop"+memberId);
	    		message="";
	    		message="Member Id is Required";

	    	}
	    	
	    	if ((cgpan == null) || (cgpan.equals(""))) {
	    		message="";
	    		message="Cgpan  is Required";
	    	}
	    	else
	    	{
	    		if(cgpan.endsWith("wc") || cgpan.endsWith("WC") || cgpan.endsWith("Wc") || cgpan.endsWith("wC"))
	    		{
	    			message="";
	    			message="If you want to modify tenure for working capital please go  for renewal option";
	    		}
	    	}
	    	
	    	if(memberId.length()<12)
	    	{
	    		message="";
	    		message="Member Id can not be less than 12 characters";
	    	}
	    	
	    	if(message.equals(""))
	    	{
	    		 String bankId = memberId.substring(0, 4);
	    		    String zoneId = memberId.substring(4, 8);
	    		    String branchId = memberId.substring(8, 12);
	    		GMDAO gmdao= new GMDAO();
	    		message="";
	    		message=gmdao.validateCgpanForTenureModificationNew(cgpan,memberId);
	    		/*Changes for GST */
	            ArrayList<MLIInfo> branchStateList=gmdao.getGSTStateList(bankId);
	    		
	            gmActionForm.setBranchStateList(branchStateList);
	       
	    	}
	    	out.print(message);

	  		
	  		return null;
	  }
	
	 
	   public ActionForward getUnitForTenureRequest(ActionMapping mapping, 
			   ActionForm form, 
			   HttpServletRequest request, 
			   HttpServletResponse response) throws Exception {
		   System.out.println("getUnitForTenureRequest executed ");
		   Log.log(Log.INFO, "GMAction", "Request for Modification of tenure", 
		   "Entered");
		   String cgpan = null;
		   GMActionForm gmActionForm = (GMActionForm)form;
		   User user = getUserInformation(request);

		   String bankId = null;
		   String zoneId = null;
		   String branchId = null;

		   String memberId = gmActionForm.getMemberIdForClosure();

		   cgpan = gmActionForm.getCgpanForClosure();

		 //  ApplicationReport appReport = new ApplicationReport();

		   System.out.println(cgpan+"getUnitForTenureRequest cgpan"+cgpan);
		   if ((memberId == null) || (memberId.equals(""))) {
			//   System.out.println("getUnitForTenureRequest memberId inside loop"+memberId);

			   throw new NoDataException("Member Id is Required");

		   } else if ((cgpan == null) || (cgpan.equals(""))) {

			   throw new NoDataException("Cgpan  is Required");
		   }
		   else if(memberId.length()<12)
		   {
			   throw new NoDataException("Member Id can not be less than 12 characters");
		   }
		   //else if ((cgpan != null) || (!cgpan.equals("")))
		   //{
		   //		if(cgpan.endsWith("WC") || cgpan.endsWith("wc") || cgpan.endsWith("Wc") || cgpan.endsWith("wC"))
		   //		{
		   //			//throw new NoDataException("You can't extend tenure of WC cases");
		   //		}
		   //
		   //}
		   else {
			   bankId = memberId.substring(0, 4);
			   zoneId = memberId.substring(4, 8);
			   branchId = memberId.substring(8, 12);
		   }
		   cgpan = gmActionForm.getCgpanForClosure();
		 //  gmActionForm.setApplicationReport(appReport);
		    Registration registration = new Registration(); //changeble

		   
		   Connection connection = DBConnection.getConnection(false);
		   CallableStatement getTenureDetail = null;
		   ResultSet resultSet = null;

		   try {

			   String exception = "";
			   String functionName = null;
			   functionName =  "{?=call Funcgetssidetailfortenure(?,?,?,?,?,?,?,?,?,?,?,?)}";
			  // System.out.println("bankId"+bankId+"zoneId"+zoneId+"branchId"+branchId);
			   getTenureDetail = connection.prepareCall(functionName);
			   getTenureDetail.registerOutParameter(1, java.sql.Types.INTEGER);
			   getTenureDetail.setString(2, bankId);
			   getTenureDetail.setString(3, zoneId);
			   getTenureDetail.setString(4, branchId);
			   getTenureDetail.setString(5, cgpan);
			   getTenureDetail.registerOutParameter(6, java.sql.Types.VARCHAR);
			   getTenureDetail.registerOutParameter(7, java.sql.Types.VARCHAR);
			   getTenureDetail.registerOutParameter(8, java.sql.Types.VARCHAR);
			   getTenureDetail.registerOutParameter(9, java.sql.Types.VARCHAR);
			   getTenureDetail.registerOutParameter(10, java.sql.Types.VARCHAR);
			   getTenureDetail.registerOutParameter(11, java.sql.Types.DATE);
			   getTenureDetail.registerOutParameter(12, java.sql.Types.VARCHAR);
			   getTenureDetail.registerOutParameter(13, java.sql.Types.VARCHAR);
			   getTenureDetail.executeQuery();

			   //  Log.log(Log.DEBUG,"GMDAO","repayment detail","exception "+exception);
			   int error = getTenureDetail.getInt(1);
			 //  System.out.println("inside getUnitForTenureRequest error "+error);

			   exception = getTenureDetail.getString(13);
			 //  System.out.println("inside getUnitForTenureRequest exception "+exception);
			   Log.log(Log.DEBUG, "GMActionNEW", "Request for Modification of tenure", 
					   "errorCode " + exception);

			   if (error == Constants.FUNCTION_FAILURE) {
				   getTenureDetail.close();
				   getTenureDetail = null;
				   connection.rollback();
				   Log.log(Log.ERROR, "GMActionNEW", "Funcgetssidetailfortenure", 
						   "error in SP " + exception);

				   throw new DatabaseException(exception);
			   } else {

				   gmActionForm.setBankName(getTenureDetail.getString(6));
				   gmActionForm.setZoneName(getTenureDetail.getString(7));
				   gmActionForm.setBranchName(getTenureDetail.getString(8));
				   gmActionForm.setUnitName(getTenureDetail.getString(9));
				   gmActionForm.setTenure(getTenureDetail.getString(10));


				   SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
				   SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yyyy");
				   Date date = format1.parse(getTenureDetail.getDate(11).toString());
				   gmActionForm.setAppStatus(getTenureDetail.getString(12));
				   System.out.println("Tenure getUnitForTenureRequest "+getTenureDetail.getString(10));
				   System.out.println("Tenure getUnitForTenureRequest "+getTenureDetail.getString(10));
				   System.out.println(format2.format(date)+"inside getUnitForTenureRequest setExpiryDate "+getTenureDetail.getDate(11));
				   gmActionForm.setExpiryDate(format2.format(date));
				//   gmActionForm.setCgpanForClosure(cgpan);
			   }


		   } catch (SQLException e) {

			   System.out.println("exception in getUnitForTenureRequest "+e.getMessage());
			   //e.printStackTrace();

			   try {
				   connection.rollback();
			   } catch (SQLException ignore) {
				   Log.log(Log.ERROR, "GMActionNew", "reactivate User", 
						   ignore.getMessage());
			   }

			   Log.log(Log.ERROR, "GMActionNew", "reactivate User", 
					   e.getMessage());

			   Log.logException(e);

			   throw new DatabaseException("Something ["+e.getMessage()+"] went wrong, Please contact Support[support@cgtmse.in] team)");

		   } finally {

			   DBConnection.freeConnection(connection);
		   }


		   Log.log(Log.INFO, "GMActionNew", "Request for Modification of tenure", 
		   "Exited");
		   return mapping.findForward(Constants.SUCCESS);

	   }
	

	   public ActionForward tenureModificationLastPayDateCalcMethod(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	    throws Exception
	  {		
	  	 // System.out.println("tenureModificationLastPayDateCalcMethod called first"+request.getParameter("tenure"));
	  	 
	        GMActionForm objGMActionForm= (GMActionForm)form;

	   //     System.out.println("ExpiryDater"+objGMActionForm.getExpiryDate());
	    //    System.out.println("Tenurer"+objGMActionForm.getTenure());
	     //   System.out.println("existingTenurer"+request.getParameter("existingTenure"));
	        
	        int reviseTenure=Integer.parseInt(objGMActionForm.getTenure())-Integer.parseInt(request.getParameter("existingTenure"));
	        Calendar cal = Calendar.getInstance();
	        Date d= new Date(objGMActionForm.getExpiryDate());
	        cal.setTime(d);
	        cal.add(Calendar.MONTH, reviseTenure); //minus number would decrement the days
	       Date d1= cal.getTime();
	  		PrintWriter out= response.getWriter();
	  		
	  		SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
	  		SimpleDateFormat format2 = new SimpleDateFormat("dd/MM/yyyy");
	  	
	  		//System.out.println("D1==="+d1);
	  		
	  		
	  		Date date = new Date();
	  		//System.out.println(format2.format(date));
	  		
	  		if(d1.before(date))
	  		{
	  			out.print("Generated revised expiry date["+format2.format(d1)+"] should be greater than today's date");
	  		} else {
	  	  		out.print(format2.format(d1));
	  		}
	  	//return mapping.findForward("success1");*/
	  		return null;
	  }
	 //DKR  
	   public ActionForward submitSSIDetailForTenure(
               ActionMapping mapping,
               ActionForm form,
               HttpServletRequest request,
               HttpServletResponse response)
               throws Exception {

		GMActionForm gmActionForm = (GMActionForm) form;
		User user = getUserInformation(request);
		String memberId = gmActionForm.getMemberIdForClosure();

		String bankId = memberId.substring(0, 4);
		String zoneId = memberId.substring(4, 8);
		String branchId = memberId.substring(8, 12);

		String reviseOfTenure = gmActionForm.getReviseOfTenure();
		String modificationOfRemarks = gmActionForm.getModificationOfRemarks();
		String cgpan = gmActionForm.getCgpanForClosure();
		String tenure = gmActionForm.getTenure();
		String userID = user.getUserId();
		String lastDateOfPayment = gmActionForm.getLastDateOfPayment();
		// GST Changes
		String gstNo = gmActionForm.getGstNo();
		String gstState = gmActionForm.getGstState();

		String gstStateCode = gmActionForm.getStateCode();
	
		Connection connection = DBConnection.getConnection(false);
		CallableStatement setTenureDetail = null;
		String exception = "";
		String functionName = null;
		boolean flag=true;
	
		try {
			System.out
					.println("Funcinstenuremodreq called modificationOfRemarks "
							+ modificationOfRemarks);
			//functionName = "{?=call Funcinstenuremodreq(?,?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?)}";
			functionName = "{?=call Funcinstenuremodreq(?,?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?,?)}";
			setTenureDetail = connection.prepareCall(functionName);
			setTenureDetail.registerOutParameter(1, java.sql.Types.INTEGER);
			setTenureDetail.setString(2, bankId);
			setTenureDetail.setString(3, zoneId);
			setTenureDetail.setString(4, branchId);
			setTenureDetail.setString(5, cgpan);
			setTenureDetail.setString(6, tenure);
			setTenureDetail.setString(7, reviseOfTenure);
			setTenureDetail.setString(8, lastDateOfPayment);
			setTenureDetail.setString(9, modificationOfRemarks);
			setTenureDetail.setString(10, userID);
			setTenureDetail.setString(11, gstNo);	
			setTenureDetail.setString(12, gstStateCode);
			/*setTenureDetail.setString(12, branchCode);     wait for dusscussion
			setTenureDetail.setString(11, gstNo);	
			setTenureDetail.setString(13, gstState);	*/		
			
			/*setTenureDetail.registerOutParameter(11, java.sql.Types.VARCHAR);
			setTenureDetail.executeQuery();
			int error = setTenureDetail.getInt(1);
			exception = setTenureDetail.getString(11);*/
			
			setTenureDetail.registerOutParameter(13, java.sql.Types.VARCHAR);
			setTenureDetail.executeQuery();
			int error = setTenureDetail.getInt(1);
			exception = setTenureDetail.getString(13);

			if (error == Constants.FUNCTION_FAILURE) {
				setTenureDetail.close();
				setTenureDetail = null;
				connection.rollback();
				flag=false;
				//System.out.println("submitSSIDetailForTenure " + exception);
				throw new DatabaseException("Something ["+exception+"] went wrong, Please contact Support[support@cgtmse.in] team");
			} else {

			}

			gmActionForm.setReviseOfTenure("");
			gmActionForm.setLastDateOfPayment("");
			gmActionForm.setModificationOfRemarks("");
		} catch (SQLException e) {
			LogClass.writeExceptionOnFile(e);
			flag=false;
			try {
				connection.rollback();
			} catch (SQLException ignore) {

			}
			throw new DatabaseException("Something ["+exception+"] went wrong, Please contact Support[support@cgtmse.in] team");

		} finally {

			if(flag==false)
			{
				request.setAttribute("message", "Something ["+exception+"] went wrong, Please contact Support[support@cgtmse.in] team");
			}
			else
			{
				System.out.println("Successfully submitted to checker for approval.  ");
				request.setAttribute("message", "Successfully submitted to checker for approval. ");
			}
			DBConnection.freeConnection(connection);
		}

	//	System.out
	//	.println("submitSSIDetailForTenure called request.setAttribute "
	//			+ request.getAttribute("message"));
		return mapping.findForward("success");

       }    
	   /*  Created by kailash for GST*/
	   public ActionForward getGSTNOTenure(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
	   throws Exception
	 {
	 		Log.log(4, "ApplicationProcessingAction", "getGSTNO", "Entered");
	 	    try{ 
	 	    PrintWriter out = response.getWriter();	
	 	   User user = getUserInformation(request);
    	   String bankId = user.getBankId();
	 		Administrator admin = new Administrator();
	 		GMActionForm dynaForm = (GMActionForm) form;
	 		HttpSession session1 = request.getSession(false);
	 	    String stateCode=request.getParameter("stateCode");	    
	 	    String gstNo=getGst(stateCode,bankId);
	 	   // dynaForm.set("gst",gstNo);
	 		String applicationType = (String) session1.getAttribute("APPLICATION_LOAN_TYPE");        
	         
	 		 out.print(gstNo);	
	 		}catch(Exception e)
	 		{
	 			System.err.println("Exception in ApplicationProcessingAction..."+e);
	 			
	 		}        
	 		
	         return null;
	 }
		private String getGst(String stateCode,String bankId) {
			Connection connection=DBConnection.getConnection();

		    
		    Log.log(Log.INFO, "RegistationDAO", "getGstNo", "Entered");
			String gstNo="";
			PreparedStatement pStmt = null;
			ResultSet rsSet = null;
			if((stateCode!=null||!stateCode.equals("")) &&(bankId!=null || !bankId.equals(""))){
			try {
				String query ="select gst.GST_NO from MEMBER_BANK_STATE_GST gst, state_master sm where gst.ste_code = sm.ste_code AND gst.ste_code= ? and MEM_BNK_ID=? ";	
				pStmt = connection.prepareStatement(query);
				pStmt.setString(1, stateCode);
				pStmt.setString(2, bankId);

				rsSet = pStmt.executeQuery();
				while (rsSet.next()) {		

					gstNo=rsSet.getString(1);
				}
				rsSet.close();
				pStmt.close();
			} catch (Exception exception) {
				Log.logException(exception);
				try {
					throw new DatabaseException(exception.getMessage());
				} catch (DatabaseException e) {
					e.printStackTrace();
				}
			} finally {
				DBConnection.freeConnection(connection);
			}		
			}
			return gstNo;
	}

		public ActionForward updateBorrowerDetails(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			try
			{
		

			DynaActionForm dynaActionForm = (DynaActionForm) form;

			int borrowerRefNo = ((Integer) dynaActionForm.get("borrowerRefNo"))
					.intValue();
			GMProcessor gmProcessor = new GMProcessor();
			SSIDetails ssiDetails = new SSIDetails();
			APForm apform = new APForm();
			BorrowerDetails borrowerDetails = new BorrowerDetails();
			String message = "Successfully submitted to checker for approval";
			BeanUtils.populate(ssiDetails, dynaActionForm.getMap());
			ssiDetails.setBorrowerRefNo(borrowerRefNo);

			String constitutionValue = (String) dynaActionForm
					.get("constitutionOther");
		
			if (dynaActionForm.get("constitution")!=null && dynaActionForm.get("constitution").equals("Others")) {
				ssiDetails.setConstitution(constitutionValue);
			}

			String districtOthersValue = (String) dynaActionForm
					.get("districtOthers");
		
			String udyogAdharNo = (String) dynaActionForm.get("udyogAdharNo");
			String bankAcNo = (String) dynaActionForm.get("bankAcNo");
			String branchName = (String) dynaActionForm.get("branchName");
			apform.setUdyogAdharNo(udyogAdharNo);
			apform.setBankAcNo(bankAcNo);
			apform.setBranchName(branchName);
			String aadharNos = (String) dynaActionForm.get("aadharNos");
			apform.setAadharNos(aadharNos);
			//System.out.println("saveBorrowerDetails " + udyogAdharNo);
		
			if (dynaActionForm.get("district").equals("Others")) {
				
				ssiDetails.setDistrict(districtOthersValue);
			}

			String otherLegalIdValue = (String) dynaActionForm
					.get("otherCpLegalID");
			
			if (dynaActionForm.get("cpLegalID")!=null && dynaActionForm.get("cpLegalID").equals("Others")) {
				LogClass.StepWritter("otherLegalIdValue "+otherLegalIdValue);
				ssiDetails.setCpLegalID(otherLegalIdValue);
			}
			borrowerDetails.setSsiDetails(ssiDetails);
			borrowerDetails.setApform(apform);
			BeanUtils.populate(borrowerDetails, dynaActionForm.getMap());
			User user = getUserInformation(request);
			String userId = user.getUserId();
		
			gmProcessor.updateBorrowerDetailsNew(borrowerDetails, userId);

			request.setAttribute("message", message);
		
			}
			catch(Exception e)
			{
				LogClass.writeExceptionOnFile(e);
				throw new DatabaseException("Unable to update borrower details, Reason :"+e.getMessage());
			}
			return mapping.findForward("success");
		}

		
		
		
		 public ActionForward checkBorrowalApprovalValidation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
		    throws Exception
		  {		
			  	HttpSession session= request.getSession();
		  	//  System.out.println("checkBorrowalApprovalValidation called "+session.getAttribute("BorrowerApprovalRecordSelectionDeci"));
		  	PrintWriter out= response.getWriter();
		  	String message="";
		  	
		  	GMActionForm gmform= (GMActionForm) form;
		  //  System.out.println("checkBorrowalApprovalValidation "+gmform);
		  	String BorrowerApprovalRecordSelectionDeci=(String)session.getAttribute("BorrowerApprovalRecordSelectionDeci");
		  	if(BorrowerApprovalRecordSelectionDeci!=null && BorrowerApprovalRecordSelectionDeci.equalsIgnoreCase("RecordNotSelected"))
		  	{
		  		message="Please select atleast 1 record for borrower approval";
		  	}
		    	out.print(message);

		  		
		  		
		  	  		
		  		
		  	//return mapping.findForward("success1");*/
		  		return null;
		  }
		 
		 public ActionForward fetchTenureApprovalData(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
		    throws Exception
		  {				 
			 try
			 {
				User user = getUserInformation(request);
				String bankId = user.getBankId();
				String zoneId = user.getZoneId();
				String branchId = user.getBranchId();
				String memberId = bankId.concat(zoneId).concat(branchId);
				String remark=request.getParameter("Remark");				
				if(!remark.equals("Reschedulement_Rephasement"))
				{
					remark="";
				}			
				ArrayList<MLIDeatils> objArrayList = displayRequestedForTenureApproval(memberId,remark);		 
				Gson gson = new Gson();
				System.out.println("fetchTenureApprovalData 1 ="+objArrayList);
				JsonElement element = gson.toJsonTree(objArrayList, new TypeToken<List<MLIDeatils>>() {}.getType());
				System.out.println("fetchTenureApprovalData 2 ");
				JsonArray jsonArray = element.getAsJsonArray();
				System.out.println("fetchTenureApprovalData 3 ");
				response.setContentType("application/json");
				System.out.println("fetchTenureApprovalData 4 ");
				response.getWriter().print(jsonArray);
				System.out.println("fetchTenureApprovalData 5 ");
			 }
			 catch(Exception exception)
			 {
				 exception.printStackTrace();
				 throw new DatabaseException(
							"Something ["
									+ exception.getMessage()
									+ "] went wrong, Please contact Support[support@cgtmse.in] team");
			 }
			return null;

		  }
		 
		//new entry outstanding 
		 public ActionForward showOutstandingAmountNew(ActionMapping mapping, ActionForm form,
					HttpServletRequest request, HttpServletResponse response)
					throws Exception {
			 
			 System.out.println("new entry outstanding====");
				Log.log(5, "GMAction", "showApprRegistrationForm", "Entered");
				HttpSession session = request.getSession(false);
				GMActionForm ApprovalForm = (GMActionForm) form;
				User user = getUserInformation(request);
				String bankId = user.getBankId();
				String zoneId = user.getZoneId();
				String branchId = user.getBranchId();
				String memberId = bankId + zoneId + branchId;
				// System.out.println("GMA memberId : "+memberId);
				ArrayList formList = displayOutstandingNewFormList(memberId);

				String forward = "";
				if (formList == null || formList.size() == 0) {
					request.setAttribute("message",
							"No Applications Available For Approval");
					forward = "success";
				} else {
					ApprovalForm.setNpaRegistFormList(formList);
					forward = "npaRegistList";
				}

				Log.log(5, "GMAction", "showApprRegistrationForm", "Exited");
				return mapping.findForward(forward);
			}

			public ArrayList displayOutstandingNewFormList(String memberId)
					throws DatabaseException {
				Log.log(5, "GMAction", "displayNpaRegistrationFormList", "Entered");
				
				ArrayList NpaRegistList = new ArrayList();
				GMActionForm npaRegistForm = null;
				Connection connection = DBConnection.getConnection(false);
				PreparedStatement pst = null;
				ResultSet rs = null;
				Statement stmt = null;

				 //memberId="001900040000";
				//String query = "select MLI_ID,MLI_NAME,MEM_BENEFICIARY,MEM_ACC_NO,MEM_RTGS_NO from  MEMBER_ACCOUNT_INFO  where mli_id ='"+memberId+"'  and  MEM_STATUS='MA' ";
				//String query = "select MLI_ID,MLI_NAME,MEM_BENEFICIARY,MEM_ACC_NO,MEM_RTGS_NO from  cgtsiintranetuser.MEMBER_ACCOUNT_INFO@db_intra  where   MEM_STATUS='MA' ";
				//String query = " select cgpan  from application_detail where cgpan in ('CG200401827TC','CG200401842TC','CG200401850TC' )";
				String query ="SELECT  a.cgpan,s.SSI_UNIT_NAME, NVL (a.APP_REAPPROVE_AMOUNT, a.APP_APPROVED_AMOUNT) guramt "
		+"  FROM application_detail a, member_info m, ssi_detail s,promoter_detail P ,npa_detail_temp n"
		+"  WHERE  s.bid=n.bid and s.SSI_REFERENCE_NUMBER = p.SSI_REFERENCE_NUMBER   " 
		+"    and s.SSI_REFERENCE_NUMBER = a.SSI_REFERENCE_NUMBER "
		+"   AND m.MEM_BNK_ID || m.MEM_ZNE_ID || m.MEM_BRN_ID = "
		+"    a.MEM_BNK_ID || a.MEM_ZNE_ID || a.MEM_BRN_ID "
		+"     AND a.MEM_BNK_ID || a.MEM_ZNE_ID || a.MEM_BRN_ID = '"+memberId+"' "
		+"       AND a.APP_SANCTION_DT >= '01-apr-2018' "
		+"     and a.app_status ='AP' "
		+"     and not exists (select 1 from OUTSTANDING_UPLOAD_NEW un where un.cgpan =a.cgpan)" ;
				System.out.println("query===11111111111111--"+query);
				try {
					 pst = connection.prepareStatement(query);
		    	        pst.setMaxRows(51);
		    	     
		    		  rs = pst.executeQuery();
				
					while (rs.next()) {
						npaRegistForm = new GMActionForm();
						npaRegistForm.setCgpan(rs.getString(1));
						npaRegistForm.setUnitName(rs.getString(2));
						npaRegistForm.setGuaranteeamt(rs.getDouble(3));

						
					

						//npaRegistForm.setEmpLName(rs.getString(5));
						

						

						NpaRegistList.add(npaRegistForm);
					}
					connection.commit();
					//stmt.close();
					//stmt = null;
					rs.close();
					rs = null;
				} catch (Exception sql) {
					sql.printStackTrace();
				} finally {
					DBConnection.freeConnection(connection);
				}
				//System.out.println("GMA displayNpaRegistrationFormList E");
				Log.log(5, "GMAction", "displayNpaRegistrationFormList", "Exited");
				return NpaRegistList;
			}
			
			public ActionForward saveOutstandingAmountNew(ActionMapping mapping,
					ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception {
				
				System.out.println("Entered============= saveOutstandingAmountNew-----");
				
				
				HttpSession session = request.getSession(false);

	            Connection connection = DBConnection.getConnection(false);
				
				Statement statement = null;
				connection.setAutoCommit(false);

		

				ResultSet rs = null;
				GMActionForm gmActionForm = (GMActionForm) form;
				User user = getUserInformation(request);
				String bankId = user.getBankId();
				String zoneId = user.getZoneId();
				String branchId = user.getBranchId();
				String memberId = bankId + zoneId + branchId;
				String userid = user.getUserId();
				 System.out.println("userid====: "+userid);

				String actionType = request.getParameter("action");
				 System.out.println("GMA actionType : "+actionType);

				Map commentIdVal = gmActionForm.getEmpComments();
				System.out.println("GMA commentIdVal : " + commentIdVal);
				Map commentIdVal1 = gmActionForm.getEmpComments1();
				System.out.println("GMA commentIdVal : " + commentIdVal1);
				String checkId[] = gmActionForm.getCheck();
				System.out.println("GMA checkId : " + checkId.length);

				HashMap claimapps = new HashMap();

				Vector appclms = new Vector();

				Vector retclms = new Vector();

				String chSelectId = "";
				String commVal = "";
				String query = "";
			
				Statement str1 = connection.createStatement();
				String query2 = "SELECT  CURRENT_DATE FROM DUAL";
				
				//Statement str2 = connection.createStatement();
				ResultSet rs1 = str1.executeQuery(query2);
				
				Date sysdate = null;

				while (rs1.next()) {

					sysdate = rs1.getDate(1);
					

				}
				
		
			
				System.out.println("sysdate=="+sysdate);
				
			
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			
				  String date = sdf.format((java.sql.Date)sysdate);
					System.out.println("date=="+date);
				  sdf = new SimpleDateFormat("dd-MM-yyyy");
				
				  for (int i = 0; i < checkId.length; i++) {
						chSelectId = checkId[i];
						commVal = (String) commentIdVal.get(chSelectId);
						System.out.println("chSelectId"+chSelectId+"commVal=="+commVal);
					//int qrystatus;
						Connection conn = null;
						CallableStatement stmt = null;
						ArrayList validappsSummaryDetails =new ArrayList();
						if(conn==null){
							   conn = DBConnection.getConnection();
							}
						
						 query = "SELECT COUNT(*) FROM OUTSTANDING_UPLOAD_NEW WHERE CGPAN='"+ chSelectId + "' ";
						System.out.println("testing quryforSelect4 " + query);
						rs = str1.executeQuery(query);
						int	count=0;
						while (rs.next()) {
							count = rs.getInt(1);
						}
						if (count == 0) {
							System.out.println("ENTERED FOR NEW ENTRY====+++" );
						try {					
							
							
							stmt = conn.prepareCall("{?=call FuncOutstandingUploadXls(?,?,?,?,?,?,?)}");
							stmt.registerOutParameter(1, Types.INTEGER);
						    stmt.setString(2, memberId);
							stmt.setString(3, chSelectId);
							stmt.setString(4, commVal);
							stmt.setDate(5, new java.sql.Date( sysdate.getTime()) );
							System.out.println("");
							stmt.setString(6, userid);
							stmt.setDate(7, new java.sql.Date( sysdate.getTime()));
							stmt.registerOutParameter(8, Types.VARCHAR);
							
						 //  System.out.println("uploadOutstandingIntoInterface from dao raju....................."+dupapps);
							stmt.execute();

							int errorcode = stmt.getInt(1);
							//System.out.println("error code:" + errorcode);
							String error = stmt.getString(8);
							if (errorcode == 0) {
								conn.commit();
							} else {
								throw new DatabaseException(error);
							}
						}catch (Exception sql) {
							sql.printStackTrace();
						} finally {
							DBConnection.freeConnection(connection);
						}
						}
						else{
						try{
					String quryforSelect = "update OUTSTANDING_UPLOAD_NEW  set  OUTSTANDING_AMT='"	+ commVal + "'  ,CREATED_MODIFIED_BY='"	+ userid+ "' , CREATED_MODIFIED_DT='"	+ date+ "'   where cgpan='"	+ chSelectId+ "' ";
					
					System.out.println("ENTERED FOR UPDATEING+++" + quryforSelect);
					
				int	qrystatus = str1.executeUpdate(quryforSelect);
				
				System.out.println("qrystatus :"+qrystatus);
				
						}catch (Exception sql) {
							sql.printStackTrace();
						} finally {
							DBConnection.freeConnection(connection);
						}
						}   
					appclms.add(chSelectId);
					
						   

					}
				  return mapping.findForward("success");
			}
		 
			
			public ActionForward outstandingCgpanwise(ActionMapping mapping,
					ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception {
				Log.log(4, "ReportsAction", "applicationHistoryReportDetails",
						"Entered");
				ArrayList applicationHistory = new ArrayList();
				ArrayList cgpans = new ArrayList();
				ApplicationReport appReport = new ApplicationReport();
				DynaActionForm dynaForm = (DynaActionForm) form;
				String cgpan = (String) dynaForm.get("enterCgpan");

				User user = getUserInformation(request);
				String bankId = user.getBankId();
				String zoneId = user.getZoneId();
				String branchId = user.getBranchId();
				String memberId = bankId + zoneId + branchId;
				ReportDAO rpDao = new ReportDAO();

				// cgpans = this.reportManager.getAllCgpans();
				String newCgpan = cgpan.toUpperCase();
				applicationHistory = rpDao.getCgpanHistoryReportDetails(newCgpan);
				dynaForm.set("cgpanHistoryReport", applicationHistory);
				// System.out.println("newCgpan:" + newCgpan);
				//appReport = this.reportManager.getApplicationReportForCgpan(newCgpan);
				String key = appReport.getMemberId();

				if ((key == null) || (key.equals(""))) {
					throw new NoDataException(
							"No Data is available for the values entered, Please Enter Any Other Value ");
				}
				dynaForm.set("applicationReport", appReport);

				return mapping.findForward("success");
			}
			
			public ActionForward showOutstandingAccountDetails(ActionMapping mapping, ActionForm form,
					HttpServletRequest request, HttpServletResponse response)
					throws Exception {
				Log.log(5, "GMAction", "showApprRegistrationForm", "Entered");
				HttpSession session = request.getSession(false);
				GMActionForm ApprovalForm = (GMActionForm) form;
				User user = getUserInformation(request);
				String bankId = user.getBankId();
				String zoneId = user.getZoneId();
				String branchId = user.getBranchId();
				String memberId = bankId + zoneId + branchId;
				// System.out.println("GMA memberId : "+memberId);
				ArrayList formList = displayOutstandingFormList(memberId);

				String forward = "";
				if (formList == null || formList.size() == 0) {
					request.setAttribute("message",
							"No Applications Available For Approval");
					forward = "success";
				} else {
					ApprovalForm.setNpaRegistFormList(formList);
					forward = "npaRegistList";
				}

				Log.log(5, "GMAction", "showApprRegistrationForm", "Exited");
				return mapping.findForward(forward);
			}

			public ArrayList displayOutstandingFormList(String memberId)
					throws DatabaseException {
				Log.log(5, "GMAction", "displayNpaRegistrationFormList", "Entered");
				
				ArrayList NpaRegistList = new ArrayList();
				GMActionForm npaRegistForm = null;
				Connection connection = DBConnection.getConnection(false);

				ResultSet rs = null;
				Statement stmt = null;

				
				//String query = "select MLI_ID,MLI_NAME,MEM_BENEFICIARY,MEM_ACC_NO,MEM_RTGS_NO from  MEMBER_ACCOUNT_INFO  where mli_id ='"+memberId+"'  and  MEM_STATUS='MA' ";
				String query = "select MLI_ID,MLI_NAME,MEM_BENEFICIARY,MEM_ACC_NO,MEM_RTGS_NO from  cgtsiintranetuser.MEMBER_ACCOUNT_INFO@db_intra  where   MEM_STATUS='MA' ";
				//String query = " select cgpan  from application_detail where cgpan in ('CG200401827TC','CG200401842TC','CG200401850TC' )";
				
				try {
					stmt = connection.createStatement();
					rs = stmt.executeQuery((query));
				
					while (rs.next()) {
						npaRegistForm = new GMActionForm();
						npaRegistForm.setMemberId(rs.getString(1));
						

						
					

						//npaRegistForm.setEmpLName(rs.getString(5));
						

						

						NpaRegistList.add(npaRegistForm);
					}
					connection.commit();
					stmt.close();
					stmt = null;
					rs.close();
					rs = null;
				} catch (Exception sql) {
					sql.printStackTrace();
				} finally {
					DBConnection.freeConnection(connection);
				}
				//System.out.println("GMA displayNpaRegistrationFormList E");
				Log.log(5, "GMAction", "displayNpaRegistrationFormList", "Exited");
				return NpaRegistList;
			}
			
			public ActionForward showApprClaimAccountSubmit(ActionMapping mapping,
					ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception {
				
				
				HttpSession session = request.getSession(false);

				Connection connection = DBConnection.getConnection(false);
				
				Statement statement = null;
				connection.setAutoCommit(false);

				ResultSet rs = null;
				GMActionForm gmActionForm = (GMActionForm) form;
				User user = getUserInformation(request);
				String bankId = user.getBankId();
				String zoneId = user.getZoneId();
				String branchId = user.getBranchId();
				String memberId = bankId + zoneId + branchId;
				String userid = user.getUserId();
				 System.out.println("userid====: "+userid);

				String actionType = request.getParameter("action");
				 System.out.println("GMA actionType : "+actionType);

				Map commentIdVal = gmActionForm.getEmpComments();
				System.out.println("GMA commentIdVal : " + commentIdVal);

				String checkId[] = gmActionForm.getCheck();
				System.out.println("GMA checkId : " + checkId.length);

				HashMap claimapps = new HashMap();

				Vector appclms = new Vector();

				Vector retclms = new Vector();

				String chSelectId = "";
				String commVal = "";
				String query = "";
			
				Statement str1 = connection.createStatement();
				String query2 = "SELECT  CURRENT_DATE FROM DUAL";
				
				//Statement str2 = connection.createStatement();
				ResultSet rs1 = str1.executeQuery(query2);
				
				Date sysdate = null;

				while (rs1.next()) {

					sysdate = rs1.getDate(1);
					

				}
			
				System.out.println("sysdate=="+sysdate);
				
			
				SimpleDateFormat sdf = new SimpleDateFormat("dd-MMM-yyyy");
			
				  String date = sdf.format((java.sql.Date)sysdate);
					System.out.println("date=="+date);
				  sdf = new SimpleDateFormat("dd-MM-yyyy");
				
				  for (int i = 0; i < checkId.length; i++) {
						chSelectId = checkId[i];
						commVal = (String) commentIdVal.get(chSelectId);
						System.out.println("chSelectId"+chSelectId+"commVal=="+commVal);
					//int qrystatus;
						
						
					String quryforSelect = "update NEW_OUTSTANDING_CAPTURE  set  MEM_STATUS='CA' ,MEM_REMARKS='"	+ commVal + "'  ,CG_CK_USER_ID='"	+ userid+ "' , CG_CK_MODIFIED_DATE='"	+ date+ "'   where MLI_ID='"	+ chSelectId+ "' ";
					
					System.out.println("testing...1" + quryforSelect);
					
				int	qrystatus = statement.executeUpdate(quryforSelect);
				
				System.out.println("qrystatus :"+qrystatus);

				appclms.add(chSelectId);
					
					      

					}
				return null;
			}
			
			
	public ActionForward cgpanForOutstandingAmount(ActionMapping mapping,
			           ActionForm form,
			           HttpServletRequest request,
			           HttpServletResponse response
			           )throws Exception {


			DynaActionForm dynaForm = (DynaActionForm) form;
			dynaForm.initialize(mapping);
			return mapping.findForward("success");
			}
  
	
	 public ActionForward  modifyOutstandingAmountForCgpan(ActionMapping mapping,
	           ActionForm form,
	           HttpServletRequest request,
	           HttpServletResponse response
	           )throws Exception 
	           {


     DynaActionForm dynaForm     = (DynaActionForm)form;
     String cgpan                = (String) dynaForm.get("enterCgpan");
  
   
     
   
	    ResultSet rs = null;
	    ResultSet res=null;
	    PreparedStatement pst = null;
	    int cnt=0;

    ArrayList aList = new ArrayList();

    Connection connection = DBConnection.getConnection();
	     try { 
   
    //String query = "SELECT MLI_ID, a.CGPAN, SSI_UNIT_NAME, NVL (a.APP_REAPPROVE_AMOUNT, a.APP_APPROVED_AMOUNT) guramt,OUTSTANDING_AMT  FROM OUTSTANDING_UPLOAD_NEW o, application_detail a, ssi_detail s WHERE     a.cgpan = o.cgpan       AND a.SSI_REFERENCE_NUMBER = s.SSI_REFERENCE_NUMBER       AND a.cgpan ='"+ cgpan + "'";
    
    String query = "SELECT MLI_ID, a.CGPAN, SSI_UNIT_NAME, NVL (a.APP_REAPPROVE_AMOUNT, a.APP_APPROVED_AMOUNT) guramt,OUTSTANDING_AMT,OUTSTANDING_DT  FROM OUTSTANDING_UPLOAD_2020_21 o, application_detail a, ssi_detail s WHERE     a.cgpan = o.cgpan       AND a.SSI_REFERENCE_NUMBER = s.SSI_REFERENCE_NUMBER       AND a.cgpan ='"+ cgpan + "'";
    
    System.out.println("query==="+query);
    pst = connection.prepareStatement(query);
    rs = pst.executeQuery(query);
 

    
    
    ApplicationReport appReport = new ApplicationReport();
 
    
	
    while (rs.next()) {
  	
           appReport.setMemberId(rs.getString(1));
           System.out.println("rs.getString(1)==="+rs.getString(1));
           appReport.setCgpan(rs.getString(2));
           appReport.setUnitType(rs.getString(3));
           appReport.setAppGuaranteeFee(rs.getString(4));
           appReport.setOutstandingamount(rs.getString(5));
           appReport.setAddress(rs.getString(6));
        
           aList.add(appReport);
	
			  dynaForm.set("cgpanHistoryReport",aList);
  	  }
  	  
  	
  	  
  	
	

  	     }
	
		 catch (Exception exception) {
		 		Log.logException(exception);
		 		throw new DatabaseException(exception.getMessage());
		 		}
		 	    finally 
		 	    {
		 	    	if(pst != null)
		 	    		pst.close();
		 		DBConnection.freeConnection(connection);
		 		}
		 	    
		 	
		 			
		return mapping.findForward(Constants.SUCCESS);

      

	}
	 
	 public ActionForward saveCgpanReductiondetails(ActionMapping mapping,
	           ActionForm form,
	           HttpServletRequest request,
	           HttpServletResponse response
	           )throws Exception {

		    User user = getUserInformation(request);
			String userid = user.getUserId();
			System.out.println("userid"+userid);
	DynaActionForm dynaForm = (DynaActionForm) form;
	Connection connection = DBConnection.getConnection();
//  ResultSet rs = null;
	  ResultSet rs1 = null;
	  ResultSet res=null;
	  PreparedStatement pst = null;
	
    String outstandingAmount = (String) dynaForm.get("outstanding");
	System.out.println("OUTSTANDING_AMT  is=="+outstandingAmount);
	
	String  cgpan  = (String) dynaForm.get("cgpan");
   System.out.println("cgpan is=="+cgpan);
   
   String   disbursment = request.getParameter("checks");
   Statement str = connection.createStatement();
	 ResultSet rs = null;
		String query3 = "select app_loan_type from application_detail  where cgpan='"+cgpan+"'  ";

		
		 rs = str.executeQuery(query3);

		int oldAmount=0;
		String disb=null;
		String type=null;
      
		while (rs.next()) {

			type = rs.getString(1);
			//disb=rs.getString(2);
			System.out.println("type=="+type);

		}
   if(type.equals("WC")){
	   
	   disbursment="N/A";
   }
			
	String remarks=(String) dynaForm.get("appremarks");
		
	System.out.println("remarks"+remarks);

	
	
	System.out.println("disbursment=="+disbursment);
	
	String remarks1=(String) dynaForm.get("checks");
	
	System.out.println("disbursment=="+remarks1);
	
	if(disbursment.equals("-select-")){
		throw new DatabaseException("Please Select Disbursment Type");
		
	}
	if(outstandingAmount.equals("")||outstandingAmount.equals("null")){
		throw new DatabaseException("Please Enter  Disbursment Amount");
		
	}
	if(remarks.equals("")||remarks.equals("null")){
		throw new DatabaseException("Please Enter  Disbursment Date");
		
	}
	
	double   latestamount=0;
	if(!outstandingAmount.equals("")||!outstandingAmount.equals("null")){
	   latestamount =  (Double.parseDouble(outstandingAmount)) ;
	}
	//int   latestamount = Integer.parseInt("outstandingAmount");
	System.out.println("latestamount===="+latestamount);
	//Statement str = connection.createStatement();
	// ResultSet rs = null;
		String query = "select OUTSTANDING_AMT,DISBURSMENT_TYPE from OUTSTANDING_UPLOAD_2019_20 where cgpan='"+cgpan+"' and  DISBURSMENT_TYPE like '%F%' ";

		
		 rs = str.executeQuery(query);

		
        
		while (rs.next()) {

			oldAmount = rs.getInt(1);
			disb=rs.getString(2);

		}
		if(oldAmount!=0){
		if((latestamount>oldAmount)&&(disb.equals("FULL")||disb.equals("Full")||disb.equals("Full Disbursment"))){
			//System.out.println("DKR++++++++++++++RAJUKKKK");
			throw new MessageException( "In the case of full disbursement for Term Loan,the O/s amount as on Dec 2020 cannot be greater than the amount as fed in the last year.");
		}
		}
	
	
	 
	  
	 
	  
	  try{
		
		
		  String query4 ="update OUTSTANDING_UPLOAD_2020_21  set OUTSTANDING_AMT = '"+ outstandingAmount + "',OUTSTANDING_DT=to_date('"+remarks+"','yyyy-mm-dd'),CREATED_MODIFIED_BY =  '"+ userid + "',DISBURSMENT_TYPE =  '"+ disbursment + "'  where cgpan = '"+ cgpan + "' ";
	 	
	 	
 	     pst = connection.prepareStatement(query4);
	    
          pst.executeUpdate(query4);
	   

	  }
	  
	  
	  catch (Exception exception) {
	 		Log.logException(exception);
	 		throw new DatabaseException(exception.getMessage());
	 		}
	 	    finally 
	 	    {
	 		DBConnection.freeConnection(connection);
	 		}
	
	 
	return mapping.findForward("success");
	 }
	 
	 //For OUTSTANDING NEW
	 
	 public ActionForward cgpanForOutstandingAmount1(ActionMapping mapping,
	           ActionForm form,
	           HttpServletRequest request,
	           HttpServletResponse response
	           )throws Exception {


	DynaActionForm dynaForm = (DynaActionForm) form;
	dynaForm.initialize(mapping);
	return mapping.findForward("success");
	}


	 
	/* public ActionForward  enterOutstandingAmountForCgpan(ActionMapping mapping,
	           ActionForm form,
	           HttpServletRequest request,
	           HttpServletResponse response
	           )throws Exception 
	           {


   DynaActionForm dynaForm     = (DynaActionForm)form;
   String cgpan                = (String) dynaForm.get("enterCgpan");

 
   
 
	    ResultSet rs = null;
	    ResultSet res=null;
	    PreparedStatement pst = null;
	    int cnt=0;

  ArrayList aList = new ArrayList();

  Connection connection = DBConnection.getConnection();
  Statement str1 = connection.createStatement();
  
  String querysr = "SELECT DISBURSMENT_TYPE  FROM OUTSTANDING_UPLOAD_FOR_ALL_FYS WHERE CGPAN='"+ cgpan + "' ";
	System.out.println("testing quryforSelect4 " + querysr);
	rs = str1.executeQuery(querysr);
	String 	disb="";
	while (rs.next()) {
		disb = rs.getString(1);
	}
	
	System.out.println("disb==="+disb);
	if (disb.equals("FULL")|| disb.equals("Full Disbursment")){
		throw new MessageException("Already Fully Disbursment done.");
		
	}
	
  String querys = "SELECT app_loan_type  FROM application_detail WHERE CGPAN='"+ cgpan + "' ";
	System.out.println("testing quryforSelect4 " + querys);
	rs = str1.executeQuery(querys);
	String 	type="";
	while (rs.next()) {
		type = rs.getString(1);
	}
	if(type.equals("TC"))  
	{
	try { 
 
  //String query = "SELECT MLI_ID, a.CGPAN, SSI_UNIT_NAME, NVL (a.APP_REAPPROVE_AMOUNT, a.APP_APPROVED_AMOUNT) guramt,OUTSTANDING_AMT  FROM OUTSTANDING_UPLOAD_NEW o, application_detail a, ssi_detail s WHERE     a.cgpan = o.cgpan       AND a.SSI_REFERENCE_NUMBER = s.SSI_REFERENCE_NUMBER       AND a.cgpan ='"+ cgpan + "'";
  
  //String query = "SELECT MLI_ID, a.CGPAN, SSI_UNIT_NAME, NVL (a.APP_REAPPROVE_AMOUNT, a.APP_APPROVED_AMOUNT) guramt,OUTSTANDING_AMT,OUTSTANDING_DT  FROM OUTSTANDING_UPLOAD_2019_20 o, application_detail a, ssi_detail s WHERE     a.cgpan = o.cgpan       AND a.SSI_REFERENCE_NUMBER = s.SSI_REFERENCE_NUMBER       AND a.cgpan ='"+ cgpan + "'";
  
	String query = "SELECT a.MEM_BNK_ID || a.MEM_ZNE_ID || a.MEM_BRN_ID as memberId, a.cgpan,s.SSI_UNIT_NAME, NVL (a.APP_REAPPROVE_AMOUNT, a.APP_APPROVED_AMOUNT) guramt,p.PMR_BANK_ACCOUNT_NO ,    (SELECT OUTSTANDING_AMT "
		+ " FROM OUTSTANDING_UPLOAD_NEW un "
		+ "WHERE un.cgpan = a.cgpan)as previous_outstanding_amount,"
		+ "(SELECT OUTSTANDING_DT "
		+ "FROM OUTSTANDING_UPLOAD_NEW un "
		+ "  WHERE un.cgpan = a.cgpan)as previous_outstanding_date "
			+ "  FROM application_detail a, member_info m, ssi_detail s,promoter_detail P"
			+ "  WHERE   s.SSI_REFERENCE_NUMBER = p.SSI_REFERENCE_NUMBER   "
			+ "    and s.SSI_REFERENCE_NUMBER = a.SSI_REFERENCE_NUMBER "
			+ "   AND m.MEM_BNK_ID || m.MEM_ZNE_ID || m.MEM_BRN_ID = "
			+ "    a.MEM_BNK_ID || a.MEM_ZNE_ID || a.MEM_BRN_ID "
			+ "    AND  a.app_loan_type = 'TC' "
			+ "     AND  a.cgpan ='"+ cgpan + "' "
			//+ "       AND trunc(a.APP_SANCTION_DT)   between  '01-apr-2000' and '31-dec-2019'  "
		//	+ "      AND trunc(a.APP_APPROVED_DATE_TIME) between  '01-apr-2000' and '31-dec-2019'  "
			+ "     and a.app_status ='AP' ";
			//+ "     and not exists (select 1 from OUTSTANDING_UPLOAD_2019_20 un where un.cgpan =a.cgpan)";
	 //System.out.println("query22222==="+query);
  
  System.out.println("query==="+query);
  pst = connection.prepareStatement(query);
  rs = pst.executeQuery(query);


  
  
  ApplicationReport appReport = new ApplicationReport();

  
	
  while (rs.next()) {
	
         appReport.setMemberId(rs.getString(1));
         System.out.println("rs.getString(1)==="+rs.getString(1));
         
         appReport.setCgpan(rs.getString(2));
         appReport.setUnitType(rs.getString(3));
         appReport.setAppGuaranteeFee(rs.getString(4));
         appReport.setOutstandingamount(rs.getString(5));
         appReport.setAddress(rs.getString(6));
      
         aList.add(appReport);
	
			  dynaForm.set("cgpanHistoryReport",aList);
	  }
	  
	
	  
	
	}

	 
	
		 catch (Exception exception) {
		 		Log.logException(exception);
		 		throw new DatabaseException(exception.getMessage());
		 		}
		 	    finally 
		 	    {
		 	    	if(pst != null)
		 	    		pst.close();
		 		DBConnection.freeConnection(connection);
		 		}
		 	    
	}
	else{
		throw new MessageException("Only For Tc cases");
	}
		 	    
		 	
		 			
		return mapping.findForward(Constants.SUCCESS);

    

	}*/
	 
	 public ActionForward  enterOutstandingAmountForCgpan(ActionMapping mapping,
	           ActionForm form,
	           HttpServletRequest request,
	           HttpServletResponse response
	           )throws Exception 
	           {


 DynaActionForm dynaForm     = (DynaActionForm)form;
 String cgpan                = (String) dynaForm.get("enterCgpan");


 

	    ResultSet rs = null;
	    ResultSet res=null;
	    PreparedStatement pst = null;
	    int cnt=0;

ArrayList aList = new ArrayList();

Connection connection = DBConnection.getConnection();
	     try { 

//String query = "SELECT MLI_ID, a.CGPAN, SSI_UNIT_NAME, NVL (a.APP_REAPPROVE_AMOUNT, a.APP_APPROVED_AMOUNT) guramt,OUTSTANDING_AMT  FROM OUTSTANDING_UPLOAD_NEW o, application_detail a, ssi_detail s WHERE     a.cgpan = o.cgpan       AND a.SSI_REFERENCE_NUMBER = s.SSI_REFERENCE_NUMBER       AND a.cgpan ='"+ cgpan + "'";

//String query = "SELECT MLI_ID, a.CGPAN, SSI_UNIT_NAME, NVL (a.APP_REAPPROVE_AMOUNT, a.APP_APPROVED_AMOUNT) guramt,OUTSTANDING_AMT,OUTSTANDING_DT  FROM OUTSTANDING_UPLOAD_2019_20 o, application_detail a, ssi_detail s WHERE     a.cgpan = o.cgpan       AND a.SSI_REFERENCE_NUMBER = s.SSI_REFERENCE_NUMBER       AND a.cgpan ='"+ cgpan + "'";

	String query = "SELECT a.MEM_BNK_ID || a.MEM_ZNE_ID || a.MEM_BRN_ID as memberId, a.cgpan,s.SSI_UNIT_NAME, NVL (a.APP_REAPPROVE_AMOUNT, a.APP_APPROVED_AMOUNT) guramt,p.PMR_BANK_ACCOUNT_NO ,    (SELECT OUTSTANDING_AMT "
		+ " FROM OUTSTANDING_UPLOAD_2019_20 un "
		+ "WHERE un.cgpan = a.cgpan)as previous_outstanding_amount,"
		+ "(SELECT OUTSTANDING_DT "
		+ "FROM OUTSTANDING_UPLOAD_2019_20 un "
		+ "  WHERE un.cgpan = a.cgpan)as previous_outstanding_date, "
		+ " (SELECT DISBURSMENT_TYPE  FROM OUTSTANDING_UPLOAD_2019_20 un  WHERE un.cgpan = a.cgpan)AS DISBURSMENT_TYPE"
			+ "  FROM application_detail a, member_info m, ssi_detail s,promoter_detail P"
			+ "  WHERE   s.SSI_REFERENCE_NUMBER = p.SSI_REFERENCE_NUMBER   "
			+ "    and s.SSI_REFERENCE_NUMBER = a.SSI_REFERENCE_NUMBER "
			+ "   AND m.MEM_BNK_ID || m.MEM_ZNE_ID || m.MEM_BRN_ID = "
			+ "    a.MEM_BNK_ID || a.MEM_ZNE_ID || a.MEM_BRN_ID "
			+ "     AND  a.cgpan ='"+ cgpan + "' "
			+ "       AND trunc(a.APP_SANCTION_DT)   between  '01-apr-2018' and '31-dec-2020'  "
			+ "      AND trunc(a.APP_APPROVED_DATE_TIME) between  '01-apr-2018' and '31-dec-2020'  "
			+ "     and a.app_status ='AP' "
			+ "     and not exists (select 1 from OUTSTANDING_UPLOAD_2020_21 un where un.cgpan =a.cgpan)";
	 System.out.println("query22222==="+query);

System.out.println("query==="+query);
pst = connection.prepareStatement(query);
rs = pst.executeQuery(query);




ApplicationReport appReport = new ApplicationReport();


	
while (rs.next()) {
	
       appReport.setMemberId(rs.getString(1));
       System.out.println("rs.getString(1)==="+rs.getString(1));
       
       appReport.setCgpan(rs.getString(2));
       appReport.setUnitType(rs.getString(3));
       appReport.setAppGuaranteeFee(rs.getString(4));
       appReport.setOutstandingamount(rs.getString(5));
       appReport.setAddress(rs.getString(6));
       if(rs.getString(7)==null){
      	 appReport.setAppLoanType("NA");
				
			}
			else{
				appReport.setAppLoanType(rs.getString(8));
			}
       System.out.println("rs.getString(7=="+rs.getString(8));
       aList.add(appReport);
	
			  dynaForm.set("cgpanHistoryReport",aList);
	  }
	  
	
	  
	
	

	     }
	
		 catch (Exception exception) {
		 		Log.logException(exception);
		 		throw new DatabaseException(exception.getMessage());
		 		}
		 	    finally 
		 	    {
		 	    	if(pst != null)
		 	    		pst.close();
		 		DBConnection.freeConnection(connection);
		 		}
		 	    
		 	
		 			
		return mapping.findForward(Constants.SUCCESS);

  

	}
	 
	/*public ActionForward saveenterOutstandingAmountForCgpan(ActionMapping mapping,
	           ActionForm form,
	           HttpServletRequest request,
	           HttpServletResponse response
	           )throws Exception {

		 System.out.println("RAJUKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKKK=====");
		    User user = getUserInformation(request);
			String userid = user.getUserId();
			System.out.println("userid"+userid);
	DynaActionForm dynaForm = (DynaActionForm) form;
	
    String outstandingAmount = (String) dynaForm.get("outstanding");
	System.out.println("OUTSTANDING_AMT  is=="+outstandingAmount);
	
	int newoutamt=Integer.parseInt(outstandingAmount);  
	
	 String  cgpan  = (String) dynaForm.get("cgpan");
     System.out.println("cgpan is=="+cgpan);
			
	String remarks=(String) dynaForm.get("appremarks");
	Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(remarks);

	System.out.println("remarks"+remarks);
	
	 java.util.Date todate =(java.util.Date)dynaForm.get("dateOfTheDocument37");
	 System.out.println("todate===="+todate);
	 java.sql.Date raju =(java.sql.Date)dynaForm.get("dateOfTheDocument143");
	
	 System.out.println("todate====raju==="+raju);
	

	SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MMM/yyyy");
	  String sydate1 = sdf1.format((java.util.Date)todate);

	
	
	String   disbursment = request.getParameter("checks");
	
	System.out.println("disbursment=="+disbursment);
	
	String remarks1=(String) dynaForm.get("checks");
	
	System.out.println("disbursment=="+remarks1);
	
	  ResultSet rs = null;
	  ResultSet rs1 = null;
	  ResultSet rs2 = null;
	  ResultSet res=null;
	  PreparedStatement pst = null;
		//User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId + zoneId + branchId;
	  Connection connection = DBConnection.getConnection();
	  Statement str1 = connection.createStatement();
		//Connection conn = null;
		int amount=0;
		int count=0;
		CallableStatement stmt = null;
		ArrayList validappsSummaryDetails =new ArrayList();
		
		
		String query8 = "SELECT  CURRENT_DATE FROM DUAL";

		//Statement str2 = connection.createStatement();
		 rs = str1.executeQuery(query8);

		Date sysdate = null;
		Date oldoutdt = null;

		while (rs.next()) {

			sysdate = rs.getDate(1);
			

		}



		///System.out.println("sysdate=="+sysdate);

		if(newoutamt<=0){
    		throw new MessageException("Outstanding Amount is greater than Zero."+newoutamt);
    	}
		
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
		  String sydate = sdf.format((java.sql.Date)sysdate);
	
		  String outdate = sdf.format((java.sql.Date)raju);
		  

		  if (outdate.compareTo(sydate) > 0) {
	        	throw new MessageException("Outstanding date cannot be greater than today's date.");
	        }
		  
		  if(disbursment.equals("-select-")){
				throw new DatabaseException("Please Select Disbursment Type");
				
			}
		  
		  
		  
		  
		  
		  String query9 = "SELECT count(cgpan) from  OUTSTANDING_UPLOAD_2019_20 WHERE CGPAN='"+ cgpan + "'";

			//Statement str2 = connection.createStatement();
			 rs = str1.executeQuery(query9);

			

			while (rs.next()) {

				count= rs.getInt(1);
				

			}
			
			if(count>0){
				
				
				String queryrs = "SELECT DISBURSMENT_TYPE,OUTSTANDING_AMT  FROM OUTSTANDING_UPLOAD_2019_20 WHERE CGPAN='"+ cgpan + "' and DISBURSMENT_TYPE like '%F%' ";
				System.out.println("tFInal=-====== " + queryrs);
				rs = str1.executeQuery(queryrs);
				String 	olddisburstype="";
				while (rs.next()) {
					olddisburstype = rs.getString(1);
					amount = rs.getInt(2);
					//oldoutdt=rs.getDate(3);
					
				}
				
			if(newoutamt>amount && olddisburstype.equals("FULL")){
	        		throw new MessageException("New outstanding amount cannot be greater than previous outstanding amount in case of full disbursement.previous outstanding amount was "+amount);
	        	}
				
				
			}
			
			Statement str = connection.createStatement();
			String query3 = "select OUTSTANDING_AMT,DISBURSMENT_TYPE from OUTSTANDING_UPLOAD_2019_20 where cgpan='"+cgpan+"' and  DISBURSMENT_TYPE like '%F%' ";

			
			 rs = str.executeQuery(query3);

			int oldAmount=0;
			String disb=null;
        // int latestAmount=0;
			while (rs.next()) {

				oldAmount = rs.getInt(1);
				disb=rs.getString(2);

			}
			if(oldAmount!=0){
			if((newoutamt>oldAmount)&&(disb.equals("FULL")||disb.equals("Full")||disb.equals("Full Disbursment"))){
				//System.out.println("DKR++++++++++++++RAJUKKKK");
				throw new MessageException( "In the case of full disbursement for Term Loan,the O/s amount as on Dec 2020 cannot be greater than the amount as fed in the last year.");
			}
			}
              if(count>0){
				
				
				String queryrs = "SELECT DISBURSMENT_TYPE,OUTSTANDING_AMT  FROM OUTSTANDING_UPLOAD_2019_20 WHERE CGPAN='"+ cgpan + "' and DISBURSMENT_TYPE='Full Disbursment' ";
				System.out.println("tFInal=-====== " + queryrs);
				rs = str1.executeQuery(queryrs);
				int amount1=0;
				String olddisburstype="";
				while (rs.next()) {
					olddisburstype = rs.getString(1);
					amount1 = rs.getInt(2);
					//oldoutdt=rs.getDate(3);
					
				}
				
				if(newoutamt>amount1 && olddisburstype.equals("Full Disbursment") ){
	        		throw new MessageException("New outstanding amount cannot be greater than previous outstanding amount in case of full disbursement.previous outstanding amount was "+amount1);
	        	}
				
				
			}
			
			
			
	if(count>0){
			
		
		String query = "SELECT  OUTSTANDING_DT  FROM OUTSTANDING_UPLOAD_2020_21 WHERE CGPAN='"+ cgpan + "' ";
		System.out.println("tFInal=-====== " + query);
		rs = str1.executeQuery(query);
		String 	disburstype="";
		while (rs.next()) {
			
			oldoutdt=rs.getDate(1);
			
		}
		
		
		  
	
		 String outdate1 = sdf.format((java.sql.Date)oldoutdt);
	     System.out.println("outdate==="+outdate+"sydate==="+sydate);
      
	       
	     if (outdate.compareTo(outdate1) <= 0) {
	        	throw new MessageException("New outstanding date cannot be less than last updated outstanding date.last upadated outstanding date is "+outdate1);
	        }
	 
	        	
	       
	      
	        	
	        }
		
		String queryr = "select MEM_BNK_ID||MEM_ZNE_ID||MEM_BRN_ID as member_id  from application_detail where cgpan='"+cgpan+"' ";

		//Statement str3 = connection.createStatement();
		ResultSet rs3 = str1.executeQuery(queryr);
		String mliid="";


		while (rs3.next()) {

			 mliid = rs3.getString(1);
			System.out.println("cgpans=="+mliid);

		}
			
		try {
			Statement statement = null;
			connection.setAutoCommit(false);

	       
			
					
					Statement stmtd = null;


			
			
		
				PreparedStatement ps = null;
				int no=0;
				 int gstin = 0;				
			
 String qry = "insert into OUTSTANDING_UPLOAD_2020_21" +
            "(MLI_ID,CGPAN,OUTSTANDING_AMT,OUTSTANDING_DT,CREATED_MODIFIED_BY,CREATED_MODIFIED_DT,DISBURSMENT_TYPE)"+
            "values(?,?,?,?,?,?,?)";
    System.out.println("qry===="+qry);
    ps = connection.prepareStatement(qry);
    ps.setString(1, mliid);
    ps.setString(2, cgpan);
    ps.setString(3, outstandingAmount);
    ps.setDate(4,(java.sql.Date) raju );
    ps.setString(5, userid);                                    
    ps.setString(6,sydate);
    ps.setString(7,disbursment);
     no = ps.executeUpdate();

  connection.commit();
  
			
			
		
		}catch (Exception sql) {
			sql.printStackTrace();
		} finally {
			DBConnection.freeConnection(connection);
		}
	
	
	 
	return mapping.findForward("success");
	 }*/
	 public ActionForward saveenterOutstandingAmountForCgpan(ActionMapping mapping,
	           ActionForm form,
	           HttpServletRequest request,
	           HttpServletResponse response
	           )throws Exception {
		 System.out.println("saveenterOutstandingAmountForCgpan==========EEEEE");

		    User user = getUserInformation(request);
			String userid = user.getUserId();
			System.out.println("userid"+userid);
	DynaActionForm dynaForm = (DynaActionForm) form;
	
String outstandingAmount = (String) dynaForm.get("outstanding");
	System.out.println("OUTSTANDING_AMT  is=="+outstandingAmount);
	
	String  cgpan  = (String) dynaForm.get("cgpan");
System.out.println("cgpan is=="+cgpan);
			
	String remarks=(String) dynaForm.get("appremarks");
		
	System.out.println("remarks"+remarks);

	String   disbursment = request.getParameter("checks");
	
	System.out.println("disbursment=="+disbursment);
	
	String remarks1=(String) dynaForm.get("checks");
	
	System.out.println("disbursment=="+remarks1);
	
	if(disbursment.equals("-select-")){
		throw new DatabaseException("Please Select Disbursment Type");
		
	}
	if(outstandingAmount.equals("")||outstandingAmount.equals("null")){
		throw new DatabaseException("Please Enter  Disbursment Amount");
		
	}
	double   latestamount=0;
	if(!outstandingAmount.equals("")||!outstandingAmount.equals("null")){
	   latestamount =  (Double.parseDouble(outstandingAmount)) ;
	}
	//int   latestamount = Integer.parseInt("outstandingAmount");
	System.out.println("latestamount===="+latestamount);
	
	  ResultSet rs = null;
	  ResultSet rs1 = null;
	  ResultSet rs2 = null;
	  ResultSet res=null;
	  PreparedStatement pst = null;
		//User user = getUserInformation(request);
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId + zoneId + branchId;
	  Connection connection = DBConnection.getConnection();
	  Statement str1 = connection.createStatement();
		Connection conn = null;
		CallableStatement stmt = null;
		ArrayList validappsSummaryDetails =new ArrayList();
		
		
		String query8 = "SELECT  CURRENT_DATE FROM DUAL";

		//Statement str2 = connection.createStatement();
		 rs = str1.executeQuery(query8);

		Date sysdate = null;

		while (rs.next()) {

			sysdate = rs.getDate(1);
			

		}
		Statement str = connection.createStatement();
		String query3 = "select OUTSTANDING_AMT,DISBURSMENT_TYPE from OUTSTANDING_UPLOAD_2019_20 where cgpan='"+cgpan+"' and  DISBURSMENT_TYPE like '%F%' ";

		
		 rs = str1.executeQuery(query3);

		int oldAmount=0;
		String disb=null;
        
		while (rs.next()) {

			oldAmount = rs.getInt(1);
			disb=rs.getString(2);

		}
		if(oldAmount!=0){
		if((latestamount>oldAmount)&&(disb.equals("FULL")||disb.equals("Full")||disb.equals("Full Disbursment"))){
			//System.out.println("DKR++++++++++++++RAJUKKKK");
			throw new MessageException( "In the case of full disbursement for Term Loan,the O/s amount as on Dec 2020 cannot be greater than the amount as fed in the last year.");
		}
		}


		///System.out.println("sysdate=="+sysdate);


		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
		  String sydate = sdf.format((java.sql.Date)sysdate);

		  String date = sdf.format((java.sql.Date)sysdate);
		if(conn==null){
			   conn = DBConnection.getConnection();
			}
		
		String query = "SELECT COUNT(*) FROM OUTSTANDING_UPLOAD_2020_21 WHERE CGPAN='"+ cgpan + "' ";
		System.out.println("testing quryforSelect4 " + query);
		rs = str1.executeQuery(query);
		int	count=0;
		while (rs.next()) {
			count = rs.getInt(1);
		}
	  if (count == 0) {
			System.out.println("ENTERED FOR NEW ENTRY====+++" );
			
		try {
			Statement statement = null;
			connection.setAutoCommit(false);

	       
					
					Statement stmtd = null;


			
			
		
				PreparedStatement ps = null;
				int no=0;
				 int gstin = 0;				
			
String qry = "insert into OUTSTANDING_UPLOAD_2020_21" +
          "(MLI_ID,CGPAN,OUTSTANDING_AMT,OUTSTANDING_DT,CREATED_MODIFIED_BY,CREATED_MODIFIED_DT,DISBURSMENT_TYPE)"+
          "values(?,?,?,?,?,?,?)";
  System.out.println("qry===="+qry);
  ps = connection.prepareStatement(qry);
  ps.setString(1, memberId);
  ps.setString(2, cgpan);
  ps.setString(3, outstandingAmount);
  ps.setString(4,date );
  ps.setString(5, userid);                                    
  ps.setString(6,sydate);
  ps.setString(7,disbursment);
   no = ps.executeUpdate();

String     query1 = "update OUTSTANDING_UPLOAD_2020_21  set OUTSTANDING_AMT='"+outstandingAmount+"' , OUTSTANDING_DT=to_date('"+remarks+"','yyyy-mm-dd') where cgpan='"+cgpan+"' ";
 gstin = str1.executeUpdate(query1);
connection.commit();

			
			
		
		}catch (Exception sql) {
			sql.printStackTrace();
		} finally {
			DBConnection.freeConnection(connection);
		}
		}
	  else{
		  
	  }
		
		
	 
	  
	  try{
		
		
		  String query4 ="update OUTSTANDING_UPLOAD_2020_21  set OUTSTANDING_AMT = '"+ outstandingAmount + "',OUTSTANDING_DT=to_date('"+remarks+"','yyyy-mm-dd'),CREATED_MODIFIED_BY =  '"+ userid + "',DISBURSMENT_TYPE =  '"+ disbursment + "'  where cgpan = '"+ cgpan + "' ";
	 	
	 	
	     pst = connection.prepareStatement(query4);
	    
      pst.executeUpdate(query4);
	   

	  }
	  
	  
	  catch (Exception exception) {
	 		Log.logException(exception);
	 		throw new DatabaseException(exception.getMessage());
	 		}
	 	    finally 
	 	    {
	 		DBConnection.freeConnection(connection);
	 		}
	
	 
	return mapping.findForward("success");
	 }


	 
	 //rajuk 
	 
	 
	 public ActionForward getOutstandingAmountUpdate(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			Log.log(4, "ReportsAction", "statusWiseReport", "Entered");

			DynaActionForm dynaForm = (DynaActionForm) form;
			java.util.Date date = new java.util.Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int month = calendar.get(2);
			int day = calendar.get(5);
			month -= 1;
			day += 1;
			calendar.set(2, month);
			calendar.set(5, day);
			java.util.Date prevDate = calendar.getTime();

			GeneralReport generalReport = new GeneralReport();
			generalReport.setDateOfTheDocument14(prevDate);
			generalReport.setDateOfTheDocument15(date);
			BeanUtils.copyProperties(dynaForm, generalReport);
			//Log.log(4, "ReportsAction", "statusWiseReport", "Exited");

			return mapping.findForward("success");
		}
	 
	 //added 09032020
	 public ActionForward getOutstandingAmountUpdateNew(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			Log.log(4, "ReportsAction", "statusWiseReport", "Entered");

			DynaActionForm dynaForm = (DynaActionForm) form;
			java.util.Date date = new java.util.Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(date);
			int month = calendar.get(2);
			int day = calendar.get(5);
			month -= 1;
			day += 1;
			calendar.set(2, month);
			calendar.set(5, day);
			java.util.Date prevDate = calendar.getTime();

			GeneralReport generalReport = new GeneralReport();
			generalReport.setDateOfTheDocument14(prevDate);
			generalReport.setDateOfTheDocument15(date);
			BeanUtils.copyProperties(dynaForm, generalReport);
			//Log.log(4, "ReportsAction", "statusWiseReport", "Exited");

			return mapping.findForward("successsssss");
		}
	 
	 
	 public ActionForward getOutstandingAmountUpdateDetailsNew(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			Log.log(4, "ReportsAction", "statusWiseReportDetails", "Entered");
			ArrayList mliReport = new ArrayList();
			DynaActionForm dynaForm = (DynaActionForm) form;
			java.sql.Date startDate = null;
			java.sql.Date endDate = null;
			String application = (String) dynaForm.get("applicationStatus");

			System.out.println("application=="+application);
			java.util.Date sDate = (java.util.Date) dynaForm
					.get("dateOfTheDocument14");
			String stDate = String.valueOf(sDate);
			if(application.equals("AP")){
			return mapping.findForward("success1");
			}
			else if (application.equals("NE")){
				return mapping.findForward("success2");
				}
			else
				return mapping.findForward("success3");
			
	 }
	 
	 public ActionForward getOutstandingAmountUpdateDetails(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			Log.log(4, "ReportsAction", "statusWiseReportDetails", "Entered");
			ArrayList mliReport = new ArrayList();
			DynaActionForm dynaForm = (DynaActionForm) form;
			java.sql.Date startDate = null;
			java.sql.Date endDate = null;
			String application = (String) dynaForm.get("applicationStatus");

			System.out.println("application=="+application);
			java.util.Date sDate = (java.util.Date) dynaForm
					.get("dateOfTheDocument14");
			String stDate = String.valueOf(sDate);
			if(application.equals("AP")){
			return mapping.findForward("success1");
			        }
			else if (application.equals("NE")){
				return mapping.findForward("success2");
				}
			else
				return mapping.findForward("success3");
			
	 }

	 public ActionForward getcgpanForOutstandingUpdate(ActionMapping mapping, 
	             ActionForm form, 
	             HttpServletRequest request, 
	 			HttpServletResponse response) throws Exception {

	 		RPActionForm rpForm = (RPActionForm) form;
	 		User user = getUserInformation(request);
	 		String bankId = user.getBankId();
	 		String zoneId = user.getZoneId();
	 		String branchId = user.getBranchId();
	 		String memberId = "";
	 		String cgpanCount="";

	 		
	 		
	 		if (bankId.equals("0000")) {
	 			memberId = rpForm.getSelectMember();

	 			
	 			Log.log(5, "RPAction", "getNEFTPendingGFDANs", "mliId = "
	 					+ memberId);

	 			if ((memberId == null) || (memberId.equals(""))) {
	 				
	 				return mapping.findForward("liveafmember");
	 			}
	 			bankId = memberId.substring(0, 4);
	 			zoneId = memberId.substring(4, 8);
	 			branchId = memberId.substring(8, 12);

	 			ClaimsProcessor cpProcessor = new ClaimsProcessor();
	 			Vector memberIds = cpProcessor.getAllMemberIds();
	 			if (!memberIds.contains(memberId)) {
	 				throw new NoMemberFoundException("The Member ID does not exist");
	 			}
	 		}
	 		RpProcessor rpProcessor = new RpProcessor();

	 		ArrayList danSummaries = rpProcessor.displayCgpanforOutstanding(bankId,
	 				zoneId, branchId);
	 	
	 		if (danSummaries.size() == 0) {
	 			rpForm.setSelectMember(null);
	 			throw new MissingDANDetailsException(
	 					"No CGPANS available for OUTSTANDING AMOUNT  Updation");
	 		}

	 		
	 		
	 		rpForm.setDanSummaries(danSummaries);
	 		
	 		rpForm.setBankId(bankId);
	 		rpForm.setZoneId(zoneId);
	 		rpForm.setBranchId(branchId);

	 		Log.log(4, "RPAction", "getNEFTPendingGFDANs", "Exited");
	 		if (rpForm.getSelectMember() != null) {
	 			rpForm.setMemberId(rpForm.getSelectMember());
	 		} else {
	 			rpForm.setMemberId(bankId + zoneId + branchId);
	 		}
	 		rpForm.setSelectMember(null);
	 		return mapping.findForward("outstandingamountsummary");
	 }

	 //rajuk NEW Method
	 
	/* public ActionForward getcgpanForOutstandingUpdateNew(ActionMapping mapping, 
             ActionForm form, 
             HttpServletRequest request, 
 			HttpServletResponse response) throws Exception {

 		RPActionForm rpForm = (RPActionForm) form;
 		User user = getUserInformation(request);
 		String bankId = user.getBankId();
 		String zoneId = user.getZoneId();
 		String branchId = user.getBranchId();
 		String memberId = "";
 		String cgpanCount="";

 		
 		
 		if (bankId.equals("0000")) {
 			memberId = rpForm.getSelectMember();

 			
 			Log.log(5, "RPAction", "getNEFTPendingGFDANs", "mliId = "
 					+ memberId);

 			if ((memberId == null) || (memberId.equals(""))) {
 				
 				return mapping.findForward("liveafmember");
 			}
 			bankId = memberId.substring(0, 4);
 			zoneId = memberId.substring(4, 8);
 			branchId = memberId.substring(8, 12);

 			ClaimsProcessor cpProcessor = new ClaimsProcessor();
 			Vector memberIds = cpProcessor.getAllMemberIds();
 			if (!memberIds.contains(memberId)) {
 				throw new NoMemberFoundException("The Member ID does not exist");
 			}
 		}
 		RpProcessor rpProcessor = new RpProcessor();

 		ArrayList danSummaries = rpProcessor.displayCgpanforOutstandingNew(bankId, zoneId, branchId);
 	
 		if (danSummaries.size() == 0) {
 			rpForm.setSelectMember(null);
 			throw new MissingDANDetailsException(
 					"No CGPANS available for OUTSTANDING AMOUNT  Updation");
 		}

 		
 		
 		rpForm.setDanSummaries(danSummaries);
 		
 		rpForm.setBankId(bankId);
 		rpForm.setZoneId(zoneId);
 		rpForm.setBranchId(branchId);

 		Log.log(4, "RPAction", "getNEFTPendingGFDANs", "Exited");
 		if (rpForm.getSelectMember() != null) {
 			rpForm.setMemberId(rpForm.getSelectMember());
 		} else {
 			rpForm.setMemberId(bankId + zoneId + branchId);
 		}
 		rpForm.setSelectMember(null);
 		return mapping.findForward("outstandingamountsummary");
 }

*/ //rajuk 
	 
	 public ActionForward getcgpanForOutstandingUpdate1(ActionMapping mapping, 
             ActionForm form, 
             HttpServletRequest request, 
 			HttpServletResponse response) throws Exception {

 		RPActionForm rpForm = (RPActionForm) form;
 		User user = getUserInformation(request);
 		String bankId = user.getBankId();
 		String zoneId = user.getZoneId();
 		String branchId = user.getBranchId();
 		String memberId = "";
 		String cgpanCount="";

 		
 		
 		if (bankId.equals("0000")) {
 			memberId = rpForm.getSelectMember();

 			
 			Log.log(5, "RPAction", "getNEFTPendingGFDANs", "mliId = "
 					+ memberId);

 			if ((memberId == null) || (memberId.equals(""))) {
 				
 				return mapping.findForward("liveafmember1");
 			}
 			bankId = memberId.substring(0, 4);
 			zoneId = memberId.substring(4, 8);
 			branchId = memberId.substring(8, 12);

 			ClaimsProcessor cpProcessor = new ClaimsProcessor();
 			Vector memberIds = cpProcessor.getAllMemberIds();
 			if (!memberIds.contains(memberId)) {
 				throw new NoMemberFoundException("The Member ID does not exist");
 			}
 		}
 		RpProcessor rpProcessor = new RpProcessor();

 		ArrayList danSummaries = rpProcessor.displayCgpanforOutstanding1(bankId,
 				zoneId, branchId);
 	
 		if (danSummaries.size() == 0) {
 			rpForm.setSelectMember(null);
 			throw new MissingDANDetailsException(
 					"No CGPANS available for OUTSTANDING AMOUNT  Updation");
 		}

 		
 		
 		rpForm.setDanSummaries(danSummaries);
 		
 		rpForm.setBankId(bankId);
 		rpForm.setZoneId(zoneId);
 		rpForm.setBranchId(branchId);

 		Log.log(4, "RPAction", "getNEFTPendingGFDANs", "Exited");
 		if (rpForm.getSelectMember() != null) {
 			rpForm.setMemberId(rpForm.getSelectMember());
 		} else {
 			rpForm.setMemberId(bankId + zoneId + branchId);
 		}
 		rpForm.setSelectMember(null);
 		return mapping.findForward("outstandingamountsummary1");
 }
	 
	 //new Rajuk
	 public ActionForward getcgpanForOutstandingUpdateNew1(ActionMapping mapping, 
             ActionForm form, 
             HttpServletRequest request, 
 			HttpServletResponse response) throws Exception {

 		RPActionForm rpForm = (RPActionForm) form;
 		User user = getUserInformation(request);
 		String bankId = user.getBankId();
 		String zoneId = user.getZoneId();
 		String branchId = user.getBranchId();
 		String memberId = "";
 		String cgpanCount="";

 		
 		
 		if (bankId.equals("0000")) {
 			memberId = rpForm.getSelectMember();

 			
 			Log.log(5, "RPAction", "getNEFTPendingGFDANs", "mliId = "
 					+ memberId);

 			if ((memberId == null) || (memberId.equals(""))) {
 				
 				return mapping.findForward("liveafmember1");
 			}
 			bankId = memberId.substring(0, 4);
 			zoneId = memberId.substring(4, 8);
 			branchId = memberId.substring(8, 12);

 			ClaimsProcessor cpProcessor = new ClaimsProcessor();
 			Vector memberIds = cpProcessor.getAllMemberIds();
 			if (!memberIds.contains(memberId)) {
 				throw new NoMemberFoundException("The Member ID does not exist");
 			}
 		}
 		RpProcessor rpProcessor = new RpProcessor();

 		ArrayList danSummaries = rpProcessor.displayCgpanforOutstanding1(bankId,
 				zoneId, branchId);
 	
 		if (danSummaries.size() == 0) {
 			rpForm.setSelectMember(null);
 			throw new MissingDANDetailsException(
 					"No CGPANS available for OUTSTANDING AMOUNT  Updation");
 		}

 		
 		
 		rpForm.setDanSummaries(danSummaries);
 		
 		rpForm.setBankId(bankId);
 		rpForm.setZoneId(zoneId);
 		rpForm.setBranchId(branchId);

 		Log.log(4, "RPAction", "getNEFTPendingGFDANs", "Exited");
 		if (rpForm.getSelectMember() != null) {
 			rpForm.setMemberId(rpForm.getSelectMember());
 		} else {
 			rpForm.setMemberId(bankId + zoneId + branchId);
 		}
 		rpForm.setSelectMember(null);
 		return mapping.findForward("outstandingamountsummary1");
 }
	 
	 /*public ActionForward updateOutstandingDetailsEntry(ActionMapping mapping, 
		        ActionForm form, 
		        HttpServletRequest request, 
		        HttpServletResponse response) throws DatabaseException,Exception {
		 
		 System.out.println("ENTERED------------!!!!1111");
		Log.log(4, "GMAction", "getIdForClosureRequestInput", "Entered");

		HttpSession session = request.getSession();
		RPActionForm actionFormobj = (RPActionForm) form;
		String action = request.getParameter("action"); 

		String cgpan="";
		String statecode="";
		String gstupdate="";
		ArrayList cgpanArray = new ArrayList();
		Connection connection = DBConnection.getConnection();
		RpProcessor rpProcessor = new RpProcessor();
		List danSumm = actionFormobj.getDanSummaries();
		int len=danSumm.size();
		Statement str1 = connection.createStatement();
		User user = getUserInformation(request);
		String userId=user.getUserId();
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId.concat(zoneId).concat(branchId);

		Statement str11 = connection.createStatement();
		String query2 = "SELECT  CURRENT_DATE FROM DUAL";

		//Statement str2 = connection.createStatement();
		ResultSet rs1 = str11.executeQuery(query2);

		Date sysdate = null;

		while (rs1.next()) {

			sysdate = rs1.getDate(1);
			

		}



		///System.out.println("sysdate=="+sysdate);


		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
		  String sydate = sdf.format((java.sql.Date)sysdate);

		  String date = sdf.format((java.sql.Date)sysdate);
			System.out.println("date=="+date);
		  sdf = new SimpleDateFormat("dd-MM-yyyy");
		try{
		for(int i=0;i<len ; i++){

			String rqy = request.getParameter("qryRemarks"+i);

			if(rqy!=null && rqy.equalsIgnoreCase("on")){

				DANSummary danSummary = (DANSummary) danSumm.get(i);
				
				 cgpan=danSummary.getCgpan();
				 
				//String disbustype=danSummary.getGstState();
				System.out.println("cgpan===="+cgpan);
				 
				/// System.out.println("disbustype===="+disbustype);
				
				
				String   amount = request.getParameter("gstNo"+i);
				
				
				System.out.println("qamount"+amount);
				
				
				
				
			
				String member=danSummary.getMemberId();
				
				
				String  outstanddate= request.getParameter("actionDate"+i);
				System.out.println("cgpan===="+outstanddate);
				String  disbustype;
				
				String enableSelect = cgpan.substring(cgpan.length() - 2);
				
				System.out.println("enableSelect=="+enableSelect);
				//if(enableSelect.)
				if(request.getParameter("gstState"+i)!=null)
					 disbustype = request.getParameter("gstState"+i);
				
				//System.out.println("disbustype=="+disbustype);
				else
					 disbustype = "N/A";
				
				System.out.println("disbustype>>>"+disbustype);
				
				date="31/DEC/2018";
				
				if(disbustype.equals("-select-")){
					throw new DatabaseException("Please Select Disbursment Type");
					
				}
				
				//Statement str = connection.createStatement();
				//String query3 = "select OUTSTANDING_AMT from OUTSTANDING_UPLOAD_NEW where cgpan='"+cgpan+"' ";

				
				//ResultSet rs = str11.executeQuery(query3);

				//int oldAmount=0;

				while (rs.next()) {

					oldAmount = rs1.getInt(1);
					

				}
				
				if(latestAmount>oldAmount){
					throw new DatabaseException( "NEW OUTSTANDING AMOUNT IS GREATER THAN OLD OUSTANDING AMOUNT.");
				}
				

	
		 		
				  if( !outstanddate.equals("")  && outstanddate!=null && !amount.equals("") && amount!=null && !amount.equals("0") &&  amount!="0" && !disbustype.equals("")  && disbustype!=null)
				 {
					
					 
					     int gstin = 0;
					     int gstin1 = 0;
					     
			
							
							Statement statement = null;
							connection.setAutoCommit(false);

					               Connection conn = null;
									CallableStatement stmt = null;
									
									Statement stmtd = null;


							//ResultSet rs = null;
							
						
								PreparedStatement ps = null;
								int no=0;
					    					
								String qry = "insert into OUTSTANDING_UPLOAD_2019_20" +
											"(MLI_ID,CGPAN,OUTSTANDING_AMT,OUTSTANDING_DT,CREATED_MODIFIED_BY,CREATED_MODIFIED_DT,DISBURSMENT_TYPE)"+
											"values(?,?,?,?,?,?,?)";
									System.out.println("qry===="+qry);
									ps = connection.prepareStatement(qry);
									ps.setString(1, member);
									ps.setString(2, cgpan);
									ps.setString(3, amount);
									ps.setString(4,date );
									ps.setString(5, userId);									
									ps.setString(6,sydate);
									ps.setString(7,disbustype);
									 no = ps.executeUpdate();
								
							      String	 query = "update OUTSTANDING_UPLOAD_2019_20  set OUTSTANDING_AMT='"+amount+"' , OUTSTANDING_DT=to_date('"+outstanddate+"','yyyy-mm-dd') where cgpan='"+cgpan+"' ";
							      gstin = str11.executeUpdate(query);
								  connection.commit();
								  cgpanArray.add(cgpan);
									 
								  request.setAttribute("cgpanArray", cgpanArray);
						 
								}
							
				  
				  else{
						 throw new DatabaseException("Please enter  1)valid Outstanding Amount 2) outstanding date greaterthen sanction date.3)must Selct Disbursment type.(only for TC cases.)");
					 }
				
				 }
				 
			
		    
		    }
		}
		catch (Exception e) {
			Log.logException(e);
			throw new DatabaseException("Please enter  1)valid Outstanding Amount 2) outstanding date greaterthen sanction date.3)must Selct Disbursment type.(only for TC cases.)");
		}


		finally{
		    
		   // System.out.println("raaaaaajjjjj");
			DBConnection.freeConnection(connection);

		    Log.log(Log.INFO, "ClaimAction", "getIdForUpdateGSTINDetails", "Exited");
		    }
		return mapping.findForward("outstandingSummary");
		}*/
	 
	 public ActionForward updateOutstandingDetailsEntry(ActionMapping mapping, 
		        ActionForm form, 
		        HttpServletRequest request, 
		        HttpServletResponse response) throws DatabaseException,Exception {
		 
		 System.out.println("ENTERED------------!!!!1111");
		Log.log(4, "GMAction", "getIdForClosureRequestInput", "Entered");

		HttpSession session = request.getSession();
		RPActionForm actionFormobj = (RPActionForm) form;
		String action = request.getParameter("action"); 

		String cgpan="";
		String statecode="";
		String gstupdate="";
		ArrayList cgpanArray = new ArrayList();
		Connection connection = DBConnection.getConnection();
		RpProcessor rpProcessor = new RpProcessor();
		List danSumm = actionFormobj.getDanSummaries();
		int len=danSumm.size();
		Statement str1 = connection.createStatement();
		User user = getUserInformation(request);
		String userId=user.getUserId();
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId.concat(zoneId).concat(branchId);

		Statement str11 = connection.createStatement();
		String query2 = "SELECT  CURRENT_DATE FROM DUAL";

		//Statement str2 = connection.createStatement();
		ResultSet rs1 = str11.executeQuery(query2);

		Date sysdate = null;

		while (rs1.next()) {

			sysdate = rs1.getDate(1);
			

		}



		///System.out.println("sysdate=="+sysdate);


		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
		  String sydate = sdf.format((java.sql.Date)sysdate);

		  String date = sdf.format((java.sql.Date)sysdate);
			System.out.println("date=="+date);
		  sdf = new SimpleDateFormat("dd-MM-yyyy");
		try{
		for(int i=0;i<len ; i++){

			String rqy = request.getParameter("qryRemarks"+i);

			if(rqy!=null && rqy.equalsIgnoreCase("on")){

				DANSummary danSummary = (DANSummary) danSumm.get(i);
				
				 cgpan=danSummary.getCgpan();
				 
				//String disbustype=danSummary.getGstState();
				System.out.println("cgpan===="+cgpan);
				 
				/// System.out.println("disbustype===="+disbustype);
				
				
				String   amount = request.getParameter("gstNo"+i);
				
				double   latestamount = Double.parseDouble(request.getParameter("gstNo"+i));
				
				System.out.println("qamount"+amount);
				String member=danSummary.getMemberId();
				String  outstanddate= request.getParameter("actionDate"+i);
				System.out.println("cgpan===="+outstanddate);
				String  disbustype;
				String enableSelect = cgpan.substring(cgpan.length() - 2);
				
				System.out.println("enableSelect=="+enableSelect);
				//if(enableSelect.)
				if(request.getParameter("gstState"+i)!=null)
					 disbustype = request.getParameter("gstState"+i);
				
				//System.out.println("disbustype=="+disbustype);
				else
					 disbustype = "N/A";
				
				System.out.println("disbustype>>>"+disbustype);
				
				date="31/DEC/2020";
				
				if(disbustype.equals("-select-")){
					throw new DatabaseException("Please Select Disbursment Type");
					
				}
				
				Statement str = connection.createStatement();
				String query3 = "select OUTSTANDING_AMT,DISBURSMENT_TYPE from OUTSTANDING_UPLOAD_2019_20 where cgpan='"+cgpan+"' and  DISBURSMENT_TYPE like '%F%' ";

				
				ResultSet rs = str11.executeQuery(query3);

				double oldAmount=0;
				String disb=null;
            // int latestAmount=0;
				while (rs.next()) {

					oldAmount = rs.getInt(1);
					disb=rs.getString(2);

				}
				if(oldAmount!=0){
				if((latestamount>oldAmount)&&(disb.equals("FULL")||disb.equals("Full")||disb.equals("Full Disbursment"))){
					//System.out.println("DKR++++++++++++++RAJUKKKK");
					throw new MessageException( "In the case of full disbursement for Term Loan,the O/s amount as on Dec 2020 cannot be greater than the amount as fed in the last year.");
				}
				}

	
		 		
				  if( !outstanddate.equals("")  && outstanddate!=null && !amount.equals("") && amount!=null && !amount.equals("0") &&  amount!="0" && !disbustype.equals("")  && disbustype!=null)
				 {
					
					 
					     int gstin = 0;
					     int gstin1 = 0;
					     
			
							
							Statement statement = null;
							connection.setAutoCommit(false);

					               Connection conn = null;
									CallableStatement stmt = null;
									
									Statement stmtd = null;


							//ResultSet rs = null;
							
						
								PreparedStatement ps = null;
								int no=0;
					    					
								String qry = "insert into OUTSTANDING_UPLOAD_2020_21" +
											"(MLI_ID,CGPAN,OUTSTANDING_AMT,OUTSTANDING_DT,CREATED_MODIFIED_BY,CREATED_MODIFIED_DT,DISBURSMENT_TYPE)"+
											"values(?,?,?,?,?,?,?)";
									System.out.println("qry===="+qry);
									ps = connection.prepareStatement(qry);
									ps.setString(1, member);
									ps.setString(2, cgpan);
									ps.setString(3, amount);
									ps.setString(4,date );
									ps.setString(5, userId);									
									ps.setString(6,sydate);
									ps.setString(7,disbustype);
									 no = ps.executeUpdate();
								
							      String	 query = "update OUTSTANDING_UPLOAD_2020_21  set OUTSTANDING_AMT='"+amount+"' , OUTSTANDING_DT=to_date('"+outstanddate+"','dd-mm-yyyy') where cgpan='"+cgpan+"' ";
							     gstin = str11.executeUpdate(query);
								  connection.commit();
								  cgpanArray.add(cgpan);
									 
								  request.setAttribute("cgpanArray", cgpanArray);
						 
								}
							
				  
				  else{
						 throw new DatabaseException("Please enter  1)valid Outstanding Amount 2) outstanding date greaterthen sanction date.3)must Selct Disbursment type.(only for TC cases.)");
					 }
				
				 }
				 
			
		    
		    }
		}
		catch (DatabaseException e) {
			Log.logException(e);
			throw new DatabaseException("Please enter  1)valid Outstanding Amount 2) outstanding date greaterthen sanction date.3)must Selct Disbursment type.(only for TC cases.)");
		}


		finally{
		    
		   // System.out.println("raaaaaajjjjj");
			DBConnection.freeConnection(connection);

		    Log.log(Log.INFO, "ClaimAction", "getIdForUpdateGSTINDetails", "Exited");
		    }
		return mapping.findForward("outstandingSummary");
		}
	 
	 
	 public ActionForward updateOutstandingDetailsEntry1(ActionMapping mapping, 
		        ActionForm form, 
		        HttpServletRequest request, 
		        HttpServletResponse response) throws DatabaseException,Exception {
		 
		 System.out.println("ENTERED------------!!!!1111");
		Log.log(4, "GMAction", "getIdForClosureRequestInput", "Entered");

		HttpSession session = request.getSession();
		RPActionForm actionFormobj = (RPActionForm) form;
		String action = request.getParameter("action"); 

		String cgpan="";
		String statecode="";
		String gstupdate="";
		ArrayList cgpanArray = new ArrayList();
		Connection connection = DBConnection.getConnection();
		RpProcessor rpProcessor = new RpProcessor();
		List danSumm = actionFormobj.getDanSummaries();
		int len=danSumm.size();
		Statement str1 = connection.createStatement();
		User user = getUserInformation(request);
		String userId=user.getUserId();
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId.concat(zoneId).concat(branchId);

		Statement str11 = connection.createStatement();
		String query2 = "SELECT  CURRENT_DATE FROM DUAL";

		//Statement str2 = connection.createStatement();
		ResultSet rs1 = str11.executeQuery(query2);

		Date sysdate = null;

		while (rs1.next()) {

			sysdate = rs1.getDate(1);
			

		}



		///System.out.println("sysdate=="+sysdate);


		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
		  String sydate = sdf.format((java.sql.Date)sysdate);

		  String date = sdf.format((java.sql.Date)sysdate);
			System.out.println("date=="+date);
		  sdf = new SimpleDateFormat("dd-MM-yyyy");
		try{
		for(int i=0;i<len ; i++){

			String rqy = request.getParameter("qryRemarks"+i);

			if(rqy!=null && rqy.equalsIgnoreCase("on")){

				DANSummary danSummary = (DANSummary) danSumm.get(i);
				
				 cgpan=danSummary.getCgpan();
				 
				//String disbustype=danSummary.getGstState();
				System.out.println("cgpan===="+cgpan);
				 
				/// System.out.println("disbustype===="+disbustype);
				
				
				String   amount = request.getParameter("gstNo"+i);
				
				
				System.out.println("qamount"+amount);
				
				
				
				
			
				String member=danSummary.getMemberId();
				
				
				String  outstanddate= request.getParameter("actionDate"+i);
				System.out.println("cgpan===="+outstanddate);
				//String sDate1="31/12/1998";
				//Date date1=new SimpleDateFormat("dd-MM-yyyy").parse(outstanddate);
				
				
				

				//System.out.println();
				//Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(outstanddate); 
				
				//System.out.println("date1===OUTNEWWW=="+date1);
				String  disbustype;
				
				String enableSelect = cgpan.substring(cgpan.length() - 2);
				
				System.out.println("enableSelect=="+enableSelect);
				//if(enableSelect.)
				if(request.getParameter("gstState"+i)!=null)
					 disbustype = request.getParameter("gstState"+i);
				
				//System.out.println("disbustype=="+disbustype);
				else
					 disbustype = "N/A";
				
				System.out.println("disbustype>>>"+disbustype);
				
				date="31/DEC/2018";
				
				if(disbustype.equals("-select-")){
					throw new DatabaseException("Please Select Disbursment Type");
					
				}
				if(disbustype.equals("Undisbursed")){
					amount="00";
				}
				
			/*	Statement str = connection.createStatement();
				String query3 = "select APP_SANCTION_DT from application_detail where cgpan='"+cgpan+"' ";

				
				ResultSet rs = str11.executeQuery(query3);

			  
			  Date revivaldate=null;


			  rs = str1.executeQuery(query3);
			 		

			 		while (rs.next()) {

			 			 revivaldate = rs.getDate(1);
			 			
			 		}
			 		SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy");
			 	   
			 	    String Revdate = sm.format(revivaldate);
			 	    */
			 	  // String outstanddate1 = sm.format(outstanddate);
			 	  // Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(outstanddate); 
			 	  //System.out.println("Revdate"+Revdate);
			 	//  System.out.println("outstanddate=="+outstanddate);
			 	  //System.out.println("outstanddate1=="+outstanddate1);
			 	  
			 	// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

			 //	Date date1 = format.parse("outstanddate");
			 	//Date date2 = format.parse("Revdate");
			 	
			 	// System.out.println("date1 OUTDATE"+date1);
			 	//  System.out.println("date2== SANCTIOn"+date2);

			 //	if (date1.compareTo(date2) <= 0) {
			 //	    System.out.println("earlier");
			 //	}
			            
			 	        
	
		 		
				  if( !outstanddate.equals("")  && outstanddate!=null && !amount.equals("") && amount!=null && !amount.equals("0") &&  amount!="0" && !disbustype.equals("")  && disbustype!=null)
				 {
					
					 
					     int gstin = 0;
					     int gstin1 = 0;
					     
			
							
							Statement statement = null;
							connection.setAutoCommit(false);

					               Connection conn = null;
									CallableStatement stmt = null;
									
									Statement stmtd = null;


							//ResultSet rs = null;
							
						
								PreparedStatement ps = null;
								int no=0;
					    					
								String qry = "insert into OUTSTANDING_UPLOAD_2019_20" +
											"(MLI_ID,CGPAN,OUTSTANDING_AMT,OUTSTANDING_DT,CREATED_MODIFIED_BY,CREATED_MODIFIED_DT,DISBURSMENT_TYPE)"+
											"values(?,?,?,?,?,?,?)";
									System.out.println("qry===="+qry);
									ps = connection.prepareStatement(qry);
									ps.setString(1, member);
									ps.setString(2, cgpan);
									ps.setString(3, amount);
									ps.setString(4,date );
									ps.setString(5, userId);									
									ps.setString(6,sydate);
									ps.setString(7,disbustype);
									 no = ps.executeUpdate();
								
							      String	 query = "update OUTSTANDING_UPLOAD_2019_20  set OUTSTANDING_AMT='"+amount+"' , OUTSTANDING_DT=to_date('"+outstanddate+"','dd-mm-yyyy') where cgpan='"+cgpan+"' ";
							     gstin = str11.executeUpdate(query);
								  connection.commit();
								  cgpanArray.add(cgpan);
									 
								  request.setAttribute("cgpanArray", cgpanArray);
						 
								}
							
				  
				  else{
						 throw new DatabaseException("Please enter  1)valid Outstanding Amount 2) outstanding date greaterthen sanction date.3)must Selct Disbursment type.(only for TC cases.)");
					 }
				
				 }
				 
			
		    
		    }
		}
		catch (Exception e) {
			Log.logException(e);
			throw new DatabaseException("Please enter  1)valid Outstanding Amount 2) outstanding date greaterthen sanction date.3)must Selct Disbursment type.(only for TC cases.)");
		}


		finally{
		    
		   // System.out.println("raaaaaajjjjj");
			DBConnection.freeConnection(connection);

		    Log.log(Log.INFO, "ClaimAction", "getIdForUpdateGSTINDetails", "Exited");
		    }
		return mapping.findForward("outstandingSummary");
		}
	 
	 public ActionForward updateOutstandingDetailsEntryNew1(ActionMapping mapping, 
		        ActionForm form, 
		        HttpServletRequest request, 
		        HttpServletResponse response) throws DatabaseException,Exception {
		 
		 System.out.println("ENTERED------------!!!!1111");
		Log.log(4, "GMAction", "getIdForClosureRequestInput", "Entered");

		HttpSession session = request.getSession();
		RPActionForm actionFormobj = (RPActionForm) form;
		String action = request.getParameter("action"); 

		String cgpan="";
		String statecode="";
		String gstupdate="";
		ArrayList cgpanArray = new ArrayList();
		Connection connection = DBConnection.getConnection();
		RpProcessor rpProcessor = new RpProcessor();
		List danSumm = actionFormobj.getDanSummaries();
		int len=danSumm.size();
		Statement str1 = connection.createStatement();
		User user = getUserInformation(request);
		String userId=user.getUserId();
		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId.concat(zoneId).concat(branchId);

		Statement str11 = connection.createStatement();
		String query2 = "SELECT  CURRENT_DATE FROM DUAL";

		//Statement str2 = connection.createStatement();
		ResultSet rs1 = str11.executeQuery(query2);

		Date sysdate = null;

		while (rs1.next()) {

			sysdate = rs1.getDate(1);
			

		}



		///System.out.println("sysdate=="+sysdate);


		SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
		  String sydate = sdf.format((java.sql.Date)sysdate);

		  String date = sdf.format((java.sql.Date)sysdate);
			System.out.println("date=="+date);
		  sdf = new SimpleDateFormat("dd-MM-yyyy");
		try{
		for(int i=0;i<len ; i++){

			String rqy = request.getParameter("qryRemarks"+i);

			if(rqy!=null && rqy.equalsIgnoreCase("on")){

				DANSummary danSummary = (DANSummary) danSumm.get(i);
				
				 cgpan=danSummary.getCgpan();
				 
				//String disbustype=danSummary.getGstState();
				System.out.println("cgpan===="+cgpan);
				 
				/// System.out.println("disbustype===="+disbustype);
				
				
				String   amount = request.getParameter("gstNo"+i);
				
				
				System.out.println("qamount"+amount);
				
				
				
				
			
				String member=danSummary.getMemberId();
				
				
				String  outstanddate= request.getParameter("actionDate"+i);
				System.out.println("cgpan===="+outstanddate);
				//String sDate1="31/12/1998";
				//Date date1=new SimpleDateFormat("dd-MM-yyyy").parse(outstanddate);
				
				
				

				//System.out.println();
				//Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(outstanddate); 
				
				//System.out.println("date1===OUTNEWWW=="+date1);
				String  disbustype;
				
				String enableSelect = cgpan.substring(cgpan.length() - 2);
				
				System.out.println("enableSelect=="+enableSelect);
				//if(enableSelect.)
				if(request.getParameter("gstState"+i)!=null)
					 disbustype = request.getParameter("gstState"+i);
				
				//System.out.println("disbustype=="+disbustype);
				else
					 disbustype = "N/A";
				
				System.out.println("disbustype>>>"+disbustype);
				
				date="31/DEC/2018";
				
				if(disbustype.equals("-select-")){
					throw new DatabaseException("Please Select Disbursment Type");
					
				}
				if(disbustype.equals("Undisbursed")){
					amount="00";
				}
				
			/*	Statement str = connection.createStatement();
				String query3 = "select APP_SANCTION_DT from application_detail where cgpan='"+cgpan+"' ";

				
				ResultSet rs = str11.executeQuery(query3);

			  
			  Date revivaldate=null;


			  rs = str1.executeQuery(query3);
			 		

			 		while (rs.next()) {

			 			 revivaldate = rs.getDate(1);
			 			
			 		}
			 		SimpleDateFormat sm = new SimpleDateFormat("dd-MM-yyyy");
			 	   
			 	    String Revdate = sm.format(revivaldate);
			 	    */
			 	  // String outstanddate1 = sm.format(outstanddate);
			 	  // Date date1=new SimpleDateFormat("dd/MM/yyyy").parse(outstanddate); 
			 	  //System.out.println("Revdate"+Revdate);
			 	//  System.out.println("outstanddate=="+outstanddate);
			 	  //System.out.println("outstanddate1=="+outstanddate1);
			 	  
			 	// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

			 //	Date date1 = format.parse("outstanddate");
			 	//Date date2 = format.parse("Revdate");
			 	
			 	// System.out.println("date1 OUTDATE"+date1);
			 	//  System.out.println("date2== SANCTIOn"+date2);

			 //	if (date1.compareTo(date2) <= 0) {
			 //	    System.out.println("earlier");
			 //	}
			            
			 	        
	
		 		
				  if( !outstanddate.equals("")  && outstanddate!=null && !amount.equals("") && amount!=null && !amount.equals("0") &&  amount!="0" && !disbustype.equals("")  && disbustype!=null)
				 {
					
					 
					     int gstin = 0;
					     int gstin1 = 0;
					     
			
							
							Statement statement = null;
							connection.setAutoCommit(false);

					               Connection conn = null;
									CallableStatement stmt = null;
									
									Statement stmtd = null;


							//ResultSet rs = null;
							
						
								PreparedStatement ps = null;
								int no=0;
					    					
								String qry = "insert into OUTSTANDING_UPLOAD_FOR_ALL" +
											"(MLI_ID,CGPAN,OUTSTANDING_AMT,OUTSTANDING_DT,CREATED_MODIFIED_BY,CREATED_MODIFIED_DT,DISBURSMENT_TYPE)"+
											"values(?,?,?,?,?,?,?)";
									System.out.println("qry===="+qry);
									ps = connection.prepareStatement(qry);
									ps.setString(1, member);
									ps.setString(2, cgpan);
									ps.setString(3, amount);
									ps.setString(4,date );
									ps.setString(5, userId);									
									ps.setString(6,sydate);
									ps.setString(7,disbustype);
									 no = ps.executeUpdate();
								
							      String	 query = "update OUTSTANDING_UPLOAD_FOR_ALL  set OUTSTANDING_AMT='"+amount+"' , OUTSTANDING_DT=to_date('"+outstanddate+"','dd-mm-yyyy') where cgpan='"+cgpan+"' ";
							     gstin = str11.executeUpdate(query);
								  connection.commit();
								  cgpanArray.add(cgpan);
									 
								  request.setAttribute("cgpanArray", cgpanArray);
						 
								}
							
				  
				  else{
						 throw new DatabaseException("Please enter  1)valid Outstanding Amount 2) outstanding date greaterthen sanction date.3)must Selct Disbursment type.(only for TC cases.)");
					 }
				
				 }
				 
			
		    
		    }
		}
		catch (Exception e) {
			Log.logException(e);
			throw new DatabaseException("Please enter  1)valid Outstanding Amount 2) outstanding date greaterthen sanction date.3)must Selct Disbursment type.(only for TC cases.)");
		}


		finally{
		    
		   // System.out.println("raaaaaajjjjj");
			DBConnection.freeConnection(connection);

		    Log.log(Log.INFO, "ClaimAction", "getIdForUpdateGSTINDetails", "Exited");
		    }
		return mapping.findForward("outstandingSummary");
		}
	 /*public ActionForward exportExcelNew(ActionMapping mapping, ActionForm form,
				HttpServletRequest request, HttpServletResponse response)
				throws Exception {
			ArrayList abc = new ArrayList();
			abc.add("milind");
			abc.add("joshi");

			HttpSession session = request.getSession();
			RPActionForm actionFormobj = (RPActionForm) form;
			List danSumm = actionFormobj.getDanSummaries();
			int len=danSumm.size();
			for(int i=0;i<len ; i++){

				String rqy = request.getParameter("qryRemarks"+i);

				if(rqy!=null && rqy.equalsIgnoreCase("on")){

					DANSummary danSummary = (DANSummary) danSumm.get(i);
					
					String  cgpan=danSummary.getStateName();
					
					//String gst = request.getParameter("gstNo"+i);
				
					//String actionDates= request.getParameter("actionDate"+i);
				}
			}
			PreparedStatement makePaymentfinalStmt;
			ResultSet makePaymentFinalResult;
			Double amt = 100.0;
			String ifscCode = "CORP0000633";
			String paymentID = request.getParameter("cgpan");
			String filetype = request.getParameter("fileType");
			System.out.println("filetypes" + filetype);
			Map approveFlags1 = (Map) session.getAttribute("approvedData");
			Map approveFlags = approveFlags1;
			// session.setAttribute("approvedData",null);
			System.out.println("payid " + approveFlags);

			System.out.println("payid value approveFlags " + approveFlags.size());
			Set keys = approveFlags.keySet();
			System.out.println("konkati" + keys);

			User user = getUserInformation(request);
			String userid = user.getUserId();
			String bankid = (user.getBankId()).trim();
			String zoneid = (user.getZoneId()).trim();
			String branchid = (user.getBranchId()).trim();
			String memberId = bankid + zoneid + branchid;

			CallableStatement cStmt = null;
			String errorCode = "";
			ArrayList rpArray = new ArrayList();
			Iterator PaymentIterate = keys.iterator();
			int insdanstatus = 0;
			RPActionForm actionForm = null;

			while (PaymentIterate.hasNext())

			{
				actionForm = new RPActionForm();
				String payids = (String) PaymentIterate.next();

				System.out.println("keys are" + payids);

				String arr[] = payids.split("@");
				System.out.println("Payid=== " + arr[0]);
				actionForm.setPaymentId1(arr[0]);

				actionForm.setAmmount2(Integer.parseInt(arr[1]));
				// actionForm.setAmmount1(amt);
				// System.out.println("Amount=== "+amt);
				actionForm.setVaccno(arr[2]);
				System.out.println("vaccNo=== " + arr[2]);

				actionForm.setIfscCode(ifscCode);
				// actionForm.setPayidcreateddate(new Date().toString());

				rpArray.add(actionForm);
			}

			ExcelCreator excelCreator = new ExcelCreator();
			HSSFWorkbook workbook = excelCreator.createWorkbook(rpArray);
			response.setHeader("Content-Disposition",
					"attachment; filename=PaymentDetails." + filetype);

			ServletOutputStream out = response.getOutputStream();
			workbook.write(out);
			out.flush();
			out.close();
			return mapping.findForward("success");
		}*/

	 
	//Diksha NPA Bulk Upload
	 
	 public ActionForward npaBulkUploadProcess(ActionMapping mapping,
				ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws Exception {
			
			//System.out.println("#ApplicationLogdementInBulkProcess#");
			User user = getUserInformation(request);
			String BankId=user.getBankId();
			String ZoneId=user.getZoneId();
			String BranchId=user.getBranchId();
			
			HashMap UploadStatus = null;
			DynaValidatorActionForm appForm = (DynaValidatorActionForm) form;
			FormFile formFile = (FormFile) appForm.get("filePath");
			String filePath = request.getRealPath("");
			// //System.out.println("File Path"+request.getRealPath(""));
			if (formFile.equals("") || formFile == null || !(formFile.getFileName()).endsWith(".xls")) {
				throw new MessageException("Please attach an excel file with '.xls' only");
			}
			
			Connection conn = DBConnection.getConnection();
			//System.out.println("conn :"+conn);
			NpaBulkUpload BlkUpdObj = new NpaBulkUpload();
			String TableName="NPA_UPLOAD_DETAIL";
			String BulkName="Apps";
			try{
				
			LinkedHashMap<String,TableDetailBean> headerMap = BlkUpdObj.getTableHeaderData(conn,TableName,BulkName);
			System.out.println("##1##");
			UploadStatus = BlkUpdObj.CheckExcelData(formFile,headerMap, TableName, conn,user,BulkName);
			
			}catch(Exception err){
				throw new MessageException("err"+err);
			}		
			//System.out.println("UploadStatus :"+UploadStatus);		
			String fileName = formFile.getFileName();
			
			if(UploadStatus!=null){				
					ArrayList SuccessDataList=(ArrayList)UploadStatus.get("successRecord");
					ArrayList UnSuccessDataList=(ArrayList)UploadStatus.get("unsuccessRecord");
					ArrayList allerrors=(ArrayList)UploadStatus.get("allerror");/**/
					//System.out.println("allerrors:"+allerrors);
					//System.out.println("UnSuccessDataList:"+UnSuccessDataList);				
					HttpSession sess = request.getSession(false);
					request.setAttribute("UploadedStatus",UploadStatus);
					sess.setAttribute("SuccessDataList", SuccessDataList);
					sess.setAttribute("UnSuccessDataList", UnSuccessDataList); 
					sess.setAttribute("Allerrors", allerrors);
			}
			//##
			 return mapping.findForward("success");
		}
	 
	 public ActionForward ExportToFile(ActionMapping mapping,
	            ActionForm form,
	            HttpServletRequest request,
	            HttpServletResponse response
	            )   throws Exception {
	    	OutputStream os = response.getOutputStream();    	
	    	Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			String strDate = sdf.format(cal.getTime());		
	    	////System.out.println("ExportToFile Calling..");    	
	    	String contextPath1 = request.getSession(false).getServletContext().getRealPath("");
	        String contextPath = PropertyLoader.changeToOSpath(contextPath1);        
	        ////System.out.println("contextPath1 :"+contextPath1);
	        ////System.out.println("contextPath :"+contextPath);
	    	HttpSession sess = request.getSession(false);		
	    	String fileType = request.getParameter("fileType");
	    	String FlowLevel = request.getParameter("FlowLevel");    	
	    	//System.out.println("@@@@@@@@@@@@FlowLevel :"+FlowLevel);
			//ArrayList ClmDataList = (ArrayList)sess.getAttribute("ClaimSettledDatalist");
			ArrayList ClmDataList = (ArrayList)sess.getAttribute(FlowLevel);
			//System.out.println("@@@@@@@@@@@@ClmDataList:"+ClmDataList);
			ArrayList HeaderArrLst = (ArrayList)ClmDataList.get(0);
			//System.out.println("@@@@@@@@@@@@HeaderArrLst:"+HeaderArrLst);
			int NoColumn = HeaderArrLst.size();		
			////System.out.println("fileType:"+fileType);		
			if(fileType.equals("CSVType")){
				byte[] b = generateCSV(ClmDataList,NoColumn,contextPath);			
				if (response != null)
				    response.setContentType("APPLICATION/OCTET-STREAM");
				    response.setHeader("Content-Disposition","attachment; filename=ClaimPaymentExcelData"+strDate+".csv");
		            os.write(b);
		            os.flush();			
			}
			if(fileType.equals("XLSType")){
				byte[] b = generateEXL(ClmDataList,NoColumn,contextPath);			
				if (response != null)
				    response.setContentType("APPLICATION/OCTET-STREAM");
				    response.setHeader("Content-Disposition","attachment; filename=ExcelData"+strDate+".xls");
		            os.write(b);
		            os.flush();			
			}
			/*if(fileType.equals("PDFType")){			
				byte[] b = generatePDF(ClmDataList,NoColumn,contextPath);
				if (response != null)
					
				    response.setContentType("APPLICATION/OCTET-STREAM");
				    response.setHeader("Content-Disposition","attachment; filename=ClaimPaymentExcelData"+strDate+".pdf");
		            os.write(b);
		            os.flush();
			}/**/
			return null;
	    }
	    
		public byte[]  generateCSV(ArrayList<ArrayList> ParamDataList, int No_Column,String contextPath)
				throws IOException {
			
			//System.out.println("---generateCSV()---");
			StringBuffer strbuff = new StringBuffer();
			////System.out.println("ParamDataList:" + ParamDataList);
			
			////System.out.println("contextPath :"+contextPath);
	        
	        
			ArrayList<String> rowDataLst = new ArrayList<String>();
			ArrayList<String> HeaderLst = (ArrayList) ParamDataList.get(0);
			ArrayList<ArrayList> RecordWiseLst = (ArrayList) ParamDataList.get(1);
			
			////System.out.println("HeaderLst"+HeaderLst);
			////System.out.println("RecordWiseLst"+RecordWiseLst);
			
			//#### Header List
			for(String headerdata:HeaderLst){
				rowDataLst.add(headerdata);		
				////System.out.println("Loop--headerdata:"+headerdata);
			}
			////System.out.println("rowDataLst:"+rowDataLst);
			//#### Header List
			
			//#### Data List
			for (ArrayList<String> RecordWiseLstObj : RecordWiseLst) {
				////System.out.println("RecordWiseLstObj:"+RecordWiseLstObj);
				for (String SingleRecordDataObj : RecordWiseLstObj) {
					// //System.out.println("DataLstInnerObj :"+SingleRecordDataObj);
					rowDataLst.add(SingleRecordDataObj);
				}
			//	 //System.out.println("DataLstObj :"+DataLstObj);
			}
				////System.out.println("rowDataLst::"+rowDataLst);
			//#### Data List
			
			ArrayList FinalrowDatalist= new ArrayList<String>();
			////System.out.println("1");
			int y = 0;
			////System.out.println("2"+No_Column);
			for (int n = 0; n < rowDataLst.size(); n++) {
				
				if (n % No_Column == 0 && n != 0) {
					FinalrowDatalist.add(rowDataLst.get(n).trim());
					
					FinalrowDatalist.add(n+y, "\n");
			//		//System.out.println("2n value inside if:"+n);
				//	 //System.out.println("n:"+n);
					y++;
				}else{
				//	//System.out.println("2n inside else:"+n);
					   if(null!=rowDataLst.get(n)){
						   
							    if(rowDataLst.get(n).contains(",")){
							    	rowDataLst.set(n, rowDataLst.get(n).replace(",", ";"));
							    }
							    
					   }
					    FinalrowDatalist.add(rowDataLst.get(n));
					
				}
				// //System.out.println("rowDataLst.get "+rowDataLst.get(n)+"    "+n%3);
			}
			// //System.out.println("rowDataLst :"+rowDataLst.toString().replace("\n,","\n"));
			//String tempStr = rowDataLst.toString().replace("\n,", "\n");
			////System.out.println("3");
			
			//System.out.println("FinalrowDatalist :"+FinalrowDatalist.toString());
			
			String tempStr = FinalrowDatalist.toString().replace("\n,", "\n");
			//System.out.println("tempStr :"+tempStr);
			////System.out.println("4");
			// strbuff.append(ParamDataList.toString().substring(2,
			// ParamDataList.toString().length() - 2).replace("endrow,", "\n"));
			strbuff.append(tempStr.substring(1, tempStr.length()-1));
			 //System.out.println("strbuff :"+strbuff);
			////System.out.println("5");
			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
			String strDate = sdf.format(cal.getTime());
			BufferedWriter output = null;
			OutputStream outStrm;
			//File genfile = new File("D:\\GenerateFiles\\SampleFile" + strDate+ ".csv");
			
			File genfile = new File(contextPath+"\\Download\\DataCSVFile"+strDate+ ".csv");
			
			////System.out.println("6");
			output = new BufferedWriter(new FileWriter(genfile));		
			output.write(strbuff.toString());
			////System.out.println("7");
			output.flush();
			output.close();
			////System.out.println("8");
			
			//##
//			FileInputStream fis = new FileInputStream("D:\\GenerateFiles\\SampleFile" + strDate+ ".csv");
			FileInputStream fis = new FileInputStream(contextPath+"\\Download\\DataCSVFile"+strDate+ ".csv");		                                          
			////System.out.println("9");
	        byte b[];
	        int x = fis.available();
	        b = new byte[x];
	       // //System.out.println(" b size"+b.length);
	        
	        fis.read(b);
			//##
			return  b;
			// genfile.setReadOnly();
		}
		
		public ActionForward getBulkUploadTemplate(
				ActionMapping mapping, ActionForm form, HttpServletRequest request,
				HttpServletResponse response) throws MessageException, Exception {
			
			    Connection conn = DBConnection.getConnection();
			    String contextPath1 = request.getSession(false).getServletContext().getRealPath("");
				String contextPath = PropertyLoader.changeToOSpath(contextPath1);
				//System.out.println("####getBulkUploadTemplate#### 1");
				
				
			    OutputStream os = response.getOutputStream();
			    NpaBulkUpload bulkupload = new NpaBulkUpload();
			    String TableName="NPA_UPLOAD_DETAIL";
				String BulkName="Apps";
			    
			    LinkedHashMap<String, TableDetailBean> headerMap = bulkupload.getTableHeaderData(conn, TableName,BulkName);
			    if (headerMap.size()>1) {
			    	//System.out.println("####getBulkUploadTemplate#### 2 ");
					byte[] b = generateTemplateFile(headerMap,contextPath);

					if (response != null){
						response.setContentType("APPLICATION/OCTET-STREAM");
					    response.setHeader("Content-Disposition","attachment; filename=TemplateFile.xls");
					    os.write(b);
					    os.flush();
					    //System.out.println("####getBulkUploadTemplate#### 3 ");
					}
				}    
			    //System.out.println("####getBulkUploadTemplate#### 4");		
			return null;
		}
		
		
		
		 //##
		public byte[] generateTemplateFile(LinkedHashMap<String, TableDetailBean> TableHeaderHashMap,String contextPath) throws IOException {
					    //System.out.println("---generateTemplate()---");
						StringBuffer strbuff = new StringBuffer();
						////System.out.println("ParamDataList:" + ParamDataList);				 
					    HSSFWorkbook workbook = new HSSFWorkbook();
						HSSFSheet sheet = workbook.createSheet("Template");
						// #### Header List Wrinting
						HSSFCellStyle MandatoryCellstyle = workbook.createCellStyle();
						MandatoryCellstyle.setFillForegroundColor(IndexedColors.GOLD.index);
						MandatoryCellstyle.setFillPattern(MandatoryCellstyle.SOLID_FOREGROUND);
						MandatoryCellstyle.setBorderBottom(MandatoryCellstyle.BORDER_THIN);
						MandatoryCellstyle.setBorderTop(MandatoryCellstyle.BORDER_THIN);
						MandatoryCellstyle.setBorderLeft(MandatoryCellstyle.BORDER_THIN);
						MandatoryCellstyle.setBorderRight(MandatoryCellstyle.BORDER_THIN);
						
						
						Row row = sheet.createRow(0);
						int hdcolnum = 0;
						Iterator it = TableHeaderHashMap.entrySet().iterator();
					    while (it.hasNext()) {
					        Map.Entry<String,TableDetailBean> Headerpair = (Map.Entry)it.next();
					        //System.out.println(Headerpair.getKey() + " = " + Headerpair.getValue());
					        Cell cell = row.createCell(hdcolnum);
					        TableDetailBean tabledetailbean =  Headerpair.getValue();
					        if(tabledetailbean.getColumnAllowNullFlag().equals("N")){
						         ////System.out.println("....Setting sytle");
						         cell.setCellValue(Headerpair.getKey().toString());
						         cell.setCellStyle(MandatoryCellstyle);
					        }else{				        
					        	 cell.setCellValue(Headerpair.getKey().toString());
					        }
							hdcolnum++;
					        //it.remove(); // avoids a ConcurrentModificationException
					    }
					    // #### Header List Wrinting
						
						Calendar cal = Calendar.getInstance();
						SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
						String strDate = sdf.format(cal.getTime());
						try {			
							FileOutputStream out = new FileOutputStream(new File(contextPath+ "\\Download\\TemplateNpaDetail.xls"));
							workbook.write(out);
							out.close();
							////System.out.println("Excel written successfully..");
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						} catch (IOException e) {
							e.printStackTrace();
						}		
						FileInputStream fis = new FileInputStream(contextPath+ "\\Download\\TemplateNpaDetail.xls");
						////System.out.println("9");
						byte b[];
						int x = fis.available();
						b = new byte[x];
						// //System.out.println(" b size"+b.length);
						fis.read(b);		
						return b;
	}
		
		public byte[] generateEXL(ArrayList<ArrayList> ParamDataList,int No_Column, String contextPath) throws IOException {
			//System.out.println("---generateEXL()---");
			StringBuffer strbuff = new StringBuffer();
			// //System.out.println("ParamDataList:" + ParamDataList);
			ArrayList<String> rowDataLst = new ArrayList<String>();
			ArrayList<String> HeaderLst = (ArrayList) ParamDataList.get(0);
			ArrayList<ArrayList> RecordWiseLst = (ArrayList) ParamDataList.get(1);

			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("Data1");

			// #### Header List Wrinting
			Row row = sheet.createRow(0);
			int hdcolnum = 0;
			for (String headerdata : HeaderLst) {
				Cell cell = row.createCell(hdcolnum);
				cell.setCellValue(headerdata);
				hdcolnum++;
			}
			// #### Header List Wrinting

			// #### Data List Writing
			int rownum = 1;
			for (ArrayList<String> RecordWiseLstObj : RecordWiseLst) {
				int colnum = 0;
				row = sheet.createRow(rownum);
				for (String SingleRecordDataObj : RecordWiseLstObj) {
					Cell cell = row.createCell(colnum);
					if(null!=SingleRecordDataObj){
						cell.setCellValue(SingleRecordDataObj.trim());
					}else{
						cell.setCellValue(SingleRecordDataObj);
					}
					colnum++;
					rowDataLst.add(SingleRecordDataObj);
				}
				rownum++;
			}
			// #### Data List Writing

			Calendar cal = Calendar.getInstance();
			SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
			String strDate = sdf.format(cal.getTime());
			try {
				FileOutputStream out = new FileOutputStream(new File(contextPath+"\\Download\\DataXLSFile" + strDate + ".xls"));
				workbook.write(out);
				out.close();
				// //System.out.println("Excel written successfully..");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			FileInputStream fis = new FileInputStream(contextPath+ "\\Download\\DataXLSFile" + strDate + ".xls");
			// //System.out.println("9");
			byte b[];
			int x = fis.available();
			b = new byte[x];
			// //System.out.println(" b size"+b.length);
			fis.read(b);
			return b;
		}
	 
		 //WCTL START
		 public ActionForward flaggingWctlRequestInput(ActionMapping mapping, 
	             ActionForm form, 
	             HttpServletRequest request, 
	             HttpServletResponse response) throws Exception {
	 Log.log(4, "GMAction", "getIdForClosureRequestInput", "Entered");

	                   GMProcessor gmProcessor = new GMProcessor();

	                  GMActionForm gmActionForm = (GMActionForm)form;

	User user = getUserInformation(request);

	String bankId = user.getBankId();
	String zoneId = user.getZoneId();
	String branchId = user.getBranchId();
	String memberId = bankId.concat(zoneId).concat(branchId);

	gmActionForm.setBankIdForClosure(bankId);

	if (bankId.equals("0000")) {
	memberId = "";
	}
	gmActionForm.setMemberIdForClosure(memberId);
	gmActionForm.setClosureRemarks("");
	gmActionForm.setCgpanForClosure("");

	Log.log(4, "GMAction", "getIdForClosureRequestInput", "Exited");

	return mapping.findForward("success");
	}
		 
		 public ActionForward flaggingWctlRequestDetails(ActionMapping mapping, ActionForm form,
					HttpServletRequest request, HttpServletResponse response)
					throws Exception {
				Connection connection = DBConnection.getConnection();

				GMProcessor gmProcessor = new GMProcessor();
				GMActionForm gmActionForm = (GMActionForm) form;
				ApplicationReport appReport = new ApplicationReport();
				ReportManager reportManager = new ReportManager();

				String memberId = gmActionForm.getMemberIdForClosure();
				String cgpan = gmActionForm.getCgpanForClosure().toUpperCase();
				String SSI = gmActionForm.getSsiUnitName();
				ResultSet rs=null;
				ResultSet rs1=null;
				PreparedStatement applicationStmt = null;
				String query="select count(cgpan)  from application_detail where cgpan='"+cgpan+"' and MEM_BNK_ID||MEM_ZNE_ID||MEM_BRN_ID='"+memberId+"' ";
				System.out.println("query---"+query);
				String count=null;
				applicationStmt = connection.prepareStatement(query);
				rs1 = applicationStmt.executeQuery(query);
				while (rs1.next()) {
					
					 count=(rs1.getString(1));
					
				}
				
				System.out.println("count---"+count);
				if(count.equals("0")){
					
					throw new DatabaseException("this cgpan does not belongs to this member Id.");	
					
				}
				// System.out.println("Member Id:"+memberId+" Cgpan:"+cgpan);;

				//gmActionForm.setMemberIdForClosure(memberId);
				gmActionForm.setCgpanForClosure(cgpan);
				appReport = applicationReportForCgpan(cgpan);
				gmActionForm.setCgpanForClosure(cgpan);
				gmActionForm.setSsiUnitName(SSI);
				//gmActionForm.setClosureRemarks("");
				//gmActionForm.setSelect("");
				gmActionForm.setApplicationReport(appReport);
				Log.log(Log.INFO, "GMAction", "getIdForClosureRequest", "Exited");
				return mapping.findForward("sucess");
			// memberid="001900040000";
		    }

			
		 

		 public ApplicationReport applicationReportForCgpan(String cgpan)
			throws DatabaseException {
		Log.log(Log.INFO, "ReportDAO", "applicationReportForCgpan", "Entered");
		//ApplicationReport appReport = null;
		ResultSet rs=null;
		ResultSet rs1=null;
		PreparedStatement applicationStmt = null;
		Connection connection = DBConnection.getConnection();
		ApplicationReport appReport = new ApplicationReport();
	    System.out.println("RRR=====+++"+cgpan);
	    
		try {
	            
			String cgpanstatus=null;
			String flagstatus=null;
			
			String query="select count(cgpan)  from APP_CONVERSION_WCTL where cgpan='"+cgpan+"'  ";
			System.out.println("query---"+query);
			String count=null;
			applicationStmt = connection.prepareStatement(query);
			rs1 = applicationStmt.executeQuery(query);
			while (rs1.next()) {
				
				 count=(rs1.getString(1));
				
			}
			
			System.out.println("count---"+count);
			if(!count.equals("0")){
				
				throw new DatabaseException("Already done Flagging of WCTL Approval pending from Checker.");	
				
			}
			
			String query2="select app_status  from application_detail where cgpan='"+cgpan+"' ";
		
			applicationStmt = connection.prepareStatement(query2);
			rs1 = applicationStmt.executeQuery(query2);
			while (rs1.next()) {
				
				cgpanstatus=(rs1.getString(1));
				
			}
			
			System.out.println("cgpanstatus---"+cgpanstatus);
			if(!cgpanstatus.equals("AP")){
				
				throw new DatabaseException("Please enter live cgpan only");	
				
			}
			
			/*try {
			String query3="select status  from APP_CONVERSION_WCTL where cgpan='"+cgpan+"' ";
			
			applicationStmt = connection.prepareStatement(query3);
			rs1 = applicationStmt.executeQuery(query3);
			while (rs1.next()) {
				
				flagstatus=(rs1.getString(1));
				
			}
			
			System.out.println("flagstatus---"+flagstatus);
			if(flagstatus.equals("NE")){
				
				throw new DatabaseException("Already done Flagging of WCTL Approval pending from Checker.");	
				
			}
			} catch (SQLException exception) {
				Log.logException(exception);

				throw new DatabaseException(exception.getMessage());

			}*/
			
			String query11 = "select  cgpan,SSI_UNIT_NAME,MEM_BNK_ID||MEM_ZNE_ID||MEM_BRN_ID as memberID,app_status,DECODE (NVL (app_reapprove_amount, 0),0, app_approved_amount,app_reapprove_amount) Guarantee_amount    from application_detail a,ssi_detail s where a.SSI_REFERENCE_NUMBER=s.SSI_REFERENCE_NUMBER and a.cgpan='"+cgpan+"' ";

			System.out.println("RRR=====+++"+query11);
			
			applicationStmt = connection.prepareStatement(query11);
			rs = applicationStmt.executeQuery(query11);
			 

			    
			    
			   // ApplicationReport appReport = new ApplicationReport();

			 while (rs.next()) {
			
				  appReport.setCgpan(rs.getString(1));
			     // System.out.println( "Date" +applicationResult.getString(1));
				  appReport.setSsiUnitName(rs.getString(2));
				  System.out.println( "Date" +rs.getString(2));
				  appReport.setMemberId(rs.getString(3));
				  appReport.setCgpan(rs.getString(4));
				  appReport.setAppApprovedAmount(rs.getString(5));
				  System.out.println( "Date" +rs.getString(5));
				 //System.out.println( "Status"  +applicationResult.getString(2));
			
				
			}
			 
			 
			 String status=appReport.getCgpan();
			 System.out.println("status=="+status);
			 
			
			} catch (SQLException exception) {
			Log.logException(exception);

			throw new DatabaseException(exception.getMessage());

		} finally {
			DBConnection.freeConnection(connection);
		}
		Log.log(Log.INFO, "ReportDAO", "applicationReportForCgpan", "Exited");
		return appReport;
	}
		 public ActionForward flaggingWctlSave(ActionMapping mapping, 
	             ActionForm form, 
	             HttpServletRequest request, 
	             HttpServletResponse response) throws Exception {
	Log.log(4, "GMAction", "submitClosureDetails", "Entered");

	GMProcessor gmProcessor = new GMProcessor();
	GMDAO gmDAO = new GMDAO();
	ApplicationProcessor appProcessor = new ApplicationProcessor();
	ClosureDetail closureDtl = new ClosureDetail();
	HashMap closureDetails = null;

	GMActionForm gmActionForm = (GMActionForm)form;
	HttpSession session = request.getSession(false);

	User user = getUserInformation(request);

	String userId = user.getUserId();



	String bankId = user.getBankId();
	String zoneId = user.getZoneId();
	String branchId = user.getBranchId();
	String memberId = bankId.concat(zoneId).concat(branchId);

	System.out.println("memberId==="+memberId);



	String cgpan = gmActionForm.getCgpanForClosure().toUpperCase();




	java.sql.Date mlirequesteddt = null;
	java.sql.Date reschduleDate = null;
	java.util.Date sDate = gmActionForm.getclosureDate();
	java.util.Date rDate = gmActionForm.getRescheduledt();

	ResultSet rs=null;
	ResultSet rs1=null;
	PreparedStatement applicationStmt = null;
	Connection connection = DBConnection.getConnection();
	ApplicationReport appReport = new ApplicationReport();


	String stDate = String.valueOf(sDate);
	String rtDate = String.valueOf(rDate);
	if ((stDate == null) || (stDate.equals("")))
	mlirequesteddt = null;
	else if (stDate != null) {
	mlirequesteddt = new java.sql.Date(sDate.getTime());
	}


	/*if ((rtDate == null) || (rtDate.equals("")))
	reschduleDate = null;
	else if (rtDate != null) {
	reschduleDate = new java.sql.Date(rDate.getTime());
	}*/



	java.util.Date toDate = new java.util.Date();
	java.sql.Date sysDate = new java.sql.Date(toDate.getTime());

	String Remarks = "We converted to WCTC on ";


	//Connection connection = DBConnection.getConnection();



	String forward = "";


	ArrayList borrowerIds = new ArrayList();
	borrowerIds = gmProcessor.getBorrowerIds(memberId);


	try{
	Statement statement = null;


	Connection conn = null;
	CallableStatement stmt = null;

	Statement stmtd = null;
	String status="NE";

	Statement str11 = connection.createStatement();
	String query2 = "SELECT  CURRENT_DATE FROM DUAL";

	//Statement str2 = connection.createStatement();
	 rs1 = str11.executeQuery(query2);

	Date sysdate = null;

	while (rs1.next()) {

		sysdate = rs1.getDate(1);
		

	}



	///System.out.println("sysdate=="+sysdate);

	 
	SimpleDateFormat sdf = new SimpleDateFormat("dd/MMM/yyyy");
	  String sydate = sdf.format((java.sql.Date)sysdate);
	//ResultSet rs = null;
	  System.out.println("sysdate"+sysdate);
	  System.out.println("mlirequesteddt"+mlirequesteddt);
	  
	  if (sysdate.compareTo(mlirequesteddt) < 0) {
	      	throw new DatabaseException("Reschdulement date  should be less  than SYSDATE "+mlirequesteddt+".");
	      }


	PreparedStatement ps = null;
	int no=0;

	String qry = "insert into APP_CONVERSION_WCTL" +
	"(MLI_ID,CGPAN,USER_ID,RESCHEDULEMENT_DATE,MLI_REQUESTED_DATE,WC_TO_WCTL,REMARKS,STATUS)"+
	"values(?,?,?,?,?,?,?,?)";
	System.out.println("qry===="+qry);
	ps = connection.prepareStatement(qry);
	ps.setString(1, memberId);
	ps.setString(2, cgpan);				
	ps.setString(3, userId);
	ps.setDate(4, mlirequesteddt );
	ps.setString(5, sydate);
	ps.setString(6, "Y");
	ps.setString(7, Remarks);
	ps.setString(8, status);
	no = ps.executeUpdate();



	}

	catch (Exception e) {
	Log.logException(e);
	throw new DatabaseException("Reschdulement date  should be less  than or equal SYSDATE");
	}


	finally{


	DBConnection.freeConnection(connection);


	}
	request.setAttribute("message", 
	"Successfully done.");
	forward = "success";


	return mapping.findForward(forward);
	}
		 /* RAJUK */

		// wctl checker approval
		 public ActionForward CheckerApprovalflaggingWctlRequest(ActionMapping mapping,
					ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception {

				Log.log(Log.INFO, "ClaimAction", "displayClaimProcessingInput",
				"Entered");
		Connection connection = DBConnection.getConnection();
		// System.out.println("connection success fully");
		HttpSession session = request.getSession();
		PreparedStatement claimsubmitduStmt;
		ResultSet claimsubmitduResult;
		User user = (User) getUserInformation(request);
		String userid = user.getUserId();


		String bankid = (user.getBankId()).trim();
		String zoneid = (user.getZoneId()).trim();
		String branchid = (user.getBranchId()).trim();
		String memberId = bankid + zoneid + branchid;
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Log.log(Log.INFO, "ClaimAction", "displayClaimProcessingInput",
				"Exited");
		ArrayList claimArray = new ArrayList();

		ClaimActionForm claimFormobj = (ClaimActionForm) form;
		
		try {

		

			String query = "select distinct RESCHEDULEMENT_DATE,c.CGPAN,max(decode(APP_MLI_BRANCH_NAME,null,mem_branch_name,APP_MLI_BRANCH_NAME)) branch,ssi_unit_name,sum(DECODE (a.app_reapprove_amount,NULL, a.app_approved_amount, a.app_reapprove_amount))"
				+ "GuaAppAmt , nvl(P.PMR_BANK_ACCOUNT_NO,A.APP_BANK_APP_REF_NO) bank_account_no  from APP_CONVERSION_WCTL c,application_detail a,ssi_detail s ,member_info m,PROMOTER_DETAIL p"
				+ " where   m.mem_bnk_id||m.mem_zne_id||m.mem_brn_id=a.mem_bnk_id||a.mem_zne_id||a.mem_brn_id and a.ssi_reference_number=p.SSI_REFERENCE_NUMBER and a.ssi_reference_number=S.SSI_REFERENCE_NUMBER and a.cgpan=c.cgpan and c.mli_id='" + memberId	+ "' and c.status='NE' group by RESCHEDULEMENT_DATE,c.CGPAN,ssi_unit_name,nvl(P.PMR_BANK_ACCOUNT_NO,A.APP_BANK_APP_REF_NO) ";
			
			 

			System.out.println("query==" + query);
		

			claimsubmitduStmt = connection.prepareStatement(query);
			claimsubmitduResult = claimsubmitduStmt.executeQuery();
			Date claimlodgeddate;
			while (claimsubmitduResult.next()) {
				ClaimActionForm claimForm = new ClaimActionForm();

				claimlodgeddate = claimsubmitduResult.getDate(1);

				claimForm.setDateOfTheDocument36(dateFormat.format(claimlodgeddate));

				claimForm.setClmreferenceNumber(claimsubmitduResult.getString(2));

				claimForm.setBranchname(claimsubmitduResult.getString(3));

				claimForm.setUnitname(claimsubmitduResult.getString(4));

				claimForm.setGuranteeApprovedamt(claimsubmitduResult.getDouble(5));
				
				claimForm.setLocation(claimsubmitduResult.getString(6));

				GeneralReport generalReport = new GeneralReport();
				String claimRefNum = claimsubmitduResult.getString(2);
				generalReport.setClaimRefNum(claimRefNum);
				claimArray.add(claimForm);

			}
			claimFormobj.setClaimDandU(claimArray);
		} catch (Exception exception) {
			Log.logException(exception);
			throw new DatabaseException(exception.getMessage());
		} finally {
			DBConnection.freeConnection(connection);
		}

		return mapping.findForward("displayClaimsApprovalPage");


			}
			
			//wctl checker approval save
			public ActionForward CheckerApprovalSubmitDetails(
					ActionMapping mapping, ActionForm form, HttpServletRequest request,
					HttpServletResponse response) throws Exception {
				
				
				    HttpSession session = request.getSession();
					ClaimActionForm claimFormobj = (ClaimActionForm) form;
					Map approveFlags = claimFormobj.getDuCertifyDecisionYes();
					Map approveFlags1 = claimFormobj.getComments();
					String decision = null;
					String emailid = null;
					String memid = null;

					HashMap claimapps = new HashMap();

					Vector appclms = new Vector();

					Vector retclms = new Vector();

					int ssiReferNumber = 0;
					// String message1="";
					double totGuaAmt = 0.0;
					ResultSet totalGuaAmtRes = null;

					ResultSet ssiRefres = null;

					ResultSet rs = null;
					Statement statement = null;

					if (approveFlags.size() == 0) {
						throw new NoMemberFoundException(
								"Please select atleast one(Accepet or Reject) For approve.");
					}

					Set keys = approveFlags.keySet();
					User user = getUserInformation(request);
					String userid = user.getUserId();
					String bankid = (user.getBankId()).trim();
					String zoneid = (user.getZoneId()).trim();
					String branchid = (user.getBranchId()).trim();
					String memberId = bankid + zoneid + branchid;
					String username = user.getUserId();

					Connection connection = DBConnection.getConnection();
					Statement str1 = connection.createStatement();

					try {

						Iterator clmInterat = keys.iterator();
						int qrystatus;

						while (clmInterat.hasNext())

						{

							String clmRefNumber = (String) clmInterat.next();

							decision = (String) approveFlags.get(clmRefNumber);
							String remarks = (String) approveFlags1.get(clmRefNumber);

							java.util.Date todaydate = new Date();

							
							ResultSet claimviewResult = null;
							Statement claimviewStmt = connection.createStatement();
							ResultSet claimviewResult1 = null;

							

							if (decision.equals("Y")) {
								
								String quryforSelect = "update APP_CONVERSION_WCTL set  "
									+ " STATUS='CA', CHECKER_DONE_BY='"	+ userid + "', CHECKER_DONE_DT=sysdate  where cgpan='" + clmRefNumber + "' ";
							
								System.out.println("testing1" + quryforSelect);
								
								qrystatus = str1.executeUpdate((quryforSelect));
									
								appclms.add(clmRefNumber);
			                            

								}

								else {
									
									
									String quryforSelect3 = "insert into APP_CONVERSION_WCTL_CANC  select * from  APP_CONVERSION_WCTL  where cgpan ='"+ clmRefNumber + "' ";
								    System.out.println("testing1" + quryforSelect3);
								    qrystatus = str1.executeUpdate((quryforSelect3));

								String quryforSelect4 = "delete from  APP_CONVERSION_WCTL  where cgpan ='"+ clmRefNumber + "'";
								System.out.println("testing1" + quryforSelect4);
								qrystatus = str1.executeUpdate((quryforSelect4));
								
							/*	String quryforSelect = "update APP_CONVERSION_WCTL set  "
									+ " STATUS='RE' , CHECKER_DONE_BY='"	+ userid + "', CHECKER_DONE_DT=sysdate  where cgpan='" + clmRefNumber + "' ";
								
										
								System.out.println("testing1" + quryforSelect);*/
								
								//qrystatus = str1.executeUpdate((quryforSelect));
									
										retclms.add(clmRefNumber);
								

								
							} 

								
							

							

							}

						

						claimapps.put("apprvdClaims", appclms);
						claimapps.put("retClaims", retclms);

						claimFormobj.setCgpandetails(claimapps);

						request.setAttribute("claimappsMap", claimapps);

						connection.commit();

					}

					catch (SQLException sqlexception) {
						connection.rollback();
						throw new DatabaseException(sqlexception.getMessage());
					} finally {

						DBConnection.freeConnection(connection);
					}

					
					return mapping.findForward("claimsuccessSummary");
	}
			
			
			
			//NPAMODIFICATION  START
	/* public ActionForward npaModificationRequestInput(ActionMapping mapping, 
		             ActionForm form, 
		             HttpServletRequest request, 
		             HttpServletResponse response) throws Exception {
		 Log.log(4, "GMAction", "getIdForClosureRequestInput", "Entered");

		                   GMProcessor gmProcessor = new GMProcessor();

		                  GMActionForm gmActionForm = (GMActionForm)form;

		User user = getUserInformation(request);

		String bankId = user.getBankId();
		String zoneId = user.getZoneId();
		String branchId = user.getBranchId();
		String memberId = bankId.concat(zoneId).concat(branchId);

		gmActionForm.setBankIdForClosure(bankId);

		if (bankId.equals("0000")) {
		memberId = "";
		}
		gmActionForm.setMemberIdForClosure(memberId);
		gmActionForm.setClosureRemarks("");
		gmActionForm.setCgpanForClosure("");

		Log.log(4, "GMAction", "getIdForClosureRequestInput", "Exited");

		return mapping.findForward("success");
		}
			*/
			 
			 
/* public ActionForward npaModificationRequestDetails(ActionMapping mapping, ActionForm form,
						HttpServletRequest request, HttpServletResponse response)
						throws Exception {
					Connection connection = DBConnection.getConnection();

					GMProcessor gmProcessor = new GMProcessor();
					GMActionForm gmActionForm = (GMActionForm) form;
					ApplicationReport appReport = new ApplicationReport();
					ReportManager reportManager = new ReportManager();

					String memberId = gmActionForm.getMemberIdForClosure();
					String cgpan = gmActionForm.getCgpanForClosure().toUpperCase();
					String SSI = gmActionForm.getSsiUnitName();
					ResultSet rs=null;
					ResultSet rs1=null;
					//memberId="000400400000";
					PreparedStatement applicationStmt = null;
					String query="select count(cgpan)  from application_detail where cgpan='"+cgpan+"' and MEM_BNK_ID||MEM_ZNE_ID||MEM_BRN_ID='"+memberId+"' ";
					System.out.println("query---"+query);
					String count=null;
					applicationStmt = connection.prepareStatement(query);
					rs1 = applicationStmt.executeQuery(query);
					while (rs1.next()) {
						
						 count=(rs1.getString(1));
						
					}
					
					System.out.println("count---"+count);
					if(count.equals("0")){
						
						throw new DatabaseException("this cgpan does not belongs to this member Id.");	
						
					}
					// System.out.println("Member Id:"+memberId+" Cgpan:"+cgpan);;

					//gmActionForm.setMemberIdForClosure(memberId);
					gmActionForm.setCgpanForClosure(cgpan);
					appReport = applicationReportForCgpan(cgpan);
					gmActionForm.setCgpanForClosure(cgpan);
					gmActionForm.setSsiUnitName(SSI);
					//gmActionForm.setClosureRemarks("");
					//gmActionForm.setSelect("");
					gmActionForm.setApplicationReport(appReport);
					Log.log(Log.INFO, "GMAction", "getIdForClosureRequest", "Exited");
					return mapping.findForward("sucess");
				// memberid="001900040000";
			    }*/

 public ActionForward CheckerApprovalnpaModificationRequest(ActionMapping mapping,
						ActionForm form, HttpServletRequest request,
						HttpServletResponse response) throws Exception {

					Log.log(Log.INFO, "ClaimAction", "displayClaimProcessingInput",
					"Entered");
			Connection connection = DBConnection.getConnection();
			// System.out.println("connection success fully");
			HttpSession session = request.getSession();
			PreparedStatement claimsubmitduStmt;
			ResultSet claimsubmitduResult;
			User user = (User) getUserInformation(request);
			String userid = user.getUserId();


			String bankid = (user.getBankId()).trim();
			String zoneid = (user.getZoneId()).trim();
			String branchid = (user.getBranchId()).trim();
			String memberId = bankid + zoneid + branchid;
			
			SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
			Log.log(Log.INFO, "ClaimAction", "displayClaimProcessingInput",
					"Exited");
			ArrayList claimArray = new ArrayList();

			ClaimActionForm claimFormobj = (ClaimActionForm) form;
			
			try {

			
				//String query = "select n.CGPAN,NEW_NTD_NPA_PRINCIPAL_OS_AMT,NEW_NTD_NPA_INTEREST_OS_AMT,NEW_NTD_TOTAL_DISB_AMT from  NPA_MODIFICATION_DETAILS n,application_detail a  where a.cgpan=n.cgpan and  status='MA'  and MEM_BNK_ID||MEM_ZNE_ID||MEM_BRN_ID='"+memberId+" ' ";
	String query = "select n.CGPAN,NTD_PRINCIPAL_REPAY_AMT,NTD_INTEREST_REPAY_AMT,NEW_NTD_NPA_PRINCIPAL_OS_AMT,NEW_NTD_NPA_INTEREST_OS_AMT,NEW_NTD_TOTAL_DISB_AMT from  NPA_MODIFICATION_DETAILS n,application_detail a  where a.cgpan=n.cgpan and  status='MA'  and  MEM_BNK_ID||MEM_ZNE_ID||MEM_BRN_ID='"+memberId+"' ";
	//String query = "select n.CGPAN,NTD_PRINCIPAL_REPAY_AMT,NTD_INTEREST_REPAY_AMT,NEW_NTD_NPA_PRINCIPAL_OS_AMT,NEW_NTD_NPA_INTEREST_OS_AMT,NEW_NTD_TOTAL_DISB_AMT from  NPA_MODIFICATION_DETAILS n,application_detail a  where a.cgpan=n.cgpan and  status='MA'  and  MEM_BNK_ID in('0001','0002') ";
				
				 

				System.out.println("query==" + query);
			

				claimsubmitduStmt = connection.prepareStatement(query);
				claimsubmitduResult = claimsubmitduStmt.executeQuery();
			
				while (claimsubmitduResult.next()) {
					ClaimActionForm claimForm = new ClaimActionForm();

				

					claimForm.setClaimRefNum(claimsubmitduResult.getString(1));

					claimForm.setBranchname(claimsubmitduResult.getString(2));

					claimForm.setUnitname(claimsubmitduResult.getString(3));

									
					claimForm.setLocation(claimsubmitduResult.getString(4));
					claimForm.setZone(claimsubmitduResult.getString(5));
					claimForm.setNameOfOfficial(claimsubmitduResult.getString(6));

					GeneralReport generalReport = new GeneralReport();
					String claimRefNum = claimsubmitduResult.getString(1);
					generalReport.setClaimRefNum(claimRefNum);
					claimArray.add(claimForm);

				}
				claimFormobj.setClaimDandU(claimArray);
			} catch (Exception exception) {
				Log.logException(exception);
				throw new DatabaseException(exception.getMessage());
			} finally {
				DBConnection.freeConnection(connection);
			}

			return mapping.findForward("displayClaimsApprovalPage");


				}
				
				//NPA MODIFICATION checker approval save
    public ActionForward CheckerApprovalNpaModificationSubmitDetails(
						ActionMapping mapping, ActionForm form, HttpServletRequest request,
						HttpServletResponse response) throws Exception {
					
					
					    HttpSession session = request.getSession();
						ClaimActionForm claimFormobj = (ClaimActionForm) form;
						Map approveFlags = claimFormobj.getDuCertifyDecisionYes();
						Map approveFlags1 = claimFormobj.getComments();
						String decision = null;
						String emailid = null;
						String memid = null;

						HashMap claimapps = new HashMap();

						Vector appclms = new Vector();

						Vector retclms = new Vector();

						int ssiReferNumber = 0;
						// String message1="";
						double totGuaAmt = 0.0;
						ResultSet totalGuaAmtRes = null;

						ResultSet ssiRefres = null;

						ResultSet rs = null;
						Statement statement = null;

						if (approveFlags.size() == 0) {
							throw new NoMemberFoundException(
									"Please select atleast one(Accepet or Reject) For approve.");
						}

						Set keys = approveFlags.keySet();
						User user = getUserInformation(request);
						String userid = user.getUserId();
						String bankid = (user.getBankId()).trim();
						String zoneid = (user.getZoneId()).trim();
						String branchid = (user.getBranchId()).trim();
						String memberId = bankid + zoneid + branchid;
						String username = user.getUserId();

						Connection connection = DBConnection.getConnection();
						Statement str1 = connection.createStatement();

						try {

							Iterator clmInterat = keys.iterator();
							int qrystatus;
							

							while (clmInterat.hasNext())

							{

								String clmRefNumber = (String) clmInterat.next();

								decision = (String) approveFlags.get(clmRefNumber);
								String remarks = (String) approveFlags1.get(clmRefNumber);

								java.util.Date todaydate = new Date();

								
								ResultSet claimviewResult = null;
								Statement pst = connection.createStatement();
								//ResultSet rs = null;
								String type=null;

							  	String query1 =	 "select app_loan_type from application_detail   where cgpan='"+ clmRefNumber + "' " ;
						
								  	System.out.println("query1"+query1);
								  	pst = connection.prepareStatement(query1);
								  	rs = pst.executeQuery(query1);



								  	while(rs.next() )
								  	{
								  		 type=rs.getString(1);
								  		// disbamt=rs.getInt(2);
								  		
								  	}

								if (decision.equals("Y") && type.equals("TC")) {
									
									//Connection connection = DBConnection.getConnection();
								//	ResultSet rs = null;
									//PreparedStatement pst = null;
									
								
									
								    String amount1  = null;
									String amount2  = null;
									String amount3  = null;
									String amount4  = null;
									String amount5  = null;
									java.sql.Date date=null;
								    int count=0;

									
								    
								    
									
									  	String querys =	 " select NTD_PRINCIPAL_REPAY_AMT,NTD_INTEREST_REPAY_AMT,NEW_NTD_NPA_PRINCIPAL_OS_AMT,NEW_NTD_NPA_INTEREST_OS_AMT,NEW_NTD_TOTAL_DISB_AMT  from  NPA_MODIFICATION_DETAILS  where cgpan='"+ clmRefNumber + "' " ;
								

								 

										
										  	System.out.println("query5"+querys);
										  	pst = connection.prepareStatement(querys);
										  	rs = pst.executeQuery(querys);



										  	while(rs.next() )
										  	{
										  		amount1=rs.getString(1);
										  		amount2=rs.getString(2);
										  		amount3=rs.getString(3);
										  		amount4=rs.getString(4);
										  		amount5=rs.getString(5);
										  

									
									String quryforSelect = "update NPA_MODIFICATION_DETAILS set  "
										+ " STATUS='CA',  CHECKER_MODIFIED_DATE=sysdate  where cgpan='" + clmRefNumber + "' ";
								
									System.out.println("testing1" + quryforSelect);
									
									qrystatus = str1.executeUpdate((quryforSelect));
									
									String quryfortc = "update npa_tc_detail_temp  set  "
										+ " NTD_PRINCIPAL_REPAY_AMT='"+amount1+"' , NTD_INTEREST_REPAY_AMT='"+amount2+"' ,NTD_NPA_PRINCIPAL_OS_AMT='"+amount3+"' ,NTD_NPA_INTEREST_OS_AMT='"+amount4+"', NTD_TOTAL_DISB_AMT='"+amount5+"'   where cgpan='" + clmRefNumber + "' ";
								
									System.out.println("testing2==" + quryfortc);
									
									qrystatus = str1.executeUpdate((quryfortc));
									
									
                                    // qrystatus = str1.executeUpdate((quryforSelect));
                                     
                                     
									
									
										
									appclms.add(clmRefNumber);
				                            
										  	}
								}
									
								else if(decision.equals("Y") && type.equals("WC"))
								{
									
									
									 String amount1  = null;
										String amount2  = null;
										String amount3  = null;
										java.sql.Date date=null;
									    int count=0;

										String quryforSelect = "update NPA_MODIFICATION_DETAILS set  "
											+ " STATUS='CA',  CHECKER_MODIFIED_DATE=sysdate  where cgpan='" + clmRefNumber + "' ";
									
										System.out.println("testing1" + quryforSelect);
										
										qrystatus = str1.executeUpdate((quryforSelect));
									    
									    
										
										  	String querys =	 " select NEW_NTD_NPA_PRINCIPAL_OS_AMT,NEW_NTD_NPA_INTEREST_OS_AMT,NEW_NTD_TOTAL_DISB_AMT  from  NPA_MODIFICATION_DETAILS  where cgpan='"+ clmRefNumber + "' " ;
									

									 

											
											  	System.out.println("query5"+querys);
											  	pst = connection.prepareStatement(querys);
											  	rs = pst.executeQuery(querys);



											  	while(rs.next() )
											  	{
											  		amount1=rs.getString(1);
											  		amount2=rs.getString(2);
											  		amount3=rs.getString(3);
									String quryforwc = "update npa_wc_detail_temp  set  "
										+ " NWD_NPA_PRINCIPAL_OS_AMT='" + amount1 + "' ,NWD_NPA_INTEREST_OS_AMT='" + amount2 + "'  where cgpan='" + clmRefNumber + "' ";
								
									System.out.println("testing2==" + quryforwc);
									
									qrystatus = str1.executeUpdate((quryforwc));
									appclms.add(clmRefNumber);	
									
											  	}
											
									
								}
											  	
								
									else {
										
										
										String quryforSelect3 = "insert into NPA_MODIFICATION_DETAILS_CANC  select * from  NPA_MODIFICATION_DETAILS  where cgpan ='"+ clmRefNumber + "' ";
									    System.out.println("testing1" + quryforSelect3);
									    qrystatus = str1.executeUpdate((quryforSelect3));

									   String quryforSelect4 = "delete from  NPA_MODIFICATION_DETAILS  where cgpan ='"+ clmRefNumber + "'";
									   System.out.println("testing1" + quryforSelect4);
									   qrystatus = str1.executeUpdate((quryforSelect4));
									
								     
											retclms.add(clmRefNumber);
									

									
								} 

							
								

								

								}

							

							claimapps.put("apprvdClaims", appclms);
							claimapps.put("retClaims", retclms);

							claimFormobj.setCgpandetails(claimapps);

							request.setAttribute("claimappsMap", claimapps);

							connection.commit();

						}

						catch (SQLException sqlexception) {
							connection.rollback();
							throw new DatabaseException(sqlexception.getMessage());
						} finally {

							DBConnection.freeConnection(connection);
						}

						
						return mapping.findForward("claimsuccessSummary");
		}
    
    
  //rajuk
	 public ActionForward npaModificationRequestInput(ActionMapping mapping, 
	           ActionForm form,
	           HttpServletRequest request,
	           HttpServletResponse response
	           )throws Exception {


	DynaActionForm dynaForm = (DynaActionForm) form;
	dynaForm.initialize(mapping);
	return mapping.findForward("success");
	}
	//rajuk
	 
	 
	 public ActionForward npaModificationRequestDetails(ActionMapping mapping,
	           ActionForm form,
	           HttpServletRequest request,
	           HttpServletResponse response
	           )throws Exception 
	           {


      DynaActionForm dynaForm     = (DynaActionForm)form;
      String cgpan                = (String) dynaForm.get("enterCgpan");
   
      System.out.println("cgpan====="+cgpan);  
      
    
	    ResultSet rs = null;
		PreparedStatement pst = null;
		int amount=0;
		int amount1=0;
		int amount2=0;
		int amount3=0;
		int amount4=0;
		 int count=0;
		 String type=null;
     ArrayList aList = new ArrayList();

     Connection connection = DBConnection.getConnection();
     
	     try { 
	    	 
	    		String querys =	"select NTD_PRINCIPAL_REPAY_AMT,NTD_INTEREST_REPAY_AMT,NTD_NPA_PRINCIPAL_OS_AMT,NTD_NPA_INTEREST_OS_AMT,NTD_TOTAL_DISB_AMT  from  npa_tc_detail_temp where cgpan= '"+ cgpan + "' " +
	  			" 	union  all " +
	  			"  select 0,0, NWD_NPA_PRINCIPAL_OS_AMT,NWD_NPA_INTEREST_OS_AMT,0 from  npa_wc_detail_temp where cgpan= '"+ cgpan + "' " ;

	
	  	System.out.println("query5"+querys);
	  	pst = connection.prepareStatement(querys);
	  	rs = pst.executeQuery(querys);



	  	while(rs.next() )
	  	{
	  		  amount=rs.getInt(1);
	  		  amount1=rs.getInt(2);
	  		  amount2=rs.getInt(3);
	  		  amount3=rs.getInt(4);
	  		  amount4=rs.getInt(5);
	  		 System.out.println("amount"+amount);
	  	}
	    	 
		String quer =	"select count(*) from NPA_MODIFICATION_DETAILS where cgpan= '"+ cgpan + "' " ;


	  	pst = connection.prepareStatement(quer);
	  	rs = pst.executeQuery(quer);



	  	while(rs.next() )
	  	{
	  		   count=rs.getInt(1);
	  		 
	  	}
	  	String que =	"select app_loan_type from application_detail where cgpan= '"+ cgpan + "' " ;


	  	pst = connection.prepareStatement(que);
	  	rs = pst.executeQuery(que);



	  	while(rs.next() )
	  	{
	  		   type=rs.getString(1);
	  		 
	  	}
	  	
	  	
	   if((amount==0 || amount1==0 || amount2==0 || amount3==0) && count==0 && type.equals("TC")) {	 
   //"select CGPAN,nvl(WCP_FB_LIMIT_SANCTIONED,0),nvl(WCP_NFB_LIMIT_SANCTIONED,0) from WORKING_CAPITAL_detail where CGPAN='"+ cgpan + "' ";
     
   String query =   " select cgpan,NTD_PRINCIPAL_REPAY_AMT,NTD_INTEREST_REPAY_AMT,NTD_TOTAL_DISB_AMT,NTD_NPA_PRINCIPAL_OS_AMT,NTD_NPA_INTEREST_OS_AMT  from  npa_tc_detail_temp where cgpan='"+ cgpan + "' " +
    	 "union  all " +
    	 "  	 select cgpan,0,0,0,NWD_NPA_PRINCIPAL_OS_AMT,NWD_NPA_INTEREST_OS_AMT  from  npa_wc_detail_temp where cgpan='"+ cgpan + "' ";

     
            
  
     System.out.println("query===1=="+query);
 
     
     ApplicationReport appReport = new ApplicationReport();
 
     pst = connection.prepareStatement(query);
     rs = pst.executeQuery();
 	
     while (rs.next()) {
     	   appReport.setCgpan(rs.getString(1));  
     	  appReport.setPrinciplerepaymentAmt(rs.getString(2));
		  appReport.setPrinciplerepaymentInterest(rs.getString(3));     	   
     	  appReport.setDisbursmentAmt(rs.getString(4));
     	  appReport.setPrincipleoutstandingAmt(rs.getString(5));
   	      appReport.setPrincipleoutstandingInterest(rs.getString(6));
		  
		   
		 
		   
			aList.add(appReport);
     	
     	  
            
     }
		

		 dynaForm.set("cgpanHistoryReport",aList);
		 
		 return mapping.findForward("success1");
		 
	     }
	   
	   else if((amount==0 || amount1==0) && count==0 && type.equals("WC")) {	 
		   //"select CGPAN,nvl(WCP_FB_LIMIT_SANCTIONED,0),nvl(WCP_NFB_LIMIT_SANCTIONED,0) from WORKING_CAPITAL_detail where CGPAN='"+ cgpan + "' ";
		     
		   String query =   " select cgpan,NTD_PRINCIPAL_REPAY_AMT,NTD_INTEREST_REPAY_AMT,NTD_TOTAL_DISB_AMT,NTD_NPA_PRINCIPAL_OS_AMT,NTD_NPA_INTEREST_OS_AMT  from  npa_tc_detail_temp where cgpan='"+ cgpan + "' " +
	    	 "union  all " +
	    	 "  	 select cgpan,0,0,0,NWD_NPA_PRINCIPAL_OS_AMT,NWD_NPA_INTEREST_OS_AMT  from  npa_wc_detail_temp where cgpan='"+ cgpan + "' ";
		            
		  
		     System.out.println("query===1=="+query);
		 
		     
		     ApplicationReport appReport = new ApplicationReport();
		 
		     pst = connection.prepareStatement(query);
		     rs = pst.executeQuery();
		 	
		     while (rs.next()) {
		     	   appReport.setCgpan(rs.getString(1));  
		     	  appReport.setPrinciplerepaymentAmt(rs.getString(2));
				  appReport.setPrinciplerepaymentInterest(rs.getString(3));     	   
		     	  appReport.setDisbursmentAmt(rs.getString(4));
		     	  appReport.setPrincipleoutstandingAmt(rs.getString(5));
		   	      appReport.setPrincipleoutstandingInterest(rs.getString(6));
				  
				   
				 
				   
					aList.add(appReport);
		     	
		     	  
		            
		     }
				

				 dynaForm.set("cgpanHistoryReport",aList);
				 
				 return mapping.findForward("success2");
				 
			     }
	   else {
	    	 throw new Exception("You Canot Update this cgpan. ");
	     }
	   
	     }
	    
	
		 catch (Exception exception) {
		 		Log.logException(exception);
		 		throw new DatabaseException(exception.getMessage());
		 		}
		 	    finally 
		 	    {
		 		DBConnection.freeConnection(connection);
		 		}
		 	    
		 	
		 			
		
 
       

	}

	 public ActionForward savenparepaymentamountdetail(ActionMapping mapping,
	           ActionForm form,
	           HttpServletRequest request,
	           HttpServletResponse response
	           )throws MessageException,Exception {


	DynaActionForm dynaForm = (DynaActionForm) form;
	
	String cgpan        = (String) dynaForm.get("cgpanr");
	System.out.println("cgpan"+cgpan);
	
	String NpaPrincipleAmt = (String) dynaForm.get("wcpfb");
	System.out.println("NpaPrincipleAmt is=="+NpaPrincipleAmt);
	
	String  NpaPrincipleAmtInterest  = (String) dynaForm.get("wcpnfb");
	System.out.println("NpaPrincipleAmtInterest is=="+NpaPrincipleAmtInterest);
	
	String  disbursmentamt  = (String) dynaForm.get("disbursment");
	System.out.println("disbursmentamt is=="+disbursmentamt);
	
	String NpaPrincipleOutstandingAmt = (String) dynaForm.get("wcpfb1");
	System.out.println("NpaPrincipleOutstandingAmt is=="+NpaPrincipleOutstandingAmt);
	
	String  NpaInterestoutstandingamt  = (String) dynaForm.get("wcpnfb1");
	System.out.println("NpaInterestoutstandingamt is=="+NpaInterestoutstandingamt);
	String status="MA";
	int disbamt=0;
	
	
	
	
	Connection connection = DBConnection.getConnection();
	ResultSet rs = null;
	PreparedStatement pst = null;
	
	try { 
	
    String amount1  = null;
	String amount2  = null;
	String amount3  = null;
	String amount4  = null;
	String amount5  = null;
	java.sql.Date date=null;
    int count=0;
	String type  = null;
	int disamt=0;

    

  	String querydis =	 " select app_loan_type,APP_APPROVED_AMOUNT  from  application_detail  where cgpan='"+ cgpan + "' " ;
	

	  	//System.out.println("query5"+querydis);
	  	pst = connection.prepareStatement(querydis);
	  	rs = pst.executeQuery(querydis);



	  	while(rs.next() )
	  	{
	  		
	  		type=rs.getString(1);
	  		disbamt=rs.getInt(2);
	  		
	  		 
	  	}
	  	
	  	if(NpaPrincipleAmt.equals("0")||NpaPrincipleAmtInterest.equals("0")||NpaPrincipleOutstandingAmt.equals("0")||NpaInterestoutstandingamt.equals("0")||(type.equals("TC")&& disbursmentamt.equals("0"))){
			throw  new MessageException ("Enter Valid Amount");
		}

	  	if(type.equals("TC")){
	  	 disamt=Integer.parseInt(disbursmentamt);
	  	}
	  	if(disbamt<disamt){
			throw  new MessageException ("Disbursment Amount Not greater than Approved amount");
		}

	  	
	  	
	
	
	  	String querys =	 " select NTD_PRINCIPAL_REPAY_AMT,NTD_INTEREST_REPAY_AMT,NTD_NPA_PRINCIPAL_OS_AMT,NTD_NPA_INTEREST_OS_AMT,NTD_TOTAL_DISB_AMT  from  npa_tc_detail_temp where cgpan='"+ cgpan + "' " +
   	 "union  all " +
	 "  	 select 0,0,NWD_NPA_PRINCIPAL_OS_AMT,NWD_NPA_INTEREST_OS_AMT,0 from  npa_wc_detail_temp where cgpan='"+ cgpan + "' ";

 

		
		  	System.out.println("query===querys"+querys);
		  	pst = connection.prepareStatement(querys);
		  	rs = pst.executeQuery(querys);



		  	while(rs.next() )
		  	{
		  		amount1=rs.getString(1);
		  		amount2=rs.getString(2);
		  		amount3=rs.getString(3);
		  		amount4=rs.getString(4);
		  		amount5=rs.getString(5);
		  		 
		  	}

			String que =	"select sysdate from dual" ;


			
  	pst = connection.prepareStatement(que);
  	rs = pst.executeQuery(que);



  	while(rs.next() )
  	{
  		   date=rs.getDate(1);
  		 
  	}

  	
  
  
  	if(NpaPrincipleAmt.equals("null")||NpaPrincipleAmt.equals(""))
	{
  		NpaPrincipleAmt=amount1;
	}
  	
  	if(NpaPrincipleAmtInterest.equals("null")||NpaPrincipleAmtInterest.equals(""))
	{
		NpaPrincipleAmtInterest=amount2;
		System.out.println("NpaPrincipleAmtInterest"+NpaPrincipleAmtInterest);
	}	
	
	if(NpaPrincipleOutstandingAmt.equals("null")||NpaPrincipleOutstandingAmt.equals(""))
	{
  		NpaPrincipleOutstandingAmt=amount3;
	}
  	
  	if(NpaInterestoutstandingamt.equals("null")||NpaInterestoutstandingamt.equals(""))
	{
  		NpaInterestoutstandingamt=amount4;
	}
  	
  	if(disbursmentamt.equals("null")||disbursmentamt.equals(""))
	{
  		disbursmentamt=amount5;
	}
  	



	

		Statement statement = null;


		Connection conn = null;
		CallableStatement stmt = null;

		Statement stmtd = null;
	    PreparedStatement ps = null;
		int no=0;

		String qry = "insert into NPA_MODIFICATION_DETAILS" +
		"(CGPAN,MAKER_MODIFIED_DATE,CHECKER_MODIFIED_DATE,STATUS,NEW_NTD_NPA_PRINCIPAL_OS_AMT,NEW_NTD_NPA_INTEREST_OS_AMT,NEW_NTD_TOTAL_DISB_AMT,OLD_NTD_NPA_PRINCIPAL_OS_AMT,OLD_NWD_NPA_INTEREST_OS_AMT,OLD_NTD_TOTAL_DISB_AMT,NTD_PRINCIPAL_REPAY_AMT,NTD_INTEREST_REPAY_AMT,OLD_NTD_PRINCIPAL_REPAY_AMT,OLD_NTD_INTEREST_REPAY_AMT)"+
		"values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		System.out.println("qry===="+qry);
		ps = connection.prepareStatement(qry);
		ps.setString(1, cgpan);
		ps.setDate(2, date);				
		ps.setString(3, null);
		ps.setString(4, status );
		ps.setString(5, NpaPrincipleOutstandingAmt);
		ps.setString(6, NpaInterestoutstandingamt);
		ps.setString(7, disbursmentamt);
		ps.setString(8, amount3);
		ps.setString(9, amount4);
		ps.setString(10,amount5);
		ps.setString(11, NpaPrincipleAmt);
		ps.setString(12, NpaPrincipleAmtInterest);
		ps.setString(13, amount1);
		ps.setString(14, amount2);
		no = ps.executeUpdate();

 }
catch (Exception exception) {
		 		Log.logException(exception);
		 		throw new DatabaseException(exception.getMessage());
		 		}
		 	    finally 
		 	    {
		 		DBConnection.freeConnection(connection);
		 		}

	return mapping.findForward("success");
	}	
       //end rajuk	
	

	}
