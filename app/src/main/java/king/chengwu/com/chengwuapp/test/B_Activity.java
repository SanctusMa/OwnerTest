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

public class B_Activity extends Activity {

    public static B_Activity bInstance;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ((TextView)findViewById(R.id.tv)).setText("Im B_Activity ");
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
}
