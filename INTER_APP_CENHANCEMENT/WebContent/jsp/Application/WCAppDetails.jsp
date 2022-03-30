<%@page import="com.cgtsi.registration.MLIInfo"%>
<%@page import="java.util.Iterator"%>
<%@ page import="com.cgtsi.util.SessionConstants"%>
	<!--start of WC-->		
		<script type="text/javascript">
			function validateInterestRateWC(rateType){
				var plr = document.forms[0].wcPlr.value;
				var interest = document.forms[0].limitFundBasedInterest.value;
				if(rateType === 'interest'){
					if(!(parseFloat(plr) > 0)){
						alert('Please enter PLR rate first.');
						document.forms[0].limitFundBasedInterest.value='0.0';
						return false;
					}
					if(parseFloat(interest) < parseFloat(plr)){				
						alert('Interest rate can not be less than PLR rate.');
						document.forms[0].limitFundBasedInterest.value='0.0';
						return false;
					}
					if(parseFloat(interest) > (parseFloat(plr) + 4)){
						alert('Difference between plr and interest can not be more than 4%.');
						document.forms[0].limitFundBasedInterest.value='0.0';				
						return false;
					}
				}
				if(rateType === 'plr'){
					if(parseFloat(interest) > (parseFloat(plr) + 4)){				
						alert('Difference between plr and interest can not be more than 4%.');
						document.forms[0].limitFundBasedInterest.value='0.0';
						return false;
					}
				}
				
			}
			
			
			$(function() {
		        $( ".dkr_datepicker" ).datepicker({
		           changeMonth:true,
		           changeYear:true,
		           dateFormat: 'dd/mm/yy' 
		        });
		     });
			 
						
		</script>	
		<style>
		.ui-datepicker{
			width:14em !important; font-size:13px;
		}
		.ui-datepicker.ui-widget.ui-widget-content.ui-helper-clearfix.ui-corner-all{
		box-shadow:0px 2px 9px gray;
		border:1px solid #C4DEE6;
		}
		.ui-datepicker-prev.ui-corner-all,.ui-datepicker-next.ui-corner-all{
			background:#C4DEE6;
		}
		.ui-widget-header{
		background:#D2E7EC;
		}
		.ui-datepicker th{
		padding:.2em .3em;}
		</style>
		 <table width="100%" border="0" cellspacing="1" cellpadding="0" style="">
		 	<tbody>
			 <TR>
					<TD class="SubHeading" >
					<bean:message key="workingCapital" />			
					</TD>	
					<td></td>
					<td></td>
					<td></td>
					<td></td>					
				</TR>	
					<%
	 String loanType_d = session.getAttribute("APPLICATION_LOAN_TYPE").toString();
	 System.out.println("loanType_d :"+loanType_d);
	String appWCFlag=session.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG).toString();
                         
		if((appWCFlag.equals("13"))||(appWCFlag.equals("12")))		

		{
		%>
		
			<html:hidden property="wcTypeOfPLR"  name="appForm"  />
	       <html:hidden name="appForm" property="wcInterestType" value="F" />
	       <%}%>
				<tr align="left">
									<td class="ColumnBackground" rowspan="1" width="20%">									  
									  <html:hidden property="wcPlr"  styleId="wcPlrid" name="appForm" />									  	
										<bean:message key="limitSanctioned" />
										&nbsp;&nbsp;&nbsp;
									</td>
									<td class="TableData"width="20%">
										&nbsp;<bean:message key="fundBasedLimitSanctioned" /> 
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									</TD>
									<TD class="TableData" id="fundBasedLimitSanctioned" width="10%">
										<bean:write name="appForm" property="wcFundBasedSanctioned"/>	
										&nbsp;&nbsp;
									</TD>									
									<td align="left" class="TableData" width="20%"> 
									<!-- 	<font color="#FF0000" size="2">*</font> -->
										<bean:message key="limitFundBasedInterest" />
										<%
										if((appWCFlag.equals("13"))||(appWCFlag.equals("12")))		

										{
										%>
										<bean:write name="appForm" property="limitFundBasedInterest"/>
										<%}
										else
										{ %>
										
											<html:text property="limitFundBasedInterest" size="5" alt="limitFundBasedInterest"  name="appForm" maxlength="5" onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)"/>&nbsp;
										<%}%>
											<bean:message key="inPa" />
																			
									</td>
										

									<TD class="TableData" width="20%">
										<!-- <font color="#FF0000" size="2">*</font> --><bean:message key="limitFundbasedSanctionedDate" />	&nbsp;
										<%
										if((appWCFlag.equals("13"))||(appWCFlag.equals("12")))		

										{
										%>
										<bean:write name="appForm" property="limitFundBasedSanctionedDate"/>
										<%}
										else
										{ %>
																			 
									 <% if(loanType_d.equals("WC")){%>   
										 <html:text  name="appForm"  property="limitFundBasedSanctionedDate" styleClass="dkr_datepicker" styleId="limitFundBasedSanctionedDate" size="5" alt="limitFundBasedSanctionedDate" 
										maxlength="10"  onblur="setSanctionDtValueInNFBSanDt();"/>
									
										<!-- <IMG src="images/CalendarIcon.gif" width="15"  onClick="showCalendar('appForm.limitFundBasedSanctionedDate'),enableExtGreenFld('WC');" align="center"/> -->
										 <%}else if(loanType_d.equals("BO")||loanType_d.equals("CC")){%>
										</br> <html:text  name="appForm"  property="limitFundBasedSanctionedDate" styleClass="dkr_datepicker" styleId="limitFundBasedSanctionedDate" size="5" alt="limitFundBasedSanctionedDate"
										  maxlength="10"  />
										<!--  <IMG src="images/CalendarIcon.gif" width="15"  onClick="showCalendar('appForm.limitFundBasedSanctionedDate'),enableExtGreenFld('CC');" align="center">											 -->
									 <%}}%>
										
									</td>
								</tr>
					<tr>
								<td class="TableData" width="20%">
								</td>
								<td class="TableData" width="20%">
										<bean:message key="nonFundBasedLimitSantioned" />	
									</TD>
								<TD class="TableData" id="nonFundBasedLimitSantioned" width="10%">
									<bean:write name="appForm" property="wcNonFundBasedSanctioned"/>&nbsp; 
										
									</TD>
									<TD class="TableData" width="20%">		
										
									<!-- 	<bean:message key="limitNonFundBasedCommission" /> -->

										<%
										if((appWCFlag.equals("13"))||(appWCFlag.equals("12")))		

										{
										%>
										<!-- <bean:write name="appForm" property="limitNonFundBasedCommission"/> -->
										<%}
										else
										{ %>
										
										<!-- 	<html:text property="limitNonFundBasedCommission" size="5" alt="limitFundBasedInterest" name="appForm" maxlength="5" onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)"/>&nbsp;
										 --><%}%>

											<html:hidden property="limitNonFundBasedCommission" name="appForm" />&nbsp;
									
									</TD>
									<TD class="TableData" width="20%">
										<!-- <bean:message key="limitNonFundBasedSanctionedDate" />	 -->&nbsp;										
										<%
										if((appWCFlag.equals("13"))||(appWCFlag.equals("12")))		

										{
										%>
										<!-- <bean:write name="appForm" property="limitNonFundBasedSanctionedDate"/> -->
										<%}
										else
										{ %>										
                                    <% if(loanType_d.equals("WC")){%>									
										<!-- <html:text  name="appForm"  property="limitNonFundBasedSanctionedDate"  styleId="limitNonFundBasedSanctionedDate" size="7" alt="limitNonFundBasedSanctionedDate" maxlength="10" readonly="true" style="color: red; background: #C4DEE6" />
										 --><!-- <IMG src="images/CalendarIcon.gif" width="20"  onClick="showCalendar('appForm.limitNonFundBasedSanctionedDate'), enableExtGreenFld('WC');" align="center"> -->
							            <%}else if(loanType_d.equals("BO")||loanType_d.equals("CC")){%>	
							     	<!-- <html:text  name="appForm"  property="limitNonFundBasedSanctionedDate"  styleId="limitNonFundBasedSanctionedDate" size="7" alt="limitNonFundBasedSanctionedDate" maxlength="10" readonly="true" style="color: red; background: #C4DEE6"/>
									 --><!-- 	<IMG src="images/CalendarIcon.gif" width="20"  onClick="showCalendar('appForm.limitNonFundBasedSanctionedDate'),enableExtGreenFld('CC');" align="center"> -->
							        <% }} %>

										
									</td>
								</tr>
							<tr align="left">
									<td class="ColumnBackground" rowspan="1" width="20%">
										<font color="#FF0000" size="2">*</font><bean:message key="creditguarateed" />
										&nbsp;&nbsp;&nbsp;
									</td>
									<td class="TableData" width="20%">
										<!-- <bean:message key="creditFundBased" />  &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;-->
										<%
										// String loanTypdd = session.getAttribute("APPLICATION_LOAN_TYPE").toString();
										if((appWCFlag.equals("13"))||(appWCFlag.equals("12")))	
										{
										%>
										<bean:write name="appForm" property="creditFundBased"/>
										<%}
										else
										{ %>										
										<% if(loanType_d.equals("WC")){%>
									
										<html:text property="creditFundBased" styleId="creditFundBased" size="10" alt="creditFundBased" name="appForm" maxlength="16" onkeypress="isValidDecimal(this);" onblur="checkGurentyMaxtotalMIcollatSecAmt(),setSanctionDtValueInNFBSanDt();"  onkeyup="return enableExtGreenFld('WC');"/>&nbsp;
										<html:hidden property="creditFundBased"  styleId="creditFundBasedhid" name="appForm"/>
							            <%}else if(loanType_d.equals("BO")||loanType_d.equals("CC")){%>								     	
										<html:text property="creditFundBased" styleId="creditFundBased" size="10" alt="creditFundBased" name="appForm" maxlength="16" onkeypress="isValidDecimal(this);" onblur="checkGurentyMaxtotalMIcollatSecAmt()" onkeyup="return enableExtGreenFld('CC');"/>&nbsp;
									    <html:hidden property="creditFundBased"  styleId="creditFundBasedhid" name="appForm"/>
									   <% }} %>	
									   <bean:message key="inRs" />	
									</td>
								<!-- </tr>

								<tr align="left"> -->
									<td class="TableData" width="10%">
									</td>
									<td class="TableData" width="20%">
										<!-- <bean:message key="creditNonFundBased" /> -->
										&nbsp;&nbsp;&nbsp;

										<%
										if((appWCFlag.equals("13"))||(appWCFlag.equals("12")))		
										{
										%>
										<bean:write name="appForm" property="creditNonFundBased"/> 
										<%}
										else
										{ %>	
										<html:hidden property="creditNonFundBased" styleId="creditNonFundBased" name="appForm"/>&nbsp;
										
										<%-- <% if(loanType_d.equals("WC")){%>
									
										<html:text property="creditNonFundBased" styleId="creditNonFundBased" size="10" alt="creditNonFundBased" name="appForm"  onkeypress="isValidDecimal(this);" maxlength="16" onkeyup="return enableExtGreenFld('WC');" onblur="setSanctionDtValueInNFBSanDt(),checkGurentyMaxtotalMIcollatSecAmt();"/>&nbsp;
										
							            <%}else if(loanType_d.equals("BO")||loanType_d.equals("CC")){%>	
							     	
										<html:text property="creditNonFundBased" styleId="creditNonFundBased" size="10" alt="creditNonFundBased" name="appForm"  onkeypress="isValidDecimal(this);" maxlength="16" onkeyup="return enableExtGreenFld('CC');" onblur="setSanctionDtValueInNFBSanDt(),checkGurentyMaxtotalMIcollatSecAmt()"/>&nbsp;
										<bean:message key="inRs" />
										--%>											
							          <% }//} %>	 
																		
																				
									</td>
									<td class="TableData" width="20%">									  
									   <% if(loanType_d.equals("WC") && !loanType_d.equals("CC") && !loanType_d.equals("TC")){ 
													if (appWCFlag.equals("7") || appWCFlag.equals("8")
															|| appWCFlag.equals("10") || appWCFlag.equals("0")
															|| appWCFlag.equals("3") || appWCFlag.equals("5")
															|| appWCFlag.equals("11") || appWCFlag.equals("13")
															|| appWCFlag.equals("14") || appWCFlag.equals("19")) {												
												 	} else {
												 %> 
												 <font color="#FF0000" size="2">*</font>Limit Termination Date
												 <%
												 	}						
													if ((appWCFlag.equals("11")) || (appWCFlag.equals("13"))) {
												%> <%
												 	} else{
												 %> &nbsp;<html:text property="expiryDate"  name="appForm" maxlength="10" styleId="expiryDate"  styleClass="dkr_datepicker" style="width:135px"/>						                    
												<%	}	%>
											   </td>					   
											   </tr>
											<%	}	%>	
									 </td>
								</tr>	
					
					 	</tbody>
					 </table>            						
							
							
							<table width="100%" border="0" cellspacing="1" cellpadding="0" style="">
									
										
								<!-- <tr align="left"> 
									<td class="ColumnBackground" height="28" width="">&nbsp;
										<bean:message key="interestType" />
									</td>
									<td class="TableData" colspan="2"> -->													
								 
							
														
								
								
								
								
								<!-- DKR 32 Start-->
						   <%							    
								if((loanType_d.equals("WC")) && !loanType_d.equals("WCE") && !loanType_d.equals("WCR") && !loanType_d.equals("TCE"))		
                                {
						      %> 
						      
						      <!-- DKR 32-->
							  <!-- 	=============== FINANCIAL RECORDS =========================== -->
					       		<TR>
								  <TD colspan="15">
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
								<TD align="left" valign="top" class="TableData" colspan="2">
								<html:radio	name="appForm" value="Y" property="promDirDefaltFlg" styleId="promDirDefaltFlg_Y"  onclick="validatePromDirDefaltFlg();"/>
										 <bean:message key="yes" />&nbsp;&nbsp;
										  <html:radio name="appForm" value="N" property="promDirDefaltFlg" styleId="promDirDefaltFlg_N"  onclick="validatePromDirDefaltFlg();"/>
									<bean:message key="no" /></TD>	
						       </tr>     
					             <tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font>
										<bean:message key="credBureName1" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<% 	if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="credBureName1" name="appForm" /> <%
																		} else {
																	%> <html:text property="credBureName1" size="20"
												alt="credBureName1" name="appForm"/>
												 <%	} %> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> <bean:message
												key="credBureScorKeyProm" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="credBureKeyPromScor" name="appForm" />
																	 <%
																		} else {
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
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="credBureName2"
												name="appForm" /> <%
																		} else {
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
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="credBurePromScor2"
												name="appForm" /> <%
																		} else {
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
											<%	if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="credBureName3"
												name="appForm" /> <%
																		} else {
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
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
											%> <bean:write property="credBurePromScor3"
												name="appForm" />
												<%
													} else {
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
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="credBureName4"
												name="appForm" /> <%
																		} else {
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
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="credBurePromScor4"
												name="appForm" /> <%
																		} else {
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
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="credBureName5"
												name="appForm" /> <%
																		} else {
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
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="credBurePromScor5"
												name="appForm" /> <%
																		} else {
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
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="cibilFirmMsmeRank"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="cibilFirmMsmeRank" size="20"
												alt="credBureName5" name="appForm" maxlength="2"
												onblur="javascript:calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> value between (1-10) <%
																		}
																	%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> <bean:message
												key="expCommercialScore" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="expCommerScor"
												name="appForm" /> <%
																		} else {
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
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="promBorrNetWorth"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="promBorrNetWorth" size="20"
												alt="promBorrNetWorth" name="appForm" 
												styleId="projectedSalesTurnover" onblur="alProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> (&#8377;)
												 <%
													}
												%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> <bean:message
												key="contributPromEntity" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="promContribution"
												name="appForm" /> <%
																		} else {
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
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
											%> <bean:write property="promGAssoNPA1YrFlg" name="appForm" /> <%
												} else {
											%>
											 <html:radio name="appForm" value="Y" property="promGAssoNPA1YrFlg" styleId="promGAssoNPA1YrFlgY" /><!-- onclick="promGAssoNPA1YrFlgValidate();"  -->
											 <bean:message key="yes" />&nbsp;&nbsp;
											  <html:radio name="appForm" value="N" property="promGAssoNPA1YrFlg" styleId="promGAssoNPA1YrFlgN" /><!-- onclick="promGAssoNPA1YrFlgValidate();"  -->
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
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="promBussExpYr"
												name="appForm" /> <%
																		} else {
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
											<%	if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="salesRevenue"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="salesRevenue" size="20" styleId="salesRevenue" onblur="calProjectOutlay()"
												alt="salesRevenue" name="appForm" 
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> (&#8377;)
												 <%
													}
												%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> <bean:message
												key="taxPBIT" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="taxPBIT"
												name="appForm" /> <%
																		} else {
																   %> <html:text property="taxPBIT" size="20"
												alt="taxPBIT" name="appForm" styleId="taxPBIT" onblur="calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" />(&#8377;)
											    <%
												}
												%> 
										</td>
									</tr>
									
									<tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message
												key="interestPayment" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											 <%	if (appWCFlag.equals("12") || appWCFlag.equals("5")
														|| appWCFlag.equals("13")) {
											 %> <bean:write property="interestPayment"
												name="appForm" /> 
												<%
													} else {
												%> <html:text property="interestPayment" size="20"
												alt="interestPayment" name="appForm" styleId="interestPayment" onblur="calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> (&#8377;)
												 <%
													}
												%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<!-- <font color="#FF0000" size="2">*</font>  --><bean:message
												key="taxCurrentProvisionAmt" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="taxCurrentProvisionAmt"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="taxCurrentProvisionAmt" size="20"
												alt="taxCurrentProvisionAmt" name="appForm" styleId="taxCurrentProvisionAmt" onblur="calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" />(&#8377;)
												<!-- checkAmountwith5Digit('taxCurrentProvisionAmt'), -->
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
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
											%> <bean:write property="totCurrentAssets" name="appForm" /> <%
																		} else {
																	%> <html:text property="totCurrentAssets" size="20"
												alt="totCurrentAssets" name="appForm" styleId="totCurrentAssets" onblur="calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> (&#8377;)
												 <%
													}
												%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> <bean:message
												key="totCurrentLiability" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="totCurrentLiability"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="totCurrentLiability" size="20"
												alt="totCurrentLiability" name="appForm" styleId="totCurrentLiability" 
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" />(&#8377;)
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
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="totTermLiability"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="totTermLiability" size="20"
												alt="totTermLiability" name="appForm" styleId="totTermLiability" 
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> (&#8377;)
												 <%
													}
												%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> <bean:message
												key="exuityCapital" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="exuityCapital"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="exuityCapital" size="20"
												alt="exuityCapital" name="appForm" styleId="exuityCapital" 
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" />(&#8377;)
												
												<!-- onblur="checkAmountwith5Digit('exuityCapital');" -->
											 <%
												}
												%> 
										</td>
									</tr>
									
									<tr align="left">
										<td class="ColumnBackground" width="25%"><!-- <font color="#FF0000" size="2">*</font> --><bean:message
												key="preferenceCapital" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="preferenceCapital"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="preferenceCapital" size="20"
												alt="preferenceCapital" name="appForm" styleId="preferenceCapital" 
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> (&#8377;)
												
												<!-- onblur="checkAmountwith5Digit('preferenceCapital');" -->
												 <%
													}
												%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> <bean:message
												key="reservesSurplus" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="reservesSurplus"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="reservesSurplus" size="20"
												alt="exuityCapital" name="appForm" styleId="reservesSurplus" 
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" />(&#8377;)
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
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="repaymentDueNyrAmt"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="repaymentDueNyrAmt" size="20"
												alt="repaymentDueNyrAmt" name="appForm" styleId="repaymentDueNyrAmt" 
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> (&#8377;)
												<!-- onblur="checkAmountwith5Digit('repaymentDueNyrAmt');" -->
												 <%
													}
												%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<!--  <bean:message
												key="reservesSurplus" /> -->
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <!-- <bean:write property="reservesSurplus"
												name="appForm" /> --> <%
																		} else {
																	%> <!-- <html:text property="reservesSurplus" size="20"
												alt="exuityCapital" name="appForm" maxlength="16"
												onblur="javascript:calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" />(&#8377;) -->
											 <%
												}
											 %> 
										</td>
									</tr>
					            	</table>
		                          </TD>
								</TR>
								 <!--CR 158 End -->	
								
								  <!--CR 159 add by DKR -->						
								<!-- <tr align="left"  id="existGreenFldUnitType_id" style="display:none;">
						          <TD align="left" valign="top" class="ColumnBackground"  colspan="2"><font color="#FF0000" size="2">*</font>
						          <bean:message key="existGreenFieldUnits" /></TD>
								<TD align="left" valign="top" class="tableData" colspan="2">
								<html:radio	property="existGreenFldUnitType" name="appForm" value="Existing"/>
								 <bean:message key="existUnt" />&nbsp;&nbsp;
								 <html:radio  property="existGreenFldUnitType" name="appForm" value="Greenfield" />
									<bean:message key="GreenUnt" /></TD>	
						       </tr> -->
						      <%
						      //session.setAttribute("dblockUI","UI_2");
						      
						      String dblockUIEnable="";
								if(null!=session.getAttribute("dblockUI")){
									dblockUIEnable = (String) session.getAttribute("dblockUI");	
									System.out.println("dblockUIEnable>>>>>>>>1>>>>>>"+dblockUIEnable);
								}
						      %>						       
						       <TR>						       
								<TD colspan="15">																
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
								    		System.out.println(dblockUIEnable+".................................UI_1.");
								    	%>								    	
								    	 style="display: block;"
                                        <%}else{ 
                                        %>
								    	  style="display:none;" <%}%>
								    >						 
								<!--   <tr align="left"  >
						          <TD align="left" valign="top" class="ColumnBackground"  colspan="2"><font color="#FF0000" size="2">*</font>
								           <bean:message key="existGreenFieldUnits" /></TD>
									     	<TD align="left" valign="top" class="tableData" colspan="4">
										  <html:radio	property="existGreenFldUnitType" name="appForm" value="Existing"/>
										   <bean:message key="existUnt" />&nbsp;&nbsp;
										   <html:radio  property="existGreenFldUnitType" name="appForm" value="Greenfield" />
									 		<bean:message key="GreenUnt" /></TD>	
						       </tr> -->
						            <tr align="left"  id="existGreenUnitUI_1to10L">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font>
										<bean:message key="oprtInc" /></td>
										<td class="TableData" width="20%" align="left" valign="top">										
				                           <html:hidden property="existGreenFldUnitType"  name="appForm" styleId="existGreenFldUnitType"/>										
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
											%> <bean:write property="opratIncome" name="appForm" />
											 <%
												} else {
											 %> <html:text property="opratIncome" size="20" alt="opratIncome" name="appForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
											 <%	} %> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> 
										<bean:message key="pat" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
											%> <bean:write property="profAftTax" name="appForm" /> 
											<%
												} else {
											%> <html:text property="profAftTax" size="20" alt="profAftTax" name="appForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
											 <%
												}
												%> 
										</td>								
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message
												key="netwrth" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
											%> <bean:write property="networth" name="appForm" />
											 <%
												} else {
											 %> <html:text property="networth" size="20" alt="networth" name="appForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
											 <%	} %> 
										</td>
									</tr>								
									</table> 
									</TD></TR>
									
								  <TR>						       
								  <TD colspan="15">		
								  <!--  <table width="100%"  id="existGreenUnitUI_10to50L" style="display:none;"> -->
								     <TABLE width="100%" id="existGreenUnitUI_10to50L"  	
								    	 <%																
								    	if(dblockUIEnable.equalsIgnoreCase("UI_2") ){								    		
								    	System.out.println(dblockUIEnable+"................D2.................UI_2.");
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
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
											%> <bean:write property="debitEqtRatioUnt" name="appForm" />
											 <%
												} else {
											 %> <html:text property="debitEqtRatioUnt" size="20" alt="debitEqtRatioUnt" name="appForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
											 <%	} %> 
										</td>
										
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> 
										<bean:message key="dvtSrvCoverageRatioTl" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
											%> <bean:write property="debitSrvCoverageRatioTl" name="appForm" /> 
											<%
												} else {
											%> <html:text property="debitSrvCoverageRatioTl" size="20" alt="debitSrvCoverageRatioTl" name="appForm" 
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" />
											 <%
												}
												%> 
										</td>								
										<td class="ColumnBackground" width="25%"><bean:message
												key="crtRatioWc" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
											%> <bean:write property="currentRatioWc" name="appForm" />
											 <%
												} else {
											 %> <html:text property="currentRatioWc" size="20" alt="currentRatioWc" name="appForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
											 <%	} %> 
										</td>
									</tr>								
									</table> 
									</td></tr>	
								 <TR>						       
								    <TD colspan="15">																
								  <!--  <table width="100%"  id="existGreenUnitUI_50to100L" style="display:none;"> -->
								  <TABLE width="100%" id="existGreenUnitUI_50to100L"  	
								    	  <%																
								    	if(dblockUIEnable.equalsIgnoreCase("UI_3")){
								    		System.out.println(dblockUIEnable+"..............D...................UI_3.");
								    	%>								    	
								    	 style="display: block;"
                                        <%}else{ 
                                        %>
								    	  style="display:none;" <%}%>
								     >
								  
						              <tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message
												key="dvtEqtRatio" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
											%> <bean:write property="debitEqtRatio" name="appForm" />
											 <%
												} else {
											 %> <html:text property="debitEqtRatio" size="20" alt="debitEqtRatio" name="appForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
											 <%	} %> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> 
										<bean:message key="dvtSrvCoverageRatio" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
											%> <bean:write property="debitSrvCoverageRatio" name="appForm" /> 
											<%
												} else {
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
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
											%> <bean:write property="currentRatios" name="appForm" />
											 <%
												} else {
											 %> <html:text property="currentRatios" size="20" alt="currentRatios" name="appForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
											 <%	} %> 
										</td>
									</tr>	
									
								    <tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font>
										<bean:message key="crdBureauChiefPromScor" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="creditBureauChiefPromScor"	name="appForm" /> <%
																		} else {
																	%> <html:text property="creditBureauChiefPromScor" size="20"
												alt="creditBureauChiefPromScor" name="appForm" /> 
												 <%
													}
												%> 
										</td>
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message
												key="totAssets" /> 
										</td>
										<td class="TableData" width="20%" align="left" valign="top" colspan="3">
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%>  <bean:write property="totalAssets" name="appForm" />  <%
																		} else {
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
							      <!--CR 159 End -->								
						
								 <%
									}
								  %> 					            
					         <!--    DKR 00000 BOTH OK-->					         
					           <%							    
								if(loanType_d.equals("BO") && !loanType_d.equals("WC") && !loanType_d.equals("WCE") && !loanType_d.equals("WCR") && !loanType_d.equals("TCE"))		
                                {
						      %> 
						      
						      <!-- DKR 32-->
							  <!-- 	=============== FINANCIAL RECORDS ========== BOTH ================= -->
					       		<TR>
								  <TD colspan="15">
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
								<TD align="left" valign="top" class="TableData" colspan="2">
								<html:radio	name="appForm" value="Y" property="promDirDefaltFlg" styleId="promDirDefaltFlg_Y" onclick="validatePromDirDefaltFlg();"/>
										 <bean:message key="yes" />&nbsp;&nbsp;
										  <html:radio name="appForm" value="N" property="promDirDefaltFlg" styleId="promDirDefaltFlg_N" onclick="validatePromDirDefaltFlg();"/>
									<bean:message key="no" /></TD>	
						       </tr>     
					             <tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font>
										<bean:message key="credBureName1" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<% 	if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="credBureName1" name="appForm" /> <%
																		} else {
																	%> <html:text property="credBureName1" size="20"
												alt="credBureName1" name="appForm"/>
												 <%	} %> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> <bean:message
												key="credBureScorKeyProm" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="credBureKeyPromScor" name="appForm" />
																	 <%
																		} else {
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
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="credBureName2"
												name="appForm" /> <%
																		} else {
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
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="credBurePromScor2"
												name="appForm" /> <%
																		} else {
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
											<%	if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="credBureName3"
												name="appForm" /> <%
																		} else {
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
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
											%> <bean:write property="credBurePromScor3"
												name="appForm" />
												<%
													} else {
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
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="credBureName4"
												name="appForm" /> <%
																		} else {
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
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="credBurePromScor4"
												name="appForm" /> <%
																		} else {
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
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="credBureName5"
												name="appForm" /> <%
																		} else {
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
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="credBurePromScor5"
												name="appForm" /> <%
																		} else {
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
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="cibilFirmMsmeRank"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="cibilFirmMsmeRank" size="20"
												alt="credBureName5" name="appForm" maxlength="2"
												onblur="javascript:calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> value between (1-10) <%
																		}
																	%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> <bean:message
												key="expCommercialScore" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="expCommerScor"
												name="appForm" /> <%
																		} else {
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
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="promBorrNetWorth"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="promBorrNetWorth" size="20"
												alt="promBorrNetWorth" name="appForm" 
												styleId="projectedSalesTurnover" onblur="calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> (&#8377;)
												 <%
													}
												%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> <bean:message
												key="contributPromEntity" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="promContribution"
												name="appForm" /> <%
																		} else {
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
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
											%> <bean:write property="promGAssoNPA1YrFlg" name="appForm" /> <%
												} else {
											%>
											 <html:radio name="appForm" value="Y" property="promGAssoNPA1YrFlg" styleId="promGAssoNPA1YrFlgY" onclick="promGAssoNPA1YrFlgValidate();" />
											 <bean:message key="yes" />&nbsp;&nbsp;
											  <html:radio name="appForm" value="N" property="promGAssoNPA1YrFlg" styleId="promGAssoNPA1YrFlgN" onclick="promGAssoNPA1YrFlgValidate();"/>
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
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="promBussExpYr"
												name="appForm" /> <%
																		} else {
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
											<%	if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="salesRevenue"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="salesRevenue" size="20" styleId="salesRevenue" onblur="calProjectOutlay()"
												alt="salesRevenue" name="appForm" 
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> (&#8377;)
												 <%
													}
												%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> <bean:message
												key="taxPBIT" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="taxPBIT"
												name="appForm" /> <%
																		} else {
																   %> <html:text property="taxPBIT" size="20"
												alt="taxPBIT" name="appForm" styleId="taxPBIT" onblur="calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" />(&#8377;)
											    <%
												}
												%> 
										</td>
									</tr>
									
									<tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message
												key="interestPayment" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											 <%	if (appWCFlag.equals("12") || appWCFlag.equals("5")
														|| appWCFlag.equals("13")) {
											 %> <bean:write property="interestPayment"
												name="appForm" /> 
												<%
													} else {
												%> <html:text property="interestPayment" size="20"
												alt="interestPayment" name="appForm" styleId="interestPayment" onblur="calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> (&#8377;)
												 <%
													}
												%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<!-- <font color="#FF0000" size="2">*</font> --> <bean:message
												key="taxCurrentProvisionAmt" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="taxCurrentProvisionAmt"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="taxCurrentProvisionAmt" size="20"
												alt="taxCurrentProvisionAmt" name="appForm" styleId="taxCurrentProvisionAmt" onblur="calProjectOutlay();"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" />(&#8377;)
												<!-- checkAmountwith5Digit('taxCurrentProvisionAmt'), -->
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
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
											%> <bean:write property="totCurrentAssets" name="appForm" /> <%
																		} else {
																	%> <html:text property="totCurrentAssets" size="20"
												alt="totCurrentAssets" name="appForm" styleId="totCurrentAssets" onblur="calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> (&#8377;)
												 <%
													}
												%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> <bean:message
												key="totCurrentLiability" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="totCurrentLiability"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="totCurrentLiability" size="20"
												alt="totCurrentLiability" name="appForm" styleId="totCurrentLiability" 
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" />(&#8377;)
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
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="totTermLiability"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="totTermLiability" size="20"
												alt="totTermLiability" name="appForm" styleId="totTermLiability" 
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> (&#8377;)
												 <%
													}
												%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> <bean:message
												key="exuityCapital" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="exuityCapital"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="exuityCapital" size="20"
												alt="exuityCapital" name="appForm" styleId="exuityCapital" 
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" />(&#8377;)
												
												<!-- onblur="checkAmountwith5Digit('exuityCapital');" -->
											 <%
												}
												%> 
										</td>
									</tr>
									
									<tr align="left">
										<td class="ColumnBackground" width="25%"><!-- <font color="#FF0000" size="2">*</font> --><bean:message
												key="preferenceCapital" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="preferenceCapital"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="preferenceCapital" size="20"
												alt="preferenceCapital" name="appForm" styleId="preferenceCapital" 
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> (&#8377;)<!-- onblur="checkAmountwith5Digit('preferenceCapital');" -->
												 <%
													}
												%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> <bean:message
												key="reservesSurplus" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="reservesSurplus"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="reservesSurplus" size="20"
												alt="exuityCapital" name="appForm" styleId="reservesSurplus"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" />(&#8377;)
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
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="repaymentDueNyrAmt"
												name="appForm" /> <%
																		} else {
																	%> <html:text property="repaymentDueNyrAmt" size="20"
												alt="repaymentDueNyrAmt" name="appForm" styleId="repaymentDueNyrAmt" 
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" /> (&#8377;)
												<!-- onblur="checkAmountwith5Digit('repaymentDueNyrAmt');" -->
												
												 <%
													}
												%> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<!--  <bean:message
												key="reservesSurplus" /> -->
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <!-- <bean:write property="reservesSurplus"
												name="appForm" /> --> <%
																		} else {
																	%> <!-- <html:text property="reservesSurplus" size="20"
												alt="exuityCapital" name="appForm" maxlength="16"
												onblur="javascript:calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" />(&#8377;) -->
											 <%
												}
											 %> 
										</td>
									</tr>
					            	</table>
		                          </TD>
								</TR>
								 <!--CR 158 End -->	
								
								  <!--CR 159 add by DKR -->						
								<!-- <tr align="left"  id="existGreenFldUnitType_id" style="display:none;">
						          <TD align="left" valign="top" class="ColumnBackground"  colspan="2"><font color="#FF0000" size="2">*</font>
						          <bean:message key="existGreenFieldUnits" /></TD>
								<TD align="left" valign="top" class="tableData" colspan="2">
								<html:radio	property="existGreenFldUnitType" name="appForm" value="Existing"/>
								 <bean:message key="existUnt" />&nbsp;&nbsp;
								 <html:radio  property="existGreenFldUnitType" name="appForm" value="Greenfield" />
									<bean:message key="GreenUnt" /></TD>	
						       </tr> -->
						      <%
						      //session.setAttribute("dblockUI","UI_2");
						      
						      String dblockUIEnable="";
								if(null!=session.getAttribute("dblockUI")){
									dblockUIEnable = (String) session.getAttribute("dblockUI");	
									System.out.println("dblockUIEnable>>>>>>>>1>>>>>>"+dblockUIEnable);
								}
						      %>						       
						       <TR>						       
								<TD colspan="15">																
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
								    		System.out.println(dblockUIEnable+".................................UI_1.");
								    	%>								    	
								    	 style="display: block;"
                                        <%}else{ 
                                        %>
								    	  style="display:none;" <%}%>
								    >						 
								<!--   <tr align="left"  >
						          <TD align="left" valign="top" class="ColumnBackground"  colspan="2"><font color="#FF0000" size="2">*</font>
								           <bean:message key="existGreenFieldUnits" /></TD>
									     	<TD align="left" valign="top" class="tableData" colspan="4">
										  <html:radio	property="existGreenFldUnitType" name="appForm" value="Existing"/>
										   <bean:message key="existUnt" />&nbsp;&nbsp;
										   <html:radio  property="existGreenFldUnitType" name="appForm" value="Greenfield" />
									 		<bean:message key="GreenUnt" /></TD>	
						       </tr> -->
						            <tr align="left"  id="existGreenUnitUI_1to10L">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font>
										<bean:message key="oprtInc" /></td>
										<td class="TableData" width="20%" align="left" valign="top">										
				                           <html:hidden property="existGreenFldUnitType"  name="appForm" styleId="existGreenFldUnitType"/>										
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
											%> <bean:write property="opratIncome" name="appForm" />
											 <%
												} else {
											 %> <html:text property="opratIncome" size="20" alt="opratIncome" name="appForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
											 <%	} %> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> 
										<bean:message key="pat" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
											%> <bean:write property="profAftTax" name="appForm" /> 
											<%
												} else {
											%> <html:text property="profAftTax" size="20" alt="profAftTax" name="appForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
											 <%
												}
												%> 
										</td>								
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message
												key="netwrth" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
											%> <bean:write property="networth" name="appForm" />
											 <%
												} else {
											 %> <html:text property="networth" size="20" alt="networth" name="appForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
											 <%	} %> 
										</td>
									</tr>								
									</table> 
									</TD>
								</TR>
									
								  <TR>						       
								  <TD colspan="15">		
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
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
											%> <bean:write property="debitEqtRatioUnt" name="appForm" />
											 <%
												} else {
											 %> <html:text property="debitEqtRatioUnt" size="20" alt="debitEqtRatioUnt" name="appForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
											 <%	} %> 
										</td>
										
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> 
										<bean:message key="dvtSrvCoverageRatioTl" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
											%> <bean:write property="debitSrvCoverageRatioTl" name="appForm" /> 
											<%
												} else {
											%> <html:text property="debitSrvCoverageRatioTl" size="20" alt="debitSrvCoverageRatioTl" name="appForm" 
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" />
											 <%
												}
												%> 
										</td>								
										<td class="ColumnBackground" width="25%"><bean:message
												key="crtRatioWc" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
											%> <bean:write property="currentRatioWc" name="appForm" />
											 <%
												} else {
											 %> <html:text property="currentRatioWc" size="20" alt="currentRatioWc" name="appForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
											 <%	} %> 
										</td>
									</tr>								
									</table> 
									</td>
									
									</tr>	
								 <TR>						       
								    <TD colspan="15">																
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
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
											%> <bean:write property="debitEqtRatio" name="appForm" />
											 <%
												} else {
											 %> <html:text property="debitEqtRatio" size="20" alt="debitEqtRatio" name="appForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
											 <%	} %> 
										</td>
										<td class="ColumnBackground" width="25%">&nbsp;<font color="#FF0000" size="2">*</font> 
										<bean:message key="dvtSrvCoverageRatio" />
										</td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
											%> <bean:write property="debitSrvCoverageRatio" name="appForm" /> 
											<%
												} else {
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
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
											%> <bean:write property="currentRatios" name="appForm" />
											 <%
												} else {
											 %> <html:text property="currentRatios" size="20" alt="currentRatios" name="appForm" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)" />
											 <%	} %> 
										</td>
									</tr>	
									
								    <tr align="left">
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font>
										<bean:message key="crdBureauChiefPromScor" /></td>
										<td class="TableData" width="20%" align="left" valign="top">
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%> <bean:write property="creditBureauChiefPromScor"	name="appForm" /> <%
																		} else {
																	%> <html:text property="creditBureauChiefPromScor" size="20"
												alt="creditBureauChiefPromScor" name="appForm" /> 
												 <%
													}
												%> 
										</td>
										<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font><bean:message
												key="totAssets" /> 
										</td>
										<td class="TableData" width="20%" align="left" valign="top" colspan="3">
											<%
											if (appWCFlag.equals("12") || appWCFlag.equals("5")
													|| appWCFlag.equals("13")) {
																	%>  <bean:write property="totalAssets" name="appForm" />  <%
																		} else {
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
							      <!--CR 159 End -->								
						
					              
								 <%
									}
								  %> 
                           <!-- END OK disbursement details -->	
								<tr> 
								<td class="ColumnBackground" height="28" width="25%">&nbsp;
								<!-- <bean:message key="amtDisbursed" />	 -->	
								</td>
								<td class="TableData" height="28" width="25%">
								<%									
								if((appWCFlag.equals("13"))||(appWCFlag.equals("12")))			{
								%><!-- <bean:write name="appForm" property="amtDisbursed"/>	 -->									<%}
								 else {
								 %><!-- <html:text property="amtDisbursed"  styleId="amtDisbursed" size="20" alt="amtDisbursed" name="appForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/> -->									<%}%><!-- <bean:message key="inRs" /> -->	
								</td>
								<td class="ColumnBackground" height="28" colspan="11">										
								
								<table border="0" cellpadding="0" 
								cellspacing="0"  width="100%">
								<tr align="left"><td class="ColumnBackground" height="28" width="269">&nbsp;<!--  <bean:message key="firstDisbursementDate"/>	 -->							
								</td><td class="TableData" height="28" width="160" colspan="5">									<%
																				
								if((appWCFlag.equals("13"))||(appWCFlag.equals("12"))){
								%><!-- <bean:write name="appForm" property="firstDisbursementDate"/> --><%}								else { %>
								<!-- <html:text property="firstDisbursementDate" styleClass="dkr_datepicker" size="10" alt="firstDisbursementDate" name="appForm" maxlength="10"/>	 -->							
								<%}%>
								</td>
								</tr>
								<%-- <tr align="left"><td class="ColumnBackground" height="28" width="269" >&nbsp;
									<%	if((appWCFlag.equals("13"))||(appWCFlag.equals("12")))	
									{ %><bean:message key="lastDisbursementDate"/>	
									<% } else {%><bean:message key="finalDisbursementDate"/>	
									<%}%> </td>
									
							  <td class="TableData" height="28" width="160">	
								<%	
								if((appWCFlag.equals("13"))||(appWCFlag.equals("12")))	{
								%><bean:write name="appForm" property="finalDisbursementDate"/>	
								<%}	else { %>
								<html:text property="finalDisbursementDate" size="10" alt="finalDisbursementDate" name="appForm" maxlength="10"/>
								<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('appForm.finalDisbursementDate')" align="center">
								<%}%></td>
								</tr> --%>
								</table>
								</td>
								</tr>  
								
								<!-- disbursement details end here -->							
								<!-- <tr align="left">
									<td class="ColumnBackground" width="25%" colspan="12" height="25">&nbsp;
										<bean:message key="osFundBased" />
									</td>
								</tr> -->
								
						<%--
								<tr align="left">
									<td class="ColumnBackground" width="25%" colspan="12" height="25">&nbsp;
										<bean:message key="osFundBased" />
									</td>
								</tr>
								 <tr align="left">
									<td class="TableData" colspan="12">
									  <div align="center">
										&nbsp;<bean:message key="osFundBasedPpl" />&nbsp;
										<%
										if((appWCFlag.equals("13"))||(appWCFlag.equals("12")) || (appWCFlag.equals("4")) || (appWCFlag.equals("6")))		

										{
										%>
										<html:text property="osFundBasedPpl" size="5" alt="osFundBasedPpl" name="appForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
										<%}
										else
										{ %>
										
										<html:text property="osFundBasedPpl" size="5" alt="osFundBasedPpl" name="appForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
										<%}%>
										<bean:message key="inRs" />	
										&nbsp;&nbsp;&nbsp;&nbsp;
										<!--<bean:message key="osFundBasedInterestAmt" />&nbsp;
									
										<html:text property="osFundBasedInterestAmt" size="5" alt="osFundBasedInterestAmt" name="appForm" maxlength="13" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
										<bean:message key="inRs" />-->
										
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<bean:message key="osFundBasedAsOnDate" />&nbsp;&nbsp;&nbsp;
										<%
										if((appWCFlag.equals("13"))||(appWCFlag.equals("12")) || (appWCFlag.equals("4")) || (appWCFlag.equals("6")))		

										{
										%>
										<html:text property="osFundBasedAsOnDate" size="10" alt="osFundBasedAsOnDate" name="appForm" maxlength="10"/>
										<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('appForm.osFundBasedAsOnDate')" align="center"><%}
										else
										{ %>
										
										<html:text property="osFundBasedAsOnDate" size="10" alt="osFundBasedAsOnDate" name="appForm" maxlength="10"/>
										<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('appForm.osFundBasedAsOnDate')" align="center">
										<%}%>
										
										</div>
									</td>
								</tr>
								<tr align="left">
									<td class="ColumnBackground" width="25%" colspan="12" height="25">&nbsp;
										<bean:message key="osNonFundBased" />
									</td>
								</tr>
								<tr align="left">
									<td class="TableData" colspan="12">
									  <div align="center">
										<bean:message key="osNonFundBasedPpl" />&nbsp;

										<%
										if((appWCFlag.equals("13"))||(appWCFlag.equals("12")) || (appWCFlag.equals("4")) || (appWCFlag.equals("6")))		

										{
										%>
										<bean:write name="appForm" property="osNonFundBasedPpl"/>
										<%}
										else
										{ %>
										
										<html:text property="osNonFundBasedPpl" size="5" alt="osFundBasedPpl" name="appForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
										<%}%>

										<bean:message key="inRs" />	
										
										&nbsp;&nbsp;&nbsp;&nbsp;
										<!--<bean:message key="osNonFundBasedCommissionAmt" />&nbsp;
										
										<html:text property="osNonFundBasedCommissionAmt" size="5" alt="osNonFundBasedCommissionAmt" name="appForm" maxlength="13" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>-->
 
										&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
										<bean:message key="osNonFundBasedAsOnDate" />&nbsp;&nbsp;&nbsp;

										<%
										if((appWCFlag.equals("13"))||(appWCFlag.equals("12")) || (appWCFlag.equals("4")) || (appWCFlag.equals("6")))		

										{
										%>
										<bean:write name="appForm" property="osNonFundBasedAsOnDate"/>
										<%}
										else
										{ %>
										
										<html:text property="osNonFundBasedAsOnDate" size="10" alt="osFundBasedAsOnDate" name="appForm" maxlength="10"/>
										<IMG src="images/CalendarIcon.gif" width="20" onClick="showCalendar('appForm.osNonFundBasedAsOnDate')" align="center">
										<%}%>

										
										</div>
									</td>
								</tr> --%>
							</table>			<!--end of WC-->
						