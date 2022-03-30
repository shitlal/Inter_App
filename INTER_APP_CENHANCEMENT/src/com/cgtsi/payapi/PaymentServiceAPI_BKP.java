package com.cgtsi.payapi;
/**
 * Description : PaymentServiceAPI_BKP.java
 * Created Date: 06-10-2021
 * Created By  : Deepak Kr Ranjan
 * 
 **/
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.json.JSONException;
import org.json.JSONObject;
import com.google.gson.Gson;
import com.infrasoft.kiya.security.EncryptionDecryptionImpl;

public class PaymentServiceAPI_BKP {

	public static void main(String[] args) throws Exception {
		sendReqResponse();
	}

	public static void sendReqResponse() throws Exception {
		Gson gsonAutho = new Gson(); /* Create Gson object */
		Gson gsonAuthoFinal = new Gson(); 
		String encryKey = "71001000dab44bb7b2cdc3f787cfebb4"; // "71001000dab44bb7b2cdc3f787cfebb4";
		String requestType = "0";
		String msgid = "5019";// "123";
		String msgid2 = "5020";
		EncryptionDecryptionImpl crypto = new EncryptionDecryptionImpl();
		String username = "jogesh";
		String password = "password";		
		String dealerCode="VNF5009-21-21501";	
		String virtualAccountNumber="144440000127032020";
		String amount="12345.50";
		String amountGeneratedOn="13/09/2021";  //13-09-2021 18:17:08
		
		JsonDataForAutho getDataAuth = getJsonDataForAuthApi(requestType, msgid, username, password);			
		//Normal JSON Preparation for AUth.
		String jsonAuthoString = gsonAutho.toJson(getDataAuth);
		System.out.println("Authentication JSON String::::  " + jsonAuthoString);		
		//Request Before Encryption
		String encJsonAuthoEncry = crypto.encryptMessage(jsonAuthoString, encryKey);		
		String apiAurthURL1="https://apimuat.unionbankofindia.co.in/BankServices/handlersb/1/CapsChannel/CapsServiceGroup/AuthAPI";
		
		FinalEncryptedJson getDataAurthoFinal = getJsonDataEncryptParam(encJsonAuthoEncry, msgid);
		String jsonEncryptedFinalString = gsonAuthoFinal.toJson(getDataAurthoFinal);		
		System.out.println("\n Authentication Final json::" + jsonEncryptedFinalString);		
		System.out.println("\n  Authentication URL1 :: "+apiAurthURL1);	
     	String jsonEncryResponse=sendingPostRequest(jsonEncryptedFinalString,apiAurthURL1,"AUTHO","");
     	 String decAuthString = crypto.decryptMessage(jsonEncryResponse, encryKey);  	
		ApiCgsResponse aurthoTokenResponseObj = getFinalDataString(decAuthString,"AUTHO");
		System.out.println("\n URL1 Authentication Url response if Token : "+aurthoTokenResponseObj.getToken()+"   || Response message::"+aurthoTokenResponseObj.getErrorMsg());	
				
		String dataRequestURL2="https://apimuat.unionbankofindia.co.in/BankServices/handlersb/1/CapsChannel/CapsServiceGroup/DealerInsertAPI";		
		//================================ Second Json ================================================================
		    JsonDataForToken getData = getJsonDataForApiParam(requestType, msgid2, dealerCode, virtualAccountNumber, amount, amountGeneratedOn);//Get JsonData For Token Generation
		   	Gson gson = new Gson();                     /*Create Gson object*/			
	    	String jsonString = gson.toJson(getData); 
	    	System.out.println("\n Record Json >>>:  "+jsonString); 
	    	String encMes = crypto.encryptMessage(jsonString, encryKey);	
			FinalEncryptedJson getData2 = getJsonDataEncryptParam(encMes, msgid2);
			Gson finalgson = new Gson();       		
	    	String jsonEncryptedString = finalgson.toJson(getData2);	    
	    	System.out.println("\n  Encrypted Record Json:  "+jsonEncryptedString);
	    	System.out.println("\n Final URL2:"+dataRequestURL2);
	    	String jsonDataEncryFinalResponse=sendingPostRequest(jsonEncryptedString,dataRequestURL2,"REQ_DATA",aurthoTokenResponseObj.getToken());
	    	//System.out.println("Final URL2 response >>>>>:  "+jsonDataEncryFinalResponse);	     	
	    	String jsonDecryFinalResponse = crypto.decryptMessage(jsonDataEncryFinalResponse, encryKey);
	     	System.out.println("\n Final URL2 response:  "+jsonDecryFinalResponse);	     	
	     	ApiCgsResponse fianlDataResponseObj = getFinalDataString(jsonDecryFinalResponse,"REQ_DATA");
			System.out.println("\n URL2 result Message: "+fianlDataResponseObj.getErrorMsg());	
	// Set value for Dealer API ====================================================
//**********************************************************************************************************************
	/**	String Request_json = JavaCodeToJson.convertDataToJson(invoiceIdList.get(i));//checking connection and closing connection
		JSONParser jsonParser = new JSONParser();
		 JSONObject jobj = (JSONObject)jsonParser.parse(Request_json);//{"ItemList":[],"AddlDocDtls":[]}
		JSONArray itemArray = (JSONArray) jobj.get("ItemList");
		 Gson g = new Gson();
			encryptedResponseJson=call_E_InvoiceAuthAPI(jsonEncryResponse,apiRequestURL,"API_cgtmse","27AAATC2613D1ZC",AuthToken,"IRN");//For FINAL URL REQUEST DKR
			JsonResponceError getResponcObj = g.fromJson(Encrypted_Response_json.toString(),JsonResponceError.class);
			System.out.println("Finaly Api IRN Responce Status::::::::::::"+getResponcObj.getStatus()+":::::::::::::::::::::::::");
			String decrypted_response="";
			JsonResponceSuccess successResponcObj=null;
			java.util.Date sDate = (java.util.Date) new Date();
			java.sql.Date sqlDate = new java.sql.Date(sDate.getTime());					

			SimpleDateFormat sdf2 = new SimpleDateFormat("dd/MM/yyyy");								
			//	System.out.println("API Final status==="+getResponcObj.getStatus());
			log.info("::::::IRN Responce Status::::"+getResponcObj.getStatus()+" ::::::::::::");
			if(getResponcObj.getStatus() == 1){
				String Data = getResponcObj.getData();
				decrypted_response= decryptBySymmentricKey(Data,decryptedSek); //Base 64 decrypted response in string invoice JSON using Sek
				System.out.println("TAX_INV_ID::"+invoiceIdList.get(i)+" ,Finaly Decrypted Response::::- " + decrypted_response);
				int colIndex=1;
				successResponcObj = gson.fromJson(decrypted_response.toString(),JsonResponceSuccess.class);				
					Date respDate = sdf2.parse(JavaCodeToJson.convertDateFormat(successResponcObj.getAckDt()));

				String csvDataLine[] = {""+LocalDateTime.now(),invoiceIdList.get(i), successResponcObj.getIrn(), successResponcObj.getAckNo(), successResponcObj.getAckDt(), successResponcObj.getStatus(),successResponcObj.getSignedQRCode(), successResponcObj.getSignedInvoice()};
				writer.writeNext(csvDataLine); //Writing data to the CSV file
				writer.flush(); //Flushing data from writer to file
			*/

			/**	java.sql.Date sqlResDate = new java.sql.Date(respDate.getTime());
				//Inserting data using SQL query 
				String sqlUpdtQuery = "UPDATE GST_TAX_INVOICE SET GENERATED_IRRN=?, STATUS=? , IRN_PROCESS_DATE = ? ,RES_STATUS=?,RES_ACKNO=?,RES_ACKDT=?,RES_SIGNEDINVOICE=?,RUN_FROM=?,IRN_QR_CODE=? ,ERROR_DES=? WHERE TAX_INV_ID=?";
				System.out.println("IRN generating for TaxInvoiceID::::"+invoiceIdList.get(i));
				pStmt= con.prepareStatement(sqlUpdtQuery);
				con.setAutoCommit(false);
				pStmt.setString(colIndex++,successResponcObj.getIrn());
				pStmt.setString(colIndex++,"Success");
				pStmt.setDate(colIndex++,(java.sql.Date) sqlDate);								
				pStmt.setString(colIndex++,successResponcObj.getStatus());
				pStmt.setString(colIndex++,successResponcObj.getAckNo());
				pStmt.setDate(colIndex++,sqlResDate);							
				pStmt.setString(colIndex++,successResponcObj.getSignedInvoice());
				pStmt.setString(colIndex++,"JOB");
				pStmt.setString(colIndex++,successResponcObj.getSignedQRCode());
				pStmt.setString(colIndex++,"");
				pStmt.setString(colIndex++,invoiceIdList.get(i));
				pStmt.addBatch();   */
			//	System.out.println("Success IRN Entry add in Batch:::"+colIndex);								
			}
		
