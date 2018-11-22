package king.chengwu.com.chengwuapp.activity_for_20171111;

import android.content.Context;
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

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ConcurrentLinkedQueue;

import chengwu.com.chengwuapp.R;

/**
 * Created by shmyh on 2017/9/26.
 */

public class RedPacketGroup extends ViewGroup implements View.OnClickListener {
    //    private static final int TAG_TYPE = 0x2048;
    private static final int TAG_PACKET_ZERO = 0;//红包0元
    private static final int TAG_PACKET_ONE = 1;//红包1元
    private static final int TAG_PACKET_TWO = 2;//红包2元
    private static final int TAG_BIG_BOOM = -10;//大炸弹，扣除当前全部红包（红包一次最多可获得10元）
    private static final int TAG_LITTLE_BOOM = -1;//小炸弹，扣除1元
    private static final int TAG_RAINING = 100;//小炸弹，扣除1元
    public static final float TIME_PACKET = 2800f; //红包落下时间
    public static final float TIME_BOMB = 2500f; //炸弹落下时间
    public static final int COUNT_PACKET = 50;//红包总数
    public static final int COUNT_BIG_BOMB = 2;//大炸弹总数
    public static final int COUNT_LITTLE_BOMB = 8;//小炸弹总数

    private int posCount = (int) dip2px(45);
    private int widthPacket = (int) dip2px(80);
    private int widthBigBomb = (int) dip2px(90);
    private int widthLittleBoom = (int) dip2px(80);
    private int widthRaining = (int) dip2px(70);
    private int widthTxt = (int) dip2px(30);
    private LayoutParams paramsPacket = new LayoutParams(widthPacket, widthPacket);
    private LayoutParams paramsBigBomb = new LayoutParams(widthBigBomb, widthBigBomb);
    private LayoutParams paramsLittleBoom = new LayoutParams(widthLittleBoom, widthLittleBoom);
    private LayoutParams paramsTxt = new LayoutParams(widthTxt, widthTxt);
    private LayoutParams paramsRaining = new LayoutParams(widthRaining, widthRaining);
    private int colorPacket = Color.parseColor("#f25a2b");
    private int colorBomb = Color.parseColor("#0086d1");
    private Context context;
    private Random random = new Random();
    private int timeCount = 10000;//游戏时长（毫秒）
    private int amount;//红包总金额
    private float speedPacket;//红包降落速度
    private float speedBomb;//炸弹降落速度

    private ConcurrentLinkedQueue<FallingView> queuePacket = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<FallingView> queueBigbomb = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<FallingView> queueLittleBomb = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<TextView> queueTxtViewCommon = new ConcurrentLinkedQueue<>();
    private ConcurrentLinkedQueue<FallingView> queueRaining = new ConcurrentLinkedQueue<>();
    private LinkedList<FallingView> FallingViewList = new LinkedList<>();

    private OpenPacketListener openPacketListener;

