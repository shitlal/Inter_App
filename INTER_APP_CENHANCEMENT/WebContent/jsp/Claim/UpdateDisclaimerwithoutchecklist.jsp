<%@ page language="java"%>
<%@page import ="java.text.SimpleDateFormat"%>
<%@page import ="java.util.Date"%>
<%@ page import="com.cgtsi.actionform.ClaimActionForm"%>
<%@ page import="com.cgtsi.claim.ClaimApplication"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<script type="text/javascript">

function submitDeclaResubmit(action)
{

	if(document.forms[0].nameOfOfficial.value=="" || document.forms[0].nameOfOfficial.value==null )
	{
		alert("please enter nameOfOfficial ");

		document.getElementById('nameOfOfficial').focus();

	}

	if(document.forms[0].designationOfOfficial.value=="" || document.forms[0].designationOfOfficial.value==null )
	{
		alert("please enter designationOfOfficial ");
		document.getElementById('designationOfOfficial').focus();

	}
	if(document.forms[0].place.value=="" || document.forms[0].place.value==null )
	{
		alert("please enter place ");
		document.getElementById('place').focus();

	}



//alert("gfgfgfgf");
document.forms[0].action=action;
document.forms[0].target="_self";
document.forms[0].method="POST";
document.forms[0].submit();



}




		

	</script>
	
<% session.setAttribute("CurrentPage","updateFirstClaimsPageDetails.do?method=addFirstClaimsPageDetails");%>
<% SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>

<html>
<head>
<LINK href="<%=request.getContextPath()%>/css/StyleSheet.css" rel="stylesheet" type="text/css">
</head>
kailash
<body>
<!--<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.5.0/jquery.min.js"></script>-->
<script type="text/javascript" src="<%=request.getContextPath()%>/js/jquery-1.7.1.min.js"></script>
<body onload="setCPOthersEnabled()"/>
<%
String focusField="nameOfOfficial";
org.apache.struts.action.ActionErrors errors = (org.apache.struts.action.ActionErrors)request.getAttribute(org.apache.struts.Globals.ERROR_KEY);
if (errors!=null && !errors.isEmpty())
{
    focusField="test";
}
 Date systemDate  = new Date();
 String sysDate = dateFormat.format(systemDate);
 ClaimActionForm claimForm = (ClaimActionForm)session.getAttribute("cpTcDetailsForm") ;
 
 //System.out.println("cheklist"+claimForm.getClaimapplication().getIseligact());
claimForm.setClaimSubmittedDate(sysDate);
%>
<%
String nameOfOfficial="";
ClaimApplication claimApplication = new ClaimApplication();
nameOfOfficial=claimApplication.getNameOfOfficial();
System.out.println("cheklist"+nameOfOfficial);
claimApplication.getDesignationOfOfficial();
System.out.println("cheklist"+claimApplication.getDesignationOfOfficial());
claimApplication.getPlace();
					String thiskey2="CL12MGMGMMG";
					String reasData = "reasonData("+thiskey2+")";
					%>
