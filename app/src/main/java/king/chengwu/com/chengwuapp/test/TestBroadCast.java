package king.chengwu.com.chengwuapp.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by shmyhui on 2017/4/17.
 */

public class TestBroadCast extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, this.getClass().getName(), Toast.LENGTH_SHORT).show();
        Log.e(this.getClass().getName(), this.getClass().getName());
    }
}
