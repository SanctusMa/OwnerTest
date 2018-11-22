package king.chengwu.com.chengwuapp.test;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import chengwu.com.chengwuapp.R;

/**
 * Created by shmyhui on 2017/4/21.
 */

public class ScrollingImgActivity extends Activity implements View.OnClickListener {
    private View viewVip;
    private RecyclerView recyclerView;
    private Animation animationDu;
    private Animation animationUd;
    private boolean showServiceMenu = false;

    @Override
    public void onCreate(@Nullable final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.scrolling_img_activity);
        viewVip = findViewById(R.id.vipView);
        findViewById(R.id.vipBtn).setOnClickListener(this);
        animationDu = AnimationUtils.loadAnimation(this, R.anim.translate_down_up);
        animationUd = AnimationUtils.loadAnimation(this, R.anim.translate_up_down);
        animationDu.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                showServiceMenu = true;

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        animationUd.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                showServiceMenu = false;
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
//        recyclerView = (RecyclerView) findViewById(R.id.scrollingRecyclerView);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false) {
//
//        });?
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.vipBtn:
                if (showServiceMenu)
                    viewVip.startAnimation(animationUd);
                else
                    viewVip.startAnimation(animationDu);
                break;
        }
    }

    class Adapter extends RecyclerView.Adapter<Holder> {

        @Override
        public Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new Holder(LayoutInflater.from(ScrollingImgActivity.this).inflate(R.layout.scrolling_img_item, parent, false));
        }

        @Override
        public void onBindViewHolder(Holder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }

    class Holder extends RecyclerView.ViewHolder {

        public Holder(View itemView) {
            super(itemView);
        }
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static float dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (dpValue * scale + 0.5f);
    }
}
