<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.util.*" %>
    <%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Iterator" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<TABLE width="725" border="0" cellpadding="0" cellspacing="0">
	   <TR> 
			<TD width="20" align="right" valign="bottom"><IMG src="images/TableLeftTop.gif" width="20" height="31"></TD>
			<TD background="images/TableBackground1.gif"><IMG src="images/ReportsHeading.gif" width="121" height="25"></TD>
			<TD width="20" align="left" valign="bottom"><IMG src="images/TableRightTop.gif" width="23" height="31"></TD>
		</TR>
		<TR>
			<TD width="20" background="images/TableVerticalLeftBG.gif">&nbsp;</TD>
			<TD>
				<TABLE width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
					<TR>
						<TD>
							<TABLE width="100%" border="0" cellspacing="1" cellpadding="1">
								<TR>
									<TD colspan="16"> 
										 
            
	
			<tr>
		        
		      
             <td valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>Bank Name
                      </strong><br></div></td>
                 
                  <td valign="top" class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>State Name</strong><br></div></td>
                      
                       <td valign="top"  class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>GST Number
                      </strong></div></td>
                  
                         <td valign="top"  class="HeadingBg"><div align="center">&nbsp;&nbsp;<strong>
                      </strong></div></td>
                        
		         </tr>
      
           <%--   
             <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%= request.getAttribute("MEM_BANK_NAME")%></TD>
		
             <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%= request.getAttribute("state_name")%></TD>
                  
         	  <TD width="10%" align="left" valign="top" class="ColumnBackground1"><%= request.getAttribute("GST_NO")%></TD>
         	 
         	  <TD width="10%" align="left" valign="top" class="ColumnBackground1"><a href="memberStateFormInput.do?method=memberStateFormInput">Edit</a></TD>
         	   
         	  
         	        
 </TD>
         	        
        </TR> --%>
        
         <% 
   		 // added by MSGST      
   		 	ArrayList<com.cgtsi.actionform.ReportActionCOForm> membStateReportL=null; 
	    if(request.getAttribute("memberStateGSTReportList")!=null){        	
        	membStateReportL=(ArrayList<com.cgtsi.actionform.ReportActionCOForm>)request.getAttribute("memberStateGSTReportList");
        }    
     
       int serial = 0;
       int num=1;
       int recSize=membStateReportL.size();
        String total_rec_new="";
      try{  
    %>
     <input type="hidden" Id="rowcounter" value="<%=recSize%>">
    <%	 
                  
        if(membStateReportL.size()!=0)
        {
    	 for(int i=0;i<membStateReportL.size();i++)
    	 {
    	  int total_rec=num++;
    	  if(total_rec>0)
    	  {
    		total_rec_new=Integer.toString(total_rec);
    	  }
    	  
       //===========================till here====================//
//      	String urld = "displaySecClaimDetails.do?method=displaySecClaimDetails&clMRefNo="+membStateReportL.get(i).getClmRefNo();
     	//java.text.DateFormat dateFormat = new java.text.SimpleDateFormat ("dd/mm/yyyy");
     %>
         <TR>	
         		
            <td align="left"  class="ColumnBackground1"><%=++serial%></td>
             <td align="left"  class="ColumnBackground1"><%=membStateReportL.get(i).getBankName() %></td> 
                <td align="left"  class="ColumnBackground1"><%=membStateReportL.get(i).getStateName() %></td> 
                   <td align="left"  class="ColumnBackground1"><%=membStateReportL.get(i).getGstNo() %></td>                    
               <TD width="10%" align="left" valign="top" class="ColumnBackground1"><a href="memberStateFormInput.do?method=memberStateFormInput&state_name=<%=membStateReportL.get(i).getStateName() %>">Edit</a></TD>
           </TR>
       <% }}} catch ( Exception e ){ e.printStackTrace(); } %>  
		 </TABLE>	
		 </TR>
		 <tr align="center" valign="baseline">
           		 <td colspan="4"> 
           		 <div align="center"> 
		         <a href="memberStateReportInput.do?method=memberStateReportInput">
		        <IMG src="images/Back.gif" alt="Back" width="49" height="37" border="0"></A>
		        
		         
		      </div>
		      </td>
		     </tr>
		  	</table>     
		

			<TD width="20" background="images/TableVerticalRightBG.gif">
				&nbsp;
			</TD>
	     </tr>
		
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
 
</TABLE>
</body>
</html>