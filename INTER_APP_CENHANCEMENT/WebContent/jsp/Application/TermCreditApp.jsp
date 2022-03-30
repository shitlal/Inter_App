<%@ page language="java"%>
<%@ page import="com.cgtsi.util.SessionConstants"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean"%>
<%String focusField="";%>
<% if(session.getAttribute(SessionConstants.APPLICATION_LOAN_TYPE)!=null)
{
System.out.println(session.getAttribute(SessionConstants.APPLICATION_LOAN_TYPE));
if(request.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG)!=null && request.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG).equals("15") && session.getAttribute(SessionConstants.APPLICATION_LOAN_TYPE).equals("TC"))
{
session.setAttribute("CurrentPage","tcMli.do?method=getTCMliInfo");
focusField="district";
}
else if(request.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG)!=null && request.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG).equals("16") && session.getAttribute(SessionConstants.APPLICATION_LOAN_TYPE).equals("TC"))
{
session.setAttribute("CurrentPage","tcMli.do?method=getTCMliInfo");
focusField="industrySector";
}
else if(session.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG)!=null && session.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG).equals("17"))
{
System.out.println(request.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG));
session.setAttribute("CurrentPage","afterTcMli.do?method=getBorrowerDetails");
focusField="guarantorsName1";

}
else if(session.getAttribute(SessionConstants.APPLICATION_LOAN_TYPE).equals("TC"))
{
session.setAttribute("CurrentPage","tcMli.do?method=getTCMliInfo");
focusField="mliRefNo";
}
}
else if(session.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG).equals("3") /*|| session.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG).equals("11")*/)
{
session.setAttribute("CurrentPage","afterModifyApp.do?method=showCgpanList");
focusField="mliRefNo";

}else if(session.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG).equals("14"))
{
session.setAttribute("CurrentPage","afterSsiRefPage.do?method=afterSsiRefPage");
focusField="mliRefNo";
}
else if(session.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG).equals("15"))
{
session.setAttribute("CurrentPage","afterSsiRefPage.do?method=afterSsiRefPage");
focusField="mliRefNo";
}

if(focusField.equals(""))
{
focusField=null;
}

org.apache.struts.action.ActionErrors errors = (org.apache.struts.action.ActionErrors)request.getAttribute(org.apache.struts.Globals.ERROR_KEY);
if (errors!=null && !errors.isEmpty())
{
focusField="test";
}


;%>

<%@ include file="/jsp/SetMenuInfo.jsp"%>

<style>
input[type=text],select,textarea {
	width: 160px;
}

table,tr>td>table>tbody>tr>td {
	border: 0.5px solid white;
	border-collapse: collapse;
}
</style>

<body onLoad="showProgress('none'),enableAssistance(),enableNone(),setConstEnabled(),enableDistrictOthers(),enableOtherLegalId(),enableSubsidyName(),calProjectCost(),calProjectOutlay(),enableGender(),enableHandiCrafts(),enabledcHandlooms()">

<html:form action="addTermCreditApp.do?method=submitApp" method="POST"
		focus="<%=focusField%>">
		<html:hidden name="appForm" property="test" />
		<html:errors />
		<TABLE width="99%" border="0" cellpadding="0" cellspacing="0">
			<TR>
				<TD width="20" align="right" valign="bottom"><IMG
					src="images/TableLeftTop.gif" width="20" height="31"></TD>
				<TD background="images/TableBackground1.gif"><IMG
					src="images/ApplicationProcessingHeading.gif" width="91"
					height="31"></TD>
				<TD width="20" align="left" valign="bottom"><IMG
					src="images/TableRightTop.gif" width="23" height="31"></TD>
			</TR>

			<TR>
				<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
				<TD>
					<DIV align="right">
						<A
							HREF="javascript:submitForm('termCreditHelp.do?method=termCreditHelp')">
							HELP</A>
					</DIV> <%@include file="CommonAppTcDetails.jsp"%>
					       <%@include file="TermCreditAppDetails.jsp"%> <%
