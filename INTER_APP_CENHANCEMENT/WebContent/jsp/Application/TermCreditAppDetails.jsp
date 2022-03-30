<%@ page import="com.cgtsi.util.SessionConstants"%>	
      <script type="text/javascript">
      /* function onPageLoad() {
    	  document.getElementById(styleId="restructConfirmation").checked = true;
    	} */
function checkbox() {
    var checkBox = document.getElementById("exposureId");
   // var text = document.getElementById("text");
    if (checkBox.checked == true){
     //alert('true');
    //   document.forms[0].exposureFbId.value='Y';
    //   document.forms[0].exposureFbIdY='Y';
     //   document.getElementById('checkVal').value = 'V';       
    } else {
     //alert('false');
     //document.forms[0].exposureFbIdY='N';    
     //  text.style.display = "none";
    }
}
	function validateInterestRateTC(rateType){
		var plr = document.forms[0].plr.value;
		var interest = document.forms[0].interestRate.value;
		/* if(rateType === 'interest'){
			if(!(parseFloat(plr) > 0)){
				alert('Please enter PLR rate first.');
				document.forms[0].interestRate.value='0.0';
				return false;
			}
			if(parseFloat(interest) < parseFloat(plr)){				
				alert('Interest rate can not be less than PLR rate.');
				document.forms[0].interestRate.value='0.0';
				return false;
			}
			 if(parseFloat(interest) > (parseFloat(plr) + 4)){
				alert('Difference between plr and interest can not be more than 4%.');
				document.forms[0].interestRate.value='0.0';				
				return false;
			} 
		} */
		/* if(rateType === 'plr'){
			if(parseFloat(interest) > (parseFloat(plr) + 4)){				
				alert('Difference between plr and interest can not be more than 4%.');
				document.forms[0].interestRate.value='0.0';
				return false;
			}
		} */	/* ui-datepicker ui-widget ui-widget-content ui-helper-clearfix ui-corner-all ui-datepicker-multi ui-datepicker-multi-2 */
	}
	
	$(function() {
        $( ".dkr_datepicker" ).datepicker({
           changeMonth:true,
           changeYear:true,
           dateFormat: 'dd/mm/yy' 
        });
     });
	$(function() {
        $( "#cpDobId" ).datepicker({
           changeMonth:true,
           changeYear:true,
           dateFormat: 'dd/mm/yy' 
        });
     });
	
	$(function() {
        $( "#firstInstallmentDueDate" ).datepicker({
           changeMonth:true,
           changeYear:true,
           dateFormat: 'dd/mm/yy',
           
          onSelect:function(dateStr){
        	   var newDate = $(this).datepicker('getDate');
        	   if(newDate){
        		   newDate.setDate(newDate.getDate());
        	   }
        	   
        	 //  $("#searchDateFrom").datepicker('setDate',newDate);
        	 /*   var noOfInstallments = $("#noOfInstallments").val;        	   
        	   var date1 = new Date(newDate);
        	   date1.setMonth(date1.getMonth() + noOfInstallments);		
        	  
        	   $("#expiryDate").datepicker('setDate',date1);
 */
           }, 
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


time.icon
{
  font-size: 1em; /* change icon size */
  display: block;
  position: relative;
  width: 7em;
  height: 7em;
  background-color: #fff;
  border-radius: 0.6em;
  box-shadow: 0 1px 0 #bdbdbd, 0 2px 0 #fff, 0 3px 0 #bdbdbd, 0 4px 0 #fff, 0 5px 0 #bdbdbd, 0 0 0 1px #bdbdbd;
  overflow: hidden;
}
</style>
<table width="100%" border="0" cellspacing="1" cellpadding="0">


	<TR>
		<TD colspan="10">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
				<TR>
					<TD width="31%" class="Heading"><bean:message
							key="facilityDetails" /></TD>
					<TD><IMG src="images/TriangleSubhead.gif" width="19"
						height="19"></TD>
				</TR>
				<TR>
					<TD colspan="9" class="Heading"><IMG src="images/Clear.gif"
						width="5" height="5"></TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
	<%
								  String loanTypd_d = session.getAttribute("APPLICATION_LOAN_TYPE").toString();
												System.out.println("loanTypd_d :"+loanTypd_d);
								String appTCFlag=session.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG).toString();
								if (appTCFlag.equals("0") || appTCFlag.equals("1") || appTCFlag.equals("2"))
								{
								%>
	<tr align="left">
		<td class="ColumnBackground"><bean:message
				key="facilityRehabilitation" /></td>
		<td class="TableData" colspan="3"><html:radio name="appForm"
				value="Y" property="rehabilitation"></html:radio> <bean:message
				key="yes" /> <html:radio name="appForm" value="N"
				property="rehabilitation"></html:radio> <bean:message key="no" /></td>
	</tr>
	<%}%>
	<!--<tr>
									<td class="ColumnBackground">&nbsp;<bean:message key="scheme" />
									</td>
									<td class="TableData" colspan="10">&nbsp;
									<%
									if (session.getAttribute(SessionConstants.MCGF_FLAG).equals("M"))
									{%>
									<bean:write property="scheme" name="appForm"/>
									<%}
									else
									{%>
									<bean:write property="scheme" name="appForm"/>
									<%}%>
									</td>               
								</tr>
								<tr>
									<td class="ColumnBackground" id="loanType">&nbsp;<bean:message key="loanType" />
									</td>
									<td class="TableData" colspan="10">&nbsp;<bean:write property="loanType" name="appForm"/>
									<html:hidden property="loanType" name="appForm"/>
									</td> 
								</tr>
								<tr>
									<td class="ColumnBackground">&nbsp;<bean:message key="compositeLoan" />
									</td>
									<td class="TableData" colspan="10">&nbsp;<bean:write property="compositeLoan" name="appForm"/>
									</td> 
								</tr>-->

	<tr align="left">
		<td colspan="4" class="SubHeading" height="28" width="843"><br>
			&nbsp; <bean:message key="termCreditDetails" /> <html:hidden property="loanType" name="appForm"/></td>
	</tr>

	<tr align="left">
		<td colspan="12">
			<table width="100%" height="71" border="0" cellspacing="0"
				cellpadding="0">
				<tbody>
					<tr>
						<%-- <td width="16.5%" class="ColumnBackground" height="28">&nbsp;
										<bean:message key="amountSanctioned" />
									</td>
									<td width="16.5%" class="TableData" height="28" id="amountSanctioned">
										<%				
											if((appTCFlag.equals("0"))||(appTCFlag.equals("3"))||(appTCFlag.equals("5"))||(appTCFlag.equals("6") || (appTCFlag.equals("11"))||(appTCFlag.equals("13"))))

											{
											%>
											<html:text name="appForm" property="amountSanctioned"/>
											<%}
											else
											{ %>
											<bean:message key="inRs"/>
											<%}%>
												
									</td>
									
									<td width="16.5%" class="ColumnBackground" height="28">&nbsp;
										
									</td>
									<td width="16.5%" class="TableData" height="28" id="amountSanctioned">
										
												
									</td> --%>


						<%-- <td width="25%" class="ColumnBackground" height="28">&nbsp;
										<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="amountSanctionedDate" />
									</td>
									<td width="25%" class="TableData" height="28" >
									<%				
										if((appTCFlag.equals("11"))||(appTCFlag.equals("13")))									
										{
										%>
										<bean:write name="appForm" property="amountSanctionedDate"/>
										<%}
										else
										{ 
									         if(loanTypd_d.equals("BO")||loanTypd_d.equals("CC")){
									         %>
									         <html:text property="amountSanctionedDate" size="20" alt="amountSanctionedDate" styleId="amountSanctionedDate" name="appForm" maxlength="10" style="width:150px"/>
											  <IMG src="images/CalendarIcon.gif" width="20"  onclick="showCalendar('appForm.amountSanctionedDate')" align="center">
									       <%  }else{ %>
									           <html:text property="amountSanctionedDate" size="20" alt="amountSanctionedDate" styleId="amountSanctionedDate" name="appForm" maxlength="10"  onblur="return enableExtGreenFld('TC'),checkGurentyMaxtotalMIcollatSecAmt();" style="width:150px"/>
											  <IMG src="images/CalendarIcon.gif" width="20"  onclick="showCalendar('appForm.amountSanctionedDate')" align="center">									         
									         
									     <%}}%>
											
									</td> --%>
					</tr>
					<% 
								 //String ID=session.getAttribute("expoid").toString();%>

					<tr align="left">
						<td width="16.5%" class="ColumnBackground" height="28"><font
							color="#FF0000" size="2">*</font>&nbsp;<bean:message
								key="creditGuaranteed" />
								
								</td>
						<td width="16.5%" class="TableData" height="28"
							style="max-width: 188px;">
							<%				
								if((appTCFlag.equals("11"))||(appTCFlag.equals("13")))
								{
								%> <bean:write name="appForm" property="creditGuaranteed" />
							  <%}else{
								%> <html:hidden property="exposurelmtAmt" name="appForm" /> <html:text
								property="creditGuaranteed" styleId="creditGuaranteed" size="20"
								alt="creditGuaranteed" name="appForm" maxlength="16"
								onblur="checkGurentyMaxtotalMIcollatSecAmt();"
								onkeyup="return enableExtGreenFld('TC');"
								onkeypress="return decimalOnly(this, event,13);" />&nbsp;<bean:message
								key="inRs" /> <html:hidden property="creditGuaranteed"
								styleId="creditGuaranteedhid" name="appForm" />
							<div id="FBerrorsMessage" class="errorsMessage"></div> <%}%>
						</td>
						<td width="16.5%" class="ColumnBackground" height="28" colspan="1">&nbsp;
							<font color="#FF0000" size="2">*</font>&nbsp;<bean:message
								key="amountSanctionedDate" />
						</td>
						<td width="16.5%" class="TableData" height="28">
							<%				
										if((appTCFlag.equals("11"))||(appTCFlag.equals("13")))									
										{
										%> <bean:write name="appForm" property="amountSanctionedDate" />
							<%}
										else
										{ 
									  if((appCommonLoanType.equals("BO")) || (appCommonLoanType.equals("CC"))){   %> 
					      <html:text property="amountSanctionedDate"
								name="appForm" maxlength="10" style="width:150px"
								styleId="amountSanctionedDate" styleClass="dkr_datepicker"
								size="20" onblur="calculateGuranteeExpireDate();"
								alt="amountSanctionedDate" /><!-- onclick="countFirstInstallmentDt();" <IMG
							src="images/CalendarIcon.gif" width="20"
							onclick="showCalendar('appForm.amountSanctionedDate')"
							onblur="countFirstInstallmentDt();" align="center"/>  -->
							 <%  }else{ %>										
							<html:text property="amountSanctionedDate" size="20"
								alt="amountSanctionedDate" styleId="amountSanctionedDate" styleClass="dkr_datepicker"
								name="appForm" maxlength="10"								
								onblur="return enableExtGreenFld('TC'),checkGurentyMaxtotalMIcollatSecAmt(),calculateGuranteeExpireDate();"
								style="width:150px" /> <!-- onclick="countFirstInstallmentDt()" <IMG src="images/CalendarIcon.gif"
							width="20" onclick="showCalendar('appForm.amountSanctionedDate')"
							onblur="countFirstInstallmentDt();" align="center" /> -->
															
					 <%}}%>                            
						</td>
						<td class="ColumnBackground" height="28" width="16.5%">&nbsp; <font
			color="#FF0000" size="2">*</font>&nbsp;<bean:message key="tenure" />&nbsp;
		</td>
		<td class="TableData" height="28" width="16.5%">
			<%			if((appTCFlag.equals("11"))||(appTCFlag.equals("13"))){
			         %> <bean:write name="appForm" property="tenure" /> <%}
										else
										{ %> <html:text property="tenure" size="20" alt="tenure"
				styleId="tenureId" name="appForm" maxlength="3"
				onkeypress="return numbersOnly(this, event)"
				onkeyup="isValidNumber(this)"
				onblur="calculateGuranteeExpireDate();" /> <bean:message
				key="inMonths" /> <%}%>

		</td>
					</tr>

					<tr>
						<%-- <td class="ColumnBackground" height="28" width="25%">&nbsp;
										<bean:message key="amtDisbursed" />		
									</td>
									<td class="TableData" height="28" width="25%">
									<%
									
										if(appTCFlag.equals("11")||(appTCFlag.equals("13")) || appTCFlag.equals("3") || appTCFlag.equals("6"))
										{
										
										%>
										<bean:write name="appForm" property="amtDisbursed"/>
										<%}
										else
										{ %>
										<html:text property="amtDisbursed" size="20" alt="amtDisbursed" name="appForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
										<%}%>
										<bean:message key="inRs" />	
									</td> --%>
						<td class="ColumnBackground" height="28" colspan="9"
							style="padding: 0;">
							<table border="0" cellpadding="0" cellspacing="0" width="100%">
								<tr align="left">
									<td class="ColumnBackground" height="28" width="16.5%">&nbsp;
										<bean:message key="firstDisbursementDate" />
									</td>
									<td class="TableData" height="28" width="16.5%" colspan="5">
										<%
												if(appTCFlag.equals("11")||(appTCFlag.equals("13")) || appTCFlag.equals("3") || appTCFlag.equals("6"))
												{
												%> <bean:write name="appForm"
											property="firstDisbursementDate" /> <%}
												else
												{ %> <html:text property="firstDisbursementDate"  styleId="firstDisbursementDate" styleClass="dkr_datepicker" size="20"
											alt="firstDisbursementDate" name="appForm" maxlength="10"   onblur="calculateGuranteeExpireDate();"/> 
										  <!--  <IMG   src="images/CalendarIcon.gif" class="dkr_datepicker" align="center" width="20" /> -->
										<!-- <IMG src="images/CalendarIcon.gif" width="20" 
										onclick="showCalendar('appForm.firstDisbursementDate')"	align="center"  onblur="calculateGuranteeExpireDate();"/> -->
										 <%}%>
									</td>



									<%-- <td class="ColumnBackground" height="28" width="269" >&nbsp;
												<%
												if(appTCFlag.equals("11")||(appTCFlag.equals("13")) || appTCFlag.equals("3") || appTCFlag.equals("6"))
												{
												%>
													<!-- <bean:message key="lastDisbursementDate" />	 -->
													<%
												}
												else
												{%>
												<!-- <bean:message key="finalDisbursementDate" /> -->
												<%}%>
												</td>
												<td class="TableData" height="28" width="160">
												<%
												if(appTCFlag.equals("11")||(appTCFlag.equals("13")) || appTCFlag.equals("3") || appTCFlag.equals("6"))
												{
												%>
												<!-- <bean:write name="appForm" property="finalDisbursementDate"/> -->
												<%}
												else
												{ %>
													<!-- <html:text property="finalDisbursementDate" size="10" alt="finalDisbursementDate" name="appForm" maxlength="10"/> -->
													<!-- <IMG src="images/CalendarIcon.gif" width="20" onclick="showCalendar('appForm.finalDisbursementDate')" align="center"> -->
													
													<html:hidden property="finalDisbursementDate" alt="creditGuaranteedhid" name="appForm"/>		
												<%}%>
												</td> valid 15-jan-2021
												 --%>
									<html:hidden property="finalDisbursementDate"
										alt="finalDisbursementDate" name="appForm" />

									<td class="ColumnBackground" height="28" width="16.5%"
										colspan="0">&nbsp; <bean:message key="amtDisbursed" />
									</td>
									<td class="TableData" height="28" width="16.5%">
										<%
									
										if(appTCFlag.equals("11")||(appTCFlag.equals("13")) || appTCFlag.equals("3") || appTCFlag.equals("6"))
										{
										
										%> <bean:write name="appForm" property="amtDisbursed" /> <%}
										else
										{ %> <html:text property="amtDisbursed" size="20" styleId="amtDisbursed"
											alt="amtDisbursed" name="appForm" maxlength="16"
											onkeypress="return decimalOnly(this, event,13)"
											onkeyup="isValidDecimal(this)" /> <%}%> <bean:message
											key="inRs" />
									</td>
									<td width="16.5%" height="18" class="ColumnBackground"><span
			style="font-size: 9pt; font-weight: 700"> <font
				color="#FF0000" size="2">*</font>&nbsp;<bean:message
					key="repaymentMoratorium" /></span></td>
		<td width="16.5%" height="18" class="TableData"><span
			style="font-size: 9pt; font-weight: 700"> <%				
													if((appTCFlag.equals("11"))||(appTCFlag.equals("13")) || (appTCFlag.equals("3")) || (appTCFlag.equals("6")))										

													{
													%> <bean:write name="appForm"
					property="repaymentMoratorium" /> <%}
													else
													{ %> <html:text property="repaymentMoratorium" size="5"
					alt="repaymentMoratorium" name="appForm" maxlength="3"
					styleId="repaymentMoratoriumId"
					onkeypress="return numbersOnly(this, event)"
					onkeyup="isValidNumber(this)"
					onblur="calculateGuranteeExpireDate();" /> <!-- styleId="repaymentMoratoriumId"  onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)" onblur="countFirstInstallmentDt(),calculateGuranteeExpireDate();"/> -->


				<%}%></span> <span style="font-size: 9pt"> <bean:message
					key="inMonths" />
		</span></td>
								</tr>
							</table>
						</td>
					</tr>
					<!-- DKR 32-->
				</tbody>
			</table>
		</td>


	</tr>

	<!--<td width="15%" class="ColumnBackground" height="28">
									
									<%
									//if(session.getAttribute("expoid").equals("")==false)
									//{										
									%>
										&nbsp;<bean:message key="fbschemechk" />	
									</td>
									<td width="20%" class="TableData" height="28" colspan="8">
							  
							                                   <html:radio name="appForm" value="Y1" property="exposureFbId" ></html:radio>
											
																<bean:message key="yes" />&nbsp;&nbsp;
																
																<html:radio name="appForm" value="N1" property="exposureFbId" ></html:radio>
																
																<bean:message key="no"/>  
								 </tr>
                                   <%
                                   //} 
                                   %>-->

	<%
							    String loanTypk = session.getAttribute("APPLICATION_LOAN_TYPE").toString();
								if((loanTypk.equals("TC") && !loanTypk.equals("BO") && !loanTypk.equals("TCE")))		
                                {
						      %>
	<!-- <tr align="left">
						          <TD align="left" valign="top" class="ColumnBackground"  colspan="2"><font color="#FF0000" size="2">*</font><bean:message
										key="inCrilcCibilRbi" /></TD>
								<TD align="left" valign="top" class="tableData" colspan="2">
								<html:radio	name="appForm" value="Y" property="promDirDefaltFlg" styleId="promDirDefaltFlg_Y" onclick="enableOtherFinancialDtl('TC');" />
										 <bean:message key="yes" />&nbsp;&nbsp;
										  <html:radio name="appForm" value="N" property="promDirDefaltFlg" styleId="promDirDefaltFlg_N" onclick="enableOtherFinancialDtl('TC');" />
									<bean:message key="no" /></TD>	
						       </tr> -->

	<TR>
		<TD colspan="8">
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
				style="display:none;" <%}%>>

				<tr align="left">
					<TD align="left" valign="top" class="ColumnBackground" colspan="2"><font
						color="#FF0000" size="2">*</font> <bean:message
							key="inCrilcCibilRbi" /></TD>
					<TD align="left" valign="top" class="TableData" colspan="2">
						<div>
							<html:radio name="appForm" value="Y" property="promDirDefaltFlg" styleId="promDirDefaltFlg_Y" onclick="validatePromDirDefaltFlg();" />
							<bean:message key="yes" />
						</div>
						<div style="margin-top: -30px; margin-left: 50px;">
							<html:radio name="appForm" value="N" property="promDirDefaltFlg"
								styleId="promDirDefaltFlg_N" onclick="validatePromDirDefaltFlg();" />
							<bean:message key="no" />
						</div>
					</TD>
				</tr>
				<tr align="left">
					<td class="ColumnBackground" width="25%"><font color="#FF0000"
						size="2">*</font> <bean:message key="credBureName1" /></td>
					<td class="TableData" width="20%" align="left" valign="top">
						<%if (appTCFlag.equals("11") 
													|| appTCFlag.equals("13")) {
																	%> <bean:write property="credBureName1" name="appForm" />
						<%
																		} else {
																	%> <html:text property="credBureName1" size="20"
							alt="credBureName1" name="appForm" styleId="credBureName1"
							onkeyup="validateString('credBureName1',this);" /> <%	} %>
					</td>
					<td class="ColumnBackground" width="25%">&nbsp;<font
						color="#FF0000" size="2">*</font> <bean:message
							key="credBureScorKeyProm" />
					</td>
					<td class="TableData" width="20%" align="left" valign="top">
						<%
											if (appTCFlag.equals("11") 
													|| appTCFlag.equals("13")){
																	%> <bean:write property="credBureKeyPromScor"
							name="appForm" /> <%
																		} else {
																	%> <html:text property="credBureKeyPromScor" size="20"
							alt="credBureKeyPromScor" name="appForm" maxlength="3" /> <bean:message
							key="3to9" /> <%
																		}
																	%>
					</td>
				</tr>
				<tr align="left">
					<td class="ColumnBackground" width="25%"><font color="#FF0000"
						size="2"></font> <bean:message key="credBureName2" /></td>
					<td class="TableData" width="20%" align="left" valign="top">
						<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="credBureName2" name="appForm" />
						<%
																		} else {
																	%> <html:text property="credBureName2" size="20"
							alt="credBureName2" name="appForm" styleId="credBureName2"
							onkeyup="validateString('credBureName2',this);" /> <%
																		}
																	%>
					</td>
					<td class="ColumnBackground" width="25%">&nbsp;<font
						color="#FF0000" size="2"></font> <bean:message
							key="credBureScoreProm2" />
					</td>
					<td class="TableData" width="20%" align="left" valign="top">
						<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
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
					<td class="ColumnBackground" width="25%"><font color="#FF0000"
						size="2"></font> <bean:message key="credBureName3" /></td>
					<td class="TableData" width="20%" align="left" valign="top">
						<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="credBureName3" name="appForm" />
						<%
																		} else {
																	%> <html:text property="credBureName3" size="20"
							alt="credBureName3" name="appForm"
							onkeyup="validateString('credBureName3',this);" /> <%
																		}
																	%>
					</td>
					<td class="ColumnBackground" width="25%">&nbsp;<font
						color="#FF0000" size="2"></font> <bean:message
							key="credBureScoreProm3" />
					</td>
					<td class="TableData" width="20%" align="left" valign="top">
						<%
											if (appTCFlag.equals("11") 
													|| appTCFlag.equals("13")) {
											%> <bean:write property="credBurePromScor3" name="appForm" />
						<%
													} else {
												%> <html:text property="credBurePromScor3" size="20"
							alt="credBurePromScor3" name="appForm" maxlength="3"
							onblur="javascript:calProjectOutlay()"
							onkeypress="return decimalOnly(this, event,13)"
							onkeyup="isValidDecimal(this)" /> <%
													}
												 %> <bean:message key="3to9" />
					</td>
				</tr>

				<tr align="left">
					<td class="ColumnBackground" width="25%"><font color="#FF0000"
						size="2"></font> <bean:message key="credBureName4" /></td>
					<td class="TableData" width="20%" align="left" valign="top">
						<%
																		if(appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="credBureName4" name="appForm" />
						<%
																		} else {
																	%> <html:text property="credBureName4" size="20"
							alt="credBureName4" name="appForm"
							onkeyup="validateString('credBureName4',this);" /> <%
																		}
																	%>
					</td>
					<td class="ColumnBackground" width="25%">&nbsp;<font
						color="#FF0000" size="2"></font> <bean:message
							key="credBureScoreProm4" />
					</td>
					<td class="TableData" width="20%" align="left" valign="top">
						<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
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
					<td class="ColumnBackground" width="25%"><font color="#FF0000"
						size="2"></font> <bean:message key="credBureName5" /></td>
					<td class="TableData" width="20%" align="left" valign="top">
						<%
																		if(appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="credBureName5" name="appForm" />
						<%
																		} else {
																	%> <html:text property="credBureName5" size="20"
							alt="credBureName5" name="appForm"
							onkeyup="validateString('credBureName5',this);" /> <%
																		}
																	%>
					</td>
					<td class="ColumnBackground" width="25%">&nbsp;<font
						color="#FF0000" size="2"></font> <bean:message
							key="credBureScoreProm5" />
					</td>
					<td class="TableData" width="20%" align="left" valign="top">
						<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
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
					<td class="ColumnBackground" width="25%"><font color="#FF0000"
						size="2">*</font> <bean:message key="cibMSMERankFirm" /></td>
					<td class="TableData" width="20%" align="left" valign="top">
						<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
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
					<td class="ColumnBackground" width="25%">&nbsp;<font
						color="#FF0000" size="2">*</font> <bean:message
							key="expCommercialScore" />
					</td>
					<td class="TableData" width="20%" align="left" valign="top">
						<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")){
																	%> <bean:write property="expCommerScor" name="appForm" />
						<%
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
					<td class="ColumnBackground" width="25%"><font color="#FF0000"
						size="2">*</font> <bean:message key="promNetworth" /></td>
					<td class="TableData" width="20%" align="left" valign="top">
						<%
																		if(appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="promBorrNetWorth"
							name="appForm" /> <%
																		} else {
																	%> <html:text property="promBorrNetWorth" size="20"
							styleId="promBorrNetWorth" alt="promBorrNetWorth" name="appForm"
							onblur="calProjectOutlay()"
							onkeypress="return decimalOnly(this, event,13)"
							onkeyup="isValidDecimal(this)" />(&#8377;) <%
													}
												%>
					</td>
					<td class="ColumnBackground" width="25%">&nbsp;<font
						color="#FF0000" size="2">*</font> <bean:message
							key="contributPromEntity" />
					</td>
					<td class="TableData" width="20%" align="left" valign="top">
						<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="promContribution"
							name="appForm" /> <%
																		} else {
																	%> <html:text property="promContribution" size="20"
							alt="promContribution" name="appForm" maxlength="3"
							onblur="javascript:calProjectOutlay()"
							onkeypress="return decimalOnly(this, event,13)"
							onkeyup="isValidDecimal(this)" />(Between 0-100%) <%
												}
												%>
					</td>
				</tr>
				<tr align="left">
					<td class="ColumnBackground" width="25%"><font color="#FF0000"
						size="2">*</font> <bean:message key="promNpainPast" /></td>
					<td class="TableData" width="20%" align="left" valign="top">
						<%
											if (appTCFlag.equals("11") 
													|| appTCFlag.equals("13")){
											%> <bean:write property="promGAssoNPA1YrFlg" name="appForm" />
						<%
												} else {
											%> <html:radio name="appForm" value="Y"
							property="promGAssoNPA1YrFlg" styleId="promGAssoNPA1YrFlgY" /> <!-- onclick="promGAssoNPA1YrFlgValidate();"  --><bean:message
							key="yes" />&nbsp;&nbsp; <html:radio name="appForm" value="N"
							property="promGAssoNPA1YrFlg" styleId="promGAssoNPA1YrFlgN"	/> <!-- onclick="promGAssoNPA1YrFlgValidate();"  --> 
							<bean:message
							key="no" /> <%
											}
										%>
					</td>
					<td class="ColumnBackground" width="25%">&nbsp;<font
						color="#FF0000" size="2">*</font> <bean:message
							key="promExpRelBusiness" />
					</td>
					<td class="TableData" width="20%" align="left" valign="top">
						<%
																		if(appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="promBussExpYr" name="appForm" />
						<%
																		} else {
																	%> <html:text property="promBussExpYr" size="20"
							alt="promContribution" name="appForm" maxlength="3"
							onblur="javascript:calProjectOutlay()"
							onkeypress="return decimalOnly(this, event,13)"
							onkeyup="isValidDecimal(this)" />(Between 0-100) <%
												}
												%>
					</td>
				</tr>
				<tr align="left">
					<td class="ColumnBackground" width="25%"><font color="#FF0000"
						size="2">*</font> <bean:message key="salesRevenue" /></td>
					<td class="TableData" width="20%" align="left" valign="top">
						<%
																		if(appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="salesRevenue" name="appForm" />
						<%
																		} else {
																	%> <html:text property="salesRevenue" size="20"
							styleId="salesRevenue" alt="salesRevenue" name="appForm"
							onblur="calProjectOutlay();"
							onkeypress="return decimalOnly(this, event,13)"
							onkeyup="isValidDecimal(this)" /> (&#8377;) <%
													}
												%>
					</td>
					<td class="ColumnBackground" width="25%">&nbsp;<font
						color="#FF0000" size="2">*</font> <bean:message key="taxPBIT" />
					</td>
					<td class="TableData" width="20%" align="left" valign="top">
						<%
																		if(appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="taxPBIT" name="appForm" /> <%
																		} else {
																   %> <html:text property="taxPBIT" size="20"
							alt="taxPBIT" name="appForm" styleId="taxPBIT"
							onblur="calProjectOutlay()"
							onkeypress="return decimalOnly(this, event,13)"
							onkeyup="isValidDecimal(this)" />(&#8377;) <%
												}
												%>
					</td>
				</tr>

				<tr align="left">
					<td class="ColumnBackground" width="25%"><font color="#FF0000"
						size="2">*</font> <bean:message key="interestPayment" /></td>
					<td class="TableData" width="20%" align="left" valign="top">
						<%
													if (appTCFlag.equals("11") || appTCFlag.equals("13")){
											 %> <bean:write property="interestPayment" name="appForm" />
						<%
													} else {
												%> <html:text property="interestPayment" size="20"
							alt="interestPayment" name="appForm" styleId="interestPayment"
							onblur="calProjectOutlay()"
							onkeypress="return decimalOnly(this, event,13)"
							onkeyup="isValidDecimal(this)" /> (&#8377;) <%
													}
												%>
					</td>
					<td class="ColumnBackground" width="25%">&nbsp;<!-- <font
						color="#FF0000" size="2">*</font>  --><bean:message
							key="taxCurrentProvisionAmt" />
					</td>
					<td class="TableData" width="20%" align="left" valign="top">
						<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="taxCurrentProvisionAmt"
							name="appForm" /> <%
																		} else {
																	%> <html:text property="taxCurrentProvisionAmt"
							size="20" alt="taxCurrentProvisionAmt" name="appForm"
							styleId="taxCurrentProvisionAmt"
							onblur="calProjectOutlay()"
							onkeypress="return decimalOnly(this, event,13)"
							onkeyup="isValidDecimal(this)" />(&#8377;) <%
												}
												%>
					</td>
				</tr>


				<tr align="left">
					<td class="ColumnBackground" width="25%"><font color="#FF0000"
						size="2">*</font> <bean:message key="totCurrentAssets" /></td>
					<td class="TableData" width="20%" align="left" valign="top">
						<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
											%> <bean:write property="totCurrentAssets" name="appForm" />
						<%
																		} else {
																	%> <html:text property="totCurrentAssets" size="20"
							alt="totCurrentAssets" name="appForm" styleId="totCurrentAssets"
							onblur="calProjectOutlay()"
							onkeypress="return decimalOnly(this, event,13)"
							onkeyup="isValidDecimal(this)" /> (&#8377;) <%
													}
												%>
					</td>
					<td class="ColumnBackground" width="25%">&nbsp;<font
						color="#FF0000" size="2">*</font> <bean:message
							key="totCurrentLiability" />
					</td>
					<td class="TableData" width="20%" align="left" valign="top">
						<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="totCurrentLiability"
							name="appForm" /> <%
																		} else {
																	%> <html:text property="totCurrentLiability" size="20"
							alt="totCurrentLiability" name="appForm"
							styleId="totCurrentLiability"
							onkeypress="return decimalOnly(this, event,13)"
							onkeyup="isValidDecimal(this)" />(&#8377;) <%
												}
												%>
					</td>
				</tr>


				<tr align="left">
					<td class="ColumnBackground" width="25%"><font color="#FF0000"
						size="2">*</font> <bean:message key="totTermLiability" /></td>
					<td class="TableData" width="20%" align="left" valign="top">
						<%
																		if(appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="totTermLiability"
							name="appForm" /> <%
																		} else {
																	%> <html:text property="totTermLiability" size="20"
							alt="totTermLiability" name="appForm" styleId="totTermLiability"
							onkeypress="return decimalOnly(this, event,13)"
							onkeyup="isValidDecimal(this)" /> (&#8377;) <%
													}
												%>
					</td>
					<td class="ColumnBackground" width="25%">&nbsp;<font
						color="#FF0000" size="2">*</font> <bean:message
							key="exuityCapital" />
					</td>
					<td class="TableData" width="20%" align="left" valign="top">
						<%
																		if(appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="exuityCapital" name="appForm" />
						<%
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
					<td class="ColumnBackground" width="25%"><!-- <font color="#FF0000"
						size="2">*</font> --> <bean:message key="preferenceCapital" /></td>
					<td class="TableData" width="20%" align="left" valign="top">
						<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="preferenceCapital"
							name="appForm" /> <%
																		} else {
																	%> <html:text property="preferenceCapital" size="20"
							alt="preferenceCapital" name="appForm"
							styleId="preferenceCapital"							
							onkeypress="return decimalOnly(this, event,13)"
							onkeyup="isValidDecimal(this)" /> (&#8377;) 
							<!-- onblur="checkAmountwith5Digit('preferenceCapital');" -->
							<%
													}
												%>
					</td>
					<td class="ColumnBackground" width="25%">&nbsp;<font
						color="#FF0000" size="2">*</font> <bean:message
							key="reservesSurplus" />
					</td>
					<td class="TableData" width="20%" align="left" valign="top">
						<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="reservesSurplus"
							name="appForm" /> <%
																		} else {
																	%> <html:text property="reservesSurplus" size="20"
							alt="exuityCapital" name="appForm" styleId="reservesSurplus"
							onkeypress="return decimalOnly(this, event,13)"
							onkeyup="isValidDecimal(this)" />(&#8377;) <%
												}
												%>
					</td>
				</tr>

				<tr align="left">
					<td class="ColumnBackground" width="25%"><font color="#FF0000"
						size="2">*</font> <bean:message key="repaymentDueNyrAmt" /></td>
					<td class="TableData" width="20%" align="left" valign="top">
						<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="repaymentDueNyrAmt"
							name="appForm" /> <%
																		} else {
																	%> <html:text property="repaymentDueNyrAmt" size="20"
							alt="repaymentDueNyrAmt" name="appForm"
							styleId="repaymentDueNyrAmt"							
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
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <!-- <bean:write property="reservesSurplus"
												name="appForm" /> --> <%
																		} else {
																	%> <!-- <html:text property="reservesSurplus" size="20"
												alt="exuityCapital" name="appForm" maxlength="16"
												onblur="javascript:calProjectOutlay()"
												onkeypress="return decimalOnly(this, event,13)"
												onkeyup="isValidDecimal(this)" />(&#8377;) --> <%
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
		<TD colspan="11">
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

			<TABLE id="existGreenFldUnitType_id" width="100%"
				<%																
								    	if(dblockUIEnable.equalsIgnoreCase("UI_1") || dblockUIEnable.equalsIgnoreCase("UI_2")||dblockUIEnable.equalsIgnoreCase("UI_3")){
								    		System.out.println(dblockUIEnable+".................................UI_1.");
								    	%>
				style="display: table;"
				<%}else{ 
                                        %>
				style="display:none;" <%}%>>
				<!--   <tr align="left"  width="100%">
						        <TD align="left" valign="top" class="ColumnBackground"  width="50%"><font color="#FF0000" size="2">*</font>
								           <bean:message key="existGreenFieldUnits" /></TD>
									     	<TD align="left" valign="top" class="TableData"  width="50%">
										  <html:radio	property="existGreenFldUnitType" name="appForm" value="Existing"/>
										   <bean:message key="existUnt" />&nbsp;&nbsp;
										   <html:radio  property="existGreenFldUnitType" name="appForm" value="Greenfield" />
											<bean:message key="GreenUnt" /></TD>	
						         </tr>  -->

				<html:hidden property="existGreenFldUnitType"
					styleId="existGreenFldUnitType" name="appForm" />

				<tr align="left" id="existGreenUnitUI_1to10L" style="width: 100%">
					<td class="ColumnBackground" width="16.5%"><font
						color="#FF0000" size="2">*</font> <bean:message key="oprtInc" /></td>

					<td class="ColumnBackground" width="16.5%" align="left"
						valign="top">
						<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
											%> <bean:write property="opratIncome" name="appForm" /> <%
												} else {
											 %> <html:text property="opratIncome" name="appForm"
							size="20" styleId="opratIncome"
							onblur="checkAmountwith10k('opratIncome');" alt="opratIncome" onkeypress="return decimalOnly(this, event,13);"
							onkeyup="isValidDecimal(this)" /> <%	} %>
					</td>
					<td class="ColumnBackground" width="16.5%">&nbsp;<font
						color="#FF0000" size="2">*</font> <bean:message key="pat" />
					</td>
					<td class="ColumnBackground" width="16.5%" align="left"
						valign="top">
						<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
											%> <bean:write property="profAftTax" name="appForm" /> <%
												} else {
											%> <html:text property="profAftTax" size="20"
							alt="profAftTax" name="appForm"
							onkeypress="return decimalOnly(this, event,13)"
							onkeyup="isValidDecimal(this)" /> <%
												}
												%>
					</td>
					<td class="ColumnBackground" width="16.5%"><font
						color="#FF0000" size="2">*</font> <bean:message key="netwrth" /></td>
					<td class="ColumnBackground" width="16.5%" align="left"
						valign="top">
						<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
											%> <bean:write property="networth" name="appForm" /> <%
												} else {
											 %> <html:text property="networth" size="20" alt="networth"
							name="appForm" onkeypress="return decimalOnly(this, event,13)"
							onkeyup="isValidDecimal(this)" /> <%	} %>
					</td>
				</tr>
			</table>
		</TD>
	</TR>

	<TR style="width: 100%">
		<TD>

			<TABLE width="100%" id="existGreenUnitUI_10to50L"
				<%																
								    	if(dblockUIEnable.equalsIgnoreCase("UI_2") ){								    		
								    	System.out.println(dblockUIEnable+".................................UI_2.");
								    	%>
				style="display: block;"
				<%}else{ 
                                        %>
				style="display:none;" <%}%>>
				<tbody width="100%">
				<tr align="left">
					<td class="ColumnBackground" width="16.5%"><font
						color="#FF0000" size="2">*</font> <bean:message
							key="dvtEqtRatioUnt" /></td>
					<td class="ColumnBackground" width="16.5%" align="left"
						valign="top">
						<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
											%> <bean:write property="debitEqtRatioUnt" name="appForm" />
						<%
												} else {
											 %> <html:text property="debitEqtRatioUnt" size="20"
							alt="debitEqtRatioUnt" name="appForm"
							onkeypress="return decimalOnly(this, event,13)"
							onkeyup="isValidDecimal(this)" /> <%	} %>
					</td>

					<td class="ColumnBackground" width="16.5%">&nbsp;<font
						color="#FF0000" size="2">*</font> <bean:message
							key="dvtSrvCoverageRatioTl" />
					</td>
					<td class="ColumnBackground" width="16.5%" align="left"
						valign="top">
						<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
											%> <bean:write property="debitSrvCoverageRatioTl"
							name="appForm" /> <%
												} else {
											%> <html:text property="debitSrvCoverageRatioTl" size="20"
							alt="debitSrvCoverageRatioTl" name="appForm"
							onkeypress="return decimalOnly(this, event,13)"
							onkeyup="isValidDecimal(this)" /> <%
												}
												%>
					</td>
					<td class="ColumnBackground" width="16.5%"><bean:message
							key="crtRatioWc" /></td>
					<td class="ColumnBackground" width="16.5%" align="left"
						valign="top">
						<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
											%> <bean:write property="currentRatioWc" name="appForm" /> <%
												} else {
											 %> <html:text property="currentRatioWc" size="20"
							alt="currentRatioWc" name="appForm"
							onkeypress="return decimalOnly(this, event,13)"
							onkeyup="isValidDecimal(this)" /> <%	} %>
					</td>
				</tr>
				</tbody>
			</table>
		</td>
	</tr>
	<TR style="width: 100%">
		<TD style="width: 100%">

			<TABLE width="100%" id="existGreenUnitUI_50to100L"
				<%																
								    	if(dblockUIEnable.equalsIgnoreCase("UI_3")){
								    		System.out.println(dblockUIEnable+".................................UI_3.");
								    	%>
				style="display: block;"
				<%}else{ 
                                        %>
				style="display:none;" <%}%>>

				<tr align="left">
					<td class="ColumnBackground" width="16.5%"><font
						color="#FF0000" size="2">*</font> <bean:message key="dvtEqtRatio" /></td>
					<td class="TableData" width="16.5%" align="left" valign="top">
						<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
											%> <bean:write property="debitEqtRatio" name="appForm" /> <%
												} else {
											 %> <html:text property="debitEqtRatio" size="20"
							alt="debitEqtRatio" name="appForm"
							onkeypress="return decimalOnly(this, event,13)"
							onkeyup="isValidDecimal(this)" /> <%	} %>
					</td>
					<td class="ColumnBackground" width="16.5%">&nbsp;<font
						color="#FF0000" size="2">*</font> <bean:message
							key="dvtSrvCoverageRatio" />
					</td>
					<td class="TableData" width="16.5%" align="left" valign="top">
						<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
											%> <bean:write property="debitSrvCoverageRatio"
							name="appForm" /> <%
												} else {
											%> <html:text property="debitSrvCoverageRatio" size="20"
							alt="debitSrvCoverageRatio" name="appForm"
							onkeypress="return decimalOnly(this, event,13)"
							onkeyup="isValidDecimal(this)" /> <%
												}
												%>
					</td>
					<td class="ColumnBackground" width="16.5%"><font
						color="#FF0000" size="2">*</font> <bean:message key="crtRatio" /></td>
					<td class="TableData" width="16.5%" align="left" valign="top">
						<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
											%> <bean:write property="currentRatios" name="appForm" /> <%
												} else {
											 %> <html:text property="currentRatios" size="20"
							alt="currentRatios" name="appForm"
							onkeypress="return decimalOnly(this, event,13)"
							onkeyup="isValidDecimal(this)" /> <%	} %>
					</td>
				</tr>

				<tr align="left">
					<td class="ColumnBackground" width="16.5%"><font
						color="#FF0000" size="2">*</font> <bean:message
							key="crdBureauChiefPromScor" /></td>
					<td class="TableData" width="16.5%" align="left" valign="top">
						<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="creditBureauChiefPromScor"
							name="appForm" /> <%
																		} else {
																	%> <html:text property="creditBureauChiefPromScor"
							size="20" maxlength="3" alt="creditBureauChiefPromScor"
							name="appForm" /> <%
													}
												%>
					</td>
					<td class="ColumnBackground" width="16.5%"><font
						color="#FF0000" size="2">*</font> <bean:message key="totAssets" /></td>
					<td class="TableData" width="16.5%" align="left" valign="top" colspan="3">
						<%
																		if (appTCFlag.equals("11") 
																				|| appTCFlag.equals("13")) {
																	%> <bean:write property="totalAssets" name="appForm" />
						<%
																		} else {
																	%> <html:text property="totalAssets" size="20"
							alt="totalAssets" name="appForm" maxlength="16"
							onkeypress="return decimalOnly(this, event,13)"
							onkeyup="isValidDecimal(this)" /> <%
												}
											 %>
					</td>
				</tr>
			</table>
		</TD>
	</TR>
	<!--CR 159 End -->
	<%}%>



	<%-- <tr align="left"> 
									<td class="ColumnBackground" height="28" >&nbsp;
										<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="tenure" />&nbsp;
									</td>
									<td class="TableData" height="28" colspan="3">	
									<%				
										if((appTCFlag.equals("11"))||(appTCFlag.equals("13")))
										

										{
										%>
										<bean:write name="appForm" property="tenure"/>
										<%}
										else
										{ %>
									
										<html:text property="tenure" size="20" alt="tenure" styleId="tenureId" name="appForm" maxlength="3" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)" onblur="calculateGuranteeExpireDate();"/>
										<bean:message key="inMonths" />
									<%}%>
									
									</td>
								</tr> --%>
	<tr align="left">
		

		
		<%-- <td class="ColumnBackground" height="28">&nbsp;
										<bean:message key="interestType" />
									</td>
									<td class="TableData" >
									<%				
										if((appTCFlag.equals("11"))||(appTCFlag.equals("13")))
										{
										%>
										<html:radio name="appForm" value="T" property="interestType" disabled="true">
										</html:radio>									
										<bean:message key="fixedInterest" />&nbsp;									
										<html:radio name="appForm" value="F" property="interestType" disabled="true"></html:radio>										
										<bean:message key="floatingInterest" />	
<!--										<bean:write name="appForm" property="interestType"/>-->
										<%}
										else
										{ %>

										<html:radio name="appForm" value="T" property="interestType" >
										</html:radio>									
										<bean:message key="fixedInterest" />&nbsp;									
										<html:radio name="appForm" value="F" property="interestType" ></html:radio>										
										<bean:message key="floatingInterest" />	
									<%}%>									
										&nbsp;<!--<html:text property="interestRate" size="5" alt="interestRate" name="appForm" maxlength="4" onkeypress="return decimalOnly(this, event)" onkeyup="isValidDecimal(this)"/>
										<bean:message key="inPer" />-->
									</td> --%>
		<!--
									    <td class="ColumnBackground" height="28" >&nbsp;
										<bean:message key="benchMarkPLR" />
									</td>
									<td class="TableData" height="28" width="160">&nbsp;
										<html:text property="benchMarkPLR" size="5" alt="benchMarkPLR" name="appForm" maxlength="3" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>&nbsp;
										<bean:message key="inPer" />
									</td>
									<td class="ColumnBackground" height="28" >&nbsp;
										<bean:message key="benchMarkPLR" />
									</td>
									-->
		<!-- <td width="25%" class="ColumnBackground" height="28" >&nbsp;
									<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="typeOfPLR" />
									Type of Base Rate
									</td>
									<td class="TableData" height="28" >&nbsp; -->
		<%-- <%				
										if((appTCFlag.equals("11"))||(appTCFlag.equals("13")))
										

										{
										%>
										<bean:write name="appForm" property="typeOfPLR"/>
										<%}
										else
										{ %>
										
										<html:text disabled="true" property="typeOfPLR" size="20" alt="typeOfPLR" name="appForm" maxlength="50" />&nbsp;						
									<%}%> --%>


		<%-- 	<td class="ColumnBackground" width="16.5%" align="left" valign="top" colspan="0"><font color="#FF0000" size="2">*</font> 
						 <%
						 if((appTCFlag.equals("11"))||(appTCFlag.equals("13")) || (appTCFlag.equals("3")) || (appTCFlag.equals("6"))){		
						%> <bean:message	key="expiryDate" /> <%
					 	} else {
					 %> <bean:message key="expiryDate" /> <%
					 	}
					 %>
										</td>
										<td class="TableData" width="16.5%" align="left" valign="top">
											<%
												if ((appTCFlag.equals("11")) || (appTCFlag.equals("13"))) {
											%> <%
					 	} else {
					 %> <html:text property="expiryDate" name="appForm"  styleId="expiryDate" size="20"	alt="expiryDate" maxlength="10"/> <IMG
											src="images/CalendarIcon.gif" width="20"
											onClick="showCalendar('appForm.expiryDate')" align="center">
					
											<%
												}
											%> 
										</td> --%>


	</tr>
	<tr align="left">
		<html:hidden property="typeOfPLR" styleId="typeOfPLR" name="appForm" />
		<%-- <td width="25%" class="ColumnBackground"> <div align="left">&nbsp;
										<font color="#FF0000" size="2">*</font>&nbsp;<!-- <bean:message key="plr" /> -->Base Rate</div>
									</td>
									<td class="TableData" width="25%" >
										<div align="left"> 
										<%				
										if((appTCFlag.equals("11"))||(appTCFlag.equals("13")))
										

										{
										%>
										<bean:write name="appForm" property="plr"/>
										<%}
										else
										{ %>
											<html:text property="plr" size="5" alt="plr" name="appForm" maxlength="6"  onkeypress="return decimalOnly(this, event,3)" onkeyup="isValidDecimal(this)"/>&nbsp;
										<%}%>
											<bean:message key="inPa" />
										
										</div>
									</td> --%>
		<%-- 	<td width="25%" class="ColumnBackground"> <div align="left">&nbsp;
										<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="interestRate" /></div>
									</td>
									<td width="25%" class="TableData" >
										<div align="left"> 
										<%				
										if((appTCFlag.equals("11"))||(appTCFlag.equals("13")))
										

										{
										%>
										<bean:write name="appForm" property="interestRate"/>
										<%}
										else
										{ %>
										
											<html:text property="interestRate" size="5" alt="interestRate" name="appForm"  maxlength="5" onkeypress="return decimalOnly(this, event,2)" onkeyup="isValidDecimal(this)"/>&nbsp;
										<%}%>
											<bean:message key="inPa" />
										
										</div>
									</td> --%>

	</tr>
	<!-- <tr align="left"> 
									<td class="ColumnBackground" colspan="12" height="24"  >
										<bean:message key="repaymentSchedule" />
									</td>
								</tr> -->
	<tr align="left">
		<td colspan="12" height="24">
			<table border="0" cellpadding="0" cellspacing="0" width="100%"
				height="71">
				<tbody>
					<tr>

						<%-- <td width="16.5%" height="18" class="ColumnBackground"><span style="font-size: 9pt; font-weight: 700">
													<font
					                            	color="#FF0000" size="2">*</font>&nbsp;<bean:message key="repaymentMoratorium" /></span>
												</td>
												<td width="16.5%" height="18" class="TableData" >
												
													<span style="font-size: 9pt; font-weight: 700">
													<%				
													if((appTCFlag.equals("11"))||(appTCFlag.equals("13")) || (appTCFlag.equals("3")) || (appTCFlag.equals("6")))										

													{
													%>
													<bean:write name="appForm" property="repaymentMoratorium"/>
													<%}
													else
													{ %>
													<html:text property="repaymentMoratorium" size="5" alt="repaymentMoratorium" name="appForm" maxlength="3" 
														styleId="repaymentMoratoriumId"  onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)" onblur="calculateGuranteeExpireDate();"/>
													<!-- styleId="repaymentMoratoriumId"  onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)" onblur="countFirstInstallmentDt(),calculateGuranteeExpireDate();"/> -->
												
													
													<%}%></span>
													<span style="font-size: 9pt"> 
												
													<bean:message key="inMonths" />
													</span>
												</td>  --%>
						<td width="16.5%" height="17" class="ColumnBackground"><span
							style="font-size: 9pt; font-weight: 700">&nbsp;<bean:message
									key="firstInstallmentDueDate" /> </span></td>
						<td width="16.5%" height="17" class="TableData"><span
							style="font-size: 9pt; font-weight: 700"> <%				
													if((appTCFlag.equals("11"))||(appTCFlag.equals("13")) || (appTCFlag.equals("3")) || (appTCFlag.equals("6")))																			
													{
													%> <bean:write name="appForm"
									property="firstInstallmentDueDate" /> <%}
													else
													{ %> <html:text property="firstInstallmentDueDate"
									styleId="firstInstallmentDueDate" size="20" 
									alt="firstInstallmentDueDate" name="appForm" maxlength="10"
									onblur="calculateGuranteeExpireDate(),countLoanTerminationDt();" />
								<!-- <html:text property="firstInstallmentDueDate" styleClass="dkr_datepicker" styleId="firstInstallmentDueDate" size="20" alt="firstInstallmentDueDate" name="appForm" maxlength="10"  onblur="countFirstInstallmentDt(),calculateGuranteeExpireDate(),countLoanTerminationDt();"/> -->
								<!-- <IMG src="images/CalendarIcon.gif" width="20"
								onclick="showCalendar('appForm.firstInstallmentDueDate')"
								align="center"
								onblur="calculateGuranteeExpireDate(),countLoanTerminationDt();" /> -->
								<%}%>
						</span></td>

						<td width="16.5%" height="18" class="ColumnBackground"><span
							style="font-size: 9pt; font-weight: 700"><font
								color="#FF0000" size="2">*</font>&nbsp; <bean:message
									key="noOfInstallments" /> </span></td>
						<td width="16.5%" height="18" class="TableData"><span
							style="font-size: 9pt; font-weight: 700"> <%				
													if((appTCFlag.equals("11"))||(appTCFlag.equals("13")) || (appTCFlag.equals("3")) || (appTCFlag.equals("6")))						
													{
													%> <bean:write name="appForm" property="noOfInstallments" />
								<%}
													else
													{ %> <html:text property="noOfInstallments" size="5"
									alt="noOfInstallments" name="appForm"
									styleId="noOfInstallmentsId" maxlength="3"
									onkeypress="return numbersOnly(this, event)"
									onkeyup="isValidNumber(this)"
									/> <%}%><!-- onclick="countLoanTerminationDt();" onblur="calculateGuranteeExpireDate();" -->
						</span> <html:hidden property="periodicity" styleId="periodicity"
								name="appForm" /></td>
						<td class="ColumnBackground" width="16.5%" align="left"
							valign="top" colspan="0"><font color="#FF0000" size="2">*</font>
							<%
												 if((appTCFlag.equals("11"))||(appTCFlag.equals("13")) || (appTCFlag.equals("3")) || (appTCFlag.equals("6"))){		
												%> <bean:message key="expiryDate" /> <%
											 	} else {
											 %> <bean:message key="expiryDate" />  <%
											 	}
											 %></td>
						<td class="TableData" width="16.5%" align="left" valign="top">
							<%
												if ((appTCFlag.equals("11")) || (appTCFlag.equals("13"))) {
																	%> <%
											 	} else {
											 %> 
						 <html:text property="expiryDate" name="appForm"  styleId="expiryDate" readonly="true" size="20" maxlength="10" style="background: rgb(196, 222, 230); color: red;"/>
								<!-- 
									 <html:text property="expiryDate" name="appForm" styleClass="dkr_datepicker" styleId="expiryDate" readonly="readonly" size="20" maxlength="10" style="background: rgb(196, 222, 230); color: red;"/>
							
								<IMG src="images/CalendarIcon.gif" width="20"
								onClick="showCalendar('appForm.expiryDate')" align="center"> -->

							<%
												}
											%>
						</td>
					</tr>
					<%-- <tr> 
											<td width="16.5%" height="18" class="ColumnBackground">
												
												</td>
												<td width="16.5%" height="18" class="ColumnBackground">
												
												</td>
												<td width="16.5%" height="18" class="ColumnBackground">
												
												</td>
												<td width="16.5%" height="18" class="ColumnBackground">
												
												</td>
												
												<td width="16.5%" height="18" class="ColumnBackground">
													<span style="font-size: 9pt; font-weight: 700">
														<!-- <bean:message key="periodicity" /> -->
													</span>
												</td>
												<td width="16.5%" height="18" class="ColumnBackground">
												
													<span style="font-size: 9pt; font-weight: 700">
													<%				
													if((appTCFlag.equals("11"))||(appTCFlag.equals("13")) || (appTCFlag.equals("3")) || (appTCFlag.equals("6")))						
													{
													%>
													<html:select property="periodicity" name="appForm" disabled="true">
														<html:option value="">Select</html:option>
														<html:option value="1">Monthly</html:option>
														<html:option value="2">Quarterly</html:option>
														<html:option value="3">Half-Yearly</html:option>
                                                                                                                <html:option value="4">Annually</html:option>
                                                                                                                <html:option value="5">Weekly</html:option>
													</html:select>

													<%}
													else
													{ %>
													<html:select property="periodicity" name="appForm">
														<html:option value="">Select</html:option>
														<html:option value="1">Monthly</html:option>
														<html:option value="2">Quarterly</html:option>
														<html:option value="3">Half-Yearly</html:option>
                                                                                                                <html:option value="4">Annually</html:option>
                                                                                                                <html:option value="5">Weekly</html:option>
													</html:select>
													<%}%>
													</span>
														
												 </td> 
												 
												<td width="25%" height="18" class="ColumnBackground">
													<span style="font-size: 9pt; font-weight: 700"><font color="#FF0000" size="2">*</font>&nbsp;
													<bean:message key="noOfInstallments" />
													</span>
												</td>
												<td width="25%" height="18" class="TableData">
											
													<span style="font-size: 9pt; font-weight: 700">
													<%				
													if((appTCFlag.equals("11"))||(appTCFlag.equals("13")) || (appTCFlag.equals("3")) || (appTCFlag.equals("6")))						
													{
													%>
													<bean:write name="appForm" property="noOfInstallments"/>
													<%}
													else
													{ %>
													<html:text property="noOfInstallments" size="5" alt="noOfInstallments" name="appForm" maxlength="3" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)"/>
													<%}%>
													</span>
												
												</td>
											</tr> --%>
				</tbody>
			</table> <html:hidden property="pplOS" styleId="pplOSId" name="appForm" /> <html:hidden
				property="pplOsAsOnDate" styleId="pplOsAsOnDateId" name="appForm" />
		</td>
	</tr>
	<%-- <tr> 
									<td class="ColumnBackground" height="28" width="252">&nbsp;
										<!-- <bean:message key="pplOS" /> -->
									</td>
									<td colspan="10" class="TableData" height="28" width="590">
									<%				
										if((appTCFlag.equals("11"))||(appTCFlag.equals("13")) || (appTCFlag.equals("3")) || (appTCFlag.equals("6")))			

										{
										%>
										<bean:write name="appForm" property="pplOS"/>
										<%}
										else
										{ %>
									
										<!-- <html:text property="pplOS" size="5" alt="pplOS" name="appForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/> -->
								     
									<%}%>										
										<bean:message key="inRs" />&nbsp;
										<bean:message key="pplOsAsOnDate" />
										<%				
										if((appTCFlag.equals("11"))||(appTCFlag.equals("13")) || (appTCFlag.equals("3")) || (appTCFlag.equals("6")))

										{
										%>
										<bean:write name="appForm" property="pplOsAsOnDate"/>
										<%}
										else
										{ %>
										
										<html:text property="pplOsAsOnDate" size="20" alt="pplOsAsOnDate" name="appForm" maxlength="10"/>
										<IMG src="images/CalendarIcon.gif" width="20" onclick="showCalendar('appForm.pplOsAsOnDate')" align="center">
										<%}%>
											
									</td>
								</tr> --%>
</table>
<!--end of term -->


<!-- END -->

