<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean"%>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp"%>
<%
	session.setAttribute("CurrentPage","outstandingReportInputNew.do?method=outstandingReportInputNew");
%>


<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form
		action="outstandingReportInputNew.do?method=outstandingReportInputNew"
		method="POST" enctype="multipart/form-data">
		<TR>
			<TD width="20" align="right" valign="bottom"><IMG
				src="images/TableLeftTop.gif" width="20" height="31" alt=""></TD>
			<TD background="images/TableBackground1.gif"><IMG
				src="images/ReportsHeading.gif" width="121" height="25" alt=""></TD>
			<TD width="20" align="left" valign="bottom"><IMG
				src="images/TableRightTop.gif" width="23" height="31" alt=""></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			<DIV align="right"><A
				HREF=""> HELP</A>
			<TABLE width="100%" border="0" align="left" cellpadding="0"
				cellspacing="0">
				<tr>
					<TD align="left" colspan="4"><b><font size="2">
					Fields marked as </font><font color="#FF0000" size="2">*</font><font
						size="2"> are mandatory</font></b></TD>
				</tr>
				<TR>
					<TD>
					<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
						<TR>
							<TD colspan="10">
							<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									
								</TR>
								<TR>
									
								</TR>

							</TABLE>
							</TD>
						</TR>
							<TR>
								<TD align="left" valign="top" class="ColumnBackground">
									<DIV align="center"><bean:message key="fromdate" /></DIV>
								</TD>
								<TD align="left" valign="middle" class="TableData">
									<DIV align="left">
										<html:text property="dateOfTheDocument20" size="10" maxlength="10" alt="Reference" name="reportForm"/>
										<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('reportForm.dateOfTheDocument20')" align="middle" alt="">
										<DIV align="left"></DIV>
									</DIV>
								</TD>

								<TD align="left" valign="top" class="ColumnBackground">
									<font color="#FF0000" size="2">*</font> <bean:message key="toDate" />
								</TD>

								<TD align="left" valign="middle" class="TableData">
									<DIV align="left">
										<html:text property="dateOfTheDocument21" size="10" maxlength="10" alt="Reference" name="reportForm" />
										<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('reportForm.dateOfTheDocument21')" align="middle" alt="">
										<DIV align="left"></DIV>
									</DIV>
								</TD>
							</TR>
							
							   <TR>
									  <TD  align="center" valign="center"  class="ColumnBackground" >
									  <DIV align="center">
									  <bean:message key="memberId" /> 
									   </DIV>
									  </TD>
									   <TD align="left" valign="center" class="ColumnBackground1" colspan=3>
									   <DIV align="left">
										<bean:write property="memberId" name="reportForm"/>
										 </DIV>
									  </TD>
									  
									   <logic:equal property="bankId" value="0000" name="reportForm">	
									 
									  <TR>
									  <TD  align="center" valign="center"  class="ColumnBackground" >
									  <DIV align="center">
									  <bean:message key="memberId" /> 
									   </DIV>
									  </TD>
									   <TD align="left" valign="center" class="ColumnBackground" colspan=3>
									   <DIV align="left">
										 <html:text property="memberId" size="20"  alt="memberId" name="reportForm" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)" maxlength="12"/>  
										 </DIV>
									  </TD>
									  </TR>
									  </logic:equal>


									
									  </TR>
							
							
							<!-- 	
									
								
								<tr align="left">
									<TD align="left" valign="middle" class="TableData">
										<div align="center">
										  &nbsp;<font color="#FF0000" size="2">*</font><bean:message key="MemberID"/>
										</div>
									 </td>
									<TD align="left" valign="top" class="ColumnBackground">
										<div align="left">
										  <html:text property="memberId" size="20" alt="Select Member" name="reportForm" maxlength="12" />
										</div>
									 </td>
									 
									 
									 <TD align="left" valign="middle" class="TableData">
									&nbsp;
								</TD>
								<TD align="left" valign="right" class="TableData">
									&nbsp;
								</TD>
								</tr>
							 	
							 	
							 	
							 	
							 				-->
			
							
					</TABLE>
					</TD>
				</TR>
				<TR>
					<TD height="20">&nbsp;</TD>
				</TR>
				<TR>
					<TD align="center" valign="baseline">
					<DIV align="center"><A
						href="javascript:submitForm('outstandingNewReportDetail.do?method=outstandingNewReportDetail')">
					<IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0"></A>
					<A href="javascript:document.reportForm.reset()"> <IMG
						src="images/Reset.gif" alt="Reset" width="49" height="37"
						border="0"></A> <a
						href='subHome.do?method=getSubMenu&amp;menuIcon=<%=session.getAttribute("menuIcon")%>&amp;mainMenu=<%=session.getAttribute("mainMenu")%>'>
					<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37"
						border="0"></a></DIV>
					</TD>
				</TR>
			</TABLE>
			</DIV>
			</TD>
			<TD width="20" background="images/TableVerticalRightBG.gif">
			&nbsp;</TD>
		</TR>
		<TR>
			<TD width="20" align="right" valign="top"><IMG
				src="images/TableLeftBottom1.gif" width="20" height="15" alt="">
			</TD>
			<TD background="images/TableBackground2.gif">&nbsp;</TD>
			<TD width="20" align="left" valign="top"><IMG
				src="images/TableRightBottom1.gif" width="23" height="15" alt="">
			</TD>
		</TR>
	</html:form>
</TABLE>

