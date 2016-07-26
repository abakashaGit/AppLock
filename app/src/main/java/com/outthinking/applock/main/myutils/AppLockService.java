package com.outthinking.applock.main.myutils;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.outthinking.applock.main.data.MyDatabaseHelper;
import com.outthinking.applock.main.model.AppDetails;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Outthinking11 on 21-07-2016.
 */
public class AppLockService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful
        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    private MyDatabaseHelper dbHelper;
    private Map<String, Boolean> mAppMap;

    @Override
    public void onCreate() {
        super.onCreate();
        dbHelper = MyDatabaseHelper.getDatabaseInstance(getBaseContext());
        dbHelper.openDatabase();
        List<AppDetails> appDetailsList = dbHelper.getAllAppDetails();
        mAppMap = new HashMap<String, Boolean>();
        if (appDetailsList != null && appDetailsList.size() > 0)
            for (AppDetails appDetails : appDetailsList) {
                mAppMap.put(appDetails.getPackageName().toString(),appDetails.isLockStatus());
            }

    }
}
