package com.outthinking.applock.main.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.outthinking.applock.main.model.AppDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Outthinking11 on 20-07-2016.
 */
public class MyDatabaseHelper extends SQLiteOpenHelper {

    private static MyDatabaseHelper databaseHelper;
    private SQLiteDatabase sqLiteDatabase;
    //DATABASE FIELDS
    private static final String DATABASE_NAME = "apps_preference";
    private static final String DATABASE_TABLE_NAME = "app_lock_states";
    private static final int DATABASE_VERSION = 1;
    //COLUMN NAMES
    private static final String COLUMN_PACKAGE_NAME = "package_name";
    private static final String COLUMN_LOCK_STATUS = "lock_status";
    private static final String CREATE_TABLE_QUERY = "create table " + DATABASE_TABLE_NAME + "(" + COLUMN_PACKAGE_NAME + " text primary key ,"
            + COLUMN_LOCK_STATUS + " boolean not null)";

    private MyDatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public static synchronized MyDatabaseHelper getDatabaseInstance(Context context) {
        if (databaseHelper == null) {
            databaseHelper = new MyDatabaseHelper(context.getApplicationContext());
        }
        return databaseHelper;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL(CREATE_TABLE_QUERY);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + CREATE_TABLE_QUERY);
        // Create tables again
        onCreate(sqLiteDatabase);
    }

    public void openDatabase() {
        sqLiteDatabase = databaseHelper.getWritableDatabase();
    }

    public void closeDatabase() {
        sqLiteDatabase.close();
        this.close();
    }

    //ALL DATABASE OPERATIONS
    public long insertApp(AppDetails appDetails) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PACKAGE_NAME, appDetails.getPackageName().toString());
        cv.put(COLUMN_LOCK_STATUS, false);//false because initially no lock
        long val = sqLiteDatabase.insert(DATABASE_TABLE_NAME, null, cv);
        return val;
    }

    public int deleteApp(String strPackageName) {
        int result = sqLiteDatabase.delete(DATABASE_TABLE_NAME, COLUMN_PACKAGE_NAME + "=?", new String[]{strPackageName});
        return result;
    }

    public List<AppDetails> getAllAppDetails() {
        List<AppDetails> appDetailsList = new ArrayList<AppDetails>();
        Cursor cursor = sqLiteDatabase.query(DATABASE_TABLE_NAME, new String[]{COLUMN_PACKAGE_NAME, COLUMN_LOCK_STATUS}, null, null, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            while (cursor.moveToNext()) {
                AppDetails appDetails = new AppDetails();
                appDetails.setPackageName(cursor.getString(cursor.getColumnIndex(COLUMN_PACKAGE_NAME)));
                appDetails.setLockStatus(cursor.getInt(cursor.getColumnIndex(COLUMN_PACKAGE_NAME)) != 0);//false if no lock and true if lock is on
                appDetailsList.add(appDetails);
            }

        }
        return appDetailsList;
    }

    public int changeLockStatusOn(String packageName) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_LOCK_STATUS, true);
        int value = sqLiteDatabase.update(DATABASE_TABLE_NAME, cv, COLUMN_PACKAGE_NAME + "=?", new String[]{packageName});
        if (value != 1) {
            AppDetails appDetails=new AppDetails();
            appDetails.setPackageName(packageName);
            this.insertApp(appDetails);
            value = sqLiteDatabase.update(DATABASE_TABLE_NAME, cv, COLUMN_PACKAGE_NAME + "=?", new String[]{packageName});
        }
        return value;
    }

    public int changeLockStatusOff(String packageName) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_LOCK_STATUS, false);
        int value = sqLiteDatabase.update(DATABASE_TABLE_NAME, cv, COLUMN_PACKAGE_NAME + "=?", new String[]{packageName});
        return value;
    }

    public boolean checkLockStatus(String packageName) {
        boolean result = false;
        Cursor cursor = sqLiteDatabase.query(DATABASE_TABLE_NAME, new String[]{COLUMN_LOCK_STATUS}, COLUMN_PACKAGE_NAME + "=?", new String[]{packageName}, null, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            result = cursor.getInt(cursor.getColumnIndex(COLUMN_LOCK_STATUS)) != 0;
        }
        return result;
    }

}
