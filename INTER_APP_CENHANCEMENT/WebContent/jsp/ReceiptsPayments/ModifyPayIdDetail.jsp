<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<%@ page import="com.cgtsi.util.SessionConstants"%>
<%
session.setAttribute("CurrentPage","modifyAllocatePaymentDetailInput.do?method=modifyAllocatePaymentDetailInput");

%>

<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	<html:errors />
	<html:form action="modifyAllocatePaymentDetail.do?method=modifyAllocatePaymentDetail" method="POST" focus="paymentId">
		<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<table width="661" border="0" cellspacing="1" cellpadding="0">
							 <TR>
								<TD colspan="7">
									<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										<TR>
											<TD width="51%" class="Heading">Modify Allocated Payment Id Details</TD>
											<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
										</TR>
										<TR>
											<TD colspan="6" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
										</TR>
									</TABLE>
								</TD>
							</TR>
							</table>
							
							<table width="661" border="0" cellspacing="1" cellpadding="1">
								<tr align="left">
									<td class="ColumnBackground" width="207">
										<div align="left">
										  &nbsp;<font color="#FF0000" size="2">*</font> Enter Payment Id
										</div>
									 </td>
									 <td class="TableData" width="343">
										<div align="left">
										  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										  <html:text property="paymentId" size="19" alt="Insert PaymentId" name="rpAllocationForm" maxlength="19" />
										</div>
									 </td>
								</tr>
							 </table>									
						</td>
					</tr>

					
					<TR >
						<TD align="center" valign="baseline" >
							<DIV align="center">								
							<A href="javascript:submitForm('modifyAllocatePaymentDetail.do?method=modifyAllocatePaymentDetail')"><IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0">	</A>					
							<!-- <IMG src="images/OK.gif" alt="OK" width="49" height="37" border="0"> -->
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