package com.chetan.unmolapp.Util;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.chetan.unmolapp.Pojo.Register;


public class LocalDatabase extends SQLiteOpenHelper {

    // Database attributes
    public static final String DB_NAME = "unmol.db";
    public static final int DB_VERSION = 1;

    // Table Name
    public static final String REGISTER_TABLE = "register";

    // Column Name of register table
    public static final String Id = "id";
    public static final String FIRSTNAME = "firstName";
    public static final String LASTNAME = "lastName";
    public static final String MOBILE = "mobile";
    public static final String EMAIL = "email";
    public static final String ADDRESS = "address";
    public static final String STATUS = "status";


    public LocalDatabase(Context context, String name,
                         CursorFactory factory, int version) {
        super(context, name, factory, version);
        // TODO Auto-generated constructor stub

    }

    public LocalDatabase(Context context) {
        super(context, DB_NAME, null, DB_VERSION);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String register_table_query;
        try {
            register_table_query = "create table if not exists "
                    + REGISTER_TABLE + "(" + Id
                    + " INTEGER PRIMARY KEY, " +
                    "" + FIRSTNAME + " text ," +
                    "" + LASTNAME + " text ," +
                    "" + ADDRESS + " text ," +
                    "" + MOBILE + " text," +
                    "" + EMAIL + " text," +
                    "" + STATUS + " text);";
            db.execSQL(register_table_query);

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("ItemTable", "ItemTable Failed");
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    //Insert Method

    public String RegisterUser(Register r) {

        String reslt = null;
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(Id, r.getId());
            values.put(FIRSTNAME, r.getFirstName());
            values.put(LASTNAME, r.getLastName());
            values.put(MOBILE, r.getMobile());
            values.put(EMAIL, r.getEmail());
            values.put(ADDRESS, r.getCity());
            values.put(STATUS, r.getStatus());

            long id = db.insert(REGISTER_TABLE, null, values);
            System.out.println("ID = "+id);
            reslt = "Success";
        } catch (Exception e) {
            // TODO Auto-generated catch block
            reslt = "Error";
            e.printStackTrace();
        }
        db.close();
        return reslt;
    }

    public String CheckLogin(Register r) {

        String reslt = null;
        String username = r.getEmail();
        String password = r.getMobile();
        SQLiteDatabase db = this.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put(Id, r.getId());
            values.put(FIRSTNAME, r.getFirstName());
            values.put(LASTNAME, r.getLastName());
            values.put(MOBILE, r.getMobile());
            values.put(EMAIL, r.getEmail());
            values.put(ADDRESS, r.getCity());
            values.put(STATUS, r.getStatus());

            db.insert(REGISTER_TABLE, null, values);
            reslt = "Success";
        } catch (Exception e) {
            // TODO Auto-generated catch block
            reslt = "Error";
            e.printStackTrace();
        }
        db.close();
        return reslt;
    }
}
