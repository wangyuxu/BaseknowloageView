package wyx.ad.baseknowlogeforview.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;


/**
 * Created by Wangyuxu
 * Date on 2018/4/10.
 * Description 二阶贝塞尔去曲线利用之写字板
 */

public class WriteCanvasOfBezierLineView extends View {
    private Path path;
    private Paint mPaint;
    private float preX;
    private float preY;
    private float currentX;
    private float currentY;

    public WriteCanvasOfBezierLineView(Context context) {
        super(context);
        onBaseInitView(context, null, 0, 0);
    }

    public WriteCanvasOfBezierLineView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        onBaseInitView(context, attrs, 0, 0);
    }

    public WriteCanvasOfBezierLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onBaseInitView(context, attrs, defStyleAttr, 0);
    }

    public WriteCanvasOfBezierLineView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        onBaseInitView(context, attrs, defStyleAttr, defStyleRes);
    }

    private void onBaseInitView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        path = new Path();
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//画笔设置成抗锯齿状，让界面像素点更流畅的连接在一块，形成比较圆润的界面
        mPaint.setStyle(Paint.Style.STROKE);//填充内部和描边
        mPaint.setColor(Color.BLUE);//设置画笔的颜色
        mPaint.setStrokeWidth(5);//设置画笔的宽度
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawPath(path, mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
//                path.reset();
                preX = event.getX();
                preY = event.getY();
                path.moveTo(preX, preY);
                return true;
            case MotionEvent.ACTION_MOVE:
                currentX = event.getX();
                currentY = event.getY();
                path.quadTo((preX + currentX) / 2, (preY + currentY) / 2, currentX, currentY);
                preX = currentX;
                preY = currentY;
                postInvalidate();
                break;
            case MotionEvent.ACTION_UP:

                break;
        }
        return super.onTouchEvent(event);
    }
}
