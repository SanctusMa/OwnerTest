package king.chengwu.com.chengwuapp.activity_national_day;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.TextView;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.concurrent.ConcurrentLinkedQueue;

import chengwu.com.chengwuapp.R;


/**
 * Created by 金城武 on 2016/9/18.
 */
public class ActiveGroup extends ViewGroup {
    private static final int TAG_BIG = 5;
    private static final int TAG_LITTLE = 1;
    private static final int TAG_BOMB = -1;
    public static final int KEY_ANIMATION = 0x1024;
    public static final float TIME_SHORT = 2000f;
    public static final float TIME_LONG = 2400f;

    private ActiveGameActivity activity;
    private ConcurrentLinkedQueue<ActiveView> queueActiveViewBig;
    private ConcurrentLinkedQueue<ActiveView> queueActiveViewLittle;
    private ConcurrentLinkedQueue<ActiveView> queueActiveViewBomb;
    private ConcurrentLinkedQueue<TextView> queueTxtViewCommon;
    private LinkedList<ActiveView> activeViewLinkedList = new LinkedList<>();

    //    private LayoutParams paramsBig = new LayoutParams((int) dip2px(80), (int) dip2px(80));
//    private LayoutParams paramsLittle = new LayoutParams((int) dip2px(60), (int) dip2px(60));
//    private LayoutParams paramsBomb = new LayoutParams((int) dip2px(70), (int) dip2px(70));
    //    private LayoutParams paramsTxt = new LayoutParams((int) dip2px(40), (int) dip2px(20));
    private int widthBig = (int) dip2px(80);
    private int widthLittle = (int) dip2px(60);
    private int widthBomb = (int) dip2px(80);
    private int widthTxt = (int) dip2px(30);
    private LayoutParams bomblayoutParams = new LayoutParams(widthBomb, widthBomb);
    private LayoutParams biglayoutParams = new LayoutParams(widthBig, widthBig);
    private LayoutParams littleLayoutParams = new LayoutParams(widthLittle, widthLittle);
    private LayoutParams txtlayoutParams = new LayoutParams(widthTxt, widthTxt);

    private int colorLucky = Color.parseColor("#f25a2b");
    private int colorBomb = Color.parseColor("#0086d1");
    private float speedYBig;
    private float speedYLittle;

    public ActiveGroup(ActiveGameActivity context) {
        super(context);
        this.activity = context;
    }

