<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>
<% session.setAttribute("CurrentPage","showLogin.do");%>
<SCRIPT language="JavaScript" type="text/JavaScript" src="<%=request.getContextPath()%>/js/CGTSI.js">
  
</SCRIPT>
<!-- added by sukumar@path for disable right-click option -->
<script language="javascript"> 
 
              // var message="This function is not allowed here.";
               var message="Sorry, right-click has been disabled";
               function clickIE4(){
 
                             if (event.button==2){
                             alert(message);
                             return false;
                             }
               }
 
               function clickNS4(e){
                             if (document.layers||document.getElementById&&!document.all){
                                            if (e.which==2||e.which==3){
                                                      alert(message);
                                                      return false;
                                            }
                                    }
               }
 
               if (document.layers){
                             document.captureEvents(Event.MOUSEDOWN);
                             document.onmousedown=clickNS4;
               }
 
               else if (document.all&&!document.getElementById){
                             document.onmousedown=clickIE4;
               }
 
               document.oncontextmenu=new Function("alert(message);return false;")
 
</script>



<!-- <body onload="freshLogin()"/> -->
<html:html>
	<HEAD>
		<LINK REV="MADE" HREF="Kesavan_Srinivasan@satyam.com">
		<LINK href="<%=request.getContextPath()%>/css/StyleSheet.css" rel="stylesheet" type="text/css">
		<TITLE>Credit Guarantee Fund Trust for Small Industries(CGTSI)</TITLE>
	</HEAD>

	<BODY BGCOLOR="white">
		<html:errors />
		<html:form action="/login?method=login" method="POST" focus="memberId">
			<TABLE width="100%" border="1">
				<TR>
					<TD>
						<TABLE width="100%" border="0" cellspacing="0" cellpadding="5">
							<TR>
								<TD class="CGTSIInfo">
									<DIV align="center">Credit Guarantee Fund Trust
										<BR>
										for Micro and Small Enterprises
										<BR>
										<BR>
										Set Up
										<BR>
										<BR>
										By
										<BR>
										<BR>
										<DIV class="GOIInfo" align="center">Ministry of SSI,GoI
											<BR>
											&amp;
											<BR>
											Small Industries Development Bank of India
										</DIV>
									</DIV>
								</TD>
								<TD align="center" valign="middle" class="TableData">
									<TABLE border="0">
										<TR>
											<TD class="TableData" ><bean:message key="memberId" />
											</TD>
											<TD><html:text property="memberId" size="14" value="000000000000" onkeypress="return numbersOnly(this, event)" onkeyup="isValidNumber(this)" maxlength="12"/><!--<INPUT name="userId" type="text" size="14" maxlength="8">-->
											</TD>
										</TR>
										<TR>
											<TD class="TableData" ><bean:message key="userId" />
											</TD>
											<TD>
											<html:text property="userId" size="14" maxlength="25" value="admin" /><!--<INPUT name="userId" type="text" size="14" maxlength="8">-->
											</TD>
										</TR>
										<TR>
											<TD class="TableData"><bean:message key="password" />
											</TD>
											<TD><html:password property="password" size="14" value="admincgtsi" maxlength="16" /><!--<INPUT name="password" type="password" size="14">-->
											</TD>
										</TR>
										<TR>
											<TD>
												<DIV align="center">
												</DIV>
											</TD>
											<TD>
												<DIV align="center">
													<!--<INPUT type="submit" name="Submit" value="Submit">-->
													<html:submit>
														<bean:message key="submit" />
													</html:submit>
												</DIV>
											</TD>
										</TR>
										<TR>
											<TD class="TableData">
												<DIV align="right">
												<%
													String url="javascript:submitForm('"+request.getContextPath()+"/getHintQuestion.do?method=getHintQuestion')";
												%>
												<html:link href="<%=url%>">
												Forgot your Password?
												</html:link>
												<td>
												</td>												
												<TD class="TableData">
												<html:link href="jsp/Home.jsp">
												Home
												</html:link>
												</TD>
													<!--<A href="ForgotYourPassword.html">Forgot your Password?
													</A> -->
												</DIV>
											</TD>
										</TR>
                    <!--Start Code by Path on 10Oct06 -->
                   
										<TR>
												<TD class="TableData">
                        <div align="right">
                        <%
                        String url2="javascript:submitForm('"+request.getContextPath()+"/listOfMLIPath1.do?method=listOfMLIPath1')";
                        %>
                        <html:link href="<%=url2%>">List Of MLIs new</html:link>
                        </div>
                   			</TD>
										</TR>                       
                     <!--End Code by Path on 10Oct06 -->
									</TABLE>
									<DIV align="center">
									</DIV>
								</TD>
							</TR>
						</TABLE>
					</TD>
				</TR>
			</TABLE>
		</html:form>
	</BODY>
</html:html>

