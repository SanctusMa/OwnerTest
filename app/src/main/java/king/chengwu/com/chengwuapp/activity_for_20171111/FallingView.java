package king.chengwu.com.chengwuapp.activity_for_20171111;

import android.content.Context;
import android.widget.ImageView;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Created by 金城武 on 2017/9/26.
 */
public class FallingView extends ImageView {

    private long startTime;
    private int startX;
    private int startY;
    private float speedY;
    private float speedX;

    public FallingView(Context context) {
        super(context);
    }

    public void init(long startTime, int startX, int startY, float speedY, float speedX) {
        this.startTime = startTime;
        this.startX = startX;
        this.startY = startY;
        this.speedX = speedX;
        this.speedY = speedY;
//        LogUtil.e("SpeedX"+speedX);
        layout(startX, startY, startX + getMeasuredWidth(), startY + getMeasuredHeight());
    }

    void animateLayout(boolean canFalling, final ConcurrentLinkedQueue<FallingView> listBox, final int screenWdith, final int screenHeight) {
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
