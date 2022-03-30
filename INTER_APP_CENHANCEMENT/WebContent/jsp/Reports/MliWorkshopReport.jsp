<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@page import ="java.text.SimpleDateFormat"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","mliworkshopReport.do?method=mliworkshopReport");%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>
<%@page import ="java.text.DecimalFormat"%>
<%DecimalFormat decimalFormat = new DecimalFormat("#########0.0##");%>

<form name="addded" action="workshopReport.do?method=workshopReport">

<TABLE width="800" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="mliworkshopReport.do?method=mliworkshopReport" method="POST" enctype="multipart/form-data">
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
									<TD colspan="3"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<tr>
                          <td colspan="3" class="Heading1" align="center"><u>Credit Guarantee Fund Trust for Micro and Small Enterprises&nbsp;</u></td>
                      </tr>
                      <tr> <td colspan="3">&nbsp;</td></tr>
                      
                      <TR>
												<TD width="45%" class="Heading">MLI wise Scheme Propagation Report Details</TD>
												<td class="Heading" width="40%">&nbsp;<bean:message key="from"/> <bean:write  name="rsForm" property="dateOfTheDocument26"/>&nbsp;<bean:message key="to"/> <bean:write  name="rsForm" property="dateOfTheDocument27"/></td>
                        <TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>
        			</TABLE>
									</TD>

									<TR>
                  <td width="1%" align="left" valign="top" class="ColumnBackground"><bean:message key="sNo"/></td>
									
                  <TD width="25%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="bank"/></TD>                     
									
                 <TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="numberOfUnits"/></TD>  
									</TR>	
	                <% 
                      int count       = 0;
									  	int totalCount  = 0;
                  %>
									<tr>
								<logic:iterate name="rsForm" id="object" property="danRaised" indexId="index">
               	<%
									     com.cgtsi.reports.GeneralReport danReport = (com.cgtsi.reports.GeneralReport)object;
							  %>
										<TR>
                      <td align="left" valign="top" class="ColumnBackground1"><%=Integer.parseInt(index+"")+1%></td>
								       <TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%String bankName= danReport.getBankName();
											if((bankName == null)||(bankName.equals("")))
											{
											%>
											<%=""%>
											<%
											}
											else
											{
											%>
											<%=bankName%> |	
											<%
                      String url = "stateworkshopReportNew.do?method=stateworkshopReportNew&state="+bankName;
                      String url1 = "schemeworkshopReportNew.do?method=schemeworkshopReportNew&state="+bankName;
									 %>
                    <html:link href="<%=url%>">StateWise</html:link> |  <html:link href="<%=url1%>">Scheme Propagation</html:link>
                    <%	}
											%> 
               			</TD>
                    <TD width="10%" align="left" valign="top" class="ColumnBackground1">&nbsp;<% count=danReport.getProposals();
                      totalCount = totalCount+count;
                    %><%=count%></TD>                    
											
																
											</TR>
											</logic:iterate>
        			<tr>
									<TD align="left" valign="top" colspan="2" class="ColumnBackground">
									Total
									</TD>
									<TD align="left" class="ColumnBackground"> 
									<div align="left">
									<%=totalCount%>
									</div>
									</TD>
                  </tr>
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
								<A href="javascript:submitForm('mliwiseWorkshopReportInput.do?method=mliwiseWorkshopReportInput')">
									<IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0">									
								<!-- <INPUT type="button" value="Export to Excel" onclick="exportToExcel()" />    -->            
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
</form>
