<%@ page language="java"%>
<%@ page import="java.io.File"%>
<%@ page import="java.io.FileOutputStream"%>
<%@ page import="java.io.IOException"%>
<%@ page import="org.apache.poi.hpsf.HPSFException"%>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFCell"%>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFCellStyle"%>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFRow"%>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFSheet"%>
<%@ page import="org.apache.poi.hssf.usermodel.HSSFWorkbook"%>

<%@page import="java.util.ArrayList"%>
<%@page import="java.util.Iterator"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html"%>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean"%>
<%@ include file="/jsp/SetMenuInfo.jsp"%>

<%
ArrayList invalidApps = new  ArrayList();
invalidApps = (ArrayList)request.getAttribute("INVALIDAPPS");
if(invalidApps !=null)
{
	System.out.println("inside jsp : invalidApps not null");
	if(invalidApps.size() > 0)
	{
		System.out.println("invalidApps size : "+invalidApps.size());
	}
}
ArrayList clearApps = new ArrayList();
%>

<HTML>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<BODY>
<%-- 
<%		
String exportToExcel = request.getParameter("exportToExcel");  
if (exportToExcel != null && exportToExcel.toString().equalsIgnoreCase("YES")) 
{        	
	response.setContentType("application/vnd.ms-excel");
    response.setHeader("Content-Disposition", "inline; filename=" + "excel.xls"); 
}
%>
--%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:form action="uploadGuaranteeApp.do?method=uploadGuaranteeApp"
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
					<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
						<TR>
							<TD colspan="4">
							<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="31%" class="Heading">&nbsp;<bean:message
										key="FileUpload" /> Summary</TD>
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
					</table>
					<%
						/*java.util.ArrayList invalidApps = (java.util.ArrayList) request
									.getAttribute("INVALIDAPPS");
							java.util.ArrayList clearApps = (java.util.ArrayList) request
									.getAttribute("CLEARAPPS");*/
							//java.util.ArrayList dupApps = (java.util.ArrayList) request
									//.getAttribute("DUPAPPS");
						invalidApps = (ArrayList) request.getAttribute("INVALIDAPPS");
						clearApps = (ArrayList) request.getAttribute("CLEARAPPS");
					%>
					<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
						<TR>
							<TD style="color: green; size: 5pt;">Number of
							application(s) uploaded successfully: <%=clearApps.size()%></TD>
						</TR>
					</TABLE>

					<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
						<TR>
							<TD style="color: red; size: 5pt;">Please correct following
							record(s) from the excel file.</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
						<tr>
							<TD class="ColumnBackground" width="5%"
								style="text-align: center;">&nbsp;Excel&nbsp;<br>
							&nbsp;Sheet&nbsp;<br>
							&nbsp;Row No.&nbsp;</TD>
							<TD class="ColumnBackground" width="95%"
								style="text-align: center;">&nbsp;Incorrect/Invalid Data</TD>
						</tr>
						<%
						if(invalidApps!=null)
						{
							if(invalidApps.size() > 0)
							{
							System.out.println("invalid apps:" + invalidApps.size());
								Iterator itr = invalidApps.iterator();
								int no = 1;
								while (itr.hasNext()) {
									ArrayList errors = (ArrayList) itr.next();
									int total_errors = errors.size();
									String data1 = "";
									String data2 = "";
									System.out.println("total errors:" + total_errors);
						%>
						<TR>

							<%
								for (int i = 0; i < total_errors; i++) {
											if (i == 0) {
												data1 = (String) errors.get(i);

											} else if (i >= 1) {
												data2 = data2 + (String) errors.get(i) + ".";
											}
							%>
							<%
								}
							%>
							<td class="TableData" style="text-align: right;"><%=data1%>&nbsp;&nbsp;</td>
							<td class="TableData"><%=data2%>&nbsp;&nbsp;</td>

						</TR>
						<%
							no++;
								}
								}
						}
						%>
					</TABLE>

					</TD>
				</TR>
				<%--s --%>
				<TR align="center" valign="baseline" >
					<TD colspan="2" width="700">
						<DIV align="center">
						 	<A href="javascript:submitForm('uploadGuaranteeAppInput.do')">
								<IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0">
							</A>								
						</DIV>
					</TD>
				</TR>
				<%--e --%>
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
<%-- 
<%
if (exportToExcel == null) 
{
%>
<a href="jsp/Application/ApplicationsSummary.jsp?exportToExcel=YES">Export to Excel</a>
<%
}
%>
--%>
<%--add by vinod@path 03-july-15 for download invalidApps file --%>
<%
	String filename = (String)request.getAttribute("FileName");
	System.out.println(filename);
%>

<%
if(invalidApps != null)
{
	if(invalidApps.size() > 0)
	{
%>
<a href="<%=request.getContextPath()%>/downloadFile.do?method=DownloadInvalidAppsFile&fileName=<%=request.getAttribute("FileName") %>">
	Incorrect/Invalid Data
</a>
<html:hidden property="fileName" name="appForm" value="<%=filename%>"></html:hidden>		
<%
}
}
%>

</BODY>
</HTML>