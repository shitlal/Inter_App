// FrontEnd Plus GUI for JAD
// DeCompiled : LoginAction.class

package com.cgtsi.action;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.URL;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

import com.cgtsi.admin.Administrator;
import com.cgtsi.admin.Hint;
import com.cgtsi.admin.MenuOptions;
import com.cgtsi.admin.PasswordManager;
import com.cgtsi.admin.User;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.InactiveUserException;
import com.cgtsi.common.InvalidPasswordException;
import com.cgtsi.common.Log;
import com.cgtsi.common.Login;
import com.cgtsi.common.NoUserFoundException;
import com.cgtsi.common.UserLockedException;
import com.cgtsi.registration.MLIInfo;
import com.cgtsi.registration.Registration;
import java.net.*; 
import java.io.*; 
import java.util.*; 
import java.net.InetAddress; 
// Referenced classes of package com.cgtsi.action:
//            BaseAction

public class LoginAction extends BaseAction {

	private static final String ALPHA_NUM = "!@#$%0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public LoginAction() {
	}

	public ActionForward login(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws DatabaseException, UserLockedException,
			InactiveUserException, InvalidPasswordException,
			NoUserFoundException {
		Log.log(4, "LoginAction", "login", "Entered");
		DynaActionForm dynaActionForm = (DynaActionForm) form;
		String memberId = (String) dynaActionForm.get("memberId");
		HttpSession session = request.getSession(false);
		String loggedInUser = null;
		PasswordManager passwordManager = new PasswordManager();
		if (memberId.equals("000000000000"))
			loggedInUser = "CGTSI";
		else
			loggedInUser = "MLI";
		session.setAttribute("loggedInUser", loggedInUser);
		String userId = (String) dynaActionForm.get("userId");
		System.out.println((new StringBuilder()).append("userId:")
				.append(userId).toString());
		String password = (String) dynaActionForm.get("password");
		System.out.println("Password tested");
		String ipAddress = "";
		String hostName = "";
		String proxyName = "";
		String sessionId = "";
		
		// Returns the instance of InetAddress containing 
		// local host name and address 
		/*String systemipaddress = ""; 
		try
		{ 
		InetAddress localhost = InetAddress.getLocalHost(); 
		System.out.println("System IP Address : " + 
					(localhost.getHostAddress()).trim()); 

		// Find public IP address 
		
		
			URL url_name = new URL("http://158.100.60.13:8012/CGINTER_RJU/jsp/Home.jsp"); 

			BufferedReader sc = 
			new BufferedReader(new InputStreamReader(url_name.openStream())); 

			// reads system IPAddress 
			systemipaddress = sc.readLine().trim(); 
		} 
		catch (Exception e) 
		{ 
			systemipaddress = "Cannot Execute Properly"; 
		} 
		System.out.println("Public IP Address: " + systemipaddress +"\n"); */
		try {
			InetAddress i = InetAddress.getLocalHost();
			ipAddress = request.getRemoteAddr();
			//System.out.println("ipAddress=="+i);
			hostName = request.getRemoteHost();
			//System.out.println("hostName=="+hostName);
			proxyName = request.getHeader("VIA");
			//System.out.println("proxyName=="+proxyName);
			
			sessionId = session.getId();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String bankId = memberId.substring(0, 4);
		String zoneId = memberId.substring(4, 8);
		String branchId = memberId.substring(8, 12);
		Registration registration = new Registration();
		registration.updateLoginInformation(bankId, zoneId, branchId,ipAddress, hostName, proxyName, sessionId, userId);
		registration.getMemberDetails(bankId, zoneId, branchId);
		userId = userId.toUpperCase();
		dynaActionForm.set("userId", userId);
		User user = Login.login(memberId, userId, password);
		userId = user.getUserId();
		dynaActionForm.set("userId", user.getUserId());
		session.setAttribute((String) dynaActionForm.get("userId"), user);
		session.setAttribute("USER_ID", (String) dynaActionForm.get("userId"));
		session.setAttribute("USER", user);
		Administrator admin = new Administrator();
		ArrayList userPrivileges = admin.getPrivileges(userId);
		dynaActionForm.set("userPrivileges", userPrivileges);
		user.setPrivileges(userPrivileges);
		ArrayList alerts = admin.getAlerts(userId);
		String message = admin.getMessage(userId);
		Log.log(5, "LoginAction", "login",
				(new StringBuilder()).append("message ").append(message)
						.toString());
		dynaActionForm.set("message", message);
		dynaActionForm.set("alerts", alerts);
		String shortName = user.getShortName();
		String bankName = user.getBankName();
		String zoneName = user.getZoneName();
		String branchName = user.getBranchName();
		Log.log(5,
				"LoginAction",
				"login",
				(new StringBuilder()).append("bank id ")
						.append(user.getBankId()).toString());
		Log.log(5,
				"LoginAction",
				"login",
				(new StringBuilder()).append("zone id ")
						.append(user.getZoneId()).toString());
		Log.log(5,
				"LoginAction",
				"login",
				(new StringBuilder()).append("branch id ")
						.append(user.getBranchId()).toString());
		Log.log(5, "LoginAction", "login",
				(new StringBuilder()).append("bank Name ").append(bankName)
						.toString());
		Log.log(5, "LoginAction", "login",
				(new StringBuilder()).append("zone name ").append(zoneName)
						.toString());
		Log.log(5, "LoginAction", "login",
				(new StringBuilder()).append("branch name ").append(branchName)
						.toString());
		Log.log(5, "LoginAction", "login",
				(new StringBuilder()).append("short name ").append(shortName)
						.toString());
		if (shortName == null) {
			bankName = bankName.length() <= 10 ? bankName : bankName.substring(
					0, 10);
			dynaActionForm.set("bankName", bankName);
		} else {
			dynaActionForm.set("bankName", shortName);
		}
		if (zoneName.length() > 10)
			zoneName = zoneName.substring(0, 10);
		if (branchName.length() > 10)
			branchName = branchName.substring(0, 10);
		dynaActionForm.set("zoneName", zoneName);
		dynaActionForm.set("branchName", branchName);
		MLIInfo mliInfo = registration.getMemberDetails(userId);
		Log.log(5, "LoginAction", "login",
				(new StringBuilder()).append("before setting...bank id...")
						.append(mliInfo.getBankId()).toString());
		session.setAttribute("MEMBER_INFO", mliInfo);
		if (user.isPasswordExpired()) {
			Log.log(5, "LoginAction", "login", "Password is expired");
			session.setAttribute("PASSWORD_EXPIRED", "true");
			Hint hint = user.getHint();
			request.setAttribute("hintQuestion", hint.getHintQuestion());
			request.setAttribute("hintAnswer", hint.getHintAnswer());
			request.setAttribute("emailId", user.getEmailId());
			return mapping.findForward("passwordExpired");
		} else {
			Log.log(5, "LoginAction", "login", "Password is Valid");
			Log.log(4, "LoginAction", "login", "Exited");
			return mapping.findForward("success");
		}
	}

	public ActionForward showCGTSIHome(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws DatabaseException {
		Log.log(4, "LoginAction", "showHome", "Exited");
		DynaActionForm dynaForm = (DynaActionForm) form;
		User user = getUserInformation(request);
		String userId = user.getUserId();
		Log.log(5, "LoginAction", "showHome",
				(new StringBuilder()).append("userId ").append(userId)
						.toString());
		Administrator admin = new Administrator();
		ArrayList alerts = admin.getAlerts(userId);
		String message = admin.getMessage(userId);
		Log.log(5, "LoginAction", "showHome",
				(new StringBuilder()).append("message ").append(message)
						.toString());
		dynaForm.set("message", message);
		dynaForm.set("alerts", alerts);
		MLIInfo memberInfo = getMemberInfo(request);
		Log.log(5,
				"LoginAction",
				"showHome",
				(new StringBuilder()).append("bank id and distict ")
						.append(memberInfo.getBankId()).append(" ... ")
						.append(memberInfo.getDistrict()).toString());
		Log.log(4, "LoginAction", "showHome", "Exited");
		return mapping.findForward("success");
	}

	public ActionForward getMainMenu(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "LoginAction", "getMainMenu", "Entered");
		setMainMenu(request);
		Log.log(4, "LoginAction", "getMainMenu", "Exited");
		return mapping.findForward("success");
	}

	private ArrayList setMainMenu(HttpServletRequest request) {
		Log.log(4, "LoginAction", "setMainMenu", "Entered");
		String menuIcon = request.getParameter("menuIcon");
		ArrayList mainMenus = new ArrayList();
		ArrayList mainMenuValues = new ArrayList();
		if (menuIcon != null) {
			User user = getUserInformation(request);
			ArrayList userPrivileges = user.getPrivileges();
			mainMenus.add(MenuOptions.getMenu("HOME_SELECT"));
			mainMenuValues.add(MenuOptions.getMenuAction("HOME_SELECT"));
			if (menuIcon.equals("IO")) {
				if (userPrivileges.contains("ADD_INWARD")) {
					mainMenus.add(MenuOptions.getMenu("IO_INWARD"));
					mainMenuValues.add(MenuOptions.getMenuAction("IO_INWARD"));
					mainMenus.add(MenuOptions.getMenu("IO_INWARD_NEW"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("IO_INWARD_NEW"));
					mainMenus.add(MenuOptions
							.getMenu("IO_ADD_INWARD_SOURCE_NAME"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("IO_ADD_INWARD_SOURCE_NAME"));
					mainMenus.add(MenuOptions
							.getMenu("IO_UPDATE_INWARD_DETAILS"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("IO_UPDATE_INWARD_DETAILS"));
					mainMenus.add(MenuOptions.getMenu("IO_WORKSHOP_ENTRY"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("IO_WORKSHOP_ENTRY"));
					String loggedUserId = user.getUserId();
					if (loggedUserId.equalsIgnoreCase("ADMIN")
							|| loggedUserId.equalsIgnoreCase("CPRAB0001")
							|| loggedUserId.equalsIgnoreCase("PUSLATH0001")) {
						mainMenus
								.add(MenuOptions
										.getMenu("IO_UPDATE_SCHEME_PROPAGATION_DETAILS"));
						mainMenuValues
								.add(MenuOptions
										.getMenuAction("IO_UPDATE_SCHEME_PROPAGATION_DETAILS"));
					}
				}
				if (userPrivileges.contains("ADD_OUTWARD")) {
					mainMenus.add(MenuOptions.getMenu("IO_OUTWARD"));
					mainMenuValues.add(MenuOptions.getMenuAction("IO_OUTWARD"));
				}
				if (!userPrivileges.contains("UPLOAD_DOCUMENTS"))
					;
				if (userPrivileges.contains("SEARCH_DOCUMENTS")) {
					mainMenus.add(MenuOptions.getMenu("IO_SEARCH_DOCUMENTS"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("IO_SEARCH_DOCUMENTS"));
				}
			} else if (menuIcon.equals("RS")) {
				mainMenus.add(MenuOptions.getMenu("CLM_ACCOUNT_REPORT_DATA"));
				mainMenuValues.add(MenuOptions.getMenuAction("CLM_ACCOUNT_REPORT_DATA"));
				mainMenus.add(MenuOptions.getMenu("ACCOUNT_HISTORY_REPORT"));
				mainMenuValues.add(MenuOptions
						.getMenuAction("ACCOUNT_HISTORY_REPORT"));
				mainMenus.add(MenuOptions.getMenu("RS_APPLICATION_STATUS"));
				mainMenuValues.add(MenuOptions
						.getMenuAction("RS_APPLICATION_STATUS"));
				mainMenus.add(MenuOptions.getMenu("RS_SEARCH_HISTORY"));
				mainMenuValues.add(MenuOptions
						.getMenuAction("RS_SEARCH_HISTORY"));
				mainMenus.add(MenuOptions.getMenu("RS_EPCS_REPORT"));
				mainMenuValues.add(MenuOptions.getMenuAction("RS_EPCS_REPORT"));
				if (userPrivileges.contains("REPORTS")) {
					mainMenus
							.add(MenuOptions.getMenu("RS_APPLICATION_DETAILS"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RS_APPLICATION_DETAILS"));
					mainMenus.add(MenuOptions
							.getMenu("RS_APPLICATION_RELATED_REPORT"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RS_APPLICATION_RELATED_REPORT"));
					mainMenus
							.add(MenuOptions.getMenu("RS_ASF_SUMMERY_DETAILS"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RS_ASF_SUMMERY_DETAILS"));
					String loggedUserId = user.getUserId();
					if (loggedUserId.equalsIgnoreCase("ADMIN")
							|| loggedUserId.equalsIgnoreCase("SHRSHUK0001")
							|| loggedUserId.equalsIgnoreCase("GYANESH0001")
							|| loggedUserId.equalsIgnoreCase("CPRAB0001")
							|| loggedUserId.equalsIgnoreCase("PUSLATH0001")) {
						mainMenus.add(MenuOptions
								.getMenu("RS_ASF2011_NOTPAID_SUMMARY_REPORT"));
						mainMenuValues
								.add(MenuOptions
										.getMenuAction("RS_ASF2011_NOTPAID_SUMMARY_REPORT"));
					}
					mainMenus.add(MenuOptions.getMenu("RS_REPORT_OF_PROPOSAL"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RS_REPORT_OF_PROPOSAL"));
					mainMenus.add(MenuOptions
							.getMenu("RS_GUARANTEE_COVER_ISSUED"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RS_GUARANTEE_COVER_ISSUED"));
					mainMenus.add(MenuOptions.getMenu("RS_MLI_LIST"));
					mainMenuValues
							.add(MenuOptions.getMenuAction("RS_MLI_LIST"));
					mainMenus.add(MenuOptions
							.getMenu("RS_PENDING_APPLICATION_MLI_WISE"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RS_PENDING_APPLICATION_MLI_WISE"));
					mainMenus.add(MenuOptions
							.getMenu("RS_MONTHLY_PROGRESS_REPORT"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RS_MONTHLY_PROGRESS_REPORT"));
					mainMenus.add(MenuOptions.getMenu("RS_DAN_REPORTS-ALL"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RS_DAN_REPORTS-ALL"));
					// mainMenus.add(MenuOptions.getMenu("RS_MIS"));
					// mainMenuValues.add(MenuOptions.getMenuAction("RS_MIS"));
					mainMenus.add(MenuOptions.getMenu("RS_GM_REPORTS"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RS_GM_REPORTS"));
					mainMenus.add(MenuOptions.getMenu("RS_RP_REPORTS"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RS_RP_REPORTS"));
					mainMenus.add(MenuOptions.getMenu("QUERY_BUILDER"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("QUERY_BUILDER"));
					mainMenus.add(MenuOptions.getMenu("RS_CLAIMS_DETAILS"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RS_CLAIMS_DETAILS"));
					mainMenus.add(MenuOptions
							.getMenu("RS_SECURITIZATION_REPORT"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RS_SECURITIZATION_REPORT"));
					mainMenus.add(MenuOptions
							.getMenu("RS_WOMEN_ENTREPRENEUR_REPORT"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RS_WOMEN_ENTREPRENEUR_REPORT"));
					mainMenus.add(MenuOptions.getMenu("RS_INWARD_REPORT"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RS_INWARD_REPORT"));
					mainMenus.add(MenuOptions.getMenu("RS_NPA_DETAIL_REPORT"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RS_NPA_DETAIL_REPORT"));
					mainMenus.add(MenuOptions.getMenu("RS_INVESTMENT_REPORT"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RS_INVESTMENT_REPORT"));
					mainMenus.add(MenuOptions
							.getMenu("RS_SCHEM_PROPAGATION_DETAILS"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RS_SCHEM_PROPAGATION_DETAILS"));
					mainMenus.add(MenuOptions.getMenu("RS_OUTSTANDING_REPORT_INPUT"));
					mainMenuValues.add(MenuOptions.getMenuAction("RS_OUTSTANDING_REPORT_INPUT"));
					// mainMenus.add(MenuOptions.getMenu("RS_MONTH_WISE_REPORT_NEW"));
					// mainMenuValues.add(MenuOptions.getMenuAction("RS_MONTH_WISE_REPORT_NEW"));

					if (loggedUserId.equalsIgnoreCase("ADMIN")
							|| loggedUserId.equalsIgnoreCase("ADMIN")
							|| loggedUserId.equalsIgnoreCase("CPRAB0001")
							|| loggedUserId.equalsIgnoreCase("PUSLATH0001")) {
						mainMenus.add(MenuOptions
								.getMenu("RS_CGPAN_COMPLE_HISTORY"));
						mainMenuValues.add(MenuOptions.getMenuAction("RS_CGPAN_COMPLE_HISTORY"));
					
					}
				} else {
					mainMenus
							.add(MenuOptions.getMenu("RS_APPLICATION_DETAILS"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RS_APPLICATION_DETAILS"));
					mainMenus.add(MenuOptions
							.getMenu("RS_APPLICATION_RELATED_REPORT"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RS_APPLICATION_RELATED_REPORT"));
					mainMenus
							.add(MenuOptions.getMenu("RS_ASF_SUMMERY_DETAILS"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RS_ASF_SUMMERY_DETAILS"));
					mainMenus.add(MenuOptions
							.getMenu("RS_MONTHLY_PROGRESS_REPORT"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RS_MONTHLY_PROGRESS_REPORT"));
					mainMenus.add(MenuOptions.getMenu("RS_DAN_REPORTS-ALL"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RS_DAN_REPORTS-ALL"));
					mainMenus.add(MenuOptions.getMenu("RS_GM_REPORTS"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RS_GM_REPORTS"));
					// mainMenus.add(MenuOptions.getMenu("RS_MIS"));
					// mainMenuValues.add(MenuOptions.getMenuAction("RS_MIS"));
					mainMenus.add(MenuOptions.getMenu("RS_MLI_LIST"));
					mainMenuValues
							.add(MenuOptions.getMenuAction("RS_MLI_LIST"));
					mainMenus.add(MenuOptions.getMenu("RS_RP_REPORTS"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RS_RP_REPORTS"));
					mainMenus.add(MenuOptions.getMenu("RS_CLAIMS_DETAILS"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RS_CLAIMS_DETAILS"));
					mainMenus.add(MenuOptions.getMenu("RS_NPA_DETAIL_REPORT"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RS_NPA_DETAIL_REPORT"));
					mainMenus.add(MenuOptions
							.getMenu("CLAIM_DECL_UNDET_TAKING"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("CLAIM_DECL_UNDET_TAKING"));
					mainMenus.add(MenuOptions.getMenu("NPA_UPGRADATION_LIST"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("NPA_UPGRADATION_LIST"));
					mainMenus.add(MenuOptions.getMenu("RS_QUERY_REPORT_INPUT"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RS_QUERY_REPORT_INPUT"));
					
					mainMenus.add(MenuOptions.getMenu("RS_OUTSTANDING_REPORT_INPUT"));
					mainMenuValues.add(MenuOptions.getMenuAction("RS_OUTSTANDING_REPORT_INPUT"));


				}

			} else if (menuIcon.equals("AD")) {
				if (userPrivileges.contains("REGISTER_COLLECTING_BANK")
						|| userPrivileges
								.contains("MODIFY_COLLECTING_BANK_DETAILS")) {
					mainMenus.add(MenuOptions.getMenu("AD_COLLECTING_BANK"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("AD_COLLECTING_BANK"));
				}
				if (userPrivileges.contains("REGISTER_MLI")
						|| userPrivileges.contains("ASSIGN_COLLECTING_BANK")
						|| userPrivileges.contains("MODIFY_COLLECTING_BANK")
						|| userPrivileges
								.contains("DEFINE_ORGANIZATION_STRUCTURE")
						|| userPrivileges.contains("DEACTIVATE_MEMBER")
						|| userPrivileges.contains("ACTIVATE_MEMBER")) {
					mainMenus.add(MenuOptions.getMenu("AD_MLI"));
					mainMenuValues.add(MenuOptions.getMenuAction("AD_MLI"));
				}
				if (userPrivileges.contains("ADD_MODIFY_ROLES")) {
					mainMenus.add(MenuOptions.getMenu("AD_ROLES_MGMT"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("AD_ROLES_MGMT"));
				}
				if (userPrivileges.contains("CREATE_CGTSI_USER")
						|| userPrivileges.contains("CREATE_MLI_USER")
						|| userPrivileges.contains("MODIFY_USER_DETAILS")
						|| userPrivileges.contains("DEACTIVATE_USER")
						|| userPrivileges.contains("REACTIVATE_USER")
						|| userPrivileges
								.contains("ASSIGN_MODIFY_ROLES_AND_PRIVILEGES")) {
					mainMenus.add(MenuOptions.getMenu("AD_USER_MGMT"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("AD_USER_MGMT"));
				}
				mainMenus.add(MenuOptions.getMenu("AD_PASSWORD_MGMT"));
				mainMenuValues.add(MenuOptions
						.getMenuAction("AD_PASSWORD_MGMT"));
				mainMenus.add(MenuOptions.getMenu("AD_MEMBER_STATE_GST"));
				mainMenuValues.add(MenuOptions
						.getMenuAction("AD_MEMBER_STATE_GST"));
				mainMenus.add(MenuOptions.getMenu("AD_MODIFY_MLI_ADDRESS"));
				mainMenuValues.add(MenuOptions
						.getMenuAction("AD_MODIFY_MLI_ADDRESS"));
			//	mainMenus.add(MenuOptions.getMenu("MEMBER_STATE_REPORT"));
			//	mainMenuValues.add(MenuOptions
				//		.getMenuAction("MEMBER_STATE_REPORT"));
				if (userPrivileges.contains("ENTER_AUDIT_DETAILS")
						|| userPrivileges.contains("REVIEW_AUDIT_DETAILS")) {
					mainMenus.add(MenuOptions.getMenu("AD_AUDIT"));
					mainMenuValues.add(MenuOptions.getMenuAction("AD_AUDIT"));
				}
				if (userPrivileges.contains("BROADCAST_MESSAGE")) {
					mainMenus.add(MenuOptions.getMenu("AD_BROADCAST_MESSAGE"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("AD_BROADCAST_MESSAGE"));
				}
				if (userPrivileges.contains("UPDATE_MASTER_TABLE")) {
					mainMenus.add(MenuOptions.getMenu("AD_UPDATE_MASTER"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("AD_UPDATE_MASTER"));
				}
				if (userPrivileges.contains("DAY_END_PROCESS")) {
					mainMenus.add(MenuOptions.getMenu("AD_DAY_END_PROCESSES"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("AD_DAY_END_PROCESSES"));
				}

				mainMenus.add(MenuOptions.getMenu("ACCOUNT_INFO_NEW"));
				mainMenuValues.add(MenuOptions
						.getMenuAction("ACCOUNT_INFO_NEW"));
				// Diksha
				/*
				 * mainMenus.add(MenuOptions.getMenu("AD_SEND_MAIL"));
				 * mainMenuValues
				 * .add(MenuOptions.getMenuAction("AD_SEND_MAIL"));
				 */
				String bankid = user.getBankId();
				String zoneid = user.getZoneId();
				String branchid = user.getBranchId();
				if ("0000".equals(zoneid) && "0000".equals(branchid)) {
					mainMenus.add(MenuOptions.getMenu("AD_REG_NPA_APP"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("AD_REG_NPA_APP"));
					// mainMenus.add(MenuOptions.getMenu("AD_ACCOUNT_INFO_NEW"));
					// mainMenuValues.add(MenuOptions.getMenuAction("AD_ACCOUNT_INFO_NEW"));

				}
				 mainMenus.add(MenuOptions.getMenu("MEMBER_STATE_REPORT"));
				 mainMenuValues.add(MenuOptions.getMenuAction("MEMBER_STATE_REPORT"));
				if (user.getUserId().contains("0")) {
					mainMenus.add(MenuOptions.getMenu("AD_REG_FOR_NPA"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("AD_REG_FOR_NPA"));
					// mainMenus.add(MenuOptions.getMenu("ACCOUNT_INFO_NEW"));
					// mainMenuValues.add(MenuOptions.getMenuAction("ACCOUNT_INFO_NEW"));
				}
			} else if (menuIcon.equals("CP")) {
				if (userPrivileges.contains("CLAIM_FIRST_INSTALLMENT")
						|| userPrivileges.contains("CLAIM_SECOND_INSTALLMENT")) {
					mainMenus.add(MenuOptions.getMenu("CP_CLAIM_FOR"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("CP_CLAIM_FOR"));
					String loggedUserId = user.getUserId();
					if (loggedUserId.equalsIgnoreCase("ADMIN")
							|| loggedUserId.equalsIgnoreCase("URTATA0001")
							|| loggedUserId.equalsIgnoreCase("PRADH0001")
							|| loggedUserId.equalsIgnoreCase("MUKKUMA0001")
							|| loggedUserId.equalsIgnoreCase("GAUKAPO0001")
							|| loggedUserId.equalsIgnoreCase("AVSYAM0001")) {
						mainMenus.add(MenuOptions
								.getMenu("Claim_Change_Status"));
						mainMenuValues.add(MenuOptions
								.getMenuAction("Claim_Change_Status"));
					}
				}
				if (userPrivileges.contains("OTS_REQUEST")
						|| userPrivileges.contains("OTS_APPROVAL")) {
					// mainMenus.add(MenuOptions.getMenu("CP_OTS"));
					// mainMenuValues.add(MenuOptions.getMenuAction("CP_OTS"));
				}
				// Diksha 26/05/2017
				/*
				 * if(userPrivileges.contains("CLAIM_APPROVAL")) {
				 * mainMenus.add(MenuOptions.getMenu("CP_APPROVAL"));
				 * mainMenuValues.add(MenuOptions.getMenuAction("CP_APPROVAL"));
				 * mainMenus.add(MenuOptions.getMenu("CP_PROCESSING"));
				 * mainMenuValues
				 * .add(MenuOptions.getMenuAction("CP_PROCESSING")); }
				 */// 26/05/2017

				if (userPrivileges.contains("PERIODIC_INFO_APPROVAL")) {
					String loggedUserId = user.getUserId();
					if (loggedUserId.equalsIgnoreCase("ADMIN")
							|| loggedUserId.equalsIgnoreCase("URTATA0001")
							|| loggedUserId.equalsIgnoreCase("PRADH0001")
							|| loggedUserId.equalsIgnoreCase("MUKKUMA0001")
							|| loggedUserId.equalsIgnoreCase("AVSYAM0001")) {
						mainMenus.add(MenuOptions.getMenu("CP_APPROVAL_NEW"));
						mainMenuValues.add(MenuOptions
								.getMenuAction("CP_APPROVAL_NEW"));
					}
				}
				String loggedUserId = user.getUserId();
				// if(loggedUserId.equalsIgnoreCase("demouser"))
				{
					mainMenus
							.add(MenuOptions.getMenu("CP_CLAIM_RECOVERY_INFO"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("CP_CLAIM_RECOVERY_INFO"));
					// mainMenus.add(MenuOptions.getMenu("CP_SUBMIT_DU"));//
					// //FRO
					// DEMOUSER
					// mainMenuValues.add(MenuOptions.getMenuAction("CP_SUBMIT_DU"));//
					// FRO DEMOUSER
					// mainMenus.add(MenuOptions.getMenu("AD_ACCOUNT_INFO_NEW"));
					// mainMenuValues.add(MenuOptions.getMenuAction("AD_ACCOUNT_INFO_NEW"));//rajuk
					if (userPrivileges.contains("APPROVE_B_T_MODIFICATION")
							|| userPrivileges.contains("CLAIM_DNU_APPROVAL")) {
						mainMenus.add(MenuOptions.getMenu("CP_SUBMIT_DU"));
						mainMenuValues.add(MenuOptions
								.getMenuAction("CP_SUBMIT_DU"));
						mainMenus.add(MenuOptions
								.getMenu("AD_ACCOUNT_INFO_NEW"));
						mainMenuValues.add(MenuOptions
								.getMenuAction("AD_ACCOUNT_INFO_NEW"));// rajuk

					}
				}
			} else if (menuIcon.equals("GM")) {
				// Diksha 26/05/2017
				/*
				 * mainMenus.add(MenuOptions.getMenu("RP_REQUEST_TENURE"));
				 * mainMenuValues
				 * .add(MenuOptions.getMenuAction("RP_REQUEST_TENURE"));
				 */
				if (userPrivileges.contains("MODIFY_BORROWER_DETAILS")) {
					mainMenus.add(MenuOptions
							.getMenu("GM_MODIFY_BORROWER_DETAILS"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("GM_MODIFY_BORROWER_DETAILS"));
					mainMenus.add(MenuOptions.getMenu("RP_REQUEST_TENURE"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RP_REQUEST_TENURE"));
				}
				if (userPrivileges.contains("PERIODIC_INFO")) {
					mainMenus.add(MenuOptions.getMenu("GM_PERIODIC_INFO"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("GM_PERIODIC_INFO"));
					mainMenus.add(MenuOptions.getMenu("GM_VIEW_CGPAN"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("GM_VIEW_CGPAN"));
				}
				if (userPrivileges.contains("UPDATE_REPAYMENT_SCHEDULE")) {
					mainMenus.add(MenuOptions
							.getMenu("GM_UPDATE_REPAYMENT_SCHEDULE"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("GM_UPDATE_REPAYMENT_SCHEDULE"));
				}
				if (userPrivileges.contains("SHIFT_CGPAN_OR_BORROWER")) {
					mainMenus.add(MenuOptions
							.getMenu("GM_SHIFT_CGPAN_OR_BORROWER"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("GM_SHIFT_CGPAN_OR_BORROWER"));
				}
				if (!userPrivileges.contains("SHIFT_CGPAN_OR_BORROWER"))
					;
				if (!userPrivileges.contains("EXPORT_PERIDIC_INFO"))
					;
				if (userPrivileges.contains("APPLICATION_CLOSURE")) {
					mainMenus.add(MenuOptions.getMenu("GM_CLOSURE"));
					mainMenuValues.add(MenuOptions.getMenuAction("GM_CLOSURE"));
				}
				if (userPrivileges.contains("MODIFY_BORROWER_DETAILS")) {
					mainMenus.add(MenuOptions.getMenu("GM_CLOSURE_REQUEST"));
					mainMenuValues.add(MenuOptions.getMenuAction("GM_CLOSURE_REQUEST"));
					 mainMenus.add(MenuOptions.getMenu("GM_OUTSTANDING_REQUEST_MENU"));                    // 27/1/2020
					 mainMenuValues.add(MenuOptions.getMenuAction("GM_OUTSTANDING_REQUEST_MENU"));         // 27/1/2020
					 //mainMenus.add(MenuOptions.getMenu("GM_OUTSTANDING_REQUEST_MENU_NEW"));                    // 27/1/2020
					 //mainMenuValues.add(MenuOptions.getMenuAction("GM_OUTSTANDING_REQUEST_MENU_NEW"));         // 27/1/2020
					// mainMenus.add(MenuOptions.getMenu("GM_UPDATION_GSTIN"));
					// mainMenuValues.add(MenuOptions.getMenuAction("GM_UPDATION_GSTIN"));//rajuk
					mainMenus.add(MenuOptions.getMenu("GA_FLAGGING_OF_WCTL"));
                    mainMenuValues.add(MenuOptions.getMenuAction("GA_FLAGGING_OF_WCTL"));
                  

				}
				if (userPrivileges.contains("PERIODIC_INFO_APPROVAL")) {
					String loggedUserId = user.getUserId();
					if (loggedUserId.equalsIgnoreCase("ADMIN")
							|| loggedUserId.equalsIgnoreCase("CPRAB0001")
							|| loggedUserId.equalsIgnoreCase("PUSLATH0001")) {
						mainMenus
								.add(MenuOptions.getMenu("GM_CLOSURE_APPROVE"));
						mainMenuValues.add(MenuOptions
								.getMenuAction("GM_CLOSURE_APPROVE"));
					}
				}
				if (userPrivileges.contains("PERIODIC_INFO_APPROVAL")) {
					mainMenus.add(MenuOptions
							.getMenu("GM_PERIODIC_INFO_APPROVAL"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("GM_PERIODIC_INFO_APPROVAL"));
					// Diksha 26/05/2017
					/*
					 * mainMenus.add(MenuOptions.getMenu(
					 * "GM_BORROWER_DETAILS_APPROVAL"));
					 * mainMenuValues.add(MenuOptions
					 * .getMenuAction("GM_BORROWER_DETAILS_APPROVAL"));
					 */

				}
				if (userPrivileges.contains("APPROVE_NPA_UPGRADATION")) {
					mainMenus.add(MenuOptions.getMenu("GM_NPA_UPDATION_LIST"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("GM_NPA_UPDATION_LIST"));
				}
				if (userPrivileges.contains("APPROVE_B_T_MODIFICATION")) {
					mainMenus.add(MenuOptions.getMenu("GM_BORROWER_DETAILS_APPROVAL"));
					mainMenuValues.add(MenuOptions.getMenuAction("GM_BORROWER_DETAILS_APPROVAL"));

					mainMenus.add(MenuOptions.getMenu("GM_TENURE_APPROVE"));//  rajuk
			        mainMenuValues.add(MenuOptions.getMenuAction("GM_TENURE_APPROVE"));
				     mainMenus.add(MenuOptions.getMenu("GA_FLAGGING_OF_WCTL_CHECKER"));
	                 mainMenuValues.add(MenuOptions.getMenuAction("GA_FLAGGING_OF_WCTL_CHECKER"));

				}
			} else if (menuIcon.equals("IF")) {
				if (userPrivileges.contains("ENTER_BUDGET_DETAILS")) {
					mainMenus.add(MenuOptions.getMenu("IF_BUDGET_DETAILS"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("IF_BUDGET_DETAILS"));
				}
				if (userPrivileges.contains("ENTER_INFLOW_DETAILS")) {
					mainMenus.add(MenuOptions.getMenu("IF_INFLOW_DETAILS"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("IF_INFLOW_DETAILS"));
				}
				if (userPrivileges.contains("ENTER_OUTFLOW_DETAILS")) {
					mainMenus.add(MenuOptions.getMenu("IF_OUTFLOW_DETAILS"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("IF_OUTFLOW_DETAILS"));
				}
				if (userPrivileges.contains("SET_CEILING_FOR_INVESTMENT")) {
					mainMenus.add(MenuOptions
							.getMenu("IF_SET_INVESTMENT_CEILING"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("IF_SET_INVESTMENT_CEILING"));
				}
				if (userPrivileges.contains("ENTER_INVESTMENT_DETAILS")) {
					mainMenus.add(MenuOptions.getMenu("IF_INVESTMENT_DETAILS"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("IF_INVESTMENT_DETAILS"));
					mainMenus.add(MenuOptions.getMenu("IF_VOUCHER_GENERATION"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("IF_VOUCHER_GENERATION"));
					mainMenus.add(MenuOptions.getMenu("IF_CHEQUE_LEAVES"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("IF_CHEQUE_LEAVES"));
					mainMenus.add(MenuOptions
							.getMenu("IF_UPDATE_INVESTMENT_DETAILS"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("IF_UPDATE_INVESTMENT_DETAILS"));
				}
				if (userPrivileges.contains("IF_CALCULATE_EXPOSUER")) {
					mainMenus.add(MenuOptions.getMenu("IF_CALCULATE_EXPOSURE"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("IF_CALCULATE_EXPOSURE"));
					mainMenus.add(MenuOptions.getMenu("IF_MAKE_INVESTMENT"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("IF_MAKE_INVESTMENT"));
					mainMenus.add(MenuOptions
							.getMenu("IF_MISCELLANEOUS_RECEIPTS"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("IF_MISCELLANEOUS_RECEIPTS"));
				}
				if (userPrivileges.contains("TDS_DETAILS")) {
					mainMenus.add(MenuOptions.getMenu("IF_TDS_DETAILS"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("IF_TDS_DETAILS"));
				}
				if (userPrivileges.contains("STATEMENT_DETAILS")) {
					mainMenus.add(MenuOptions.getMenu("IF_CHEQUE_DETAILS"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("IF_CHEQUE_DETAILS"));
				}
				if (userPrivileges.contains("STATEMENT_DETAILS")) {
					mainMenus.add(MenuOptions.getMenu("IF_HVC"));
					mainMenuValues.add(MenuOptions.getMenuAction("IF_HVC"));
				}
				if (userPrivileges.contains("STATEMENT_DETAILS")) {
					mainMenus.add(MenuOptions.getMenu("IF_BANK_STATEMENT"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("IF_BANK_STATEMENT"));
				}
				if (userPrivileges.contains("PROJECT_EXPECTED_CLAIMS")) {
					mainMenus.add(MenuOptions
							.getMenu("IF_PROJECT_EXPECTED_CLAIMS"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("IF_PROJECT_EXPECTED_CLAIMS"));
				}
				if (userPrivileges.contains("IF_UPDATE_MASTER_TABLES")) {
					mainMenus.add(MenuOptions.getMenu("IF_PAYMENT_DETAILS"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("IF_PAYMENT_DETAILS"));
					mainMenus
							.add(MenuOptions.getMenu("IF_UPDATE_MASTER_TABLE"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("IF_UPDATE_MASTER_TABLE"));
				}
				if (userPrivileges.contains("IF_REPORT")) {
					mainMenus.add(MenuOptions.getMenu("IF_REPORT"));
					mainMenuValues.add(MenuOptions.getMenuAction("IF_REPORT"));
				}
				if (userPrivileges.contains("STATEMENT_DETAILS")) {
					mainMenus.add(MenuOptions.getMenu("IF_FILE_UPLOAD"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("IF_FILE_UPLOAD"));
				}
			} else if (menuIcon.equals("RP")) {
				String loggedUserId1;
				if (userPrivileges.contains("ALLOCATE_PAYMENTS")) {
					loggedUserId1 = user.getUserId();
					// Diksha 26/05/2017
					/*
					 * mainMenus.add(MenuOptions.getMenu("RP_ALLOCATE_PAYMENTS"))
					 * ; mainMenuValues.add(MenuOptions.getMenuAction(
					 * "RP_ALLOCATE_PAYMENTS")); loggedUserId1 =
					 * user.getUserId();
					 * mainMenus.add(MenuOptions.getMenu("RP_MAP_NEFT_PAYMETNS"
					 * )); mainMenuValues.add(MenuOptions.getMenuAction(
					 * "RP_MAP_NEFT_PAYMETNS"));
					 */
					mainMenus.add(MenuOptions.getMenu("RP_ALLOCATE_PAYMENTS_ONLINEMENU"));
					mainMenuValues.add(MenuOptions.getMenuAction("RP_ALLOCATE_PAYMENTS_ONLINEMENU"));
					mainMenus.add(MenuOptions.getMenu("ASF_PAYMENT_THROUGH_NEFT_OR_RTGS"));
					mainMenuValues.add(MenuOptions.getMenuAction("ASF_PAYMENT_THROUGH_NEFT_OR_RTGS"));
					mainMenus.add(MenuOptions.getMenu("RP_ALLOCATE_TENURE_ONLINEMENU"));
					mainMenuValues.add(MenuOptions.getMenuAction("RP_ALLOCATE_TENURE_ONLINEMENU"));
				}
				/*if (userPrivileges.contains("ASF_PAYMENT_ONLINE")) {
					loggedUserId1 = user.getUserId();
				
					mainMenus.add(MenuOptions.getMenu("ASF_PAYMENT_THROUGH_NEFT_OR_RTGS_NEW"));
					mainMenuValues.add(MenuOptions.getMenuAction("ASF_PAYMENT_THROUGH_NEFT_OR_RTGS_NEW"));
				}*/
				if (userPrivileges.contains("APPROPRIATE_PAYMENTS")) {
					mainMenus.add(MenuOptions
							.getMenu("RP_APPROPRIATE_ALLOCATE_PAYMENTS"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RP_APPROPRIATE_ALLOCATE_PAYMENTS"));
				}
				if (userPrivileges.contains("VIEW_PENDING_PAYMENTS")) {
					mainMenus.add(MenuOptions
							.getMenu("RP_VIEW_PENDING_PAYMENTS"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RP_VIEW_PENDING_PAYMENTS"));
				}
				if (userPrivileges.contains("APPROPRIATE_PAYMENTS")) {
					mainMenus.add(MenuOptions
							.getMenu("RP_DE_ALLOCATE_PAYMENTS"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RP_DE_ALLOCATE_PAYMENTS"));
				}
				loggedUserId1 = user.getUserId();
				if (loggedUserId1.equalsIgnoreCase("ADMIN")
						|| loggedUserId1.equalsIgnoreCase("AVSYAM0001")
						|| loggedUserId1.equalsIgnoreCase("ASHBHOV0001")
						|| loggedUserId1.equalsIgnoreCase("CPRAB0001")
						|| loggedUserId1.equalsIgnoreCase("PUSLATH0001")) {
					mainMenus.add(MenuOptions
							.getMenu("RP_MODIFY_PAYMENT_DETAIL"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RP_MODIFY_PAYMENT_DETAIL"));
				}
				if (userPrivileges.contains("APPROPRIATE_PAYMENTS")) {
					mainMenus.add(MenuOptions
							.getMenu("RP_APPROPRIATE_PAYMENTS"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RP_APPROPRIATE_PAYMENTS"));
				}
				if (userPrivileges.contains("APPROPRIATE_PAYMENTS")) {
					mainMenus.add(MenuOptions
							.getMenu("RP_BATCH_APPROPRIATE_PAYMENT_NEW"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RP_BATCH_APPROPRIATE_PAYMENT_NEW"));
				}
				if (userPrivileges.contains("RE_ALLOCATE_PAYMENTS")) {
					mainMenus.add(MenuOptions
							.getMenu("RP_RE_ALLOCATE_PAYMENTS"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RP_RE_ALLOCATE_PAYMENTS"));
				}
				if (userPrivileges.contains("GENERATE_SHORT_DAN")) {
					mainMenus.add(MenuOptions
							.getMenu("RP_GENERATE_DAN_FOR_SHORT"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RP_GENERATE_DAN_FOR_SHORT"));
				}
				if (userPrivileges.contains("GENERATE_CGDAN")) {
					mainMenus.add(MenuOptions.getMenu("RP_GENERATE_CGDAN"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RP_GENERATE_CGDAN"));
				}
				String loggedUserId;
				if (userPrivileges.contains("GENERATE_SFDAN")) {
					mainMenus.add(MenuOptions.getMenu("RP_GENERATE_SFDAN"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RP_GENERATE_SFDAN"));
					mainMenus.add(MenuOptions
							.getMenu("RP_GENERATE_SFDAN_FOR_EXPIRY"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RP_GENERATE_SFDAN_FOR_EXPIRY"));
					loggedUserId = user.getUserId();
					if (loggedUserId.equalsIgnoreCase("ADMIN")) {
						mainMenus.add(MenuOptions
								.getMenu("RP_GENERATE_BATCH_SFDAN"));
						mainMenuValues.add(MenuOptions
								.getMenuAction("RP_GENERATE_BATCH_SFDAN"));
						mainMenus.add(MenuOptions
								.getMenu("RP_GENERATE_SFDAN_FOR_CLAIM"));
						mainMenuValues.add(MenuOptions
								.getMenuAction("RP_GENERATE_SFDAN_FOR_CLAIM"));
					}
				}
				if (userPrivileges.contains("GENERATE_CLDAN")) {
					mainMenus.add(MenuOptions.getMenu("RP_GENERATE_CLDAN"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RP_GENERATE_CLDAN"));
				}
				if (!userPrivileges.contains("GENERATE_VOUCHER"))
					;
				if (!userPrivileges.contains("WAIVE_SHORT_AMOUNTS"))
					;
				loggedUserId = user.getUserId();
				if (loggedUserId.equalsIgnoreCase("ADMIN")
						&& userPrivileges.contains("PRINT_PAY_IN_SLIP")) {
					mainMenus.add(MenuOptions.getMenu("RP_PRINT_PAY_IN_SLIP"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RP_PRINT_PAY_IN_SLIP"));
				}
			} else if (menuIcon.equals("AP")) {
				if (userPrivileges.contains("ADD_APPLICATION")) {
					mainMenus.add(MenuOptions.getMenu("AP_GUARANTEE_FOR"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("AP_GUARANTEE_FOR"));
				}
				if (userPrivileges.contains("MODIFY_APPLICATION")) {
					mainMenus.add(MenuOptions.getMenu("AP_MODIFY_APPLICATION"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("AP_MODIFY_APPLICATION"));
				}
				if (userPrivileges.contains("MODIFY_APPLICATION")) {
					mainMenus.add(MenuOptions.getMenu("AP_MODIFY_BRANCHNAME"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("AP_MODIFY_BRANCHNAME"));
				}
				if (userPrivileges.contains("APPLICATION_APPROVAL")) {
					mainMenus.add(MenuOptions.getMenu("AP_APPROVAL"));
					mainMenuValues
							.add(MenuOptions.getMenuAction("AP_APPROVAL"));
				}
				if (!userPrivileges.contains("APP_SPECIAL_MESSAGE"))
					;
				if (userPrivileges.contains("APPLICATION_RE_APPROVAL")) {
					mainMenus.add(MenuOptions.getMenu("AP_RE_APPROVAL"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("AP_RE_APPROVAL"));
				}
				if (userPrivileges.contains("APPLICATION_RE_APPROVAL")) {
					mainMenus.add(MenuOptions.getMenu("AP_REJECT_APPLICATION"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("AP_REJECT_APPLICATION"));
				}
				String loggedUserId = user.getUserId();
				// if((loggedUserId.equalsIgnoreCase("DEMOUSER") ||
				// loggedUserId.equalsIgnoreCase("OFFOFFI0104") ||
				// loggedUserId.equalsIgnoreCase("AKHJALO0001") ||
				// loggedUserId.equalsIgnoreCase("SISSAHA0001") ||
				// loggedUserId.equalsIgnoreCase("NREDD0001") ||
				// loggedUserId.equalsIgnoreCase("SKUCH0001")) &&
				// userPrivileges.contains("ADD_APPLICATION"))
				System.out.println("userPrivileges " + userPrivileges);
				
				mainMenus.add(MenuOptions.getMenu("AP_FILE_UPLOAD_NEW"));
	            mainMenuValues.add(MenuOptions.getMenuAction("AP_FILE_UPLOAD_NEW"));
				// Diksha
				if (userPrivileges.contains("EXCEL_UPLOAD")) {
					mainMenus.add(MenuOptions.getMenu("AP_FILE_UPLOAD"));
					mainMenuValues.add(MenuOptions.getMenuAction("AP_FILE_UPLOAD"));
					
				}
			} else if (menuIcon.equals("RI")) {
				if (userPrivileges.contains("LIMIT_SET_UP")) {
					mainMenus.add(MenuOptions.getMenu("RI_LIMIT_SETUP"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RI_LIMIT_SETUP"));
				}
				if (userPrivileges.contains("RISK_CALCULATE_EXPOSUER")) {
					mainMenus.add(MenuOptions.getMenu("RI_CALCULATE_EXPOSURE"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RI_CALCULATE_EXPOSURE"));
				}
				if (userPrivileges.contains("GENERATE_EXPOSURE_REPORT")) {
					mainMenus.add(MenuOptions
							.getMenu("RI_GENERATE_EXPOSURE_REPORT"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RI_GENERATE_EXPOSURE_REPORT"));
					mainMenus.add(MenuOptions.getMenu("RI_SUB_SCHEME_REPORT"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RI_SUB_SCHEME_REPORT"));
				}
				if (userPrivileges.contains("SET_UPDATE_SUB_SCHEME_PARAMS")) {
					mainMenus.add(MenuOptions
							.getMenu("RI_SUB_SCHEME_PARAMETERS"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RI_SUB_SCHEME_PARAMETERS"));
					mainMenus
							.add(MenuOptions.getMenu("RI_DEFAULT_RATE_REPORT"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("RI_DEFAULT_RATE_REPORT"));
				}
			} else if (menuIcon.equals("TC")) {
				if (userPrivileges.contains("FILE_UPLOAD")) {
					mainMenus.add(MenuOptions.getMenu("FILE_UPLOAD"));
					mainMenuValues
							.add(MenuOptions.getMenuAction("FILE_UPLOAD"));
					mainMenus.add(MenuOptions.getMenu("DOWNLOAD_THIN_CLIENT"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("DOWNLOAD_THIN_CLIENT"));
				}
				mainMenus.add(MenuOptions.getMenu("DOWNLOAD_JRE"));
				mainMenuValues.add(MenuOptions.getMenuAction("DOWNLOAD_JRE"));
			} else if (menuIcon.equals("SC")) {
				if (userPrivileges.contains("SECURITIZATION")) {
					mainMenus.add(MenuOptions.getMenu("SECURITIZATION"));
					mainMenuValues.add(MenuOptions
							.getMenuAction("SECURITIZATION"));
				}
				if (userPrivileges.contains("ADD_DONOR_DETAILS")
						|| userPrivileges.contains("ADD_PARTICIPATING_BANKS")
						|| userPrivileges.contains("ADD_SSI_MEMBERS")) {
					mainMenus.add(MenuOptions.getMenu("MCGS"));
					mainMenuValues.add(MenuOptions.getMenuAction("MCGS"));
				}
			}
		}
		request.setAttribute("mainMenuValues", mainMenuValues);
		request.setAttribute("mainMenus", mainMenus);
		Log.log(4, "LoginAction", "setMainMenu", "Exited");
		return mainMenus;
	}

	public ActionForward getSubMenu(ActionMapping mapping, ActionForm form,
			HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		Log.log(4, "LoginAction", "getSubMenu", "Entered");
		String mainMenu = request.getParameter("mainMenu");
		String menuIcon = request.getParameter("menuIcon");
		setSubMenu(menuIcon, mainMenu, request);
		Log.log(4, "LoginAction", "getSubMenu", "Exited");
		return mapping.findForward("success");
	}

	private void setSubMenu(String menuIcon, String mainMenu,
			HttpServletRequest request) {
		Log.log(4, "LoginAction", "setSubMenu", "Entered");
		ArrayList subMenus = new ArrayList();
		ArrayList subMenuValues = new ArrayList();
		if (menuIcon != null) {
			User user = getUserInformation(request);
			String bankID = user.getBankId();
			String zoneID = user.getZoneId();
			String branchID = user.getBranchId();
			ArrayList userPrivileges = user.getPrivileges();
			subMenus.add(MenuOptions.getMenu("SUB_HOME_SELECT"));
			subMenuValues.add(MenuOptions.getMenuAction("SUB_HOME_SELECT"));
			if (menuIcon.equals("IO")) {
				if (mainMenu.equals(MenuOptions.getMenu("IO_INWARD"))) {
					subMenus.add(MenuOptions.getMenu("IO_INWARD_ADD_INWARD"));
					subMenuValues.add(MenuOptions
							.getMenuAction("IO_INWARD_ADD_INWARD"));
					subMenus.add(MenuOptions.getMenu("IO_INWARD_SHOW_SUMMARY"));
					subMenuValues.add(MenuOptions
							.getMenuAction("IO_INWARD_SHOW_SUMMARY"));
				} else if (mainMenu.equals(MenuOptions.getMenu("IO_OUTWARD"))) {
					subMenus.add(MenuOptions.getMenu("IO_OUTWARD_ADD_OUTWARD"));
					subMenuValues.add(MenuOptions
							.getMenuAction("IO_OUTWARD_ADD_OUTWARD"));
					subMenus.add(MenuOptions.getMenu("IO_OUTWARD_SHOW_SUMMARY"));
					subMenuValues.add(MenuOptions
							.getMenuAction("IO_OUTWARD_SHOW_SUMMARY"));
				}
			} else if (menuIcon.equals("RS")) {
				if (mainMenu.equals(MenuOptions
						.getMenu("RS_REPORT_OF_PROPOSAL"))) {
					subMenus.add(MenuOptions
							.getMenu("RS_REPORT_OF_PROPOSAL_SIZE"));
					subMenuValues.add(MenuOptions
							.getMenuAction("RS_REPORT_OF_PROPOSAL_SIZE"));
					subMenus.add(MenuOptions
							.getMenu("RS_REPORT_OF_PROPOSAL_SECTOR"));
					subMenuValues.add(MenuOptions
							.getMenuAction("RS_REPORT_OF_PROPOSAL_SECTOR"));
					subMenus.add(MenuOptions
							.getMenu("RS_REPORT_OF_PROPOSAL_MONTHLY"));
					subMenuValues.add(MenuOptions
							.getMenuAction("RS_REPORT_OF_PROPOSAL_MONTHLY"));
				}
				if (mainMenu.equals(MenuOptions
						.getMenu("RS_APPLICATION_DETAILS"))) {
					if (userPrivileges.contains("REPORTS")) {
						subMenus.add(MenuOptions
								.getMenu("RS_APPLICATION_DETAILS_MLI"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_APPLICATION_DETAILS_MLI"));
						subMenus.add(MenuOptions
								.getMenu("RS_APPLICATION_DETAILS_MLI_RSF"));
						subMenuValues
								.add(MenuOptions
										.getMenuAction("RS_APPLICATION_DETAILS_MLI_RSF"));
						subMenus.add(MenuOptions
								.getMenu("RS_APPLICATION_DETAILS_MLI_SLAB"));
						subMenuValues
								.add(MenuOptions
										.getMenuAction("RS_APPLICATION_DETAILS_MLI_SLAB"));
						subMenus.add(MenuOptions
								.getMenu("RS_APPLICATION_DETAILS_MLI_STATE"));
						subMenuValues
								.add(MenuOptions
										.getMenuAction("RS_APPLICATION_DETAILS_MLI_STATE"));
						subMenus.add(MenuOptions
								.getMenu("RS_APPLICATION_DETAILS_PENDING"));
						subMenuValues
								.add(MenuOptions
										.getMenuAction("RS_APPLICATION_DETAILS_PENDING"));
						subMenus.add(MenuOptions
								.getMenu("RS_APPLICATION_DETAILS_STATUS"));
						subMenuValues
								.add(MenuOptions
										.getMenuAction("RS_APPLICATION_DETAILS_STATUS"));
										
                        subMenus.add(MenuOptions.getMenu("RS_BULKUPLOAD_APPLICATION_DETAILS"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_BULKUPLOAD_APPLICATION_DETAILS"));
						subMenus.add(MenuOptions
								.getMenu("RS_DC_HANDICRAFT_REPORT"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_DC_HANDICRAFT_REPORT"));
						subMenus.add(MenuOptions
								.getMenu("RS_DC_HANDLOOM_REPORT"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_DC_HANDLOOM_REPORT"));
					} else {
						subMenus.add(MenuOptions
								.getMenu("RS_APPLICATION_DETAILS_MLI"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_APPLICATION_DETAILS_MLI"));
						subMenus.add(MenuOptions
								.getMenu("RS_APPLICATION_DETAILS_MLI_SLAB"));
						subMenuValues
								.add(MenuOptions
										.getMenuAction("RS_APPLICATION_DETAILS_MLI_SLAB"));
						subMenus.add(MenuOptions
								.getMenu("RS_APPLICATION_DETAILS_MLI_STATE"));
						subMenuValues
								.add(MenuOptions
										.getMenuAction("RS_APPLICATION_DETAILS_MLI_STATE"));
						subMenus.add(MenuOptions
								.getMenu("RS_APPLICATION_DETAILS_STATUS"));
						subMenuValues
								.add(MenuOptions
										.getMenuAction("RS_APPLICATION_DETAILS_STATUS"));
										
                        subMenus.add(MenuOptions.getMenu("RS_BULKUPLOAD_APPLICATION_DETAILS"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_BULKUPLOAD_APPLICATION_DETAILS"));
						subMenus.add(MenuOptions
								.getMenu("RS_DC_HANDICRAFT_REPORT"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_DC_HANDICRAFT_REPORT"));
						subMenus.add(MenuOptions
								.getMenu("RS_DC_HANDLOOM_REPORT"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_DC_HANDLOOM_REPORT"));
					}
					String loggedUserId = user.getUserId();
					if (loggedUserId.equalsIgnoreCase("ADMIN")) {
						subMenus.add(MenuOptions
								.getMenu("RS_APPLICATION_DETAILS_PENDING"));
						subMenuValues
								.add(MenuOptions
										.getMenuAction("RS_APPLICATION_DETAILS_PENDING"));
					}
					if (!userPrivileges.contains("REPORTS"))
						;
				}
				if (mainMenu.equals(MenuOptions
						.getMenu("RS_APPLICATION_RELATED_REPORT"))) {
					if (userPrivileges.contains("REPORTS")) {
						subMenus.add(MenuOptions
								.getMenu("RS_APPLICATION_REPORT"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_APPLICATION_REPORT"));
						subMenus.add(MenuOptions
								.getMenu("RS_CGPAN_HISTORY_REPORT"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_CGPAN_HISTORY_REPORT"));
						subMenus.add(MenuOptions
								.getMenu("RS_RECIEVED_APPLICATION_REPORT"));
						subMenuValues
								.add(MenuOptions
										.getMenuAction("RS_RECIEVED_APPLICATION_REPORT"));
					} else {
						subMenus.add(MenuOptions
								.getMenu("RS_APPLICATION_REPORT"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_APPLICATION_REPORT"));
						subMenus.add(MenuOptions
								.getMenu("RS_CGPAN_HISTORY_REPORT"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_CGPAN_HISTORY_REPORT"));
					}
					if (userPrivileges.contains("SPECIAL_REPORTS")) {
						subMenus.add(MenuOptions
								.getMenu("RS_SANCTIONED_APPLICATION_REPORT"));
						subMenuValues
								.add(MenuOptions
										.getMenuAction("RS_SANCTIONED_APPLICATION_REPORT"));
						subMenus.add(MenuOptions
								.getMenu("RS_APPROVED_APPLICATION_REPORT"));
						subMenuValues
								.add(MenuOptions
										.getMenuAction("RS_APPROVED_APPLICATION_REPORT"));
					}
					String loggedUserId = user.getUserId();
					if (loggedUserId.equalsIgnoreCase("ADMIN")) {
						subMenus.add(MenuOptions
								.getMenu("RS_APPROVED_APPLICATION_REPORT"));
						subMenuValues
								.add(MenuOptions
										.getMenuAction("RS_APPROVED_APPLICATION_REPORT"));
					}
				}
				if (mainMenu.equals(MenuOptions.getMenu("RS_DAN_REPORTS-ALL")))
					if (userPrivileges.contains("REPORTS")) {
						subMenus.add(MenuOptions.getMenu("RS_DAN_REPORT"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_DAN_REPORT"));
						subMenus.add(MenuOptions
								.getMenu("RS_DAN_REPORT_FOR_ASF"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_DAN_REPORT_FOR_ASF"));
						subMenus.add(MenuOptions
								.getMenu("RS_DAN_REPORT_FOR_GF"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_DAN_REPORT_FOR_GF"));
						subMenus.add(MenuOptions
								.getMenu("RS_DAN_PROFORMA_REPORT_FOR_GF"));
						subMenuValues
								.add(MenuOptions
										.getMenuAction("RS_DAN_PROFORMA_REPORT_FOR_GF"));
						subMenus.add(MenuOptions
								.getMenu("RS_DAN_PROFORMA_REPORT_FOR_GFF"));
						subMenuValues
								.add(MenuOptions
										.getMenuAction("RS_DAN_PROFORMA_REPORT_FOR_GFF"));
						subMenus.add(MenuOptions
								.getMenu("RS_DAN_PROFORMA_REPORT_FOR_ASF"));
						subMenuValues
								.add(MenuOptions
										.getMenuAction("RS_DAN_PROFORMA_REPORT_FOR_ASF"));
						subMenus.add(MenuOptions
								.getMenu("RS_DAN_PROFORMA_REPORT_FOR_ASFF"));
						subMenuValues
								.add(MenuOptions
										.getMenuAction("RS_DAN_PROFORMA_REPORT_FOR_ASFF"));

					} else {
						subMenus.add(MenuOptions
								.getMenu("RS_DAN_REPORT_FOR_ASF"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_DAN_REPORT_FOR_ASF"));
						subMenus.add(MenuOptions
								.getMenu("RS_DAN_REPORT_FOR_GF"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_DAN_REPORT_FOR_GF"));
						subMenus.add(MenuOptions.getMenu("RS_DAN_REPORT"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_DAN_REPORT"));
						subMenus.add(MenuOptions
								.getMenu("RS_DAN_PROFORMA_REPORT_FOR_GF"));
						subMenuValues
								.add(MenuOptions
										.getMenuAction("RS_DAN_PROFORMA_REPORT_FOR_GF"));
						subMenus.add(MenuOptions
								.getMenu("RS_DAN_PROFORMA_REPORT_FOR_GFF"));
						subMenuValues
								.add(MenuOptions
										.getMenuAction("RS_DAN_PROFORMA_REPORT_FOR_GFF"));
						subMenus.add(MenuOptions
								.getMenu("RS_DAN_PROFORMA_REPORT_FOR_ASF"));
						subMenuValues
								.add(MenuOptions
										.getMenuAction("RS_DAN_PROFORMA_REPORT_FOR_ASF"));
						subMenus.add(MenuOptions
								.getMenu("RS_DAN_PROFORMA_REPORT_FOR_ASFF"));
						subMenuValues
								.add(MenuOptions
										.getMenuAction("RS_DAN_PROFORMA_REPORT_FOR_ASFF"));
					}
				if (mainMenu.equals(MenuOptions.getMenu("RS_MIS"))) {
					if (userPrivileges.contains("REPORTS")) {
						subMenus.add(MenuOptions.getMenu("RS_NER_BANK_WISE"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_NER_BANK_WISE"));
						subMenus.add(MenuOptions.getMenu("RS_NER_STATE_WISE"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_NER_STATE_WISE"));
						subMenus.add(MenuOptions.getMenu("RS_MINORITY_REPORT"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_MINORITY_REPORT"));
						subMenus.add(MenuOptions
								.getMenu("RS_MINORITY_REPORT_STATE_WISE"));
						subMenuValues
								.add(MenuOptions
										.getMenuAction("RS_MINORITY_REPORT_STATE_WISE"));
						subMenus.add(MenuOptions.getMenu("RS_STATE_WISE"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_STATE_WISE"));
						subMenus.add(MenuOptions
								.getMenu("RS_GUARANTEE_APPROVED"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_GUARANTEE_APPROVED"));
						subMenus.add(MenuOptions
								.getMenu("RS_CATEGORY_WISE_GUARANTEE_ISSUED"));
						subMenuValues
								.add(MenuOptions
										.getMenuAction("RS_CATEGORY_WISE_GUARANTEE_ISSUED"));
						subMenus.add(MenuOptions
								.getMenu("RS_TURNOVER_AND_EXPORTS"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_TURNOVER_AND_EXPORTS"));
						subMenus.add(MenuOptions.getMenu("RS_GF_OUTSTANDING"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_GF_OUTSTANDING"));
					} else {
						subMenus.add(MenuOptions
								.getMenu("RS_APPLICATION_DETAILS_MLI_SLAB"));
						subMenuValues
								.add(MenuOptions
										.getMenuAction("RS_APPLICATION_DETAILS_MLI_SLAB"));
						subMenus.add(MenuOptions
								.getMenu("RS_GUARANTEE_APPROVED"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_GUARANTEE_APPROVED"));
						subMenus.add(MenuOptions.getMenu("RS_STATE_WISE"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_STATE_WISE"));
						subMenus.add(MenuOptions.getMenu("RS_MINORITY_REPORT"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_MINORITY_REPORT"));
						subMenus.add(MenuOptions
								.getMenu("RS_MINORITY_REPORT_STATE_WISE"));
						subMenuValues
								.add(MenuOptions
										.getMenuAction("RS_MINORITY_REPORT_STATE_WISE"));
						subMenus.add(MenuOptions
								.getMenu("RS_CATEGORY_WISE_GUARANTEE_ISSUED"));
						subMenuValues
								.add(MenuOptions
										.getMenuAction("RS_CATEGORY_WISE_GUARANTEE_ISSUED"));
						subMenus.add(MenuOptions.getMenu("RS_NER_STATE_WISE"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_NER_STATE_WISE"));
						subMenus.add(MenuOptions.getMenu("RS_NER_BANK_WISE"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_NER_BANK_WISE"));
						subMenus.add(MenuOptions
								.getMenu("RS_TURNOVER_AND_EXPORTS"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_TURNOVER_AND_EXPORTS"));
					}
					if (userPrivileges.contains("SPECIAL_REPORTS")) {
						subMenus.add(MenuOptions.getMenu("RS_DAY_REPORT"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_DAY_REPORT"));
					}
					String loggedUserId = user.getUserId();
					if (loggedUserId.equalsIgnoreCase("ADMIN")) {
						subMenus.add(MenuOptions.getMenu("RS_DAY_REPORT"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_DAY_REPORT"));
					}
				}
				if (mainMenu.equals(MenuOptions.getMenu("RS_GM_REPORTS"))) {
					subMenus.add(MenuOptions.getMenu("RS_DISBURSEMENT_REPORT"));
					subMenuValues.add(MenuOptions
							.getMenuAction("RS_DISBURSEMENT_REPORT"));
					if (userPrivileges.contains("REPORTS")) {
						subMenus.add(MenuOptions
								.getMenu("RS_PERIODIC_INFO_REPORT"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_PERIODIC_INFO_REPORT"));
					}
				}
				if (mainMenu.equals(MenuOptions.getMenu("RS_RP_REPORTS")))
					if (userPrivileges.contains("REPORTS")) {
						subMenus.add(MenuOptions
								.getMenu("RS_GF_PAYMENT_REPORT_DAILY"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_GF_PAYMENT_REPORT_DAILY"));
						subMenus.add(MenuOptions.getMenu("RS_PAYMENT_REPORT"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_PAYMENT_REPORT"));
						subMenus.add(MenuOptions
								.getMenu("RS_GF_ALLOCATED_REPORT"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_GF_ALLOCATED_REPORT"));
						subMenus.add(MenuOptions
								.getMenu("RS_ASF_PAYMENT_REPORT"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_ASF_PAYMENT_REPORT"));
						subMenus.add(MenuOptions
								.getMenu("RS_ASF_ALLOCATED_REPORT"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_ASF_ALLOCATED_REPORT"));
						subMenus.add(MenuOptions
								.getMenu("RS_RP_CANCELLED_ALLOC_REPORT"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_RP_CANCELLED_ALLOC_REPORT"));
						subMenus.add(MenuOptions
								.getMenu("RS_RP_NOT_APPROPRITED_DETAILS"));
						subMenuValues
								.add(MenuOptions
										.getMenuAction("RS_RP_NOT_APPROPRITED_DETAILS"));
						subMenus.add(MenuOptions
								.getMenu("RS_DD_DEPOSITED_CASES_REPORT"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_DD_DEPOSITED_CASES_REPORT"));
					} else {
						subMenus.add(MenuOptions.getMenu("RS_PAYMENT_REPORT"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_PAYMENT_REPORT"));
						subMenus.add(MenuOptions
								.getMenu("RS_GF_ALLOCATED_REPORT"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_GF_ALLOCATED_REPORT"));
						subMenus.add(MenuOptions
								.getMenu("RS_ASF_PAYMENT_REPORT"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_ASF_PAYMENT_REPORT"));
						subMenus.add(MenuOptions
								.getMenu("RS_ASF_ALLOCATED_REPORT"));
						subMenuValues.add(MenuOptions
								.getMenuAction("RS_ASF_ALLOCATED_REPORT"));
					}
				if (mainMenu.equals(MenuOptions
						.getMenu("RS_SCHEM_PROPAGATION_DETAILS"))
						&& userPrivileges.contains("REPORTS")) {
					subMenus.add(MenuOptions.getMenu("RS_MLI_WORKSHOP_REPORT"));
					subMenuValues.add(MenuOptions
							.getMenuAction("RS_MLI_WORKSHOP_REPORT"));
					subMenus.add(MenuOptions
							.getMenu("RS_STATE_WORKSHOP_REPORT"));
					subMenuValues.add(MenuOptions
							.getMenuAction("RS_STATE_WORKSHOP_REPORT"));
					subMenus.add(MenuOptions
							.getMenu("RS_AGENCY_WORKSHOP_REPORT"));
					subMenuValues.add(MenuOptions
							.getMenuAction("RS_AGENCY_WORKSHOP_REPORT"));
					subMenus.add(MenuOptions
							.getMenu("RS_SCHEME_WORKSHOP_REPORT"));
					subMenuValues.add(MenuOptions
							.getMenuAction("RS_SCHEME_WORKSHOP_REPORT"));
					subMenus.add(MenuOptions.getMenu("RS_WORKSHOP_REPORT"));
					subMenuValues.add(MenuOptions
							.getMenuAction("RS_WORKSHOP_REPORT"));
				}

				/*
				 * if(mainMenu.equals(MenuOptions.getMenu("RS_MONTH_WISE_REPORT_NEW"
				 * )) && userPrivileges.contains("REPORTS")) {
				 * subMenus.add(MenuOptions.getMenu("RS_MONTH_WISE_REPORT"));
				 * subMenuValues
				 * .add(MenuOptions.getMenuAction("RS_MONTH_WISE_REPORT"));
				 * 
				 * }
				 */
				if (mainMenu.equals(MenuOptions.getMenu("RS_CLAIMS_DETAILS"))) {
					subMenus.add(MenuOptions
							.getMenu("RS_CLAIMS_DETAILS_MLI_WISE"));
					subMenuValues.add(MenuOptions
							.getMenuAction("RS_CLAIMS_DETAILS_MLI_WISE"));
					subMenus.add(MenuOptions
							.getMenu("RS_CLAIMS_DETAILS_STATE_WISE"));
					subMenuValues.add(MenuOptions
							.getMenuAction("RS_CLAIMS_DETAILS_STATE_WISE"));
					subMenus.add(MenuOptions
							.getMenu("RS_CLAIMS_DETAILS_SLAB_WISE"));
					subMenuValues.add(MenuOptions
							.getMenuAction("RS_CLAIMS_DETAILS_SLAB_WISE"));
					subMenus.add(MenuOptions
							.getMenu("RS_CLAIMS_DETAILS_SECTOR_WISE"));
					subMenuValues.add(MenuOptions
							.getMenuAction("RS_CLAIMS_DETAILS_SECTOR_WISE"));
					subMenus.add(MenuOptions
							.getMenu("RS_CLAIMS_DETAILS_STATUS_WISE"));
					subMenuValues.add(MenuOptions
							.getMenuAction("RS_CLAIMS_DETAILS_STATUS_WISE"));
					subMenus.add(MenuOptions.getMenu("CLM_SETTLED_REPORT"));
					subMenuValues.add(MenuOptions
							.getMenuAction("CLM_SETTLED_REPORT"));
					subMenus.add(MenuOptions
							.getMenu("RS_TCQUERY_CLAIM_DETAILS"));
					subMenuValues.add(MenuOptions
							.getMenuAction("RS_TCQUERY_CLAIM_DETAILS"));
					// Diksha
					subMenus.add(MenuOptions.getMenu("RS_CLAIM_LODGED_DETAILS"));
					subMenuValues.add(MenuOptions
							.getMenuAction("RS_CLAIM_LODGED_DETAILS"));
					String loggedUserId = user.getUserId();
					if (!userPrivileges.contains("REPORTS"))
						;
				}
			} else if (menuIcon.equals("AD")) {
				if (mainMenu.equals(MenuOptions.getMenu("AD_COLLECTING_BANK"))) {
					if (userPrivileges.contains("REGISTER_COLLECTING_BANK")) {
						subMenus.add(MenuOptions
								.getMenu("AD_COLLECTING_BANK_REGISTER"));
						subMenuValues.add(MenuOptions
								.getMenuAction("AD_COLLECTING_BANK_REGISTER"));
					}
					if (userPrivileges
							.contains("MODIFY_COLLECTING_BANK_DETAILS")) {
						subMenus.add(MenuOptions
								.getMenu("AD_COLLECTING_BANK_MODIFY"));
						subMenuValues.add(MenuOptions
								.getMenuAction("AD_COLLECTING_BANK_MODIFY"));
					}
				} else if (mainMenu.equals(MenuOptions.getMenu("AD_MLI"))) {
					if (userPrivileges.contains("REGISTER_MLI")) {
						subMenus.add(MenuOptions.getMenu("AD_MLI_REGISTER"));
						subMenuValues.add(MenuOptions
								.getMenuAction("AD_MLI_REGISTER"));
					}
					if (userPrivileges.contains("REGISTER_MLI")) {
						subMenus.add(MenuOptions.getMenu("AD_MODIFY_MLI"));
						subMenuValues.add(MenuOptions
								.getMenuAction("AD_MODIFY_MLI"));
					}
					if (userPrivileges.contains("ASSIGN_COLLECTING_BANK")) {
						subMenus.add(MenuOptions.getMenu("AD_MLI_ASSIGN_CB"));
						subMenuValues.add(MenuOptions
								.getMenuAction("AD_MLI_ASSIGN_CB"));
					}
					if (userPrivileges.contains("MODIFY_COLLECTING_BANK")) {
						subMenus.add(MenuOptions
								.getMenu("AD_MLI_MODIFY_COLLECTING_BANK"));
						subMenuValues
								.add(MenuOptions
										.getMenuAction("AD_MLI_MODIFY_COLLECTING_BANK"));
					}
					if (userPrivileges.contains("MODIFY_COLLECTING_BANK")) {
						subMenus.add(MenuOptions
								.getMenu("AD_MLI_MODIFY_EXPOSURE_LIMITS"));
						subMenuValues
								.add(MenuOptions
										.getMenuAction("AD_MLI_MODIFY_EXPOSURE_LIMITS"));
					}
					if (userPrivileges
							.contains("DEFINE_ORGANIZATION_STRUCTURE")) {
						subMenus.add(MenuOptions
								.getMenu("AD_MLI_DEFINE_ORG_STRUCTURE"));
						subMenuValues.add(MenuOptions
								.getMenuAction("AD_MLI_DEFINE_ORG_STRUCTURE"));
					}
					if (userPrivileges.contains("DEACTIVATE_MEMBER")) {
						subMenus.add(MenuOptions
								.getMenu("AD_MLI_DEACTIVATE_MEMBER"));
						subMenuValues.add(MenuOptions
								.getMenuAction("AD_MLI_DEACTIVATE_MEMBER"));
					}
					if (userPrivileges.contains("ACTIVATE_MEMBER")) {
						subMenus.add(MenuOptions
								.getMenu("AD_MLI_REACTIVATE_MEMBER"));
						subMenuValues.add(MenuOptions
								.getMenuAction("AD_MLI_REACTIVATE_MEMBER"));
					}
				} else if (mainMenu
						.equals(MenuOptions.getMenu("AD_ROLES_MGMT"))) {
					if (userPrivileges.contains("ADD_MODIFY_ROLES")) {
						subMenus.add(MenuOptions
								.getMenu("AD_ROLES_MGMT_ADD_ROLE"));
						subMenuValues.add(MenuOptions
								.getMenuAction("AD_ROLES_MGMT_ADD_ROLE"));
						subMenus.add(MenuOptions
								.getMenu("AD_ROLES_MGMT_MODIFY_ROLE"));
						subMenuValues.add(MenuOptions
								.getMenuAction("AD_ROLES_MGMT_MODIFY_ROLE"));
					}
				} else if (mainMenu.equals(MenuOptions.getMenu("AD_USER_MGMT"))) {
					if (userPrivileges.contains("CREATE_CGTSI_USER")) {
						subMenus.add(MenuOptions
								.getMenu("AD_USER_MGMT_CREATE_CGTSI_USER"));
						subMenuValues
								.add(MenuOptions
										.getMenuAction("AD_USER_MGMT_CREATE_CGTSI_USER"));
					}
					if (userPrivileges.contains("CREATE_MLI_USER")) {
						subMenus.add(MenuOptions
								.getMenu("AD_USER_MGMT_CREATE_MLI_USER"));
						subMenuValues.add(MenuOptions
								.getMenuAction("AD_USER_MGMT_CREATE_MLI_USER"));
					}
					if (userPrivileges.contains("MODIFY_USER_DETAILS")) {
						subMenus.add(MenuOptions
								.getMenu("AD_USER_MGMT_MODIFY_USER_DETAILS"));
						subMenuValues
								.add(MenuOptions
										.getMenuAction("AD_USER_MGMT_MODIFY_USER_DETAILS"));
					}
					if (userPrivileges.contains("DEACTIVATE_USER")) {
						subMenus.add(MenuOptions
								.getMenu("AD_USER_MGMT_DEACTIVATE_USER"));
						subMenuValues.add(MenuOptions
								.getMenuAction("AD_USER_MGMT_DEACTIVATE_USER"));
					}
					if (userPrivileges.contains("REACTIVATE_USER")) {
						subMenus.add(MenuOptions
								.getMenu("AD_USER_MGMT_REACTIVATE_USER"));
						subMenuValues.add(MenuOptions
								.getMenuAction("AD_USER_MGMT_REACTIVATE_USER"));
					}
					if (userPrivileges
							.contains("ASSIGN_MODIFY_ROLES_AND_PRIVILEGES")) {
						subMenus.add(MenuOptions
								.getMenu("AD_USER_MGMT_ASSIGN_ROLES"));
						subMenuValues.add(MenuOptions
								.getMenuAction("AD_USER_MGMT_ASSIGN_ROLES"));
						subMenus.add(MenuOptions
								.getMenu("AD_USER_MGMT_MODIFY_ROLES"));
						subMenuValues.add(MenuOptions
								.getMenuAction("AD_USER_MGMT_MODIFY_ROLES"));
					}
				} else if (mainMenu.equals(MenuOptions
						.getMenu("AD_PASSWORD_MGMT"))) {
					subMenus.add(MenuOptions
							.getMenu("AD_PASSWORD_MGMT_CHANGE_PASSWORD"));
					subMenuValues.add(MenuOptions
							.getMenuAction("AD_PASSWORD_MGMT_CHANGE_PASSWORD"));
					if (userPrivileges.contains("RESET_PASSWORD")) {
						subMenus.add(MenuOptions
								.getMenu("AD_PASSWORD_MGMT_RESET_PASSWORD"));
						subMenuValues
								.add(MenuOptions
										.getMenuAction("AD_PASSWORD_MGMT_RESET_PASSWORD"));
					}
					subMenus.add(MenuOptions
							.getMenu("AD_PASSWORD_MGMT_CHANGE_HINT"));
					subMenuValues.add(MenuOptions
							.getMenuAction("AD_PASSWORD_MGMT_CHANGE_HINT"));
				} else if (mainMenu.equals(MenuOptions.getMenu("AD_AUDIT"))) {
					if (userPrivileges.contains("ENTER_AUDIT_DETAILS")) {
						subMenus.add(MenuOptions.getMenu("AD_AUDIT_ENTER"));
						subMenuValues.add(MenuOptions
								.getMenuAction("AD_AUDIT_ENTER"));
					}
					if (userPrivileges.contains("REVIEW_AUDIT_DETAILS")) {
						subMenus.add(MenuOptions.getMenu("AD_AUDIT_REVIEW"));
						subMenuValues.add(MenuOptions
								.getMenuAction("AD_AUDIT_REVIEW"));
					}
				} else if (mainMenu.equals(MenuOptions
						.getMenu("AD_UPDATE_MASTER"))
						&& userPrivileges.contains("UPDATE_MASTER_TABLE")) {
					subMenus.add(MenuOptions.getMenu("AD_UPDATE_MASTER_STATE"));
					subMenuValues.add(MenuOptions
							.getMenuAction("AD_UPDATE_MASTER_STATE"));
					subMenus.add(MenuOptions
							.getMenu("AD_UPDATE_MASTER_DISTRICT"));
					subMenuValues.add(MenuOptions
							.getMenuAction("AD_UPDATE_MASTER_DISTRICT"));
					subMenus.add(MenuOptions
							.getMenu("AD_UPDATE_MASTER_STATE_MOD"));
					subMenuValues.add(MenuOptions
							.getMenuAction("AD_UPDATE_MASTER_STATE_MOD"));
					subMenus.add(MenuOptions
							.getMenu("AD_UPDATE_MASTER_DISTRICT_MOD"));
					subMenuValues.add(MenuOptions
							.getMenuAction("AD_UPDATE_MASTER_DISTRICT_MOD"));
					subMenus.add(MenuOptions.getMenu("AD_UPDATE_MASTER_ALERTS"));
					subMenuValues.add(MenuOptions
							.getMenuAction("AD_UPDATE_MASTER_ALERTS"));
					subMenus.add(MenuOptions
							.getMenu("AD_UPDATE_MASTER_DESIGNATION"));
					subMenuValues.add(MenuOptions
							.getMenuAction("AD_UPDATE_MASTER_DESIGNATION"));
					subMenus.add(MenuOptions
							.getMenu("AD_UPDATE_MASTER_PARAMETER"));
					subMenuValues.add(MenuOptions
							.getMenuAction("AD_UPDATE_MASTER_PARAMETER"));
					subMenus.add(MenuOptions
							.getMenu("AD_UPDATE_MASTER_PLR_INSERT"));
					subMenuValues.add(MenuOptions
							.getMenuAction("AD_UPDATE_MASTER_PLR_INSERT"));
					subMenus.add(MenuOptions
							.getMenu("AD_UPDATE_MASTER_PLR_MODIFY"));
					subMenuValues.add(MenuOptions
							.getMenuAction("AD_UPDATE_MASTER_PLR_MODIFY"));
				}
			} else if (menuIcon.equals("AP")) {
				if (mainMenu.equals(MenuOptions.getMenu("AP_GUARANTEE_FOR"))) {
					subMenus.add(MenuOptions.getMenu("AP_GF_TERM_LOAN"));
					subMenuValues.add(MenuOptions
							.getMenuAction("AP_GF_TERM_LOAN"));
					subMenus.add(MenuOptions.getMenu("AP_GF_COMPOSITE_LOAN"));
					subMenuValues.add(MenuOptions
							.getMenuAction("AP_GF_COMPOSITE_LOAN"));
					subMenus.add(MenuOptions.getMenu("AP_GF_BOTH"));
					subMenuValues.add(MenuOptions.getMenuAction("AP_GF_BOTH"));
					subMenus.add(MenuOptions.getMenu("AP_GF_ADDL_TERM_LOAN"));
					subMenuValues.add(MenuOptions
							.getMenuAction("AP_GF_ADDL_TERM_LOAN"));
					subMenus.add(MenuOptions.getMenu("AP_GF_WC_ENHANCEMENT"));
					subMenuValues.add(MenuOptions
							.getMenuAction("AP_GF_WC_ENHANCEMENT"));
					subMenus.add(MenuOptions.getMenu("AP_GF_WC_RENEWAL"));
					subMenuValues.add(MenuOptions
							.getMenuAction("AP_GF_WC_RENEWAL"));
					subMenus.add(MenuOptions.getMenu("AP_GF_RSF2_WC_LOAN"));
					subMenuValues.add(MenuOptions
							.getMenuAction("AP_GF_RSF2_WC_LOAN"));
					subMenus.add(MenuOptions.getMenu("AP_GF_RSF2_LOAN"));
					subMenuValues.add(MenuOptions
							.getMenuAction("AP_GF_RSF2_LOAN"));
					subMenus.add(MenuOptions.getMenu("AP_RSF2_BOTH"));
					subMenuValues
							.add(MenuOptions.getMenuAction("AP_RSF2_BOTH"));
				} else if (mainMenu.equals(MenuOptions.getMenu("AP_APPROVAL"))) {
					subMenus.add(MenuOptions.getMenu("AP_APPROVAL_APPROVE"));
					subMenuValues.add(MenuOptions
							.getMenuAction("AP_APPROVAL_APPROVE"));
					subMenus.add(MenuOptions.getMenu("AP_APPROVAL_APPROVE_NEW"));
					subMenuValues.add(MenuOptions
							.getMenuAction("AP_APPROVAL_APPROVE_NEW"));
				} else if (mainMenu.equals(MenuOptions
						.getMenu("AP_APPROVAL_SPECIAL_MESSAGE"))) {
					subMenus.add(MenuOptions
							.getMenu("AP_APPROVAL_SPECIAL_MESSAGE_INSERT"));
					subMenuValues
							.add(MenuOptions
									.getMenuAction("AP_APPROVAL_SPECIAL_MESSAGE_INSERT"));
					subMenus.add(MenuOptions
							.getMenu("AP_APPROVAL_SPECIAL_MESSAGE_UPDATE"));
					subMenuValues
							.add(MenuOptions
									.getMenuAction("AP_APPROVAL_SPECIAL_MESSAGE_UPDATE"));
				} else if (mainMenu
						.equals(MenuOptions.getMenu("AP_CONVERSION"))) {
					subMenus.add(MenuOptions.getMenu("AP_CONVERSION_TC"));
					subMenuValues.add(MenuOptions
							.getMenuAction("AP_CONVERSION_TC"));
					subMenus.add(MenuOptions.getMenu("AP_CONVERSION_WC"));
					subMenuValues.add(MenuOptions
							.getMenuAction("AP_CONVERSION_WC"));
				}
			} else if (menuIcon.equals("RI")) {
				if (mainMenu.equals(MenuOptions.getMenu("RI_LIMIT_SETUP"))) {
					subMenus.add(MenuOptions.getMenu("RI_LIMIT_SETUP_GLOBAL"));
					subMenuValues.add(MenuOptions
							.getMenuAction("RI_LIMIT_SETUP_GLOBAL"));
					subMenus.add(MenuOptions.getMenu("RI_LIMIT_SETUP_USER"));
					subMenuValues.add(MenuOptions
							.getMenuAction("RI_LIMIT_SETUP_USER"));
					subMenus.add(MenuOptions
							.getMenu("RI_LIMIT_SETUP_PARTICIPATING_BANK"));
					subMenuValues
							.add(MenuOptions
									.getMenuAction("RI_LIMIT_SETUP_PARTICIPATING_BANK"));
				}
			} else if (menuIcon.equals("CP")) {
				if (mainMenu.equals(MenuOptions.getMenu("CP_CLAIM_FOR"))) {
					if (userPrivileges.contains("CLAIM_FIRST_INSTALLMENT")) {
						subMenus.add(MenuOptions
								.getMenu("CP_CLAIM_FOR_FIRST_INSTALLMENT"));
						subMenuValues
								.add(MenuOptions
										.getMenuAction("CP_CLAIM_FOR_FIRST_INSTALLMENT"));
					}
					if (userPrivileges.contains("CLAIM_SECOND_INSTALLMENT")) {
						subMenus.add(MenuOptions
								.getMenu("CP_CLAIM_FOR_SECOND_INSTALLMENT"));
						subMenuValues
								.add(MenuOptions
										.getMenuAction("CP_CLAIM_FOR_SECOND_INSTALLMENT"));
					}
					subMenus.add(MenuOptions.getMenu("CP_UPDATE_RT_CLAIM"));
					subMenuValues.add(MenuOptions
							.getMenuAction("CP_UPDATE_RT_CLAIM"));
					subMenus.add(MenuOptions.getMenu("CP_UPDATE_TC_CLAIM"));
					subMenuValues.add(MenuOptions
							.getMenuAction("CP_UPDATE_TC_CLAIM"));
					// String bankID = user.getBankId();
					if ("0029".equals(bankID) || "0056".equals(bankID)
							|| "0035".equals(bankID) || "0082".equals(bankID)
							|| "0024".equals(bankID) || "0001".equals(bankID)
							|| "0000".equals(bankID) || "0103".equals(bankID)
							|| "0093".equals(bankID)) {
						subMenus.add(MenuOptions.getMenu("CP_UPLOAD_FILES"));
						subMenuValues.add(MenuOptions
								.getMenuAction("CP_UPLOAD_FILES"));
					}
					// Diksha 26/05/2017
					subMenus.add(MenuOptions.getMenu("CP_RESUBMISSION_DU"));
					subMenuValues.add(MenuOptions
							.getMenuAction("CP_RESUBMISSION_DU"));
				} else if (mainMenu.equals(MenuOptions
						.getMenu("CP_APPROVAL_NEW"))) {
					if (userPrivileges.contains("CLAIM_APPROVAL")) {
						subMenus.add(MenuOptions
								.getMenu("CP_APPROVAL_MODIFIED"));
						subMenuValues.add(MenuOptions
								.getMenuAction("CP_APPROVAL_MODIFIED"));
					}
				} else if (mainMenu.equals(MenuOptions.getMenu("CP_OTS"))) {
					if (userPrivileges.contains("OTS_REQUEST")) {
						subMenus.add(MenuOptions.getMenu("CP_OTS_REQUEST"));
						subMenuValues.add(MenuOptions
								.getMenuAction("CP_OTS_REQUEST"));
					}
					if (userPrivileges.contains("OTS_APPROVAL")) {
						subMenus.add(MenuOptions.getMenu("CP_OTS_CONSENT"));
						subMenuValues.add(MenuOptions
								.getMenuAction("CP_OTS_CONSENT"));
					}
				} else if (mainMenu.equals(MenuOptions
						.getMenu("CP_EXPORT_CLAIM_FILE"))) {
					subMenus.add(MenuOptions
							.getMenu("CP_EXPORT_CLAIM_FILE_FIRST_INSTALLMNT"));
					subMenuValues
							.add(MenuOptions
									.getMenuAction("CP_EXPORT_CLAIM_FILE_FIRST_INSTALLMNT"));
					subMenus.add(MenuOptions
							.getMenu("CP_EXPORT_CLAIM_FILE_SECOND_INSTALLMNT"));
					subMenuValues
							.add(MenuOptions
									.getMenuAction("CP_EXPORT_CLAIM_FILE_SECOND_INSTALLMNT"));
				}
			} else if (menuIcon.equals("GM")) {

				if (mainMenu.equals(MenuOptions
						.getMenu("GM_OUTSTANDING_REQUEST_MENU"))) {
					// subMenus.add(MenuOptions.getMenu("GM_OUTSTANDING_REQUEST_ENTRY"));
					// subMenuValues.add(MenuOptions.getMenuAction("GM_OUTSTANDING_REQUEST_ENTRY"));
					subMenus.add(MenuOptions.getMenu("GM_OUTSTANDING_AMOUNT"));
					subMenuValues.add(MenuOptions.getMenuAction("GM_OUTSTANDING_AMOUNT"));
					//subMenus.add(MenuOptions.getMenu("GM_OUTSTANDING_EDIT"));
					//subMenuValues.add(MenuOptions.getMenuAction("GM_OUTSTANDING_EDIT"));
					subMenus.add(MenuOptions.getMenu("GM_OUTSTANDING_REQUEST_MODIFY"));
					subMenuValues.add(MenuOptions.getMenuAction("GM_OUTSTANDING_REQUEST_MODIFY"));
					subMenus.add(MenuOptions.getMenu("GM_OUTSTANDING_REQUEST_UPLOAD"));
					subMenuValues.add(MenuOptions.getMenuAction("GM_OUTSTANDING_REQUEST_UPLOAD"));

				}
			/*	if (mainMenu.equals(MenuOptions
						.getMenu("GM_OUTSTANDING_REQUEST_MENU_NEW"))) {					
					subMenus.add(MenuOptions.getMenu("GM_OUTSTANDING_AMOUNT_NEW"));
					subMenuValues.add(MenuOptions.getMenuAction("GM_OUTSTANDING_AMOUNT_NEW"));
					subMenus.add(MenuOptions.getMenu("GM_OUTSTANDING_REQUEST_UPLOAD_NEW"));
					subMenuValues.add(MenuOptions.getMenuAction("GM_OUTSTANDING_REQUEST_UPLOAD_NEW"));

				}*/
				if (mainMenu.equals(MenuOptions.getMenu("GM_PERIODIC_INFO"))) {
					subMenus.add(MenuOptions
							.getMenu("GM_PI_OUTSTANDING_DETAILS"));
					subMenuValues.add(MenuOptions
							.getMenuAction("GM_PI_OUTSTANDING_DETAILS"));
					subMenus.add(MenuOptions
							.getMenu("GM_PI_DISBURSEMENT_DETAILS"));
					subMenuValues.add(MenuOptions
							.getMenuAction("GM_PI_DISBURSEMENT_DETAILS"));
					subMenus.add(MenuOptions.getMenu("GM_PI_REPAYMENT_DETAILS"));
					subMenuValues.add(MenuOptions
							.getMenuAction("GM_PI_REPAYMENT_DETAILS"));
					subMenus.add(MenuOptions.getMenu("GM_PI_NPA_DETAILS"));
					subMenuValues.add(MenuOptions
							.getMenuAction("GM_PI_NPA_DETAILS"));
                    subMenus.add(MenuOptions.getMenu("GM_NPA_BULK_UPLOAD"));
                    subMenuValues.add(MenuOptions.getMenuAction("GM_NPA_BULK_UPLOAD"));
					subMenus.add(MenuOptions.getMenu("GM_PI_NPA_DETAILS1"));
					subMenuValues.add(MenuOptions
							.getMenuAction("GM_PI_NPA_DETAILS1"));
					subMenus.add(MenuOptions.getMenu("GM_PI_NPA_DETAILS2"));
					subMenuValues.add(MenuOptions
							.getMenuAction("GM_PI_NPA_DETAILS2"));
					subMenus.add(MenuOptions.getMenu("GM_PI_NPA_DETAILS3"));
					subMenuValues.add(MenuOptions
							.getMenuAction("GM_PI_NPA_DETAILS3"));
					subMenus.add(MenuOptions.getMenu("GM_PI_RECOVERY_DETAILS"));
					subMenuValues.add(MenuOptions
							.getMenuAction("GM_PI_RECOVERY_DETAILS"));
					if (userPrivileges.contains("UPDATE_RECOVERY")) {
						subMenus.add(MenuOptions
								.getMenu("GM_PI_UPDATE_RECOVERY_DETAILS"));
						subMenuValues
								.add(MenuOptions
										.getMenuAction("GM_PI_UPDATE_RECOVERY_DETAILS"));
					}
				}
			} else if (menuIcon.equals("RP")) {
				if (mainMenu.equals(MenuOptions
						.getMenu("RP_VIEW_PENDING_PAYMENTS"))) {
					subMenus.add(MenuOptions.getMenu("RP_VIEW_PP_MLI_WISE"));
					subMenuValues.add(MenuOptions
							.getMenuAction("RP_VIEW_PP_MLI_WISE"));
					subMenus.add(MenuOptions
							.getMenu("RP_VIEW_PP_DAN_DATE_WISE"));
					subMenuValues.add(MenuOptions
							.getMenuAction("RP_VIEW_PP_DAN_DATE_WISE"));
				}
				if (mainMenu
						.equals(MenuOptions.getMenu("RP_ALLOCATE_PAYMENTS"))) {
					// Diksha
					// subMenus.add(MenuOptions.getMenu("RP_GUARANTEE_FEE"));
					// Diksha
					// subMenuValues.add(MenuOptions.getMenuAction("RP_GUARANTEE_FEE"));
					subMenus.add(MenuOptions.getMenu("RP_LIVE_AF"));
					subMenuValues.add(MenuOptions.getMenuAction("RP_LIVE_AF"));
					subMenus.add(MenuOptions.getMenu("RP_EXPIRED_AF"));
					subMenuValues.add(MenuOptions
							.getMenuAction("RP_EXPIRED_AF"));
					subMenus.add(MenuOptions.getMenu("RP_LIVE_ASF"));
					subMenuValues.add(MenuOptions.getMenuAction("RP_LIVE_ASF"));
					subMenus.add(MenuOptions.getMenu("RP_EXPIRED_ASF"));
					subMenuValues.add(MenuOptions
							.getMenuAction("RP_EXPIRED_ASF"));
				}
				// Diksha 26/05/2017

				if (mainMenu.equals(MenuOptions
						.getMenu("RP_ALLOCATE_PAYMENTS_ONLINEMENU"))) {
					subMenus.add(MenuOptions
							.getMenu("RP_ALLOCATE_PAYMENTS_ONLINE"));
					subMenuValues.add(MenuOptions
							.getMenuAction("RP_ALLOCATE_PAYMENTS_ONLINE"));
					subMenus.add(MenuOptions
							.getMenu("RP_ALLOCATE_PAYMENTS_ONLINE_MODIFYORCANCEL"));
					subMenuValues
							.add(MenuOptions
									.getMenuAction("RP_ALLOCATE_PAYMENTS_ONLINE_MODIFYORCANCEL"));
					subMenus.add(MenuOptions
							.getMenu("RP_ALLOCATE_PAYMENTS_ONLINE_FINAL"));
					subMenuValues
							.add(MenuOptions
									.getMenuAction("RP_ALLOCATE_PAYMENTS_ONLINE_FINAL"));
				}
				if (mainMenu.equals(MenuOptions
						.getMenu("ASF_PAYMENT_THROUGH_NEFT_OR_RTGS")))
				{
					subMenus.add(MenuOptions
							.getMenu("ASF_ONLINE_EXPIRED_SF_FINAL"));
					subMenuValues.add(MenuOptions
							.getMenuAction("ASF_ONLINE_EXPIRED_SF_FINAL"));
					subMenus.add(MenuOptions.getMenu("ASF_ONLINE_LIVE_AF"));
					subMenuValues.add(MenuOptions
							.getMenuAction("ASF_ONLINE_LIVE_AF"));
					subMenus.add(MenuOptions.getMenu("ASF_ONLINE_EXPIRED_AF"));
					subMenuValues.add(MenuOptions
							.getMenuAction("ASF_ONLINE_EXPIRED_AF"));
					subMenus.add(MenuOptions.getMenu("ASF_ONLINE_LIVE_SF"));
					subMenuValues.add(MenuOptions
							.getMenuAction("ASF_ONLINE_LIVE_SF"));
					subMenus.add(MenuOptions.getMenu("ASF_ONLINE_EXPIRED_SF"));
					subMenuValues.add(MenuOptions.getMenuAction("ASF_ONLINE_EXPIRED_SF"));
					subMenus.add(MenuOptions.getMenu("TENURE_ONLINE_CASES_ALLOCATION"));
					subMenuValues.add(MenuOptions.getMenuAction("TENURE_ONLINE_CASES_ALLOCATION"));
					subMenus.add(MenuOptions
							.getMenu("ASF_ONLINE_EXPIRED_SF_MODIFYORCANCEL"));
					subMenuValues
							.add(MenuOptions
									.getMenuAction("ASF_ONLINE_EXPIRED_SF_MODIFYORCANCEL"));
				} 
				if (mainMenu.equals(MenuOptions.getMenu("RP_ALLOCATE_TENURE_ONLINEMENU"))) {
					subMenus.add(MenuOptions.getMenu("RP_ALLOCATE_TENURE_ONLINE_AF"));
					subMenuValues.add(MenuOptions.getMenuAction("RP_ALLOCATE_TENURE_ONLINE_AF"));
					subMenus.add(MenuOptions.getMenu("RP_ALLOCATE_TENURE_ONLINE_SF"));
					subMenuValues.add(MenuOptions.getMenuAction("RP_ALLOCATE_TENURE_ONLINE_SF"));
					subMenus.add(MenuOptions.getMenu("RP_ALLOCATE_TENURE_ONLINE_MODIFYORCANCEL"));
					subMenuValues.add(MenuOptions.getMenuAction("RP_ALLOCATE_TENURE_ONLINE_MODIFYORCANCEL"));
					subMenus.add(MenuOptions.getMenu("RP_ALLOCATE_TENURE_ONLINE_FINAL"));
					subMenuValues.add(MenuOptions.getMenuAction("RP_ALLOCATE_TENURE_ONLINE_FINAL"));
				}
			
			
			/*	if (mainMenu.equals(MenuOptions
						.getMenu("ASF_PAYMENT_THROUGH_NEFT_OR_RTGS_NEW")))
				{
					subMenus.add(MenuOptions
							.getMenu("ASF_ONLINE_EXPIRED_SF_FINAL"));
					subMenuValues.add(MenuOptions
							.getMenuAction("ASF_ONLINE_EXPIRED_SF_FINAL"));
					subMenus.add(MenuOptions.getMenu("ASF_ONLINE_LIVE_AF"));
					subMenuValues.add(MenuOptions
							.getMenuAction("ASF_ONLINE_LIVE_AF"));
					subMenus.add(MenuOptions.getMenu("ASF_ONLINE_EXPIRED_AF"));
					subMenuValues.add(MenuOptions
							.getMenuAction("ASF_ONLINE_EXPIRED_AF"));
					subMenus.add(MenuOptions.getMenu("ASF_ONLINE_LIVE_SF"));
					subMenuValues.add(MenuOptions
							.getMenuAction("ASF_ONLINE_LIVE_SF"));
					subMenus.add(MenuOptions.getMenu("ASF_ONLINE_EXPIRED_SF"));
					subMenuValues.add(MenuOptions
							.getMenuAction("ASF_ONLINE_EXPIRED_SF"));
					subMenus.add(MenuOptions
							.getMenu("ASF_ONLINE_EXPIRED_SF_MODIFYORCANCEL"));
					subMenuValues
							.add(MenuOptions
									.getMenuAction("ASF_ONLINE_EXPIRED_SF_MODIFYORCANCEL"));
				}*/
				
				// 26/05/2017 end
				if (mainMenu.equals(MenuOptions
						.getMenu("RP_APPROPRIATE_PAYMENTS"))) {
					subMenus.add(MenuOptions
							.getMenu("RP_APPROPRIATE_PAYMENTS_FOR_GF"));
					subMenuValues.add(MenuOptions
							.getMenuAction("RP_APPROPRIATE_PAYMENTS_FOR_GF"));
					subMenus.add(MenuOptions
							.getMenu("RP_APPROPRIATE_PAYMENTS_FOR_ASF"));
					subMenuValues.add(MenuOptions
							.getMenuAction("RP_APPROPRIATE_PAYMENTS_FOR_ASF"));
					subMenus.add(MenuOptions
							.getMenu("RP_APPROPRIATE_PAYMENTS_FOR_CLAIM"));
					subMenuValues
							.add(MenuOptions
									.getMenuAction("RP_APPROPRIATE_PAYMENTS_FOR_CLAIM"));
					subMenus.add(MenuOptions
							.getMenu("RP_APPROPRIATE_PAYMENTS_FOR_ALL"));
					subMenuValues.add(MenuOptions
							.getMenuAction("RP_APPROPRIATE_PAYMENTS_FOR_ALL"));
				}
				if (!mainMenu
						.equals(MenuOptions.getMenu("RP_GENERATE_VOUCHER")))
					;
			} else if (menuIcon.equals("TC")) {
				if (mainMenu.equals(MenuOptions.getMenu("FILE_UPLOAD"))) {
					subMenus.add(MenuOptions
							.getMenu("THIN_CLIENT_AP_FILE_UPLOAD"));
					subMenuValues.add(MenuOptions
							.getMenuAction("THIN_CLIENT_AP_FILE_UPLOAD"));
					subMenus.add(MenuOptions
							.getMenu("THIN_CLIENT_GM_FILE_UPLOAD"));
					subMenuValues.add(MenuOptions
							.getMenuAction("THIN_CLIENT_GM_FILE_UPLOAD"));
					subMenus.add(MenuOptions
							.getMenu("THIN_CLIENT_CP_FILE_UPLOAD"));
					subMenuValues.add(MenuOptions
							.getMenuAction("THIN_CLIENT_CP_FILE_UPLOAD"));
				}
			} else if (menuIcon.equals("SC")) {
				if (mainMenu.equals(MenuOptions.getMenu("SECURITIZATION"))) {
					subMenus.add(MenuOptions.getMenu("SC_SELECT_QUERY"));
					subMenuValues.add(MenuOptions
							.getMenuAction("SC_SELECT_QUERY"));
					subMenus.add(MenuOptions.getMenu("SC_ADD_INVESTOR"));
					subMenuValues.add(MenuOptions
							.getMenuAction("SC_ADD_INVESTOR"));
				} else if (mainMenu.equals(MenuOptions.getMenu("MCGS"))) {
					subMenus.add(MenuOptions.getMenu("MCGF_ADD_DONOR_DETAILS"));
					subMenuValues.add(MenuOptions
							.getMenuAction("MCGF_ADD_DONOR_DETAILS"));
					subMenus.add(MenuOptions
							.getMenu("MCGF_ADD_PARTICIPATING_BANK"));
					subMenuValues.add(MenuOptions
							.getMenuAction("MCGF_ADD_PARTICIPATING_BANK"));
					subMenus.add(MenuOptions.getMenu("MCGF_ADD_SSI_MEMBERS"));
					subMenuValues.add(MenuOptions
							.getMenuAction("MCGF_ADD_SSI_MEMBERS"));
					subMenus.add(MenuOptions.getMenu("MCGF_DEFAULT"));
					subMenuValues
							.add(MenuOptions.getMenuAction("MCGF_DEFAULT"));
				}
			} else if (menuIcon.equals("IF"))
				if (mainMenu.equals(MenuOptions.getMenu("IF_BUDGET_DETAILS"))) {
					subMenus.add(MenuOptions.getMenu("IF_BUDGET_INFLOW"));
					subMenuValues.add(MenuOptions
							.getMenuAction("IF_BUDGET_INFLOW"));
					subMenus.add(MenuOptions.getMenu("IF_BUDGET_OUTFLOW"));
					subMenuValues.add(MenuOptions
							.getMenuAction("IF_BUDGET_OUTFLOW"));
				} else if (mainMenu.equals(MenuOptions
						.getMenu("IF_INVESTMENT_DETAILS"))) {
					subMenus.add(MenuOptions.getMenu("IF_INVESTMENT_ENTRY"));
					subMenuValues.add(MenuOptions
							.getMenuAction("IF_INVESTMENT_ENTRY"));
					subMenus.add(MenuOptions.getMenu("IF_INVESTMENT_FD"));
					subMenuValues.add(MenuOptions
							.getMenuAction("IF_INVESTMENT_FD"));
					subMenus.add(MenuOptions.getMenu("IF_INVESTMENT_BONDS"));
					subMenuValues.add(MenuOptions
							.getMenuAction("IF_INVESTMENT_BONDS"));
					subMenus.add(MenuOptions.getMenu("IF_INVESTMENT_DEBENTURE"));
					subMenuValues.add(MenuOptions
							.getMenuAction("IF_INVESTMENT_DEBENTURE"));
					subMenus.add(MenuOptions.getMenu("IF_INVESTMENT_GS"));
					subMenuValues.add(MenuOptions
							.getMenuAction("IF_INVESTMENT_GS"));
					subMenus.add(MenuOptions.getMenu("IF_INVESTMENT_MF"));
					subMenuValues.add(MenuOptions
							.getMenuAction("IF_INVESTMENT_MF"));
					subMenus.add(MenuOptions.getMenu("IF_INVESTMENT_CP"));
					subMenuValues.add(MenuOptions
							.getMenuAction("IF_INVESTMENT_CP"));
				} else if (mainMenu.equals(MenuOptions
						.getMenu("IF_UPDATE_MASTER_TABLE"))) {
					subMenus.add(MenuOptions
							.getMenu("IF_UPDATE_MASTER_HOLIDAY"));
					subMenuValues.add(MenuOptions
							.getMenuAction("IF_UPDATE_MASTER_HOLIDAY"));
					subMenus.add(MenuOptions
							.getMenu("IF_UPDATE_MASTER_INVESTEE_GROUP"));
					subMenuValues.add(MenuOptions
							.getMenuAction("IF_UPDATE_MASTER_INVESTEE_GROUP"));
					subMenus.add(MenuOptions
							.getMenu("IF_UPDATE_MASTER_INVESTEE"));
					subMenuValues.add(MenuOptions
							.getMenuAction("IF_UPDATE_MASTER_INVESTEE"));
					subMenus.add(MenuOptions
							.getMenu("IF_UPDATE_MASTER_MATURITY"));
					subMenuValues.add(MenuOptions
							.getMenuAction("IF_UPDATE_MASTER_MATURITY"));
					subMenus.add(MenuOptions
							.getMenu("IF_UPDATE_MASTER_BUDGET_HEADS"));
					subMenuValues.add(MenuOptions
							.getMenuAction("IF_UPDATE_MASTER_BUDGET_HEADS"));
					subMenus.add(MenuOptions
							.getMenu("IF_UPDATE_MASTER_BUDGET_SUB_HEADS"));
					subMenuValues
							.add(MenuOptions
									.getMenuAction("IF_UPDATE_MASTER_BUDGET_SUB_HEADS"));
					subMenus.add(MenuOptions
							.getMenu("IF_UPDATE_MASTER_INSTRUMENT"));
					subMenuValues.add(MenuOptions
							.getMenuAction("IF_UPDATE_MASTER_INSTRUMENT"));
					subMenus.add(MenuOptions
							.getMenu("IF_UPDATE_MASTER_INSTRUMENT_FEATURE"));
					subMenuValues
							.add(MenuOptions
									.getMenuAction("IF_UPDATE_MASTER_INSTRUMENT_FEATURE"));
					subMenus.add(MenuOptions
							.getMenu("IF_UPDATE_MASTER_INSTRUMENT_SCHEME"));
					subMenuValues
							.add(MenuOptions
									.getMenuAction("IF_UPDATE_MASTER_INSTRUMENT_SCHEME"));
					subMenus.add(MenuOptions.getMenu("IF_UPDATE_MASTER_RATING"));
					subMenuValues.add(MenuOptions
							.getMenuAction("IF_UPDATE_MASTER_RATING"));
					subMenus.add(MenuOptions.getMenu("IF_INSERT_MASTER_CORPUS"));
					subMenuValues.add(MenuOptions
							.getMenuAction("IF_INSERT_MASTER_CORPUS"));
					subMenus.add(MenuOptions.getMenu("IF_UPDATE_MASTER_CORPUS"));
					subMenuValues.add(MenuOptions
							.getMenuAction("IF_UPDATE_MASTER_CORPUS"));
					subMenus.add(MenuOptions
							.getMenu("IF_UPDATE_BANK_ACCOUNT_MASTER"));
					subMenuValues.add(MenuOptions
							.getMenuAction("IF_UPDATE_BANK_ACCOUNT_MASTER"));
					subMenus.add(MenuOptions
							.getMenu("IF_UPDATE_INSTRUMENT_CATEGORY"));
					subMenuValues.add(MenuOptions
							.getMenuAction("IF_UPDATE_INSTRUMENT_CATEGORY"));
					subMenus.add(MenuOptions.getMenu("IF_UPDATE_MASTER_AGENCY"));
					subMenuValues.add(MenuOptions
							.getMenuAction("IF_UPDATE_MASTER_AGENCY"));
					subMenus.add(MenuOptions
							.getMenu("IF_UPDATE_MASTER_RATINGS_AND_AGENCY"));
					subMenuValues
							.add(MenuOptions
									.getMenuAction("IF_UPDATE_MASTER_RATINGS_AND_AGENCY"));
				} else if (mainMenu.equals(MenuOptions
						.getMenu("IF_SET_INVESTMENT_CEILING"))) {
					subMenus.add(MenuOptions
							.getMenu("IF_SET_INVESTMENT_CEILING_MATURITY"));
					subMenuValues
							.add(MenuOptions
									.getMenuAction("IF_SET_INVESTMENT_CEILING_MATURITY"));
					subMenus.add(MenuOptions
							.getMenu("IF_SET_INVESTMENT_CEILING_INSTRUMENT"));
					subMenuValues
							.add(MenuOptions
									.getMenuAction("IF_SET_INVESTMENT_CEILING_INSTRUMENT"));
					subMenus.add(MenuOptions
							.getMenu("IF_SET_INVESTMENT_CEILING_INVESTEE_GROUP"));
					subMenuValues
							.add(MenuOptions
									.getMenuAction("IF_SET_INVESTMENT_CEILING_INVESTEE_GROUP"));
					subMenus.add(MenuOptions
							.getMenu("IF_SET_INVESTMENT_CEILING_INVESTEE"));
					subMenuValues
							.add(MenuOptions
									.getMenuAction("IF_SET_INVESTMENT_CEILING_INVESTEE"));
					subMenus.add(MenuOptions.getMenu("RATING"));
					subMenuValues.add(MenuOptions.getMenuAction("RATING"));
				} else if (!mainMenu.equals(MenuOptions
						.getMenu("IF_PROJECT_EXPECTED_CLAIMS")))
					if (mainMenu.equals(MenuOptions.getMenu("IF_REPORT"))) {
						subMenus.add(MenuOptions
								.getMenu("IF_STATEMENT_DETAIL_REPORT"));
						subMenuValues.add(MenuOptions
								.getMenuAction("IF_STATEMENT_DETAIL_REPORT"));
						subMenus.add(MenuOptions.getMenu("IF_FDI_REPORT"));
						subMenuValues.add(MenuOptions
								.getMenuAction("IF_FDI_REPORT"));
						subMenus.add(MenuOptions
								.getMenu("IF_CHEQUE_DETAILS_REPORT"));
						subMenuValues.add(MenuOptions
								.getMenuAction("IF_CHEQUE_DETAILS_REPORT"));
						subMenus.add(MenuOptions
								.getMenu("IF_INFLOW_OUTFLOW_REPORT"));
						subMenuValues.add(MenuOptions
								.getMenuAction("IF_INFLOW_OUTFLOW_REPORT"));
						subMenus.add(MenuOptions
								.getMenu("IF_INVESTMENT_DETAILS_REPORT"));
						subMenuValues.add(MenuOptions
								.getMenuAction("IF_INVESTMENT_DETAILS_REPORT"));
						subMenus.add(MenuOptions
								.getMenu("IF_INVESTMENT_MATURITY_REPORT"));
						subMenuValues
								.add(MenuOptions
										.getMenuAction("IF_INVESTMENT_MATURITY_REPORT"));
						subMenus.add(MenuOptions.getMenu("IF_INVESTMENT_ROI"));
						subMenuValues.add(MenuOptions
								.getMenuAction("IF_INVESTMENT_ROI"));
						subMenus.add(MenuOptions
								.getMenu("IF_INVESTEE_WISE_REPORT"));
						subMenuValues.add(MenuOptions
								.getMenuAction("IF_INVESTEE_WISE_REPORT"));
						subMenus.add(MenuOptions
								.getMenu("IF_ACCRUED_INTEREST_INCOME_REPORT"));
						subMenuValues
								.add(MenuOptions
										.getMenuAction("IF_ACCRUED_INTEREST_INCOME_REPORT"));
						subMenus.add(MenuOptions
								.getMenu("IF_BANK_RECONCILATION"));
						subMenuValues.add(MenuOptions
								.getMenuAction("IF_BANK_RECONCILATION"));
					} else if (mainMenu.equals(MenuOptions
							.getMenu("IF_CHEQUE_DETAILS"))) {
						subMenus.add(MenuOptions
								.getMenu("IF_CHEQUE_DETAILS_INSERT"));
						subMenuValues.add(MenuOptions
								.getMenuAction("IF_CHEQUE_DETAILS_INSERT"));
						subMenus.add(MenuOptions
								.getMenu("IF_CHEQUE_DETAILS_UPDATE"));
						subMenuValues.add(MenuOptions
								.getMenuAction("IF_CHEQUE_DETAILS_UPDATE"));
					} else if (mainMenu.equals(MenuOptions
							.getMenu("IF_PAYMENT_DETAILS"))) {
						subMenus.add(MenuOptions
								.getMenu("IF_PAYMENT_DETAILS_ENTER"));
						subMenuValues.add(MenuOptions
								.getMenuAction("IF_PAYMENT_DETAILS_ENTER"));
						subMenus.add(MenuOptions
								.getMenu("IF_PAYMENT_DETAILS_MODIFY"));
						subMenuValues.add(MenuOptions
								.getMenuAction("IF_PAYMENT_DETAILS_MODIFY"));
					} else if (mainMenu.equals(MenuOptions
							.getMenu("IF_MAKE_INVESTMENT"))) {
						subMenus.add(MenuOptions.getMenu("IF_PLAN_INVESTMENT"));
						subMenuValues.add(MenuOptions
								.getMenuAction("IF_PLAN_INVESTMENT"));
						subMenus.add(MenuOptions.getMenu("IF_MAKE_REQUEST"));
						subMenuValues.add(MenuOptions
								.getMenuAction("IF_MAKE_REQUEST"));
						subMenus.add(MenuOptions
								.getMenu("IF_INVESTMENT_FULFILLMENT"));
						subMenuValues.add(MenuOptions
								.getMenuAction("IF_INVESTMENT_FULFILLMENT"));
					} else if (mainMenu.equals(MenuOptions
							.getMenu("IF_TDS_DETAILS"))) {
						subMenus.add(MenuOptions
								.getMenu("IF_ENTER_TDS_DETAILS"));
						subMenuValues.add(MenuOptions
								.getMenuAction("IF_ENTER_TDS_DETAILS"));
						subMenus.add(MenuOptions
								.getMenu("IF_MODIFY_TDS_DETAILS"));
						subMenuValues.add(MenuOptions
								.getMenuAction("IF_MODIFY_TDS_DETAILS"));
					} else if (mainMenu.equals(MenuOptions
							.getMenu("IF_CHEQUE_LEAVES"))) {
						subMenus.add(MenuOptions
								.getMenu("IF_CHEQUE_LEAVES_INSERT"));
						subMenuValues.add(MenuOptions
								.getMenuAction("IF_CHEQUE_LEAVES_INSERT"));
						subMenus.add(MenuOptions
								.getMenu("IF_CHEQUE_LEAVES_UPDATE"));
						subMenuValues.add(MenuOptions
								.getMenuAction("IF_CHEQUE_LEAVES_UPDATE"));
						subMenus.add(MenuOptions
								.getMenu("IF_CHEQUE_LEAVES_CONVERSION"));
						subMenuValues.add(MenuOptions
								.getMenuAction("IF_CHEQUE_LEAVES_CONVERSION"));
					}
		}
		request.setAttribute("subMenus", subMenus);
		request.setAttribute("subMenuValues", subMenuValues);
		Log.log(4, "LoginAction", "setSubMenu", "Exited");
	}
}
/*// FrontEnd Plus GUI for JAD
// DeCompiled : LoginAction.class

package com.cgtsi.action;

import com.cgtsi.admin.*;
import com.cgtsi.common.*;
import com.cgtsi.registration.MLIInfo;
import com.cgtsi.registration.Registration;
import java.io.PrintStream;
import java.net.InetAddress;
import java.util.ArrayList;
import javax.servlet.http.*;
import org.apache.struts.action.*;

// Referenced classes of package com.cgtsi.action:
//            BaseAction

public class LoginAction extends BaseAction
{

    private static final String ALPHA_NUM = "!@#$%0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public LoginAction()
    {
    }

    public ActionForward login(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws DatabaseException, UserLockedException, InactiveUserException, InvalidPasswordException, NoUserFoundException
    {
        Log.log(4, "LoginAction", "login", "Entered");
        DynaActionForm dynaActionForm = (DynaActionForm)form;
        String memberId = (String)dynaActionForm.get("memberId");
        HttpSession session = request.getSession(false);
        String loggedInUser = null;
        PasswordManager passwordManager = new PasswordManager();
        if(memberId.equals("000000000000"))
            loggedInUser = "CGTSI";
        else
            loggedInUser = "MLI";
        session.setAttribute("loggedInUser", loggedInUser);
        String userId = (String)dynaActionForm.get("userId");
        System.out.println((new StringBuilder()).append("userId:").append(userId).toString());
        String password = (String)dynaActionForm.get("password");
        System.out.println("Password tested");
        String ipAddress = "";
        String hostName = "";
        String proxyName = "";
        String sessionId = "";
        try
        {
            InetAddress i = InetAddress.getLocalHost();
            ipAddress = request.getRemoteAddr();
            hostName = request.getRemoteHost();
            proxyName = request.getHeader("VIA");
            sessionId = session.getId();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        String bankId = memberId.substring(0, 4);
        String zoneId = memberId.substring(4, 8);
        String branchId = memberId.substring(8, 12);
        Registration registration = new Registration();
        registration.updateLoginInformation(bankId, zoneId, branchId, ipAddress, hostName, proxyName, sessionId, userId);
        registration.getMemberDetails(bankId, zoneId, branchId);
        userId = userId.toUpperCase();
        dynaActionForm.set("userId", userId);
        User user = Login.login(memberId, userId, password);
        userId = user.getUserId();
        dynaActionForm.set("userId", user.getUserId());
        session.setAttribute((String)dynaActionForm.get("userId"), user);
        session.setAttribute("USER_ID", (String)dynaActionForm.get("userId"));
        session.setAttribute("USER", user);
        Administrator admin = new Administrator();
        ArrayList userPrivileges = admin.getPrivileges(userId);
        dynaActionForm.set("userPrivileges", userPrivileges);
        user.setPrivileges(userPrivileges);
        ArrayList alerts = admin.getAlerts(userId);
        String message = admin.getMessage(userId);
        Log.log(5, "LoginAction", "login", (new StringBuilder()).append("message ").append(message).toString());
        dynaActionForm.set("message", message);
        dynaActionForm.set("alerts", alerts);
        String shortName = user.getShortName();
        String bankName = user.getBankName();
        String zoneName = user.getZoneName();
        String branchName = user.getBranchName();
        Log.log(5, "LoginAction", "login", (new StringBuilder()).append("bank id ").append(user.getBankId()).toString());
        Log.log(5, "LoginAction", "login", (new StringBuilder()).append("zone id ").append(user.getZoneId()).toString());
        Log.log(5, "LoginAction", "login", (new StringBuilder()).append("branch id ").append(user.getBranchId()).toString());
        Log.log(5, "LoginAction", "login", (new StringBuilder()).append("bank Name ").append(bankName).toString());
        Log.log(5, "LoginAction", "login", (new StringBuilder()).append("zone name ").append(zoneName).toString());
        Log.log(5, "LoginAction", "login", (new StringBuilder()).append("branch name ").append(branchName).toString());
        Log.log(5, "LoginAction", "login", (new StringBuilder()).append("short name ").append(shortName).toString());
        if(shortName == null)
        {
            bankName = bankName.length() <= 10 ? bankName : bankName.substring(0, 10);
            dynaActionForm.set("bankName", bankName);
        } else
        {
            dynaActionForm.set("bankName", shortName);
        }
        if(zoneName.length() > 10)
            zoneName = zoneName.substring(0, 10);
        if(branchName.length() > 10)
            branchName = branchName.substring(0, 10);
        dynaActionForm.set("zoneName", zoneName);
        dynaActionForm.set("branchName", branchName);
        MLIInfo mliInfo = registration.getMemberDetails(userId);
        Log.log(5, "LoginAction", "login", (new StringBuilder()).append("before setting...bank id...").append(mliInfo.getBankId()).toString());
        session.setAttribute("MEMBER_INFO", mliInfo);
        if(user.isPasswordExpired())
        {
            Log.log(5, "LoginAction", "login", "Password is expired");
            session.setAttribute("PASSWORD_EXPIRED", "true");
            Hint hint = user.getHint();
            request.setAttribute("hintQuestion", hint.getHintQuestion());
            request.setAttribute("hintAnswer", hint.getHintAnswer());
            request.setAttribute("emailId", user.getEmailId());
            return mapping.findForward("passwordExpired");
        } else
        {
            Log.log(5, "LoginAction", "login", "Password is Valid");
            Log.log(4, "LoginAction", "login", "Exited");
            return mapping.findForward("success");
        }
    }

    public ActionForward showCGTSIHome(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws DatabaseException
    {
        Log.log(4, "LoginAction", "showHome", "Exited");
        DynaActionForm dynaForm = (DynaActionForm)form;
        User user = getUserInformation(request);
        String userId = user.getUserId();
        Log.log(5, "LoginAction", "showHome", (new StringBuilder()).append("userId ").append(userId).toString());
        Administrator admin = new Administrator();
        ArrayList alerts = admin.getAlerts(userId);
        String message = admin.getMessage(userId);
        Log.log(5, "LoginAction", "showHome", (new StringBuilder()).append("message ").append(message).toString());
        dynaForm.set("message", message);
        dynaForm.set("alerts", alerts);
        MLIInfo memberInfo = getMemberInfo(request);
        Log.log(5, "LoginAction", "showHome", (new StringBuilder()).append("bank id and distict ").append(memberInfo.getBankId()).append(" ... ").append(memberInfo.getDistrict()).toString());
        Log.log(4, "LoginAction", "showHome", "Exited");
        return mapping.findForward("success");
    }

    public ActionForward getMainMenu(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        Log.log(4, "LoginAction", "getMainMenu", "Entered");
        setMainMenu(request);
        Log.log(4, "LoginAction", "getMainMenu", "Exited");
        return mapping.findForward("success");
    }

    private ArrayList setMainMenu(HttpServletRequest request)
    {
        Log.log(4, "LoginAction", "setMainMenu", "Entered");
        String menuIcon = request.getParameter("menuIcon");
        ArrayList mainMenus = new ArrayList();
        ArrayList mainMenuValues = new ArrayList();
        if(menuIcon != null)
        {
            User user = getUserInformation(request);
            ArrayList userPrivileges = user.getPrivileges();
            mainMenus.add(MenuOptions.getMenu("HOME_SELECT"));
            mainMenuValues.add(MenuOptions.getMenuAction("HOME_SELECT"));
            if(menuIcon.equals("IO"))
            {
                if(userPrivileges.contains("ADD_INWARD"))
                {
                    mainMenus.add(MenuOptions.getMenu("IO_INWARD"));
                    mainMenuValues.add(MenuOptions.getMenuAction("IO_INWARD"));
                    mainMenus.add(MenuOptions.getMenu("IO_INWARD_NEW"));
                    mainMenuValues.add(MenuOptions.getMenuAction("IO_INWARD_NEW"));
                    mainMenus.add(MenuOptions.getMenu("IO_ADD_INWARD_SOURCE_NAME"));
                    mainMenuValues.add(MenuOptions.getMenuAction("IO_ADD_INWARD_SOURCE_NAME"));
                    mainMenus.add(MenuOptions.getMenu("IO_UPDATE_INWARD_DETAILS"));
                    mainMenuValues.add(MenuOptions.getMenuAction("IO_UPDATE_INWARD_DETAILS"));
                    mainMenus.add(MenuOptions.getMenu("IO_WORKSHOP_ENTRY"));
                    mainMenuValues.add(MenuOptions.getMenuAction("IO_WORKSHOP_ENTRY"));
                    String loggedUserId = user.getUserId();
                    if(loggedUserId.equalsIgnoreCase("ADMIN") || loggedUserId.equalsIgnoreCase("CPRAB0001") || loggedUserId.equalsIgnoreCase("PUSLATH0001"))
                    {
                        mainMenus.add(MenuOptions.getMenu("IO_UPDATE_SCHEME_PROPAGATION_DETAILS"));
                        mainMenuValues.add(MenuOptions.getMenuAction("IO_UPDATE_SCHEME_PROPAGATION_DETAILS"));
                    }
                }
                if(userPrivileges.contains("ADD_OUTWARD"))
                {
                    mainMenus.add(MenuOptions.getMenu("IO_OUTWARD"));
                    mainMenuValues.add(MenuOptions.getMenuAction("IO_OUTWARD"));
                }
                if(!userPrivileges.contains("UPLOAD_DOCUMENTS"));
                if(userPrivileges.contains("SEARCH_DOCUMENTS"))
                {
                    mainMenus.add(MenuOptions.getMenu("IO_SEARCH_DOCUMENTS"));
                    mainMenuValues.add(MenuOptions.getMenuAction("IO_SEARCH_DOCUMENTS"));
                }
            } else
            if(menuIcon.equals("RS"))
            {
            	mainMenus.add(MenuOptions.getMenu("ACCOUNT_HISTORY_REPORT"));
                mainMenuValues.add(MenuOptions.getMenuAction("ACCOUNT_HISTORY_REPORT"));
            	mainMenus.add(MenuOptions.getMenu("RS_APPLICATION_STATUS"));
                mainMenuValues.add(MenuOptions.getMenuAction("RS_APPLICATION_STATUS"));
                mainMenus.add(MenuOptions.getMenu("RS_SEARCH_HISTORY"));
                mainMenuValues.add(MenuOptions.getMenuAction("RS_SEARCH_HISTORY"));
                mainMenus.add(MenuOptions.getMenu("RS_EPCS_REPORT"));
                mainMenuValues.add(MenuOptions.getMenuAction("RS_EPCS_REPORT"));
                if(userPrivileges.contains("REPORTS"))
                {
                    mainMenus.add(MenuOptions.getMenu("RS_APPLICATION_DETAILS"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RS_APPLICATION_DETAILS"));
                    mainMenus.add(MenuOptions.getMenu("RS_APPLICATION_RELATED_REPORT"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RS_APPLICATION_RELATED_REPORT"));
                    mainMenus.add(MenuOptions.getMenu("RS_ASF_SUMMERY_DETAILS"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RS_ASF_SUMMERY_DETAILS"));
                    String loggedUserId = user.getUserId();
                    if(loggedUserId.equalsIgnoreCase("ADMIN") || loggedUserId.equalsIgnoreCase("SHRSHUK0001") || loggedUserId.equalsIgnoreCase("GYANESH0001") || loggedUserId.equalsIgnoreCase("CPRAB0001") || loggedUserId.equalsIgnoreCase("PUSLATH0001"))
                    {
                        mainMenus.add(MenuOptions.getMenu("RS_ASF2011_NOTPAID_SUMMARY_REPORT"));
                        mainMenuValues.add(MenuOptions.getMenuAction("RS_ASF2011_NOTPAID_SUMMARY_REPORT"));
                    }
                    mainMenus.add(MenuOptions.getMenu("RS_REPORT_OF_PROPOSAL"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RS_REPORT_OF_PROPOSAL"));
                    mainMenus.add(MenuOptions.getMenu("RS_GUARANTEE_COVER_ISSUED"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RS_GUARANTEE_COVER_ISSUED"));
                    mainMenus.add(MenuOptions.getMenu("RS_MLI_LIST"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RS_MLI_LIST"));
                    mainMenus.add(MenuOptions.getMenu("RS_PENDING_APPLICATION_MLI_WISE"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RS_PENDING_APPLICATION_MLI_WISE"));
                    mainMenus.add(MenuOptions.getMenu("RS_MONTHLY_PROGRESS_REPORT"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RS_MONTHLY_PROGRESS_REPORT"));
                    mainMenus.add(MenuOptions.getMenu("RS_DAN_REPORTS-ALL"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RS_DAN_REPORTS-ALL"));
                   // mainMenus.add(MenuOptions.getMenu("RS_MIS"));
                   // mainMenuValues.add(MenuOptions.getMenuAction("RS_MIS"));
                    mainMenus.add(MenuOptions.getMenu("RS_GM_REPORTS"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RS_GM_REPORTS"));
                    mainMenus.add(MenuOptions.getMenu("RS_RP_REPORTS"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RS_RP_REPORTS"));
                    mainMenus.add(MenuOptions.getMenu("QUERY_BUILDER"));
                    mainMenuValues.add(MenuOptions.getMenuAction("QUERY_BUILDER"));
                    mainMenus.add(MenuOptions.getMenu("RS_CLAIMS_DETAILS"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RS_CLAIMS_DETAILS"));
                    mainMenus.add(MenuOptions.getMenu("RS_SECURITIZATION_REPORT"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RS_SECURITIZATION_REPORT"));
                    mainMenus.add(MenuOptions.getMenu("RS_WOMEN_ENTREPRENEUR_REPORT"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RS_WOMEN_ENTREPRENEUR_REPORT"));
                    mainMenus.add(MenuOptions.getMenu("RS_INWARD_REPORT"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RS_INWARD_REPORT"));
                    mainMenus.add(MenuOptions.getMenu("RS_NPA_DETAIL_REPORT"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RS_NPA_DETAIL_REPORT"));
                    mainMenus.add(MenuOptions.getMenu("RS_INVESTMENT_REPORT"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RS_INVESTMENT_REPORT"));
                    mainMenus.add(MenuOptions.getMenu("RS_SCHEM_PROPAGATION_DETAILS"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RS_SCHEM_PROPAGATION_DETAILS"));
                    //mainMenus.add(MenuOptions.getMenu("RS_MONTH_WISE_REPORT_NEW"));
                   // mainMenuValues.add(MenuOptions.getMenuAction("RS_MONTH_WISE_REPORT_NEW"));
                    
                    if(loggedUserId.equalsIgnoreCase("ADMIN") || loggedUserId.equalsIgnoreCase("ADMIN") || loggedUserId.equalsIgnoreCase("CPRAB0001") || loggedUserId.equalsIgnoreCase("PUSLATH0001"))
                    {
                        mainMenus.add(MenuOptions.getMenu("RS_CGPAN_COMPLE_HISTORY"));
                        mainMenuValues.add(MenuOptions.getMenuAction("RS_CGPAN_COMPLE_HISTORY"));
                    }
                } else
                {
                    mainMenus.add(MenuOptions.getMenu("RS_APPLICATION_DETAILS"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RS_APPLICATION_DETAILS"));
                    mainMenus.add(MenuOptions.getMenu("RS_APPLICATION_RELATED_REPORT"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RS_APPLICATION_RELATED_REPORT"));
                    mainMenus.add(MenuOptions.getMenu("RS_ASF_SUMMERY_DETAILS"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RS_ASF_SUMMERY_DETAILS"));
                    mainMenus.add(MenuOptions.getMenu("RS_MONTHLY_PROGRESS_REPORT"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RS_MONTHLY_PROGRESS_REPORT"));
                    mainMenus.add(MenuOptions.getMenu("RS_DAN_REPORTS-ALL"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RS_DAN_REPORTS-ALL"));
                    mainMenus.add(MenuOptions.getMenu("RS_GM_REPORTS"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RS_GM_REPORTS"));
                  //  mainMenus.add(MenuOptions.getMenu("RS_MIS"));
                   // mainMenuValues.add(MenuOptions.getMenuAction("RS_MIS"));
                    mainMenus.add(MenuOptions.getMenu("RS_MLI_LIST"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RS_MLI_LIST"));
                    mainMenus.add(MenuOptions.getMenu("RS_RP_REPORTS"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RS_RP_REPORTS"));
                    mainMenus.add(MenuOptions.getMenu("RS_CLAIMS_DETAILS"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RS_CLAIMS_DETAILS"));
                    mainMenus.add(MenuOptions.getMenu("RS_NPA_DETAIL_REPORT"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RS_NPA_DETAIL_REPORT"));
                    mainMenus.add(MenuOptions.getMenu("CLAIM_DECL_UNDET_TAKING"));
                    mainMenuValues.add(MenuOptions.getMenuAction("CLAIM_DECL_UNDET_TAKING"));
                    mainMenus.add(MenuOptions.getMenu("NPA_UPGRADATION_LIST"));
                    mainMenuValues.add(MenuOptions.getMenuAction("NPA_UPGRADATION_LIST"));
                    mainMenus.add(MenuOptions.getMenu("RS_QUERY_REPORT_INPUT"));               
                    mainMenuValues.add(MenuOptions.getMenuAction("RS_QUERY_REPORT_INPUT")); 
                    
                }
                
            } else
            if(menuIcon.equals("AD"))
            {
                if(userPrivileges.contains("REGISTER_COLLECTING_BANK") || userPrivileges.contains("MODIFY_COLLECTING_BANK_DETAILS"))
                {
                    mainMenus.add(MenuOptions.getMenu("AD_COLLECTING_BANK"));
                    mainMenuValues.add(MenuOptions.getMenuAction("AD_COLLECTING_BANK"));
                }
                if(userPrivileges.contains("REGISTER_MLI") || userPrivileges.contains("ASSIGN_COLLECTING_BANK") || userPrivileges.contains("MODIFY_COLLECTING_BANK") || userPrivileges.contains("DEFINE_ORGANIZATION_STRUCTURE") || userPrivileges.contains("DEACTIVATE_MEMBER") || userPrivileges.contains("ACTIVATE_MEMBER"))
                {
                    mainMenus.add(MenuOptions.getMenu("AD_MLI"));
                    mainMenuValues.add(MenuOptions.getMenuAction("AD_MLI"));
                }
                if(userPrivileges.contains("ADD_MODIFY_ROLES"))
                {
                    mainMenus.add(MenuOptions.getMenu("AD_ROLES_MGMT"));
                    mainMenuValues.add(MenuOptions.getMenuAction("AD_ROLES_MGMT"));
                }
                if(userPrivileges.contains("CREATE_CGTSI_USER") || userPrivileges.contains("CREATE_MLI_USER") || userPrivileges.contains("MODIFY_USER_DETAILS") || userPrivileges.contains("DEACTIVATE_USER") || userPrivileges.contains("REACTIVATE_USER") || userPrivileges.contains("ASSIGN_MODIFY_ROLES_AND_PRIVILEGES"))
                {
                    mainMenus.add(MenuOptions.getMenu("AD_USER_MGMT"));
                    mainMenuValues.add(MenuOptions.getMenuAction("AD_USER_MGMT"));
                }
                mainMenus.add(MenuOptions.getMenu("AD_PASSWORD_MGMT"));
                mainMenuValues.add(MenuOptions.getMenuAction("AD_PASSWORD_MGMT"));
                mainMenus.add(MenuOptions.getMenu("AD_MODIFY_MLI_ADDRESS"));
                mainMenuValues.add(MenuOptions.getMenuAction("AD_MODIFY_MLI_ADDRESS"));
                if(userPrivileges.contains("ENTER_AUDIT_DETAILS") || userPrivileges.contains("REVIEW_AUDIT_DETAILS"))
                {
                    mainMenus.add(MenuOptions.getMenu("AD_AUDIT"));
                    mainMenuValues.add(MenuOptions.getMenuAction("AD_AUDIT"));
                }
                if(userPrivileges.contains("BROADCAST_MESSAGE"))
                {
                    mainMenus.add(MenuOptions.getMenu("AD_BROADCAST_MESSAGE"));
                    mainMenuValues.add(MenuOptions.getMenuAction("AD_BROADCAST_MESSAGE"));
                }
                if(userPrivileges.contains("UPDATE_MASTER_TABLE"))
                {
                    mainMenus.add(MenuOptions.getMenu("AD_UPDATE_MASTER"));
                    mainMenuValues.add(MenuOptions.getMenuAction("AD_UPDATE_MASTER"));
                }
                if(userPrivileges.contains("DAY_END_PROCESS"))
                {
                    mainMenus.add(MenuOptions.getMenu("AD_DAY_END_PROCESSES"));
                    mainMenuValues.add(MenuOptions.getMenuAction("AD_DAY_END_PROCESSES"));
                }
                
                mainMenus.add(MenuOptions.getMenu("ACCOUNT_INFO_NEW"));
                mainMenuValues.add(MenuOptions.getMenuAction("ACCOUNT_INFO_NEW"));
                // 
                mainMenus.add(MenuOptions.getMenu("AD_SEND_MAIL"));
                mainMenuValues.add(MenuOptions.getMenuAction("AD_SEND_MAIL"));
                String bankid = user.getBankId();
                String zoneid = user.getZoneId();
                String branchid = user.getBranchId();
                if("0000".equals(zoneid) && "0000".equals(branchid))
                {
                    mainMenus.add(MenuOptions.getMenu("AD_REG_NPA_APP"));
                    mainMenuValues.add(MenuOptions.getMenuAction("AD_REG_NPA_APP"));              
                  //  mainMenus.add(MenuOptions.getMenu("AD_ACCOUNT_INFO_NEW"));
                //    mainMenuValues.add(MenuOptions.getMenuAction("AD_ACCOUNT_INFO_NEW"));
               
                }
                if(user.getUserId().contains("0"))
                {
                    mainMenus.add(MenuOptions.getMenu("AD_REG_FOR_NPA"));
                    mainMenuValues.add(MenuOptions.getMenuAction("AD_REG_FOR_NPA"));
                    //mainMenus.add(MenuOptions.getMenu("ACCOUNT_INFO_NEW"));
                    //mainMenuValues.add(MenuOptions.getMenuAction("ACCOUNT_INFO_NEW"));
                }
            } else
            if(menuIcon.equals("CP"))
            {
                if(userPrivileges.contains("CLAIM_FIRST_INSTALLMENT") || userPrivileges.contains("CLAIM_SECOND_INSTALLMENT"))
                {
                    mainMenus.add(MenuOptions.getMenu("CP_CLAIM_FOR"));
                    mainMenuValues.add(MenuOptions.getMenuAction("CP_CLAIM_FOR"));
                    String loggedUserId = user.getUserId();
                    if(loggedUserId.equalsIgnoreCase("ADMIN") || loggedUserId.equalsIgnoreCase("URTATA0001") || loggedUserId.equalsIgnoreCase("PRADH0001") || loggedUserId.equalsIgnoreCase("MUKKUMA0001") || loggedUserId.equalsIgnoreCase("GAUKAPO0001") || loggedUserId.equalsIgnoreCase("AVSYAM0001"))
                    {
                        mainMenus.add(MenuOptions.getMenu("Claim_Change_Status"));
                        mainMenuValues.add(MenuOptions.getMenuAction("Claim_Change_Status"));
                    }
                }
                if(userPrivileges.contains("OTS_REQUEST") || userPrivileges.contains("OTS_APPROVAL"))
                {
                  //  mainMenus.add(MenuOptions.getMenu("CP_OTS"));
                   // mainMenuValues.add(MenuOptions.getMenuAction("CP_OTS"));
                }
                // 26/05/2017
                if(userPrivileges.contains("CLAIM_APPROVAL"))
                {
                    mainMenus.add(MenuOptions.getMenu("CP_APPROVAL"));
                    mainMenuValues.add(MenuOptions.getMenuAction("CP_APPROVAL"));
                    mainMenus.add(MenuOptions.getMenu("CP_PROCESSING"));
                    mainMenuValues.add(MenuOptions.getMenuAction("CP_PROCESSING"));
                } //26/05/2017
                
                if(userPrivileges.contains("PERIODIC_INFO_APPROVAL"))
                {
                    String loggedUserId = user.getUserId();
                    if(loggedUserId.equalsIgnoreCase("ADMIN") || loggedUserId.equalsIgnoreCase("URTATA0001") || loggedUserId.equalsIgnoreCase("PRADH0001") || loggedUserId.equalsIgnoreCase("MUKKUMA0001") || loggedUserId.equalsIgnoreCase("AVSYAM0001"))
                    {
                        mainMenus.add(MenuOptions.getMenu("CP_APPROVAL_NEW"));
                        mainMenuValues.add(MenuOptions.getMenuAction("CP_APPROVAL_NEW"));
                    }
                }
                String loggedUserId = user.getUserId();
               // if(loggedUserId.equalsIgnoreCase("demouser"))
                {
                	 mainMenus.add(MenuOptions.getMenu("CP_CLAIM_RECOVERY_INFO"));
                     mainMenuValues.add(MenuOptions.getMenuAction("CP_CLAIM_RECOVERY_INFO"));
                   // mainMenus.add(MenuOptions.getMenu("CP_SUBMIT_DU"));////FRO DEMOUSER
                  //  mainMenuValues.add(MenuOptions.getMenuAction("CP_SUBMIT_DU"));//FRO DEMOUSER
                   //  mainMenus.add(MenuOptions.getMenu("AD_ACCOUNT_INFO_NEW"));
                   // mainMenuValues.add(MenuOptions.getMenuAction("AD_ACCOUNT_INFO_NEW"));//rajuk
                     if(userPrivileges.contains("APPROVE_B_T_MODIFICATION") || userPrivileges.contains("CLAIM_DNU_APPROVAL"))
                     {
                         mainMenus.add(MenuOptions.getMenu("CP_SUBMIT_DU"));
                         mainMenuValues.add(MenuOptions.getMenuAction("CP_SUBMIT_DU"));
                         mainMenus.add(MenuOptions.getMenu("AD_ACCOUNT_INFO_NEW"));
                         mainMenuValues.add(MenuOptions.getMenuAction("AD_ACCOUNT_INFO_NEW"));//rajuk  
                         
                     }
                }
            } else
            if(menuIcon.equals("GM"))
            {
            	 // 26/05/2017
            	mainMenus.add(MenuOptions.getMenu("RP_REQUEST_TENURE"));
                  mainMenuValues.add(MenuOptions.getMenuAction("RP_REQUEST_TENURE"));
                if(userPrivileges.contains("MODIFY_BORROWER_DETAILS"))
                {
                    mainMenus.add(MenuOptions.getMenu("GM_MODIFY_BORROWER_DETAILS"));
                    mainMenuValues.add(MenuOptions.getMenuAction("GM_MODIFY_BORROWER_DETAILS"));
                    mainMenus.add(MenuOptions.getMenu("RP_REQUEST_TENURE"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RP_REQUEST_TENURE"));
                }
                if(userPrivileges.contains("PERIODIC_INFO"))
                {
                    mainMenus.add(MenuOptions.getMenu("GM_PERIODIC_INFO"));
                    mainMenuValues.add(MenuOptions.getMenuAction("GM_PERIODIC_INFO"));
                    mainMenus.add(MenuOptions.getMenu("GM_VIEW_CGPAN"));
                    mainMenuValues.add(MenuOptions.getMenuAction("GM_VIEW_CGPAN"));
                }
                if(userPrivileges.contains("UPDATE_REPAYMENT_SCHEDULE"))
                {
                    mainMenus.add(MenuOptions.getMenu("GM_UPDATE_REPAYMENT_SCHEDULE"));
                    mainMenuValues.add(MenuOptions.getMenuAction("GM_UPDATE_REPAYMENT_SCHEDULE"));
                }
                if(userPrivileges.contains("SHIFT_CGPAN_OR_BORROWER"))
                {
                    mainMenus.add(MenuOptions.getMenu("GM_SHIFT_CGPAN_OR_BORROWER"));
                    mainMenuValues.add(MenuOptions.getMenuAction("GM_SHIFT_CGPAN_OR_BORROWER"));
                }
                if(!userPrivileges.contains("SHIFT_CGPAN_OR_BORROWER"));
                if(!userPrivileges.contains("EXPORT_PERIDIC_INFO"));
                if(userPrivileges.contains("APPLICATION_CLOSURE"))
                {
                    mainMenus.add(MenuOptions.getMenu("GM_CLOSURE"));
                    mainMenuValues.add(MenuOptions.getMenuAction("GM_CLOSURE"));
                }
                if(userPrivileges.contains("MODIFY_BORROWER_DETAILS"))
                {
                    mainMenus.add(MenuOptions.getMenu("GM_CLOSURE_REQUEST"));
                    mainMenuValues.add(MenuOptions.getMenuAction("GM_CLOSURE_REQUEST"));
                    mainMenus.add(MenuOptions.getMenu("GM_OUTSTANDING_REQUEST_MENU"));
                    mainMenuValues.add(MenuOptions.getMenuAction("GM_OUTSTANDING_REQUEST_MENU"));
                   //mainMenus.add(MenuOptions.getMenu("GM_UPDATION_GSTIN"));
                   //mainMenuValues.add(MenuOptions.getMenuAction("GM_UPDATION_GSTIN"));//rajuk
                   
                }
                if(userPrivileges.contains("PERIODIC_INFO_APPROVAL"))
                {
                    String loggedUserId = user.getUserId();
                    if(loggedUserId.equalsIgnoreCase("ADMIN") || loggedUserId.equalsIgnoreCase("CPRAB0001") || loggedUserId.equalsIgnoreCase("PUSLATH0001"))
                    {
                        mainMenus.add(MenuOptions.getMenu("GM_CLOSURE_APPROVE"));
                        mainMenuValues.add(MenuOptions.getMenuAction("GM_CLOSURE_APPROVE"));
                    }
                }
                if(userPrivileges.contains("PERIODIC_INFO_APPROVAL"))
                {
                    mainMenus.add(MenuOptions.getMenu("GM_PERIODIC_INFO_APPROVAL"));
                    mainMenuValues.add(MenuOptions.getMenuAction("GM_PERIODIC_INFO_APPROVAL"));
                    // 26/05/2017
                    mainMenus.add(MenuOptions.getMenu("GM_BORROWER_DETAILS_APPROVAL"));
                    mainMenuValues.add(MenuOptions.getMenuAction("GM_BORROWER_DETAILS_APPROVAL"));
                    
                    
                }
                if(userPrivileges.contains("APPROVE_NPA_UPGRADATION"))
                {
                    mainMenus.add(MenuOptions.getMenu("GM_NPA_UPDATION_LIST"));
                    mainMenuValues.add(MenuOptions.getMenuAction("GM_NPA_UPDATION_LIST"));
                }
                if(userPrivileges.contains("APPROVE_B_T_MODIFICATION"))
                {
                	mainMenus.add(MenuOptions.getMenu("GM_BORROWER_DETAILS_APPROVAL"));
                	mainMenuValues.add(MenuOptions.getMenuAction("GM_BORROWER_DETAILS_APPROVAL"));
                
                	mainMenus.add(MenuOptions.getMenu("GM_TENURE_APPROVE"));
                	mainMenuValues.add(MenuOptions.getMenuAction("GM_TENURE_APPROVE"));
                
                }
            } else
            if(menuIcon.equals("IF"))
            {
                if(userPrivileges.contains("ENTER_BUDGET_DETAILS"))
                {
                    mainMenus.add(MenuOptions.getMenu("IF_BUDGET_DETAILS"));
                    mainMenuValues.add(MenuOptions.getMenuAction("IF_BUDGET_DETAILS"));
                }
                if(userPrivileges.contains("ENTER_INFLOW_DETAILS"))
                {
                    mainMenus.add(MenuOptions.getMenu("IF_INFLOW_DETAILS"));
                    mainMenuValues.add(MenuOptions.getMenuAction("IF_INFLOW_DETAILS"));
                }
                if(userPrivileges.contains("ENTER_OUTFLOW_DETAILS"))
                {
                    mainMenus.add(MenuOptions.getMenu("IF_OUTFLOW_DETAILS"));
                    mainMenuValues.add(MenuOptions.getMenuAction("IF_OUTFLOW_DETAILS"));
                }
                if(userPrivileges.contains("SET_CEILING_FOR_INVESTMENT"))
                {
                    mainMenus.add(MenuOptions.getMenu("IF_SET_INVESTMENT_CEILING"));
                    mainMenuValues.add(MenuOptions.getMenuAction("IF_SET_INVESTMENT_CEILING"));
                }
                if(userPrivileges.contains("ENTER_INVESTMENT_DETAILS"))
                {
                    mainMenus.add(MenuOptions.getMenu("IF_INVESTMENT_DETAILS"));
                    mainMenuValues.add(MenuOptions.getMenuAction("IF_INVESTMENT_DETAILS"));
                    mainMenus.add(MenuOptions.getMenu("IF_VOUCHER_GENERATION"));
                    mainMenuValues.add(MenuOptions.getMenuAction("IF_VOUCHER_GENERATION"));
                    mainMenus.add(MenuOptions.getMenu("IF_CHEQUE_LEAVES"));
                    mainMenuValues.add(MenuOptions.getMenuAction("IF_CHEQUE_LEAVES"));
                    mainMenus.add(MenuOptions.getMenu("IF_UPDATE_INVESTMENT_DETAILS"));
                    mainMenuValues.add(MenuOptions.getMenuAction("IF_UPDATE_INVESTMENT_DETAILS"));
                }
                if(userPrivileges.contains("IF_CALCULATE_EXPOSUER"))
                {
                    mainMenus.add(MenuOptions.getMenu("IF_CALCULATE_EXPOSURE"));
                    mainMenuValues.add(MenuOptions.getMenuAction("IF_CALCULATE_EXPOSURE"));
                    mainMenus.add(MenuOptions.getMenu("IF_MAKE_INVESTMENT"));
                    mainMenuValues.add(MenuOptions.getMenuAction("IF_MAKE_INVESTMENT"));
                    mainMenus.add(MenuOptions.getMenu("IF_MISCELLANEOUS_RECEIPTS"));
                    mainMenuValues.add(MenuOptions.getMenuAction("IF_MISCELLANEOUS_RECEIPTS"));
                }
                if(userPrivileges.contains("TDS_DETAILS"))
                {
                    mainMenus.add(MenuOptions.getMenu("IF_TDS_DETAILS"));
                    mainMenuValues.add(MenuOptions.getMenuAction("IF_TDS_DETAILS"));
                }
                if(userPrivileges.contains("STATEMENT_DETAILS"))
                {
                    mainMenus.add(MenuOptions.getMenu("IF_CHEQUE_DETAILS"));
                    mainMenuValues.add(MenuOptions.getMenuAction("IF_CHEQUE_DETAILS"));
                }
                if(userPrivileges.contains("STATEMENT_DETAILS"))
                {
                    mainMenus.add(MenuOptions.getMenu("IF_HVC"));
                    mainMenuValues.add(MenuOptions.getMenuAction("IF_HVC"));
                }
                if(userPrivileges.contains("STATEMENT_DETAILS"))
                {
                    mainMenus.add(MenuOptions.getMenu("IF_BANK_STATEMENT"));
                    mainMenuValues.add(MenuOptions.getMenuAction("IF_BANK_STATEMENT"));
                }
                if(userPrivileges.contains("PROJECT_EXPECTED_CLAIMS"))
                {
                    mainMenus.add(MenuOptions.getMenu("IF_PROJECT_EXPECTED_CLAIMS"));
                    mainMenuValues.add(MenuOptions.getMenuAction("IF_PROJECT_EXPECTED_CLAIMS"));
                }
                if(userPrivileges.contains("IF_UPDATE_MASTER_TABLES"))
                {
                    mainMenus.add(MenuOptions.getMenu("IF_PAYMENT_DETAILS"));
                    mainMenuValues.add(MenuOptions.getMenuAction("IF_PAYMENT_DETAILS"));
                    mainMenus.add(MenuOptions.getMenu("IF_UPDATE_MASTER_TABLE"));
                    mainMenuValues.add(MenuOptions.getMenuAction("IF_UPDATE_MASTER_TABLE"));
                }
                if(userPrivileges.contains("IF_REPORT"))
                {
                    mainMenus.add(MenuOptions.getMenu("IF_REPORT"));
                    mainMenuValues.add(MenuOptions.getMenuAction("IF_REPORT"));
                }
                if(userPrivileges.contains("STATEMENT_DETAILS"))
                {
                    mainMenus.add(MenuOptions.getMenu("IF_FILE_UPLOAD"));
                    mainMenuValues.add(MenuOptions.getMenuAction("IF_FILE_UPLOAD"));
                }
            } else
            if(menuIcon.equals("RP"))
            {
                String loggedUserId1;
                if(userPrivileges.contains("ALLOCATE_PAYMENTS"))
                {
                	loggedUserId1 = user.getUserId();
                	// 26/05/2017
                    mainMenus.add(MenuOptions.getMenu("RP_ALLOCATE_PAYMENTS"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RP_ALLOCATE_PAYMENTS"));
                    loggedUserId1 = user.getUserId();
                    mainMenus.add(MenuOptions.getMenu("RP_MAP_NEFT_PAYMETNS"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RP_MAP_NEFT_PAYMETNS"));
                	mainMenus.add(MenuOptions.getMenu("RP_ALLOCATE_PAYMENTS_ONLINEMENU"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RP_ALLOCATE_PAYMENTS_ONLINEMENU"));
                    mainMenus.add(MenuOptions.getMenu("ASF_PAYMENT_THROUGH_NEFT_OR_RTGS"));
                    mainMenuValues.add(MenuOptions.getMenuAction("ASF_PAYMENT_THROUGH_NEFT_OR_RTGS"));
                }
                if(userPrivileges.contains("APPROPRIATE_PAYMENTS"))
                {
                    mainMenus.add(MenuOptions.getMenu("RP_APPROPRIATE_ALLOCATE_PAYMENTS"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RP_APPROPRIATE_ALLOCATE_PAYMENTS"));
                }
                if(userPrivileges.contains("VIEW_PENDING_PAYMENTS"))
                {
                    mainMenus.add(MenuOptions.getMenu("RP_VIEW_PENDING_PAYMENTS"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RP_VIEW_PENDING_PAYMENTS"));
                }
                if(userPrivileges.contains("APPROPRIATE_PAYMENTS"))
                {
                    mainMenus.add(MenuOptions.getMenu("RP_DE_ALLOCATE_PAYMENTS"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RP_DE_ALLOCATE_PAYMENTS"));
                }
                loggedUserId1 = user.getUserId();
                if(loggedUserId1.equalsIgnoreCase("ADMIN") || loggedUserId1.equalsIgnoreCase("AVSYAM0001") || loggedUserId1.equalsIgnoreCase("ASHBHOV0001") || loggedUserId1.equalsIgnoreCase("CPRAB0001") || loggedUserId1.equalsIgnoreCase("PUSLATH0001"))
                {
                    mainMenus.add(MenuOptions.getMenu("RP_MODIFY_PAYMENT_DETAIL"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RP_MODIFY_PAYMENT_DETAIL"));
                }
                if(userPrivileges.contains("APPROPRIATE_PAYMENTS"))
                {
                    mainMenus.add(MenuOptions.getMenu("RP_APPROPRIATE_PAYMENTS"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RP_APPROPRIATE_PAYMENTS"));
                }
                if(userPrivileges.contains("APPROPRIATE_PAYMENTS"))
                {
                    mainMenus.add(MenuOptions.getMenu("RP_BATCH_APPROPRIATE_PAYMENT_NEW"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RP_BATCH_APPROPRIATE_PAYMENT_NEW"));
                }
                if(userPrivileges.contains("RE_ALLOCATE_PAYMENTS"))
                {
                    mainMenus.add(MenuOptions.getMenu("RP_RE_ALLOCATE_PAYMENTS"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RP_RE_ALLOCATE_PAYMENTS"));
                }
                if(userPrivileges.contains("GENERATE_SHORT_DAN"))
                {
                    mainMenus.add(MenuOptions.getMenu("RP_GENERATE_DAN_FOR_SHORT"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RP_GENERATE_DAN_FOR_SHORT"));
                }
                if(userPrivileges.contains("GENERATE_CGDAN"))
                {
                    mainMenus.add(MenuOptions.getMenu("RP_GENERATE_CGDAN"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RP_GENERATE_CGDAN"));
                }
                String loggedUserId;
                if(userPrivileges.contains("GENERATE_SFDAN"))
                {
                    mainMenus.add(MenuOptions.getMenu("RP_GENERATE_SFDAN"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RP_GENERATE_SFDAN"));
                    mainMenus.add(MenuOptions.getMenu("RP_GENERATE_SFDAN_FOR_EXPIRY"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RP_GENERATE_SFDAN_FOR_EXPIRY"));
                    loggedUserId = user.getUserId();
                    if(loggedUserId.equalsIgnoreCase("ADMIN"))
                    {
                        mainMenus.add(MenuOptions.getMenu("RP_GENERATE_BATCH_SFDAN"));
                        mainMenuValues.add(MenuOptions.getMenuAction("RP_GENERATE_BATCH_SFDAN"));
                        mainMenus.add(MenuOptions.getMenu("RP_GENERATE_SFDAN_FOR_CLAIM"));
                        mainMenuValues.add(MenuOptions.getMenuAction("RP_GENERATE_SFDAN_FOR_CLAIM"));
                    }
                }
                if(userPrivileges.contains("GENERATE_CLDAN"))
                {
                    mainMenus.add(MenuOptions.getMenu("RP_GENERATE_CLDAN"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RP_GENERATE_CLDAN"));
                }
                if(!userPrivileges.contains("GENERATE_VOUCHER"));
                if(!userPrivileges.contains("WAIVE_SHORT_AMOUNTS"));
                loggedUserId = user.getUserId();
                if(loggedUserId.equalsIgnoreCase("ADMIN") && userPrivileges.contains("PRINT_PAY_IN_SLIP"))
                {
                    mainMenus.add(MenuOptions.getMenu("RP_PRINT_PAY_IN_SLIP"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RP_PRINT_PAY_IN_SLIP"));
                }
            } else
            if(menuIcon.equals("AP"))
            {
                if(userPrivileges.contains("ADD_APPLICATION"))
                {
                    mainMenus.add(MenuOptions.getMenu("AP_GUARANTEE_FOR"));
                    mainMenuValues.add(MenuOptions.getMenuAction("AP_GUARANTEE_FOR"));
                }
                if(userPrivileges.contains("MODIFY_APPLICATION"))
                {
                    mainMenus.add(MenuOptions.getMenu("AP_MODIFY_APPLICATION"));
                    mainMenuValues.add(MenuOptions.getMenuAction("AP_MODIFY_APPLICATION"));
                }
                if(userPrivileges.contains("MODIFY_APPLICATION"))
                {
                    mainMenus.add(MenuOptions.getMenu("AP_MODIFY_BRANCHNAME"));
                    mainMenuValues.add(MenuOptions.getMenuAction("AP_MODIFY_BRANCHNAME"));
                }
                if(userPrivileges.contains("APPLICATION_APPROVAL"))
                {
                    mainMenus.add(MenuOptions.getMenu("AP_APPROVAL"));
                    mainMenuValues.add(MenuOptions.getMenuAction("AP_APPROVAL"));
                }
                if(!userPrivileges.contains("APP_SPECIAL_MESSAGE"));
                if(userPrivileges.contains("APPLICATION_RE_APPROVAL"))
                {
                    mainMenus.add(MenuOptions.getMenu("AP_RE_APPROVAL"));
                    mainMenuValues.add(MenuOptions.getMenuAction("AP_RE_APPROVAL"));
                }
                if(userPrivileges.contains("APPLICATION_RE_APPROVAL"))
                {
                    mainMenus.add(MenuOptions.getMenu("AP_REJECT_APPLICATION"));
                    mainMenuValues.add(MenuOptions.getMenuAction("AP_REJECT_APPLICATION"));
                }
                String loggedUserId = user.getUserId();
           //     if((loggedUserId.equalsIgnoreCase("DEMOUSER") || loggedUserId.equalsIgnoreCase("OFFOFFI0104") || loggedUserId.equalsIgnoreCase("AKHJALO0001") || loggedUserId.equalsIgnoreCase("SISSAHA0001") || loggedUserId.equalsIgnoreCase("NREDD0001") || loggedUserId.equalsIgnoreCase("SKUCH0001")) && userPrivileges.contains("ADD_APPLICATION"))
                System.out.println("userPrivileges "+userPrivileges);
                //
              if(userPrivileges.contains("EXCEL_UPLOAD"))
                {
                    mainMenus.add(MenuOptions.getMenu("AP_FILE_UPLOAD"));
                    mainMenuValues.add(MenuOptions.getMenuAction("AP_FILE_UPLOAD"));
                }
            } else
            if(menuIcon.equals("RI"))
            {
                if(userPrivileges.contains("LIMIT_SET_UP"))
                {
                    mainMenus.add(MenuOptions.getMenu("RI_LIMIT_SETUP"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RI_LIMIT_SETUP"));
                }
                if(userPrivileges.contains("RISK_CALCULATE_EXPOSUER"))
                {
                    mainMenus.add(MenuOptions.getMenu("RI_CALCULATE_EXPOSURE"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RI_CALCULATE_EXPOSURE"));
                }
                if(userPrivileges.contains("GENERATE_EXPOSURE_REPORT"))
                {
                    mainMenus.add(MenuOptions.getMenu("RI_GENERATE_EXPOSURE_REPORT"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RI_GENERATE_EXPOSURE_REPORT"));
                    mainMenus.add(MenuOptions.getMenu("RI_SUB_SCHEME_REPORT"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RI_SUB_SCHEME_REPORT"));
                }
                if(userPrivileges.contains("SET_UPDATE_SUB_SCHEME_PARAMS"))
                {
                    mainMenus.add(MenuOptions.getMenu("RI_SUB_SCHEME_PARAMETERS"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RI_SUB_SCHEME_PARAMETERS"));
                    mainMenus.add(MenuOptions.getMenu("RI_DEFAULT_RATE_REPORT"));
                    mainMenuValues.add(MenuOptions.getMenuAction("RI_DEFAULT_RATE_REPORT"));
                }
            } else
            if(menuIcon.equals("TC"))
            {
                if(userPrivileges.contains("FILE_UPLOAD"))
                {
                    mainMenus.add(MenuOptions.getMenu("FILE_UPLOAD"));
                    mainMenuValues.add(MenuOptions.getMenuAction("FILE_UPLOAD"));
                    mainMenus.add(MenuOptions.getMenu("DOWNLOAD_THIN_CLIENT"));
                    mainMenuValues.add(MenuOptions.getMenuAction("DOWNLOAD_THIN_CLIENT"));
                }
                mainMenus.add(MenuOptions.getMenu("DOWNLOAD_JRE"));
                mainMenuValues.add(MenuOptions.getMenuAction("DOWNLOAD_JRE"));
            } else
            if(menuIcon.equals("SC"))
            {
                if(userPrivileges.contains("SECURITIZATION"))
                {
                    mainMenus.add(MenuOptions.getMenu("SECURITIZATION"));
                    mainMenuValues.add(MenuOptions.getMenuAction("SECURITIZATION"));
                }
                if(userPrivileges.contains("ADD_DONOR_DETAILS") || userPrivileges.contains("ADD_PARTICIPATING_BANKS") || userPrivileges.contains("ADD_SSI_MEMBERS"))
                {
                    mainMenus.add(MenuOptions.getMenu("MCGS"));
                    mainMenuValues.add(MenuOptions.getMenuAction("MCGS"));
                }
            }
        }
        request.setAttribute("mainMenuValues", mainMenuValues);
        request.setAttribute("mainMenus", mainMenus);
        Log.log(4, "LoginAction", "setMainMenu", "Exited");
        return mainMenus;
    }

    public ActionForward getSubMenu(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
        throws Exception
    {
        Log.log(4, "LoginAction", "getSubMenu", "Entered");
        String mainMenu = request.getParameter("mainMenu");
        String menuIcon = request.getParameter("menuIcon");
        setSubMenu(menuIcon, mainMenu, request);
        Log.log(4, "LoginAction", "getSubMenu", "Exited");
        return mapping.findForward("success");
    }

    private void setSubMenu(String menuIcon, String mainMenu, HttpServletRequest request)
    {
        Log.log(4, "LoginAction", "setSubMenu", "Entered");
        ArrayList subMenus = new ArrayList();
        ArrayList subMenuValues = new ArrayList();
        if(menuIcon != null)
        {
            User user = getUserInformation(request);
            String bankID = user.getBankId();
            String zoneID = user.getZoneId();
            String branchID = user.getBranchId();
            ArrayList userPrivileges = user.getPrivileges();
            subMenus.add(MenuOptions.getMenu("SUB_HOME_SELECT"));
            subMenuValues.add(MenuOptions.getMenuAction("SUB_HOME_SELECT"));
            if(menuIcon.equals("IO"))
            {
                if(mainMenu.equals(MenuOptions.getMenu("IO_INWARD")))
                {
                    subMenus.add(MenuOptions.getMenu("IO_INWARD_ADD_INWARD"));
                    subMenuValues.add(MenuOptions.getMenuAction("IO_INWARD_ADD_INWARD"));
                    subMenus.add(MenuOptions.getMenu("IO_INWARD_SHOW_SUMMARY"));
                    subMenuValues.add(MenuOptions.getMenuAction("IO_INWARD_SHOW_SUMMARY"));
                } else
                if(mainMenu.equals(MenuOptions.getMenu("IO_OUTWARD")))
                {
                    subMenus.add(MenuOptions.getMenu("IO_OUTWARD_ADD_OUTWARD"));
                    subMenuValues.add(MenuOptions.getMenuAction("IO_OUTWARD_ADD_OUTWARD"));
                    subMenus.add(MenuOptions.getMenu("IO_OUTWARD_SHOW_SUMMARY"));
                    subMenuValues.add(MenuOptions.getMenuAction("IO_OUTWARD_SHOW_SUMMARY"));
                }
            } else
            if(menuIcon.equals("RS"))
            {
                if(mainMenu.equals(MenuOptions.getMenu("RS_REPORT_OF_PROPOSAL")))
                {
                    subMenus.add(MenuOptions.getMenu("RS_REPORT_OF_PROPOSAL_SIZE"));
                    subMenuValues.add(MenuOptions.getMenuAction("RS_REPORT_OF_PROPOSAL_SIZE"));
                    subMenus.add(MenuOptions.getMenu("RS_REPORT_OF_PROPOSAL_SECTOR"));
                    subMenuValues.add(MenuOptions.getMenuAction("RS_REPORT_OF_PROPOSAL_SECTOR"));
                    subMenus.add(MenuOptions.getMenu("RS_REPORT_OF_PROPOSAL_MONTHLY"));
                    subMenuValues.add(MenuOptions.getMenuAction("RS_REPORT_OF_PROPOSAL_MONTHLY"));
                }
                if(mainMenu.equals(MenuOptions.getMenu("RS_APPLICATION_DETAILS")))
                {
                    if(userPrivileges.contains("REPORTS"))
                    {
                        subMenus.add(MenuOptions.getMenu("RS_APPLICATION_DETAILS_MLI"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_APPLICATION_DETAILS_MLI"));
                        subMenus.add(MenuOptions.getMenu("RS_APPLICATION_DETAILS_MLI_RSF"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_APPLICATION_DETAILS_MLI_RSF"));
                        subMenus.add(MenuOptions.getMenu("RS_APPLICATION_DETAILS_MLI_SLAB"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_APPLICATION_DETAILS_MLI_SLAB"));
                        subMenus.add(MenuOptions.getMenu("RS_APPLICATION_DETAILS_MLI_STATE"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_APPLICATION_DETAILS_MLI_STATE"));
                        subMenus.add(MenuOptions.getMenu("RS_APPLICATION_DETAILS_PENDING"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_APPLICATION_DETAILS_PENDING"));
                        subMenus.add(MenuOptions.getMenu("RS_APPLICATION_DETAILS_STATUS"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_APPLICATION_DETAILS_STATUS"));
                        subMenus.add(MenuOptions.getMenu("RS_DC_HANDICRAFT_REPORT"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_DC_HANDICRAFT_REPORT"));
                        subMenus.add(MenuOptions.getMenu("RS_DC_HANDLOOM_REPORT"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_DC_HANDLOOM_REPORT"));
                    } else
                    {
                        subMenus.add(MenuOptions.getMenu("RS_APPLICATION_DETAILS_MLI"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_APPLICATION_DETAILS_MLI"));
                        subMenus.add(MenuOptions.getMenu("RS_APPLICATION_DETAILS_MLI_SLAB"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_APPLICATION_DETAILS_MLI_SLAB"));
                        subMenus.add(MenuOptions.getMenu("RS_APPLICATION_DETAILS_MLI_STATE"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_APPLICATION_DETAILS_MLI_STATE"));
                        subMenus.add(MenuOptions.getMenu("RS_APPLICATION_DETAILS_STATUS"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_APPLICATION_DETAILS_STATUS"));
                        subMenus.add(MenuOptions.getMenu("RS_DC_HANDICRAFT_REPORT"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_DC_HANDICRAFT_REPORT"));
                        subMenus.add(MenuOptions.getMenu("RS_DC_HANDLOOM_REPORT"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_DC_HANDLOOM_REPORT"));
                    }
                    String loggedUserId = user.getUserId();
                    if(loggedUserId.equalsIgnoreCase("ADMIN"))
                    {
                        subMenus.add(MenuOptions.getMenu("RS_APPLICATION_DETAILS_PENDING"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_APPLICATION_DETAILS_PENDING"));
                    }
                    if(!userPrivileges.contains("REPORTS"));
                }
                if(mainMenu.equals(MenuOptions.getMenu("RS_APPLICATION_RELATED_REPORT")))
                {
                    if(userPrivileges.contains("REPORTS"))
                    {
                        subMenus.add(MenuOptions.getMenu("RS_APPLICATION_REPORT"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_APPLICATION_REPORT"));
                        subMenus.add(MenuOptions.getMenu("RS_CGPAN_HISTORY_REPORT"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_CGPAN_HISTORY_REPORT"));
                        subMenus.add(MenuOptions.getMenu("RS_RECIEVED_APPLICATION_REPORT"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_RECIEVED_APPLICATION_REPORT"));
                    } else
                    {
                        subMenus.add(MenuOptions.getMenu("RS_APPLICATION_REPORT"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_APPLICATION_REPORT"));
                        subMenus.add(MenuOptions.getMenu("RS_CGPAN_HISTORY_REPORT"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_CGPAN_HISTORY_REPORT"));
                    }
                    if(userPrivileges.contains("SPECIAL_REPORTS"))
                    {
                        subMenus.add(MenuOptions.getMenu("RS_SANCTIONED_APPLICATION_REPORT"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_SANCTIONED_APPLICATION_REPORT"));
                        subMenus.add(MenuOptions.getMenu("RS_APPROVED_APPLICATION_REPORT"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_APPROVED_APPLICATION_REPORT"));
                    }
                    String loggedUserId = user.getUserId();
                    if(loggedUserId.equalsIgnoreCase("ADMIN"))
                    {
                        subMenus.add(MenuOptions.getMenu("RS_APPROVED_APPLICATION_REPORT"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_APPROVED_APPLICATION_REPORT"));
                    }
                }
                if(mainMenu.equals(MenuOptions.getMenu("RS_DAN_REPORTS-ALL")))
                    if(userPrivileges.contains("REPORTS"))
                    {
                        subMenus.add(MenuOptions.getMenu("RS_DAN_REPORT"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_DAN_REPORT"));
                        subMenus.add(MenuOptions.getMenu("RS_DAN_REPORT_FOR_ASF"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_DAN_REPORT_FOR_ASF"));
                        subMenus.add(MenuOptions.getMenu("RS_DAN_REPORT_FOR_GF"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_DAN_REPORT_FOR_GF"));
                        subMenus.add(MenuOptions.getMenu("RS_DAN_PROFORMA_REPORT_FOR_GF"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_DAN_PROFORMA_REPORT_FOR_GF"));
                        subMenus.add(MenuOptions.getMenu("RS_DAN_PROFORMA_REPORT_FOR_GFF"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_DAN_PROFORMA_REPORT_FOR_GFF"));
                        subMenus.add(MenuOptions.getMenu("RS_DAN_PROFORMA_REPORT_FOR_ASF"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_DAN_PROFORMA_REPORT_FOR_ASF"));
                        subMenus.add(MenuOptions.getMenu("RS_DAN_PROFORMA_REPORT_FOR_ASFF"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_DAN_PROFORMA_REPORT_FOR_ASFF"));
                        
                    } else
                    {
                        subMenus.add(MenuOptions.getMenu("RS_DAN_REPORT_FOR_ASF"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_DAN_REPORT_FOR_ASF"));
                        subMenus.add(MenuOptions.getMenu("RS_DAN_REPORT_FOR_GF"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_DAN_REPORT_FOR_GF"));
                        subMenus.add(MenuOptions.getMenu("RS_DAN_REPORT"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_DAN_REPORT"));
                        subMenus.add(MenuOptions.getMenu("RS_DAN_PROFORMA_REPORT_FOR_GF"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_DAN_PROFORMA_REPORT_FOR_GF"));
                        subMenus.add(MenuOptions.getMenu("RS_DAN_PROFORMA_REPORT_FOR_GFF"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_DAN_PROFORMA_REPORT_FOR_GFF"));
                        subMenus.add(MenuOptions.getMenu("RS_DAN_PROFORMA_REPORT_FOR_ASF"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_DAN_PROFORMA_REPORT_FOR_ASF"));
                        subMenus.add(MenuOptions.getMenu("RS_DAN_PROFORMA_REPORT_FOR_ASFF"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_DAN_PROFORMA_REPORT_FOR_ASFF"));
                    }
                if(mainMenu.equals(MenuOptions.getMenu("RS_MIS")))
                {
                    if(userPrivileges.contains("REPORTS"))
                    {
                        subMenus.add(MenuOptions.getMenu("RS_NER_BANK_WISE"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_NER_BANK_WISE"));
                        subMenus.add(MenuOptions.getMenu("RS_NER_STATE_WISE"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_NER_STATE_WISE"));
                        subMenus.add(MenuOptions.getMenu("RS_MINORITY_REPORT"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_MINORITY_REPORT"));
                        subMenus.add(MenuOptions.getMenu("RS_MINORITY_REPORT_STATE_WISE"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_MINORITY_REPORT_STATE_WISE"));
                        subMenus.add(MenuOptions.getMenu("RS_STATE_WISE"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_STATE_WISE"));
                        subMenus.add(MenuOptions.getMenu("RS_GUARANTEE_APPROVED"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_GUARANTEE_APPROVED"));
                        subMenus.add(MenuOptions.getMenu("RS_CATEGORY_WISE_GUARANTEE_ISSUED"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_CATEGORY_WISE_GUARANTEE_ISSUED"));
                        subMenus.add(MenuOptions.getMenu("RS_TURNOVER_AND_EXPORTS"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_TURNOVER_AND_EXPORTS"));
                        subMenus.add(MenuOptions.getMenu("RS_GF_OUTSTANDING"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_GF_OUTSTANDING"));
                    } else
                    {
                        subMenus.add(MenuOptions.getMenu("RS_APPLICATION_DETAILS_MLI_SLAB"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_APPLICATION_DETAILS_MLI_SLAB"));
                        subMenus.add(MenuOptions.getMenu("RS_GUARANTEE_APPROVED"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_GUARANTEE_APPROVED"));
                        subMenus.add(MenuOptions.getMenu("RS_STATE_WISE"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_STATE_WISE"));
                        subMenus.add(MenuOptions.getMenu("RS_MINORITY_REPORT"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_MINORITY_REPORT"));
                        subMenus.add(MenuOptions.getMenu("RS_MINORITY_REPORT_STATE_WISE"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_MINORITY_REPORT_STATE_WISE"));
                        subMenus.add(MenuOptions.getMenu("RS_CATEGORY_WISE_GUARANTEE_ISSUED"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_CATEGORY_WISE_GUARANTEE_ISSUED"));
                        subMenus.add(MenuOptions.getMenu("RS_NER_STATE_WISE"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_NER_STATE_WISE"));
                        subMenus.add(MenuOptions.getMenu("RS_NER_BANK_WISE"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_NER_BANK_WISE"));
                        subMenus.add(MenuOptions.getMenu("RS_TURNOVER_AND_EXPORTS"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_TURNOVER_AND_EXPORTS"));
                    }
                    if(userPrivileges.contains("SPECIAL_REPORTS"))
                    {
                        subMenus.add(MenuOptions.getMenu("RS_DAY_REPORT"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_DAY_REPORT"));
                    }
                    String loggedUserId = user.getUserId();
                    if(loggedUserId.equalsIgnoreCase("ADMIN"))
                    {
                        subMenus.add(MenuOptions.getMenu("RS_DAY_REPORT"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_DAY_REPORT"));
                    }
                }
                if(mainMenu.equals(MenuOptions.getMenu("RS_GM_REPORTS")))
                {
                    subMenus.add(MenuOptions.getMenu("RS_DISBURSEMENT_REPORT"));
                    subMenuValues.add(MenuOptions.getMenuAction("RS_DISBURSEMENT_REPORT"));
                    if(userPrivileges.contains("REPORTS"))
                    {
                        subMenus.add(MenuOptions.getMenu("RS_PERIODIC_INFO_REPORT"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_PERIODIC_INFO_REPORT"));
                    }
                }
                if(mainMenu.equals(MenuOptions.getMenu("RS_RP_REPORTS")))
                    if(userPrivileges.contains("REPORTS"))
                    {
                        subMenus.add(MenuOptions.getMenu("RS_GF_PAYMENT_REPORT_DAILY"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_GF_PAYMENT_REPORT_DAILY"));
                        subMenus.add(MenuOptions.getMenu("RS_PAYMENT_REPORT"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_PAYMENT_REPORT"));
                        subMenus.add(MenuOptions.getMenu("RS_GF_ALLOCATED_REPORT"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_GF_ALLOCATED_REPORT"));
                        subMenus.add(MenuOptions.getMenu("RS_ASF_PAYMENT_REPORT"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_ASF_PAYMENT_REPORT"));
                        subMenus.add(MenuOptions.getMenu("RS_ASF_ALLOCATED_REPORT"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_ASF_ALLOCATED_REPORT"));
                        subMenus.add(MenuOptions.getMenu("RS_RP_CANCELLED_ALLOC_REPORT"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_RP_CANCELLED_ALLOC_REPORT"));
                        subMenus.add(MenuOptions.getMenu("RS_RP_NOT_APPROPRITED_DETAILS"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_RP_NOT_APPROPRITED_DETAILS"));
                        subMenus.add(MenuOptions.getMenu("RS_DD_DEPOSITED_CASES_REPORT"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_DD_DEPOSITED_CASES_REPORT"));
                    } else
                    {
                        subMenus.add(MenuOptions.getMenu("RS_PAYMENT_REPORT"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_PAYMENT_REPORT"));
                        subMenus.add(MenuOptions.getMenu("RS_GF_ALLOCATED_REPORT"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_GF_ALLOCATED_REPORT"));
                        subMenus.add(MenuOptions.getMenu("RS_ASF_PAYMENT_REPORT"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_ASF_PAYMENT_REPORT"));
                        subMenus.add(MenuOptions.getMenu("RS_ASF_ALLOCATED_REPORT"));
                        subMenuValues.add(MenuOptions.getMenuAction("RS_ASF_ALLOCATED_REPORT"));
                    }
                if(mainMenu.equals(MenuOptions.getMenu("RS_SCHEM_PROPAGATION_DETAILS")) && userPrivileges.contains("REPORTS"))
                {
                    subMenus.add(MenuOptions.getMenu("RS_MLI_WORKSHOP_REPORT"));
                    subMenuValues.add(MenuOptions.getMenuAction("RS_MLI_WORKSHOP_REPORT"));
                    subMenus.add(MenuOptions.getMenu("RS_STATE_WORKSHOP_REPORT"));
                    subMenuValues.add(MenuOptions.getMenuAction("RS_STATE_WORKSHOP_REPORT"));
                    subMenus.add(MenuOptions.getMenu("RS_AGENCY_WORKSHOP_REPORT"));
                    subMenuValues.add(MenuOptions.getMenuAction("RS_AGENCY_WORKSHOP_REPORT"));
                    subMenus.add(MenuOptions.getMenu("RS_SCHEME_WORKSHOP_REPORT"));
                    subMenuValues.add(MenuOptions.getMenuAction("RS_SCHEME_WORKSHOP_REPORT"));
                    subMenus.add(MenuOptions.getMenu("RS_WORKSHOP_REPORT"));
                    subMenuValues.add(MenuOptions.getMenuAction("RS_WORKSHOP_REPORT"));
                }
                
                if(mainMenu.equals(MenuOptions.getMenu("RS_MONTH_WISE_REPORT_NEW")) && userPrivileges.contains("REPORTS"))
                {
                    subMenus.add(MenuOptions.getMenu("RS_MONTH_WISE_REPORT"));
                    subMenuValues.add(MenuOptions.getMenuAction("RS_MONTH_WISE_REPORT"));
                    
                }
                if(mainMenu.equals(MenuOptions.getMenu("RS_CLAIMS_DETAILS")))
                {
                    subMenus.add(MenuOptions.getMenu("RS_CLAIMS_DETAILS_MLI_WISE"));
                    subMenuValues.add(MenuOptions.getMenuAction("RS_CLAIMS_DETAILS_MLI_WISE"));
                    subMenus.add(MenuOptions.getMenu("RS_CLAIMS_DETAILS_STATE_WISE"));
                    subMenuValues.add(MenuOptions.getMenuAction("RS_CLAIMS_DETAILS_STATE_WISE"));
                    subMenus.add(MenuOptions.getMenu("RS_CLAIMS_DETAILS_SLAB_WISE"));
                    subMenuValues.add(MenuOptions.getMenuAction("RS_CLAIMS_DETAILS_SLAB_WISE"));
                    subMenus.add(MenuOptions.getMenu("RS_CLAIMS_DETAILS_SECTOR_WISE"));
                    subMenuValues.add(MenuOptions.getMenuAction("RS_CLAIMS_DETAILS_SECTOR_WISE"));
                    subMenus.add(MenuOptions.getMenu("RS_CLAIMS_DETAILS_STATUS_WISE"));
                    subMenuValues.add(MenuOptions.getMenuAction("RS_CLAIMS_DETAILS_STATUS_WISE"));
                    subMenus.add(MenuOptions.getMenu("CLM_SETTLED_REPORT"));
                    subMenuValues.add(MenuOptions.getMenuAction("CLM_SETTLED_REPORT"));
                    subMenus.add(MenuOptions.getMenu("RS_TCQUERY_CLAIM_DETAILS"));
                    subMenuValues.add(MenuOptions.getMenuAction("RS_TCQUERY_CLAIM_DETAILS"));
                    //
                    subMenus.add(MenuOptions.getMenu("RS_CLAIM_LODGED_DETAILS"));
                    subMenuValues.add(MenuOptions.getMenuAction("RS_CLAIM_LODGED_DETAILS"));
                    String loggedUserId = user.getUserId();
                    if(!userPrivileges.contains("REPORTS"));
                }
            } else
            if(menuIcon.equals("AD"))
            {
                if(mainMenu.equals(MenuOptions.getMenu("AD_COLLECTING_BANK")))
                {
                    if(userPrivileges.contains("REGISTER_COLLECTING_BANK"))
                    {
                        subMenus.add(MenuOptions.getMenu("AD_COLLECTING_BANK_REGISTER"));
                        subMenuValues.add(MenuOptions.getMenuAction("AD_COLLECTING_BANK_REGISTER"));
                    }
                    if(userPrivileges.contains("MODIFY_COLLECTING_BANK_DETAILS"))
                    {
                        subMenus.add(MenuOptions.getMenu("AD_COLLECTING_BANK_MODIFY"));
                        subMenuValues.add(MenuOptions.getMenuAction("AD_COLLECTING_BANK_MODIFY"));
                    }
                } else
                if(mainMenu.equals(MenuOptions.getMenu("AD_MLI")))
                {
                    if(userPrivileges.contains("REGISTER_MLI"))
                    {
                        subMenus.add(MenuOptions.getMenu("AD_MLI_REGISTER"));
                        subMenuValues.add(MenuOptions.getMenuAction("AD_MLI_REGISTER"));
                    }
                    if(userPrivileges.contains("REGISTER_MLI"))
                    {
                        subMenus.add(MenuOptions.getMenu("AD_MODIFY_MLI"));
                        subMenuValues.add(MenuOptions.getMenuAction("AD_MODIFY_MLI"));
                    }
                    if(userPrivileges.contains("ASSIGN_COLLECTING_BANK"))
                    {
                        subMenus.add(MenuOptions.getMenu("AD_MLI_ASSIGN_CB"));
                        subMenuValues.add(MenuOptions.getMenuAction("AD_MLI_ASSIGN_CB"));
                    }
                    if(userPrivileges.contains("MODIFY_COLLECTING_BANK"))
                    {
                        subMenus.add(MenuOptions.getMenu("AD_MLI_MODIFY_COLLECTING_BANK"));
                        subMenuValues.add(MenuOptions.getMenuAction("AD_MLI_MODIFY_COLLECTING_BANK"));
                    }
                    if(userPrivileges.contains("MODIFY_COLLECTING_BANK"))
                    {
                        subMenus.add(MenuOptions.getMenu("AD_MLI_MODIFY_EXPOSURE_LIMITS"));
                        subMenuValues.add(MenuOptions.getMenuAction("AD_MLI_MODIFY_EXPOSURE_LIMITS"));
                    }
                    if(userPrivileges.contains("DEFINE_ORGANIZATION_STRUCTURE"))
                    {
                        subMenus.add(MenuOptions.getMenu("AD_MLI_DEFINE_ORG_STRUCTURE"));
                        subMenuValues.add(MenuOptions.getMenuAction("AD_MLI_DEFINE_ORG_STRUCTURE"));
                    }
                    if(userPrivileges.contains("DEACTIVATE_MEMBER"))
                    {
                        subMenus.add(MenuOptions.getMenu("AD_MLI_DEACTIVATE_MEMBER"));
                        subMenuValues.add(MenuOptions.getMenuAction("AD_MLI_DEACTIVATE_MEMBER"));
                    }
                    if(userPrivileges.contains("ACTIVATE_MEMBER"))
                    {
                        subMenus.add(MenuOptions.getMenu("AD_MLI_REACTIVATE_MEMBER"));
                        subMenuValues.add(MenuOptions.getMenuAction("AD_MLI_REACTIVATE_MEMBER"));
                    }
                } else
                if(mainMenu.equals(MenuOptions.getMenu("AD_ROLES_MGMT")))
                {
                    if(userPrivileges.contains("ADD_MODIFY_ROLES"))
                    {
                        subMenus.add(MenuOptions.getMenu("AD_ROLES_MGMT_ADD_ROLE"));
                        subMenuValues.add(MenuOptions.getMenuAction("AD_ROLES_MGMT_ADD_ROLE"));
                        subMenus.add(MenuOptions.getMenu("AD_ROLES_MGMT_MODIFY_ROLE"));
                        subMenuValues.add(MenuOptions.getMenuAction("AD_ROLES_MGMT_MODIFY_ROLE"));
                    }
                } else
                if(mainMenu.equals(MenuOptions.getMenu("AD_USER_MGMT")))
                {
                    if(userPrivileges.contains("CREATE_CGTSI_USER"))
                    {
                        subMenus.add(MenuOptions.getMenu("AD_USER_MGMT_CREATE_CGTSI_USER"));
                        subMenuValues.add(MenuOptions.getMenuAction("AD_USER_MGMT_CREATE_CGTSI_USER"));
                    }
                    if(userPrivileges.contains("CREATE_MLI_USER"))
                    {
                        subMenus.add(MenuOptions.getMenu("AD_USER_MGMT_CREATE_MLI_USER"));
                        subMenuValues.add(MenuOptions.getMenuAction("AD_USER_MGMT_CREATE_MLI_USER"));
                    }
                    if(userPrivileges.contains("MODIFY_USER_DETAILS"))
                    {
                        subMenus.add(MenuOptions.getMenu("AD_USER_MGMT_MODIFY_USER_DETAILS"));
                        subMenuValues.add(MenuOptions.getMenuAction("AD_USER_MGMT_MODIFY_USER_DETAILS"));
                    }
                    if(userPrivileges.contains("DEACTIVATE_USER"))
                    {
                        subMenus.add(MenuOptions.getMenu("AD_USER_MGMT_DEACTIVATE_USER"));
                        subMenuValues.add(MenuOptions.getMenuAction("AD_USER_MGMT_DEACTIVATE_USER"));
                    }
                    if(userPrivileges.contains("REACTIVATE_USER"))
                    {
                        subMenus.add(MenuOptions.getMenu("AD_USER_MGMT_REACTIVATE_USER"));
                        subMenuValues.add(MenuOptions.getMenuAction("AD_USER_MGMT_REACTIVATE_USER"));
                    }
                    if(userPrivileges.contains("ASSIGN_MODIFY_ROLES_AND_PRIVILEGES"))
                    {
                        subMenus.add(MenuOptions.getMenu("AD_USER_MGMT_ASSIGN_ROLES"));
                        subMenuValues.add(MenuOptions.getMenuAction("AD_USER_MGMT_ASSIGN_ROLES"));
                        subMenus.add(MenuOptions.getMenu("AD_USER_MGMT_MODIFY_ROLES"));
                        subMenuValues.add(MenuOptions.getMenuAction("AD_USER_MGMT_MODIFY_ROLES"));
                    }
                } else
                if(mainMenu.equals(MenuOptions.getMenu("AD_PASSWORD_MGMT")))
                {
                    subMenus.add(MenuOptions.getMenu("AD_PASSWORD_MGMT_CHANGE_PASSWORD"));
                    subMenuValues.add(MenuOptions.getMenuAction("AD_PASSWORD_MGMT_CHANGE_PASSWORD"));
                    if(userPrivileges.contains("RESET_PASSWORD"))
                    {
                        subMenus.add(MenuOptions.getMenu("AD_PASSWORD_MGMT_RESET_PASSWORD"));
                        subMenuValues.add(MenuOptions.getMenuAction("AD_PASSWORD_MGMT_RESET_PASSWORD"));
                    }
                    subMenus.add(MenuOptions.getMenu("AD_PASSWORD_MGMT_CHANGE_HINT"));
                    subMenuValues.add(MenuOptions.getMenuAction("AD_PASSWORD_MGMT_CHANGE_HINT"));
                } else
                if(mainMenu.equals(MenuOptions.getMenu("AD_AUDIT")))
                {
                    if(userPrivileges.contains("ENTER_AUDIT_DETAILS"))
                    {
                        subMenus.add(MenuOptions.getMenu("AD_AUDIT_ENTER"));
                        subMenuValues.add(MenuOptions.getMenuAction("AD_AUDIT_ENTER"));
                    }
                    if(userPrivileges.contains("REVIEW_AUDIT_DETAILS"))
                    {
                        subMenus.add(MenuOptions.getMenu("AD_AUDIT_REVIEW"));
                        subMenuValues.add(MenuOptions.getMenuAction("AD_AUDIT_REVIEW"));
                    }
                } else
                if(mainMenu.equals(MenuOptions.getMenu("AD_UPDATE_MASTER")) && userPrivileges.contains("UPDATE_MASTER_TABLE"))
                {
                    subMenus.add(MenuOptions.getMenu("AD_UPDATE_MASTER_STATE"));
                    subMenuValues.add(MenuOptions.getMenuAction("AD_UPDATE_MASTER_STATE"));
                    subMenus.add(MenuOptions.getMenu("AD_UPDATE_MASTER_DISTRICT"));
                    subMenuValues.add(MenuOptions.getMenuAction("AD_UPDATE_MASTER_DISTRICT"));
                    subMenus.add(MenuOptions.getMenu("AD_UPDATE_MASTER_STATE_MOD"));
                    subMenuValues.add(MenuOptions.getMenuAction("AD_UPDATE_MASTER_STATE_MOD"));
                    subMenus.add(MenuOptions.getMenu("AD_UPDATE_MASTER_DISTRICT_MOD"));
                    subMenuValues.add(MenuOptions.getMenuAction("AD_UPDATE_MASTER_DISTRICT_MOD"));
                    subMenus.add(MenuOptions.getMenu("AD_UPDATE_MASTER_ALERTS"));
                    subMenuValues.add(MenuOptions.getMenuAction("AD_UPDATE_MASTER_ALERTS"));
                    subMenus.add(MenuOptions.getMenu("AD_UPDATE_MASTER_DESIGNATION"));
                    subMenuValues.add(MenuOptions.getMenuAction("AD_UPDATE_MASTER_DESIGNATION"));
                    subMenus.add(MenuOptions.getMenu("AD_UPDATE_MASTER_PARAMETER"));
                    subMenuValues.add(MenuOptions.getMenuAction("AD_UPDATE_MASTER_PARAMETER"));
                    subMenus.add(MenuOptions.getMenu("AD_UPDATE_MASTER_PLR_INSERT"));
                    subMenuValues.add(MenuOptions.getMenuAction("AD_UPDATE_MASTER_PLR_INSERT"));
                    subMenus.add(MenuOptions.getMenu("AD_UPDATE_MASTER_PLR_MODIFY"));
                    subMenuValues.add(MenuOptions.getMenuAction("AD_UPDATE_MASTER_PLR_MODIFY"));
                }
            } else
            if(menuIcon.equals("AP"))
            {
                if(mainMenu.equals(MenuOptions.getMenu("AP_GUARANTEE_FOR")))
                {
                    subMenus.add(MenuOptions.getMenu("AP_GF_TERM_LOAN"));
                    subMenuValues.add(MenuOptions.getMenuAction("AP_GF_TERM_LOAN"));
                    subMenus.add(MenuOptions.getMenu("AP_GF_COMPOSITE_LOAN"));
                    subMenuValues.add(MenuOptions.getMenuAction("AP_GF_COMPOSITE_LOAN"));
                    subMenus.add(MenuOptions.getMenu("AP_GF_BOTH"));
                    subMenuValues.add(MenuOptions.getMenuAction("AP_GF_BOTH"));
                    subMenus.add(MenuOptions.getMenu("AP_GF_ADDL_TERM_LOAN"));
                    subMenuValues.add(MenuOptions.getMenuAction("AP_GF_ADDL_TERM_LOAN"));
                    subMenus.add(MenuOptions.getMenu("AP_GF_WC_ENHANCEMENT"));
                    subMenuValues.add(MenuOptions.getMenuAction("AP_GF_WC_ENHANCEMENT"));
                    subMenus.add(MenuOptions.getMenu("AP_GF_WC_RENEWAL"));
                    subMenuValues.add(MenuOptions.getMenuAction("AP_GF_WC_RENEWAL"));
                    subMenus.add(MenuOptions.getMenu("AP_GF_RSF2_WC_LOAN"));
                    subMenuValues.add(MenuOptions.getMenuAction("AP_GF_RSF2_WC_LOAN"));
                    subMenus.add(MenuOptions.getMenu("AP_GF_RSF2_LOAN"));
                    subMenuValues.add(MenuOptions.getMenuAction("AP_GF_RSF2_LOAN"));
                    subMenus.add(MenuOptions.getMenu("AP_RSF2_BOTH"));
                    subMenuValues.add(MenuOptions.getMenuAction("AP_RSF2_BOTH"));
                } else
                if(mainMenu.equals(MenuOptions.getMenu("AP_APPROVAL")))
                {
                    subMenus.add(MenuOptions.getMenu("AP_APPROVAL_APPROVE"));
                    subMenuValues.add(MenuOptions.getMenuAction("AP_APPROVAL_APPROVE"));
                    subMenus.add(MenuOptions.getMenu("AP_APPROVAL_APPROVE_NEW"));
                    subMenuValues.add(MenuOptions.getMenuAction("AP_APPROVAL_APPROVE_NEW"));
                } else
                if(mainMenu.equals(MenuOptions.getMenu("AP_APPROVAL_SPECIAL_MESSAGE")))
                {
                    subMenus.add(MenuOptions.getMenu("AP_APPROVAL_SPECIAL_MESSAGE_INSERT"));
                    subMenuValues.add(MenuOptions.getMenuAction("AP_APPROVAL_SPECIAL_MESSAGE_INSERT"));
                    subMenus.add(MenuOptions.getMenu("AP_APPROVAL_SPECIAL_MESSAGE_UPDATE"));
                    subMenuValues.add(MenuOptions.getMenuAction("AP_APPROVAL_SPECIAL_MESSAGE_UPDATE"));
                } else
                if(mainMenu.equals(MenuOptions.getMenu("AP_CONVERSION")))
                {
                    subMenus.add(MenuOptions.getMenu("AP_CONVERSION_TC"));
                    subMenuValues.add(MenuOptions.getMenuAction("AP_CONVERSION_TC"));
                    subMenus.add(MenuOptions.getMenu("AP_CONVERSION_WC"));
                    subMenuValues.add(MenuOptions.getMenuAction("AP_CONVERSION_WC"));
                }
            } else
            if(menuIcon.equals("RI"))
            {
                if(mainMenu.equals(MenuOptions.getMenu("RI_LIMIT_SETUP")))
                {
                    subMenus.add(MenuOptions.getMenu("RI_LIMIT_SETUP_GLOBAL"));
                    subMenuValues.add(MenuOptions.getMenuAction("RI_LIMIT_SETUP_GLOBAL"));
                    subMenus.add(MenuOptions.getMenu("RI_LIMIT_SETUP_USER"));
                    subMenuValues.add(MenuOptions.getMenuAction("RI_LIMIT_SETUP_USER"));
                    subMenus.add(MenuOptions.getMenu("RI_LIMIT_SETUP_PARTICIPATING_BANK"));
                    subMenuValues.add(MenuOptions.getMenuAction("RI_LIMIT_SETUP_PARTICIPATING_BANK"));
                }
            } else
            if(menuIcon.equals("CP"))
            {
                if(mainMenu.equals(MenuOptions.getMenu("CP_CLAIM_FOR")))
                {
                    if(userPrivileges.contains("CLAIM_FIRST_INSTALLMENT"))
                    {
                        subMenus.add(MenuOptions.getMenu("CP_CLAIM_FOR_FIRST_INSTALLMENT"));
                        subMenuValues.add(MenuOptions.getMenuAction("CP_CLAIM_FOR_FIRST_INSTALLMENT"));
                    }
                    if(userPrivileges.contains("CLAIM_SECOND_INSTALLMENT"))
                    {
                        subMenus.add(MenuOptions.getMenu("CP_CLAIM_FOR_SECOND_INSTALLMENT"));
                        subMenuValues.add(MenuOptions.getMenuAction("CP_CLAIM_FOR_SECOND_INSTALLMENT"));
                    }
                    subMenus.add(MenuOptions.getMenu("CP_UPDATE_RT_CLAIM"));
                    subMenuValues.add(MenuOptions.getMenuAction("CP_UPDATE_RT_CLAIM"));
                    subMenus.add(MenuOptions.getMenu("CP_UPDATE_TC_CLAIM"));
                    subMenuValues.add(MenuOptions.getMenuAction("CP_UPDATE_TC_CLAIM"));
                   //String bankID = user.getBankId();
                    if("0029".equals(bankID) || "0056".equals(bankID) || "0035".equals(bankID) || "0082".equals(bankID) || "0024".equals(bankID) || "0001".equals(bankID) || "0000".equals(bankID) || "0103".equals(bankID) || "0093".equals(bankID))
                    {
                        subMenus.add(MenuOptions.getMenu("CP_UPLOAD_FILES"));
                        subMenuValues.add(MenuOptions.getMenuAction("CP_UPLOAD_FILES"));
                    }
                    // 26/05/2017
                    subMenus.add(MenuOptions.getMenu("CP_RESUBMISSION_DU"));
                    subMenuValues.add(MenuOptions.getMenuAction("CP_RESUBMISSION_DU"));
                } else
                if(mainMenu.equals(MenuOptions.getMenu("CP_APPROVAL_NEW")))
                {
                    if(userPrivileges.contains("CLAIM_APPROVAL"))
                    {
                        subMenus.add(MenuOptions.getMenu("CP_APPROVAL_MODIFIED"));
                        subMenuValues.add(MenuOptions.getMenuAction("CP_APPROVAL_MODIFIED"));
                    }
                } else
                if(mainMenu.equals(MenuOptions.getMenu("CP_OTS")))
                {
                    if(userPrivileges.contains("OTS_REQUEST"))
                    {
                        subMenus.add(MenuOptions.getMenu("CP_OTS_REQUEST"));
                        subMenuValues.add(MenuOptions.getMenuAction("CP_OTS_REQUEST"));
                    }
                    if(userPrivileges.contains("OTS_APPROVAL"))
                    {
                        subMenus.add(MenuOptions.getMenu("CP_OTS_CONSENT"));
                        subMenuValues.add(MenuOptions.getMenuAction("CP_OTS_CONSENT"));
                    }
                } else
                if(mainMenu.equals(MenuOptions.getMenu("CP_EXPORT_CLAIM_FILE")))
                {
                    subMenus.add(MenuOptions.getMenu("CP_EXPORT_CLAIM_FILE_FIRST_INSTALLMNT"));
                    subMenuValues.add(MenuOptions.getMenuAction("CP_EXPORT_CLAIM_FILE_FIRST_INSTALLMNT"));
                    subMenus.add(MenuOptions.getMenu("CP_EXPORT_CLAIM_FILE_SECOND_INSTALLMNT"));
                    subMenuValues.add(MenuOptions.getMenuAction("CP_EXPORT_CLAIM_FILE_SECOND_INSTALLMNT"));
                }
            } else
            if(menuIcon.equals("GM"))
            {
            	
            	if(mainMenu.equals(MenuOptions.getMenu("GM_OUTSTANDING_REQUEST_MENU")))
                {
            		// subMenus.add(MenuOptions.getMenu("GM_OUTSTANDING_REQUEST_ENTRY"));
            	     //subMenuValues.add(MenuOptions.getMenuAction("GM_OUTSTANDING_REQUEST_ENTRY"));
            		subMenus.add(MenuOptions.getMenu("GM_OUTSTANDING_EDIT"));
           	        subMenuValues.add(MenuOptions.getMenuAction("GM_OUTSTANDING_EDIT"));
            		 subMenus.add(MenuOptions.getMenu("GM_OUTSTANDING_REQUEST_MODIFY"));
            	     subMenuValues.add(MenuOptions.getMenuAction("GM_OUTSTANDING_REQUEST_MODIFY"));
            		 subMenus.add(MenuOptions.getMenu("GM_OUTSTANDING_REQUEST_UPLOAD"));
            	     subMenuValues.add(MenuOptions.getMenuAction("GM_OUTSTANDING_REQUEST_UPLOAD"));
            	     
            		
                }
                if(mainMenu.equals(MenuOptions.getMenu("GM_PERIODIC_INFO")))
                {
                    subMenus.add(MenuOptions.getMenu("GM_PI_OUTSTANDING_DETAILS"));
                    subMenuValues.add(MenuOptions.getMenuAction("GM_PI_OUTSTANDING_DETAILS"));
                    subMenus.add(MenuOptions.getMenu("GM_PI_DISBURSEMENT_DETAILS"));
                    subMenuValues.add(MenuOptions.getMenuAction("GM_PI_DISBURSEMENT_DETAILS"));
                    subMenus.add(MenuOptions.getMenu("GM_PI_REPAYMENT_DETAILS"));
                    subMenuValues.add(MenuOptions.getMenuAction("GM_PI_REPAYMENT_DETAILS"));
                    subMenus.add(MenuOptions.getMenu("GM_PI_NPA_DETAILS"));
                    subMenuValues.add(MenuOptions.getMenuAction("GM_PI_NPA_DETAILS"));
                    subMenus.add(MenuOptions.getMenu("GM_PI_NPA_DETAILS1"));
                    subMenuValues.add(MenuOptions.getMenuAction("GM_PI_NPA_DETAILS1"));
                    subMenus.add(MenuOptions.getMenu("GM_PI_NPA_DETAILS2"));
                    subMenuValues.add(MenuOptions.getMenuAction("GM_PI_NPA_DETAILS2"));
                    subMenus.add(MenuOptions.getMenu("GM_PI_NPA_DETAILS3"));
                    subMenuValues.add(MenuOptions.getMenuAction("GM_PI_NPA_DETAILS3"));
                    subMenus.add(MenuOptions.getMenu("GM_PI_RECOVERY_DETAILS"));
                    subMenuValues.add(MenuOptions.getMenuAction("GM_PI_RECOVERY_DETAILS"));
                    if(userPrivileges.contains("UPDATE_RECOVERY"))
                    {
                        subMenus.add(MenuOptions.getMenu("GM_PI_UPDATE_RECOVERY_DETAILS"));
                        subMenuValues.add(MenuOptions.getMenuAction("GM_PI_UPDATE_RECOVERY_DETAILS"));
                    }
                }
            } else
            if(menuIcon.equals("RP"))
            {
                if(mainMenu.equals(MenuOptions.getMenu("RP_VIEW_PENDING_PAYMENTS")))
                {
                    subMenus.add(MenuOptions.getMenu("RP_VIEW_PP_MLI_WISE"));
                    subMenuValues.add(MenuOptions.getMenuAction("RP_VIEW_PP_MLI_WISE"));
                    subMenus.add(MenuOptions.getMenu("RP_VIEW_PP_DAN_DATE_WISE"));
                    subMenuValues.add(MenuOptions.getMenuAction("RP_VIEW_PP_DAN_DATE_WISE"));
                }
                if(mainMenu.equals(MenuOptions.getMenu("RP_ALLOCATE_PAYMENTS")))
                {
                   //   subMenus.add(MenuOptions.getMenu("RP_GUARANTEE_FEE"));
                   //  subMenuValues.add(MenuOptions.getMenuAction("RP_GUARANTEE_FEE"));
                    subMenus.add(MenuOptions.getMenu("RP_LIVE_AF"));
                    subMenuValues.add(MenuOptions.getMenuAction("RP_LIVE_AF"));
                    subMenus.add(MenuOptions.getMenu("RP_EXPIRED_AF"));
                    subMenuValues.add(MenuOptions.getMenuAction("RP_EXPIRED_AF"));
                    subMenus.add(MenuOptions.getMenu("RP_LIVE_ASF"));
                    subMenuValues.add(MenuOptions.getMenuAction("RP_LIVE_ASF"));
                    subMenus.add(MenuOptions.getMenu("RP_EXPIRED_ASF"));
                    subMenuValues.add(MenuOptions.getMenuAction("RP_EXPIRED_ASF"));
                }
                // 26/05/2017
            
            if(mainMenu.equals(MenuOptions.getMenu("RP_ALLOCATE_PAYMENTS_ONLINEMENU")))
            {
                subMenus.add(MenuOptions.getMenu("RP_ALLOCATE_PAYMENTS_ONLINE"));
                subMenuValues.add(MenuOptions.getMenuAction("RP_ALLOCATE_PAYMENTS_ONLINE"));
                subMenus.add(MenuOptions.getMenu("RP_ALLOCATE_PAYMENTS_ONLINE_MODIFYORCANCEL"));
                subMenuValues.add(MenuOptions.getMenuAction("RP_ALLOCATE_PAYMENTS_ONLINE_MODIFYORCANCEL"));
                subMenus.add(MenuOptions.getMenu("RP_ALLOCATE_PAYMENTS_ONLINE_FINAL"));
                subMenuValues.add(MenuOptions.getMenuAction("RP_ALLOCATE_PAYMENTS_ONLINE_FINAL"));
            }
            if(mainMenu.equals(MenuOptions.getMenu("ASF_PAYMENT_THROUGH_NEFT_OR_RTGS")))
                if(zoneID.equals("0000") && branchID.equals("0000"))
                {
                    subMenus.add(MenuOptions.getMenu("ASF_ONLINE_EXPIRED_SF_FINAL"));
                    subMenuValues.add(MenuOptions.getMenuAction("ASF_ONLINE_EXPIRED_SF_FINAL"));
                } else
                {
                    subMenus.add(MenuOptions.getMenu("ASF_ONLINE_EXPIRED_SF_FINAL"));
                    subMenuValues.add(MenuOptions.getMenuAction("ASF_ONLINE_EXPIRED_SF_FINAL"));
                    subMenus.add(MenuOptions.getMenu("ASF_ONLINE_LIVE_AF"));
                    subMenuValues.add(MenuOptions.getMenuAction("ASF_ONLINE_LIVE_AF"));
                    subMenus.add(MenuOptions.getMenu("ASF_ONLINE_EXPIRED_AF"));
                    subMenuValues.add(MenuOptions.getMenuAction("ASF_ONLINE_EXPIRED_AF"));
                    subMenus.add(MenuOptions.getMenu("ASF_ONLINE_LIVE_SF"));
                    subMenuValues.add(MenuOptions.getMenuAction("ASF_ONLINE_LIVE_SF"));
                    subMenus.add(MenuOptions.getMenu("ASF_ONLINE_EXPIRED_SF"));
                    subMenuValues.add(MenuOptions.getMenuAction("ASF_ONLINE_EXPIRED_SF"));
                    subMenus.add(MenuOptions.getMenu("ASF_ONLINE_EXPIRED_SF_MODIFYORCANCEL"));
                    subMenuValues.add(MenuOptions.getMenuAction("ASF_ONLINE_EXPIRED_SF_MODIFYORCANCEL"));
                }
                  //26/05/2017 end
                if(mainMenu.equals(MenuOptions.getMenu("RP_APPROPRIATE_PAYMENTS")))
                {
                    subMenus.add(MenuOptions.getMenu("RP_APPROPRIATE_PAYMENTS_FOR_GF"));
                    subMenuValues.add(MenuOptions.getMenuAction("RP_APPROPRIATE_PAYMENTS_FOR_GF"));
                    subMenus.add(MenuOptions.getMenu("RP_APPROPRIATE_PAYMENTS_FOR_ASF"));
                    subMenuValues.add(MenuOptions.getMenuAction("RP_APPROPRIATE_PAYMENTS_FOR_ASF"));
                    subMenus.add(MenuOptions.getMenu("RP_APPROPRIATE_PAYMENTS_FOR_CLAIM"));
                    subMenuValues.add(MenuOptions.getMenuAction("RP_APPROPRIATE_PAYMENTS_FOR_CLAIM"));
                    subMenus.add(MenuOptions.getMenu("RP_APPROPRIATE_PAYMENTS_FOR_ALL"));
                    subMenuValues.add(MenuOptions.getMenuAction("RP_APPROPRIATE_PAYMENTS_FOR_ALL"));
                }
                if(!mainMenu.equals(MenuOptions.getMenu("RP_GENERATE_VOUCHER")));
            } else
            if(menuIcon.equals("TC"))
            {
                if(mainMenu.equals(MenuOptions.getMenu("FILE_UPLOAD")))
                {
                    subMenus.add(MenuOptions.getMenu("THIN_CLIENT_AP_FILE_UPLOAD"));
                    subMenuValues.add(MenuOptions.getMenuAction("THIN_CLIENT_AP_FILE_UPLOAD"));
                    subMenus.add(MenuOptions.getMenu("THIN_CLIENT_GM_FILE_UPLOAD"));
                    subMenuValues.add(MenuOptions.getMenuAction("THIN_CLIENT_GM_FILE_UPLOAD"));
                    subMenus.add(MenuOptions.getMenu("THIN_CLIENT_CP_FILE_UPLOAD"));
                    subMenuValues.add(MenuOptions.getMenuAction("THIN_CLIENT_CP_FILE_UPLOAD"));
                }
            } else
            if(menuIcon.equals("SC"))
            {
                if(mainMenu.equals(MenuOptions.getMenu("SECURITIZATION")))
                {
                    subMenus.add(MenuOptions.getMenu("SC_SELECT_QUERY"));
                    subMenuValues.add(MenuOptions.getMenuAction("SC_SELECT_QUERY"));
                    subMenus.add(MenuOptions.getMenu("SC_ADD_INVESTOR"));
                    subMenuValues.add(MenuOptions.getMenuAction("SC_ADD_INVESTOR"));
                } else
                if(mainMenu.equals(MenuOptions.getMenu("MCGS")))
                {
                    subMenus.add(MenuOptions.getMenu("MCGF_ADD_DONOR_DETAILS"));
                    subMenuValues.add(MenuOptions.getMenuAction("MCGF_ADD_DONOR_DETAILS"));
                    subMenus.add(MenuOptions.getMenu("MCGF_ADD_PARTICIPATING_BANK"));
                    subMenuValues.add(MenuOptions.getMenuAction("MCGF_ADD_PARTICIPATING_BANK"));
                    subMenus.add(MenuOptions.getMenu("MCGF_ADD_SSI_MEMBERS"));
                    subMenuValues.add(MenuOptions.getMenuAction("MCGF_ADD_SSI_MEMBERS"));
                    subMenus.add(MenuOptions.getMenu("MCGF_DEFAULT"));
                    subMenuValues.add(MenuOptions.getMenuAction("MCGF_DEFAULT"));
                }
            } else
            if(menuIcon.equals("IF"))
                if(mainMenu.equals(MenuOptions.getMenu("IF_BUDGET_DETAILS")))
                {
                    subMenus.add(MenuOptions.getMenu("IF_BUDGET_INFLOW"));
                    subMenuValues.add(MenuOptions.getMenuAction("IF_BUDGET_INFLOW"));
                    subMenus.add(MenuOptions.getMenu("IF_BUDGET_OUTFLOW"));
                    subMenuValues.add(MenuOptions.getMenuAction("IF_BUDGET_OUTFLOW"));
                } else
                if(mainMenu.equals(MenuOptions.getMenu("IF_INVESTMENT_DETAILS")))
                {
                    subMenus.add(MenuOptions.getMenu("IF_INVESTMENT_ENTRY"));
                    subMenuValues.add(MenuOptions.getMenuAction("IF_INVESTMENT_ENTRY"));
                    subMenus.add(MenuOptions.getMenu("IF_INVESTMENT_FD"));
                    subMenuValues.add(MenuOptions.getMenuAction("IF_INVESTMENT_FD"));
                    subMenus.add(MenuOptions.getMenu("IF_INVESTMENT_BONDS"));
                    subMenuValues.add(MenuOptions.getMenuAction("IF_INVESTMENT_BONDS"));
                    subMenus.add(MenuOptions.getMenu("IF_INVESTMENT_DEBENTURE"));
                    subMenuValues.add(MenuOptions.getMenuAction("IF_INVESTMENT_DEBENTURE"));
                    subMenus.add(MenuOptions.getMenu("IF_INVESTMENT_GS"));
                    subMenuValues.add(MenuOptions.getMenuAction("IF_INVESTMENT_GS"));
                    subMenus.add(MenuOptions.getMenu("IF_INVESTMENT_MF"));
                    subMenuValues.add(MenuOptions.getMenuAction("IF_INVESTMENT_MF"));
                    subMenus.add(MenuOptions.getMenu("IF_INVESTMENT_CP"));
                    subMenuValues.add(MenuOptions.getMenuAction("IF_INVESTMENT_CP"));
                } else
                if(mainMenu.equals(MenuOptions.getMenu("IF_UPDATE_MASTER_TABLE")))
                {
                    subMenus.add(MenuOptions.getMenu("IF_UPDATE_MASTER_HOLIDAY"));
                    subMenuValues.add(MenuOptions.getMenuAction("IF_UPDATE_MASTER_HOLIDAY"));
                    subMenus.add(MenuOptions.getMenu("IF_UPDATE_MASTER_INVESTEE_GROUP"));
                    subMenuValues.add(MenuOptions.getMenuAction("IF_UPDATE_MASTER_INVESTEE_GROUP"));
                    subMenus.add(MenuOptions.getMenu("IF_UPDATE_MASTER_INVESTEE"));
                    subMenuValues.add(MenuOptions.getMenuAction("IF_UPDATE_MASTER_INVESTEE"));
                    subMenus.add(MenuOptions.getMenu("IF_UPDATE_MASTER_MATURITY"));
                    subMenuValues.add(MenuOptions.getMenuAction("IF_UPDATE_MASTER_MATURITY"));
                    subMenus.add(MenuOptions.getMenu("IF_UPDATE_MASTER_BUDGET_HEADS"));
                    subMenuValues.add(MenuOptions.getMenuAction("IF_UPDATE_MASTER_BUDGET_HEADS"));
                    subMenus.add(MenuOptions.getMenu("IF_UPDATE_MASTER_BUDGET_SUB_HEADS"));
                    subMenuValues.add(MenuOptions.getMenuAction("IF_UPDATE_MASTER_BUDGET_SUB_HEADS"));
                    subMenus.add(MenuOptions.getMenu("IF_UPDATE_MASTER_INSTRUMENT"));
                    subMenuValues.add(MenuOptions.getMenuAction("IF_UPDATE_MASTER_INSTRUMENT"));
                    subMenus.add(MenuOptions.getMenu("IF_UPDATE_MASTER_INSTRUMENT_FEATURE"));
                    subMenuValues.add(MenuOptions.getMenuAction("IF_UPDATE_MASTER_INSTRUMENT_FEATURE"));
                    subMenus.add(MenuOptions.getMenu("IF_UPDATE_MASTER_INSTRUMENT_SCHEME"));
                    subMenuValues.add(MenuOptions.getMenuAction("IF_UPDATE_MASTER_INSTRUMENT_SCHEME"));
                    subMenus.add(MenuOptions.getMenu("IF_UPDATE_MASTER_RATING"));
                    subMenuValues.add(MenuOptions.getMenuAction("IF_UPDATE_MASTER_RATING"));
                    subMenus.add(MenuOptions.getMenu("IF_INSERT_MASTER_CORPUS"));
                    subMenuValues.add(MenuOptions.getMenuAction("IF_INSERT_MASTER_CORPUS"));
                    subMenus.add(MenuOptions.getMenu("IF_UPDATE_MASTER_CORPUS"));
                    subMenuValues.add(MenuOptions.getMenuAction("IF_UPDATE_MASTER_CORPUS"));
                    subMenus.add(MenuOptions.getMenu("IF_UPDATE_BANK_ACCOUNT_MASTER"));
                    subMenuValues.add(MenuOptions.getMenuAction("IF_UPDATE_BANK_ACCOUNT_MASTER"));
                    subMenus.add(MenuOptions.getMenu("IF_UPDATE_INSTRUMENT_CATEGORY"));
                    subMenuValues.add(MenuOptions.getMenuAction("IF_UPDATE_INSTRUMENT_CATEGORY"));
                    subMenus.add(MenuOptions.getMenu("IF_UPDATE_MASTER_AGENCY"));
                    subMenuValues.add(MenuOptions.getMenuAction("IF_UPDATE_MASTER_AGENCY"));
                    subMenus.add(MenuOptions.getMenu("IF_UPDATE_MASTER_RATINGS_AND_AGENCY"));
                    subMenuValues.add(MenuOptions.getMenuAction("IF_UPDATE_MASTER_RATINGS_AND_AGENCY"));
                } else
                if(mainMenu.equals(MenuOptions.getMenu("IF_SET_INVESTMENT_CEILING")))
                {
                    subMenus.add(MenuOptions.getMenu("IF_SET_INVESTMENT_CEILING_MATURITY"));
                    subMenuValues.add(MenuOptions.getMenuAction("IF_SET_INVESTMENT_CEILING_MATURITY"));
                    subMenus.add(MenuOptions.getMenu("IF_SET_INVESTMENT_CEILING_INSTRUMENT"));
                    subMenuValues.add(MenuOptions.getMenuAction("IF_SET_INVESTMENT_CEILING_INSTRUMENT"));
                    subMenus.add(MenuOptions.getMenu("IF_SET_INVESTMENT_CEILING_INVESTEE_GROUP"));
                    subMenuValues.add(MenuOptions.getMenuAction("IF_SET_INVESTMENT_CEILING_INVESTEE_GROUP"));
                    subMenus.add(MenuOptions.getMenu("IF_SET_INVESTMENT_CEILING_INVESTEE"));
                    subMenuValues.add(MenuOptions.getMenuAction("IF_SET_INVESTMENT_CEILING_INVESTEE"));
                    subMenus.add(MenuOptions.getMenu("RATING"));
                    subMenuValues.add(MenuOptions.getMenuAction("RATING"));
                } else
                if(!mainMenu.equals(MenuOptions.getMenu("IF_PROJECT_EXPECTED_CLAIMS")))
                    if(mainMenu.equals(MenuOptions.getMenu("IF_REPORT")))
                    {
                        subMenus.add(MenuOptions.getMenu("IF_STATEMENT_DETAIL_REPORT"));
                        subMenuValues.add(MenuOptions.getMenuAction("IF_STATEMENT_DETAIL_REPORT"));
                        subMenus.add(MenuOptions.getMenu("IF_FDI_REPORT"));
                        subMenuValues.add(MenuOptions.getMenuAction("IF_FDI_REPORT"));
                        subMenus.add(MenuOptions.getMenu("IF_CHEQUE_DETAILS_REPORT"));
                        subMenuValues.add(MenuOptions.getMenuAction("IF_CHEQUE_DETAILS_REPORT"));
                        subMenus.add(MenuOptions.getMenu("IF_INFLOW_OUTFLOW_REPORT"));
                        subMenuValues.add(MenuOptions.getMenuAction("IF_INFLOW_OUTFLOW_REPORT"));
                        subMenus.add(MenuOptions.getMenu("IF_INVESTMENT_DETAILS_REPORT"));
                        subMenuValues.add(MenuOptions.getMenuAction("IF_INVESTMENT_DETAILS_REPORT"));
                        subMenus.add(MenuOptions.getMenu("IF_INVESTMENT_MATURITY_REPORT"));
                        subMenuValues.add(MenuOptions.getMenuAction("IF_INVESTMENT_MATURITY_REPORT"));
                        subMenus.add(MenuOptions.getMenu("IF_INVESTMENT_ROI"));
                        subMenuValues.add(MenuOptions.getMenuAction("IF_INVESTMENT_ROI"));
                        subMenus.add(MenuOptions.getMenu("IF_INVESTEE_WISE_REPORT"));
                        subMenuValues.add(MenuOptions.getMenuAction("IF_INVESTEE_WISE_REPORT"));
                        subMenus.add(MenuOptions.getMenu("IF_ACCRUED_INTEREST_INCOME_REPORT"));
                        subMenuValues.add(MenuOptions.getMenuAction("IF_ACCRUED_INTEREST_INCOME_REPORT"));
                        subMenus.add(MenuOptions.getMenu("IF_BANK_RECONCILATION"));
                        subMenuValues.add(MenuOptions.getMenuAction("IF_BANK_RECONCILATION"));
                    } else
                    if(mainMenu.equals(MenuOptions.getMenu("IF_CHEQUE_DETAILS")))
                    {
                        subMenus.add(MenuOptions.getMenu("IF_CHEQUE_DETAILS_INSERT"));
                        subMenuValues.add(MenuOptions.getMenuAction("IF_CHEQUE_DETAILS_INSERT"));
                        subMenus.add(MenuOptions.getMenu("IF_CHEQUE_DETAILS_UPDATE"));
                        subMenuValues.add(MenuOptions.getMenuAction("IF_CHEQUE_DETAILS_UPDATE"));
                    } else
                    if(mainMenu.equals(MenuOptions.getMenu("IF_PAYMENT_DETAILS")))
                    {
                        subMenus.add(MenuOptions.getMenu("IF_PAYMENT_DETAILS_ENTER"));
                        subMenuValues.add(MenuOptions.getMenuAction("IF_PAYMENT_DETAILS_ENTER"));
                        subMenus.add(MenuOptions.getMenu("IF_PAYMENT_DETAILS_MODIFY"));
                        subMenuValues.add(MenuOptions.getMenuAction("IF_PAYMENT_DETAILS_MODIFY"));
                    } else
                    if(mainMenu.equals(MenuOptions.getMenu("IF_MAKE_INVESTMENT")))
                    {
                        subMenus.add(MenuOptions.getMenu("IF_PLAN_INVESTMENT"));
                        subMenuValues.add(MenuOptions.getMenuAction("IF_PLAN_INVESTMENT"));
                        subMenus.add(MenuOptions.getMenu("IF_MAKE_REQUEST"));
                        subMenuValues.add(MenuOptions.getMenuAction("IF_MAKE_REQUEST"));
                        subMenus.add(MenuOptions.getMenu("IF_INVESTMENT_FULFILLMENT"));
                        subMenuValues.add(MenuOptions.getMenuAction("IF_INVESTMENT_FULFILLMENT"));
                    } else
                    if(mainMenu.equals(MenuOptions.getMenu("IF_TDS_DETAILS")))
                    {
                        subMenus.add(MenuOptions.getMenu("IF_ENTER_TDS_DETAILS"));
                        subMenuValues.add(MenuOptions.getMenuAction("IF_ENTER_TDS_DETAILS"));
                        subMenus.add(MenuOptions.getMenu("IF_MODIFY_TDS_DETAILS"));
                        subMenuValues.add(MenuOptions.getMenuAction("IF_MODIFY_TDS_DETAILS"));
                    } else
                    if(mainMenu.equals(MenuOptions.getMenu("IF_CHEQUE_LEAVES")))
                    {
                        subMenus.add(MenuOptions.getMenu("IF_CHEQUE_LEAVES_INSERT"));
                        subMenuValues.add(MenuOptions.getMenuAction("IF_CHEQUE_LEAVES_INSERT"));
                        subMenus.add(MenuOptions.getMenu("IF_CHEQUE_LEAVES_UPDATE"));
                        subMenuValues.add(MenuOptions.getMenuAction("IF_CHEQUE_LEAVES_UPDATE"));
                        subMenus.add(MenuOptions.getMenu("IF_CHEQUE_LEAVES_CONVERSION"));
                        subMenuValues.add(MenuOptions.getMenuAction("IF_CHEQUE_LEAVES_CONVERSION"));
                    }
        }
        request.setAttribute("subMenus", subMenus);
        request.setAttribute("subMenuValues", subMenuValues);
        Log.log(4, "LoginAction", "setSubMenu", "Exited");
    }
}*/