	private static ApiCgsResponse getFinalDataString(String apiResonseVal, String jsonApiType) throws JSONException {
		 ApiCgsResponse  apiCgsResponse = new ApiCgsResponse();
		 JSONObject json = new JSONObject(apiResonseVal);
		 if(jsonApiType.equals("AUTHO")) {
			 if((json.getString("status").equals("00")) && (json.getJSONObject("data") !=null || !json.getJSONObject("data").equals(""))) {
				JSONObject data = json.getJSONObject("data");		    
				apiCgsResponse.setMsgtime(json.getString("msgtime"));
				apiCgsResponse.setMsgid(json.getString("msgid"));
				apiCgsResponse.setChannelName(json.getString("channelName"));
				apiCgsResponse.setStatus(json.getString("status"));		    
				apiCgsResponse.setToken(data.getString("token"));		   
				System.out.println("tokenVal::"+ apiCgsResponse.getToken());
		   }else if(!json.getString("status").equals("00")) {  // Failure response
			  if(json.has("msgtime")) {
				   apiCgsResponse.setMsgtime(json.getString("msgtime"));
			   }
			   if(json.has("msgid")) {
				   apiCgsResponse.setMsgid(json.getString("msgid"));
			   }
			   apiCgsResponse.setChannelName(json.getString("channelName"));
			   apiCgsResponse.setStatus(json.getString("status"));	
			   apiCgsResponse.setErrorMsg(json.getString("errorMsg"));		    
		     }
		 }else if(jsonApiType.equals("REQ_DATA")) {
			      JSONObject data_d = null; 
			      if(json.has("data")) {			    
			       data_d = json.getJSONObject("data");
			      }
			    if(json.has("data")) {
			    	 apiCgsResponse.setMsgrrn(json.getString("msgrrn"));
					 apiCgsResponse.setMsgtime(json.getString("msgtime"));
					 apiCgsResponse.setMsgid(json.getString("msgid"));
					 apiCgsResponse.setChannelName(json.getString("channelName"));			    
					 apiCgsResponse.setStatus(data_d.getString("status"));
					 apiCgsResponse.setErrorMsg(data_d.getString("errorMsg")); 			    				    	
				  }else if(!json.getString("status").equals("00"))  // Failure response
				   {
					        apiCgsResponse.setMsgid(json.getString("msgid"));
						    apiCgsResponse.setChannelName(json.getString("channelName"));
						    if(json.has("status")) {
						    apiCgsResponse.setStatus(json.getString("status"));
						    }
						    if(json.has("errorMsg")) {
						    apiCgsResponse.setErrorMsg(json.getString("errorMsg"));  
						    }		
				  }			 
		 }
		return apiCgsResponse;
	}
	
