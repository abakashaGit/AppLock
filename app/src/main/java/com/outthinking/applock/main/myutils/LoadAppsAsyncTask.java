package com.outthinking.applock.main.myutils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;

import com.outthinking.applock.main.data.MyDatabaseHelper;
import com.outthinking.applock.main.model.AppDetails;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Outthinking11 on 18-07-2016.
 */
public class LoadAppsAsyncTask extends AsyncTask<Void, Void, List<AppDetails>> {

    private Context context;


    public LoadAppsAsyncTask(Context context) {
        this.context = context;
    }

    @Override
    protected List<AppDetails> doInBackground(Void... params) {
        PackageManager manager;
        List<AppDetails> appDetailsList;
        manager = context.getPackageManager();
        appDetailsList = new ArrayList<AppDetails>();

        Intent i = new Intent(Intent.ACTION_MAIN, null);
        i.addCategory(Intent.CATEGORY_LAUNCHER);

        List<ResolveInfo> availableActivities = manager.queryIntentActivities(i, 0);
        //instance of database
        MyDatabaseHelper dbInstance = MyDatabaseHelper.getDatabaseInstance(context);
        dbInstance.openDatabase();

        for (ResolveInfo ri : availableActivities) {
            AppDetails app = new AppDetails();
            app.setName(ri.loadLabel(manager));
            app.setIcon(ri.activityInfo.loadIcon(manager));
            app.setPackageName(ri.activityInfo.packageName);
            appDetailsList.add(app);
            //inserting to databse
            if (((AppCompatActivity) context).getPreferences(Context.MODE_PRIVATE).getBoolean("data_inserted", true))
                dbInstance.insertApp(app);

        }
        ((AppCompatActivity) context).getPreferences(Context.MODE_PRIVATE).edit().putBoolean("data_inserted", true).commit();

        //List<AppDetails> list = dbInstance.getAllAppDetails();
        dbInstance.closeDatabase();
        return appDetailsList;
    }

}
