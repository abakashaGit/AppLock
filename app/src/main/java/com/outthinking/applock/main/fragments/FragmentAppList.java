package com.outthinking.applock.main.fragments;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.outthinking.applock.R;
import com.outthinking.applock.main.adapter.AppListAdapter;
import com.outthinking.applock.main.data.MyDatabaseHelper;
import com.outthinking.applock.main.model.AppDetails;
import com.outthinking.applock.main.myutils.LoadAppsAsyncTask;
import com.pinlock.activity.SetPinActivity;

import java.util.List;

/**
 * Created by Outthinking11 on 18-07-2016.
 */
public class FragmentAppList extends Fragment {

    private AppListAdapter adapter;
    private List<AppDetails> listAppDetails;
    private MyDatabaseHelper dbHelper;
    private Button btnLockApps;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_applist, container, false);
        try {
            dbHelper=MyDatabaseHelper.getDatabaseInstance(getActivity());

            listAppDetails = new LoadAppsAsyncTask(getActivity()).execute().get();
            ListView listViewAppList = (ListView) rootView.findViewById(R.id.listViewAppList);
            btnLockApps= (Button) rootView.findViewById(R.id.btnLockApps);
            adapter = new AppListAdapter(listAppDetails, getActivity(),dbHelper);
            listViewAppList.setAdapter(adapter);
            listViewAppList.setOnItemClickListener(new ListView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                    String packageName = listAppDetails.get(position).getPackageName().toString();
                    dbHelper.openDatabase();
                    boolean isLocked = dbHelper.checkLockStatus(packageName);
                    if (isLocked){
                        dbHelper.changeLockStatusOff(listAppDetails.get(position).getPackageName().toString());
                    }else{
                        dbHelper.changeLockStatusOn(listAppDetails.get(position).getPackageName().toString());
                    }
                    dbHelper.closeDatabase();
                    adapter.notifyDataSetChanged();
                }
            });
            btnLockApps.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Toast.makeText(getActivity(),"locked",Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(getActivity(), SetPinActivity.class));
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
        return rootView;
    }
}
