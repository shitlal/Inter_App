<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% if(session.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG)!=null)
{
if(session.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG).equals("0"))
{
session.setAttribute("CurrentPage","afterEnterApp.do?method=showCgpanList&flag=1");
}
};%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<body onLoad="showProgress('none'),setConstEnabled(),enableNone(),enableDistrictOthers(),enableOtherLegalId(),enableSubsidyName(),enableHandiCrafts(),enabledcHandlooms()">
<TABLE width="99%"  border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="addtlAppPage.do?method=submitApp" method="POST">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/ApplicationProcessingHeading.gif" width="91" height="31"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<%@include file="CommonAppAddTcDetails.jsp"%>
				<%@include file="TermCreditAddTcDetails.jsp"%>
				<%@include file="SecuritizationDetails.jsp"%>	
				<%
				if (session.getAttribute(SessionConstants.MCGF_FLAG).equals("M"))
				{%>
					<%@include file="MCGFDetails.jsp"%>
				<%}
				%>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
                     <TR class="ColumnBackground">
							<font color="#FF0000" size="2">*</font>
							<!--Check box Added by DKR@pathinfotech on 15/02/2021-->
							<html:checkbox property="agree" styleId="agree" value="Y" disabled="false" />	<%-- <html:checkbox property="agree"  value="Y" disabled="false" /> --%>
							We certify that the account is standard and regular with nil
							overdues as on date. We accept all Terms and Conditions of the
							Scheme.
							<A HREF="applicationValidation.do?method=applicationValidation">
								Click Here</A> to see Terms and Conditions:
						</TR>
					<tr align="left">
						<td class="ColumnBackground" height="28">&nbsp;
							<bean:message key="remarks"/>
						</td>
						<td class="TableData" height="28" colspan="4">
						
							<html:textarea property="remarks" cols="75" rows="4" alt="address" name="appForm"/>
							
						</td>
					</tr>					
					<TR >
						<TD align="center" valign="baseline" colspan="5">
							<DIV align="center">
							<IMG align="center" src="images/warten-gif-5.gif" alt="Wait.." width="49" height="37" border="0"  id="progress_id" style="display:none"/>
								<A href="javascript:submitFormAddTerm('addtlAppPage.do?method=submitApp')"><IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"  onclick="showProgress('block');"></A>	
								<A href="javascript:document.appForm.reset()">
									<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>
									<A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
									<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>							
							</DIV>
						</TD>
					</TR>					
				</TABLE>
			</TD>
			<TD width="20" background="images/TableVerticalRightBG.gif">
				&nbsp;
			</TD>
		</TR>
		<TR>
			<TD width="20" align="right" valign="top">
				<IMG src="images/TableLeftBottom1.gif" width="20" height="15">
			</TD>
			<TD background="images/TableBackground2.gif">
				&nbsp;
			</TD>
			<TD width="20" align="left" valign="top">
				<IMG src="images/TableRightBottom1.gif" width="23" height="15">
			</TD>
		</TR>
	</html:form>
</TABLE>







					