package king.chengwu.com.chengwuapp.test;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import chengwu.com.chengwuapp.R;

/**
 * Created by shmyhui on 2017/4/11.
 */

public class C_Activity extends Activity implements View.OnClickListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((TextView) findViewById(R.id.tv)).setText("Im C_Activity ");
        findViewById(R.id.tv).setOnClickListener(this);
        findViewById(R.id.tv1).setOnClickListener(this);
        findViewById(R.id.tv2).setOnClickListener(this);
        findViewById(R.id.tv3).setOnClickListener(this);
        findViewById(R.id.tv4).setOnClickListener(this);
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
        }
    }
}
