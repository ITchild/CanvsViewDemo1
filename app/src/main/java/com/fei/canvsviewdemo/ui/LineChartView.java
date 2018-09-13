package com.fei.canvsviewdemo.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class LineChartView extends View {
    //坐标轴原点的位置
    private int xPoint = 80;
    private int yPoint = 860;
    //刻度长度
    private int xScale = 10;  //x轴10个单位构成一个刻度
    private int yScale = 40; //y轴40个单位构成一个刻度
    //x与y坐标轴的长度
    private int xLength = 380;  // X轴的长度
    private int yLength = 240; // Y轴的长度

    private int BTimes = 2;

    public static final int TC = 2;
    public static final int NTC = 1;
    private int isTianChong = NTC;//1:不填充颜色  2： 填充颜色

    private int MaxDataSize ;  //横坐标  最多可绘制的点

    private List<Integer> data = new ArrayList<Integer>();   //存放 纵坐标 所描绘的点

    private String[] yLabel;  //Y轴的刻度上显示字的集合

    public LineChartView(Context context) {
        super(context);
        initData();
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public LineChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
    }

    private void initData(){
        xScale = BTimes * xScale;
        yScale = BTimes* yScale;
        xLength = BTimes * BTimes * xLength;
        yLength = BTimes * xLength;
        MaxDataSize = xLength / xScale;
        if(null == yLabel) {
            yLabel = new String[xLength / xScale];
        }
        for (int i = 0; i < yLabel.length; i++) {
            yLabel[i] = (i + 1) + "M/s";
        }
    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setStyle(Paint.Style.STROKE);
        paint.setTextSize(30);//设置字体大小
        paint.setAntiAlias(true); //去锯齿
        paint.setColor(Color.RED);
        //绘制Y轴
        canvas.drawLine(xPoint, yPoint - yLength, xPoint, yPoint, paint);
        //绘制Y轴左右两边的箭头
        canvas.drawLine(xPoint, yPoint - yLength, xPoint - 3, yPoint - yLength + 6, paint);
        canvas.drawLine(xPoint, yPoint - yLength, xPoint + 3, yPoint - yLength + 6, paint);
        //Y轴上的刻度与文字
        for (int i = 0; i * yScale < yLength; i++) {
            canvas.drawLine(xPoint, yPoint - i * yScale, xPoint + 5, yPoint - i * yScale, paint);  //刻度
            canvas.drawText(yLabel[i], xPoint - 50, yPoint - i * yScale, paint);//文字
        }
        //X轴
        canvas.drawLine(xPoint, yPoint, xPoint + xLength, yPoint, paint);

        if(isTianChong == NTC) { // 不填充颜色
            //如果集合中有数据
            if (null != data && data.size() > 1) {
                for (int i = 1; i < data.size(); i++) {  //依次取出数据进行绘制
                    canvas.drawLine(xPoint + (i - 1) * xScale, yPoint - data.get(i - 1) * yScale, xPoint + i * xScale, yPoint - data.get(i) * yScale, paint);
                }
            }
        }else if(isTianChong == TC) {//填充颜色
            paint.setStyle(Paint.Style.FILL);
            if (data.size() > 1) {
                Path path = new Path();
                path.moveTo(xPoint, yPoint);
                for (int i = 0; i < data.size(); i++) {
                    path.lineTo(xPoint + i * xScale, yPoint - data.get(i) * yScale);
                }
                path.lineTo(xPoint + (data.size() - 1) * xScale, yPoint);
                canvas.drawPath(path, paint);
            }
        }
    }

    /**
     * 设置单点的数据
     * @param data
     */
    public void setData(int data) {
        this.data.add(data);
        if (this.data.size() > MaxDataSize) {  //判断集合的长度是否大于最大绘制长度
            this.data.remove(0);  //删除头数据
        }
        this.invalidate();//刷新数据
    }

    /**
     * 设置多点数据（只显示能显示下的后面的数据）
     * @param data
     * @param isClear
     */
    public void setData(List<Integer> data,boolean isClear){
        if(null != data){
            if(isClear){
                this.data.clear();
            }
            this.data.addAll(data);
            while (this.data.size() > MaxDataSize){
                this.data.remove(0);  //删除头数据
            }
        }
        this.invalidate();//刷新数据
    }

    /**
     * 设置是曲线是否填充 1：不填充   2： 填充
     * @param isTianChong
     */
    public void isTianChong(int isTianChong){
        this.isTianChong = isTianChong;
    }

}
