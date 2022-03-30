<script language="JavaScript" type="text/JavaScript" src="js/CGTSI.js"> </script>
<script language="JavaScript" type="text/JavaScript" src="js/selectdate.js"> </script>
<link href="css/StyleSheet.css" rel="stylesheet" type="text/css">
<LINK href="css/StyleSheet.css" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Open+Sans:400,400i,600|Raleway|Roboto:400,400i,500&display=swap" rel="stylesheet">
<%-- <link href="<%=request.getContextPath()%>/css/custom.css" rel="stylesheet" type="text/css"> --%>
<link href="<%=request.getContextPath()%>/css/common.css" rel="stylesheet" type="text/css">
<%-- <link href="<%=request.getContextPath()%>/css/bootstrap.min.css" rel="stylesheet" type="text/css"> --%>
<link href="<%=request.getContextPath()%>/font-awesome-4.7.0/css/font-awesome.css" rel="stylesheet" type="text/css">

<%-- <script type="text/JavaScript" src="<%=request.getContextPath()%>/js/jquery.min.1.12.4.js"> </script>
<script type="text/JavaScript" src="<%=request.getContextPath()%>/js/bootstrap.min.js"> </script> --%>
<!-- <script type="text/javascript">
var menu1= document.querySelector('.header-menu1');
document.getElementById('menuToggle').addEventListener('click', function(){
	debugger;
	menu1.style.width= '50px';
});
</script> -->
<script>
<%

if(request.getParameter("subMenu")!=null)
{
	session.setAttribute("subMenuItem",request.getParameter("subMenu"));
}

if(request.getParameter("mainMenu")!=null)
{
	if(!request.getParameter("mainMenu").equals((String)session.getAttribute("mainMenu")))
	{
		session.removeAttribute("subMenus");
		session.setAttribute("mainMenu",request.getParameter("mainMenu"));
	}
}

java.util.ArrayList mainMenusItems =(java.util.ArrayList)session.getAttribute("mainMenus");
java.util.ArrayList mainMenuValues =(java.util.ArrayList)session.getAttribute("mainMenuValues");
java.util.ArrayList subMenus =(java.util.ArrayList)session.getAttribute("subMenus");
java.util.ArrayList subMenuValues =(java.util.ArrayList)session.getAttribute("subMenuValues");
%>

<%if(subMenus!=null){ for(int ctr=0; ctr<subMenus.size(); ctr++) { %>

	subMenus[<%=ctr%>] = '<%=subMenus.get(ctr)%>';
	subMenuValues[<%=ctr%>] = '<%=subMenuValues.get(ctr)%>';
<% }} %>	

<% if(mainMenusItems!=null){for (int i=0;i<mainMenusItems.size();i++){%>
mainMenus[<%=i%>]='<%=mainMenusItems.get(i)%>';
mainMenuValues[<%=i%>]='<%=mainMenuValues.get(i)%>';
<%}}%>

selection='<%=session.getAttribute("menuIcon")%>';
mainMenuItem='<%=session.getAttribute("mainMenu")%>';
subMenuItem='<%=session.getAttribute("subMenuItem")%>';

<% if(mainMenusItems!=null){%>
setSubMenu(subMenus);
<%}%>
</script>
