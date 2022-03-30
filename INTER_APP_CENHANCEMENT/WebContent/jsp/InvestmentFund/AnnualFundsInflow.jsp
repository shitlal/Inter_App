<%@ page language="java"%>
<%@ page import="java.util.HashMap"%>
<%@ page import="java.util.Set"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="com.cgtsi.actionform.InvestmentForm"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<% session.setAttribute("CurrentPage","showAnnualFundsInflowDetails.do?method=showAnnualFundsInflowDetails");%>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0">
	<html:errors />
	<html:form action="setAnnualFundsInflowDetails.do?method=setAnnualFundsInflowDetails" method="POST" enctype="multipart/form-data" name="investmentForm"
	type="com.cgtsi.actionform.investmentForm" focus="dateOfFlow">

    <tr>
      <td width="20" align="right" valign="bottom"><img src="images/TableLeftTop.gif" width="20" height="31"></td>
      <td width="323" background="images/TableBackground1.gif"><img src="images/InvestmentManagementHeading.gif" width="169" height="25"></td>
      <td align="right" background="images/TableBackground1.gif"> </td>
      <td width="23" align="left" valign="bottom"><img src="images/TableRightTop.gif" width="23" height="31"></td>
    </tr>
    <tr>
      <td background="images/TableVerticalLeftBG.gif">&nbsp;</td>

      <td colspan="2"><table width="100%" border="0" cellspacing="1" cellpadding="0">
	  <tr>
	  <TD>			
			<DIV align="right">			
				<A HREF="javascript:submitForm('helpAnnualFundsInflowDetails.do?method=helpAnnualFundsInflowDetails')">
			    HELP</A>
			</DIV>
		</td>
	  </tr>
          <tr>
            <td><table width="100%" border="0" cellspacing="0" cellpadding="0">
                <tr>
                  <td width="30%" class="Heading">&nbsp; <bean:message key="inflowDetailsHeading"/></td>
                  <td colspan="3"><img src="images/TriangleSubhead.gif" width="19" height="19"></td>
                </tr>
                <tr>
                  <td colspan="4" class="Heading"><img src="images/Clear.gif" width="5" height="5"></td>
                </tr>
              </table></td>
          </tr>
          <tr>
            <td><table width="100%" border="0" cellspacing="1" cellpadding="0">
				<TR align="left" valign="top">
					<TD width="30%" valign="top" class="ColumnBackground">
						<DIV align="left">
						&nbsp;&nbsp;<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="dateOfFlow" />
						</DIV>
					</TD>
					<TD align="left" valign="top" class="TableData">
						<html:text property="dateOfFlow" size="20"  alt="Date Of Flow" name="investmentForm" maxlength="10"/>
						<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('investmentForm.dateOfFlow')" align="center">
						<a href="javascript:submitForm('getFundsInflowDetails.do?method=getFundsInflowDetails')">Get Inflow Details</a>
					</TD>
				</TR>
                <!-- <tr>
                  <td class="ColumnBackground">&nbsp;<bean:message key="year"/></td>
                  <td class="tableData">
				  </td>
                </tr> -->

								<%
									int noOfHeads=0;
									int noOfSubHeads=0;
									InvestmentForm ifForm=(InvestmentForm) session.getAttribute("investmentForm");
								%>

								<%
									HashMap heads=new HashMap(ifForm.getHeadsToRender());
									HashMap subHeads=new HashMap();
									String headTitle="";
									String subHeadTitle="";

									Set headsSet=heads.keySet();
									Iterator headsIterator=headsSet.iterator();
								%>

								<%
									int i = 0;
									while (headsIterator.hasNext())
									{
								%>

								<%
										headTitle=headsIterator.next().toString();
										if (heads.get(headTitle) == null)
										{
									%>

											<%
											//System.out.println("jsp: "+headTitle);
											String headKey="head("+headTitle+")";
											%>

				<tr>
					<td width="20%" valign="top" class="ColumnBackground">
						<div align="left">&nbsp;<%= headTitle%></div>
					</td>
					<td align="left" valign="top" class="TableData">
						<html:text name="investmentForm" property="<%=headKey%>" size="20" maxlength="16" onkeypress="return decimalOnly(this, event, 13)" onkeyup="isValidNumber(this)"/> &nbsp;<bean:message key="inRs" />
					</td>
				</tr>
				<%
					}
					else
					{
						//out.println("jsp getting sub heads");
											subHeads=(HashMap) heads.get(headTitle);
											Set subHeadsSet=subHeads.keySet();
											Iterator subHeadsIterator=subHeadsSet.iterator();
									%>
										<tr align="left" valign="top">
											<TD width="20%" valign="top" class="ColumnBackground" colspan="2" >
												<DIV align="left">&nbsp;<%=headTitle%>
												</DIV>
											</TD>

										</TR>
									<%
           				while (subHeadsIterator.hasNext())
							{	i++;
								subHeadTitle=subHeadsIterator.next().toString();
								//out.println("jsp "+subHeadTitle);
					%>

						<TR align="left" valign="top">
							<TD width="20%" valign="top" class="ColumnBackground">
								<DIV align="left">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
													&nbsp;<%= subHeadTitle%></DIV>
							</TD>
							<% String subHeadsName = "subHead("+headTitle+"_"+subHeadTitle+")"; %>

							<TD align="left" valign="top" class="TableData">
						<html:text name="investmentForm" property="<%=subHeadsName%>" size="20" maxlength="12" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/> &nbsp;<bean:message key="inRs" />
							</TD>
						</TR>
					<%
							}
						}
					}
				%>
			</table></td>
          </tr>
          <tr align="center" valign="baseline">
            <td> <div align="center">
		<A href="javascript:submitForm('setAnnualFundsInflowDetails.do?method=setAnnualFundsInflowDetails')">
			<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
		<html:link href="javascript:document.investmentForm.reset()"><img src="images/Reset.gif" alt="Reset" width="49" height="37" border="0"></html:link>
			<a href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>">
			<IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
			</div></td>
          </tr>
        </table></td>
		
      <td background="images/TableVerticalRightBG.gif">&nbsp;</td>
    </tr>
    <tr>
      <td width="20" align="right" valign="top"><img src="images/TableLeftBottom1.gif" width="20" height="15"></td>
      <td colspan="2" background="images/TableBackground2.gif"><div align="center"></div></td>
      <td align="left" valign="top"><img src="images/TableRightBottom1.gif" width="23" height="15"></td>
    </tr>
  </table>
</html:form>