<html:form action="updateClaimApplication.do?method=updateClaimApplication" method="POST" focus="<%=focusField%>" enctype="multipart/form-data">
<html:errors/>
<html:hidden name="cpTcDetailsForm" property="test"/>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    
    <tr>
      <td class="FontStyle">&nbsp;</td>
    </tr>
  </table>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr> 
    <td width="20" align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
      <td width="248" background="images/TableBackground1.gif"><img src="images/ClaimsProcessingHeading.gif" width="131" height="25"></td>
    <td align="right" valign="top" background="images/TableBackground1.gif"> 
      <div align="right"></div></td>
    <td width="23" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
  </tr>
  <tr> 
    <td width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</td>
    <td colspan="2">
      <DIV align="right">			
      		<A HREF="javascript:submitForm('helpDisclaimer.do')">
      	        HELP</A>
      </DIV>        
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
          <td><table width="100%" border="0" cellspacing="1" cellpadding="0">
                <tr> 
                  <td colspan="8"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td width="35%" height="20" class="Heading">&nbsp;<bean:message key="declarationandundertakingbymli"/></td>
                        <td align="left" valign="bottom"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                        <td>&nbsp;</td>
                      </tr>                      
                      <tr> 
                        <td colspan="4" class="Heading"><img src="images/Clear.gif" width="5" height="5"></td>
                      </tr>
                    </table></td>
                </tr>                                
                <tr> 
                  <td colspan="4" class="SubHeading"><br> &nbsp;<bean:message key="declaration"/></td>
                </tr>
                <tr> 
                  <td class="TableData" >&nbsp; <bean:message key="para1"/></td>
				 
                </tr>
                <tr> 
                  <td class="TableData" >&nbsp;<bean:message key="para2"/></td>
				  
                </tr>
                <tr> 
                  <td colspan="4" class="SubHeading"><br> &nbsp;<bean:message key="undertaking"/></td>
                </tr>
				<tr>
				<td class="TableData"> &nbsp;<bean:message key="undertaking1"/></td>
                </tr> 
				<tr>
				<td class="TableData"> &nbsp;<bean:message key="undertaking2"/></td>
                </tr> 
				<tr>
				<td class="TableData"> &nbsp;<bean:message key="undertaking3"/></td>
                </tr> 
				<tr>
				<td class="TableData"> &nbsp;<bean:message key="undertaking4"/></td>
                </tr> 
				</table></td>
			</tr>
			<tr> 
                  <td colspan="4"><img src="images/Clear.gif" width="5" height="15"></td>
                </tr>
				<tr> 
                  <td colspan="4"><table width="100%" border="0" cellspacing="1">
					<tr>
					<td class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="nameofofficial"/></td>
					<td class="TableData"><html:text property="nameOfOfficial" name="cpTcDetailsForm" maxlength="100"/></td>
					</tr>
					<tr>
					<td class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="designationOfOfficial"/>lll</td>
					<td class="TableData"><html:text property="designationOfOfficial" name="cpTcDetailsForm" maxlength="50"/></td>
					</tr>
					<tr>
					<td class="ColumnBackground">&nbsp;<bean:message key="mliName"/></td>
					<td class="TableData"><bean:write property="memberDetails.memberBankName" name="cpTcDetailsForm"/>, <bean:write property="memberDetails.memberBranchName" name="cpTcDetailsForm"/></td>
					</tr>
					<tr>
					<td class="ColumnBackground">&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="dateofclaimfiling"/></td>
					<td class="TableData" align="center"><!--<html:text property="claimSubmittedDate" name="cpTcDetailsForm" maxlength="10"/><img src="images/CalendarIcon.gif" width="20" onClick="showCalendar('cpTcDetailsForm.claimSubmittedDate')" align="center">--><bean:write property="claimSubmittedDate" name="cpTcDetailsForm"/></td>
					</tr>
					<tr>
					<td class="ColumnBackground">&nbsp;<bean:message key="place"/></td>
					<td class="TableData"><html:text property="place"  name="cpTcDetailsForm" maxlength="100"/></td>
					</tr>
						
				<tr>
				<td class="SubHeading"> &nbsp;<bean:message key="cgtsinote1"/></td>
                </tr> 
				<tr>
				<td class="SubHeading"> &nbsp;<bean:message key="cgtsinote2"/></td>
                </tr> 
              </table>
            </td>
        </tr>
      </table></td>
    <td width="23" background="images/TableVerticalRightBG.gif">&nbsp;</td>
  </tr>
  <tr> 
      <td width="20" align="right" valign="bottom"><img src="images/TableLeftBottom.gif" width="20" height="51"></td>
      <td colspan="2" valign="bottom" background="images/TableBackground3.gif"> 
        <div>
          <div align="center">          
          <A href="javascript:submitDeclaResubmit('updateClaimApplicationforResub.do?method=updateClaimApplicationforResub')"><img src="images/Save.gif" alt="Accept" width="49" height="37" border="0"></a>
          <a href="javascript:document.form1.reset()"><img src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></a>
          <a href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>"><img src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></a><div>
      </div></td>
      <td width="23" align="right" valign="bottom"><img src="images/TableRightBottom.gif" width="23" height="51"></td>
  </tr>
</table>
</html:form>
</body>
</html>
