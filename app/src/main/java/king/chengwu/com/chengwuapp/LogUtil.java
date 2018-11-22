package king.chengwu.com.chengwuapp;

import android.util.Log;

/**
 * Created by shmyh on 2017/9/14.
 */

public class LogUtil {
    private static final String CHENG_WU_LOG_TAG = "ChengWuLogTag";

    public static void e(Throwable e) {
        Log.e(CHENG_WU_LOG_TAG, e.toString());
    }

    public static void e(String e) {
        Log.e(CHENG_WU_LOG_TAG, e);
    }
}
