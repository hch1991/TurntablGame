package com.hch.gotoplay;

import android.os.Parcel;
import android.os.Parcelable;

public class ActivityItemObj implements Parcelable {
    String activityName;
    int activityType; // 0 不显示 1 公园  2 爬山  3 逛街  4 在家

    public ActivityItemObj(String activityName, int activityType) {
        this.activityName = activityName;
        this.activityType = activityType;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }

    public int getActivityType() {
        return activityType;
    }

    public void setActivityType(int activityType) {
        this.activityType = activityType;
    }

    protected ActivityItemObj(Parcel in) {
        activityName = in.readString();
        activityType = in.readInt();
    }

    public static final Creator<ActivityItemObj> CREATOR = new Creator<ActivityItemObj>() {
        @Override
        public ActivityItemObj createFromParcel(Parcel in) {
            return new ActivityItemObj(in);
        }

        @Override
        public ActivityItemObj[] newArray(int size) {
            return new ActivityItemObj[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(activityName);
        dest.writeInt(activityType);
    }
}
