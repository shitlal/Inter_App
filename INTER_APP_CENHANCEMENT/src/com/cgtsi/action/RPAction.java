package com.cgtsi.action;


import com.cgtsi.action.BaseAction;
import com.cgtsi.action.RPAction;
import com.cgtsi.actionform.RPActionForm;
import com.cgtsi.actionform.ReportActionForm;
import com.cgtsi.admin.ExcelCreator;
import com.cgtsi.admin.MenuOptions;
import com.cgtsi.admin.User;
import com.cgtsi.claim.ClaimsProcessor;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.Log;
import com.cgtsi.common.MessageException;
import com.cgtsi.common.NoDataException;
import com.cgtsi.investmentfund.IFProcessor;
import com.cgtsi.receiptspayments.AllocationDetail;
import com.cgtsi.receiptspayments.DANSummary;
import com.cgtsi.receiptspayments.DemandAdvice;
import com.cgtsi.receiptspayments.MissingCGPANsException;
import com.cgtsi.receiptspayments.MissingDANDetailsException;
import com.cgtsi.receiptspayments.PaymentDetails;
import com.cgtsi.receiptspayments.RealisationDetail;
import com.cgtsi.receiptspayments.RpDAO;
import com.cgtsi.receiptspayments.RpProcessor;
import com.cgtsi.receiptspayments.ShortExceedsLimitException;
import com.cgtsi.receiptspayments.Voucher;
import com.cgtsi.receiptspayments.VoucherDetail;
import com.cgtsi.registration.CollectingBank;
import com.cgtsi.registration.MLIInfo;
import com.cgtsi.registration.NoMemberFoundException;
import com.cgtsi.registration.Registration;
import com.cgtsi.reports.GeneralReport;
import com.cgtsi.reports.ReportManager;
import com.cgtsi.util.DBConnection;
import com.cgtsi.util.PropertyLoader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeMap;
import java.util.Vector;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class RPAction extends BaseAction {
  private static final String className = "RPAction";
  
  Registration registration;
  
  private void $init$() {
    this.registration = new Registration();
  }
  
  public ActionForward exportExcelNew(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    ArrayList<String> abc = new ArrayList();
    abc.add("milind");
    abc.add("joshi");
    HttpSession session = request.getSession();
    RPActionForm actionFormobj = (RPActionForm)form;
    Double amt = Double.valueOf(100.0D);
    String ifscCode = "UBIN0996335";
    String paymentID = request.getParameter("paymentIds");
    String filetype = request.getParameter("fileType");
    System.out.println("filetypes" + filetype);
    Map approveFlags1 = (Map)session.getAttribute("approvedData");
    Map approveFlags = approveFlags1;
    System.out.println("payid " + approveFlags);
    System.out.println("payid value approveFlags " + approveFlags.size());
    Set keys = approveFlags.keySet();
    System.out.println("konkati" + keys);
    User user = getUserInformation(request);
    String userid = user.getUserId();
    String bankid = user.getBankId().trim();
    String zoneid = user.getZoneId().trim();
    String branchid = user.getBranchId().trim();
    String memberId = String.valueOf(bankid) + zoneid + branchid;
    CallableStatement cStmt = null;
    String errorCode = "";
    ArrayList<RPActionForm> rpArray = new ArrayList();
    Iterator<String> PaymentIterate = keys.iterator();
    int insdanstatus = 0;
    RPActionForm actionForm = null;
    while (PaymentIterate.hasNext()) {
      actionForm = new RPActionForm();
      String payids = PaymentIterate.next();
      System.out.println("keys are" + payids);
      String[] arr = payids.split("@");
      System.out.println("Payid=== " + arr[0]);
      actionForm.setPaymentId1(arr[0]);
      actionForm.setAmmount2(Integer.parseInt(arr[1]));
      actionForm.setVaccno(arr[2]);
      System.out.println("vaccNo=== " + arr[2]);
      actionForm.setIfscCode(ifscCode);
      rpArray.add(actionForm);
    } 
    ExcelCreator excelCreator = new ExcelCreator();
    HSSFWorkbook workbook = excelCreator.createWorkbook(rpArray);
    response.setHeader("Content-Disposition", 
        "attachment; filename=PaymentDetails." + filetype);
    ServletOutputStream out = response.getOutputStream();
    workbook.write((OutputStream)out);
    out.flush();
    out.close();
    return mapping.findForward("success");
  }
  
  public ActionForward exportCsvNew(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    ArrayList<String> abc = new ArrayList();
    abc.add("milind");
    abc.add("joshi");
    HttpSession session = request.getSession();
    RPActionForm actionFormobj = (RPActionForm)form;
    Double amt = Double.valueOf(100.0D);
    String ifscCode = "UBIN0996335";
    String paymentID = request.getParameter("paymentIds");
    String filetype = request.getParameter("fileType");
    System.out.println("filetypes" + filetype);
    Map approveFlags1 = (Map)session.getAttribute("approvedData");
    Map approveFlags = approveFlags1;
    System.out.println("payid " + approveFlags);
    System.out.println("payid value approveFlags " + approveFlags.size());
    Set keys = approveFlags.keySet();
    System.out.println("konkati" + keys);
    User user = getUserInformation(request);
    String userid = user.getUserId();
    String bankid = user.getBankId().trim();
    String zoneid = user.getZoneId().trim();
    String branchid = user.getBranchId().trim();
    String memberId = String.valueOf(bankid) + zoneid + branchid;
    CallableStatement cStmt = null;
    String errorCode = "";
    ArrayList<RPActionForm> rpArray = new ArrayList();
    Iterator<String> PaymentIterate = keys.iterator();
    int insdanstatus = 0;
    RPActionForm actionForm = null;
    while (PaymentIterate.hasNext()) {
      actionForm = new RPActionForm();
      String payids = PaymentIterate.next();
      System.out.println("keys are" + payids);
      String[] arr = payids.split("@");
      System.out.println("Payid=== " + arr[0]);
      actionForm.setPaymentId1(arr[0]);
      actionForm.setAmmount2(Integer.parseInt(arr[1]));
      actionForm.setVaccno(arr[2]);
      System.out.println("vaccNo=== " + arr[2]);
      actionForm.setIfscCode(ifscCode);
      rpArray.add(actionForm);
    } 
    ExcelCreator excelCreator = new ExcelCreator();
    HSSFWorkbook workbook = excelCreator.createWorkbook(rpArray);
    response.setHeader("Content-Disposition", 
        "attachment; filename=PaymentDetails." + filetype);
    ServletOutputStream out = response.getOutputStream();
    workbook.write((OutputStream)out);
    out.flush();
    out.close();
    return mapping.findForward("success");
  }
  
  public ActionForward showPaymentFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "showPaymentFilter", "Entered");
    Log.log(4, "RPAction", "showPaymentFilter", "Exited");
    RPActionForm rpActionForm = (RPActionForm)form;
    rpActionForm.resetWhenRequired();
    rpActionForm.setPaymentId(null);
    User user = getUserInformation(request);
    String bankId = user.getBankId();
    rpActionForm.setBankId(user.getBankId());
    rpActionForm.setZoneId(user.getZoneId());
    rpActionForm.setBranchId(user.getBranchId());
    if (bankId.equals("0000")) {
      user = null;
      rpActionForm.setSelectMember("");
    } else {
      MLIInfo mliInfo = getMemberInfo(request);
      bankId = mliInfo.getBankId();
      String branchId = mliInfo.getBranchId();
      String zoneId = mliInfo.getZoneId();
      String memberId = String.valueOf(bankId) + zoneId + branchId;
      rpActionForm.setSelectMember(memberId);
    } 
    return mapping.findForward("success");
  }
  
  public ActionForward generateClaimSFDAN(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "generateClaimSFDAN", "Entered");
    RPActionForm rpActionForm = (RPActionForm)form;
    User user = getUserInformation(request);
    String bankId = user.getBankId();
    rpActionForm.setBankId(user.getBankId());
    rpActionForm.setZoneId(user.getZoneId());
    rpActionForm.setBranchId(user.getBranchId());
    if (bankId.equals("0000")) {
      user = null;
      rpActionForm.setSelectMember("");
    } else {
      MLIInfo mliInfo = getMemberInfo(request);
      bankId = mliInfo.getBankId();
      String branchId = mliInfo.getBranchId();
      String zoneId = mliInfo.getZoneId();
      String memberId = String.valueOf(bankId) + zoneId + branchId;
      rpActionForm.setSelectMember(memberId);
    } 
    return mapping.findForward("generateSFDAN");
  }
  
  public ActionForward generateClaimASFDAN(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "generateClaimASFDAN", "Entered");
    RPActionForm rpActionForm = (RPActionForm)form;
    RpDAO rpDao = new RpDAO();
    User user = getUserInformation(request);
    String cgpan = rpActionForm.getCgpan().toUpperCase();
    int serviceFee = rpActionForm.getDanAmt();
    String remarks = rpActionForm.getApplRemarks().toUpperCase();
    String danType = rpActionForm.getDanType();
    rpDao.generateASFDANforClaimSettled(cgpan, serviceFee, remarks, danType);
    rpActionForm.setCgpan(null);
    rpActionForm.setApplRemarks(null);
    rpActionForm.setDanAmt(0);
    rpActionForm.setDanType(null);
    request.setAttribute("message", 
        "ASF DAN generated for entered CGPAN No - " + cgpan + 
        " Successfully");
    return mapping.findForward("success");
  }
  
  public ActionForward getPaymentDetailsForPayInSlip(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "getPaymentDetailsForPayInSlip", "Entered");
    String paymentId = request.getParameter("payId");
    RPActionForm rpActionForm = (RPActionForm)form;
    Log.log(5, "RPAction", "getPaymentDetailsForPayInSlip", "paymentId " + 
        paymentId);
    RpProcessor rpProcessor = new RpProcessor();
    PaymentDetails paymentDetails = rpProcessor.displayPayInSlip(paymentId);
    paymentDetails.setPaymentId(paymentId);
    BeanUtils.copyProperties(rpActionForm, paymentDetails);
    rpActionForm.setAccountNumber(paymentDetails.getCgtsiAccNumber());
    String bankName = rpActionForm.getPayInSlipFormat();
    String retPath = "";
    if (bankName.equalsIgnoreCase("IDBI")) {
      retPath = "idbi";
    } else if (bankName.equalsIgnoreCase("PNB")) {
      retPath = "pnb";
    } else if (bankName.equalsIgnoreCase("HDFC")) {
      retPath = "hdfc";
    } 
    Log.log(4, "RPAction", "getPaymentDetailsForPayInSlip", "Exited");
    return mapping.findForward(retPath);
  }
  
  public ActionForward showJournalVoucherDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "showJournalVoucherDetails", "Entered");
    RPActionForm actionForm = (RPActionForm)form;
    actionForm.resetWhenRequired();
    IFProcessor ifProcessor = new IFProcessor();
    ArrayList instruments = ifProcessor.getInstrumentTypes("G");
    actionForm.setInstruments(instruments);
    request.setAttribute("IsRequired", Boolean.valueOf(true));
    Log.log(4, "RPAction", "showJournalVoucherDetails", "Exited");
    return mapping.findForward("success");
  }
  
  public ActionForward addMoreJournalVoucherDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "addMoreJournalVoucherDetails", "Entered");
    RPActionForm rpForm = (RPActionForm)form;
    Map voucherDetails = rpForm.getVoucherDetails();
    Set voucherDetailsSet = voucherDetails.keySet();
    Iterator<String> voucherDetailsIterator = voucherDetailsSet.iterator();
    String count = null;
    int counter = 0;
    while (voucherDetailsIterator.hasNext()) {
      String key = voucherDetailsIterator.next();
      Log.log(5, "RPAction", "addMoreJournalVoucherDetails", " key " + 
          key);
      count = key.substring(key.indexOf("-") + 1, key.length());
      Log.log(5, "RPAction", "addMoreJournalVoucherDetails", " count " + 
          count);
    } 
    Log.log(5, "RPAction", "addMoreJournalVoucherDetails", " counter " + 
        counter);
    request.setAttribute("IsRequired", Boolean.valueOf(true));
    Log.log(4, "RPAction", "addMoreJournalVoucherDetails", "Exited");
    return mapping.findForward("success");
  }
  
  public ActionForward insertJournalVoucherDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "insertJournalVoucherDetails", "Entered");
    RPActionForm rpForm = (RPActionForm)form;
    Map voucherDetails = rpForm.getVoucherDetails();
    Set voucherSet = voucherDetails.keySet();
    Iterator<String> voucherIterator = voucherSet.iterator();
    ArrayList<Voucher> vouchers = new ArrayList();
    double dbtAmt = 0.0D;
    double cdtAmt = 0.0D;
    while (voucherIterator.hasNext()) {
      String key = voucherIterator.next();
      Log.log(5, "RPAction", "insertJournalVoucherDetails", "key " + key);
      Voucher voucher = (Voucher)voucherDetails.get(key);
      vouchers.add(voucher);
      Log.log(4, "RPAction", "insertJournalVoucherDetails", " Ac code " + 
          voucher.getAcCode());
      Log.log(4, "RPAction", "insertJournalVoucherDetails", " adv date " + 
          voucher.getAdvDate());
      Log.log(5, "RPAction", "insertJournalVoucherDetails", "adv no " + 
          voucher.getAdvNo());
      Log.log(5, "RPAction", "insertJournalVoucherDetails", 
          "amount is rs " + voucher.getAmountInRs());
      Log.log(5, "RPAction", "insertJournalVoucherDetails", 
          " debit or credir " + voucher.getDebitOrCredit());
      Log.log(5, "RPAction", "insertJournalVoucherDetails", 
          "instrument date " + voucher.getInstrumentDate());
      Log.log(5, "RPAction", "insertJournalVoucherDetails", 
          " instrument no " + voucher.getInstrumentNo());
      if (voucher.getDebitOrCredit().equalsIgnoreCase("D")) {
        dbtAmt += Double.parseDouble(voucher.getAmountInRs());
        continue;
      } 
      if (voucher.getDebitOrCredit().equalsIgnoreCase("C")) {
        cdtAmt += Double.parseDouble(voucher.getAmountInRs());
        voucher.setAmountInRs("-" + voucher.getAmountInRs());
      } 
    } 
    VoucherDetail voucherDetail = new VoucherDetail();
    BeanUtils.copyProperties(voucherDetail, rpForm);
    Log.log(5, "RPAction", "insertJournalVoucherDetails", " amount " + 
        voucherDetail.getAmount());
    Log.log(5, "RPAction", "insertJournalVoucherDetails", " figure " + 
        voucherDetail.getAmountInFigure());
    Log.log(5, "RPAction", "insertJournalVoucherDetails", " GL code " + 
        voucherDetail.getBankGLCode());
    Log.log(5, "RPAction", "insertJournalVoucherDetails", " GL name" + 
        voucherDetail.getBankGLName());
    Log.log(5, "RPAction", "insertJournalVoucherDetails", "dept code " + 
        voucherDetail.getDeptCode());
    voucherDetail.setAmount(cdtAmt - dbtAmt);
    Log.log(5, "RPAction", "insertJournalVoucherDetails", " amount " + 
        voucherDetail.getAmount());
    voucherDetail.setVouchers(vouchers);
    RpProcessor rpProcessor = new RpProcessor();
    User user = getUserInformation(request);
    voucherDetail.setVoucherType("JOURNAL VOUCHER");
    String voucherId = rpProcessor.insertVoucherDetails(voucherDetail, 
        user.getUserId());
    String message = "Journal Voucher details stored successfull. Voucher number is " + 
      voucherId;
    request.setAttribute("message", message);
    Log.log(4, "RPAction", "insertJournalVoucherDetails", "Exited");
    return mapping.findForward("success");
  }
  
  public ActionForward showPaymentVoucherDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "showPaymentVoucherDetails", "Entered");
    RPActionForm actionForm = (RPActionForm)form;
    actionForm.resetWhenRequired();
    IFProcessor ifProcessor = new IFProcessor();
    ArrayList instruments = ifProcessor.getInstrumentTypes("G");
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList glHeads = rpProcessor.getGLHeads();
    actionForm.setGlHeads(glHeads);
    actionForm.setInstruments(instruments);
    request.setAttribute("IsRequired", Boolean.valueOf(true));
    HttpSession session = request.getSession(false);
    session.setAttribute("VOUCHER_FLAG", "1");
    Log.log(4, "RPAction", "showPaymentVoucherDetails", "Exited");
    return mapping.findForward("success");
  }
  
  public ActionForward insertPaymentVoucherDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "insertPaymentVoucherDetails", "Entered");
    RPActionForm rpForm = (RPActionForm)form;
    Map voucherDetails = rpForm.getVoucherDetails();
    Set voucherSet = voucherDetails.keySet();
    Iterator<String> voucherIterator = voucherSet.iterator();
    ArrayList<Voucher> vouchers = new ArrayList();
    double dbtAmt = 0.0D;
    double cdtAmt = 0.0D;
    while (voucherIterator.hasNext()) {
      String key = voucherIterator.next();
      Log.log(5, "RPAction", "insertPaymentVoucherDetails", "key " + key);
      Voucher voucher = (Voucher)voucherDetails.get(key);
      vouchers.add(voucher);
      Log.log(5, "RPAction", "insertPaymentVoucherDetails", " Ac code " + 
          voucher.getAcCode());
      Log.log(5, "RPAction", "insertPaymentVoucherDetails", " adv date " + 
          voucher.getAdvDate());
      Log.log(5, "RPAction", "insertPaymentVoucherDetails", "adv no " + 
          voucher.getAdvNo());
      Log.log(5, "RPAction", "insertPaymentVoucherDetails", 
          "amount is rs " + voucher.getAmountInRs());
      Log.log(5, "RPAction", "insertPaymentVoucherDetails", 
          " debit or credir " + voucher.getDebitOrCredit());
      Log.log(5, "RPAction", "insertPaymentVoucherDetails", 
          "instrument date " + voucher.getInstrumentDate());
      Log.log(5, "RPAction", "insertPaymentVoucherDetails", 
          " instrument no " + voucher.getInstrumentNo());
      if (voucher.getDebitOrCredit().equalsIgnoreCase("D")) {
        dbtAmt += Double.parseDouble(voucher.getAmountInRs());
        continue;
      } 
      if (voucher.getDebitOrCredit().equalsIgnoreCase("C")) {
        cdtAmt += Double.parseDouble(voucher.getAmountInRs());
        if (cdtAmt > 0.0D)
          voucher.setAmountInRs("-" + voucher.getAmountInRs()); 
      } 
    } 
    VoucherDetail voucherDetail = new VoucherDetail();
    BeanUtils.copyProperties(voucherDetail, rpForm);
    Log.log(5, "RPAction", "insertPaymentVoucherDetails", " amount " + 
        rpForm.getAmount());
    Log.log(5, "RPAction", "insertPaymentVoucherDetails", " amount " + 
        voucherDetail.getAmount());
    Log.log(5, "RPAction", "insertPaymentVoucherDetails", " figure " + 
        voucherDetail.getAmountInFigure());
    Log.log(5, "RPAction", "insertPaymentVoucherDetails", " GL code " + 
        voucherDetail.getBankGLCode());
    Log.log(5, "RPAction", "insertPaymentVoucherDetails", " GL name" + 
        voucherDetail.getBankGLName());
    Log.log(5, "RPAction", "insertPaymentVoucherDetails", "dept code " + 
        voucherDetail.getDeptCode());
    voucherDetail.setAmount(cdtAmt - dbtAmt);
    Log.log(5, "RPAction", "insertPaymentVoucherDetails", " amount " + 
        voucherDetail.getAmount());
    voucherDetail.setVouchers(vouchers);
    RpProcessor rpProcessor = new RpProcessor();
    User user = getUserInformation(request);
    voucherDetail.setVoucherType("PAYMENT VOUCHER");
    String voucherId = rpProcessor.insertVoucherDetails(voucherDetail, 
        user.getUserId());
    String message = "Voucher details stored successfull. Voucher number is " + 
      voucherId;
    request.setAttribute("message", message);
    Log.log(4, "RPAction", "insertPaymentVoucherDetails", "Exited");
    return mapping.findForward("success");
  }
  
  public ActionForward addMorePaymentVoucherDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "addMorePaymentVoucherDetails", "Entered");
    RPActionForm rpForm = (RPActionForm)form;
    Map voucherDetails = rpForm.getVoucherDetails();
    Set voucherDetailsSet = voucherDetails.keySet();
    Iterator<String> voucherDetailsIterator = voucherDetailsSet.iterator();
    String count = null;
    int counter = 0;
    while (voucherDetailsIterator.hasNext()) {
      String key = voucherDetailsIterator.next();
      Log.log(5, "RPAction", "addMorePaymentVoucherDetails", " key " + 
          key);
      count = key.substring(key.indexOf("-") + 1, key.length());
      Log.log(5, "RPAction", "addMorePaymentVoucherDetails", " count " + 
          count);
    } 
    Log.log(5, "RPAction", "addMorePaymentVoucherDetails", " counter " + 
        counter);
    request.setAttribute("IsRequired", Boolean.valueOf(true));
    Log.log(4, "RPAction", "addMorePaymentVoucherDetails", "Exited");
    return mapping.findForward("success");
  }
  
  public ActionForward showReceiptVoucherDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "showReceiptVoucherDetails", "Entered");
    RPActionForm actionForm = (RPActionForm)form;
    actionForm.resetWhenRequired();
    IFProcessor ifProcessor = new IFProcessor();
    ArrayList instruments = ifProcessor.getInstrumentTypes("G");
    actionForm.setInstruments(instruments);
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList glHeads = rpProcessor.getGLHeads();
    actionForm.setGlHeads(glHeads);
    HttpSession session = request.getSession(false);
    session.setAttribute("VOUCHER_FLAG", "1");
    request.setAttribute("IsRequired", Boolean.valueOf(true));
    Log.log(4, "RPAction", "showReceiptVoucherDetails", "Exited");
    return mapping.findForward("success");
  }
  
  public ActionForward insertReceiptVoucherDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "insertReceiptVoucherDetails", "Entered");
    RPActionForm rpForm = (RPActionForm)form;
    Map voucherDetails = rpForm.getVoucherDetails();
    Set voucherSet = voucherDetails.keySet();
    Iterator<String> voucherIterator = voucherSet.iterator();
    ArrayList<Voucher> vouchers = new ArrayList();
    double dbtAmt = 0.0D;
    double cdtAmt = 0.0D;
    while (voucherIterator.hasNext()) {
      String key = voucherIterator.next();
      Log.log(5, "RPAction", "insertReceiptVoucherDetails", "key " + key);
      Voucher voucher = (Voucher)voucherDetails.get(key);
      vouchers.add(voucher);
      Log.log(5, "RPAction", "insertReceiptVoucherDetails", " Ac code " + 
          voucher.getAcCode());
      Log.log(5, "RPAction", "insertReceiptVoucherDetails", " adv date " + 
          voucher.getAdvDate());
      Log.log(5, "RPAction", "insertReceiptVoucherDetails", "adv no " + 
          voucher.getAdvNo());
      Log.log(5, "RPAction", "insertReceiptVoucherDetails", 
          "amount is rs " + voucher.getAmountInRs());
      Log.log(5, "RPAction", "insertReceiptVoucherDetails", 
          " debit or credir " + voucher.getDebitOrCredit());
      Log.log(5, "RPAction", "insertReceiptVoucherDetails", 
          "instrument date " + voucher.getInstrumentDate());
      Log.log(5, "RPAction", "insertReceiptVoucherDetails", 
          " instrument no " + voucher.getInstrumentNo());
      if (voucher.getDebitOrCredit().equalsIgnoreCase("D")) {
        dbtAmt += Double.parseDouble(voucher.getAmountInRs());
        continue;
      } 
      if (voucher.getDebitOrCredit().equalsIgnoreCase("C")) {
        cdtAmt += Double.parseDouble(voucher.getAmountInRs());
        voucher.setAmountInRs("-" + voucher.getAmountInRs());
      } 
    } 
    VoucherDetail voucherDetail = new VoucherDetail();
    BeanUtils.copyProperties(voucherDetail, rpForm);
    Log.log(5, "RPAction", "insertReceiptVoucherDetails", " amount " + 
        voucherDetail.getAmount());
    Log.log(5, "RPAction", "insertReceiptVoucherDetails", " figure " + 
        voucherDetail.getAmountInFigure());
    Log.log(5, "RPAction", "insertReceiptVoucherDetails", " GL code " + 
        voucherDetail.getBankGLCode());
    Log.log(5, "RPAction", "insertReceiptVoucherDetails", " GL name" + 
        voucherDetail.getBankGLName());
    Log.log(5, "RPAction", "insertReceiptVoucherDetails", "dept code " + 
        voucherDetail.getDeptCode());
    voucherDetail.setAmount(dbtAmt - cdtAmt);
    Log.log(5, "RPAction", "insertReceiptVoucherDetails", " amount " + 
        voucherDetail.getAmount());
    voucherDetail.setVouchers(vouchers);
    RpProcessor rpProcessor = new RpProcessor();
    User user = getUserInformation(request);
    voucherDetail.setVoucherType("RECEIPT VOUCHER");
    String voucherId = rpProcessor.insertVoucherDetails(voucherDetail, 
        user.getUserId());
    String message = "Voucher details stored successfull. Voucher number is " + 
      voucherId;
    request.setAttribute("message", message);
    Log.log(4, "RPAction", "insertReceiptVoucherDetails", "Exited");
    return mapping.findForward("success");
  }
  
  public ActionForward addMoreReceiptVoucherDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "addMoreReceiptVoucherDetails", "Entered");
    RPActionForm rpForm = (RPActionForm)form;
    Map voucherDetails = rpForm.getVoucherDetails();
    Set voucherDetailsSet = voucherDetails.keySet();
    Iterator<String> voucherDetailsIterator = voucherDetailsSet.iterator();
    String count = null;
    int counter = 0;
    while (voucherDetailsIterator.hasNext()) {
      String key = voucherDetailsIterator.next();
      Log.log(5, "RPAction", "addMoreReceiptVoucherDetails", " key " + 
          key);
      count = key.substring(key.indexOf("-") + 1, key.length());
      Log.log(5, "RPAction", "addMoreReceiptVoucherDetails", " count " + 
          count);
    } 
    Log.log(5, "RPAction", "addMoreReceiptVoucherDetails", " counter " + 
        counter);
    request.setAttribute("IsRequired", Boolean.valueOf(true));
    Log.log(4, "RPAction", "addMoreReceiptVoucherDetails", "Exited");
    return mapping.findForward("success");
  }
  
  public ActionForward getPendingDANsFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    User user = getUserInformation(request);
    String bankId = user.getBankId();
    RPActionForm rpActionForm = (RPActionForm)form;
    HttpSession session = request.getSession(false);
    rpActionForm.setAllocationType("F");
    if (bankId.equalsIgnoreCase("0000")) {
      rpActionForm.setSelectMember("");
      session.setAttribute("TARGET_URL", 
          "selectMember1.do?method=getPendingDANs");
      return mapping.findForward("memberInfo");
    } 
    request.setAttribute("pageValue", "1");
    getPendingDANs(mapping, form, request, response);
    return mapping.findForward("danSummary");
  }
  
  public ActionForward getPendingASFDANsFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    User user = getUserInformation(request);
    String bankId = user.getBankId();
    RPActionForm rpActionForm = (RPActionForm)form;
    HttpSession session = request.getSession(false);
    rpActionForm.setAllocationType("A");
    if (bankId.equalsIgnoreCase("0000")) {
      rpActionForm.setSelectMember("");
      session.setAttribute("TARGET_URL", 
          "selectASFMember.do?method=getPendingASFDANs");
      return mapping.findForward("memberInfo");
    } 
    request.setAttribute(
        "message", 
        "<b> In terms of Circular No.59/2009-10 dated March 11,2010, it is mandatory for all MLIs <br>  to make ASF 2011 payment through a single payment from the Head Office.");
    return mapping.findForward("success");
  }
  
  public ActionForward getPendingExpiredASFDANsFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    User user = getUserInformation(request);
    String bankId = user.getBankId();
    RPActionForm rpActionForm = (RPActionForm)form;
    HttpSession session = request.getSession(false);
    if (bankId.equalsIgnoreCase("0000")) {
      rpActionForm.setSelectMember("");
      session.setAttribute("TARGET_URL", 
          "selectASFMemberForExpired.do?method=getPendingExpiredASFDANs");
      return mapping.findForward("memberInfo");
    } 
    request.setAttribute(
        "message", 
        "<b> In terms of Circular No.59/2009-10 dated March 11,2010, it is mandatory for all MLIs <br>  to make ASF 2011 payment through a single payment from the Head Office.");
    return mapping.findForward("success");
  }
  
  public ActionForward getPendingGFDANsFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    User user = getUserInformation(request);
    String bankId = user.getBankId();
    RPActionForm rpActionForm = (RPActionForm)form;
    rpActionForm.setAllocationType("G");
    HttpSession session = request.getSession(false);
    if (bankId.equalsIgnoreCase("0000")) {
      rpActionForm.setSelectMember("");
      session.setAttribute("TARGET_URL", 
          "selectGFMember.do?method=getPendingGFDANs");
      return mapping.findForward("memberInfo");
    } 
    request.setAttribute("pageValue", "1");
    getPendingGFDANs(mapping, form, request, response);
    return mapping.findForward("danSummary");
  }
  
  public ActionForward getPendingGFDANsFilterNew(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    User user = getUserInformation(request);
    String bankId = user.getBankId();
    RPActionForm rpActionForm = (RPActionForm)form;
    rpActionForm.setAllocationType("G");
    HttpSession session = request.getSession(false);
    if (bankId.equalsIgnoreCase("0000")) {
      rpActionForm.setSelectMember("");
      session.setAttribute("TARGET_URL", 
          "selectGFMemberNew.do?method=getNEFTPendingGFDANs");
      return mapping.findForward("memberInfoNew");
    } 
    request.setAttribute("pageValue", "1");
    getNEFTPendingGFDANs(mapping, form, request, response);
    return mapping.findForward("neftdanSummary");
  }
  
  public ActionForward getPendingTextileGFDANsFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    User user = getUserInformation(request);
    String bankId = user.getBankId();
    RPActionForm rpActionForm = (RPActionForm)form;
    HttpSession session = request.getSession(false);
    rpActionForm.setAllocationType("H");
    request.setAttribute("pageValue", "1");
    getPendingTextileGFDANs(mapping, form, request, response);
    return mapping.findForward("danSummary4");
  }
  
  public ActionForward getPendingTextileGFDANs(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "getPendingTextileGFDANs", "Entered");
    User user = getUserInformation(request);
    String bankId = user.getBankId();
    String zoneId = user.getZoneId();
    String branchId = user.getBranchId();
    String memberId = "";
    RPActionForm actionForm = (RPActionForm)form;
    HttpSession session = request.getSession(false);
    if (actionForm.getDanSummaries() != null)
      actionForm.getDanSummaries().clear(); 
    if (actionForm.getDanPanDetails() != null)
      actionForm.getDanPanDetails().clear(); 
    if (actionForm.getCgpans() != null)
      actionForm.getCgpans().clear(); 
    if (actionForm.getAllocatedFlags() != null)
      actionForm.getAllocatedFlags().clear(); 
    if (actionForm.getFirstDisbursementDates() != null)
      actionForm.getFirstDisbursementDates().clear(); 
    if (actionForm.getNotAllocatedReasons() != null)
      actionForm.getNotAllocatedReasons().clear(); 
    Log.log(5, "RPAction", "getPendingTextileGFDANs", "Bank Id : " + bankId);
    Log.log(5, "RPAction", "getPendingTextileGFDANs", "Zone Id : " + zoneId);
    Log.log(5, "RPAction", "getPendingTextileGFDANs", "Branch Id : " + 
        branchId);
    bankId = "0019";
    zoneId = "0001";
    Log.log(5, "RPAction", "getPendingGFDANs", 
        "Selected Bank Id,zone and branch ids : " + bankId + "," + 
        zoneId + "," + branchId);
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList<DANSummary> danSummaries = rpProcessor.displayTextileGFDANs();
    Log.log(5, "RPAction", "getPendingGFDANs", "dan summary size : " + 
        danSummaries.size());
    if (danSummaries.size() == 0) {
      actionForm.setSelectMember(null);
      throw new MissingDANDetailsException(
          "No DANs available for Allocation");
    } 
    boolean isDanAvailable = false;
    for (int i = 0; i < danSummaries.size(); i++) {
      DANSummary danSummary = danSummaries.get(i);
      if (danSummary.getAmountDue() != danSummary.getAmountPaid()) {
        isDanAvailable = true;
        break;
      } 
    } 
    if (!isDanAvailable) {
      actionForm.setSelectMember(null);
      throw new MissingDANDetailsException(
          "No DANs available for Allocation");
    } 
    actionForm.setDanSummaries(danSummaries);
    actionForm.setBankId(bankId);
    actionForm.setZoneId(zoneId);
    actionForm.setBranchId(branchId);
    Log.log(4, "RPAction", "getPendingTextileGFDANs", "Exited");
    if (actionForm.getSelectMember() != null) {
      actionForm.setMemberId(actionForm.getSelectMember());
    } else {
      actionForm.setMemberId(String.valueOf(bankId) + zoneId + branchId);
    } 
    actionForm.setSelectMember(null);
    return mapping.findForward("danSummary4");
  }
  
  public ActionForward submitTextileGFDANPayments(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    double totalAmount = 0.0D;
    Map danIds = actionForm.getDanIds();
    Map allocatedFlags = actionForm.getAllocatedFlags();
    Map cgpans = actionForm.getCgpans();
    int allocatedcount = 0;
    int testallocatecount = 0;
    Set danIdSet = danIds.keySet();
    Log.log(5, "RPAction", "submitTextileGFDANPayments", "Checkbox size = " + 
        allocatedFlags.size());
    Set<String> cgpansSet = cgpans.keySet();
    Iterator<String> cgpanIterator = cgpansSet.iterator();
    Log.log(5, "RPAction", "submitTextileGFDANPayments", "Checkbox size = " + 
        cgpans.size());
    while (cgpanIterator.hasNext()) {
      String key = cgpanIterator.next();
      String value = (String)cgpans.get(key);
      Log.log(5, "RPAction", "submitTextileGFDANPayments", "cgpan key = " + 
          key);
      Log.log(5, "RPAction", "submitTextileGFDANPayments", 
          "cgpan value = " + value);
    } 
    cgpanIterator = cgpansSet.iterator();
    ArrayList<DANSummary> danSummaries = actionForm.getDanSummaries();
    boolean isAllocated = false;
    for (int i = 0; i < danSummaries.size(); i++) {
      DANSummary danSummary = danSummaries.get(i);
      String danId = danSummary.getDanId();
      Log.log(5, "RPAction", "submitTextileGFDANPayments", "danId= " + 
          danId);
      String danIdKey = danId.replace('.', '_');
      if (allocatedFlags.containsKey(danIdKey) && 
        request.getParameter("allocatedFlag(" + danIdKey + ")") != null) {
        allocatedcount++;
        Log.log(5, "RPAction", "submitTextileGFDANPayments", 
            "danSummaries= " + danSummaries.size());
        isAllocated = true;
        totalAmount += danSummary.getAmountDue() - 
          danSummary.getAmountPaid();
        Log.log(5, 
            "RPAction", 
            "submitTextileGFDANPayments", 
            "due amount " + (
            danSummary.getAmountDue() - danSummary
            .getAmountPaid()));
      } else {
        Log.log(5, "RPAction", "submitTextileGFDANPayments", 
            "CGPANS are allocated ");
        ArrayList<AllocationDetail> panDetails = (ArrayList)actionForm
          .getDanPanDetail(danId);
        while (cgpanIterator.hasNext()) {
          String key = cgpanIterator.next();
          String value = (String)cgpans.get(key);
          String cgpanPart = value.substring(value.indexOf("-") + 1, 
              value.length());
          String tempKey = value.replace('.', '_');
          Log.log(5, "RPAction", "submitTextileGFDANPayments", "key " + 
              key);
          Log.log(5, "RPAction", "submitTextileGFDANPayments", 
              "value " + value);
          Log.log(5, "RPAction", "submitTextileGFDANPayments", 
              "tempKey " + tempKey);
          if (value.startsWith(danId) && 
            allocatedFlags.get(tempKey) != null && (
            (String)allocatedFlags.get(tempKey))
            .equals("Y")) {
            testallocatecount++;
            cgpanPart = value.substring(value.indexOf("-") + 1, 
                value.length());
            isAllocated = true;
            for (int j = 0; j < panDetails.size(); j++) {
              AllocationDetail allocation = panDetails
                .get(j);
              Log.log(5, "RPAction", 
                  "submitTextileGFDANPayments", 
                  "amount for CGPAN " + allocation.getCgpan() + 
                  "," + allocation.getAmountDue());
              if (cgpanPart.equals(allocation.getCgpan())) {
                totalAmount += allocation.getAmountDue();
                break;
              } 
            } 
          } 
        } 
        cgpanIterator = cgpansSet.iterator();
      } 
    } 
    if (!isAllocated)
      throw new MissingDANDetailsException("No Allocation made."); 
    Registration registration = new Registration();
    Log.log(5, "RPAction", "submitTextileGFDANPayments", "member id " + 
        actionForm.getMemberId());
    CollectingBank collectingBank = registration.getCollectingBank("(" + 
        actionForm.getMemberId() + ")");
    Log.log(5, "RPAction", "submitTextileGFDANPayments", "collectingBank " + 
        collectingBank);
    actionForm.setModeOfPayment("");
    actionForm.setPaymentDate(null);
    actionForm.setInstrumentNo("");
    actionForm.setInstrumentDate(null);
    actionForm.setDrawnAtBank("");
    actionForm.setDrawnAtBranch("");
    actionForm.setPayableAt("");
    IFProcessor ifProcessor = new IFProcessor();
    ArrayList instruments = ifProcessor.getInstrumentTypes("G");
    actionForm.setInstruments(instruments);
    RpDAO rpDAO = new RpDAO();
    actionForm.setInstrumentNo(rpDAO.getInstrumentSeq());
    actionForm.setCollectingBank(collectingBank.getCollectingBankId());
    actionForm
      .setCollectingBankName(collectingBank.getCollectingBankName());
    actionForm.setCollectingBankBranch(collectingBank.getBranchName());
    actionForm.setAccountNumber(collectingBank.getAccNo());
    actionForm.setInstrumentAmount(totalAmount);
    return mapping.findForward("gfpaymentDetails");
  }
  
  public ActionForward getTextileGFPANDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "getGFPANDetails", "Entered");
    RPActionForm actionForm = (RPActionForm)form;
    Map cgpans = actionForm.getCgpans();
    Set cgpanSet = cgpans.keySet();
    Iterator<String> cgpanIterator = cgpanSet.iterator();
    Log.log(5, "RPAction", "getGFPANDetails", "CGPANS selected ");
    while (cgpanIterator.hasNext()) {
      String key = cgpanIterator.next();
      Log.log(5, "RPAction", "getGFPANDetails", "key,value " + key + "," + 
          cgpans.get(key));
    } 
    Log.log(5, "RPAction", "getGFPANDetails", 
        "Cgpan map size " + cgpans.size());
    String danNo = actionForm.getDanNo();
    Log.log(4, "RPAction", "getGFPANDetails", "On entering, DAN no: " + 
        danNo);
    Log.log(4, "RPAction", "getGFPANDetails", "No Session: DAN no : " + 
        danNo);
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList<ArrayList> returnList = rpProcessor.displayCGPANs(danNo);
    ArrayList<AllocationDetail> panDetails = returnList.get(0);
    ArrayList allocatedPanDetails = returnList.get(1);
    Log.log(4, "RPAction", "getGFPANDetails", 
        "No Session: No. of PAN details : " + panDetails.size());
    String allocatedFlag = (String)actionForm.getAllocatedFlag(danNo
        .replace('.', '_'));
    Log.log(4, "RPAction", "getGFPANDetails", "flag " + allocatedFlag);
    Map<String, String> allocatedFlags = actionForm.getAllocatedFlags();
    if (allocatedFlag != null && allocatedFlag.equalsIgnoreCase(danNo))
      for (int i = 0; i < panDetails.size(); i++) {
        AllocationDetail allocationDetail = panDetails
          .get(i);
        String key = String.valueOf(danNo.replace('.', '_')) + "-" + 
          allocationDetail.getCgpan();
        allocatedFlags.put(key, "Y");
      }  
    actionForm.setAllocatedFlags(allocatedFlags);
    Log.log(4, "RPAction", "getGFPANDetails", "After session validation : " + 
        panDetails.size());
    actionForm.setDanPanDetail(danNo, panDetails);
    actionForm.setPanDetails(panDetails);
    actionForm.setAllocatedPanDetails(allocatedPanDetails);
    return mapping.findForward("gfpanDetails");
  }
  
  public ActionForward gfallocatePaymentsforTextile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    RpProcessor rpProcessor = new RpProcessor();
    RpDAO rpDAO = new RpDAO();
    String paymentId = "";
    String methodName = "gfallocatePaymentsforTextile";
    Log.log(5, "RPAction", methodName, "Entered");
    User user = getUserInformation(request);
    PaymentDetails paymentDetails = new PaymentDetails();
    String allocationType4 = actionForm.getAllocationType();
    paymentDetails.setAllocationType1(allocationType4);
    String modeOfPayment = actionForm.getModeOfPayment();
    double tempamounttobeallocated = rpDAO
      .getBalancePaymentFromOtherFacility(modeOfPayment);
    String collectingBranch = actionForm.getCollectingBankBranch();
    Date paymentDate = actionForm.getPaymentDate();
    String instrumentNumber = actionForm.getInstrumentNo();
    Date instrumentDate = actionForm.getInstrumentDate();
    String modeOfDelivery = actionForm.getModeOfDelivery();
    double instrumentAmount = actionForm.getInstrumentAmount();
    String drawnAtBank = actionForm.getDrawnAtBank();
    String drawnAtBranch = actionForm.getDrawnAtBranch();
    String payableAt = actionForm.getPayableAt();
    String accNumber = actionForm.getAccountNumber();
    Log.log(5, "RPAction", methodName, 
        "collecting bank " + actionForm.getCollectingBankName());
    paymentDetails.setCollectingBank(actionForm.getCollectingBankName());
    Log.log(5, "RPAction", methodName, "mode of payment " + modeOfPayment);
    paymentDetails.setModeOfPayment(modeOfPayment);
    Log.log(5, "RPAction", methodName, "collecting branch " + 
        collectingBranch);
    paymentDetails.setCollectingBankBranch(collectingBranch);
    Log.log(5, "RPAction", methodName, "payment date " + paymentDate);
    paymentDetails.setPaymentDate(paymentDate);
    Log.log(5, "RPAction", methodName, "instrument number " + 
        instrumentNumber);
    paymentDetails.setInstrumentNo(instrumentNumber);
    Log.log(5, "RPAction", methodName, "instrument date " + instrumentDate);
    paymentDetails.setInstrumentDate(instrumentDate);
    Log.log(5, "RPAction", methodName, "mode of delivery " + modeOfDelivery);
    paymentDetails.setModeOfDelivery(modeOfDelivery);
    Log.log(5, "RPAction", methodName, "instrument amount " + 
        instrumentAmount);
    paymentDetails.setInstrumentAmount(instrumentAmount);
    Log.log(5, "RPAction", methodName, "drawn at bank " + drawnAtBank);
    paymentDetails.setDrawnAtBank(drawnAtBank);
    Log.log(5, "RPAction", methodName, "drawn at branch " + drawnAtBranch);
    paymentDetails.setDrawnAtBranch(drawnAtBranch);
    Log.log(5, "RPAction", methodName, "payable at " + payableAt);
    paymentDetails.setPayableAt(payableAt);
    Log.log(5, "RPAction", methodName, "acc num " + accNumber);
    paymentDetails.setCgtsiAccNumber(accNumber);
    if (tempamounttobeallocated - instrumentAmount < 0.0D)
      throw new ShortExceedsLimitException(
          "Sufficient fund not available. Short by - " + (
          tempamounttobeallocated - instrumentAmount)); 
    if (tempamounttobeallocated - instrumentAmount >= 0.0D)
      rpDAO.updateTempUtilForOtherFacility(modeOfPayment, 
          instrumentAmount); 
    Map allocationFlags = actionForm.getAllocatedFlags();
    ArrayList<DANSummary> danSummaries = actionForm.getDanSummaries();
    Map cgpans = actionForm.getCgpans();
    Set cgpansSet = cgpans.keySet();
    Map<String, ArrayList<AllocationDetail>> danCgpanDetails = actionForm.getDanPanDetails();
    Map notAllocatedReasons = actionForm.getNotAllocatedReasons();
    for (int i = 0; i < danSummaries.size(); i++) {
      DANSummary danSummary = danSummaries.get(i);
      String danId = danSummary.getDanId();
      Log.log(5, "RPAction", methodName, "danId " + danId);
      String shiftDanId = danId.replace('.', '_');
      Log.log(5, "RPAction", methodName, 
          "contains " + danCgpanDetails.containsKey(danId));
      if (danCgpanDetails.containsKey(danId)) {
        ArrayList<AllocationDetail> panAllocationDetails = (ArrayList)danCgpanDetails
          .get(danId);
        if (panAllocationDetails == null) {
          Log.log(5, "RPAction", methodName, 
              "CGPAN details are not available. get them.");
          ArrayList<ArrayList> totalList = rpProcessor.displayCGPANs(danId);
          panAllocationDetails = totalList.get(0);
        } 
        for (int j = 0; j < panAllocationDetails.size(); j++) {
          AllocationDetail allocationDetail = panAllocationDetails
            .get(j);
          Log.log(5, 
              "RPActionForm", 
              "validate", 
              " cgpan from danpandetails " + 
              allocationDetail.getCgpan());
          if (allocationDetail.getAllocatedFlag().equals("N")) {
            Log.log(5, "RPActionForm", "validate", 
                " not allocated ");
            String reasons = (String)notAllocatedReasons
              .get(String.valueOf(shiftDanId) + "-" + 
                allocationDetail.getCgpan());
            Log.log(5, "RPActionForm", "validate", 
                " reason for not allocated " + reasons);
            allocationDetail.setNotAllocatedReason(reasons);
          } else {
            allocationDetail.setNotAllocatedReason("");
          } 
          panAllocationDetails.set(j, allocationDetail);
        } 
        danCgpanDetails.put(danId, panAllocationDetails);
      } 
    } 
    request.setAttribute("message", 
        "Payment Allocated Successfully.<BR>Payment ID : " + paymentId);
    Log.log(5, "RPAction", methodName, "Exited");
    return mapping.findForward("success");
  }
  
  public ActionForward getPendingSFDANsFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    User user = getUserInformation(request);
    String bankId = user.getBankId();
    RPActionForm rpActionForm = (RPActionForm)form;
    HttpSession session = request.getSession(false);
    if (bankId.equalsIgnoreCase("0000")) {
      rpActionForm.setSelectMember("");
      session.setAttribute("TARGET_URL", 
          "selectSFMember.do?method=getPendingSFDANs");
      return mapping.findForward("memberInfo");
    } 
    request.setAttribute("pageValue", "1");
    getPendingDANs(mapping, form, request, response);
    return mapping.findForward("danSummary");
  }
  
  public ActionForward getPendingDANs(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "getPendingDANs", "Entered");
    User user = getUserInformation(request);
    String bankId = user.getBankId();
    String zoneId = user.getZoneId();
    String branchId = user.getBranchId();
    String memberId = "";
    RPActionForm actionForm = (RPActionForm)form;
    HttpSession session = request.getSession(false);
    if (actionForm.getDanSummaries() != null)
      actionForm.getDanSummaries().clear(); 
    if (actionForm.getDanPanDetails() != null)
      actionForm.getDanPanDetails().clear(); 
    if (actionForm.getCgpans() != null)
      actionForm.getCgpans().clear(); 
    if (actionForm.getAllocatedFlags() != null)
      actionForm.getAllocatedFlags().clear(); 
    if (actionForm.getFirstDisbursementDates() != null)
      actionForm.getFirstDisbursementDates().clear(); 
    if (actionForm.getNotAllocatedReasons() != null)
      actionForm.getNotAllocatedReasons().clear(); 
    Log.log(5, "RPAction", "getPendingDANs", "Bank Id : " + bankId);
    Log.log(5, "RPAction", "getPendingDANs", "Zone Id : " + zoneId);
    Log.log(5, "RPAction", "getPendingDANs", "Branch Id : " + branchId);
    if (bankId.equals("0000")) {
      memberId = actionForm.getSelectMember();
      if (memberId == null || memberId.equals(""))
        memberId = actionForm.getMemberId(); 
      Log.log(5, "RPAction", "getPendingDANs", "mliId = " + memberId);
      if (memberId == null || memberId.equals("")) {
        session.setAttribute("TARGET_URL", 
            "selectMember1.do?method=getPendingDANs");
        return mapping.findForward("memberInfo");
      } 
      bankId = memberId.substring(0, 4);
      zoneId = memberId.substring(4, 8);
      branchId = memberId.substring(8, 12);
      ClaimsProcessor cpProcessor = new ClaimsProcessor();
      Vector memberIds = cpProcessor.getAllMemberIds();
      if (!memberIds.contains(memberId))
        throw new NoMemberFoundException("The Member ID does not exist"); 
    } 
    Log.log(5, "RPAction", "getPendingDANs", 
        "Selected Bank Id,zone and branch ids : " + bankId + "," + 
        zoneId + "," + branchId);
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList<DANSummary> danSummaries = rpProcessor.displayDANs(bankId, zoneId, 
        branchId);
    Log.log(5, "RPAction", "getPendingDANs", "dan summary size : " + 
        danSummaries.size());
    if (danSummaries.size() == 0) {
      actionForm.setSelectMember(null);
      throw new MissingDANDetailsException(
          "No DANs available for Allocation");
    } 
    boolean isDanAvailable = false;
    for (int i = 0; i < danSummaries.size(); i++) {
      DANSummary danSummary = danSummaries.get(i);
      if (danSummary.getAmountDue() != danSummary.getAmountPaid()) {
        isDanAvailable = true;
        break;
      } 
    } 
    if (!isDanAvailable) {
      actionForm.setSelectMember(null);
      throw new MissingDANDetailsException(
          "No DANs available for Allocation");
    } 
    actionForm.setDanSummaries(danSummaries);
    actionForm.setBankId(bankId);
    actionForm.setZoneId(zoneId);
    actionForm.setBranchId(branchId);
    Log.log(4, "RPAction", "getPendingDANs", "Exited");
    if (actionForm.getSelectMember() != null) {
      actionForm.setMemberId(actionForm.getSelectMember());
    } else {
      actionForm.setMemberId(String.valueOf(bankId) + zoneId + branchId);
    } 
    actionForm.setSelectMember(null);
    return mapping.findForward("danSummary");
  }
  
  public ActionForward getPendingASFDANs(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "getPendingASFDANs", "Entered");
    User user = getUserInformation(request);
    String bankId = user.getBankId();
    String zoneId = user.getZoneId();
    String branchId = user.getBranchId();
    String memberId = "";
    RPActionForm actionForm = (RPActionForm)form;
    HttpSession session = request.getSession(false);
    if (actionForm.getDanSummaries() != null)
      actionForm.getDanSummaries().clear(); 
    if (actionForm.getDanPanDetails() != null)
      actionForm.getDanPanDetails().clear(); 
    if (actionForm.getCgpans() != null)
      actionForm.getCgpans().clear(); 
    if (actionForm.getAllocatedFlags() != null)
      actionForm.getAllocatedFlags().clear(); 
    if (actionForm.getFirstDisbursementDates() != null)
      actionForm.getFirstDisbursementDates().clear(); 
    if (actionForm.getNotAllocatedReasons() != null)
      actionForm.getNotAllocatedReasons().clear(); 
    Log.log(5, "RPAction", "getPendingASFDANs", "Bank Id : " + bankId);
    Log.log(5, "RPAction", "getPendingASFDANs", "Zone Id : " + zoneId);
    Log.log(5, "RPAction", "getPendingASFDANs", "Branch Id : " + branchId);
    if (bankId.equals("0000")) {
      memberId = actionForm.getSelectMember();
      if (memberId == null || memberId.equals(""))
        memberId = actionForm.getMemberId(); 
      Log.log(5, "RPAction", "getPendingASFDANs", "mliId = " + memberId);
      if (memberId == null || memberId.equals("")) {
        Log.log(5, "RPAction", "getPendingASFDANs", "Menu Target = " + 
            MenuOptions.getMenuAction("RP_ALLOCATE_PAYMENTS"));
        session.setAttribute("TARGET_URL", 
            "selectASFMember.do?method=getPendingASFDANs");
        return mapping.findForward("memberInfo");
      } 
      bankId = memberId.substring(0, 4);
      zoneId = memberId.substring(4, 8);
      branchId = memberId.substring(8, 12);
      ClaimsProcessor cpProcessor = new ClaimsProcessor();
      Vector memberIds = cpProcessor.getAllMemberIds();
      if (!memberIds.contains(memberId))
        throw new NoMemberFoundException("The Member ID does not exist"); 
    } 
    Log.log(5, "RPAction", "getPendingASFDANs", 
        "Selected Bank Id,zone and branch ids : " + bankId + "," + 
        zoneId + "," + branchId);
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList<DANSummary> danSummaries = rpProcessor.displayASFDANs(bankId, zoneId, 
        branchId);
    Log.log(5, "RPAction", "getPendingASFDANs", "dan summary size : " + 
        danSummaries.size());
    if (danSummaries.size() == 0) {
      actionForm.setSelectMember(null);
      throw new MissingDANDetailsException(
          "No DANs available for Allocation");
    } 
    boolean isDanAvailable = false;
    for (int i = 0; i < danSummaries.size(); i++) {
      DANSummary danSummary = danSummaries.get(i);
      if (danSummary.getAmountDue() != danSummary.getAmountPaid()) {
        isDanAvailable = true;
        break;
      } 
    } 
    if (!isDanAvailable) {
      actionForm.setSelectMember(null);
      throw new MissingDANDetailsException(
          "No DANs available for Allocation");
    } 
    actionForm.setDanSummaries(danSummaries);
    actionForm.setBankId(bankId);
    actionForm.setZoneId(zoneId);
    actionForm.setBranchId(branchId);
    Log.log(4, "RPAction", "getPendingASFDANs", "Exited");
    if (actionForm.getSelectMember() != null) {
      actionForm.setMemberId(actionForm.getSelectMember());
    } else {
      actionForm.setMemberId(String.valueOf(bankId) + zoneId + branchId);
    } 
    actionForm.setSelectMember(null);
    return mapping.findForward("danSummary3");
  }
  
  public ActionForward getPendingExpiredASFDANs(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "getPendingExpiredASFDANs", "Entered");
    User user = getUserInformation(request);
    String bankId = user.getBankId();
    String zoneId = user.getZoneId();
    String branchId = user.getBranchId();
    String memberId = "";
    RPActionForm actionForm = (RPActionForm)form;
    HttpSession session = request.getSession(false);
    if (actionForm.getDanSummaries() != null)
      actionForm.getDanSummaries().clear(); 
    if (actionForm.getDanPanDetails() != null)
      actionForm.getDanPanDetails().clear(); 
    if (actionForm.getCgpans() != null)
      actionForm.getCgpans().clear(); 
    if (actionForm.getAllocatedFlags() != null)
      actionForm.getAllocatedFlags().clear(); 
    if (actionForm.getFirstDisbursementDates() != null)
      actionForm.getFirstDisbursementDates().clear(); 
    if (actionForm.getNotAllocatedReasons() != null)
      actionForm.getNotAllocatedReasons().clear(); 
    Log.log(5, "RPAction", "getPendingExpiredASFDANs", "Bank Id : " + 
        bankId);
    Log.log(5, "RPAction", "getPendingExpiredASFDANs", "Zone Id : " + 
        zoneId);
    Log.log(5, "RPAction", "getPendingExpiredASFDANs", "Branch Id : " + 
        branchId);
    if (bankId.equals("0000")) {
      memberId = actionForm.getSelectMember();
      if (memberId == null || memberId.equals(""))
        memberId = actionForm.getMemberId(); 
      Log.log(5, "RPAction", "getPendingExpiredASFDANs", "mliId = " + 
          memberId);
      if (memberId == null || memberId.equals("")) {
        Log.log(5, 
            "RPAction", 
            "getPendingExpiredASFDANs", 
            "Menu Target = " + 
            
            MenuOptions.getMenuAction("RP_ALLOCATE_PAYMENTS"));
        session.setAttribute("TARGET_URL", 
            "selectASFMemberForExpired.do?method=getPendingExpiredASFDANs");
        return mapping.findForward("memberInfo");
      } 
      bankId = memberId.substring(0, 4);
      zoneId = memberId.substring(4, 8);
      branchId = memberId.substring(8, 12);
      ClaimsProcessor cpProcessor = new ClaimsProcessor();
      Vector memberIds = cpProcessor.getAllMemberIds();
      if (!memberIds.contains(memberId))
        throw new NoMemberFoundException("The Member ID does not exist"); 
    } 
    Log.log(5, "RPAction", "getPendingExpiredASFDANs", 
        "Selected Bank Id,zone and branch ids : " + bankId + "," + 
        zoneId + "," + branchId);
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList<DANSummary> danSummaries = rpProcessor.displayASFDANsforExpired(bankId, 
        zoneId, branchId);
    Log.log(5, "RPAction", "getPendingExpiredASFDANs", 
        "dan summary size : " + danSummaries.size());
    if (danSummaries.size() == 0) {
      actionForm.setSelectMember(null);
      throw new MissingDANDetailsException(
          "No DANs available for Allocation");
    } 
    boolean isDanAvailable = false;
    for (int i = 0; i < danSummaries.size(); i++) {
      DANSummary danSummary = danSummaries.get(i);
      if (danSummary.getAmountDue() != danSummary.getAmountPaid()) {
        isDanAvailable = true;
        break;
      } 
    } 
    if (!isDanAvailable) {
      actionForm.setSelectMember(null);
      throw new MissingDANDetailsException(
          "No DANs available for Allocation");
    } 
    actionForm.setDanSummaries(danSummaries);
    actionForm.setBankId(bankId);
    actionForm.setZoneId(zoneId);
    actionForm.setBranchId(branchId);
    Log.log(4, "RPAction", "getPendingExpiredASFDANs", "Exited");
    if (actionForm.getSelectMember() != null) {
      actionForm.setMemberId(actionForm.getSelectMember());
    } else {
      actionForm.setMemberId(String.valueOf(bankId) + zoneId + branchId);
    } 
    actionForm.setSelectMember(null);
    return mapping.findForward("danSummary3");
  }
  
  public ActionForward displayallocatePaymentModifySubmit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "ClaimAction", "displayClaimProcessingInput", 
        "Entered");
    Connection connection = DBConnection.getConnection();
    HttpSession session = request.getSession();
    User user = getUserInformation(request);
    String bankid = user.getBankId().trim();
    String zoneid = user.getZoneId().trim();
    String branchid = user.getBranchId().trim();
    String memberId = String.valueOf(bankid) + zoneid + branchid;
    String dantype = "GF";
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    Log.log(4, "ClaimAction", "displayClaimProcessingInput", 
        "Exited");
    ArrayList<RPActionForm> rpArray = new ArrayList();
    RPActionForm actionFormobj = (RPActionForm)form;
    try {
      String query = "select PAY_ID, VIRTUAL_ACCOUNT_NO, AMOUNT, TO_CHAR(Pay_ID_CREAted_date, 'DD-MM-YYYY HH24:MI:SS') from online_payment_detail where PAYMENT_STATUS ='N' and DAN_TYPE='" + dantype + "'   and mem_bnk_id||mem_zne_id||mem_brn_id = '" + memberId + "'";
      PreparedStatement allocateModifyStmt = connection.prepareStatement(query);
      ResultSet allocateModifyResult = allocateModifyStmt.executeQuery();
      while (allocateModifyResult.next()) {
        RPActionForm actionForm = new RPActionForm();
        actionForm.setPaymentId1(allocateModifyResult.getString(1));
        actionForm.setVaccno(allocateModifyResult.getString(2));
        actionForm.setAmmount1(allocateModifyResult.getDouble(3));
        actionForm.setPayidcreateddate(allocateModifyResult
            .getString(4));
        String paymentIds = allocateModifyResult.getString(1);
        actionForm.setPaymentIds("paymentIds");
        rpArray.add(actionForm);
      } 
      actionFormobj.setAllocatepaymentmodify(rpArray);
    } catch (Exception exception) {
      Log.logException(exception);
      throw new DatabaseException(exception.getMessage());
    } finally {
      DBConnection.freeConnection(connection);
    } 
    return mapping.findForward("displayallocatePayments");
  }
  
  public ActionForward displayallocatePaymentModifySubmitDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Connection conn;
    HttpSession session = request.getSession();
    RPActionForm actionFormobj = (RPActionForm)form;
    String paymentID = request.getParameter("paymentIds");
    Map approveFlags = actionFormobj.getAllocationPaymentYes();
    if (approveFlags.size() == 0)
      throw new NoMemberFoundException(
          "Please select atleast one PAYMENT ID to Approve."); 
    System.out.println("payid " + approveFlags);
    System.out.println("payid value approveFlags " + approveFlags.size());
    Set keys = approveFlags.keySet();
    User user = getUserInformation(request);
    String userid = user.getUserId();
    String bankid = user.getBankId().trim();
    String zoneid = user.getZoneId().trim();
    String branchid = user.getBranchId().trim();
    String memberId = String.valueOf(bankid) + zoneid + branchid;
    Connection connection = DBConnection.getConnection();
    connection.setAutoCommit(false);
    Statement str1 = connection.createStatement();
    try {
      Iterator<String> clmInterat = keys.iterator();
      int insdanstatus = 0;
      int deldanstatus = 0;
      int deldanstatus1 = 0;
      int inspaystatus = 0;
      int delpaystatus = 0;
      while (clmInterat.hasNext()) {
        String payids = clmInterat.next();
        String[] arr = payids.split("@");
        System.out.println("PayID " + arr[0]);
        String decision = (String)approveFlags.get(payids);
        System.out.println("keys are" + payids);
        System.out.println("values  are" + decision);
        Date todaydate = new Date();
        String quryforSelect = "insert into dan_cgpan_info_temp_canc select *  from dan_cgpan_info_temp where pay_id ='" + 
          arr[0] + "' ";
        insdanstatus = str1.executeUpdate(quryforSelect);
        System.out.println("testing1" + quryforSelect);
        String quryforSelect3 = "insert into payment_detail_temp_canc  select * from  payment_detail_temp where pay_id ='" + 
          arr[0] + "' ";
        System.out.println("testing1" + quryforSelect3);
        inspaystatus = str1.executeUpdate(quryforSelect3);
        String quryforSelect4 = "delete from payment_detail_temp where pay_id ='" + 
          arr[0] + "'";
        System.out.println("testing1" + quryforSelect4);
        delpaystatus = str1.executeUpdate(quryforSelect4);
        String quryforSelect2 = "delete from dan_cgpan_info_temp where pay_id ='" + 
          arr[0] + "'";
        System.out.println("testing2" + quryforSelect2);
        deldanstatus = str1.executeUpdate(quryforSelect2);
        String quryforSelect5 = "update  online_payment_detail set PAYMENT_STATUS ='C' where PAY_ID  ='" + 
          arr[0] + "'";
        System.out.println("testing3" + quryforSelect5);
        deldanstatus1 = str1.executeUpdate(quryforSelect5);
        System.out.println("insdanstatus " + insdanstatus + 
            "deldanstatus " + deldanstatus + "inspaystatus " + 
            inspaystatus + "delpaystatus " + delpaystatus + 
            "deldanstatus1 " + deldanstatus1);
        if (insdanstatus != 0 && deldanstatus != 0 && 
          inspaystatus != 0 && delpaystatus != 0 && 
          deldanstatus1 != 0) {
          connection.commit();
          continue;
        } 
        connection.rollback();
        throw new MissingDANDetailsException(
            "not able to deallocate pay id. problem in  '" + 
            arr[0] + "'");
      } 
    } catch (SQLException sqlexception) {
      connection.rollback();
      throw new DatabaseException(sqlexception.getMessage());
    } finally {
      Connection connection1 = null;
      DBConnection.freeConnection(connection1);
    } 
    connection.commit();
    request.setAttribute("message", 
        " RP Modified / Cancelled successfully !!!");
    return mapping.findForward("success");
  }
  
  public ActionForward displayPaymentIdDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String PaymentId = "";
    Log.log(4, "RPAction", "displayPaymentIdDetail", "Entered");
    ReportManager manager = new ReportManager();
    RPActionForm rappform = (RPActionForm)form;
    String clmApplicationStatus = "";
    Log.log(4, 
        "RPAction", 
        "displayPaymentIdDetail", 
        
        "Claim Application Status being queried :" + 
        clmApplicationStatus);
    User user = getUserInformation(request);
    String bankid = user.getBankId().trim();
    String zoneid = user.getZoneId().trim();
    String branchid = user.getBranchId().trim();
    String memberId = bankid + zoneid + 
      branchid;
    request.setAttribute("PaymentId", request.getParameter("PaymentId"));
    PaymentId = request.getParameter("PaymentId");
    System.out.println(PaymentId);
    ArrayList<RPActionForm> rpArray = new ArrayList();
    RPActionForm actionFormobj = (RPActionForm)form;
    PreparedStatement pstmt = null;
    ResultSet rst = null;
    String PayId = "";
    Connection connection = DBConnection.getConnection();
    Statement allocateStmt = connection.createStatement();
    ResultSet allocateModifyResult1 = null;
    try {
      String query = "select cgpan,dan_id,dci_amount_raised,to_char(DCI_ALLOCATION_DT, 'DD-MM-YYYY HH24:MI:SS') from dan_cgpan_info_temp where pay_id ='" + 
        PaymentId + "' ";
      System.out.println("query" + query);
      allocateModifyResult1 = allocateStmt.executeQuery(query);
      RPActionForm actionForm = null;
      while (allocateModifyResult1.next()) {
        actionForm = new RPActionForm();
        actionForm.setCgpan1(allocateModifyResult1.getString(1));
        actionForm.setDanid1(allocateModifyResult1.getString(2));
        actionForm.setAmmount2(allocateModifyResult1.getDouble(3));
        actionForm.setDandate1(allocateModifyResult1.getString(4));
        String allocatedanIds = allocateModifyResult1.getString(2);
        System.out.println("displayPaymentIdDetail allocatedanIds" + 
            allocatedanIds);
        actionForm.setAllocatedanIds("allocatedanIds");
        actionForm.setPaymentId(request.getParameter("PaymentId"));
        rpArray.add(actionForm);
      } 
      actionFormobj.setAllocatepayIDdetails(rpArray);
    } catch (Exception exception) {
      Log.logException(exception);
      throw new DatabaseException(exception.getMessage());
    } finally {
      DBConnection.freeConnection(connection);
    } 
    return mapping.findForward("success");
  }
  
  public ActionForward submitDanWiseDeallocation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "generateClaimSFDAN", "Entered");
    HttpSession session = request.getSession();
    RPActionForm actionFormobj = (RPActionForm)form;
    String danID = request.getParameter("allocatedanIds");
    System.out.println("danID " + danID);
    Map approveFlags = actionFormobj.getAllocationPaymentDans();
    System.out.println("danID " + approveFlags);
    request.setAttribute("PaymentId", request.getParameter("PaymentId"));
    String PaymentId = "";
    PaymentId = request.getParameter("PaymentId");
    System.out.println(PaymentId);
    int size = 0;
    if (approveFlags.size() == 0)
      throw new NoMemberFoundException(
          "Please select atleast one DAN ID to Approve."); 
    System.out.println("danID " + approveFlags);
    System.out.println("danID value approveFlags " + approveFlags.size());
    Set keys = approveFlags.keySet();
    System.out.println("keys size " + keys.size());
    size = approveFlags.size();
    User user = getUserInformation(request);
    String userid = user.getUserId();
    String bankid = user.getBankId().trim();
    String zoneid = user.getZoneId().trim();
    String branchid = user.getBranchId().trim();
    String memberId = String.valueOf(bankid) + zoneid + branchid;
    Connection connection = DBConnection.getConnection();
    connection.setAutoCommit(false);
    Statement str1 = connection.createStatement();
    try {
      Iterator<String> danIterat = keys.iterator();
      int insdanstatus1 = 0;
      int deldanstatus1 = 0;
      int[] inspaystatus1 = new int[4];
      int[] delpaystatus1 = new int[5];
      int count = 0;
      ResultSet rs = null;
      while (danIterat.hasNext()) {
        String danids = danIterat.next();
        System.out
          .println("submitDanWiseDeallocation danids " + danids);
        String[] danIDPaymentID = danids.split("@");
        System.out.println("dan id== " + danIDPaymentID[0]);
        System.out.println("payment id==111 " + danIDPaymentID[1]);
        String quryforSelect4 = "select count(*) from dan_cgpan_info_temp where pay_id=(select pay_id from dan_cgpan_info_temp where dan_id='" + 
          danIDPaymentID[0] + "') ";
        System.out.println("testing quryforSelect4 " + quryforSelect4);
        rs = str1.executeQuery(quryforSelect4);
        while (rs.next())
          count = rs.getInt(1); 
        System.out.println("size " + size + " count " + count);
        if (size == count) {
          System.out.println("size==count");
          String quryforSelect10 = "update online_payment_detail set PAYMENT_STATUS ='C'  where pay_id='" + 
            danIDPaymentID[1] + "'";
          System.out.println("quryforSelect10" + quryforSelect10);
          delpaystatus1[4] = str1.executeUpdate(quryforSelect10);
          String str2 = "insert into payment_detail_temp_canc  select * from payment_detail_temp where pay_id='" + 
            danIDPaymentID[1] + "'";
          System.out.println("quryforSelect6" + str2);
          delpaystatus1[0] = str1.executeUpdate(str2);
          String quryforSelect8 = "delete from payment_detail_temp  where pay_id='" + 
            danIDPaymentID[1] + "'";
          System.out.println("quryforSelect8" + quryforSelect8);
          delpaystatus1[1] = str1.executeUpdate(quryforSelect8);
          String quryforSelect7 = "insert into dan_cgpan_info_temp_canc  select * from dan_cgpan_info_temp where pay_id='" + 
            danIDPaymentID[1] + "'";
          System.out.println("quryforSelect7" + quryforSelect7);
          delpaystatus1[2] = str1.executeUpdate(quryforSelect7);
          String quryforSelect9 = "delete from dan_cgpan_info_temp where pay_id='" + 
            danIDPaymentID[1] + "'";
          System.out.println("quryforSelect9" + quryforSelect9);
          delpaystatus1[3] = str1.executeUpdate(quryforSelect9);
          System.out.println("delpaystatus1[0] " + delpaystatus1[0]);
          System.out.println("delpaystatus1[1] " + delpaystatus1[1]);
          System.out.println("delpaystatus1[2] " + delpaystatus1[2]);
          System.out.println("delpaystatus1[3] " + delpaystatus1[3]);
          System.out.println("delpaystatus1[4] " + delpaystatus1[4]);
          if (delpaystatus1[0] != 0 && delpaystatus1[1] != 0 && 
            delpaystatus1[2] != 0 && 
            delpaystatus1[3] != 0 && 
            delpaystatus1[4] != 0) {
            connection.commit();
            break;
          } 
          connection.rollback();
          throw new MissingDANDetailsException(
              "Not able to deallocate dan id. problem in  '" + 
              danIDPaymentID[0] + "'");
        } 
        String quryforSelect3 = " update payment_detail_temp  set PAY_AMOUNT=PAY_AMOUNT-(select dci_amount_raised from dan_cgpan_info_temp  where  dan_id='" + 
          
          danIDPaymentID[0] + 
          "') where pay_id=(select a.pay_id from dan_cgpan_info_temp a where   dan_id='" + 
          danIDPaymentID[0] + "') ";
        System.out.println("quryforSelect3 " + quryforSelect3);
        inspaystatus1[2] = str1.executeUpdate(quryforSelect3);
        String quryforSelect6 = " update online_payment_detail  set AMOUNT=AMOUNT-(select dci_amount_raised from dan_cgpan_info_temp  where  dan_id='" + 
          
          danIDPaymentID[0] + 
          "') where pay_id=(select a.pay_id from dan_cgpan_info_temp a where   dan_id='" + 
          danIDPaymentID[0] + "') ";
        System.out.println(" quryforSelect6" + quryforSelect6);
        inspaystatus1[3] = str1.executeUpdate(quryforSelect6);
        String quryforSelect = "insert into dan_cgpan_info_temp_canc  select * from  dan_cgpan_info_temp where  dan_id = '" + 
          danIDPaymentID[0] + "' ";
        inspaystatus1[0] = str1.executeUpdate(quryforSelect);
        System.out.println("quryforSelect" + quryforSelect);
        String quryforSelect2 = "delete from dan_cgpan_info_temp where dan_id ='" + 
          danIDPaymentID[0] + "'";
        System.out.println("quryforSelect" + quryforSelect2);
        inspaystatus1[1] = str1.executeUpdate(quryforSelect2);
        System.out.println("inspaystatus1[0] " + inspaystatus1[0]);
        System.out.println("inspaystatus1[1] " + inspaystatus1[1]);
        System.out.println("inspaystatus1[2] " + inspaystatus1[2]);
        System.out.println("inspaystatus1[3] " + inspaystatus1[3]);
        if (inspaystatus1[0] != 0 && inspaystatus1[1] != 0 && 
          inspaystatus1[2] != 0 && 
          inspaystatus1[3] != 0) {
          connection.commit();
          continue;
        } 
        connection.rollback();
        throw new MissingDANDetailsException(
            " 22 Not able to deallocate dan id. problem in  '" + 
            danIDPaymentID[0] + "'");
      } 
    } catch (SQLException sqlexception) {
      connection.rollback();
      throw new DatabaseException(sqlexception.getMessage());
    } finally {
      DBConnection.freeConnection(connection);
    } 
    request.setAttribute("message", 
        "Selected RPs Modified/ Cancelled Successfully!!!");
    return mapping.findForward("success");
  }
  
  public ActionForward displayallocatePaymentFinal(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "ClaimAction", "displayClaimProcessingInput", 
        "Entered");
    Connection connection = DBConnection.getConnection();
    HttpSession session = request.getSession();
    User user = getUserInformation(request);
    System.out.println("user" + user);
    String bankid = user.getBankId().trim();
    String zoneid = user.getZoneId().trim();
    String branchid = user.getBranchId().trim();
    String memberId = String.valueOf(bankid) + zoneid + branchid;
    String dantype = "GF";
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    Log.log(4, "ClaimAction", "displayClaimProcessingInput", 
        "Exited");
    ArrayList<RPActionForm> rpArray = new ArrayList();
    RPActionForm actionFormobj = (RPActionForm)form;
    try {
      String query = "select PAY_ID, VIRTUAL_ACCOUNT_NO, AMOUNT, Pay_ID_CREAted_date from online_payment_detail where PAYMENT_STATUS='N' and DAN_TYPE='" + dantype + "' and mem_bnk_id||mem_zne_id||mem_brn_id = '" + memberId + "'";
      PreparedStatement allocatePaymentfinalStmt = connection.prepareStatement(query);
      ResultSet allocatePaymentFinalResult = allocatePaymentfinalStmt
        .executeQuery();
      while (allocatePaymentFinalResult.next()) {
        RPActionForm actionForm = new RPActionForm();
        actionForm.setPaymentIdF(allocatePaymentFinalResult
            .getString(1));
        actionForm.setVitrualAcF(allocatePaymentFinalResult
            .getString(2));
        actionForm.setAmtF(allocatePaymentFinalResult.getDouble(3));
        actionForm.setRPDATEF(allocatePaymentFinalResult.getString(4));
        String paymentIds = allocatePaymentFinalResult.getString(1);
        actionForm.setPaymentIds("paymentIds");
        rpArray.add(actionForm);
      } 
      actionFormobj.setAllocatepaymentFinal(rpArray);
    } catch (Exception exception) {
      Log.logException(exception);
      throw new DatabaseException(exception.getMessage());
    } finally {
      DBConnection.freeConnection(connection);
    } 
    return mapping.findForward("displayallocatePaymentFinal");
  }
  
  public ActionForward displayallocatePaymentFinalSubmit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    System.out.println("ENTERED++++++++++======");
    HttpSession session = request.getSession();
    RPActionForm actionFormobj = (RPActionForm)form;
    String paymentID = request.getParameter("paymentIds");
    Map approveFlags = actionFormobj.getAllocationPaymentFinalSubmit();
    session.setAttribute("approvedData", approveFlags);
    System.out.println("payid " + approveFlags);
    if (approveFlags.size() == 0)
      throw new NoMemberFoundException(
          "Please select atleast one PAYMENT-ID to MAKE PAYMENT."); 
    System.out.println("payid value approveFlags " + approveFlags.size());
    Set keys = approveFlags.keySet();
    System.out.println("konkati" + keys);
    User user = getUserInformation(request);
    String userid = user.getUserId();
    String bankid = user.getBankId().trim();
    String zoneid = user.getZoneId().trim();
    String branchid = user.getBranchId().trim();
    String memberId = String.valueOf(bankid) + zoneid + branchid;
    Connection connection=null;
    ArrayList<RPActionForm> rpArray = new ArrayList();
    try {
     connection = DBConnection.getConnection(false);
    CallableStatement cStmt = null;
    String errorCode = "";
    
    Iterator<String> PaymentIterate = keys.iterator();
    int insdanstatus = 0;
    RPActionForm actionForm = null;
    while (PaymentIterate.hasNext()) {
      actionForm = new RPActionForm();
      String payids = PaymentIterate.next();
      System.out.println("keys are" + payids);
      String[] arr = payids.split("@");
      System.out.println("PayID ====" + arr[0]);
      System.out.println("amount==== " + arr[1]);
      System.out.println("virtualno-=== " + arr[2]);
      actionForm.setPaymentIdR(arr[0]);
      actionForm.setAmmount2(Integer.parseInt(arr[1]));
      actionForm.setVaccno(arr[2]);
      actionForm.setPaymentInitiateDate(new Date());
      rpArray.add(actionForm);
      try {
        String query = "select PAYMENT_STATUS  from online_payment_detail where PAY_ID='" + 
          arr[0] + "' and PAYMENT_STATUS='X'";
        PreparedStatement makePaymentfinalStmt = connection.prepareStatement(query);
        ResultSet makePaymentFinalResult = makePaymentfinalStmt.executeQuery();
        if (makePaymentFinalResult.next())
          throw new NoMemberFoundException("Payment already done"); 
        try {
          cStmt = connection
            .prepareCall("{ call PACK_ONLINE_PAYMENT_DETAIL.PROC_XML_GENRATE(?,?)}");
          System.out.println("proc_dan_deallocation  633" + cStmt);
          cStmt.setString(1, arr[0]);
          cStmt.registerOutParameter(2, 12);
          cStmt.execute();
          String error = cStmt.getString(2);
          Log.log(5, "RPAction", "cancelRpAppropriation", 
              "Error code and error are " + errorCode + " " + 
              error);
          if (error != null) {
            connection.rollback();
            throw new DatabaseException(error);
          } 
        } catch (SQLException e) {
          try {
            connection.rollback();
          } catch (SQLException ignore) {
            Log.log(2, "RPAction", "cancelRpAppropriation", 
                ignore.getMessage());
          } 
          Log.log(2, "RPAction", "cancelRpAppropriation", 
              e.getMessage());
          Log.logException(e);
          throw new DatabaseException(e.getMessage());
        } 
      } finally {
//        DBConnection.freeConnection(connection);
      } 
      connection.commit();
    }
  }catch (SQLException ignore) {
	  connection.rollback();
      Log.log(2, "RPAction", "cancelRpAppropriation", 
              ignore.getMessage());
        } 
    finally {
    	if(connection!=null) {
    		connection.close();
    		connection =null;
    	}
//      DBConnection.freeConnection(connection);
    } 
    actionFormobj.setMakepaymentFinal(rpArray);
    return mapping.findForward("displayallocatePaymentFinalsubmit");
  }
  
  private HSSFWorkbook createWorkbook(ArrayList makepaymentFinal) {
    return null;
  }
  
  public ActionForward getPendingGFDANs(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "getPendingGFDANs", "Entered");
    User user = getUserInformation(request);
    String bankId = user.getBankId();
    String zoneId = user.getZoneId();
    String branchId = user.getBranchId();
    String memberId = "";
    RPActionForm actionForm = (RPActionForm)form;
    HttpSession session = request.getSession(false);
    if (actionForm.getDanSummaries() != null)
      actionForm.getDanSummaries().clear(); 
    if (actionForm.getDanPanDetails() != null)
      actionForm.getDanPanDetails().clear(); 
    if (actionForm.getCgpans() != null)
      actionForm.getCgpans().clear(); 
    if (actionForm.getAllocatedFlags() != null)
      actionForm.getAllocatedFlags().clear(); 
    if (actionForm.getFirstDisbursementDates() != null)
      actionForm.getFirstDisbursementDates().clear(); 
    if (actionForm.getNotAllocatedReasons() != null)
      actionForm.getNotAllocatedReasons().clear(); 
    if (actionForm.getAppropriatedFlags() != null)
      actionForm.getAppropriatedFlags().clear(); 
    Log.log(5, "RPAction", "getPendingGFDANs", "Bank Id : " + bankId);
    Log.log(5, "RPAction", "getPendingGFDANs", "Zone Id : " + zoneId);
    Log.log(5, "RPAction", "getPendingGFDANs", "Branch Id : " + branchId);
    if (bankId.equals("0000")) {
      memberId = actionForm.getSelectMember();
      if (memberId == null || memberId.equals(""))
        memberId = actionForm.getMemberId(); 
      Log.log(5, "RPAction", "getPendingGFDANs", "mliId = " + memberId);
      if (memberId == null || memberId.equals("")) {
        session.setAttribute("TARGET_URL", 
            "selectGFMember.do?method=getPendingGFDANs");
        return mapping.findForward("memberInfo");
      } 
      bankId = memberId.substring(0, 4);
      zoneId = memberId.substring(4, 8);
      branchId = memberId.substring(8, 12);
      ClaimsProcessor cpProcessor = new ClaimsProcessor();
      Vector memberIds = cpProcessor.getAllMemberIds();
      if (!memberIds.contains(memberId))
        throw new NoMemberFoundException("The Member ID does not exist"); 
    } 
    Log.log(5, "RPAction", "getPendingGFDANs", 
        "Selected Bank Id,zone and branch ids : " + bankId + "," + 
        zoneId + "," + branchId);
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList<DANSummary> danSummaries = rpProcessor.displayGFDANs(bankId, zoneId, 
        branchId);
    Log.log(5, "RPAction", "getPendingGFDANs", "dan summary size : " + 
        danSummaries.size());
    if (danSummaries.size() == 0) {
      actionForm.setSelectMember(null);
      throw new MissingDANDetailsException(
          "No DANs available for Allocation");
    } 
    boolean isDanAvailable = false;
    for (int i = 0; i < danSummaries.size(); i++) {
      DANSummary danSummary = danSummaries.get(i);
      if (danSummary.getAmountDue() != danSummary.getAmountPaid()) {
        isDanAvailable = true;
        break;
      } 
    } 
    if (!isDanAvailable) {
      actionForm.setSelectMember(null);
      throw new MissingDANDetailsException(
          "No DANs available for Allocation");
    } 
    actionForm.setDanSummaries(danSummaries);
    actionForm.setBankId(bankId);
    actionForm.setZoneId(zoneId);
    actionForm.setBranchId(branchId);
    Log.log(4, "RPAction", "getPendingGFDANs", "Exited");
    if (actionForm.getSelectMember() != null) {
      actionForm.setMemberId(actionForm.getSelectMember());
    } else {
      actionForm.setMemberId(String.valueOf(bankId) + zoneId + branchId);
    } 
    actionForm.setSelectMember(null);
    return mapping.findForward("danSummary2");
  }
  
  public ActionForward getPendingGFDANsOnline(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "getPendingGFDANs", "Entered");
    User user = getUserInformation(request);
    String bankId = user.getBankId();
    String zoneId = user.getZoneId();
    String branchId = user.getBranchId();
    String memberId = "";
    RPActionForm actionForm = (RPActionForm)form;
    HttpSession session = request.getSession(false);
    if (actionForm.getDanSummaries() != null)
      actionForm.getDanSummaries().clear(); 
    if (actionForm.getDanPanDetails() != null)
      actionForm.getDanPanDetails().clear(); 
    if (actionForm.getCgpans() != null)
      actionForm.getCgpans().clear(); 
    if (actionForm.getAllocatedFlags() != null)
      actionForm.getAllocatedFlags().clear(); 
    if (actionForm.getFirstDisbursementDates() != null)
      actionForm.getFirstDisbursementDates().clear(); 
    if (actionForm.getNotAllocatedReasons() != null)
      actionForm.getNotAllocatedReasons().clear(); 
    if (actionForm.getAppropriatedFlags() != null)
      actionForm.getAppropriatedFlags().clear(); 
    Log.log(5, "RPAction", "getPendingGFDANs", "Bank Id : " + bankId);
    Log.log(5, "RPAction", "getPendingGFDANs", "Zone Id : " + zoneId);
    Log.log(5, "RPAction", "getPendingGFDANs", "Branch Id : " + branchId);
    if (bankId.equals("0000")) {
      memberId = actionForm.getSelectMember();
      if (memberId == null || memberId.equals(""))
        memberId = actionForm.getMemberId(); 
      Log.log(5, "RPAction", "getPendingGFDANs", "mliId = " + memberId);
      if (memberId == null || memberId.equals("")) {
        session.setAttribute("TARGET_URL", 
            "selectGFMember.do?method=getPendingGFDANs");
        return mapping.findForward("memberInfo");
      } 
      bankId = memberId.substring(0, 4);
      zoneId = memberId.substring(4, 8);
      branchId = memberId.substring(8, 12);
      ClaimsProcessor cpProcessor = new ClaimsProcessor();
      Vector memberIds = cpProcessor.getAllMemberIds();
      if (!memberIds.contains(memberId))
        throw new NoMemberFoundException("The Member ID does not exist"); 
    } 
    Log.log(5, "RPAction", "getPendingGFDANs", 
        "Selected Bank Id,zone and branch ids : " + bankId + "," + 
        zoneId + "," + branchId);
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList<DANSummary> danSummaries = rpProcessor.displayGFDANs(bankId, zoneId, 
        branchId);
    Log.log(5, "RPAction", "getPendingGFDANs", "dan summary size : " + 
        danSummaries.size());
    if (danSummaries.size() == 0) {
      actionForm.setSelectMember(null);
      throw new MissingDANDetailsException(
          "No DANs available for Allocation");
    } 
    boolean isDanAvailable = false;
    for (int i = 0; i < danSummaries.size(); i++) {
      DANSummary danSummary = danSummaries.get(i);
      if (danSummary.getAmountDue() != danSummary.getAmountPaid()) {
        isDanAvailable = true;
        break;
      } 
    } 
    if (!isDanAvailable) {
      actionForm.setSelectMember(null);
      throw new MissingDANDetailsException(
          "No DANs available for Allocation");
    } 
    actionForm.setDanSummaries(danSummaries);
    actionForm.setBankId(bankId);
    actionForm.setZoneId(zoneId);
    actionForm.setBranchId(branchId);
    Log.log(4, "RPAction", "getPendingGFDANs", "Exited");
    if (actionForm.getSelectMember() != null) {
      actionForm.setMemberId(actionForm.getSelectMember());
    } else {
      actionForm.setMemberId(String.valueOf(bankId) + zoneId + branchId);
    } 
    actionForm.setSelectMember(null);
    return mapping.findForward("danSummary2");
  }
  
  public ActionForward getNEFTPendingGFDANs(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "getNEFTPendingGFDANs", "Entered");
    User user = getUserInformation(request);
    String bankId = user.getBankId();
    String zoneId = user.getZoneId();
    String branchId = user.getBranchId();
    String memberId = "";
    RPActionForm actionForm = (RPActionForm)form;
    HttpSession session = request.getSession(false);
    if (actionForm.getDanSummaries() != null)
      actionForm.getDanSummaries().clear(); 
    if (actionForm.getDanPanDetails() != null)
      actionForm.getDanPanDetails().clear(); 
    if (actionForm.getCgpans() != null)
      actionForm.getCgpans().clear(); 
    if (actionForm.getAllocatedFlags() != null)
      actionForm.getAllocatedFlags().clear(); 
    if (actionForm.getFirstDisbursementDates() != null)
      actionForm.getFirstDisbursementDates().clear(); 
    if (actionForm.getNotAllocatedReasons() != null)
      actionForm.getNotAllocatedReasons().clear(); 
    if (actionForm.getAppropriatedFlags() != null)
      actionForm.getAppropriatedFlags().clear(); 
    Log.log(5, "RPAction", "getNEFTPendingGFDANs", "Bank Id : " + bankId);
    Log.log(5, "RPAction", "getNEFTPendingGFDANs", "Zone Id : " + zoneId);
    Log.log(5, "RPAction", "getNEFTPendingGFDANs", "Branch Id : " + 
        branchId);
    if (bankId.equals("0000")) {
      memberId = actionForm.getSelectMember();
      if (memberId == null || memberId.equals(""))
        memberId = actionForm.getMemberId(); 
      Log.log(5, "RPAction", "getNEFTPendingGFDANs", "mliId = " + 
          memberId);
      if (memberId == null || memberId.equals("")) {
        session.setAttribute("TARGET_URL", 
            "selectGFMemberNew.do?method=getNEFTPendingGFDANs");
        return mapping.findForward("memberInfoNew");
      } 
      bankId = memberId.substring(0, 4);
      zoneId = memberId.substring(4, 8);
      branchId = memberId.substring(8, 12);
      ClaimsProcessor cpProcessor = new ClaimsProcessor();
      Vector memberIds = cpProcessor.getAllMemberIds();
      if (!memberIds.contains(memberId))
        throw new NoMemberFoundException("The Member ID does not exist"); 
    } 
    Log.log(5, "RPAction", "getNEFTPendingGFDANs", 
        "Selected Bank Id,zone and branch ids : " + bankId + "," + 
        zoneId + "," + branchId);
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList<DANSummary> danSummaries = rpProcessor.displayGFDANs(bankId, zoneId, 
        branchId);
    Log.log(5, "RPAction", "getNEFTPendingGFDANs", "dan summary size : " + 
        danSummaries.size());
    if (danSummaries.size() == 0) {
      actionForm.setSelectMember(null);
      throw new MissingDANDetailsException(
          "No DANs available for Allocation");
    } 
    boolean isDanAvailable = false;
    for (int i = 0; i < danSummaries.size(); i++) {
      DANSummary danSummary = danSummaries.get(i);
      if (danSummary.getAmountDue() != danSummary.getAmountPaid()) {
        isDanAvailable = true;
        break;
      } 
    } 
    if (!isDanAvailable) {
      actionForm.setSelectMember(null);
      throw new MissingDANDetailsException(
          "No DANs available for Allocation");
    } 
    actionForm.setDanSummaries(danSummaries);
    actionForm.setBankId(bankId);
    actionForm.setZoneId(zoneId);
    actionForm.setBranchId(branchId);
    Log.log(4, "RPAction", "getNEFTPendingGFDANs", "Exited");
    if (actionForm.getSelectMember() != null) {
      actionForm.setMemberId(actionForm.getSelectMember());
    } else {
      actionForm.setMemberId(String.valueOf(bankId) + zoneId + branchId);
    } 
    actionForm.setSelectMember(null);
    return mapping.findForward("neftdanSummary");
  }
  
  public ActionForward getPendingSFDANs(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "getPendingDANs", "Entered");
    User user = getUserInformation(request);
    String bankId = user.getBankId();
    String zoneId = user.getZoneId();
    String branchId = user.getBranchId();
    String memberId = "";
    RPActionForm actionForm = (RPActionForm)form;
    HttpSession session = request.getSession(false);
    if (actionForm.getDanSummaries() != null)
      actionForm.getDanSummaries().clear(); 
    if (actionForm.getDanPanDetails() != null)
      actionForm.getDanPanDetails().clear(); 
    if (actionForm.getCgpans() != null)
      actionForm.getCgpans().clear(); 
    if (actionForm.getAllocatedFlags() != null)
      actionForm.getAllocatedFlags().clear(); 
    if (actionForm.getFirstDisbursementDates() != null)
      actionForm.getFirstDisbursementDates().clear(); 
    if (actionForm.getNotAllocatedReasons() != null)
      actionForm.getNotAllocatedReasons().clear(); 
    Log.log(5, "RPAction", "getPendingDANs", "Bank Id : " + bankId);
    Log.log(5, "RPAction", "getPendingDANs", "Zone Id : " + zoneId);
    Log.log(5, "RPAction", "getPendingDANs", "Branch Id : " + branchId);
    if (bankId.equals("0000")) {
      memberId = actionForm.getSelectMember();
      if (memberId == null || memberId.equals(""))
        memberId = actionForm.getMemberId(); 
      Log.log(5, "RPAction", "getPendingSFDANs", "mliId = " + memberId);
      if (memberId == null || memberId.equals("")) {
        session.setAttribute("TARGET_URL", 
            "selectMember1.do?method=getPendingSFDANs");
        return mapping.findForward("memberInfo");
      } 
      bankId = memberId.substring(0, 4);
      zoneId = memberId.substring(4, 8);
      branchId = memberId.substring(8, 12);
      ClaimsProcessor cpProcessor = new ClaimsProcessor();
      Vector memberIds = cpProcessor.getAllMemberIds();
      if (!memberIds.contains(memberId))
        throw new NoMemberFoundException("The Member ID does not exist"); 
    } 
    Log.log(5, "RPAction", "getPendingDANs", 
        "Selected Bank Id,zone and branch ids : " + bankId + "," + 
        zoneId + "," + branchId);
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList<DANSummary> danSummaries = rpProcessor.displaySFDANs(bankId, zoneId, 
        branchId);
    Log.log(5, "RPAction", "getPendingDANs", "dan summary size : " + 
        danSummaries.size());
    if (danSummaries.size() == 0) {
      actionForm.setSelectMember(null);
      throw new MissingDANDetailsException(
          "No DANs available for Allocation");
    } 
    boolean isDanAvailable = false;
    for (int i = 0; i < danSummaries.size(); i++) {
      DANSummary danSummary = danSummaries.get(i);
      if (danSummary.getAmountDue() != danSummary.getAmountPaid()) {
        isDanAvailable = true;
        break;
      } 
    } 
    if (!isDanAvailable) {
      actionForm.setSelectMember(null);
      throw new MissingDANDetailsException(
          "No DANs available for Allocation");
    } 
    actionForm.setDanSummaries(danSummaries);
    actionForm.setBankId(bankId);
    actionForm.setZoneId(zoneId);
    actionForm.setBranchId(branchId);
    Log.log(4, "RPAction", "getPendingDANs", "Exited");
    if (actionForm.getSelectMember() != null) {
      actionForm.setMemberId(actionForm.getSelectMember());
    } else {
      actionForm.setMemberId(String.valueOf(bankId) + zoneId + branchId);
    } 
    actionForm.setSelectMember(null);
    return mapping.findForward("danSummary");
  }
  
  public ActionForward getASFPANDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "getASFPANDetails", "Entered");
    RPActionForm actionForm = (RPActionForm)form;
    Map cgpans = actionForm.getCgpans();
    Set cgpanSet = cgpans.keySet();
    Iterator<String> cgpanIterator = cgpanSet.iterator();
    Log.log(5, "RPAction", "getASFPANDetails", "CGPANS selected ");
    while (cgpanIterator.hasNext()) {
      String key = cgpanIterator.next();
      Log.log(5, "RPAction", "getASFPANDetails", "key,value " + key + "," + 
          cgpans.get(key));
    } 
    Log.log(5, "RPAction", "getASFPANDetails", 
        "Cgpan map size " + cgpans.size());
    String danNo = actionForm.getDanNo();
    Log.log(4, "RPAction", "getASFPANDetails", "On entering, DAN no: " + 
        danNo);
    Log.log(4, "RPAction", "getASFPANDetails", "No Session: DAN no : " + 
        danNo);
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList<ArrayList> returnList = rpProcessor.displayCGPANs(danNo);
    ArrayList<AllocationDetail> panDetails = returnList.get(0);
    ArrayList allocatedPanDetails = returnList.get(1);
    Log.log(4, "RPAction", "getASFPANDetails", 
        "No Session: No. of PAN details : " + panDetails.size());
    String allocatedFlag = (String)actionForm.getAllocatedFlag(danNo
        .replace('.', '_'));
    Log.log(4, "RPAction", "getASFPANDetails", "flag " + allocatedFlag);
    Map<String, String> allocatedFlags = actionForm.getAllocatedFlags();
    if (allocatedFlag != null && allocatedFlag.equalsIgnoreCase(danNo))
      for (int i = 0; i < panDetails.size(); i++) {
        AllocationDetail allocationDetail = panDetails
          .get(i);
        String key = String.valueOf(danNo.replace('.', '_')) + "-" + 
          allocationDetail.getCgpan();
        allocatedFlags.put(key, "Y");
      }  
    actionForm.setAllocatedFlags(allocatedFlags);
    Log.log(4, "RPAction", "getASFPANDetails", 
        "After session validation : " + panDetails.size());
    actionForm.setDanPanDetail(danNo, panDetails);
    actionForm.setPanDetails(panDetails);
    actionForm.setAllocatedPanDetails(allocatedPanDetails);
    return mapping.findForward("asfpanDetails");
  }
  
  public ActionForward getExpiredASFPANDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "getExpiredASFPANDetails", "Entered");
    RPActionForm actionForm = (RPActionForm)form;
    Map cgpans = actionForm.getCgpans();
    Set cgpanSet = cgpans.keySet();
    Iterator<String> cgpanIterator = cgpanSet.iterator();
    Log.log(5, "RPAction", "getExpiredASFPANDetails", "CGPANS selected ");
    while (cgpanIterator.hasNext()) {
      String key = cgpanIterator.next();
      Log.log(5, "RPAction", "getExpiredASFPANDetails", "key,value " + 
          key + "," + cgpans.get(key));
    } 
    Log.log(5, "RPAction", "getExpiredASFPANDetails", "Cgpan map size " + 
        cgpans.size());
    String danNo = actionForm.getDanNo();
    Log.log(4, "RPAction", "getExpiredASFPANDetails", 
        "On entering, DAN no: " + danNo);
    Log.log(4, "RPAction", "getExpiredASFPANDetails", 
        "No Session: DAN no : " + danNo);
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList<ArrayList> returnList = rpProcessor.displayCGPANs(danNo);
    ArrayList<AllocationDetail> panDetails = returnList.get(0);
    ArrayList allocatedPanDetails = returnList.get(1);
    Log.log(4, "RPAction", "getExpiredASFPANDetails", 
        "No Session: No. of PAN details : " + panDetails.size());
    String allocatedFlag = (String)actionForm.getAllocatedFlag(danNo
        .replace('.', '_'));
    Log.log(4, "RPAction", "getExpiredASFPANDetails", "flag " + 
        allocatedFlag);
    Map<String, String> allocatedFlags = actionForm.getAllocatedFlags();
    if (allocatedFlag != null && allocatedFlag.equalsIgnoreCase(danNo))
      for (int i = 0; i < panDetails.size(); i++) {
        AllocationDetail allocationDetail = panDetails
          .get(i);
        String key = String.valueOf(danNo.replace('.', '_')) + "-" + 
          allocationDetail.getCgpan();
        allocatedFlags.put(key, "Y");
      }  
    actionForm.setAllocatedFlags(allocatedFlags);
    Log.log(4, "RPAction", "getExpiredASFPANDetails", 
        "After session validation : " + panDetails.size());
    actionForm.setDanPanDetail(danNo, panDetails);
    actionForm.setPanDetails(panDetails);
    actionForm.setAllocatedPanDetails(allocatedPanDetails);
    return mapping.findForward("asfpanDetails");
  }
  
  public ActionForward getGFPANDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "getGFPANDetails", "Entered");
    RPActionForm actionForm = (RPActionForm)form;
    Map cgpans = actionForm.getCgpans();
    Set cgpanSet = cgpans.keySet();
    Iterator<String> cgpanIterator = cgpanSet.iterator();
    Log.log(5, "RPAction", "getGFPANDetails", "CGPANS selected ");
    while (cgpanIterator.hasNext()) {
      String key = cgpanIterator.next();
      Log.log(5, "RPAction", "getGFPANDetails", "key,value " + key + "," + 
          cgpans.get(key));
    } 
    Log.log(5, "RPAction", "getGFPANDetails", 
        "Cgpan map size " + cgpans.size());
    String danNo = actionForm.getDanNo();
    Log.log(4, "RPAction", "getGFPANDetails", "On entering, DAN no: " + 
        danNo);
    Log.log(4, "RPAction", "getGFPANDetails", "No Session: DAN no : " + 
        danNo);
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList<ArrayList> returnList = rpProcessor.displayCGPANs(danNo);
    ArrayList<AllocationDetail> panDetails = returnList.get(0);
    ArrayList allocatedPanDetails = returnList.get(1);
    Log.log(4, "RPAction", "getGFPANDetails", 
        "No Session: No. of PAN details : " + panDetails.size());
    String allocatedFlag = (String)actionForm.getAllocatedFlag(danNo
        .replace('.', '_'));
    Log.log(4, "RPAction", "getGFPANDetails", "flag " + allocatedFlag);
    Map<String, String> allocatedFlags = actionForm.getAllocatedFlags();
    if (allocatedFlag != null && allocatedFlag.equalsIgnoreCase(danNo))
      for (int i = 0; i < panDetails.size(); i++) {
        AllocationDetail allocationDetail = panDetails
          .get(i);
        String key = String.valueOf(danNo.replace('.', '_')) + "-" + 
          allocationDetail.getCgpan();
        allocatedFlags.put(key, "Y");
      }  
    actionForm.setAllocatedFlags(allocatedFlags);
    Log.log(4, "RPAction", "getGFPANDetails", "After session validation : " + 
        panDetails.size());
    actionForm.setDanPanDetail(danNo, panDetails);
    actionForm.setPanDetails(panDetails);
    actionForm.setAllocatedPanDetails(allocatedPanDetails);
    return mapping.findForward("gfpanDetails");
  }
  
  public ActionForward getPANDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "getPANDetails", "Entered");
    RPActionForm actionForm = (RPActionForm)form;
    Map cgpans = actionForm.getCgpans();
    Set cgpanSet = cgpans.keySet();
    Iterator<String> cgpanIterator = cgpanSet.iterator();
    Log.log(5, "RPAction", "getPANDetails", "CGPANS selected ");
    while (cgpanIterator.hasNext()) {
      String key = cgpanIterator.next();
      Log.log(5, "RPAction", "getPANDetails", "key,value " + key + "," + 
          cgpans.get(key));
    } 
    Log.log(5, "RPAction", "getPANDetails", 
        "Cgpan map size " + cgpans.size());
    String danNo = actionForm.getDanNo();
    Log.log(4, "RPAction", "getPANDetails", "On entering, DAN no: " + danNo);
    Log.log(4, "RPAction", "getPANDetails", "No Session: DAN no : " + danNo);
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList<ArrayList> returnList = rpProcessor.displayCGPANs(danNo);
    ArrayList<AllocationDetail> panDetails = returnList.get(0);
    ArrayList allocatedPanDetails = returnList.get(1);
    Log.log(4, "RPAction", "getPANDetails", 
        "No Session: No. of PAN details : " + panDetails.size());
    String allocatedFlag = (String)actionForm.getAllocatedFlag(danNo
        .replace('.', '_'));
    Log.log(4, "RPAction", "getPANDetails", "flag " + allocatedFlag);
    Map<String, String> allocatedFlags = actionForm.getAllocatedFlags();
    if (allocatedFlag != null && allocatedFlag.equalsIgnoreCase(danNo))
      for (int i = 0; i < panDetails.size(); i++) {
        AllocationDetail allocationDetail = panDetails
          .get(i);
        String key = String.valueOf(danNo.replace('.', '_')) + "-" + 
          allocationDetail.getCgpan();
        allocatedFlags.put(key, "Y");
      }  
    actionForm.setAllocatedFlags(allocatedFlags);
    Log.log(4, "RPAction", "getPANDetails", "After session validation : " + 
        panDetails.size());
    actionForm.setDanPanDetail(danNo, panDetails);
    actionForm.setPanDetails(panDetails);
    actionForm.setAllocatedPanDetails(allocatedPanDetails);
    return mapping.findForward("panDetails");
  }
  
  public ActionForward getSFPANDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "getSFPANDetails", "Entered");
    RPActionForm actionForm = (RPActionForm)form;
    Map cgpans = actionForm.getCgpans();
    Set cgpanSet = cgpans.keySet();
    Iterator<String> cgpanIterator = cgpanSet.iterator();
    Log.log(5, "RPAction", "getSFPANDetails", "CGPANS selected ");
    while (cgpanIterator.hasNext()) {
      String key = cgpanIterator.next();
      Log.log(5, "RPAction", "getPANDetails", "key,value " + key + "," + 
          cgpans.get(key));
    } 
    Log.log(5, "RPAction", "getPANDetails", 
        "Cgpan map size " + cgpans.size());
    String danNo = actionForm.getDanNo();
    Log.log(4, "RPAction", "getPANDetails", "On entering, DAN no: " + danNo);
    Log.log(4, "RPAction", "getPANDetails", "No Session: DAN no : " + danNo);
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList<ArrayList> returnList = rpProcessor.displaySFCGPANs(danNo);
    ArrayList<AllocationDetail> panDetails = returnList.get(0);
    ArrayList allocatedPanDetails = returnList.get(1);
    Log.log(4, "RPAction", "getPANDetails", 
        "No Session: No. of PAN details : " + panDetails.size());
    String allocatedFlag = (String)actionForm.getAllocatedFlag(danNo
        .replace('.', '_'));
    Log.log(4, "RPAction", "getPANDetails", "flag " + allocatedFlag);
    Map<String, String> allocatedFlags = actionForm.getAllocatedFlags();
    if (allocatedFlag != null && allocatedFlag.equalsIgnoreCase(danNo))
      for (int i = 0; i < panDetails.size(); i++) {
        AllocationDetail allocationDetail = panDetails
          .get(i);
        String key = String.valueOf(danNo.replace('.', '_')) + "-" + 
          allocationDetail.getCgpan();
        allocatedFlags.put(key, "Y");
      }  
    actionForm.setAllocatedFlags(allocatedFlags);
    Log.log(4, "RPAction", "getPANDetails", "After session validation : " + 
        panDetails.size());
    actionForm.setDanPanDetail(danNo, panDetails);
    actionForm.setPanDetails(panDetails);
    actionForm.setAllocatedPanDetails(allocatedPanDetails);
    return mapping.findForward("panDetails");
  }
  
  public ActionForward deAllocatePayments(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "deAllocatePayments", "Entered");
    RPActionForm actionForm = (RPActionForm)form;
    RpProcessor rpProcessor = new RpProcessor();
    User user = getUserInformation(request);
    String userId = user.getUserId();
    String paymentId = null;
    paymentId = actionForm.getPaymentId();
    if (paymentId != null) {
      rpProcessor.deAllocatePayments(paymentId, userId);
      actionForm.setPaymentId(null);
    } 
    request.setAttribute("message", "Payment ID : " + paymentId + 
        " Cancelled Successfully:");
    Log.log(4, "RPAction", "deAllocatePayments", "Exited");
    return mapping.findForward("success");
  }
  
  public ActionForward deAllocatePaymentsInput(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "deAllocatePaymentsInput", "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    Log.log(4, "RPAction", "deAllocatePaymentsInput", "Exited");
    return mapping.findForward("success");
  }
  
  public ActionForward neftAllocatePaymentsInput(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "neftAllocatePaymentsInput", "Entered");
    RPActionForm dynaForm = (RPActionForm)form;
    String paymentId = null;
    dynaForm.setPaymentId("");
    dynaForm.setMemberId("");
    dynaForm.setPaymentDate(null);
    dynaForm.setNeftCode("");
    Log.log(4, "RPAction", "neftAllocatePaymentsInput", "Exited");
    return mapping.findForward("success");
  }
  
  public ActionForward neftAllocatePayments(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "neftAllocatePayments", "Entered");
    RPActionForm actionForm = (RPActionForm)form;
    RpDAO rpDAO = new RpDAO();
    RpProcessor rpProcessor = new RpProcessor();
    User user = getUserInformation(request);
    String userId = user.getUserId();
    String bankId = user.getBankId();
    String zoneId = user.getZoneId();
    String branchId = user.getBranchId();
    String paymentId = null;
    paymentId = actionForm.getPaymentId();
    String loginMemberId = bankId.concat(zoneId).concat(branchId);
    PaymentDetails paymentDtls = null;
    if (paymentId != null) {
      String memberId = rpDAO.getMemberId(paymentId);
      if (memberId != null) {
        if (loginMemberId.equals(memberId)) {
          paymentDtls = rpDAO.getPayInSlipDetails(paymentId);
          double allocatedAmt = paymentDtls.getInstrumentAmount();
          actionForm.setAllocatedAmt(allocatedAmt);
          actionForm.setPaymentId(paymentId);
          actionForm.setMemberId(memberId);
          actionForm.setBankId(bankId);
          actionForm.setZoneId(zoneId);
          actionForm.setBranchId(branchId);
        } else {
          throw new DatabaseException(
              "RP Number not relevant with member id-" + 
              loginMemberId + 
              ".Please check RP number.");
        } 
      } else {
        throw new DatabaseException("Please check RP number.");
      } 
      return mapping.findForward("insertPayId");
    } 
    Log.log(4, "RPAction", "neftAllocatePayments", "Exited");
    return mapping.findForward("success");
  }
  
  public ActionForward SubmitMappingRPandNEFT(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    RpDAO rpDAO = new RpDAO();
    RpProcessor rpProcessor = new RpProcessor();
    User user = getUserInformation(request);
    String userId = user.getUserId();
    String paymentId = actionForm.getPaymentId();
    String memberId = actionForm.getMemberId();
    double allocatedAmt = actionForm.getAllocatedAmt();
    String neftCode = actionForm.getNeftCode();
    String bankName = actionForm.getBankName();
    String zoneName = actionForm.getZoneName();
    String branchName = actionForm.getBranchName();
    String ifscCode = actionForm.getIfscCode();
    Date paymentDate = actionForm.getPaymentDate();
    rpDAO.afterMapRPwithNEFTDtls(memberId, paymentId, allocatedAmt, 
        neftCode, bankName, zoneName, branchName, ifscCode, 
        paymentDate, userId);
    request.setAttribute("message", "<b> Mapping Payment Id " + paymentId + 
        " with NEFT transaction " + neftCode + " Successful");
    return mapping.findForward("success");
  }
  
  public ActionForward modifyAllocatePaymentDetailInput(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "modifyAllocatePaymentDetailInput", "Entered");
    RPActionForm rpAllocationForm = (RPActionForm)form;
    String paymentId = "";
    rpAllocationForm.setPaymentId(paymentId);
    Log.log(4, "RPAction", "modifyAllocatePaymentDetailInput", "Exited");
    return mapping.findForward("success");
  }
  
  public ActionForward modifyAllocatePaymentDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "modifyAllocatePaymentDetail", "Entered");
    RPActionForm rpActionForm = (RPActionForm)form;
    String paymentId = rpActionForm.getPaymentId().toUpperCase();
    Log.log(5, "RPAction", "getPaymentDetailsForPayInSlip", "paymentId " + 
        paymentId);
    RpProcessor rpProcessor = new RpProcessor();
    PaymentDetails paymentDetails = rpProcessor.displayPayInSlip(paymentId);
    paymentDetails.setPaymentId(paymentId);
    paymentDetails.setNewInstrumentNo(paymentDetails.getInstrumentNo());
    paymentDetails.setNewInstrumentDt(paymentDetails.getInstrumentDate());
    BeanUtils.copyProperties(rpActionForm, paymentDetails);
    paymentDetails = null;
    Log.log(4, "RPAction", "modifyAllocatePaymentDetail", "Exited");
    return mapping.findForward("insertPayId");
  }
  
  public ActionForward afterModifyAllocatePaymentDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "afterModifyAllocatePaymentDetail", "Entered");
    RPActionForm rpActionForm = (RPActionForm)form;
    RpProcessor rpProcessor = new RpProcessor();
    RpDAO rpDAO = new RpDAO();
    User user = getUserInformation(request);
    String bankId = user.getBankId();
    String zoneId = user.getZoneId();
    String branchId = user.getBranchId();
    String memberId = bankId.concat(zoneId).concat(branchId);
    rpDAO.afterModifyAllocatePaymentDetail(rpActionForm, user.getUserId());
    request.setAttribute("message", "Allocated Payment Id - " + 
        rpActionForm.getPaymentId() + 
        " Details Modified Successfully ");
    Log.log(4, "RPAction", "afterModifyAllocatePaymentDetail", "Exited");
    return mapping.findForward("success");
  }
  
  public ActionForward asfallocatePayments(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    RpProcessor rpProcessor = new RpProcessor();
    String paymentId = "";
    String methodName = "asfallocatePayments";
    Log.log(5, "RPAction", methodName, "Entered");
    User user = getUserInformation(request);
    PaymentDetails paymentDetails = new PaymentDetails();
    String allocationType2 = actionForm.getAllocationType();
    paymentDetails.setAllocationType1(allocationType2);
    String modeOfPayment = actionForm.getModeOfPayment();
    String collectingBranch = actionForm.getCollectingBankBranch();
    Date paymentDate = actionForm.getPaymentDate();
    String instrumentNumber = actionForm.getInstrumentNo();
    Date instrumentDate = actionForm.getInstrumentDate();
    String modeOfDelivery = actionForm.getModeOfDelivery();
    double instrumentAmount = actionForm.getInstrumentAmount();
    String drawnAtBank = actionForm.getDrawnAtBank();
    String drawnAtBranch = actionForm.getDrawnAtBranch();
    String payableAt = actionForm.getPayableAt();
    String accNumber = actionForm.getAccountNumber();
    Log.log(5, "RPAction", methodName, 
        "collecting bank " + actionForm.getCollectingBankName());
    paymentDetails.setCollectingBank(actionForm.getCollectingBankName());
    Log.log(5, "RPAction", methodName, "mode of payment " + modeOfPayment);
    paymentDetails.setModeOfPayment(modeOfPayment);
    Log.log(5, "RPAction", methodName, "collecting branch " + 
        collectingBranch);
    paymentDetails.setCollectingBankBranch(collectingBranch);
    Log.log(5, "RPAction", methodName, "payment date " + paymentDate);
    paymentDetails.setPaymentDate(paymentDate);
    Log.log(5, "RPAction", methodName, "instrument number " + 
        instrumentNumber);
    paymentDetails.setInstrumentNo(instrumentNumber);
    Log.log(5, "RPAction", methodName, "instrument date " + instrumentDate);
    paymentDetails.setInstrumentDate(instrumentDate);
    Log.log(5, "RPAction", methodName, "mode of delivery " + modeOfDelivery);
    paymentDetails.setModeOfDelivery(modeOfDelivery);
    Log.log(5, "RPAction", methodName, "instrument amount " + 
        instrumentAmount);
    paymentDetails.setInstrumentAmount(instrumentAmount);
    Log.log(5, "RPAction", methodName, "drawn at bank " + drawnAtBank);
    paymentDetails.setDrawnAtBank(drawnAtBank);
    Log.log(5, "RPAction", methodName, "drawn at branch " + drawnAtBranch);
    paymentDetails.setDrawnAtBranch(drawnAtBranch);
    Log.log(5, "RPAction", methodName, "payable at " + payableAt);
    paymentDetails.setPayableAt(payableAt);
    Log.log(5, "RPAction", methodName, "acc num " + accNumber);
    paymentDetails.setCgtsiAccNumber(accNumber);
    Map allocationFlags = actionForm.getAllocatedFlags();
    ArrayList<DANSummary> danSummaries = actionForm.getDanSummaries();
    Map cgpans = actionForm.getCgpans();
    Set cgpansSet = cgpans.keySet();
    Map<String, ArrayList<AllocationDetail>> danCgpanDetails = actionForm.getDanPanDetails();
    Map notAllocatedReasons = actionForm.getNotAllocatedReasons();
    for (int i = 0; i < danSummaries.size(); i++) {
      DANSummary danSummary = danSummaries.get(i);
      String danId = danSummary.getDanId();
      Log.log(5, "RPAction", methodName, "danId " + danId);
      String shiftDanId = danId.replace('.', '_');
      Log.log(5, "RPAction", methodName, 
          "contains " + danCgpanDetails.containsKey(danId));
      if (danCgpanDetails.containsKey(danId)) {
        ArrayList<AllocationDetail> panAllocationDetails = (ArrayList)danCgpanDetails
          .get(danId);
        if (panAllocationDetails == null) {
          Log.log(5, "RPAction", methodName, 
              "CGPAN details are not available. get them.");
          ArrayList<ArrayList> totalList = rpProcessor.displayCGPANs(danId);
          panAllocationDetails = totalList.get(0);
        } 
        for (int j = 0; j < panAllocationDetails.size(); j++) {
          AllocationDetail allocationDetail = panAllocationDetails
            .get(j);
          Log.log(5, 
              "RPActionForm", 
              "validate", 
              " cgpan from danpandetails " + 
              allocationDetail.getCgpan());
          if (allocationDetail.getAllocatedFlag().equals("N")) {
            Log.log(5, "RPActionForm", "validate", 
                " not allocated ");
            String reasons = (String)notAllocatedReasons
              .get(String.valueOf(shiftDanId) + "-" + 
                allocationDetail.getCgpan());
            Log.log(5, "RPActionForm", "validate", 
                " reason for not allocated " + reasons);
            allocationDetail.setNotAllocatedReason(reasons);
          } else {
            allocationDetail.setNotAllocatedReason("");
          } 
          panAllocationDetails.set(j, allocationDetail);
        } 
        danCgpanDetails.put(danId, panAllocationDetails);
      } 
    } 
    paymentId = rpProcessor.allocateASFDAN(paymentDetails, danSummaries, 
        allocationFlags, cgpans, danCgpanDetails, user);
    request.setAttribute("message", 
        "Payment Allocated Successfully.<BR>Payment ID : " + paymentId);
    Log.log(5, "RPAction", methodName, "Exited");
    return mapping.findForward("success");
  }
  
  public ActionForward expiredasfallocatePayments(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    RpProcessor rpProcessor = new RpProcessor();
    String paymentId = "";
    String methodName = "expiredasfallocatePayments";
    Log.log(5, "RPAction", methodName, "Entered");
    User user = getUserInformation(request);
    PaymentDetails paymentDetails = new PaymentDetails();
    String modeOfPayment = actionForm.getModeOfPayment();
    String collectingBranch = actionForm.getCollectingBankBranch();
    Date paymentDate = actionForm.getPaymentDate();
    String instrumentNumber = actionForm.getInstrumentNo();
    Date instrumentDate = actionForm.getInstrumentDate();
    String modeOfDelivery = actionForm.getModeOfDelivery();
    double instrumentAmount = actionForm.getInstrumentAmount();
    String drawnAtBank = actionForm.getDrawnAtBank();
    String drawnAtBranch = actionForm.getDrawnAtBranch();
    String payableAt = actionForm.getPayableAt();
    String accNumber = actionForm.getAccountNumber();
    Log.log(5, "RPAction", methodName, 
        "collecting bank " + actionForm.getCollectingBankName());
    paymentDetails.setCollectingBank(actionForm.getCollectingBankName());
    Log.log(5, "RPAction", methodName, "mode of payment " + modeOfPayment);
    paymentDetails.setModeOfPayment(modeOfPayment);
    Log.log(5, "RPAction", methodName, "collecting branch " + 
        collectingBranch);
    paymentDetails.setCollectingBankBranch(collectingBranch);
    Log.log(5, "RPAction", methodName, "payment date " + paymentDate);
    paymentDetails.setPaymentDate(paymentDate);
    Log.log(5, "RPAction", methodName, "instrument number " + 
        instrumentNumber);
    paymentDetails.setInstrumentNo(instrumentNumber);
    Log.log(5, "RPAction", methodName, "instrument date " + instrumentDate);
    paymentDetails.setInstrumentDate(instrumentDate);
    Log.log(5, "RPAction", methodName, "mode of delivery " + modeOfDelivery);
    paymentDetails.setModeOfDelivery(modeOfDelivery);
    Log.log(5, "RPAction", methodName, "instrument amount " + 
        instrumentAmount);
    paymentDetails.setInstrumentAmount(instrumentAmount);
    Log.log(5, "RPAction", methodName, "drawn at bank " + drawnAtBank);
    paymentDetails.setDrawnAtBank(drawnAtBank);
    Log.log(5, "RPAction", methodName, "drawn at branch " + drawnAtBranch);
    paymentDetails.setDrawnAtBranch(drawnAtBranch);
    Log.log(5, "RPAction", methodName, "payable at " + payableAt);
    paymentDetails.setPayableAt(payableAt);
    Log.log(5, "RPAction", methodName, "acc num " + accNumber);
    paymentDetails.setCgtsiAccNumber(accNumber);
    Map allocationFlags = actionForm.getAllocatedFlags();
    ArrayList<DANSummary> danSummaries = actionForm.getDanSummaries();
    Map cgpans = actionForm.getCgpans();
    Set cgpansSet = cgpans.keySet();
    Map<String, ArrayList<AllocationDetail>> danCgpanDetails = actionForm.getDanPanDetails();
    Map notAllocatedReasons = actionForm.getNotAllocatedReasons();
    for (int i = 0; i < danSummaries.size(); i++) {
      DANSummary danSummary = danSummaries.get(i);
      String danId = danSummary.getDanId();
      Log.log(5, "RPAction", methodName, "danId " + danId);
      String shiftDanId = danId.replace('.', '_');
      Log.log(5, "RPAction", methodName, 
          "contains " + danCgpanDetails.containsKey(danId));
      if (danCgpanDetails.containsKey(danId)) {
        ArrayList<AllocationDetail> panAllocationDetails = (ArrayList)danCgpanDetails
          .get(danId);
        if (panAllocationDetails == null) {
          Log.log(5, "RPAction", methodName, 
              "CGPAN details are not available. get them.");
          ArrayList<ArrayList> totalList = rpProcessor.displayCGPANs(danId);
          panAllocationDetails = totalList.get(0);
        } 
        for (int j = 0; j < panAllocationDetails.size(); j++) {
          AllocationDetail allocationDetail = panAllocationDetails
            .get(j);
          Log.log(5, 
              "RPActionForm", 
              "validate", 
              " cgpan from danpandetails " + 
              allocationDetail.getCgpan());
          if (allocationDetail.getAllocatedFlag().equals("N")) {
            Log.log(5, "RPActionForm", "validate", 
                " not allocated ");
            String reasons = (String)notAllocatedReasons
              .get(String.valueOf(shiftDanId) + "-" + 
                allocationDetail.getCgpan());
            Log.log(5, "RPActionForm", "validate", 
                " reason for not allocated " + reasons);
            allocationDetail.setNotAllocatedReason(reasons);
          } else {
            allocationDetail.setNotAllocatedReason("");
          } 
          panAllocationDetails.set(j, allocationDetail);
        } 
        danCgpanDetails.put(danId, panAllocationDetails);
      } 
    } 
    paymentId = rpProcessor.allocateASFDAN(paymentDetails, danSummaries, 
        allocationFlags, cgpans, danCgpanDetails, user);
    request.setAttribute("message", 
        "Payment Allocated Successfully.<BR>Payment ID : " + paymentId);
    Log.log(5, "RPAction", methodName, "Exited");
    return mapping.findForward("success");
  }
  
  public ActionForward gfallocatePayments(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    RpProcessor rpProcessor = new RpProcessor();
    String paymentId = "";
    String methodName = "gfallocatePayments";
    Log.log(5, "RPAction", methodName, "Entered");
    User user = getUserInformation(request);
    PaymentDetails paymentDetails = new PaymentDetails();
    String allocationType = actionForm.getAllocationType();
    paymentDetails.setAllocationType1(allocationType);
    String modeOfPayment = actionForm.getModeOfPayment();
    String collectingBranch = actionForm.getCollectingBankBranch();
    Date paymentDate = actionForm.getPaymentDate();
    String instrumentNumber = actionForm.getInstrumentNo();
    Date instrumentDate = actionForm.getInstrumentDate();
    String modeOfDelivery = actionForm.getModeOfDelivery();
    double instrumentAmount = actionForm.getInstrumentAmount();
    String drawnAtBank = actionForm.getDrawnAtBank();
    String drawnAtBranch = actionForm.getDrawnAtBranch();
    String payableAt = actionForm.getPayableAt();
    String accNumber = actionForm.getAccountNumber();
    Log.log(5, "RPAction", methodName, 
        "collecting bank " + actionForm.getCollectingBankName());
    paymentDetails.setCollectingBank(actionForm.getCollectingBankName());
    Log.log(5, "RPAction", methodName, "mode of payment " + modeOfPayment);
    paymentDetails.setModeOfPayment(modeOfPayment);
    Log.log(5, "RPAction", methodName, "collecting branch " + 
        collectingBranch);
    paymentDetails.setCollectingBankBranch(collectingBranch);
    Log.log(5, "RPAction", methodName, "payment date " + paymentDate);
    paymentDetails.setPaymentDate(paymentDate);
    Log.log(5, "RPAction", methodName, "instrument number " + 
        instrumentNumber);
    paymentDetails.setInstrumentNo(instrumentNumber);
    Log.log(5, "RPAction", methodName, "instrument date " + instrumentDate);
    paymentDetails.setInstrumentDate(instrumentDate);
    Log.log(5, "RPAction", methodName, "mode of delivery " + modeOfDelivery);
    paymentDetails.setModeOfDelivery(modeOfDelivery);
    Log.log(5, "RPAction", methodName, "instrument amount " + 
        instrumentAmount);
    paymentDetails.setInstrumentAmount(instrumentAmount);
    Log.log(5, "RPAction", methodName, "drawn at bank " + drawnAtBank);
    paymentDetails.setDrawnAtBank(drawnAtBank);
    Log.log(5, "RPAction", methodName, "drawn at branch " + drawnAtBranch);
    paymentDetails.setDrawnAtBranch(drawnAtBranch);
    Log.log(5, "RPAction", methodName, "payable at " + payableAt);
    paymentDetails.setPayableAt(payableAt);
    Log.log(5, "RPAction", methodName, "acc num " + accNumber);
    paymentDetails.setCgtsiAccNumber(accNumber);
    Map appropriatedFlags = actionForm.getAppropriatedFlags();
    Map cgpans = actionForm.getCgpans();
    Set cgpansSet = cgpans.keySet();
    Map danCgpanDetails = actionForm.getDanPanDetails();
    paymentId = rpProcessor.allocateCGDAN(paymentDetails, 
        appropriatedFlags, cgpans, danCgpanDetails, user);
    HttpSession session = request.getSession(false);
    request.setAttribute("message", 
        "Payment Allocated Successfully.<BR>Payment ID : " + paymentId);
    Log.log(5, "RPAction", methodName, "Exited");
    return mapping.findForward("success");
  }
  
  public ActionForward gfallocatePaymentsold(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    RpProcessor rpProcessor = new RpProcessor();
    String paymentId = "";
    String methodName = "gfallocatePayments";
    Log.log(5, "RPAction", methodName, "Entered");
    User user = getUserInformation(request);
    PaymentDetails paymentDetails = new PaymentDetails();
    String allocationType = actionForm.getAllocationType();
    paymentDetails.setAllocationType1(allocationType);
    String modeOfPayment = actionForm.getModeOfPayment();
    String collectingBranch = actionForm.getCollectingBankBranch();
    Date paymentDate = actionForm.getPaymentDate();
    String instrumentNumber = actionForm.getInstrumentNo();
    Date instrumentDate = actionForm.getInstrumentDate();
    String modeOfDelivery = actionForm.getModeOfDelivery();
    double instrumentAmount = actionForm.getInstrumentAmount();
    String drawnAtBank = actionForm.getDrawnAtBank();
    String drawnAtBranch = actionForm.getDrawnAtBranch();
    String payableAt = actionForm.getPayableAt();
    String accNumber = actionForm.getAccountNumber();
    Log.log(5, "RPAction", methodName, 
        "collecting bank " + actionForm.getCollectingBankName());
    paymentDetails.setCollectingBank(actionForm.getCollectingBankName());
    Log.log(5, "RPAction", methodName, "mode of payment " + modeOfPayment);
    paymentDetails.setModeOfPayment(modeOfPayment);
    Log.log(5, "RPAction", methodName, "collecting branch " + 
        collectingBranch);
    paymentDetails.setCollectingBankBranch(collectingBranch);
    Log.log(5, "RPAction", methodName, "payment date " + paymentDate);
    paymentDetails.setPaymentDate(paymentDate);
    Log.log(5, "RPAction", methodName, "instrument number " + 
        instrumentNumber);
    paymentDetails.setInstrumentNo(instrumentNumber);
    Log.log(5, "RPAction", methodName, "instrument date " + instrumentDate);
    paymentDetails.setInstrumentDate(instrumentDate);
    Log.log(5, "RPAction", methodName, "mode of delivery " + modeOfDelivery);
    paymentDetails.setModeOfDelivery(modeOfDelivery);
    Log.log(5, "RPAction", methodName, "instrument amount " + 
        instrumentAmount);
    paymentDetails.setInstrumentAmount(instrumentAmount);
    Log.log(5, "RPAction", methodName, "drawn at bank " + drawnAtBank);
    paymentDetails.setDrawnAtBank(drawnAtBank);
    Log.log(5, "RPAction", methodName, "drawn at branch " + drawnAtBranch);
    paymentDetails.setDrawnAtBranch(drawnAtBranch);
    Log.log(5, "RPAction", methodName, "payable at " + payableAt);
    paymentDetails.setPayableAt(payableAt);
    Log.log(5, "RPAction", methodName, "acc num " + accNumber);
    paymentDetails.setCgtsiAccNumber(accNumber);
    Map allocationFlags = actionForm.getAllocatedFlags();
    ArrayList<DANSummary> danSummaries = actionForm.getDanSummaries();
    Map cgpans = actionForm.getCgpans();
    Set cgpansSet = cgpans.keySet();
    Map<String, ArrayList<AllocationDetail>> danCgpanDetails = actionForm.getDanPanDetails();
    Map notAllocatedReasons = actionForm.getNotAllocatedReasons();
    for (int i = 0; i < danSummaries.size(); i++) {
      DANSummary danSummary = danSummaries.get(i);
      String danId = danSummary.getDanId();
      Log.log(5, "RPAction", methodName, "danId " + danId);
      String shiftDanId = danId.replace('.', '_');
      Log.log(5, "RPAction", methodName, 
          "contains " + danCgpanDetails.containsKey(danId));
      if (danCgpanDetails.containsKey(danId)) {
        ArrayList<AllocationDetail> panAllocationDetails = (ArrayList)danCgpanDetails
          .get(danId);
        if (panAllocationDetails == null) {
          Log.log(5, "RPAction", methodName, 
              "CGPAN details are not available. get them.");
          ArrayList<ArrayList> totalList = rpProcessor.displayCGPANs(danId);
          panAllocationDetails = totalList.get(0);
        } 
        for (int j = 0; j < panAllocationDetails.size(); j++) {
          AllocationDetail allocationDetail = panAllocationDetails
            .get(j);
          Log.log(5, 
              "RPActionForm", 
              "validate", 
              " cgpan from danpandetails " + 
              allocationDetail.getCgpan());
          if (allocationDetail.getAllocatedFlag().equals("N")) {
            Log.log(5, "RPActionForm", "validate", 
                " not allocated ");
            String reasons = (String)notAllocatedReasons
              .get(String.valueOf(shiftDanId) + "-" + 
                allocationDetail.getCgpan());
            Log.log(5, "RPActionForm", "validate", 
                " reason for not allocated " + reasons);
            allocationDetail.setNotAllocatedReason(reasons);
          } else {
            allocationDetail.setNotAllocatedReason("");
          } 
          panAllocationDetails.set(j, allocationDetail);
        } 
        danCgpanDetails.put(danId, panAllocationDetails);
      } 
    } 
    request.setAttribute("message", 
        "Payment Allocated Successfully.<BR>Payment ID : " + paymentId);
    Log.log(5, "RPAction", methodName, "Exited");
    return mapping.findForward("success");
  }
  
  public ActionForward gfallocatePaymentsNew(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    RpProcessor rpProcessor = new RpProcessor();
    String paymentId = "";
    String methodName = "gfallocatePaymentsNew";
    Log.log(5, "RPAction", methodName, "Entered");
    User user = getUserInformation(request);
    PaymentDetails paymentDetails = new PaymentDetails();
    String allocationType = actionForm.getAllocationType();
    paymentDetails.setAllocationType1(allocationType);
    String modeOfPayment = actionForm.getModeOfPayment();
    String collectingBank = actionForm.getCollectingBank();
    String collectingBranch = actionForm.getCollectingBankBranch();
    String accountName = actionForm.getAccountName();
    String accNumber = actionForm.getAccountNumber();
    String ifscCode = actionForm.getIfscCode();
    Date paymentDate = actionForm.getPaymentDate();
    double allocatedAmt = actionForm.getInstrumentAmount();
    Date instrumentDate = actionForm.getPaymentDate();
    String instrumentNumber = actionForm.getInstrumentNo();
    String modeOfDelivery = actionForm.getModeOfDelivery();
    String drawnAtBank = actionForm.getDrawnAtBank();
    String drawnAtBranch = actionForm.getDrawnAtBranch();
    String payableAt = actionForm.getPayableAt();
    Log.log(5, "RPAction", methodName, 
        "collecting bank " + actionForm.getCollectingBank());
    paymentDetails.setCollectingBank(actionForm.getCollectingBank());
    Log.log(5, "RPAction", methodName, "mode of payment " + modeOfPayment);
    paymentDetails.setModeOfPayment(modeOfPayment);
    Log.log(5, "RPAction", methodName, "collecting branch " + 
        collectingBranch);
    paymentDetails.setCollectingBankBranch(collectingBranch);
    Log.log(5, "RPAction", methodName, "payment date " + paymentDate);
    paymentDetails.setPaymentDate(paymentDate);
    Log.log(5, "RPAction", methodName, "instrument number " + 
        instrumentNumber);
    paymentDetails.setInstrumentNo(instrumentNumber);
    Log.log(5, "RPAction", methodName, "instrument date " + instrumentDate);
    paymentDetails.setInstrumentDate(instrumentDate);
    Log.log(5, "RPAction", methodName, "mode of delivery " + modeOfDelivery);
    paymentDetails.setModeOfDelivery(modeOfDelivery);
    Log.log(5, "RPAction", methodName, "instrument amount " + allocatedAmt);
    paymentDetails.setInstrumentAmount(allocatedAmt);
    Log.log(5, "RPAction", methodName, "drawn at bank " + drawnAtBank);
    paymentDetails.setDrawnAtBank(drawnAtBank);
    Log.log(5, "RPAction", methodName, "drawn at branch " + drawnAtBranch);
    paymentDetails.setDrawnAtBranch(drawnAtBranch);
    Log.log(5, "RPAction", methodName, "payable at " + payableAt);
    paymentDetails.setPayableAt(payableAt);
    Log.log(5, "RPAction", methodName, "acc num " + accNumber);
    paymentDetails.setCgtsiAccNumber(accNumber);
    Map appropriatedFlags = actionForm.getAppropriatedFlags();
    Map cgpans = actionForm.getCgpans();
    Set cgpansSet = cgpans.keySet();
    Map<String, ArrayList<AllocationDetail>> danCgpanDetails = actionForm.getDanPanDetails();
    Map notAllocatedReasons = actionForm.getNotAllocatedReasons();
    ArrayList<DANSummary> danSummaries = actionForm.getDanSummaries();
    for (int i = 0; i < danSummaries.size(); i++) {
      DANSummary danSummary = danSummaries.get(i);
      String danId = danSummary.getDanId();
      Log.log(5, "RPAction", methodName, "danId " + danId);
      String shiftDanId = danId.replace('.', '_');
      Log.log(5, "RPAction", methodName, 
          "contains " + danCgpanDetails.containsKey(danId));
      if (danCgpanDetails.containsKey(danId)) {
        ArrayList<AllocationDetail> panAllocationDetails = (ArrayList)danCgpanDetails
          .get(danId);
        if (panAllocationDetails == null) {
          Log.log(5, "RPAction", methodName, 
              "CGPAN details are not available. get them.");
          ArrayList<ArrayList> totalList = rpProcessor.displayCGPANs(danId);
          panAllocationDetails = totalList.get(0);
        } 
        for (int j = 0; j < panAllocationDetails.size(); j++) {
          AllocationDetail allocationDetail = panAllocationDetails
            .get(j);
          Log.log(5, 
              "RPActionForm", 
              "validate", 
              " cgpan from danpandetails " + 
              allocationDetail.getCgpan());
          if (allocationDetail.getAllocatedFlag().equals("N")) {
            Log.log(5, "RPActionForm", "validate", 
                " not allocated ");
            String reasons = (String)notAllocatedReasons
              .get(String.valueOf(shiftDanId) + "-" + 
                allocationDetail.getCgpan());
            Log.log(5, "RPActionForm", "validate", 
                " reason for not allocated " + reasons);
            allocationDetail.setNotAllocatedReason(reasons);
          } else {
            allocationDetail.setNotAllocatedReason("");
          } 
          panAllocationDetails.set(j, allocationDetail);
        } 
        danCgpanDetails.put(danId, panAllocationDetails);
      } 
    } 
    paymentId = rpProcessor.allocateNEFTCGDAN(paymentDetails, 
        appropriatedFlags, cgpans, danCgpanDetails, user);
    request.setAttribute("message", 
        "Payment Allocated Successfully.<BR>Payment ID : " + paymentId + 
        " of Rs." + allocatedAmt);
    Log.log(5, "RPAction", methodName, "Exited");
    return mapping.findForward("success");
  }
  
  public ActionForward gfallocatePaymentsNewOld(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    RpProcessor rpProcessor = new RpProcessor();
    String paymentId = "";
    String methodName = "gfallocatePaymentsNew";
    Log.log(5, "RPAction", methodName, "Entered");
    User user = getUserInformation(request);
    PaymentDetails paymentDetails = new PaymentDetails();
    String allocationType = actionForm.getAllocationType();
    paymentDetails.setAllocationType1(allocationType);
    String modeOfPayment = actionForm.getModeOfPayment();
    String collectingBank = actionForm.getCollectingBank();
    String collectingBranch = actionForm.getCollectingBankBranch();
    String accountName = actionForm.getAccountName();
    String accNumber = actionForm.getAccountNumber();
    String ifscCode = actionForm.getIfscCode();
    Date paymentDate = actionForm.getPaymentDate();
    double allocatedAmt = actionForm.getInstrumentAmount();
    Date instrumentDate = actionForm.getPaymentDate();
    String instrumentNumber = actionForm.getInstrumentNo();
    String modeOfDelivery = actionForm.getModeOfDelivery();
    String drawnAtBank = actionForm.getDrawnAtBank();
    String drawnAtBranch = actionForm.getDrawnAtBranch();
    String payableAt = actionForm.getPayableAt();
    Log.log(5, "RPAction", methodName, 
        "collecting bank " + actionForm.getCollectingBank());
    paymentDetails.setCollectingBank(actionForm.getCollectingBank());
    Log.log(5, "RPAction", methodName, "mode of payment " + modeOfPayment);
    paymentDetails.setModeOfPayment(modeOfPayment);
    Log.log(5, "RPAction", methodName, "collecting branch " + 
        collectingBranch);
    paymentDetails.setCollectingBankBranch(collectingBranch);
    Log.log(5, "RPAction", methodName, "payment date " + paymentDate);
    paymentDetails.setPaymentDate(paymentDate);
    Log.log(5, "RPAction", methodName, "instrument number " + 
        instrumentNumber);
    paymentDetails.setInstrumentNo(instrumentNumber);
    Log.log(5, "RPAction", methodName, "instrument date " + instrumentDate);
    paymentDetails.setInstrumentDate(instrumentDate);
    Log.log(5, "RPAction", methodName, "mode of delivery " + modeOfDelivery);
    paymentDetails.setModeOfDelivery(modeOfDelivery);
    Log.log(5, "RPAction", methodName, "instrument amount " + allocatedAmt);
    paymentDetails.setInstrumentAmount(allocatedAmt);
    Log.log(5, "RPAction", methodName, "drawn at bank " + drawnAtBank);
    paymentDetails.setDrawnAtBank(drawnAtBank);
    Log.log(5, "RPAction", methodName, "drawn at branch " + drawnAtBranch);
    paymentDetails.setDrawnAtBranch(drawnAtBranch);
    Log.log(5, "RPAction", methodName, "payable at " + payableAt);
    paymentDetails.setPayableAt(payableAt);
    Log.log(5, "RPAction", methodName, "acc num " + accNumber);
    paymentDetails.setCgtsiAccNumber(accNumber);
    Map allocationFlags = actionForm.getAllocatedFlags();
    ArrayList<DANSummary> danSummaries = actionForm.getDanSummaries();
    Map cgpans = actionForm.getCgpans();
    Set cgpansSet = cgpans.keySet();
    Map<String, ArrayList<AllocationDetail>> danCgpanDetails = actionForm.getDanPanDetails();
    Map notAllocatedReasons = actionForm.getNotAllocatedReasons();
    for (int i = 0; i < danSummaries.size(); i++) {
      DANSummary danSummary = danSummaries.get(i);
      String danId = danSummary.getDanId();
      Log.log(5, "RPAction", methodName, "danId " + danId);
      String shiftDanId = danId.replace('.', '_');
      Log.log(5, "RPAction", methodName, 
          "contains " + danCgpanDetails.containsKey(danId));
      if (danCgpanDetails.containsKey(danId)) {
        ArrayList<AllocationDetail> panAllocationDetails = (ArrayList)danCgpanDetails
          .get(danId);
        if (panAllocationDetails == null) {
          Log.log(5, "RPAction", methodName, 
              "CGPAN details are not available. get them.");
          ArrayList<ArrayList> totalList = rpProcessor.displayCGPANs(danId);
          panAllocationDetails = totalList.get(0);
        } 
        for (int j = 0; j < panAllocationDetails.size(); j++) {
          AllocationDetail allocationDetail = panAllocationDetails
            .get(j);
          Log.log(5, 
              "RPActionForm", 
              "validate", 
              " cgpan from danpandetails " + 
              allocationDetail.getCgpan());
          if (allocationDetail.getAllocatedFlag().equals("N")) {
            Log.log(5, "RPActionForm", "validate", 
                " not allocated ");
            String reasons = (String)notAllocatedReasons
              .get(String.valueOf(shiftDanId) + "-" + 
                allocationDetail.getCgpan());
            Log.log(5, "RPActionForm", "validate", 
                " reason for not allocated " + reasons);
            allocationDetail.setNotAllocatedReason(reasons);
          } else {
            allocationDetail.setNotAllocatedReason("");
          } 
          panAllocationDetails.set(j, allocationDetail);
        } 
        danCgpanDetails.put(danId, panAllocationDetails);
      } 
    } 
    Map appropriatedFlags = actionForm.getAppropriatedFlags();
    request.setAttribute("message", 
        "Payment Allocated Successfully.<BR>Payment ID : " + paymentId + 
        " of Rs." + allocatedAmt);
    Log.log(5, "RPAction", methodName, "Exited");
    return mapping.findForward("success");
  }
  
  public ActionForward allocatePayments(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    RpProcessor rpProcessor = new RpProcessor();
    String paymentId = "";
    String methodName = "allocatePayments";
    Log.log(5, "RPAction", methodName, "Entered");
    User user = getUserInformation(request);
    PaymentDetails paymentDetails = new PaymentDetails();
    String allocationType3 = actionForm.getAllocationType();
    paymentDetails.setAllocationType1(allocationType3);
    String modeOfPayment = actionForm.getModeOfPayment();
    String collectingBranch = actionForm.getCollectingBankBranch();
    Date paymentDate = actionForm.getPaymentDate();
    String instrumentNumber = actionForm.getInstrumentNo();
    Date instrumentDate = actionForm.getInstrumentDate();
    String modeOfDelivery = actionForm.getModeOfDelivery();
    double instrumentAmount = actionForm.getInstrumentAmount();
    String drawnAtBank = actionForm.getDrawnAtBank();
    String drawnAtBranch = actionForm.getDrawnAtBranch();
    String payableAt = actionForm.getPayableAt();
    String accNumber = actionForm.getAccountNumber();
    Log.log(5, "RPAction", methodName, 
        "collecting bank " + actionForm.getCollectingBankName());
    paymentDetails.setCollectingBank(actionForm.getCollectingBankName());
    Log.log(5, "RPAction", methodName, "mode of payment " + modeOfPayment);
    paymentDetails.setModeOfPayment(modeOfPayment);
    Log.log(5, "RPAction", methodName, "collecting branch " + 
        collectingBranch);
    paymentDetails.setCollectingBankBranch(collectingBranch);
    Log.log(5, "RPAction", methodName, "payment date " + paymentDate);
    paymentDetails.setPaymentDate(paymentDate);
    Log.log(5, "RPAction", methodName, "instrument number " + 
        instrumentNumber);
    paymentDetails.setInstrumentNo(instrumentNumber);
    Log.log(5, "RPAction", methodName, "instrument date " + instrumentDate);
    paymentDetails.setInstrumentDate(instrumentDate);
    Log.log(5, "RPAction", methodName, "mode of delivery " + modeOfDelivery);
    paymentDetails.setModeOfDelivery(modeOfDelivery);
    Log.log(5, "RPAction", methodName, "instrument amount " + 
        instrumentAmount);
    paymentDetails.setInstrumentAmount(instrumentAmount);
    Log.log(5, "RPAction", methodName, "drawn at bank " + drawnAtBank);
    paymentDetails.setDrawnAtBank(drawnAtBank);
    Log.log(5, "RPAction", methodName, "drawn at branch " + drawnAtBranch);
    paymentDetails.setDrawnAtBranch(drawnAtBranch);
    Log.log(5, "RPAction", methodName, "payable at " + payableAt);
    paymentDetails.setPayableAt(payableAt);
    Log.log(5, "RPAction", methodName, "acc num " + accNumber);
    paymentDetails.setCgtsiAccNumber(accNumber);
    Map allocationFlags = actionForm.getAllocatedFlags();
    ArrayList<DANSummary> danSummaries = actionForm.getDanSummaries();
    Map cgpans = actionForm.getCgpans();
    Set cgpansSet = cgpans.keySet();
    Map<String, ArrayList<AllocationDetail>> danCgpanDetails = actionForm.getDanPanDetails();
    Map notAllocatedReasons = actionForm.getNotAllocatedReasons();
    for (int i = 0; i < danSummaries.size(); i++) {
      DANSummary danSummary = danSummaries.get(i);
      String danId = danSummary.getDanId();
      Log.log(5, "RPAction", methodName, "danId " + danId);
      String shiftDanId = danId.replace('.', '_');
      Log.log(5, "RPAction", methodName, 
          "contains " + danCgpanDetails.containsKey(danId));
      if (danCgpanDetails.containsKey(danId)) {
        ArrayList<AllocationDetail> panAllocationDetails = (ArrayList)danCgpanDetails
          .get(danId);
        if (panAllocationDetails == null) {
          Log.log(5, "RPAction", methodName, 
              "CGPAN details are not available. get them.");
          ArrayList<ArrayList> totalList = rpProcessor.displayCGPANs(danId);
          panAllocationDetails = totalList.get(0);
        } 
        for (int j = 0; j < panAllocationDetails.size(); j++) {
          AllocationDetail allocationDetail = panAllocationDetails
            .get(j);
          Log.log(5, 
              "RPActionForm", 
              "validate", 
              " cgpan from danpandetails " + 
              allocationDetail.getCgpan());
          if (allocationDetail.getAllocatedFlag().equals("N")) {
            Log.log(5, "RPActionForm", "validate", 
                " not allocated ");
            String reasons = (String)notAllocatedReasons
              .get(String.valueOf(shiftDanId) + "-" + 
                allocationDetail.getCgpan());
            Log.log(5, "RPActionForm", "validate", 
                " reason for not allocated " + reasons);
            allocationDetail.setNotAllocatedReason(reasons);
          } else {
            allocationDetail.setNotAllocatedReason("");
          } 
          panAllocationDetails.set(j, allocationDetail);
        } 
        danCgpanDetails.put(danId, panAllocationDetails);
      } 
    } 
    paymentId = rpProcessor.allocateDAN(paymentDetails, danSummaries, 
        allocationFlags, cgpans, danCgpanDetails, user);
    request.setAttribute("message", 
        "Payment Allocated Successfully.<BR>Payment ID : " + paymentId);
    Log.log(5, "RPAction", methodName, "Exited");
    return mapping.findForward("success");
  }
  
  public ActionForward appropriateallocatePayments(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    RpProcessor rpProcessor = new RpProcessor();
    String paymentId = "";
    String methodName = "appropriateallocatePayments";
    Log.log(5, "RPAction", methodName, "Entered");
    User user = getUserInformation(request);
    PaymentDetails paymentDetails = new PaymentDetails();
    String modeOfPayment = actionForm.getModeOfPayment();
    String collectingBranch = actionForm.getCollectingBankBranch();
    Date paymentDate = actionForm.getPaymentDate();
    String instrumentNumber = actionForm.getInstrumentNo();
    Date instrumentDate = actionForm.getInstrumentDate();
    String modeOfDelivery = actionForm.getModeOfDelivery();
    double instrumentAmount = actionForm.getInstrumentAmount();
    String drawnAtBank = actionForm.getDrawnAtBank();
    String drawnAtBranch = actionForm.getDrawnAtBranch();
    String payableAt = actionForm.getPayableAt();
    String accNumber = actionForm.getAccountNumber();
    double realisationAmount = actionForm.getReceivedAmount();
    Date realisationDate = actionForm.getDateOfRealisation();
    String remarksforAppropriation = actionForm
      .getremarksforAppropriation();
    paymentDetails.setReceivedAmount(realisationAmount);
    paymentDetails.setRealisationDate(realisationDate);
    paymentDetails.setRemarksforAppropriation(remarksforAppropriation);
    Log.log(5, "RPAction", methodName, 
        "collecting bank " + actionForm.getCollectingBankName());
    paymentDetails.setCollectingBank(actionForm.getCollectingBankName());
    Log.log(5, "RPAction", methodName, "mode of payment " + modeOfPayment);
    paymentDetails.setModeOfPayment(modeOfPayment);
    Log.log(5, "RPAction", methodName, "collecting branch " + 
        collectingBranch);
    paymentDetails.setCollectingBankBranch(collectingBranch);
    Log.log(5, "RPAction", methodName, "payment date " + paymentDate);
    paymentDetails.setPaymentDate(paymentDate);
    Log.log(5, "RPAction", methodName, "instrument number " + 
        instrumentNumber);
    paymentDetails.setInstrumentNo(instrumentNumber);
    Log.log(5, "RPAction", methodName, "instrument date " + instrumentDate);
    paymentDetails.setInstrumentDate(instrumentDate);
    Log.log(5, "RPAction", methodName, "mode of delivery " + modeOfDelivery);
    paymentDetails.setModeOfDelivery(modeOfDelivery);
    Log.log(5, "RPAction", methodName, "instrument amount " + 
        instrumentAmount);
    paymentDetails.setInstrumentAmount(instrumentAmount);
    Log.log(5, "RPAction", methodName, "drawn at bank " + drawnAtBank);
    paymentDetails.setDrawnAtBank(drawnAtBank);
    Log.log(5, "RPAction", methodName, "drawn at branch " + drawnAtBranch);
    paymentDetails.setDrawnAtBranch(drawnAtBranch);
    Log.log(5, "RPAction", methodName, "payable at " + payableAt);
    paymentDetails.setPayableAt(payableAt);
    Log.log(5, "RPAction", methodName, "acc num " + accNumber);
    paymentDetails.setCgtsiAccNumber(accNumber);
    Map allocationFlags = actionForm.getAllocatedFlags();
    ArrayList<DANSummary> danSummaries = actionForm.getDanSummaries();
    Map cgpans = actionForm.getCgpans();
    Set cgpansSet = cgpans.keySet();
    Map<String, ArrayList<AllocationDetail>> danCgpanDetails = actionForm.getDanPanDetails();
    Map notAllocatedReasons = actionForm.getNotAllocatedReasons();
    for (int i = 0; i < danSummaries.size(); i++) {
      DANSummary danSummary = danSummaries.get(i);
      String danId = danSummary.getDanId();
      Log.log(5, "RPAction", methodName, "danId " + danId);
      String shiftDanId = danId.replace('.', '_');
      Log.log(5, "RPAction", methodName, 
          "contains " + danCgpanDetails.containsKey(danId));
      if (danCgpanDetails.containsKey(danId)) {
        ArrayList<AllocationDetail> panAllocationDetails = (ArrayList)danCgpanDetails
          .get(danId);
        if (panAllocationDetails == null) {
          Log.log(5, "RPAction", methodName, 
              "CGPAN details are not available. get them.");
          ArrayList<ArrayList> totalList = rpProcessor.displaySFCGPANs(danId);
          panAllocationDetails = totalList.get(0);
        } 
        for (int j = 0; j < panAllocationDetails.size(); j++) {
          AllocationDetail allocationDetail = panAllocationDetails
            .get(j);
          Log.log(5, 
              "RPActionForm", 
              "validate", 
              " cgpan from danpandetails " + 
              allocationDetail.getCgpan());
          if (allocationDetail.getAllocatedFlag().equals("N")) {
            Log.log(5, "RPActionForm", "validate", 
                " not allocated ");
            String reasons = (String)notAllocatedReasons
              .get(String.valueOf(shiftDanId) + "-" + 
                allocationDetail.getCgpan());
            Log.log(5, "RPActionForm", "validate", 
                " reason for not allocated " + reasons);
            allocationDetail.setNotAllocatedReason(reasons);
          } else {
            allocationDetail.setNotAllocatedReason("");
          } 
          panAllocationDetails.set(j, allocationDetail);
        } 
        danCgpanDetails.put(danId, panAllocationDetails);
      } 
    } 
    paymentDetails.setRealisationDate(actionForm.getDateOfRealisation());
    paymentId = rpProcessor.appropriateallocateDAN(paymentDetails, 
        danSummaries, allocationFlags, cgpans, danCgpanDetails, user);
    request.setAttribute("message", 
        "Payment Allocated & Appropriated Successfully.<BR>Payment ID : " + 
        paymentId);
    Log.log(5, "RPAction", methodName, "Exited");
    return mapping.findForward("success");
  }
  
  public ActionForward submitASFDANPayments(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    double totalAmount = 0.0D;
    Map danIds = actionForm.getDanIds();
    Map allocatedFlags = actionForm.getAllocatedFlags();
    Map cgpans = actionForm.getCgpans();
    int allocatedcount = 0;
    int testallocatecount = 0;
    Set danIdSet = danIds.keySet();
    Log.log(5, "RPAction", "submitASFDANPayments", "Checkbox size = " + 
        allocatedFlags.size());
    Set<String> cgpansSet = cgpans.keySet();
    Iterator<String> cgpanIterator = cgpansSet.iterator();
    Log.log(5, "RPAction", "submitASFDANPayments", "Checkbox size = " + 
        cgpans.size());
    while (cgpanIterator.hasNext()) {
      String key = cgpanIterator.next();
      String value = (String)cgpans.get(key);
      Log.log(5, "RPAction", "submitASFDANPayments", "cgpan key = " + key);
      Log.log(5, "RPAction", "submitASFDANPayments", "cgpan value = " + 
          value);
    } 
    cgpanIterator = cgpansSet.iterator();
    ArrayList<DANSummary> danSummaries = actionForm.getDanSummaries();
    boolean isAllocated = false;
    for (int i = 0; i < danSummaries.size(); i++) {
      DANSummary danSummary = danSummaries.get(i);
      String danId = danSummary.getDanId();
      Log.log(5, "RPAction", "submitASFDANPayments", "danId= " + danId);
      String danIdKey = danId.replace('.', '_');
      if (allocatedFlags.containsKey(danIdKey) && 
        request.getParameter("allocatedFlag(" + danIdKey + ")") != null) {
        allocatedcount++;
        Log.log(5, "RPAction", "submitASFDANPayments", "danSummaries= " + 
            danSummaries.size());
        isAllocated = true;
        totalAmount += danSummary.getAmountDue() - 
          danSummary.getAmountPaid();
        Log.log(5, 
            "RPAction", 
            "submitASFDANPayments", 
            "due amount " + (
            danSummary.getAmountDue() - danSummary
            .getAmountPaid()));
      } else {
        Log.log(5, "RPAction", "submitASFDANPayments", 
            "CGPANS are allocated ");
        ArrayList<AllocationDetail> panDetails = (ArrayList)actionForm
          .getDanPanDetail(danId);
        while (cgpanIterator.hasNext()) {
          String key = cgpanIterator.next();
          String value = (String)cgpans.get(key);
          String cgpanPart = value.substring(value.indexOf("-") + 1, 
              value.length());
          String tempKey = value.replace('.', '_');
          Log.log(5, "RPAction", "submitASFDANPayments", "key " + key);
          Log.log(5, "RPAction", "submitASFDANPayments", "value " + 
              value);
          Log.log(5, "RPAction", "submitASFDANPayments", "tempKey " + 
              tempKey);
          if (value.startsWith(danId) && 
            allocatedFlags.get(tempKey) != null && (
            (String)allocatedFlags.get(tempKey))
            .equals("Y")) {
            testallocatecount++;
            cgpanPart = value.substring(value.indexOf("-") + 1, 
                value.length());
            isAllocated = true;
            for (int j = 0; j < panDetails.size(); j++) {
              AllocationDetail allocation = panDetails
                .get(j);
              Log.log(5, "RPAction", "submitASFDANPayments", 
                  "amount for CGPAN " + allocation.getCgpan() + 
                  "," + allocation.getAmountDue());
              if (cgpanPart.equals(allocation.getCgpan())) {
                totalAmount += allocation.getAmountDue();
                break;
              } 
            } 
          } 
        } 
        cgpanIterator = cgpansSet.iterator();
      } 
    } 
    if (!isAllocated)
      throw new MissingDANDetailsException("No Allocation made."); 
    Registration registration = new Registration();
    Log.log(5, "RPAction", "submitASFDANPayments", "member id " + 
        actionForm.getMemberId());
    CollectingBank collectingBank = registration.getCollectingBank("(" + 
        actionForm.getMemberId() + ")");
    Log.log(5, "RPAction", "submitASFDANPayments", "collectingBank " + 
        collectingBank);
    actionForm.setModeOfPayment("");
    actionForm.setPaymentDate(null);
    actionForm.setInstrumentNo("");
    actionForm.setInstrumentDate(null);
    actionForm.setDrawnAtBank("");
    actionForm.setDrawnAtBranch("");
    actionForm.setPayableAt("");
    IFProcessor ifProcessor = new IFProcessor();
    ArrayList instruments = ifProcessor.getInstrumentTypes("G");
    actionForm.setInstruments(instruments);
    actionForm.setCollectingBank(collectingBank.getCollectingBankId());
    actionForm
      .setCollectingBankName(collectingBank.getCollectingBankName());
    actionForm.setCollectingBankBranch(collectingBank.getBranchName());
    actionForm.setAccountNumber(collectingBank.getAccNo());
    actionForm.setInstrumentAmount(totalAmount);
    return mapping.findForward("asfpaymentDetails");
  }
  
  public ActionForward submitExpiredASFDANPayments(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    double totalAmount = 0.0D;
    Map danIds = actionForm.getDanIds();
    Map allocatedFlags = actionForm.getAllocatedFlags();
    Map cgpans = actionForm.getCgpans();
    int allocatedcount = 0;
    int testallocatecount = 0;
    Set danIdSet = danIds.keySet();
    Log.log(5, "RPAction", "submitExpiredASFDANPayments", 
        "Checkbox size = " + allocatedFlags.size());
    Set<String> cgpansSet = cgpans.keySet();
    Iterator<String> cgpanIterator = cgpansSet.iterator();
    Log.log(5, "RPAction", "submitExpiredASFDANPayments", 
        "Checkbox size = " + cgpans.size());
    while (cgpanIterator.hasNext()) {
      String key = cgpanIterator.next();
      String value = (String)cgpans.get(key);
      Log.log(5, "RPAction", "submitExpiredASFDANPayments", 
          "cgpan key = " + key);
      Log.log(5, "RPAction", "submitExpiredASFDANPayments", 
          "cgpan value = " + value);
    } 
    cgpanIterator = cgpansSet.iterator();
    ArrayList<DANSummary> danSummaries = actionForm.getDanSummaries();
    boolean isAllocated = false;
    for (int i = 0; i < danSummaries.size(); i++) {
      DANSummary danSummary = danSummaries.get(i);
      String danId = danSummary.getDanId();
      Log.log(5, "RPAction", "submitExpiredASFDANPayments", "danId= " + 
          danId);
      String danIdKey = danId.replace('.', '_');
      if (allocatedFlags.containsKey(danIdKey) && 
        request.getParameter("allocatedFlag(" + danIdKey + ")") != null) {
        allocatedcount++;
        Log.log(5, "RPAction", "submitExpiredASFDANPayments", 
            "danSummaries= " + danSummaries.size());
        isAllocated = true;
        totalAmount += danSummary.getAmountDue() - 
          danSummary.getAmountPaid();
        Log.log(5, 
            "RPAction", 
            "submitExpiredASFDANPayments", 
            "due amount " + (
            danSummary.getAmountDue() - danSummary
            .getAmountPaid()));
      } else {
        Log.log(5, "RPAction", "submitExpiredASFDANPayments", 
            "CGPANS are allocated ");
        ArrayList<AllocationDetail> panDetails = (ArrayList)actionForm
          .getDanPanDetail(danId);
        while (cgpanIterator.hasNext()) {
          String key = cgpanIterator.next();
          String value = (String)cgpans.get(key);
          String cgpanPart = value.substring(value.indexOf("-") + 1, 
              value.length());
          String tempKey = value.replace('.', '_');
          Log.log(5, "RPAction", "submitExpiredASFDANPayments", 
              "key " + key);
          Log.log(5, "RPAction", "submitExpiredASFDANPayments", 
              "value " + value);
          Log.log(5, "RPAction", "submitExpiredASFDANPayments", 
              "tempKey " + tempKey);
          if (value.startsWith(danId) && 
            allocatedFlags.get(tempKey) != null && (
            (String)allocatedFlags.get(tempKey))
            .equals("Y")) {
            testallocatecount++;
            cgpanPart = value.substring(value.indexOf("-") + 1, 
                value.length());
            isAllocated = true;
            for (int j = 0; j < panDetails.size(); j++) {
              AllocationDetail allocation = panDetails
                .get(j);
              Log.log(5, "RPAction", 
                  "submitExpiredASFDANPayments", 
                  "amount for CGPAN " + allocation.getCgpan() + 
                  "," + allocation.getAmountDue());
              if (cgpanPart.equals(allocation.getCgpan())) {
                totalAmount += allocation.getAmountDue();
                break;
              } 
            } 
          } 
        } 
        cgpanIterator = cgpansSet.iterator();
      } 
    } 
    if (!isAllocated)
      throw new MissingDANDetailsException("No Allocation made."); 
    Registration registration = new Registration();
    Log.log(5, "RPAction", "submitExpiredASFDANPayments", "member id " + 
        actionForm.getMemberId());
    CollectingBank collectingBank = registration.getCollectingBank("(" + 
        actionForm.getMemberId() + ")");
    Log.log(5, "RPAction", "submitExpiredASFDANPayments", "collectingBank " + 
        collectingBank);
    actionForm.setModeOfPayment("");
    actionForm.setPaymentDate(null);
    actionForm.setInstrumentNo("");
    actionForm.setInstrumentDate(null);
    actionForm.setDrawnAtBank("");
    actionForm.setDrawnAtBranch("");
    actionForm.setPayableAt("");
    IFProcessor ifProcessor = new IFProcessor();
    ArrayList instruments = ifProcessor.getInstrumentTypes("G");
    actionForm.setInstruments(instruments);
    actionForm.setCollectingBank(collectingBank.getCollectingBankId());
    actionForm
      .setCollectingBankName(collectingBank.getCollectingBankName());
    actionForm.setCollectingBankBranch(collectingBank.getBranchName());
    actionForm.setAccountNumber(collectingBank.getAccNo());
    actionForm.setInstrumentAmount(totalAmount);
    return mapping.findForward("asfpaymentDetails");
  }
  
  public ActionForward submitGFDANPayments(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    double totalAmount = 0.0D;
    StringTokenizer tokenizer = null;
    double tot = 0.0D;
    Map appropriatedCases = actionForm.getAppropriatedFlags();
    if (appropriatedCases.size() < 1)
      throw new MissingDANDetailsException(
          "Please select atleast one dan for allocation ."); 
    Set appropriatedCasesSet = appropriatedCases.keySet();
    Iterator<String> appropriatedCasesIterator = appropriatedCasesSet.iterator();
    String token = null;
    String token1 = null;
    float total = 0.0F;
    float total2 = 0.0F;
    while (appropriatedCasesIterator.hasNext()) {
      String key = appropriatedCasesIterator.next();
      tokenizer = new StringTokenizer(key, "#");
      for (; tokenizer
        .hasMoreTokens(); System.out.println(total2)) {
        token = tokenizer.nextToken();
        token1 = tokenizer.nextToken();
        total = Integer.parseInt(token1);
        total2 += total;
      } 
    } 
    Registration registration = new Registration();
    Log.log(5, "RPAction", "submitGFDANPayments", 
        "member id " + actionForm.getMemberId());
    CollectingBank collectingBank = registration
      .getCollectingBank("(" + 
        actionForm.getMemberId() + ")");
    Log.log(5, "RPAction", "submitGFDANPayments", 
        "collectingBank " + collectingBank);
    actionForm.setModeOfPayment("");
    actionForm.setPaymentDate(null);
    actionForm.setInstrumentNo("");
    actionForm.setInstrumentDate(null);
    actionForm.setDrawnAtBank("");
    actionForm.setDrawnAtBranch("");
    actionForm.setPayableAt("");
    IFProcessor ifProcessor = new IFProcessor();
    ArrayList instruments = ifProcessor.getInstrumentTypes("G");
    actionForm.setInstruments(instruments);
    actionForm.setCollectingBank(collectingBank.getCollectingBankId());
    actionForm
      .setCollectingBankName(collectingBank.getCollectingBankName());
    actionForm.setCollectingBankBranch(collectingBank.getBranchName());
    actionForm.setAccountNumber(collectingBank.getAccNo());
    actionForm.setInstrumentAmount(total2);
    return mapping.findForward("gfpaymentDetails");
  }
  
  public ActionForward submitGFDANPaymentsOnlione(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    double totalAmount = 0.0D;
    StringTokenizer tokenizer = null;
    double tot = 0.0D;
    Map appropriatedCases = actionForm.getAppropriatedFlags();
    if (appropriatedCases.size() < 1)
      throw new MissingDANDetailsException(
          "Please select atleast one dan for allocation ."); 
    Set appropriatedCasesSet = appropriatedCases.keySet();
    Iterator<String> appropriatedCasesIterator = appropriatedCasesSet.iterator();
    String token = null;
    String token1 = null;
    float total = 0.0F;
    float total2 = 0.0F;
    while (appropriatedCasesIterator.hasNext()) {
      String key = appropriatedCasesIterator.next();
      tokenizer = new StringTokenizer(key, "#");
      for (; tokenizer
        .hasMoreTokens(); System.out.println(total2)) {
        token = tokenizer.nextToken();
        token1 = tokenizer.nextToken();
        total = Integer.parseInt(token1);
        total2 += total;
      } 
    } 
    Registration registration = new Registration();
    Log.log(5, "RPAction", "submitGFDANPayments", 
        "member id " + actionForm.getMemberId());
    CollectingBank collectingBank = registration
      .getCollectingBank("(" + 
        actionForm.getMemberId() + ")");
    Log.log(5, "RPAction", "submitGFDANPayments", 
        "collectingBank " + collectingBank);
    actionForm.setModeOfPayment("");
    actionForm.setPaymentDate(null);
    actionForm.setInstrumentNo("");
    actionForm.setInstrumentDate(null);
    actionForm.setDrawnAtBank("");
    actionForm.setDrawnAtBranch("");
    actionForm.setPayableAt("");
    IFProcessor ifProcessor = new IFProcessor();
    ArrayList instruments = ifProcessor.getInstrumentTypes("G");
    actionForm.setInstruments(instruments);
    actionForm.setCollectingBank(collectingBank.getCollectingBankId());
    actionForm
      .setCollectingBankName(collectingBank.getCollectingBankName());
    actionForm.setCollectingBankBranch(collectingBank.getBranchName());
    actionForm.setAccountNumber(collectingBank.getAccNo());
    actionForm.setInstrumentAmount(total2);
    return mapping.findForward("gfpaymentDetails");
  }
  
  public ActionForward submitGFDANPaymentsold(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    double totalAmount = 0.0D;
    Map allocatedFlags = actionForm.getAllocatedFlags();
    Map cgpans = actionForm.getCgpans();
    int allocatedcount = 0;
    int testallocatecount = 0;
    Log.log(5, "RPAction", "submitGFDANPayments", "Checkbox size = " + 
        allocatedFlags.size());
    Set<String> cgpansSet = cgpans.keySet();
    Iterator<String> cgpanIterator = cgpansSet.iterator();
    Log.log(5, "RPAction", "submitGFDANPayments", "Checkbox size = " + 
        cgpans.size());
    while (cgpanIterator.hasNext()) {
      String key = cgpanIterator.next();
      String value = (String)cgpans.get(key);
      Log.log(5, "RPAction", "submitGFDANPayments", "cgpan key = " + key);
      Log.log(5, "RPAction", "submitGFDANPayments", "cgpan value = " + 
          value);
    } 
    cgpanIterator = cgpansSet.iterator();
    ArrayList<DANSummary> danSummaries = actionForm.getDanSummaries();
    boolean isAllocated = false;
    for (int i = 0; i < danSummaries.size(); i++) {
      DANSummary danSummary = danSummaries.get(i);
      String danId = danSummary.getDanId();
      Log.log(5, "RPAction", "submitGFDANPayments", "danId= " + danId);
      String danIdKey = danId.replace('.', '_');
      if (allocatedFlags.containsKey(danIdKey) && 
        request.getParameter("allocatedFlag(" + danIdKey + ")") != null) {
        allocatedcount++;
        Log.log(5, "RPAction", "submitGFDANPayments", "danSummaries= " + 
            danSummaries.size());
        isAllocated = true;
        totalAmount += danSummary.getAmountDue() - 
          danSummary.getAmountPaid();
        Log.log(5, 
            "RPAction", 
            "submitGFDANPayments", 
            "due amount " + (
            danSummary.getAmountDue() - danSummary
            .getAmountPaid()));
      } else {
        Log.log(5, "RPAction", "submitGFDANPayments", 
            "CGPANS are allocated ");
        ArrayList<AllocationDetail> panDetails = (ArrayList)actionForm
          .getDanPanDetail(danId);
        while (cgpanIterator.hasNext()) {
          String key = cgpanIterator.next();
          String value = (String)cgpans.get(key);
          String cgpanPart = value.substring(value.indexOf("-") + 1, 
              value.length());
          String tempKey = value.replace('.', '_');
          Log.log(5, "RPAction", "submitGFDANPayments", "key " + key);
          Log.log(5, "RPAction", "submitGFDANPayments", "value " + 
              value);
          Log.log(5, "RPAction", "submitGFDANPayments", "tempKey " + 
              tempKey);
          if (value.startsWith(danId) && 
            allocatedFlags.get(tempKey) != null && (
            (String)allocatedFlags.get(tempKey))
            .equals("Y")) {
            testallocatecount++;
            cgpanPart = value.substring(value.indexOf("-") + 1, 
                value.length());
            isAllocated = true;
            for (int j = 0; j < panDetails.size(); j++) {
              AllocationDetail allocation = panDetails
                .get(j);
              Log.log(5, "RPAction", "submitGFDANPayments", 
                  "amount for CGPAN " + allocation.getCgpan() + 
                  "," + allocation.getAmountDue());
              if (cgpanPart.equals(allocation.getCgpan())) {
                totalAmount += allocation.getAmountDue();
                break;
              } 
            } 
          } 
        } 
        cgpanIterator = cgpansSet.iterator();
      } 
    } 
    if (!isAllocated)
      throw new MissingDANDetailsException("No Allocation made."); 
    Registration registration = new Registration();
    Log.log(5, "RPAction", "submitGFDANPayments", 
        "member id " + actionForm.getMemberId());
    CollectingBank collectingBank = registration.getCollectingBank("(" + 
        actionForm.getMemberId() + ")");
    Log.log(5, "RPAction", "submitGFDANPayments", "collectingBank " + 
        collectingBank);
    actionForm.setModeOfPayment("");
    actionForm.setPaymentDate(null);
    actionForm.setInstrumentNo("");
    actionForm.setInstrumentDate(null);
    actionForm.setDrawnAtBank("");
    actionForm.setDrawnAtBranch("");
    actionForm.setPayableAt("");
    IFProcessor ifProcessor = new IFProcessor();
    ArrayList instruments = ifProcessor.getInstrumentTypes("G");
    actionForm.setInstruments(instruments);
    actionForm.setCollectingBank(collectingBank.getCollectingBankId());
    actionForm
      .setCollectingBankName(collectingBank.getCollectingBankName());
    actionForm.setCollectingBankBranch(collectingBank.getBranchName());
    actionForm.setAccountNumber(collectingBank.getAccNo());
    actionForm.setInstrumentAmount(totalAmount);
    return mapping.findForward("gfpaymentDetails");
  }
  
  public ActionForward submitNEFTGFDANPayments(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    StringTokenizer tokenizer = null;
    double tot = 0.0D;
    Map appropriatedCases = actionForm.getAppropriatedFlags();
    if (appropriatedCases.size() < 1)
      throw new MissingDANDetailsException(
          "Please select atleast one dan for allocation."); 
    Set appropriatedCasesSet = appropriatedCases.keySet();
    Iterator<String> appropriatedCasesIterator = appropriatedCasesSet.iterator();
    String token = null;
    String token2 = null;
    float total = 0.0F;
    float total2 = 0.0F;
    while (appropriatedCasesIterator.hasNext()) {
      String key = appropriatedCasesIterator.next();
      tokenizer = new StringTokenizer(key, "#");
      while (tokenizer
        .hasMoreElements()) {
        token = tokenizer.nextToken();
        token2 = tokenizer.nextToken();
        total = Integer.parseInt(token2);
        total2 += total;
      } 
    } 
    Registration registration = new Registration();
    Log.log(5, "RPAction", "submitNEFTGFDANPayments", "member id " + 
        actionForm.getMemberId());
    CollectingBank collectingBank = registration.getCollectingBank("(" + 
        actionForm.getMemberId() + ")");
    Log.log(5, "RPAction", "submitNEFTGFDANPayments", "collectingBank " + 
        collectingBank);
    actionForm.setModeOfPayment("");
    actionForm.setPaymentDate(null);
    actionForm.setInstrumentNo("");
    actionForm.setInstrumentDate(null);
    actionForm.setDrawnAtBank("");
    actionForm.setDrawnAtBranch("");
    actionForm.setPayableAt("");
    IFProcessor ifProcessor = new IFProcessor();
    actionForm.setCollectingBank("IDBI BANK LTD");
    actionForm.setCollectingBankBranch("CHEMBUR");
    actionForm.setAccountNumber("018102000014951");
    actionForm
      .setAccountName("Credit Guarantee Fund Trust for Micro And Small Enterprises");
    actionForm.setIfscCode("IBKL0000018");
    actionForm.setModeOfPayment("NEFT");
    actionForm.setInstrumentAmount(total2);
    return mapping.findForward("gfpaymentDetailsNew");
  }
  
  public ActionForward submitNEFTGFDANPaymentsOld(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    double totalAmount = 0.0D;
    Map danIds = actionForm.getDanIds();
    Map allocatedFlags = actionForm.getAllocatedFlags();
    Map cgpans = actionForm.getCgpans();
    int allocatedcount = 0;
    int testallocatecount = 0;
    Set danIdSet = danIds.keySet();
    Log.log(5, "RPAction", "submitNEFTGFDANPayments", "Checkbox size = " + 
        allocatedFlags.size());
    Set<String> cgpansSet = cgpans.keySet();
    Iterator<String> cgpanIterator = cgpansSet.iterator();
    Log.log(5, "RPAction", "submitNEFTGFDANPayments", "Checkbox size = " + 
        cgpans.size());
    while (cgpanIterator.hasNext()) {
      String key = cgpanIterator.next();
      String value = (String)cgpans.get(key);
      Log.log(5, "RPAction", "submitNEFTGFDANPayments", "cgpan key = " + 
          key);
      Log.log(5, "RPAction", "submitNEFTGFDANPayments", "cgpan value = " + 
          value);
    } 
    cgpanIterator = cgpansSet.iterator();
    ArrayList<DANSummary> danSummaries = actionForm.getDanSummaries();
    boolean isAllocated = false;
    for (int i = 0; i < danSummaries.size(); i++) {
      DANSummary danSummary = danSummaries.get(i);
      String danId = danSummary.getDanId();
      Log.log(5, "RPAction", "submitNEFTGFDANPayments", "danId= " + danId);
      String danIdKey = danId.replace('.', '_');
      if (allocatedFlags.containsKey(danIdKey) && 
        request.getParameter("allocatedFlag(" + danIdKey + ")") != null) {
        allocatedcount++;
        Log.log(5, "RPAction", "submitNEFTGFDANPayments", 
            "danSummaries= " + danSummaries.size());
        isAllocated = true;
        totalAmount += danSummary.getAmountDue() - 
          danSummary.getAmountPaid();
        Log.log(5, 
            "RPAction", 
            "submitNEFTGFDANPayments", 
            "due amount " + (
            danSummary.getAmountDue() - danSummary
            .getAmountPaid()));
      } else {
        Log.log(5, "RPAction", "submitNEFTGFDANPayments", 
            "CGPANS are allocated ");
        ArrayList<AllocationDetail> panDetails = (ArrayList)actionForm
          .getDanPanDetail(danId);
        while (cgpanIterator.hasNext()) {
          String key = cgpanIterator.next();
          String value = (String)cgpans.get(key);
          String cgpanPart = value.substring(value.indexOf("-") + 1, 
              value.length());
          String tempKey = value.replace('.', '_');
          Log.log(5, "RPAction", "submitNEFTGFDANPayments", "key " + 
              key);
          Log.log(5, "RPAction", "submitNEFTGFDANPayments", "value " + 
              value);
          Log.log(5, "RPAction", "submitNEFTGFDANPayments", 
              "tempKey " + tempKey);
          if (value.startsWith(danId) && 
            allocatedFlags.get(tempKey) != null && (
            (String)allocatedFlags.get(tempKey))
            .equals("Y")) {
            testallocatecount++;
            cgpanPart = value.substring(value.indexOf("-") + 1, 
                value.length());
            isAllocated = true;
            for (int j = 0; j < panDetails.size(); j++) {
              AllocationDetail allocation = panDetails
                .get(j);
              Log.log(5, "RPAction", "submitNEFTGFDANPayments", 
                  "amount for CGPAN " + allocation.getCgpan() + 
                  "," + allocation.getAmountDue());
              if (cgpanPart.equals(allocation.getCgpan())) {
                totalAmount += allocation.getAmountDue();
                break;
              } 
            } 
          } 
        } 
        cgpanIterator = cgpansSet.iterator();
      } 
    } 
    if (!isAllocated)
      throw new MissingDANDetailsException("No Allocation made."); 
    Registration registration = new Registration();
    Log.log(5, "RPAction", "submitNEFTGFDANPayments", "member id " + 
        actionForm.getMemberId());
    CollectingBank collectingBank = registration.getCollectingBank("(" + 
        actionForm.getMemberId() + ")");
    Log.log(5, "RPAction", "submitNEFTGFDANPayments", "collectingBank " + 
        collectingBank);
    actionForm.setModeOfPayment("");
    actionForm.setPaymentDate(null);
    actionForm.setInstrumentNo("");
    actionForm.setInstrumentDate(null);
    actionForm.setDrawnAtBank("");
    actionForm.setDrawnAtBranch("");
    actionForm.setPayableAt("");
    IFProcessor ifProcessor = new IFProcessor();
    actionForm.setCollectingBank("IDBI BANK LTD");
    actionForm.setCollectingBankBranch("CHEMBUR");
    actionForm.setAccountNumber("018102000014951");
    actionForm
      .setAccountName("Credit Guarantee Fund Trust for Micro And Small Enterprises");
    actionForm.setIfscCode("IBKL0000018");
    actionForm.setModeOfPayment("NEFT");
    actionForm.setInstrumentAmount(totalAmount);
    return mapping.findForward("gfpaymentDetailsNew");
  }
  
  public ActionForward submitDANPayments(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    double totalAmount = 0.0D;
    Map danIds = actionForm.getDanIds();
    Map allocatedFlags = actionForm.getAllocatedFlags();
    Map cgpans = actionForm.getCgpans();
    int allocatedcount = 0;
    int testallocatecount = 0;
    Set danIdSet = danIds.keySet();
    Log.log(5, "RPAction", "submitDANPayments", "Checkbox size = " + 
        allocatedFlags.size());
    Set<String> cgpansSet = cgpans.keySet();
    Iterator<String> cgpanIterator = cgpansSet.iterator();
    Log.log(5, "RPAction", "submitDANPayments", 
        "Checkbox size = " + cgpans.size());
    while (cgpanIterator.hasNext()) {
      String key = cgpanIterator.next();
      String value = (String)cgpans.get(key);
      Log.log(5, "RPAction", "submitDANPayments", "cgpan key = " + key);
      Log.log(5, "RPAction", "submitDANPayments", "cgpan value = " + 
          value);
    } 
    cgpanIterator = cgpansSet.iterator();
    ArrayList<DANSummary> danSummaries = actionForm.getDanSummaries();
    boolean isAllocated = false;
    for (int i = 0; i < danSummaries.size(); i++) {
      DANSummary danSummary = danSummaries.get(i);
      String danId = danSummary.getDanId();
      Log.log(5, "RPAction", "submitDANPayments", "danId= " + danId);
      String danIdKey = danId.replace('.', '_');
      if (allocatedFlags.containsKey(danIdKey) && 
        request.getParameter("allocatedFlag(" + danIdKey + ")") != null) {
        allocatedcount++;
        Log.log(5, "RPAction", "submitDANPayments", "danSummaries= " + 
            danSummaries.size());
        isAllocated = true;
        totalAmount += danSummary.getAmountDue() - 
          danSummary.getAmountPaid();
        Log.log(5, 
            "RPAction", 
            "submitDANPayments", 
            "due amount " + (
            danSummary.getAmountDue() - danSummary
            .getAmountPaid()));
      } else {
        Log.log(5, "RPAction", "submitDANPayments", 
            "CGPANS are allocated ");
        ArrayList<AllocationDetail> panDetails = (ArrayList)actionForm
          .getDanPanDetail(danId);
        while (cgpanIterator.hasNext()) {
          String key = cgpanIterator.next();
          String value = (String)cgpans.get(key);
          String cgpanPart = value.substring(value.indexOf("-") + 1, 
              value.length());
          String tempKey = value.replace('.', '_');
          Log.log(5, "RPAction", "submitDANPayments", "key " + key);
          Log.log(5, "RPAction", "submitDANPayments", "value " + 
              value);
          Log.log(5, "RPAction", "submitDANPayments", "tempKey " + 
              tempKey);
          if (value.startsWith(danId) && 
            allocatedFlags.get(tempKey) != null && (
            (String)allocatedFlags.get(tempKey))
            .equals("Y")) {
            testallocatecount++;
            cgpanPart = value.substring(value.indexOf("-") + 1, 
                value.length());
            isAllocated = true;
            for (int j = 0; j < panDetails.size(); j++) {
              AllocationDetail allocation = panDetails
                .get(j);
              Log.log(5, "RPAction", "submitDANPayments", 
                  "amount for CGPAN " + allocation.getCgpan() + 
                  "," + allocation.getAmountDue());
              if (cgpanPart.equals(allocation.getCgpan())) {
                totalAmount += allocation.getAmountDue();
                break;
              } 
            } 
          } 
        } 
        cgpanIterator = cgpansSet.iterator();
      } 
    } 
    if (!isAllocated)
      throw new MissingDANDetailsException("No Allocation made."); 
    Registration registration = new Registration();
    Log.log(5, "RPAction", "submitDANPayments", 
        "member id " + actionForm.getMemberId());
    CollectingBank collectingBank = registration.getCollectingBank("(" + 
        actionForm.getMemberId() + ")");
    Log.log(5, "RPAction", "submitDANPayments", "collectingBank " + 
        collectingBank);
    actionForm.setModeOfPayment("");
    actionForm.setPaymentDate(null);
    actionForm.setInstrumentNo("");
    actionForm.setInstrumentDate(null);
    actionForm.setDrawnAtBank("");
    actionForm.setDrawnAtBranch("");
    actionForm.setPayableAt("");
    IFProcessor ifProcessor = new IFProcessor();
    ArrayList instruments = ifProcessor.getInstrumentTypes("G");
    actionForm.setInstruments(instruments);
    actionForm.setCollectingBank(collectingBank.getCollectingBankId());
    actionForm
      .setCollectingBankName(collectingBank.getCollectingBankName());
    actionForm.setCollectingBankBranch(collectingBank.getBranchName());
    actionForm.setAccountNumber(collectingBank.getAccNo());
    actionForm.setInstrumentAmount(totalAmount);
    return mapping.findForward("paymentDetails");
  }
  
  public ActionForward submitSFDANPayments(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    double totalAmount = 0.0D;
    Map danIds = actionForm.getDanIds();
    Map allocatedFlags = actionForm.getAllocatedFlags();
    Map cgpans = actionForm.getCgpans();
    Set danIdSet = danIds.keySet();
    Log.log(5, "RPAction", "submitSFDANPayments", "Checkbox size = " + 
        allocatedFlags.size());
    Set<String> cgpansSet = cgpans.keySet();
    Iterator<String> cgpanIterator = cgpansSet.iterator();
    Log.log(5, "RPAction", "submitSFDANPayments", "Checkbox size = " + 
        cgpans.size());
    while (cgpanIterator.hasNext()) {
      String key = cgpanIterator.next();
      String value = (String)cgpans.get(key);
      Log.log(5, "RPAction", "submitSFDANPayments", "cgpan key = " + key);
      Log.log(5, "RPAction", "submitSFDANPayments", "cgpan value = " + 
          value);
    } 
    cgpanIterator = cgpansSet.iterator();
    ArrayList<DANSummary> danSummaries = actionForm.getDanSummaries();
    boolean isAllocated = false;
    for (int i = 0; i < danSummaries.size(); i++) {
      DANSummary danSummary = danSummaries.get(i);
      String danId = danSummary.getDanId();
      Log.log(5, "RPAction", "submitSFDANPayments", "danId= " + danId);
      String danIdKey = danId.replace('.', '_');
      if (allocatedFlags.containsKey(danIdKey) && 
        request.getParameter("allocatedFlag(" + danIdKey + ")") != null) {
        Log.log(5, "RPAction", "submitSFDANPayments", "danSummaries= " + 
            danSummaries.size());
        isAllocated = true;
        totalAmount += danSummary.getAmountDue() - 
          danSummary.getAmountPaid();
        Log.log(5, 
            "RPAction", 
            "submitSFDANPayments", 
            "due amount " + (
            danSummary.getAmountDue() - danSummary
            .getAmountPaid()));
      } else {
        Log.log(5, "RPAction", "submitSFDANPayments", 
            "CGPANS are allocated ");
        ArrayList<AllocationDetail> panDetails = (ArrayList)actionForm
          .getDanPanDetail(danId);
        while (cgpanIterator.hasNext()) {
          String key = cgpanIterator.next();
          String value = (String)cgpans.get(key);
          String cgpanPart = value.substring(value.indexOf("-") + 1, 
              value.length());
          String tempKey = value.replace('.', '_');
          Log.log(5, "RPAction", "submitSFDANPayments", "key " + key);
          Log.log(5, "RPAction", "submitSFDANPayments", "value " + 
              value);
          Log.log(5, "RPAction", "submitSFDANPayments", "tempKey " + 
              tempKey);
          if (value.startsWith(danId) && 
            allocatedFlags.get(tempKey) != null && (
            (String)allocatedFlags.get(tempKey))
            .equals("Y")) {
            cgpanPart = value.substring(value.indexOf("-") + 1, 
                value.length());
            isAllocated = true;
            for (int j = 0; j < panDetails.size(); j++) {
              AllocationDetail allocation = panDetails
                .get(j);
              Log.log(5, "RPAction", "submitSFDANPayments", 
                  "amount for CGPAN " + allocation.getCgpan() + 
                  "," + allocation.getAmountDue());
              if (cgpanPart.equals(allocation.getCgpan())) {
                totalAmount += allocation.getAmountDue();
                break;
              } 
            } 
          } 
        } 
        cgpanIterator = cgpansSet.iterator();
      } 
    } 
    if (!isAllocated)
      throw new MissingDANDetailsException("No Allocation made."); 
    Registration registration = new Registration();
    Log.log(5, "RPAction", "submitSFDANPayments", 
        "member id " + actionForm.getMemberId());
    CollectingBank collectingBank = registration.getCollectingBank("(" + 
        actionForm.getMemberId() + ")");
    Log.log(5, "RPAction", "submitSFDANPayments", "collectingBank " + 
        collectingBank);
    actionForm.setModeOfPayment("");
    actionForm.setPaymentDate(null);
    actionForm.setInstrumentNo("");
    actionForm.setInstrumentDate(null);
    actionForm.setDrawnAtBank("");
    actionForm.setDrawnAtBranch("");
    actionForm.setPayableAt("");
    IFProcessor ifProcessor = new IFProcessor();
    ArrayList instruments = ifProcessor.getInstrumentTypes("G");
    actionForm.setInstruments(instruments);
    actionForm.setCollectingBank(collectingBank.getCollectingBankId());
    actionForm
      .setCollectingBankName(collectingBank.getCollectingBankName());
    actionForm.setCollectingBankBranch(collectingBank.getBranchName());
    actionForm.setAccountNumber(collectingBank.getAccNo());
    actionForm.setInstrumentAmount(totalAmount);
    return mapping.findForward("sfpaymentDetails");
  }
  
  public ActionForward getPaymentsMade(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList paymentDetails = rpProcessor.displayPaymentsReceived();
    RPActionForm actionForm = (RPActionForm)form;
    actionForm.getCgpans().clear();
    actionForm.setPaymentDetails(paymentDetails);
    return mapping.findForward("paymentsSummary");
  }
  
  public ActionForward getPaymentsMadeForGF(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList paymentDetails = rpProcessor.displayPaymentsReceivedForGF();
    RPActionForm actionForm = (RPActionForm)form;
    actionForm.getCgpans().clear();
    actionForm.setPaymentDetails(paymentDetails);
    return mapping.findForward("paymentsSummary");
  }
  
  public ActionForward gfbatchappropriatePayments(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList paymentDetails = rpProcessor
      .displayBatchPaymentsReceivedForGF();
    RPActionForm actionForm = (RPActionForm)form;
    actionForm.getCgpans().clear();
    actionForm.setPaymentDetails(paymentDetails);
    return mapping.findForward("paymentsSummary");
  }
  
  public ActionForward daywisegfbatchappropriation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    Date date = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    GeneralReport generalReport = new GeneralReport();
    generalReport.setDateOfTheDocument24(date);
    BeanUtils.copyProperties(actionForm, generalReport);
    return mapping.findForward("inputDate");
  }
  
  public ActionForward daywiseddmarkedforDeposited(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    Date date = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    GeneralReport generalReport = new GeneralReport();
    generalReport.setDateOfTheDocument24(date);
    BeanUtils.copyProperties(actionForm, generalReport);
    return mapping.findForward("inputDate");
  }
  
  public ActionForward daywisegfbatchappropriatePayments(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RpProcessor rpProcessor = new RpProcessor();
    RPActionForm actionForm = (RPActionForm)form;
    Date dateofRealisation = actionForm.getDateOfTheDocument24();
    ArrayList paymentDetails = rpProcessor
      .daywiseBatchPaymentsReceivedForGF(dateofRealisation);
    actionForm.getCgpans().clear();
    actionForm.setPaymentDetails(paymentDetails);
    return mapping.findForward("paymentsSummary");
  }
  
  public ActionForward daywiseddmarkedforDepositedCases(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RpProcessor rpProcessor = new RpProcessor();
    RPActionForm actionForm = (RPActionForm)form;
    Date inwardDate = actionForm.getDateOfTheDocument24();
    ArrayList paymentDetails = rpProcessor
      .daywiseBatchPaymentsInwardedForGF(inwardDate);
    actionForm.getCgpans().clear();
    actionForm.setPaymentDetails(paymentDetails);
    return mapping.findForward("paymentsSummary");
  }
  
  public ActionForward dayWiseddMarkedForDepositedDate(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "dayWiseddMarkedForDepositedDate", "Entered");
    RPActionForm actionForm = (RPActionForm)form;
    RpProcessor rpProcessor = new RpProcessor();
    Date dateofDeposit = actionForm.getDateOfTheDocument24();
    User user = getUserInformation(request);
    String userId = user.getUserId();
    Log.log(4, "RPAction", "dayWiseddMarkedForDepositedDate", "Exited");
    return mapping.findForward("deposited");
  }
  
  public ActionForward aftergfbatchappropriatePayments(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "aftergfbatchappropriatePayments", "Entered");
    RPActionForm actionForm = (RPActionForm)form;
    RpProcessor rpProcessor = new RpProcessor();
    User user = getUserInformation(request);
    String userId = user.getUserId();
    int appropriatedCount = 0;
    Map appropriatedCases = actionForm.getAppropriatedFlags();
    Set appropriatedCasesSet = appropriatedCases.keySet();
    Iterator<String> appropriatedCasesIterator = appropriatedCasesSet.iterator();
    boolean appropriatedFlag = false;
    while (appropriatedCasesIterator.hasNext()) {
      String key = appropriatedCasesIterator.next();
      String decision = (String)appropriatedCases.get(key);
      if (decision.equals("Y")) {
        appropriatedCount += rpProcessor
          .aftergfbatchappropriatePayments(key, userId);
        appropriatedFlag = true;
      } 
    } 
    if (!appropriatedFlag)
      throw new MissingDANDetailsException("No Appropriation Made."); 
    System.out.println("Appropriated Count:" + appropriatedCount);
    actionForm.setPaymentDetails(null);
    appropriatedCases.clear();
    request.setAttribute("message", "No.of Appropriations done are: " + 
        appropriatedCount);
    Log.log(4, "RPAction", "aftergfbatchappropriatePayments", "Exited");
    return mapping.findForward("success");
  }
  
  public ActionForward aftergfdaywisebatchappropriatePayments(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "aftergfdaywisebatchappropriatePayments", 
        "Entered");
    RPActionForm actionForm = (RPActionForm)form;
    RpProcessor rpProcessor = new RpProcessor();
    Date dateofRealisation = actionForm.getDateOfTheDocument24();
    User user = getUserInformation(request);
    String userId = user.getUserId();
    int appropriatedCount = 0;
    Map appropriatedCases = actionForm.getAppropriatedFlags();
    Set appropriatedCasesSet = appropriatedCases.keySet();
    Iterator<String> appropriatedCasesIterator = appropriatedCasesSet.iterator();
    boolean appropriatedFlag = false;
    while (appropriatedCasesIterator.hasNext()) {
      String key = appropriatedCasesIterator.next();
      String decision = (String)appropriatedCases.get(key);
      if (decision.equals("Y")) {
        appropriatedCount += rpProcessor
          .aftergfdaywisebatchappropriatePayments(key, userId, 
            dateofRealisation);
        appropriatedFlag = true;
      } 
    } 
    if (!appropriatedFlag)
      throw new MissingDANDetailsException("No Appropriation Made."); 
    System.out.println("Appropriated Count:" + appropriatedCount);
    actionForm.setPaymentDetails(null);
    appropriatedCases.clear();
    request.setAttribute("message", "No.of Appropriations done are: " + 
        appropriatedCount);
    Log.log(4, "RPAction", "aftergfdaywisebatchappropriatePayments", 
        "Exited");
    return mapping.findForward("success");
  }
  
  public ActionForward dayWiseddMarkedForDepositedSummary(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "dayWiseddMarkedForDepositedSummary", "Entered");
    RPActionForm actionForm = (RPActionForm)form;
    RpProcessor rpProcessor = new RpProcessor();
    Date dateofDeposit = actionForm.getDateOfTheDocument24();
    System.out.println("After Confirm date of Deposit:" + dateofDeposit);
    User user = getUserInformation(request);
    String userId = user.getUserId();
    int depositedCount = 0;
    StringTokenizer tokenizer = null;
    String instrumentNo = null;
    String inwardId = null;
    Map appropriatedCases = actionForm.getAppropriatedFlags();
    Set appropriatedCasesSet = appropriatedCases.keySet();
    Iterator<String> appropriatedCasesIterator = appropriatedCasesSet.iterator();
    boolean appropriatedFlag = false;
    while (appropriatedCasesIterator.hasNext()) {
      String key = appropriatedCasesIterator.next();
      String decision = (String)appropriatedCases.get(key);
      if (decision.equals("Y")) {
        String token = null;
        tokenizer = new StringTokenizer(key, "#");
        boolean isInstrumentNoRead = false;
        boolean isInwardIdRead = false;
        while (tokenizer.hasMoreTokens()) {
          token = tokenizer.nextToken();
          if (!isInwardIdRead) {
            if (!isInstrumentNoRead) {
              instrumentNo = token;
              isInstrumentNoRead = true;
              continue;
            } 
            inwardId = token;
            isInwardIdRead = true;
          } 
        } 
        depositedCount += rpProcessor
          .dayWiseddMarkedForDepositedSummary(inwardId, 
            instrumentNo, userId, dateofDeposit);
        appropriatedFlag = true;
      } 
    } 
    if (!appropriatedFlag)
      throw new MissingDANDetailsException("No Selection Made."); 
    System.out.println("No.of dds Marked for Deposited:" + depositedCount);
    actionForm.setPaymentDetails(null);
    appropriatedCases.clear();
    request.setAttribute("message", "No.of dds Marked for Deposited are: " + 
        depositedCount);
    Log.log(4, "RPAction", "dayWiseddMarkedForDepositedSummary", "Exited");
    return mapping.findForward("success");
  }
  
  public ActionForward getPaymentsMadeForASF(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList paymentDetails = rpProcessor.displayPaymentsReceivedForASF();
    RPActionForm actionForm = (RPActionForm)form;
    actionForm.getCgpans().clear();
    actionForm.setPaymentDetails(paymentDetails);
    return mapping.findForward("paymentsSummary");
  }
  
  public ActionForward getPaymentsMadeForCLAIM(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList paymentDetails = rpProcessor
      .displayPaymentsReceivedForCLAIM();
    RPActionForm actionForm = (RPActionForm)form;
    actionForm.getCgpans().clear();
    actionForm.setPaymentDetails(paymentDetails);
    return mapping.findForward("paymentsSummary");
  }
  
  public ActionForward getPaymentsForReallocation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "getPaymentsForReallocation", "Entered");
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList paymentDetails = rpProcessor.displayPaymentsForReallocation();
    Log.log(5, "RPAction", "getPaymentsForReallocation", "paymentDetails " + 
        paymentDetails);
    RPActionForm actionForm = (RPActionForm)form;
    Log.log(5, "RPAction", "getPaymentsForReallocation", "actionForm " + 
        actionForm);
    actionForm.setPaymentDetails(paymentDetails);
    Log.log(4, "RPAction", "getPaymentsForReallocation", "Exited");
    return mapping.findForward("success");
  }
  
  public ActionForward submitReallocationPayments(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "submitReallocationPayments", "Entered");
    RPActionForm actionForm = (RPActionForm)form;
    String payId = actionForm.getPaymentId();
    Log.log(5, "RPAction", "submitReallocationPayments", 
        "Pay id from form is " + payId);
    RpProcessor rpProcessor = new RpProcessor();
    User user = getUserInformation(request);
    rpProcessor.submitReAllocationDetails(actionForm, request, user, payId);
    request.setAttribute("message", "Reallocation details are updated.");
    Log.log(4, "RPAction", "submitReallocationPayments", "Exited");
    return mapping.findForward("success");
  }
  
  public ActionForward submitASFPANPayments(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "submitASFPANPayments", "Entered");
    RPActionForm actionForm = (RPActionForm)form;
    String danNo = actionForm.getDanNo();
    Log.log(5, "RPAction", "submitASFPANPayments", "danNo " + danNo);
    ArrayList<AllocationDetail> panAllocationDetails = (ArrayList)actionForm
      .getDanPanDetail(danNo);
    String strDanNo = danNo.replace('.', '_');
    Map allocatedFlags = actionForm.getAllocatedFlags();
    Map cgpans = actionForm.getCgpans();
    Set<String> cgpanSet = cgpans.keySet();
    Iterator<String> cgpanIterator = cgpanSet.iterator();
    Log.log(5, "RPAction", "submitASFPANPayments", "CGPANS selected " + 
        cgpans.size());
    while (cgpanIterator.hasNext()) {
      String key = cgpanIterator.next();
      Log.log(5, "RPAction", "submitASFPANPayments", "key,value " + key + 
          "," + cgpans.get(key));
      Log.log(5, 
          "RPAction", 
          "submitASFPANPayments", 
          "From request " + 
          request.getParameter(
            "cgpan(" + key + ")"));
    } 
    cgpanIterator = cgpanSet.iterator();
    String cgpanPart = null;
    Log.log(5, "RPAction", "submitASFPANPayments", 
        "browsing through the pan list");
    boolean isAvl = false;
    String value = null;
    Log.log(5, "RPAction", "submitASFPANPayments", "Cgpan map size " + 
        cgpans.size());
    for (int i = 0; i < panAllocationDetails.size(); i++) {
      AllocationDetail allocationDetail = panAllocationDetails
        .get(i);
      Log.log(5, "RPAction", "submitASFPANPayments", "cgpan frm array " + 
          allocationDetail.getCgpan());
      Log.log(5, "RPAction", "submitASFPANPayments", "flag frm array " + 
          allocationDetail.getAllocatedFlag());
      while (cgpanIterator.hasNext()) {
        Object key = cgpanIterator.next();
        value = (String)cgpans.get(key);
        Log.log(5, "RPAction", "submitASFPANPayments", "key " + key);
        Log.log(5, "RPAction", "submitASFPANPayments", "value " + value);
        cgpanPart = value.substring(value.indexOf("-") + 1, 
            value.length());
        Log.log(5, "RPAction", "submitASFPANPayments", "cgpanPart " + 
            cgpanPart);
        if (value.startsWith(danNo) && 
          cgpanPart.equals(allocationDetail.getCgpan()) && 
          allocatedFlags.get(key) != null && (
          (String)allocatedFlags.get(key)).equals("Y")) {
          Log.log(5, "RPAction", "submitASFPANPayments", 
              "amount due  " + allocationDetail.getAmountDue());
          allocationDetail.setAllocatedFlag("Y");
          isAvl = true;
          break;
        } 
      } 
      if (!isAvl) {
        Object removed = cgpans.remove(String.valueOf(strDanNo) + "-" + 
            allocationDetail.getCgpan());
        Log.log(5, "RPAction", "submitASFPANPayments", 
            "Removed element" + removed);
      } 
      isAvl = false;
      cgpanIterator = cgpanSet.iterator();
    } 
    Log.log(5, "RPAction", "submitASFPANPayments", "Cgpan map size " + 
        cgpans.size());
    return mapping.findForward("danSummary");
  }
  
  public ActionForward submitExpiredASFPANPayments(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "submitExpiredASFPANPayments", "Entered");
    RPActionForm actionForm = (RPActionForm)form;
    String danNo = actionForm.getDanNo();
    Log.log(5, "RPAction", "submitExpiredASFPANPayments", "danNo " + danNo);
    ArrayList<AllocationDetail> panAllocationDetails = (ArrayList)actionForm
      .getDanPanDetail(danNo);
    String strDanNo = danNo.replace('.', '_');
    Map allocatedFlags = actionForm.getAllocatedFlags();
    Map cgpans = actionForm.getCgpans();
    Set<String> cgpanSet = cgpans.keySet();
    Iterator<String> cgpanIterator = cgpanSet.iterator();
    Log.log(5, "RPAction", "submitExpiredASFPANPayments", 
        "CGPANS selected " + cgpans.size());
    while (cgpanIterator.hasNext()) {
      String key = cgpanIterator.next();
      Log.log(5, "RPAction", "submitExpiredASFPANPayments", "key,value " + 
          key + "," + cgpans.get(key));
      Log.log(5, 
          "RPAction", 
          "submitExpiredASFPANPayments", 
          "From request " + 
          request.getParameter(
            "cgpan(" + key + ")"));
    } 
    cgpanIterator = cgpanSet.iterator();
    String cgpanPart = null;
    Log.log(5, "RPAction", "submitExpiredASFPANPayments", 
        "browsing through the pan list");
    boolean isAvl = false;
    String value = null;
    Log.log(5, "RPAction", "submitExpiredASFPANPayments", "Cgpan map size " + 
        cgpans.size());
    for (int i = 0; i < panAllocationDetails.size(); i++) {
      AllocationDetail allocationDetail = panAllocationDetails
        .get(i);
      Log.log(5, "RPAction", "submitExpiredASFPANPayments", 
          "cgpan frm array " + allocationDetail.getCgpan());
      Log.log(5, "RPAction", "submitExpiredASFPANPayments", 
          "flag frm array " + allocationDetail.getAllocatedFlag());
      while (cgpanIterator.hasNext()) {
        Object key = cgpanIterator.next();
        value = (String)cgpans.get(key);
        Log.log(5, "RPAction", "submitExpiredASFPANPayments", "key " + 
            key);
        Log.log(5, "RPAction", "submitExpiredASFPANPayments", "value " + 
            value);
        cgpanPart = value.substring(value.indexOf("-") + 1, 
            value.length());
        Log.log(5, "RPAction", "submitExpiredASFPANPayments", 
            "cgpanPart " + cgpanPart);
        if (value.startsWith(danNo) && 
          cgpanPart.equals(allocationDetail.getCgpan()) && 
          allocatedFlags.get(key) != null && (
          (String)allocatedFlags.get(key)).equals("Y")) {
          Log.log(5, "RPAction", "submitExpiredASFPANPayments", 
              "amount due  " + allocationDetail.getAmountDue());
          allocationDetail.setAllocatedFlag("Y");
          isAvl = true;
          break;
        } 
      } 
      if (!isAvl) {
        Object removed = cgpans.remove(String.valueOf(strDanNo) + "-" + 
            allocationDetail.getCgpan());
        Log.log(5, "RPAction", "submitExpiredASFPANPayments", 
            "Removed element" + removed);
      } 
      isAvl = false;
      cgpanIterator = cgpanSet.iterator();
    } 
    Log.log(5, "RPAction", "submitExpiredASFPANPayments", "Cgpan map size " + 
        cgpans.size());
    return mapping.findForward("danSummary");
  }
  
  public ActionForward submitGFPANPayments(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "submitGFPANPayments", "Entered");
    RPActionForm actionForm = (RPActionForm)form;
    String danNo = actionForm.getDanNo();
    Log.log(5, "RPAction", "submitGFPANPayments", "danNo " + danNo);
    ArrayList<AllocationDetail> panAllocationDetails = (ArrayList)actionForm
      .getDanPanDetail(danNo);
    String strDanNo = danNo.replace('.', '_');
    Map allocatedFlags = actionForm.getAllocatedFlags();
    Map cgpans = actionForm.getCgpans();
    Set<String> cgpanSet = cgpans.keySet();
    Iterator<String> cgpanIterator = cgpanSet.iterator();
    Log.log(5, "RPAction", "submitGFPANPayments", "CGPANS selected " + 
        cgpans.size());
    while (cgpanIterator.hasNext()) {
      String key = cgpanIterator.next();
      Log.log(5, "RPAction", "submitGFPANPayments", "key,value " + key + 
          "," + cgpans.get(key));
      Log.log(5, 
          "RPAction", 
          "submitGFPANPayments", 
          "From request " + 
          request.getParameter(
            "cgpan(" + key + ")"));
    } 
    cgpanIterator = cgpanSet.iterator();
    String cgpanPart = null;
    Log.log(5, "RPAction", "submitGFPANPayments", 
        "browsing through the pan list");
    boolean isAvl = false;
    String value = null;
    Log.log(5, "RPAction", "submitGFPANPayments", "Cgpan map size " + 
        cgpans.size());
    for (int i = 0; i < panAllocationDetails.size(); i++) {
      AllocationDetail allocationDetail = panAllocationDetails
        .get(i);
      Log.log(5, "RPAction", "submitGFPANPayments", "cgpan frm array " + 
          allocationDetail.getCgpan());
      Log.log(5, "RPAction", "submitGFPANPayments", "flag frm array " + 
          allocationDetail.getAllocatedFlag());
      while (cgpanIterator.hasNext()) {
        Object key = cgpanIterator.next();
        value = (String)cgpans.get(key);
        Log.log(5, "RPAction", "submitGFPANPayments", "key " + key);
        Log.log(5, "RPAction", "submitGFPANPayments", "value " + value);
        cgpanPart = value.substring(value.indexOf("-") + 1, 
            value.length());
        Log.log(5, "RPAction", "submitGFPANPayments", "cgpanPart " + 
            cgpanPart);
        if (value.startsWith(danNo) && 
          cgpanPart.equals(allocationDetail.getCgpan()) && 
          allocatedFlags.get(key) != null && (
          (String)allocatedFlags.get(key)).equals("Y")) {
          Log.log(5, "RPAction", "submitGFPANPayments", 
              "amount due  " + allocationDetail.getAmountDue());
          allocationDetail.setAllocatedFlag("Y");
          isAvl = true;
          break;
        } 
      } 
      if (!isAvl) {
        Object removed = cgpans.remove(String.valueOf(strDanNo) + "-" + 
            allocationDetail.getCgpan());
        Log.log(5, "RPAction", "submitGFPANPayments", "Removed element" + 
            removed);
      } 
      isAvl = false;
      cgpanIterator = cgpanSet.iterator();
    } 
    Log.log(5, "RPAction", "submitGFPANPayments", "Cgpan map size " + 
        cgpans.size());
    return mapping.findForward("danSummary");
  }
  
  public ActionForward submitPANPayments(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "submitPANPayments", "Entered");
    RPActionForm actionForm = (RPActionForm)form;
    String danNo = actionForm.getDanNo();
    Log.log(5, "RPAction", "submitPANPayments", "danNo " + danNo);
    ArrayList<AllocationDetail> panAllocationDetails = (ArrayList)actionForm
      .getDanPanDetail(danNo);
    String strDanNo = danNo.replace('.', '_');
    Map allocatedFlags = actionForm.getAllocatedFlags();
    Map cgpans = actionForm.getCgpans();
    Set<String> cgpanSet = cgpans.keySet();
    Iterator<String> cgpanIterator = cgpanSet.iterator();
    Log.log(5, "RPAction", "submitPANPayments", 
        "CGPANS selected " + cgpans.size());
    while (cgpanIterator.hasNext()) {
      String key = cgpanIterator.next();
      Log.log(5, "RPAction", "submitPANPayments", "key,value " + key + 
          "," + cgpans.get(key));
      Log.log(5, 
          "RPAction", 
          "submitPANPayments", 
          "From request " + 
          request.getParameter(
            "cgpan(" + key + ")"));
    } 
    cgpanIterator = cgpanSet.iterator();
    String cgpanPart = null;
    Log.log(5, "RPAction", "submitPANPayments", 
        "browsing through the pan list");
    boolean isAvl = false;
    String value = null;
    Log.log(5, "RPAction", "submitPANPayments", 
        "Cgpan map size " + cgpans.size());
    for (int i = 0; i < panAllocationDetails.size(); i++) {
      AllocationDetail allocationDetail = panAllocationDetails
        .get(i);
      Log.log(5, "RPAction", "submitPANPayments", "cgpan frm array " + 
          allocationDetail.getCgpan());
      Log.log(5, "RPAction", "submitPANPayments", "flag frm array " + 
          allocationDetail.getAllocatedFlag());
      while (cgpanIterator.hasNext()) {
        Object key = cgpanIterator.next();
        value = (String)cgpans.get(key);
        Log.log(5, "RPAction", "submitPANPayments", "key " + key);
        Log.log(5, "RPAction", "submitPANPayments", "value " + value);
        cgpanPart = value.substring(value.indexOf("-") + 1, 
            value.length());
        Log.log(5, "RPAction", "submitPANPayments", "cgpanPart " + 
            cgpanPart);
        if (value.startsWith(danNo) && 
          cgpanPart.equals(allocationDetail.getCgpan()) && 
          allocatedFlags.get(key) != null && (
          (String)allocatedFlags.get(key)).equals("Y")) {
          Log.log(5, "RPAction", "submitPANPayments", "amount due  " + 
              allocationDetail.getAmountDue());
          isAvl = true;
          break;
        } 
      } 
      if (!isAvl) {
        Object removed = cgpans.remove(String.valueOf(strDanNo) + "-" + 
            allocationDetail.getCgpan());
        Log.log(5, "RPAction", "submitPANPayments", "Removed element" + 
            removed);
      } 
      isAvl = false;
      cgpanIterator = cgpanSet.iterator();
    } 
    Log.log(5, "RPAction", "submitPANPayments", 
        "Cgpan map size " + cgpans.size());
    return mapping.findForward("danSummary");
  }
  
  public ActionForward submitSFPANPayments(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "submitsfPANPayments", "Entered");
    RPActionForm actionForm = (RPActionForm)form;
    String danNo = actionForm.getDanNo();
    Log.log(5, "RPAction", "submitPANPayments", "danNo " + danNo);
    ArrayList<AllocationDetail> panAllocationDetails = (ArrayList)actionForm
      .getDanPanDetail(danNo);
    String strDanNo = danNo.replace('.', '_');
    Map allocatedFlags = actionForm.getAllocatedFlags();
    Map cgpans = actionForm.getCgpans();
    Set<String> cgpanSet = cgpans.keySet();
    Iterator<String> cgpanIterator = cgpanSet.iterator();
    Log.log(5, "RPAction", "submitPANPayments", 
        "CGPANS selected " + cgpans.size());
    while (cgpanIterator.hasNext()) {
      String key = cgpanIterator.next();
      Log.log(5, "RPAction", "submitPANPayments", "key,value " + key + 
          "," + cgpans.get(key));
      Log.log(5, 
          "RPAction", 
          "submitPANPayments", 
          "From request " + 
          request.getParameter(
            "cgpan(" + key + ")"));
    } 
    cgpanIterator = cgpanSet.iterator();
    String cgpanPart = null;
    Log.log(5, "RPAction", "submitPANPayments", 
        "browsing through the pan list");
    boolean isAvl = false;
    String value = null;
    Log.log(5, "RPAction", "submitPANPayments", 
        "Cgpan map size " + cgpans.size());
    for (int i = 0; i < panAllocationDetails.size(); i++) {
      AllocationDetail allocationDetail = panAllocationDetails
        .get(i);
      Log.log(5, "RPAction", "submitPANPayments", "cgpan frm array " + 
          allocationDetail.getCgpan());
      Log.log(5, "RPAction", "submitPANPayments", "flag frm array " + 
          allocationDetail.getAllocatedFlag());
      while (cgpanIterator.hasNext()) {
        Object key = cgpanIterator.next();
        value = (String)cgpans.get(key);
        Log.log(5, "RPAction", "submitPANPayments", "key " + key);
        Log.log(5, "RPAction", "submitPANPayments", "value " + value);
        cgpanPart = value.substring(value.indexOf("-") + 1, 
            value.length());
        Log.log(5, "RPAction", "submitPANPayments", "cgpanPart " + 
            cgpanPart);
        if (value.startsWith(danNo) && 
          cgpanPart.equals(allocationDetail.getCgpan()) && 
          allocatedFlags.get(key) != null && (
          (String)allocatedFlags.get(key)).equals("Y")) {
          Log.log(5, "RPAction", "submitPANPayments", "amount due  " + 
              allocationDetail.getAmountDue());
          allocationDetail.setAllocatedFlag("Y");
          isAvl = true;
          break;
        } 
      } 
      if (!isAvl) {
        Object removed = cgpans.remove(String.valueOf(strDanNo) + "-" + 
            allocationDetail.getCgpan());
        Log.log(5, "RPAction", "submitPANPayments", "Removed element" + 
            removed);
      } 
      isAvl = false;
      cgpanIterator = cgpanSet.iterator();
    } 
    Log.log(5, "RPAction", "submitPANPayments", 
        "Cgpan map size " + cgpans.size());
    return mapping.findForward("danSummary");
  }
  
  public ActionForward getAllocatedPaymentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String methodName = "getAllocatedPaymentDetails";
    RPActionForm actionForm = (RPActionForm)form;
    Log.log(4, "RPAction", methodName, "Entering");
    actionForm.getCgpans().clear();
    String payId = request.getParameter("payId");
    String memberId = request.getParameter("memberId");
    actionForm.setPaymentId(payId);
    actionForm.setMemberId(memberId);
    Log.log(5, "RPAction", methodName, "Got paymentId " + payId);
    Log.log(5, "RPAction", methodName, "Got memberId " + memberId);
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList<AllocationDetail> paymentDetails = null;
    Log.log(5, "RPAction", methodName, "Before calling payment details ");
    paymentDetails = rpProcessor.getDANDetailsForReallocation(payId, 
        memberId);
    Map<Object, Object> danCgpanInfo = new HashMap<Object, Object>();
    Map<String, String> cgpans = actionForm.getCgpans();
    ArrayList<AllocationDetail> panDetails = null;
    String tempDanNo = "";
    for (int i = 0; i < paymentDetails.size(); i++) {
      panDetails = null;
      AllocationDetail allocationDtl = paymentDetails
        .get(i);
      Log.log(4, "RPAction", methodName, "dan no " + tempDanNo);
      Log.log(4, "RPAction", methodName, 
          "cgpan " + allocationDtl.getCgpan());
      if (allocationDtl.getAllocatedFlag().equals("Y"))
        cgpans.put(String.valueOf(allocationDtl.getDanNo().replace('.', '_')) + "-" + 
            allocationDtl.getCgpan(), allocationDtl.getCgpan()); 
      if (danCgpanInfo.containsKey(allocationDtl.getDanNo())) {
        panDetails = (ArrayList)danCgpanInfo.get(allocationDtl
            .getDanNo());
      } else {
        panDetails = new ArrayList();
      } 
      panDetails.add(allocationDtl);
      danCgpanInfo.put(allocationDtl.getDanNo(), panDetails);
    } 
    Log.log(5, "RPAction", methodName, "After calling payment details");
    Log.log(5, "RPAction", methodName, 
        "Before dynaForm set in RPAction::getPaymentDetails");
    actionForm.setDanPanDetails(danCgpanInfo);
    actionForm.setCgpans(cgpans);
    Log.log(5, "RPAction", methodName, 
        "After dynaForm set in RPAction::getPaymentDetails");
    return mapping.findForward("paymentDetails");
  }
  
  public ActionForward getPaymentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String methodName = "getPaymentDetails";
    RPActionForm actionForm = (RPActionForm)form;
    Log.log(4, "RPAction", methodName, "Entering");
    String paymentId = actionForm.getPaymentId();
    Log.log(4, "RPAction", methodName, "Got paymentId");
    actionForm.getCgpans().clear();
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList paymentDetails = null;
    Log.log(4, "RPAction", methodName, "Before calling payment details");
    paymentDetails = rpProcessor.getPaymentDetails(paymentId);
    HashMap<Object, Object> bankIds = new HashMap<Object, Object>();
    HashMap<Object, Object> zoneIds = new HashMap<Object, Object>();
    HashMap<Object, Object> branchIds = new HashMap<Object, Object>();
    PaymentDetails payDetails = (PaymentDetails)paymentDetails.get(0);
    actionForm.setInstrumentDate(payDetails.getInstrumentDate());
    for (int i = 1; i < paymentDetails.size(); i++) {
      DemandAdvice demandAdvice = (DemandAdvice)paymentDetails.get(i);
      demandAdvice.setAppropriated(demandAdvice.getAllocated());
      actionForm.setAppropriatedFlag("key-" + (i - 1), 
          demandAdvice.getAllocated());
      bankIds.put("key-" + (i - 1), demandAdvice.getBankId());
      zoneIds.put("key-" + (i - 1), demandAdvice.getZoneId());
      branchIds.put("key-" + (i - 1), demandAdvice.getBranchId());
    } 
    Log.log(4, "RPAction", methodName, "After calling payment details");
    Log.log(4, "RPAction", methodName, 
        "Before dynaForm set in RPAction::getPaymentDetails");
    actionForm.setPaymentDetails(paymentDetails);
    actionForm.setDateOfRealisation(null);
    actionForm.setReceivedAmount(0.0D);
    actionForm.setPaymentId(paymentId);
    actionForm.setBankIds(bankIds);
    actionForm.setBranchIds(branchIds);
    actionForm.setZoneIds(zoneIds);
    Log.log(4, "RPAction", methodName, 
        "After dynaForm set in RPAction::getPaymentDetails");
    return mapping.findForward("paymentDetails");
  }
  
  public ActionForward getPaymentDetailsForGF(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String methodName = "getPaymentDetails";
    RPActionForm actionForm = (RPActionForm)form;
    Log.log(4, "RPAction", methodName, "Entering");
    String paymentId = actionForm.getPaymentId();
    Log.log(4, "RPAction", methodName, "Got paymentId");
    actionForm.getCgpans().clear();
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList paymentDetails = null;
    Log.log(4, "RPAction", methodName, "Before calling payment details");
    paymentDetails = rpProcessor.getPaymentDetails(paymentId);
    HashMap<Object, Object> bankIds = new HashMap<Object, Object>();
    HashMap<Object, Object> zoneIds = new HashMap<Object, Object>();
    HashMap<Object, Object> branchIds = new HashMap<Object, Object>();
    PaymentDetails payDetails =(PaymentDetails)paymentDetails.get(0);
    actionForm.setInstrumentDate(payDetails.getInstrumentDate());
    for (int i = 1; i < paymentDetails.size(); i++) {
      DemandAdvice demandAdvice = (DemandAdvice)paymentDetails.get(i);
      demandAdvice.setAppropriated(demandAdvice.getAllocated());
      actionForm.setAppropriatedFlag("key-" + (i - 1), 
          demandAdvice.getAllocated());
      bankIds.put("key-" + (i - 1), demandAdvice.getBankId());
      zoneIds.put("key-" + (i - 1), demandAdvice.getZoneId());
      branchIds.put("key-" + (i - 1), demandAdvice.getBranchId());
    } 
    Log.log(4, "RPAction", methodName, "After calling payment details");
    Log.log(4, "RPAction", methodName, 
        "Before dynaForm set in RPAction::getPaymentDetails");
    actionForm.setPaymentDetails(paymentDetails);
    actionForm.setDateOfRealisation(null);
    actionForm.setReceivedAmount(0.0D);
    actionForm.setPaymentId(paymentId);
    actionForm.setBankIds(bankIds);
    actionForm.setBranchIds(branchIds);
    actionForm.setZoneIds(zoneIds);
    Log.log(4, "RPAction", methodName, 
        "After dynaForm set in RPAction::getPaymentDetails");
    return mapping.findForward("paymentDetails");
  }
  
  public ActionForward getPaymentDetailsForASF(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String methodName = "getPaymentDetails";
    RPActionForm actionForm = (RPActionForm)form;
    Log.log(4, "RPAction", methodName, "Entering");
    String paymentId = actionForm.getPaymentId();
    Log.log(4, "RPAction", methodName, "Got paymentId");
    actionForm.getCgpans().clear();
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList paymentDetails = null;
    Log.log(4, "RPAction", methodName, "Before calling payment details");
    paymentDetails = rpProcessor.getPaymentDetails(paymentId);
    HashMap<Object, Object> bankIds = new HashMap<Object, Object>();
    HashMap<Object, Object> zoneIds = new HashMap<Object, Object>();
    HashMap<Object, Object> branchIds = new HashMap<Object, Object>();
    PaymentDetails payDetails = (PaymentDetails)paymentDetails.get(0);
    actionForm.setInstrumentDate(payDetails.getInstrumentDate());
    for (int i = 1; i < paymentDetails.size(); i++) {
      DemandAdvice demandAdvice = (DemandAdvice)paymentDetails.get(i);
      demandAdvice.setAppropriated(demandAdvice.getAllocated());
      actionForm.setAppropriatedFlag("key-" + (i - 1), 
          demandAdvice.getAllocated());
      bankIds.put("key-" + (i - 1), demandAdvice.getBankId());
      zoneIds.put("key-" + (i - 1), demandAdvice.getZoneId());
      branchIds.put("key-" + (i - 1), demandAdvice.getBranchId());
    } 
    Log.log(4, "RPAction", methodName, "After calling payment details");
    Log.log(4, "RPAction", methodName, 
        "Before dynaForm set in RPAction::getPaymentDetails");
    actionForm.setPaymentDetails(paymentDetails);
    actionForm.setDateOfRealisation(null);
    actionForm.setReceivedAmount(0.0D);
    actionForm.setPaymentId(paymentId);
    actionForm.setBankIds(bankIds);
    actionForm.setBranchIds(branchIds);
    actionForm.setZoneIds(zoneIds);
    Log.log(4, "RPAction", methodName, 
        "After dynaForm set in RPAction::getPaymentDetails");
    return mapping.findForward("paymentDetails");
  }
  
  public ActionForward getPaymentDetailsForCLAIM(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String methodName = "getPaymentDetails";
    RPActionForm actionForm = (RPActionForm)form;
    Log.log(4, "RPAction", methodName, "Entering");
    String paymentId = actionForm.getPaymentId();
    Log.log(4, "RPAction", methodName, "Got paymentId");
    actionForm.getCgpans().clear();
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList paymentDetails = null;
    Log.log(4, "RPAction", methodName, "Before calling payment details");
    paymentDetails = rpProcessor.getPaymentDetails(paymentId);
    HashMap<Object, Object> bankIds = new HashMap<Object, Object>();
    HashMap<Object, Object> zoneIds = new HashMap<Object, Object>();
    HashMap<Object, Object> branchIds = new HashMap<Object, Object>();
    PaymentDetails payDetails = (PaymentDetails)paymentDetails.get(0);
    actionForm.setInstrumentDate(payDetails.getInstrumentDate());
    for (int i = 1; i < paymentDetails.size(); i++) {
      DemandAdvice demandAdvice = (DemandAdvice)paymentDetails.get(i);
      demandAdvice.setAppropriated(demandAdvice.getAllocated());
      actionForm.setAppropriatedFlag("key-" + (i - 1), 
          demandAdvice.getAllocated());
      bankIds.put("key-" + (i - 1), demandAdvice.getBankId());
      zoneIds.put("key-" + (i - 1), demandAdvice.getZoneId());
      branchIds.put("key-" + (i - 1), demandAdvice.getBranchId());
    } 
    Log.log(4, "RPAction", methodName, "After calling payment details");
    Log.log(4, "RPAction", methodName, 
        "Before dynaForm set in RPAction::getPaymentDetails");
    actionForm.setPaymentDetails(paymentDetails);
    actionForm.setDateOfRealisation(null);
    actionForm.setReceivedAmount(0.0D);
    actionForm.setPaymentId(paymentId);
    actionForm.setBankIds(bankIds);
    actionForm.setBranchIds(branchIds);
    actionForm.setZoneIds(zoneIds);
    Log.log(4, "RPAction", methodName, 
        "After dynaForm set in RPAction::getPaymentDetails");
    return mapping.findForward("paymentDetails");
  }
  
  public ActionForward getClaimPaymentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String methodName = "getClaimPaymentDetails";
    RPActionForm actionForm = (RPActionForm)form;
    actionForm.resetWhenRequired();
    Log.log(4, "RPAction", methodName, "Entering");
    Log.log(5, "RPAction", methodName, "actionForm.getSelectMember() " + 
        actionForm.getSelectMember());
    IFProcessor ifProcessor = new IFProcessor();
    ArrayList instruments = ifProcessor.getInstrumentTypes("G");
    actionForm.setInstruments(instruments);
    Log.log(4, "RPAction", methodName, "Exited");
    return mapping.findForward("success");
  }
  
  public ActionForward insertPaymentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String methodName = "insertPaymentDetails";
    RPActionForm actionForm = (RPActionForm)form;
    Log.log(4, "RPAction", methodName, "Entering");
    PaymentDetails paymentDetails = new PaymentDetails();
    Log.log(5, "RPAction", methodName, "actionForm.getModeOfPayment() " + 
        actionForm.getModeOfPayment());
    BeanUtils.copyProperties(paymentDetails, actionForm);
    paymentDetails.setCollectingBank(actionForm.getCollectingBankName());
    Log.log(5, "RPAction", methodName, "paymentDetails.getModeOfPayment() " + 
        paymentDetails.getModeOfPayment());
    Log.log(5, "RPAction", methodName, "actionForm.getModeOfPayment() " + 
        actionForm.getModeOfPayment());
    RpProcessor rpProcessor = new RpProcessor();
    String paymentId = rpProcessor.insertPaymentByCGTSI(paymentDetails);
    Log.log(5, "RPAction", methodName, "paymentId " + paymentId);
    String message = "Payment details stored successfull. Payment id is " + 
      paymentId;
    request.setAttribute("message", message);
    Log.log(4, "RPAction", methodName, "Exited");
    return mapping.findForward("success");
  }
  
  public ActionForward appropriatePayments(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList<DemandAdvice> demandAdvices = new ArrayList();
    String methodName = "appropriatePayments";
    String danId = "";
    String cgpan = "";
    String allocatedFlag = "";
    String appropriatedFlag = "";
    String remark = "";
    String paymentId = "";
    DemandAdvice demandAdvice = null;
    Log.log(4, "RPAction", methodName, "Entered");
    Map danIds = actionForm.getDanIds();
    Map cgpans = actionForm.getCgpans();
    Map remarks = actionForm.getRemarks();
    Map allocatedFlags = actionForm.getAllocatedFlags();
    Map appropriatedFlags = actionForm.getAppropriatedFlags();
    Map amounts = actionForm.getAmountsRaised();
    Map penalties = actionForm.getPenalties();
    Map bankIds = actionForm.getBankIds();
    Map zoneIds = actionForm.getZoneIds();
    Map branchIds = actionForm.getBranchIds();
    Log.log(4, "RPAction", methodName, "Assigned CGPAN details to hashmap");
    Set danIdSet = danIds.keySet();
    Set allocatedFlagSet = allocatedFlags.keySet();
    Set appropriatedFlagSet = appropriatedFlags.keySet();
    Set cgpanSet = cgpans.keySet();
    Set remarksSet = remarks.keySet();
    Set amountsSet = amounts.keySet();
    Set penaltySet = penalties.keySet();
    Set bankIdSet = bankIds.keySet();
    Set zoneIdSet = zoneIds.keySet();
    Set branchIdSet = branchIds.keySet();
    Log.log(4, "RPAction", methodName, "Assigned CGPAN details to Set");
    Iterator<String> cgpanIterator = cgpanSet.iterator();
    Iterator bankIdIterator = bankIdSet.iterator();
    Iterator zoneIdIterator = zoneIdSet.iterator();
    Iterator branchIdIterator = branchIdSet.iterator();
    Log.log(4, "RPAction", methodName, "Assigned CGPAN details to Iterator");
    User user = getUserInformation(request);
    String userId = user.getUserId();
    paymentId = actionForm.getPaymentId();
    double appropriatedAmount = 0.0D;
    while (cgpanIterator.hasNext()) {
      String cgpanKey = cgpanIterator.next();
      danId = (String)danIds.get(cgpanKey);
      cgpan = (String)cgpans.get(cgpanKey);
      allocatedFlag = (String)allocatedFlags.get(cgpanKey);
      appropriatedFlag = (String)appropriatedFlags.get(cgpanKey);
      remark = (String)remarks.get(cgpanKey);
      double amount = Double.parseDouble((String)amounts.get(cgpanKey));
      double penalty = Double.parseDouble((String)penalties
          .get(cgpanKey));
      Log.log(4, "RPAction", methodName, " inside iterator - dan id - " + 
          danId);
      Log.log(4, "RPAction", methodName, " inside iterator - cgpan - " + 
          cgpan);
      Log.log(4, "RPAction", methodName, 
          " inside iterator - allocated flag - " + allocatedFlag);
      Log.log(4, "RPAction", methodName, 
          " inside iterator - appropriated flag - " + 
          appropriatedFlag);
      Log.log(4, "RPAction", methodName, " inside iterator - amount - " + 
          amount);
      Log.log(4, "RPAction", methodName, " inside iterator - penalty - " + 
          penalty);
      demandAdvice = new DemandAdvice();
      demandAdvice.setDanNo(danId);
      demandAdvice.setCgpan(cgpan);
      demandAdvice.setReason(remark);
      demandAdvice.setAmountRaised(amount);
      demandAdvice.setPenalty(penalty);
      demandAdvice.setPaymentId(paymentId);
      demandAdvice.setAllocated(appropriatedFlag);
      demandAdvice.setAppropriated(appropriatedFlag);
      demandAdvice.setUserId(userId);
      demandAdvice.setBankId((String)bankIds.get(bankIdIterator.next()));
      demandAdvice.setZoneId((String)zoneIds.get(zoneIdIterator.next()));
      demandAdvice.setBranchId((String)branchIds.get(branchIdIterator
            .next()));
      Log.log(4, "RPAction", methodName, " inside iterator - cgpan - " + 
          demandAdvice.getCgpan());
      Log.log(4, 
          "RPAction", 
          methodName, 
          " inside iterator - allocated flag - " + 
          demandAdvice.getAllocated());
      Log.log(4, 
          "RPAction", 
          methodName, 
          " inside iterator - appropriated flag - " + 
          demandAdvice.getAppropriated());
      if (appropriatedFlag.equals("Y"))
        appropriatedAmount += amount; 
      demandAdvices.add(demandAdvice);
      Log.log(4, "RPAction", methodName, 
          " inside iterator - adding cgpan to demand advice list - " + 
          cgpan);
      Log.log(4, "RPAction", methodName, 
          "DemandAdvices added to ArrayList");
    } 
    Date realisationDate = actionForm.getDateOfRealisation();
    double receivedAmount = actionForm.getReceivedAmount();
    RealisationDetail realisationDetail = new RealisationDetail();
    realisationDetail.setPaymentId(paymentId);
    realisationDetail.setRealisationAmount(receivedAmount);
    realisationDetail.setRealisationDate(realisationDate);
    if (receivedAmount < appropriatedAmount) {
      double shortLimit = appropriatedAmount - receivedAmount;
      throw new ShortExceedsLimitException(
          "Received Amount is less than Allocated Amount by Rs." + 
          shortLimit);
    } 
    if (receivedAmount > appropriatedAmount) {
      double excessLimit = receivedAmount - appropriatedAmount;
      throw new ShortExceedsLimitException(
          "Received Amount is greater than Allocated Amount by Rs." + 
          excessLimit);
    } 
    double shortOrExcess = rpProcessor.appropriatePayment(demandAdvices, 
        realisationDetail, request.getSession(false)
        .getServletContext().getRealPath(""));
    request.setAttribute("message", 
        "Payment Amount Appropriated Successfully.<BR><BR>Total Received Amount : " + 
        receivedAmount + "<BR>Total Appropriated Amount : " + 
        appropriatedAmount + "<BR>Short / Excess : " + 
        shortOrExcess);
    return mapping.findForward("success");
  }
  
  public ActionForward appropriatePaymentsForGF(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList<DemandAdvice> demandAdvices = new ArrayList();
    String methodName = "appropriatePayments";
    String danId = "";
    String cgpan = "";
    String allocatedFlag = "";
    String appropriatedFlag = "";
    String remark = "";
    String paymentId = "";
    DemandAdvice demandAdvice = null;
    Log.log(4, "RPAction", methodName, "Entered");
    Map danIds = actionForm.getDanIds();
    Map cgpans = actionForm.getCgpans();
    Map remarks = actionForm.getRemarks();
    Map allocatedFlags = actionForm.getAllocatedFlags();
    Map appropriatedFlags = actionForm.getAppropriatedFlags();
    Map amounts = actionForm.getAmountsRaised();
    Map penalties = actionForm.getPenalties();
    Map bankIds = actionForm.getBankIds();
    Map zoneIds = actionForm.getZoneIds();
    Map branchIds = actionForm.getBranchIds();
    Log.log(4, "RPAction", methodName, "Assigned CGPAN details to hashmap");
    Set danIdSet = danIds.keySet();
    Set allocatedFlagSet = allocatedFlags.keySet();
    Set appropriatedFlagSet = appropriatedFlags.keySet();
    Set cgpanSet = cgpans.keySet();
    Set remarksSet = remarks.keySet();
    Set amountsSet = amounts.keySet();
    Set penaltySet = penalties.keySet();
    Set bankIdSet = bankIds.keySet();
    Set zoneIdSet = zoneIds.keySet();
    Set branchIdSet = branchIds.keySet();
    Log.log(4, "RPAction", methodName, "Assigned CGPAN details to Set");
    Iterator<String> cgpanIterator = cgpanSet.iterator();
    Iterator bankIdIterator = bankIdSet.iterator();
    Iterator zoneIdIterator = zoneIdSet.iterator();
    Iterator branchIdIterator = branchIdSet.iterator();
    Log.log(4, "RPAction", methodName, "Assigned CGPAN details to Iterator");
    User user = getUserInformation(request);
    String userId = user.getUserId();
    paymentId = actionForm.getPaymentId();
    double appropriatedAmount = 0.0D;
    while (cgpanIterator.hasNext()) {
      String cgpanKey = cgpanIterator.next();
      danId = (String)danIds.get(cgpanKey);
      cgpan = (String)cgpans.get(cgpanKey);
      allocatedFlag = (String)allocatedFlags.get(cgpanKey);
      appropriatedFlag = (String)appropriatedFlags.get(cgpanKey);
      remark = (String)remarks.get(cgpanKey);
      double amount = Double.parseDouble((String)amounts.get(cgpanKey));
      double penalty = Double.parseDouble((String)penalties
          .get(cgpanKey));
      Log.log(4, "RPAction", methodName, " inside iterator - dan id - " + 
          danId);
      Log.log(4, "RPAction", methodName, " inside iterator - cgpan - " + 
          cgpan);
      Log.log(4, "RPAction", methodName, 
          " inside iterator - allocated flag - " + allocatedFlag);
      Log.log(4, "RPAction", methodName, 
          " inside iterator - appropriated flag - " + 
          appropriatedFlag);
      Log.log(4, "RPAction", methodName, " inside iterator - amount - " + 
          amount);
      Log.log(4, "RPAction", methodName, " inside iterator - penalty - " + 
          penalty);
      demandAdvice = new DemandAdvice();
      demandAdvice.setDanNo(danId);
      demandAdvice.setCgpan(cgpan);
      demandAdvice.setReason(remark);
      demandAdvice.setAmountRaised(amount);
      demandAdvice.setPenalty(penalty);
      demandAdvice.setPaymentId(paymentId);
      demandAdvice.setAllocated(appropriatedFlag);
      demandAdvice.setAppropriated(appropriatedFlag);
      demandAdvice.setUserId(userId);
      demandAdvice.setBankId((String)bankIds.get(bankIdIterator.next()));
      demandAdvice.setZoneId((String)zoneIds.get(zoneIdIterator.next()));
      demandAdvice.setBranchId((String)branchIds.get(branchIdIterator
            .next()));
      Log.log(4, "RPAction", methodName, " inside iterator - cgpan - " + 
          demandAdvice.getCgpan());
      Log.log(4, 
          "RPAction", 
          methodName, 
          " inside iterator - allocated flag - " + 
          demandAdvice.getAllocated());
      Log.log(4, 
          "RPAction", 
          methodName, 
          " inside iterator - appropriated flag - " + 
          demandAdvice.getAppropriated());
      if (appropriatedFlag.equals("Y"))
        appropriatedAmount += amount; 
      demandAdvices.add(demandAdvice);
      Log.log(4, "RPAction", methodName, 
          " inside iterator - adding cgpan to demand advice list - " + 
          cgpan);
      Log.log(4, "RPAction", methodName, 
          "DemandAdvices added to ArrayList");
    } 
    Date realisationDate = actionForm.getDateOfRealisation();
    double receivedAmount = actionForm.getReceivedAmount();
    RealisationDetail realisationDetail = new RealisationDetail();
    realisationDetail.setPaymentId(paymentId);
    realisationDetail.setRealisationAmount(receivedAmount);
    realisationDetail.setRealisationDate(realisationDate);
    if (receivedAmount < appropriatedAmount) {
      double shortLimit = appropriatedAmount - receivedAmount;
      throw new ShortExceedsLimitException(
          "Received Amount is less than Allocated Amount by Rs." + 
          shortLimit);
    } 
    if (receivedAmount > appropriatedAmount) {
      double excessLimit = receivedAmount - appropriatedAmount;
      throw new ShortExceedsLimitException(
          "Received Amount is greater than Allocated Amount by Rs." + 
          excessLimit);
    } 
    double shortOrExcess = rpProcessor.appropriatePayment(demandAdvices, 
        realisationDetail, request.getSession(false)
        .getServletContext().getRealPath(""));
    request.setAttribute("message", 
        "Payment Amount Appropriated Successfully.<BR><BR>Total Received Amount : " + 
        receivedAmount + "<BR>Total Appropriated Amount : " + 
        appropriatedAmount + "<BR>Short / Excess : " + 
        shortOrExcess);
    return mapping.findForward("success");
  }
  
  public ActionForward appropriatePaymentsForASF(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList<DemandAdvice> demandAdvices = new ArrayList();
    String methodName = "appropriatePayments";
    String danId = "";
    String cgpan = "";
    String allocatedFlag = "";
    String appropriatedFlag = "";
    String remark = "";
    String paymentId = "";
    DemandAdvice demandAdvice = null;
    Log.log(4, "RPAction", methodName, "Entered");
    Map danIds = actionForm.getDanIds();
    Map cgpans = actionForm.getCgpans();
    Map remarks = actionForm.getRemarks();
    Map allocatedFlags = actionForm.getAllocatedFlags();
    Map appropriatedFlags = actionForm.getAppropriatedFlags();
    Map amounts = actionForm.getAmountsRaised();
    Map penalties = actionForm.getPenalties();
    Map bankIds = actionForm.getBankIds();
    Map zoneIds = actionForm.getZoneIds();
    Map branchIds = actionForm.getBranchIds();
    Log.log(4, "RPAction", methodName, "Assigned CGPAN details to hashmap");
    Set danIdSet = danIds.keySet();
    Set allocatedFlagSet = allocatedFlags.keySet();
    Set appropriatedFlagSet = appropriatedFlags.keySet();
    Set cgpanSet = cgpans.keySet();
    Set remarksSet = remarks.keySet();
    Set amountsSet = amounts.keySet();
    Set penaltySet = penalties.keySet();
    Set bankIdSet = bankIds.keySet();
    Set zoneIdSet = zoneIds.keySet();
    Set branchIdSet = branchIds.keySet();
    Log.log(4, "RPAction", methodName, "Assigned CGPAN details to Set");
    Iterator<String> cgpanIterator = cgpanSet.iterator();
    Iterator bankIdIterator = bankIdSet.iterator();
    Iterator zoneIdIterator = zoneIdSet.iterator();
    Iterator branchIdIterator = branchIdSet.iterator();
    Log.log(4, "RPAction", methodName, "Assigned CGPAN details to Iterator");
    User user = getUserInformation(request);
    String userId = user.getUserId();
    paymentId = actionForm.getPaymentId();
    double appropriatedAmount = 0.0D;
    while (cgpanIterator.hasNext()) {
      String cgpanKey = cgpanIterator.next();
      danId = (String)danIds.get(cgpanKey);
      cgpan = (String)cgpans.get(cgpanKey);
      allocatedFlag = (String)allocatedFlags.get(cgpanKey);
      appropriatedFlag = (String)appropriatedFlags.get(cgpanKey);
      remark = (String)remarks.get(cgpanKey);
      double amount = Double.parseDouble((String)amounts.get(cgpanKey));
      double penalty = Double.parseDouble((String)penalties
          .get(cgpanKey));
      Log.log(4, "RPAction", methodName, " inside iterator - dan id - " + 
          danId);
      Log.log(4, "RPAction", methodName, " inside iterator - cgpan - " + 
          cgpan);
      Log.log(4, "RPAction", methodName, 
          " inside iterator - allocated flag - " + allocatedFlag);
      Log.log(4, "RPAction", methodName, 
          " inside iterator - appropriated flag - " + 
          appropriatedFlag);
      Log.log(4, "RPAction", methodName, " inside iterator - amount - " + 
          amount);
      Log.log(4, "RPAction", methodName, " inside iterator - penalty - " + 
          penalty);
      demandAdvice = new DemandAdvice();
      demandAdvice.setDanNo(danId);
      demandAdvice.setCgpan(cgpan);
      demandAdvice.setReason(remark);
      demandAdvice.setAmountRaised(amount);
      demandAdvice.setPenalty(penalty);
      demandAdvice.setPaymentId(paymentId);
      demandAdvice.setAllocated(appropriatedFlag);
      demandAdvice.setAppropriated(appropriatedFlag);
      demandAdvice.setUserId(userId);
      demandAdvice.setBankId((String)bankIds.get(bankIdIterator.next()));
      demandAdvice.setZoneId((String)zoneIds.get(zoneIdIterator.next()));
      demandAdvice.setBranchId((String)branchIds.get(branchIdIterator
            .next()));
      Log.log(4, "RPAction", methodName, " inside iterator - cgpan - " + 
          demandAdvice.getCgpan());
      Log.log(4, 
          "RPAction", 
          methodName, 
          " inside iterator - allocated flag - " + 
          demandAdvice.getAllocated());
      Log.log(4, 
          "RPAction", 
          methodName, 
          " inside iterator - appropriated flag - " + 
          demandAdvice.getAppropriated());
      if (appropriatedFlag.equals("Y"))
        appropriatedAmount += amount; 
      demandAdvices.add(demandAdvice);
      Log.log(4, "RPAction", methodName, 
          " inside iterator - adding cgpan to demand advice list - " + 
          cgpan);
      Log.log(4, "RPAction", methodName, 
          "DemandAdvices added to ArrayList");
    } 
    Date realisationDate = actionForm.getDateOfRealisation();
    double receivedAmount = actionForm.getReceivedAmount();
    RealisationDetail realisationDetail = new RealisationDetail();
    realisationDetail.setPaymentId(paymentId);
    realisationDetail.setRealisationAmount(receivedAmount);
    realisationDetail.setRealisationDate(realisationDate);
    if (receivedAmount < appropriatedAmount) {
      double shortLimit = appropriatedAmount - receivedAmount;
      throw new ShortExceedsLimitException(
          "Received Amount is less than Allocated Amount by Rs." + 
          shortLimit);
    } 
    if (receivedAmount > appropriatedAmount) {
      double excessLimit = receivedAmount - appropriatedAmount;
      throw new ShortExceedsLimitException(
          "Received Amount is greater than Allocated Amount by Rs." + 
          excessLimit);
    } 
    double shortOrExcess = rpProcessor.appropriatePayment(demandAdvices, 
        realisationDetail, request.getSession(false)
        .getServletContext().getRealPath(""));
    request.setAttribute("message", 
        "Payment Amount Appropriated Successfully.<BR><BR>Total Received Amount : " + 
        receivedAmount + "<BR>Total Appropriated Amount : " + 
        appropriatedAmount + "<BR>Short / Excess : " + 
        shortOrExcess);
    return mapping.findForward("success");
  }
  
  public ActionForward generateCGDAN(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    DynaActionForm dynaForm = (DynaActionForm)form;
    String mliName = (String)dynaForm.get("selectMember");
    java.sql.Date startDate = null;
    java.sql.Date endDate = null;
    Date sDate = (Date)dynaForm.get("fromdt");
    Date eDate = (Date)dynaForm.get("todt");
    String stDate = String.valueOf(sDate);
    String estDate = String.valueOf(eDate);
    if (stDate == null || stDate.equals("")) {
      startDate = null;
    } else {
      startDate = new java.sql.Date(sDate.getTime());
    } 
    if (estDate == null || estDate.equals("")) {
      endDate = null;
    } else {
      endDate = new java.sql.Date(eDate.getTime());
    } 
    Log.log(4, "RPAction", "generateCGDAN", "Selected Member Id : " + 
        mliName);
    String forwardPage = "";
    User user = getUserInformation(request);
    Log.log(4, "RPAction", "generateCGDAN", 
        "Logged in user: " + user.getUserId());
    if (mliName.equals("")) {
      Log.log(4, "RPAction", "generateCGDAN", 
          "Fetching Member Details for whom CGDAN has to be generated");
      ArrayList<MLIInfo> memberNames = this.registration.getAllMembers();
      ArrayList<String> memberDetails = new ArrayList(memberNames.size());
      for (int i = 0; i < memberNames.size(); i++) {
        MLIInfo mliInfo = memberNames.get(i);
        String mli = "";
        mli = "(" + mliInfo.getBankId() + mliInfo.getZoneId() + 
          mliInfo.getBranchId() + ")" + mliInfo.getShortName();
        if (mliInfo.getBranchName() != null && 
          !mliInfo.getBranchName().equals("") && (
          mliInfo.getZoneName() == null || mliInfo
          .getZoneName().equals(""))) {
          mli = String.valueOf(mli) + "," + mliInfo.getBranchName();
        } else if ((mliInfo.getBranchName() == null || mliInfo
          .getBranchName().equals("")) && 
          mliInfo.getZoneName() != null && 
          !mliInfo.getZoneName().equals("")) {
          mli = String.valueOf(mli) + "," + mliInfo.getZoneName();
        } else if (mliInfo.getBranchName() != null && 
          !mliInfo.getBranchName().equals("") && 
          mliInfo.getZoneName() != null && 
          !mliInfo.getZoneName().equals("")) {
          mli = String.valueOf(mli) + "," + mliInfo.getZoneName() + "," + 
            mliInfo.getBranchName();
        } 
        memberDetails.add(mli);
      } 
      Log.log(4, "RPAction", "generateCGDAN", 
          "Fetched Member Details for whom CGDAN has to be generated");
      dynaForm.set("mliNames", memberDetails);
      request.setAttribute("TARGET_URL", 
          MenuOptions.getMenuAction("RP_GENERATE_CGDAN"));
      HttpSession session = request.getSession(false);
      session.setAttribute("DAN_TYPE", "CGDAN");
      forwardPage = "memberInfo";
    } else {
      if (!mliName.equals("All"))
        mliName = mliName.substring(1, 13); 
      Log.log(4, "RPAction", "generateCGDAN", "mli name " + mliName);
      RpProcessor rpProcessor = new RpProcessor();
      try {
        String message = "";
        if ((startDate == null || startDate.equals("")) && (
          endDate == null || endDate.equals(""))) {
          rpProcessor.generateCGDAN(user, mliName);
        } else {
          rpProcessor
            .generateCGDAN(user, mliName, startDate, endDate);
        } 
        message = "CGDAN generated Successfully";
        request.setAttribute("message", message);
        forwardPage = "success";
      } catch (Exception exp) {
        forwardPage = "success1";
        request.setAttribute("message", 
            "No Applications Available For CGDAN Generation");
      } 
    } 
    return mapping.findForward(forwardPage);
  }
  
  public ActionForward generateSFDAN(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String methodName = "generateSFDAN";
    Log.log(4, "RPAction", methodName, "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    String mliName = (String)dynaForm.get("selectMember");
    Log.log(4, "RPAction", methodName, "Selected Member Id : " + mliName);
    String forwardPage = "";
    User user = getUserInformation(request);
    Log.log(4, "RPAction", "generateCGDAN", 
        "Logged in user: " + user.getUserId());
    if (mliName.equals("")) {
      Log.log(5, "RPAction", methodName, 
          "Fetching Member Details for whom SFDAN has to be generated");
      ArrayList<MLIInfo> memberNames = this.registration.getAllMembers();
      ArrayList<String> memberDetails = new ArrayList(memberNames.size());
      for (int i = 0; i < memberNames.size(); i++) {
        MLIInfo mliInfo = memberNames.get(i);
        String mli = "";
        mli = "(" + mliInfo.getBankId() + mliInfo.getZoneId() + 
          mliInfo.getBranchId() + ")" + mliInfo.getShortName();
        if (mliInfo.getBranchName() != null && 
          !mliInfo.getBranchName().equals("") && (
          mliInfo.getZoneName() == null || mliInfo
          .getZoneName().equals(""))) {
          mli = String.valueOf(mli) + "," + mliInfo.getBranchName();
        } else if ((mliInfo.getBranchName() == null || mliInfo
          .getBranchName().equals("")) && 
          mliInfo.getZoneName() != null && 
          !mliInfo.getZoneName().equals("")) {
          mli = String.valueOf(mli) + "," + mliInfo.getZoneName();
        } else if (mliInfo.getBranchName() != null && 
          !mliInfo.getBranchName().equals("") && 
          mliInfo.getZoneName() != null && 
          !mliInfo.getZoneName().equals("")) {
          mli = String.valueOf(mli) + "," + mliInfo.getZoneName() + "," + 
            mliInfo.getBranchName();
        } 
        memberDetails.add(mli);
      } 
      Log.log(5, "RPAction", methodName, 
          "Fetched Member Details for whom SFDAN has to be generated");
      dynaForm.set("mliNames", memberDetails);
      request.setAttribute("TARGET_URL", 
          MenuOptions.getMenuAction("RP_GENERATE_SFDAN"));
      HttpSession session = request.getSession(false);
      session.setAttribute("DAN_TYPE", "SFDAN");
      forwardPage = "memberInfo";
    } else {
      forwardPage = "";
      String message = "";
      try {
        Log.log(5, "RPAction", methodName, 
            "Entering routine to generate SFDAN for all members");
        RpProcessor rpProcessor = new RpProcessor();
        if (mliName.equalsIgnoreCase("All")) {
          rpProcessor.generateSFDAN(user, null, null, null);
        } else {
          mliName = mliName.substring(1, 13);
          rpProcessor.generateSFDAN(user, mliName.substring(0, 4), 
              mliName.substring(4, 8), mliName.substring(8, 12));
        } 
        request.setAttribute("message", "SFDAN generated successfully");
        forwardPage = "success";
      } catch (Exception exp) {
        forwardPage = "success1";
        request.setAttribute("message", 
            " No Applications Available For SFDAN Generation");
      } 
    } 
    Log.log(4, "RPAction", methodName, "Exited");
    return mapping.findForward(forwardPage);
  }
  
  public ActionForward generateSFDANEXP(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String methodName = "generateSFDANEXP";
    Log.log(4, "RPAction", methodName, "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    String mliName = (String)dynaForm.get("selectMember");
    Log.log(4, "RPAction", methodName, "Selected Member Id : " + mliName);
    String forwardPage = "";
    User user = getUserInformation(request);
    Log.log(4, "RPAction", "generateCGDAN", 
        "Logged in user: " + user.getUserId());
    if (mliName.equals("")) {
      Log.log(5, "RPAction", methodName, 
          "Fetching Member Details for whom SFDANEXP has to be generated");
      ArrayList<MLIInfo> memberNames = this.registration.getAllMembers();
      ArrayList<String> memberDetails = new ArrayList(memberNames.size());
      for (int i = 0; i < memberNames.size(); i++) {
        MLIInfo mliInfo = memberNames.get(i);
        String mli = "";
        mli = "(" + mliInfo.getBankId() + mliInfo.getZoneId() + 
          mliInfo.getBranchId() + ")" + mliInfo.getShortName();
        if (mliInfo.getBranchName() != null && 
          !mliInfo.getBranchName().equals("") && (
          mliInfo.getZoneName() == null || mliInfo
          .getZoneName().equals(""))) {
          mli = String.valueOf(mli) + "," + mliInfo.getBranchName();
        } else if ((mliInfo.getBranchName() == null || mliInfo
          .getBranchName().equals("")) && 
          mliInfo.getZoneName() != null && 
          !mliInfo.getZoneName().equals("")) {
          mli = String.valueOf(mli) + "," + mliInfo.getZoneName();
        } else if (mliInfo.getBranchName() != null && 
          !mliInfo.getBranchName().equals("") && 
          mliInfo.getZoneName() != null && 
          !mliInfo.getZoneName().equals("")) {
          mli = String.valueOf(mli) + "," + mliInfo.getZoneName() + "," + 
            mliInfo.getBranchName();
        } 
        memberDetails.add(mli);
      } 
      Log.log(5, "RPAction", methodName, 
          "Fetched Member Details for whom SFDANEXP has to be generated");
      dynaForm.set("mliNames", memberDetails);
      request.setAttribute("TARGET_URL", 
          MenuOptions.getMenuAction("RP_GENERATE_SFDAN"));
      HttpSession session = request.getSession(false);
      session.setAttribute("DAN_TYPE", "SFDAN");
      forwardPage = "memberInfo";
    } else {
      forwardPage = "";
      String message = "";
      try {
        Log.log(5, "RPAction", methodName, 
            "Entering routine to generate SFDANEXP for all members");
        RpProcessor rpProcessor = new RpProcessor();
        if (mliName.equalsIgnoreCase("All")) {
          rpProcessor.generateSFDAN(user, null, null, null);
        } else {
          mliName = mliName.substring(1, 13);
          rpProcessor.generateSFDANEXP(user, mliName.substring(0, 4), 
              mliName.substring(4, 8), mliName.substring(8, 12));
        } 
        request.setAttribute("message", 
            "SFDAN for Expired Cases generated successfully");
        forwardPage = "success";
      } catch (Exception exp) {
        forwardPage = "success1";
        request.setAttribute("message", 
            " No Applications Available For SFDAN for Expired Cases Generation");
      } 
    } 
    Log.log(4, "RPAction", methodName, "Exited");
    return mapping.findForward(forwardPage);
  }
  
  public ActionForward generateSHDAN(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String methodName = "generateSHDAN";
    Log.log(4, "RPAction", methodName, "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    String mliName = (String)dynaForm.get("selectMember");
    Log.log(5, "RPAction", methodName, "Selected Member Id : " + mliName);
    String forwardPage = "";
    User user = getUserInformation(request);
    Log.log(5, "RPAction", methodName, 
        "Logged in user: " + user.getUserId());
    RpProcessor rpProcessor = new RpProcessor();
    if (mliName.equals("")) {
      Log.log(5, "RPAction", methodName, 
          "Fetching Member Details for whom CGDAN has to be generated");
      ArrayList<String> memberIds = rpProcessor.getMemberIdsForSHDAN();
      if (memberIds != null && memberIds.size() != 0) {
        ArrayList<String> memberDetails = new ArrayList(memberIds.size());
        for (int i = 0; i < memberIds.size(); i++) {
          String memberId = memberIds.get(i);
          MLIInfo mliInfo = this.registration.getMemberDetails(
              memberId.substring(0, 4), memberId.substring(4, 8), 
              memberId.substring(8, 12));
          String mli = "";
          mli = "(" + memberId.substring(0, 4) + 
            memberId.substring(4, 8) + 
            memberId.substring(8, 12) + ")" + 
            mliInfo.getShortName();
          if (mliInfo.getBranchName() != null && 
            !mliInfo.getBranchName().equals("") && (
            mliInfo.getZoneName() == null || mliInfo
            .getZoneName().equals(""))) {
            mli = String.valueOf(mli) + "," + mliInfo.getBranchName();
          } else if ((mliInfo.getBranchName() == null || mliInfo
            .getBranchName().equals("")) && 
            mliInfo.getZoneName() != null && 
            !mliInfo.getZoneName().equals("")) {
            mli = String.valueOf(mli) + "," + mliInfo.getZoneName();
          } else if (mliInfo.getBranchName() != null && 
            !mliInfo.getBranchName().equals("") && 
            mliInfo.getZoneName() != null && 
            !mliInfo.getZoneName().equals("")) {
            mli = String.valueOf(mli) + "," + mliInfo.getZoneName() + "," + 
              mliInfo.getBranchName();
          } 
          memberDetails.add(mli);
        } 
        dynaForm.set("mliNames", memberDetails);
        request.setAttribute("TARGET_URL", 
            MenuOptions.getMenuAction("RP_GENERATE_DAN_FOR_SHORT"));
        HttpSession session = request.getSession(false);
        session.setAttribute("DAN_TYPE", "SHDAN");
        forwardPage = "memberInfo";
      } else {
        request.setAttribute("message", 
            "No Members available for SHDAN Generation");
        forwardPage = "success";
      } 
    } else {
      Log.log(5, "RPAction", methodName, 
          "Entering routine to generate CGDAN for all members");
      rpProcessor.generateSHDAN(user, mliName);
      String message = "Short DAN generated successfully";
      request.setAttribute("message", message);
      forwardPage = "success";
    } 
    Log.log(5, "RPAction", methodName, "Exited");
    return mapping.findForward(forwardPage);
  }
  
  public ActionForward generateCLDAN(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String methodName = "generateCLDAN";
    Log.log(4, "RPAction", methodName, "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    String mliName = (String)dynaForm.get("selectMember");
    Log.log(4, "RPAction", methodName, "Selected Member Id : " + mliName);
    String forwardPage = "";
    User user = getUserInformation(request);
    Log.log(4, "RPAction", "generateCLDAN", 
        "Logged in user: " + user.getUserId());
    if (mliName.equals("")) {
      Log.log(5, "RPAction", methodName, 
          "Fetching Member Details for whom CLDAN has to be generated");
      ArrayList<MLIInfo> memberNames = this.registration.getAllMembers();
      ArrayList<String> memberDetails = new ArrayList(memberNames.size());
      for (int i = 0; i < memberNames.size(); i++) {
        MLIInfo mliInfo = memberNames.get(i);
        String mli = "";
        mli = "(" + mliInfo.getBankId() + mliInfo.getZoneId() + 
          mliInfo.getBranchId() + ")" + mliInfo.getShortName();
        if (mliInfo.getBranchName() != null && 
          !mliInfo.getBranchName().equals("") && (
          mliInfo.getZoneName() == null || mliInfo
          .getZoneName().equals(""))) {
          mli = String.valueOf(mli) + "," + mliInfo.getBranchName();
        } else if ((mliInfo.getBranchName() == null || mliInfo
          .getBranchName().equals("")) && 
          mliInfo.getZoneName() != null && 
          !mliInfo.getZoneName().equals("")) {
          mli = String.valueOf(mli) + "," + mliInfo.getZoneName();
        } else if (mliInfo.getBranchName() != null && 
          !mliInfo.getBranchName().equals("") && 
          mliInfo.getZoneName() != null && 
          !mliInfo.getZoneName().equals("")) {
          mli = String.valueOf(mli) + "," + mliInfo.getZoneName() + "," + 
            mliInfo.getBranchName();
        } 
        memberDetails.add(mli);
      } 
      Log.log(5, "RPAction", methodName, 
          "Fetched Member Details for whom CLDAN has to be generated");
      dynaForm.set("mliNames", memberDetails);
      request.setAttribute("TARGET_URL", 
          MenuOptions.getMenuAction("RP_GENERATE_CLDAN"));
      HttpSession session = request.getSession(false);
      session.setAttribute("DAN_TYPE", "CLDAN");
      forwardPage = "memberInfo";
    } else {
      forwardPage = "";
      String message = "";
      try {
        Log.log(5, "RPAction", methodName, 
            "Entering routine to generate CGDAN for all members");
        RpProcessor rpProcessor = new RpProcessor();
        if (mliName.equalsIgnoreCase("All")) {
          String bankId = null;
          String zoneId = null;
          String branchId = null;
          rpProcessor.generateCLDAN(user, bankId, zoneId, branchId);
        } else {
          mliName = mliName.substring(1, 13);
          rpProcessor.generateCLDAN(user, mliName.substring(0, 4), 
              mliName.substring(4, 8), mliName.substring(8, 12));
        } 
        request.setAttribute("message", "CLDAN generated successfully");
        forwardPage = "success";
      } catch (Exception exp) {
        forwardPage = "success1";
        request.setAttribute("message", 
            " No Applications Available For CLDAN Generation");
      } 
    } 
    Log.log(4, "RPAction", methodName, "Exited");
    return mapping.findForward(forwardPage);
  }
  
  public ActionForward displayPPMLIWiseFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    User user = getUserInformation(request);
    String bankId = user.getBankId();
    RPActionForm rpActionForm = (RPActionForm)form;
    RpProcessor processor = new RpProcessor();
    String fwdPage = "";
    if (bankId.equalsIgnoreCase("0000")) {
      rpActionForm.setMemberId("");
      fwdPage = "displayFilter";
    } else {
      rpActionForm.setMemberId(String.valueOf(user.getBankId()) + user.getZoneId() + 
          user.getBranchId());
      HashMap details = processor.getMLIWiseDANDetails(String.valueOf(user.getBankId()) + 
          user.getZoneId() + user.getBranchId());
      Vector mliWiseDanDetails = (Vector)details.get("pending_dtls");
      rpActionForm.setMliWiseDanDetails(mliWiseDanDetails);
      fwdPage = "getDetails";
    } 
    return mapping.findForward(fwdPage);
  }
  
  public ActionForward displayPPDateWiseFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm rpActionForm = (RPActionForm)form;
    Date date = new Date();
    Calendar calendar = Calendar.getInstance();
    calendar.setTime(date);
    int month = calendar.get(2);
    int day = calendar.get(5);
    month--;
    day++;
    calendar.set(2, month);
    calendar.set(5, day);
    Date prevDate = calendar.getTime();
    rpActionForm.setFromDate(prevDate);
    rpActionForm.setToDate(date);
    return mapping.findForward("displayFilter");
  }
  
  public ActionForward getPPMLIWiseDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "getPPMLIWiseDetails", "Entered");
    RpProcessor processor = new RpProcessor();
    RPActionForm rpActionForm = (RPActionForm)form;
    String memberId = rpActionForm.getMemberId().trim();
    ClaimsProcessor cpProcessor = new ClaimsProcessor();
    Vector memberIds = cpProcessor.getAllMemberIds();
    if (!memberIds.contains(memberId))
      throw new NoMemberFoundException("The Member ID does not exist"); 
    String bankId = memberId.substring(0, 4);
    String zoneId = memberId.substring(4, 8);
    String branchId = memberId.substring(8, 12);
    MLIInfo mliInfo = this.registration.getMemberDetails(bankId, zoneId, 
        branchId);
    if (mliInfo != null) {
      Log.log(5, "ApplicationProcessingAction", "getApps", "mli Info.. :" + 
          mliInfo);
      String statusFlag = mliInfo.getStatus();
      if (statusFlag.equals("I"))
        throw new NoDataException("Member Id:" + memberId + 
            "  has been deactivated."); 
    } 
    HashMap details = processor.getMLIWiseDANDetails(memberId);
    Vector mliWiseDanDetails = (Vector)details.get("pending_dtls");
    rpActionForm.setMliWiseDanDetails(mliWiseDanDetails);
    Log.log(4, "RPAction", "getPPMLIWiseDetails", "Exited");
    return mapping.findForward("getDetails");
  }
  
  public ActionForward getPPDateWiseDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "getPPDateWiseDetails", "Entered");
    RpProcessor processor = new RpProcessor();
    RPActionForm rpActionForm = (RPActionForm)form;
    Vector dateWiseDANDetails = null;
    Date fromDate = rpActionForm.getFromDate();
    Date toDate = rpActionForm.getToDate();
    if (toDate.toString().equals(""))
      toDate = new Date(System.currentTimeMillis()); 
    java.sql.Date sqlFromDate = null;
    if (fromDate.toString().equals("")) {
      dateWiseDANDetails = processor.getDateWiseDANDetails(null, 
          new java.sql.Date(toDate.getTime()));
    } else {
      sqlFromDate = new java.sql.Date(fromDate.getTime());
      dateWiseDANDetails = processor.getDateWiseDANDetails(sqlFromDate, 
          new java.sql.Date(toDate.getTime()));
    } 
    rpActionForm.setDateWiseDANDetails(dateWiseDANDetails);
    Log.log(4, "RPAction", "getPPDateWiseDetails", "Exited");
    return mapping.findForward("getDetails");
  }
  
  public ActionForward afterMemberInfo(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm rpAllocationForm = (RPActionForm)form;
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList demandAdviceList = new ArrayList();
    String forward = "";
    String mliId = rpAllocationForm.getSelectMember();
    String bankId = mliId.substring(0, 4);
    String zoneId = mliId.substring(4, 8);
    String branchId = mliId.substring(8, 12);
    ClaimsProcessor cpProcessor = new ClaimsProcessor();
    Vector memberIds = cpProcessor.getAllMemberIds();
    if (!memberIds.contains(mliId))
      throw new NoMemberFoundException("The Member ID does not exist"); 
    demandAdviceList = rpProcessor.showShortDansForWaive(bankId, zoneId, 
        branchId);
    if (demandAdviceList == null || demandAdviceList.size() == 0) {
      request.setAttribute("message", 
          "No Applications For Waive Short DAN Amounts");
      forward = "success";
    } else {
      rpAllocationForm.setPaymentDetails(demandAdviceList);
      forward = "waiveShortAmntsDisplay";
    } 
    return mapping.findForward(forward);
  }
  
  public ActionForward waiveShortDans(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(5, "RPAction", "waiveShortDANs", "entered");
    RPActionForm rpAllocationForm = (RPActionForm)form;
    RpProcessor rpProcessor = new RpProcessor();
    Map danNoMap = rpAllocationForm.getDanIds();
    Map waivedFlagMap = rpAllocationForm.getWaivedFlags();
    Set waivedFlagSet = waivedFlagMap.keySet();
    Iterator<String> waivedFlagIterator = waivedFlagSet.iterator();
    while (waivedFlagIterator.hasNext()) {
      String key = waivedFlagIterator.next();
      Log.log(5, "RPAction", "waiveShortDANs", "key :" + key);
      String shdan = (String)danNoMap.get(key);
      Log.log(5, "RPAction", "waiveShortDANs", "key :" + shdan);
      rpProcessor.waiveShortDANs(key.replace('_', '.'));
    } 
    request.setAttribute("message", "Short DANs Waived Sucessfully.");
    return mapping.findForward("success");
  }
  
  public ActionForward afterShowMliForRefAdv(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm rpAllocationForm = (RPActionForm)form;
    RpProcessor rpProcessor = new RpProcessor();
    String mliId = rpAllocationForm.getSelectMember();
    ClaimsProcessor cpProcessor = new ClaimsProcessor();
    Vector memberIds = cpProcessor.getAllMemberIds();
    if (!memberIds.contains(mliId))
      throw new NoMemberFoundException("The Member ID does not exist"); 
    double refAmount = rpProcessor.getRefundAmountForMember(mliId);
    rpAllocationForm.setRefundAmount(refAmount);
    return mapping.findForward("success");
  }
  
  public ActionForward generateRefAdv(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm rpAllocationForm = (RPActionForm)form;
    RpProcessor rpProcessor = new RpProcessor();
    String mliId = rpAllocationForm.getSelectMember();
    User user = getUserInformation(request);
    String userId = user.getUserId();
    String refAdvNumber = rpProcessor.generateRefundAdvice(mliId, userId);
    String message = "Refund Advice Generated. Refund Advice Number: " + 
      refAdvNumber;
    request.setAttribute("message", message);
    return mapping.findForward("success");
  }
  
  public ActionForward getPaymentList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm rpAllocationForm = (RPActionForm)form;
    RpProcessor rpProcessor = new RpProcessor();
    Date fromDate = rpAllocationForm.getFromDate();
    Date toDate = rpAllocationForm.getToDate();
    Log.log(4, "RPAction", "getPaymentList", " from date " + fromDate);
    Log.log(4, "RPAction", "getPaymentList", " to date " + toDate);
    String dateType = rpAllocationForm.getDateType();
    User user = getUserInformation(request);
    String memberId = "";
    if (user.getBankId().equals("0000")) {
      memberId = rpAllocationForm.getSelectMember();
      ClaimsProcessor cpProcessor = new ClaimsProcessor();
      Vector memberIds = cpProcessor.getAllMemberIds();
      if (!memberIds.contains(memberId))
        throw new NoMemberFoundException("Member Id does not exist"); 
    } else {
      memberId = rpAllocationForm.getSelectMember();
      Log.log(4, "RPAction", "getPaymentList", " member id " + memberId);
    } 
    ArrayList paymentIds = rpProcessor.getPaymentList(fromDate, toDate, 
        dateType, memberId);
    rpAllocationForm.setPaymentList(paymentIds);
    return mapping.findForward("success");
  }
  
  public ActionForward showCGDANGenFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    DynaActionForm rpAllocationForm = (DynaActionForm)form;
    rpAllocationForm.initialize(mapping);
    ArrayList<MLIInfo> memberNames = this.registration.getAllMembers();
    ArrayList<String> memberDetails = new ArrayList(memberNames.size());
    for (int i = 0; i < memberNames.size(); i++) {
      MLIInfo mliInfo = memberNames.get(i);
      String mli = "";
      mli = "(" + mliInfo.getBankId() + mliInfo.getZoneId() + 
        mliInfo.getBranchId() + ")" + mliInfo.getShortName();
      if (mliInfo.getBranchName() != null && 
        !mliInfo.getBranchName().equals("") && (
        mliInfo.getZoneName() == null || mliInfo
        .getZoneName().equals(""))) {
        mli = String.valueOf(mli) + "," + mliInfo.getBranchName();
      } else if ((mliInfo.getBranchName() == null || mliInfo
        .getBranchName().equals("")) && 
        mliInfo.getZoneName() != null && 
        !mliInfo.getZoneName().equals("")) {
        mli = String.valueOf(mli) + "," + mliInfo.getZoneName();
      } else if (mliInfo.getBranchName() != null && 
        !mliInfo.getBranchName().equals("") && 
        mliInfo.getZoneName() != null && 
        !mliInfo.getZoneName().equals("")) {
        mli = String.valueOf(mli) + "," + mliInfo.getZoneName() + "," + 
          mliInfo.getBranchName();
      } 
      memberDetails.add(mli);
    } 
    Log.log(4, "RPAction", "generateCGDAN", 
        "Fetched Member Details for whom CGDAN has to be generated");
    rpAllocationForm.set("mliNames", memberDetails);
    HttpSession session = request.getSession(false);
    session.setAttribute("TARGET_URL", 
        "generateCGDAN.do?method=generateCGDAN");
    session.setAttribute("DAN_TYPE", "CGDAN");
    return mapping.findForward("memberInfo");
  }
  
  public ActionForward showSFDANGenFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    DynaActionForm rpAllocationForm = (DynaActionForm)form;
    rpAllocationForm.initialize(mapping);
    ArrayList<MLIInfo> memberNames = this.registration.getAllMembers();
    ArrayList<String> memberDetails = new ArrayList(memberNames.size());
    for (int i = 0; i < memberNames.size(); i++) {
      MLIInfo mliInfo = memberNames.get(i);
      String mli = "";
      mli = "(" + mliInfo.getBankId() + mliInfo.getZoneId() + 
        mliInfo.getBranchId() + ")" + mliInfo.getShortName();
      if (mliInfo.getBranchName() != null && 
        !mliInfo.getBranchName().equals("") && (
        mliInfo.getZoneName() == null || mliInfo
        .getZoneName().equals(""))) {
        mli = String.valueOf(mli) + "," + mliInfo.getBranchName();
      } else if ((mliInfo.getBranchName() == null || mliInfo
        .getBranchName().equals("")) && 
        mliInfo.getZoneName() != null && 
        !mliInfo.getZoneName().equals("")) {
        mli = String.valueOf(mli) + "," + mliInfo.getZoneName();
      } else if (mliInfo.getBranchName() != null && 
        !mliInfo.getBranchName().equals("") && 
        mliInfo.getZoneName() != null && 
        !mliInfo.getZoneName().equals("")) {
        mli = String.valueOf(mli) + "," + mliInfo.getZoneName() + "," + 
          mliInfo.getBranchName();
      } 
      memberDetails.add(mli);
    } 
    Log.log(4, "RPAction", "generateCGDAN", 
        "Fetched Member Details for whom CGDAN has to be generated");
    rpAllocationForm.set("mliNames", memberDetails);
    HttpSession session = request.getSession(false);
    session.setAttribute("TARGET_URL", 
        "generateSFDAN.do?method=generateSFDAN");
    session.setAttribute("DAN_TYPE", "SFDAN");
    return mapping.findForward("memberInfo");
  }
  
  public ActionForward showBatchSFDANGenFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    DynaActionForm rpAllocationForm = (DynaActionForm)form;
    rpAllocationForm.initialize(mapping);
    ArrayList<MLIInfo> memberNames = this.registration.getAllHOMembers();
    ArrayList<String> memberDetails = new ArrayList(memberNames.size());
    for (int i = 0; i < memberNames.size(); i++) {
      MLIInfo mliInfo = memberNames.get(i);
      String mli = "";
      mli = "(" + mliInfo.getBankId() + mliInfo.getZoneId() + 
        mliInfo.getBranchId() + ")" + mliInfo.getBankName();
      if (mliInfo.getBranchName() != null && 
        !mliInfo.getBranchName().equals("") && (
        mliInfo.getZoneName() == null || mliInfo
        .getZoneName().equals(""))) {
        mli = String.valueOf(mli) + "," + mliInfo.getBranchName();
      } else if ((mliInfo.getBranchName() == null || mliInfo
        .getBranchName().equals("")) && 
        mliInfo.getZoneName() != null && 
        !mliInfo.getZoneName().equals("")) {
        mli = String.valueOf(mli) + "," + mliInfo.getZoneName();
      } else if (mliInfo.getBranchName() != null && 
        !mliInfo.getBranchName().equals("") && 
        mliInfo.getZoneName() != null && 
        !mliInfo.getZoneName().equals("")) {
        mli = String.valueOf(mli) + "," + mliInfo.getZoneName() + "," + 
          mliInfo.getBranchName();
      } 
      memberDetails.add(mli);
    } 
    Log.log(4, "RPAction", "showBatchSFDANGenFilter", 
        "Fetched Member Details for whom CGDAN has to be generated");
    rpAllocationForm.set("mliNames", memberDetails);
    HttpSession session = request.getSession(false);
    session.setAttribute("TARGET_URL", 
        "generateBatchSFDAN.do?method=generateBatchSFDAN");
    session.setAttribute("DAN_TYPE", "BATCHSFDAN");
    return mapping.findForward("memberInfo");
  }
  
  public ActionForward generateBatchSFDAN(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String methodName = "generateBatchSFDAN";
    Log.log(4, "RPAction", methodName, "Entered");
    DynaActionForm dynaForm = (DynaActionForm)form;
    String mliName = (String)dynaForm.get("selectMember");
    Log.log(4, "RPAction", methodName, "Selected Member Id : " + mliName);
    String forwardPage = "";
    User user = getUserInformation(request);
    Log.log(4, "RPAction", "generateBatchSFDAN", 
        "Logged in user: " + user.getUserId());
    if (mliName.equals("")) {
      Log.log(5, "RPAction", methodName, 
          "Fetching Member Details for whom SFDAN has to be generated");
      ArrayList<MLIInfo> memberNames = this.registration.getAllMembers();
      ArrayList<String> memberDetails = new ArrayList(memberNames.size());
      for (int i = 0; i < memberNames.size(); i++) {
        MLIInfo mliInfo = memberNames.get(i);
        String mli = "";
        mli = "(" + mliInfo.getBankId() + mliInfo.getZoneId() + 
          mliInfo.getBranchId() + ")" + mliInfo.getShortName();
        if (mliInfo.getBranchName() != null && 
          !mliInfo.getBranchName().equals("") && (
          mliInfo.getZoneName() == null || mliInfo
          .getZoneName().equals(""))) {
          mli = String.valueOf(mli) + "," + mliInfo.getBranchName();
        } else if ((mliInfo.getBranchName() == null || mliInfo
          .getBranchName().equals("")) && 
          mliInfo.getZoneName() != null && 
          !mliInfo.getZoneName().equals("")) {
          mli = String.valueOf(mli) + "," + mliInfo.getZoneName();
        } else if (mliInfo.getBranchName() != null && 
          !mliInfo.getBranchName().equals("") && 
          mliInfo.getZoneName() != null && 
          !mliInfo.getZoneName().equals("")) {
          mli = String.valueOf(mli) + "," + mliInfo.getZoneName() + "," + 
            mliInfo.getBranchName();
        } 
        memberDetails.add(mli);
      } 
      Log.log(5, "RPAction", methodName, 
          "Fetched Member Details for whom SFDAN has to be generated");
      dynaForm.set("mliNames", memberDetails);
      request.setAttribute("TARGET_URL", 
          MenuOptions.getMenuAction("RP_GENERATE_BATCH_SFDAN"));
      HttpSession session = request.getSession(false);
      session.setAttribute("DAN_TYPE", "BATCHSFDAN");
      forwardPage = "memberInfo";
    } else {
      forwardPage = "";
      String message = "";
      try {
        Log.log(5, "RPAction", methodName, 
            "Entering routine to generate Batch SFDAN for all members");
        RpProcessor rpProcessor = new RpProcessor();
        if (mliName.equalsIgnoreCase("All")) {
          request.setAttribute("message", 
              " No Applications Available For SFDAN Generation");
        } else {
          mliName = mliName.substring(1, 13);
          System.out.println("MLI Name:" + mliName);
          rpProcessor.generateBatchSFDAN(user, 
              mliName.substring(0, 4));
          message = "Batch SFDAN generated successfully";
        } 
        request.setAttribute("message", 
            "Batch SFDAN generated successfully");
        forwardPage = "success";
      } catch (Exception exp) {
        forwardPage = "success1";
        request.setAttribute("message", 
            " No Applications Available For SFDAN Generation");
      } 
    } 
    Log.log(4, "RPAction", methodName, "Exited");
    return mapping.findForward(forwardPage);
  }
  
  public ActionForward showSFDANGenFilterForExpired(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    DynaActionForm rpAllocationForm = (DynaActionForm)form;
    rpAllocationForm.initialize(mapping);
    ArrayList<MLIInfo> memberNames = this.registration.getAllMembers();
    ArrayList<String> memberDetails = new ArrayList(memberNames.size());
    for (int i = 0; i < memberNames.size(); i++) {
      MLIInfo mliInfo = memberNames.get(i);
      String mli = "";
      mli = "(" + mliInfo.getBankId() + mliInfo.getZoneId() + 
        mliInfo.getBranchId() + ")" + mliInfo.getShortName();
      if (mliInfo.getBranchName() != null && 
        !mliInfo.getBranchName().equals("") && (
        mliInfo.getZoneName() == null || mliInfo
        .getZoneName().equals(""))) {
        mli = String.valueOf(mli) + "," + mliInfo.getBranchName();
      } else if ((mliInfo.getBranchName() == null || mliInfo
        .getBranchName().equals("")) && 
        mliInfo.getZoneName() != null && 
        !mliInfo.getZoneName().equals("")) {
        mli = String.valueOf(mli) + "," + mliInfo.getZoneName();
      } else if (mliInfo.getBranchName() != null && 
        !mliInfo.getBranchName().equals("") && 
        mliInfo.getZoneName() != null && 
        !mliInfo.getZoneName().equals("")) {
        mli = String.valueOf(mli) + "," + mliInfo.getZoneName() + "," + 
          mliInfo.getBranchName();
      } 
      memberDetails.add(mli);
    } 
    Log.log(4, 
        "RPAction", 
        "showSFDANGenFilterForExpired", 
        "Fetched Member Details for whom showSFDANGenFilterForExpired has to be generated");
    rpAllocationForm.set("mliNames", memberDetails);
    HttpSession session = request.getSession(false);
    session.setAttribute("TARGET_URL", 
        "generateSFDANEXP.do?method=generateSFDANEXP");
    session.setAttribute("DAN_TYPE", "SFDANEXP");
    return mapping.findForward("memberInfo");
  }
  
  public ActionForward showSHDANGenFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    DynaActionForm rpAllocationForm = (DynaActionForm)form;
    rpAllocationForm.initialize(mapping);
    RpProcessor rpProcessor = new RpProcessor();
    String forwardPage = "";
    ArrayList<String> memberIds = rpProcessor.getMemberIdsForSHDAN();
    if (memberIds != null && memberIds.size() != 0) {
      ArrayList<String> memberDetails = new ArrayList(memberIds.size());
      for (int i = 0; i < memberIds.size(); i++) {
        String memberId = memberIds.get(i);
        MLIInfo mliInfo = this.registration.getMemberDetails(
            memberId.substring(0, 4), memberId.substring(4, 8), 
            memberId.substring(8, 12));
        String mli = "";
        mli = "(" + memberId.substring(0, 4) + memberId.substring(4, 8) + 
          memberId.substring(8, 12) + ")" + 
          mliInfo.getShortName();
        if (mliInfo.getBranchName() != null && 
          !mliInfo.getBranchName().equals("") && (
          mliInfo.getZoneName() == null || mliInfo
          .getZoneName().equals(""))) {
          mli = String.valueOf(mli) + "," + mliInfo.getBranchName();
        } else if ((mliInfo.getBranchName() == null || mliInfo
          .getBranchName().equals("")) && 
          mliInfo.getZoneName() != null && 
          !mliInfo.getZoneName().equals("")) {
          mli = String.valueOf(mli) + "," + mliInfo.getZoneName();
        } else if (mliInfo.getBranchName() != null && 
          !mliInfo.getBranchName().equals("") && 
          mliInfo.getZoneName() != null && 
          !mliInfo.getZoneName().equals("")) {
          mli = String.valueOf(mli) + "," + mliInfo.getZoneName() + "," + 
            mliInfo.getBranchName();
        } 
        memberDetails.add(mli);
      } 
      rpAllocationForm.set("mliNames", memberDetails);
      forwardPage = "memberInfo";
      HttpSession session = request.getSession(false);
      session.setAttribute("TARGET_URL", 
          "generateSHDAN.do?method=generateSHDAN");
      session.setAttribute("DAN_TYPE", "SHDAN");
    } else {
      request.setAttribute("message", 
          "No Members available for SHDAN Generation");
      forwardPage = "success";
    } 
    Log.log(4, "RPAction", "generateCGDAN", 
        "Fetched Member Details for whom CGDAN has to be generated");
    return mapping.findForward(forwardPage);
  }
  
  public ActionForward showCLDANGenFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String methodName = "showCLDANGenFilter";
    Log.log(4, "RPAction", methodName, "Entered");
    String forwardPage = "";
    User user = getUserInformation(request);
    Log.log(4, "RPAction", "generateCLDAN", 
        "Logged in user: " + user.getUserId());
    DynaActionForm rpAllocationForm = (DynaActionForm)form;
    rpAllocationForm.initialize(mapping);
    Log.log(5, "RPAction", methodName, 
        "Fetching Member Details for whom CLDAN has to be generated");
    ArrayList<MLIInfo> memberNames = this.registration.getAllMembers();
    ArrayList<String> memberDetails = new ArrayList(memberNames.size());
    for (int i = 0; i < memberNames.size(); i++) {
      MLIInfo mliInfo = memberNames.get(i);
      String mli = "";
      mli = "(" + mliInfo.getBankId() + mliInfo.getZoneId() + 
        mliInfo.getBranchId() + ")" + mliInfo.getShortName();
      if (mliInfo.getBranchName() != null && 
        !mliInfo.getBranchName().equals("") && (
        mliInfo.getZoneName() == null || mliInfo
        .getZoneName().equals(""))) {
        mli = String.valueOf(mli) + "," + mliInfo.getBranchName();
      } else if ((mliInfo.getBranchName() == null || mliInfo
        .getBranchName().equals("")) && 
        mliInfo.getZoneName() != null && 
        !mliInfo.getZoneName().equals("")) {
        mli = String.valueOf(mli) + "," + mliInfo.getZoneName();
      } else if (mliInfo.getBranchName() != null && 
        !mliInfo.getBranchName().equals("") && 
        mliInfo.getZoneName() != null && 
        !mliInfo.getZoneName().equals("")) {
        mli = String.valueOf(mli) + "," + mliInfo.getZoneName() + "," + 
          mliInfo.getBranchName();
      } 
      memberDetails.add(mli);
    } 
    Log.log(5, "RPAction", methodName, 
        "Fetched Member Details for whom CLDAN has to be generated");
    rpAllocationForm.set("mliNames", memberDetails);
    HttpSession session = request.getSession(false);
    session.setAttribute("TARGET_URL", 
        "generateCLDAN.do?method=generateCLDAN");
    session.setAttribute("DAN_TYPE", "CLDAN");
    forwardPage = "memberInfo";
    Log.log(4, "RPAction", methodName, "Exited");
    return mapping.findForward(forwardPage);
  }
  
  public ActionForward getGLName(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "getGLName", "Entered");
    RPActionForm actionForm = (RPActionForm)form;
    String glCode = actionForm.getBankGLCode();
    Log.log(4, "RPAction", "getGLName", "code " + glCode);
    String glName = "";
    if (!glCode.equals("")) {
      RpProcessor rpProcessor = new RpProcessor();
      glName = rpProcessor.getGLName(glCode);
    } 
    request.setAttribute("IsRequired", null);
    actionForm.setBankGLName(glName);
    HttpSession session = request.getSession(false);
    session.setAttribute("VOUCHER_FLAG", "2");
    Log.log(4, "RPAction", "getGLName", "Exited");
    return mapping.findForward("success");
  }
  
  public ActionForward generateExcessVoucherFilter(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String methodName = "generateExcessVoucherFilter";
    Log.log(4, "RPAction", methodName, "Entered");
    String forwardPage = "";
    User user = getUserInformation(request);
    Log.log(5, "RPAction", methodName, 
        "Logged in user: " + user.getUserId());
    RpProcessor rpProcessor = new RpProcessor();
    DynaActionForm rpAllocationForm = (DynaActionForm)form;
    rpAllocationForm.initialize(mapping);
    ArrayList<String> memberIds = rpProcessor.getMemberIdsForExcess();
    if (memberIds != null && memberIds.size() != 0) {
      ArrayList<String> memberDetails = new ArrayList(memberIds.size());
      for (int i = 0; i < memberIds.size(); i++) {
        String memberId = memberIds.get(i);
        MLIInfo mliInfo = this.registration.getMemberDetails(
            memberId.substring(0, 4), memberId.substring(4, 8), 
            memberId.substring(8, 12));
        String mli = "";
        mli = "(" + memberId.substring(0, 4) + memberId.substring(4, 8) + 
          memberId.substring(8, 12) + ")" + 
          mliInfo.getShortName();
        if (mliInfo.getBranchName() != null && 
          !mliInfo.getBranchName().equals("") && (
          mliInfo.getZoneName() == null || mliInfo
          .getZoneName().equals(""))) {
          mli = String.valueOf(mli) + "," + mliInfo.getBranchName();
        } else if ((mliInfo.getBranchName() == null || mliInfo
          .getBranchName().equals("")) && 
          mliInfo.getZoneName() != null && 
          !mliInfo.getZoneName().equals("")) {
          mli = String.valueOf(mli) + "," + mliInfo.getZoneName();
        } else if (mliInfo.getBranchName() != null && 
          !mliInfo.getBranchName().equals("") && 
          mliInfo.getZoneName() != null && 
          !mliInfo.getZoneName().equals("")) {
          mli = String.valueOf(mli) + "," + mliInfo.getZoneName() + "," + 
            mliInfo.getBranchName();
        } 
        memberDetails.add(mli);
      } 
      rpAllocationForm.set("mliNames", memberDetails);
      forwardPage = "memberInfo";
    } else {
      request.setAttribute("message", 
          "No Members available for Voucher Generation");
      forwardPage = "success";
    } 
    HttpSession session = request.getSession(false);
    session.setAttribute("TARGET_URL", 
        "generateExcessVoucher.do?method=generateExcessVoucher");
    session.setAttribute("DAN_TYPE", "EXCESS");
    return mapping.findForward(forwardPage);
  }
  
  public ActionForward generateExcessVoucher(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RpProcessor rpProcessor = new RpProcessor();
    DynaActionForm rpGenerateDANForm = (DynaActionForm)form;
    String mliName = (String)rpGenerateDANForm.get("selectMember");
    Log.log(4, "RPAction", "generateCGDAN", "Selected Member Id : " + 
        mliName);
    User user = getUserInformation(request);
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
    Properties accCodes = new Properties();
    String contextPath = request.getSession(false).getServletContext()
      .getRealPath("");
    Log.log(5, "RPAction", "getPaymentsMade", "path " + contextPath);
    File tempFile = new File(String.valueOf(contextPath) + "\\WEB-INF\\classes", 
        "AccountCodes.properties");
    Log.log(5, "RPAction", "getPaymentsMade", "file opened ");
    File accCodeFile = new File(tempFile.getAbsolutePath());
    try {
      FileInputStream fin = new FileInputStream(accCodeFile);
      accCodes.load(fin);
    } catch (FileNotFoundException fe) {
      throw new MessageException("Could not load Account Codes.");
    } catch (IOException ie) {
      throw new MessageException("Could not load Account Codes.");
    } 
    if (!mliName.equals("All")) {
      VoucherDetail voucherDetail = new VoucherDetail();
      ArrayList<Voucher> vouchers = new ArrayList();
      mliName = mliName.substring(1, 13);
      double voucherAmount = rpProcessor.getAmountForExcess(mliName);
      voucherDetail.setBankGLName("");
      voucherDetail.setAmount(voucherAmount);
      voucherDetail.setBankGLCode(accCodes.getProperty("bank_ac"));
      voucherDetail.setDeptCode("CG");
      voucherDetail.setVoucherType("PAYMENT VOUCHER");
      Voucher voucher = new Voucher();
      voucher.setAcCode(accCodes.getProperty("excess_ac"));
      voucher.setPaidTo("CGTSI");
      voucher.setDebitOrCredit("C");
      voucher.setInstrumentDate(dateFormat.format(new Date()));
      voucher.setInstrumentNo(null);
      voucher.setInstrumentType(null);
      voucher.setAmountInRs("-" + voucherAmount);
      vouchers.add(voucher);
      String narration = "";
      narration = String.valueOf(narration) + " Member Id: " + mliName;
      narration = String.valueOf(narration) + " Voucher Amount: " + voucherAmount;
      voucherDetail.setNarration(narration);
      voucherDetail.setVouchers(vouchers);
      String voucherId = rpProcessor.insertVoucherDetails(voucherDetail, 
          user.getUserId());
      rpProcessor.updateIdForExcess(mliName, voucherId);
      vouchers.clear();
      voucherDetail = null;
    } else if (mliName.equals("All")) {
      ArrayList<String> memberIds = rpProcessor.getMemberIdsForExcess();
      for (int i = 0; i < memberIds.size(); i++) {
        VoucherDetail voucherDetail = new VoucherDetail();
        ArrayList<Voucher> vouchers = new ArrayList();
        mliName = memberIds.get(i);
        double voucherAmount = rpProcessor.getAmountForExcess(mliName);
        voucherDetail.setBankGLName("");
        voucherDetail.setAmount(voucherAmount);
        voucherDetail.setBankGLCode(accCodes.getProperty("bank_ac"));
        voucherDetail.setDeptCode("CG");
        voucherDetail.setVoucherType("PAYMENT VOUCHER");
        Voucher voucher = new Voucher();
        voucher.setAcCode(accCodes.getProperty("excess_ac"));
        voucher.setPaidTo("CGTSI");
        voucher.setDebitOrCredit("C");
        voucher.setInstrumentDate(dateFormat
            .format(new Date()));
        voucher.setInstrumentNo(null);
        voucher.setInstrumentType(null);
        voucher.setAmountInRs("-" + voucherAmount);
        vouchers.add(voucher);
        String narration = "";
        narration = String.valueOf(narration) + " Member Id: " + mliName;
        narration = String.valueOf(narration) + " Voucher Amount: " + voucherAmount;
        voucherDetail.setNarration(narration);
        voucherDetail.setVouchers(vouchers);
        String voucherId = rpProcessor.insertVoucherDetails(
            voucherDetail, user.getUserId());
        rpProcessor.updateIdForExcess(mliName, voucherId);
        vouchers.clear();
        voucherDetail = null;
      } 
    } 
    request.setAttribute("message", 
        "Voucher details are stored successfully");
    return mapping.findForward("success");
  }
  
  public ActionForward showAllCardRates(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RpProcessor rpProcessor = new RpProcessor();
    RPActionForm actionForm = (RPActionForm)form;
    ArrayList cardRateList = rpProcessor.getAllCardRates();
    actionForm.setGfCardRateList(cardRateList);
    return mapping.findForward("success");
  }
  
  public ActionForward saveGFCardRates(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "saveGFCardRates", "Entered");
    RpProcessor rpProcessor = new RpProcessor();
    RPActionForm actionForm = (RPActionForm)form;
    User user = getUserInformation(request);
    Map<Object, Object> rateId = new TreeMap<Object, Object>();
    Map<Object, Object> gfLowAmount = new TreeMap<Object, Object>();
    Map<Object, Object> gfLowHigh = new TreeMap<Object, Object>();
    Map<Object, Object> gfCardRate = new TreeMap<Object, Object>();
    rateId = actionForm.getRateId();
    gfLowAmount = actionForm.getLowAmount();
    gfLowHigh = actionForm.getHighAmount();
    gfCardRate = actionForm.getGfRate();
    Set rateIdSet = rateId.keySet();
    Iterator<String> rateIdIterator = rateIdSet.iterator();
    while (rateIdIterator.hasNext()) {
      String key = rateIdIterator.next();
      int id = Integer.parseInt((String)rateId.get(key));
      double cardRate = Double.parseDouble((String)gfCardRate.get(key));
      Log.log(4, "RPAction", "saveGFCardRates", "cardRate ;" + cardRate);
      Log.log(4, "RPAction", "saveGFCardRates", "id :" + id);
      rpProcessor.updateCardRate(id, cardRate, user.getUserId());
    } 
    request.setAttribute("message", 
        "Guarantee Fee Card Rates Updated Successfully");
    Log.log(4, "RPAction", "saveGFCardRates", "Exited");
    return mapping.findForward("success");
  }
  
  public ActionForward rpCancelPayments(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "ReportsAction", "rpCancelPayments", "Entered");
    RPActionForm actionForm = (RPActionForm)form;
    Log.log(4, "ReportsAction", "rpCancelPayments", "Exited");
    return mapping.findForward("success");
  }
  
  public ActionForward cancelRpAppropriation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "cancelRpAppropriation", "Entered");
    ReportManager manager = new ReportManager();
    RPActionForm rpForm = (RPActionForm)form;
    String clmApplicationStatus = "";
    Log.log(4, "ReportsAction", "displayTCQueryDetail", 
        "Claim Application Status being queried ");
    User user = getUserInformation(request);
    String userid = user.getUserId().trim();
    String bankid = user.getBankId().trim();
    String zoneid = user.getZoneId().trim();
    String branchid = user.getBranchId().trim();
    String memberId = String.valueOf(bankid) + zoneid + branchid;
    String paymentId = null;
    String instrumentNo = null;
    String remarks = null;
    paymentId = request.getParameter("paymentId");
    remarks = request.getParameter("remarksforAppropriation");
    instrumentNo = request.getParameter("instrumentNo");
    if (paymentId != null && instrumentNo != null) {
      Connection connection = DBConnection.getConnection(false);
      try {
        CallableStatement callable = connection
          .prepareCall("{?=call funccancelappropriation(?,?,?,?,?)}");
        callable.registerOutParameter(1, 4);
        callable.setString(2, paymentId);
        callable.setString(3, instrumentNo);
        callable.setString(4, userid);
        callable.setString(5, remarks);
        callable.registerOutParameter(6, 12);
        callable.execute();
        int errorCode = callable.getInt(1);
        String error = callable.getString(6);
        Log.log(5, "RPAction", "cancelRpAppropriation", 
            "Error code and error are " + errorCode + " " + error);
        if (errorCode == 1) {
          connection.rollback();
          callable.close();
          callable = null;
          throw new DatabaseException(error);
        } 
        callable.close();
        callable = null;
        connection.commit();
      } catch (SQLException e) {
        try {
          connection.rollback();
        } catch (SQLException ignore) {
          Log.log(2, "RPAction", "cancelRpAppropriation", 
              ignore.getMessage());
        } 
        Log.log(2, "RPAction", "cancelRpAppropriation", e.getMessage());
        Log.logException(e);
      } finally {
        DBConnection.freeConnection(connection);
      } 
    } 
    return mapping.findForward("success");
  }
  
  public ActionForward getPendingAFDANsLive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm rpForm = (RPActionForm)form;
    User user = getUserInformation(request);
    String bankId = user.getBankId();
    String zoneId = user.getZoneId();
    String branchId = user.getBranchId();
    String memberId = "";
    if (rpForm.getDanSummaries() != null)
      rpForm.getDanSummaries().clear(); 
    if (rpForm.getDanPanDetails() != null)
      rpForm.getDanPanDetails().clear(); 
    if (rpForm.getCgpans() != null)
      rpForm.getCgpans().clear(); 
    if (rpForm.getAllocatedFlags() != null)
      rpForm.getAllocatedFlags().clear(); 
    if (rpForm.getFirstDisbursementDates() != null)
      rpForm.getFirstDisbursementDates().clear(); 
    if (rpForm.getNotAllocatedReasons() != null)
      rpForm.getNotAllocatedReasons().clear(); 
    if (rpForm.getAllocatedFlags() != null)
      rpForm.getAllocatedFlags().clear(); 
    if (rpForm.getAppropriatedFlags() != null)
      rpForm.getAppropriatedFlags().clear(); 
    if (rpForm.getAmountsRaised() != null)
      rpForm.getAmountsRaised().clear(); 
    if (rpForm.getAmountBeingPaid() != null)
      rpForm.getAmountBeingPaid().clear(); 
    if (bankId.equals("0000")) {
      memberId = rpForm.getSelectMember();
      Log.log(5, "RPAction", "getNEFTPendingGFDANs", "mliId = " + 
          memberId);
      if (memberId == null || memberId.equals(""))
        return mapping.findForward("liveafmember"); 
      bankId = memberId.substring(0, 4);
      zoneId = memberId.substring(4, 8);
      branchId = memberId.substring(8, 12);
      ClaimsProcessor cpProcessor = new ClaimsProcessor();
      Vector memberIds = cpProcessor.getAllMemberIds();
      if (!memberIds.contains(memberId))
        throw new NoMemberFoundException("The Member ID does not exist"); 
    } 
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList<DANSummary> danSummaries = rpProcessor.displayLiveAFDANs(bankId, zoneId, 
        branchId);
    Log.log(5, "RPAction", "getNEFTPendingGFDANs", "dan summary size : " + 
        danSummaries.size());
    if (danSummaries.size() == 0) {
      rpForm.setSelectMember(null);
      throw new MissingDANDetailsException(
          "No DANs available for Allocation");
    } 
    boolean isDanAvailable = false;
    for (int i = 0; i < danSummaries.size(); i++) {
      DANSummary danSummary = danSummaries.get(i);
      if (danSummary.getAmountDue() != danSummary.getAmountPaid()) {
        isDanAvailable = true;
        break;
      } 
    } 
    if (!isDanAvailable) {
      rpForm.setSelectMember(null);
      throw new MissingDANDetailsException(
          "No DANs available for Allocation");
    } 
    rpForm.setDanSummaries(danSummaries);
    rpForm.setBankId(bankId);
    rpForm.setZoneId(zoneId);
    rpForm.setBranchId(branchId);
    Log.log(4, "RPAction", "getNEFTPendingGFDANs", "Exited");
    if (rpForm.getSelectMember() != null) {
      rpForm.setMemberId(rpForm.getSelectMember());
    } else {
      rpForm.setMemberId(String.valueOf(bankId) + zoneId + branchId);
    } 
    rpForm.setSelectMember(null);
    return mapping.findForward("liveafdansummary");
  }
  
  public ActionForward getPendingGFDANsLiveOnline(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    System.out.println("getPendingGFDANsLiveOnline ");
    RPActionForm rpForm = (RPActionForm)form;
    User user = getUserInformation(request);
    String bankId = user.getBankId();
    String zoneId = user.getZoneId();
    String branchId = user.getBranchId();
    String mem2 = bankId.concat(zoneId).concat(branchId);
    System.out.println("getPendingGFDANsLiveOnline mem2" + mem2);
    String memberId = "";
    if (rpForm.getDanSummaries() != null)
      rpForm.getDanSummaries().clear(); 
    if (rpForm.getDanPanDetails() != null)
      rpForm.getDanPanDetails().clear(); 
    if (rpForm.getCgpans() != null)
      rpForm.getCgpans().clear(); 
    if (rpForm.getAllocatedFlags() != null)
      rpForm.getAllocatedFlags().clear(); 
    if (rpForm.getFirstDisbursementDates() != null)
      rpForm.getFirstDisbursementDates().clear(); 
    if (rpForm.getNotAllocatedReasons() != null)
      rpForm.getNotAllocatedReasons().clear(); 
    if (rpForm.getAllocatedFlags() != null)
      rpForm.getAllocatedFlags().clear(); 
    if (rpForm.getAppropriatedFlags() != null)
      rpForm.getAppropriatedFlags().clear(); 
    if (rpForm.getAmountsRaised() != null)
      rpForm.getAmountsRaised().clear(); 
    if (rpForm.getAmountBeingPaid() != null)
      rpForm.getAmountBeingPaid().clear(); 
    if (bankId.equals("0000")) {
      memberId = rpForm.getSelectMember();
      memberId = mem2;
      Log.log(5, "RPAction", "getNEFTPendingGFDANs", "mliId = " + 
          memberId);
      System.out.println("getPendingGFDANsLiveOnline memberId " + 
          memberId);
      if (memberId == null || memberId.equals(""))
        return mapping.findForward("liveafmemberOnline"); 
      System.out
        .println("getPendingGFDANsLiveOnline memberId after liveafmemberOnline " + 
          memberId);
      bankId = memberId.substring(0, 4);
      zoneId = memberId.substring(4, 8);
      branchId = memberId.substring(8, 12);
      ClaimsProcessor cpProcessor = new ClaimsProcessor();
      Vector memberIds = cpProcessor.getAllMemberIds();
      if (!memberIds.contains(memberId))
        throw new NoMemberFoundException("The Member ID does not exist"); 
    } 
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList<DANSummary> danSummaries = rpProcessor.displayLiveGFDANs(bankId, zoneId, 
        branchId);
    Log.log(5, "RPAction", "getNEFTPendingGFDANs", "dan summary size : " + 
        danSummaries.size());
    if (danSummaries.size() == 0) {
      rpForm.setSelectMember(null);
      throw new MissingDANDetailsException(
          "No DANs available for Allocation");
    } 
    boolean isDanAvailable = false;
    for (int i = 0; i < danSummaries.size(); i++) {
      DANSummary danSummary = danSummaries.get(i);
      if (danSummary.getAmountDue() != danSummary.getAmountPaid()) {
        isDanAvailable = true;
        break;
      } 
    } 
    if (!isDanAvailable) {
      rpForm.setSelectMember(null);
      throw new MissingDANDetailsException(
          "No DANs available for Allocation");
    } 
    rpForm.setDanSummaries(danSummaries);
    rpForm.setBankId(bankId);
    rpForm.setZoneId(zoneId);
    rpForm.setBranchId(branchId);
    Log.log(4, "RPAction", "getNEFTPendingGFDANs", "Exited");
    if (rpForm.getSelectMember() != null) {
      rpForm.setMemberId(rpForm.getSelectMember());
    } else {
      rpForm.setMemberId(String.valueOf(bankId) + zoneId + branchId);
    } 
    rpForm.setSelectMember(null);
    return mapping.findForward("liveafdansummaryOnline");
  }
  
  public ActionForward getLiveAFDANsPaymentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    double totalAmount = 0.0D;
    StringTokenizer tokenizer = null;
    Map appropriatedCases = actionForm.getAppropriatedFlags();
    if (appropriatedCases.size() < 1)
      throw new MissingDANDetailsException(
          "Please select atleast one dan for allocation."); 
    Set appropriatedCasesSet = appropriatedCases.keySet();
    Iterator<String> appropriatedCasesIterator = appropriatedCasesSet.iterator();
    String token = null;
    String token1 = null;
    float total = 0.0F;
    float total2 = 0.0F;
    while (appropriatedCasesIterator.hasNext()) {
      String key = appropriatedCasesIterator.next();
      tokenizer = new StringTokenizer(key, "#");
      for (; tokenizer
        .hasMoreTokens(); System.out.println(total2)) {
        token = tokenizer.nextToken();
        token1 = tokenizer.nextToken();
        total = Integer.parseInt(token1);
        total2 += total;
      } 
    } 
    Registration registration = new Registration();
    CollectingBank collectingBank = registration
      .getCollectingBank(actionForm.getMemberId());
    actionForm.setModeOfPayment("");
    actionForm.setPaymentDate(null);
    actionForm.setInstrumentNo("");
    actionForm.setInstrumentDate(null);
    actionForm.setDrawnAtBank("");
    actionForm.setDrawnAtBranch("");
    actionForm.setPayableAt("");
    IFProcessor ifProcessor = new IFProcessor();
    ArrayList instruments = ifProcessor.getInstrumentTypes("G");
    actionForm.setInstruments(instruments);
    actionForm.setCollectingBank(collectingBank.getCollectingBankId());
    actionForm
      .setCollectingBankName(collectingBank.getCollectingBankName());
    actionForm.setCollectingBankBranch(collectingBank.getBranchName());
    actionForm.setAccountNumber(collectingBank.getAccNo());
    actionForm.setInstrumentAmount(total2);
    return mapping.findForward("liveafdanpaymentdetails");
  }
  
  public ActionForward getLiveGFDANsPaymentDetailsOnline(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    double totalAmount = 0.0D;
    StringTokenizer tokenizer = null;
    Map appropriatedCases = actionForm.getAppropriatedFlags();
    if (appropriatedCases.size() < 1)
      throw new MissingDANDetailsException(
          "Please select atleast one dan for allocation."); 
    if (appropriatedCases.size() > 50)
      throw new MissingDANDetailsException(
          "Maximum 50 allocation is only possible. "); 
    Set appropriatedCasesSet = appropriatedCases.keySet();
    Iterator<String> appropriatedCasesIterator = appropriatedCasesSet.iterator();
    String token = null;
    String token1 = null;
    float total = 0.0F;
    float total2 = 0.0F;
    double d2 = total;
    String paymentId = "";
    String ifscCode = "UBIN0996335";
    User user = getUserInformation(request);
    PaymentDetails paymentDetails = new PaymentDetails();
    RpProcessor rpProcessor = new RpProcessor();
    while (appropriatedCasesIterator.hasNext()) {
      String key = appropriatedCasesIterator.next();
      tokenizer = new StringTokenizer(key, "#");
      for (; tokenizer
        .hasMoreTokens(); System.out.println(total2)) {
        token = tokenizer.nextToken();
        token1 = tokenizer.nextToken();
        total = Integer.parseInt(token1);
        total2 += total;
      } 
    } 
    paymentDetails.setInstrumentAmount(total2);
    Registration registration = new Registration();
    paymentDetails.setAllocationType1("G");
    Map appropriatedFlags = actionForm.getAppropriatedFlags();
    Map cgpans = actionForm.getCgpans();
    Map danCgpanDetails = actionForm.getDanPanDetails();
    paymentId = rpProcessor.allocateCGDANonline(paymentDetails, 
        appropriatedFlags, cgpans, danCgpanDetails, user);
    System.out.println("rajukonkati2" + paymentId);
    StringTokenizer tok = new StringTokenizer(paymentId, "()- ");
    StringBuilder br = new StringBuilder();
    String a = tok.nextToken();
    String b = tok.nextToken();
    String c = tok.nextToken();
    String d = tok.nextToken();
    String e = tok.nextToken();
    System.out.println("rajukonkati" + a);
    System.out.println("rajukonkati" + b);
    System.out.println("rajukonkati" + c);
    System.out.println("rajukonkati" + d);
    System.out.println("rajukonkati" + e);
    String f = "16666";
    String vaccno = f.concat(b).concat(c).concat(d).concat(e);
    System.out.println(vaccno);
    paymentDetails.setIfscCode(ifscCode);
    actionForm.setModeOfPayment("");
    actionForm.setPaymentDate(null);
    actionForm.setInstrumentNo("");
    actionForm.setInstrumentDate(null);
    actionForm.setDrawnAtBank("");
    actionForm.setDrawnAtBranch("");
    actionForm.setPayableAt("");
    IFProcessor ifProcessor = new IFProcessor();
    ArrayList instruments = ifProcessor.getInstrumentTypes("G");
    actionForm.setInstruments(instruments);
    actionForm.setInstrumentAmount(total2);
    actionForm.setPaymentId(paymentId);
    actionForm.setVaccno(vaccno);
    actionForm.setIfscCode(ifscCode);
    return mapping.findForward("liveafdanpaymentdetailsonline");
  }
  
  public ActionForward submitAllocatePaymentOnline(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    RpProcessor rpProcessor = new RpProcessor();
    String paymentId = "";
    String methodName = "submitAFDANsPaymentDetails";
    Log.log(5, "RPAction", methodName, "Entered");
    User user = getUserInformation(request);
    PaymentDetails paymentDetails = new PaymentDetails();
    String allocationType = actionForm.getAllocationType();
    paymentDetails.setAllocationType1("S");
    String modeOfPayment = actionForm.getModeOfPayment();
    String collectingBranch = actionForm.getCollectingBankBranch();
    Date paymentDate = actionForm.getPaymentDate();
    String instrumentNumber = actionForm.getInstrumentNo();
    Date instrumentDate = actionForm.getInstrumentDate();
    String modeOfDelivery = actionForm.getModeOfDelivery();
    double instrumentAmount = actionForm.getInstrumentAmount();
    String drawnAtBank = actionForm.getDrawnAtBank();
    String drawnAtBranch = actionForm.getDrawnAtBranch();
    String payableAt = actionForm.getPayableAt();
    String accNumber = actionForm.getAccountNumber();
    paymentDetails.setCollectingBank(actionForm.getCollectingBankName());
    paymentDetails.setModeOfPayment(modeOfPayment);
    paymentDetails.setCollectingBankBranch(collectingBranch);
    paymentDetails.setPaymentDate(paymentDate);
    paymentDetails.setInstrumentNo(instrumentNumber);
    paymentDetails.setInstrumentDate(instrumentDate);
    paymentDetails.setModeOfDelivery(modeOfDelivery);
    paymentDetails.setInstrumentAmount(instrumentAmount);
    paymentDetails.setDrawnAtBank(drawnAtBank);
    paymentDetails.setDrawnAtBranch(drawnAtBranch);
    paymentDetails.setPayableAt(payableAt);
    paymentDetails.setCgtsiAccNumber(accNumber);
    Map appropriatedFlags = actionForm.getAppropriatedFlags();
    Map cgpans = actionForm.getCgpans();
    Map danCgpanDetails = actionForm.getDanPanDetails();
    CallableStatement callableStmt = null;
    Connection conn = null;
    boolean hasExceptionOccured = false;
    int status = -1;
    String errorCode = null;
    conn = DBConnection.getConnection(false);
    try {
      callableStmt = conn
        .prepareCall("{?=call funcInsertClaimCheckList(?,?,?,?,?,?,?,?,?,?)}");
      callableStmt.registerOutParameter(1, 4);
      callableStmt.registerOutParameter(29, 12);
      callableStmt.execute();
      status = callableStmt.getInt(1);
      errorCode = callableStmt.getString(29);
    } catch (Exception exception) {
      throw new DatabaseException(exception.getMessage());
    } finally {
      DBConnection.freeConnection(conn);
    } 
    return mapping.findForward("success");
  }
  
  public ActionForward submitAFDANsPaymentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    RpProcessor rpProcessor = new RpProcessor();
    String paymentId = "";
    String methodName = "submitAFDANsPaymentDetails";
    Log.log(5, "RPAction", methodName, "Entered");
    User user = getUserInformation(request);
    PaymentDetails paymentDetails = new PaymentDetails();
    String allocationType = actionForm.getAllocationType();
    paymentDetails.setAllocationType1("S");
    String modeOfPayment = actionForm.getModeOfPayment();
    String collectingBranch = actionForm.getCollectingBankBranch();
    Date paymentDate = actionForm.getPaymentDate();
    String instrumentNumber = actionForm.getInstrumentNo();
    Date instrumentDate = actionForm.getInstrumentDate();
    String modeOfDelivery = actionForm.getModeOfDelivery();
    double instrumentAmount = actionForm.getInstrumentAmount();
    String drawnAtBank = actionForm.getDrawnAtBank();
    String drawnAtBranch = actionForm.getDrawnAtBranch();
    String payableAt = actionForm.getPayableAt();
    String accNumber = actionForm.getAccountNumber();
    Log.log(5, "RPAction", methodName, 
        "collecting bank " + actionForm.getCollectingBankName());
    paymentDetails.setCollectingBank(actionForm.getCollectingBankName());
    Log.log(5, "RPAction", methodName, "mode of payment " + modeOfPayment);
    paymentDetails.setModeOfPayment(modeOfPayment);
    Log.log(5, "RPAction", methodName, "collecting branch " + 
        collectingBranch);
    paymentDetails.setCollectingBankBranch(collectingBranch);
    Log.log(5, "RPAction", methodName, "payment date " + paymentDate);
    paymentDetails.setPaymentDate(paymentDate);
    Log.log(5, "RPAction", methodName, "instrument number " + 
        instrumentNumber);
    paymentDetails.setInstrumentNo(instrumentNumber);
    Log.log(5, "RPAction", methodName, "instrument date " + instrumentDate);
    paymentDetails.setInstrumentDate(instrumentDate);
    Log.log(5, "RPAction", methodName, "mode of delivery " + modeOfDelivery);
    paymentDetails.setModeOfDelivery(modeOfDelivery);
    Log.log(5, "RPAction", methodName, "instrument amount " + 
        instrumentAmount);
    paymentDetails.setInstrumentAmount(instrumentAmount);
    Log.log(5, "RPAction", methodName, "drawn at bank " + drawnAtBank);
    paymentDetails.setDrawnAtBank(drawnAtBank);
    Log.log(5, "RPAction", methodName, "drawn at branch " + drawnAtBranch);
    paymentDetails.setDrawnAtBranch(drawnAtBranch);
    Log.log(5, "RPAction", methodName, "payable at " + payableAt);
    paymentDetails.setPayableAt(payableAt);
    Log.log(5, "RPAction", methodName, "acc num " + accNumber);
    paymentDetails.setCgtsiAccNumber(accNumber);
    Map appropriatedFlags = actionForm.getAppropriatedFlags();
    Map cgpans = actionForm.getCgpans();
    Map danCgpanDetails = actionForm.getDanPanDetails();
    paymentId = rpProcessor.allocateCGDAN(paymentDetails, 
        appropriatedFlags, cgpans, danCgpanDetails, user);
    request.setAttribute("message", 
        "Payment Allocated Successfully.<BR>Payment ID : " + paymentId + 
        " for Rs." + paymentDetails.getInstrumentAmount());
    Log.log(5, "RPAction", methodName, "Exited");
    return mapping.findForward("success");
  }
  
  public ActionForward getPendingAFDANsExpired(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm rpForm = (RPActionForm)form;
    User user = getUserInformation(request);
    String bankId = user.getBankId();
    String zoneId = user.getZoneId();
    String branchId = user.getBranchId();
    String memberId = "";
    if (rpForm.getDanSummaries() != null)
      rpForm.getDanSummaries().clear(); 
    if (rpForm.getDanPanDetails() != null)
      rpForm.getDanPanDetails().clear(); 
    if (rpForm.getCgpans() != null)
      rpForm.getCgpans().clear(); 
    if (rpForm.getAllocatedFlags() != null)
      rpForm.getAllocatedFlags().clear(); 
    if (rpForm.getFirstDisbursementDates() != null)
      rpForm.getFirstDisbursementDates().clear(); 
    if (rpForm.getNotAllocatedReasons() != null)
      rpForm.getNotAllocatedReasons().clear(); 
    if (rpForm.getAllocatedFlags() != null)
      rpForm.getAllocatedFlags().clear(); 
    if (rpForm.getAppropriatedFlags() != null)
      rpForm.getAppropriatedFlags().clear(); 
    if (rpForm.getAmountsRaised() != null)
      rpForm.getAmountsRaised().clear(); 
    if (rpForm.getAmountBeingPaid() != null)
      rpForm.getAmountBeingPaid().clear(); 
    if (bankId.equals("0000")) {
      memberId = rpForm.getSelectMember();
      Log.log(5, "RPAction", "getNEFTPendingGFDANs", "mliId = " + 
          memberId);
      if (memberId == null || memberId.equals(""))
        return mapping.findForward("expiredafmember"); 
      bankId = memberId.substring(0, 4);
      zoneId = memberId.substring(4, 8);
      branchId = memberId.substring(8, 12);
      ClaimsProcessor cpProcessor = new ClaimsProcessor();
      Vector memberIds = cpProcessor.getAllMemberIds();
      if (!memberIds.contains(memberId))
        throw new NoMemberFoundException("The Member ID does not exist"); 
    } 
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList<DANSummary> danSummaries = rpProcessor.displayExpiredAFDANs(bankId, 
        zoneId, branchId);
    Log.log(5, "RPAction", "getNEFTPendingGFDANs", "dan summary size : " + 
        danSummaries.size());
    if (danSummaries.size() == 0) {
      rpForm.setSelectMember(null);
      throw new MissingDANDetailsException(
          "No DANs available for Allocation");
    } 
    boolean isDanAvailable = false;
    for (int i = 0; i < danSummaries.size(); i++) {
      DANSummary danSummary = danSummaries.get(i);
      if (danSummary.getAmountDue() != danSummary.getAmountPaid()) {
        isDanAvailable = true;
        break;
      } 
    } 
    if (!isDanAvailable) {
      rpForm.setSelectMember(null);
      throw new MissingDANDetailsException(
          "No DANs available for Allocation");
    } 
    rpForm.setDanSummaries(danSummaries);
    rpForm.setBankId(bankId);
    rpForm.setZoneId(zoneId);
    rpForm.setBranchId(branchId);
    Log.log(4, "RPAction", "getNEFTPendingGFDANs", "Exited");
    if (rpForm.getSelectMember() != null) {
      rpForm.setMemberId(rpForm.getSelectMember());
    } else {
      rpForm.setMemberId(String.valueOf(bankId) + zoneId + branchId);
    } 
    rpForm.setSelectMember(null);
    return mapping.findForward("expiredafdansummary");
  }
  
  public ActionForward getExpiredAFDANsPaymentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    double totalAmount = 0.0D;
    StringTokenizer tokenizer = null;
    Map appropriatedCases = actionForm.getAppropriatedFlags();
    if (appropriatedCases.size() < 1)
      throw new MissingDANDetailsException(
          "Please select atleast one dan for allocation ."); 
    Set appropriatedCasesSet = appropriatedCases.keySet();
    Iterator<String> appropriatedCasesIterator = appropriatedCasesSet.iterator();
    String token = null;
    String token1 = null;
    float total = 0.0F;
    float total2 = 0.0F;
    while (appropriatedCasesIterator.hasNext()) {
      String key = appropriatedCasesIterator.next();
      tokenizer = new StringTokenizer(key, "#");
      for (; tokenizer
        .hasMoreTokens(); System.out.println(total2)) {
        token = tokenizer.nextToken();
        token1 = tokenizer.nextToken();
        total = Integer.parseInt(token1);
        total2 += total;
      } 
    } 
    Registration registration = new Registration();
    Log.log(5, 
        "RPAction", 
        "getExpiredAFDANsPaymentDetails", 
        "member id " + 
        actionForm.getMemberId());
    CollectingBank collectingBank = registration
      .getCollectingBank("(" + 
        actionForm.getMemberId() + ")");
    Log.log(5, 
        "RPAction", 
        "getExpiredAFDANsPaymentDetails", 
        "collectingBank " + 
        collectingBank);
    actionForm.setModeOfPayment("");
    actionForm.setPaymentDate(null);
    actionForm.setInstrumentNo("");
    actionForm.setInstrumentDate(null);
    actionForm.setDrawnAtBank("");
    actionForm.setDrawnAtBranch("");
    actionForm.setPayableAt("");
    IFProcessor ifProcessor = new IFProcessor();
    ArrayList instruments = ifProcessor.getInstrumentTypes("G");
    actionForm.setInstruments(instruments);
    actionForm.setCollectingBank(collectingBank.getCollectingBankId());
    actionForm
      .setCollectingBankName(collectingBank.getCollectingBankName());
    actionForm.setCollectingBankBranch(collectingBank.getBranchName());
    actionForm.setAccountNumber(collectingBank.getAccNo());
    actionForm.setInstrumentAmount(total2);
    return mapping.findForward("expiredafdanpaymentdetails");
  }
  
  public ActionForward getPendingASFDANsLive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm rpForm = (RPActionForm)form;
    User user = getUserInformation(request);
    String bankId = user.getBankId();
    String zoneId = user.getZoneId();
    String branchId = user.getBranchId();
    String memberId = "";
    if (rpForm.getDanSummaries() != null)
      rpForm.getDanSummaries().clear(); 
    if (rpForm.getDanPanDetails() != null)
      rpForm.getDanPanDetails().clear(); 
    if (rpForm.getCgpans() != null)
      rpForm.getCgpans().clear(); 
    if (rpForm.getAllocatedFlags() != null)
      rpForm.getAllocatedFlags().clear(); 
    if (rpForm.getFirstDisbursementDates() != null)
      rpForm.getFirstDisbursementDates().clear(); 
    if (rpForm.getNotAllocatedReasons() != null)
      rpForm.getNotAllocatedReasons().clear(); 
    if (rpForm.getAllocatedFlags() != null)
      rpForm.getAllocatedFlags().clear(); 
    if (rpForm.getAppropriatedFlags() != null)
      rpForm.getAppropriatedFlags().clear(); 
    if (rpForm.getAmountsRaised() != null)
      rpForm.getAmountsRaised().clear(); 
    if (rpForm.getAmountBeingPaid() != null)
      rpForm.getAmountBeingPaid().clear(); 
    if (bankId.equals("0000")) {
      memberId = rpForm.getSelectMember();
      Log.log(5, "RPAction", "getNEFTPendingGFDANs", "mliId = " + 
          memberId);
      if (memberId == null || memberId.equals(""))
        return mapping.findForward("liveasfmember"); 
      bankId = memberId.substring(0, 4);
      zoneId = memberId.substring(4, 8);
      branchId = memberId.substring(8, 12);
      ClaimsProcessor cpProcessor = new ClaimsProcessor();
      Vector memberIds = cpProcessor.getAllMemberIds();
      if (!memberIds.contains(memberId))
        throw new NoMemberFoundException("The Member ID does not exist"); 
    } 
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList<DANSummary> danSummaries = rpProcessor.displayLiveASFDANs(bankId, zoneId, 
        branchId);
    Log.log(5, "RPAction", "getNEFTPendingGFDANs", "dan summary size : " + 
        danSummaries.size());
    if (danSummaries.size() == 0) {
      rpForm.setSelectMember(null);
      throw new MissingDANDetailsException(
          "No DANs available for Allocation");
    } 
    boolean isDanAvailable = false;
    for (int i = 0; i < danSummaries.size(); i++) {
      DANSummary danSummary = danSummaries.get(i);
      System.out.println("danSummary.getDanId():" + danSummary.getDanId() + 
          "--danSummary.getAmountDue():" + 
          danSummary.getAmountDue() + 
          "--danSummary.getAmountPaid():" + 
          danSummary.getAmountPaid());
      if (danSummary.getAmountDue() != danSummary.getAmountPaid()) {
        isDanAvailable = true;
        break;
      } 
    } 
    if (!isDanAvailable) {
      rpForm.setSelectMember(null);
      throw new MissingDANDetailsException(
          "No DANs available for Allocation");
    } 
    rpForm.setDanSummaries(danSummaries);
    rpForm.setBankId(bankId);
    rpForm.setZoneId(zoneId);
    rpForm.setBranchId(branchId);
    Log.log(4, "RPAction", "getNEFTPendingGFDANs", "Exited");
    if (rpForm.getSelectMember() != null) {
      rpForm.setMemberId(rpForm.getSelectMember());
    } else {
      rpForm.setMemberId(String.valueOf(bankId) + zoneId + branchId);
    } 
    rpForm.setSelectMember(null);
    return mapping.findForward("liveasfdansummary");
  }
  
  public ActionForward getLiveASFDANsPaymentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    double totalAmount = 0.0D;
    StringTokenizer tokenizer = null;
    Map appropriatedCases = actionForm.getAppropriatedFlags();
    if (appropriatedCases.size() < 1)
      throw new MissingDANDetailsException(
          "Please select atleast one dan for allocation ."); 
    Set appropriatedCasesSet = appropriatedCases.keySet();
    Iterator<String> appropriatedCasesIterator = appropriatedCasesSet.iterator();
    String token = null;
    String token1 = null;
    float total = 0.0F;
    float total2 = 0.0F;
    while (appropriatedCasesIterator.hasNext()) {
      String key = appropriatedCasesIterator.next();
      tokenizer = new StringTokenizer(key, "#");
      for (; tokenizer
        .hasMoreTokens(); System.out.println(total2)) {
        token = tokenizer.nextToken();
        token1 = tokenizer.nextToken();
        total = Integer.parseInt(token1);
        total2 += total;
      } 
    } 
    Registration registration = new Registration();
    Log.log(5, 
        "RPAction", 
        "getLiveASFDANsPaymentDetails", 
        "member id " + 
        actionForm.getMemberId());
    CollectingBank collectingBank = registration
      .getCollectingBank("(" + 
        actionForm.getMemberId() + ")");
    Log.log(5, 
        "RPAction", 
        "getLiveASFDANsPaymentDetails", 
        "collectingBank " + 
        collectingBank);
    actionForm.setModeOfPayment("");
    actionForm.setPaymentDate(null);
    actionForm.setInstrumentNo("");
    actionForm.setInstrumentDate(null);
    actionForm.setDrawnAtBank("");
    actionForm.setDrawnAtBranch("");
    actionForm.setPayableAt("");
    IFProcessor ifProcessor = new IFProcessor();
    ArrayList instruments = ifProcessor.getInstrumentTypes("G");
    actionForm.setInstruments(instruments);
    actionForm.setCollectingBank(collectingBank.getCollectingBankId());
    actionForm
      .setCollectingBankName(collectingBank.getCollectingBankName());
    actionForm.setCollectingBankBranch(collectingBank.getBranchName());
    actionForm.setAccountNumber(collectingBank.getAccNo());
    actionForm.setInstrumentAmount(total2);
    return mapping.findForward("liveasfdanpaymentdetails");
  }
  
  public ActionForward submitASFDANsPaymentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    RpProcessor rpProcessor = new RpProcessor();
    String paymentId = "";
    String methodName = "submitLiveASFDANsPaymentDetails";
    Log.log(5, "RPAction", methodName, "Entered");
    User user = getUserInformation(request);
    PaymentDetails paymentDetails = new PaymentDetails();
    String allocationType = actionForm.getAllocationType();
    paymentDetails.setAllocationType1("A");
    String modeOfPayment = actionForm.getModeOfPayment();
    String collectingBranch = actionForm.getCollectingBankBranch();
    Date paymentDate = actionForm.getPaymentDate();
    String instrumentNumber = actionForm.getInstrumentNo();
    Date instrumentDate = actionForm.getInstrumentDate();
    String modeOfDelivery = actionForm.getModeOfDelivery();
    double instrumentAmount = actionForm.getInstrumentAmount();
    String drawnAtBank = actionForm.getDrawnAtBank();
    String drawnAtBranch = actionForm.getDrawnAtBranch();
    String payableAt = actionForm.getPayableAt();
    String accNumber = actionForm.getAccountNumber();
    Log.log(5, "RPAction", methodName, 
        "collecting bank " + actionForm.getCollectingBankName());
    paymentDetails.setCollectingBank(actionForm.getCollectingBankName());
    Log.log(5, "RPAction", methodName, "mode of payment " + modeOfPayment);
    paymentDetails.setModeOfPayment(modeOfPayment);
    Log.log(5, "RPAction", methodName, "collecting branch " + 
        collectingBranch);
    paymentDetails.setCollectingBankBranch(collectingBranch);
    Log.log(5, "RPAction", methodName, "payment date " + paymentDate);
    paymentDetails.setPaymentDate(paymentDate);
    Log.log(5, "RPAction", methodName, "instrument number " + 
        instrumentNumber);
    paymentDetails.setInstrumentNo(instrumentNumber);
    Log.log(5, "RPAction", methodName, "instrument date " + instrumentDate);
    paymentDetails.setInstrumentDate(instrumentDate);
    Log.log(5, "RPAction", methodName, "mode of delivery " + modeOfDelivery);
    paymentDetails.setModeOfDelivery(modeOfDelivery);
    Log.log(5, "RPAction", methodName, "instrument amount " + 
        instrumentAmount);
    paymentDetails.setInstrumentAmount(instrumentAmount);
    Log.log(5, "RPAction", methodName, "drawn at bank " + drawnAtBank);
    paymentDetails.setDrawnAtBank(drawnAtBank);
    Log.log(5, "RPAction", methodName, "drawn at branch " + drawnAtBranch);
    paymentDetails.setDrawnAtBranch(drawnAtBranch);
    Log.log(5, "RPAction", methodName, "payable at " + payableAt);
    paymentDetails.setPayableAt(payableAt);
    Log.log(5, "RPAction", methodName, "acc num " + accNumber);
    paymentDetails.setCgtsiAccNumber(accNumber);
    Map appropriatedFlags = actionForm.getAppropriatedFlags();
    Map cgpans = actionForm.getCgpans();
    Map danCgpanDetails = actionForm.getDanPanDetails();
    paymentId = rpProcessor.allocateCGDAN(paymentDetails, 
        appropriatedFlags, cgpans, danCgpanDetails, user);
    request.setAttribute("message", 
        "Payment Allocated Successfully.<BR>Payment ID : " + paymentId + 
        " for Rs." + paymentDetails.getInstrumentAmount());
    Log.log(5, "RPAction", methodName, "Exited");
    return mapping.findForward("success");
  }
  
  public ActionForward getPendingASFDANsExpired(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm rpForm = (RPActionForm)form;
    User user = getUserInformation(request);
    String bankId = user.getBankId();
    String zoneId = user.getZoneId();
    String branchId = user.getBranchId();
    String memberId = "";
    if (rpForm.getDanSummaries() != null)
      rpForm.getDanSummaries().clear(); 
    if (rpForm.getDanPanDetails() != null)
      rpForm.getDanPanDetails().clear(); 
    if (rpForm.getCgpans() != null)
      rpForm.getCgpans().clear(); 
    if (rpForm.getAllocatedFlags() != null)
      rpForm.getAllocatedFlags().clear(); 
    if (rpForm.getFirstDisbursementDates() != null)
      rpForm.getFirstDisbursementDates().clear(); 
    if (rpForm.getNotAllocatedReasons() != null)
      rpForm.getNotAllocatedReasons().clear(); 
    if (rpForm.getAllocatedFlags() != null)
      rpForm.getAllocatedFlags().clear(); 
    if (rpForm.getAppropriatedFlags() != null)
      rpForm.getAppropriatedFlags().clear(); 
    if (rpForm.getAmountsRaised() != null)
      rpForm.getAmountsRaised().clear(); 
    if (rpForm.getAmountBeingPaid() != null)
      rpForm.getAmountBeingPaid().clear(); 
    if (bankId.equals("0000")) {
      memberId = rpForm.getSelectMember();
      Log.log(5, "RPAction", "getNEFTPendingGFDANs", "mliId = " + 
          memberId);
      if (memberId == null || memberId.equals(""))
        return mapping.findForward("expiredasfmember"); 
      bankId = memberId.substring(0, 4);
      zoneId = memberId.substring(4, 8);
      branchId = memberId.substring(8, 12);
      ClaimsProcessor cpProcessor = new ClaimsProcessor();
      Vector memberIds = cpProcessor.getAllMemberIds();
      if (!memberIds.contains(memberId))
        throw new NoMemberFoundException("The Member ID does not exist"); 
    } 
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList<DANSummary> danSummaries = rpProcessor.displayExpiredASFDANs(bankId, 
        zoneId, branchId);
    Log.log(5, "RPAction", "getNEFTPendingGFDANs", "dan summary size : " + 
        danSummaries.size());
    if (danSummaries.size() == 0) {
      rpForm.setSelectMember(null);
      throw new MissingDANDetailsException(
          "No DANs available for Allocation");
    } 
    boolean isDanAvailable = false;
    for (int i = 0; i < danSummaries.size(); i++) {
      DANSummary danSummary = danSummaries.get(i);
      if (danSummary.getAmountDue() != danSummary.getAmountPaid()) {
        isDanAvailable = true;
        break;
      } 
    } 
    if (!isDanAvailable) {
      rpForm.setSelectMember(null);
      throw new MissingDANDetailsException(
          "No DANs available for Allocation.");
    } 
    rpForm.setDanSummaries(danSummaries);
    rpForm.setBankId(bankId);
    rpForm.setZoneId(zoneId);
    rpForm.setBranchId(branchId);
    Log.log(4, "RPAction", "getNEFTPendingGFDANs", "Exited");
    if (rpForm.getSelectMember() != null) {
      rpForm.setMemberId(rpForm.getSelectMember());
    } else {
      rpForm.setMemberId(String.valueOf(bankId) + zoneId + branchId);
    } 
    rpForm.setSelectMember(null);
    return mapping.findForward("expiredasfdansummary");
  }
  
  public ActionForward getExpiredASFDANsPaymentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    double totalAmount = 0.0D;
    StringTokenizer tokenizer = null;
    double tot = 0.0D;
    Map appropriatedCases = actionForm.getAppropriatedFlags();
    if (appropriatedCases.size() < 1)
      throw new MissingDANDetailsException(
          "Please select atleast one dan for allocation ."); 
    Set appropriatedCasesSet = appropriatedCases.keySet();
    Iterator<String> appropriatedCasesIterator = appropriatedCasesSet.iterator();
    String token = null;
    String token1 = null;
    float total = 0.0F;
    float total2 = 0.0F;
    while (appropriatedCasesIterator.hasNext()) {
      String key = appropriatedCasesIterator.next();
      tokenizer = new StringTokenizer(key, "#");
      for (; tokenizer
        .hasMoreTokens(); System.out.println(total2)) {
        token = tokenizer.nextToken();
        token1 = tokenizer.nextToken();
        total = Integer.parseInt(token1);
        total2 += total;
      } 
    } 
    Registration registration = new Registration();
    Log.log(5, 
        "RPAction", 
        "getExpiredASFDANsPaymentDetails", 
        "member id " + 
        actionForm.getMemberId());
    CollectingBank collectingBank = registration
      .getCollectingBank("(" + 
        actionForm.getMemberId() + ")");
    Log.log(5, 
        "RPAction", 
        "getExpiredASFDANsPaymentDetails", 
        "collectingBank " + 
        collectingBank);
    actionForm.setModeOfPayment("");
    actionForm.setPaymentDate(null);
    actionForm.setInstrumentNo("");
    actionForm.setInstrumentDate(null);
    actionForm.setDrawnAtBank("");
    actionForm.setDrawnAtBranch("");
    actionForm.setPayableAt("");
    IFProcessor ifProcessor = new IFProcessor();
    ArrayList instruments = ifProcessor.getInstrumentTypes("G");
    actionForm.setInstruments(instruments);
    actionForm.setCollectingBank(collectingBank.getCollectingBankId());
    actionForm
      .setCollectingBankName(collectingBank.getCollectingBankName());
    actionForm.setCollectingBankBranch(collectingBank.getBranchName());
    actionForm.setAccountNumber(collectingBank.getAccNo());
    actionForm.setInstrumentAmount(total2);
    return mapping.findForward("expiredasfdanpaymentdetails");
  }
  
  public ActionForward getPendingGFDANsFilterForPaymentGateway(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    User user = getUserInformation(request);
    String bankId = user.getBankId();
    RPActionForm rpActionForm = (RPActionForm)form;
    rpActionForm.setAllocationType("G");
    HttpSession session = request.getSession(false);
    if (bankId.equalsIgnoreCase("0000")) {
      rpActionForm.setSelectMember("");
      session.setAttribute("TARGET_URL", 
          "selectGFMember.do?method=getPendingGFDANsForPaymentGateway");
      return mapping.findForward("memberInfo");
    } 
    request.setAttribute("pageValue", "1");
    getPendingGFDANs(mapping, form, request, response);
    return mapping.findForward("danSummary");
  }
  
  public ActionForward getPendingGFDANsForPaymentGateway(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "getPendingGFDANs", "Entered");
    User user = getUserInformation(request);
    String bankId = user.getBankId();
    String zoneId = user.getZoneId();
    String branchId = user.getBranchId();
    String memberId = "";
    RPActionForm actionForm = (RPActionForm)form;
    HttpSession session = request.getSession(false);
    if (actionForm.getDanSummaries() != null)
      actionForm.getDanSummaries().clear(); 
    if (actionForm.getDanPanDetails() != null)
      actionForm.getDanPanDetails().clear(); 
    if (actionForm.getCgpans() != null)
      actionForm.getCgpans().clear(); 
    if (actionForm.getAllocatedFlags() != null)
      actionForm.getAllocatedFlags().clear(); 
    if (actionForm.getFirstDisbursementDates() != null)
      actionForm.getFirstDisbursementDates().clear(); 
    if (actionForm.getNotAllocatedReasons() != null)
      actionForm.getNotAllocatedReasons().clear(); 
    if (actionForm.getAppropriatedFlags() != null)
      actionForm.getAppropriatedFlags().clear(); 
    Log.log(5, "RPAction", "getPendingGFDANs", "Bank Id : " + bankId);
    Log.log(5, "RPAction", "getPendingGFDANs", "Zone Id : " + zoneId);
    Log.log(5, "RPAction", "getPendingGFDANs", "Branch Id : " + branchId);
    if (bankId.equals("0000")) {
      memberId = actionForm.getSelectMember();
      if (memberId == null || memberId.equals(""))
        memberId = actionForm.getMemberId(); 
      Log.log(5, "RPAction", "getPendingGFDANs", "mliId = " + memberId);
      if (memberId == null || memberId.equals("")) {
        session.setAttribute("TARGET_URL", 
            "selectGFMember.do?method=getPendingGFDANs");
        return mapping.findForward("memberInfo");
      } 
      bankId = memberId.substring(0, 4);
      zoneId = memberId.substring(4, 8);
      branchId = memberId.substring(8, 12);
      ClaimsProcessor cpProcessor = new ClaimsProcessor();
      Vector memberIds = cpProcessor.getAllMemberIds();
      if (!memberIds.contains(memberId))
        throw new NoMemberFoundException("The Member ID does not exist"); 
    } 
    Log.log(5, "RPAction", "getPendingGFDANs", 
        "Selected Bank Id,zone and branch ids : " + bankId + "," + 
        zoneId + "," + branchId);
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList<DANSummary> danSummaries = rpProcessor.displayGFDANs(bankId, zoneId, 
        branchId);
    Log.log(5, "RPAction", "getPendingGFDANs", "dan summary size : " + 
        danSummaries.size());
    if (danSummaries.size() == 0) {
      actionForm.setSelectMember(null);
      throw new MissingDANDetailsException(
          "No DANs available for Allocation");
    } 
    boolean isDanAvailable = false;
    for (int i = 0; i < danSummaries.size(); i++) {
      DANSummary danSummary = danSummaries.get(i);
      if (danSummary.getAmountDue() != danSummary.getAmountPaid()) {
        isDanAvailable = true;
        break;
      } 
    } 
    if (!isDanAvailable) {
      actionForm.setSelectMember(null);
      throw new MissingDANDetailsException(
          "No DANs available for Allocation");
    } 
    actionForm.setDanSummaries(danSummaries);
    actionForm.setBankId(bankId);
    actionForm.setZoneId(zoneId);
    actionForm.setBranchId(branchId);
    Log.log(4, "RPAction", "getPendingGFDANs", "Exited");
    if (actionForm.getSelectMember() != null) {
      actionForm.setMemberId(actionForm.getSelectMember());
    } else {
      actionForm.setMemberId(String.valueOf(bankId) + zoneId + branchId);
    } 
    actionForm.setSelectMember(null);
    return mapping.findForward("danSummary2");
  }
  
  public RPAction() {
    $init$();
  }
  
  public ActionForward getOnlineAFDANsLive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm rpForm = (RPActionForm)form;
    User user = getUserInformation(request);
    String bankId = user.getBankId();
    String zoneId = user.getZoneId();
    String branchId = user.getBranchId();
    String memberId = "";
    if (rpForm.getDanSummaries() != null)
      rpForm.getDanSummaries().clear(); 
    if (rpForm.getDanPanDetails() != null)
      rpForm.getDanPanDetails().clear(); 
    if (rpForm.getCgpans() != null)
      rpForm.getCgpans().clear(); 
    if (rpForm.getAllocatedFlags() != null)
      rpForm.getAllocatedFlags().clear(); 
    if (rpForm.getFirstDisbursementDates() != null)
      rpForm.getFirstDisbursementDates().clear(); 
    if (rpForm.getNotAllocatedReasons() != null)
      rpForm.getNotAllocatedReasons().clear(); 
    if (rpForm.getAllocatedFlags() != null)
      rpForm.getAllocatedFlags().clear(); 
    if (rpForm.getAppropriatedFlags() != null)
      rpForm.getAppropriatedFlags().clear(); 
    if (rpForm.getAmountsRaised() != null)
      rpForm.getAmountsRaised().clear(); 
    if (rpForm.getAmountBeingPaid() != null)
      rpForm.getAmountBeingPaid().clear(); 
    if (bankId.equals("0000")) {
      memberId = rpForm.getSelectMember();
      Log.log(5, "RPAction", "getOnlineAFDANsLive", "mliId = " + 
          memberId);
      if (memberId == null || memberId.equals(""))
        return mapping.findForward("OnlineLiveAFInfo"); 
      bankId = memberId.substring(0, 4);
      zoneId = memberId.substring(4, 8);
      branchId = memberId.substring(8, 12);
      ClaimsProcessor cpProcessor = new ClaimsProcessor();
      Vector memberIds = cpProcessor.getAllMemberIds();
      if (!memberIds.contains(memberId))
        throw new NoMemberFoundException("The Member ID does not exist"); 
    } 
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList<DANSummary> danSummaries = rpProcessor.displayOnlineLiveAFDANs(bankId, zoneId, branchId);
    Log.log(5, "RPAction", "getOnlineAFDANsLive", "dan summary size : " + 
        danSummaries.size());
    if (danSummaries.size() == 0) {
      rpForm.setSelectMember(null);
      throw new MissingDANDetailsException(
          "No DANs available for Allocation");
    } 
    boolean isDanAvailable = false;
    for (int i = 0; i < danSummaries.size(); i++) {
      DANSummary danSummary = danSummaries.get(i);
      if (danSummary.getAmountDue() != danSummary.getAmountPaid()) {
        isDanAvailable = true;
        break;
      } 
    } 
    if (!isDanAvailable) {
      rpForm.setSelectMember(null);
      throw new MissingDANDetailsException(
          "No DANs available for Allocation");
    } 
    rpForm.setDanSummaries(danSummaries);
    rpForm.setBankId(bankId);
    rpForm.setZoneId(zoneId);
    rpForm.setBranchId(branchId);
    Log.log(4, "RPAction", "getOnlineAFDANsLive", "Exited");
    if (rpForm.getSelectMember() != null) {
      rpForm.setMemberId(rpForm.getSelectMember());
    } else {
      rpForm.setMemberId(String.valueOf(bankId) + zoneId + branchId);
    } 
    rpForm.setSelectMember(null);
    return mapping.findForward("OnlineLiveAFSummary");
  }
  
  public ActionForward getOnlineDANsLiveForAll(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    System.out.println("ENTERED_++++++++++++++++++====");
    RPActionForm rpForm = (RPActionForm)form;
    User user = getUserInformation(request);
    String bankId = user.getBankId();
    String zoneId = user.getZoneId();
    String branchId = user.getBranchId();
    String memberId = "";
    String dantype = request.getParameter("mode").substring(0, 2);
    String danLiveType = request.getParameter("mode").substring(2, 5);
    System.out.println("dantype===RRRRRRRRR" + dantype);
    System.out.println("danLiveType===AAAAAAAAAAAAAA" + danLiveType);
    HttpSession session = request.getSession();
    System.out.println("user" + user);
    String bankid = user.getBankId().trim();
    String zoneid = user.getZoneId().trim();
    String branchid = user.getBranchId().trim();
    String Zoneids = String.valueOf(zoneid) + branchid;
    ArrayList<DANSummary> danSummaries = new ArrayList();
    if (rpForm.getDanSummaries() != null)
      rpForm.getDanSummaries().clear(); 
    if (rpForm.getDanPanDetails() != null)
      rpForm.getDanPanDetails().clear(); 
    if (rpForm.getCgpans() != null)
      rpForm.getCgpans().clear(); 
    if (rpForm.getAllocatedFlags() != null)
      rpForm.getAllocatedFlags().clear(); 
    if (rpForm.getFirstDisbursementDates() != null)
      rpForm.getFirstDisbursementDates().clear(); 
    if (rpForm.getNotAllocatedReasons() != null)
      rpForm.getNotAllocatedReasons().clear(); 
    if (rpForm.getAllocatedFlags() != null)
      rpForm.getAllocatedFlags().clear(); 
    if (rpForm.getAppropriatedFlags() != null)
      rpForm.getAppropriatedFlags().clear(); 
    if (rpForm.getAmountsRaised() != null)
      rpForm.getAmountsRaised().clear(); 
    if (rpForm.getAmountBeingPaid() != null)
      rpForm.getAmountBeingPaid().clear(); 
    if (dantype.equals("AF")) {
      RpProcessor rpProcessor = new RpProcessor();
      danSummaries = rpProcessor.displayOnlineLiveAFDANs(bankId, zoneId, branchId);
    } 
    if (dantype.equals("TF")) {
      RpProcessor rpProcessor = new RpProcessor();
      danSummaries = rpProcessor.displayOnlineLiveDANsTN(bankId, zoneId, branchId);
    } 
    if (dantype.equals("RF")) {
      RpProcessor rpProcessor = new RpProcessor();
      danSummaries = rpProcessor.displayOnlineLiveDANsRV(bankId, zoneId, branchId);
    } 
    if (dantype.equals("RO")) {
      RpProcessor rpProcessor = new RpProcessor();
      danSummaries = rpProcessor.displayOnlineLiveDANsRO(bankId, zoneId, branchId);
    } 
    Log.log(5, "RPAction", "getOnlineAFDANsLive", "dan summary size : " + 
        danSummaries.size());
    if (danSummaries.size() == 0) {
      rpForm.setSelectMember(null);
      throw new MissingDANDetailsException(
          "No DANs available for Allocation");
    } 
    boolean isDanAvailable = false;
    for (int i = 0; i < danSummaries.size(); i++) {
      DANSummary danSummary = danSummaries.get(i);
      if (danSummary.getAmountDue() != danSummary.getAmountPaid()) {
        isDanAvailable = true;
        break;
      } 
    } 
    if (!isDanAvailable) {
      rpForm.setSelectMember(null);
      throw new MissingDANDetailsException(
          "No DANs available for Allocation");
    } 
    rpForm.setDanSummaries(danSummaries);
    rpForm.setBankId(bankId);
    rpForm.setZoneId(zoneId);
    rpForm.setBranchId(branchId);
    Log.log(4, "RPAction", "getOnlineAFDANsLive", "Exited");
    if (rpForm.getSelectMember() != null) {
      rpForm.setMemberId(rpForm.getSelectMember());
    } else {
      rpForm.setMemberId(String.valueOf(bankId) + zoneId + branchId);
    } 
    rpForm.setSelectMember(null);
    String forwardJsp = "";
    if (dantype.equals("AF")) {
      forwardJsp = "AFOnlineLiveSummary";
      System.out.println("RAAJJJ");
    } 
    if (dantype.equals("TF")) {
      forwardJsp = "TFOnlineLiveSummary";
      System.out.println("KKKKK====TN");
    } 
    if (dantype.equals("RF")) {
      forwardJsp = "RFOnlineLiveSummary";
      System.out.println("UUUUUUURV");
    } 
    if (dantype.equals("RO")) {
      forwardJsp = "ROOnlineLiveSummary";
      System.out.println("UUUUUUURV");
    } 
    return mapping.findForward(forwardJsp);
  }
  
  public ActionForward getOnlineAFDANsLiveNew(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm rpForm = (RPActionForm)form;
    User user = getUserInformation(request);
    String bankId = user.getBankId();
    String zoneId = user.getZoneId();
    String branchId = user.getBranchId();
    String memberId = "";
    if (rpForm.getDanSummaries() != null)
      rpForm.getDanSummaries().clear(); 
    if (rpForm.getDanPanDetails() != null)
      rpForm.getDanPanDetails().clear(); 
    if (rpForm.getCgpans() != null)
      rpForm.getCgpans().clear(); 
    if (rpForm.getAllocatedFlags() != null)
      rpForm.getAllocatedFlags().clear(); 
    if (rpForm.getFirstDisbursementDates() != null)
      rpForm.getFirstDisbursementDates().clear(); 
    if (rpForm.getNotAllocatedReasons() != null)
      rpForm.getNotAllocatedReasons().clear(); 
    if (rpForm.getAllocatedFlags() != null)
      rpForm.getAllocatedFlags().clear(); 
    if (rpForm.getAppropriatedFlags() != null)
      rpForm.getAppropriatedFlags().clear(); 
    if (rpForm.getAmountsRaised() != null)
      rpForm.getAmountsRaised().clear(); 
    if (rpForm.getAmountBeingPaid() != null)
      rpForm.getAmountBeingPaid().clear(); 
    if (bankId.equals("0000")) {
      memberId = rpForm.getSelectMember();
      Log.log(5, "RPAction", "getOnlineAFDANsLive", "mliId = " + 
          memberId);
      if (memberId == null || memberId.equals(""))
        return mapping.findForward("OnlineLiveInfoNew"); 
      bankId = memberId.substring(0, 4);
      zoneId = memberId.substring(4, 8);
      branchId = memberId.substring(8, 12);
      ClaimsProcessor cpProcessor = new ClaimsProcessor();
      Vector memberIds = cpProcessor.getAllMemberIds();
      if (!memberIds.contains(memberId))
        throw new NoMemberFoundException("The Member ID does not exist"); 
    } 
    return mapping.findForward("OnlineLiveInfoNew");
  }
  
  public ActionForward getOnlineAFDANsExpired(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm rpForm = (RPActionForm)form;
    User user = getUserInformation(request);
    String bankId = user.getBankId();
    String zoneId = user.getZoneId();
    String branchId = user.getBranchId();
    String memberId = "";
    if (rpForm.getDanSummaries() != null)
      rpForm.getDanSummaries().clear(); 
    if (rpForm.getDanPanDetails() != null)
      rpForm.getDanPanDetails().clear(); 
    if (rpForm.getCgpans() != null)
      rpForm.getCgpans().clear(); 
    if (rpForm.getAllocatedFlags() != null)
      rpForm.getAllocatedFlags().clear(); 
    if (rpForm.getFirstDisbursementDates() != null)
      rpForm.getFirstDisbursementDates().clear(); 
    if (rpForm.getNotAllocatedReasons() != null)
      rpForm.getNotAllocatedReasons().clear(); 
    if (rpForm.getAllocatedFlags() != null)
      rpForm.getAllocatedFlags().clear(); 
    if (rpForm.getAppropriatedFlags() != null)
      rpForm.getAppropriatedFlags().clear(); 
    if (rpForm.getAmountsRaised() != null)
      rpForm.getAmountsRaised().clear(); 
    if (rpForm.getAmountBeingPaid() != null)
      rpForm.getAmountBeingPaid().clear(); 
    if (bankId.equals("0000")) {
      memberId = rpForm.getSelectMember();
      Log.log(5, "RPAction", "getOnlineAFDANsExpired", "mliId = " + 
          memberId);
      if (memberId == null || memberId.equals(""))
        return mapping.findForward("OnlineExpiredAFInfo"); 
      bankId = memberId.substring(0, 4);
      zoneId = memberId.substring(4, 8);
      branchId = memberId.substring(8, 12);
      ClaimsProcessor cpProcessor = new ClaimsProcessor();
      Vector memberIds = cpProcessor.getAllMemberIds();
      if (!memberIds.contains(memberId))
        throw new NoMemberFoundException("The Member ID does not exist"); 
    } 
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList<DANSummary> danSummaries = rpProcessor.displayOnlineExpiredAFDANs(bankId, zoneId, 
        branchId);
    Log.log(5, "RPAction", "getOnlineAFDANsExpired", "dan summary size : " + 
        danSummaries.size());
    if (danSummaries.size() == 0) {
      rpForm.setSelectMember(null);
      throw new MissingDANDetailsException("No DANs available for Allocation");
    } 
    boolean isDanAvailable = false;
    for (int i = 0; i < danSummaries.size(); i++) {
      DANSummary danSummary = danSummaries.get(i);
      if (danSummary.getAmountDue() != danSummary.getAmountPaid()) {
        isDanAvailable = true;
        break;
      } 
    } 
    if (!isDanAvailable) {
      rpForm.setSelectMember(null);
      throw new MissingDANDetailsException(
          "No DANs available for Allocation");
    } 
    rpForm.setDanSummaries(danSummaries);
    rpForm.setBankId(bankId);
    rpForm.setZoneId(zoneId);
    rpForm.setBranchId(branchId);
    Log.log(4, "RPAction", "getOnlineAFDANsExpired", "Exited");
    if (rpForm.getSelectMember() != null) {
      rpForm.setMemberId(rpForm.getSelectMember());
    } else {
      rpForm.setMemberId(String.valueOf(bankId) + zoneId + branchId);
    } 
    rpForm.setSelectMember(null);
    return mapping.findForward("OnlineExpiredAFSummary");
  }
  
  public ActionForward getOnlineSFDANsLive(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm rpForm = (RPActionForm)form;
    User user = getUserInformation(request);
    String bankId = user.getBankId();
    String zoneId = user.getZoneId();
    String branchId = user.getBranchId();
    String memberId = "";
    if (rpForm.getDanSummaries() != null)
      rpForm.getDanSummaries().clear(); 
    if (rpForm.getDanPanDetails() != null)
      rpForm.getDanPanDetails().clear(); 
    if (rpForm.getCgpans() != null)
      rpForm.getCgpans().clear(); 
    if (rpForm.getAllocatedFlags() != null)
      rpForm.getAllocatedFlags().clear(); 
    if (rpForm.getFirstDisbursementDates() != null)
      rpForm.getFirstDisbursementDates().clear(); 
    if (rpForm.getNotAllocatedReasons() != null)
      rpForm.getNotAllocatedReasons().clear(); 
    if (rpForm.getAllocatedFlags() != null)
      rpForm.getAllocatedFlags().clear(); 
    if (rpForm.getAppropriatedFlags() != null)
      rpForm.getAppropriatedFlags().clear(); 
    if (rpForm.getAmountsRaised() != null)
      rpForm.getAmountsRaised().clear(); 
    if (rpForm.getAmountBeingPaid() != null)
      rpForm.getAmountBeingPaid().clear(); 
    if (bankId.equals("0000")) {
      memberId = rpForm.getSelectMember();
      Log.log(5, "RPAction", "getOnlineSFDANsLive", "mliId = " + 
          memberId);
      if (memberId == null || memberId.equals(""))
        return mapping.findForward("OnlineLiveSFInfo"); 
      bankId = memberId.substring(0, 4);
      zoneId = memberId.substring(4, 8);
      branchId = memberId.substring(8, 12);
      ClaimsProcessor cpProcessor = new ClaimsProcessor();
      Vector memberIds = cpProcessor.getAllMemberIds();
      if (!memberIds.contains(memberId))
        throw new NoMemberFoundException("The Member ID does not exist"); 
    } 
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList<DANSummary> danSummaries = rpProcessor.displayOnlineLiveSFDANs(bankId, zoneId, 
        branchId);
    Log.log(5, "RPAction", "getOnlineSFDANsLive", "dan summary size : " + 
        danSummaries.size());
    if (danSummaries.size() == 0) {
      rpForm.setSelectMember(null);
      throw new MissingDANDetailsException(
          "No DANs available for Allocation");
    } 
    boolean isDanAvailable = false;
    for (int i = 0; i < danSummaries.size(); i++) {
      DANSummary danSummary = danSummaries.get(i);
      System.out.println("danSummary.getDanId():" + danSummary.getDanId() + 
          "--danSummary.getAmountDue():" + 
          danSummary.getAmountDue() + 
          "--danSummary.getAmountPaid():" + 
          danSummary.getAmountPaid());
      if (danSummary.getAmountDue() != danSummary.getAmountPaid()) {
        isDanAvailable = true;
        break;
      } 
    } 
    if (!isDanAvailable) {
      rpForm.setSelectMember(null);
      throw new MissingDANDetailsException(
          "No DANs available for Allocation");
    } 
    rpForm.setDanSummaries(danSummaries);
    rpForm.setBankId(bankId);
    rpForm.setZoneId(zoneId);
    rpForm.setBranchId(branchId);
    Log.log(4, "RPAction", "getOnlineSFDANsLive", "Exited");
    if (rpForm.getSelectMember() != null) {
      rpForm.setMemberId(rpForm.getSelectMember());
    } else {
      rpForm.setMemberId(String.valueOf(bankId) + zoneId + branchId);
    } 
    rpForm.setSelectMember(null);
    return mapping.findForward("OnlineLiveSFSummary");
  }
  
  public ActionForward getOnlineSFDANsExpired(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm rpForm = (RPActionForm)form;
    User user = getUserInformation(request);
    String bankId = user.getBankId();
    String zoneId = user.getZoneId();
    String branchId = user.getBranchId();
    String memberId = "";
    if (rpForm.getDanSummaries() != null)
      rpForm.getDanSummaries().clear(); 
    if (rpForm.getDanPanDetails() != null)
      rpForm.getDanPanDetails().clear(); 
    if (rpForm.getCgpans() != null)
      rpForm.getCgpans().clear(); 
    if (rpForm.getAllocatedFlags() != null)
      rpForm.getAllocatedFlags().clear(); 
    if (rpForm.getFirstDisbursementDates() != null)
      rpForm.getFirstDisbursementDates().clear(); 
    if (rpForm.getNotAllocatedReasons() != null)
      rpForm.getNotAllocatedReasons().clear(); 
    if (rpForm.getAllocatedFlags() != null)
      rpForm.getAllocatedFlags().clear(); 
    if (rpForm.getAppropriatedFlags() != null)
      rpForm.getAppropriatedFlags().clear(); 
    if (rpForm.getAmountsRaised() != null)
      rpForm.getAmountsRaised().clear(); 
    if (rpForm.getAmountBeingPaid() != null)
      rpForm.getAmountBeingPaid().clear(); 
    if (bankId.equals("0000")) {
      memberId = rpForm.getSelectMember();
      Log.log(5, "RPAction", "getOnlineSFDANsExpired", "mliId = " + 
          memberId);
      if (memberId == null || memberId.equals(""))
        return mapping.findForward("OnlineExpiredSFInfo"); 
      bankId = memberId.substring(0, 4);
      zoneId = memberId.substring(4, 8);
      branchId = memberId.substring(8, 12);
      ClaimsProcessor cpProcessor = new ClaimsProcessor();
      Vector memberIds = cpProcessor.getAllMemberIds();
      if (!memberIds.contains(memberId))
        throw new NoMemberFoundException("The Member ID does not exist"); 
    } 
    RpProcessor rpProcessor = new RpProcessor();
    ArrayList<DANSummary> danSummaries = rpProcessor.displayOnlineExpiredSFDANs(bankId, 
        zoneId, branchId);
    Log.log(5, "RPAction", "getOnlineSFDANsExpired", "dan summary size : " + 
        danSummaries.size());
    if (danSummaries.size() == 0) {
      rpForm.setSelectMember(null);
      throw new MissingDANDetailsException(
          "No DANs available for Allocation");
    } 
    boolean isDanAvailable = false;
    for (int i = 0; i < danSummaries.size(); i++) {
      DANSummary danSummary = danSummaries.get(i);
      if (danSummary.getAmountDue() != danSummary.getAmountPaid()) {
        isDanAvailable = true;
        break;
      } 
    } 
    if (!isDanAvailable) {
      rpForm.setSelectMember(null);
      throw new MissingDANDetailsException(
          "No DANs available for Allocation.");
    } 
    rpForm.setDanSummaries(danSummaries);
    rpForm.setBankId(bankId);
    rpForm.setZoneId(zoneId);
    rpForm.setBranchId(branchId);
    Log.log(4, "RPAction", "getOnlineSFDANsExpired", "Exited");
    if (rpForm.getSelectMember() != null) {
      rpForm.setMemberId(rpForm.getSelectMember());
    } else {
      rpForm.setMemberId(String.valueOf(bankId) + zoneId + branchId);
    } 
    rpForm.setSelectMember(null);
    return mapping.findForward("OnlineExpiredSFSummary");
  }
  
  public ActionForward allocategetLiveAFDANsPaymentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    double totalAmount = 0.0D;
    StringTokenizer tokenizer = null;
    Map appropriatedCases = actionForm.getAppropriatedFlags();
    if (appropriatedCases.size() < 1)
      throw new MissingDANDetailsException(
          "Please select atleast one dan for allocation."); 
    if (appropriatedCases.size() >= 1000)
      throw new NoMemberFoundException("Please select maximum  1000 dans for Allocation"); 
   /// System.out.println("allocategetLiveAFDANsPaymentDetails============");
    Set appropriatedCasesSet = appropriatedCases.keySet();
    Iterator<String> appropriatedCasesIterator = appropriatedCasesSet.iterator();
    String token = null;
    String token1 = null;
    float total = 0.0F;
    float total2 = 0.0F;
    while (appropriatedCasesIterator.hasNext()) {
      String key = appropriatedCasesIterator.next();
      tokenizer = new StringTokenizer(key, "#");
      for (; tokenizer
        .hasMoreTokens(); System.out.println(total2)) {
        token = tokenizer.nextToken();
        token1 = tokenizer.nextToken();
        total = Integer.parseInt(token1);
        total2 += total;
      } 
    } 
    User user = getUserInformation(request);
    String bankid = user.getBankId().trim();
    String zoneid = user.getZoneId().trim();
    String branchid = user.getBranchId().trim();
    String memberId = String.valueOf(bankid) + zoneid + branchid;
    Registration registration = new Registration();
    CollectingBank collectingBank = registration.getCollectingBank(memberId);
    actionForm.setModeOfPayment("");
    actionForm.setPaymentDate(null);
    actionForm.setInstrumentNo("");
    actionForm.setInstrumentDate(null);
    actionForm.setDrawnAtBank("");
    actionForm.setDrawnAtBranch("");
    actionForm.setPayableAt("");
    IFProcessor ifProcessor = new IFProcessor();
    ArrayList instruments = ifProcessor.getInstrumentTypes("G");
    actionForm.setInstruments(instruments);
    actionForm.setCollectingBank(collectingBank.getCollectingBankId());
    actionForm.setCollectingBankName(collectingBank.getCollectingBankName());
    actionForm.setCollectingBankBranch(collectingBank.getBranchName());
    actionForm.setAccountNumber(collectingBank.getAccNo());
    actionForm.setInstrumentAmount(total2);
    return mapping.findForward("allocateliveafdanpaymentdetails");
  }
  
  public ActionForward allocategetTNDANsPaymentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    double totalAmount = 0.0D;
    StringTokenizer tokenizer = null;
    Map appropriatedCases = actionForm.getAppropriatedFlags();
    if (appropriatedCases.size() < 1)
      throw new MissingDANDetailsException(
          "Please select atleast one dan for allocation."); 
    if (appropriatedCases.size() >= 1000)
      throw new NoMemberFoundException("Please select maximum  1000 dans for Allocation"); 
    Set appropriatedCasesSet = appropriatedCases.keySet();
    Iterator<String> appropriatedCasesIterator = appropriatedCasesSet.iterator();
    String token = null;
    String token1 = null;
    float total = 0.0F;
    float total2 = 0.0F;
    while (appropriatedCasesIterator.hasNext()) {
      String key = appropriatedCasesIterator.next();
      tokenizer = new StringTokenizer(key, "#");
      for (; tokenizer
        .hasMoreTokens(); System.out.println(total2)) {
        token = tokenizer.nextToken();
        token1 = tokenizer.nextToken();
        total = Integer.parseInt(token1);
        total2 += total;
      } 
    } 
    User user = getUserInformation(request);
    String bankid = user.getBankId().trim();
    String zoneid = user.getZoneId().trim();
    String branchid = user.getBranchId().trim();
    String memberId = String.valueOf(bankid) + zoneid + branchid;
    Registration registration = new Registration();
    CollectingBank collectingBank = registration.getCollectingBank(memberId);
    actionForm.setModeOfPayment("");
    actionForm.setPaymentDate(null);
    actionForm.setInstrumentNo("");
    actionForm.setInstrumentDate(null);
    actionForm.setDrawnAtBank("");
    actionForm.setDrawnAtBranch("");
    actionForm.setPayableAt("");
    IFProcessor ifProcessor = new IFProcessor();
    ArrayList instruments = ifProcessor.getInstrumentTypes("G");
    actionForm.setInstruments(instruments);
    actionForm.setCollectingBank(collectingBank.getCollectingBankId());
    actionForm.setCollectingBankName(collectingBank.getCollectingBankName());
    actionForm.setCollectingBankBranch(collectingBank.getBranchName());
    actionForm.setAccountNumber(collectingBank.getAccNo());
    actionForm.setInstrumentAmount(total2);
    return mapping.findForward("allocatelivetndanpaymentdetails");
  }
  
  public ActionForward allocategetRVDANsPaymentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    double totalAmount = 0.0D;
    StringTokenizer tokenizer = null;
    Map appropriatedCases = actionForm.getAppropriatedFlags();
    if (appropriatedCases.size() < 1)
      throw new MissingDANDetailsException(
          "Please select atleast one dan for allocation."); 
    if (appropriatedCases.size() >= 1000)
      throw new NoMemberFoundException("Please select maximum  1000 dans for Allocation"); 
    Set appropriatedCasesSet = appropriatedCases.keySet();
    Iterator<String> appropriatedCasesIterator = appropriatedCasesSet.iterator();
    String token = null;
    String token1 = null;
    float total = 0.0F;
    float total2 = 0.0F;
    while (appropriatedCasesIterator.hasNext()) {
      String key = appropriatedCasesIterator.next();
      tokenizer = new StringTokenizer(key, "#");
      for (; tokenizer
        .hasMoreTokens(); System.out.println(total2)) {
        token = tokenizer.nextToken();
        token1 = tokenizer.nextToken();
        total = Integer.parseInt(token1);
        total2 += total;
      } 
    } 
    User user = getUserInformation(request);
    String bankid = user.getBankId().trim();
    String zoneid = user.getZoneId().trim();
    String branchid = user.getBranchId().trim();
    String memberId = String.valueOf(bankid) + zoneid + branchid;
    Registration registration = new Registration();
    CollectingBank collectingBank = registration.getCollectingBank(memberId);
    actionForm.setModeOfPayment("");
    actionForm.setPaymentDate(null);
    actionForm.setInstrumentNo("");
    actionForm.setInstrumentDate(null);
    actionForm.setDrawnAtBank("");
    actionForm.setDrawnAtBranch("");
    actionForm.setPayableAt("");
    IFProcessor ifProcessor = new IFProcessor();
    ArrayList instruments = ifProcessor.getInstrumentTypes("G");
    actionForm.setInstruments(instruments);
    actionForm.setCollectingBank(collectingBank.getCollectingBankId());
    actionForm.setCollectingBankName(collectingBank.getCollectingBankName());
    actionForm.setCollectingBankBranch(collectingBank.getBranchName());
    actionForm.setAccountNumber(collectingBank.getAccNo());
    actionForm.setInstrumentAmount(total2);
    return mapping.findForward("allocatelivervdanpaymentdetails");
  }
  
  public ActionForward allocategetRODANsPaymentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    double totalAmount = 0.0D;
    StringTokenizer tokenizer = null;
    Map appropriatedCases = actionForm.getAppropriatedFlags();
    if (appropriatedCases.size() < 1)
      throw new MissingDANDetailsException(
          "Please select atleast one dan for allocation."); 
    if (appropriatedCases.size() >= 1000)
      throw new NoMemberFoundException("Please select maximum  1000 dans for Allocation"); 
    Set appropriatedCasesSet = appropriatedCases.keySet();
    Iterator<String> appropriatedCasesIterator = appropriatedCasesSet.iterator();
    String token = null;
    String token1 = null;
    float total = 0.0F;
    float total2 = 0.0F;
    while (appropriatedCasesIterator.hasNext()) {
      String key = appropriatedCasesIterator.next();
      tokenizer = new StringTokenizer(key, "#");
      for (; tokenizer
        .hasMoreTokens(); System.out.println(total2)) {
        token = tokenizer.nextToken();
        token1 = tokenizer.nextToken();
        total = Integer.parseInt(token1);
        total2 += total;
      } 
    } 
    User user = getUserInformation(request);
    String bankid = user.getBankId().trim();
    String zoneid = user.getZoneId().trim();
    String branchid = user.getBranchId().trim();
    String memberId = String.valueOf(bankid) + zoneid + branchid;
    Registration registration = new Registration();
    CollectingBank collectingBank = registration.getCollectingBank(memberId);
    actionForm.setModeOfPayment("");
    actionForm.setPaymentDate(null);
    actionForm.setInstrumentNo("");
    actionForm.setInstrumentDate(null);
    actionForm.setDrawnAtBank("");
    actionForm.setDrawnAtBranch("");
    actionForm.setPayableAt("");
    IFProcessor ifProcessor = new IFProcessor();
    ArrayList instruments = ifProcessor.getInstrumentTypes("G");
    actionForm.setInstruments(instruments);
    actionForm.setCollectingBank(collectingBank.getCollectingBankId());
    actionForm.setCollectingBankName(collectingBank.getCollectingBankName());
    actionForm.setCollectingBankBranch(collectingBank.getBranchName());
    actionForm.setAccountNumber(collectingBank.getAccNo());
    actionForm.setInstrumentAmount(total2);
    return mapping.findForward("allocateliverodanpaymentdetails");
  }
  
  public ActionForward allocategetExpiredAFDANsPaymentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    double totalAmount = 0.0D;
    StringTokenizer tokenizer = null;
    Map appropriatedCases = actionForm.getAppropriatedFlags();
    if (appropriatedCases.size() < 1)
      throw new MissingDANDetailsException(
          "Please select atleast one dan for allocation ."); 
    if (appropriatedCases.size() >= 1000)
      throw new NoMemberFoundException("Please select maximum  1000 dans for Allocation"); 
    Set appropriatedCasesSet = appropriatedCases.keySet();
    Iterator<String> appropriatedCasesIterator = appropriatedCasesSet.iterator();
    String token = null;
    String token1 = null;
    float total = 0.0F;
    float total2 = 0.0F;
    while (appropriatedCasesIterator.hasNext()) {
      String key = appropriatedCasesIterator.next();
      tokenizer = new StringTokenizer(key, "#");
      for (; tokenizer
        .hasMoreTokens(); System.out.println(total2)) {
        token = tokenizer.nextToken();
        token1 = tokenizer.nextToken();
        total = Integer.parseInt(token1);
        total2 += total;
      } 
    } 
    User user = getUserInformation(request);
    String bankid = user.getBankId().trim();
    String zoneid = user.getZoneId().trim();
    String branchid = user.getBranchId().trim();
    String memberId = String.valueOf(bankid) + zoneid + branchid;
    Registration registration = new Registration();
    CollectingBank collectingBank = registration
      .getCollectingBank(memberId);
    Log.log(5, 
        "RPAction", 
        "getExpiredAFDANsPaymentDetails", 
        "collectingBank " + 
        collectingBank);
    actionForm.setModeOfPayment("");
    actionForm.setPaymentDate(null);
    actionForm.setInstrumentNo("");
    actionForm.setInstrumentDate(null);
    actionForm.setDrawnAtBank("");
    actionForm.setDrawnAtBranch("");
    actionForm.setPayableAt("");
    IFProcessor ifProcessor = new IFProcessor();
    ArrayList instruments = ifProcessor.getInstrumentTypes("G");
    actionForm.setInstruments(instruments);
    actionForm.setCollectingBank(collectingBank.getCollectingBankId());
    actionForm
      .setCollectingBankName(collectingBank.getCollectingBankName());
    actionForm.setCollectingBankBranch(collectingBank.getBranchName());
    actionForm.setAccountNumber(collectingBank.getAccNo());
    actionForm.setInstrumentAmount(total2);
    return mapping.findForward("allocateexpiredafdanpaymentdetails");
  }
  
  public ActionForward allocategetLiveASFDANsPaymentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    double totalAmount = 0.0D;
    StringTokenizer tokenizer = null;
    Map appropriatedCases = actionForm.getAppropriatedFlags();
    if (appropriatedCases.size() < 1)
      throw new MissingDANDetailsException(
          "Please select atleast one dan for allocation ."); 
    if (appropriatedCases.size() >= 1000)
      throw new NoMemberFoundException("Please select maximum  1000 dans for Allocation"); 
    Set appropriatedCasesSet = appropriatedCases.keySet();
    Iterator<String> appropriatedCasesIterator = appropriatedCasesSet.iterator();
    String token = null;
    String token1 = null;
    float total = 0.0F;
    float total2 = 0.0F;
    while (appropriatedCasesIterator.hasNext()) {
      String key = appropriatedCasesIterator.next();
      tokenizer = new StringTokenizer(key, "#");
      for (; tokenizer
        .hasMoreTokens(); System.out.println(total2)) {
        token = tokenizer.nextToken();
        token1 = tokenizer.nextToken();
        total = Integer.parseInt(token1);
        total2 += total;
      } 
    } 
    User user = getUserInformation(request);
    String bankid = user.getBankId().trim();
    String zoneid = user.getZoneId().trim();
    String branchid = user.getBranchId().trim();
    String memberId = String.valueOf(bankid) + zoneid + branchid;
    Registration registration = new Registration();
    Log.log(5, 
        "RPAction", 
        "getLiveASFDANsPaymentDetails", 
        "member id " + 
        actionForm.getMemberId());
    CollectingBank collectingBank = registration
      .getCollectingBank(memberId);
    Log.log(5, 
        "RPAction", 
        "getLiveASFDANsPaymentDetails", 
        "collectingBank " + 
        collectingBank);
    actionForm.setModeOfPayment("");
    actionForm.setPaymentDate(null);
    actionForm.setInstrumentNo("");
    actionForm.setInstrumentDate(null);
    actionForm.setDrawnAtBank("");
    actionForm.setDrawnAtBranch("");
    actionForm.setPayableAt("");
    IFProcessor ifProcessor = new IFProcessor();
    ArrayList instruments = ifProcessor.getInstrumentTypes("G");
    actionForm.setInstruments(instruments);
    actionForm.setCollectingBank(collectingBank.getCollectingBankId());
    actionForm
      .setCollectingBankName(collectingBank.getCollectingBankName());
    actionForm.setCollectingBankBranch(collectingBank.getBranchName());
    actionForm.setAccountNumber(collectingBank.getAccNo());
    actionForm.setInstrumentAmount(total2);
    return mapping.findForward("allocateliveasfdanpaymentdetails");
  }
  
  public ActionForward allocategetExpiredASFDANsPaymentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    double totalAmount = 0.0D;
    StringTokenizer tokenizer = null;
    double tot = 0.0D;
    Map appropriatedCases = actionForm.getAppropriatedFlags();
    if (appropriatedCases.size() < 1)
      throw new MissingDANDetailsException(
          "Please select atleast one dan for allocation ."); 
    if (appropriatedCases.size() >= 1000)
      throw new NoMemberFoundException("Please select maximum  1000 dans for Allocation"); 
    Set appropriatedCasesSet = appropriatedCases.keySet();
    Iterator<String> appropriatedCasesIterator = appropriatedCasesSet.iterator();
    String token = null;
    String token1 = null;
    float total = 0.0F;
    float total2 = 0.0F;
    while (appropriatedCasesIterator.hasNext()) {
      String key = appropriatedCasesIterator.next();
      tokenizer = new StringTokenizer(key, "#");
      for (; tokenizer
        .hasMoreTokens(); System.out.println(total2)) {
        token = tokenizer.nextToken();
        token1 = tokenizer.nextToken();
        total = Integer.parseInt(token1);
        total2 += total;
      } 
    } 
    User user = getUserInformation(request);
    String bankid = user.getBankId().trim();
    String zoneid = user.getZoneId().trim();
    String branchid = user.getBranchId().trim();
    String memberId = String.valueOf(bankid) + zoneid + branchid;
    Registration registration = new Registration();
    Log.log(5, 
        "RPAction", 
        "getExpiredASFDANsPaymentDetails", 
        "member id " + 
        actionForm.getMemberId());
    CollectingBank collectingBank = registration
      .getCollectingBank(memberId);
    Log.log(5, 
        "RPAction", 
        "getExpiredASFDANsPaymentDetails", 
        "collectingBank " + 
        collectingBank);
    actionForm.setModeOfPayment("");
    actionForm.setPaymentDate(null);
    actionForm.setInstrumentNo("");
    actionForm.setInstrumentDate(null);
    actionForm.setDrawnAtBank("");
    actionForm.setDrawnAtBranch("");
    actionForm.setPayableAt("");
    IFProcessor ifProcessor = new IFProcessor();
    ArrayList instruments = ifProcessor.getInstrumentTypes("G");
    actionForm.setInstruments(instruments);
    actionForm.setCollectingBank(collectingBank.getCollectingBankId());
    actionForm
      .setCollectingBankName(collectingBank.getCollectingBankName());
    actionForm.setCollectingBankBranch(collectingBank.getBranchName());
    actionForm.setAccountNumber(collectingBank.getAccNo());
    actionForm.setInstrumentAmount(total2);
    return mapping.findForward("allocateexpiredasfdanpaymentdetails");
  }
  
  public ActionForward asfdisplayallocatePaymentFinal(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "ClaimAction", "displayClaimProcessingInput", 
        "Entered");
    Connection connection = DBConnection.getConnection();
    String dantype = request.getParameter("mode").substring(0, 2);
    String danLiveType = request.getParameter("mode").substring(2, 5);
    System.out.println("dantype===" + dantype);
    System.out.println("danLiveType===" + danLiveType);
    HttpSession session = request.getSession();
    User user = getUserInformation(request);
    System.out.println("user" + user);
    String bankid = user.getBankId().trim();
    String zoneid = user.getZoneId().trim();
    String branchid = user.getBranchId().trim();
    String Zoneids = String.valueOf(zoneid) + branchid;
    String memberId = String.valueOf(bankid) + zoneid + branchid;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    Log.log(4, "ClaimAction", "displayClaimProcessingInput", 
        "Exited");
    ArrayList<RPActionForm> rpArray = new ArrayList();
    RPActionForm actionFormobj = (RPActionForm)form;
    try {
      String query = "";
      if (Zoneids.equals("00000000")) {
        query = "select PAY_ID, VIRTUAL_ACCOUNT_NO, AMOUNT, Pay_ID_CREAted_date from online_payment_detail where DAN_TYPE='" + dantype + "'  and  LIVE_EXPRIED='" + danLiveType + "'  and PAYMENT_STATUS='N' and mem_bnk_id= '" + bankid + "'";
      } else {
        query = "select PAY_ID, VIRTUAL_ACCOUNT_NO, AMOUNT, Pay_ID_CREAted_date from online_payment_detail where DAN_TYPE='" + dantype + "'  and  LIVE_EXPRIED='" + danLiveType + "'  and PAYMENT_STATUS='N' and mem_bnk_id||mem_zne_id||mem_brn_id = '" + 
          memberId + "'";
      } 
      PreparedStatement allocatePaymentfinalStmt = connection.prepareStatement(query);
      ResultSet allocatePaymentFinalResult = allocatePaymentfinalStmt
        .executeQuery();
      while (allocatePaymentFinalResult.next()) {
        RPActionForm actionForm = new RPActionForm();
        actionForm.setPaymentIdF(allocatePaymentFinalResult
            .getString(1));
        actionForm.setVitrualAcF(allocatePaymentFinalResult
            .getString(2));
        actionForm.setAmtF(allocatePaymentFinalResult.getDouble(3));
        actionForm.setRPDATEF(allocatePaymentFinalResult.getString(4));
        String paymentIds = allocatePaymentFinalResult.getString(1);
        actionForm.setPaymentIds("paymentIds");
        rpArray.add(actionForm);
      } 
      actionFormobj.setAllocatepaymentFinal(rpArray);
    } catch (Exception exception) {
      Log.logException(exception);
      throw new DatabaseException(exception.getMessage());
    } finally {
      DBConnection.freeConnection(connection);
    } 
    return mapping.findForward("asfdisplayallocatePaymentFinal");
  }
  
  public ActionForward asfdisplayallocatePaymentFinalSubmit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    System.out.println("asfdisplayallocatePaymentFinalSubmit===11210");
    HttpSession session = request.getSession();
    RPActionForm actionFormobj = (RPActionForm)form;
    String paymentID = request.getParameter("paymentIds");
    Map approveFlags = actionFormobj.getAllocationPaymentFinalSubmit();
    session.setAttribute("approvedData", approveFlags);
    System.out.println("payid " + approveFlags);
    if (approveFlags.size() == 0)
      throw new NoMemberFoundException(
          "Please select atleast one PAYMENT-ID to MAKE PAYMENT."); 
    System.out.println("payid " + approveFlags);
    System.out.println("payid value approveFlags " + approveFlags.size());
    Set keys = approveFlags.keySet();
    System.out.println("konkati" + keys);
    User user = getUserInformation(request);
    String userid = user.getUserId();
    String bankid = user.getBankId().trim();
    String zoneid = user.getZoneId().trim();
    String branchid = user.getBranchId().trim();
    String memberId = String.valueOf(bankid) + zoneid + branchid;
    Connection connection = DBConnection.getConnection();
    CallableStatement cStmt = null;
    String errorCode = "";
    ArrayList<RPActionForm> rpArray = new ArrayList();
    Iterator<String> PaymentIterate = keys.iterator();
    int insdanstatus = 0;
    RPActionForm actionForm = null;
    while (PaymentIterate.hasNext()) {
      actionForm = new RPActionForm();
      String payids = PaymentIterate.next();
      System.out.println("keys are" + payids);
      String[] arr = payids.split("@");
      System.out.println("PayID " + arr[0]);
      actionForm.setPaymentIdR(arr[0]);
      actionForm.setAmmount2(Integer.parseInt(arr[1]));
      actionForm.setVaccno(arr[2]);
      actionForm.setPaymentInitiateDate(new Date());
      rpArray.add(actionForm);
      try {
        String query = "select PAYMENT_STATUS  from online_payment_detail where PAY_ID='" + 
          arr[0] + "' and PAYMENT_STATUS='X'";
        PreparedStatement makePaymentfinalStmt = connection.prepareStatement(query);
        ResultSet makePaymentFinalResult = makePaymentfinalStmt.executeQuery();
        if (makePaymentFinalResult.next())
          throw new NoMemberFoundException("Payment already done"); 
        try {
          cStmt = connection
            .prepareCall("{ call PACK_ONLINE_PAYMENT_DETAIL.PROC_XML_GENRATE(?,?)}");
          System.out.println("proc_dan_deallocation  line 11288===" + cStmt);
          cStmt.setString(1, arr[0]);
          cStmt.registerOutParameter(2, 12);
          cStmt.execute();
          String error = cStmt.getString(2);
          Log.log(5, "RPAction", "cancelRpAppropriation", 
              "Error code and error are " + errorCode + " " + 
              error);
          if (error != null) {
            connection.rollback();
            throw new DatabaseException(error);
          } 
        } catch (SQLException e) {
          try {
            connection.rollback();
          } catch (SQLException ignore) {
            Log.log(2, "RPAction", "cancelRpAppropriation", 
                ignore.getMessage());
          } 
          Log.log(2, "RPAction", "cancelRpAppropriation", 
              e.getMessage());
          Log.logException(e);
          throw new DatabaseException(e.getMessage());
        } 
      } finally {
        DBConnection.freeConnection(connection);
      } 
    } 
    actionFormobj.setMakepaymentFinal(rpArray);
    return mapping.findForward("asfdisplayallocatePaymentFinalSubmitonline");
  }
  
  public ActionForward asfmodifyonline(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    return mapping.findForward("success");
  }
  
  public ActionForward asfintiateyonline(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    DynaActionForm dynaForm = (DynaActionForm)form;
    dynaForm.initialize(mapping);
    return mapping.findForward("success");
  }
  
  public ActionForward asfdisplayallocatePaymentModifySubmit(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "ClaimAction", "displayClaimProcessingInput", 
        "Entered");
    String dantype = request.getParameter("mode").substring(0, 2);
    String danLiveType = request.getParameter("mode").substring(2, 5);
    System.out.println("dantype===" + dantype);
    System.out.println("danLiveType===" + danLiveType);
    Connection connection = DBConnection.getConnection();
    HttpSession session = request.getSession();
    User user = getUserInformation(request);
    String bankid = user.getBankId().trim();
    String zoneid = user.getZoneId().trim();
    String branchid = user.getBranchId().trim();
    String memberId = String.valueOf(bankid) + zoneid + branchid;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    Log.log(4, "ClaimAction", "displayClaimProcessingInput", 
        "Exited");
    ArrayList<RPActionForm> rpArray = new ArrayList();
    RPActionForm actionFormobj = (RPActionForm)form;
    try {
      String query = "select PAY_ID, VIRTUAL_ACCOUNT_NO, AMOUNT, TO_CHAR(Pay_ID_CREAted_date, 'DD-MM-YYYY HH24:MI:SS') from online_payment_detail where DAN_TYPE='" + dantype + "' and LIVE_EXPRIED='" + danLiveType + "' and  PAYMENT_STATUS ='N'and mem_bnk_id||mem_zne_id||mem_brn_id = '" + 
        memberId + "'";
      System.out.println("query" + query);
      PreparedStatement allocateModifyStmt = connection.prepareStatement(query);
      ResultSet allocateModifyResult = allocateModifyStmt.executeQuery();
      while (allocateModifyResult.next()) {
        RPActionForm actionForm = new RPActionForm();
        actionForm.setPaymentId1(allocateModifyResult.getString(1));
        actionForm.setVaccno(allocateModifyResult.getString(2));
        actionForm.setAmmount1(allocateModifyResult.getDouble(3));
        actionForm.setPayidcreateddate(allocateModifyResult
            .getString(4));
        String paymentIds = allocateModifyResult.getString(1);
        actionForm.setPaymentIds("paymentIds");
        rpArray.add(actionForm);
      } 
      actionFormobj.setAllocatepaymentmodify(rpArray);
    } catch (Exception exception) {
      Log.logException(exception);
      throw new DatabaseException(exception.getMessage());
    } finally {
      DBConnection.freeConnection(connection);
    } 
    return mapping.findForward("asfdisplayallocatePaymentModifySubmit");
  }
  
  public ActionForward allocatesubmitLAFDANsPaymentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    RpProcessor rpProcessor = new RpProcessor();
    String paymentId = "";
    String methodName = "submitLiveASFDANsPaymentDetails";
    Log.log(5, "RPAction", methodName, "Entered");
    User user = getUserInformation(request);
    PaymentDetails paymentDetails = new PaymentDetails();
    String allocationType = actionForm.getAllocationType();
    paymentDetails.setAllocationType1("A");
    String modeOfPayment = actionForm.getModeOfPayment();
    String collectingBranch = actionForm.getCollectingBankBranch();
    Date paymentDate = actionForm.getPaymentDate();
    String instrumentNumber = actionForm.getInstrumentNo();
    Date instrumentDate = actionForm.getInstrumentDate();
    String modeOfDelivery = actionForm.getModeOfDelivery();
    double instrumentAmount = actionForm.getInstrumentAmount();
    String drawnAtBank = actionForm.getDrawnAtBank();
    String drawnAtBranch = actionForm.getDrawnAtBranch();
    String payableAt = actionForm.getPayableAt();
    String accNumber = actionForm.getAccountNumber();
    Log.log(5, "RPAction", methodName, 
        "collecting bank " + actionForm.getCollectingBankName());
    paymentDetails.setCollectingBank(actionForm.getCollectingBankName());
    Log.log(5, "RPAction", methodName, "mode of payment " + modeOfPayment);
    paymentDetails.setModeOfPayment(modeOfPayment);
    Log.log(5, "RPAction", methodName, "collecting branch " + 
        collectingBranch);
    paymentDetails.setCollectingBankBranch(collectingBranch);
    Log.log(5, "RPAction", methodName, "payment date " + paymentDate);
    paymentDetails.setPaymentDate(paymentDate);
    Log.log(5, "RPAction", methodName, "instrument number " + 
        instrumentNumber);
    paymentDetails.setInstrumentNo(instrumentNumber);
    Log.log(5, "RPAction", methodName, "instrument date " + instrumentDate);
    paymentDetails.setInstrumentDate(instrumentDate);
    Log.log(5, "RPAction", methodName, "mode of delivery " + modeOfDelivery);
    paymentDetails.setModeOfDelivery(modeOfDelivery);
    Log.log(5, "RPAction", methodName, "instrument amount " + 
        instrumentAmount);
    paymentDetails.setInstrumentAmount(instrumentAmount);
    Log.log(5, "RPAction", methodName, "drawn at bank " + drawnAtBank);
    paymentDetails.setDrawnAtBank(drawnAtBank);
    Log.log(5, "RPAction", methodName, "drawn at branch " + drawnAtBranch);
    paymentDetails.setDrawnAtBranch(drawnAtBranch);
    Log.log(5, "RPAction", methodName, "payable at " + payableAt);
    paymentDetails.setPayableAt(payableAt);
    Log.log(5, "RPAction", methodName, "acc num " + accNumber);
    paymentDetails.setCgtsiAccNumber(accNumber);
    Map appropriatedFlags = actionForm.getAppropriatedFlags();
    Map cgpans = actionForm.getCgpans();
    Map danCgpanDetails = actionForm.getDanPanDetails();
    paymentId = rpProcessor.allocateCGDANonlinelaf(paymentDetails, 
        appropriatedFlags, cgpans, danCgpanDetails, user);
    request.setAttribute("paymentId", paymentId);
    request.setAttribute("intrumentAmount", Double.valueOf(paymentDetails.getInstrumentAmount()));
    return mapping.findForward("allocatesuccess");
  }
  
  public ActionForward allocatesubmitTNDANsPaymentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    RpProcessor rpProcessor = new RpProcessor();
    String paymentId = "";
    String methodName = "submitLiveASFDANsPaymentDetails";
    Log.log(5, "RPAction", methodName, "Entered");
    User user = getUserInformation(request);
    PaymentDetails paymentDetails = new PaymentDetails();
    String allocationType = actionForm.getAllocationType();
    paymentDetails.setAllocationType1("A");
    String modeOfPayment = actionForm.getModeOfPayment();
    String collectingBranch = actionForm.getCollectingBankBranch();
    Date paymentDate = actionForm.getPaymentDate();
    String instrumentNumber = actionForm.getInstrumentNo();
    Date instrumentDate = actionForm.getInstrumentDate();
    String modeOfDelivery = actionForm.getModeOfDelivery();
    double instrumentAmount = actionForm.getInstrumentAmount();
    String drawnAtBank = actionForm.getDrawnAtBank();
    String drawnAtBranch = actionForm.getDrawnAtBranch();
    String payableAt = actionForm.getPayableAt();
    String accNumber = actionForm.getAccountNumber();
    Log.log(5, "RPAction", methodName, 
        "collecting bank " + actionForm.getCollectingBankName());
    paymentDetails.setCollectingBank(actionForm.getCollectingBankName());
    Log.log(5, "RPAction", methodName, "mode of payment " + modeOfPayment);
    paymentDetails.setModeOfPayment(modeOfPayment);
    Log.log(5, "RPAction", methodName, "collecting branch " + 
        collectingBranch);
    paymentDetails.setCollectingBankBranch(collectingBranch);
    Log.log(5, "RPAction", methodName, "payment date " + paymentDate);
    paymentDetails.setPaymentDate(paymentDate);
    Log.log(5, "RPAction", methodName, "instrument number " + 
        instrumentNumber);
    paymentDetails.setInstrumentNo(instrumentNumber);
    Log.log(5, "RPAction", methodName, "instrument date " + instrumentDate);
    paymentDetails.setInstrumentDate(instrumentDate);
    Log.log(5, "RPAction", methodName, "mode of delivery " + modeOfDelivery);
    paymentDetails.setModeOfDelivery(modeOfDelivery);
    Log.log(5, "RPAction", methodName, "instrument amount " + 
        instrumentAmount);
    paymentDetails.setInstrumentAmount(instrumentAmount);
    Log.log(5, "RPAction", methodName, "drawn at bank " + drawnAtBank);
    paymentDetails.setDrawnAtBank(drawnAtBank);
    Log.log(5, "RPAction", methodName, "drawn at branch " + drawnAtBranch);
    paymentDetails.setDrawnAtBranch(drawnAtBranch);
    Log.log(5, "RPAction", methodName, "payable at " + payableAt);
    paymentDetails.setPayableAt(payableAt);
    Log.log(5, "RPAction", methodName, "acc num " + accNumber);
    paymentDetails.setCgtsiAccNumber(accNumber);
    Map appropriatedFlags = actionForm.getAppropriatedFlags();
    Map cgpans = actionForm.getCgpans();
    Map danCgpanDetails = actionForm.getDanPanDetails();
    paymentId = rpProcessor.allocateCGDANonlinelTN(paymentDetails, 
        appropriatedFlags, cgpans, danCgpanDetails, user);
    request.setAttribute("paymentId", paymentId);
    request.setAttribute("intrumentAmount", Double.valueOf(paymentDetails.getInstrumentAmount()));
    return mapping.findForward("allocatesuccess");
  }
  
  public ActionForward allocatesubmitRVDANsPaymentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    RpProcessor rpProcessor = new RpProcessor();
    String paymentId = "";
    String methodName = "submitLiveASFDANsPaymentDetails";
    Log.log(5, "RPAction", methodName, "Entered");
    User user = getUserInformation(request);
    PaymentDetails paymentDetails = new PaymentDetails();
    String allocationType = actionForm.getAllocationType();
    paymentDetails.setAllocationType1("A");
    String modeOfPayment = actionForm.getModeOfPayment();
    String collectingBranch = actionForm.getCollectingBankBranch();
    Date paymentDate = actionForm.getPaymentDate();
    String instrumentNumber = actionForm.getInstrumentNo();
    Date instrumentDate = actionForm.getInstrumentDate();
    String modeOfDelivery = actionForm.getModeOfDelivery();
    double instrumentAmount = actionForm.getInstrumentAmount();
    String drawnAtBank = actionForm.getDrawnAtBank();
    String drawnAtBranch = actionForm.getDrawnAtBranch();
    String payableAt = actionForm.getPayableAt();
    String accNumber = actionForm.getAccountNumber();
    Log.log(5, "RPAction", methodName, 
        "collecting bank " + actionForm.getCollectingBankName());
    paymentDetails.setCollectingBank(actionForm.getCollectingBankName());
    Log.log(5, "RPAction", methodName, "mode of payment " + modeOfPayment);
    paymentDetails.setModeOfPayment(modeOfPayment);
    Log.log(5, "RPAction", methodName, "collecting branch " + 
        collectingBranch);
    paymentDetails.setCollectingBankBranch(collectingBranch);
    Log.log(5, "RPAction", methodName, "payment date " + paymentDate);
    paymentDetails.setPaymentDate(paymentDate);
    Log.log(5, "RPAction", methodName, "instrument number " + 
        instrumentNumber);
    paymentDetails.setInstrumentNo(instrumentNumber);
    Log.log(5, "RPAction", methodName, "instrument date " + instrumentDate);
    paymentDetails.setInstrumentDate(instrumentDate);
    Log.log(5, "RPAction", methodName, "mode of delivery " + modeOfDelivery);
    paymentDetails.setModeOfDelivery(modeOfDelivery);
    Log.log(5, "RPAction", methodName, "instrument amount " + 
        instrumentAmount);
    paymentDetails.setInstrumentAmount(instrumentAmount);
    Log.log(5, "RPAction", methodName, "drawn at bank " + drawnAtBank);
    paymentDetails.setDrawnAtBank(drawnAtBank);
    Log.log(5, "RPAction", methodName, "drawn at branch " + drawnAtBranch);
    paymentDetails.setDrawnAtBranch(drawnAtBranch);
    Log.log(5, "RPAction", methodName, "payable at " + payableAt);
    paymentDetails.setPayableAt(payableAt);
    Log.log(5, "RPAction", methodName, "acc num " + accNumber);
    paymentDetails.setCgtsiAccNumber(accNumber);
    Map appropriatedFlags = actionForm.getAppropriatedFlags();
    Map cgpans = actionForm.getCgpans();
    Map danCgpanDetails = actionForm.getDanPanDetails();
    paymentId = rpProcessor.allocateCGDANonlinelRV(paymentDetails, 
        appropriatedFlags, cgpans, danCgpanDetails, user);
    request.setAttribute("paymentId", paymentId);
    request.setAttribute("intrumentAmount", Double.valueOf(paymentDetails.getInstrumentAmount()));
    return mapping.findForward("allocatesuccess");
  }
  
  public ActionForward allocatesubmitRODANsPaymentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    RpProcessor rpProcessor = new RpProcessor();
    String paymentId = "";
    String methodName = "submitLiveASFDANsPaymentDetails";
    Log.log(5, "RPAction", methodName, "Entered");
    User user = getUserInformation(request);
    PaymentDetails paymentDetails = new PaymentDetails();
    String allocationType = actionForm.getAllocationType();
    paymentDetails.setAllocationType1("A");
    String modeOfPayment = actionForm.getModeOfPayment();
    String collectingBranch = actionForm.getCollectingBankBranch();
    Date paymentDate = actionForm.getPaymentDate();
    String instrumentNumber = actionForm.getInstrumentNo();
    Date instrumentDate = actionForm.getInstrumentDate();
    String modeOfDelivery = actionForm.getModeOfDelivery();
    double instrumentAmount = actionForm.getInstrumentAmount();
    String drawnAtBank = actionForm.getDrawnAtBank();
    String drawnAtBranch = actionForm.getDrawnAtBranch();
    String payableAt = actionForm.getPayableAt();
    String accNumber = actionForm.getAccountNumber();
    Log.log(5, "RPAction", methodName, 
        "collecting bank " + actionForm.getCollectingBankName());
    paymentDetails.setCollectingBank(actionForm.getCollectingBankName());
    Log.log(5, "RPAction", methodName, "mode of payment " + modeOfPayment);
    paymentDetails.setModeOfPayment(modeOfPayment);
    Log.log(5, "RPAction", methodName, "collecting branch " + 
        collectingBranch);
    paymentDetails.setCollectingBankBranch(collectingBranch);
    Log.log(5, "RPAction", methodName, "payment date " + paymentDate);
    paymentDetails.setPaymentDate(paymentDate);
    Log.log(5, "RPAction", methodName, "instrument number " + 
        instrumentNumber);
    paymentDetails.setInstrumentNo(instrumentNumber);
    Log.log(5, "RPAction", methodName, "instrument date " + instrumentDate);
    paymentDetails.setInstrumentDate(instrumentDate);
    Log.log(5, "RPAction", methodName, "mode of delivery " + modeOfDelivery);
    paymentDetails.setModeOfDelivery(modeOfDelivery);
    Log.log(5, "RPAction", methodName, "instrument amount " + 
        instrumentAmount);
    paymentDetails.setInstrumentAmount(instrumentAmount);
    Log.log(5, "RPAction", methodName, "drawn at bank " + drawnAtBank);
    paymentDetails.setDrawnAtBank(drawnAtBank);
    Log.log(5, "RPAction", methodName, "drawn at branch " + drawnAtBranch);
    paymentDetails.setDrawnAtBranch(drawnAtBranch);
    Log.log(5, "RPAction", methodName, "payable at " + payableAt);
    paymentDetails.setPayableAt(payableAt);
    Log.log(5, "RPAction", methodName, "acc num " + accNumber);
    paymentDetails.setCgtsiAccNumber(accNumber);
    Map appropriatedFlags = actionForm.getAppropriatedFlags();
    Map cgpans = actionForm.getCgpans();
    Map danCgpanDetails = actionForm.getDanPanDetails();
    paymentId = rpProcessor.allocateCGDANonlinelRO(paymentDetails, 
        appropriatedFlags, cgpans, danCgpanDetails, user);
    request.setAttribute("paymentId", paymentId);
    request.setAttribute("intrumentAmount", Double.valueOf(paymentDetails.getInstrumentAmount()));
    return mapping.findForward("allocatesuccess");
  }
  
  public ActionForward allocatesubmitEAFDANsPaymentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    RpProcessor rpProcessor = new RpProcessor();
    String paymentId = "";
    String methodName = "submitLiveASFDANsPaymentDetails";
    Log.log(5, "RPAction", methodName, "Entered");
    User user = getUserInformation(request);
    PaymentDetails paymentDetails = new PaymentDetails();
    String allocationType = actionForm.getAllocationType();
    paymentDetails.setAllocationType1("A");
    String modeOfPayment = actionForm.getModeOfPayment();
    String collectingBranch = actionForm.getCollectingBankBranch();
    Date paymentDate = actionForm.getPaymentDate();
    String instrumentNumber = actionForm.getInstrumentNo();
    Date instrumentDate = actionForm.getInstrumentDate();
    String modeOfDelivery = actionForm.getModeOfDelivery();
    double instrumentAmount = actionForm.getInstrumentAmount();
    String drawnAtBank = actionForm.getDrawnAtBank();
    String drawnAtBranch = actionForm.getDrawnAtBranch();
    String payableAt = actionForm.getPayableAt();
    String accNumber = actionForm.getAccountNumber();
    Log.log(5, "RPAction", methodName, 
        "collecting bank " + actionForm.getCollectingBankName());
    paymentDetails.setCollectingBank(actionForm.getCollectingBankName());
    Log.log(5, "RPAction", methodName, "mode of payment " + modeOfPayment);
    paymentDetails.setModeOfPayment(modeOfPayment);
    Log.log(5, "RPAction", methodName, "collecting branch " + 
        collectingBranch);
    paymentDetails.setCollectingBankBranch(collectingBranch);
    Log.log(5, "RPAction", methodName, "payment date " + paymentDate);
    paymentDetails.setPaymentDate(paymentDate);
    Log.log(5, "RPAction", methodName, "instrument number " + 
        instrumentNumber);
    paymentDetails.setInstrumentNo(instrumentNumber);
    Log.log(5, "RPAction", methodName, "instrument date " + instrumentDate);
    paymentDetails.setInstrumentDate(instrumentDate);
    Log.log(5, "RPAction", methodName, "mode of delivery " + modeOfDelivery);
    paymentDetails.setModeOfDelivery(modeOfDelivery);
    Log.log(5, "RPAction", methodName, "instrument amount " + 
        instrumentAmount);
    paymentDetails.setInstrumentAmount(instrumentAmount);
    Log.log(5, "RPAction", methodName, "drawn at bank " + drawnAtBank);
    paymentDetails.setDrawnAtBank(drawnAtBank);
    Log.log(5, "RPAction", methodName, "drawn at branch " + drawnAtBranch);
    paymentDetails.setDrawnAtBranch(drawnAtBranch);
    Log.log(5, "RPAction", methodName, "payable at " + payableAt);
    paymentDetails.setPayableAt(payableAt);
    Log.log(5, "RPAction", methodName, "acc num " + accNumber);
    paymentDetails.setCgtsiAccNumber(accNumber);
    Map appropriatedFlags = actionForm.getAppropriatedFlags();
    Map cgpans = actionForm.getCgpans();
    Map danCgpanDetails = actionForm.getDanPanDetails();
    paymentId = rpProcessor.allocateCGDANonlineeaf(paymentDetails, 
        appropriatedFlags, cgpans, danCgpanDetails, user);
    request.setAttribute("paymentId", paymentId);
    request.setAttribute("intrumentAmount", Double.valueOf(paymentDetails.getInstrumentAmount()));
    return mapping.findForward("allocatesuccess");
  }
  
  public ActionForward allocatesubmitLSFDANsPaymentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    RpProcessor rpProcessor = new RpProcessor();
    String paymentId = "";
    String methodName = "submitLiveASFDANsPaymentDetails";
    Log.log(5, "RPAction", methodName, "Entered");
    User user = getUserInformation(request);
    PaymentDetails paymentDetails = new PaymentDetails();
    String allocationType = actionForm.getAllocationType();
    paymentDetails.setAllocationType1("A");
    String modeOfPayment = actionForm.getModeOfPayment();
    String collectingBranch = actionForm.getCollectingBankBranch();
    Date paymentDate = actionForm.getPaymentDate();
    String instrumentNumber = actionForm.getInstrumentNo();
    Date instrumentDate = actionForm.getInstrumentDate();
    String modeOfDelivery = actionForm.getModeOfDelivery();
    double instrumentAmount = actionForm.getInstrumentAmount();
    String drawnAtBank = actionForm.getDrawnAtBank();
    String drawnAtBranch = actionForm.getDrawnAtBranch();
    String payableAt = actionForm.getPayableAt();
    String accNumber = actionForm.getAccountNumber();
    Log.log(5, "RPAction", methodName, 
        "collecting bank " + actionForm.getCollectingBankName());
    paymentDetails.setCollectingBank(actionForm.getCollectingBankName());
    Log.log(5, "RPAction", methodName, "mode of payment " + modeOfPayment);
    paymentDetails.setModeOfPayment(modeOfPayment);
    Log.log(5, "RPAction", methodName, "collecting branch " + 
        collectingBranch);
    paymentDetails.setCollectingBankBranch(collectingBranch);
    Log.log(5, "RPAction", methodName, "payment date " + paymentDate);
    paymentDetails.setPaymentDate(paymentDate);
    Log.log(5, "RPAction", methodName, "instrument number " + 
        instrumentNumber);
    paymentDetails.setInstrumentNo(instrumentNumber);
    Log.log(5, "RPAction", methodName, "instrument date " + instrumentDate);
    paymentDetails.setInstrumentDate(instrumentDate);
    Log.log(5, "RPAction", methodName, "mode of delivery " + modeOfDelivery);
    paymentDetails.setModeOfDelivery(modeOfDelivery);
    Log.log(5, "RPAction", methodName, "instrument amount " + 
        instrumentAmount);
    paymentDetails.setInstrumentAmount(instrumentAmount);
    Log.log(5, "RPAction", methodName, "drawn at bank " + drawnAtBank);
    paymentDetails.setDrawnAtBank(drawnAtBank);
    Log.log(5, "RPAction", methodName, "drawn at branch " + drawnAtBranch);
    paymentDetails.setDrawnAtBranch(drawnAtBranch);
    Log.log(5, "RPAction", methodName, "payable at " + payableAt);
    paymentDetails.setPayableAt(payableAt);
    Log.log(5, "RPAction", methodName, "acc num " + accNumber);
    paymentDetails.setCgtsiAccNumber(accNumber);
    Map appropriatedFlags = actionForm.getAppropriatedFlags();
    Map cgpans = actionForm.getCgpans();
    Map danCgpanDetails = actionForm.getDanPanDetails();
    paymentId = rpProcessor.allocateCGDANonlinelsf(paymentDetails, 
        appropriatedFlags, cgpans, danCgpanDetails, user);
    request.setAttribute("paymentId", paymentId);
    request.setAttribute("intrumentAmount", Double.valueOf(paymentDetails.getInstrumentAmount()));
    return mapping.findForward("allocatesuccess");
  }
  
  public ActionForward allocatesubmitESFDANsPaymentDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    RPActionForm actionForm = (RPActionForm)form;
    RpProcessor rpProcessor = new RpProcessor();
    String paymentId = "";
    String methodName = "submitLiveASFDANsPaymentDetails";
    Log.log(5, "RPAction", methodName, "Entered");
    User user = getUserInformation(request);
    PaymentDetails paymentDetails = new PaymentDetails();
    String allocationType = actionForm.getAllocationType();
    paymentDetails.setAllocationType1("A");
    String modeOfPayment = actionForm.getModeOfPayment();
    String collectingBranch = actionForm.getCollectingBankBranch();
    Date paymentDate = actionForm.getPaymentDate();
    String instrumentNumber = actionForm.getInstrumentNo();
    Date instrumentDate = actionForm.getInstrumentDate();
    String modeOfDelivery = actionForm.getModeOfDelivery();
    double instrumentAmount = actionForm.getInstrumentAmount();
    String drawnAtBank = actionForm.getDrawnAtBank();
    String drawnAtBranch = actionForm.getDrawnAtBranch();
    String payableAt = actionForm.getPayableAt();
    String accNumber = actionForm.getAccountNumber();
    Log.log(5, "RPAction", methodName, 
        "collecting bank " + actionForm.getCollectingBankName());
    paymentDetails.setCollectingBank(actionForm.getCollectingBankName());
    Log.log(5, "RPAction", methodName, "mode of payment " + modeOfPayment);
    paymentDetails.setModeOfPayment(modeOfPayment);
    Log.log(5, "RPAction", methodName, "collecting branch " + 
        collectingBranch);
    paymentDetails.setCollectingBankBranch(collectingBranch);
    Log.log(5, "RPAction", methodName, "payment date " + paymentDate);
    paymentDetails.setPaymentDate(paymentDate);
    Log.log(5, "RPAction", methodName, "instrument number " + 
        instrumentNumber);
    paymentDetails.setInstrumentNo(instrumentNumber);
    Log.log(5, "RPAction", methodName, "instrument date " + instrumentDate);
    paymentDetails.setInstrumentDate(instrumentDate);
    Log.log(5, "RPAction", methodName, "mode of delivery " + modeOfDelivery);
    paymentDetails.setModeOfDelivery(modeOfDelivery);
    Log.log(5, "RPAction", methodName, "instrument amount " + 
        instrumentAmount);
    paymentDetails.setInstrumentAmount(instrumentAmount);
    Log.log(5, "RPAction", methodName, "drawn at bank " + drawnAtBank);
    paymentDetails.setDrawnAtBank(drawnAtBank);
    Log.log(5, "RPAction", methodName, "drawn at branch " + drawnAtBranch);
    paymentDetails.setDrawnAtBranch(drawnAtBranch);
    Log.log(5, "RPAction", methodName, "payable at " + payableAt);
    paymentDetails.setPayableAt(payableAt);
    Log.log(5, "RPAction", methodName, "acc num " + accNumber);
    paymentDetails.setCgtsiAccNumber(accNumber);
    Map appropriatedFlags = actionForm.getAppropriatedFlags();
    Map cgpans = actionForm.getCgpans();
    Map danCgpanDetails = actionForm.getDanPanDetails();
    paymentId = rpProcessor.allocateCGDANonlineesf(paymentDetails, 
        appropriatedFlags, cgpans, danCgpanDetails, user);
    request.setAttribute("paymentId", paymentId);
    request.setAttribute("intrumentAmount", Double.valueOf(paymentDetails.getInstrumentAmount()));
    return mapping.findForward("allocatesuccess");
  }
  
  public ActionForward asfdisplayPaymentIdDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    String PaymentId = "";
    Log.log(4, "RPAction", "displayPaymentIdDetail", "Entered");
    ReportManager manager = new ReportManager();
    RPActionForm rappform = (RPActionForm)form;
    String clmApplicationStatus = "";
    Log.log(4, 
        "RPAction", 
        "displayPaymentIdDetail", 
        
        "Claim Application Status being queried :" + 
        clmApplicationStatus);
    User user = getUserInformation(request);
    String bankid = user.getBankId().trim();
    String zoneid = user.getZoneId().trim();
    String branchid = user.getBranchId().trim();
    String memberId = bankid + zoneid + 
      branchid;
    request.setAttribute("PaymentId", request.getParameter("PaymentId"));
    PaymentId = request.getParameter("PaymentId");
    System.out.println(PaymentId);
    ArrayList<RPActionForm> rpArray = new ArrayList();
    RPActionForm actionFormobj = (RPActionForm)form;
    PreparedStatement pstmt = null;
    ResultSet rst = null;
    String PayId = "";
    Connection connection = DBConnection.getConnection();
    Statement allocateStmt = connection.createStatement();
    ResultSet allocateModifyResult1 = null;
    try {
      String query = "select cgpan,dan_id,dci_amount_raised,to_char(DCI_ALLOCATION_DT, 'DD-MM-YYYY HH24:MI:SS') from dan_cgpan_info_temp where pay_id ='" + 
        PaymentId + "' ";
      System.out.println("query" + query);
      allocateModifyResult1 = allocateStmt.executeQuery(query);
      RPActionForm actionForm = null;
      while (allocateModifyResult1.next()) {
        actionForm = new RPActionForm();
        actionForm.setCgpan1(allocateModifyResult1.getString(1));
        actionForm.setDanid1(allocateModifyResult1.getString(2));
        actionForm.setAmmount2(allocateModifyResult1.getDouble(3));
        actionForm.setDandate1(allocateModifyResult1.getString(4));
        String allocatedanIds = allocateModifyResult1.getString(2);
        System.out.println("displayPaymentIdDetail allocatedanIds" + 
            allocatedanIds);
        actionForm.setAllocatedanIds("allocatedanIds");
        actionForm.setPaymentId(request.getParameter("PaymentId"));
        rpArray.add(actionForm);
      } 
      actionFormobj.setAllocatepayIDdetails(rpArray);
    } catch (Exception exception) {
      Log.logException(exception);
      throw new DatabaseException(exception.getMessage());
    } finally {
      DBConnection.freeConnection(connection);
    } 
    return mapping.findForward("success");
  }
  
  public ActionForward asfdisplayallocatePaymentModifySubmitDetails(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Connection conn;
    HttpSession session = request.getSession();
    RPActionForm actionFormobj = (RPActionForm)form;
    String paymentID = request.getParameter("paymentIds");
    Map approveFlags = actionFormobj.getAllocationPaymentYes();
    if (approveFlags.size() == 0)
      throw new NoMemberFoundException(
          "Please select atleast one PAYMENT ID to Approve."); 
    System.out.println("payid " + approveFlags);
    System.out.println("payid value approveFlags " + approveFlags.size());
    Set keys = approveFlags.keySet();
    User user = getUserInformation(request);
    String userid = user.getUserId();
    String bankid = user.getBankId().trim();
    String zoneid = user.getZoneId().trim();
    String branchid = user.getBranchId().trim();
    String memberId = String.valueOf(bankid) + zoneid + branchid;
    Connection connection = DBConnection.getConnection();
    connection.setAutoCommit(false);
    Statement str1 = connection.createStatement();
    try {
      Iterator<String> clmInterat = keys.iterator();
      int insdanstatus = 0;
      int deldanstatus = 0;
      int deldanstatus1 = 0;
      int inspaystatus = 0;
      int delpaystatus = 0;
      while (clmInterat.hasNext()) {
        String payids = clmInterat.next();
        String[] arr = payids.split("@");
        System.out.println("PayID " + arr[0]);
        String decision = (String)approveFlags.get(payids);
        System.out.println("keys are" + payids);
        System.out.println("values  are" + decision);
        Date todaydate = new Date();
        String quryforSelect = "insert into dan_cgpan_info_temp_canc select *  from dan_cgpan_info_temp where pay_id ='" + 
          arr[0] + "' ";
        insdanstatus = str1.executeUpdate(quryforSelect);
        System.out.println("testing1" + quryforSelect);
        String quryforSelect3 = "insert into payment_detail_temp_canc  select * from  payment_detail_temp where pay_id ='" + 
          arr[0] + "' ";
        System.out.println("testing1" + quryforSelect3);
        inspaystatus = str1.executeUpdate(quryforSelect3);
        String quryforSelect4 = "delete from payment_detail_temp where pay_id ='" + 
          arr[0] + "'";
        System.out.println("testing1" + quryforSelect4);
        delpaystatus = str1.executeUpdate(quryforSelect4);
        String quryforSelect2 = "delete from dan_cgpan_info_temp where pay_id ='" + 
          arr[0] + "'";
        System.out.println("testing2" + quryforSelect2);
        deldanstatus = str1.executeUpdate(quryforSelect2);
        String quryforSelect5 = "update  online_payment_detail set PAYMENT_STATUS ='C' where PAY_ID  ='" + 
          arr[0] + "'";
        System.out.println("testing3" + quryforSelect5);
        deldanstatus1 = str1.executeUpdate(quryforSelect5);
        System.out.println("insdanstatus " + insdanstatus + 
            "deldanstatus " + deldanstatus + "inspaystatus " + 
            inspaystatus + "delpaystatus " + delpaystatus + 
            "deldanstatus1 " + deldanstatus1);
        if (insdanstatus != 0 && deldanstatus != 0 && 
          inspaystatus != 0 && delpaystatus != 0 && 
          deldanstatus1 != 0) {
          connection.commit();
          continue;
        } 
        connection.rollback();
        throw new MissingDANDetailsException(
            "not able to deallocate pay id. problem in  '" + 
            arr[0] + "'");
      } 
    } catch (SQLException sqlexception) {
      connection.rollback();
      throw new DatabaseException(sqlexception.getMessage());
    } finally {
      Connection connection1 = null;
      DBConnection.freeConnection(connection1);
    } 
    connection.commit();
    request.setAttribute("message", 
        " RP Modified / Cancelled successfully !!!");
    return mapping.findForward("success");
  }
  
  public ActionForward asfsubmitDanWiseDeallocation(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RPAction", "generateClaimSFDAN", "Entered");
    HttpSession session = request.getSession();
    RPActionForm actionFormobj = (RPActionForm)form;
    String danID = request.getParameter("allocatedanIds");
    System.out.println("danID " + danID);
    Map approveFlags = actionFormobj.getAllocationPaymentDans();
    System.out.println("danID " + approveFlags);
    request.setAttribute("PaymentId", request.getParameter("PaymentId"));
    String PaymentId = "";
    PaymentId = request.getParameter("PaymentId");
    System.out.println(PaymentId);
    int size = 0;
    if (approveFlags.size() == 0)
      throw new NoMemberFoundException(
          "Please select atleast one DAN ID to Approve."); 
    System.out.println("danID " + approveFlags);
    System.out.println("danID value approveFlags " + approveFlags.size());
    Set keys = approveFlags.keySet();
    System.out.println("keys size " + keys.size());
    size = approveFlags.size();
    User user = getUserInformation(request);
    String userid = user.getUserId();
    String bankid = user.getBankId().trim();
    String zoneid = user.getZoneId().trim();
    String branchid = user.getBranchId().trim();
    String memberId = String.valueOf(bankid) + zoneid + branchid;
    Connection connection = DBConnection.getConnection();
    connection.setAutoCommit(false);
    Statement str1 = connection.createStatement();
    try {
      Iterator<String> danIterat = keys.iterator();
      int insdanstatus1 = 0;
      int deldanstatus1 = 0;
      int[] inspaystatus1 = new int[4];
      int[] delpaystatus1 = new int[5];
      int count = 0;
      ResultSet rs = null;
      while (danIterat.hasNext()) {
        String danids = danIterat.next();
        System.out
          .println("submitDanWiseDeallocation danids " + danids);
        String[] danIDPaymentID = danids.split("@");
        System.out.println("dan id== " + danIDPaymentID[0]);
        System.out.println("payment id==111 " + danIDPaymentID[1]);
        String quryforSelect4 = "select count(*) from dan_cgpan_info_temp where pay_id=(select pay_id from dan_cgpan_info_temp where dan_id='" + 
          danIDPaymentID[0] + "') ";
        System.out.println("testing quryforSelect4 " + quryforSelect4);
        rs = str1.executeQuery(quryforSelect4);
        while (rs.next())
          count = rs.getInt(1); 
        System.out.println("size " + size + " count " + count);
        if (size == count) {
          System.out.println("size==count");
          String quryforSelect10 = "update online_payment_detail set PAYMENT_STATUS ='C'  where pay_id='" + 
            danIDPaymentID[1] + "'";
          System.out.println("quryforSelect10" + quryforSelect10);
          delpaystatus1[4] = str1.executeUpdate(quryforSelect10);
          String str2 = "insert into payment_detail_temp_canc  select * from payment_detail_temp where pay_id='" + 
            danIDPaymentID[1] + "'";
          System.out.println("quryforSelect6" + str2);
          delpaystatus1[0] = str1.executeUpdate(str2);
          String quryforSelect8 = "delete from payment_detail_temp  where pay_id='" + 
            danIDPaymentID[1] + "'";
          System.out.println("quryforSelect8" + quryforSelect8);
          delpaystatus1[1] = str1.executeUpdate(quryforSelect8);
          String quryforSelect7 = "insert into dan_cgpan_info_temp_canc  select * from dan_cgpan_info_temp where pay_id='" + 
            danIDPaymentID[1] + "'";
          System.out.println("quryforSelect7" + quryforSelect7);
          delpaystatus1[2] = str1.executeUpdate(quryforSelect7);
          String quryforSelect9 = "delete from dan_cgpan_info_temp where pay_id='" + 
            danIDPaymentID[1] + "'";
          System.out.println("quryforSelect9" + quryforSelect9);
          delpaystatus1[3] = str1.executeUpdate(quryforSelect9);
          System.out.println("delpaystatus1[0] " + delpaystatus1[0]);
          System.out.println("delpaystatus1[1] " + delpaystatus1[1]);
          System.out.println("delpaystatus1[2] " + delpaystatus1[2]);
          System.out.println("delpaystatus1[3] " + delpaystatus1[3]);
          System.out.println("delpaystatus1[4] " + delpaystatus1[4]);
          if (delpaystatus1[0] != 0 && delpaystatus1[1] != 0 && 
            delpaystatus1[2] != 0 && 
            delpaystatus1[3] != 0 && 
            delpaystatus1[4] != 0) {
            connection.commit();
            break;
          } 
          connection.rollback();
          throw new MissingDANDetailsException(
              "Not able to deallocate dan id. problem in  '" + 
              danIDPaymentID[0] + "'");
        } 
        String quryforSelect3 = " update payment_detail_temp  set PAY_AMOUNT=PAY_AMOUNT-(select dci_amount_raised from dan_cgpan_info_temp  where  dan_id='" + 
          
          danIDPaymentID[0] + 
          "') where pay_id=(select a.pay_id from dan_cgpan_info_temp a where   dan_id='" + 
          danIDPaymentID[0] + "') ";
        System.out.println("quryforSelect3 " + quryforSelect3);
        inspaystatus1[2] = str1.executeUpdate(quryforSelect3);
        String quryforSelect6 = " update online_payment_detail  set AMOUNT=AMOUNT-(select dci_amount_raised from dan_cgpan_info_temp  where  dan_id='" + 
          
          danIDPaymentID[0] + 
          "') where pay_id=(select a.pay_id from dan_cgpan_info_temp a where   dan_id='" + 
          danIDPaymentID[0] + "') ";
        System.out.println(" quryforSelect6" + quryforSelect6);
        inspaystatus1[3] = str1.executeUpdate(quryforSelect6);
        String quryforSelect = "insert into dan_cgpan_info_temp_canc  select * from  dan_cgpan_info_temp where  dan_id = '" + 
          danIDPaymentID[0] + "' ";
        inspaystatus1[0] = str1.executeUpdate(quryforSelect);
        System.out.println("quryforSelect" + quryforSelect);
        String quryforSelect2 = "delete from dan_cgpan_info_temp where dan_id ='" + 
          danIDPaymentID[0] + "'";
        System.out.println("quryforSelect" + quryforSelect2);
        inspaystatus1[1] = str1.executeUpdate(quryforSelect2);
        System.out.println("inspaystatus1[0] " + inspaystatus1[0]);
        System.out.println("inspaystatus1[1] " + inspaystatus1[1]);
        System.out.println("inspaystatus1[2] " + inspaystatus1[2]);
        System.out.println("inspaystatus1[3] " + inspaystatus1[3]);
        if (inspaystatus1[0] != 0 && inspaystatus1[1] != 0 && 
          inspaystatus1[2] != 0 && 
          inspaystatus1[3] != 0) {
          connection.commit();
          continue;
        } 
        connection.rollback();
        throw new MissingDANDetailsException(
            " 22 Not able to deallocate dan id. problem in  '" + 
            danIDPaymentID[0] + "'");
      } 
    } catch (SQLException sqlexception) {
      connection.rollback();
      throw new DatabaseException(sqlexception.getMessage());
    } finally {
      DBConnection.freeConnection(connection);
    } 
    request.setAttribute("message", 
        "Selected RPs Modified/ Cancelled Successfully!!!");
    return mapping.findForward("success");
  }
  
  public ActionForward bulkUploadReportFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    ServletOutputStream servletOutputStream = response.getOutputStream();
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
    String strDate = sdf.format(cal.getTime());
    String flag = request.getParameter("Flag");
    System.out.println("Flag---" + flag);
    String contextPath1 = request.getSession(false).getServletContext().getRealPath("");
    String contextPath = PropertyLoader.changeToOSpath(contextPath1);
    HttpSession sess = request.getSession(false);
    String fileType = request.getParameter("fileType");
    System.out.println("fileType----" + fileType);
    String FlowLevel = request.getParameter("FlowLevel");
    System.out.println("@@@@@@@@@@@@FlowLevel :" + FlowLevel);
    ArrayList<ArrayList> bulkDataList = (ArrayList)sess.getAttribute(FlowLevel);
    ArrayList HeaderArrLst = bulkDataList.get(0);
    int NoColumn = HeaderArrLst.size();
    System.out.println("fileType:" + fileType);
    if (fileType.equals("CSVType")) {
      byte[] b = generateCSV(bulkDataList, NoColumn, contextPath);
      response.setContentType("APPLICATION/OCTET-STREAM");
      response.setHeader("Content-Disposition", 
          "attachment; filename=BulkUploadExcelData" + strDate + 
          ".csv");
      servletOutputStream.write(b);
      servletOutputStream.flush();
    } 
    return null;
  }
  
  public byte[] generateCSV(ArrayList<ArrayList> ParamDataList, int No_Column, String contextPath) throws IOException {
    System.out.println("---generateCSV()---");
    StringBuffer strbuff = new StringBuffer();
    ArrayList<String> rowDataLst = new ArrayList<String>();
    ArrayList<String> HeaderLst = ParamDataList.get(0);
    ArrayList<ArrayList> RecordWiseLst = ParamDataList.get(1);
    for (String headerdata : HeaderLst)
      rowDataLst.add(headerdata); 
    for (ArrayList<String> RecordWiseLstObj : RecordWiseLst) {
      for (String SingleRecordDataObj : RecordWiseLstObj) {
        if (SingleRecordDataObj != null) {
          rowDataLst.add(SingleRecordDataObj.replace("<b>", "")
              .replace("</b>", ""));
          continue;
        } 
        rowDataLst.add(SingleRecordDataObj);
      } 
    } 
    ArrayList<String> FinalrowDatalist = new ArrayList();
    int y = 0;
    for (int n = 0; n < rowDataLst.size(); n++) {
      if (n % No_Column == 0 && n != 0) {
        FinalrowDatalist.add(rowDataLst.get(n));
        FinalrowDatalist.add(n + y, "\n");
        y++;
      } else {
        if (rowDataLst.get(n) != null && (
          (String)rowDataLst.get(n)).contains(","))
          rowDataLst.set(n, ((String)rowDataLst.get(n)).replace(",", ";")); 
        FinalrowDatalist.add(rowDataLst.get(n));
      } 
    } 
    String tempStr = FinalrowDatalist.toString().replace("\n,", "\n").replace(" ,", ",").replace(", ", ",");
    strbuff.append(tempStr.substring(1, tempStr.length() - 1));
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
    String strDate = sdf.format(cal.getTime());
    BufferedWriter output = null;
    File genfile = new File(String.valueOf(contextPath) + "\\Download\\DataCSVFile" + 
        strDate + ".csv");
    output = new BufferedWriter(new FileWriter(genfile));
    output.write(strbuff.toString());
    output.flush();
    output.close();
    FileInputStream fis = new FileInputStream(String.valueOf(contextPath) + 
        "\\Download\\DataCSVFile" + strDate + ".csv");
    int x = fis.available();
    byte[] b = new byte[x];
    fis.read(b);
    return b;
  }
  
  public byte[] generateEXL(ArrayList<ArrayList> ParamDataList, int No_Column, String contextPath) throws IOException {
    System.out.println("---generateEXL()---");
    StringBuffer strbuff = new StringBuffer();
    ArrayList<String> rowDataLst = new ArrayList<String>();
    ArrayList<String> HeaderLst = ParamDataList.get(0);
    ArrayList<ArrayList> RecordWiseLst = ParamDataList.get(1);
    HSSFWorkbook workbook = new HSSFWorkbook();
    HSSFSheet sheet = workbook.createSheet("Data");
    HSSFRow hSSFRow = sheet.createRow(0);
    int hdcolnum = 0;
    for (String headerdata : HeaderLst) {
      Cell cell = hSSFRow.createCell(hdcolnum);
      cell.setCellValue(headerdata);
      hdcolnum++;
    } 
    int rownum = 1;
    for (ArrayList<String> RecordWiseLstObj : RecordWiseLst) {
      int colnum = 0;
      hSSFRow = sheet.createRow(rownum);
      for (String SingleRecordDataObj : RecordWiseLstObj) {
        Cell cell = hSSFRow.createCell(colnum);
        cell.setCellValue(SingleRecordDataObj);
        colnum++;
        rowDataLst.add(SingleRecordDataObj);
      } 
      rownum++;
    } 
    Calendar cal = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyyHHmmss");
    String strDate = sdf.format(cal.getTime());
    try {
      FileOutputStream out = new FileOutputStream(new File(String.valueOf(contextPath) + "\\Download\\DataCSVFile" + strDate + ".xls"));
      workbook.write(out);
      out.close();
    } catch (FileNotFoundException e) {
      e.printStackTrace();
    } catch (IOException e) {
      e.printStackTrace();
    } 
    FileInputStream fis = new FileInputStream(String.valueOf(contextPath) + "\\Download\\DataCSVFile" + strDate + ".xls");
    int x = fis.available();
    byte[] b = new byte[x];
    fis.read(b);
    return b;
  }
  
  public ActionForward bulkUploadDetailsReportFile(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "ReportsAction", "bulkUploadApplicationDetailsReports", "Entered");
    User user = getUserInformation(request);
    String userId = user.getUserId();
    System.out.println("User Id===" + userId);
    ReportManager reportManager = new ReportManager();
    ArrayList<ArrayList> bulkUploadDetails = new ArrayList();
    ReportActionForm dynaForm = (ReportActionForm)form;
    String report_value = "";
    String report_value1 = "";
    Date fromDt = dynaForm.getDateOfTheDocument20();
    Date toDt = dynaForm.getDateOfTheDocument21();
    java.sql.Date startDate = new java.sql.Date(fromDt.getTime());
    java.sql.Date endDate = new java.sql.Date(toDt.getTime());
    String id = dynaForm.getMemberId();
    Connection connection = null;
    Statement stmt = null;
    ResultSet rs = null;
    String query = null;
    bulkUploadDetails = reportManager.bulkUploadDetailsReportFile(startDate, endDate, userId, id);
    System.out.println("List----" + bulkUploadDetails);
    HttpSession sess = request.getSession(false);
    sess.setAttribute("FileReport", bulkUploadDetails);
    if (bulkUploadDetails == null || bulkUploadDetails.equals(""))
      throw new MessageException("Data not available"); 
    dynaForm.setBulkUploadReportName(bulkUploadDetails.get(0));
    dynaForm.setBulkUploadReportValue(bulkUploadDetails.get(1));
    return mapping.findForward("success");
  }
  
  public ActionForward bulkMliIdList(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    System.out.println("dz===");
    Log.log(4, "ReportsAction", "bulkUploadApplicationDetailsReports", "Entered");
    User user = getUserInformation(request);
    String bankId = user.getBankId();
    String zoneId = user.getZoneId();
    String branchId = user.getBranchId();
    String memberId = bankId.concat(zoneId).concat(branchId);
    System.out.println("bankId===" + bankId);
    String userId = user.getUserId();
    System.out.println("User Id===" + userId);
    ReportManager reportManager = new ReportManager();
    HttpSession sess = request.getSession(false);
    ArrayList<ArrayList> bulkUploadDetails = new ArrayList();
    ReportActionForm dynaForm = (ReportActionForm)form;
    String report_value = "";
    String report_value1 = "";
    Date fromDt = dynaForm.getDateOfTheDocument20();
    Date toDt = dynaForm.getDateOfTheDocument21();
    java.sql.Date startDate = new java.sql.Date(fromDt.getTime());
    java.sql.Date endDate = new java.sql.Date(toDt.getTime());
    String id = dynaForm.getMemberId();
    if ((id.equals(null) || id.equals("")) && zoneId.equals("0000")) {
      Connection connection = null;
      Statement stmt = null;
      ResultSet rs = null;
      String query = null;
      System.out.println("AAAA");
      bulkUploadDetails = reportManager.bulkUploadDetailsAllReport(startDate, endDate, userId, bankId);
      System.out.println("List----" + bulkUploadDetails.size());
      sess.setAttribute("FileReport", bulkUploadDetails);
      if (bulkUploadDetails == null || bulkUploadDetails.equals(""))
        throw new MessageException("Data not available"); 
      dynaForm.setBulkUploadReportName(bulkUploadDetails.get(0));
      dynaForm.setBulkUploadReportValue(bulkUploadDetails.get(1));
    } 
    return mapping.findForward("success1");
  }
  
  public ActionForward searchHistory(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "ReportsAction", "applicationReport", "Entered");
    ReportActionForm dynaForm = (ReportActionForm)form;
    Log.log(4, "ReportsAction", "applicationReport", "Exited");
    return mapping.findForward("success");
  }
  
  public ActionForward showSearchHistory(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    ReportActionForm dynaForm = (ReportActionForm)form;
    Log.log(4, "ReportsAction", "showSearchHistory", "Entered");
    HttpSession session = request.getSession();
    User user = getUserInformation(request);
    String userid = user.getUserId();
    String bankid = user.getBankId().trim();
    String zoneid = user.getZoneId().trim();
    String branchid = user.getBranchId().trim();
    String memberId = String.valueOf(bankid) + zoneid + branchid;
    Connection connection = null;
    String itpan = dynaForm.getItpan();
    List<ArrayList> list = new ArrayList();
    if (itpan == null && itpan == null)
      throw new MessageException("Please Enter ITPAN."); 
    list = searchITPANHistoryDetails(connection, memberId, itpan, userid);
    dynaForm.setColletralCoulmnName(list.get(0));
    dynaForm.setColletralCoulmnValue(list.get(1));
    return mapping.findForward("colletralReport");
  }
  
  private List searchITPANHistoryDetails(Connection conn, String memberId, String itpan, String userid) throws DatabaseException {
    Log.log(4, "reportaction", "actionHistoryDetailReport()", "Entered!");
    CallableStatement callableStmt = null;
    ResultSet resultset = null;
    ResultSetMetaData resultSetMetaData = null;
    ArrayList<String> coulmName = new ArrayList();
    ArrayList<ArrayList<String>> nestData = new ArrayList();
    ArrayList colletralData = new ArrayList();
    int status = -1;
    String errorCode = null;
    try {
      conn = DBConnection.getConnection();
      callableStmt = conn
        .prepareCall("{?=call CGTSITEMPUSER.Fun_ITPAN_HISTORY_report(?,?,?,?,?)}");
      callableStmt.registerOutParameter(1, 4);
      callableStmt.setString(2, memberId);
      callableStmt.setString(3, itpan);
      callableStmt.setString(4, userid);
      callableStmt.registerOutParameter(5, -10);
      callableStmt.registerOutParameter(6, 12);
      callableStmt.execute();
      status = callableStmt.getInt(1);
      errorCode = callableStmt.getString(6);
      if (status == 1) {
        Log.log(2, "CPDAO", "colletralHybridRetailReport()", 
            "SP returns a 1. Error code is :" + errorCode);
        callableStmt.close();
        throw new DatabaseException(errorCode);
      } 
      if (status == 0) {
        resultset = (ResultSet)callableStmt.getObject(5);
        resultSetMetaData = resultset.getMetaData();
        int coulmnCount = resultSetMetaData.getColumnCount();
        for (int i = 1; i <= coulmnCount; i++)
          coulmName.add(resultSetMetaData.getColumnName(i)); 
        while (resultset.next()) {
          ArrayList<String> columnValue = new ArrayList();
          for (int j = 1; j <= coulmnCount; j++)
            columnValue.add(resultset.getString(j)); 
          nestData.add(columnValue);
        } 
        colletralData.add(0, coulmName);
        colletralData.add(1, nestData);
      } 
      resultset.close();
      resultset = null;
      callableStmt.close();
      callableStmt = null;
      resultSetMetaData = null;
    } catch (SQLException sqlexception) {
      Log.log(2, "CPDAO", "colletralHybridRetailReport()", 
          "Error retrieving all colletral data!");
      throw new DatabaseException(sqlexception.getMessage());
    } finally {
      DBConnection.freeConnection(conn);
    } 
    return colletralData;
  }
  
  public ActionForward updateRecoveryrReceivedCGPANDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws MissingCGPANsException, DatabaseException, NoMemberFoundException {
    Log.log(4, "RPAction", "generateClaimSFDAN", "Entered");
    System.out.println("updateRecoveryrReceivedCGPANDetail:::::::enter for updation");
    HttpSession session = request.getSession(false);
    RPActionForm actionFormobj = (RPActionForm)form;
    Map approveFlags = actionFormobj.getAllocationPaymentCgpans();
    request.setAttribute("PaymentId", request.getParameter("PaymentId"));
    String PaymentId = request.getParameter("PaymentId");
    String selMap = request.getParameter("cgdetail");
    int size = 0;
    if (approveFlags.size() == 0)
      throw new NoMemberFoundException("Please select atleast one CGPAN along with Claim Reference No. or Pay ID to modification."); 
    Set keys = approveFlags.keySet();
    System.out.println("keys size " + keys.size());
    size = approveFlags.size();
    System.out.println("size : " + size);
    User user = getUserInformation(request);
    String userid = user.getUserId();
    String bankid = user.getBankId().trim();
    String zoneid = user.getZoneId().trim();
    String branchid = user.getBranchId().trim();
    String memberId = String.valueOf(bankid) + zoneid + branchid;
    Connection connection = null;
    Statement str1 = null;
    try {
      connection = DBConnection.getConnection();
      connection.setAutoCommit(false);
      str1 = connection.createStatement();
      Iterator<String> cgpanIterat = keys.iterator();
      int insdanstatus1 = 0, deldanstatus1 = 0;
      int[] inspaystatus1 = new int[4];
      int[] delpaystatus1 = new int[5];
      int count = 0;
      List<String> cgArrayList = new ArrayList();
      List<String> payIdArrayList = new ArrayList();
      ResultSet rs = null;
      if (cgpanIterat.hasNext()) {
        String cgpanidds = cgpanIterat.next();
        String[] cgpanIDPaymentI = cgpanidds.split("@");
        String quryforSelect4 = "select count(*) from RECOVRY_AFTER_BEFORE_FST_CLAIM where PAY_ID='" + cgpanIDPaymentI[1] + "' ";
        rs = str1.executeQuery(quryforSelect4);
        if (rs.next()) {
          count = rs.getInt(1);
          rs = null;
        } 
        System.out.println(String.valueOf(size) + " size---total rpcount-----------count " + count);
        if (size == count) {
          String quryforSelect10 = "update online_payment_detail set PAYMENT_STATUS ='C' where pay_id='" + cgpanIDPaymentI[1] + "'";
          System.out.println("recovery quryforSelect10=============1==============================" + quryforSelect10);
          delpaystatus1[4] = str1.executeUpdate(quryforSelect10);
          String quryforSelect6 = "insert into payment_detail_temp_audit  select d.*,'" + userid + "',sysdate from  payment_detail_temp d where pay_id='" + cgpanIDPaymentI[1] + "'";
          System.out.println(" quryforSelect6======================2==================================" + quryforSelect6);
          delpaystatus1[0] = str1.executeUpdate(quryforSelect6);
          String quryforSelect8 = "delete from payment_detail_temp  where pay_id='" + cgpanIDPaymentI[1] + "'";
          System.out.println("quryforSelect8======================3=======================================" + quryforSelect8);
          delpaystatus1[1] = str1.executeUpdate(quryforSelect8);
          String quryforSelect7 = "INSERT INTO RECOVRY_AFT_BEF_FST_CLM_CANCEL SELECT RAFC_ID,PAY_ID,CLM_REF_NO,CGPAN,TYPE_OF_RECOVERY,RECOVERY_AMOUNT,LEGAL_EXPENSES,AMOUNT_REMITTED_TO_CGTMSE,RECOVERY_UPDATION_FLAG, RAFC_ID,RECOVERY_CREATED_DATE,RECOVERY_APPROVED_BY, RECOVERY_APPROVED_DATE, RECOVERY_FLAG,RECV_ONLPAMENT_FLAG,RECV_RECEIVED_MLI_DATE,PENAL_BNK_INTEREST_RATE,TOTAL_PAYMENY_AMT FROM RECOVRY_AFTER_BEFORE_FST_CLAIM WHERE cgpan='" + 
            cgpanIDPaymentI[0] + "' AND pay_id='" + cgpanIDPaymentI[1] + "'";
          System.out.println("quryforSelect7=====================4=======================================" + quryforSelect7);
          delpaystatus1[2] = str1.executeUpdate(quryforSelect7);
          String quryforSelect9 = "delete from RECOVRY_AFTER_BEFORE_FST_CLAIM where cgpan ='" + cgpanIDPaymentI[0] + "' and pay_id='" + cgpanIDPaymentI[1] + "'";
          System.out.println("quryforSelect9=====================5=======================================" + quryforSelect9);
          delpaystatus1[3] = str1.executeUpdate(quryforSelect9);
          System.out.println("recovery delpaystatus1[0] " + delpaystatus1[0]);
          System.out.println("recovery delpaystatus1[1] " + delpaystatus1[1]);
          System.out.println("recovery delpaystatus1[2] " + delpaystatus1[2]);
          System.out.println("recovery delpaystatus1[3] " + delpaystatus1[3]);
          System.out.println("recovery delpaystatus1[4] " + delpaystatus1[4]);
          if (delpaystatus1[0] != 0 && delpaystatus1[1] != 0 && 
            delpaystatus1[2] != 0 && 
            delpaystatus1[3] != 0 && 
            delpaystatus1[4] != 0) {
            connection.commit();
            payIdArrayList.add(cgpanIDPaymentI[1]);
          } else {
            payIdArrayList.add(cgpanIDPaymentI[1]);
            connection.rollback();
            throw new MissingCGPANsException("CGPAN's are unable to deallocate from PayId: '" + cgpanIDPaymentI[1] + "'are unable to deallocate.");
          } 
        } else {
          String quryforSelect3d = "update payment_detail_temp  set PAY_AMOUNT=PAY_AMOUNT-(select AMOUNT_REMITTED_TO_CGTMSE from RECOVRY_AFTER_BEFORE_FST_CLAIM  where cgpan='" + cgpanIDPaymentI[0] + "' AND" + 
            " pay_id='" + cgpanIDPaymentI[1] + "') where pay_id =(select a.pay_id from RECOVRY_AFTER_BEFORE_FST_CLAIM a where cgpan='" + cgpanIDPaymentI[0] + "' AND pay_id='" + cgpanIDPaymentI[1] + "')";
          System.out.println("else recovery quryforSelect3d==============================6========================== " + quryforSelect3d);
          inspaystatus1[2] = str1.executeUpdate(quryforSelect3d);
          String quryforSelect6d = "update online_payment_detail  set AMOUNT=AMOUNT-(select AMOUNT_REMITTED_TO_CGTMSE from RECOVRY_AFTER_BEFORE_FST_CLAIM  where  cgpan='" + cgpanIDPaymentI[0] + "' and pay_id='" + cgpanIDPaymentI[1] + "'), TOTAL_REMITTED_AMOUNT=TOTAL_REMITTED_AMOUNT-(select AMOUNT_REMITTED_TO_CGTMSE from RECOVRY_AFTER_BEFORE_FST_CLAIM  where  cgpan='" + cgpanIDPaymentI[0] + "' and pay_id='" + cgpanIDPaymentI[1] + "')" + 
            " where pay_id=(select a.pay_id from RECOVRY_AFTER_BEFORE_FST_CLAIM a where cgpan='" + cgpanIDPaymentI[0] + "' AND pay_id='" + cgpanIDPaymentI[1] + "')";
          System.out.println("else recovery quryforSelect6d===============================7===========================" + quryforSelect6d);
          inspaystatus1[3] = str1.executeUpdate(quryforSelect6d);
          String quryforSelectd = "INSERT INTO RECOVRY_AFT_BEF_FST_CLM_CANCEL SELECT RAFC_ID,PAY_ID,CLM_REF_NO,CGPAN,TYPE_OF_RECOVERY,RECOVERY_AMOUNT,LEGAL_EXPENSES,AMOUNT_REMITTED_TO_CGTMSE,RECOVERY_UPDATION_FLAG, RAFC_ID,RECOVERY_CREATED_DATE,RECOVERY_APPROVED_BY, RECOVERY_APPROVED_DATE, RECOVERY_FLAG,RECV_ONLPAMENT_FLAG,RECV_RECEIVED_MLI_DATE,PENAL_BNK_INTEREST_RATE,TOTAL_PAYMENY_AMT FROM RECOVRY_AFTER_BEFORE_FST_CLAIM WHERE cgpan='" + 
            cgpanIDPaymentI[0] + "' AND pay_id='" + cgpanIDPaymentI[1] + "'";
          inspaystatus1[0] = str1.executeUpdate(quryforSelectd);
          System.out.println("quryforSelect==============================================8=============================" + quryforSelectd);
          String quryforSelect2d = "delete from RECOVRY_AFTER_BEFORE_FST_CLAIM where cgpan ='" + cgpanIDPaymentI[0] + "' " + 
            "and pay_id='" + cgpanIDPaymentI[1] + "'";
          System.out.println("quryforSelect2d==============================9=============================================" + quryforSelect2d);
          inspaystatus1[1] = str1.executeUpdate(quryforSelect2d);
          if (inspaystatus1[0] != 0 && inspaystatus1[1] != 0 && 
            inspaystatus1[2] != 0 && 
            inspaystatus1[3] != 0) {
            connection.commit();
            cgArrayList.add(cgpanIDPaymentI[0]);
            payIdArrayList.add(cgpanIDPaymentI[1]);
          } else {
            cgArrayList.add(cgpanIDPaymentI[0]);
            payIdArrayList.add(cgpanIDPaymentI[1]);
            connection.rollback();
            throw new MissingCGPANsException("CGPAN:  '" + cgArrayList + "' alogn with PayID: '" + payIdArrayList + "'are unable to deallocate.");
          } 
        } 
      } 
    } catch (SQLException sqlexception) {
      try {
        connection.rollback();
      } catch (SQLException e) {
        throw new DatabaseException(sqlexception.getMessage());
      } 
      throw new DatabaseException(sqlexception.getMessage());
    } finally {
      str1 = null;
      DBConnection.freeConnection(connection);
    } 
    request.setAttribute("dMessage", "Selected RPs Modified / Cancelled Successfully!!!");
    return mapping.findForward("success");
  }
  
  public ActionForward displayRecoveryPaymentIdDetail(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws DatabaseException {
    Log.log(4, "RecoveryRNPaymentAction", "displayRecoveryPaymentIdDetail()", "Entered");
    System.out.println("...............DKR 0*************//********************displayRecoveryPaymentIdDetail()");
    ReportManager manager = new ReportManager();
    RPActionForm rappform = (RPActionForm)form;
    String clmApplicationStatus = "";
    User user = getUserInformation(request);
    String bankid = user.getBankId().trim();
    String zoneid = user.getZoneId().trim();
    String branchid = user.getBranchId().trim();
    String memberId = bankid + zoneid + branchid;
    String paymentId = request.getParameter("PaymentId");
    request.setAttribute("PaymentId", request.getParameter("PaymentId"));
    System.out.println("paymentId" + paymentId);
    ArrayList<RPActionForm> rpArray = new ArrayList();
    RPActionForm actionFormobj = (RPActionForm)form;
    PreparedStatement pstmt = null;
    ResultSet rst = null;
    String PayId = "";
    Connection connection = null;
    Statement allocateStmt = null;
    ResultSet allocateModifyResult1 = null;
    RPActionForm actionForm = null;
    try {
      connection = DBConnection.getConnection();
      allocateStmt = connection.createStatement();
      String query = "select r.PAY_ID,r.CLM_REF_NO,r.CGPAN,s.SSI_UNIT_NAME,r.AMOUNT_REMITTED_TO_CGTMSE,TO_CHAR(r.RECOVERY_CREATED_DATE, 'DD-MM-YYYY')  FROM  RECOVRY_AFTER_BEFORE_FST_CLAIM r,claim_detail c, ssi_detail s where r.pay_id='" + 
        paymentId + "' and r.CLM_REF_NO=c.CLM_REF_NO and  c.BID=s.BID";
      System.out.println("query" + query);
      allocateModifyResult1 = allocateStmt.executeQuery(query);
      System.out.println(query);
      while (allocateModifyResult1.next()) {
        actionForm = new RPActionForm();
        actionForm.setPaymentId(allocateModifyResult1.getString(1));
        actionForm.setClaimRefNo(allocateModifyResult1.getString(2));
        actionForm.setCgpan1(allocateModifyResult1.getString(3));
        actionForm.setUnitName(allocateModifyResult1.getString(4));
        actionForm.setAmmount2(allocateModifyResult1.getDouble(5));
        actionForm.setPayidcreateddate(allocateModifyResult1.getString(6));
        System.out.println(String.valueOf(actionForm.getUnitName()) + " setUnitName displayRecoveryPaymentIdDetail allocatedanIds" + allocateModifyResult1.getString(1));
        if (request.getParameter("PaymentId") != null) {
          actionForm.setPaymentId(request.getParameter("PaymentId"));
        } else if (request.getAttribute("PaymentId") != null) {
          String paymId = (String)request.getAttribute("PaymentId");
          actionForm.setPaymentId(paymId);
        } 
        System.out.println("query" + query);
        rpArray.add(actionForm);
      } 
      actionFormobj.setAllocatepayIDdetails(rpArray);
    } catch (Exception exception) {
      Log.log(2, "RecoveryRNPaymentAction", "displayRecoveryPaymentIdDetail()", exception.getMessage());
      System.out.println(exception.getMessage());
      throw new DatabaseException(exception.getMessage());
    } finally {
      DBConnection.freeConnection(connection);
    } 
    return mapping.findForward("success");
  }
  
  public ActionForward displayallocateRecoveryPaymentFinal(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {
    Log.log(4, "RecoveryRNPaymentAction", "displayallocateRecoveryPaymentFinal()", "Entered");
    Connection connection = null;
    HttpSession session = request.getSession();
    User user = getUserInformation(request);
    System.out.println("user" + user);
    String bankid = user.getBankId().trim();
    String zoneid = user.getZoneId().trim();
    String branchid = user.getBranchId().trim();
    String memberId = String.valueOf(bankid) + zoneid + branchid;
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    ArrayList<RPActionForm> rpArray = new ArrayList();
    RPActionForm actionFormobj = (RPActionForm)form;
    String forwardAction = "";
    try {
      connection = DBConnection.getConnection();
      String query = " select PAY_ID, VIRTUAL_ACCOUNT_NO, AMOUNT, TO_CHAR(Pay_ID_CREAted_date, 'DD-MM-YYYY'),TO_CHAR(PAYMENT_INITIATED_DATE, 'DD-MM-YYYY'),MLI_IFSC_CODE from online_payment_detail where PAYMENT_STATUS='N' and  dan_type='RV' and mem_bnk_id||mem_zne_id||mem_brn_id = '" + 
        memberId + "' AND AMOUNT NOT IN(NULL||0)";
      PreparedStatement allocatePaymentfinalStmt = connection.prepareStatement(query);
      ResultSet allocatePaymentFinalResult = allocatePaymentfinalStmt
        .executeQuery();
      while (allocatePaymentFinalResult.next()) {
        RPActionForm actionForm = new RPActionForm();
        actionForm.setPaymentIdF(allocatePaymentFinalResult.getString(1));
        actionForm.setVitrualAcF(allocatePaymentFinalResult.getString(2));
        actionForm.setAmtF(allocatePaymentFinalResult.getDouble(3));
        actionForm.setRPDATEF(allocatePaymentFinalResult.getString(4));
        actionForm.setPaymentInitiateDate((new SimpleDateFormat("dd-MM-yyyy")).parse(allocatePaymentFinalResult.getString(5)));
        actionForm.setIfscCode(allocatePaymentFinalResult.getString(6));
        String paymentIds = allocatePaymentFinalResult.getString(1);
        actionForm.setPaymentIds("paymentIds");
        rpArray.add(actionForm);
      } 
      if (rpArray.size() == 0) {
        request.setAttribute("dMessage", "Records are not availabe for action.  Kindly submit the recovery and allocate the payment.");
        forwardAction = "success";
      } else {
        forwardAction = "displayallocateRecoveryPaymentFinal";
      } 
      actionFormobj.setAllocatepaymentFinal(rpArray);
    } catch (Exception exception) {
      Log.log(2, "RecoveryRNPaymentAction", "displayallocateRecoveryPaymentFinal()....Exception", exception.getMessage());
      throw new DatabaseException(exception.getMessage());
    } finally {
      DBConnection.freeConnection(connection);
    } 
    return mapping.findForward(forwardAction);
  }
}
