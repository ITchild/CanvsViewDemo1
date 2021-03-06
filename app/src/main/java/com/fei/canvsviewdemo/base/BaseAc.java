package com.fei.canvsviewdemo.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;

/**
 * activity的基类
 */
public abstract class BaseAc extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(initLatout());
        showActionBarBack();
        initView();
        initData();
        initListener();
    }

    /**
     * 返回布局文件
     *
     * @return
     */
    protected abstract int initLatout();

    private void showActionBarBack() {
        android.support.v7.app.ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setHomeButtonEnabled(isBackshow());
            actionBar.setDisplayHomeAsUpEnabled(isBackshow());
        }
    }

    protected abstract void initView();

    protected abstract void initData();

    protected abstract void initListener();

    /**
     * actionBar中是否显示返回按键
     *
     * @return
     */
    protected abstract boolean isBackshow();

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    /**
     * 省去类型转换  将此方法写在基类Activity
     */
    protected <T extends View> T findView(int id) {
        return (T) super.findViewById(id);
    }

    /**
     * 跳转Activity
     *
     * @param cls
     */
    protected void JumpToAc(Class cls) {
        Intent intent = new Intent();
        intent.setClass(this, cls);
        startActivity(intent);
    }

    /**
     * antionBar中返回按钮的监听
     *
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
