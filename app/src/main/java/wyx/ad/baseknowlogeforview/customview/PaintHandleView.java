package wyx.ad.baseknowlogeforview.customview;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.DashPathEffect;
import android.graphics.DiscretePathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathDashPathEffect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;


/**
 * Created by Wangyuxu
 * Date on 2018/4/10.
 * Description paint的常规使用
 */

public class PaintHandleView extends View {

    private Paint mPaint;
    private float dx;
    private Path path;
    float shi = 25;
    float xu = 25;

    public PaintHandleView(Context context) {
        this(context, null);
    }

    public PaintHandleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        baseDataInit();
    }

    private void baseDataInit() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//画笔设置成抗锯齿状，让界面像素点更流畅的连接在一块，形成比较圆润的界面
        mPaint.setStyle(Paint.Style.STROKE);//填充内部和描边
        mPaint.setColor(Color.BLUE);//设置画笔的颜色
        mPaint.setStrokeWidth(50);//设置画笔的宽度
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        handleStrokeCap(canvas);
//        handleStrokeJoin(canvas);
//        handlePathEffect(canvas);
        drawDashPath(canvas);
    }

    //strokcap的使用，这个地方一个知识点，strokewidth是线宽，textsize是字体大小，字体也是线画的跟线宽有关系
    private void handleStrokeCap(Canvas canvas) {
        mPaint.setStrokeWidth(50);
        mPaint.setStrokeCap(Paint.Cap.BUTT);
        canvas.drawLine(100, 100, 300, 100, mPaint);
        mPaint.reset();
        mPaint.setTextSize(30);
        canvas.drawText("Paint.Cap.BUTT 无线冒", 400, 100, mPaint);

        mPaint.setColor(Color.BLUE);//设置画笔的颜色
        mPaint.setStrokeWidth(50);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(100, 300, 300, 300, mPaint);
        mPaint.reset();
        mPaint.setTextSize(30);
        canvas.drawText("Paint.Cap.ROUND圆形线冒", 400, 300, mPaint);

        mPaint.setColor(Color.BLUE);//设置画笔的颜色
        mPaint.setStrokeWidth(50);
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawLine(100, 600, 300, 600, mPaint);
        mPaint.reset();
        mPaint.setTextSize(30);
        canvas.drawText("Paint.Cap.SQUARE 矩形无线冒", 400, 600, mPaint);

    }

    private void handleStrokeJoin(Canvas canvas) {
        Path path = new Path();
        mPaint.setStrokeWidth(50);//设置画笔的宽度
        path.moveTo(100, 300);
        path.lineTo(300, 300);
        path.lineTo(100, 600);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        canvas.drawPath(path, mPaint);
        mPaint.setStrokeWidth(1);
        mPaint.setTextSize(30);
        canvas.drawText("Paint.Join.ROUND", 400, 300, mPaint);

        path.reset();
        mPaint.setStrokeWidth(50);//设置画笔的宽度
        path.moveTo(100, 600);
        path.lineTo(300, 600);
        path.lineTo(100, 900);
        mPaint.setStrokeJoin(Paint.Join.BEVEL);
        canvas.drawPath(path, mPaint);
        mPaint.setStrokeWidth(1);
        mPaint.setTextSize(30);
        canvas.drawText("Paint.Join.BEVEL", 400, 600, mPaint);

        path.reset();
        mPaint.setStrokeWidth(50);//设置画笔的宽度
        path.moveTo(100, 1200);
        path.lineTo(300, 1200);
        path.lineTo(100, 1500);
        mPaint.setStrokeJoin(Paint.Join.MITER);
        canvas.drawPath(path, mPaint);
        mPaint.setStrokeWidth(1);
        mPaint.setTextSize(30);
        canvas.drawText("Paint.Join.MITER", 400, 1200, mPaint);
    }

    private void handlePathEffect(Canvas canvas) {
        Path path = new Path();


        path.reset();
        mPaint.reset();
        mPaint.setAntiAlias(true);//画笔设置成抗锯齿状，让界面像素点更流畅的连接在一块，形成比较圆润的界面
        mPaint.setStyle(Paint.Style.STROKE);//填充内部和描边
        mPaint.setColor(Color.BLUE);//设置画笔的颜色
        mPaint.setStrokeWidth(5);//设置画笔的宽度
        path.moveTo(100, 300);
        path.lineTo(300, 300);
        path.lineTo(100, 600);
        //100圆角半径
        mPaint.setPathEffect(new CornerPathEffect(100));
        canvas.drawPath(path, mPaint);
        mPaint.setStrokeWidth(1);
        mPaint.setTextSize(30);
        canvas.drawText("圆角路径效果", 400, 300, mPaint);

        path.reset();
        mPaint.setStrokeWidth(5);//设置画笔的宽度
        path.moveTo(100, 600);
        path.lineTo(300, 600);
        path.lineTo(100, 900);
        //new float[]{10,5,30,6} 实线长度，虚线长度，视线长度，虚线长度
        mPaint.setPathEffect(new DashPathEffect(new float[]{10, 5, 30, 6}, 5));
        canvas.drawPath(path, mPaint);
        mPaint.setStrokeWidth(1);
        mPaint.setTextSize(30);
        canvas.drawText("虚线路径效果", 400, 600, mPaint);

        path.reset();
        mPaint.setStrokeWidth(5);//设置画笔的宽度
        path.moveTo(100, 1200);
        path.lineTo(300, 1200);
        path.lineTo(100, 1500);
        //new DiscretePathEffect(6, 5)第一个参数segmentLength：表示将原来的路径切成多长的线段。如果值为2，那么这个路径就会被切成一段段由长度为2的小线段。所以这个值越小，所切成的小线段越多；这个值越大，所切成的小线段越少。
        //第二参数deviation：表示被切成的每个小线段的可偏移距离。值越大，就表示每个线段的可偏移距离就越大，就显得越凌乱，值越小，每个线段的可偏移原位置的距离就越小。
        mPaint.setPathEffect(new DiscretePathEffect(6, 5));
        canvas.drawPath(path, mPaint);
        mPaint.setStrokeWidth(1);
        mPaint.setTextSize(30);
        canvas.drawText("离散路径效果", 400, 1200, mPaint);


        //构建印章路径
        Path stampPath = new Path();
        stampPath.moveTo(0, 20);
        stampPath.lineTo(10, 0);
        stampPath.lineTo(20, 20);
        stampPath.close();
        stampPath.addCircle(0, 0, 3, Path.Direction.CCW);


        path.reset();

        mPaint.reset();
        mPaint.setAntiAlias(true);//画笔设置成抗锯齿状，让界面像素点更流畅的连接在一块，形成比较圆润的界面
        mPaint.setStyle(Paint.Style.STROKE);//填充内部和描边
        mPaint.setColor(Color.BLUE);//设置画笔的颜色
        mPaint.setStrokeWidth(5);//设置画笔的宽度
        path.moveTo(100, 0);
        path.lineTo(300, 0);
        path.lineTo(100, 300);
        //new float[]{10,5,30,6} 实线长度，虚线长度，视线长度，虚线长度
        mPaint.setPathEffect(new PathDashPathEffect(stampPath, 36, 0, PathDashPathEffect.Style.TRANSLATE));
        canvas.drawPath(path, mPaint);
        mPaint.reset();
        mPaint.setStrokeWidth(1);
        mPaint.setTextSize(30);
        mPaint.setAntiAlias(true);//画笔设置成抗锯齿状，让界面像素点更流畅的连接在一块，形成比较圆润的界面
        mPaint.setStyle(Paint.Style.STROKE);//填充内部和描边
        mPaint.setColor(Color.BLUE);//设置画笔的颜色
        canvas.drawText("印章效果", 400, 100, mPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        startAnim();
    }

    private void startAnim() {
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0, 226);
        valueAnimator.setDuration(1000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                dx = (float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        valueAnimator.start();
    }

    private void drawDashPath(Canvas canvas) {
        path.reset();
        mPaint.setStrokeWidth(5);//设置画笔的宽度

//        path.moveTo((float) (Math.cos(Math.atan(2))*dx),600- (float) (Math.sin(Math.atan(2))*dx));
        path.moveTo(0,600);
        path.lineTo(300, 0);
        path.lineTo(600, 600);
        //new float[]{10,5,30,6} 实线长度，虚线长度，视线长度，虚线长度
//        mPaint.setPathEffect(new DashPathEffect(new float[]{10 - dx, 5 - dx, 30 - dx, 6 - dx},

        mPaint.setPathEffect(new DashPathEffect(new float[]{50,6,15,80,50, 25}, 226-dx));
        canvas.drawPath(path, mPaint);

    }

}
