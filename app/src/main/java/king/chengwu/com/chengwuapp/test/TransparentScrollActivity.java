package king.chengwu.com.chengwuapp.test;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import chengwu.com.chengwuapp.R;
import king.chengwu.com.chengwuapp.LogUtil;

/**
 * @author shmyh
 */
public class TransparentScrollActivity extends AppCompatActivity {

    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transparent_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        RecyclerView recyclerView = findViewById(R.id.recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new RecyclerView.Adapter<Holder>() {
            @NonNull
            @Override
            public Holder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return new Holder(LayoutInflater.from(TransparentScrollActivity.this)
                        .inflate(R.layout.transparent_item_view, viewGroup, false));
            }

            @Override
            public void onBindViewHolder(@NonNull Holder holder, int i) {
                holder.tv.setText("第" + i + "个TextView");
                if (i == 3) {
                    holder.view.setVisibility(View.VISIBLE);
                } else {
                    holder.view.setVisibility(View.GONE);
                }
            }

            @Override
            public int getItemCount() {
                return 10;
            }
        });

        findViewById(R.id.fab).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (recyclerView.getVisibility() == View.GONE) {
                    recyclerView.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.GONE);
                }
            }
        });

        View view = findViewById(R.id.animView1);
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        LogUtil.i(location[0] + " " + location[1]);
        TranslateAnimation animation = new TranslateAnimation(0, 0, 300, 150);
        animation.setDuration(2000);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                view.clearAnimation();
                view.getLocationOnScreen(location);
                LogUtil.i(location[0] + " " + location[1]);
                view.setAnimation(animation);
                view.startAnimation(animation);
            }
        });

        View view2 = findViewById(R.id.animView2);
        TranslateAnimation animation2 = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, 0,
                Animation.RELATIVE_TO_SELF, 0, Animation.RELATIVE_TO_SELF, -0.5f);
        animation2.setDuration(5000);
        view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                view.clearAnimation();
                view.setAnimation(animation2);
                view.startAnimation(animation2);
            }
        });

        TelephonyManager tm =  (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE);
        tm.getDeviceId();
    }

    public float dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (dpValue * scale + 0.5f);
    }

    class Holder extends RecyclerView.ViewHolder {
        private View view;
        private TextView tv;

        public Holder(@NonNull View itemView) {
            super(itemView);
            tv = itemView.findViewById(R.id.transparent_tv);
            view = itemView.findViewById(R.id.transparent_view);
        }
    }
}
