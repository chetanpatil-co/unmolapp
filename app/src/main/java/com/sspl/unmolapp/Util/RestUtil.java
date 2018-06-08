package com.sspl.unmolapp.Util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.util.Date;

public class RestUtil {

	private static String URL1 = "http://192.168.2.150:8085/loyaltysystem/rest/testWebService/";

	private static String result;

	public static String Call(String urlService, Object obj) {

		urlService = URL1 + urlService;
		DefaultHttpClient httpClient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(urlService);
		GsonBuilder gsonBuilder = new GsonBuilder();
		gsonBuilder.registerTypeAdapter(Date.class, new CustomDateSerializer());
		gsonBuilder.registerTypeAdapter(Date.class,
				new CustomDateDeserializer());
		try {
			Gson gson = gsonBuilder.create();
			String json = gson.toJson(obj);
			StringEntity se = new StringEntity(json);
			httpPost.setEntity(se);
 			httpPost.setHeader("Accept", "application/json");
			httpPost.setHeader("Content-type", "application/json");
 			HttpResponse httpResponse = httpClient.execute(httpPost);
			result = EntityUtils.toString(httpResponse.getEntity());
			
			httpClient.getConnectionManager().shutdown();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception");
		}
		return result;
	}
	public static Object downCast(String resultData, TypeToken typeToken) {
		try {
			GsonBuilder gsonBuilder = new GsonBuilder();
			gsonBuilder.registerTypeAdapter(Date.class,
					new CustomDateSerializer());
			gsonBuilder.registerTypeAdapter(Date.class,
					new CustomDateDeserializer());
			Gson gson = gsonBuilder.create();
			return gson.fromJson(resultData, typeToken.getType());
		} catch (Exception ee) {
			ee.printStackTrace();
			System.out.println("Error While DownCasting : " + ee);
		  }
		return null;
	}
}
