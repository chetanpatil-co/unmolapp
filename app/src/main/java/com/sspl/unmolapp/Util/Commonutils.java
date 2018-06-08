package com.sspl.unmolapp.Util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.app.admin.DevicePolicyManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.AssetFileDescriptor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.ExifInterface;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

import com.sspl.unmolapp.R;
import com.stericson.RootTools.RootTools;
import com.stericson.RootTools.execution.CommandCapture;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.HttpContext;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

@SuppressLint("NewApi")
public class Commonutils {
    public static String URL = "http://192.168.2.150:8085";
    public static String WS = "http://192.168.2.150:8085/loyaltysystem/rest/testWebService/testService";
    public static String mlatit = new String();
    public static String Strval = new String();
    public static String videopath = new String();
    public static int videocount = 0;
    public static boolean chatflag = true;
    public static boolean optionflag = true;
    public static String mlongi = new String();
    static MediaPlayer m = new MediaPlayer();
    public static String videopathserver = new String();
    public static String setcommontext = "";
    public static DevicePolicyManager devicePolicyManager;
    public static boolean appActivity = false;
    public static boolean alarmflag = false;
    public static long isTime;
    public static String finalpath = null;
    public static String finalvideopath = null;
    public static int ID = 0;
    private static ProgressDialog progressDialog;

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

    public static Bitmap getBitmap(String url) {
        Bitmap bitmap = null;
        InputStream in = null;
        try {
            in = OpenHttpConnection(url);
            bitmap = BitmapFactory.decodeStream(in);

            File imagesFolder = new File(Environment
                    .getExternalStorageDirectory().toString(),
                    "/DIGITEMP/Images");

            if (imagesFolder.isDirectory()) {

            } else {
                imagesFolder.mkdirs();

            }

            String path = Environment.getExternalStorageDirectory().toString()
                    + "/DIGITEMP/Images";
            OutputStream fOut = null;
            String str[] = url.split("/");
            String imageName = str[str.length - 1];
            finalpath = path + "/" + imageName;
            File file = new File(path, imageName);
            try {
                fOut = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.flush();
                fOut.close();
            } catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            in.close();
        } catch (IOException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }
        return bitmap;
    }

    public static InputStream OpenHttpConnection(String urlString)
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

    public static int now() {
        int length = 4;
        String result = "";
        int random;
        while (true) {
            random = (int) ((Math.random() * (10)));
            if (result.length() == 0 && random == 0)
            {// when parsed this
                // insures that the
                // number doesn't start
                // with 0
                random += 1;
                result += random;
            }
            else if (!result.contains(Integer.toString(random)))
            {// if my
                // result
                // doesn't
                // contain
                // the new
                // generated
                // digit
                // then I
                // add it to
                // the
                // result
                result += Integer.toString(random);
            }
            if (result.length() >= length) {// when i reach the number of digits
                // desired i break out of the loop
                // and return the final result
                break;
            }
        }

        return Integer.parseInt(result);
    }

    public static SharedPreferences getsharedforget(Context ctx) {
        SharedPreferences qwe = ctx.getSharedPreferences("koutuk", 0);
        SharedPreferences.Editor qwer = qwe.edit();
        return qwe;
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

    public static void fullvolume(Context c, int vol) {
        AudioManager am = (AudioManager) c
                .getSystemService(Context.AUDIO_SERVICE);
        switch (vol) {
            case 100:

                am.setStreamVolume(AudioManager.STREAM_MUSIC, 100, 0);
                break;
            case 50:

                am.setStreamVolume(AudioManager.STREAM_MUSIC, 50, 0);
                break;
            case 0:

                am.setStreamVolume(AudioManager.STREAM_MUSIC, 20, 0);
                break;

        }

    }

    static public String distance(double lat1, double lon1, double lat2,
                                  double lon2) {
        double theta = lon1 - lon2;
        double dist = Math.sin(deg2rad(lat1)) * Math.sin(deg2rad(lat2))
                + Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2))
                * Math.cos(deg2rad(theta));
        dist = Math.acos(dist);
        dist = rad2deg(dist);
        dist = dist * 60 * 1.1515;
        double meterConversion = 1609.00;