    public RedPacketGroup(Context context) {
        super(context);
        this.context = context;
        setBackgroundColor(Color.parseColor("#000000"));
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        speedPacket = h / TIME_PACKET;
        speedBomb = h / TIME_BOMB;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN || event.getAction() == MotionEvent.ACTION_POINTER_DOWN) {
            for (int i = 0; i < getChildCount(); i++) {
                View child = getChildAt(i);
                if (child.getVisibility() == View.VISIBLE && child.isClickable() && child.isEnabled()
                        && child.getLeft() < event.getX() && child.getRight() > event.getX()
                        && child.getTop() - dip2px(50) < event.getY() && child.getBottom() > event.getY()) {
                    child.performClick();
                }
            }
        }
        return true;
    }

    public float dip2px(float dpValue) {
        final float scale = getResources().getDisplayMetrics().density;
        return (dpValue * scale + 0.5f);
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        if (timeCount <= 0) {
            return;
        }
        for (FallingView view : FallingViewList) {
            if (VISIBLE == getVisibility()) {
                int tag = (int) view.getTag(R.id.redPacketTag);
                switch (tag) {
                    case TAG_PACKET_ZERO:
                    case TAG_PACKET_ONE:
                    case TAG_PACKET_TWO://0,1,2元红包
                        view.animateLayout(true, queuePacket, getWidth(), getHeight());
                        break;
                    case TAG_LITTLE_BOOM://小炸弹
                        view.animateLayout(true, queueLittleBomb, getWidth(), getHeight());
                        break;
                    case TAG_BIG_BOOM://大炸弹
                        view.animateLayout(true, queueBigbomb, getWidth(), getHeight());
                        break;
                    case TAG_RAINING:
                        view.animateLayout(true, queueRaining, getWidth(), getHeight());
                        break;
                }
            }
        }
        super.dispatchDraw(canvas);
    }

    /**
     * @param amount 红包总金额
     */
    public void startRun(int amount, OpenPacketListener openPacketListener) {
        this.openPacketListener = openPacketListener;

        this.amount = amount;
        int intervalPacket = timeCount / COUNT_PACKET;
        for (int i = 0; i < timeCount; i += intervalPacket) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    createRedPacket();
                }
            }, i);
        }

        int intervalLittleBomb = timeCount / COUNT_LITTLE_BOMB;
        for (int i = 0; i < timeCount; i += intervalLittleBomb) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    createLittleBombView();
                }
            }, i);
        }

        postDelayed(new Runnable() {
            @Override
            public void run() {
                createBigBombView();
                postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        createBigBombView();
                    }
                }, 3000);
            }
        }, 3000);

        for (int i = 0; i < 50000; i += 200) {
            postDelayed(new Runnable() {
                @Override
                public void run() {
                    createRainingView();
                }
            }, i);
        }
    }

    private void createRedPacket() {
        FallingView packetView = queuePacket.poll();
        if (null == packetView) {
            packetView = new FallingView(context);
            FallingViewList.add(packetView);
            addViewInLayout(packetView, 0, paramsPacket, true);
            measureChildren(MeasureSpec.makeMeasureSpec(widthPacket, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(widthPacket, MeasureSpec.EXACTLY));
            packetView.setOnClickListener(this);
        } else {
            packetView.setEnabled(true);
            packetView.setVisibility(VISIBLE);
        }
        int typeP = random.nextInt(4);//随机红包图案
        if (0 == typeP)
            packetView.setImageResource(R.drawable.r_p1);
        else if (1 == typeP)
            packetView.setImageResource(R.drawable.r_p2);
        else if (2 == typeP)
            packetView.setImageResource(R.drawable.r_p3);
        else
            packetView.setImageResource(R.drawable.r_p4);
        int money = 0;//随机红包金额
        if (amount > 0) {
            if (amount == 1)
                money = random.nextInt(2);//生成0或者1
            else
                money = random.nextInt(3);//生成0或者1或者2
        }
        if (0 == money)
            packetView.setTag(R.id.redPacketTag, TAG_PACKET_ZERO);
        else if (1 == money)
            packetView.setTag(R.id.redPacketTag, TAG_PACKET_ONE);
        else
            packetView.setTag(R.id.redPacketTag, TAG_PACKET_TWO);

        int posStartX = random.nextInt(getWidth() - packetView.getWidth());
        int posEndX = random.nextInt(getWidth());
        int distanceX = posEndX - posStartX;
        int posStartY = random.nextInt(300);
        packetView.init(System.currentTimeMillis(), posStartX, -posStartY, speedPacket, distanceX / TIME_PACKET);
        packetView.animateLayout(true, queuePacket, getWidth(), getHeight());
    }

    private void createBigBombView() {
        FallingView bigBombView = queueBigbomb.poll();
        if (null == bigBombView) {
            bigBombView = new FallingView(context);
            FallingViewList.add(bigBombView);
            addViewInLayout(bigBombView, 0, paramsBigBomb, true);
            bigBombView.setTag(R.id.redPacketTag, TAG_BIG_BOOM);
            measureChildren(MeasureSpec.makeMeasureSpec(widthBigBomb, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(widthBigBomb, MeasureSpec.EXACTLY));
            bigBombView.setOnClickListener(this);
        } else {
            bigBombView.setEnabled(true);
            bigBombView.setVisibility(VISIBLE);
        }
        bigBombView.setImageResource(R.drawable.r_boom_big);
        int posStartX = random.nextInt(getWidth() - bigBombView.getWidth());
        int posEndX = random.nextInt(getWidth());
        int distanceX = posEndX - posStartX;
        int posStartY = random.nextInt(300);
        bigBombView.init(System.currentTimeMillis(), posStartX, -posStartY, speedBomb, distanceX / TIME_BOMB);
        bigBombView.animateLayout(true, queueBigbomb, getWidth(), getHeight());
    }

    private void createLittleBombView() {
        FallingView littleBombView = queueLittleBomb.poll();
        if (null == littleBombView) {
            littleBombView = new FallingView(context);
            FallingViewList.add(littleBombView);
            addViewInLayout(littleBombView, 0, paramsLittleBoom, true);
            littleBombView.setTag(R.id.redPacketTag, TAG_LITTLE_BOOM);
            measureChildren(MeasureSpec.makeMeasureSpec(widthLittleBoom, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(widthLittleBoom, MeasureSpec.EXACTLY));
            littleBombView.setOnClickListener(this);
        } else {
            littleBombView.setEnabled(true);
            littleBombView.setVisibility(VISIBLE);
        }
        littleBombView.setImageResource(R.drawable.r_boom_little);
        int posStartX = random.nextInt(getWidth() - littleBombView.getWidth());
        int posEndX = random.nextInt(getWidth());
        int distanceX = posEndX - posStartX;
        int posStartY = random.nextInt(300);
        littleBombView.init(System.currentTimeMillis(), posStartX, -posStartY, speedBomb, distanceX / TIME_BOMB);
        littleBombView.animateLayout(true, queueLittleBomb, getWidth(), getHeight());
    }

    private void createRainingView() {
        FallingView raingingView = queueRaining.poll();
        if (null == raingingView) {
            raingingView = new FallingView(context);
            FallingViewList.add(raingingView);
            addViewInLayout(raingingView, 0, paramsRaining, true);
            raingingView.setTag(R.id.redPacketTag, TAG_LITTLE_BOOM);
            measureChildren(MeasureSpec.makeMeasureSpec(widthRaining, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(widthRaining, MeasureSpec.EXACTLY));
//            raingingView.setOnClickListener(this);
        } else {
            raingingView.setEnabled(true);
            raingingView.setVisibility(VISIBLE);
        }
        raingingView.setImageResource(R.drawable.r_rain_big);
        double posStartX, posEndX, distanceX, posStartY, posEndY, speed;
        if (random.nextBoolean()) {//雨点的初始Y值为0
            posStartX = random.nextInt(getWidth()) + widthRaining;
            posEndX = 0;
            distanceX = posEndX - posStartX;
//            posStartY = 0;
            posEndY = posStartX * Math.tan(Math.PI / 3);
            speed = speedPacket * (posEndY / getHeight());
            raingingView.init(System.currentTimeMillis(), (int) posStartX, 0, (float) speed, (float) (distanceX / (posEndY / speed)));
        } else {
            posStartY = random.nextInt(getHeight() - widthRaining);
            posEndY = getHeight();
            posStartX = getWidth() - widthRaining;
            posEndX = getWidth() - (posEndY - posStartY) * Math.tan(Math.PI / 3);
            if (posEndX < 0)
                posEndX = -widthRaining + random.nextInt(2 * widthRaining);
            distanceX = -(posStartX - posEndX);
            speed = speedPacket * ((posEndY - posStartY) / getHeight());
            raingingView.init(System.currentTimeMillis(), (int) posStartX, (int) posStartY, (float) speed, (float) (distanceX / ((posEndY - posStartY) / speed)));
        }
        raingingView.animateLayout(true, queueRaining, getWidth(), getHeight());
    }

    @Override
    public void onClick(View v) {
        FallingView fallingView = (FallingView) v;
        fallingView.setEnabled(false);
        fallingView.animateLayout(false, null, 0, 0);
        int tag = (int) fallingView.getTag(R.id.redPacketTag);
        if (TAG_PACKET_ZERO == tag || TAG_PACKET_ONE == tag || TAG_PACKET_TWO == tag) {
            animationPakcet(fallingView);
        } else if (TAG_LITTLE_BOOM == tag) {
            animationBomb(fallingView, tag);
        } else if (TAG_BIG_BOOM == tag) {
            animationBomb(fallingView, tag);
            tag = -amount;
        }
        amount += tag;
        if (amount < 0)
            amount = 0;
        openPacketListener.onClick(amount);
        txtAnimation(fallingView, tag);
    }

    private void animationPakcet(final FallingView packetView) {
        Animation animation = (Animation) packetView.getTag(R.id.finance_red_packet_animation);
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
                        packetView.setVisibility(GONE);
                        packetView.clearAnimation();
                        packetView.layout(0, 0, 0, 0);
                        queuePacket.add(packetView);
                    } catch (Exception e) {
//                        LogUtil.e(e);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            animation.setDuration(300);
            packetView.setTag(R.id.finance_red_packet_animation, animation);
        }
        animation.reset();
        packetView.startAnimation(animation);
    }

    private void animationBomb(final FallingView bombView, final int tag) {
        if (TAG_BIG_BOOM == tag)
            bombView.setImageResource(R.drawable.finance_r_game_bomb_anim_big);
        else
            bombView.setImageResource(R.drawable.finance_r_game_bomb_anim_little);
        AnimationDrawable animationDrawable = (AnimationDrawable) bombView.getDrawable();
        animationDrawable.start();
        Animation animation = (Animation) bombView.getTag(R.id.finance_red_packet_animation);
        if (null == animation) {
            animation = new ScaleAnimation(1, 1.8f, 1, 1.8f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            animation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    try {
                        bombView.setVisibility(GONE);
                        bombView.clearAnimation();
                        bombView.layout(0, 0, 0, 0);
                        if (TAG_BIG_BOOM == tag)
                            queueBigbomb.add(bombView);
                        else
                            queueLittleBomb.add(bombView);
                    } catch (Exception e) {
//                        LogUtil.e(e);
                    }
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            animation.setDuration(150);
            bombView.setTag(R.id.finance_red_packet_animation, animation);
        }
        animation.reset();
        bombView.startAnimation(animation);
    }

    private void txtAnimation(FallingView view, int tag) {
        int[] location = new int[2];
        view.getLocationOnScreen(location);
        final TextView txtView = createTxtView(view, location, tag);
        int[] locationTxt = new int[2];
        txtView.getLocationOnScreen(locationTxt);
        Animation animation = (Animation) txtView.getTag(R.id.finance_red_packet_animation);
        if (null == animation) {
            animation = new TranslateAnimation(0, -locationTxt[0] + posCount, 0, -locationTxt[1] + posCount);
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
            txtView.setTag(R.id.finance_red_packet_animation, animation);
        }
        animation.reset();
        txtView.startAnimation(animation);
    }

    private TextView createTxtView(View view, int[] location, int tag) {
        TextView txtView = queueTxtViewCommon.poll();
        if (null == txtView) {
            txtView = new TextView(context);
            txtView.setBackgroundResource(0);
            txtView.setGravity(Gravity.CENTER);
            txtView.setTextSize(dip2px(8));
            addView(txtView, widthTxt, widthTxt);
            addViewInLayout(txtView, 0, paramsTxt, true);
            measureChildren(MeasureSpec.makeMeasureSpec(100, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(100, MeasureSpec.EXACTLY));
        } else {
            txtView.setVisibility(VISIBLE);
        }
        if (tag >= 0) {
            txtView.setText("+" + tag);
            txtView.setTextColor(colorPacket);
        } else {
            txtView.setText("-" + tag);
            txtView.setTextColor(colorBomb);
        }
        int left = location[0] + view.getMeasuredWidth() / 2 - widthTxt / 2;
        int top = location[1] - widthTxt;
        int right = location[0] + view.getMeasuredHeight() + widthTxt / 2;
        int bottom = location[1];
        txtView.layout(left, top, right, bottom);
        return txtView;
    }

    public interface OpenPacketListener {
        //改变用户累计获得金额数
        void onClick(int moneyAmount);
    }
}
