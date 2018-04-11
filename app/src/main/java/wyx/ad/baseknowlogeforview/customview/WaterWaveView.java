package wyx.ad.baseknowlogeforview.customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.LinearInterpolator;

import static android.R.attr.x;
import static android.view.View.MeasureSpec.getMode;
import static android.view.View.MeasureSpec.getSize;

/**
 * Created by Wangyuxu
 * Date on 2018/4/10.
 * Description 流动的波浪
 */

public class WaterWaveView extends View {
    private Paint mPaint;
    private int width;
    private int waveNum = 3; //一屏幕可以显示几个正弦波
    private int waveLength;//波长
    private int xStepLength;//x轴的步长
    private int yStepLength = 100;//y方向max
    private int baseLine = 300;//x轴的卓彪
    private int widSize;//当前view的宽度
    private int heightSize;//当前view的高度
    private int dx = 200;//水波动画的偏移量
    private Path path;


    public WaterWaveView(Context context) {
        super(context);
        onBaseInitView(context, null, 0, 0);
    }

    public WaterWaveView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        onBaseInitView(context, attrs, 0, 0);
    }

    public WaterWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onBaseInitView(context, attrs, defStyleAttr, 0);
    }


    public WaterWaveView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        onBaseInitView(context, attrs, defStyleAttr, defStyleRes);
    }

    private void onBaseInitView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {


    }

    private void initDrawVariable() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//画笔设置成抗锯齿状，让界面像素点更流畅的连接在一块，形成比较圆润的界面
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);//填充内部和描边
        mPaint.setColor(Color.BLUE);//设置画笔的颜色
        mPaint.setStrokeWidth(5);//设置画笔的宽度
        DisplayMetrics dm = new DisplayMetrics();
        width = widSize;//屏幕宽度
        waveLength = width / waveNum;//获取一个波长的长度
        xStepLength = waveLength / 4;//获取x方向的步长 也就是一个正弦波需要四个点的x轴方向的步长
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        drawOneWave(canvas);
        drawMultiumWave(canvas);
    }

    private void drawOneWave(Canvas canvas) {
        Path path = new Path();
        path.moveTo(0, 200);
        path.quadTo(100, 100, 200, 200);
        path.quadTo(300, 300, 400, 200);
        canvas.drawPath(path, mPaint);
    }

    private void drawMultiumWave(Canvas canvas) {
        path.reset();//每次ondraw被调用时候，如果不reset的话，会造成路径重合，还有一种解决方案，这里再重新new ，不过在ondraw中尽量减少new对象以减轻内存开销
        path.moveTo(0 * waveLength - dx, baseLine);
        for (int i = 0; i <= waveNum; i++) {
            path.quadTo(1 * xStepLength + i * waveLength - dx, baseLine - yStepLength, 2 * xStepLength + i * waveLength - dx, baseLine);
            path.quadTo(3 * xStepLength + i * waveLength - dx, baseLine + yStepLength, 4 * xStepLength + i * waveLength - dx, baseLine);
        }
        path.lineTo(widSize, heightSize);
        path.lineTo(0, heightSize);
        path.close();
        canvas.drawPath(path, mPaint);//每调用一次drawXXX就会生成一个新的画布，然后跟底层的画布进行重叠
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widethMode = MeasureSpec.getMode(widthMeasureSpec);
        widSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        heightSize = MeasureSpec.getSize(heightMeasureSpec);
        initDrawVariable();
        startAnim();
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    public void startAnim() {
        ValueAnimator animator = ValueAnimator.ofInt(0, waveLength);
        animator.setDuration(2000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setInterpolator(new LinearInterpolator());

        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animator.start();
    }
}
