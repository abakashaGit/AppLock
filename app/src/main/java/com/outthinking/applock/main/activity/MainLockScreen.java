package com.outthinking.applock.main.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.outthinking.applock.R;
import com.outthinking.applock.main.fragments.FragmentAppList;

public class MainLockScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_lock_screen);
        try {
            getFragmentManager().beginTransaction().add(R.id.main_fragment_holder, new FragmentAppList()).addToBackStack("appList").commit();
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
