package wyx.ad.baseknowlogeforview.customview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.graphics.Region;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;


/**
 * Created by 走向大厂的日子
 * Date on 2018/3/23.
 * Description 自定义view当中基础paint和canvas的使用
 */

public class PaintAndCanvasBaseHandleView extends View {
    enum ActionFlag {
        DRAW_LINE, //画直线
        DRAW_CIRCLE, // 画圆
        DRAW_OCTOL,//画椭圆
        DRAW_RECET//画矩形
    }

    private Paint mPaint;

    public PaintAndCanvasBaseHandleView(Context context) {
        super(context);
        onBaseInitView();
    }

    public PaintAndCanvasBaseHandleView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        onBaseInitView();
    }

    public PaintAndCanvasBaseHandleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        onBaseInitView();
    }

    public PaintAndCanvasBaseHandleView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        onBaseInitView();
    }

    private void onBaseInitView() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);//画笔设置成抗锯齿状，让界面像素点更流畅的连接在一块，形成比较圆润的界面
        mPaint.setStyle(Paint.Style.STROKE);//填充内部和描边
        mPaint.setColor(Color.BLUE);//设置画笔的颜色
        mPaint.setStrokeWidth(5);//设置画笔的宽度
    }

    //绘制界面的方法，当界面需要主动或者被动发生改变之后都会被调用的方法，
    // 因此在这个方法当中尽量减少不必要的对象进行重复的创建 比如说mPaint
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawLine(canvas);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void drawLine(Canvas canvas) {
        mPaint.setColor(Color.RED);//设置画笔的颜色
        mPaint.setStrokeWidth(5);//设置画笔的宽度
        //下面这个方法只需要指定直线的起始点跟 终点的坐标即可
        //重要点是，该坐标针对的是本画布的坐标点，现在当前我们认为是该view的左上角为原点，可以这样尝试，
        // 在该view的xml中添加margin，其余不变，你会发现直线的位置发生变化
        //具体为何说针对的本画布，以后谈到画布的时候在做分析
//        canvas.drawLine(0, 0, 50, 100, mPaint);
        //线起始点xy，终止点xy，线起始点xy，终止点xy，线起始点xy，终止点xy，
        float[] pts = {10, 10, 100, 100, 200, 200, 400, 400, 770, 500, 300, 400};
        mPaint.setColor(Color.BLUE);//设置画笔的颜色
//        canvas.drawLines(pts, mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
//        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
//        canvas.drawCircle(500, 600, 100, mPaint);


        //        canvas.drawRect(100, 100, 600, 500, mPaint);
//        canvas.drawRoundRect(300, 400, 600, 700, 20, 50, mPaint);
//        RectF rectF = new RectF();
//        mPaint.setColor(Color.RED);
//        rectF.set(300, 400, 600, 700);
//        canvas.drawRect(rectF, mPaint);
//        canvas.drawArc(rectF, 60, 210, false, mPaint);
//        canvas.drawOval(200, 200, 300, 400, mPaint);
//        canvas.drawPoint(0, 100, mPaint);
//        canvas.drawARGB(255,0,0,0);//画整个画布颜色
//        canvas.drawColor(Color.RED, PorterDuff.Mode.DST_OUT);
        Path path = new Path();
        path.moveTo(20, 30);
        path.lineTo(500, 120);
        path.lineTo(625, 541);
        path.addCircle(500, 300, 100, Path.Direction.CW);
//        path.addCircle(400, 300, 300, Path.Direction.CW);
//        path.addRoundRect(50, 60, 300, 200, 20, 10, Path.Direction.CCW);
        path.lineTo(945, 415);
        path.close();
        canvas.drawPath(path, mPaint);
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), android.R.mipmap.sym_def_app_icon);
//        canvas.drawBitmap(bitmap, 900, 10, mPaint);

//        canvas.drawb/**/
        mPaint.setTextSize(75);
        mPaint.setUnderlineText(true);
        mPaint.setLinearText(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setTextScaleX((float) 3);
        mPaint.setFakeBoldText(true);
        mPaint.setStrikeThruText(true);
        mPaint.setTextSkewX((float) 0.7);
//        canvas.drawText("woaini", 0, 300, mPaint);
        drawViewByPath(canvas);
    }
    private void drawViewByPath(Canvas canvas){
//        Path path =new Path();
//        path.
        Path path = new Path();
        path.moveTo(0,200);
        path.quadTo(100,100,200,200);
        path.quadTo(300,300,400,200);
        canvas.drawPath(path,mPaint);

    }

}
