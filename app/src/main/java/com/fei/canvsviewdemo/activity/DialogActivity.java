package com.fei.canvsviewdemo.activity;

import android.view.Display;
import android.view.WindowManager;

import com.fei.canvsviewdemo.R;
import com.fei.feilibs_1_0_0.base.ac.BaseActivity;

/**
 * @author fei
 */

public class DialogActivity extends BaseActivity {
    @Override
    protected int initLayout() {
        return R.layout.activity_dialog;
    }

    @Override
    protected boolean isBackshow() {
        return false;
    }

    @Override
    protected void initView() {
        setDialogNature();
    }

    /**
     * 设置Dialog的属性
     */
    private void setDialogNature(){
        WindowManager m = getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        android.view.WindowManager.LayoutParams p = getWindow().getAttributes();
        p.height = (int) (d.getHeight() * 0.3); // 高度设置为屏幕的0.3
        p.width = (int) (d.getWidth() * 0.7); // 宽度设置为屏幕的0.7
        getWindow().setAttributes(p);
        setFinishOnTouchOutside(true);//点击外部是否消失（true ： 消失  false:不消失）
    }
}
