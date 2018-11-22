package king.chengwu.com.chengwuapp.test;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * Created by shmyhui on 2017/4/20.
 */

public class TestBroadCastActive extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        switch (intent.getAction()) {
            case "hahahahaha":
                Toast.makeText(context, "hahahahaha", Toast.LENGTH_SHORT).show();
                Log.e("ha", "hahaha");
                break;
            case "xixixixixi":
                Toast.makeText(context, "xixixixixi", Toast.LENGTH_SHORT).show();
                Log.e("xi", "xixixi");
                break;
            case "hehehehehe":
                Toast.makeText(context, "hehehehehe", Toast.LENGTH_SHORT).show();
                Log.e("he", "hehehe");
                break;
            case "gogogogogo":
                String str = intent.getStringExtra("lalalalala");
                Toast.makeText(context, "gogogogogo" + "    " + str, Toast.LENGTH_SHORT).show();
                Log.e("go", "gogogo" + str);
                break;
            case "ohohohohoh":
                Toast.makeText(context, "ohohohohoh", Toast.LENGTH_SHORT).show();
                Log.e("oh", "ohoho");
                break;
        }
    }
}
