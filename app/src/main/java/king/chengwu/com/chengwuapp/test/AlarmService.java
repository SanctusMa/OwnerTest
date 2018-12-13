package king.chengwu.com.chengwuapp.test;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

import king.chengwu.com.chengwuapp.ToastUtil;

public class AlarmService extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        ToastUtil.showNormalToast("Alarm Start --  " + intent.getStringExtra("COUNT"));
        return super.onStartCommand(intent, flags, startId);
    }
}
