package king.chengwu.com.chengwuapp.activity_national_day;

import android.content.Context;
import android.widget.ImageView;


import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by 金城武 on 2016/9/18.
 */
public class ActiveView extends ImageView {

    private long startTime;
    private int startX;
    private int startY;
    private float speedY;
    private float speedX;

    public ActiveView(Context context) {
        super(context);
    }

    public void init(long startTime, int offsetX, int offsetY, float speedY, float speedX) {
        this.startTime = startTime;
        this.startX = offsetX;
        this.startY = offsetY;
        this.speedX = speedX;
        this.speedY = speedY;
//        LogUtil.e("SpeedX"+speedX);
        layout(startX, startY, startX + getMeasuredWidth(), startY + getMeasuredHeight());
    }

    void animateLayout(boolean canFalling, final ConcurrentLinkedQueue<ActiveView> listBox, final int screenWdith, final int screenHeight) {
        if (!canFalling || GONE == getVisibility() || !isEnabled()) {
            return;
        }
        if (getRight() + getMeasuredWidth() <= 0 || getLeft() >= screenWdith || getTop() >= screenHeight) {
            listBox.add(this);
            setVisibility(GONE);
            return;
        }
        long timePassed = System.currentTimeMillis() - startTime;
        int offsetX = (int) (startX + speedX * timePassed);
        int offsetY = (int) (startY + speedY * timePassed);
        layout(offsetX, offsetY, offsetX + getMeasuredWidth(), offsetY + getMeasuredHeight());
    }
}
