package com.meganet.pan.util;

import java.io.*;
import java.net.*;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//import org.springframework.beans.factory.annotation.Autowired;

//import com.reliance.common.util.PropsValues;

import java.util.*;

@Component
public class PanVerificationUtil {


	@Autowired
	PKCS7Signer signer;

	public static class DummyTrustManager implements X509TrustManager {

		public DummyTrustManager() {
		}

		public boolean isClientTrusted(X509Certificate cert[]) {
			return true;
		}

		public boolean isServerTrusted(X509Certificate cert[]) {
			return true;
		}

		public X509Certificate[] getAcceptedIssuers() {
			return new X509Certificate[0];
		}

		public void checkClientTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException {

		}

		public void checkServerTrusted(X509Certificate[] arg0, String arg1)
				throws CertificateException {

		}
	}

	public static class DummyHostnameVerifier implements HostnameVerifier {

		public boolean verify(String urlHostname, String certHostname) {
			return true;
		}

		public boolean verify(String arg0, SSLSession arg1) {
			return true;
		}
	}

	public static void main(String[] args) {

		try {

			Date startTime = null;
			Calendar c1 = Calendar.getInstance();
			startTime = c1.getTime();

			Date connectionStartTime = null;
			String logMsg = "\n-";
			BufferedWriter out = null;
			FileWriter fstream = null;
			Calendar c = Calendar.getInstance();
			long nonce = c.getTimeInMillis();

			// "https://59.163.46.2/TIN/PanInquiryBackEnd"59.163.223.205
			String urlOfNsdl = "https://59.163.46.2/TIN/PanInquiryBackEnd";
			String signature = null;
//			String data = "V0140401^AMXPP6546K^AWXCC6589K^AKLPP6421L^ACQHP6546K^AZXPM8531O";
			String data = "V0140401^ESZPS2465C";
//			String data = "V0140401^AMXPP6546K";

			// Properties prop = new Properties();
			try {
				// prop.load(new FileInputStream("params.properties"));

				// data=prop.getProperty("data");
//				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
//				signature = "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";
//signature=new String(pkcs7gen.signatureGeneration(data));
System.out.println(signature);
			} catch (Exception e) {
				logMsg += "::Exception: " + e.getMessage()
						+ " ::Program Start Time:" + startTime + "::nonce= "
						+ nonce;
				System.out.println(logMsg);
			}

			try {
				fstream = new FileWriter("API_PAN_verification.logs", true);
				out = new BufferedWriter(fstream);
			} catch (Exception e) {
				logMsg += "::Exception: " + e.getMessage()
						+ " ::Program Start Time:" + startTime + "::nonce= "
						+ nonce;
				out.write(logMsg);
				out.close();
			}

			SSLContext sslcontext = null;
			try {
				sslcontext = SSLContext.getInstance("SSL");

				sslcontext.init(new KeyManager[0],
						new TrustManager[] { new DummyTrustManager() },
						new SecureRandom());
			} catch (NoSuchAlgorithmException e) {
				logMsg += "::Exception: " + e.getMessage()
						+ " ::Program Start Time:" + startTime + "::nonce= "
						+ nonce;
				e.printStackTrace(System.err);
				out.write(logMsg);
				out.close();
			} catch (KeyManagementException e) {
				logMsg += "::Exception: " + e.getMessage()
						+ " ::Program Start Time:" + startTime + "::nonce= "
						+ nonce;
				e.printStackTrace(System.err);
				out.write(logMsg);
				out.close();
			}

			SSLSocketFactory factory = sslcontext.getSocketFactory();

			String urlParameters = "data=";
			try {
				urlParameters = urlParameters
						+ URLEncoder.encode(data, "UTF-8") + "&signature="
						+ URLEncoder.encode(signature, "UTF-8");
			} catch (Exception e) {
				logMsg += "::Exception: " + e.getMessage()
						+ " ::Program Start Time:" + startTime + "::nonce= "
						+ nonce;
				e.printStackTrace();
				out.write(logMsg);
				out.close();
			}

			try {
				URL url;
				HttpsURLConnection connection;
				InputStream is = null;

				String ip = urlOfNsdl;
				url = new URL(ip);
				System.out.println("URL " + ip);
				connection = (HttpsURLConnection) url.openConnection();
				connection.setRequestMethod("POST");
				connection.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded");
				connection.setRequestProperty("Content-Length",
						"" + Integer.toString(urlParameters.getBytes().length));
				connection.setRequestProperty("Content-Language", "en-US");
				connection.setUseCaches(false);
				connection.setDoInput(true);
				connection.setDoOutput(true);
				connection.setSSLSocketFactory(factory);
				connection.setHostnameVerifier(new DummyHostnameVerifier());
				OutputStream os = connection.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os);
				osw.write(urlParameters);
				osw.flush();
				connectionStartTime = new Date();
				logMsg += "::Request Sent At: " + connectionStartTime;
				logMsg += "::Request Data: " + data;
				osw.close();
				is = connection.getInputStream();
				BufferedReader in = new BufferedReader(
						new InputStreamReader(is));
				String line = null;
				line = in.readLine();

				System.out.println("Output: " + line);
				is.close();
				in.close();
			} catch (ConnectException e) {
				logMsg += "::Exception: " + e.getMessage()
						+ "::Program Start Time:" + startTime + "::nonce= "
						+ nonce;
				try {
					out.write(logMsg);
					out.close();
				} catch (IOException ex) {
					e.printStackTrace();
				}
			} catch (Exception e) {
				logMsg += "::Exception: " + e.getMessage()
						+ "::Program Start Time:" + startTime + "::nonce= "
						+ nonce;
				try {
					out.write(logMsg);
					out.close();
				} catch (IOException ex) {
					e.printStackTrace();
				}
				e.printStackTrace();
			}

			out.write(logMsg);
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String panWebService(String filePath,String panUserId,String panUrl,String key,String...panData){
		StringBuilder panReqData = new StringBuilder();
//		String panUserId = "V0143301";
		//pan userId for RHF-V0143301  &&  RCF-V0140401
		panReqData.append(panUserId);
		String result="";
		for(String pan:panData){
			panReqData.append("^");
			panReqData.append(pan);
		}
		try {

			Date startTime = null;
			Calendar c1 = Calendar.getInstance();
			startTime = c1.getTime();

			Date connectionStartTime = null;
			String logMsg = "\n-";
			BufferedWriter out = null;
			FileWriter fstream = null;
			Calendar c = Calendar.getInstance();
			long nonce = c.getTimeInMillis();

			// "https://59.163.46.2/TIN/PanInquiryBackEnd"
			String urlOfNsdl = "https://59.163.46.2/TIN/PanInquiryBackEnd";
			String signature = null;
//			String data = "V0140401^AMXPP6546K^AWXCC6589K^AKLPP6421L^ACQHP6546K^AZXPM8531O";
			String data = panReqData.toString();

			// Properties prop = new Properties();
			try {
				// prop.load(new FileInputStream("params.properties"));

				// data=prop.getProperty("data");
//				signature = "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";
				//signature=new String(pkcs7gen.signatureGeneration(data,filePath,key));
				signature=new String(signer.signedData(data));
//				System.out.println("signature: "+signature);

			} catch (Exception e) {
				logMsg += "::Exception: " + e.getMessage()
						+ " ::Program Start Time:" + startTime + "::nonce= "
						+ nonce;
			}

			try {
				fstream = new FileWriter("API_PAN_verification.logs", true);
				out = new BufferedWriter(fstream);
			} catch (Exception e) {
				logMsg += "::Exception: " + e.getMessage()
						+ " ::Program Start Time:" + startTime + "::nonce= "
						+ nonce;
//				out.write(logMsg);
//				out.close();
			}

			SSLContext sslcontext = null;
			try {
				sslcontext = SSLContext.getInstance("SSL");

				sslcontext.init(new KeyManager[0],
						new TrustManager[] { new DummyTrustManager() },
						new SecureRandom());
			} catch (NoSuchAlgorithmException e) {
				logMsg += "::Exception: " + e.getMessage()
						+ " ::Program Start Time:" + startTime + "::nonce= "
						+ nonce;
				e.printStackTrace(System.err);
//				out.write(logMsg);
//				out.close();
			} catch (KeyManagementException e) {
				logMsg += "::Exception: " + e.getMessage()
						+ " ::Program Start Time:" + startTime + "::nonce= "
						+ nonce;
				e.printStackTrace(System.err);
//				out.write(logMsg);
//				out.close();
			}

			SSLSocketFactory factory = sslcontext.getSocketFactory();

			String urlParameters = "data=";
			try {
				urlParameters = urlParameters
						+ URLEncoder.encode(data, "UTF-8") + "&signature="
						+ URLEncoder.encode(signature, "UTF-8");
			} catch (Exception e) {
				logMsg += "::Exception: " + e.getMessage()
						+ " ::Program Start Time:" + startTime + "::nonce= "
						+ nonce;
				e.printStackTrace();
//				out.write(logMsg);
//				out.close();
			}

			try {
				URL url;
				HttpsURLConnection connection;
				InputStream is = null;

				String ip = urlOfNsdl;
				url = new URL(ip);
//				System.out.println("URL " + ip);
				connection = (HttpsURLConnection) url.openConnection();
				connection.setRequestMethod("POST");
				connection.setRequestProperty("Content-Type",
						"application/x-www-form-urlencoded");
				connection.setRequestProperty("Content-Length",
						"" + Integer.toString(urlParameters.getBytes().length));
				connection.setRequestProperty("Content-Language", "en-US");
				connection.setUseCaches(false);
				connection.setDoInput(true);
				connection.setDoOutput(true);
				connection.setSSLSocketFactory(factory);
				connection.setHostnameVerifier(new DummyHostnameVerifier());
				connection.setConnectTimeout(300000);
				OutputStream os = connection.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os);
				osw.write(urlParameters);
				osw.flush();
				connectionStartTime = new Date();
				logMsg += "::Request Sent At: " + connectionStartTime;
				logMsg += "::Request Data: " + data;
				osw.close();
				is = connection.getInputStream();
				BufferedReader in = new BufferedReader(
						new InputStreamReader(is));
				String line = null;
				line = in.readLine();
				result = line;

//				System.out.println("Output: " + line);
				is.close();
				in.close();
			} catch (ConnectException e) {
				logMsg += "::Exception: " + e.getMessage()
						+ "::Program Start Time:" + startTime + "::nonce= "
						+ nonce;
//				try {
//					out.write(logMsg);
//					out.close();
//				} catch (IOException ex) {
					e.printStackTrace();
//				}
			} catch (Exception e) {
				logMsg += "::Exception: " + e.getMessage()
						+ "::Program Start Time:" + startTime + "::nonce= "
						+ nonce;
//				try {
//					out.write(logMsg);
//					out.close();
//				} catch (IOException ex) {
//					e.printStackTrace();
//				}
				e.printStackTrace();
			}
//			System.out.println("Before END of Method Excution");
			out.write(logMsg);
			out.close();
//			System.out.println("ENd of Method Excution");
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}
}
