package com.fei.canvsviewdemo.activity;

import android.os.Handler;
import android.os.Message;

import com.fei.canvsviewdemo.R;
import com.fei.canvsviewdemo.base.BaseAc;
import com.fei.canvsviewdemo.ui.ColumnChartView;

import java.util.Random;

public class ColumnChartActivity extends BaseAc {
    private final int SENDMSG = 1000;
    private ColumnChartView column_view_ccv; //柱状图的视图
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
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SENDMSG:
                    if (null != column_view_ccv) {
                        column_view_ccv.setData(new Random().nextInt(10) + 1);
                    }
                    mHandler.post(mRunnable);
                    break;
            }
        }
    };

    @Override
    protected int initLatout() {
        return R.layout.activity_colummnchart;
    }

    @Override
    protected void initView() {
        column_view_ccv = findView(R.id.column_view_ccv);
    }

    @Override
    protected void initData() {
        mHandler.post(mRunnable);
    }

    @Override
    protected void initListener() {

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
