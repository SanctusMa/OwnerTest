package king.chengwu.com.chengwuapp.activity_national_day;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import king.chengwu.com.chengwuapp.ThApplication;


/**
 * Created by 金城武 on 2016/9/26.
 */
public class ActiveConfig {
    private static final String TH_SHARED_PREFERENCES = "TH_SHARED_PREFERENCES_V3_0";

    //当日是否提示过可玩
    private static final String ACTIVE_NOTICEED_TODAY = "activeCanPlay";
    //今天是否玩过游戏
    private static final String ACTIVE_TODAY_PLAY = "activeTodayPlay";
    //今天的上一局游戏获得的T币数是否成功发送至服务器
    private static final String ACTIVE_LAST_GAME_SEND_DATA = "activeLastGameSendData";
    //今天的上一局游戏获得的T币数
    private static final String ACTIVE_LAST_GAME_COIN_COUNT = "activeLastGameCoinCount";


    /**
     * 存储本日是否已提示过活动
     *
     * @param dayNum 记录当天是几号
     */
    public static void setActiveNoticedDate(String dayNum) {
        SharedPreferences prefs = ThApplication.getInstance().getSharedPreferences(TH_SHARED_PREFERENCES, 0);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(ACTIVE_NOTICEED_TODAY, dayNum);
        prefsEditor.apply();
    }

    /**
     * 读取本日是否已提示过活动
     *
     * @return 记录下来的是几号
     */
    @Nullable
    public static String getActiveNoticedDate() {
        SharedPreferences prefs = ThApplication.getInstance().getSharedPreferences(TH_SHARED_PREFERENCES, 0);
        return prefs.getString(ACTIVE_NOTICEED_TODAY, "");
    }


    /**
     * 存储本日以及之前是否玩过游戏,记录日期
     *
     * @param date
     */
    public static void setActivePlayDate(String date) {
        SharedPreferences prefs = ThApplication.getInstance().getSharedPreferences(TH_SHARED_PREFERENCES, 0);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putString(ACTIVE_TODAY_PLAY, date);
        prefsEditor.apply();
    }

    /**
     * 读取本日以及之前是否玩过游戏，记录日期
     *
     * @return
     */
    @Nullable
    public static String getActivePlayDate() {
        SharedPreferences prefs = ThApplication.getInstance().getSharedPreferences(TH_SHARED_PREFERENCES, 0);
        return prefs.getString(ACTIVE_TODAY_PLAY, "");
    }

    /**
     * 存储本日以及之前玩过的上一局游戏数据是否成功发送至服务器
     *
     * @param sendSuccess true 成功 false 失败
     */
    public static void setActiveDataSendSuccess(boolean sendSuccess) {
        SharedPreferences prefs = ThApplication.getInstance().getSharedPreferences(TH_SHARED_PREFERENCES, 0);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putBoolean(ACTIVE_LAST_GAME_SEND_DATA, sendSuccess);
        prefsEditor.apply();
    }

    /**
     * 读取本日以及之前玩过的上一局游戏数据是否成功发送至服务器
     *
     * @return true 成功 false 失败 默认值true，避免第一次没有该缓存而出现重复请求发送的情况
     */
    @Nullable
    public static boolean getActiveDataSendSuccess() {
        SharedPreferences prefs = ThApplication.getInstance().getSharedPreferences(TH_SHARED_PREFERENCES, 0);
        return prefs.getBoolean(ACTIVE_LAST_GAME_SEND_DATA, true);
    }

    /**
     * 存储本日以及之前玩过的上一局游戏数据
     *
     * @param count
     */
    public static void setActiveData(int count) {
        SharedPreferences prefs = ThApplication.getInstance().getSharedPreferences(TH_SHARED_PREFERENCES, 0);
        SharedPreferences.Editor prefsEditor = prefs.edit();
        prefsEditor.putInt(ACTIVE_LAST_GAME_COIN_COUNT, count);
        prefsEditor.apply();
    }

    /**
     * 读取本日以及之前玩过的上一局游戏数据
     *
     * @return
     */
    @Nullable
    public static int getActiveData() {
        SharedPreferences prefs = ThApplication.getInstance().getSharedPreferences(TH_SHARED_PREFERENCES, 0);
        return prefs.getInt(ACTIVE_LAST_GAME_COIN_COUNT, 0);
    }
}
