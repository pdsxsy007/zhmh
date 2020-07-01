package io.cordova.zhmh.utils;

import android.app.Activity;
import android.app.Application;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wangcongming.
 */

public class FinishActivity extends Application {
    private static List<Activity> lists = new ArrayList<>();
    public static void addActivity(Activity activity) {
        lists.add(activity);
    }
    public static void clearActivity() {
        if (lists != null) {
            for (Activity activity : lists) {
                activity.finish();
            }

            lists.clear();
        }
    }
}
