package king.chengwu.com.chengwuapp.test;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.view.View;

import chengwu.com.chengwuapp.R;
import king.chengwu.com.chengwuapp.ToastUtil;

public class AlarmActivity extends Activity {
    private int count;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);
        findViewById(R.id.alarm).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ToastUtil.showNormalToast("设置成功:" + count);
                    long triggerAtTime = SystemClock.elapsedRealtime() + 5 * 1000;
                    Intent intent = new Intent(AlarmActivity.this, AlarmService.class);
                    intent.putExtra("COUNT", count + "");
                    count++;
                    PendingIntent pi = PendingIntent.getService(AlarmActivity.this, 0, intent, 0);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);//开启提醒
                } catch (Exception e) {
                    ToastUtil.showNormalToast(e.toString());
                }
            }
        });
        findViewById(R.id.alarmRepeat).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    ToastUtil.showNormalToast("repeat 设置成功:" + count);
                    long triggerAtTime = SystemClock.elapsedRealtime() + 5 * 1000;
                    Intent intent = new Intent(AlarmActivity.this, AlarmService.class);
                    intent.putExtra("COUNT", "repeat-" + count);
                    count++;
                    PendingIntent pi = PendingIntent.getService(AlarmActivity.this, 0, intent, 0);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        alarmManager.setAndAllowWhileIdle(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
                    } else {
                        alarmManager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
                    }
//                    alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, 5 * 1000, pi);//开启提醒
                } catch (Exception e) {
                    ToastUtil.showNormalToast(e.toString());
                }
            }
        });
        findViewById(R.id.cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(AlarmActivity.this, AlarmService.class);
                    PendingIntent pi = PendingIntent.getService(AlarmActivity.this, 0, intent, 0);
                    AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                    alarmManager.cancel(pi);
                    ToastUtil.showNormalToast("取消成功");
                } catch (Exception e) {
                    ToastUtil.showNormalToast(e.toString());
                }
            }
        });


        findViewById(R.id.work).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
