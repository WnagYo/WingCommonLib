package com.wint_ti.commonlib.utils;

import android.app.Activity;
import android.content.Context;

import com.blankj.ALog;

import java.util.Stack;

/**
 * activity进栈 出栈工具类
 */

public class AppManager {

    private static Stack<Activity> activityStack;
    private static AppManager instance;

    private AppManager() {

    }

    /**
     * 单一实例
     */
    public static AppManager getAppManager() {
        if (instance == null) {
            instance = new AppManager();
        }
        return instance;
    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new Stack<Activity>();
        }
        ALog.i("-------加入管理--------" + activity.getClass().getSimpleName() + "-------");
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = null;
        if (activityStack != null) {
            activity = activityStack.lastElement();
        }
        return activity;
    }

    /**
     * 移除当前activity 即最后一个activity
     */
    public void removeCurrentActivity() {
        Activity activity = activityStack.lastElement();
        if (activity != null) {
            removeActivity(activity);
        }
    }

    /**
     * 移除指定的Activity
     */
    public void removeActivity(Activity activity) {
        if (activity != null) {
            ALog.i("-------移除管理--------" + activity.getClass().getSimpleName() + "-------");
            activityStack.remove(activity);
            activity = null;
        }
    }

    /**
     * 移除指定类名的Activity
     */
    public void removeActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                removeActivity(activity);
                break;
            }
        }
    }

    /**
     * 结束除了指定名称的activity 外 所有activity
     */
    public void finishNoActivity(Class<?> cls) {
        for (int i = 0; i < activityStack.size(); i++) {
            if (!activityStack.get(i).getClass().equals(cls)) {
                ALog.e("结束：" + activityStack.get(i).getClass());
                activityStack.get(i).finish();
            }
        }
    }


    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
    }

    /**
     * 获取指定的Activity
     *
     */
    public static Activity getActivity(Class<?> cls) {
        if (activityStack != null)
            for (Activity activity : activityStack) {
                if (activity.getClass().equals(cls)) {
                    return activity;
                }
            }
        return null;
    }

    /**
     * 退出应用程序
     */
    public void AppExit(Context context) {
        try {
            finishAllActivity();
            // 杀死该应用进程
            android.os.Process.killProcess(android.os.Process.myPid());
            System.exit(0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
