<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","fdiReportInput.do?method=fdiReportInput");%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="fdiReport.do?method=fdiReport" method="POST" enctype="multipart/form-data" focus="dateOfTheDocument">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/InvestmentManagementHeading.gif" width="180" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
				<tr>
	  <TD>			
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpFDReportDetails.do?method=helpFDReportDetails')">
			    HELP</A>
			</DIV>
		</td>
	  </tr>
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="10"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="27%" class="Heading"><bean:message key="danReportHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>

									<TR>
									<TD align="left" valign="top" class="ColumnBackground">
									<DIV align="center">
										  &nbsp;<bean:message key="fromdate" /> 
										  </DIV>
									</TD>
									   <TD  align="left" valign="center" class="ColumnBackground">
										  <DIV align="left">
										  <html:text property="dateOfTheDocument" size="20" maxlength="10" alt="Reference"name="ifForm"/>
										  <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('ifForm.dateOfTheDocument')" align="center">
										  <DIV align="left">
									  </TD>
									
									  <TD align="left" valign="top" class="ColumnBackground">
									<DIV align="center">
										  &nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="toDate"/> 
										  </DIV>
									</TD>

									    <TD  align="left" valign="center" class="ColumnBackground">
										  <DIV align="left">
										  <html:text property="dateOfTheDocument1" size="20" maxlength="10" alt="Reference" name="ifForm"/>
										  <IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('ifForm.dateOfTheDocument1')" align="center">
										  <DIV align="left">
									  </TD>
									  </TR>

									<TR>
									<TD colspan="4" align="left" valign="top" class="ColumnBackground">
									<DIV align="center">
									  <html:radio name="ifForm" value="maturityDate" property="status" ><bean:message key="maturityDate" /></html:radio>&nbsp;&nbsp;
									 <html:radio name="ifForm" value="fdiNumber" property="status" ><bean:message key="fdiNumber" /></html:radio>&nbsp;&nbsp;
									 <html:radio name="ifForm" value="depositDate" property="status" ><bean:message key="depositDate" /></html:radio>

									</DIV>
									</TD>
									</TR>	
							
	
							</TABLE>
						</TD>
					</TR>
					<TR >
						<TD height="20" >
							&nbsp;
						</TD>
					</TR>
					<TR >
						<TD align="center" valign="baseline" >
							<DIV align="center">
									<A href="javascript:submitForm('fdiReport.do?method=fdiReport')">
									<IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0"></A>
									<A href="javascript:document.ifForm.reset()">
									<IMG src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></A>
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

