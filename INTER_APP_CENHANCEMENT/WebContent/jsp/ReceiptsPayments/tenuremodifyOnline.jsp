<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<script>


function myFunction() {
    var mode = document.getElementById("asf").value;
    //alert(mode)
    
        document.forms[0].target = "_self";
		document.forms[0].method = "POST";
		//document.forms[0].action = "asfdisplayallocatePaymentModifySubmit.do?method=asfdisplayallocatePaymentModifySubmit&mode="+mode;
		document.forms[0].action = "tenuredisplayallocatePaymentModifySubmit.do?method=tenuredisplayallocatePaymentModifySubmit&mode="+mode;
		document.forms[0].submit();
   // document.getElementById("demo").innerHTML = "You selected: " + x;
}

</script>
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="asfmodifyonline.do?method=asfmodifyonline" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
			<DIV align="right">			
		
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
				
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="8"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="22%" class="Heading"><bean:message key="danReportHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>
										</TABLE>
									</TD>
						
									<TR>
									<TD align="left" valign="top" class="ColumnBackground">
										  &nbsp;Please Select One Module For Modify
									</TD>
									
									 <TD align="left" valign="center" class="TableData">
															
									<select id="asf" onChange="myFunction()">
									<option value="">select</option>
                                    <option value="TNTAF" >Modify For Tenure AF DANs</option>
                                    <option value="TNTSF">Modify For Tenure SF DANs</option>
                                  
</select>
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