    @Override
    protected void onLayout(boolean b, int i, int i1, int i2, int i3) {

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        speedYBig = h / TIME_SHORT;
        speedYLittle = speedYBig / 2;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_POINTER_DOWN) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (child.getVisibility() == View.VISIBLE && child.isClickable()
                        && child.isEnabled()
                        && child.getLeft() < event.getX()
                        && child.getRight() > event.getX()
                        && child.getTop() - dip2px(50) < event.getY()
                        && child.getBottom() > event.getY()) {
                    child.performClick();
                }
            }
        }
        return true;
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (activity.timeCount <= 0) {
            return;
        }
        for (ActiveView view : activeViewLinkedList) {
            if (VISIBLE == getVisibility()) {
                switch ((int) view.getTag()) {
                    case TAG_BOMB:
                        view.animateLayout(true, queueActiveViewBomb, getWidth(), getHeight());
                        break;
                    case TAG_LITTLE:
                        view.animateLayout(true, queueActiveViewLittle, getWidth(), getHeight());
                        break;
                    case TAG_BIG:
                        view.animateLayout(true, queueActiveViewBig, getWidth(), getHeight());
                        break;
                }
            }
        }
        super.dispatchDraw(canvas);
    }

    public void startRun(HashSet<Integer> hashSetBig, int intervalOfCreateLittle, int intervalOfCreateBomb) {
        queueActiveViewBig = new ConcurrentLinkedQueue<ActiveView>();
        queueActiveViewLittle = new ConcurrentLinkedQueue<ActiveView>();
        queueActiveViewBomb = new ConcurrentLinkedQueue<ActiveView>();
        queueTxtViewCommon = new ConcurrentLinkedQueue<>();

        int timeCount = activity.timeCount * 1000;
        for (int i = 0; i < timeCount; i = i + intervalOfCreateLittle) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        createBigView(TAG_LITTLE);
                    } catch (Exception e) {
//                        LogUtil.e(e);
                    }
                }
            }, i);
        }
        for (int i = 0; i < timeCount; i = i + intervalOfCreateBomb) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        createBombView();
                    } catch (Exception e) {
//                        LogUtil.e(e);
                    }
                }
            }, i);
        }
        for (int posBig : hashSetBig) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    try {
                        createBigView(TAG_BIG);
                    } catch (Exception e) {
//                        LogUtil.e(e);
                    }
                }
            }, posBig * 1000);
        }
    }

    private void createBigView(final int tag) {
        ActiveView activeView;
        int widthView;
        if (tag == TAG_BIG) {
            widthView = widthBig;
            activeView = queueActiveViewBig.poll();
            if (null == activeView) {
                activeView = new ActiveView(activity);
                activeViewLinkedList.add(activeView);
                activeView.setImageResource(R.drawable.active_coin_big);
                addViewInLayout(activeView, 0, biglayoutParams, true);
                activeView.setTag(TAG_BIG);
                measureChildren(MeasureSpec.makeMeasureSpec(widthBig, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(widthBig, MeasureSpec.EXACTLY));
            } else {
//                activeView.layout(0, 0, 0, 0);
                activeView.setEnabled(true);
                activeView.setVisibility(VISIBLE);
            }
        } else {
            widthView = widthLittle;
            activeView = queueActiveViewLittle.poll();
            if (null == activeView) {
                activeView = new ActiveView(activity);
                activeViewLinkedList.add(activeView);
                activeView.setImageResource(R.drawable.active_coin_little);
                addViewInLayout(activeView, 0, littleLayoutParams, true);
                activeView.setTag(TAG_LITTLE);
                measureChildren(MeasureSpec.makeMeasureSpec(widthLittle, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(widthLittle, MeasureSpec.EXACTLY));
            } else {
//                activeView.layout(0, 0, 0, 0);
                activeView.setEnabled(true);
                activeView.setVisibility(VISIBLE);
            }
        }
        activeView.setOnClickListener(listener);
        int posStartX = activity.random.nextInt(getWidth() - activeView.getWidth());
        int posEndX = activity.random.nextInt(getWidth());
        int distanceX = posEndX - posStartX;
        int posStartY = activity.random.nextInt(300);
        if (tag == TAG_BIG) {
            activeView.init(System.currentTimeMillis(), posStartX, -posStartY, speedYBig, distanceX / TIME_SHORT);
            activeView.animateLayout(true, queueActiveViewBig, getWidth(), getHeight());
        } else {
            activeView.init(System.currentTimeMillis(), posStartX, -posStartY, speedYLittle, distanceX / TIME_LONG);
            activeView.animateLayout(true, queueActiveViewLittle, getWidth(), getHeight());
        }
    }

    private void createBombView() {
        ActiveView activeView = queueActiveViewBomb.poll();
        if (null == activeView) {
            activeView = new ActiveView(activity);
            activeViewLinkedList.add(activeView);
            addViewInLayout(activeView, 0, bomblayoutParams, true);
            activeView.setTag(TAG_BOMB);
            measureChildren(MeasureSpec.makeMeasureSpec(widthBomb, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(widthBomb, MeasureSpec.EXACTLY));
        } else {
            activeView.setEnabled(true);
            activeView.setVisibility(VISIBLE);
        }
        activeView.setImageResource(R.drawable.active_bomb);
        activeView.setOnClickListener(listener);
        int posStartX = activity.random.nextInt(getWidth() - activeView.getWidth());
        int posEndX = activity.random.nextInt(getWidth());
        int distanceX = posEndX - posStartX;
        int posStartY = activity.random.nextInt(300);
        activeView.init(System.currentTimeMillis(), posStartX, -posStartY, speedYLittle, distanceX / TIME_LONG);
        activeView.animateLayout(true, queueActiveViewBomb, getWidth(), getHeight());
    }

    private OnClickListener listener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            try {
                final ActiveView activeView = (ActiveView) view;
                activeView.setEnabled(false);
                activeView.animateLayout(false, null, 0, 0);
                int mTag = (int) activeView.getTag();
                if (mTag == TAG_BOMB) {
                    animationBomb(activeView);
                    activity.soundPool.play(3, 1f, 1f, 1, 0, 1);
                    activity.vibrator.vibrate(100);
                } else {
                    activity.soundPool.play(2, 1f, 1f, 1, 0, 1);
                    animationLucky(activeView, mTag);
                }
                //左上角总数和加减textView处理
//            activity.txtShow.setText(mTag > 0 ? ("+" + mTag) : (mTag + ""));
                activity.getCoin += mTag;
                if (activity.getCoin < 0) {
                    activity.getCoin = 0;
                }
                if (activity.getCoin > activity.countCoin) {
                    activity.getCoin = activity.countCoin;
                }
                activity.txtCoinCount.setText(activity.getCoin + "");
//            activity.txtShow.setVisibility(VISIBLE);

                txtAnimation(activeView, mTag);
            } catch (Exception e) {
//                LogUtil.e(e);
            }
        }
    };

    private TextView createTxtView(View view, int[] location, int tag) {
        TextView txtView = queueTxtViewCommon.poll();
        if (null == txtView) {
            txtView = new TextView(activity);
            txtView.setBackgroundResource(0);
            txtView.setGravity(Gravity.CENTER);
            txtView.setTextSize(dip2px(8));
            addView(txtView, widthTxt, widthTxt);
            addViewInLayout(txtView, 0, txtlayoutParams, true);
            measureChildren(MeasureSpec.makeMeasureSpec(100, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(100, MeasureSpec.EXACTLY));
        } else {
            txtView.setVisibility(VISIBLE);
        }
        if (tag > 0) {
            txtView.setText("+" + tag);
            txtView.setTextColor(colorLucky);
        } else {
            txtView.setText("" + tag);
            txtView.setTextColor(colorBomb);
        }
        int left = location[0] + view.getMeasuredWidth() / 2 - widthTxt / 2;
        int top = location[1] - widthTxt;
        int right = location[0] + view.getMeasuredHeight() + widthTxt / 2;
        int bottom = location[1];
        txtView.layout(left, top, right, bottom);
        return txtView;
    }

    private void txtAnimation(ActiveView view, int tag) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        final TextView txtView = createTxtView(view, location, tag);
        int[] locationTxt = new int[2];
        txtView.getLocationOnScreen(locationTxt);
        Animation animation = (Animation) txtView.getTag(KEY_ANIMATION);
        if (null == animation) {
            animation = new TranslateAnimation(0, -locationTxt[0] + activity.viewCoinCountRight, 0, -locationTxt[1] + activity.viewCoinCountRight);
//        int timeX = Math.abs(-locationTxt[0] + activity.viewCoinCountRight) / 30;
//        int timeY = Math.abs(-locationTxt[1] + activity.viewCoinCountRight) / 40;
            animation.setDuration(500);
            animation.setInterpolator(new AccelerateInterpolator(1.1f));
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    try {
                        txtView.setVisibility(GONE);
                        txtView.layout(0, 0, 0, 0);
                        txtView.clearAnimation();
                        queueTxtViewCommon.add(txtView);
                    } catch (Exception e) {
//                        LogUtil.e(e);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            txtView.setTag(KEY_ANIMATION, animation);
        }
        animation.reset();
        txtView.startAnimation(animation);
    }

    private void animationLucky(final ActiveView activeView, final int mTag) {
        Animation animation = (Animation) activeView.getTag(KEY_ANIMATION);
        if (null == animation) {
            animation = new ScaleAnimation(1, 0.1f, 1, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setRepeatMode(Animation.REVERSE);
            animation.setRepeatCount(1);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    try {
                        activeView.setVisibility(GONE);
                        activeView.clearAnimation();
                        activeView.layout(0, 0, 0, 0);
                        if (TAG_BIG == mTag) {
                            queueActiveViewBig.add(activeView);
                        } else {
                            queueActiveViewLittle.add(activeView);
                        }
                    } catch (Exception e) {
//                        LogUtil.e(e);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            animation.setDuration(300);
            activeView.setTag(KEY_ANIMATION, animation);
        }
        animation.reset();
        activeView.startAnimation(animation);
    }

    private void animationBomb(final ActiveView activeView) {
        activeView.setImageResource(R.drawable.active_boom_animation);
        AnimationDrawable animationDrawable = (AnimationDrawable) activeView.getDrawable();
        animationDrawable.start();
        Animation animation = (Animation) activeView.getTag(KEY_ANIMATION);
        if (null == animation) {
            animation = new ScaleAnimation(1, 1.8f, 1, 1.8f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    try {
                        activeView.setVisibility(GONE);
                        activeView.clearAnimation();
                        activeView.layout(0, 0, 0, 0);
                        queueActiveViewBomb.add(activeView);
                    } catch (Exception e) {
//                        LogUtil.e(e);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            animation.setDuration(150);
            activeView.setTag(KEY_ANIMATION, animation);
        }
        animation.reset();
        activeView.startAnimation(animation);
    }

    public float dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (dpValue * scale + 0.5f);
    }
}
