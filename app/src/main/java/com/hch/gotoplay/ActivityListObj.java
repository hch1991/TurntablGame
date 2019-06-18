package com.hch.gotoplay;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class ActivityListObj {
    ArrayList<ActivityItemObj> activityItemObjs;

    public ArrayList<ActivityItemObj> getActivityItemObjs() {
        return activityItemObjs;
    }

    public void setActivityItemObjs(ArrayList<ActivityItemObj> activityItemObjs) {
        this.activityItemObjs = activityItemObjs;
    }
}
