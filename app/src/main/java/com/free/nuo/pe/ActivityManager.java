package com.free.nuo.pe;


import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

public class ActivityManager extends Application {
    /**
     * 建立链表集合
     */
    private static List<Activity> activityList = new LinkedList<Activity>();

    private static ActivityManager instance;

    private ActivityManager() {

    }

    public static ActivityManager getInstance() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    public List<Activity> getActivityList() {
        return activityList;
    }

    public Activity getActivity(Activity activity) {
        return activityList.get(activityList.indexOf(activity));
    }

    public boolean delActivity(Activity activity) {
        return activityList.remove(activity);
    }


    public void exit() {

        //从后面往前面Kill
        for (int i = activityList.size() - 1; i >= 0; i--) {
            Activity activity = activityList.get(i);
            if (!activity.isFinishing()) {
                activity.finish();
            }
        }

        int id = android.os.Process.myPid();
        if (id != 0) {
            android.os.Process.killProcess(id);
        }
    }
}
