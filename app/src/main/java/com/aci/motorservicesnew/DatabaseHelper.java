package com.aci.motorservicesnew;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DATABASEHELPER";
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "MotorService";
    // Table Names

    private static final String TABLE_SERVICE_MANAGER = "servicemanager";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_SERVER_MASTER_ID = "server_id";
    private static final String KEY_CREATED_AT = "created_at";
    private static final String KEY_EDITED_AT = "edited_at";
    private static final String KEY_PRODUCT = "product_type";
    private static final String KEY_CALL_TYPE = "call_type";
    private static final String KEY_SERVICE_TYPE = "service_type";
    private static final String KEY_IS_SYNCH = "is_synch";
    private static final String KEY_EDIT_LOG_COUNT = "log_count";
    private static final String KEY_IS_EDIT = "is_edited";

    private static final String KEY_CUSTOMER_NAME = "custtomer_name";
    private static final String KEY_CUSTOMER_MOBILE = "custtomer_mobile";
    private static final String KEY_BUYING_DATE = "buy_date";
    private static final String KEY_RUNNING_HOUER = "runnign_houer";
    private static final String KEY_INSTALLAION_DATE = "installation_date";

    private static final String KEY_CALL_SERVICE_DATE = "service_call_date";
    private static final String KEY_SERVICE_START_DATE = "service_start_date";
    private static final String KEY_SERVICE_END_DATE = "service_end_date";

    private static final String KEY_SERVICE_INCOME = "service_income";
    private static final String KEY_VISITED_DATE = "visited_date";

    private static final String CREATE_TABLE_SERVICE_MANAGER = "CREATE TABLE " + TABLE_SERVICE_MANAGER
            +"(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + KEY_SERVER_MASTER_ID + " INTEGER DEFAULT 0, "
            + KEY_PRODUCT + " TEXT DEFAULT '0', " + KEY_CALL_TYPE+" TEXT DEFAULT '0', " + KEY_SERVICE_TYPE + " TEXT DEFAULT '0', "
            + KEY_IS_SYNCH + " TEXT DEFAULT '0', " + KEY_EDIT_LOG_COUNT + " TEXT DEFAULT '0', " + KEY_IS_EDIT + " TEXT DEFAULT '0', "
            + KEY_CUSTOMER_NAME + " TEXT, " + KEY_CUSTOMER_MOBILE + " TEXT, " + KEY_BUYING_DATE + " DATETIME DEFAULT (datetime('now','localtime')), "
            + KEY_RUNNING_HOUER +" TEXT, "+ KEY_INSTALLAION_DATE + " DATETIME DEFAULT (datetime('now','localtime')), "
            + KEY_CALL_SERVICE_DATE + " DATETIME DEFAULT (datetime('now','localtime')), " + KEY_SERVICE_START_DATE + " DATETIME DEFAULT (datetime('now','localtime')),  "
            + KEY_SERVICE_END_DATE + " DATETIME DEFAULT (datetime('now','localtime')), " + KEY_SERVICE_INCOME + "TEXT, "
            + KEY_VISITED_DATE + " DATETIME DEFAULT (datetime('now','localtime')), "+ KEY_CREATED_AT + " DATETIME DEFAULT (datetime('now','localtime')),"
            + KEY_EDITED_AT + " DATETIME DEFAULT (datetime('now','localtime')));";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_SERVICE_MANAGER);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SERVICE_MANAGER);
        onCreate(db);
    }

    public boolean addInstallationService(String productType, String callType, String serviceType, String customerName, String mobile, String buyingDate, String houers, String installationDate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_SERVICE_TYPE , serviceType);
        contentValues.put(KEY_CALL_TYPE , callType);
        contentValues.put(KEY_PRODUCT , productType);
        contentValues.put(KEY_CUSTOMER_NAME , customerName);
        contentValues.put(KEY_CUSTOMER_MOBILE , mobile);
        contentValues.put(KEY_BUYING_DATE , buyingDate);
        contentValues.put(KEY_RUNNING_HOUER , houers);
        contentValues.put(KEY_INSTALLAION_DATE , installationDate);
        contentValues.put(KEY_IS_SYNCH , "N");
        contentValues.put(KEY_EDIT_LOG_COUNT , "1");
        contentValues.put(KEY_IS_EDIT , "N");

        long result = db.insert(TABLE_SERVICE_MANAGER, null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean addPerodicEntryService(String productType, String callType, String serviceType, String customerName, String mobile, String houers, String buyingDate,  String installationDate, String insserviceenddate ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_SERVICE_TYPE , serviceType);
        contentValues.put(KEY_CALL_TYPE , callType);
        contentValues.put(KEY_PRODUCT , productType);
        contentValues.put(KEY_CUSTOMER_NAME , customerName);
        contentValues.put(KEY_CUSTOMER_MOBILE , mobile);
        contentValues.put(KEY_RUNNING_HOUER , houers);
        contentValues.put(KEY_CALL_SERVICE_DATE , buyingDate);
        contentValues.put(KEY_SERVICE_START_DATE , installationDate);
        contentValues.put(KEY_SERVICE_END_DATE , insserviceenddate);
        contentValues.put(KEY_IS_SYNCH , "N");
        contentValues.put(KEY_EDIT_LOG_COUNT , "1");
        contentValues.put(KEY_IS_EDIT , "N");

        long result = db.insert(TABLE_SERVICE_MANAGER, null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }


}
