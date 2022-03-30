<%@ page language="java"%>
<% session.setAttribute("CurrentPage","showSuccessPage.do");%>
<%@ include file="/jsp/SetMenuInfo.jsp" %>

<html> 
<head>
<title><bean:message key="success" /> </title>
</head>
<body>
<form>
<table>
<tr>
<td width="755" align="center" valign="bottom" height="100">
	<%
		String message1="Reschedulement request pending at checker for Approval.";
		
		if(request.getAttribute("message1")!=null)
		{
			message1=(String)request.getAttribute("message1");
		}
		out.println(message1);
	%>
</td>
</tr>
<TR> 
<TD width="755" align="center" valign="bottom">
	 <a href="home.do?method=getMainMenu&menuIcon=<%=session.getAttribute("menuIcon")%>"><img src="images/OK.gif" width="49" height="37" border="0"></a>
</TD>
</TR>
</TABLE>
</form>
</body>
</html>

