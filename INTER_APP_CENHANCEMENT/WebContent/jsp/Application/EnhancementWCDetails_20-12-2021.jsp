<%@ page import="com.cgtsi.util.SessionConstants"%>
<!--start of enhance WC-->
	<table width="100%" border="0" cellspacing="1" cellpadding="0">
								<TR>
									<TD colspan="7">
										<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
											<TR>
												<TD width="31%" class="Heading"><bean:message key="facilityDetails" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="6" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>
										</TABLE>
									</TD>
								</TR>
								<tr align="left">
									<td class="ColumnBackground">
										<bean:message key="facilityRehabilitation" />
									</td>
									<td class="TableData" colspan="6">
									<%
									String appWCEFlag=(String)session.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG);
									if(appWCEFlag.equals("1"))
									{
									%>
										<bean:write name="appForm" property="rehabilitation"/>
									<%
									}
									else
									{%>
										<html:radio name="appForm" value="Yes" property="rehabilitation" ><bean:message key="yes" /></html:radio>
										<html:radio name="appForm" value="No" property="rehabilitation" ><bean:message key="no" /></html:radio>
									<%}%>
									</td>
								</tr>
								<tr align="left">
									<td class="ColumnBackground">&nbsp;<bean:message key="compositeLoan" />
									</td>
									<td colspan="6" class="TableData">&nbsp;No
									</td>
								</tr>
								<tr align="left">
									<td width="30%" class="ColumnBackground"><div align="left">&nbsp;
										<bean:message key="loanType" /></div>
									</td>
									<td colspan="6" class="TableData"><div align="left">&nbsp;Working Capital Enhancement</div>
									</td>
								</tr>
								<tr>
									<td class="ColumnBackground">&nbsp;<bean:message key="scheme" />
									</td>
									<td class="TableData" colspan="6">&nbsp;
									<%
										if (session.getAttribute(SessionConstants.MCGF_FLAG).equals("M"))
										{%>
										MCGF
										<%}
										else
										{%>
										CGFSI<%}%>

									</td>               
								</tr>
								 <TR>
									<TD class="SubHeading" width="843" colspan="6"><bean:message key="workingCapitalEnhancement" />
									</TD>						
								</TR>			
								<tr align="left"> 
									<td class="ColumnBackground" height="28">&nbsp;
										<bean:message key="interestType" />
									</td>
									<td class="TableData" colspan="6">
									
										<html:radio name="appForm" value="T" property="wcInterestType" >
										</html:radio>
									
										<bean:message key="fixedInterest" />&nbsp;
									
										<html:radio name="appForm" value="F" property="wcInterestType" ></html:radio>	
									
										<bean:message key="floatingInterest" />	
									
										&nbsp;
									</td>
								</tr>
										
								<TR align="left">
									<td class="ColumnBackground" >
										<bean:message key="existingFundBased"/>
									</td>
									<td class="TableData" id="wcFundBased">
									<%									
									if(appWCEFlag.equals("1"))
									{
									%>
										<bean:write name="appForm" property="existingFundBasedTotal"/>
									<%
									}
									else
									{%><html:hidden property="exposurelmtAmt" name="appForm"/>		
										<html:text property="wcFundBasedSanctioned" size="20" alt="fundBasedLimitSanctioned" name="appForm" disabled="true" onkeypress="return numbersOnly(this, event,13)" onkeyup="isValidNumber(this)"/>
										<%}%>
									</td>
									<div id="FBerrorsMessage" class="errorsMessage"/>
									<td class="ColumnBackground">
										<bean:message key="limitFundBasedInterest"/>
									</td>
									<td class="TableData" colspan="3">
									<%									
									if(appWCEFlag.equals("1"))
									{
									%>
										<bean:write name="appForm" property="limitFundBasedInterest"/>&nbsp&nbsp<bean:message key="inPa" />
									<%
									}
									else
									{%>
										<html:text property="limitFundBasedInterest" size="20" alt="limitFundBasedInterest" name="appForm" disabled="true" onkeypress="return decimalOnly(this, event)" onkeyup="isValidDecimal(this)"/>
										<%}%>
									</td>
								</TR>
								<TR align="left">
									<td class="ColumnBackground" >
										<bean:message key="existingNonFundBased"/>
									</td>
									<td class="TableData" id="wcNonFundBased">
									<%									
									if(appWCEFlag.equals("1"))
									{
									%>
										<bean:write name="appForm" property="existingNonFundBasedTotal"/>
									<%
									}
									else
									{%>
										<html:text property="wcNonFundBasedSanctioned" size="20" alt="nonFundBasedLimitSantioned" name="appForm" disabled="true" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
										<%}%>
									</td>
									<td class="ColumnBackground">
										<bean:message key="limitNonFundBasedCommission"/>
									</td>
									<td class="TableData" colspan="3">
									<%									
									if(appWCEFlag.equals("1"))
									{
									%>
										<bean:write name="appForm" property="limitNonFundBasedCommission"/>
									<%
									}
									else
									{%>
										<html:text property="limitNonFundBasedCommission" size="20" alt="limitNonFundBasedCommission" name="appForm" disabled="true" onkeypress="return decimalOnly(this, event)" onkeyup="isValidDecimal(this)"/>
										<%}%>
									</td>
								</TR>	
								
	               						
					<TR align="left">		    								
									<td class="ColumnBackground" >
										<bean:message key="enhancedFundBased"/>
									</td>
									<td class="TableData" id="enhancedFundBased">
									<%
										if(appWCEFlag.equals("1"))

										{
										%>
										<bean:write name="appForm" property="enhancedFundBased"/>
										<%}%>
								</td>
				
									
									<td class="ColumnBackground">
										&nbsp;<bean:message key="enhancedFBInterest"/>
									</td>
									<td class="TableData" colspan="3">
										<html:text property="enhancedFBInterest" size="20" alt="enhancedFBInterest" name="appForm" onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)" maxlength="5"/>
									</td>
								</TR>
								<TR align="left">
									<td class="ColumnBackground" >
										<bean:message key="enhancedNonFundBased"/>
									</td>
									<td class="TableData" id="enhancedNonFundBased">
									<%
										if(appWCEFlag.equals("1"))

										{
										%>
										<bean:write name="appForm" property="enhancedNonFundBased"/>
										<%}%>

									</td>
									<td class="ColumnBackground">
										<bean:message key="enhancedNFBComission"/>
									</td>
									<td class="TableData" colspan="3">
										<html:text property="enhancedNFBComission" size="20" alt="enhancedNFBComission" name="appForm" onkeypress="return decimalOnly(this, event)" onkeyup="isValidDecimal(this)"/>
									</td>
								</TR>	
								
								
						<!-- added by DKR for hybird -->	
						<TR align="left">		    								
									<td class="ColumnBackground" >
									<bean:message key="creditFundBased" />(Credit to be guaranteed)
									</td>
									<td class="TableData" id="enhancedFundBased">
									<%
										if(appWCEFlag.equals("1"))

										{
										%>
										<html:text property="creditFundBased" styleId="creditFundBased" size="10" alt="creditFundBased" name="appForm" maxlength="16" onkeypress="isValidDecimal(this);" onblur="checkGurentyMaxtotalMIcollatSecAmt();"  onkeyup="return enableExtGreenFld('WCE');"/>&nbsp;
										<html:hidden property="creditFundBased"  styleId="creditFundBasedhid" name="appForm"/> <bean:message key="inRs" />	
										<%}%>
								</td>								
								<td class="ColumnBackground">
										&nbsp;<bean:message key="limitFundbasedSanctionedDate" /></td>
									<td class="TableData" colspan="3">
									<html:text  name="appForm"  property="limitFundBasedSanctionedDate"  styleId="limitFundBasedSanctionedDate" size="7" alt="limitFundBasedSanctionedDate" maxlength="10" />
										<IMG src="images/CalendarIcon.gif" width="20"  onClick="showCalendar('appForm.limitFundBasedSanctionedDate'),enableExtGreenFld('WCE');" align="center">
									</td>  
								</TR>	
								
								
								<TR align="left">		    								
									<td class="ColumnBackground" >
										<bean:message key="creditNonFundBased" />(Credit to be guaranteed)
									</td>
									<td class="TableData" id="enhancedFundBased">
									<%
										if(appWCEFlag.equals("1"))

										{
										%>
										   <html:text property="creditNonFundBased" size="20"	alt="creditNonFundBased" name="appForm"  readonly="readonly"/>
										<%}%>
								</td>								
								<td class="ColumnBackground">
										&nbsp;<bean:message key="limitFundbasedSanctionedDate" /></td>
									<td class="TableData" colspan="3">
										  <html:text  name="appForm"  property="limitNonFundBasedSanctionedDate"  styleId="limitNonFundBasedSanctionedDate" size="7" alt="limitNonFundBasedSanctionedDate" maxlength="10" />
										<IMG src="images/CalendarIcon.gif" width="20"  onClick="showCalendar('appForm.limitNonFundBasedSanctionedDate'), enableExtGreenFld('WCE');" align="center">
							        </td>  
								</TR>							
						<!-- Hybrid End -->		
								
								
								
										
								
