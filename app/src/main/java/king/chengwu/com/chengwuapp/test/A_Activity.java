package king.chengwu.com.chengwuapp.test;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import chengwu.com.chengwuapp.R;

/**
 * Created by shmyhui on 2017/4/11.
 */

public class A_Activity extends Activity {
    public static A_Activity aInstance;
    private TestBroadCast broadCast;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((TextView) findViewById(R.id.tv)).setText("Im A_Activity ");
        aInstance = this;
        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(A_Activity.this, B_Activity.class);
                startActivity(intent);
            }
        });

        broadCast = new TestBroadCast();
        registerReceiver(broadCast, new IntentFilter("heiheiheiheihei"));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadCast);
    }
}
