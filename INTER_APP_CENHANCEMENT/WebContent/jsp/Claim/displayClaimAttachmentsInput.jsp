<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<title>CGTSI</title>
<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1">
<style type="text/css">
</style>
<LINK href="css/StyleSheet.css" rel="stylesheet" type="text/css">
<SCRIPT language="javaScript" src="js/selectdate.js" type=text/javascript></SCRIPT>
<SCRIPT language="JavaScript" type="text/JavaScript" src="js/CGTSI.js"></SCRIPT>

</head>
<% 
	session.setAttribute("CurrentPage","claimFilesAttachmentInput.do?method=claimFilesAttachmentInput");
%>
<body topmargin="0">
<html:errors />
<html:form action="getCgpanWiseDetailsForClmRefNo.do?method=getCgpanWiseDetailsForClmRefNo" method="POST" enctype="multipart/form-data">
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    
    <tr>
      <td class="FontStyle">&nbsp;</td>
    </tr>
  </table>
  <table width="100%" border="0" cellspacing="0" cellpadding="0">
    <tr> 
    <td width="20" align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
      <td width="248" background="images/TableBackground1.gif"></td>
    <td align="right" valign="top" background="images/TableBackground1.gif"> </td>
    <td width="23" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
  </tr>
  <tr> 
    <td width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</td>
    <td colspan="2"><table width="100%" border="0" cellspacing="0" cellpadding="0">
        <tr>
            <td> <table width="100%" border="0" cellspacing="1" cellpadding="0">
                <tr> 
                  <td colspan="4"><table width="100%" border="0" cellspacing="0" cellpadding="0">
                      <tr> 
                        <td width="25%" class="Heading"><a name="AD" id="AD"></a>&nbsp;Enter Claim Details</td>
                        <td align="left" valign="bottom"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                        <td>&nbsp;</td>
                        <td>&nbsp;</td>
                      </tr>
                      <tr> 
                        <td colspan="4" class="Heading"><img src="images/Clear.gif" width="5" height="5"></td>
                      </tr>
                    </table></td>
                </tr>
				
                <tr> 
                  <td width="30%" class="ColumnBackground"> <div align="left">&nbsp; Claim Reference Number</div></td>
                  <td class="TableData"> <div align="left"> 
					  <html:text name="cpTcDetailsForm" property="clmRefNumberNew"></html:text>
					      
                    </div></td>
                </tr>
                <tr> 
                  <td colspan="4"><img src="images/Clear.gif" width="5" height="15"></td>
                </tr>
              </table>
      </table></td>
    <td width="23" background="images/TableVerticalRightBG.gif">&nbsp;</td>
  </tr>
  <tr> 
      <td width="20" align="right" valign="bottom"><img src="images/TableLeftBottom.gif" width="20" height="51"></td>
      <td colspan="2" valign="bottom" background="images/TableBackground3.gif"> 
        <div>
          <div align="center">
          
<a href="javascript:submitForm('getCgpanWiseDetailsForClmRefNo.do?method=getCgpanWiseDetailsForClmRefNo')">
<img src="images/OK.gif" alt="OK" width="49" height="37" border="0"></a> 

          <a href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>"><img src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></a></div>
      </div></td>
      <td width="23" align="right" valign="bottom"><img src="images/TableRightBottom.gif" width="23" height="51"></td>
  </tr>
</table>
</html:form>
</body>
</html>
