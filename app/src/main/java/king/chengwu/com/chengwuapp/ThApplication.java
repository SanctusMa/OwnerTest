package king.chengwu.com.chengwuapp;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by shmyh on 2017/9/14.
 */

public class ThApplication extends Application {
    public static ThApplication application;

    public static ThApplication getInstance() {
        if (null == application)
            application = new ThApplication();
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
}
