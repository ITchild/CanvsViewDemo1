package com.fei.canvsviewdemo.activity;

import android.view.View;

import com.fei.canvsviewdemo.R;
import com.fei.canvsviewdemo.activity.carmerview.CarmerActivity;
import com.fei.canvsviewdemo.activity.myVedioGet.ShowActivity;
import com.fei.canvsviewdemo.base.BaseAc;

public class MainActivity extends BaseAc implements View.OnClickListener{

    @Override
    protected int initLatout() {
        return R.layout.activity_main;
    }

    @Override
    protected boolean isBackshow() {
        return false;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        findView(R.id.main_lineChart_bt).setOnClickListener(this);
        findView(R.id.main_columnChart_bt).setOnClickListener(this);
        findView(R.id.main_webService_bt).setOnClickListener(this);
        findView(R.id.main_dialogAc_bt).setOnClickListener(this);
        findView(R.id.main_TabAc_bt).setOnClickListener(this);
        findView(R.id.main_Carmer_bt).setOnClickListener(this);
        findView(R.id.main_showVodie_bt).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.main_lineChart_bt :
                JumpToAc(LineChartActivity.class);
                break;
            case R.id.main_columnChart_bt:
                JumpToAc(ColumnChartActivity.class);
                break;
            case R.id.main_webService_bt :
                JumpToAc(WebServiceActivity.class);
                break;
            case R.id.main_dialogAc_bt:
                JumpToAc(DialogActivity.class);
                break;
            case R.id.main_TabAc_bt :
                break;
            case R.id.main_Carmer_bt :
                JumpToAc(CarmerActivity.class);
                break;
            case R.id.main_showVodie_bt :
                JumpToAc(ShowActivity.class);
                break;
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
