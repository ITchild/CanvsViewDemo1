package com.fei.canvsviewdemo.activity;

import android.os.Handler;
import android.os.Message;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.fei.canvsviewdemo.R;
import com.fei.canvsviewdemo.base.BaseAc;
import com.fei.canvsviewdemo.ui.LineChartView;

import java.util.Random;

public class LineChartActivity extends BaseAc {

    private final int SENDMSG = 1000;
    private CheckBox LineChart_tianChong_cb;
    private LineChartView main_lineChart_lv;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SENDMSG:
                    if (null != main_lineChart_lv) {
                        main_lineChart_lv.setData(new Random().nextInt(10) + 1);
                    }
                    mHandler.post(mRunnable);
                    break;
            }
        }
    };
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            mHandler.sendEmptyMessage(SENDMSG);
        }
    };

    @Override
    protected int initLatout() {
        return R.layout.activity_linechart;
    }

    @Override
    protected void initView() {
        main_lineChart_lv = findView(R.id.main_lineChart_lv);
        LineChart_tianChong_cb = findView(R.id.LineChart_tianChong_cb);
    }

    @Override
    protected void initData() {
        mHandler.post(mRunnable);
    }

    @Override
    protected void initListener() {
        LineChart_tianChong_cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (b) {
                    main_lineChart_lv.isTianChong(LineChartView.TC);
                } else {
                    main_lineChart_lv.isTianChong(LineChartView.NTC);
                }
            }
        });
    }

    @Override
    protected boolean isBackshow() {
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != mHandler) {
            mHandler.removeCallbacks(mRunnable);
        }
    }
}
