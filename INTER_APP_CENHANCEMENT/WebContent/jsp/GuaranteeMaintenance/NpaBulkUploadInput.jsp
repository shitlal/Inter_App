<%@page import="com.cgtsi.application.ApplicationProcessor"%>
<%@page import="com.cgtsi.action.ApplicationProcessingAction"%>
<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean"%>
<%
	session.setAttribute("CurrentPage", "NpaBulkUploadInput.do");
%>
<%@ include file="/jsp/SetMenuInfo.jsp"%>

<HTML>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<BODY>
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:form action="NpaBulkUploadProcess.do?method=npaBulkUploadProcess"
		method="POST" enctype="multipart/form-data">
		<html:errors />
		<TR>
			<TD width="20" align="right" valign="bottom"><IMG
				src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG
				src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>

		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			<TABLE width="100%" border="0" align="left" cellpadding="0"
				cellspacing="0">
				<TR>
				
					<TD>
				  	<!-- <A target="_blank" href="ApplicationLogdementInBulkProcess.do?method=getBulkUploadTemplate" >
					Click here to download Excel Template File</A>-->
					
					<A target="_blank" href="Download/NPA_EXCEL.xls" >
					Click here to download Excel Template File</A>
					
					
					<DIV align="right"><A HREF="#">HELP</A></DIV>
					<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
						<TR>
							<TD colspan="4">
							<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="31%" class="Heading">&nbsp;<bean:message
										key="FileUpload" /></TD>
									<TD><IMG src="images/TriangleSubhead.gif" width="19"
										height="19"></TD>
								</TR>
								<TR>
									<TD colspan="3" class="Heading"><IMG
										src="images/Clear.gif" width="5" height="5"></TD>
								</TR>

							</TABLE>
							</TD>
						</TR>
						<TR>
							<TD colspan="3">
							<font style="color: red; font-size: 15px;">
							      Note: 1] File Format should be xls (or XLS)  
								  
							</font>
							</TD>
						</TR>
						<TR>
							<TD class="ColumnBackground">&nbsp;&nbsp;<bean:message
								key="ChooseFile" /></TD>							
							<TD class="TableData">
							<div align="left">&nbsp;&nbsp; <html:file property="filePath" name="ioForm" /></div>
							</td>
						</tr>
						<tr>
							<TD class="ColumnBackground"><font style="color: red; font-size: 11px;">Important : </font></TD>							
							<td class="ColumnBackground" >
								<div>
									<font style="color: red; font-size: 11px;">
											1.Please upload npa records only once. Only Unsuccessful / Error records may be re-uploaded again</font>
											<br>
											<font style="color: red; font-size: 11px;">
											2. Update final disbursement details in the disbursement module before uploading details for NPA marking</font>
											<br>
											<font style="color: red; font-size: 11px;">
											3. Upload CGPAN / all linked CGPANs details of borrower where Application Status is in AP status only for NPA marking</font>
											<br>
											<font style="color: red; font-size: 11px;">
											4. System Date Format should also be in dd/mm/yyyy format </font>
											<br><br>	
																			
									<font style="color: green; font-size: 11px;">Successful Records - </font>
									<font style="color: black; font-size: 11px;"> Records uploaded successfully. </font> <br>
									<font style="color: red; font-size: 11px;">Unsuccessful Records - </font> 
									<font style="color: black; font-size: 11px;">Business validations failed.</font> <br>
									<font style="color: red; font-size: 11px;">Error - </font> 
									<font style="color: black; font-size: 11px;">Data not as per Excel Template.</font><br> 
								</div>
							</TD>
						</TR>
					</TABLE>
					</TD>
				</TR>
				<TR>
					<TD height="20">&nbsp;</TD>
				</TR>
				<TR>
					<TD align="center" valign="baseline">
					<DIV align="center" ><A
						href="javascript:submitForm('NpaBulkUploadProcess.do?method=npaBulkUploadProcess')">
					<IMG src="images/Upload.gif" alt="OK" width="49" height="37"
						border="0"></A> </div>
						 
						<A href="javascript:document.appForm.reset()">
							<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37"	border="0">
						</A> 
						
						<A href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
					<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37"
						border="0"></A></DIV>
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
	</html:form>
</TABLE>
</BODY>
</HTML>