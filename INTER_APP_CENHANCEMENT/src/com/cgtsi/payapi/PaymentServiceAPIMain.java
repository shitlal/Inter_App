package com.cgtsi.payapi;
/**
 * Description : PaymentServiceAPIMain.java
 * Created Date: 06-10-2021
 * Created By  : Deepak Kr Ranjan
 * 
 **/
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Random;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONException;
import org.json.JSONObject;

import com.cgtsi.util.PropertyLoader;
import com.cgtsi.util.ReadResourcePro;
import com.google.gson.Gson;
import com.infrasoft.kiya.security.EncryptionDecryptionImpl;

public class PaymentServiceAPIMain {

	//public static void main(String[] args) throws Exception {
	public static void hitCorpApi(HttpServletRequest request) throws Exception {	
		String extensionSts="";
		Properties prop = null;				 
		try {
				prop = ReadResourcePro.getProperties(request.getRealPath(PropertyLoader.PROPERTY_CONFIG_DIRECTORY+"/classes/apiresource.properties"));
				String msgKey = prop.getProperty("msgKey");
				String encryKey = prop.getProperty("encryKey");
				int msgRanMaxLength = Integer.valueOf(prop.getProperty("msgRanMaxLength"));
				String username =prop.getProperty("username");
				String password = prop.getProperty("password");
				String apiAurthURL1 =prop.getProperty("apiAurthURL1");
				String dataRequestURL2 = prop.getProperty("dataRequestURL2");
				String requestType =prop.getProperty("requestType");
				sendReqResponse(requestType, msgKey, encryKey, msgRanMaxLength, username, password, apiAurthURL1,
						dataRequestURL2);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		
	}

	public static void sendReqResponse(String requestType, String msgKey, String encryKey, int msgRanMaxLength,
			String username, String password, String apiAurthURL1, String dataRequestURL2) throws Exception {
		
		Gson dGson = new Gson();
		String msgid = generateDRandomString(msgRanMaxLength, msgKey);
		StringBuilder revMsgKey = new StringBuilder().append(msgKey);
		String msgid2 = generateDRandomString(msgRanMaxLength, revMsgKey.reverse().toString());
		System.out.println("msgid:::::" + revMsgKey + " \n msgid2::" + msgid2);		
		EncryptionDecryptionImpl crypto = new EncryptionDecryptionImpl();
		JsonDataForAutho getDataAuth = null;
		FinalEncryptedJson getDataAurthoFinal = null;
		ApiCgsResponse aurthoTokenResponseObj = null, fianlDataResponseObj = null;
		FinalEncryptedJson getData2 = null;
		JsonDataForToken getData = null;

		CgtPaymentDao cgtPaymentDao = new CgtPaymentDao();
		
		ArrayList<String> payList=new ArrayList<String>();
		//payList.add("RP-00093-17-07-2020");
		ArrayList<PaymentRequest> payReqList = cgtPaymentDao.requestVanListFromDb(payList); // get record from db pay_id for api
		
	//	System.out.println("payReqList>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + payReqList.size());
		if (payReqList.size() > 0) {
			for (PaymentRequest paymentRequest : payReqList) {
				getDataAuth = getJsonDataForAuthApi(requestType, msgid, username, password);
				// Normal JSON Preparation for AUth.
				String jsonAuthoString = dGson.toJson(getDataAuth);
				System.out.println("Authentication JSON String::::  " + jsonAuthoString);
				// Request Before Encryption
				String encJsonAuthoEncry = crypto.encryptMessage(jsonAuthoString, encryKey);

				getDataAurthoFinal = getJsonDataEncryptParam(encJsonAuthoEncry, msgid);
				String jsonEncryptedFinalString = dGson.toJson(getDataAurthoFinal);
				System.out.println("\n Authentication Final json::" + jsonEncryptedFinalString);
				System.out.println("\n  Authentication URL1 :: " + apiAurthURL1);
				String jsonEncryResponse = sendingPostRequest(jsonEncryptedFinalString, apiAurthURL1, "AUTHO", "");
				String decAuthString = crypto.decryptMessage(jsonEncryResponse, encryKey);
				aurthoTokenResponseObj = getFinalDataString(decAuthString, "AUTHO");
				System.out.println("\n URL1 Authentication Url response if Token : " + aurthoTokenResponseObj.getToken()
						+ "   || Response message::" + aurthoTokenResponseObj.getErrorMsg());
				// ============== Second Json ==============================================
				getData = getJsonDataForApiParam(requestType, msgid2, paymentRequest.getDealerCode(),
						paymentRequest.getVirtualAccountNumber(), paymentRequest.getAmount(),
						paymentRequest.getAmountGeneratedOn());// Get JsonData For Token Generation
				/* Create Gson object */
				String jsonString = dGson.toJson(getData);
				System.out.println("\n Record Json >>>:  " + jsonString);
				String encMes = crypto.encryptMessage(jsonString, encryKey);
				getData2 = getJsonDataEncryptParam(encMes, msgid2);

				String jsonEncryptedString = dGson.toJson(getData2);
				System.out.println("\n  Encrypted Record Json:  " + jsonEncryptedString);
				System.out.println("\n Final URL2:" + dataRequestURL2);
				String jsonDataEncryFinalResponse = sendingPostRequest(jsonEncryptedString, dataRequestURL2, "REQ_DATA",
						aurthoTokenResponseObj.getToken());
				// System.out.println("Final URL2 response >>>>>: "+jsonDataEncryFinalResponse);
				String jsonDecryFinalResponse = crypto.decryptMessage(jsonDataEncryFinalResponse, encryKey);
				System.out.println("\n Final URL2 response:  " + jsonDecryFinalResponse);
				fianlDataResponseObj = getFinalDataString(jsonDecryFinalResponse, "REQ_DATA");
				System.out.println("\n URL2 result Message: " + fianlDataResponseObj.getErrorMsg());
				fianlDataResponseObj.setToken(aurthoTokenResponseObj.getToken());   // Token added
				if (fianlDataResponseObj.getErrorMsg().equals("Success")
						&& (fianlDataResponseObj.getStatus().equals("00")
								|| fianlDataResponseObj.getStatus().equals("01"))) {
					cgtPaymentDao.updatePaidStatuswithVan(fianlDataResponseObj,paymentRequest.getVirtualAccountNumber(),"USER");
				} else {
					cgtPaymentDao.updatePaidStatuswithVan(fianlDataResponseObj, paymentRequest.getVirtualAccountNumber(),"USER");
				}
			} // For loop
		}
	}

	private static ApiCgsResponse getFinalDataString(String apiResonseVal, String jsonApiType) throws Exception {
		ApiCgsResponse apiCgsResponse = new ApiCgsResponse();
		JSONObject json = new JSONObject(apiResonseVal);
		if (jsonApiType.equals("AUTHO")) {
			if ((json.getString("status").equals("00"))
					&& (json.getJSONObject("data") != null || !json.getJSONObject("data").equals(""))) {
				JSONObject data = json.getJSONObject("data");
				apiCgsResponse.setMsgtime(json.getString("msgtime"));
				apiCgsResponse.setMsgid(json.getString("msgid"));
				apiCgsResponse.setChannelName(json.getString("channelName"));
				apiCgsResponse.setStatus(json.getString("status"));
				apiCgsResponse.setToken(data.getString("token"));
				System.out.println("tokenVal::" + apiCgsResponse.getToken());
			} else if (!json.getString("status").equals("00")) { // Failure response
				if (json.has("msgtime")) {
					apiCgsResponse.setMsgtime(json.getString("msgtime"));
				}
				if (json.has("msgid")) {
					apiCgsResponse.setMsgid(json.getString("msgid"));
				}
				apiCgsResponse.setChannelName(json.getString("channelName"));
				apiCgsResponse.setStatus(json.getString("status"));
				apiCgsResponse.setErrorMsg(json.getString("errorMsg"));
			}
		} else if (jsonApiType.equals("REQ_DATA")) {
			JSONObject data_d = null;
			if (json.has("data")) {
				data_d = json.getJSONObject("data");
			}
			if (json.has("data")) {
				apiCgsResponse.setMsgrrn(json.getString("msgrrn"));
				apiCgsResponse.setMsgtime(json.getString("msgtime"));
				apiCgsResponse.setMsgid(json.getString("msgid"));
				apiCgsResponse.setChannelName(json.getString("channelName"));
				apiCgsResponse.setStatus(data_d.getString("status"));
				apiCgsResponse.setErrorMsg(data_d.getString("errorMsg"));
			} else if (!json.getString("status").equals("00")) // Failure response
			{
				apiCgsResponse.setMsgid(json.getString("msgid"));
				apiCgsResponse.setChannelName(json.getString("channelName"));
				if (json.has("status")) {
					apiCgsResponse.setStatus(json.getString("status"));
				}
				if (json.has("errorMsg")) {
					apiCgsResponse.setErrorMsg(json.getString("errorMsg"));
				}
			}
		}
		return apiCgsResponse;
	}

	private static String sendingPostRequest(String postJsonData, String apiurl, String apiTypeRequest, String tokenStr)
			throws Exception {
		URL obj = new URL(apiurl);
		// Create a trust manager that does not validate certificate chains
		/*TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
			public X509Certificate[] getAcceptedIssuers() {
				return null;
			}

			public void checkClientTrusted(X509Certificate[] certs, String authType) {
			}

			public void checkServerTrusted(X509Certificate[] certs, String authType) {
			}
		} };
		// Install the all-trusting trust manager
		try {
			SSLContext sc = SSLContext.getInstance("TLS");
			sc.init(null, trustAllCerts, new SecureRandom());
			HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

		} catch (Exception e) {
			;
		}*/
		// =================================================================
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("POST");
		con.setDoOutput(true);
		con.setDoInput(true);
		con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
		con.setRequestProperty("Content-Type", "application/json");
		con.setRequestProperty("cache-control", "no-cache");
		con.setRequestProperty("https.protocols", "TLSv1,TLSv1.1,TLSv1.2");
		if (apiTypeRequest.equals("AUTHO")) {
			con.setRequestProperty("Authorization", postJsonData);
		} else if (apiTypeRequest.equals("REQ_DATA")) {
			con.setRequestProperty("Authorization", "Bearer " + tokenStr);
			con.setRequestProperty("data", postJsonData);
		}
		con.setDoOutput(true);
		DataOutputStream wr = new DataOutputStream(con.getOutputStream());
		wr.writeBytes(postJsonData);
		wr.flush();
		wr.close();
		int responseCode = con.getResponseCode();
		System.out.println("RESPONSECODE:::"+responseCode);
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
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
/**	private static JsonAurthToken getJsonDataForJsonAurthToken(String username, String password) {
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
*/
	// AUTH METHOD
	private static JsonDataForAutho getJsonDataForAuthApi(String requestType, String msgid, String username,
			String password) {
		// System.out.println("Calling
		// getJsonDataForApiParam().......................... 7 ");
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
		// System.out.println("Calling
		// getJsonDataEncryptParam().......................... 6 ");
		FinalEncryptedJson tokenDataObj = new FinalEncryptedJson();
		tokenDataObj.setReqdata(encryptAecKey);
		tokenDataObj.setMsgid(msgId);
		return tokenDataObj;
	}

	// MsgId 1-2 Genrated
	public static String generateDRandomString(int msgRanMaxLength, String msgKey) {
		StringBuffer randStr = new StringBuffer();
		for (int i = 0; i < msgRanMaxLength; i++) {
			int number = getDRandomNumber(msgKey);
			char ch = msgKey.charAt(number);
			randStr.append(ch);
		}
		return randStr.toString();
	}

	private static int getDRandomNumber(String msgKey) {
		int randomInt = 0;
		Random randomGenerator = new Random();
		randomInt = randomGenerator.nextInt(msgKey.length());
		if (randomInt - 1 == -1) {
			return randomInt;
		} else {
			return randomInt - 1;
		}
	}

}
