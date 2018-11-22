package king.chengwu.com.chengwuapp.viewgroup;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Created by shmyh on 2017/11/27.
 */

public class DetailProgressBar extends View {
    private Context context;
    private Paint paint;
    private int lastProgress = 0;
    private int screenWidth;
    private int widthBar;
    private int widthTv;
    private int fontSize;
    private int leftTv;
    private int lineY;
    private int colorStart;
    private int colorEnd;

    private LinearGradient linearGradient;
    private ValueAnimator animation;

    public DetailProgressBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public DetailProgressBar(Context context) {
        this(context, null);
    }

    public DetailProgressBar(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.parseColor("#80FFFFFF"));
        colorStart = Color.parseColor("#00e6e9");
        colorEnd = Color.parseColor("#68ffeb");
        linearGradient = new LinearGradient(0, 0, getMeasuredWidth(), getMeasuredHeight(),
                new int[]{colorStart, colorEnd},
                null, LinearGradient.TileMode.CLAMP);
        widthBar = (int) dip2px(2);
        fontSize = (int) dip2px(12);
        lineY = (int) dip2px(18);
        animation = new ValueAnimator();
        animation.setInterpolator(new AccelerateDecelerateInterpolator());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(MeasureSpec.getSize(widthMeasureSpec), (int) dip2px(19));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        screenWidth = getResources().getDisplayMetrics().widthPixels;
        //进度条
        paint.setStrokeWidth(widthBar);
        linearGradient = new LinearGradient(0, 0, screenWidth * lastProgress * 0.01f, 0,
                new int[]{colorStart, colorEnd}, null, LinearGradient.TileMode.CLAMP);
        paint.setShader(linearGradient);
        canvas.drawLine(0, lineY, screenWidth * lastProgress * 0.01f, lineY, paint);
        //进度文字
        paint.setShader(null);
        paint.setStrokeWidth(0);
        paint.setTextSize(fontSize);
        paint.setTextAlign(Paint.Align.CENTER);
        String text = "已售" + lastProgress + "%";
        widthTv = text.length() * fontSize;
        if (lastProgress * 0.01f * screenWidth <= widthTv * 0.5f) {
            //此时tvProgress在最左侧
            leftTv = 0 ;
        } else if (lastProgress * 0.01f * screenWidth + widthTv * 0.5f >= screenWidth) {
            leftTv = screenWidth - widthTv;
        } else {
            leftTv = (int) (lastProgress * 0.01f * screenWidth - widthTv * 0.5f);
        }
        float x = leftTv + widthTv / 2;
        float y = fontSize;
        canvas.drawText(text, x, y, paint);
    }

    /*设置进度条进度, 外部调用*/
    public void setProgress(final int totalProgress) {
        if (null == animation) {
            animation = new ValueAnimator();
            animation.setInterpolator(new AccelerateDecelerateInterpolator());
        }
        if (animation.isRunning())
            animation.cancel();
        animation.setIntValues(0, totalProgress);
        animation.setDuration(2000);
        animation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int progress = (int) animation.getAnimatedValue();
                if (progress != lastProgress) {
                    lastProgress = progress;
                    postInvalidate();
                }
            }
        });
        animation.start();
    }

    public float dip2px(float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (dpValue * scale + 0.5f);
    }
}
