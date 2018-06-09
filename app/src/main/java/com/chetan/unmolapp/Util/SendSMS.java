package com.chetan.unmolapp.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class SendSMS {
	public static void main(String[] args) {

		// Replace with your username
		String user = "arunmaharana123";

		// Replace with your API KEY (We have sent API KEY on activation email,
		// also available on panel)
		String apikey = "NOZugnbkTKKVcW2Q1YJd";

		// Replace with the destination mobile Number to which you want to send
		// sms
		String mobile = "8895848226";

		// Replace if you have your own Sender ID, else donot change
		String senderid = "MYTEXT";

		// Replace with your Message content
		String message = "Test Demo";

		// For Plain Text, use "txt" ; for Unicode symbols or regional Languages
		// like hindi/tamil/kannada use "uni"
		String type = "txt";

		// Prepare Url
		URLConnection myURLConnection = null;
		URL myURL = null;
		BufferedReader reader = null;

		// encoding message
		String encoded_message = URLEncoder.encode(message);

		// Send SMS API
		String mainUrl = "http://smshorizon.co.in/api/sendsms.php?";

		// Prepare parameter string
		StringBuilder sbPostData = new StringBuilder(mainUrl);
		sbPostData.append("user=" + user);
		sbPostData.append("&apikey=" + apikey);
		sbPostData.append("&message=" + encoded_message);
		sbPostData.append("&mobile=" + mobile);
		sbPostData.append("&senderid=" + senderid);
		sbPostData.append("&type=" + type);

		// final string
		mainUrl = sbPostData.toString();
		try {
			// prepare connection
			myURL = new URL(mainUrl);
			myURLConnection = myURL.openConnection();
			myURLConnection.connect();
			reader = new BufferedReader(new InputStreamReader(
					myURLConnection.getInputStream()));
			// reading response
			String response;
			while ((response = reader.readLine()) != null)
				// print response
				System.out.println(response);
			if ("82303634".equals(response)) {
				System.out.println("Successfully sent");
			}

			// finally close connection
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void sendSMS(String mobilenumber, String text) {

		URL url;
		try {
			// get URL content
			String message = "Hi,Your task Inspection is successfully completed. You can check status here http://198.12.95.14:8080/Realtor/";
			message = message.replaceAll(" ", "+");
			message = text.replaceAll(" ", "+");
			String a = "https://www.smsgatewayhub.com/api/mt/SendSMS?APIKey=HpL6SHL6skOFvJ5SAPlpOg&senderid=TESTIN&channel=2&DCS=8&flashsms=0&number="
					+ mobilenumber + "&text=" + message + "&route=11";
			url = new URL(a);
			URLConnection conn = url.openConnection();

			// open the stream and put it into BufferedReader
			BufferedReader br = new BufferedReader(new InputStreamReader(
					conn.getInputStream()));

			String inputLine;
			while ((inputLine = br.readLine()) != null) {
				System.out.println(inputLine);
			}
			br.close();

			System.out.println("Done");

		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