<!--								<TR align="left">
									<td class="ColumnBackground" >
										<bean:message key="enhancedTotal"/>
									</td>
									<td class="TableData" colspan="7" id="enhancedTotal">
										
									</td>
								</TR>-->
								<TR align="left">
									<td class="ColumnBackground" >
										<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="enhancementDate"/>
									</td>
									<td class="TableData" colspan="7">
										<html:text property="enhancementDate" size="20" alt="enhancementDate" name="appForm" maxlength="10"/>
										<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('appForm.enhancementDate')" align="center">
									</td>
								</TR>
									<!-- DKR 32 Start-->
						   <%							    
							//	if((loanType_d.equals("WC") || loanType_d.equals("BO")) || loanType_d.equals("WCE") && !loanType_d.equals("WCR") && !loanType_d.equals("TCE"))		
                            //    {
						      %> 
						      
						      <!-- DKR 32-->
							  <!-- 	=============== FINANCIAL RECORDS =========================== -->
					       		<TR>
								  <TD colspan="4">
									<TABLE width="100%" id="financialOtherDtlLblId" 
										<%
											String gFinancialUIflag="DFALSEUI";
												if(null!=session.getAttribute("gFinancialUIflag")){
													gFinancialUIflag = (String)session.getAttribute("gFinancialUIflag");		
													System.out.println("gFinancialUIflag>>>>>>>>1>>>>>>"+gFinancialUIflag);
									   }						
								    	if(gFinancialUIflag.equalsIgnoreCase("DTRUEUI")){  System.out.println("gFinancialUIflag>>>>>2>>>>DTRUEUI>>>>>"+gFinancialUIflag); %>
								    	
								    	 style="display: block;"
                                        <%}else if(gFinancialUIflag.equalsIgnoreCase("DFALSEUI")){     	System.out.println("gFinancialUIflag>>>>>>3>>>DFALSEUI>>>>>"+gFinancialUIflag);
                                        %>
								    	  style="display:none;" <%}%> >	
								   	 
								<tr align="left">
						          <TD align="left" valign="top" class="ColumnBackground"  colspan="2"><font color="#FF0000" size="2">*</font>
						          <bean:message	key="inCrilcCibilRbi" /></TD>
								<TD align="left" valign="top" class="tableData">
								<% 	if (appWCEFlag.equals("1")) { %>
														<bean:write name="appForm" property="promDirDefaltFlg" />
														<% } else {
																	%>
								<html:radio	name="appForm" value="Y" property="promDirDefaltFlg" styleId="promDirDefaltFlg_Y" />
										 <bean:message key="yes" />&nbsp;&nbsp;
										  <html:radio name="appForm" value="N" property="promDirDefaltFlg" styleId="promDirDefaltFlg_N" />
									<bean:message key="no" />
									 <%	} %> 
									</TD>	
						       </tr>     
					             <tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font>
										<bean:message key="credBureName1" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<% 	if (appWCEFlag.equals("1")) { %>
														<bean:write name="appForm" property="credBureName1" />
														<% } else {
																	%> <html:text property="credBureName1" size="20"
												alt="credBureName1" name="appForm"/>
												 <%	} %> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> <bean:message
												key="credBureScorKeyProm" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCEFlag.equals("1")) {%>
														<bean:write name="appForm" property="credBureKeyPromScor" />
														<% } else {
																	%> <html:text property="credBureKeyPromScor" size="20"
												alt="credBureKeyPromScor" name="appForm" maxlength="3" /> <bean:message key="3to9" /><%
																		}
																	%> 
										</td>
									</tr>						       						
									<tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2"></font><bean:message
												key="credBureName2" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCEFlag.equals("1")) { %>
														<bean:write name="appForm" property="credBureName2" />
														<% } else {
																	%> <html:text property="credBureName2" size="20"
												alt="credBureName2" name="appForm" /> <%
																		}
																	%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2"></font> <bean:message
												key="credBureScoreProm2" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCEFlag.equals("1")) { %>
														<bean:write name="appForm" property="credBurePromScor2" />
														<% } else {
																	%> <html:text property="credBurePromScor2" size="20"
												alt="credBurePromScor2" name="appForm" maxlength="3"
												onblur="javascript:calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> <%
																		}
																	%> <bean:message key="3to9" />
										</td>
									</tr>								
									<tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2"></font><bean:message
												key="credBureName3" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%	if (appWCEFlag.equals("1")) { %>
														<bean:write name="appForm" property="credBureName3" />
														<% } else {
																	%> <html:text property="credBureName3" size="20"
												alt="credBureName3" name="appForm" /> <%
																		}
																	%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2"></font> <bean:message
												key="credBureScoreProm3" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCEFlag.equals("1")
													) {%>
														<bean:write name="appForm" property="credBurePromScor3" />
														<% } else {
												%> <html:text property="credBurePromScor3" size="20"
												alt="credBurePromScor3" name="appForm" maxlength="3"
												onblur="javascript:calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" />
												 <%
													}
												 %> 
												 <bean:message key="3to9" />
										</td>
									</tr>							
									
									<tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2"></font><bean:message
												key="credBureName4" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCEFlag.equals("1")) { %>
														<bean:write name="appForm" property="credBureName4" />
														<% } else {
																	%> <html:text property="credBureName4" size="20"
												alt="credBureName4" name="appForm" /> <%
																		}
																	%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2"></font> <bean:message
												key="credBureScoreProm4" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCEFlag.equals("1")) {%>
														<bean:write name="appForm" property="credBurePromScor4" />
														<% } else {
																	%> <html:text property="credBurePromScor4" size="20"
												alt="credBurePromScor4" name="appForm" maxlength="3"
												onblur="javascript:calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> <%
																		}
																	%> <bean:message key="3to9" />
										</td>
									</tr>									
									<tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2"></font><bean:message
												key="credBureName5" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCEFlag.equals("1")) {%>
														<bean:write name="appForm" property="credBureName5" />
														<% } else {
																	%> <html:text property="credBureName5" size="20"
												alt="credBureName5" name="appForm"  /> <%
																		}
																	%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2"></font> <bean:message
												key="credBureScoreProm5" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCEFlag.equals("1")) { %>
														<bean:write name="appForm" property="credBurePromScor5" />
														<% } else {
																	%> <html:text property="credBurePromScor5" size="20"
												alt="credBurePromScor5" name="appForm" maxlength="3"
												onblur="javascript:calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> <%
																		}
												%> <bean:message key="3to9" />
										</td>
									</tr>
									<tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message
												key="cibMSMERankFirm" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCEFlag.equals("1")) {
																	%> <bean:write property="cibilFirmMsmeRank"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="cibilFirmMsmeRank" size="20"
												alt="credBureName5" name="appForm" maxlength="2"
												onblur="javascript:calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> value between(1-10) <%
																		}
																	%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> <bean:message
												key="expCommercialScore" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCEFlag.equals("1")) { %>
														<bean:write name="appForm" property="expCommerScor" />
														<% } else {
																	%> <html:text property="expCommerScor" size="20"
												alt="credBurePromScor5" name="appForm" maxlength="3"
												onblur="javascript:calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> <%
																		}
												%> <bean:message key="3to9" />
										</td>
									</tr>
									<tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message
												key="promNetworth" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%	if (appWCEFlag.equals("1")) { %>
														<bean:write name="appForm" property="promBorrNetWorth" />
														<% } else {	%> <html:text property="promBorrNetWorth" size="20"
												alt="promBorrNetWorth" name="appForm" 
												onblur="javascript:calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> (in Rs. Lacs)
												 <%
													}
												%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> <bean:message
												key="contributPromEntity" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCEFlag.equals("1")) {%>
														<bean:write name="appForm" property="promContribution" />
														<% } else {
																	%> <html:text property="promContribution" size="20"
												alt="promContribution" name="appForm" maxlength="3"
												onblur="javascript:calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" />(Between 0-100%)
											 <%
												}
												%> 
										</td>
									</tr>
								   <tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message
												key="promNpainPast" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCEFlag.equals("1")
													) { %>
														<bean:write name="appForm" property="promGAssoNPA1YrFlg" />
														<% }else {
											%>
											 <html:radio name="appForm" value="Y" property="promGAssoNPA1YrFlg"/>
											 <bean:message key="yes" />&nbsp;&nbsp;
											  <html:radio name="appForm" value="N" property="promGAssoNPA1YrFlg" />
										      <bean:message key="no" />
										 <%
											}
										%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> <bean:message
												key="promExpRelBusiness" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCEFlag.equals("1")
													) { %>
														<bean:write name="appForm" property="promBussExpYr" />
														<% } else {
																	%> <html:text property="promBussExpYr" size="20"
												alt="promContribution" name="appForm" maxlength="3"
												onblur="javascript:calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" />(Between 0-100)
											 <%
												}
												%> 
										</td>
									</tr>									
									<tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message
												key="salesRevenue" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%	if (appWCEFlag.equals("1")
													) { %>
														<bean:write name="appForm" property="salesRevenue" />
														<% } else {
																	%> <html:text property="salesRevenue" size="20"
												alt="salesRevenue" name="appForm" onblur="javascript:calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> (in Rs. Lacs)
												 <%
													}
												%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> <bean:message
												key="taxPBIT" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCEFlag.equals("1")
													)  {%>
														<bean:write name="appForm" property="taxPBIT" />
														<% } else {
																   %> <html:text property="taxPBIT" size="20"
												alt="taxPBIT" name="appForm" onblur="javascript:calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" />(in Rs. Lacs)
											    <%
												}
												%> 
										</td>
									</tr>
									
									<tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message
												key="interestPayment" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											 <%	if (appWCEFlag.equals("1")
														) {%>
															<bean:write name="appForm" property="interestPayment" />
															<%}else {
												%> <html:text property="interestPayment" size="20"
												alt="interestPayment" name="appForm" onblur="javascript:calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> (in Rs. Lacs)
												 <%
													}
												%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> <bean:message
												key="taxCurrentProvisionAmt" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCEFlag.equals("1")
													) {%>
														<bean:write name="appForm" property="taxCurrentProvisionAmt" />
														<% } else {
																	%> <html:text property="taxCurrentProvisionAmt" size="20"
												alt="taxCurrentProvisionAmt" name="appForm" 
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" />(in Rs. Lacs)
											 <%
												}
												%> 
										</td>
									</tr>
									
									
									<tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message
												key="totCurrentAssets" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCEFlag.equals("1")){%>
														<bean:write name="appForm" property="totCurrentAssets" />
														<% } else {
																	%> <html:text property="totCurrentAssets" size="20"
												alt="totCurrentAssets" name="appForm" 
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> (in Rs. Lacs)
												 <%
													}
												%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> <bean:message
												key="totCurrentLiability" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCEFlag.equals("1")){%>
														<bean:write name="appForm" property="totCurrentLiability" />
														<% } else {
																	%> <html:text property="totCurrentLiability" size="20"
												alt="totCurrentLiability" name="appForm" 
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" />(in Rs. Lacs)
											 <%
												}
												%> 
										</td>
									</tr>
									
									
									<tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message
												key="totTermLiability" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCEFlag.equals("1")){ %>
														<bean:write name="appForm" property="totTermLiability" />
														<% 	} else { %> <html:text property="totTermLiability" size="20"
												alt="totTermLiability" name="appForm" 
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> (in Rs. Lacs)
												 <%
													}
												%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> <bean:message
												key="exuityCapital" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCEFlag.equals("1")
													) {%>
														<bean:write name="appForm" property="exuityCapital" />
														<% } else {
																	%> <html:text property="exuityCapital" size="20"
												alt="exuityCapital" name="appForm" 
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" />(in Rs. Lacs)
											 <%
												}
												%> 
										</td>
									</tr>
									
									<tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message
												key="preferenceCapital" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCEFlag.equals("1")){ %>
														<bean:write name="appForm" property="preferenceCapital" />
														<% } else {
																	%> <html:text property="preferenceCapital" size="20"
												alt="preferenceCapital" name="appForm" 
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> (in Rs. Lacs)
												 <%
													}
												%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> <bean:message
												key="reservesSurplus" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCEFlag.equals("1")
													) {%>
														<bean:write name="appForm" property="reservesSurplus" />
														<% } else {
																	%> <html:text property="reservesSurplus" size="20"
												alt="exuityCapital" name="appForm" 
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" />(in Rs. Lacs)
											 <%
												}
												%> 
										</td>
									</tr>
									
									<tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message
												key="repaymentDueNyrAmt" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCEFlag.equals("1")
													) {%>
														<bean:write name="appForm" property="repaymentDueNyrAmt" />
														<% } else {
																	%> <html:text property="repaymentDueNyrAmt" size="20"
												alt="repaymentDueNyrAmt" name="appForm" 
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> (in Rs. Lacs)
												 <%
													}
												%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<!--  <bean:message
												key="reservesSurplus" /> -->
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCEFlag.equals("1")
													) {
																	%> <!-- <bean:write property="reservesSurplus"
												name="appForm" /> --> <%
																		} else {
																	%> <!-- <html:text property="reservesSurplus" size="20"
												alt="exuityCapital" name="appForm" maxlength="16"
												onblur="javascript:calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" />(in Rs. Lacs) -->
											 <%
												}
											 %> 
										</td>
									</tr>
					            	</table>
		                          </TD>
								</TR>
								 <!--CR 158 End -->	
								
 <%
						      //session.setAttribute("dblockUI","UI_2");
						      
						      String dblockUIEnable="";
								if(null!=session.getAttribute("dblockUI")){
									dblockUIEnable = (String) session.getAttribute("dblockUI");	
									System.out.println("dblockUIEnable>>>>>>>>1>>>>>>"+dblockUIEnable);
								}
						      %>						       
						       <TR>						       
								<TD colspan="4">																
								<!--  <table width="100%" id="existGreenUnitUI_1to10L" style="display:none;">	 -->								 
								 <%-- <TABLE width="100%" id="existGreenFldUnitType_id" 
										<%
											String gExgGreenUIflag="RFALSEUI";
												if(null!=session.getAttribute("gExgGreenUIFlag")){
													gExgGreenUIflag = (String)session.getAttribute("gExgGreenUIFlag");		
													System.out.println("gExgGreenUIflag>>>>>>>>1>>>>>>"+gExgGreenUIflag);
									   }						
								    	if(gExgGreenUIflag.equalsIgnoreCase("RTRUEUI")){  System.out.println("gExgGreenUIflag>>>>>2>>>>RTRUEUI>>>>>"+gExgGreenUIflag); %>
								    	
								    	 style="display: block;"
                                        <%}else if(gExgGreenUIflag.equalsIgnoreCase("RFALSEUI")){     	System.out.println("gExgGreenUIflag>>>>>>3>>>RFALSEUI>>>>>"+gExgGreenUIflag);
                                        %>
								    	  style="display:none;" <%}%> >  --%>
								    	  
								   <TABLE width="100%" id="existGreenFldUnitType_id"  	
								    	  <%																
								    	if(dblockUIEnable.equalsIgnoreCase("UI_1") || dblockUIEnable.equalsIgnoreCase("UI_2")||dblockUIEnable.equalsIgnoreCase("UI_3")){
								    		//System.out.println(dblockUIEnable+".................................UI_1.");
								    	%>								    	
								    	 style="display: block;"
                                        <%}else{ 
                                        %>
								    	  style="display:none;" <%}%>
								    >						 
								  <tr align="left"  >
						          <TD align="left" valign="top" class="ColumnBackground"  colspan="2"><font color="#FF0000" size="2">*</font>
								           <bean:message key="existGreenFieldUnits" /></TD>
									     	<TD align="left" valign="top" class="tableData" colspan="2">
									     	<% if (appWCEFlag.equals("1")
													) { 
														%> 														   
														<bean:write name="appForm" property="existGreenFldUnitType" />
														<%} else { %> 
										  <html:radio	property="existGreenFldUnitType" name="appForm" value="Existing"/>
										   <bean:message key="existUnt" />&nbsp;&nbsp;
										   <html:radio  property="existGreenFldUnitType" name="appForm" value="Greenfield" />
											<bean:message key="GreenUnt" />
											 <%	} %> 
											</TD>	
						       </tr>
						            <tr align="left"  id="existGreenUnitUI_1to10L">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font>
										<bean:message key="oprtInc" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCEFlag.equals("1")) {%>
											 <bean:write name="appForm" property="credBureName5" />
											<% } else {
											 %> <html:text property="opratIncome" size="20" alt="opratIncome" name="appForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
											<%	} %> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> 
										<bean:message key="pat" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCEFlag.equals("1")) {%>
														<bean:write name="appForm" property="profAftTax" />
														<% } else {
											%> <html:text property="profAftTax" size="20" alt="profAftTax" name="appForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
											 <%
												}
												%> 
										</td>								
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message
												key="netwrth" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCEFlag.equals("1")
													) { %>
														<bean:write name="appForm" property="networth" />
														<% } else {
											 %> <html:text property="networth" size="20" alt="networth" name="appForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
											 <%	} %> 
										</td>
									</tr>								
									</table> 
									</TD></TR>
									
								  <TR>						       
								  <TD colspan="4">		
								  <!--  <table width="100%"  id="existGreenUnitUI_10to50L" style="display:none;"> -->
								     <TABLE width="100%" id="existGreenUnitUI_10to50L"  	
								    	 <%																
								    	if(dblockUIEnable.equalsIgnoreCase("UI_2") ){								    		
								    	//System.out.println(dblockUIEnable+"................D2.................UI_2.");
								    	%>										    							    	
								    	 style="display: block;"
                                        <%}else{ 
                                        %>
								    	  style="display:none;" <%}%>
								     >								   
						              <tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message
												key="dvtEqtRatioUnt" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCEFlag.equals("1")) { %>
														<bean:write name="appForm" property="credBureName5" />
														<% } else {
											 %> <html:text property="debitEqtRatioUnt" size="20" alt="debitEqtRatioUnt" name="appForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
											 <%	} %> 
										</td>
										
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> 
										<bean:message key="dvtSrvCoverageRatioTl" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCEFlag.equals("1")
													) {%>
														<bean:write name="appForm" property="debitSrvCoverageRatioTl" />
														<% } else {%>
												 <html:text property="debitSrvCoverageRatioTl" size="20" alt="debitSrvCoverageRatioTl" name="appForm" 
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" />
											 <%	}%> 
										</td>								
										<td class="ColumnBackground" width="25%"><bean:message
												key="crtRatioWc" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCEFlag.equals("1")
													) { %>
														<bean:write name="appForm" property="currentRatioWc" />
														<% } else {
											 %> <html:text property="currentRatioWc" size="20" alt="currentRatioWc" name="appForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
											 <%	} %> 
										</td>
									</tr>								
									</table> 
									</td></tr>	
								 <TR>						       
								    <TD colspan="4">																
								  <!--  <table width="100%"  id="existGreenUnitUI_50to100L" style="display:none;"> -->
								  <TABLE width="100%" id="existGreenUnitUI_50to100L"  	
								    	  <%																
								    	if(dblockUIEnable.equalsIgnoreCase("UI_3")){
								    		//System.out.println(dblockUIEnable+"..............D...................UI_3.");
								    	%>								    	
								    	 style="display: block;"
                                        <%}else{ 
                                        %>
								    	  style="display:none;" <%}%>>								  
						              <tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message
												key="dvtEqtRatio" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCEFlag.equals("1")	) { %>
														<bean:write name="appForm" property="debitEqtRatio" />
														<% } else {
											 %> <html:text property="debitEqtRatio" size="20" alt="debitEqtRatio" name="appForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
											 <%	} %> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> 
										<bean:message key="dvtSrvCoverageRatio" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCEFlag.equals("1")) { 		%>
														<bean:write name="appForm" property="debitSrvCoverageRatio" />
														<% } else {
											%> <html:text property="debitSrvCoverageRatio" size="20" alt="debitSrvCoverageRatio" name="appForm"
											 onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
											 <%
												}
												%> 
										</td>								
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message
												key="crtRatio" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCEFlag.equals("1")) { 	%>
														<bean:write name="appForm" property="currentRatios" />
														<% } else {
											 %> <html:text property="currentRatios" size="20" alt="currentRatios" name="appForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
											 <%	} %> 
										</td>
									</tr>	
									
								    <tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font>
										<bean:message key="crdBureauChiefPromScor" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCEFlag.equals("1")) {											
														%> 
														<bean:write name="appForm" property="creditBureauChiefPromScor" />
														<% } else {
																	%> <html:text property="creditBureauChiefPromScor" size="20"
												alt="creditBureauChiefPromScor" name="appForm" 
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> 
												 <%
													}
												%> 
										</td>
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message
												key="totAssets" /> 
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCEFlag.equals("1")) {											
												%> 
														<bean:write name="appForm" property="totalAssets" />
														<% } else {
																	%>  <html:text property="totalAssets" size="20"	alt="totalAssets" name="appForm" maxlength="16"	onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" />
											 <%
												}
											 %> 
										</td>
									</tr>							
									</table> 
								  </TD>
								</TR>		              
								 <%
								//	}
								  %> 
							</table>			<!--end of WC-->
						