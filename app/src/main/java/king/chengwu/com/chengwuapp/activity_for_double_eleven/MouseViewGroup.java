package king.chengwu.com.chengwuapp.activity_for_double_eleven;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.drawable.AnimationDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import chengwu.com.chengwuapp.R;
import king.chengwu.com.chengwuapp.activity_national_day.ActiveGameActivity;

/**
 * Created by Sanctus on 2016/10/12.
 */

public class MouseViewGroup extends ViewGroup {
    private static final int TAG_BIG = 5;
    private static final int TAG_LITTLE = 1;
    private static final int TAG_TAI = -1;
    private static final int RUNNABLE_ACTIVE_VIEW = 0x1025;
    private static final int POSITION_ACTIVE_VIEW = 0x1025;
    private ActiveGameActivity activity;
    //    private ConcurrentLinkedQueue<MousePosition> queuePosition;
    private List<MousePosition> listPosition;
    private ConcurrentLinkedQueue<ImageView> queueActiveView;
    private ConcurrentLinkedQueue<ImageView> queueScoreView;
    private int screenWidth;
    private int screenHeight;
    private int mouseRadius = (int) dip2px(75) / 2;
    private LayoutParams layoutParams = new LayoutParams(mouseRadius * 2, mouseRadius * 2);
    private int widthImg = (int) dip2px(30);
    private int heightImg = (int) dip2px(20);
    private int heightScoreView = (int) dip2px(300);
    private LayoutParams layoutParamsTxt = new LayoutParams(widthImg, heightImg);
//    int countBig = 0;
//    int countLittle = 0;
//    int countTai = 0;

    public MouseViewGroup(ActiveGameActivity activity) {
        super(activity);
        this.activity = activity;
    }