	private static String  sendingPostRequest(String postJsonData,String apiurl,String apiTypeRequest,String tokenStr) throws Exception {
            URL obj = new URL(apiurl);      	   		
      	// Create a trust manager that does not validate certificate chains
      		TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager(){
      		    public X509Certificate[] getAcceptedIssuers(){return null;}
      		    public void checkClientTrusted(X509Certificate[] certs, String authType){}
      		    public void checkServerTrusted(X509Certificate[] certs, String authType){}
      		}};
      		// Install the all-trusting trust manager
      		try {
      		    SSLContext sc = SSLContext.getInstance("TLS");
      		    sc.init(null, trustAllCerts, new SecureRandom());
      		    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
      		          		
      		} catch (Exception e) {
      		    ;
      		}
      		//=================================================================
      		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
      		// Setting basic post request
      		con.setRequestMethod("POST");
      		con.setDoOutput(true);  
      		con.setDoInput(true);
      	//	con.setRequestProperty("User-Agent", USER_AGENT);
      		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
      		con.setRequestProperty("Content-Type","application/json");
      		con.setRequestProperty("cache-control","no-cache");
      		if(apiTypeRequest.equals("AUTHO")) {	      		
	      		con.setRequestProperty ("Authorization", postJsonData); 
	      //	System.out.println("===============================GOING TO AUTHENTICATE==============================");	
      		}else if(apiTypeRequest.equals("REQ_DATA")) {    
      		//	System.out.println("===========================REQUESTING FOR FINAL STATUS PIFF ==============================");	
	      		con.setRequestProperty ("Authorization", "Bearer "+tokenStr);
				con.setRequestProperty("data",postJsonData);
      		}
      		con.setDoOutput(true);
      		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
      		wr.writeBytes(postJsonData);
      		wr.flush();
      		wr.close();
      		int responseCode = con.getResponseCode();
      		BufferedReader in = new BufferedReader( new InputStreamReader(con.getInputStream()));
      		String output;
      		StringBuffer response = new StringBuffer();

