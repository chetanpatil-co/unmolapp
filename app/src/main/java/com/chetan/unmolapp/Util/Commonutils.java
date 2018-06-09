package com.chetan.unmolapp.Util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;

@SuppressLint("NewApi")
public class Commonutils {

    public static boolean isInternetAvailable(Context c) {
        boolean haveConnectedWifi = false;
        boolean haveConnectedMobile = false;

        ConnectivityManager cm = (ConnectivityManager) c
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
            if (ni.getTypeName().equalsIgnoreCase("MOBILE"))
                if (ni.isConnected())
                    haveConnectedMobile = true;
        }
        if (haveConnectedWifi || haveConnectedMobile) {
            return true;
        } else {

            return false;
        }
    }

    private static boolean isWSDLAvailable() {
        HttpURLConnection c = null;

        final String wsdlAddr = "http://www.google.com";

        try {
            URL u = new URL(wsdlAddr);
            c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("HEAD");
            c.getInputStream();
            c.setConnectTimeout(5000);
            return c.getResponseCode() == 200;
        } catch (Exception e) {
            return false;
        } finally {
            if (c != null)
                c.disconnect();
        }

    }

    public static boolean isWIFIAvailable(Context c) {
        boolean haveConnectedWifi = false;
        ConnectivityManager cm = (ConnectivityManager) c
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo[] netInfo = cm.getAllNetworkInfo();
        for (NetworkInfo ni : netInfo) {
            if (ni.getTypeName().equalsIgnoreCase("WIFI"))
                if (ni.isConnected())
                    haveConnectedWifi = true;
        }
        return haveConnectedWifi;
    }


    public static void backupdata(String usernamess) {
        // TODO Auto-generated method stub
        try {
            File sd = new File(Environment.getExternalStorageDirectory()
                    + "/KKDDB");
            if (!sd.exists()) {
                sd.mkdirs();
            }
            if (new File(Environment.getExternalStorageDirectory()
                    + "/KKDDB/MY_DB_" + usernamess + ".db").exists()) {

                File sds = new File(Environment.getExternalStorageDirectory()
                        + "/KKDDB/MY_DB_" + usernamess + ".db");
                sds.delete();
            }
            File data = Environment.getDataDirectory();

            if (sd.canWrite()) {
                String currentDBPath = "//data//com.example.kkd2//databases//KKD2_DB.db";

                String backupDBPath = "MY_DB_" + usernamess + ".db";
                File currentDB = new File(data, currentDBPath);
                File backupDB = new File(sd, backupDBPath);

                if (currentDB.exists()) {
                    FileChannel src = new FileInputStream(currentDB)
                            .getChannel();
                    FileChannel dst = new FileOutputStream(backupDB)
                            .getChannel();
                    dst.transferFrom(src, 0, src.size());
                    src.close();
                    dst.close();
                }
            } else {
                System.out.println("No");

            }

        } catch (Exception e) {

        }

    }

    public static InputStream OpenHttpConnection1(String urlString)
            throws IOException {
        InputStream in = null;
        int response = -1;

        URL url = new URL(urlString);
        URLConnection conn = url.openConnection();

        if (!(conn instanceof HttpURLConnection))
            throw new IOException("Not an HTTP connection");

        try {
            HttpURLConnection httpConn = (HttpURLConnection) conn;
            httpConn.setAllowUserInteraction(false);
            httpConn.setInstanceFollowRedirects(true);
            httpConn.setRequestMethod("GET");
            httpConn.connect();

            response = httpConn.getResponseCode();
            if (response == HttpURLConnection.HTTP_OK) {
                in = httpConn.getInputStream();
            }
        } catch (Exception ex) {
            throw new IOException("Error connecting");
        }
        return in;
    }

    /* Method For Webservice is connected or not */
    public static boolean isConnected(String url) {
        try {
            HttpGet request = new HttpGet(url);
            DefaultHttpClient httpClient = new DefaultHttpClient();
            httpClient.setKeepAliveStrategy(new ConnectionKeepAliveStrategy() {
                @Override
                public long getKeepAliveDuration(HttpResponse arg0,
                                                 HttpContext arg1) {
                    return 0;
                }
            });
            HttpResponse response = httpClient.execute(request);
            return response.getStatusLine().getStatusCode() == 200;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }


}
