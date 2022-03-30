package com.cgtsi.util;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import oracle.jdbc.OracleTypes;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.struts.upload.FormFile;

import com.cgtsi.admin.User;
import com.cgtsi.common.Constants;
import com.cgtsi.common.DatabaseException;
import com.cgtsi.common.MessageException;


public class BulkUpload {

	
	public static void main(String[] args) throws IOException, ClassNotFoundException, SQLException, ParseException, DatabaseException {
		
/*		BulkUpload BlUpObj = new BulkUpload();
		Connection con=BlUpObj.getConnection();		
		String BulkName="UTR";
		LinkedHashMap<String,TableDetailBean> headerMap = BlUpObj.getTableHeaderData(con,"claim_payment_utr_detail_stag",BulkName);				
		User user = new User();
		user.setUserId("Admin");
		user.setBankId("0000");
		user.setZoneId("0000");
		user.setBranchId("0000");
		
		Date stDt = new Date();
		System.out.println("stDt :"+stDt);
		BlUpObj.CheckExcelData(headerMap,"claim_payment_utr_detail_stag",con,user,BulkName);
		System.out.println("Conn:"+BlUpObj.getConnection());/**/
/**/		
		//String pattern = "ddMMMyyyyhhmmss";
		//String dateInString =new SimpleDateFormat(pattern).format(new Date());
		//System.out.println("dateInString :"+dateInString);   
		
		
	}
	
	public Connection getConnection() throws ClassNotFoundException, SQLException{
		Connection dbConn=null;
		//##
		System.out.println("getConnection()");
		Class.forName("oracle.jdbc.driver.OracleDriver");  
		Connection con=DriverManager.getConnection(  
		//"jdbc:oracle:thin:@192.168.10.120:1523:CGINTRA","CGTSIINTRANETUSER","CGTSIINTRANETUSER$321");  
		"jdbc:oracle:thin:@158.100.60.116:1521:CGINTRA","CGTSIINTRANETUSER","CGTSIINTRANETUSER");		
		Statement stmt=con.createStatement();  
		ResultSet rs=stmt.executeQuery("select sysdate from dual");  
		while(rs.next()){
				System.out.println(rs.getString(1));
		} 
		//## 
		return con; 
		
	}/**/
	/*    Old ************************
	public String prepareDataForInsertQuery(HSSFCell cell,String FieldType){
		String preparedValue="";
		if(FieldType.equals("VARCHAR2")){
			preparedValue="'"+cell.toString()+"'";
			preparedValue=preparedValue.replace("''","'");
		}else if(FieldType.equals("DATE")){
			preparedValue="'"+cell.toString()+"'";
			
		}else if(FieldType.equals("NUMBER")){
			preparedValue=cell.toString();
		}		
		return preparedValue;		
	}/**/
	
	//##
	public String prepareDataForInsertQuery(HSSFCell cell,TableDetailBean tableBeanObj){
		String preparedValue="''";
		if(tableBeanObj.getColumnDataType().equals("VARCHAR2")){
			
			if(cell!=null){
					if(cell.getCellType() == Cell.CELL_TYPE_NUMERIC){					
						BigDecimal bd = new BigDecimal(cell.getNumericCellValue());
						preparedValue=bd.toPlainString();						
					}else {			
						if(cell.getCellType() == Cell.CELL_TYPE_BLANK){
								preparedValue="''";
						}else{
							
					   //Comment System.out.println(tableBeanObj.getDisplayColumnName()+"***preparedValue:****"+cell.toString()+"cell.toString() trim() length:"+cell.toString().trim().length());
					    
						    if(cell.toString().trim().length()==0){					    	
       				    	    preparedValue="''";					    	
						    }else{
								preparedValue="'"+cell.toString().replace("'","''")+"'";
						    }
					    
						}
					}
			}
		}else if(tableBeanObj.getColumnDataType().equals("DATE")){			
			if(cell!=null){
				Date dateParsed1=null;
				Date dateParsed2=null;				
				//##
				 try{
					 dateParsed1 = new SimpleDateFormat("dd-MMM-yyyy").parse(cell.toString().trim());
				 }catch(ParseException pex1){					 
				 }
				try{
					dateParsed2 =new SimpleDateFormat("dd/mm/yyyy").parse(cell.toString().trim());
				}catch(ParseException pex2){						
				}				
				if(dateParsed1!=null){
					preparedValue="'"+cell.toString().trim()+"'";
				}				
				if(dateParsed2!=null){
					preparedValue="to_date('"+cell.toString().trim()+"','dd/mm/yyyy')";
				}
			}			
		}else if(tableBeanObj.getColumnDataType().equals("NUMBER")){			
			if(cell!=null){				
					if(cell.getCellType() == Cell.CELL_TYPE_BLANK){
						   preparedValue="''";
					}else{
						   BigDecimal bd = new BigDecimal(cell.getNumericCellValue());     						
						   preparedValue=bd.toPlainString();
					}
			} 
		}
		//System.out.println(tableBeanObj.getDisplayColumnName()+" b4 preparedValue :"+preparedValue);
		
		preparedValue=preparedValue.trim();
		//System.out.println(tableBeanObj.getDisplayColumnName()+" af preparedValue :"+preparedValue);
		//System.out.println(tableBeanObj.getDisplayColumnName()+" af preparedValue length:"+preparedValue.trim().length());
		return preparedValue;		
	}
	
	
	
