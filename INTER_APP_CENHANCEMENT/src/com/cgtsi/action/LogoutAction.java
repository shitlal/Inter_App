package com.cgtsi.action;

import com.cgtsi.admin.User;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.Log;
import com.cgtsi.registration.Registration;
import java.io.PrintStream;
import java.net.InetAddress;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;

public class LogoutAction extends BaseAction
{
  public ActionForward logout(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws DatabaseException
  {
    Log.log(4, "LogoutAction", "logout", "Entered");
    HttpSession session = request.getSession(false);

    User user = (User)session.getAttribute("USER");

    String userName = user.getFirstName();

    String bankId = user.getBankId().trim();
    String zoneId = user.getZoneId().trim();
    String branchId = user.getBranchId().trim();
    String ipAddress = "";
    String hostName = "";
    String proxyName = "";
    String sessionId = "";
    try
    {
      InetAddress i = InetAddress.getLocalHost();

      ipAddress = request.getRemoteAddr();
      hostName = request.getRemoteHost();
      proxyName = request.getHeader("VIA");
      sessionId = session.getId();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

    session.removeAttribute("USER");
    Registration registration = new Registration();
    System.out.println("Session Id:" + sessionId);
    registration.updateLogoutInformation(bankId, zoneId, branchId, ipAddress, hostName, proxyName, sessionId, user.getUserId());

    session.invalidate();

    request.setAttribute("userId", userName);

    Log.log(4, "LogoutAction", "logout", "Exited");

    return mapping.findForward("success");
  }
}