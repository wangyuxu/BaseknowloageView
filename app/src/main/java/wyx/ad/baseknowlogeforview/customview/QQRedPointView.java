package wyx.ad.baseknowlogeforview.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Wangyuxu
 * Date on 2018/4/13.
 * Description
 */

public class QQRedPointView extends View {
    private Paint mPaint;
    private Point mFixedPoint;
    private Point mMovePoint;
    private int mFixedPointRadius = 50;
    private boolean mIsTouch = false;
    private Path path;

    public QQRedPointView(Context context) {
        super(context);
        initBaseView();
    }


    public QQRedPointView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initBaseView();
    }

    public QQRedPointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBaseView();
    }

    public QQRedPointView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initBaseView();
    }

    private void initBaseView() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(Color.RED);
        mFixedPoint = new Point();
        mMovePoint = new Point();
        mFixedPoint.set(100, 100);
        path = new Path();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        canvas.saveLayer(new RectF(0, 0, getWidth(), getHeight()), mPaint, Canvas.ALL_SAVE_FLAG);
        canvas.drawCircle(mFixedPoint.x, mFixedPoint.y, mFixedPointRadius, mPaint);
        if (mIsTouch) {
            canvas.drawCircle(mMovePoint.x, mMovePoint.y, mFixedPointRadius, mPaint);
            canvas.drawPath(path, mPaint);
        }
//        canvas.restore();


    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                mIsTouch = true;
                return true;
            case MotionEvent.ACTION_MOVE:
                mIsTouch = true;
                mMovePoint.set((int) event.getX(), (int) event.getY());
                calcutePath();

                break;
            case MotionEvent.ACTION_UP:
                mIsTouch = false;
                break;
        }
        postInvalidate();
        return super.onTouchEvent(event);

    }

    private void calcutePath() {
        float x = mFixedPoint.x;
        float y = mFixedPoint.y;
        float currentX = mMovePoint.x;
        float currentY = mMovePoint.y;
        float dx = currentX - x;
        float dy = currentY - y;

        double angle = Math.atan(dy / dx);
        path.reset();

        path.moveTo((float) (x + mFixedPointRadius * Math.sin(angle)), (float) (y - mFixedPointRadius * Math.cos(angle)));
        path.quadTo((x + currentX) / 2, (y + currentY) / 2, (float) (currentX + mFixedPointRadius * Math.sin(angle)), (float) (currentY - mFixedPointRadius * Math.cos(angle)));
        path.lineTo((float) (currentX - mFixedPointRadius * Math.sin(angle)), (float) (currentY + mFixedPointRadius * Math.cos(angle)));
        path.quadTo((x + currentX) / 2, (y + currentY) / 2, (float) (x - mFixedPointRadius * Math.sin(angle)), (float) (y + mFixedPointRadius * Math.cos(angle)));
        path.close();
    }
}
