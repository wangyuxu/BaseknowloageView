package wyx.ad.baseknowlogeforview.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import wyx.ad.baseknowlogeforview.R;

/**
 * Created by Wangyuxu
 * Date on 2018/4/11.
 * Description
 */

public class XformodeHandleView extends View {
    private Paint mPaint;
    private Path path;
    private Context mContext;
    private Bitmap mBitmap;
    private int width = 400;
    private int height = 400;
    private Bitmap dst;
    private Bitmap src;
    private Bitmap mSrcBitmap;
    private Bitmap mDesBitmap;
    private Bitmap mDesSquareBm;
    private Bitmap mSrcRectBm;

    public XformodeHandleView(Context context) {
        this(context, null);
    }

    public XformodeHandleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        baseDataInit(context);
        this.mContext = context;
    }

    private void baseDataInit(Context context) {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//画笔设置成抗锯齿状，让界面像素点更流畅的连接在一块，形成比较圆润的界面
        mPaint.setStyle(Paint.Style.STROKE);//填充内部和描边
        mPaint.setColor(Color.BLUE);//设置画笔的颜色
        mPaint.setStrokeWidth(50);//设置画笔的宽度
        path = new Path();
        mSrcBitmap = BitmapFactory.decodeResource(context.getResources(), R.mipmap.dog);
        setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mDesBitmap = Bitmap.createBitmap(mSrcBitmap.getWidth(), mSrcBitmap.getHeight(), Bitmap.Config.ARGB_8888);
        mDesSquareBm = makeDst(width, height);
        mSrcRectBm = makeSrc(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mPaint.setColor(Color.RED);

        int layerID = canvas.saveLayer(0, 0, getWidth(), getHeight(), mPaint, Canvas.ALL_SAVE_FLAG);
//        handleSrcOut(canvas);
        handleSrcInOrDesIn(canvas);
        canvas.restoreToCount(layerID);
    }

    private void handleSrcInOrDesIn(Canvas canvas) {
        canvas.drawBitmap(mDesSquareBm, 0, 0, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(mSrcRectBm, 200, 200, mPaint);
        mPaint.setXfermode(null);
    }

    static Bitmap makeDst(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        p.setColor(0xFFFFCC44);
        c.drawOval(new RectF(0, 0, w, h), p);
        return bm;
    }

    static Bitmap makeSrc(int w, int h) {
        Bitmap bm = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);

        p.setColor(0xFF66AAFF);
        c.drawRect(0, 0, w, h, p);
        return bm;
    }

    private void handleSrcOut(Canvas canvas) {
        Canvas canvas1 = new Canvas(mDesBitmap);
        canvas1.drawPath(path, mPaint);
        canvas.drawBitmap(mDesBitmap, 0, 0, mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_OUT));
        canvas.drawBitmap(mSrcBitmap, 0, 0, mPaint);
        mPaint.setXfermode(null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(event.getX(), event.getY());
                return true;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(event.getX(), event.getY());
                postInvalidate();
                break;
        }
        return super.onTouchEvent(event);

    }
}
