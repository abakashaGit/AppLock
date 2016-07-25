package com.outthinking.applock.main.myutils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.outthinking.applock.main.data.MyDatabaseHelper;

/**
 * Created by Outthinking11 on 21-07-2016.
 */
public class ReceiverPackageUnInstall extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent != null) {
            String strPackage = intent.getData().getEncodedSchemeSpecificPart();//.toString().split(":")[1];
            MyDatabaseHelper dbHelper=MyDatabaseHelper.getDatabaseInstance(context);
            dbHelper.openDatabase();
            int val=dbHelper.deleteApp(strPackage);
            if (val == 1)
                Toast.makeText(context,"App UnInstalled",Toast.LENGTH_SHORT).show();
            dbHelper.closeDatabase();
        }
    }
}
