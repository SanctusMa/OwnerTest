package king.chengwu.com.chengwuapp.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import chengwu.com.chengwuapp.R;
import king.chengwu.com.chengwuapp.ToastUtil;

/**
 * Created by shmyhui on 2017/4/11.
 */

public class C_Activity extends Activity implements View.OnClickListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("ThApplicationC", "C-onCreate");
        setContentView(R.layout.activity_main);
        ((TextView) findViewById(R.id.tvCopy)).setText("Im C_Activity ");
        findViewById(R.id.tv).setOnClickListener(this);
        findViewById(R.id.tv1).setOnClickListener(this);
        findViewById(R.id.tv2).setOnClickListener(this);
        findViewById(R.id.tv3).setOnClickListener(this);
        findViewById(R.id.tv4).setOnClickListener(this);
        findViewById(R.id.progressBarCustom).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.tv:
                sendBroadcast(new Intent("hahahahaha"));
                break;
            case R.id.tv1:
                sendBroadcast(new Intent("hehehehehe"));
                break;
            case R.id.tv2:
                sendBroadcast(new Intent("xixixixixi"));
                break;
            case R.id.tv3:
                sendBroadcast(new Intent("ohohohohoh"));
                break;
            case R.id.tv4:
                intent = new Intent("gogogogogo");
                intent.putExtra("lalalalala", "lalalalala");
                sendBroadcast(intent);
                break;
            case R.id.progressBarCustom:
                moveTaskToBack(true);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("ThApplicationC", "C-onStart");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.e("ThApplicationC", "C-onStop");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.e("ThApplicationC", "C-onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.e("ThApplicationC", "C-onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.e("ThApplicationC", "C-onDestroy");
    }

    @Override
    public void finish() {
        super.finish();
        Log.e("ThApplicationC", "C-finish");
    }

    long lastTime;

    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() - lastTime < 200) {
            ToastUtil.cancelTosat();
            FinishActivity.exitApp(this);
        } else {
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