        double aa = (dist * meterConversion) / 1000;
        return aa + "";
    }

    private static double deg2rad(double deg) {
        return (deg * Math.PI / 180.0);
    }

    private static double rad2deg(double rad) {
        return (rad * 180.0 / Math.PI);
    }

    public static void errortone(Context c) {
        try {

            if (m.isPlaying()) {
                m.stop();
                m.release();
                m = new MediaPlayer();
            }

            AssetFileDescriptor descriptor = c.getAssets().openFd("beep23.mp3");
            m.setDataSource(descriptor.getFileDescriptor(),
                    descriptor.getStartOffset(), descriptor.getLength());
            descriptor.close();

            m.prepare();
            m.setVolume(1f, 1f);
            m.setLooping(true);
            m.start();
            Handler h = new Handler();
            Runnable stopPlaybackRun = new Runnable() {
                public void run() {
                    m.stop();
                    m.release();
                    m = new MediaPlayer();
                }
            };
            h.postDelayed(stopPlaybackRun, 2 * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private final static LocationListener locationListener = new LocationListener() {
        public void onLocationChanged(Location location) {
            updateWithNewLocation(location);
        }

        public void onProviderDisabled(String provider) {
            updateWithNewLocation(null);
        }

        public void onProviderEnabled(String provider) {
        }

        public void onStatusChanged(String provider, int status, Bundle extras) {
        }
    };
    private static final String PIC_WIDTH = null;

    public static void Getcurrentlatlng(Context ctx) {
        try {
            LocationManager locationManager = (LocationManager) ctx
                    .getSystemService(Context.LOCATION_SERVICE);

            Criteria criteria = new Criteria();
            criteria.setAccuracy(Criteria.ACCURACY_FINE);
            criteria.setAltitudeRequired(false);
            criteria.setBearingRequired(false);
            criteria.setCostAllowed(true);
            criteria.setPowerRequirement(Criteria.POWER_LOW);
            String provider = locationManager.getBestProvider(criteria, true);

            if (ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(ctx, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            }
            locationManager.requestLocationUpdates(provider, 2000, 5,
                    locationListener);
            Location location = locationManager.getLastKnownLocation(provider);
            boolean isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);
            location = null;
            if (isGPSEnabled) {
                if (location == null) {
                    // locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                    // new Long("2"), new Float("2"), locationListener, null);
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            mlatit = location.getLatitude() + "";
                            mlongi = location.getLongitude() + "";

                        }
                    }
                }

            }
            // getting network status
            boolean isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (isNetworkEnabled) {
                // locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
                // new Long("2"), new Float("2"), locationListener, null);
                Log.v("Network", "Network is enabled");
                if (locationManager != null) {
                    location = locationManager
                            .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                    if (location != null) {
                        mlatit = location.getLatitude() + "";
                        mlongi = location.getLongitude() + "";

                    } else {
                        Log.v("LocationTracker", "Location is null");
                    }
                }

            }

            updateWithNewLocation(location);
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static void updateWithNewLocation(Location location) {

        if (location != null) {
            mlatit = location.getLatitude() + "";
            mlongi = location.getLongitude() + "";
            if (validations1(mlatit, mlongi)) {

            } else {
                mlatit = "NA";

                mlongi = "NA";

            }

        } else {

            mlatit = "NA";

            mlongi = "NA";

        }

    }

    @SuppressLint("NewApi")
    static private boolean validations1(String latit, String Longi) {
        if (latit == null || latit.toString().isEmpty()
                || latit.toString().length() == 0
                || latit.toString().equals("")) {

            return false;
        }
        if (Longi == null || Longi.toString().isEmpty()
                || Longi.toString().length() == 0
                || Longi.toString().equals("")) {

            return false;
        } else {
            return true;
        }
    }

    private static String getRealPathFromURI(String contentURI, Context ctx) {
        Uri contentUri = Uri.parse(contentURI);
        Cursor cursor = ctx.getContentResolver().query(contentUri, null, null,
                null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor
                    .getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(index);
        }
    }

    public static String compressImage(String imageUri, Context ctx) {

        String filePath = getRealPathFromURI(imageUri, ctx);
        Bitmap scaledBitmap = null;

        BitmapFactory.Options options = new BitmapFactory.Options();

        // by setting this field as true, the actual bitmap pixels are not
        // loaded in the memory. Just the bounds are loaded. If
        // you try the use the bitmap here, you will get null.
        options.inJustDecodeBounds = true;
        Bitmap bmp = BitmapFactory.decodeFile(filePath, options);

        int actualHeight = options.outHeight;
        int actualWidth = options.outWidth;

        // max Height and width values of the compressed image is taken as
        // 816x612

        float maxHeight = 816.0f;
        float maxWidth = 612.0f;
        float imgRatio = actualWidth / actualHeight;
        float maxRatio = maxWidth / maxHeight;

        // width and height values are set maintaining the aspect ratio of the
        // image

        if (actualHeight > maxHeight || actualWidth > maxWidth) {
            if (imgRatio < maxRatio) {
                imgRatio = maxHeight / actualHeight;
                actualWidth = (int) (imgRatio * actualWidth);
                actualHeight = (int) maxHeight;
            } else if (imgRatio > maxRatio) {
                imgRatio = maxWidth / actualWidth;
                actualHeight = (int) (imgRatio * actualHeight);
                actualWidth = (int) maxWidth;
            } else {
                actualHeight = (int) maxHeight;
                actualWidth = (int) maxWidth;

            }
        }

        // setting inSampleSize value allows to load a scaled down version of
        // the original image

        options.inSampleSize = calculateInSampleSize(options, actualWidth,
                actualHeight);

        // inJustDecodeBounds set to false to load the actual bitmap
        options.inJustDecodeBounds = false;

        // this options allow android to claim the bitmap memory if it runs low
        // on memory
        options.inPurgeable = true;
        options.inInputShareable = true;
        options.inTempStorage = new byte[16 * 1024];

        try {
            // load the bitmap from its path
            bmp = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();

        }
        try {
            scaledBitmap = Bitmap.createBitmap(actualWidth, actualHeight,
                    Bitmap.Config.ARGB_8888);
        } catch (OutOfMemoryError exception) {
            exception.printStackTrace();
        }

        float ratioX = actualWidth / (float) options.outWidth;
        float ratioY = actualHeight / (float) options.outHeight;
        float middleX = actualWidth / 2.0f;
        float middleY = actualHeight / 2.0f;

        Matrix scaleMatrix = new Matrix();
        scaleMatrix.setScale(ratioX, ratioY, middleX, middleY);

        Canvas canvas = new Canvas(scaledBitmap);
        canvas.setMatrix(scaleMatrix);
        canvas.drawBitmap(bmp, middleX - bmp.getWidth() / 2,
                middleY - bmp.getHeight() / 2, new Paint(
                        Paint.FILTER_BITMAP_FLAG));

        // check the rotation of the image and display it properly
        ExifInterface exif;
        try {
            exif = new ExifInterface(filePath);

            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION, 0);
            Log.d("EXIF", "Exif: " + orientation);
            Matrix matrix = new Matrix();
            if (orientation == 6) {
                matrix.postRotate(90);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 3) {
                matrix.postRotate(180);
                Log.d("EXIF", "Exif: " + orientation);
            } else if (orientation == 8) {
                matrix.postRotate(270);
                Log.d("EXIF", "Exif: " + orientation);
            }
            scaledBitmap = Bitmap.createBitmap(scaledBitmap, 0, 0,
                    scaledBitmap.getWidth(), scaledBitmap.getHeight(), matrix,
                    true);
        } catch (IOException e) {
            e.printStackTrace();
        }

        FileOutputStream out = null;
        String filename = getFilename();
        try {
            out = new FileOutputStream(filename);

            // write the compressed bitmap at the destination specified by
            // filename.
            scaledBitmap.compress(Bitmap.CompressFormat.PNG, 100, out);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return filename;

    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height
                    / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        final float totalPixels = width * height;
        final float totalReqPixelsCap = reqWidth * reqHeight * 2;
        while (totalPixels / (inSampleSize * inSampleSize) > totalReqPixelsCap) {
            inSampleSize++;
        }

        return inSampleSize;
    }

    public static String getFilename() {
        File file = new File(Environment.getExternalStorageDirectory()
                .getPath(), "/StcpCompressed");
        if (!file.exists()) {
            file.mkdirs();
        }
        String uriSting = (file.getAbsolutePath() + "/"
                + System.currentTimeMillis() + ".jpg");
        return uriSting;

    }

    public static void fullscreen(Activity koutuk) {
        try {
            koutuk.requestWindowFeature(Window.FEATURE_NO_TITLE);
            koutuk.getWindow().setFlags(
                    WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);

            if (Build.VERSION.SDK_INT < 16) {
                koutuk.getWindow().setFlags(
                        WindowManager.LayoutParams.FLAG_FULLSCREEN,
                        WindowManager.LayoutParams.FLAG_FULLSCREEN);
            }
        } catch (Exception e) {

            // TODO: handle exception
        }
    }

    public static void makedirectory() {
        if (new File(Environment.getExternalStorageDirectory() + "/DMB")
                .exists()) {
            new File(Environment.getExternalStorageDirectory() + "/DMB/outvdo")
                    .mkdirs();
            new File(Environment.getExternalStorageDirectory() + "/DMB/outimg")
                    .mkdirs();

        } else {

            new File(Environment.getExternalStorageDirectory() + "/DMB")
                    .mkdirs();
            new File(Environment.getExternalStorageDirectory() + "/DMB/outvdo")
                    .mkdirs();
            new File(Environment.getExternalStorageDirectory() + "/DMB/outimg")
                    .mkdirs();
        }
    }

    public static void DownloadFile(String fileURL, String fileName) {
        try {
            String RootDir = Environment.getExternalStorageDirectory()
                    + File.separator + "DIGIMENU/Video";
            File RootFile = new File(RootDir);
            RootFile.mkdir();
            // File root = Environment.getExternalStorageDirectory();
            URL u = new URL(fileURL);
            HttpURLConnection c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();
            FileOutputStream f = new FileOutputStream(new File(RootFile,
                    fileName));
            InputStream in = c.getInputStream();
            byte[] buffer = new byte[1024];
            int len1 = 0;

            while ((len1 = in.read(buffer)) > 0) {
                f.write(buffer, 0, len1);
            }
            f.close();

        } catch (Exception e) {

            Log.d("Error....", e.toString());
        }

    }

    public static Bitmap DownloadTemplate(String URL) {

        Bitmap bitmap = null;
        InputStream in = null;
        String path = Environment.getExternalStorageDirectory().toString()
                + "/DIGITEMP/Images";
        OutputStream fOut = null;
        String str[] = URL.split("/");
        String imageName = str[str.length - 1];
        finalpath = path + "/" + imageName;

        File checkFile = new File(finalpath);
        if (checkFile.exists()) {

        } else {
            try {
                in = OpenHttpConnection(URL);
                bitmap = BitmapFactory.decodeStream(in);

                File imagesFolder = new File(
                        Environment.getExternalStorageDirectory(),
                        "/DIGITEMP/Images");

                if (imagesFolder.isDirectory()) {

                } else {
                    imagesFolder.mkdirs();

                }

                File file = new File(path, imageName);
                try {
                    fOut = new FileOutputStream(file);
                    /*
					 * if (!bitmap.compress(Bitmap.CompressFormat.PNG, 30,
					 * fOut)) { Log.e("Log", "error while saving bitmap " + path
					 * + imageName); }
					 */

                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                    fOut.flush();
                    fOut.close();
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }

                in.close();
            } catch (IOException e1) {
                // TODO Auto-generated catch block
                e1.printStackTrace();
            }
        }
        return bitmap;
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

    public static void downloadVideo(String URLpath) {
        try {
            int count = 0;
            URL url = new URL(URLpath);
            URLConnection conection = url.openConnection();
            conection.connect();
            int lenghtOfFile = conection.getContentLength();
            InputStream input = new BufferedInputStream(url.openStream(),
                    10 * 1024);
            String str[] = URLpath.split("/");
            String videoName = str[str.length - 1];
            // finalpath = path + "/" + imageName;
            File f = new File(Environment.getExternalStorageDirectory()
                    + "/ABC/Deepu" + "/" + videoName);
            if (f.exists()) {
                OutputStream output;
                output = new FileOutputStream(
                        Environment.getExternalStorageDirectory()
                                + "/ABC/Deepu" + "/" + videoName);
                finalpath = Environment.getExternalStorageDirectory()
                        .toString() + "/ABC/Deepu/" + videoName;
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishProgress("" + (int) ((total * 100) /
                    // lenghtOfFile));
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();

            } else {
                f.getParentFile().mkdirs();
                OutputStream output;

                output = new FileOutputStream(
                        Environment.getExternalStorageDirectory()
                                + "/ABC/Deepu" + "/" + videoName);
                finalpath = Environment.getExternalStorageDirectory()
                        .toString() + "/ABC/Deepu/" + videoName;
                byte data[] = new byte[1024];
                long total = 0;
                while ((count = input.read(data)) != -1) {
                    total += count;
                    output.write(data, 0, count);
                }
                output.flush();
                output.close();
                input.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Bitmap DownloadImage(String URL) {

        Bitmap bitmap = null;
        InputStream in = null;
        try {
            in = OpenHttpConnection1(URL);
            bitmap = BitmapFactory.decodeStream(in);

            File imagesFolder = new File(Environment
                    .getExternalStorageDirectory().toString(),
                    "/DIGITEMP/Images");

            if (imagesFolder.isDirectory()) {

            } else {
                imagesFolder.mkdirs();

            }

            String path = Environment.getExternalStorageDirectory().toString()
                    + "/DIGITEMP/Images";
            OutputStream fOut = null;
            String str[] = URL.split("/");
            String imageName = str[str.length - 1];
            finalpath = path + "/" + imageName;
            File file = new File(path, imageName);
            try {
                fOut = new FileOutputStream(file);
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
                fOut.flush();
                fOut.close();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            in.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        return bitmap;
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

    @SuppressLint("NewApi")
    public static void hideActionAndTaskBar(Activity ac) {

        try {
            if (ac.getActionBar() != null) {
                ac.getActionBar().hide();
            }
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        Commonutils.hideSystemBar();
    }

    public static void hideSystemBar() {
        try {
            // REQUIRES ROOT
            Build.VERSION_CODES vc = new Build.VERSION_CODES();
            Build.VERSION vr = new Build.VERSION();
            String ProcID = "79"; // HONEYCOMB AND OLDER

            // v.RELEASE //4.0.3
            if (vr.SDK_INT >= vc.ICE_CREAM_SANDWICH) {
                ProcID = "42"; // ICS AND NEWER
            }

            String commandStr = "service call activity " + ProcID
                    + " s16 com.android.systemui";
            runAsRoot(commandStr);
        } catch (Exception e) {
            // something went wrong, deal with it here
        }
    }

    public static void runAsRoot(String commandStr) {
        try {
            CommandCapture command = new CommandCapture(0, commandStr);
            RootTools.getShell(true).add(command).wait(2000);
        } catch (Exception e) {
            // something went wrong, deal with it here
            System.out.println("asdas");
        }
    }

    public static boolean isOnline(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null
                && activeNetwork.isConnectedOrConnecting();
        return isConnected;

    }

    @SuppressLint("LongLogTag")
    public static void DownloadAnyFile(String fileURL, String fileName) {
        try {
            String RootDir = Environment.getExternalStorageDirectory()
                    + File.separator + "PKM/Data";
            File RootFile = new File(RootDir);
            RootFile.mkdir();
            // File root = Environment.getExternalStorageDirectory();
            URL u = new URL(fileURL);
            HttpURLConnection c = (HttpURLConnection) u.openConnection();
            c.setRequestMethod("GET");
            c.setDoOutput(true);
            c.connect();
            FileOutputStream f = new FileOutputStream(new File(RootFile,
                    fileName));
            InputStream in = c.getInputStream();
            byte[] buffer = new byte[1024];
            int len1 = 0;

            while ((len1 = in.read(buffer)) > 0) {
                f.write(buffer, 0, len1);
            }
            f.close();

        } catch (Exception e) {

            Log.d("Image Download Error....", e.toString());
        }
    }

    public static String getTodayDate() {

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String date = sdf.format(new Date());
        return date;

    }

    public static Date getDate() {

        Calendar cal = Calendar.getInstance();
        Date d = cal.getTime();
        return d;

    }

    private static void audioFileUpload(String filePath) {

        HttpURLConnection conn = null;
        DataOutputStream dos = null;
        DataInputStream inStream = null;
        String existingFileName = filePath;// Environment.getExternalStorageDirectory().getAbsolutePath()
        // + "/mypic.png"
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        String responseFromServer = "";
        String urlString = "http://mywebsite.com/directory/upload.php";

        try {

            // ------------------ CLIENT REQUEST
            FileInputStream fileInputStream = new FileInputStream(new File(
                    existingFileName));
            // open a URL connection to the Servlet
            URL url = new URL(urlString);
            // Open a HTTP connection to the URL
            conn = (HttpURLConnection) url.openConnection();
            // Allow Inputs
            conn.setDoInput(true);
            // Allow Outputs
            conn.setDoOutput(true);
            // Don't use a cached copy.
            conn.setUseCaches(false);
            // Use a post method.
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("Content-Type",
                    "multipart/form-data;boundary=" + boundary);
            dos = new DataOutputStream(conn.getOutputStream());
            dos.writeBytes(twoHyphens + boundary + lineEnd);
            dos.writeBytes("Content-Disposition: form-data; name=\"uploadedfile\";filename=\""
                    + existingFileName + "\"" + lineEnd);
            dos.writeBytes(lineEnd);
            // create a buffer of maximum size
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];
            // read file and write it into form...
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {

                dos.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            }

            // send multipart form data necesssary after file data...
            dos.writeBytes(lineEnd);
            dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            // close streams
            Log.e("Debug", "File is written");
            fileInputStream.close();
            dos.flush();
            dos.close();

        } catch (MalformedURLException ex) {
            Log.e("Debug", "error: " + ex.getMessage(), ex);
        } catch (IOException ioe) {
            Log.e("Debug", "error: " + ioe.getMessage(), ioe);
        }

        // ------------------ read the SERVER RESPONSE
        try {

            inStream = new DataInputStream(conn.getInputStream());
            String str;

            while ((str = inStream.readLine()) != null) {

                Log.e("Debug", "Server Response " + str);

            }

            inStream.close();

        } catch (IOException ioex) {
            Log.e("Debug", "error: " + ioex.getMessage(), ioex);
        }
    }

    public static void ShowProgressDialogBox(Context mContext, String Message) {
        progressDialog = new ProgressDialog(mContext);
        progressDialog.setMessage(Message);
        progressDialog.setIndeterminate(false);
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            Drawable drawable = new ProgressBar(mContext).getIndeterminateDrawable().mutate();
            drawable.setColorFilter(ContextCompat.getColor(mContext, R.color.colorAccent),
                    PorterDuff.Mode.SRC_IN);
            progressDialog.setIndeterminateDrawable(drawable);
        }
        progressDialog.show();
    }

    public static void DismissProgressDialogBox(Context mContext) {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
        }
    }

}
