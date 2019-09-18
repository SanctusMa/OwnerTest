package king.chengwu.com.chengwuapp.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import chengwu.com.chengwuapp.R;

/**
 * Created by shmyhui on 2017/4/11.
 */

public class B_Activity extends Activity {

    public static B_Activity bInstance;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.e("ThApplication", "B-onCreate");
        ((TextView)findViewById(R.id.tvCopy)).setText("Im B_Activity ");
        bInstance = this;
        findViewById(R.id.tv).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(A_Activity.aInstance, C_Activity.class);
                bInstance.startActivity(intent);
                sendBroadcast(new Intent("heiheiheiheihei"));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("ThApplication", "B-onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("ThApplication", "B-onStop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("ThApplication", "B-onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("ThApplication", "B-onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("ThApplication", "B-onDestroy");
    }
}