    public MouseViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MouseViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
    }

    public void startRun(int intervalOfBig, int intervalOfLittle, int intervalOfTai) {
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        screenHeight = getResources().getDisplayMetrics().heightPixels;
//        queuePosition = new ConcurrentLinkedQueue<>();
        listPosition = new ArrayList<>();
        queueActiveView = new ConcurrentLinkedQueue<>();
        queueScoreView = new ConcurrentLinkedQueue<>();
        float x, y;
        int l, t, r, b;

        for (int i = 0; i < 5; i++) {
            x = screenWidth * (2 * i + 1) / 10;
            for (int j = 0; j < 5; j++) {
                y = screenWidth * (j - 2) / 5 + screenHeight / 2;
                l = (int) (x - mouseRadius);
                r = (int) (x + mouseRadius);
                t = (int) (y - mouseRadius);
                b = (int) (y + mouseRadius);
//                queuePosition.add(new MousePosition(l, t, r, b));
                listPosition.add(new MousePosition(l, t, r, b));
            }
        }

        for (int i = 0; i < activity.timeCount; i += intervalOfLittle) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    MousePosition mousePosition;
//                    int num = activity.random.nextInt(queuePosition.size());
//                    for (int k = 0; k < queuePosition.size(); k++) {
//                        mousePosition = queuePosition.poll();
//                        if (k == num) {
//                            mousePosition.tag = 1;
//                            activeView(mousePosition);
//                            break;
//                        }
//                        queuePosition.add(mousePosition);
//                    }
                    int index;
                    while (true) {
                        index = activity.random.nextInt(25);
                        mousePosition = listPosition.get(index);
                        if (mousePosition.using == 0) {
                            mousePosition.using = 1;
                            mousePosition.pos = index;
                            mousePosition.tag = TAG_LITTLE;
//                            LogUtil.e("CountLittle:" + (++countLittle));
                            activeView(mousePosition);
                            break;
                        }
                    }
                }
            }, i);
        }
        for (int i = 0; i < activity.timeCount; i += intervalOfBig) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    MousePosition mousePosition;
                    int index;
                    while (true) {
                        index = activity.random.nextInt(25);
                        mousePosition = listPosition.get(index);
                        if (mousePosition.using == 0) {
                            mousePosition.using = 1;
                            mousePosition.pos = index;
                            mousePosition.tag = TAG_BIG;
//                            LogUtil.e("CountBig:" + (++countBig));
                            activeView(mousePosition);
                            break;
                        }
                    }
                }
            }, i);
        }

        for (int i = 0; i < activity.timeCount; i += intervalOfTai) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    MousePosition mousePosition;
                    int index;
                    while (true) {
                        index = activity.random.nextInt(25);
                        mousePosition = listPosition.get(index);
                        if (mousePosition.using == 0) {
                            mousePosition.using = 1;
                            mousePosition.pos = index;
                            mousePosition.tag = TAG_TAI;
//                            LogUtil.e("CountTai:" + (++countTai));
                            activeView(mousePosition);
                            break;
                        }
                    }
                }
            }, i);
        }
    }

    private void activeView(MousePosition mousePosition) {
        ImageView imageView = queueActiveView.poll();
        if (null == imageView) {
            imageView = new ImageView(activity);
            addViewInLayout(imageView, 0, layoutParams, true);
            measureChildren(MeasureSpec.makeMeasureSpec(100, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(100, MeasureSpec.EXACTLY));
        } else {
            imageView.setEnabled(true);
            imageView.setVisibility(VISIBLE);
        }
//        imageView.setTag(POSITION_ACTIVE_VIEW, mousePosition);
        imageView.layout(mousePosition.left, mousePosition.top, mousePosition.right, mousePosition.bottom);
        if (mousePosition.tag == TAG_BIG)
            imageView.setImageResource(R.drawable.d_active_mouse_big);
        else if (mousePosition.tag == TAG_LITTLE)
            imageView.setImageResource(R.drawable.d_active_mouse_little);
        else
            imageView.setImageResource(R.drawable.d_active_mouse_tai);
        AnimationDrawable animation = (AnimationDrawable) imageView.getDrawable();
        animation.start();
        activeViewGone(imageView, animation, mousePosition);
    }

    private void activeViewGone(final ImageView imageView, final AnimationDrawable animation, final MousePosition mousePosition) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                imageView.setEnabled(false);
                imageView.setVisibility(GONE);
                mousePosition.using = 0;
                listPosition.set(mousePosition.pos, mousePosition);
//                queuePosition.add((MousePosition) imageView.getTag());
                queueActiveView.add(imageView);
            }
        };
        postDelayed(runnable, 1100);
        imageView.setTag(RUNNABLE_ACTIVE_VIEW, runnable);
        imageView.setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_POINTER_DOWN) {
                    //移除延时操作，避免该imageView被重新利用的时候，定时器被触发了，出现闪一下的画面
                    imageView.setEnabled(false);
                    animation.stop();
                    imageView.removeCallbacks((Runnable) imageView.getTag(RUNNABLE_ACTIVE_VIEW));
                    activity.getCoin += mousePosition.tag;
                    if (activity.getCoin < 0) {
                        activity.getCoin = 0;
                    }
                    if (activity.getCoin > activity.countCoin) {
                        activity.getCoin = activity.countCoin;
                    }
                    activity.txtCoinCount.setText(activity.getCoin + "");
                    switch (mousePosition.tag) {
                        case TAG_BIG:
                            imageView.setImageResource(R.drawable.d_active_b_beat);
                            activity.soundPool.play(2, 1f, 1f, 1, 0, 1);
                            break;
                        case TAG_LITTLE:
                            imageView.setImageResource(R.drawable.d_active_a_beat);
                            activity.soundPool.play(2, 1f, 1f, 1, 0, 1);
                            break;
                        case TAG_TAI:
                            imageView.setImageResource(R.drawable.d_active_c_beat);
                            activity.soundPool.play(3, 1f, 1f, 1, 0, 1);
                            break;
                    }
                    Animation animationAlpha = imageView.getAnimation();
                    if (null == animationAlpha) {
                        animationAlpha = new AlphaAnimation(1, 0);
                        animationAlpha.setDuration(300);
                        imageView.setAnimation(animationAlpha);
                        animationAlpha.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                imageView.setVisibility(GONE);
                                mousePosition.using = 0;
                                listPosition.set(mousePosition.pos, mousePosition);
//                queuePosition.add((MousePosition) imageView.getTag());
                                queueActiveView.add(imageView);
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }
                    imageView.startAnimation(animationAlpha);
                    scoreView(mousePosition);
                }
                return false;
            }
        });
    }

    private void scoreView(MousePosition mousePosition) {
        ImageView scoreView = queueScoreView.poll();
        if (null == scoreView) {
            scoreView = new ImageView(activity);
            scoreView.setBackgroundResource(0);
            addView(scoreView, widthImg, heightImg);
            addViewInLayout(scoreView, 0, layoutParamsTxt, true);
            measureChildren(MeasureSpec.makeMeasureSpec(100, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(100, MeasureSpec.EXACTLY));
        } else {
            scoreView.setVisibility(VISIBLE);
        }
        if (mousePosition.tag == TAG_LITTLE)
            scoreView.setImageResource(R.drawable.d_active_score_little);
        else if (mousePosition.tag == TAG_BIG)
            scoreView.setImageResource(R.drawable.d_active_score_big);
        else
            scoreView.setImageResource(R.drawable.d_active_score_tai);
        int left = mousePosition.left + screenWidth / 10 - widthImg / 2;
        int top = mousePosition.top - heightImg;
        int right = mousePosition.left + screenWidth / 10 + widthImg / 2;
        int bottom = mousePosition.top;
        scoreView.layout(left, top, right, bottom);
        animationScoreView(scoreView);
    }

    private void animationScoreView(final ImageView scoreView) {
        AnimationSet animationSet;
        Animation animation = scoreView.getAnimation();
        if (null == animation) {
            Animation animationT = new TranslateAnimation(0, 0, 0, -heightScoreView);
            Animation animationA = new AlphaAnimation(1, 0);
            animationSet = new AnimationSet(true);
            animationSet.addAnimation(animationT);
            animationSet.addAnimation(animationA);
            animationSet.setDuration(1000);
//            animationSet.setInterpolator(new AccelerateInterpolator(1.1f));
            animationSet.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    scoreView.setVisibility(GONE);
                    scoreView.layout(0, 0, 0, 0);
                    scoreView.clearAnimation();
                    queueScoreView.add(scoreView);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            scoreView.setAnimation(animationSet);
        } else {
            animationSet = (AnimationSet) animation;
        }
        animationSet.reset();
        scoreView.startAnimation(animationSet);
    }

    public float dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (dpValue * scale + 0.5f);
    }

    class MousePosition {
        public int left;
        public int top;
        public int right;
        public int bottom;
        public int using = 0; //  1：有地鼠  0：无地鼠
        public int pos;
        public int tag;

        public MousePosition(int l, int t, int r, int b) {
            left = l;
            right = r;
            top = t;
            bottom = b;
        }
    }
}
