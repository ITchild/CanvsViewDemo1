package com.fei.canvsviewdemo.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ColumnChartView extends View {
    //坐标轴原点的位置
    private int xPoint = 80;
    private int yPoint = 860;
    //柱状图的宽度
    private int renctWidth = 20 ;
    //刻度长度
    private int yScale = 40; //y轴40个单位构成一个刻度
    //x与y坐标轴的长度
    private int xLength = 380;  // X轴的长度
    private int yLength = 220; // Y轴的长度

    private int BTimes = 2;//倍数

    private int MaxDataSize;   //横坐标  最多可绘制的点
    private String[] yLabel ;  //Y轴的刻度上显示字的集合

    private List<Integer> data = new ArrayList<Integer>();   //存放 纵坐标 所描绘的点

    public ColumnChartView(Context context) {
        super(context);
        initData();
    }

    /**
     * 数据的初始化
     */
    private void initData() {
        yScale = BTimes * yScale;
        xLength = BTimes * BTimes * xLength;
        yLength = BTimes * xLength;
        if(null == yLabel){
            yLabel = new String[yLength/yScale];
        }
        for (int i = 0; i < yLabel.length; i++) {
            yLabel[i] = (i + 1) + "M/s";
        }
        MaxDataSize = xLength / renctWidth;
    }

    public ColumnChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initData();
    }

    public ColumnChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initData();
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
        paint.setStyle(Paint.Style.FILL);
        //如果集合中有数据
        if (null != data && data.size() > 1) {
            for (int i = 1; i < data.size(); i++) {  //依次取出数据进行绘制
                canvas.drawRect(xPoint + (i - 1) * renctWidth, yPoint - data.get(i - 1) * yScale, xPoint + i * renctWidth, yPoint, paint);

            }
        }
    }


    /**
     * 设置单点的数据
     *
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
     *
     * @param data
     * @param isClear
     */
    public void setData(List<Integer> data, boolean isClear) {
        if (null != data) {
            if (isClear) {
                this.data.clear();
            }
            this.data.addAll(data);
            while (this.data.size() > MaxDataSize) {
                this.data.remove(0);  //删除头数据
            }
        }
        this.invalidate();//刷新数据
    }
}