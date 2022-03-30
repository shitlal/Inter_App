<%@ page language="java"%>
<%@ taglib uri="/WEB-INF/TLD/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/TLD/struts-bean.tld" prefix="bean" %>

<%
	session.setAttribute("CurrentPage","showChangePassword.do");
%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>
<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
<html:errors />

<html:form action="showChangePassword.do" method="POST" focus="oldPassword">
<TABLE width="680" border="0" cellspacing="0" cellpadding="0" align="center">
<TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>

			<TD width="713">

			<DIV align="right">			
				<A HREF="javascript:submitForm('helpChangePassword.do?method=helpChangePassword')">
			    HELP</A>
			</DIV>
			
			<TABLE width="606" border="0" cellspacing="1" cellpadding="0">
			<TD  align="left" colspan="4"><font size="2"><bold>
				Fields marked as </font><font color="#FF0000" size="2">*</font><font size="2"> are mandatory</bold></font>
				</td>
          <TR> 
            <TD colspan="2" width="700"><TABLE width="604" border="0" cellspacing="0" cellpadding="0">
												<TD width="31%" class="Heading"><bean:message key="changePasswordHeader" /></TD>
												<TD><IMG src="images/TriangleSubhead.gif" width="19" height="19"></TD>
											</TR>
											<TR>
												<TD colspan="3" class="Heading"><IMG src="images/Clear.gif" width="5" height="5"></TD>
											</TR>

										</TABLE>
									</TD> 

								</TR>

          <TR>
		    <TD align="left" valign="top" class="ColumnBackground" >
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="oldPassword" />
			</TD>
			<TD align="left" valign="top" class="TableData" >										
			<html:password property="oldPassword" size="20" maxlength="16"  alt="Reference"  name="adminForm"/>
			</TD>            
          </TR>

		   <TR>
		    <TD align="left" valign="top" class="ColumnBackground" >
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="newPassword" />
			</TD>
			<TD align="left" valign="top" class="TableData" >										
			<html:password property="newPassword" size="20" maxlength="16"  alt="Reference"  name="adminForm"/>
			</TD>            
          </TR>
		   <TR>
		    <TD align="left" valign="top" class="ColumnBackground" >
										&nbsp;<font color="#FF0000" size="2">*</font><bean:message key="confirm" />
			</TD>
			<TD align="left" valign="top" class="TableData" >										
			<html:password property="confirm" size="20" maxlength="16" alt="Reference"  name="adminForm"/>
			</TD>            
          </TR>
  
           
          </TR>
            
							<TR align="center" valign="baseline" >
						<TD colspan="2" width="700"><DIV align="center">
						<A href="javascript:submitForm('changePassword.do?method=changePassword&passwordExpiry=<%=session.getAttribute(com.cgtsi.util.SessionConstants.PASSWORD_EXPIRED)%>')">
									<IMG src="images/Save.gif" alt="Save" width="49" height="37" border="0"></A>
								<A href="javascript:document.adminForm.reset()">
									<IMG src="images/Reset.gif" alt="Cancel" width="49" height="37" border="0"></A>
								
						<A href="subHome.do?method=getSubMenu&menuIcon=<%=session.getAttribute("menuIcon")%>&mainMenu=<%=session.getAttribute("mainMenu")%>"><IMG src="images/Cancel.gif" alt="Cancel" width="49" height="37" border="0"></A>
							</DIV>
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

