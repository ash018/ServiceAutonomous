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
    private static final String DATABASE_NAME = "recoveryManager";
    // Table Names
    private static final String TABLE_USERMGR = "usermanager";
    private static final String TABLE_RECOVERY = "recovery";
    private static final String TABLE_PROJECTION = "projection";
    private static final String TABLE_CAPTURE = "capture";
    private static final String TABLE_TRELEASE = "trelease";
    private static final String TABLE_MYAPPCUSTOMER = "myappcustomer";

    // Common column names
    private static final String KEY_ID = "id";
    private static final String KEY_CREATED_AT = "created_at";

    //UserMgr table -id, username, password, territorycode, status, created_at
    private static final String KEY_USERNAME = "username";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_TERRITORYCODE = "territorycode";
    private static final String KEY_STATUS = "status";

    //TABLE_MYAPPCUSTOMER -id, KEY_MACUSER, KEY_MACCODE, KEY_MACNAME, KEY_MACFORMONTH, KEY_MACSYNC, created_at
    private static final String KEY_MACUSER = "staffid";  //KEY_MACUSER,KEY_MACCODE,KEY_MACNAME,KEY_MACFORMONTH,KEY_MACSYNC
    private static final String KEY_MACCODE = "customercode";
    private static final String KEY_MACNAME = "customername";
    private static final String KEY_MACFORMONTH = "formonth";
    private static final String KEY_MACSYNC = "syncstatus";

    // Collection Table - column nmaes--KEY_RUSER,KEY_RCODE,KEY_RAMT,KEY_RMRN,KEY_RDATE, KEY_RSUPPORTEDBY, KEY_RREMARKS,KEY_RLAT,KEY_RLANG, KEY_RSYNC
    //  staffid, customercode, amount, mrn, collectiondate, supportedby, remarks, latitude, longitude, syncstatus
    private static final String KEY_RUSER = "staffid";
    private static final String KEY_RCODE = "customercode";
    private static final String KEY_RAMT = "amount";
    private static final String KEY_RMRN = "mrn";
    private static final String KEY_RDATE = "collectiondate";
    private static final String KEY_RSUPPORTEDBY = "supportedby";
    private static final String KEY_RREMARKS = "remarks";
    private static final String KEY_COLORSTATUS = "colorstatus";
    private static final String KEY_RLAT = "latitude";
    private static final String KEY_RLANG = "longitude";
    private static final String KEY_RSYNC = "syncstatus";
    //KEY_RSUPPORTEDBY, KEY_RREMARKS,KEY_RLAT,KEY_RLANG = supportedby, remarks,latitude,longitude

    // Projection Table - column names-KEY_PUSER,KEY_PCODE,KEY_PAMT,KEY_PDATE,KEY_PSYNC
    private static final String KEY_PUSER = "staffid";
    private static final String KEY_PCODE = "customercode";
    private static final String KEY_PAMT = "amount";
    private static final String KEY_PDATE = "projectiondate";
    private static final String KEY_PCMobile = "customermobile";
    private static final String KEY_PSYNC = "syncstatus";

    // Capture Table - column names-KEY_CPUSER,KEY_CPCODE,KEY_CPNAME,KEY_CPDATE,KEY_CPLOCATION,KEY_CPSYNC
    private static final String KEY_CPUSER = "staffid";
    private static final String KEY_CPCODE = "customercode";
    private static final String KEY_CPNAME = "customername";
    private static final String KEY_CPDATE = "capturedate";
    private static final String KEY_CPLOCATION = "capturelocation";
    private static final String KEY_CPTRACTORMODEL = "capturetractormodel"; //KEY_CPTRACTORMODEL-capturetractormodel,KEY_CPOTHER-captureother
    private static final String KEY_CPOTHER = "captureother";
    private static final String KEY_CPSYNC = "syncstatus";

    // Release Table - column names-KEY_ID,KEY_RLUSER,KEY_RLCODE,KEY_RLCNAME,KEY_RLDATE,KEY_RLDAMOUNT,KEY_RLSYNC,KEY_CREATED_AT
    private static final String KEY_RLUSER = "staffid";
    private static final String KEY_RLCODE = "customercode";
    private static final String KEY_RLCNAME = "customername";
    private static final String KEY_RLDATE = "releasedate";
    private static final String KEY_RLDAMOUNT = "amount";
    private static final String KEY_RLSYNC = "syncstatus";

    //Meeting Plan TABLE_MEETINGP -Column names-KEY_MPCODE,KEY_MPUSER,KEY_MPDATE,KEY_MPVLOG
    private static final String KEY_MPCODE = "customercode";
    private static final String KEY_MPUSER = "submitby";
    private static final String KEY_MPDATE = "submitdate";
    private static final String KEY_MPVLOG = "visitlogid";



    /* Table Create Statements */
    // User table create statement
    private static final String CREATE_TABLE_USERMGT = "CREATE TABLE " + TABLE_USERMGR
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_USERNAME + " TEXT,"
            + KEY_PASSWORD + " TEXT," + KEY_TERRITORYCODE + " TEXT," + KEY_STATUS + " INTEGER,"
            + KEY_CREATED_AT + " DATETIME" + ")";


    // TABLE_MYAPPCUSTOMER - KEY_MACUSER,KEY_MACCODE,KEY_MACNAME,KEY_MACFORMONTH,KEY_MACSYNC
    private static final String CREATE_TABLE_MYAPPCUSTOMER = "CREATE TABLE " + TABLE_MYAPPCUSTOMER
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_MACUSER + " TEXT, " + KEY_MACCODE + " TEXT, "
            + KEY_MACNAME + " TEXT, " + KEY_MACFORMONTH + " TEXT, " + KEY_MACSYNC + " INTEGER" + ")";


    // Collection Table - column nmaes--KEY_RUSER,KEY_RCODE,KEY_RAMT,KEY_RMRN,KEY_RDATE, KEY_RSUPPORTEDBY, KEY_RREMARKS, KEY_RSYNC
    //KEY_RSUPPORTEDBY, KEY_RREMARKS,KEY_RLAT,KEY_RLANG = supportedby, remarks,latitude,longitude
    //staffid, customercode, amount, mrn, collectiondate, supportedby, remarks, latitude, longitude, syncstatus
    private static final String CREATE_TABLE_RECOVERY = "CREATE TABLE "
            + TABLE_RECOVERY + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_RUSER + " TEXT,"
            + KEY_RCODE + " TEXT," + KEY_RAMT + " TEXT," + KEY_RMRN + " TEXT," + KEY_RDATE + " DATETIME,"
            + KEY_RSUPPORTEDBY + " TEXT," + KEY_RREMARKS + " TEXT,"
            + KEY_COLORSTATUS + " INTEGER,"
            + KEY_RLAT + " TEXT," + KEY_RLANG + " TEXT,"
            + KEY_RSYNC + " INTEGER," + KEY_CREATED_AT + " DATETIME" + ")";

    // Projection table create statement
    private static final String CREATE_TABLE_PROJECTION = "CREATE TABLE " + TABLE_PROJECTION
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_PUSER + " TEXT," + KEY_PCODE + " TEXT, "
            + KEY_PAMT + " TEXT, " + KEY_PDATE + " DATETIME," + KEY_PCMobile + " TEXT," + KEY_PSYNC + " INTEGER, "
            + KEY_CREATED_AT + " DATETIME" + ")";

    // Capture table:KEY_CPUSER,KEY_CPCODE,KEY_CPNAME,KEY_CPDATE,KEY_CPLOCATION,KEY_CPSYNC
    private static final String CREATE_TABLE_CAPTURE = "CREATE TABLE " + TABLE_CAPTURE
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_CPUSER + " TEXT," + KEY_CPCODE + " TEXT,"
            + KEY_CPNAME + " TEXT," + KEY_CPDATE + " DATETIME," + KEY_CPLOCATION + " TEXT," + KEY_CPTRACTORMODEL + " TEXT,"
            + KEY_CPOTHER + " TEXT," + KEY_CPSYNC + " INTEGER,"
            + KEY_CREATED_AT + " DATETIME" + ")";

    // Release Table - column names-KEY_RLUSER,KEY_RLCODE,KEY_RLCNAME,KEY_RLDATE,KEY_RLDAMOUNT,KEY_RLSYNC
    private static final String CREATE_TABLE_TRELEASE = "CREATE TABLE " + TABLE_TRELEASE
            + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," + KEY_RLUSER + " TEXT," + KEY_RLCODE + " TEXT,"
            + KEY_RLCNAME + " TEXT," + KEY_RLDATE + " DATETIME," + KEY_RLDAMOUNT + " TEXT," + KEY_RLSYNC + " INTEGER,"
            + KEY_CREATED_AT + " DATETIME" + ")";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_USERMGT);
        db.execSQL(CREATE_TABLE_RECOVERY);
        db.execSQL(CREATE_TABLE_PROJECTION);
        db.execSQL(CREATE_TABLE_CAPTURE);
        db.execSQL(CREATE_TABLE_TRELEASE);
        db.execSQL(CREATE_TABLE_MYAPPCUSTOMER);

        /*  Insert data into usermanager table  */
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('com1','1234', 'B001', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('com2','1333', 'B002', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('com3','1432', 'B004', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('chan','1531', 'B005', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('laks','1630', 'B006', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('feni','1729', 'B007', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('noak','1828', 'B008', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('com4','1927', 'B018', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('chit','2026', 'B020', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('coxs','2125', 'B021', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('fati','2224', 'B022', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('syl2','2323', 'D001', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('syl1','2422', 'D002', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('habi','2521', 'D003', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('moul','2620', 'D007', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('bram','2719', 'D010', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('bogr','2818', 'F001', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('rajs','2917', 'F002', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('cha1','3016', 'F003', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('nao1','3115', 'F004', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('joyp','3214', 'F005', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('nato','3313', 'F006', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('pabn','3412', 'F007', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('nao2','3511', 'F008', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('nao3','3610', 'F009', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('sira','3709', 'F016', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('cha2','3808', 'F017', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('ishw','3907', 'F018', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('sing','4006', 'F019', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('chat','4105', 'F020', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('puth','4204', 'F021', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('ran1','4303', 'G001', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('gaib','4402', 'G002', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('lal2','4501', 'G003', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('nilp','4600', 'G004', 1, '2019-0-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('kuri','4699', 'G005', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('ran2','4798', 'G006', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('lal1','4897', 'G007', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('ghor','4996', 'G008', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('nage','5095', 'G009', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('bira','5194', 'G010', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('bari','5293', 'L001', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('barg','5392', 'L002', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('fari','5491', 'L003', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('mada','5590', 'L004', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('char','5689', 'L006', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('bhol','5788', 'L007', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('gopa','5887', 'L008', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('kis1','5986', 'M001', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('myme','6085', 'M002', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('nars','6184', 'M003', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('muns','6283', 'M006', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('jama','6382', 'M008', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('tang','6481', 'M010', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('man1','6580', 'M012', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('sher','6679', 'M021', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('gazi','6778', 'M022', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('gaff','6877', 'M025', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('netr','6976', 'M026', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('modh','7075', 'M027', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('kis2','7174', 'm028', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('man2','7273', 'm029', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('boks','7372', 'm030', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('jess','7471', 'N001', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('chua','7570', 'N002', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('jhen','7669', 'N005', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('satk','7768', 'N006', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('mehe','7867', 'N007', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('kush','7966', 'N008', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('magu','8065', 'N014', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('khul','8164', 'N016', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('dina','8263', 'S004', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('seta','8362', 'S008', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('birg','8461', 'S012', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('tha1','8560', 'T001', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('tha2','8659', 'T002', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('pirg','8758', 'T003', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('pan1','8857', 'T004', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('pan2','8956', 'T005', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('pan3','9055', 'T006', 1, '2019-07-01')");
        db.execSQL("INSERT INTO " + TABLE_USERMGR + "(username, password, territorycode, status, created_at) VALUES ('hori','9154', 'T007', 1, '2019-07-01')");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERMGR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECOVERY);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PROJECTION);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CAPTURE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TRELEASE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MYAPPCUSTOMER);
        onCreate(db);
    }

    //TABLE_MYAPPCUSTOMER -id, KEY_MACUSER, KEY_MACCODE, KEY_MACNAME, KEY_MACFORMONTH, KEY_MACSYNC, created_at
    /*public boolean syncAppcustomer(String username, String customercode, String customername, String formonth, int syncstatus) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_MACUSER , username);
        contentValues.put(KEY_MACCODE , customercode);
        contentValues.put(KEY_MACNAME, customername);
        contentValues.put(KEY_MACFORMONTH, formonth);
        contentValues.put(KEY_MACSYNC, syncstatus);

        Log.d(TAG, "syncAppcustomer: Adding"+ username + customercode + customername + formonth + syncstatus + "to" + TABLE_MYAPPCUSTOMER);
        long result = db.insert(TABLE_MYAPPCUSTOMER, null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }*/

    /*public void saveAllCustomer(List<MyAppCustomer> myAppCustoerm){
        SQLiteDatabase db = this.getWritableDatabase();
        for (MyAppCustomer mCus : myAppCustoerm) {
            System.out.println("---Customer Name---XX------>" + mCus.getCustomername());
            String sss = "INSERT INTO " + TABLE_MYAPPCUSTOMER + "(" + KEY_MACUSER + "," + KEY_MACCODE + "," + KEY_MACNAME + "," + KEY_MACFORMONTH + "," + KEY_MACSYNC + ") VALUES ('" + mCus.getUsername() + "','" + mCus.getCustomercode() + "', '" + mCus.getCustomername() + "', '" + mCus.getFormonth() + "', '" + mCus.getSyncstatus() + "')";
            ///System.out.println("kups---->" + sss);
            db.execSQL("INSERT INTO " + TABLE_MYAPPCUSTOMER + "(" + KEY_MACUSER + "," + KEY_MACCODE + "," + KEY_MACNAME + "," + KEY_MACFORMONTH + "," + KEY_MACSYNC + ") VALUES ('" + mCus.getUsername() + "','" + mCus.getCustomercode() + "', '" + mCus.getCustomername() + "', '" + mCus.getFormonth() + "', " + mCus.getSyncstatus() + ")");

        }
        *//*try {
            for (MyAppCustomer mCus : myAppCustoerm) {
                System.out.println("---Customer Name---XX------>" + mCus.getCustomername());
                db.execSQL("INSERT INTO " + TABLE_MYAPPCUSTOMER + "(" + KEY_MACUSER + "," + KEY_MACCODE + "," + KEY_MACNAME + "," + KEY_MACFORMONTH + "," + KEY_MACSYNC + ") VALUES ('" + mCus.getUsername() + "','" + mCus.getCustomercode() + "', '" + mCus.getCustomername() + "', '" + mCus.getFormonth() + "', '" + mCus.getSyncstatus() + "')" );

            }
            db.close();
        } catch (Exception e){
            Log.e("Problem", e + " ");
        }*//*


    }
*/


    /* Add Users: id, username, password, territorycode, status, created_at*/
    public boolean addUsers(String username, String password, String territorycode, int status,String created_at) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_USERNAME , username);
        contentValues.put(KEY_PASSWORD , password);
        contentValues.put(KEY_TERRITORYCODE, territorycode);
        contentValues.put(KEY_STATUS, status);
        contentValues.put(KEY_CREATED_AT, created_at);
        System.out.print("----444----");
        Log.d(TAG, "addUsers: Adding"+ username + password + territorycode + status + created_at + "to" + TABLE_USERMGR);
        long result = db.insert(TABLE_USERMGR, null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    /*public Cursor getUserdata(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_USERMGR;
        Cursor data = db.rawQuery(query, null);
        return data;
    }*/

    /*public User Authenticate(User user) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_USERMGR,// Selecting Table
                new String[]{ KEY_ID, KEY_USERNAME, KEY_PASSWORD, KEY_TERRITORYCODE, KEY_STATUS },//Selecting columns want to query
                KEY_USERNAME + "=?",
                new String[]{user.staff},//Where clause
                null, null, null);
        if (cursor != null && cursor.moveToFirst()&& cursor.getCount()>0) {
            //if cursor has value then in user database there is user associated with this given email
            User user1 = new User(cursor.getString(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4));

            //Match both passwords check they are same or not
            if (user.password.equalsIgnoreCase(user1.password)) {
                return user1;
            }
        }
        //if user password does not matches or there is no record with that email then return @false
        return null;
    }*/

    /* Add Collection and get collection, TABLE_RECOVERY: KEY_RUSER,KEY_RCODE,KEY_RAMT,KEY_RMRN,KEY_RDATE,KEY_RSYNC */
    //KEY_RSUPPORTEDBY, KEY_RREMARKS,KEY_RLAT,KEY_RLANG = supportedby, remarks,latitude,longitude
    //staffid, customercode, amount, mrn, collectiondate, supportedby, remarks, latitude, longitude, syncstatus
    public boolean addCollection(String rstaff, String rccode, String ramt, String mrn, String rdate,String supportedby, String remarks, int colorstatus, String rlatitude, String rlongitude, int rsync, String createdt) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_RUSER , rstaff);
        contentValues.put(KEY_RCODE , rccode);
        contentValues.put(KEY_RAMT , ramt);
        contentValues.put(KEY_RMRN , mrn);
        contentValues.put(KEY_RDATE , rdate);
        contentValues.put(KEY_RSUPPORTEDBY , supportedby);
        contentValues.put(KEY_RREMARKS , remarks);
        contentValues.put(KEY_COLORSTATUS , colorstatus);
        contentValues.put(KEY_RLAT , rlatitude);
        contentValues.put(KEY_RLANG , rlongitude);
        contentValues.put(KEY_RSYNC , rsync);
        contentValues.put(KEY_CREATED_AT , createdt);

        Log.d(TAG, "addCollection: Adding"+ rstaff + rccode + ramt + mrn + rdate + supportedby + colorstatus + remarks + rlatitude + rlongitude + rsync + createdt + "to" + TABLE_RECOVERY);

        long result = db.insert(TABLE_RECOVERY, null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    public boolean updateCollection(String cid, String ccode, String ramt,String mrn,String rdate,String supportedby, /*int colorstatus,*/String remarks,String latitude, String longitude, int rsync, String createdate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID,cid);
        contentValues.put(KEY_RCODE, ccode);
        contentValues.put(KEY_RAMT,ramt);
        contentValues.put(KEY_RMRN,mrn);
        contentValues.put(KEY_RDATE,rdate);
        contentValues.put(KEY_RSUPPORTEDBY , supportedby);
        contentValues.put(KEY_RREMARKS , remarks);
        //contentValues.put(KEY_COLORSTATUS , colorstatus);
        contentValues.put(KEY_RLAT , latitude);
        contentValues.put(KEY_RLANG , longitude);
        contentValues.put(KEY_RSYNC,rsync);
        contentValues.put(KEY_CREATED_AT,createdate);
        db.update(TABLE_RECOVERY, contentValues, KEY_ID + " = " + cid, null);
        return true;
    }

    public Cursor getMrnwisedata(int mrn) {
        SQLiteDatabase db = this.getReadableDatabase();
        String sql = "SELECT * FROM " + TABLE_RECOVERY + " WHERE " + KEY_RMRN + " = " + mrn;
        Cursor c = db.rawQuery(sql, null);
        return c;
    }

    /* Get collection from sqlite */
    public Cursor getCollection(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_RECOVERY;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    public HashMap<String, String> getCollectionDetails() {
        HashMap<String, String> collection = new HashMap<String, String>();
        String selectQuery = "SELECT  * FROM " + TABLE_RECOVERY;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        // Move to first row
        cursor.moveToFirst();
        if (cursor.getCount() > 0) {
            collection.put("id", cursor.getString(0));
            collection.put("staffid", cursor.getString(1));
            collection.put("customercode", cursor.getString(2));
            collection.put("amount", cursor.getString(3));
            collection.put("mrn", cursor.getString(4));
            collection.put("collectiondate", cursor.getString(5));
            collection.put("syncstatus", cursor.getString(6));
        }
        cursor.close();
        db.close();
        // return user
        Log.d(TAG, "Fetching user from Sqlite: " + collection.toString());
        return collection;
    }


    /*  Add Projection and get collection,
        TABLE_PROJECTION: KEY_PUSER,KEY_PCODE,KEY_PAMT,KEY_PDATE,KEY_PSYNC,KEY_CREATED_AT
    */
    public boolean addProjection(String pstaff, String pccode, String pamt, String pdate, String cmobile, int psync,String pcreatedate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_PUSER , pstaff);
        contentValues.put(KEY_PCODE , pccode);
        contentValues.put(KEY_PAMT , pamt);
        contentValues.put(KEY_PDATE , pdate);
        contentValues.put(KEY_PCMobile , cmobile);
        contentValues.put(KEY_PSYNC , psync);
        contentValues.put(KEY_CREATED_AT , pcreatedate);
        Log.d(TAG, "addProjection: Adding"+ pstaff + pccode + pamt + pdate + cmobile + psync + "to" + TABLE_PROJECTION);

        long result = db.insert(TABLE_PROJECTION, null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }

    public boolean checkCustomerCode(String customerCode){

        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "select count(*) as c from myappcustomer where customercode LIKE '%"+customerCode+"%';";
        Cursor cursor = db.rawQuery(selectQuery, null);
        String customerNumber = "";
        if (cursor.moveToFirst()) { //staffid,customercode,customername,releasedate,amount,syncstatus,created_at
            while (!cursor.isAfterLast()) {
                customerNumber = cursor.getString(cursor.getColumnIndex("c"));
                cursor.moveToNext();
            }
        }

        if( Integer.parseInt(customerNumber) > 0 ){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean checkCaptureEntry(String customerCode){

        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "select count(*) as c from capture where customercode LIKE '%"+customerCode+"%';";
        Cursor cursor = db.rawQuery(selectQuery, null);
        String customerNumber = "";
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                customerNumber = cursor.getString(cursor.getColumnIndex("c"));
                cursor.moveToNext();
            }
        }

        if( Integer.parseInt(customerNumber) > 0 ){
            return true;
        }
        else{
            return false;
        }
    }

    public String getCustomerMoble(String customerMobile){

        SQLiteDatabase db = this.getWritableDatabase();
        String selectQuery = "select  customermobile from projection where customercode LIKE '%"+customerMobile+"%';";
        Cursor cursor = db.rawQuery(selectQuery, null);

        String customerCode = "";
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                customerCode = cursor.getString(cursor.getColumnIndex("c"));
                cursor.moveToNext();
            }
        }
        return customerCode;
    }

    public boolean deleteAllcustomer(){
        SQLiteDatabase db = this.getWritableDatabase();
        String delCustomer = "delete from myappcustomer";

        db.execSQL(delCustomer);
        return true;
    }

    public boolean checkRowCount(){

        Date date = new Date();
        String modifiedDate= new SimpleDateFormat("yyyy-MM-dd").format(date);
        String forMonth = modifiedDate.split("-")[0] + "-" + modifiedDate.split("-")[1] + "01";
        SQLiteDatabase db = this.getWritableDatabase();
        System.out.println("-----Now I am in Row Count-----");
        String selectQuery = "select count(*) as c from myappcustomer where formonth = '"+forMonth+"';";
        Cursor cursor = db.rawQuery(selectQuery, null);
        String customerNumberForthisMonth = "";
        if (cursor.moveToFirst()) { //staffid,customercode,customername,releasedate,amount,syncstatus,created_at
            while (!cursor.isAfterLast()) {
                customerNumberForthisMonth = cursor.getString(cursor.getColumnIndex("c"));
                cursor.moveToNext();
            }
        }
        if( Integer.parseInt(customerNumberForthisMonth) > 0 ){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean updateProjection(String pid, String pccode,String pamt,String pdate, String cmobile, String psync, String upcreatedate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID,pid);
        contentValues.put(KEY_PCODE,pccode);
        contentValues.put(KEY_PAMT,pamt);
        contentValues.put(KEY_PDATE,pdate);
        contentValues.put(KEY_PCMobile,cmobile);
        contentValues.put(KEY_PSYNC,psync);
        contentValues.put(KEY_CREATED_AT,upcreatedate);
        db.update(TABLE_PROJECTION, contentValues, KEY_ID + " = " + pid, null);
        return true;
    }

    /* Get projection from sqlite */
    public Cursor getProjection(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PROJECTION;
        Cursor data = db.rawQuery(query, null);
        return data;
    }


    // Capture table:KEY_CPUSER,KEY_CPCODE,KEY_CPNAME,KEY_CPDATE,KEY_CPLOCATION,KEY_CPSYNC
    public boolean addCapture(String cpstaff, String cpccode, String cpcname, String cpdate, String cplocation,String capturetractormodel, String captureother, int cpsync, String createdate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_CPUSER , cpstaff);
        contentValues.put(KEY_CPCODE , cpccode);
        contentValues.put(KEY_CPNAME , cpcname);
        contentValues.put(KEY_CPDATE , cpdate);
        contentValues.put(KEY_CPLOCATION , cplocation);
        //KEY_CPTRACTORMODEL-capturetractormodel,KEY_CPOTHER-captureother
        contentValues.put(KEY_CPTRACTORMODEL , capturetractormodel);
        contentValues.put(KEY_CPOTHER , captureother);
        contentValues.put(KEY_CPSYNC , cpsync);
        contentValues.put(KEY_CREATED_AT , createdate);

        Log.d(TAG, "addCapture: Adding"+ cpstaff + cpccode + cpcname + cpdate + cplocation + capturetractormodel + captureother + cpsync + createdate + "to" + TABLE_CAPTURE);
        long result = db.insert(TABLE_CAPTURE, null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    //KEY_ID, KEY_CPUSER,KEY_CPCODE,KEY_CPNAME,KEY_CPDATE,KEY_CPLOCATION,KEY_CPSYNC,KEY_CREATED_AT
    public boolean updateCaptures(String cpid,String cpccode,String cpcname,String cpcdate, String cplocation, String capturetractormodel, String captureother, String cpsync,String createdate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID,cpid);
        contentValues.put(KEY_CPCODE,cpccode);
        contentValues.put(KEY_CPNAME,cpcname);
        contentValues.put(KEY_CPDATE,cpcdate);
        contentValues.put(KEY_CPLOCATION,cplocation);
        ////KEY_CPTRACTORMODEL-capturetractormodel,KEY_CPOTHER-captureother
        contentValues.put(KEY_CPTRACTORMODEL,capturetractormodel);
        contentValues.put(KEY_CPOTHER,captureother);
        contentValues.put(KEY_CPSYNC,cpsync);
        contentValues.put(KEY_CREATED_AT,createdate);
        db.update(TABLE_CAPTURE, contentValues, KEY_ID + " = " + cpid, null);
        return true;
    }
    /* Get all data from sqlite */
    public Cursor getCapture(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_CAPTURE;
        Cursor data = db.rawQuery(query, null);
        return data;
    }

    // Release Table - column names-KEY_ID,KEY_RLUSER,KEY_RLCODE,KEY_RLCNAME,KEY_RLDATE,KEY_RLDAMOUNT,KEY_RLSYNC,KEY_CREATED_AT
    public boolean addRelease(String rlstaff, String rlccode, String rlcname, String rldate, String rlamount, int rlsync) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_RLUSER , rlstaff);
        contentValues.put(KEY_RLCODE , rlccode);
        contentValues.put(KEY_RLCNAME , rlcname);
        contentValues.put(KEY_RLDATE , rldate);
        contentValues.put(KEY_RLDAMOUNT , rlamount);
        contentValues.put(KEY_RLSYNC , rlsync);

        Log.d(TAG, "addRelease: Adding"+ rlstaff + rlccode + rlcname + rldate + rlamount + rlsync + "to" + TABLE_TRELEASE);

        long result = db.insert(TABLE_TRELEASE, null, contentValues);
        if(result == -1){
            return false;
        }else{
            return true;
        }
    }
    /* Get all data from sqlite */
    public Cursor getRelease(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_TRELEASE;
        Cursor data = db.rawQuery(query, null);
        return data;
    }
    //KEY_ID,KEY_RLUSER,KEY_RLCODE,KEY_RLCNAME,KEY_RLDATE,KEY_RLDAMOUNT,KEY_RLSYNC,KEY_CREATED_AT
    public boolean updateReleases(String rlid,String rlccode,String rlcname,String rldate, String rlamt, String rlsyncs,String createdate) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_ID,rlid);
        contentValues.put(KEY_RLCODE,rlccode);
        contentValues.put(KEY_RLCNAME,rlcname);
        contentValues.put(KEY_RLDATE,rldate);
        contentValues.put(KEY_RLDAMOUNT,rlamt);
        contentValues.put(KEY_RLSYNC,rlsyncs);
        contentValues.put(KEY_CREATED_AT,createdate);
        db.update(TABLE_CAPTURE, contentValues, KEY_ID + " = " + rlid, null);
        return true;
    }


    /* Closing connection */
    public void closeDB() {
        SQLiteDatabase db = this.getReadableDatabase();
        if (db != null && db.isOpen())
            db.close();
    }
    /* get datetime */
    private String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public String getTerritoryname(String username){
        SQLiteDatabase db = this.getReadableDatabase();
        String territorycode = "";
        try
        {
            //Cursor c = null;
            Cursor c = db.rawQuery("select territorycode from usermanager where username = '" + username  + "'", null);
            c.moveToFirst();
            territorycode = c.getString(c.getColumnIndex("territorycode"));
            c.close();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        return territorycode;
    }

   /* public List<RecoveryRow> getDataForSynch(){
        List<RecoveryRow> recList = new ArrayList<RecoveryRow>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_RECOVERY + " where syncstatus = '0';";
        Cursor cursor = db.rawQuery(selectQuery, null);
        //staffid, customercode, amount, mrn, collectiondate, supportedby, remarks, latitude, longitude, syncstatus
        if (cursor.moveToFirst()) {
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String staffid = cursor.getString(cursor.getColumnIndex("staffid"));
                String customercode = cursor.getString(cursor.getColumnIndex("customercode"));
                String amount = cursor.getString(cursor.getColumnIndex("amount"));
                String mrn = cursor.getString(cursor.getColumnIndex("mrn"));
                String collectiondate = cursor.getString(cursor.getColumnIndex("collectiondate"));
                String supportedby = cursor.getString(cursor.getColumnIndex("supportedby"));
                String remarks = cursor.getString(cursor.getColumnIndex("remarks"));
                String colorstatus = cursor.getString(cursor.getColumnIndex("colorstatus"));
                String latitude = cursor.getString(cursor.getColumnIndex("latitude"));
                String longitude = cursor.getString(cursor.getColumnIndex("longitude"));
                String created_at = cursor.getString(cursor.getColumnIndex("created_at"));
                System.out.println("Dhorci colorstatus######-->" + colorstatus);
                RecoveryRow rr = new RecoveryRow(id, staffid, customercode, amount, mrn, collectiondate,supportedby, remarks, colorstatus ,latitude, longitude, created_at);
                recList.add(rr);
                cursor.moveToNext();
            }
        }

        return recList;
    }
    //Projection Sync
    public List<ProjectionRow> getDataProjectionForSynch(){
        List<ProjectionRow> prjList = new ArrayList<ProjectionRow>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_PROJECTION + " where syncstatus = '0';";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) { //staffid,customercode,amount,projectiondate,customermobile,syncstatus
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String staffid = cursor.getString(cursor.getColumnIndex("staffid"));
                String customercode = cursor.getString(cursor.getColumnIndex("customercode"));
                String amount = cursor.getString(cursor.getColumnIndex("amount"));
                String projectiondate = cursor.getString(cursor.getColumnIndex("projectiondate"));
                String customermobile = cursor.getString(cursor.getColumnIndex("customermobile"));
                //String pssyncstatus = cursor.getString(cursor.getColumnIndex("syncstatus"));
                String created_at = cursor.getString(cursor.getColumnIndex("created_at"));

                ProjectionRow pr = new ProjectionRow(id, staffid, customercode, amount, projectiondate, customermobile, created_at);
                prjList.add(pr);
                cursor.moveToNext();
            }
        }

        return prjList;
    }
    //Capture Sync
    public List<CaptureRow> getDataCaptureForSynch(){
        List<CaptureRow> capList = new ArrayList<CaptureRow>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_CAPTURE + " where syncstatus = '0';";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) { //staffid,customercode,customername,capturedate,capturelocation
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String staffid = cursor.getString(cursor.getColumnIndex("staffid"));
                String customercode = cursor.getString(cursor.getColumnIndex("customercode"));
                String customername = cursor.getString(cursor.getColumnIndex("customername"));
                String capturedate = cursor.getString(cursor.getColumnIndex("capturedate"));
                String capturelocation = cursor.getString(cursor.getColumnIndex("capturelocation"));
                String capturetractormodel = cursor.getString(cursor.getColumnIndex("capturetractormodel"));
                String captureother = cursor.getString(cursor.getColumnIndex("captureother"));
                String created_at = cursor.getString(cursor.getColumnIndex("created_at"));

                CaptureRow cr = new CaptureRow(id, staffid, customercode, customername, capturedate, capturelocation,capturetractormodel,captureother, created_at);
                capList.add(cr);
                cursor.moveToNext();
            }
        }

        return capList;
    }
    //Release Sync
    public List<ReleaseRow> getDataReleaseForSynch(){
        List<ReleaseRow> relList = new ArrayList<ReleaseRow>();
        SQLiteDatabase db = this.getWritableDatabase();

        String selectQuery = "SELECT  * FROM " + TABLE_TRELEASE + " where syncstatus = '0';";
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) { //staffid,customercode,customername,releasedate,amount,syncstatus,created_at
            while (!cursor.isAfterLast()) {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String staffid = cursor.getString(cursor.getColumnIndex("staffid"));
                String customercode = cursor.getString(cursor.getColumnIndex("customercode"));
                String customername = cursor.getString(cursor.getColumnIndex("customername"));
                String releasedate = cursor.getString(cursor.getColumnIndex("releasedate"));
                String amount = cursor.getString(cursor.getColumnIndex("amount"));
                //String pssyncstatus = cursor.getString(cursor.getColumnIndex("syncstatus"));
                String created_at = cursor.getString(cursor.getColumnIndex("created_at"));

                ReleaseRow rr = new ReleaseRow(id, staffid, customercode, customername, releasedate, amount, created_at);
                relList.add(rr);
                cursor.moveToNext();
            }
        }

        return relList;
    }

    public boolean isAnyDatForSynch(){
        SQLiteDatabase db = this.getWritableDatabase();

        String recoveryTblSynchSql = "select count(*) as rCount From recovery where syncstatus = 0;";
        String projectionTblSynchSql = "select count(*) as rCount From projection where syncstatus = 0;";
        String captureTblSynchSql = "select count(*) as rCount From capture where syncstatus = 0;";
        String releaseTblSynchSql = "select count(*) as rCount From trelease where syncstatus = 0;";

        Cursor cursorRecoveryTbl = db.rawQuery(recoveryTblSynchSql, null);
        Cursor cursorProjectionTbl = db.rawQuery(projectionTblSynchSql, null);
        Cursor cursorCaptureTbl = db.rawQuery(captureTblSynchSql, null);
        Cursor cursorReleaseTbl = db.rawQuery(releaseTblSynchSql, null);

        int recTblRow = 0;
        int projTblRow = 0;
        int captTblRow = 0;
        int releTblRow = 0;

        if (cursorRecoveryTbl.moveToFirst()) {
            recTblRow =  cursorRecoveryTbl.getInt(0);// EventOperator
        }

        if (cursorProjectionTbl.moveToFirst()) {
            projTblRow =  cursorProjectionTbl.getInt(0);// EventOperator
        }

        if (cursorCaptureTbl.moveToFirst()) {
            captTblRow =  cursorCaptureTbl.getInt(0);// EventOperator
        }

        if (cursorReleaseTbl.moveToFirst()) {
            releTblRow =  cursorReleaseTbl.getInt(0);// EventOperator
        }

        if (recTblRow > 0 || projTblRow > 0 || captTblRow > 0 || releTblRow > 0 ){
            return true;
        }else{
            return false;
        }
    }

    public void updateAllSynStatus(int[] recoveryIds, int[] projectionsIds, int[] capyureIds, int[] releaseIds ){
        SQLiteDatabase db = this.getWritableDatabase();

        for(int i = 0; i< recoveryIds.length; i++){
            ContentValues contentValues = new ContentValues();
            contentValues.put("syncstatus","1");
            db.update(TABLE_RECOVERY, contentValues, KEY_ID + " = " + recoveryIds[i], null);
        }
        for(int i = 0; i< projectionsIds.length; i++){
            ContentValues contentValues = new ContentValues();
            contentValues.put("syncstatus","1");
            db.update(TABLE_PROJECTION, contentValues, KEY_ID + " = " + projectionsIds[i], null);
        }
        for(int i = 0; i< capyureIds.length; i++){
            ContentValues contentValues = new ContentValues();
            contentValues.put("syncstatus","1");
            db.update(TABLE_CAPTURE, contentValues, KEY_ID + " = " + capyureIds[i], null);
        }
        for(int i = 0; i< releaseIds.length; i++){
            ContentValues contentValues = new ContentValues();
            contentValues.put("syncstatus","1");
            db.update(TABLE_TRELEASE, contentValues, KEY_ID + " = " + releaseIds[i], null);
        }
    }
*/



}
