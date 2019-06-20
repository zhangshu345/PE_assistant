package com.free.nuo.pe;


import android.app.Activity;
import android.app.Application;

import java.util.LinkedList;
import java.util.List;

/**
 * 全局活动管理器
 * 单例
 *
 * <p>
 *
 * @author yanxiaonuo
 * @email yanxiaonuo@foxmail.com
 */
public class ActivityManager extends Application {
    /**
     * 建立链表集合
     */
    private static List<Activity> activityList = new LinkedList<Activity>();

    /**
     * ActivityManager 实例
     */
    private static ActivityManager instance;

    /**
     * 私有化方法
     */
    private ActivityManager() {

    }

    /**
     * 获取单例对象
     *
     * @return ActivityManager
     */
    public static ActivityManager getInstance() {
        if (instance == null) {
            instance = new ActivityManager();
        }
        return instance;
    }


    /**
     * 往ActivityManager 添加 Activity
     *
     * @param activity
     */
    public void addActivity(Activity activity) {
        activityList.add(activity);
    }


    /**
     * 获取ActivityManager--activityList
     *
     * @return
     */
    public List<Activity> getActivityList() {
        return activityList;
    }


    /**
     * 删除ActivityManager中的activity
     *
     * @param activity :待删除的activity
     * @return 是否删除成功
     */
    public boolean delActivity(Activity activity) {
        if (activity != null) {
            return activityList.remove(activity);
        }
        return false;
    }


    /**
     * 一键杀死所有进程，清理内存。
     */
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
