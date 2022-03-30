package com.cgtsi.action;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.cgtsi.actionform.ReportActionCOForm;
import com.cgtsi.actionform.ReportActionForm;
import com.cgtsi.admin.Administrator;
import com.cgtsi.admin.User;
import com.cgtsi.common.Log;
import com.cgtsi.common.NoDataException;
import com.cgtsi.registration.NoMemberFoundException;
import com.cgtsi.reports.GeneralReport;
import com.cgtsi.util.DBConnection;

public class ReportActionCO extends BaseAction{

	public ActionForward memberStateReportInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// System.out.println("entered===");
//		ReportActionForm dynaForm = (ReportActionForm) form;
		DynaActionForm dynaForm = (DynaActionForm) form;

		// ArrayList list=getReportType();
		// dynaForm.setReportTypeList(list);
		// String memberId = null;
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DATE);
		month = month - 1;
		day = day + 1;
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DATE, day);
		Date prevdate = cal.getTime();

		GeneralReport general = new GeneralReport();
		general.setDateOfTheDocument20(prevdate);
		general.setDateOfTheDocument21(date);
		BeanUtils.copyProperties(dynaForm, general);
		/*
		 * User user = getUserInformation(request); String bankId =
		 * user.getBankId(); String zoneId = user.getZoneId(); String branchId =
		 * user.getBranchId(); String memberId1 =
		 * bankId.concat(zoneId).concat(branchId);
		 * System.out.println("memberId"+memberId1);
		 * general.setMemberId(memberId1);
		 */

		// dynaForm.set("memberId", memberId);
		return mapping.findForward("coletralInputPage");
	}
	
	public ActionForward memberStateGSTReport(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// System.out.println("entered===");
//	ReportActionForm dynaForm = (ReportActionForm) form;
		DynaActionForm dynaForm = (DynaActionForm) form;
		// ArrayList list=getReportType();
		// dynaForm.setReportTypeList(list);
		// String memberId = null;
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DATE);
		month = month - 1;
		day = day + 1;
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DATE, day);
		Date prevdate = cal.getTime();

		GeneralReport general = new GeneralReport();
		general.setDateOfTheDocument20(prevdate);
		general.setDateOfTheDocument21(date);
		BeanUtils.copyProperties(dynaForm, general);
		
		  User user = getUserInformation(request); String bankId = user.getBankId();
		  String zoneId = user.getZoneId(); String branchId = user.getBranchId();
		  String memberId1 = bankId.concat(zoneId).concat(branchId);
		  System.out.println("memberId"+memberId1); general.setMemberId(memberId1);
		 

		// dynaForm.set("memberId", memberId);
		/*
		 * User user = (User) getUserInformation(request); String bankid =
		 * (user.getBankId()).trim(); String zoneid = (user.getZoneId()).trim(); String
		 * branchid = (user.getBranchId()).trim(); String memberId = bankid + zoneid +
		 * branchid;
		 */

		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		 ArrayList<ReportActionCOForm> memberStateGSTReportList = new ArrayList<ReportActionCOForm>();

		try {
			conn = DBConnection.getConnection();

			String appDetails = "select MEM_BANK_NAME,STE_NAME as state_name,GST_NO from MEMBER_BANK_STATE_GST  where MEM_BNK_ID ||MEM_ZNE_ID||MEM_BRN_ID='"
			+ memberId1 + "'";
			System.out.println("Query : "+ appDetails);

			// System.out.println("getDanRecord : "+memberId);

			stmt = conn.createStatement();
			rs = stmt.executeQuery(appDetails);

			/*
			 * String MEM_BANK_NAME = ""; String state_name = ""; String GST_NO = "";
			 */

			while (rs.next()) {
				ReportActionCOForm reportActionCOForm=new ReportActionCOForm();

				reportActionCOForm.setBankName(rs.getString("MEM_BANK_NAME"));
				reportActionCOForm.setStateName(rs.getString("state_name"));
				reportActionCOForm.setGstNo(rs.getString("GST_NO"));
				
//				MEM_BANK_NAME = rs.getString("MEM_BANK_NAME");
//				state_name = rs.getString("state_name");
//				GST_NO = rs.getString("GST_NO");
				
				memberStateGSTReportList.add(reportActionCOForm);
			

			} 
			if (memberStateGSTReportList.size() > 0) {
				request.setAttribute("memberStateGSTReportList", memberStateGSTReportList);
			} else {
				throw new NoDataException(" Data are not available.");
			}

			// System.out.println("MEM_BANK_NAME "+MEM_BANK_NAME);
//			request.setAttribute("MEM_BANK_NAME", MEM_BANK_NAME);
//			request.setAttribute("state_name", state_name);
//			request.setAttribute("GST_NO", GST_NO);


			
		} catch (Exception e) {
			
			  throw new NoMemberFoundException(
			  "Something Went wrong, Kindly contact to support Team(support@cgtmse.in) " +
			  e.getMessage());
			  
		}

		finally {
			DBConnection.freeConnection(conn);
		}
		return mapping.findForward("colletralReport");
	}
	
	public ActionForward memberStateFormInput(ActionMapping mapping,
			ActionForm form, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		// System.out.println("entered===");
//		ReportActionForm dynaForm = (ReportActionForm) form;
		DynaActionForm dynaForm = (DynaActionForm) form;
		// ArrayList list=getReportType();
		// dynaForm.setReportTypeList(list);
		// String memberId = null;
		Date date = new Date();
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int month = cal.get(Calendar.MONTH);
		int day = cal.get(Calendar.DATE);
		month = month - 1;
		day = day + 1;
		cal.set(Calendar.MONTH, month);
		cal.set(Calendar.DATE, day);
		Date prevdate = cal.getTime();

		GeneralReport general = new GeneralReport();
		general.setDateOfTheDocument20(prevdate);
		general.setDateOfTheDocument21(date);
		BeanUtils.copyProperties(dynaForm, general);
		String StateName=request.getParameter("state_name");
		System.out.println("StateName "+StateName);
		
		  Administrator admin = new Administrator(); ArrayList states =
		  admin.getAllStates();
		 
		
		dynaForm.set("states", states);
		
		  User user = getUserInformation(request); String bankId =
		  user.getBankId(); String zoneId = user.getZoneId(); String branchId =
		  user.getBranchId(); String memberId1 =
		  bankId.concat(zoneId).concat(branchId);
		  System.out.println("memberId"+memberId1);
		  general.setMemberId(memberId1);
		

		// dynaForm.set("memberId", memberId);
		
		Statement stmt = null;
		ResultSet rs = null;
		Connection conn = null;
		 ArrayList<ReportActionCOForm> memberStateGSTReportList = new ArrayList<ReportActionCOForm>();

		try {
			conn = DBConnection.getConnection();

			String appDetails = "select MEM_BNK_ID,MEM_ZNE_ID,MEM_BRN_ID,MEM_BANK_NAME,STE_NAME,GST_NO,MEM_REGS_ADDRESS,MEM_PERSON_NAME,MEM_PHONE_NO,EMAIL_ID,HEAD_OFFICE,RECIPIENT_PLACE,PINCODE,MEM_REGS_ADDRESS_2 "
					+ " from member_bank_state_gst  where MEM_BNK_ID ||MEM_ZNE_ID||MEM_BRN_ID='"
			+ memberId1 + "' and STE_NAME='" + StateName + "'";
			System.out.println("Query : "+ appDetails);

			// System.out.println("getDanRecord : "+memberId);

			stmt = conn.createStatement();
			rs = stmt.executeQuery(appDetails);

			/*
			 * String MEM_BANK_NAME = ""; String state_name = ""; String GST_NO = "";
			 */

			while (rs.next()) {
				ReportActionCOForm reportActionCOForm=new ReportActionCOForm();
				reportActionCOForm.setBankId(rs.getString("MEM_BNK_ID"));
				reportActionCOForm.setZoneId(rs.getString("MEM_ZNE_ID"));
				reportActionCOForm.setZoneId(rs.getString("MEM_BRN_ID"));
				reportActionCOForm.setZoneId(rs.getString("MEM_BANK_NAME"));
				reportActionCOForm.setZoneId(rs.getString("STE_NAME"));
				reportActionCOForm.setZoneId(rs.getString("GST_NO"));
				reportActionCOForm.setZoneId(rs.getString("MEM_REGS_ADDRESS"));
				reportActionCOForm.setZoneId(rs.getString("MEM_PERSON_NAME"));
				reportActionCOForm.setZoneId(rs.getString("MEM_PHONE_NO"));
				reportActionCOForm.setZoneId(rs.getString("EMAIL_ID"));
				reportActionCOForm.setZoneId(rs.getString("HEAD_OFFICE"));
//				reportActionCOForm.setZoneId(rs.getString("RECIPIENT_PLACE"));
				reportActionCOForm.setZoneId(rs.getString("PINCODE"));
				reportActionCOForm.setZoneId(rs.getString("MEM_REGS_ADDRESS_2"));
				
//				MEM_BANK_NAME = rs.getString("MEM_BANK_NAME");
//				state_name = rs.getString("state_name");
//				GST_NO = rs.getString("GST_NO");
				
				memberStateGSTReportList.add(reportActionCOForm);
			

			} 
			if (memberStateGSTReportList.size() > 0) {
				request.setAttribute("memberStateGSTReportList", memberStateGSTReportList);
			} else {
				throw new NoDataException(" Data are not available.");
			}

			// System.out.println("MEM_BANK_NAME "+MEM_BANK_NAME);
//			request.setAttribute("MEM_BANK_NAME", MEM_BANK_NAME);
//			request.setAttribute("state_name", state_name);
//			request.setAttribute("GST_NO", GST_NO);


			
		} catch (Exception e) {
			
			  throw new NoMemberFoundException(
			  "Something Went wrong, Kindly contact to support Team(support@cgtmse.in) " +
			  e.getMessage());
			  
		}

		finally {
			DBConnection.freeConnection(conn);
		}
		
		return mapping.findForward("updateMemGST");
	}
	
	/*
	 * public ActionForward memberStateFormInput(ActionMapping mapping, ActionForm
	 * form, HttpServletRequest request, HttpServletResponse response) throws
	 * Exception {
	 * 
	 * 
	 * 
	 * return mapping.findForward("updateMemGST"); }
	 */
	public ActionForward getDistricts(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "AdministrationAction", "getDistricts", "Entered");
		Administrator admin = new Administrator();
		DynaActionForm dynaActionForm = (DynaActionForm) form;
		String state = (String) dynaActionForm.get("state");

		ArrayList districts = admin.getAllDistricts(state);
		dynaActionForm.set("recPlace", districts);

		request.setAttribute("recPlace", "1");

		admin = null;
		districts = null;

		Log.log(4, "AdministrationAction", "getDistricts", "Exited");

		return mapping.findForward("updateMemGST");
	}
	
}
