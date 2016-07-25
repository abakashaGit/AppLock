package com.outthinking.applock.main.model;

import android.graphics.drawable.Drawable;

/**
 * Created by Outthinking11 on 18-07-2016.
 */
public class AppDetails {
    private CharSequence packageName;
    private CharSequence name;
    private Drawable icon;
    private boolean lockStatus;

    public AppDetails() {
    }

    public boolean isLockStatus() {
        return lockStatus;
    }

    public void setLockStatus(boolean lockStatus) {
        this.lockStatus = lockStatus;
    }

    public CharSequence getPackageName() {
        return packageName;
    }

    public void setPackageName(CharSequence label) {
        this.packageName = label;
    }

    public CharSequence getName() {
        return name;
    }

    public void setName(CharSequence name) {
        this.name = name;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}
