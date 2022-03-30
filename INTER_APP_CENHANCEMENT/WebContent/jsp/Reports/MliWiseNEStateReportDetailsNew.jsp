<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@page import="java.sql.Date"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@page import ="java.text.DecimalFormat"%>
<%DecimalFormat decimalFormat = new DecimalFormat("##########0.00");%>
<% session.setAttribute("CurrentPage","mliWiseNEStateReportDetailsNew.do?method=mliWiseNEStateReportDetailsNew");%> 
<%@page import = "org.apache.struts.action.DynaActionForm"%>
<% DynaActionForm dynaForm = (DynaActionForm)session.getAttribute("rsForm");
 String mliId = (String)dynaForm.get("memberId");
 System.out.println("mliId"+mliId);
%>


<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="mliWiseNEStateReportDetailsNew.do?method=mliWiseNEStateReportDetailsNew" method="POST" enctype="multipart/form-data">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/ReportsHeading.gif" width="121" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="10"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr>
                          <td colspan="6" class="Heading1" align="center"><u><!-- <bean:message key="reportHeader"/> --> Credit Guarantee Fund Trust for Micro And Small Enterprises </u></td>
                      </tr>
                      <tr> <td colspan="6">&nbsp;</td></tr>
                      <TR>
												<TD width="16%" class="Heading">NER State-wise Report </TD>
												<td class="Heading" width="53%">&nbsp;<bean:message key="from"/> <bean:write  name="rsForm" property="dateOfTheDocument12"/>&nbsp;<bean:message key="to"/> <bean:write  name="rsForm" property="dateOfTheDocument13"/></td>
                        <TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>
                    </TABLE>
									</TD>

	
				<tr> 
				<td colspan="12">
            <table width="100%" border="0" cellspacing="1" cellpadding="0">
             <tr> 
                  <td width="3%"  align="center" valign="middle" class="ColumnBackground "><div align="center">Sl.<br>No.</div></td>
                  <td width="20%" class="ColumnBackground "><div align="center"><bean:message key="stateName"/>
                 </div></td>
                  <td width="10%" class="ColumnBackground "><div align="center"><bean:message key="proposals"/>
                 </div></td>
                  <td class="ColumnBackground"  width="10%"> <div align="center">Approved Amount(In Rs.)
                </div></td>
                 <logic:equal property="bankId" value="0000" name="rsForm">
  
                   <td class="ColumnBackground"  width="10%"> <div align="center"><bean:message key="proposals"/>
                  </div></td>
                   <td class="ColumnBackground"  width="10%"> <div align="center">Issued Amount(In Rs.) </div></td>
                   </logic:equal>
            </tr> 
            
       </td>
        </tr>                       
        <tr>
        <td>
				<%
				      int serial = 0;
              double total = 0;
              int issuedcases = 0;
              double totalissued = 0;

				%>
        </td>
       </tr>
				<logic:iterate id="object" name="rsForm" property="mliWiseNEStateReportNew" indexId="index">
				<%
				com.cgtsi.reports.GeneralReport pReport = (com.cgtsi.reports.GeneralReport)object;
				%>
        <tr class="tableData">
				  <td><div align="center">&nbsp;<%= Integer.parseInt(index+"")+1%></div>
          </td>
          <TD align="left" valign="top" class="ColumnBackground1">
        <%  String state = pReport.getName();
          String url = "mliWiseNEDistrictReportDetailsNew1.do?method=mliWiseNEDistrictReportDetailsNew1&District="+state;%>
         <% int i = state.indexOf("&");
											if(i == -1){
                      %>
                   <html:link href="<%=url%>"><%= state%></html:link>
                   <%}else{
											String newState = state.replace('&','$');
											String newUrl = "mliWiseNEDistrictReportDetailsNew1.do?method=mliWiseNEDistrictReportDetailsNew1&District="+newState;
											%>
											<html:link href="<%=newUrl%>"><%= state%></html:link>
											<%} %>
           </td>
          <%
                  int count = pReport.getProposals();
           %>
        <td><div align="right">&nbsp;<%= count%></div></td>
        <% double amount = 0;
                  serial=serial+count;
                  amount = pReport.getAmount();
                  total = total+amount;
        %>

        <td><div align="right">&nbsp;<%= decimalFormat.format(amount)%></div></td>
        <logic:equal property="bankId" value="0000" name="rsForm">
         <%
                  int count1 = pReport.getProposal();
                  issuedcases = issuedcases+count1;
          %>

        <td><div align="right">&nbsp;<%=count1%></div></td>
        <%
                  double guaranteeIssued = 0;
                  guaranteeIssued=pReport.getCumAmount();
                  totalissued = totalissued+guaranteeIssued;
        %>

         <td><div align="right">&nbsp;<%= decimalFormat.format(guaranteeIssued)%></div></td>

     		  <td><div align="right">
          </div>
          </logic:equal>

    	</logic:iterate>
         				
				</TABLE>
				</TD>
        </TR>
        
                
          <td width = "36%" align = "right" colspan = "5" class="ColumnBackground ">&nbsp;TOTAL
          <td width = "18%" align = "center" class="ColumnBackground ">
              <div align="right"><%= serial%></</div></td>
				  <td width = "17%" align = "right" class="ColumnBackground ">
              <div align="right"><%= decimalFormat.format(total)%></</div></td>
          <logic:equal property="bankId" value="0000" name="rsForm">
          
          <td width = "22%" align = "right" class="ColumnBackground ">
              <div align="center"><%= issuedcases%></div></td>
          <td width = "19%" align = "right" class="ColumnBackground ">
              <div align="right"><%= decimalFormat.format(totalissued)%></div></td>
          </logic:equal>
               
				</TABLE>
         <tr class="tableData">
          



            </TR>
						</TD>
					</TR>
					<TR >

      
					</TR>
					<TR >
						<TD align="center" valign="baseline">
							<DIV align="center">
								<A href="javascript:submitForm('mliWiseNEStateReportNew.do?method=mliWiseNEStateReportNew')">
									<IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0"></A>
								
									<A href="javascript:printpage()">
									<IMG src="images/Print.gif" alt="Print" width="49" height="37" border="0"></A>
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


