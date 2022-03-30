<%@page import="java.util.Map"%>
<%@page import="java.util.HashMap"%>
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
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<%@ include file="/jsp/SetMenuInfo.jsp"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.Map.Entry"%>

<%
String value="";
String strException="Something went wrong , kindly contact to CGTMSE Support team.";
String strSuccessful="Record successfully uploaded.";
String strError="Please verify record and upload again.";

HashMap<String,ArrayList> map =null;
ArrayList successRecords = null;
ArrayList unsuccessRecords = null;
ArrayList errorRecords = null;
ArrayList error = null;
int success_cnt = 0;
int unsuccess_cnt = 0;
int all_eror_cnt = 0;
int eror_cnt = 0;

if(request.getAttribute("UploadedStatus")!=null)
{
	
	map = (HashMap)request.getAttribute("UploadedStatus");
	
	if(null!=map.get("successRecord")){		
		successRecords = (ArrayList)map.get("successRecord");
		//out.println("successRecords.size() :"+successRecords.size());	
		if(successRecords.size()==2){
	  		ArrayList SuccessDataList=(ArrayList)successRecords.get(1);
	  		success_cnt = SuccessDataList.size();
		}
	}
	
	if(null!=map.get("unsuccessRecord")){
			unsuccessRecords = (ArrayList)map.get("unsuccessRecord");
			//out.println("unsuccessRecords.size() :"+unsuccessRecords.size());
			if(unsuccessRecords.size()==2){				
			  ArrayList UnSuccessDataList=(ArrayList)unsuccessRecords.get(1);
		  	  unsuccess_cnt = UnSuccessDataList.size();		
			}
	}
	
	if(null!=map.get("allerror")){
		errorRecords = (ArrayList)map.get("allerror");
		//out.println("errorRecords.size() :"+errorRecords.size());
		if(errorRecords.size()==2){				
		  ArrayList errorDataList=(ArrayList)errorRecords.get(1);
		  all_eror_cnt = errorDataList.size();		
		}
		
	if(null!=map.get("error")){
		   error = (ArrayList)map.get("error");
		   eror_cnt=error.size();
			//out.println("errorRecords.size() :"+error.size());
	}	
		
}
	
	
	
	/*  
	if(null!=map.get("error")){
		errorRecords = (ArrayList)map.get("error");
		eror_cnt = errorRecords.size();
	}/**/
	
	System.out.println("Result::"+map);	
	//out.println("successRecords::"+success_cnt);
	//out.println("unsuccessRecords::"+unsuccess_cnt);
	//out.println("errorRecords cnt::"+eror_cnt);
}  	

 

%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<!--<style>
#frameWrap {
    position:relative;
    height: 360px;
    width: 640px;
    border: 1px solid #777777;
    background:#f0f0f0;
    box-shadow:0px 0px 10px #777777;
}

#iframe1 {
    height: 360px;
    width: 640px;
    margin:0;
    padding:0;
    border:0;
}

#loader1 {
    position:absolute;
    left:40%;
    top:35%;
    border-radius:20px;
    padding:25px;
    border:1px solid #777777;
    background:#ffffff;
    box-shadow:0px 0px 10px #777777;
}
</style>
<script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.7.2/jquery.min.js"></script>

<script>
    $(document).ready(function () {
        $('#iframe1').on('load', function () {
            $('#loader1').hide();
        });
    });
</script>




<HTML>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<BODY>

<div id="frameWrap">
<img id="loader1" src="loading.jpg" width="36" height="36" alt="loading jpg"/>
<iframe id="iframe1" src="jsp/GuaranteeMaintenance/NpaBulkUploadProcess.jsp" ></iframe>
</div>
--><%-- 
<%		
String exportToExcel = request.getParameter("exportToExcel");  
if (exportToExcel != null && exportToExcel.toString().equalsIgnoreCase("YES")) 
{        	
	response.setContentType("application/vnd.ms-excel");
    response.setHeader("Content-Disposition", "inline; filename=" + "excel.xls"); 
}
%>
--%>
<HTML>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<BODY>
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:form action="NpaBulkUploadInput.do?method=npaBulkUploadInput"
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
			<TABLE width="100%" border="1" align="left" cellpadding="0"
				cellspacing="0">
				<TR>
					<TD>
					<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
						<TR>
							<TD colspan="4">
							<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="31%" class="Heading">&nbsp;<bean:message
										key="FileUpload" />Summary</TD>
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
					
					%>
					<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
						<TR>
						
						</TR>
					</TABLE>

					<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
						<TR>
							<TD style="color: blue; size: 5pt;">Uploaded Summary for NPA Lodgement Detail</TD>
						</TR>
					</TABLE>
					
					<% if(eror_cnt==0){ %>
					
					<TABLE width="100%" border="0" cellspacing="5" cellpadding="5" class="TableData" style="text-align: center;" > 
					<tr>	
						<td>Sr.no</td>
						<td>Response Type</td>
						<td>Count</td>
					</tr>
					<tr>
						<td>1</td>
						<td>Successful Records</td>
						<%if(success_cnt>0){%>
						<td><a href="NpaBulkUploadProcess.do?method=ExportToFile&&fileType=XLSType&FlowLevel=SuccessDataList"><%=success_cnt%></a></td>
						<%}else{ %>
						<td>0</td>
						<%}%>
						
					</tr>
					<tr>	
						<td>2</td>
						<td>UnSuccessful Records</td>
						<%if(unsuccess_cnt>0){%>
						<td><a href="NpaBulkUploadProcess.do?method=ExportToFile&&fileType=XLSType&FlowLevel=UnSuccessDataList"><%=unsuccess_cnt%></a></td>
						<%}else{ %>
						<td>0</td>
						<%}%>
					</tr>					 
					<tr>	
						<td>3</td>
						<td>Error</td>
						<%if(all_eror_cnt>0){%>
						<td><a href="NpaBulkUploadProcess.do?method=ExportToFile&&fileType=XLSType&FlowLevel=Allerrors"><%=all_eror_cnt%></a></td>
						<%}else{ %>
						<td>0</td>
						<%}%>
					</tr>
					
					</TABLE>
					<%}else{ %>
			            <TABLE width="100%" border="0" cellspacing="5" cellpadding="5" class="TableData" style="text-align: center;" > 
					<tr>	
						<td><font color=red><h2><%=error%></h2></font></td>
					</tr>
					</TABLE>
			            <%} %>
					 	 		 
				     </TD>
				</TR> 
				    
				<%--s --%>
				<TR align="center" valign="baseline" >
					<TD colspan="2" width="700">
						<DIV align="center">
						 	<A href="javascript:submitForm('NpaBulkUploadInput.do?method=NpaBulkUploadInput')">
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
<TABLE>
<tr>
							<TD class="ColumnBackground"><font style="color: red; font-size: 11px;">Important : </font></TD>							
							<td class="ColumnBackground" >
								<div>
									<font style="color: red; font-size: 11px;">
											Please upload NPA records only once. Only Unsuccessful / Error records may be re-uploaded again</font>
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


</BODY>
</HTML>