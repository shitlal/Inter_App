	<%@page import="com.cgtsi.registration.MLIInfo"%>
	<%@page import="java.util.Iterator"%>
	<%@ page import="com.cgtsi.util.SessionConstants"%>
	<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html"%>
	<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean"%>
	 <script src = "js/jquery.min.1.12.4.js"></script>
	 <link href = "css/jquery-ui.css"   rel = "stylesheet">
      <script src = "js/jquery-ui.js"></script>
      
	<script type="text/javascript">
	function checkAgeMinority(){
		 if(null!=document.getElementById('cpDobId')){
		 var dateString = document.getElementById('cpDobId').value;
		  var dateSplit = dateString.split("/");
		  var currentDt = new Date();
		  var currentYrr = currentDt.getFullYear();		 
		  var inputDt = parseInt(dateSplit[2]);	 
		  var current18Min = parseInt(currentYrr) - 18;	
	     if (inputDt > current18Min)
		  {
		      alert("Promoter age is minor.");
		      document.getElementById('cpDobId').value.focus();
		      return false;
		  }      
	     
	    if((Math.abs(currentYrr - inputDt)) > 100){
	   	 alert("Promoter age should not be more than 100.");
		      document.getElementById('cpDobId').value='';
		      return false;
	    }
	  }
	}
	
	function calltotalAmt()
	{
	var sanctionedamt=document.getElementById('termCreditSanctioned');
	var fundbasedamt=document.getElementById('wcFundBasedSanctioned');
	var nonfundbasedamt=document.getElementById('wcNonFundBasedSanctioned');
	
	
	if (!(isNaN(sanctionedamt.value)) && (sanctionedamt.value != ""))
		{
	sanctionedamtValue=parseFloat(sanctionedamt.value);
		}
		else
		{
		sanctionedamtValue=0;
		}
	
	if (!(isNaN(fundbasedamt.value)) && (fundbasedamt.value != ""))
		{
	fundbasedamtValue=parseFloat(fundbasedamt.value);
		}
		else
		{
		fundbasedamtValue=0;
		}
	if (!(isNaN(nonfundbasedamt.value)) && (nonfundbasedamt.value != ""))
		{
	nonfundbasedamtValue=parseFloat(nonfundbasedamt.value);
		}
	else
		{
		nonfundbasedamtValue=0;
		}
		//alert("sukant");
	var amtVal=sanctionedamtValue+fundbasedamtValue+nonfundbasedamtValue;
	//alert("sukant"+amtVal);
			if (amtVal>1000000)
			{
			document.forms[0].agree.disabled=false;
			document.forms[0].agree.checked=false;
			}
			else{
			document.forms[0].agree.disabled=true;
			document.forms[0].agree.checked=false;
			}	
	}
	
	function calSecurityAvail(flag)
	{
			if(flag == 'N')
			{
				alert('The scheme envisages creation of Primary Security out of the loan / credit provided to the borrower.');
				 //document.getElementById("pSecurityY").checked=true;
			}	
	}
	function pmudra(flag){
	
	    if(flag =='Y')
	        {
	         //alert('As the credit facility is already covered under PMMY/MUDRA , Same can not be covered under CGTMSE.');
	 			alert('As the credit facility is already covered under NCGTC Mudra Guarantee Programme, Same cannot be covered under CGTMSE.');
	 			  document.getElementById("pmmymudraN").checked=true;			  
	 			 document.getElementById("pmmymudraY").checked=false;  			
	        }
		
	}
	
	
	//Added by DKR. for GST
	function getGSTValue()	{
	var xmlhttp;
   if (window.XMLHttpRequest){
       xmlhttp = new XMLHttpRequest(); //for IE7+, Firefox, Chrome, Opera, Safari
   } else {
       xmlhttp = new ActiveXObject("Microsoft.XMLHTTP"); //for IE6, IE5
   }			
   var stateCode = document.getElementById("stateCode").value; 			   		 
   if(stateCode!=null || stateCode!='')
   {
   	xmlhttp.open("POST", "getGSTByStateCode.do?method=getGSTNO&stateCode="+stateCode, true);		    
   	xmlhttp.onreadystatechange = function() {			    	
   	if (xmlhttp.readyState == 4) 
   	{			    		
   		if (xmlhttp.status == 200)
   		{		    			         	
       	   	if(xmlhttp.responseText.trim()!="0"){      		
       	   	  document.getElementById('gstNo').value=xmlhttp.responseText.trim();
       	   	 document.getElementById('pmmymudraN').checked=true;        	   		    		
		    				              
		    }else{	            	
		    		alert('Invalid State. Please contact to CGTMSE@Support');
		    }
           }
           else{
           	alert("Something is wrong !! Plz contact CGTMSE Support[support@cgtmse.com] team");               
           }
         
   	  }
   	};
   	xmlhttp.send(null);
   }
   else
   {
	    alert('State not mapped with any GST No. Please contact to head office.');			    	
   }
}	
	
	
	function getCGTItpanGuaranteeAmt()	{
		var xmlhttps;
	    if (window.XMLHttpRequest){
	        xmlhttps = new XMLHttpRequest(); //for IE7+, Firefox, Chrome, Opera, Safari
	    } else {
	        xmlhttps = new ActiveXObject("Microsoft.XMLHTTP"); //for IE6, IE5
	    }		
	    
	     var hybridSecVal='';
		 var ssiITPanVal='';
		   if(null!=document.querySelector('input[name="hybridSecurity"]:checked')){
		   	     hybridSecVal = document.querySelector('input[name="hybridSecurity"]:checked').value;
			 }
		   if(null!=document.getElementById("ssiITPan")){
			   ssiITPanVal = document.getElementById("ssiITPan").value;		
		   }else if(null!=document.getElementById("cpITPAN")){
			   ssiITPanVal = document.getElementById("cpITPAN").value;   	 
		   }/*else{
		   	document.getElementById('existExpoCgt').value=0.0;
		   }*/				   		 
	   // if((hybridSecVal=='Y' || hybridSecVal=='y') && (null!=ssiITPanVal || ssiITPanVal!=''))
	    	 if((null!=ssiITPanVal || ssiITPanVal!=''))
	    {	
	    	xmlhttps.open("POST", "findGauranteeAmountByItpan.do?method=findGauranteeAmountByItpan&ssiITPanVal="+ssiITPanVal, true);
	    	//xmlHttp.setRequestHeader("X-Requested-With", "XMLHttpRequest");
	    	xmlhttps.onreadystatechange = function() {			    	
	    	if (xmlhttps.readyState == 4) 
	    	{			    		
	    		if (xmlhttps.status == 200)
	    		{		    			         	
	        	  if(xmlhttps.responseText.trim()!="0"){      		
	        		//  alert(xmlhttps.responseText.trim());
	        	   	  document.getElementById('existExpoCgt').value=xmlhttps.responseText.trim();				              
			      }else{	            	
			    		//alert('Invalid State. Please contact to CGTMSE@Support');
			    	  document.getElementById('existExpoCgt').value=0.0;
			      }
	            }
	            else{
	            	//alert("Something is wrong !!"); 
	            	document.getElementById('existExpoCgt').value=0.0;
	            }
	          
	    	  }
	    	};
	    	xmlhttps.send(null);
	    }
	    else
	    {
	    	document.getElementById('existExpoCgt').value=0.0;
		   // alert('State not mapped with any GST No. Please contact to head office.');			    	
	    }
	}

	$( document ).ready(function() {
		if(document.getElementById("creditGuaranteed")!=null &&
				document.getElementById("creditGuaranteed").value!= "" && document.getElementById("creditGuaranteed").value!="0.0"){
		var creditGuaranteedVal = parseFloat(document.getElementById("creditGuaranteed").value);
		document.getElementById("creditGuaranteed").value=creditGuaranteedVal;
		}
		if(document.getElementById("termCreditSanctioned")!=null &&
				document.getElementById("termCreditSanctioned").value!= "" && document.getElementById("termCreditSanctioned").value!="0.0"){
		var termCreditSanctionedVal = parseFloat(document.getElementById("termCreditSanctioned").value);
		document.getElementById("termCreditSanctioned").value=termCreditSanctionedVal;
		}
		
		
		if(document.getElementById("amtDisbursed")!=null &&
				document.getElementById("amtDisbursed").value!= "" && document.getElementById("amtDisbursed").value!="0.0"){
		var amtDisbursedVal = parseFloat(document.getElementById("amtDisbursed").value);
		document.getElementById("amtDisbursed").value=amtDisbursedVal;
		}
		
		if(document.getElementById("projectOutlayId")!=null &&
				document.getElementById("projectOutlayId").value!= "" && document.getElementById("projectOutlayId").value!="0.0"){
		var projectOutlayIdVal = parseFloat(document.getElementById("projectOutlayId").value);
		document.getElementById("projectOutlayId").value=projectOutlayIdVal;
		}
		
       // jQuery('#filter-date, #amountSanctionedDate, #search-to-date').datetimepicker();tcPromoterContribution
       
	});
	</script>
		<!--start for app details-->
	<TABLE width="100%" border="0" cellspacing="1" cellpadding="0">
		<tr>
			<TD align="left" colspan="4"><font size="2"><bold>
					Fields marked as </font><font color="#FF0000" size="2">*</font><font
				size="2"> are mandatory</bold></font></td>
		</tr>
		<TR>
			<TD colspan="4">
				<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
					<TR>
						<TD width="31%" class="Heading"><bean:message
								key="applicationHeader" /></TD>
						<TD><IMG src="images/TriangleSubhead.gif" width="19"
							height="19"></TD>
					</TR>
					<TR>
						<TD colspan="12" class="Heading"><IMG src="images/Clear.gif"
							width="5" height="5"> <input type="hidden"
							name="hiddenGstNo" id="hiddenGstNo"></TD>
					</TR>
				</TABLE>
			</TD>
		</TR>
		<!-- added by dkr 2021 -->
		<TR align="left">
			<TD align="left" valign="top" class="ColumnBackground" colspan="2"
				width="15%"><font color="#FF0000" size="2">*</font>&nbsp; <bean:message
					key="branchCode" /> </TD>
			<TD align="left" valign="top" class="ColumnBackground" width="10%">
			 <%
				String appCommonLoanType = (String) session.getAttribute(SessionConstants.APPLICATION_LOAN_TYPE);
					String appCommonFlag = (String) session.getAttribute(SessionConstants.APPLICATION_TYPE_FLAG);
				if ((appCommonFlag.equals("1")) || (appCommonFlag.equals("11"))
							|| (appCommonFlag.equals("12"))
							|| (appCommonFlag.equals("13"))) {
				%> <bean:write name="appForm" property="mliBranchCode" /> <%
	 	} else {
	 %> <html:text property="mliBranchCode" size="25" alt="Bank Code"
					name="appForm" maxlength="13" /> <%
	 	}
	 %> 
			</TD>
			
			<%-- <TD align="left" valign="top" class="ColumnBackground" width="25%">
				<font color="#FF0000" size="2">*</font>&nbsp;<bean:message
					key="branchCode" />
			<TD align="left" valign="top" class="ColumnBackground" width="25%">
				<%
					if ((appCommonFlag.equals("1")) || (appCommonFlag.equals("11"))
							|| (appCommonFlag.equals("12"))
							|| (appCommonFlag.equals("13"))) {
				%><!--
											<bean:write name="appForm" property="mliBranchCode"/>
										--> <html:text property="mliBranchCode" size="20"
					alt="Branch Code" name="appForm" maxlength="10" /> <%
	 	} else {
	 %> <html:text property="mliBranchCode" size="20" alt="Branch Code"
					name="appForm" maxlength="10" /> <%
	 	}
	 %>	
			</TD> --%>			
			<TD align="left" valign="top" class="ColumnBackground" width="25%">
				<font color="#FF0000" size="2">*</font>&nbsp;<bean:message
					key="branchName" /><html:hidden property="mliRefNo" name="appForm" />
			</TD>
			<TD align="left" valign="top" class="ColumnBackground" width="25%">
				<%
				if ((appCommonFlag.equals("1")) || (appCommonFlag.equals("11"))
						|| (appCommonFlag.equals("12"))
						|| (appCommonFlag.equals("13"))) {
				%> <bean:write name="appForm" property="mliBranchName" /> <%
	 	} else {
	 %><html:text property="mliBranchName" size="20" alt="branch Name"
					name="appForm" maxlength="100" /> <%
	 	}
	 %>
			</TD>
			<TD align="left" valign="top" class="ColumnBackground" width="25%">
				<%-- <font color="#FF0000" size="2">*</font>&nbsp;<bean:message
					key="branchCode" /> --%>
			<TD align="left" valign="top" class="ColumnBackground" width="25%">
				<%
					if ((appCommonFlag.equals("1")) || (appCommonFlag.equals("11"))
							|| (appCommonFlag.equals("12"))
							|| (appCommonFlag.equals("13"))) {
				%><!--
											<bean:write name="appForm" property="mliBranchCode"/>
										--> <%-- <html:text property="mliBranchCode" size="20"
					alt="Branch Code" name="appForm" maxlength="10" /> --%> <%
	 	} else {
	 %> <%-- <html:text property="mliBranchCode" size="20" alt="Branch Code"
					name="appForm" maxlength="10" />  --%><%
	 	}
	 %>
	
			</TD>
		</TR>
		<TR align="left" class="dkrtooltip">
			<TD align="left" valign="top" class="TableData" colspan="2"><b><bean:message
						key="branchState" /></b><span class="dkrtooltiptext"> <bean:message
						key="branchGstTooltip" />
			</span></td>
			<td style="background: #C4DEE6;">
				<%
							if ((appCommonFlag.equals("1")) || (appCommonFlag.equals("11"))
									|| (appCommonFlag.equals("12"))
									|| (appCommonFlag.equals("13"))) {
						%><!--
											<bean:write name="appForm" property="state"/>
										--> 
					<html:select property="gstState" styleId="stateCode"
					onchange="return getGSTValue();">
					<html:option value="">-- Branch State --</html:option>
					<html:optionsCollection name="appForm" property="branchStateList"
						label="stateName" value="stateCode" />
				</html:select> <%
	 	} else {
	 %> <html:select property="gstState" styleId="stateCode"
					onchange="return getGSTValue();">
					<html:option value=""> Branch State</html:option>
					<html:optionsCollection name="appForm" property="branchStateList"
						label="stateName" value="stateCode" />
				</html:select> <%
	 	}
	 %>
			</TD>
			<TD align="left" valign="top" class="ColumnBackground">&nbsp;<bean:message
					key="gstlable" /></td>
			<TD align="left" valign="top" class="TableData">
				<%
					if ((appCommonFlag.equals("1")) || (appCommonFlag.equals("11"))
							|| (appCommonFlag.equals("12"))
							|| (appCommonFlag.equals("13"))) {
				%> <input type="text" id="gstNo" name="gstNo"
				value="<bean:write name="appForm" property="gstNo" scope="session"/>"
				readonly style="color: red; background: #C4DEE6"> <%
	 	} else {
	 %> <input type="text" id="gstNo" name="gstNo"
				value="<bean:write name="appForm" property="gstNo" scope="session"/>"
				readonly style="color: red; background: #C4DEE6"> <%
	 	}
	 %>
			</TD>
			<TD align="left" valign="top" class="ColumnBackground"><font
				color="#FF0000" size="2">*</font>&nbsp; Loan Account Number</TD>
			<TD align="left" valign="top" class="TableData"><html:text
					property="bankAcNo" size="20" name="appForm" maxlength="16" /></TD>
		</TR>
	
		<%
				String objhybridUIflag = "";
				if (session.getAttribute("hybridUIflag") != null) {
					objhybridUIflag = (String) session.getAttribute("hybridUIflag")
							.toString();
				} else {
					session.setAttribute("hybridUIflag", "FALSE");
				}
				if (objhybridUIflag.equals("DTRUE")) {
			%>
		<TR align="left" class="dkrtooltip">
			<TD align="left" valign="top" class="ColumnBackground" colspan="2"
				width="25%"><bean:message key="hybridSecurityLbl" /> <span
				class="dkrtooltiptext"> <bean:message key="hybridTooltip" />
			</span></TD>
			<TD align="left" valign="top" class="TableData"><html:radio
					name="appForm" value="Y" property="hybridSecurity"
					styleId="hybridSecurityY" onclick="enableHybridSecurity()"></html:radio>
				<bean:message key="yes" />&nbsp;&nbsp; <html:radio name="appForm"
					value="N" property="hybridSecurity" styleId="hybridSecurityN"
					onclick="enableHybridSecurity()"></html:radio> <bean:message
					key="no" /></TD>
	
	
			<%
							}
						%>
			<!--  End 35   -->
	
	
	
	
	
			<%-- <TR align="left">
			<TD align="left" valign="top" class="ColumnBackground">&nbsp;<bean:message
					key="gstlable" /></td>
			<TD align="left" valign="top" class="TableData">
				<%
											if ((appCommonFlag.equals("1")) || (appCommonFlag.equals("11"))
													|| (appCommonFlag.equals("12"))
													|| (appCommonFlag.equals("13"))) {
										%> <input type="text" id="gstNo" name="gstNo"
				value="<bean:write name="appForm" property="gstNo" scope="session"/>"
				readonly style="color: red; background: #C4DEE6"> <%
											} else {
										%> <input type="text" id="gstNo" name="gstNo"
				value="<bean:write name="appForm" property="gstNo" scope="session"/>"
				readonly style="color: red; background: #C4DEE6"> <%
												}
											%>
			</TD>
		</TR> --%>
			<td class="ColumnBackground" valign="top" width="25%"><font
				color="#FF0000" size="2">*</font>&nbsp;<bean:message
					key="collateralSec" />
				<div class="dkrtooltip">
					<span class="dkrtooltiptext"> <bean:message
							key="collateralSecHint" /></span>
				</div></td>
			<td align="left" valign="middle" class="TableData" width="25%">
				<%
					if (appCommonFlag.equals("11") || appCommonFlag.equals("12")
							|| appCommonFlag.equals("13")) {
				%> <!--				<bean:write property="collateralSecurityTaken" name="appForm"/>-->
				<%-- <html:radio name="appForm" value="Y"
					property="collateralSecurityTaken" disabled="true">
				</html:radio> Yes <html:radio name="appForm" value="N"
					property="collateralSecurityTaken" disabled="true"></html:radio> No --%>
				<bean:write property="collateralSecurityTaken" name="appForm" /> <%
					} else {
				%> <html:radio name="appForm" value="Y"
					property="collateralSecurityTaken"
					styleId="collateralSecurityTakenY" onclick="updateHybridSecurity();">
				</html:radio> Yes <html:radio name="appForm" value="N"
					property="collateralSecurityTaken"
					styleId="collateralSecurityTakenN" onclick="updateHybridSecurity();"></html:radio>
				No <%
					}
				%>
	
			</td>
			<td class="ColumnBackground" width="25%">&nbsp; <font
				color="#FF0000" size="1"></font>&nbsp;<bean:message
					key="thirdPartyTaken" />
				<div class="dkrtooltip">
					<font color="#FF0000" size="2">*</font>&nbsp;<span
						class="dkrtooltiptext"> <bean:message
							key="thirdPartyTooltipTxt" /></span>
				</div>
			</td>
			<td align="left" valign="middle" class="TableData" width="25%">
				<%
					if (appCommonFlag.equals("11") || appCommonFlag.equals("12")
							|| appCommonFlag.equals("13")) {
				%> <!--					<bean:write property="thirdPartyGuaranteeTaken" name="appForm"/>-->
				<html:radio name="appForm" value="Y"
					property="thirdPartyGuaranteeTaken" disabled="true">
					Yes
				</html:radio> <html:radio name="appForm" value="N"
					property="thirdPartyGuaranteeTaken" disabled="true">
	
					No
				</html:radio> <%
	 	} else {
	 %> <html:radio name="appForm" value="Y"
					property="thirdPartyGuaranteeTaken"
					styleId="thirdPartyGuaranteeTakenY"
					onclick="thirdPartyGuaranteeTakenForApp()">
					Yes
				</html:radio> <html:radio name="appForm" value="N"
					property="thirdPartyGuaranteeTaken"
					styleId="thirdPartyGuaranteeTakenN"
					onclick="thirdPartyGuaranteeTakenForApp()">
	
					No
				</html:radio> <%
	 	}
	 %>
	
			</td>
		</TR>
		<!-- ----------------------- Application  2021 --------------------- -->
	
	
		<TR align="left">
			<TD align="left" valign="top" class="ColumnBackground" colspan="3"
				width="25%">Credit facility is covered / Propsed to be covered under NCGTC Mudra Guarantee Programme:</TD>
			<TD align="left" valign="top" class="ColumnBackground" width="25%"
				colspan="1"><font color="#FF0000" size="2">*</font>&nbsp; <html:radio
					property="pmmymudra" name="appForm" value="Y" onclick="pmudra('Y');"
					styleId="pmmymudraY">Yes</html:radio> <html:radio
					property="pmmymudra" name="appForm" value="N" onclick="pmudra('N');"
					styleId="pmmymudraN">No</html:radio></TD>
			<TD align="left" valign="top" class="ColumnBackground" colspan="2"
				width="25%">&nbsp; Unit is engaged in Education/Training/Agriculture and Constitution is SHG/JLG/CIG:</TD>
			<TD align="left" valign="top" class="ColumnBackground" width="25%"
				colspan="2"><input type="radio" name="activityConfirm"
				id="activityConfirmY" value="Y" onclick="shgJLgValidationForApp();">Yes
				<input type="radio" name="activityConfirm" id="activityConfirmN"
				value="N" checked="checked" onclick="shgJLgValidationForApp();">No
			</TD>
		</TR>
	
	
	
	
	</TABLE>
	<!--closing for app details-->
	
	
	<!--table starting -->
	<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
		<TR>
			<TD colspan="7">
				<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
					<TR>
						<TD width="31%" class="Heading"><bean:message
								key="borrowerHeader" /></TD>
						<TD><IMG src="images/TriangleSubhead.gif" width="19"
							height="19"></TD>
					</TR>
					<TR>
						<TD colspan="6" class="Heading"><IMG src="images/Clear.gif"
							width="5" height="5"></TD>
					</TR>
				</TABLE>
			</TD>
		</TR>
		<TR align="left">
	
			<TD align="left" valign="top" class="ColumnBackground" colspan="2">Whether Unit assisted is a Greenfield (New) Unit:</TD>
			<TD align="left" valign="top" class="ColumnBackground"><input
				type="radio" name="unitAssisted" id="unitAssistedY" value="Y"
				onclick="unitAssistCGTPreviouslyValidate();">Yes<input
				type="radio" name="unitAssisted" value="N" id="unitAssistedN"
				checked="checked" onclick="unitAssistCGTPreviouslyValidate();">No
			</TD>
	
			<TD align="left" valign="top" class="ColumnBackground" colspan="2">
				<bean:message key="coveredByCGTSI" />
			</TD>
			<TD align="left" class="TableData" colspan="1">
				<%
					if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
							|| (appCommonFlag.equals("2"))
							|| (appCommonFlag.equals("3"))
							|| (appCommonFlag.equals("4"))
							|| (appCommonFlag.equals("5"))
							|| (appCommonFlag.equals("6"))
							|| (appCommonFlag.equals("11"))
							|| (appCommonFlag.equals("12"))
							|| (appCommonFlag.equals("13"))
							|| (appCommonFlag.equals("14"))
							|| (appCommonFlag.equals("17"))
							|| (appCommonFlag.equals("18"))
							|| (appCommonFlag.equals("19"))) {
				%> <html:radio name="appForm" value="Y" property="previouslyCovered"
					disabled="true"></html:radio> <bean:message key="yes" />&nbsp;&nbsp;
				<html:radio name="appForm" value="N" property="previouslyCovered"
					disabled="true"></html:radio> <bean:message key="no" /> <%
	 	} else {
	 %> <html:radio name="appForm" value="Y" property="previouslyCovered"
					styleId="previouslyCoveredY"
					onclick="enableNone(),unitAssistCGTPreviouslyValidate();"></html:radio>
				<bean:message key="yes" />&nbsp;&nbsp; <html:radio name="appForm"
					value="N" property="previouslyCovered" styleId="previouslyCoveredN"
					onclick="enableNone(),unitAssistCGTPreviouslyValidate();"></html:radio>
				<bean:message key="no" /> <%
	 	}
	 %></TD>	
			<TD align="left" valign="top" class="ColumnBackground" colspan="2">
	
				<!--<TR align="left">
										<TD align="left" valign="top" class="ColumnBackground" colspan="3">
											<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="bankAssistance" />
										</TD>
										<TD align="left" class="TableData" colspan="4"> 
				<%if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
						|| (appCommonFlag.equals("2"))
						|| (appCommonFlag.equals("3"))
						|| (appCommonFlag.equals("4"))
						|| (appCommonFlag.equals("5"))
						|| (appCommonFlag.equals("6"))
						|| (appCommonFlag.equals("11"))
						|| (appCommonFlag.equals("12"))
						|| (appCommonFlag.equals("13"))
						|| (appCommonFlag.equals("14"))
						|| (appCommonFlag.equals("17"))
						|| (appCommonFlag.equals("18"))
						|| (appCommonFlag.equals("19"))) {%>
	<!--						<bean:write property="assistedByBank" name="appForm"/>--> <html:radio
					name="appForm" value="Y" property="assistedByBank" disabled="true"></html:radio>
	
				<bean:message key="yes" /> &nbsp;&nbsp; <html:radio name="appForm"
					value="N" property="assistedByBank" disabled="true"></html:radio> <bean:message
					key="no" /> <%
			} else {
		%> <html:radio name="appForm" value="Y" property="assistedByBank"
					onclick="javascript:enableAssistance()"></html:radio> <bean:message
					key="yes" /> &nbsp;&nbsp; <html:radio name="appForm" value="N"
					property="assistedByBank" onclick="javascript:enableAssistance()"></html:radio>
	
				<bean:message key="no" /> <%
			}
		%>
			</TD>
		</TR>
		<TR align="left">
			<TD align="left" valign="top" class="ColumnBackground" colspan="3"><bean:message
					key="osAmt" /></td>
			<TD align="left" valign="top" class="TableData" colspan="4">
				<%
					if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
							|| (appCommonFlag.equals("2"))
							|| (appCommonFlag.equals("3"))
							|| (appCommonFlag.equals("4"))
							|| (appCommonFlag.equals("5"))
							|| (appCommonFlag.equals("6"))
							|| (appCommonFlag.equals("11"))
							|| (appCommonFlag.equals("12"))
							|| (appCommonFlag.equals("13"))
							|| (appCommonFlag.equals("14"))
							|| (appCommonFlag.equals("17"))
							|| (appCommonFlag.equals("18"))
							|| (appCommonFlag.equals("19")))
	
					{
				%> <bean:write name="appForm" property="osAmt" /> <%
	 	} else {
	 %><html:text property="osAmt" size="20" alt="Outstanding Amount"
					name="appForm" maxlength="16"
					onkeypress="return decimalOnly(this, event,13)"
					onkeyup="isValidDecimal(this)" /> <%
	 	}
	 %> <bean:message key="inRs" />
			</TD>
		</TR>
		<TR align="left">
			<TD align="left" valign="top" class="ColumnBackground" colspan="3">
				<bean:message key="npa" />
			</TD>
			<TD align="left" class="TableData" colspan="4">
				<%
					if ((appCommonFlag.equals("0"))
							|| (appCommonFlag.equals("1"))
							// || (appCommonFlag.equals("2"))
							|| (appCommonFlag.equals("3"))
							|| (appCommonFlag.equals("4"))
							|| (appCommonFlag.equals("5"))
							|| (appCommonFlag.equals("6"))
							|| (appCommonFlag.equals("11"))
							|| (appCommonFlag.equals("12"))
							|| (appCommonFlag.equals("13"))
							|| (appCommonFlag.equals("14"))
							|| (appCommonFlag.equals("17"))
							|| (appCommonFlag.equals("18"))
							|| (appCommonFlag.equals("19"))) {
				%> <bean:write property="npa" name="appForm" /> <%
	 	} else {
	 %> <html:radio name="appForm" value="Y" property="npa"></html:radio> <bean:message
					key="yes" />&nbsp;&nbsp; <html:radio name="appForm" value="N"
					property="npa"></html:radio> <bean:message key="no" /> <%
	 	}
	 %>
			</TD>
		</TR>
		-->
		<%-- 	<TR align="left">
			<TD align="left" valign="top" class="ColumnBackground" colspan="2">
				<bean:message key="coveredByCGTSI" />
			</TD>
			<TD align="left" class="TableData" colspan="1">
				<%
					if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
							|| (appCommonFlag.equals("2"))
							|| (appCommonFlag.equals("3"))
							|| (appCommonFlag.equals("4"))
							|| (appCommonFlag.equals("5"))
							|| (appCommonFlag.equals("6"))
							|| (appCommonFlag.equals("11"))
							|| (appCommonFlag.equals("12"))
							|| (appCommonFlag.equals("13"))
							|| (appCommonFlag.equals("14"))
							|| (appCommonFlag.equals("17"))
							|| (appCommonFlag.equals("18"))
							|| (appCommonFlag.equals("19"))) {
				%> <html:radio name="appForm" value="Y"
					property="previouslyCovered" disabled="true"></html:radio> <bean:message
					key="yes" />&nbsp;&nbsp; <html:radio name="appForm" value="N"
					property="previouslyCovered" disabled="true"></html:radio> <bean:message
					key="no" /> <%
	 	} else {
	 %> <html:radio name="appForm" value="Y"
					property="previouslyCovered" onclick="enableNone()"></html:radio> <bean:message
					key="yes" />&nbsp;&nbsp; <html:radio name="appForm" value="N"
					property="previouslyCovered" onclick="enableNone()"></html:radio> <bean:message
					key="no" /> <%
	 	}
	 %>
	
			</TD>
		</TR> --%>
	
		<TR>
			<TD colspan="9">
				<TABLE width="100%" border="0" cellpadding="1" cellspacing="1">
					<TR align="left">
						<%
							if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
									|| (appCommonFlag.equals("2"))
									|| (appCommonFlag.equals("3"))
									|| (appCommonFlag.equals("4"))
									|| (appCommonFlag.equals("5"))
									|| (appCommonFlag.equals("6"))
									|| (appCommonFlag.equals("11"))
									|| (appCommonFlag.equals("12"))
									|| (appCommonFlag.equals("13"))
									|| (appCommonFlag.equals("14"))
									|| (appCommonFlag.equals("17"))
									|| (appCommonFlag.equals("18"))
									|| (appCommonFlag.equals("19"))) {
						%>
						                      <script>
													booleanVal=true;
												</script>
	
						<TD align="left" valign="top" class="ColumnBackground" width="25%"><html:radio
								name="appForm" value="none" property="none"  style="display:none">
								<%-- <bean:message key="none" /> --%>
							</html:radio></TD>
						<TD align="left" valign="top" class="ColumnBackground"><html:radio
								name="appForm" value="cgbid" property="none" disabled="false" style="display:none">
								<%-- <bean:message key="cgbid" /> --%>
							</html:radio></TD>
						<TD align="left" valign="top" class="ColumnBackground" width="25%"><html:radio
								name="appForm" value="cgpan" property="none" disabled="false">
								<bean:message key="cgpan" />
							</html:radio></TD>
						<%
							} else {
						%>
	
						<TD align="left" valign="top" class="ColumnBackground" width="25%" style="display:none"><html:radio
								name="appForm" value="none" property="none" style="display:none">
								<%-- <bean:message key="none" /> --%>
							</html:radio></TD>
						<TD align="left" valign="top" class="ColumnBackground" style="display:none"><html:radio
								name="appForm" value="cgbid" property="none" disabled="false" style="display:none">
								<%-- <bean:message key="cgbid" /> --%>
							</html:radio></TD>
							 <TD align="left" valign="top" class="ColumnBackground" width="25%"><html:radio
								name="appForm" value="cgpan" property="none" disabled="false">
								<bean:message key="cgpan" />
							</html:radio> </TD> 
						<%
	 	}
	 %>
					
						<TD align="left" valign="top" class="ColumnBackground" width="25%">
							<%
								if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
										|| (appCommonFlag.equals("2"))
										|| (appCommonFlag.equals("3"))
										|| (appCommonFlag.equals("4"))
										|| (appCommonFlag.equals("5"))
										|| (appCommonFlag.equals("6"))
										|| (appCommonFlag.equals("11"))
										|| (appCommonFlag.equals("12"))
										|| (appCommonFlag.equals("13"))) {
							%> <script>
													booleanVal=true;
												</script> <html:text property="unitValue" size="15" alt="Value"
								name="appForm" maxlength="15" disabled="true" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<%
								} else if ((appCommonFlag.equals("14"))
										|| (appCommonFlag.equals("17"))
										|| (appCommonFlag.equals("18"))
										|| (appCommonFlag.equals("19"))) {
							%> <script>
													booleanVal=true;
												</script> <html:text property="unitValue" size="15" alt="Value"
								name="appForm" maxlength="15" disabled="false" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	
							<%
								} else {
							%> <script>
													booleanVal=false;
												</script> <html:text property="unitValue" size="15" alt="Value"
								name="appForm" maxlength="15" disabled="false" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
							<%
								}
							%> <%
	 	if ((appCommonFlag.equals("7")) || (appCommonFlag.equals("8"))
	 			|| (appCommonFlag.equals("9"))
	 			|| (appCommonFlag.equals("10"))
	 			|| (appCommonFlag.equals("14"))
	 			|| (appCommonFlag.equals("17"))
	 			|| (appCommonFlag.equals("18"))
	 			|| (appCommonFlag.equals("19"))) {
	 %> <a
							href="javascript:submitForm('afterTcMli.do?method=getBorrowerDetails')"><bean:message
									key="getBorrowerDetails" /></a> <%
	 	}
	 %>
						</TD>
		
		<TD align="left" valign="top" class="ColumnBackground">&nbsp;<bean:message
								key="balanceApprovedAmt" />
						</TD>
						<TD align="left" valign="top" class="ColumnBackground">
							<%
								if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
										|| (appCommonFlag.equals("2"))
										|| (appCommonFlag.equals("3"))
										|| (appCommonFlag.equals("4"))
										|| (appCommonFlag.equals("5"))
										|| (appCommonFlag.equals("6"))
										|| (appCommonFlag.equals("11"))
										|| (appCommonFlag.equals("12"))
										|| (appCommonFlag.equals("13"))
										|| (appCommonFlag.equals("14"))
										|| (appCommonFlag.equals("17"))
										|| (appCommonFlag.equals("18"))
										|| (appCommonFlag.equals("19"))) {
							%> <bean:write property="balanceApprovedAmt" name="appForm" /> <%
								}
							%>
						</TD>
	
						<TD align="left" valign="top" class="ColumnBackground" colspan="2"><font
							color="#FF0000" size="2">*</font>&nbsp;<bean:message
								key="constitution" /></TD>
						<TD align="left" valign="top" class="ColumnBackground">
							<%
								if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
										|| (appCommonFlag.equals("2"))
										|| (appCommonFlag.equals("3"))
										|| (appCommonFlag.equals("4"))
										|| (appCommonFlag.equals("5"))
										|| (appCommonFlag.equals("6"))
										|| (appCommonFlag.equals("11"))
										|| (appCommonFlag.equals("12"))
										|| (appCommonFlag.equals("13"))
										|| (appCommonFlag.equals("14"))
										|| (appCommonFlag.equals("17"))
										|| (appCommonFlag.equals("18"))
										|| (appCommonFlag.equals("19"))) {
							%> <html:select property="constitution" name="appForm"
								disabled="true">
								<html:option value="">Select</html:option>
								<html:option value="Proprietary/Individual">Proprietary/Individual</html:option>
								<html:option value="Partnership">Partnership Firm</html:option>
								<html:option value="Limited liability Partnership">Limited liability Partnership</html:option>
								<html:option value="private">
									<bean:message key="private" />
								</html:option>
	
								<html:option value="public">
									<bean:message key="public" />
								</html:option>
								<html:option value="HUF">Hindu Undivided Family</html:option>
								<html:option value="Trust">Trust</html:option>
								<html:option value="Society/Co op">Society/Co op Society</html:option>
								<!--	<html:option value="Others"><bean:message key="others" /></html:option> -->
							</html:select> <!--				<bean:write property="constitution" name="appForm"/>--> <%
	 	} else {
	 %> <html:select property="constitution" name="appForm"
								onchange="setConstEnabled()">
								<html:option value="">Select</html:option>
								<html:option value="Proprietary/Individual">Proprietary/Individual</html:option>
								<html:option value="Partnership">Partnership Firm</html:option>
								<html:option value="Limited liability Partnership">Limited liability Partnership</html:option>
								<html:option value="private">
									<bean:message key="private" />
								</html:option>
	
								<html:option value="public">
									<bean:message key="public" />
								</html:option>
								<html:option value="HUF">Hindu Undivided Family</html:option>
								<html:option value="Trust">Trust</html:option>
								<html:option value="Society/Co op">Society/Co op Society</html:option>
	
								<!--	<html:option value="Others"><bean:message key="others" /></html:option> -->
							</html:select>&nbsp;&nbsp;&nbsp;&nbsp; <%
	 	}
	 %> <%
	 	if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
	 			|| (appCommonFlag.equals("2"))
	 			|| (appCommonFlag.equals("3"))
	 			|| (appCommonFlag.equals("4"))
	 			|| (appCommonFlag.equals("5"))
	 			|| (appCommonFlag.equals("6"))
	 			|| (appCommonFlag.equals("11"))
	 			|| (appCommonFlag.equals("12"))
	 			|| (appCommonFlag.equals("13"))
	 			|| (appCommonFlag.equals("14"))
	 			|| (appCommonFlag.equals("17"))
	 			|| (appCommonFlag.equals("18"))
	 			|| (appCommonFlag.equals("19"))) {
	 %> <bean:write property="constitutionOther" name="appForm" /> <%
	 	} else {
	 %> <%-- <html:text property="constitutionOther" size="15"
								alt="constitution" name="appForm" maxlength="50" disabled="true" /> --%>
							<%
								}
							%>
						</TD>
		
					</TR>
	
					<TR align="left">
						
					</TR>
	
	
					<%-- <TR align="left">
						<TD align="left" valign="top" class="ColumnBackground" width="30%"><font
							color="#FF0000" size="2">*</font>&nbsp;<bean:message
								key="constitution" /></TD>
						<TD align="left" valign="top" class="TableData" colspan="8">
							<%
														if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
																|| (appCommonFlag.equals("2"))
																|| (appCommonFlag.equals("3"))
																|| (appCommonFlag.equals("4"))
																|| (appCommonFlag.equals("5"))
																|| (appCommonFlag.equals("6"))
																|| (appCommonFlag.equals("11"))
																|| (appCommonFlag.equals("12"))
																|| (appCommonFlag.equals("13"))
																|| (appCommonFlag.equals("14"))
																|| (appCommonFlag.equals("17"))
																|| (appCommonFlag.equals("18"))
																|| (appCommonFlag.equals("19"))) {
													%> <html:select property="constitution" name="appForm"
								disabled="true">
								<html:option value="">Select</html:option>
								<html:option value="Proprietary/Individual">Proprietary/Individual</html:option>
								<html:option value="Partnership">Partnership</html:option>
								<html:option value="Limited liability Partnership">Limited liability Partnership</html:option>
								<html:option value="private">
									<bean:message key="private" />
								</html:option>
	
								<html:option value="public">
									<bean:message key="public" />
								</html:option>
								<html:option value="HUF">HUF</html:option>
								<html:option value="Trust">Trust</html:option>
								<html:option value="Society/Co op">Society/Co op Society</html:option>
								<!--	<html:option value="Others"><bean:message key="others" /></html:option> -->
							</html:select> <!--				<bean:write property="constitution" name="appForm"/>--> <%
														} else {
													%> <html:select property="constitution" name="appForm"
								onchange="setConstEnabled()">
								<html:option value="">Select</html:option>
								<html:option value="Proprietary/Individual">Proprietary/Individual</html:option>
								<html:option value="Partnership">Partnership</html:option>
								<html:option value="Limited liability Partnership">Limited liability Partnership</html:option>
								<html:option value="private">
									<bean:message key="private" />
								</html:option>
	
								<html:option value="public">
									<bean:message key="public" />
								</html:option>
								<html:option value="HUF">HUF</html:option>
								<html:option value="Trust">Trust</html:option>
								<html:option value="Society/Co op">Society/Co op Society</html:option>
	
								<!--	<html:option value="Others"><bean:message key="others" /></html:option> -->
							</html:select>&nbsp;&nbsp;&nbsp;&nbsp; <%
																}
															%> <%
																									 	if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
																									 			|| (appCommonFlag.equals("2"))
																									 			|| (appCommonFlag.equals("3"))
																									 			|| (appCommonFlag.equals("4"))
																									 			|| (appCommonFlag.equals("5"))
																									 			|| (appCommonFlag.equals("6"))
																									 			|| (appCommonFlag.equals("11"))
																									 			|| (appCommonFlag.equals("12"))
																									 			|| (appCommonFlag.equals("13"))
																									 			|| (appCommonFlag.equals("14"))
																									 			|| (appCommonFlag.equals("17"))
																									 			|| (appCommonFlag.equals("18"))
																									 			|| (appCommonFlag.equals("19"))) {
																									 %> <bean:write property="constitutionOther"
								name="appForm" /> <%
														} else {
													%> <html:text property="constitutionOther" size="15"
								alt="constitution" name="appForm" maxlength="50" disabled="true" />
							<%
														}
													%>
	
						</TD>
					</TR> --%>
	
	
					<TR>
						<TD align="left" valign="top" class="ColumnBackground">Unit
							Assisted is an MSE as per the MSMED Act</TD>
						<TD align="left" valign="top" class="ColumnBackground"><html:radio
								name="appForm" value="Y" property="mSE" styleId="mseY"
								onclick="mseMsmedValidation()">Yes
													</html:radio> <html:radio name="appForm" value="N" property="mSE"
								styleId="mseN" onclick="mseMsmedValidation()">No</html:radio></TD>
	
						<TD align="left" valign="top" class="ColumnBackground">Unit
							Assisted is a Micro Enterprise</TD>
						<TD align="left" valign="top" class="ColumnBackground" colspan="6">
							<!--<input	type="radio" name="enterprise" value="Y">Yes
			<input	type="radio" name="enterprise" value="N" checked="checked">No</TD>-->
	
							<html:radio property="enterprise" name="appForm" value="Y">Yes</html:radio>
							<html:radio property="enterprise" name="appForm" value="N">No</html:radio>
						</TD>
	
					</TR>
	
	
					<TR align="left">
						<TD align="left" valign="top" class="ColumnBackground" width="30%"><font
							color="#FF0000" size="2">*</font>&nbsp;<bean:message
								key="unitName" /></TD>
						<TD align="left" valign="top" class="ColumnBackground" colspan="2">
							<%
								if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
										|| (appCommonFlag.equals("2"))
										|| (appCommonFlag.equals("3"))
										|| (appCommonFlag.equals("4"))
										|| (appCommonFlag.equals("5"))
										|| (appCommonFlag.equals("6"))
										|| (appCommonFlag.equals("11"))
										|| (appCommonFlag.equals("12"))
										|| (appCommonFlag.equals("13"))
										|| (appCommonFlag.equals("14"))
										|| (appCommonFlag.equals("17"))
										|| (appCommonFlag.equals("18"))
										|| (appCommonFlag.equals("19"))) {
							%> <bean:write property="ssiType" name="appForm" /> <%
	 	} else {
	 %> <%-- <html:select property="ssiType" name="appForm" style="width:15%">
								<html:option value="">Select</html:option>
								<html:option value="M/s">M/s</html:option>
								<html:option value="Shri">Shri</html:option>
								<html:option value="Smt">Smt</html:option>
								<html:option value="Ku">Ku</html:option>
							</html:select>&nbsp;&nbsp;&nbsp;&nbsp; --%> <html:hidden
								property="ssiType" name="appForm" value="M/s" /> <%
	 	} if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
	 			|| (appCommonFlag.equals("2"))
	 			|| (appCommonFlag.equals("3"))
	 			|| (appCommonFlag.equals("4"))
	 			|| (appCommonFlag.equals("5"))
	 			|| (appCommonFlag.equals("6"))
	 			|| (appCommonFlag.equals("11"))
	 			|| (appCommonFlag.equals("12"))
	 			|| (appCommonFlag.equals("13"))
	 			|| (appCommonFlag.equals("14"))
	 			|| (appCommonFlag.equals("17"))
	 			|| (appCommonFlag.equals("18"))
	 			|| (appCommonFlag.equals("19")))
	
	 	{
	 %> <bean:write name="appForm" property="ssiName" /> <%
	 	} else {
	 %> <html:text property="ssiName" name="appForm" maxlength="100"
								size="500" alt="unitName" style="width:35%;" /> <%
	 														}
	 													%>
						</td>
						<TD align="left" valign="top" class="TableData" width="15%"><font
							color="#FF0000" size="2">*</font>&nbsp;<b><bean:message
								key="unitAddress" /></b></td>
						<TD align="left" valign="top" class="ColumnBackground" colspan="5">
							<%
								if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
										|| (appCommonFlag.equals("2"))
										|| (appCommonFlag.equals("3"))
										|| (appCommonFlag.equals("4"))
										|| (appCommonFlag.equals("5"))
										|| (appCommonFlag.equals("6"))
										|| (appCommonFlag.equals("11"))
										|| (appCommonFlag.equals("12"))
										|| (appCommonFlag.equals("13"))
										|| (appCommonFlag.equals("14"))
										|| (appCommonFlag.equals("17"))
										|| (appCommonFlag.equals("18"))
										|| (appCommonFlag.equals("19")))
	
								{
							%> <bean:write name="appForm" property="address" /> <%
	 	} else {
	 %> <div align="right"><html:textarea property="address" cols="50" alt="address" 
								name="appForm" rows="3" /></div> <%
	 	}
	 %>
						</td>
					</tr>
	
					<TR align="left">
						<TD align="left" valign="top" class="ColumnBackground"><font
							color="#FF0000" size="2">*</font>
						<bean:message key="state" /></TD>
						<TD align="left" valign="top" class="ColumnBackground">
							<%
															if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
																	|| (appCommonFlag.equals("2"))
																	|| (appCommonFlag.equals("3"))
																	|| (appCommonFlag.equals("4"))
																	|| (appCommonFlag.equals("5"))
																	|| (appCommonFlag.equals("6"))
																	|| (appCommonFlag.equals("11"))
																	|| (appCommonFlag.equals("12"))
																	|| (appCommonFlag.equals("13"))
																	|| (appCommonFlag.equals("14"))
																	|| (appCommonFlag.equals("17"))
																	|| (appCommonFlag.equals("18"))
																	|| (appCommonFlag.equals("19")))
								
															{
														%> <bean:write name="appForm" property="state" /> <%
								 	} else {
								 %> <html:select property="state" name="appForm"
								onchange="javascript:submitForm('afterTcMli.do?method=getDistricts')">
								<html:option value="">Select</html:option>
								<html:options name="appForm" property="statesList"></html:options>
	
							</html:select> <%
								 	}
								 %>
						</TD>
						<TD align="left" valign="top" class="ColumnBackground"><font
							color="#FF0000" size="2">*</font>&nbsp;<bean:message
								key="district" /></TD>
						<TD align="left" valign="top" class="ColumnBackground">
							<%
															if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
																	|| (appCommonFlag.equals("2"))
																	|| (appCommonFlag.equals("3"))
																	|| (appCommonFlag.equals("4"))
																	|| (appCommonFlag.equals("5"))
																	|| (appCommonFlag.equals("6"))
																	|| (appCommonFlag.equals("11"))
																	|| (appCommonFlag.equals("12"))
																	|| (appCommonFlag.equals("13"))
																	|| (appCommonFlag.equals("14"))
																	|| (appCommonFlag.equals("17"))
																	|| (appCommonFlag.equals("18"))
																	|| (appCommonFlag.equals("19")))
								
															{
														%> <bean:write name="appForm" property="district" /> <%
								 	} else {
								 %> <html:select property="district" name="appForm">
								<html:option value="">Select</html:option>
								<html:options name="appForm" property="districtList" />
							</html:select> <%
															}
														%>
						</TD>
						<TD align="left" valign="top" class="ColumnBackground" colspan="4">
							<font color="#FF0000" size="2">*</font>&nbsp;<bean:message
								key="city" />
						</TD>
						<TD align="left" valign="top" class="ColumnBackground">
							<%
															if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
																	|| (appCommonFlag.equals("2"))
																	|| (appCommonFlag.equals("3"))
																	|| (appCommonFlag.equals("4"))
																	|| (appCommonFlag.equals("5"))
																	|| (appCommonFlag.equals("6"))
																	|| (appCommonFlag.equals("11"))
																	|| (appCommonFlag.equals("12"))
																	|| (appCommonFlag.equals("13"))
																	|| (appCommonFlag.equals("14"))
																	|| (appCommonFlag.equals("17"))
																	|| (appCommonFlag.equals("18"))
																	|| (appCommonFlag.equals("19")))
								
															{
														%> <bean:write name="appForm" property="city" /> <%
								 	} else {
								 %> <html:text property="city" size="20" alt="city"
								name="appForm" maxlength="100" /> <%
								 	}
								 %>
						</td>
					</TR>
	
					<TR align="left">
						<TD align="left" valign="top" class="ColumnBackground" width="25%"><font
							color="#FF0000" size="2">*</font>&nbsp;<bean:message key="pinCode" />
						</td>
						<TD align="left" valign="top" class="TableData" width="25%"
							colspan="1">
							<%
								if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
										|| (appCommonFlag.equals("2"))
										|| (appCommonFlag.equals("3"))
										|| (appCommonFlag.equals("4"))
										|| (appCommonFlag.equals("5"))
										|| (appCommonFlag.equals("6"))
										|| (appCommonFlag.equals("11"))
										|| (appCommonFlag.equals("12"))
										|| (appCommonFlag.equals("13"))
										|| (appCommonFlag.equals("14"))
										|| (appCommonFlag.equals("17"))
										|| (appCommonFlag.equals("18"))
										|| (appCommonFlag.equals("19")))
	
								{
							%> <bean:write name="appForm" property="pincode" /> <%
	 	} else {
	 %> <html:text property="pincode" size="20" alt="pinCode" name="appForm"
								maxlength="6" onkeypress="return numbersOnly(this, event)"
								onkeyup="isValidNumber(this)" /> <%
	 	}
	 %>
						</td>
	
						<TD align="left" valign="top" class="ColumnBackground" width="25%">
						<div class="dkrtooltip"><span class="dkrtooltiptext"> <bean:message key="ssiItpanTooltipTxt" /></span></div>
						&nbsp;<font	color="#FF0000" size="2">*</font> <bean:message key="firmItpan" /><br/><font color="#FF0000" size="1">(In respect of Individuals ITPAN of borrower is to be filled)</font>			
						</td>
						<TD align="left" valign="top" class="TableData" width="15%">
							<%
								if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
										|| (appCommonFlag.equals("2"))
										|| (appCommonFlag.equals("3"))
										|| (appCommonFlag.equals("4"))
										|| (appCommonFlag.equals("5"))
										|| (appCommonFlag.equals("6"))
										|| (appCommonFlag.equals("11"))
										|| (appCommonFlag.equals("12"))
										|| (appCommonFlag.equals("13"))
										|| (appCommonFlag.equals("14"))
										|| (appCommonFlag.equals("17"))
										|| (appCommonFlag.equals("18"))
										|| (appCommonFlag.equals("19")))
	
								{
							%>
						<html:text property="ssiITPan" styleId="ssiITPan" size="20"
								alt="ssiITPan" name="appForm" maxlength="10"
								onkeyup="getCGTItpanGuaranteeAmt();" /> 	
							<%-- <bean:write name="appForm" property="ssiITPan" /> --%>
						 <%
	 	} else {
			 %><html:text property="ssiITPan" styleId="ssiITPan" size="20"
								alt="ssiITPan" name="appForm" maxlength="10"
								onkeyup="getCGTItpanGuaranteeAmt();" /> <%
	 	}
	 %>
						</td>
						<!-- regNo -->
						<TD align="left" valign="top" class="ColumnBackground" width="25%"
							colspan="4">&nbsp;<bean:message key="udyogAdharNo" />
						</td>
						<TD align="left" valign="top" class="TableData" width="15%"
							colspan="2">
							<%
								if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
										|| (appCommonFlag.equals("2"))
										|| (appCommonFlag.equals("3"))
										|| (appCommonFlag.equals("4"))
										|| (appCommonFlag.equals("5"))
										|| (appCommonFlag.equals("6"))
										|| (appCommonFlag.equals("11"))
										|| (appCommonFlag.equals("12"))
										|| (appCommonFlag.equals("13"))
										|| (appCommonFlag.equals("14"))
										|| (appCommonFlag.equals("17"))
										|| (appCommonFlag.equals("18"))
										|| (appCommonFlag.equals("19")))
	
								{
							%> <bean:write name="appForm" property="udyogAdharNo" /> <%
						 	} else {
						 %> <html:text property="udyogAdharNo" size="30" alt="udyogAdharNo"
													name="appForm" maxlength="12" /> <%
						 	}
						 %>
						</td>
											
					</TR>
	
					<TR align="left">
						<%-- <TD align="left" valign="top" class="ColumnBackground" width="25%">&nbsp;<bean:message
								key="firmItpan" />
						</td>
						<TD align="left" valign="top" class="TableData" width="15%">
							<%
								if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
										|| (appCommonFlag.equals("2"))
										|| (appCommonFlag.equals("3"))
										|| (appCommonFlag.equals("4"))
										|| (appCommonFlag.equals("5"))
										|| (appCommonFlag.equals("6"))
										|| (appCommonFlag.equals("11"))
										|| (appCommonFlag.equals("12"))
										|| (appCommonFlag.equals("13"))
										|| (appCommonFlag.equals("14"))
										|| (appCommonFlag.equals("17"))
										|| (appCommonFlag.equals("18"))
										|| (appCommonFlag.equals("19")))
	
								{
							%> <bean:write name="appForm" property="ssiITPan" /> <%
	 	} else {
	 %><html:text property="ssiITPan" styleId="ssiITPan" size="20" alt="ITPAN"
								name="appForm" maxlength="10" onkeyup="getCGTItpanGuaranteeAmt();" /> <%
	 	}
	 %>
						</td>
						<TD align="left" valign="top" class="ColumnBackground" width="25%">&nbsp;<bean:message
								key="ssiRegNo" />
						</td>
						<TD align="left" valign="top" class="TableData" width="15%"
							colspan="2">
							<%
								if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
										|| (appCommonFlag.equals("2"))
										|| (appCommonFlag.equals("3"))
										|| (appCommonFlag.equals("4"))
										|| (appCommonFlag.equals("5"))
										|| (appCommonFlag.equals("6"))
										|| (appCommonFlag.equals("11"))
										|| (appCommonFlag.equals("12"))
										|| (appCommonFlag.equals("13"))
										|| (appCommonFlag.equals("14"))
										|| (appCommonFlag.equals("17"))
										|| (appCommonFlag.equals("18"))
										|| (appCommonFlag.equals("19")))
	
								{
							%> <bean:write name="appForm" property="regNo" /> <%
	 	} else {
	 %> <html:text property="regNo" size="30" alt="SSI Reg No"
								name="appForm" maxlength="25" /> <%
	 	}
	 %>
						</td> --%>
	
					</tr>
	<TR align="left" class="dkrtooltip">
						<TD align="left" valign="top" class="ColumnBackground" width="16.5%"><font
							color="#FF0000" size="2">*</font>&nbsp;<bean:message
								key="industryNature" /><span class="dkrtooltiptext"> <bean:message
									key="industrySectorTooltipdkr" />
						</span></TD>
						<TD align="left" valign="top" class="TableData" width="16.5%">
							<%
								if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
										|| (appCommonFlag.equals("2"))
										|| (appCommonFlag.equals("3"))
										|| (appCommonFlag.equals("4"))
										|| (appCommonFlag.equals("5"))
										|| (appCommonFlag.equals("6"))
										|| (appCommonFlag.equals("11"))
										|| (appCommonFlag.equals("12"))
										|| (appCommonFlag.equals("13"))
										|| (appCommonFlag.equals("14"))
										|| (appCommonFlag.equals("17"))
										|| (appCommonFlag.equals("18"))
										|| (appCommonFlag.equals("19")))
	
								{
							%> <bean:write name="appForm" property="activityType" /> <%
	 	} else {
	 %>                      <%-- <html:select property="industryNature" name="appForm"
								styleId="industryNature"
								onchange="javascript:submitForm('afterTcMli.do?method=getIndustrySector');">
								<html:option value="">Select</html:option>
								<html:options name="appForm" property="industryNatureList"></html:options>
							</html:select>  --%>
							<html:select property="activityType" styleId="activityType" name="appForm">
								<html:option value="">Select</html:option>
								<html:option value="MANUFACTURING SSI">MANUFACTURING</html:option>
								<html:option value="SSSBE">SERVICES</html:option>
								<html:option value="RETAIL TRADE">RETAIL TRADE</html:option>
							</html:select> 
							
							<%
	 	}
	 %>
						</td>
						<TD align="left" valign="top" class="ColumnBackground" width="16.5%">&nbsp;<font color="#FF0000" size="2">*</font> <bean:message
								key="activitytype" />
						</TD>
						<TD align="left" valign="top" class="TableData" width="16.5%">
							
							<%
								if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
										|| (appCommonFlag.equals("2"))
										|| (appCommonFlag.equals("3"))
										|| (appCommonFlag.equals("4"))
										|| (appCommonFlag.equals("5"))
										|| (appCommonFlag.equals("6"))
										|| (appCommonFlag.equals("11"))
										|| (appCommonFlag.equals("12"))
										|| (appCommonFlag.equals("13"))
										|| (appCommonFlag.equals("14"))
										|| (appCommonFlag.equals("17"))
										|| (appCommonFlag.equals("18"))
										|| (appCommonFlag.equals("19")))
	
								{
							%>  <bean:write name="appForm" property="industryNature" /> 
							<%-- <html:select property="industryNature" name="appForm"
								styleId="industryNature"
								onchange="javascript:submitForm('afterTcMli.do?method=getIndustrySector');">
								<html:option value="">Select</html:option>
								<html:options name="appForm" property="industryNatureList"></html:options>
							</html:select>  --%>
							 <%
						 	} else {
						 %> 
						 <html:select property="industryNature" name="appForm"
								styleId="industryNature" onchange="javascript:submitForm('afterTcMli.do?method=getIndustrySector');">
								<html:option value="">Select</html:option>
								<html:options name="appForm" property="industryNatureList"></html:options>
							</html:select> 
						 <%-- <html:select property="activityType" name="appForm"
								onchange="enableActivityType()">
								<html:option value="">Select</html:option>
								<html:option value="Manufacturing">Manufacturing</html:option>
								<html:option value="Services">Services</html:option>
								<html:option value="Retail Trade">Retail trade</html:option>
							   </html:select>  --%>
							  
							<%
						 	}
						 %>
						</td>
						<TD align="left" valign="top" class="ColumnBackground" width="16.5%" colspan="4"><font
							color="#FF0000" size="2">*</font>&nbsp;<bean:message
								key="industrySector" /></td>
						<TD align="left" valign="top" class="TableData" width="16.5%">
							<%
								if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
										|| (appCommonFlag.equals("2"))
										|| (appCommonFlag.equals("3"))
										|| (appCommonFlag.equals("4"))
										|| (appCommonFlag.equals("5"))
										|| (appCommonFlag.equals("6"))
										|| (appCommonFlag.equals("11"))
										|| (appCommonFlag.equals("12"))
										|| (appCommonFlag.equals("13"))
										|| (appCommonFlag.equals("14"))
										|| (appCommonFlag.equals("17"))
										|| (appCommonFlag.equals("18"))
										|| (appCommonFlag.equals("19")))
	
								{
							%> <bean:write name="appForm" property="industrySector" /> <%
							 	} else {
							 %> <html:select property="industrySector" styleId="industrySector" name="appForm">													
														<html:option value="">Select</html:option>
														<html:options name="appForm" property="industrySectorList"></html:options>
														<!-- <html:option value="Others">Others</html:option>-->
													</html:select> <%
							 	}
							 %>
						</td>
	
						
					</tr>
					<%-- <TR align="left" class="dkrtooltip">
						<TD align="left" valign="top" class="ColumnBackground" width="30%"><font
							color="#FF0000" size="2">*</font>&nbsp;<bean:message
								key="industryNature" /><span class="dkrtooltiptext"> <bean:message
									key="industrySectorTooltipdkr" />
						</span></TD>
						<TD align="left" valign="top" class="TableData">
							<%
								if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
										|| (appCommonFlag.equals("2"))
										|| (appCommonFlag.equals("3"))
										|| (appCommonFlag.equals("4"))
										|| (appCommonFlag.equals("5"))
										|| (appCommonFlag.equals("6"))
										|| (appCommonFlag.equals("11"))
										|| (appCommonFlag.equals("12"))
										|| (appCommonFlag.equals("13"))
										|| (appCommonFlag.equals("14"))
										|| (appCommonFlag.equals("17"))
										|| (appCommonFlag.equals("18"))
										|| (appCommonFlag.equals("19")))
	
								{
							%> <bean:write name="appForm" property="industryNature" /> <%
	 	} else {
	 %> <html:select property="industryNature" name="appForm"
								styleId="industryNature"
								onchange="javascript:submitForm('afterTcMli.do?method=getIndustrySector');">
								<html:option value="">Select</html:option>
								<html:options name="appForm" property="industryNatureList"></html:options>
							</html:select> <%
	 	}
	 %>
						</td>
						<TD align="left" valign="top" class="ColumnBackground" width="20%"><font
							color="#FF0000" size="2">*</font>&nbsp;<bean:message
								key="industrySector" /></td>
						<TD align="left" valign="top" class="TableData" colspan="1">
							<%
								if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
										|| (appCommonFlag.equals("2"))
										|| (appCommonFlag.equals("3"))
										|| (appCommonFlag.equals("4"))
										|| (appCommonFlag.equals("5"))
										|| (appCommonFlag.equals("6"))
										|| (appCommonFlag.equals("11"))
										|| (appCommonFlag.equals("12"))
										|| (appCommonFlag.equals("13"))
										|| (appCommonFlag.equals("14"))
										|| (appCommonFlag.equals("17"))
										|| (appCommonFlag.equals("18"))
										|| (appCommonFlag.equals("19")))
	
								{
							%> <bean:write name="appForm" property="industrySector" /> <%
	 	} else {
	 %> <html:select property="industrySector" name="appForm"
								onchange="javascript:submitForm('afterTcMli.do?method=getTypeOfActivity')">
								<html:option value="">Select</html:option>
								<html:options name="appForm" property="industrySectorList"></html:options>
								<!-- <html:option value="Others">Others</html:option>-->
							</html:select> <%
	 	}
	 %>
						</td>
	
						<TD align="left" valign="top" class="ColumnBackground" width="20%"
							colspan="2">&nbsp;<font color="#FF0000" size="2">*</font> <bean:message
								key="activitytype" />
						</TD>
						<TD align="left" valign="top" class="TableData"
							onmouseover="getSelectIndustryRetail();">
							<%
								if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
										|| (appCommonFlag.equals("2"))
										|| (appCommonFlag.equals("3"))
										|| (appCommonFlag.equals("4"))
										|| (appCommonFlag.equals("5"))
										|| (appCommonFlag.equals("6"))
										|| (appCommonFlag.equals("11"))
										|| (appCommonFlag.equals("12"))
										|| (appCommonFlag.equals("13"))
										|| (appCommonFlag.equals("14"))
										|| (appCommonFlag.equals("17"))
										|| (appCommonFlag.equals("18"))
										|| (appCommonFlag.equals("19")))
	
								{
							%> <html:text property="activityType" styleId="activityType"
								size="20" alt="Activity Type" name="appForm" maxlength="75" /> 
							<html:select property="activityType" name="appForm"
								onchange="enableActivityType()">
								<html:option value="">Select</html:option>
								<html:option value="Manufacturing">Manufacturing</html:option>
								<html:option value="Services">Services</html:option>
								<html:option value="Retail Trade">Retail trade</html:option>
							</html:select> <%
						 	} else {
						 %> <html:select property="activityType" name="appForm"
								onchange="enableActivityType()">
								<html:option value="">Select</html:option>
								<html:option value="Manufacturing">Manufacturing</html:option>
								<html:option value="Services">Services</html:option>
								<html:option value="Retail Trade">Retail trade</html:option>
							</html:select>  <html:text property="activityType" size="20"	styleId="activityType" alt="Activity Type" name="appForm"
													maxlength="75" /> <%
						 	}
						 %>
						</td>
					</tr> --%>
	
					<TR align="left">
						<%-- <TD align="left" valign="top" class="ColumnBackground" width="30%">&nbsp;<font
							color="#FF0000" size="2">*</font>
						<bean:message key="activitytype" />
						</TD>
						<TD align="left" valign="top" class="TableData"
							onmouseover="getSelectIndustryRetail();">
							<%
								if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
										|| (appCommonFlag.equals("2"))
										|| (appCommonFlag.equals("3"))
										|| (appCommonFlag.equals("4"))
										|| (appCommonFlag.equals("5"))
										|| (appCommonFlag.equals("6"))
										|| (appCommonFlag.equals("11"))
										|| (appCommonFlag.equals("12"))
										|| (appCommonFlag.equals("13"))
										|| (appCommonFlag.equals("14"))
										|| (appCommonFlag.equals("17"))
										|| (appCommonFlag.equals("18"))
										|| (appCommonFlag.equals("19")))
	
								{
							%> <html:text property="activityType" styleId="activityType"
								size="20" alt="Activity Type" name="appForm" maxlength="75" /> <%
	 	} else {
	 %> <html:text property="activityType" size="20"
								styleId="activityType" alt="Activity Type" name="appForm"
								maxlength="75" /> <%
	 	}
	 %>
						</td> --%>
						<TD align="left" valign="top" class="ColumnBackground" width="25%"><font
							color="#FF0000" size="2">*</font> <bean:message
								key="noOfEmployees" /></td>
						<TD align="left" valign="top" class="TableData" width="15%">
							<%
								if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
										|| (appCommonFlag.equals("2"))
										|| (appCommonFlag.equals("3"))
										|| (appCommonFlag.equals("4"))
										|| (appCommonFlag.equals("5"))
										|| (appCommonFlag.equals("6"))
										|| (appCommonFlag.equals("11"))
										|| (appCommonFlag.equals("12"))
										|| (appCommonFlag.equals("13"))
										|| (appCommonFlag.equals("14"))
										|| (appCommonFlag.equals("17"))
										|| (appCommonFlag.equals("18"))
										|| (appCommonFlag.equals("19")))
	
								{
							%> <html:text property="employeeNos" size="10"
								alt="No Of employees" name="appForm" maxlength="4" /> <%
	 	} else {
	 %><html:text property="employeeNos" size="10" alt="No Of employees"
								name="appForm" onkeypress="return numbersOnly(this, event)"
								onkeyup="isValidNumber(this)" maxlength="4" /> <%
	 	}
	 %>
						</td>
	
						<TD align="left" valign="top" class="ColumnBackground"><font
							color="#FF0000" size="2">*</font> <bean:message key="turnover" /></TD>
						<TD align="left" valign="top" class="TableData">
							<%
								if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
										|| (appCommonFlag.equals("2"))
										|| (appCommonFlag.equals("3"))
										|| (appCommonFlag.equals("4"))
										|| (appCommonFlag.equals("5"))
										|| (appCommonFlag.equals("6"))
										|| (appCommonFlag.equals("11"))
										|| (appCommonFlag.equals("12"))
										|| (appCommonFlag.equals("13"))
										|| (appCommonFlag.equals("14"))
										|| (appCommonFlag.equals("17"))
										|| (appCommonFlag.equals("18"))
										|| (appCommonFlag.equals("19"))) {
							%> <html:text property="projectedSalesTurnover" size="10"
								styleId="projectedSalesTurnover"
								onblur="checkAmountwith10k('projectedSalesTurnover');"
								alt="turnover" name="appForm" maxlength="16" /> <%
	 	} else {
	 %>                  <html:text property="projectedSalesTurnover" name="appForm"
								styleId="projectedSalesTurnover"
								onblur="checkAmountwith10k('projectedSalesTurnover');"
								onkeypress="return decimalOnly(this, event,13);"
								onkeyup="isValidDecimal(this)" size="20" maxlength="16"
								alt="turnover" /> <%
	 	}
	 %>
						</td>
						<TD align="left" valign="top" class="ColumnBackground" width="25%" colspan="4">&nbsp;<font
							color="red">*</font>
						<bean:message key="exports" /></td>
						<TD align="left" valign="top" class="TableData" width="15%"
							colspan="2">
							<%
								if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
										|| (appCommonFlag.equals("2"))
										|| (appCommonFlag.equals("3"))
										|| (appCommonFlag.equals("4"))
										|| (appCommonFlag.equals("5"))
										|| (appCommonFlag.equals("6"))
										|| (appCommonFlag.equals("11"))
										|| (appCommonFlag.equals("12"))
										|| (appCommonFlag.equals("13"))
										|| (appCommonFlag.equals("14"))
										|| (appCommonFlag.equals("17"))
										|| (appCommonFlag.equals("18"))
										|| (appCommonFlag.equals("19")))
	
								{
							%> <bean:write name="appForm" property="projectedExports" />
							<%-- <bean:message key="inRs" /> --%> <%
								} else {
							%> <html:text property="projectedExports" styleId="projectedExports" size="7" alt="exports" name="appForm" maxlength="16"
								onkeypress="return decimalOnly(this, event,13)"
								onkeyup="isValidDecimal(this)" /> <%
	 	}
	 %>
						</td>
					</tr>
					<!-- <TR>
						<TD align="left" valign="top" class="ColumnBackground" colspan="2"><font
							color="red"><B>Whether the Unit is engaged in Training and Constitution is SHG/JLG:</B></font></TD>
						<TD align="left" valign="top" class="ColumnBackground" colspan="5">
							<input type="radio" name="activityConfirm" value="Y">Yes<input
							type="radio" name="activityConfirm" value="N" checked="checked">No
						</TD>
	
					</TR> -->
	
					<TR align="left">
						<%-- <TD align="left" valign="top" class="ColumnBackground" width="30%"><font
							color="#FF0000" size="2">*</font>
						<bean:message key="turnover" /></TD>
						<TD align="left" valign="top" class="TableData">
							<%
								if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
										|| (appCommonFlag.equals("2"))
										|| (appCommonFlag.equals("3"))
										|| (appCommonFlag.equals("4"))
										|| (appCommonFlag.equals("5"))
										|| (appCommonFlag.equals("6"))
										|| (appCommonFlag.equals("11"))
										|| (appCommonFlag.equals("12"))
										|| (appCommonFlag.equals("13"))
										|| (appCommonFlag.equals("14"))
										|| (appCommonFlag.equals("17"))
										|| (appCommonFlag.equals("18"))
										|| (appCommonFlag.equals("19"))) {
							%> <html:text property="projectedSalesTurnover" size="10"
								alt="turnover" name="appForm" maxlength="16" /> <%
	 	} else {
	 %> <html:text property="projectedSalesTurnover" size="20"
								alt="turnover" name="appForm" maxlength="16"
								onkeypress="return decimalOnly(this, event,13)"
								onkeyup="isValidDecimal(this)" /> <%
	 	}
	 %>
						</td>
						<TD align="left" valign="top" class="ColumnBackground" width="25%"><bean:message
								key="exports" /></td>
						<TD align="left" valign="top" class="TableData" width="15%"
							colspan="2">
							<%
								if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
										|| (appCommonFlag.equals("2"))
										|| (appCommonFlag.equals("3"))
										|| (appCommonFlag.equals("4"))
										|| (appCommonFlag.equals("5"))
										|| (appCommonFlag.equals("6"))
										|| (appCommonFlag.equals("11"))
										|| (appCommonFlag.equals("12"))
										|| (appCommonFlag.equals("13"))
										|| (appCommonFlag.equals("14"))
										|| (appCommonFlag.equals("17"))
										|| (appCommonFlag.equals("18"))
										|| (appCommonFlag.equals("19")))
	
								{
							%> <bean:write name="appForm" property="projectedExports" /><bean:message key="inRs" />
	
							<%
								} else {
							%> <html:text property="projectedExports" size="7"
								alt="exports" name="appForm" maxlength="16"
								onkeypress="return decimalOnly(this, event,13)"
								onkeyup="isValidDecimal(this)" /> <%
	 	}
	 %>
						</td> --%>
					</tr>
	
	
				</TABLE>
			</TD>
		</TR>
		<tr>
			<td></td>
		</tr>
		<TR>
			<TD class="SubHeading" colspan="6"><bean:message
					key="promoterHeader" /></TD>
		</TR>
	
		<TR align="left">
			<TD align="left" valign="top" class="ColumnBackground" colspan="7">
				<bean:message key="chiefInfo" />
			</TD>
		</TR>
	
		<TR align="left">
			<TD align="left" valign="top" class="ColumnBackground" colspan="2"
				width="15%"></TD>
			<TD align="left" valign="top" class="ColumnBackground" width="10%">
				<font color="#FF0000" size="2">*</font>&nbsp;<bean:message
					key="title" />
			</TD>
			<TD align="left" valign="top" class="ColumnBackground" width="25%">
				<font color="#FF0000" size="2">*</font>&nbsp;<bean:message
					key="firstName" />
			</TD>
			<TD align="left" valign="top" class="ColumnBackground" width="25%">
				<bean:message key="middleName" />
			</TD>
			<TD align="left" valign="top" class="ColumnBackground" width="25%" colspan="3">
				<font color="#FF0000" size="2">*</font>&nbsp;<bean:message
					key="lastName" /><br/><font color="#FF0000" size="1">(If No Last Name, Please mention First Name)</font>
			</TD>
		</TR>
	
		<TR align="left">
			<TD align="left" valign="top" class="ColumnBackground" colspan="2"
				width="15%"><bean:message key="name" /></TD>
			<TD align="left" valign="top" class="TableData">
				<%
					if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
							|| (appCommonFlag.equals("2"))
							|| (appCommonFlag.equals("3"))
							|| (appCommonFlag.equals("4"))
							|| (appCommonFlag.equals("5"))
							|| (appCommonFlag.equals("6"))
							|| (appCommonFlag.equals("11"))
							|| (appCommonFlag.equals("12"))
							|| (appCommonFlag.equals("13"))
							|| (appCommonFlag.equals("14"))
							|| (appCommonFlag.equals("17"))
							|| (appCommonFlag.equals("18"))
							|| (appCommonFlag.equals("19")))
	
					{
				%> <bean:write property="cpTitle" name="appForm" /> <%
	 	} else {
	 %> <html:select property="cpTitle" name="appForm" styleId="cpTitleId"
					onchange="enableGender()">
					<html:option value="">Select</html:option>
					<html:option value="Mr.">Mr.</html:option>
					<html:option value="Smt">Mrs.</html:option>
					<html:option value="Ku">Ms.</html:option>
					<html:option value="Mx">Mx.</html:option>
				</html:select> <%
	 	}
	 %>
	
			</TD>
			<TD align="left" valign="top" class="TableData">
				<%
					if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
							|| (appCommonFlag.equals("2"))
							|| (appCommonFlag.equals("3"))
							|| (appCommonFlag.equals("4"))
							|| (appCommonFlag.equals("5"))
							|| (appCommonFlag.equals("6"))
							|| (appCommonFlag.equals("11"))
							|| (appCommonFlag.equals("12"))
							|| (appCommonFlag.equals("13"))
							|| (appCommonFlag.equals("14"))
							|| (appCommonFlag.equals("17"))
							|| (appCommonFlag.equals("18"))
							|| (appCommonFlag.equals("19"))) {
				%> <bean:write property="cpFirstName" name="appForm" /> <%
	 	} else {
	 %> <html:text property="cpFirstName" size="20" alt="firstName"
					name="appForm" maxlength="20" /> <%
	 	}
	 %>
	
			</TD>
			<TD align="left" valign="top" class="TableData">
				<%
					if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
							|| (appCommonFlag.equals("2"))
							|| (appCommonFlag.equals("3"))
							|| (appCommonFlag.equals("4"))
							|| (appCommonFlag.equals("5"))
							|| (appCommonFlag.equals("6"))
							|| (appCommonFlag.equals("11"))
							|| (appCommonFlag.equals("12"))
							|| (appCommonFlag.equals("13"))
							|| (appCommonFlag.equals("14"))
							|| (appCommonFlag.equals("17"))
							|| (appCommonFlag.equals("18"))
							|| (appCommonFlag.equals("19")))
	
					{
				%> <bean:write property="cpMiddleName" name="appForm" /> <%
	 	} else {
	 %> <html:text property="cpMiddleName" size="20" alt="middleName"
					name="appForm" maxlength="20" /> <%
	 	}
	 %>
	
			</TD>
			<TD align="left" valign="top" class="TableData" colspan="3">
				<%
					if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
							|| (appCommonFlag.equals("2"))
							|| (appCommonFlag.equals("3"))
							|| (appCommonFlag.equals("4"))
							|| (appCommonFlag.equals("5"))
							|| (appCommonFlag.equals("6"))
							|| (appCommonFlag.equals("11"))
							|| (appCommonFlag.equals("12"))
							|| (appCommonFlag.equals("13"))
							|| (appCommonFlag.equals("14"))
							|| (appCommonFlag.equals("17"))
							|| (appCommonFlag.equals("18"))
							|| (appCommonFlag.equals("19"))) {
				%> <bean:write property="cpLastName" name="appForm" /><%
	 	} else {
	 %> <html:text property="cpLastName" size="20" alt="lastName"
					name="appForm" maxlength="20" /> <%
	 	}
	 %>
			</TD>
		</TR>
	
		<TR align="left">
			<TD align="left" valign="top" class="ColumnBackground" colspan="2"><font
				color="#FF0000" size="2">*</font>&nbsp;<bean:message key="gender" />
			</TD>
			<TD align="left" valign="top" class="TableData" width="25%">
				<%
					if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
							|| (appCommonFlag.equals("2"))
							|| (appCommonFlag.equals("3"))
							|| (appCommonFlag.equals("4"))
							|| (appCommonFlag.equals("5"))
							|| (appCommonFlag.equals("6"))
							|| (appCommonFlag.equals("11"))
							|| (appCommonFlag.equals("12"))
							|| (appCommonFlag.equals("13"))
							|| (appCommonFlag.equals("14"))
							|| (appCommonFlag.equals("17"))
							|| (appCommonFlag.equals("18"))
							|| (appCommonFlag.equals("19"))) {
				%> <bean:write property="cpGender" name="appForm" /> <%
	 	} else {
	 %> <html:radio name="appForm" value="M" property="cpGender"
					styleId="cpGenderM">
				</html:radio> <bean:message key="male" />&nbsp;&nbsp;&nbsp; <html:radio
					name="appForm" value="F" property="cpGender" styleId="cpGenderF"></html:radio>
				<bean:message key="female" />
				<html:radio name="appForm" value="T" property="cpGender"
					styleId="cpGenderT"></html:radio> <bean:message key="transgender" />
				<%
	 	}
	 %>
	
			</TD>
			<TD align="left" valign="top" class="ColumnBackground">&nbsp;</font><font
				color="#FF0000" size="2">*</font>
			<bean:message key="chiefItpan" />
			</TD>
			<TD align="left" valign="top" class="TableData" colspan="3"
				width="20%">
				<%
					if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
							|| (appCommonFlag.equals("2"))
							|| (appCommonFlag.equals("3"))
							|| (appCommonFlag.equals("4"))
							|| (appCommonFlag.equals("5"))
							|| (appCommonFlag.equals("6"))
							|| (appCommonFlag.equals("11"))
							|| (appCommonFlag.equals("12"))
							|| (appCommonFlag.equals("13"))
							|| (appCommonFlag.equals("14"))
							|| (appCommonFlag.equals("17"))
							|| (appCommonFlag.equals("18"))
							|| (appCommonFlag.equals("19"))) {
				%> <%-- <bean:write property="cpITPAN" name="appForm" />  --%>
				  <html:text property="cpITPAN" styleId="cpITPAN" size="15"
					alt="chiefItpan" name="appForm" maxlength="10"
					onkeyup="getCGTItpanGuaranteeAmt();" />
				<%
	 	} else {
	 %><html:text property="cpITPAN" styleId="cpITPAN" size="15"
					alt="chiefItpan" name="appForm" maxlength="10"
					onkeyup="getCGTItpanGuaranteeAmt();" /> <%
	 	}
	 %>
	
			</TD>
		</TR>
		<!--added by sukant/DKR-->
		<%-- <TR>
			<TD align="left" valign="top" class="ColumnBackground" colspan="2">Whether
				the Chief Promoter belongs to Minority Community:</TD>
			<TD align="left" valign="top" class="ColumnBackground" >
				<input type="radio" name="religion" value="Y">Yes<input
				type="radio" name="religion" value="N" checked="checked">No
			</TD>
	
	      <TD align="right" valign="top" class="ColumnBackground" colspan="2"><font
				color="#FF0000" size="2">*</font>&nbsp;Whether the Chief Promoter is
				Physically Handicapped:</TD>
			<TD align="left" valign="top" class="ColumnBackground" colspan="3"><html:radio
					name="appForm" value="Y" property="physicallyHandicapped">Yes
													</html:radio>
				<html:radio name="appForm" value="N" property="physicallyHandicapped">No</html:radio>
			</TD>
		</TR> --%>
		<TR align="left">
			<TD align="left" valign="top" class="ColumnBackground"><font
				color="#FF0000" size="2">*</font> &nbsp;Minority Community:</TD>
			<TD align="left" valign="top" class="ColumnBackground"><input
				type="radio" name="religion" value="Y">Yes<input type="radio"
				name="religion" value="N" checked="checked">No</TD>
	
	
	
			<TD align="left" valign="top" class="ColumnBackground"><font
				color="#FF0000" size="2">*</font>&nbsp; Physically Handicapped:</TD>
			<TD align="left" valign="top" class="ColumnBackground">&nbsp;<html:radio
					name="appForm" value="Y" property="physicallyHandicapped">Yes</html:radio>
				<html:radio name="appForm" value="N" property="physicallyHandicapped">No</html:radio>
			</TD>
	
			<TD align="left" valign="top" class="ColumnBackground"><font
				color="#FF0000" size="2">*</font>&nbsp;<bean:message
					key="promoterMobNo" /></td>
			<TD align="left" valign="top" class="TableData" colspan="2"
				width="20%"><html:text property="proMobileNo" size="20"
					alt="proMobileNo" name="appForm" maxlength="10"
					onkeypress="return numbersOnly(this, event)"
					onkeyup="isValidNumber(this)" /></td>
		</TR>
		<%-- <TR>
			<TD align="left" valign="top" class="ColumnBackground" colspan="3"><font
				color="#FF0000" size="2">*</font>&nbsp;Whether the Chief Promoter is
				Physically Handicapped:</TD>
			<TD align="left" valign="top" class="ColumnBackground" colspan="7"><html:radio
					name="appForm" value="Y" property="physicallyHandicapped">Yes
													</html:radio>
				<html:radio name="appForm" value="N" property="physicallyHandicapped">No</html:radio>
			</TD>
		</TR> --%>
	
		<TR align="left">
			<TD align="left" valign="top" class="ColumnBackground"><font
				color="#FF0000" size="2">*</font> <bean:message key="dob" /></TD>
			<TD align="left" valign="top" class="TableData"
				onblur="checkAgeMinority();" />
			<%
					if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
							|| (appCommonFlag.equals("2"))
							|| (appCommonFlag.equals("3"))
							|| (appCommonFlag.equals("4"))
							|| (appCommonFlag.equals("5"))
							|| (appCommonFlag.equals("6"))
							|| (appCommonFlag.equals("11"))
							|| (appCommonFlag.equals("12"))
							|| (appCommonFlag.equals("13"))
							|| (appCommonFlag.equals("14"))
							|| (appCommonFlag.equals("17"))
							|| (appCommonFlag.equals("18"))
							|| (appCommonFlag.equals("19"))) {
				%>
			<bean:write property="cpDOB" name="appForm" />
			<%
	 	} else {
	 %>
			<html:text property="cpDOB" alt="dob" styleId="cpDobId" name="appForm" 
				maxlength="10" size="15" onblur="checkAgeMinority();" />
			<!-- <IMG src="images/CalendarIcon.gif" width="15"
				onClick="showCalendar('appForm.cpDOB')" align="center" /> -->
			<%
					}
				%>
	
			<TD align="left" valign="top" class="ColumnBackground"><bean:message
					key="socialCategory" /></TD>
			<TD align="left" valign="top" class="TableData" width="20%"
				colspan="1">
				<%
					if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
							|| (appCommonFlag.equals("2"))
							|| (appCommonFlag.equals("3"))
							|| (appCommonFlag.equals("4"))
							|| (appCommonFlag.equals("5"))
							|| (appCommonFlag.equals("6"))
							|| (appCommonFlag.equals("11"))
							|| (appCommonFlag.equals("12"))
							|| (appCommonFlag.equals("13"))
							|| (appCommonFlag.equals("14"))
							|| (appCommonFlag.equals("17"))
							|| (appCommonFlag.equals("18"))
							|| (appCommonFlag.equals("19"))) {
				%> <bean:write property="socialCategory" name="appForm" /> <%
	 	} else {
	 %> <html:select property="socialCategory" name="appForm">
					<html:option value="">Select</html:option>
					<html:options name="appForm" property="socialCategoryList"></html:options>
	
				</html:select> <%
	 	}
	 %>
	
			</TD>
	
			<TD align="left" valign="top" class="ColumnBackground">Unit Assisted is Women Owned / Shareholding Greater than 50%:</TD>
			<TD align="left" valign="top" class="ColumnBackground" colspan="2">
				<input type="radio" name="womenOperated" value="Y"
				id="womenOperatedY" onclick="changeGendarFlag();">Yes <input
				type="radio" name="womenOperated" value="N" checked="checked"
				id="womenOperatedN" onclick="changeGendarFlag();">No
			</TD>
	
	
	
		</TR>
		<!-- <TR align="left"> -->
		<%-- <TD align="left" valign="top" class="ColumnBackground" colspan="3"><font
				color="#FF0000" size="2"></font> &nbsp;<bean:message
					key="udyogAdharNo" /></TD>
			<TD align="left" valign="top" class="ColumnBackground"><html:text
					property="udyogAdharNo" size="12" name="appForm" maxlength="12" /></TD> --%>
	
	
	
		<%-- <TD align="left" valign="top" class="ColumnBackground">
				<font color="#FF0000" size="2">*</font>&nbsp;Loan Account Number</TD>
			<TD align="left" valign="top" class="ColumnBackground">&nbsp;<html:text
					property="bankAcNo" size="20" name="appForm" maxlength="16" />
			</TD> --%>
		</TR>
	
	
		<!--<TR align="left">
										<TD align="left" valign="top" class="ColumnBackground" colspan="2">
											&nbsp;<bean:message key="legalid" />					
										</TD>
										<TD align="left" valign="top" class="TableData" colspan="5">
	
										<%if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
						|| (appCommonFlag.equals("2"))
						|| (appCommonFlag.equals("3"))
						|| (appCommonFlag.equals("4"))
						|| (appCommonFlag.equals("5"))
						|| (appCommonFlag.equals("6"))
						|| (appCommonFlag.equals("11"))
						|| (appCommonFlag.equals("12"))
						|| (appCommonFlag.equals("13"))
						|| (appCommonFlag.equals("14"))
						|| (appCommonFlag.equals("17"))
						|| (appCommonFlag.equals("18"))
						|| (appCommonFlag.equals("19"))) {%>
										<bean:write property="cpLegalID" name="appForm"/>
										<%} else {%>	
																		
											<html:select property="cpLegalID" name="appForm" onchange="enableOtherLegalId()">
												<html:option value="">Select</html:option>
												<html:option value="VoterIdentityCard">Voter Identity Card</html:option>
												<html:option value="PASSPORT">Passport Number</html:option>
												<html:option value="Driving License">Driving License</html:option>
												<html:option value="RationCardnumber">Ration Card Number</html:option>
												<html:option value="Others">Others</html:option>
											</html:select>			
										<%}%>
	
										<%if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
						|| (appCommonFlag.equals("2"))
						|| (appCommonFlag.equals("3"))
						|| (appCommonFlag.equals("4"))
						|| (appCommonFlag.equals("5"))
						|| (appCommonFlag.equals("6"))
						|| (appCommonFlag.equals("11"))
						|| (appCommonFlag.equals("12"))
						|| (appCommonFlag.equals("13"))
						|| (appCommonFlag.equals("14"))
						|| (appCommonFlag.equals("17"))
						|| (appCommonFlag.equals("18"))
						|| (appCommonFlag.equals("19"))) {%>
										<bean:write property="otherCpLegalID" name="appForm"/>
										<%} else {%>																	
											<html:text property="otherCpLegalID" size="20" alt="otherId" name="appForm"  maxlength="50" disabled="true"/>&nbsp;&nbsp;<bean:message key="otherId"/>	
										<%}%>
	
											&nbsp;&nbsp;&nbsp;&nbsp;<strong><bean:message key="pleaseSpecify"/></strong>
	
										<%if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
						|| (appCommonFlag.equals("2"))
						|| (appCommonFlag.equals("3"))
						|| (appCommonFlag.equals("4"))
						|| (appCommonFlag.equals("5"))
						|| (appCommonFlag.equals("6"))
						|| (appCommonFlag.equals("11"))
						|| (appCommonFlag.equals("12"))
						|| (appCommonFlag.equals("13"))
						|| (appCommonFlag.equals("14"))
						|| (appCommonFlag.equals("17"))
						|| (appCommonFlag.equals("18"))
						|| (appCommonFlag.equals("19"))) {%>
										<bean:write property="cpLegalIdValue" name="appForm"/>
										<%} else {%>							
										
											<html:text property="cpLegalIdValue" size="20" alt="value" name="appForm" maxlength="20"/>&nbsp;&nbsp;<bean:message key="id" />
										<%}%>
										
										
										</TD>
									</TR>-->
		<TR align="left">
			<%-- <TD align="left" valign="top" class="ColumnBackground" colspan="2"><font
				color="#FF0000" size="2">*</font>&nbsp;<bean:message
					key="promoterAdharNo" /></td>
			<TD align="left" valign="top" class="TableData" width="20%"><html:text
					property="adhar" size="20" alt="adhar" name="appForm" maxlength="12"
					onkeypress="return numbersOnly(this, event)"
					onkeyup="isValidNumber(this)" /></td> --%>
			<%-- <TD align="left" valign="top" class="ColumnBackground"><font
				color="#FF0000" size="2">*</font>&nbsp;<bean:message
					key="promoterMobNo" /></td>
			<TD align="left" valign="top" class="TableData" colspan="2"
				width="20%"><html:text property="proMobileNo" size="20"
					alt="proMobileNo" name="appForm" maxlength="12"
					onkeypress="return numbersOnly(this, event)"
					onkeyup="isValidNumber(this)" /></td> --%>
		</TR>
	
		<TR align="left">
		 <TD align="left" valign="top" class="ColumnBackground" width="15%">
				&nbsp; <bean:message key="promoterAdharNo" />
			</TD>
			<TD align="left" valign="top" class="TableData" colspan="2"><html:text
					property="adhar" size="12" name="appForm" maxlength="12" onkeypress="return numbersOnly(this, event)"
					onkeyup="isValidNumber(this)" /></TD>	 
					
			<%-- <TD align="left" valign="top" class="ColumnBackground" width="15%">
				&nbsp; <bean:message key="udyogAdharNo" />
			</TD>
			<TD align="left" valign="top" class="TableData"><html:text
					property="udyogAdharNo" size="12" name="appForm" maxlength="12" /></TD>	 --%>
			
			<TD align="left" valign="top" class="TableData">&nbsp; <%-- <b><bean:message
						key="promoterAdharNo" /></b> --%>
						</td>
			
			<TD align="left" valign="top" class="TableData" colspan="3"><%-- <html:text
					property="adhar" size="20" alt="adhar" name="appForm" maxlength="12"
					onkeypress="return numbersOnly(this, event)"
					onkeyup="isValidNumber(this)" /> --%>
					</TD>
		</TR>
	
		<TR align="left">
			<TD align="left" valign="top" class="ColumnBackground" colspan="7"><bean:message
					key="otherPromoters" /></TD>
		</TR>
		<tr>
			<td class="ColumnBackground" colspan="7">
				<table border="0" cellpadding="1" cellspacing="0" width="100%">
					<tr>
						<td class="ColumnBackground" width="17%"><b> <span
								style="font-size: 9pt">1. <bean:message key="promoterName" />
							</span></b></td>
						<td width="15%" class="TableData"><b><span
								style="font-size: 9pt"> <%
	 	if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
	 			|| (appCommonFlag.equals("2"))
	 			|| (appCommonFlag.equals("3"))
	 			|| (appCommonFlag.equals("4"))
	 			|| (appCommonFlag.equals("5"))
	 			|| (appCommonFlag.equals("6"))
	 			|| (appCommonFlag.equals("11"))
	 			|| (appCommonFlag.equals("12"))
	 			|| (appCommonFlag.equals("13"))
	 			|| (appCommonFlag.equals("14"))
	 			|| (appCommonFlag.equals("17"))
	 			|| (appCommonFlag.equals("18"))
	 			|| (appCommonFlag.equals("19"))) {
	 %> <bean:write property="firstName" name="appForm" /> <%
	 	} else {
	 %> <html:text property="firstName" size="20" alt="firstName"
										name="appForm" maxlength="50" /> <%
	 	}
	 %>
							</span></b></td>
						<td width="17%" class="ColumnBackground"><b><span
								style="font-size: 9pt"> <bean:message key="promoterItpan" /></span></b>
						</td>
						<td width="17%" class="TableData"><b><span
								style="font-size: 9pt"> <%
	 	if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
	 			|| (appCommonFlag.equals("2"))
	 			|| (appCommonFlag.equals("3"))
	 			|| (appCommonFlag.equals("4"))
	 			|| (appCommonFlag.equals("5"))
	 			|| (appCommonFlag.equals("6"))
	 			|| (appCommonFlag.equals("11"))
	 			|| (appCommonFlag.equals("12"))
	 			|| (appCommonFlag.equals("13"))
	 			|| (appCommonFlag.equals("14"))
	 			|| (appCommonFlag.equals("17"))
	 			|| (appCommonFlag.equals("18"))
	 			|| (appCommonFlag.equals("19"))) {
	 %> <bean:write property="firstItpan" name="appForm" /> <%
	 	} else {
	 %> <html:text property="firstItpan" size="20" alt="firstItpan"
										name="appForm" maxlength="10" /> <%
	 	}
	 %>
							</span></b></td>
						<td width="17%" class="ColumnBackground"><b><span
								style="font-size: 9pt"><bean:message key="promoterDob" /></span></b>
						</td>
						<td width="17%" class="TableData"><b><span
								style="font-size: 9pt"> <%
	 	if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
	 			|| (appCommonFlag.equals("2"))
	 			|| (appCommonFlag.equals("3"))
	 			|| (appCommonFlag.equals("4"))
	 			|| (appCommonFlag.equals("5"))
	 			|| (appCommonFlag.equals("6"))
	 			|| (appCommonFlag.equals("11"))
	 			|| (appCommonFlag.equals("12"))
	 			|| (appCommonFlag.equals("13"))
	 			|| (appCommonFlag.equals("14"))
	 			|| (appCommonFlag.equals("17"))
	 			|| (appCommonFlag.equals("18"))
	 			|| (appCommonFlag.equals("19"))) {
	 %> <bean:write property="firstDOB" name="appForm" /> <%
	 	} else {
	 %> <html:text property="firstDOB" size="10" alt="firstDOB"
										name="appForm" maxlength="10" styleClass="dkr_datepicker"/><!--  <IMG
									src="images/CalendarIcon.gif" width="20"
									onClick="showCalendar('appForm.firstDOB')" align="center"> -->
									<%
										}
									%>
							</span></b></td>
					</tr>
					<tr>
						<td class="ColumnBackground" width="17%"><b> <span
								style="font-size: 9pt">2. <bean:message key="promoterName" />
							</span></b></td>
						<td width="15%" class="TableData"><b><span
								style="font-size: 9pt"> <%
	 	if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
	 			|| (appCommonFlag.equals("2"))
	 			|| (appCommonFlag.equals("3"))
	 			|| (appCommonFlag.equals("4"))
	 			|| (appCommonFlag.equals("5"))
	 			|| (appCommonFlag.equals("6"))
	 			|| (appCommonFlag.equals("11"))
	 			|| (appCommonFlag.equals("12"))
	 			|| (appCommonFlag.equals("13"))
	 			|| (appCommonFlag.equals("14"))
	 			|| (appCommonFlag.equals("17"))
	 			|| (appCommonFlag.equals("18"))
	 			|| (appCommonFlag.equals("19"))) {
	 %> <bean:write property="secondName" name="appForm" /> <%
	 	} else {
	 %> <html:text property="secondName" size="20" alt="secondName"
										name="appForm" maxlength="50" /> <%
	 	}
	 %>
							</span></b></td>
						<td width="17%" class="ColumnBackground"><b><span
								style="font-size: 9pt"> <bean:message key="promoterItpan" /></span></b>
						</td>
						<td width="17%" class="TableData"><b><span
								style="font-size: 9pt"> <%
	 	if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
	 			|| (appCommonFlag.equals("2"))
	 			|| (appCommonFlag.equals("3"))
	 			|| (appCommonFlag.equals("4"))
	 			|| (appCommonFlag.equals("5"))
	 			|| (appCommonFlag.equals("6"))
	 			|| (appCommonFlag.equals("11"))
	 			|| (appCommonFlag.equals("12"))
	 			|| (appCommonFlag.equals("13"))
	 			|| (appCommonFlag.equals("14"))
	 			|| (appCommonFlag.equals("17"))
	 			|| (appCommonFlag.equals("18"))
	 			|| (appCommonFlag.equals("19"))) {
	 %> <bean:write property="secondItpan" name="appForm" /> <%
	 	} else {
	 %> <html:text property="secondItpan" size="20" alt="secondItpan"
										name="appForm" maxlength="10" /> <%
	 	}
	 %>
							</span></b></td>
						<td width="17%" class="ColumnBackground"><b><span
								style="font-size: 9pt"><bean:message key="promoterDob" /></span></b>
						</td>
						<td width="17%" class="TableData"><b><span
								style="font-size: 9pt"> <%
	 	if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
	 			|| (appCommonFlag.equals("2"))
	 			|| (appCommonFlag.equals("3"))
	 			|| (appCommonFlag.equals("4"))
	 			|| (appCommonFlag.equals("5"))
	 			|| (appCommonFlag.equals("6"))
	 			|| (appCommonFlag.equals("11"))
	 			|| (appCommonFlag.equals("12"))
	 			|| (appCommonFlag.equals("13"))
	 			|| (appCommonFlag.equals("14"))
	 			|| (appCommonFlag.equals("17"))
	 			|| (appCommonFlag.equals("18"))
	 			|| (appCommonFlag.equals("19"))) {
	 %> <bean:write property="secondDOB" name="appForm" /> <%
	 	} else {
	 %> <html:text property="secondDOB" size="10" alt="secondDOB" styleClass="dkr_datepicker"
										name="appForm" maxlength="10" /> <!-- <IMG
									src="images/CalendarIcon.gif" width="20"
									onClick="showCalendar('appForm.secondDOB')" align="center"> -->
									<%
										}
									%>
							</span></b></td>
					</tr>
	
					<tr>
						<td class="ColumnBackground" width="17%"><b> <span
								style="font-size: 9pt">3. <bean:message key="promoterName" />
							</span></b></td>
						<td width="15%" class="TableData"><b><span
								style="font-size: 9pt"> <%
	 	if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
	 			|| (appCommonFlag.equals("2"))
	 			|| (appCommonFlag.equals("3"))
	 			|| (appCommonFlag.equals("4"))
	 			|| (appCommonFlag.equals("5"))
	 			|| (appCommonFlag.equals("6"))
	 			|| (appCommonFlag.equals("11"))
	 			|| (appCommonFlag.equals("12"))
	 			|| (appCommonFlag.equals("13"))
	 			|| (appCommonFlag.equals("14"))
	 			|| (appCommonFlag.equals("17"))
	 			|| (appCommonFlag.equals("18"))
	 			|| (appCommonFlag.equals("19"))) {
	 %> <bean:write property="thirdName" name="appForm" /> <%
	 	} else {
	 %> <html:text property="thirdName" size="20" alt="thirdName"
										name="appForm" maxlength="50" /> <%
	 	}
	 %>
							</span></b></td>
						<td width="17%" class="ColumnBackground"><b><span
								style="font-size: 9pt"> <bean:message key="promoterItpan" /></span></b>
						</td>
						<td width="17%" class="TableData"><b><span
								style="font-size: 9pt"> <%
	 	if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
	 			|| (appCommonFlag.equals("2"))
	 			|| (appCommonFlag.equals("3"))
	 			|| (appCommonFlag.equals("4"))
	 			|| (appCommonFlag.equals("5"))
	 			|| (appCommonFlag.equals("6"))
	 			|| (appCommonFlag.equals("11"))
	 			|| (appCommonFlag.equals("12"))
	 			|| (appCommonFlag.equals("13"))
	 			|| (appCommonFlag.equals("14"))
	 			|| (appCommonFlag.equals("17"))
	 			|| (appCommonFlag.equals("18"))
	 			|| (appCommonFlag.equals("19"))) {
	 %> <bean:write property="thirdItpan" name="appForm" /> <%
	 	} else {
	 %> <html:text property="thirdItpan" size="20" alt="thirdItpan"
										name="appForm" maxlength="10" /> <%
	 	}
	 %>
							</span></b></td>
						<td width="17%" class="ColumnBackground"><b><span
								style="font-size: 9pt"><bean:message key="promoterDob" /></span></b>
						</td>
						<td width="17%" class="TableData"><b><span
								style="font-size: 9pt"> <%
	 	if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
	 			|| (appCommonFlag.equals("2"))
	 			|| (appCommonFlag.equals("3"))
	 			|| (appCommonFlag.equals("4"))
	 			|| (appCommonFlag.equals("5"))
	 			|| (appCommonFlag.equals("6"))
	 			|| (appCommonFlag.equals("11"))
	 			|| (appCommonFlag.equals("12"))
	 			|| (appCommonFlag.equals("13"))
	 			|| (appCommonFlag.equals("14"))
	 			|| (appCommonFlag.equals("17"))
	 			|| (appCommonFlag.equals("18"))
	 			|| (appCommonFlag.equals("19"))) {
	 %> <bean:write property="thirdDOB" name="appForm" /> <%
	 	} else {
	 %> <html:text property="thirdDOB" size="10" alt="thirdDOB" styleClass="dkr_datepicker"
										name="appForm" maxlength="10" /> <!-- <IMG
									src="images/CalendarIcon.gif" width="20"
									onClick="showCalendar('appForm.thirdDOB')" align="center"> -->
									<%
										}
									%>
							</span></b></td>
					</tr>
				</table>
			</td>
		</tr>
		<tr align="left">
			<td colspan="6"><img src="../images/clear.gif" width="5"
				height="15"></td>
		</tr>
	</TABLE>
	<!--table closing for borrower details-->
	
	
	<!--start for project-->
	<table width="100%" cellspacing="1" cellpadding="0">
		<tr>
			<td colspan="8">
				<table width="100%" border="0" cellspacing="0" cellpadding="0">
					<tr>
						<TD width="31%" class="Heading"><bean:message
								key="projectHeader" /></TD>
						<TD><IMG src="images/TriangleSubhead.gif" width="19"
							height="19"></TD>
						<td>&nbsp;</td>
						<td>&nbsp;</td>
					</tr>
					<TR>
						<TD colspan="4" class="Heading"><IMG src="images/Clear.gif"
							width="5" height="5"></TD>
					</TR>
				</table>
			</td>
		</tr>
		<!-- <TR>
			<TD align="left" valign="top" class="ColumnBackground" colspan="2">Whether
				Unit assisted is a new unit:</TD>
			<TD align="left" valign="top" class="ColumnBackground" colspan="5">
				<input type="radio" name="unitAssisted" value="Y">Yes<input
				type="radio" name="unitAssisted" value="N" checked="checked">No
			</TD>
	
		</TR> -->
	
	
		<%-- <TR>
			<TD align="left" valign="top" class="ColumnBackground" colspan="2">
				Whether the credit facility is covered under NCGTC Mudra Guarantee
				Programme:</TD>
			<TD align="left" valign="top" class="ColumnBackground" colspan="5">
				<html:radio property="pmmymudra" name="appForm" value="Y"
					onclick="pmudra('Y');">Yes</html:radio>
				<!--
	                        <input type="radio" name="pmmymudra" id="mudra" value="Y">Yes
	                        -->
				<input type="radio" name="pmmymudra" value="N" checked="checked">No
			</TD>
		</TR> --%>
		<%-- <TR>
			<TD align="left" valign="top" class="ColumnBackground" colspan="2">Whether
				the Unit Assisted is an MSE as per the MSMED Act 2006 definition of
				MSE:</TD>
			<TD align="left" valign="top" class="ColumnBackground" colspan="5"><html:radio
					name="appForm" value="Y" property="mSE">Y
													</html:radio>
				<html:radio name="appForm" value="N" property="mSE">N</html:radio></TD>
	
		</TR>
		<TR>
			<TD align="left" valign="top" class="ColumnBackground"  colspan="2">Whether
				the Unit Assisted is a Micro Enterprise as per the MSMED Act 2006:</TD>
			<TD align="left" valign="top" class="ColumnBackground" colspan="5">
			<!--<input	type="radio" name="enterprise" value="Y">Yes
			<input	type="radio" name="enterprise" value="N" checked="checked">No</TD>-->
			
			<html:radio	property="enterprise" name="appForm" value="Y">Yes</html:radio> 
			<html:radio	property="enterprise" name="appForm" value="N">No</html:radio> </TD>
	
		</TR>
	 --%>
		<!-- Added by Sayali FB -->
		<% if(((!appCommonLoanType.equals("TC")) || (!appCommonLoanType.equals("CC") || !appCommonLoanType.equals("BOTH") || !appCommonLoanType.equals("BO"))) && (appCommonLoanType.equals("WC"))){ %>
		<TR align="left">
			<%
				if (session.getAttribute("expoid") != null
						&& !session.getAttribute("expoid").equals("")) {
			%>
			<TD align="left" valign="top" class="ColumnBackground" colspan="2">&nbsp;<bean:message
					key="fbschemechk" /></td>
			<TD align="left" valign="top" class="ColumnBackground" colspan="5">
				<html:radio name="appForm" value="Y1" property="exposureFbId"></html:radio>
				<bean:message key="Y1" />&nbsp;&nbsp; <html:radio name="appForm"
					value="N1" property="exposureFbId"></html:radio> <bean:message
					key="N1" />
	
			</TD>
			<%
				}
			%>
		</TR>
		<%  } %>
	
		<!-- added vinod 17-Mar-2015 bhu-->
		<TR>
			<TD align="left" valign="top" class="ColumnBackground" colspan="2"><font
				color="#FF0000" size="2">*</font> Whether exclusive first charge on Primary Securities is available</TD>
			<TD align="left" valign="top" class="TableData" colspan="8"><html:radio
					property="pSecurity" name="appForm" value="Y" styleId="pSecurityY"
					onclick="calSecurityAvail('Y');">Yes</html:radio> <html:radio
					property="pSecurity" name="appForm" value="N" styleId="pSecurityN"
					onclick="calSecurityAvail('N');">No</html:radio></TD>
		</TR>
	
		<!-- <tr align="left">
			<td class="ColumnBackground" colspan="12"><font color="#FF0000"
				size="2">*&nbsp;</font> <font color="#FF0000" size="1">The
					scheme envisages creation of Primary Security out of the loan /
					credit provided to the borrower.</font><br></td>
		</tr> -->
	
	
	
	
	
		<tr align="left">
			<%-- 	<td class="ColumnBackground" width="25%"><font color="#FF0000" size="2">*</font>&nbsp;<bean:message
					key="collateralSec" /></td>
			<td align="left" valign="middle" class="TableData" width="25%">
			 
					<%
										 	if (appCommonFlag.equals("11") || appCommonFlag.equals("12")
										 			|| appCommonFlag.equals("13")) {
										 %>
					<!--				<bean:write property="collateralSecurityTaken" name="appForm"/>-->
					<html:radio name="appForm" value="Y"
						property="collateralSecurityTaken" disabled="true">
					</html:radio>
	
					<bean:message key="y" />
	
	
					<html:radio name="appForm" value="N"
						property="collateralSecurityTaken" disabled="true"></html:radio>
	
					<bean:message key="n" />
	
					<%
												} else {
											%>
	
					<html:radio name="appForm" value="Y"
						property="collateralSecurityTaken">
					</html:radio>
	
					<bean:message key="y" />
	
	
					<html:radio name="appForm" value="N"
						property="collateralSecurityTaken"></html:radio>
	
					<bean:message key="n" />
					<%
											}
										%>
				
			</td> --%>
			<%-- <td class="ColumnBackground"  width="25%"> &nbsp; <font color="#FF0000" size="1">#</font>&nbsp;<bean:message
					key="thirdPartyTaken" />
			</td>
			<td align="left" valign="middle" class="TableData" width="25%">
				<%
											 	if (appCommonFlag.equals("11") || appCommonFlag.equals("12")
											 			|| appCommonFlag.equals("13")) {
											 %> <!--					<bean:write property="thirdPartyGuaranteeTaken" name="appForm"/>-->
				<html:radio name="appForm" value="Y"
					property="thirdPartyGuaranteeTaken" disabled="true">
					<bean:message key="y" />
				</html:radio> <html:radio name="appForm" value="N"
					property="thirdPartyGuaranteeTaken" disabled="true">
	
					<bean:message key="n" />
				</html:radio> <%
												} else {
											%> <html:radio name="appForm" value="Y"
					property="thirdPartyGuaranteeTaken">
					<bean:message key="y" />
				</html:radio> <html:radio name="appForm" value="N"
					property="thirdPartyGuaranteeTaken">
	
					<bean:message key="n" />
				</html:radio> <%
											}
										%>
	
			</td> --%>
		</tr>
		<%-- <tr align="left">
			<td class="ColumnBackground" colspan="12"><font color="#FF0000"
				size="2">*&nbsp;</font> <font color="#FF0000" size="1"><bean:message
						key="collateralSecHint" /></font><br> <font color="#FF0000" size="1">#&nbsp;</font><font
				color="#FF0000" size="1"><bean:message
						key="thirdPartyTakenHint" /></font></td>
		</tr> 
		<tr align="left">
			<td colspan="8" align="left" class="ColumnBackground"><b><font
					color="#008600" style="font-size: 14px;">Joint Finance</font></b></td>
		</tr>
		 <TR>
			<TD align="left" valign="top" class="ColumnBackground" colspan="2"><font
				color="#FF0000" size="2">*</font>&nbsp;Whether the credit is
				sanctioned under Joint Finance scheme:</TD>
			<TD align="left" valign="top" class="ColumnBackground" colspan="2">
				<html:radio name="appForm" value="Y" property="jointFinance"
					styleId="jointFinanceY"
					onclick="enableJointFinance(),joinFinHandLoomHandyCraftValidation()">Yes</html:radio>
				<html:radio name="appForm" value="N" property="jointFinance"
					styleId="jointFinanceN"
					onclick="enableJointFinance(),joinFinHandLoomHandyCraftValidation()">No</html:radio>
			</TD>
			<TD align="left" valign="top" class="ColumnBackground" colspan="2">&nbsp;Joint
				Cgpan (Existing Cgpan of this borrower)&nbsp;</TD>
			<TD align="left" valign="top" class="ColumnBackground" colspan="2">&nbsp;<html:text
					property="jointcgpan" styleId="jointcgpan" size="25"
					alt="jointcgpan" name="appForm" maxlength="15" disabled="true" /></td>
		</TR> --%>
	
	
		<!-- Handicrafts -->
		<tr align="left">		
		<td colspan="8" align="left" class="ColumnBackground"><b><font
					color="#FF0000" style="font-size: 14px;">Handicrafts</font></b>
		<html:hidden property="jointFinance" name="appForm" value="N"  styleId="jointFinanceY" />			
		</td>
		</tr>
		<TR>
			<TD align="left" valign="top" class="ColumnBackground" width="25%"><font
				color="#FF0000" size="2">*</font>&nbsp;Whether the credit is
				sanctioned under Artisan Credit Card (ACC) scheme for Handicraft
				Artisans operated by DC(Handicrafts), Govt. of India:</TD>
			<TD align="left" valign="top" class="ColumnBackground" width="16.5%">
				<%
					if ((appCommonFlag.equals("4")) || ((appCommonFlag.equals("3")))) {
				%> <html:radio name="appForm" value="Y" property="handiCraftsStatus">Yes</html:radio>
				<html:radio name="appForm" value="N" property="handiCraftsStatus">No</html:radio>
				<%
					} else {
				%><html:radio name="appForm" value="Y" property="handiCrafts"
					styleId="handiCraftsY"
					onclick="enableHandiCrafts(),joinFinHandLoomHandyCraftValidation()">Yes</html:radio>
				<html:radio property="handiCrafts" name="appForm" value="N"
					styleId="handiCraftsN"
					onclick="enableHandiCrafts(),joinFinHandLoomHandyCraftValidation()">No</html:radio>
				<%
	 	}
	 %> <!--<input type="radio" name="handiCrafts" value="Y" onclick="javascript:enableHandiCrafts()">Yes<input type="radio" name="handiCrafts" value="N" checked="checked" onclick="javascript:enableHandiCrafts()">N-->
	
			</TD>
			<TD align="left" valign="top" class="ColumnBackground" width="25%"><font
				color="#FF0000" size="2">*</font>&nbsp;Whether Guarantee Fee is re-imbursable from O/o DC (Handicrafts) Govt. of India:</TD>
			<TD align="left" valign="top" class="ColumnBackground" width="16.5%"
				colspan="4">
				<%
					if ((appCommonFlag.equals("4")) || ((appCommonFlag.equals("3")))) {
				%><html:radio name="appForm" value="Y" property="dcHandicraftsStatus">Yes</html:radio>
				<html:radio name="appForm" value="N" property="dcHandicraftsStatus">No</html:radio>
				<%
					} else {
				%><html:radio name="appForm" value="Y" property="dcHandicrafts">Yes</html:radio>
				<html:radio name="appForm" value="N" property="dcHandicrafts">No</html:radio>
				<%
	 	}
	 %> <!--<input type="radio" name="dcHandicrafts" value="Y">Yes<input type="radio" name="dcHandicrafts" value="N" checked="checked">N-->
	
			</TD>
	
		</TR>
		<%-- <TR>
			<TD align="left" valign="top" class="ColumnBackground" colspan="2"><font
				color="#FF0000" size="2">*</font>&nbsp;Whether GF/ASF is
				re-imbursable from O/o DC(Handicrafts) Govt. of India:</TD>
			<TD align="left" valign="top" class="ColumnBackground" colspan="7">
				<%
					if ((appCommonFlag.equals("4")) || ((appCommonFlag.equals("3")))) {
				%><html:radio name="appForm" value="Y"
					property="dcHandicraftsStatus">Yes</html:radio> <html:radio
					name="appForm" value="N" property="dcHandicraftsStatus">No</html:radio>
				<%
					} else {
				%><html:radio name="appForm" value="Y"
					property="dcHandicrafts">Yes</html:radio> <html:radio
					name="appForm" value="N" property="dcHandicrafts">No</html:radio> <%
	 	}
	 %> <!--<input type="radio" name="dcHandicrafts" value="Y">Yes<input type="radio" name="dcHandicrafts" value="N" checked="checked">N-->
	
			</TD>
	
		</TR> --%>
		<%-- 
		<tr align="left">
			<td colspan="4" align="left" class="ColumnBackground">
			<font
				color="#FF0000" size="1">
			 Details of Identity Card Issued by DC(Handicraft),GOI. (Compulsory when reimbursement is required ) </font></td>
		</tr> --%>
		<tr>
			<TD align="left" valign="top" class="ColumnBackground">&nbsp;I
				Card Number:</td>
			<TD align="left" valign="top" class="ColumnBackground">&nbsp;<html:text
					property="icardNo" size="25" alt="icard No" name="appForm"
					maxlength="15" /></td>
			<TD align="left" valign="top" class="ColumnBackground" colspan="2">&nbsp;I
				Card Issue date:</td>
			<TD align="left" valign="top" class="ColumnBackground" colspan="4">&nbsp;<html:text
					property="icardIssueDate" size="20" alt="icardIssue Date"
					name="appForm" maxlength="10" styleClass="dkr_datepicker"/>&nbsp;<!--  <IMG
				src="images/CalendarIcon.gif" width="20"
				onClick="showCalendar('appForm.icardIssueDate')" align="center"> --></td>
	
		</tr>
		<tr align="left">
			<td colspan="8" align="left" class="ColumnBackground"><b><font
					color="#FF0000" style="font-size: 14px;">Handlooms</font></b></td>
		</tr>
		<TR>
			<TD align="left" valign="top" class="ColumnBackground"><font
				color="#FF0000" size="2">*</font>&nbsp;Whether the credit is
				sanctioned under DC(Handloom) scheme for Handloom Weavers</TD>
			<TD align="left" valign="top" class="ColumnBackground">
				<%
					if ((appCommonFlag.equals("4")) || ((appCommonFlag.equals("3")))) {
				%> <html:radio name="appForm" value="Y" property="dcHandloomsStatus">Yes</html:radio>
				<html:radio name="appForm" value="N" property="dcHandloomsStatus"
					onclick="javascript:enabledcHandlooms()">No</html:radio> <%
	 	} else {
	 %> <html:radio name="appForm" value="Y" property="dcHandlooms"
					styleId="dcHandloomsY"
					onclick="enabledcHandlooms(),joinFinHandLoomHandyCraftValidation()">Yes</html:radio>
				<html:radio name="appForm" value="N" property="dcHandlooms"
					styleId="dcHandloomsN"
					onclick="enabledcHandlooms(),joinFinHandLoomHandyCraftValidation()">No</html:radio>
				<%
	 	}
	 %>
			</TD>
			<TD align="left" valign="top" class="ColumnBackground" colspan="2"><font
				color="#FF0000" size="2">*</font>&nbsp;If Yes, Please select the name
				of the Scheme</TD>
			<TD align="left" valign="top" class="ColumnBackground" colspan="4">
	
				<!-- <html:text property="WeaverCreditScheme" size="25"  name="appForm" /> --->
	
				<html:select property="WeaverCreditScheme" name="appForm">
					<html:option value="Select">Select</html:option>
					<html:option value="IHDS">Integreated Handloom Development Scheme(CP{IHDS}) </html:option>
					<html:option value="CHCDS">Mega Cluster Scheme( Varanasi/Murshidabad)-CHCDS(MC)</html:option>
				</html:select>
			</TD>
		</TR>
		<%-- 	<TR>
			<TD align="left" valign="top" class="ColumnBackground" colspan="2"><font
				color="#FF0000" size="2">*</font>&nbsp;If Yes, Please select the name
				of the Scheme</TD>
			<TD align="left" valign="top" class="ColumnBackground" colspan="7">
	
				<!-- <html:text property="WeaverCreditScheme" size="25"  name="appForm" /> --->
	
				<html:select property="WeaverCreditScheme" name="appForm">
					<html:option value="Select">Select</html:option>
					<html:option value="IHDS">Integreated Handloom Development Scheme(CP{IHDS}) </html:option>
					<html:option value="CHCDS">Mega Cluster Scheme( Varanasi/Murshidabad)-CHCDS(MC)</html:option>
				</html:select>
			</TD>
		</TR> --%>
	
		<TR align="left">
			<TD align="left" valign="top" class="ColumnBackground" width="60%"><font
				color="#FF0000" size="2">*</font>&nbsp;<font color="#008600" size="1"><b>
						If Yes, Please certify by ticking the checkbox.</b></font> We certify that the
				credit facility being covered under CGTMSE and for which
				reimbursement of GF/ASF in being availed adheres to all guidelines as
				issued by Office of DC(Handloom) from time to time for sanction of
				Credit under the respective schemes</TD>
			<TD align="left" valign="top" class="ColumnBackground" width="50%"
				colspan="8"><html:checkbox property="handloomchk" name="appForm"
					value="Y" /></TD>
		</TR>
		<tr align="left">
			<td class="ColumnBackground" width="16%"><font color="#FF0000"
				size="2">*&nbsp;</font> Internal Rating / Score as per MLI <br></td>
			<td align="left" valign="top" class="ColumnBackground" width="16%">
				<html:text property="internalRating" size="30" alt="internal Rating"
					name="appForm" maxlength="15" />
			</td>
	
			<TD align="left" valign="top" class="ColumnBackground" width="16%"><font
				color="#FF0000" size="2">*</font>&nbsp;I/We confirm that the same is
				an investment grade rating of the bank as per Bank's Policy.</td>
			<TD align="left" valign="top" class="ColumnBackground" width="16%">
				<html:checkbox property="ivConfirmInvestGrad" value="Y" />
			</TD>
	
			<TD align="left" valign="top" class="ColumnBackground" width="16%"><!-- <font
				color="#FF0000" size="2">*</font>&nbsp;Internal Rating/Equivalent
				Standared Rating --></td>
			<TD align="left" valign="top" class="ColumnBackground" width="16%"
				colspan="2">
				<%
					if (appCommonFlag.equals("11") || appCommonFlag.equals("12")
							|| appCommonFlag.equals("13")) {
				%> <!-- Internal Rating done and is of Investment grade? --> <%
					} else {
				%> <%-- <html:select property="equivStandaredRating"
					styleId="equivStandaredRating">
					<html:option value="">--Select--</html:option>
					<html:option value="1">1</html:option>
					<html:option value="2">2</html:option>
					<html:option value="3">3</html:option>
					<html:option value="4">4</html:option>
					<html:option value="5">5</html:option>
					<html:option value="6">6</html:option>
					<html:option value="7">7</html:option>
					<html:option value="8">8</html:option>
					<html:option value="9">9</html:option>
					<html:option value="10">10</html:option> 
				</html:select>--%> <!--  <html:radio property="internalRating" name="appForm" value="Y">Yes</html:radio> -->
				<%-- <html:radio property="internalRating" name="appForm" value="N">No</html:radio>  --%>
				<%
					}
				%>
			</TD>
		</tr>
		<%-- <TR align="left">
			<TD align="left" valign="top" class="ColumnBackground" colspan="2"><font
				color="#FF0000" size="2">*</font>&nbsp;Internal Rating</td>
			<TD align="left" valign="top" class="TableData">
				<%
					if (appCommonFlag.equals("11") || appCommonFlag.equals("12")
							|| appCommonFlag.equals("13")) {
				%> Internal Rating done and is of Investment grade? <%
					} else {
				%> 
										 
										 <html:text property="internalRating" size="30" alt="internal Rating" name="appForm" maxlength="15"/>
										 
				<!--  <html:radio property="internalRating" name="appForm" value="Y">Yes</html:radio> -->
				<html:radio property="internalRating" name="appForm" value="N">No</html:radio> 
				<%
					}
				%>
			</TD>
			<TD align="left" valign="top" class="ColumnBackground"></td>
	
		</TR> --%>
	
	
	
	
		<tr align="left">
			<td colspan="8" class="SubHeading" height="28" width="843%"><br>
				&nbsp; <bean:message key="meansOfFinance" /></td>
		</tr>
		<tr valign="top">
			<td colspan="8">
				<%-- <% if(((appCommonLoanType.equals("TC")) || (appCommonLoanType.equals("CC") || appCommonLoanType.equals("BOTH") || appCommonLoanType.equals("BO"))) && (!appCommonLoanType.equals("WC"))){ %>
			 --%>
			 	<table width="100%" border="0" align="right" cellpadding="0"
					cellspacing="1">
					<tr>
						<td class="ColumnBackground" width="16.5%" align="left"
							valign="top" colspan="0">
							<%
								if (appCommonFlag.equals("7") || appCommonFlag.equals("8")
										|| appCommonFlag.equals("10") || appCommonFlag.equals("0")
										|| appCommonFlag.equals("3") || appCommonFlag.equals("5")
										|| appCommonFlag.equals("11") || appCommonFlag.equals("13")
										|| appCommonFlag.equals("14") || appCommonFlag.equals("19")) {
							%> <font color="#FF0000" size="2">*</font> <bean:message
								key="tcSanctioned" /> <%
	 	} else {
	 %> <bean:message key="tcSanctioned" /> <%
	 	}
	 %>
						</td>
						<td class="ColumnBackground" width="16.5%" align="left"
							valign="top" colspan="0" id="tcSanctioned">
							<%
								if (appCommonFlag.equals("11") || appCommonFlag.equals("12")
										|| appCommonFlag.equals("13") || appCommonFlag.equals("2")) {
							%> <bean:write property="termCreditSanctioned" name="appForm" />
							<%
	 	} else {
	 %> <html:text property="termCreditSanctioned" size="20"
								alt="tcSanctioned" name="appForm" maxlength="16"
								onchange="javascript:countProjectOutlay();"
								onblur="javascript:calProjectOutlay(),calltotalAmt()"
								onkeypress="return decimalOnly(this, event,13)"
								onkeyup="isValidDecimal(this)" styleId="termCreditSanctioned" />
							<%
	 	}
	 %> <bean:message key="inRs" />
						</td>
						<td class="ColumnBackground" width="16.5%" align="left"
							valign="top" colspan="0"><bean:message
								key="tcPromoterContribution" /></td>
						<td class="ColumnBackground" width="20%" align="left" valign="top"
							id="tcCont">
							<%
								if (appCommonFlag.equals("11") || appCommonFlag.equals("12")
										|| appCommonFlag.equals("13")) {
							%> <bean:write property="tcPromoterContribution" name="appForm" />
							<%
	 	} else {
	 %> <html:text property="tcPromoterContribution" size="20"
								styleId="tcPromoterContribution" alt="promotersCont"
								name="appForm" maxlength="16"
								onchange="javascript:countProjectOutlay();"
								onblur="javascript:calProjectOutlay()"
								onkeypress="return decimalOnly(this, event,13)"
								onkeyup="isValidDecimal(this)" /> <%
	 	}
	 %> <bean:message key="inRs" />
						</td>
	
						<td class="ColumnBackground" width="16.5%" colspan="0">&nbsp;
							<bean:message key="tcSubsidyOrEquity" />
						</td>
						<td class="ColumnBackground" width="16.5%" align="left"
							valign="top" id="tcSubsidy">
							<%
								if (appCommonFlag.equals("11") || appCommonFlag.equals("12")
										|| appCommonFlag.equals("13")) {
							%> <bean:write property="tcSubsidyOrEquity" name="appForm" /> <%
								} else {
							%> <html:text property="tcSubsidyOrEquity"  styleId="tcSubsidyOrEquity" size="20"
								alt="tcSubsidyOrEquity" name="appForm" maxlength="16"
								onblur="countProjectOutlay(),calProjectOutlay()"
								onkeypress="return decimalOnly(this, event,13)"
								onkeyup="isValidDecimal(this)" /> <%
	 	}
	 %> <bean:message key="inRs" />
						</td>
	
	
						<%-- <% } %> --%>
						<% if(((appCommonLoanType.equals("WC")) || (appCommonLoanType.equals("CC") || appCommonLoanType.equals("BOTH")  || appCommonLoanType.equals("BO"))) && (!appCommonLoanType.equals("TC"))){ %>
	
						<td class="ColumnBackground" colspan="2" align="left" valign="top">
	
							<table width="100%" border="0" align="right" cellpadding="0"
								cellspacing="1">
								<tr align="left">
									<td class="ColumnBackground" colspan="2" align="center">
										<%
											if (appCommonFlag.equals("9") || appCommonFlag.equals("8")
													|| appCommonFlag.equals("10") || appCommonFlag.equals("2")
													|| appCommonFlag.equals("4") || appCommonFlag.equals("6")
													|| appCommonFlag.equals("12") || appCommonFlag.equals("13")
													|| appCommonFlag.equals("18") || appCommonFlag.equals("19")) {
										%> <font color="#FF0000" size="2">*</font> <bean:message
											key="wcLimitSanctioned" /> <%
	 	} else if (appCommonFlag.equals("1")) {
	 %> <font color="#FF0000" size="2">*</font> <bean:message
											key="wcEnhanceLimitSanctioned" /> <%
	 	} else {
	 %> <bean:message key="wcLimitSanctioned" /> <%
	 	}
	 %>
									</td>
								</tr>
								<tr align="left">
									<td class="ColumnBackground" align="left" valign="top"><bean:message
											key="wcFundBased" /></td>
									<td class="TableData" align="left" valign="top"
										id="wcFBsanctioned">
										<%
											if (appCommonFlag.equals("11") || appCommonFlag.equals("12")
													|| appCommonFlag.equals("13") || appCommonFlag.equals("2")) {
										%> <bean:write property="wcFundBasedSanctioned" name="appForm" />
										<%
	 	} else {
	 %> <html:text property="wcFundBasedSanctioned"
											styleId="wcFundBasedSanctioned" size="20" alt="wcFundBased"
											name="appForm" maxlength="16" onchange="calltotalAmt()"
											onblur="javascript:calProjectOutlay()"
											onkeypress="return decimalOnly(this, event,13)"
											onkeyup="isValidDecimal(this)" /> <%
	 	}
	 %> <bean:message key="inRs" />
									</td>
								</tr>
	
								<tr align="left">
									<td class="ColumnBackground" align="left" valign="top"><bean:message
											key="wcNonFundBased" /></td>
									<td class="TableData" align="left" valign="top"
										id="wcNFBsanctioned">
										<%
											if (appCommonFlag.equals("11") || appCommonFlag.equals("12")
													|| appCommonFlag.equals("13") || appCommonFlag.equals("2")) {
										%> <bean:write property="wcNonFundBasedSanctioned"
											name="appForm" /> <%
	 	} else {
	 %> <html:text property="wcNonFundBasedSanctioned"
											styleId="wcNonFundBasedSanctioned" size="20" alt="wcFundBased"
											name="appForm" maxlength="16" onchange="calltotalAmt()"
											onblur="javascript:calProjectOutlay()"
											onkeypress="return decimalOnly(this, event,13)"
											onkeyup="isValidDecimal(this)" /> <%
	 	}
	 %> <bean:message key="inRs" />
									</td>
								</tr>
								<tr align="left">
									<td class="ColumnBackground" align="left" valign="top"
										width="50%">
										<%-- <bean:message key="marginMoneyAsTL" /> --%>
									</td>
									<td class="TableData" align="left" valign="top">
										<%-- <%
											if (appCommonFlag.equals("11") || appCommonFlag.equals("12")
													|| appCommonFlag.equals("13")) {
										%> <!--						<bean:write property="marginMoneyAsTL" name="appForm"/>-->
										<html:radio name="appForm" value="Y" property="marginMoneyAsTL"
											disabled="true"></html:radio> <bean:message key="yes" />&nbsp;&nbsp;
	
										<html:radio name="appForm" value="N" property="marginMoneyAsTL"
											disabled="true"></html:radio> <bean:message key="no" /> <%
	 	} else {
	 %> <html:radio name="appForm" value="Y"
											property="marginMoneyAsTL"
											onclick="javascript:calProjectOutlay()"></html:radio> <bean:message
											key="yes" />&nbsp;&nbsp; <html:radio name="appForm" value="N"
											property="marginMoneyAsTL"
											onclick="javascript:calProjectOutlay()"></html:radio> <bean:message
											key="no" /> <%
	 	}
	 %> --%>
	
									</td>
								</tr>
	
							</table>
	
						</td>
	
	
						<td class="ColumnBackground" width="25%">&nbsp; <bean:message
								key="wcPromoterContribution" />
						</td>
						<td class="TableData" width="20%" align="left" valign="top"
							id="wcCont">
							<%
								if (appCommonFlag.equals("11") || appCommonFlag.equals("12")
										|| appCommonFlag.equals("13")) {
							%> <bean:write property="wcPromoterContribution" name="appForm" />
							<%
	 	} else {
	 %> <html:text property="wcPromoterContribution" size="20"
								alt="wcPromoterContribution" name="appForm" maxlength="16"
								onblur="javascript:calProjectOutlay()"
								onkeypress="return decimalOnly(this, event,13)"
								onkeyup="isValidDecimal(this)" /> <%
	 	}
	 %> <bean:message key="inRs" />
						</td>
	
						<td class="ColumnBackground" width="25%">&nbsp; <bean:message
								key="wcSubsidyOrEquity" />
						</td>
						<td class="TableData" width="20%" align="left" valign="top"
							id="wcSubsidy">
							<%
								if (appCommonFlag.equals("11") || appCommonFlag.equals("12")
										|| appCommonFlag.equals("13")) {
							%> <bean:write property="wcSubsidyOrEquity" name="appForm" /> <%
								} else {
							%> <html:text property="wcSubsidyOrEquity" size="20"
								alt="wcSubsidyOrEquity" name="appForm" maxlength="16"
								onblur="javascript:calProjectOutlay()"
								onkeypress="return decimalOnly(this, event,13)"
								onkeyup="isValidDecimal(this)" /> <%
	 	}
	 %> <bean:message key="inRs" />
						</td>
						<% } %>
					</tr>
					<tr align="left">
	
						<%-- <% if(!appCommonLoanType.equals("TC") && (appCommonLoanType.equals("WC") || appCommonLoanType.equals("CC") || appCommonLoanType.equals("BOTH"))){ %>
						<td class="ColumnBackground" width="25%">&nbsp; <bean:message
								key="wcPromoterContribution" />
						</td>
						<td class="TableData" width="20%" align="left" valign="top"
							id="wcCont">
							<%
								if (appCommonFlag.equals("11") || appCommonFlag.equals("12")
										|| appCommonFlag.equals("13")) {
							%> <bean:write property="wcPromoterContribution"
								name="appForm" /> <%
	 	} else {
	 %> <html:text property="wcPromoterContribution" size="20"
								alt="wcPromoterContribution" name="appForm" maxlength="16"
								onblur="javascript:calProjectOutlay()"
								onkeypress="return decimalOnly(this, event,13)"
								onkeyup="isValidDecimal(this)" /> <%
	 	}
	 %> <bean:message key="inRs" />
						</td>
						<% } %> --%>
					</tr>
					<tr align="left">
						<%-- <td class="ColumnBackground" width="25%">&nbsp; <bean:message
								key="tcSubsidyOrEquity" />
						</td>
						<td class="TableData" width="20%" align="left" valign="top"
							id="tcSubsidy">
							<%
								if (appCommonFlag.equals("11") || appCommonFlag.equals("12")
										|| appCommonFlag.equals("13")) {
							%> <bean:write property="tcSubsidyOrEquity" name="appForm" />
							<%
								} else {
							%> <html:text property="tcSubsidyOrEquity" size="20"
								alt="tcSubsidyOrEquity" name="appForm" maxlength="16"
								onblur="javascript:calProjectOutlay()"
								onkeypress="return decimalOnly(this, event,13)"
								onkeyup="isValidDecimal(this)" /> <%
	 	}
	 %> <bean:message key="inRs" />
						</td> --%>
						<%-- 	
						<td class="ColumnBackground" width="25%">&nbsp; <bean:message
								key="wcOthers" />
						</td>
						<td class="TableData" width="20%" align="left" valign="top"
							id="wcOther">
							<%
								if (appCommonFlag.equals("11") || appCommonFlag.equals("12")
										|| appCommonFlag.equals("13")) {
							%> <bean:write property="wcOthers" name="appForm" /> <%
	 	} else {
	 %> <html:text property="wcOthers" size="20" alt="wcOthers"
								name="appForm" maxlength="16"
								onblur="javascript:calProjectOutlay()"
								onkeypress="return decimalOnly(this, event,13)"
								onkeyup="isValidDecimal(this)" /> <%
	 	}
	 %> <bean:message key="inRs" />
						</td>
						<td class="ColumnBackground" width="25%">&nbsp; <bean:message
								key="wcAssessed" />
						</td>
						<td class="TableData" width="20%" align="left" valign="top"
							id="wcAssessed">
							<%
								if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
										|| (appCommonFlag.equals("2"))
										|| (appCommonFlag.equals("3"))
										|| (appCommonFlag.equals("4"))
										|| (appCommonFlag.equals("5")) || appCommonFlag.equals("6")
										|| appCommonFlag.equals("11") || appCommonFlag.equals("12")
										|| appCommonFlag.equals("13"))
	
								{
							%> <bean:write name="appForm" property="wcAssessed" /> <%
	 	} else {
	 %> <bean:message key="inRs" /> <%
	 	}
	 %> --%>
						</td>
						<%-- <% if(!appCommonLoanType.equals("TC") && (appCommonLoanType.equals("WC") || appCommonLoanType.equals("CC") || appCommonLoanType.equals("BOTH"))){ %>
						<td class="ColumnBackground" width="25%">&nbsp; <bean:message
								key="wcSubsidyOrEquity" />
						</td>
						<td class="TableData" width="20%" align="left" valign="top"
							id="wcSubsidy">
							<%
								if (appCommonFlag.equals("11") || appCommonFlag.equals("12")
										|| appCommonFlag.equals("13")) {
							%> <bean:write property="wcSubsidyOrEquity" name="appForm" />
							<%
								} else {
							%> <html:text property="wcSubsidyOrEquity" size="20"
								alt="wcSubsidyOrEquity" name="appForm" maxlength="16"
								onblur="javascript:calProjectOutlay()"
								onkeypress="return decimalOnly(this, event,13)"
								onkeyup="isValidDecimal(this)" /> <%
	 	}
	 %> <bean:message key="inRs" />
						</td>
						<%} %> --%>
					</tr>
					<tr align="left">
						<%-- <td class="ColumnBackground" width="25%">&nbsp; <bean:message
								key="tcOthers" />
						</td>
						<td class="TableData" width="20%" align="left" valign="top"
							id="tcOther">
							<%
								if (appCommonFlag.equals("11") || appCommonFlag.equals("12")
										|| appCommonFlag.equals("13")) {
							%> <bean:write property="tcOthers" name="appForm" /> <%
	 	} else {
	 %> <html:text property="tcOthers" size="20" alt="tcOthers"
								name="appForm" maxlength="16"
								onblur="javascript:calProjectOutlay()"
								onkeypress="return decimalOnly(this, event,13)"
								onkeyup="isValidDecimal(this)" /> <%
	 	}
	 %> <bean:message key="inRs" />
						</td> --%>
						<%-- <% if(!appCommonLoanType.equals("TC") && (appCommonLoanType.equals("WC") || appCommonLoanType.equals("CC") || appCommonLoanType.equals("BOTH"))){ %>
						<td class="ColumnBackground" width="25%">&nbsp; <bean:message
								key="wcOthers" />
						</td>
						<td class="TableData" width="20%" align="left" valign="top"
							id="wcOther">
							<%
								if (appCommonFlag.equals("11") || appCommonFlag.equals("12")
										|| appCommonFlag.equals("13")) {
							%> <bean:write property="wcOthers" name="appForm" /> <%
	 	} else {
	 %> <html:text property="wcOthers" size="20" alt="wcOthers"
								name="appForm" maxlength="16"
								onblur="javascript:calProjectOutlay()"
								onkeypress="return decimalOnly(this, event,13)"
								onkeyup="isValidDecimal(this)" /> <%
	 	}
	 %> <bean:message key="inRs" />
						</td>
						<% } %> --%>
					</tr>
					<TR align="left">
						<td class="ColumnBackground" width="16.5%" align="left"
							valign="top" colspan="0">&nbsp; <bean:message
								key="projectCost" />
						</td>
						<td class="ColumnBackground" width="16.5%" align="left"
							valign="top" id="projectCost" colspan="0">
							<%
								if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
										|| (appCommonFlag.equals("2"))
										|| (appCommonFlag.equals("3"))
										|| (appCommonFlag.equals("4"))
										|| (appCommonFlag.equals("5")) || appCommonFlag.equals("6")
										|| appCommonFlag.equals("11") || appCommonFlag.equals("12")
										|| appCommonFlag.equals("13"))
	
								{
							%> <bean:write name="appForm" property="projectCost" /> <%
				 	} else {
				 %> <bean:write name="appForm" property="projectCost" /> <%
				 	}
				 %>
						</td>
						<% if(appCommonLoanType.equals("TC")){ %>
						<td class="ColumnBackground" width="16.5%" colspan="0">&nbsp;
							<bean:message key="tcOthers" />
						</td>
						<td class="TableData" width="16.5%" align="left" valign="top"
							colspan="0" id="tcOther">
							<%
								if (appCommonFlag.equals("11") || appCommonFlag.equals("12")
										|| appCommonFlag.equals("13")) {
							%> <bean:write property="tcOthers" name="appForm" /> <%
					 	} else {
					 %> <html:text property="tcOthers" styleId="tcOthers" name="appForm" size="20" alt="tcOthers" 
					 maxlength="16" onblur="countProjectOutlay(),calProjectOutlay();" 
					 onkeypress="return decimalOnly(this, event,13)"	onkeyup="isValidDecimal(this)" /> <%
					 	}
					 %> <bean:message key="inRs" />
						</td>
	                  <% } %>
	
						<td width="16.5%" class="ColumnBackground">
							<div align="left">
								&nbsp; <font color="#FF0000" size="2">*</font>&nbsp;
								<bean:message key="interestRate" />
							</div>
						</td>
						<td width="16.5%" class="TableData">
							<div align="left">
								<%				
											if((appCommonFlag.equals("11"))||(appCommonFlag.equals("13")))
											
	
											{
											%>
								<bean:write name="appForm" property="interestRate" />
								<%}
											else
											{ %>
	
								<html:text property="interestRate" size="5" alt="interestRate"
									name="appForm" maxlength="5"
									onkeypress="return decimalOnly(this, event,2)"
									onkeyup="isValidDecimal(this)" />
								&nbsp;
								<%}%>
								<bean:message key="inPa" />
	
							</div>
						</td>
	
	                 <td class="ColumnBackground" width="25%">&nbsp; <bean:message
								key="tcOthers" />
						</td>
						<td class="TableData" width="20%" align="left" valign="top"
							id="tcOther">
							<%
								if (appCommonFlag.equals("11") || appCommonFlag.equals("12")
										|| appCommonFlag.equals("13")) {
							%> <bean:write property="tcOthers" name="appForm" /> <%
						 	} else {
						 %> <html:text property="tcOthers" size="20" alt="tcOthers"
													name="appForm" maxlength="16"
													onblur="javascript:calProjectOutlay()"
													onkeypress="return decimalOnly(this, event,13)"
													onkeyup="isValidDecimal(this)" /> <%
						 	}
						 %> <bean:message key="inRs" />
											</td> 
	
						<%-- 	<% if(!appCommonLoanType.equals("TC") && (appCommonLoanType.equals("WC") || appCommonLoanType.equals("CC") || appCommonLoanType.equals("BOTH"))){ %>
						<td class="ColumnBackground" width="25%">&nbsp; <bean:message
								key="wcAssessed" />
						</td>
						<td class="TableData" width="20%" align="left" valign="top"
							id="wcAssessed">
							<%
								if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
										|| (appCommonFlag.equals("2"))
										|| (appCommonFlag.equals("3"))
										|| (appCommonFlag.equals("4"))
										|| (appCommonFlag.equals("5")) || appCommonFlag.equals("6")
										|| appCommonFlag.equals("11") || appCommonFlag.equals("12")
										|| appCommonFlag.equals("13"))
	
								{
							%> <bean:write name="appForm" property="wcAssessed" /> <%
	 	} else {
	 %> <bean:message key="inRs" /> <%
	 	}
	 %>
						</td>
						<% } %> --%>
					</tr>
					<tr align="left">
						<td class="ColumnBackground" width="16.5%" colspan="0"><bean:message
								key="projectOutlay" /></td>
						<td class="ColumnBackground" width="16.5%" colspan="0"
							id="projectOutlay">
							<%
								if ((appCommonFlag.equals("0")) || (appCommonFlag.equals("1"))
										|| (appCommonFlag.equals("2"))
										|| (appCommonFlag.equals("3"))
										|| (appCommonFlag.equals("4"))
										|| (appCommonFlag.equals("5"))
										|| (appCommonFlag.equals("6"))
										|| appCommonFlag.equals("11") || appCommonFlag.equals("12")
										|| appCommonFlag.equals("13"))
	
								{
							%> <bean:write name="appForm" property="projectOutlay" /> <%
	 	                 }
	                            %> <html:text property="projectOutlay"
								name="appForm" styleId="projectOutlayId" readonly="true"
								style="background-color:#C4DEE6" />
						</td>
	
	
						<td width="16.5%" class="ColumnBackground" height="28" colspan="1">&nbsp;
						</td>
						<td width="16.5%" class="TableData" height="28" colspan="1"></td>
	
						<td class="ColumnBackground" height="28" width="16.5%" colspan="0">&nbsp;
							<%-- <bean:message key="amtDisbursed" />	 --%>
						</td>
						<td class="TableData" height="28" width="16.5%">
							<%-- <%
										
											if(appCommonFlag.equals("11")||(appCommonFlag.equals("13")) || appCommonFlag.equals("3") || appCommonFlag.equals("6"))
											{
											
											%>
											<bean:write name="appForm" property="amtDisbursed"/>
											<%}
											else
											{ %>
											<html:text property="amtDisbursed" size="20" alt="amtDisbursed" name="appForm" maxlength="16" onkeypress="return decimalOnly(this, event,13)" onkeyup="isValidDecimal(this)"/>
											<%}%>
											<bean:message key="inRs" />	 --%>
						</td>
					</tr>
					<html:hidden property="status" name="appForm" />
	
	
					<html:hidden property="interestType" name="appForm" value="F" />
					<!-- <tr> -->
					<%-- <tr align="left">
						<td class="ColumnBackground" height="28" width="16.5%" colspan="0">&nbsp;
											<bean:message key="interestType" />
										</td>
										<td class="TableData" width="16.5%">
										<%				
											if((appCommonFlag.equals("11"))||(appCommonFlag.equals("13")))
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
										</td>
										
										<td width="16.5%" class="ColumnBackground" height="28" colspan="0"> <div align="left">&nbsp;
											<!-- <font color="#FF0000" size="2">*</font>&nbsp; Base Rate</div> -->
										</td>
										
										<td class="TableData"width="16.5%" >
											<div align="left"> 
											<%				
											if((appCommonFlag.equals("11"))||(appCommonFlag.equals("13")))
											
	
											{
											%>
											<bean:write name="appForm" property="plr"/>
											<%}
											else
											{ %> maxlength="6"  onkeypress="return decimalOnly(this, event,3)" onkeyup="isValidDecimal(this)"
												<html:hidden property="plr" name="appForm" />&nbsp;
											<%}%>
												 <bean:message key="inPa" />
											
											</div>
										</td>
										
										<td width="16.5%" class="ColumnBackground"> <div align="left">&nbsp;
											<font color="#FF0000" size="2">*</font>&nbsp;<bean:message key="interestRate" /></div>
										</td>
										<td width="16.5%" class="TableData" >
											<div align="left"> 
											<%				
											if((appCommonFlag.equals("11"))||(appCommonFlag.equals("13")))
											
	
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
										</td> 
					</tr> --%>
	
					<%-- <td class="ColumnBackground" width="25%" align="left" valign="top">
							<%
								if (appCommonFlag.equals("7") || appCommonFlag.equals("8")
										|| appCommonFlag.equals("10") || appCommonFlag.equals("0")
										|| appCommonFlag.equals("3") || appCommonFlag.equals("5")
										|| appCommonFlag.equals("11") || appCommonFlag.equals("13")
										|| appCommonFlag.equals("14") || appCommonFlag.equals("19")) {
							%> <font color="#FF0000" size="2">*</font> <bean:message
								key="expiryDate" /> <%
	 	} else {
	 %> <bean:message key="expiryDate" /> <%
	 	}
	 %>
						</td>
						<td class="TableData" width="20%" align="left" valign="top"
							id="expiryDate">
							<%
								if ((appCommonFlag.equals("11")) || (appCommonFlag.equals("13"))) {
							%> <%
	 	} else {
	 %> <html:text property="expiryDate" size="20"
								alt="expiryDate" name="appForm" maxlength="10" /> <IMG
							src="images/CalendarIcon.gif" width="20"
							onClick="showCalendar('appForm.expiryDate')" align="center">
	
							<%
								}
							%>
						</td> --%>
					<!-- <td class="ColumnBackground" width="25%" align="left" valign="top"
							colspan="4">
					</tr> -->
				</table>
			</TD>
		</TR>
		<!-- added by dkr YES 35 -->
		<TR>
			<TD colspan="8">
				<TABLE width="100%" id="movCollateratlSecurityLblId"
					<%String hybridflg = "N";
				if (null != request.getParameter("hybridSecurity")) {
					hybridflg = request.getParameter("hybridSecurity");
				}
				if (hybridflg.equals("N")) {%>
					style="display: none;" <%} else {%> style="display:block;" <%}%>>
					<tr>
						<td width="25%" align="left" class="ColumnBackground" valign="top">
							<font color="#FF0000" size="1"><bean:message
									key="movCollateratlSecurityLbl" /></font>
						</td>
						<td width="25%" align="left" class="ColumnBackground" valign="top"
							colspan="6"></td>
					</tr>
					<tr>
						<td width="25%" align="left" class="ColumnBackground" valign="top">
							<font color="#FF0000" size="2">*</font> <bean:message
								key="immovCollateratlSecurity" />
						</td>
						<td width="20%" align="left" class="TableData"
							id="immovCollateratlSecurity" colspan="4">
							<%
								if (appCommonFlag.equals("11") || appCommonFlag.equals("12")
										|| appCommonFlag.equals("13") || appCommonFlag.equals("2")) {
							%> <bean:write property="immovCollateratlSecurityAmt"
								name="appForm" /> <%
	 	} else {
	 %> <html:text property="immovCollateratlSecurityAmt"
								styleId="immovCollateratlSecurityAmt" size="20"
								alt="immovCollateratlSecurityAmt" name="appForm" maxlength="16"
								onblur="calTotalMIcollatSecAmt();"
								onkeypress="return decimalOnly(this, event,13)"
								onkeyup="isValidDecimal(this)" /> <%
	 	}
	 %> <bean:message key="inRs" />
	
	
						</td>
					</tr>
	
					<tr align="left">
						<td class="ColumnBackground" align="left" valign="top"><font
							color="#FF0000" size="2">*</font> <bean:message
								key="unseqLoanportionLbl" /></td>
						<td class="TableData" align="left" valign="top" colspan="4"
							id="totalAb">
							<%
								if (appCommonFlag.equals("11") || appCommonFlag.equals("12")
										|| appCommonFlag.equals("13") || appCommonFlag.equals("2")) {
							%> <bean:write property="unseqLoanportion" name="appForm" /> <%
	 	} else {
	 %> <html:text property="unseqLoanportion" styleId="unseqLoanportion"
								size="20" alt="unseqLoanportion"
								style="color: red; background: #C4DEE6"
								onblur="calTotalMIcollatSecAmt();" name="appForm" /> <%
	 	}
	 %> <bean:message key="inRs" />
						</td>
					</tr>
					<tr align="left">
						<td class="ColumnBackground" align="left" valign="top"><font
							color="#FF0000" size="2">*</font> <bean:message
								key="existExpoCgtLbl" /></td>
						<td class="TableData" align="left" valign="top" colspan="4">
							<%
								if (appCommonFlag.equals("11") || appCommonFlag.equals("12")
										|| appCommonFlag.equals("13") || appCommonFlag.equals("2")) {
							%> <bean:write property="existExpoCgt" name="appForm" /> <%
	 	} else {
	 %> <html:text property="existExpoCgt" styleId="existExpoCgt"
								alt="existExpoCgt" name="appForm" readonly="true"
								style="color: red; background: #C4DEE6" /> <%
	 	}
	 %> <bean:message key="inRs" />
						</td>
					</tr>
	
					<tr align="left">
						<td class="ColumnBackground" align="left" valign="top"><font
							color="#FF0000" size="2">*</font> <bean:message
								key="unLoanPortionExcludCgtCoveredLbl" /></td>
						<td class="TableData" align="left" valign="top" colspan="4"
							id="totalAb">
							<%
								if (appCommonFlag.equals("11") || appCommonFlag.equals("12")
										|| appCommonFlag.equals("13") || appCommonFlag.equals("2")) {
							%> <bean:write property="unLoanPortionExcludCgtCovered"
								name="appForm" /> <%
	 	} else {
	 %> <html:text property="unLoanPortionExcludCgtCovered"
								styleId="unLoanPortionExcludCgtCovered" size="20"
								alt="unLoanPortionExcludCgtCovered"
								style="color: red; background: #C4DEE6" name="appForm"
								readonly="true" /> <%
	 	}
	 %> <bean:message key="inRs" />
						</td>
					</tr>
	
				</table>
			</TD>
			<!-- End dkr YES 35 -->
		</TR>
	
	
	</table>
	
	
	
	
	
	
	
	
	
	
	
