package com.fei.canvsviewdemo.activity.carmerview;

import android.view.View;

import com.fei.canvsviewdemo.R;
import com.fei.canvsviewdemo.ui.CameraView;
import com.fei.feilibs_1_0_0.base.ac.BaseActivity;

public class CarmerActivity extends BaseActivity {
    CameraView surfaceView1;
    @Override
    protected int initLayout() {
        return R.layout.activity_carmerview;
    }

    @Override
    protected boolean isBackshow() {
        return true;
    }

    @Override
    protected void initView() {
        surfaceView1 = findView(R.id.surfaceView1);
        View focus = findViewById(R.id.focus);
        View capture = findViewById(R.id.capture);
        focus.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                surfaceView1.focus();
            }
        });
        capture.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                surfaceView1.capture();
            }
        });

    }
}
