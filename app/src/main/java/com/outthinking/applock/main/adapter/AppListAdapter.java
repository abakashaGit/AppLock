package com.outthinking.applock.main.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.outthinking.applock.R;
import com.outthinking.applock.main.data.MyDatabaseHelper;
import com.outthinking.applock.main.model.AppDetails;

import java.util.List;

/**
 * Created by Outthinking11 on 18-07-2016.
 */
public class AppListAdapter extends BaseAdapter {

    private List<AppDetails> listAppDetails;
    private MyDatabaseHelper dbHelper;
    private Context context;

    public AppListAdapter(List<AppDetails> listAppDetails, Context context,MyDatabaseHelper dbHelper) {
        this.listAppDetails = listAppDetails;
        this.context = context;
        this.dbHelper =dbHelper;
    }

    @Override
    public int getCount() {
        return listAppDetails.size();
    }

    @Override
    public Object getItem(int i) {
        return listAppDetails.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int position, View view, ViewGroup viewGroup) {
        AppViewHolder viewHolder;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.item_view_applist, viewGroup, false);
            viewHolder = new AppViewHolder();
            viewHolder.tvAppName = (TextView) view.findViewById(R.id.tvAppName);
            viewHolder.imgviewIcon = (ImageView) view.findViewById(R.id.imgViewAppIcon);
            viewHolder.tvSecurityHint = (TextView) view.findViewById(R.id.tvSecurityHint);
            //viewHolder.appLockSwitch = (Switch) view.findViewById(R.id.switchAppLock);
            viewHolder.viewIndicator = view.findViewById(R.id.viewLockIndicator);
            view.setTag(viewHolder);
        } else {
            viewHolder = (AppViewHolder) view.getTag();
        }
        viewHolder.imgviewIcon.setImageDrawable(listAppDetails.get(position).getIcon());
        viewHolder.tvAppName.setText(listAppDetails.get(position).getName());
        viewHolder.tvSecurityHint.setText("Protect " + listAppDetails.get(position).getName() + " from being snooped");
        dbHelper.openDatabase();
        boolean isLokced=dbHelper.checkLockStatus(listAppDetails.get(position).getPackageName().toString());
        if (isLokced){
            viewHolder.viewIndicator.setBackgroundResource(R.drawable.locked);
        }else{
            viewHolder.viewIndicator.setBackgroundResource(R.drawable.lock_open);
        }
//        viewHolder.appLockSwitch.setChecked(dbHelper.checkLockStatus(listAppDetails.get(position).getPackageName().toString()));
//        viewHolder.appLockSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
//            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                // true if the switch is in the On position
//                if (isChecked) {
//                    dbHelper.openDatabase();
//                    dbHelper.changeLockStatusOn(listAppDetails.get(position).getPackageName().toString());
//                    dbHelper.closeDatabase();
//                } else {
//                    dbHelper.openDatabase();
//                    dbHelper.changeLockStatusOff(listAppDetails.get(position).getPackageName().toString());
//                    dbHelper.closeDatabase();
//                }
//            }
//        });
        dbHelper.closeDatabase();

        return view;
    }


    public static class AppViewHolder {
        TextView tvAppName, tvSecurityHint;
        ImageView imgviewIcon;
        // Switch appLockSwitch;
        View viewIndicator;
    }
}
