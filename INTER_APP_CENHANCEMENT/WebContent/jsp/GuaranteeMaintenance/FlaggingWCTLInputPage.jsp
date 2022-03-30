<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic"%>
<% session.setAttribute("CurrentPage","flaggingWctlRequestInput.do?method=flaggingWctlRequestInput");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%
String focusField="";
%>
<logic:equal property="bankIdForClosure" value="0000" name="gmPeriodicInfoForm">
<%focusField = "memberIdForClosure";%>
</logic:equal>

<script language="javascript" type="text/javascript"> 

//var request; 
function submitForm1()
{

	

	//alert("dfdfdfdfdf00");
	
	var fname=document.getElementById('cgpanForClosure').value;
	var cgpan = fname.substr(fname.length -2);
	//alert("cgpan=="+cgpan);
	
	
	 if(cgpan==""||cgpan==null){
	        
	       alert("Enter CGPAN.");
	     

	  }
	
	 else if(cgpan=="TC"){
       alert("Only Working Capital allowed for flagging of WCTL.");

        }

   
        
  
    else{
    	document.forms[0].action="flaggingWctlRequestDetails.do?method=flaggingWctlRequestDetails";
    	document.forms[0].target="_self";
    	document.forms[0].method="POST";
    	document.forms[0].submit();
    }
    	
       


}
</script>
<HTML>


<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="flaggingWctlRequestDetails.do?method=flaggingWctlRequestDetails" method="POST" enctype="multipart/form-data" focus="<%=focusField%>">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/GuaranteeMaintenanceHeading.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			<DIV align="right">			
	<!--			<A HREF="javascript:submitForm('helpClosureDetailsFilter.do?method=helpClosureDetailsFilter')">
			    HELP</A> -->
			</DIV>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="4"> 
                					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
                            <tr>
			<TD  align="left" colspan="4"><font size="2"><bold>
				Fields marked as </font><font color="#FF0000" size="2">*</font><font size="2"> are mandatory </bold></font>
			</td>
		</tr>
					
											<TR>
												<TD width="31%" class="Heading"><bean:message key="modifyBorrowerHeader"/></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>
								</TR>
                
						
								<TR align="left" valign="top">
									
                  <TD align="left" valign="top" class="ColumnBackground"><font color="#FF0000" size="2">*</font><bean:message key="cgpan"/>
									</TD>
									<TD align="left" valign="top" class="TableData">
									 <html:text property="cgpanForClosure" size="20" alt="CGPAN" name="gmPeriodicInfoForm" styleId="cgpanForClosure"  maxlength ="15" />
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
								
									<A href="javascript:submitForm1('flaggingWctlRequestDetails.do?method=flaggingWctlRequestDetails')">
									<IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0"></A>
									<A href="javascript:document.gmPeriodicInfoForm.reset()">
									<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>

								<A href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>">
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
	</BODY>
</HTML>