if (session.getAttribute(SessionConstants.MCGF_FLAG).equals("M"))
{%> <%@include file="MCGFDetails.jsp"%> <%}
%>
					<TABLE width="100%" border="0" align="left" cellpadding="0"
						cellspacing="0">
						<%
String appFlag=session.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG).toString();
if(appFlag.equals("3"))
{
%>
						<tr align="left">
							<td class="ColumnBackground" height="28">&nbsp; Existing
								Remarks</td>
							<td class="TableData" height="28" colspan="5"><bean:write
									name="appForm" property="existingRemarks" /></td>
						</tr>
						<%}%>

						<tr align="left">
							<td class="ColumnBackground" height="28" colspan="2">&nbsp;
								Remarks</td>
							<td class="TableData" height="28" colspan="4">
								<%		
if(appFlag.equals("11") || appFlag.equals("13"))
{
%> <bean:write property="remarks" name="appForm" /> <%} else {%> <html:textarea
									property="remarks" cols="75" alt="address" name="appForm"
									rows="4" /> <%}%>

							</td>
						</tr>
						<TR class="ColumnBackground">&nbsp;
				                      <input type="checkbox" name="restructConfirmation"  disabled="disabled" checked="checked"  value="The credit facility is not restructured / remained in SMA2 in last 1 year"/>The credit facility is not restructured / remained in SMA2 in last 1 year.        
			                            </br>					
							<font color="#FF0000" size="2">*</font>
							<!--Check box Added by sukant@pathinfotech on 15/05/2007-->
							<html:checkbox property="agree" value="Y" disabled="false" />
							We certify that the account is standard and regular with nil
							overdues as on date. We accept all Terms and Conditions of the
							Scheme.
							<A HREF="applicationValidation.do?method=applicationValidation">
								Click Here</A> to see Terms and Conditions:
						</TR>

						<TR>
							<TD align="center" valign="baseline" colspan="7">
								<DIV align="center">
								<IMG align="center" src="images/warten-gif-5.gif" alt="Wait.." width="49" height="37" border="0"  id="progress_id" />
									<%	
if (request.getParameter("detail")!=null)
{%>
									<A href="javascript:window.close()"> <IMG
										src="images/Close.gif" alt="Close" width="49" height="37"
										border="0"></A>

									<%}
else if(appFlag.equals("11") || appFlag.equals("13"))
{
%>
									<A href="javascript:history.back()"> <IMG
										src="images/Back.gif" alt="Back" width="49" height="37"
										border="0"></A>
									<%}							
else if(appFlag.equals("3")){%>
									<A
										href="javascript:submitForm1('addTermCreditApp.do?method=submitApp')"><IMG
										src="images/Save.gif" alt="Save" width="49" height="37"
										border="0" onclick="showProgress('block');"></A>  <A
										href="javascript:document.appForm.reset()"> <IMG
										src="images/Reset.gif" alt="Cancel" width="49" height="37"
										border="0"></A> <A
										href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
										<IMG src="images/Cancel.gif" alt="Cancel" width="49"
										height="37" border="0">
									</A>
									<%}
else{%>
									<A href="javascript:submitForm1('addTermCreditApp.do?method=submitApp')">
									<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0" onclick="showProgress('block');"></A> 
									
									<A href="javascript:document.appForm.reset()"> <IMG
										src="images/Reset.gif" alt="Cancel" width="49" height="37"
										border="0"></A> <A
										href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
										<IMG src="images/Cancel.gif" alt="Cancel" width="49"
										height="37" border="0">
									</A>
									<%}%>                                   
								</DIV>
							</TD>
						</TR>
					</TABLE>
				</TD>
				<TD width="20" background="images/TableVerticalRightBG.gif">
					&nbsp;</TD>
			</TR>
			<TR>
				<TD width="20" align="right" valign="top"><IMG
					src="images/TableLeftBottom1.gif" width="20" height="15"></TD>
				<TD background="images/TableBackground2.gif">&nbsp;</TD>
				<TD width="20" align="left" valign="top"><IMG
					src="images/TableRightBottom1.gif" width="23" height="15"></TD>
			</TR>
		</TABLE>

	</html:form>
</body>






