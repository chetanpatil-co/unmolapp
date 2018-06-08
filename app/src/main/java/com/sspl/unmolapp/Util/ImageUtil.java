package com.sspl.unmolapp.Util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.os.Environment;
import android.util.Base64;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ImageUtil {

	String imageDataString;

	public String imageCompression(String image) {
		// to compress image
		BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
		Bitmap b = BitmapFactory.decodeFile(image, bmpFactoryOptions);
		Bitmap out = Bitmap.createScaledBitmap(b, 200, 200, false);

		String[] bits = image.split("/");
		String lastOne = bits[bits.length - 1];
		File imageFile = null;
		File imagesFolder = new File(Environment.getExternalStorageDirectory(),
				"SSPL_MRO");

		if (imagesFolder.isDirectory()) {

			imageFile = new File(imagesFolder, lastOne);
			FileOutputStream fOut;
			try {
				fOut = new FileOutputStream(imageFile);
				out.compress(Bitmap.CompressFormat.JPEG, 70, fOut);
				fOut.flush();
				fOut.close();
				b.recycle();
				out.recycle();

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			imagesFolder.mkdirs();
			imageFile = new File(imagesFolder, lastOne);
			FileOutputStream fOut;
			try {
				fOut = new FileOutputStream(imageFile);
				out.compress(Bitmap.CompressFormat.JPEG, 70, fOut);
				fOut.flush();
				fOut.close();
				b.recycle();
				out.recycle();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		File file = new File(imageFile.getPath());
		byte imageData[] = new byte[(int) file.length()];
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e3) {

			e3.printStackTrace();
		}
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e2) {

			e2.printStackTrace();
		}
		BufferedInputStream bis = new BufferedInputStream(fis);
		try {
			bis.read(imageData, 0, imageData.length);
		} catch (IOException e1) {

			e1.printStackTrace();
		}
		imageDataString = Base64.encodeToString(imageData, 0);
		return imageDataString;
	}

	public String addDateTime(String image) {
		BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
		Bitmap b = BitmapFactory.decodeFile(image, bmpFactoryOptions);
		Bitmap out = Bitmap.createScaledBitmap(b, b.getWidth(), b.getHeight(),
				false);

		String[] bits = image.split("/");
		String lastOne = bits[bits.length - 1];
		File imageFile = null;
		File imagesFolder = new File(Environment.getExternalStorageDirectory(),
				"CompressedImages");

		Canvas canvas = new Canvas(out);
		canvas.drawBitmap(b, 0, 0, null);

		Paint paint = new Paint();

		paint.setStyle(Style.FILL);
		paint.setColor(Color.RED);
		paint.setTextSize(80);

		paint.setTextAlign(Paint.Align.LEFT);
		SimpleDateFormat df2 = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
		String currentDateandTime = df2.format(new Date());

		canvas.drawText(currentDateandTime, 50, 1100, paint);

		if (imagesFolder.isDirectory()) {

			imageFile = new File(imagesFolder, lastOne);
			FileOutputStream fOut;
			try {
				fOut = new FileOutputStream(imageFile);
				out.compress(Bitmap.CompressFormat.JPEG, 70, fOut);
				fOut.flush();
				fOut.close();
				b.recycle();
				out.recycle();

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			imagesFolder.mkdirs();
			imageFile = new File(imagesFolder, lastOne);
			FileOutputStream fOut;
			try {
				fOut = new FileOutputStream(imageFile);
				out.compress(Bitmap.CompressFormat.JPEG, 70, fOut);
				fOut.flush();
				fOut.close();
				b.recycle();
				out.recycle();

			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		return imageFile.getAbsolutePath();
	}

}