	public boolean validateFieldType(HSSFCell cell,TableDetailBean tableBeanObj) {
		boolean isValid = false;
 	 try{ 
		if(tableBeanObj.getColumnDataType().equals("VARCHAR2")){
				if (cell!= null) {		
						if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
							
							    //System.out.println(tableBeanObj.getColumnName()+"###validateFieldType####"+cell.getStringCellValue().length()+"######tableBeanObj.ColumnAllowNullFlag :######"+tableBeanObj.ColumnAllowNullFlag);
							    
							if ((cell.getStringCellValue().trim().length() > 0 && cell.getStringCellValue().trim().length()<=tableBeanObj.getColumnLength())  ){												
								if ((cell.getStringCellValue().length() > 0 && cell.getStringCellValue().length()<=tableBeanObj.getColumnLength())  ){
								//	if ((cell.getStringCellValue().trim().length() > 0 && cell.getStringCellValue().trim().length()<=tableBeanObj.getColumnLength()) && tableBeanObj.ColumnAllowNullFlag.equals("Y") ){
										  
										  isValid = true;
										 // System.out.println(tableBeanObj.getColumnName()+" 1 isValid :"+isValid);
									  }else{
										  isValid = false;										 
										 // System.out.println(tableBeanObj.getColumnName()+" 2 isValid :"+isValid);
									  }					
								}else{									
									isValid = false;
									//System.out.println(tableBeanObj.getColumnName()+" 3 isValid :"+isValid);
								}
						}
						if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
							String val=""+cell.getNumericCellValue();
							if(val.contains(".0")){
								val=val.substring(0,val.length()-2);
							}
							if (val.length()> 0 && val.length()<=tableBeanObj.getColumnLength()){
								isValid = true;
							}else{
								isValid = false;
							}							
						}						
						if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
							if(tableBeanObj.getColumnAllowNullFlag().equals("Y")){
								isValid = true;
							}else{
								isValid = false;
							}
						}
						///#####
						if(cell.getStringCellValue().trim().length()==0){
							if(tableBeanObj.getColumnAllowNullFlag().equals("Y")){
								isValid = true;
							}else{
								isValid = false;
							}
						}
						
						
				}else{				
						if(tableBeanObj.getColumnAllowNullFlag().equals("Y")){
							isValid = true;
						}else{
							isValid = false;
						}					
				}
		}
		
		if(tableBeanObj.getColumnDataType().equals("NUMBER")){
			if (cell != null) {
				if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
						isValid = true;
				}
				
				if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
					System.out.println(tableBeanObj.getColumnName()+" CELL_TYPE_STRING ");
					isValid = false;
				}
				
				if(cell.getCellType()==Cell.CELL_TYPE_BLANK){
					if(tableBeanObj.getColumnAllowNullFlag().equals("Y")){
						isValid = true;
					}else{					
						isValid = false;
					}
				}
			} else {		
					if(tableBeanObj.getColumnAllowNullFlag().equals("Y")){
						isValid = true;
					}else{
						isValid = false;
					}				
			}
		}		
		if(tableBeanObj.getColumnDataType().equals("DATE")){
			
			if (cell != null) {	
				Date date=null;
				DateFormat df1 = new SimpleDateFormat("dd-MMM-yyyy"); // for parsing input
				DateFormat df3 = new SimpleDateFormat("dd/mm/yyyy"); // for parsing input
				
				try{
					
					Date data_dt = df1.parse(cell.toString());					
					isValid = true;
					
				} catch (ParseException e){
					
					System.out.println("e :"+e);
					
				try{						
					Date data_dt = df3.parse(cell.toString());
					isValid = true;
					
					}catch(ParseException inner_e){
						
						System.out.println("inner e :"+e);
						isValid = false;
					}
				}							
     		}else{     			
     			if(tableBeanObj.getColumnAllowNullFlag().equals("Y")){
					isValid = true;
				}else{
					isValid = false;
				}
     		}
		}		
		}catch(Exception e){
			 System.out.println("Error:"+e);
		}	
		System.out.println(tableBeanObj.getColumnName()+"=====isValid"+isValid);
		return isValid;
	 }		
	
	public LinkedHashMap<String,TableDetailBean> getTableHeaderData(Connection conn,String TableName,String BulkName)	throws  ParseException, DatabaseException {
		//Log.log(Log.INFO, "ClaimAction", "getTableHeaderData()","Entered!");
		System.out.println("##getTableHeaderData## 1");
		CallableStatement callableStmt = null;
		ResultSet resultset = null;
		int status = -1;
		String errorCode = null;
		LinkedHashMap<String,TableDetailBean> TableHeaderDataDetail=new LinkedHashMap<String,TableDetailBean>();
		try {
			// ##			
			callableStmt = conn.prepareCall("{?=call BULKUPLOAD.Func_table_header_data_"+BulkName+"(?,?,?,?,?,?)}");
			callableStmt.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt.setString(2, "Admin");
			callableStmt.setString(3, TableName);
			callableStmt.registerOutParameter(4, Constants.CURSOR);			
			callableStmt.registerOutParameter(5, java.sql.Types.NUMERIC);
			callableStmt.registerOutParameter(6, java.sql.Types.VARCHAR);
			callableStmt.registerOutParameter(7, java.sql.Types.VARCHAR);
			callableStmt.execute();
			status = callableStmt.getInt(1);
			errorCode = callableStmt.getString(7);

			if (status == Constants.FUNCTION_FAILURE) {
				//Log.log(Log.ERROR, "ClaimAction","getClaimSettlePaymentReportData()","SP returns a 1. Error code is :" + errorCode);
				callableStmt.close();
				System.out.println("errorCode:"+errorCode);
				throw new DatabaseException(errorCode);
				

			} else if (status == Constants.FUNCTION_SUCCESS) {
				
				resultset = (ResultSet) callableStmt.getObject(4);
				int max_tab_id = callableStmt.getInt(5);
				String firstColName = callableStmt.getString(6);
				
				//System.out.println("##getTableHeaderData## max_tab_id:"+max_tab_id);
				//System.out.println("##getTableHeaderData## firstColName:"+firstColName);
				
				
				while (resultset.next()) {
					TableDetailBean tableBeanObj=new TableDetailBean();
					tableBeanObj.setColumnName(resultset.getString("column_name"));
					tableBeanObj.setColumnDataType(resultset.getString("data_type"));
					tableBeanObj.setColumnLength(resultset.getInt("data_length"));
					tableBeanObj.setColumnAllowNullFlag(resultset.getString("NULLABLE"));
					tableBeanObj.setMax_table_id(max_tab_id);
					//tableBeanObj.setFirstColumnName(firstColName);
					tableBeanObj.setDisplayColumnName(resultset.getString("DISPLAY_NAME"));
					TableHeaderDataDetail.put(resultset.getString("column_name"), tableBeanObj);
				}			 
			}
			resultset.close();
			resultset = null;
			callableStmt.close();
			callableStmt = null;			
		} catch (SQLException sqlexception) {
			//Log.log(Log.ERROR, "ClaimAction","getClaimSettlePaymentSavedMLIWiseCK2Data()","Error retrieving all Claim settled Payment Process Data!");
			System.out.println("sqlexception :"+sqlexception.toString());
			throw new DatabaseException(sqlexception.getMessage());
			
		} finally {
			//DBConnection.freeConnection(conn);
		}
		return TableHeaderDataDetail;
	}
	 
	
	public void validateData(Connection con,ArrayList successRecord,ArrayList unsuccessRecord,String UploadId,String BulkName,LinkedHashMap<String,TableDetailBean> headers) throws SQLException{
		
		
		//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@1@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		
		//####Validate Function####start####
		CallableStatement callableStmt1 = null;		
		int status1 = -1;
		String errorCode1 = null;
		try {
			// ##			
			callableStmt1 = con.prepareCall("{?=call BULKUPLOAD.Func_validate_data_"+BulkName+"(?,?,?)}");
			callableStmt1.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt1.setString(2, "Admin");
			callableStmt1.setString(3, UploadId);
			callableStmt1.registerOutParameter(4, java.sql.Types.VARCHAR);
			callableStmt1.execute();
			status1 = callableStmt1.getInt(1);
			errorCode1 = callableStmt1.getString(4);

			if (status1 == Constants.FUNCTION_FAILURE) {
				//Log.log(Log.ERROR, "ClaimAction","getClaimSettlePaymentReportData()","SP returns a 1. Error code is :" + errorCode);
				callableStmt1.close();
				System.out.println("errorCode1 :"+errorCode1);
				//throw new DatabaseException(errorCode1);

			} else if (status1 == Constants.FUNCTION_SUCCESS) {
				System.out.println("Data inserted into staging table successfully");
			}
			callableStmt1.close();
			callableStmt1 = null;			
		} catch (SQLException sqlexception) {
			//Log.log(Log.ERROR, "ClaimAction","getClaimSettlePaymentSavedMLIWiseCK2Data()","Error retrieving all Claim settled Payment Process Data!");
			//throw new DatabaseException(sqlexception.getMessage());
			System.out.println("sqlexception :"+sqlexception);
		} finally {
			//DBConnection.freeConnection(conn);
			con.commit();
		} 
		//####Validate Function####end####
		
		
		
		//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@2@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		
		
		//####Moving to main table Function####start####
		CallableStatement callableStmt2 = null;		
		int status2 = -1;
		String errorCode2 = null;
		try {
			// ##			
			callableStmt2 = con.prepareCall("{?=call BULKUPLOAD.Func_move_stag_to_main_"+BulkName+"(?,?,?)}");
			callableStmt2.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt2.setString(2, "Admin");
			callableStmt2.setString(3, UploadId);
			callableStmt2.registerOutParameter(4, java.sql.Types.VARCHAR);
			callableStmt2.execute();
			status2 = callableStmt2.getInt(1);
			errorCode2 = callableStmt2.getString(4);

			if (status2 == Constants.FUNCTION_FAILURE) {
				//Log.log(Log.ERROR, "ClaimAction","getClaimSettlePaymentReportData()","SP returns a 1. Error code is :" + errorCode);
				callableStmt2.close();
				//throw new DatabaseException(errorCode);

			} else if (status2 == Constants.FUNCTION_SUCCESS) {
				System.out.println("Data moved into main table successfully");
			}
			callableStmt2.close();
			callableStmt2 = null;			
		} catch (SQLException sqlexception) {
			//Log.log(Log.ERROR, "ClaimAction","getClaimSettlePaymentSavedMLIWiseCK2Data()","Error retrieving all Claim settled Payment Process Data!");
			//throw new DatabaseException(sqlexception.getMessage());
		} finally {
			con.commit();
			//DBConnection.freeConnection(conn);
		} 
		//####Moving to main table Function####end####
		
		
		
		
		//@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@3@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
		
		
		//####Getting Successful and Unsuccessful data table Function####start####
		CallableStatement callableStmt3 = null;
		ResultSet resultset3i = null;
		ResultSet resultset3ii = null;
		int status3 = -1;
		String errorCode3 = null;
		ArrayList nestData = new ArrayList();
		
		//##
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSetMetaData resultSetMetaData1 = null;
		ResultSetMetaData resultSetMetaData2 = null;
		ArrayList coulmName1 = new ArrayList();
		ArrayList coulmName2 = new ArrayList();
		
		ArrayList DataList1 = new ArrayList();
		ArrayList DataList2 = new ArrayList();
		
		ArrayList AllDataList1 = new ArrayList();
		ArrayList AllDataList2 = new ArrayList();
		//##
		
		try {
			// ##			
			callableStmt3 = con.prepareCall("{?=call BULKUPLOAD.Func_get_uploaded_data_"+BulkName+"(?,?,?,?,?)}");
			callableStmt3.registerOutParameter(1, java.sql.Types.INTEGER);
			callableStmt3.setString(2, "Admin");
			callableStmt3.setString(3, UploadId);
			callableStmt3.registerOutParameter(4, Constants.CURSOR);
			callableStmt3.registerOutParameter(5, Constants.CURSOR);			
			callableStmt3.registerOutParameter(6, java.sql.Types.VARCHAR);
			callableStmt3.execute();
			status3 = callableStmt3.getInt(1);
			errorCode3 = callableStmt3.getString(6);

			if (status3 == Constants.FUNCTION_FAILURE) {
				//Log.log(Log.ERROR, "ClaimAction","getClaimSettlePaymentReportData()","SP returns a 1. Error code is :" + errorCode);
				callableStmt3.close();
				//throw new DatabaseException(errorCode);

			} else if (status3 == Constants.FUNCTION_SUCCESS) {
				//Successful data  start
				rs1 = (ResultSet) callableStmt3.getObject(4);
				resultSetMetaData1 = rs1.getMetaData();
				int coulmnCount1 = resultSetMetaData1.getColumnCount();
				for (int i = 1; i <= coulmnCount1; i++) {
					coulmName1.add(resultSetMetaData1.getColumnName(i));
				}

				ArrayList<String> DisplayHeaderNameLst1 =  getDisplayNameOfHeaderName(coulmName1,headers);
				
				while (rs1.next()) {

					ArrayList columnValue1 = new ArrayList();
					for (int i = 1; i <= coulmnCount1; i++) {
						columnValue1.add(rs1.getString(i));
					}
					DataList1.add(columnValue1);
				}
				//System.out.println("list data " + nestData);
				//successRecord.add(0, coulmName1);
				successRecord.add(0,DisplayHeaderNameLst1);				
				successRecord.add(1, DataList1);
				//Successful data end
				
				
				//UnSuccessful data start
				rs2 = (ResultSet) callableStmt3.getObject(5);
				resultSetMetaData2 = rs2.getMetaData();
				int coulmnCount2 = resultSetMetaData2.getColumnCount();
				for (int i = 1; i <= coulmnCount2; i++) {
					coulmName2.add(resultSetMetaData2.getColumnName(i));
				}
				
				ArrayList<String> DisplayHeaderNameLst2 =  getDisplayNameOfHeaderName(coulmName2,headers);

				while (rs2.next()) {

					ArrayList columnValue2 = new ArrayList();
					for (int i = 1; i <= coulmnCount2; i++) {
						columnValue2.add(rs2.getString(i));
					}
					DataList2.add(columnValue2);
				}
				//System.out.println("list data " + nestData);
				//unsuccessRecord.add(0, coulmName2);
				unsuccessRecord.add(0, DisplayHeaderNameLst2);				
				unsuccessRecord.add(1, DataList2);
				//UnSuccessful data end
				
				
				/*resultset3i = (ResultSet) callableStmt3.getObject(3);				
				resultset3ii = (ResultSet) callableStmt3.getObject(4);
				while (resultset3i.next()) {
					ArrayList columnValue = new ArrayList();
					columnValue.add(resultset3i.getString(i));
					successRecord.add(resultset3i.getString(1));
					nestData.add(columnValue);
				}			 
				while (resultset3ii.next()) {
					unsuccessRecord.add(resultset3ii.getString(1));
				}/**/
			}
			
			rs1.close();
			rs1 = null;
			
			rs2.close();
			rs2 = null; 
			
			callableStmt3.close();
			callableStmt3 = null;
			resultSetMetaData1 = null;			
			resultSetMetaData2 = null;
			
			
			
		} catch (SQLException sqlexception) {
			//Log.log(Log.ERROR, "ClaimAction","getClaimSettlePaymentSavedMLIWiseCK2Data()","Error retrieving all Claim settled Payment Process Data!");
			//throw new DatabaseException(sqlexception.getMessage());
		} finally {
			//DBConnection.freeConnection(conn);
		}   
		//####Getting Successful and Unsuccessful data table Function####end####	 

	 }
	
	
	
     public HashMap CheckExcelData(FormFile uploadFormFile,LinkedHashMap<String,TableDetailBean> headers,String TableName,Connection con,User UserObj,String BulkName) throws IOException, SQLException{    	 
  	 //public HashMap CheckExcelData(LinkedHashMap<String,TableDetailBean> headers,String TableName,Connection con,User UserObj,String BulkName) throws IOException, SQLException{   	 
	//public HashMap CheckExcelData(LinkedHashMap<String,TableDetailBean> headers,String TableName,Connection con,String UserId) throws IOException, SQLException{
		
		HashMap UploadedStatus = null;
		UploadedStatus = new HashMap();
	        
	     
		
		ArrayList validapps = new ArrayList();		
		ArrayList invalidapps = new ArrayList();
		FileInputStream fis = null;		
		InputStream is = null;		
		Statement Stat=null;
		Stat=con.createStatement();
		HashMap<String,String> Excelheaders = new HashMap<String,String>();
		HashMap<String,String> UnMatchheaders = new HashMap<String,String>();
		
		String ColumnNames="";
		boolean setFirstColumFlag=false;
		int max_table_id=0;
		//System.out.println("headers :"+headers);
		int p=0;		
		//##BATCH_ID##
		String pattern = "ddMMMyyyyhhmmss";
		String UploadId =new SimpleDateFormat(pattern).format(new Date());
		
		//Comment StringBuffer stb = new StringBuffer();
		//Comment stb.append(" INSERT INTO BULKUPLOAD_STATUS(BULKUPLOAD_NAME,UPLOAD_ID,STATUS_DESCRIPTION,STAGING_TAB_INSERT_CNT,MAIN_TAB_INSERT_CNT,BUSINESS_TAB_INSERT_CNT,INSERT_DT) ");
		//Comment stb.append(" VALUES('"+TableName+"','"+UploadId+"','INSERTING INTO STAGING',0,0,0,SYSDATE) ");		
		//Comment String insrtQury4BulkupldStatTab=stb.toString();
		
		
		//Comment Stat.addBatch(insrtQury4BulkupldStatTab);
		
		//Comment System.out.println("insrtQury4BulkupldStatTab:"+insrtQury4BulkupldStatTab);
		
		//System.out.println("UploadId :"+UploadId);
		//############
		for (Entry<String, TableDetailBean> entry: headers.entrySet()) {			
			ColumnNames=ColumnNames+","+entry.getKey();			
		}
		ColumnNames=ColumnNames.substring(1);
		String FirstColumn="";
		int columnCnt=0;
		columnCnt=headers.size();
		//System.out.println("columnCnt:"+columnCnt);
		
		Boolean QueryAddedFlag=false;
		
		try {
			is = uploadFormFile.getInputStream();			
			//is = new FileInputStream("D:\\GenerateFiles\\UTR_NO_EXCEL_UPLOAD_FORMAT1.xls");
			
			//is = new FileInputStream("D:\\GenerateFiles\\UTR_Update_TemplateRevised2.xls");
			//is = new FileInputStream("D:\\GenerateFiles\\UTR_Update_TemplateRevised.xls");
			//is = new FileInputStream("D:\\GenerateFiles\\UTR_Data25Jan2018.xls");
			//is = new FileInputStream("D:\\GenerateFiles\\UTR_Update_TemplateRevised - Copy.xls");
			
			
			
			HSSFWorkbook book;
			book = new HSSFWorkbook(is);
			HSSFSheet sheet = book.getSheetAt(0);
			int ExcelNoOfColumns = sheet.getRow(0).getLastCellNum();
			int rowcount = sheet.getPhysicalNumberOfRows();
			int ActualRowCount = sheet.getPhysicalNumberOfRows();
			
			System.out.println("################################rowcount :"+rowcount);
			 
			
			
      		//	System.out.println(sheet.getSheetName()+"\t sheet row"+sheet.getLastRowNum());
			Iterator rowItr = sheet.iterator();		
			
			Iterator rowheaderItr = sheet.iterator();
			int row_cnt=0;			
			ArrayList errors = new ArrayList();
			
			ArrayList DataHeadererrors = new ArrayList();
			ArrayList Dataerrors = new ArrayList();
			ArrayList Allerrors = new ArrayList();
			
			ArrayList columnNameLst=new ArrayList();
			
			while(rowheaderItr.hasNext()){
				//System.out.println("AA");
				
				    HSSFRow row = (HSSFRow) rowheaderItr.next();
				    HSSFCell celVal[] = new HSSFCell[columnCnt];
				    if(row.getRowNum()==0){
						for (int k = 0; k < columnCnt; k++) {					
								HSSFCell cellV = row.getCell(k) != null ? row.getCell(k): null;
								celVal[k] = cellV != null ? cellV : null;							
									if(null!=celVal[k]){
										Excelheaders.put(celVal[k].toString(),celVal[k].toString());
										columnNameLst.add(celVal[k].toString());
									} 					             				
				        }
					
				    }else{
				    	break;
				    }
			}
			
			//$$
			for (Entry<String, String> entry: Excelheaders.entrySet()) {
				// Check if the current value is a key in the 2nd map
			    if (!headers.containsKey(entry.getKey())) {		
			        // hMap2 doesn't have the key for this value. Add key-value in new map.
			    	UnMatchheaders.put(entry.getKey(), entry.getValue());
			    }
			  //  ColumnNames=ColumnNames+","+entry.getKey();
			}
			//$$
			
			//System.out.println("###Excelheaders :"+Excelheaders);
			//System.out.println("###UnMatchheaders :"+UnMatchheaders);
			
			if(ExcelNoOfColumns!=columnCnt){
				//Excel Count not matching with Template count
				errors.add("Uploaded Excel Column [Count:"+ExcelNoOfColumns+"] not matched with Template Column Count [Count:"+columnCnt+"]");
				UploadedStatus.put("error", errors);
				
			//	
			}else if(UnMatchheaders.size()>0){	
				//Excel Header name not matching with Template Header Name
				String unmatchedHeaderName="";
				for (Entry<String, String> entry: UnMatchheaders.entrySet()) {
					unmatchedHeaderName=unmatchedHeaderName+","+entry.getKey();
				}
				errors.add("Excel Header are not matching Unmatched Header Name: "+unmatchedHeaderName);
				UploadedStatus.put("error", errors);
				
			}else{
			
			while (rowItr.hasNext()) {
				//HSSFRow row = (HSSFRow) rowItr.next();
				//if(row.getCell(0).getCellType()!=Cell.CELL_TYPE_BLANK){
				 ActualRowCount++;
				//}
					
			}
				System.out.println("######################ActualRowCount :"+ActualRowCount);
				
				
				
			while (rowItr.hasNext()) {
				//System.out.println("1");
				//Boolean rowErorExistFlag=false;
				errors = new ArrayList();
				int rowErorExistFlag=0;
				ArrayList ValidrowDataLst=new ArrayList();
				ArrayList rowDataLst=new ArrayList();
				String insertQuery="";
				HSSFRow row = (HSSFRow) rowItr.next();
				HSSFCell celVal[] = new HSSFCell[columnCnt];
				
				for (int k = 0; k < columnCnt; k++) {
					
					//System.out.println("k:"+row.getCell(k));
					HSSFCell cellV = row.getCell(k) != null ? row.getCell(k): null;
					celVal[k] = cellV != null ? cellV : null;
					 //System.out.println("rowErorExistFlag:"+rowErorExistFlag);
					
					//if(!rowErorExistFlag){						
					
					String ColumnName="";
					String ColumnDataType="";
					int ColumnLength=0;
					String ColumnAllowNullFlag="";
					//System.out.println("columnNameLst :"+columnNameLst);
				 
					ColumnName = columnNameLst.get(k).toString();
					//System.out.println("ColumnName:"+ColumnName);
					TableDetailBean tableBeanObj= headers.get(ColumnName);	
					//System.out.println("obj colName"+tableBeanObj.getColumnName());
					//System.out.println("obj datatype:"+tableBeanObj.getColumnDataType());
					ColumnDataType=tableBeanObj.getColumnDataType();
					ColumnLength=tableBeanObj.getColumnLength();
					ColumnAllowNullFlag=tableBeanObj.getColumnAllowNullFlag();
					
					if(!setFirstColumFlag){
						max_table_id=tableBeanObj.getMax_table_id();
						//System.out.println("$$$$max_table_id$$:"+max_table_id);
						FirstColumn=tableBeanObj.getFirstColumnName();
					}
					//##
					//System.out.println("###################");
					//System.out.println("ColumnName:"+ColumnName);
					//System.out.println("ColumnDataType:"+ColumnDataType);
					//System.out.println("###################");
					//##					
						if(row_cnt!=0){
							
							    rowDataLst.add(celVal[k].toString());
							    
								int row_number=row_cnt+1;
						//		    System.out.println("Excel Data:"+celVal[k]);
								if(!validateFieldType(celVal[k],tableBeanObj)){
									
										//errors.add("["+row_number+"]Invalid Data type Column Name:"+ColumnName+" Expected #"+ColumnDataType+"# " );
										errors.add(" Invalid Data type Column Name "+ColumnName+" Expected "+ColumnDataType+" ");
										
										
										//rowErorExistFlag=true;
										rowErorExistFlag++;
								}else{	
									
									//String prepValues = prepareDataForInsertQuery(celVal[k],ColumnDataType);
									String prepValues = prepareDataForInsertQuery(celVal[k],tableBeanObj);									
									ValidrowDataLst.add(prepValues);
								}
						 }						 
					//flag//}
				}				
				//System.out.println("#######################");
				//System.out.println("%%%%%rowDataLst :"+rowDataLst);
				//##
				//$$
				if(row_cnt==1){
					for (Entry<String, TableDetailBean> entry: headers.entrySet()) {					
							DataHeadererrors.add(entry.getKey().toString());
					}
					DataHeadererrors.add("Error Details");						
				}
				//$$
				//##
				if(errors.size()>0){					
					rowDataLst.add(errors.toString().substring(1,errors.toString().length()-1));
					Dataerrors.add(rowDataLst);
				}
				
				
				
				
				//System.out.println("ValidrowDataLst.size() :"+ValidrowDataLst.size());
				System.out.println("########row_cnt#########"+row_cnt);
				//System.out.println("columnCnt"+columnCnt);
				
				if((ValidrowDataLst.size()==columnCnt)&&(rowErorExistFlag==0)){
					
					if(!setFirstColumFlag){  // Appending UTR_ID FIRST COLUMN IN INSERT QUERY
						
					 ColumnNames="RECORD_ID,"+ColumnNames+",UPLOAD_ID,INSERT_BY,INSERT_DT";
					 setFirstColumFlag=true;
					 
					}
					String ColumnValues=max_table_id+",";					
					ColumnValues=ColumnValues+ValidrowDataLst.toString().substring(1, ValidrowDataLst.toString().length()-1);
					ColumnValues=ColumnValues+",'"+UploadId+"','"+UserObj.getUserId()+"',SYSDATE";					
					insertQuery="INSERT INTO "+TableName+"("+ColumnNames+") VALUES("+ColumnValues+")";
					max_table_id=max_table_id+1;
					//System.out.println("ColumnName:"+ColumnNames);
					//System.out.println("ColumnValues:"+ColumnValues);	
					
					Stat.addBatch(insertQuery);
					QueryAddedFlag=true;
					System.out.println("insertQuery : "+insertQuery);
					
				}
							
				row_cnt++;			 
				int counter = 0; 
				
				
			} // end while loop
			        int n[]={};
					try{
						//System.out.println("QueryAddedFlag :"+QueryAddedFlag);
						if(QueryAddedFlag){
			            n=Stat.executeBatch();			            
			            System.out.println("n[]"+n.length);
						}
			            
			        }catch(Exception err){
			        	err.printStackTrace();
			        	throw new MessageException("Error on Page:"+err.toString());
			        	
			        }
			        if(n.length>0){
				        ArrayList successRecord=new ArrayList();
				        ArrayList unsuccessRecord=new ArrayList();
				        
				        //validateData(con,successRecord,unsuccessRecord,UploadId);
				        validateData(con,successRecord,unsuccessRecord,UploadId,BulkName,headers);
				        System.out.println("successRecord size:"+successRecord.size());
				        System.out.println("unsuccessRecord size:"+unsuccessRecord.size());
				        
				        //System.out.println("successRecord:"+successRecord);
				        //System.out.println("unsuccessRecord:"+unsuccessRecord);
				        
				        UploadedStatus.put("successRecord", successRecord);
				        UploadedStatus.put("unsuccessRecord", unsuccessRecord);
				        UploadedStatus.put("error", errors);
				        
			        }
			
		}//end of else loop for ExcelNoOfColumns!=columnCnt
			Allerrors.add(DataHeadererrors);
			Allerrors.add(Dataerrors);
			UploadedStatus.put("allerror", Allerrors);
			
			System.out.println("$$$$$$$$$$errors$$$$$$$$$$$$$$");
			System.out.println("$$$$$$$$$$AllDataerrors$$$$$$$$$$$$$$:"+Allerrors);
			System.out.println("$$$$$$$$$$AllDataerrors.size()$$$$$$$$$$$$$$:"+Allerrors.size());
			//System.out.println("errors : "+errors);			
			System.out.println("$$$$$$$$$$errors$$$$$$$$$$$$$$");
			
			
			// for testing error
		} catch (FileNotFoundException e) {
			if (is != null) {
				is.close();
			}
			if(Stat!=null){
				Stat.close();
			}
			// e.printStackTrace();
		} catch (IOException e) {
			if (is != null) {
				is.close();
			}
			if(Stat!=null){
				Stat.close();
			}
			// e.printStackTrace();
			//throw new MessageException("Unable to Read Excel File.");
		} catch (Exception e) {  	

			e.printStackTrace();
			Date d = new Date();
		} 
      	finally {
			if (is != null) {
				is.close();
			}
			if(Stat!=null){
				Stat.close();
			}
			return UploadedStatus;
		}
  }
     
		ArrayList<String> getDisplayNameOfHeaderName(ArrayList<String> HeaderNameLst,LinkedHashMap<String,TableDetailBean> headers){			
			ArrayList<String> displayHeaderNameLst = new ArrayList<String>();
			for(Entry<String,TableDetailBean> entry:headers.entrySet()){
				TableDetailBean t=entry.getValue();
				//System.out.println("[ "+t.getDisplayColumnName()+"] display #t# Name [ "+t.getColumnName()+" ]");
			}
			
			for(String header:HeaderNameLst){
				if(null!=headers.get(header)){
					TableDetailBean tabledetailbean = headers.get(header);
					//System.out.println("##############getDisplayNameOfHeaderName###############:"+header);
					String display_header_name = tabledetailbean.getDisplayColumnName();
					//System.out.println("##############getDisplayNameOfHeaderName#######display_header_name########:"+display_header_name);
					displayHeaderNameLst.add(display_header_name);
				}else{
					displayHeaderNameLst.add(header);
				}
			}/**/		
			System.out.println("displayHeaderNameLst :"+displayHeaderNameLst);
			System.out.println("DisplayHeaderNameLst Size:"+displayHeaderNameLst.size());
			System.out.println("HeaderNameLst Size:"+HeaderNameLst.size());
			
			
			return displayHeaderNameLst;
		}





}
