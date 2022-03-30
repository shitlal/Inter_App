<%@ page language="java"%>
<%@ page import="java.util.ArrayList"%>
<%@page import ="java.text.SimpleDateFormat"%>
<%@page import ="java.text.DecimalFormat"%>
<%@page import = "org.apache.struts.action.DynaActionForm"%>

<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/TLD/struts-logic.tld" prefix="logic" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","danReportDetails.do?method=danReportDetails");%>
<%SimpleDateFormat dateFormat=new SimpleDateFormat("dd/MM/yyyy");%>
<%DecimalFormat decimalFormat = new DecimalFormat("##########.##");%>

<% DynaActionForm dynaForm = (DynaActionForm)session.getAttribute("rsForm");
	String danId = request.getParameter("danValue");
	String danType = danId.substring(0,2);
	String date = request.getParameter("date");

	String value = (String)dynaForm.get("memberId");
	String bank = (String)request.getParameter("bankName");
	String zone = (String)request.getParameter("zoneName");
	if((zone == null) || (zone.equals("null")))
	{
		zone="";	
	}
	String branch = (String)request.getParameter("branchName");
	if(branch == null || branch.equals("null"))
	{
		branch="";
		
	}
%>




<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="danReport.do?method=danReport" method="POST" enctype="multipart/form-data">
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
									<TD colspan="8"> 
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">

											<TR>
												<TD  align="left" valign="top" class="DanReport">
												Dan Id:<%=danId%>
												</TD>

												<TD  align="left" valign="top" class="DanReport">
												Generated on:<%=date%> 
												</TD>

											 </TR>											

											<TR>
											 </TR>											

											<TR>
											 </TR>											

											<TR>
											 </TR>											


											<TR>
												<TD  align="left" valign="top" class="SubMenu">
												Bank:<%=bank%>
												</TD>
												<TD  align="left" valign="top" class="SubMenu">
												Zone:<%=zone%>
												</TD>
												<TD  align="left" valign="top" class="SubMenu">
												Branch:			    	
													<%=branch%>	     
												</TD>
											 </TR>

											 <TR>
											 </TR>

											<TR>
												<TD  class="Heading"  width="40%"><bean:message key="danReportDetailsHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD  width="20%" colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD>

									<TR>

									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="cgpanNumber" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="schemeName" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="applicationNumber" />
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="applicationDate"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="ssiName"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="totalSanctionAmount"/>
									</TD>
									<%
									if(danType.equals("CG"))
									{
									%>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="guaranteeFeeRs"/>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="newDanID"/>
									</TD>
									<%
									}
									else if(danType.equals("CL"))
									{
									%>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="claimAmount"/>
									</TD>
									<%
									}
									else if(danType.equals("SH"))
									{
									%>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="shortAmount"/>
									</TD>
									<%
									}
									else if(danType.equals("SF"))
									{
									%>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
										<bean:message key="sFee"/>
									</TD>
									<%
									}
									%>
									
									<%	double sum = 0;
										double totalSum = 0;
										double gFee = 0;
										double totalgFee = 0;
										double gFeePaid = 0;
										double totalgFeePaid = 0;
									%>
									</TR>	

									<tr>
									<logic:iterate id="object" name="rsForm" property="dan">
									<%
									     com.cgtsi.reports.DanReport dReport =  (com.cgtsi.reports.DanReport)object;
									%>
											<% gFeePaid=dReport.getGuaranteeFeePaid();
											   totalgFeePaid = totalgFeePaid + gFeePaid;
												//System.out.println("------------------");
												//System.out.println("gFeePaid:"+gFeePaid);
											    //System.out.println("totalgFeePaid:"+totalgFeePaid);
											%>
											
							
											<TR>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%String mli =dReport.getCgpan();
											  String url1 = "securitizationReportDetailsForCgpan.do?method=securitizationReportDetailsForCgpan&number="+mli;%>
											  <html:link href="<%=url1%>"><%=mli%></html:link>		
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
   											<%=dReport.getScheme()%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%=dReport.getApplicationNumber()%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%   java.util.Date utilDate=dReport.getApplicationDate();
													String formatedDate = null;
													if(utilDate != null)
													{
														 formatedDate=dateFormat.format(utilDate);
													}
													else
													{
														 formatedDate = "";
													}
											%>
											<%=formatedDate%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<%=dReport.getSsi()%>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">		
											<div align="right">
											   <% sum=dReport.getTotalAmount();
											   totalSum = totalSum + sum;%>
											   <%=decimalFormat.format(sum)%>
											   </div>
											</TD>
											<TD width="10%" align="left" valign="top" class="ColumnBackground1">						
											<div align="right">
											   <%gFee=dReport.getGuaranteeFee();
											   totalgFee = totalgFee + gFee;
											  if(gFee == 0)
											  {
											  %>
											   <%="-"%>
											   <%
											  }
											  else
											  {
											  %>
											 <%=decimalFormat.format(gFee)%>
											   <%
												}
											   %>
											   </div>
											</TD>

											<%
											if(danType.equals("CG"))
											{
											%>
												<TD width="10%" align="left" valign="top" class="ColumnBackground1">
												<%String newDanId=dReport.getDan();
												if((newDanId == null) || (newDanId.equals("")))
												{
												%>	
													<%=""%>
													<%
												}
													else
													{
													%>
													<%=newDanId%>
													
												<%}
											}
											%>
											</TD>
											</TR>
											</logic:iterate>

									<TR>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
									Total									
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
									
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
									
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
									
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
									
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
									<div align="right">
									<%=decimalFormat.format(totalSum)%>
									</div>
									</TD>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
									<div align="right">
									<%=decimalFormat.format(totalgFee)%>
									</div>
									</TD>
									<%
									if(danType.equals("CG"))
									{
									%>
									<TD width="10%" align="left" valign="top" class="ColumnBackground">
									</TD>
									<%
									}
									%>
									</TR>	
									</TABLE>

					</TD>
					</TR>
					<TR>
						<TD height="20" >
							&nbsp;
						</TD>
					</TR>
					 <TR>
					 <TD>
				<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
					<TR>
						<TD align="center" valign="baseline" class="ColumnBackground">
							<DIV align="left">
									<%
									if(danType.equals("CG"))
									{
									%>
									Total Guarantee Fee in Rs.
									<%
									}
									else if(danType.equals("CL"))
									{
									%>
										Total Amount recovered from Borrower in Rs.(Demanded)
									<%
									}
									else if(danType.equals("SH"))
									{
									%>
									Total Short Fee in Rs.
									<%
									}
									else if(danType.equals("SF"))
									{
									%>
									Total Service Fee in Rs.
									<%
									}
									%>									
							</DIV>
						</TD>
						<TD align="center" valign="baseline" class="ColumnBackground">
							<DIV align="left">
									<%=decimalFormat.format(totalgFee)%>
							</DIV>
						</TD>
					</TR>

					<TR>
						<TD align="center" valign="baseline" class="ColumnBackground">
							<DIV align="left">
									Amount Paid in Rs.
							</DIV>
						</TD>
						<TD align="center" valign="baseline" class="ColumnBackground">
							<DIV align="left">
									<%=decimalFormat.format(totalgFeePaid)%>
							</DIV>
						</TD>
					</TR>

					<TR>
						<TD  align="center" valign="baseline" class="ColumnBackground">
							<DIV align="left">
									Amount Payable in Rs.
									<%double amountPayable = totalgFee - totalgFeePaid;%>
									
							</DIV>
						</TD>
						<TD  align="center" valign="baseline" class="ColumnBackground">
							<DIV align="left">
									<%=decimalFormat.format(amountPayable)%>
							</DIV>
						</TD>
					</TR>
					</TABLE>
					</TD>
					</TR>
					<TR>
						<TD align="center" valign="baseline" >
							<DIV align="center">
								<A href="javascript:submitForm('danReport.do?method=danReport')">
									<IMG src="images/Back.gif" alt="Print" width="49" height="37" border="0"></A>

									
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

