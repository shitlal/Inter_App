package com.cgtsi.payapi;
/**
 * Description : CgtPaymentDao.java
 * Created Date: 06-10-2021
 * Created By  : Deepak Kr Ranjan
 * 
 **/
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import com.cgtsi.util.DBConnection;

public class CgtPaymentDao {

	public ArrayList<PaymentRequest> requestVanListFromDb(ArrayList<String> payList) {// throws DatabaseException {
		Connection conn = null;
		PaymentRequest paymentRequest = null;
		
		ArrayList<PaymentRequest> paymentRequestList = new ArrayList<PaymentRequest>();
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			if(conn==null){
				conn = DBConnection.getConnection();
			}
          // ArrayList<String> payList=new ArrayList<String>();
           for (String pay_id : payList) {	
   			//String query = "select PAY_ID,VIRTUAL_ACCOUNT_NO,AMOUNT,TO_CHAR(PAYMENT_INITIATED_DATE,'dd-mm-yyyy hh:mm:ss') PAYMENT_INIT_DATE from online_payment_detail where DAN_TYPE='RV' and PAYMENT_STATUS='I' and rownum<=2";

			String query = "select PAY_ID,VIRTUAL_ACCOUNT_NO,AMOUNT,TO_CHAR(PAYMENT_INITIATED_DATE,'dd-mm-yyyy hh:mm:ss') PAYMENT_INIT_DATE from online_payment_detail where DAN_TYPE='RV' and PAYMENT_STATUS='I' and PAY_ID=?";
			System.out.println(conn + "::>>>> Query>>>>>>>>>>>>" + query);
			pstmt = conn.prepareStatement(query); // create a statement
			pstmt.setString(1, pay_id); // set input parameter
			rs = pstmt.executeQuery();
			// extract data from the ResultSet
			while (rs.next()) {
				paymentRequest = new PaymentRequest();
				paymentRequest.setDealerCode(rs.getString("PAY_ID"));
				paymentRequest.setVirtualAccountNumber(rs.getString("VIRTUAL_ACCOUNT_NO"));
				paymentRequest.setAmount(String.valueOf(rs.getDouble("AMOUNT")));
				paymentRequest.setAmountGeneratedOn(rs.getString("PAYMENT_INIT_DATE"));
				paymentRequestList.add(paymentRequest);
				System.out.println("record:~~~~~~~~~~~~DAO.getVirtualAccountNumber~~~~~~~~~~~~~~~~~~~~~~~~~~"
						+ paymentRequest.getVirtualAccountNumber());
			}
           }
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				rs.close();
				pstmt.close();
				//conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return paymentRequestList;
	}

	public void updatePaidStatuswithVan(ApiCgsResponse fianlDataResponseObj, String vanStatus,String createdBy) {
		// TODO Auto-generated method stub
		Connection conn = null;
		PreparedStatement st = null;
		ResultSet rs = null;
		try {
			if (conn == null) {
				conn = DBConnection.getConnection();
			}
			System.out.println("Connection UPDATE "+conn);
		    /** 	System.out.println("Going to update msg: " + fianlDataResponseObj.getErrorMsg() + " VAN / Status: " + vanStatus);
			 
			        String sqlUpdtQuery = "UPADATE ONLINE_PAYMENT_DETAIL O SET O.PAYMENT_DATE=?,O.PAYMENT_CREDITED_DATE=?, O.FILENAME=?,O.PAYMENT_STATUS=? WHERE VIRTUAL_ACCOUNT_NO=?";
			    	System.out.println("vanStatus::::"+vanStatus);
			    	pstmt= conn.prepareStatement(sqlUpdtQuery);
					conn.setAutoCommit(false);
					pstmt.setDate(1,java.sql.Date.valueOf(fianlDataResponseObj.getMsgtime()));			
					pstmt.setDate(2,java.sql.Date.valueOf(fianlDataResponseObj.getMsgtime()));			
					pstmt.setString(3,vanStatus.concat(".cvs"));
					pstmt.setString(4,"R");
					pstmt.setString(5,vanStatus);					
					ResultSet rst = pstmt.executeQuery(sqlUpdtQuery); 
					if(rst.next()==true) {
			          System.out.println("Payment Status updated successfully.");
					}else {
					  System.out.println("Payment Status unable to update.");
					}
					*/
			    String apiResponseQuery="INSERT INTO CORPORATION_API_RESPONSE (VIRTUAL_AC_NO,MSG_ID,MSG_TIME,CHANNEL_NAME,STATUS,TOKEN,ERROR_MSG,MSG_RRN,ADD_DATA,CREATED_BY,CREATED_DT,FLAG)"
			    		+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";
			  
			    st = conn.prepareStatement(apiResponseQuery);
			    st.setString(1, vanStatus);//fianlDataResponseObj.getStatus()); // VIRTUAL
			    st.setString(2, fianlDataResponseObj.getMsgid());
			    st.setString(3, fianlDataResponseObj.getMsgtime());			    
			    st.setString(4, fianlDataResponseObj.getChannelName());
			    System.out.println("fianlDataResponseObj.getChannelName():>>> "+fianlDataResponseObj.getChannelName());
			    st.setString(5, fianlDataResponseObj.getStatus());
			    System.out.println("fianlDataResponseObj.getStatus():>>> "+fianlDataResponseObj.getStatus());
			    st.setString(6, fianlDataResponseObj.getToken());
			    System.out.println("fianlDataResponseObj.getToken():>>> "+fianlDataResponseObj.getToken());
			    st.setString(7, fianlDataResponseObj.getErrorMsg());
			    System.out.println("fianlDataResponseObj.getErrorMsg():>>> "+fianlDataResponseObj.getErrorMsg());
			    st.setString(8, fianlDataResponseObj.getMsgrrn());
			    st.setString(9, fianlDataResponseObj.getData());
			    st.setString(10, createdBy);
			    st.setDate(11, new java.sql.Date(System.currentTimeMillis()));
			    st.setString(12, "VC");
			    st.executeUpdate();
			    System.out.println("Api Response Query: "+apiResponseQuery);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				st.close();
				conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