      		while ((output = in.readLine()) != null) {
      		response.append(output);
      		}
      		in.close();
      		return response.toString();
		}


	private static JsonDataForToken getJsonDataForApiParam(String requestType, String msgId, String dealerCode,
			String virtualAccNo, String amount, String amountGenDt) {
		//System.out.println("Calling getJsonDataForApiParam().......................... 7 ");
		JsonDataForToken tokenDataObj = new JsonDataForToken();
		PaymentRequest dataObj = new PaymentRequest();
		try {
			dataObj.setDealerCode(dealerCode);
			dataObj.setVirtualAccountNumber(virtualAccNo);
			dataObj.setAmount(amount);
			dataObj.setAmountGeneratedOn(amountGenDt);
		} catch (Exception e) {
			System.out.println("Exception::" + e.getMessage());
		}
		tokenDataObj.setRequestType(requestType);
		tokenDataObj.setMsgid(msgId);
		tokenDataObj.setData(dataObj);
		return tokenDataObj;
	}

	// 100 AUTH METHOD
	private static JsonAurthToken getJsonDataForJsonAurthToken(String username, String password) {
		System.out.println("Calling getJsonDataForApiParam().......................... 7 ");
		JsonAurthToken dataAuthObj = new JsonAurthToken();
		try {
			dataAuthObj.setUsername(username);
			dataAuthObj.setPassword(password);
		} catch (Exception e) {
			System.out.println("Exception::" + e.getMessage());
		}
		return dataAuthObj;
	}

	// AUTH METHOD
	private static JsonDataForAutho getJsonDataForAuthApi(String requestType, String msgid, String username,
			String password) {
		//System.out.println("Calling getJsonDataForApiParam().......................... 7 ");
		JsonDataForAutho jsonDataForAutho = new JsonDataForAutho();
		JsonAurthToken dataAuthObj = new JsonAurthToken();
		try {
			dataAuthObj.setUsername(username);
			dataAuthObj.setPassword(password);
		} catch (Exception e) {
			System.out.println("Exception::" + e.getMessage());
		}
		jsonDataForAutho.setRequestType(requestType);
		jsonDataForAutho.setMsgId(msgid);
		jsonDataForAutho.setData(dataAuthObj);
		return jsonDataForAutho;
	}

	// Encrypted record to json2
	private static FinalEncryptedJson getJsonDataEncryptParam(String encryptAecKey, String msgId) {
		//System.out.println("Calling getJsonDataEncryptParam().......................... 6 ");
		FinalEncryptedJson tokenDataObj = new FinalEncryptedJson();
		tokenDataObj.setReqdata(encryptAecKey);
		tokenDataObj.setMsgid(msgId);
		return tokenDataObj;
	}

	private static String updatePifPayStatus() {
		// TODO Auto-generated method stub
		String pif = "";
		System.out.println("Payment Status updated successfully..");
		return pif;
	}

}
