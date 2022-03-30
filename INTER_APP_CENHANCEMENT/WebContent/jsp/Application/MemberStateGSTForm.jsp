<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>

<%  String currentPage;
	String user=(String)session.getAttribute("loggedInUser");
	System.out.println(user);
	if (user.equals("CGTSI"))
	{
		currentPage="memberSelected.do?method=memberSelected";
	}
	else{
		
		currentPage="memberStateFormInput.do?method=memberStateFormInput";
	} %>

<% session.setAttribute("CurrentPage",currentPage);%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%
String focusflag = "";
if(request.getAttribute("district")!=null && request.getAttribute("district").equals("1"))
{
	focusflag="district";
}
else{

	focusflag="zoneName";
}
%>

<body onLoad="setRZonesEnabled(),danDelivery()">
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
<%
// this focusField is a variable which will point to the field which has to be focused in case of no errors.
String focusField=focusflag;
org.apache.struts.action.ActionErrors errors = (org.apache.struts.action.ActionErrors)request.getAttribute(org.apache.struts.Globals.ERROR_KEY);
if (errors!=null && !errors.isEmpty())
{
            focusField="test";
}
%>
<html:form action="defOrgStr.do" method="POST" focus="<%=focusField%>">
<html:hidden name="regForm" property="test"/>
<html:errors />
	
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpDefOrgStr.do?method=helpDefOrgStr')">
			    HELP</A>
			</DIV>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
				<TD  align="left" colspan="4"><font size="2"><bold>
				Fields marked as </font><font color="#FF0000" size="2">*</font><font size="2"> are mandatory</bold></font>
				</td>
				</tr>
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="4"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="31%" class="Heading"><bean:message key="defineGSTHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>
								</TR>

								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" width="40%">
										&nbsp;<bean:message key="bankId" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="bankId" size="20" alt="bank Id" name="regForm" maxlength="100"/>	
									
										</TD>
								</TR>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" width="40%">
										&nbsp;<bean:message key="zoneId" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="zoneId" size="20" alt="zone Id" name="regForm" maxlength="100"/>	
									
										</TD>

								</TR>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" width="40%">
										&nbsp;<bean:message key="branchId" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="branchId" size="20" alt="branch Id" name="regForm" maxlength="100"/>	
									
										</TD>
									
								</TR> 
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" width="40%">
										&nbsp;<bean:message key="bankName" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="bankName" size="20" alt="bank Name" name="regForm" maxlength="100"/>	
									
										</TD>
								</TR> 
								<%-- <TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="CBbranchName" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="branchName" size="20" alt="Branch Name" name="regForm" maxlength="100"/>
										<bean:message key="selectZone" />
										<html:select property="zoneList" name="regForm" >
											<html:option value="">Select</html:option>					
											<html:options property="reportingZones" name="regForm"/>
										</html:select>
									</TD>
								</TR>  --%>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="state" />
									</TD>
									<TD align="left" class="TableData"> 
									 <html:select property="state" name="regForm"
									 onchange="javascript:submitForm('memberStateFormInput.do?method=getDistricts')">
											<html:option value="">Select</html:option>
											<html:options property="states" name="regForm"/>			
										</html:select> 
									</TD>
								</TR>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" width="40%">
										&nbsp;<bean:message key="gstNO" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="gstNO" size="20" alt="gst No" name="regForm" maxlength="100"/>	
									
										</TD>
								</TR>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" width="40%">
										&nbsp;<bean:message key="borroName" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="borroName" size="20" alt="person Name" name="regForm" maxlength="100"/>	
									
										</TD>
								</TR>
							<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="memregaddress" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:textarea property="memregaddress" cols="20" alt="Address" name="regForm"/>
									</TD>
								</TR>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="memregaddress2" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:textarea property="memregaddress2" cols="20" alt="Address2" name="regForm"/>
									</TD>
								</TR>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="recPlace" />
									</TD>
									<TD align="left" class="TableData"> 
										 <html:select property="reciPlace" name="regForm">
											<html:option value="">Select</html:option>
											<html:options property="recPlace" name="regForm"/>			
										</html:select> 
									</TD>
								</TR>
								<%-- <TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" width="40%">
										&nbsp;<bean:message key="reciPlace" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="reciPlace" size="20" alt="recipient Place" name="regForm" maxlength="100"/>	
									
										</TD>
								</TR>  --%>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="pinCode" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="pin" size="5" alt="pinCode" name="regForm" maxlength="6" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
									</TD>
								</TR>	
							 <TR align="left">
									<TD align="left" valign="top" class="ColumnBackground" width="40%">
										&nbsp;<bean:message key="headflag"/>							
									</TD>
									<TD align="left" valign="top" class="TableData">
										<html:radio name="regForm" value="Y" property="headflag" ><bean:message key="yes" /></html:radio>&nbsp;&nbsp;
										<html:radio name="regForm" value="N" property="headflag" ><bean:message key="no" /></html:radio>
									</TD>
								</TR>
								<%--	<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="phoneNo" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="phoneStdCode" size="10" maxlength="10" alt="Phone No" name="regForm" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>&nbsp;&nbsp;-&nbsp;&nbsp;
										<html:text property="phone" size="10" maxlength="20" alt="Phone No" name="regForm"onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
									</TD>
								</TR>  --%>
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="emailId" />
									</TD>
									<TD align="left"	 class="TableData"> 
										<html:text property="emailId" size="20" maxlength="40" alt="Email Address" name="regForm"/>
									</TD>
								</TR>
								 <TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="phoneNo" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="phoneStdCode" size="10" maxlength="10" alt="Phone No" name="regForm" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>&nbsp;&nbsp;-&nbsp;&nbsp;
										<html:text property="phone" size="10" maxlength="20" alt="Phone No" name="regForm" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
									</TD>
								</TR> 
								
								<!-- <TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="zoRoName" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="zoneName" size="20" alt="ZO/RO Name" name="regForm"/>&nbsp;&nbsp;
										<html:radio name="regForm" value="ZO" property="setZoRo" onclick="setRZonesEnabled(this)" ><bean:message key="zo" /></html:radio>&nbsp;&nbsp;
										<html:radio name="regForm" value="RO" property="setZoRo" onclick="setRZonesEnabled(this)" ><bean:message key="ro" /></html:radio>&nbsp;&nbsp;
										<bean:message key="ho" />&nbsp;&nbsp;
										<html:select property="reportingZone" name="regForm" >
											<html:option value="">Select</html:option>					
											<html:options property="reportingZones" name="regForm"/> 						
										</html:select>
									</TD>
								</TR>
									
								<TR align="left">
									<TD align="left" valign="top" class="ColumnBackground">
										&nbsp;<bean:message key="CBbranchName" />
									</TD>
									<TD align="left" class="TableData"> 
										<html:text property="branchName" size="20" alt="Branch Name" name="regForm"/>
									</TD>
								</TR> -->

								
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
							<A href="javascript:submitForm('defOrgStr.do?method=defOrgStr')"><IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
								<A href="javascript:document.regForm.reset()">
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
		</TABLE>
	</html:form>
</body>




						
