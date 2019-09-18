package king.chengwu.com.chengwuapp;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.multidex.MultiDex;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import king.chengwu.com.chengwuapp.test.A_Activity;
import king.chengwu.com.chengwuapp.test.C_Activity;

/**
 * Created by shmyh on 2017/9/14.
 */

public class ThApplication extends Application {
    public static ThApplication application;
    private int mFinalCount = 0;

    public static ThApplication getInstance() {
        if (null == application)
            application = new ThApplication();
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            private List<View> listView = new ArrayList<>();

            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
            }

            @Override
            public void onActivityStarted(Activity activity) {
                mFinalCount++;
                //如果mFinalCount ==1，说明是从后台到前台
                if (mFinalCount == 1) {
                    //说明从后台回到了前台
                    Log.e("ThApplication", " 回到APP");
                    List<ActivityManager.RunningTaskInfo> list =
                            ((ActivityManager) activity.getSystemService(Context.ACTIVITY_SERVICE)).getRunningTasks(10);
                    for (ActivityManager.RunningTaskInfo taskInfo : list) {
                        LogUtil.e("TaskInfo:" + taskInfo.baseActivity.getClassName() + "  " + taskInfo.topActivity.getClassName());
                    }
                    if (!C_Activity.class.getName().equals(list.get(0).topActivity.getClassName())) {

                    }
//                        activity.startActivity(new Intent(activity, C_Activity.class));
                    long l1 = System.currentTimeMillis();
                    TextView tv = new TextView(activity);
                    tv.setBackgroundColor(Color.parseColor("#f25a2b"));
                    tv.setText("asdasd");
                    ViewGroup view = activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
                    view.addView(tv);
                    LogUtil.e("interval1:" + (System.currentTimeMillis() - l1));

                    long l2 = System.currentTimeMillis();
                    TextView tv1;
                    boolean needCreateView = true;
                    if (list.size() > 0) {
                        for (View view1 : listView) {
                            if (null != view1.getTag() && activity == view1.getTag()) {
                                view1.setVisibility(View.VISIBLE);
                                needCreateView = false;
                                break;
                            }
                        }
                    }
                    if (needCreateView) {
                        tv1 = new TextView(activity);
                        tv1.setBackgroundColor(Color.parseColor("#27a1e5"));
                        tv1.setText("new");
                        ((ViewGroup) activity.getWindow().findViewById(Window.ID_ANDROID_CONTENT)).addView(tv1);
                        listView.add(tv1);
                    }
                    LogUtil.e("interval2:" + (System.currentTimeMillis() - l2));
                }
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                mFinalCount--;
                //如果mFinalCount ==0，说明是前台到后台
                if (mFinalCount == 0) {
                    //说明从前台回到了后台
                    Log.e("ThApplication", " App进入后台");
                    if (activity instanceof A_Activity) {
//                        try {
//                            ((A_Activity)activity).getClass().getDeclaredField("finish");
//                        } catch (NoSuchFieldException e) {
//                            e.printStackTrace();
//                        }
                    }
                }
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {
                if (listView.size() > 0) {
                    for (View view : listView) {
                        if (null != view.getTag() && activity == view.getTag()) {
                            listView.remove(view);
                            break;
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
