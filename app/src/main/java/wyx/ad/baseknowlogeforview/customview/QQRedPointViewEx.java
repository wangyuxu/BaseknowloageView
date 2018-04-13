package wyx.ad.baseknowlogeforview.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;

import static android.R.attr.startX;
import static android.R.attr.startY;
import static android.icu.lang.UCharacter.GraphemeClusterBreak.L;

/**
 * Created by Wangyuxu
 * Date on 2018/4/13.
 * Description
 */

public class QQRedPointViewEx extends FrameLayout {
    private Paint mPaint;
    private Point mFixedPoint;
    private Point mMovePoint;
    private int mFixedPointRadius = 50;
    private boolean mIsTouch = false;
    private Path path;
    private TextView numTv;

    public QQRedPointViewEx(@NonNull Context context) {
        super(context);
        initBaseView();
    }

    public QQRedPointViewEx(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initBaseView();
    }

    public QQRedPointViewEx(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initBaseView();
    }

    public QQRedPointViewEx(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr, @StyleRes int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initBaseView();
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {

        canvas.drawCircle(mFixedPoint.x, mFixedPoint.y, mFixedPointRadius, mPaint);
        if (mIsTouch) {
            calcutePath();
            canvas.drawCircle(mMovePoint.x, mMovePoint.y, 50, mPaint);
            canvas.drawPath(path, mPaint);
            numTv.setX(mMovePoint.x - numTv.getWidth() / 2);
            numTv.setY(mMovePoint.y - numTv.getHeight() / 2);
        } else {
            numTv.setX(mFixedPoint.x - numTv.getWidth() / 2);
            numTv.setY(mFixedPoint.y - numTv.getHeight() / 2);
        }
        super.dispatchDraw(canvas);
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
        numTv = new TextView(getContext());
        numTv.setText("99+");
        numTv.setBackgroundColor(Color.RED);
        numTv.setTextSize(12);
        numTv.setLayoutParams(new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        addView(numTv);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Rect rect = new Rect();
                int[] location = new int[2];
                numTv.getLocationOnScreen(location);
                rect.left = location[0];
                rect.top = location[1];
                rect.right = location[0] + numTv.getWidth();
                rect.bottom = location[1] + numTv.getHeight();

                if (rect.contains((int) event.getRawX(), (int) event.getRawY())) {
                    mIsTouch = true;
                }
                return true;
            case MotionEvent.ACTION_MOVE:
                break;
            case MotionEvent.ACTION_UP:
                mIsTouch = false;
                mFixedPointRadius = 50;
                break;
        }
        mMovePoint.set((int) event.getX(), (int) event.getY());
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

        float distance = (float) Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
        mFixedPointRadius = (int) (mFixedPointRadius - distance/6);
        if (mFixedPointRadius < 10)
            mFixedPointRadius = 10;
    }
}
