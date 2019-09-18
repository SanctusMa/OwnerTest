package king.chengwu.com.chengwuapp.test;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import chengwu.com.chengwuapp.R;
import king.chengwu.com.chengwuapp.ToastUtil;

/**
 * Created by shmyhui on 2017/4/11.
 */

public class A_Activity extends Activity {
    public static A_Activity aInstance;
    private TestBroadCast broadCast;
    private boolean finish = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("ThApplication", "A-onCreate");
        setContentView(R.layout.activity_main);
        ((TextView) findViewById(R.id.tvCopy)).setText("Im A_Activity ");
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

        Spanned spanned = (Html.fromHtml("<font color='#999999'>" + "asdas" + "hahaha</font>"
                + "<font color='#000000'>阿瑟东</font>"));
        ((TextView) findViewById(R.id.tv4)).setText(Html.fromHtml("<font color='#999999'>" + "asdas" + "hahaha</font>"
                + "<font color='#000000'>阿瑟东</font>"));
        ((TextView) findViewById(R.id.tv3)).setText("Im A_Activity ");

        ((ProgressBar) findViewById(R.id.progressBar)).setProgress(50);

    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("ThApplication", "A-onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("ThApplication", "A-onStop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("ThApplication", "A-onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("ThApplication", "A-onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(broadCast);
        Log.e("ThApplication", "A-onDestroy");
    }

    @Override
    public void finish() {
        super.finish();
        Log.e("ThApplication", "A-finish");
    }

    long lastTime;
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastTime < 200){
            ToastUtil.cancelTosat();
            finish = true;
            finish();
        }else{
            ToastUtil.showNormalToast("双击退出泰然金融!!!");
            lastTime = System.currentTimeMillis();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    lastTime = 0;
                }
            }, lastTime);
        }
    }
}
