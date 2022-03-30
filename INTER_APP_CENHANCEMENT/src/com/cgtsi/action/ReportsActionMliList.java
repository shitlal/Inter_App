package com.cgtsi.action;

import com.cgtsi.common.Log;
import com.cgtsi.common.NoDataException;
import com.cgtsi.reports.ReportManager;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.DynaActionForm;

public class ReportsActionMliList extends BaseAction
{
  private ReportManager reportManager = null;

  public ReportsActionMliList()
  {
    this.reportManager = new ReportManager();
  }

  public ActionForward listOfMLIPath1(ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response)
    throws Exception
  {
    Log.log(4, "ReportsAction", "listOfMLIPath", "Entered");
    ArrayList mli = new ArrayList();
    DynaActionForm dynaForm = (DynaActionForm)form;

    mli = this.reportManager.getMliList();
    dynaForm.set("mli", mli);
    if ((mli == null) || (mli.size() == 0))
    {
      throw new NoDataException("No Data is available for the values entered, Please Enter Any Other Value ");
    }

    mli = null;
    Log.log(4, "ReportsAction", "listOfMLIPath", "Exited");

    return mapping.findForward("success1");
  }
}