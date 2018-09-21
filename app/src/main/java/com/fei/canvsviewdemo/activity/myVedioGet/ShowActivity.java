package com.fei.canvsviewdemo.activity.myVedioGet;

import android.util.Log;

import com.fei.canvsviewdemo.R;
import com.fei.feilibs_1_0_0.base.ac.BaseActivity;

public class ShowActivity extends BaseActivity {
    ParseWebUrlHelper parseWebUrlHelper;

    @Override
    protected int initLayout() {
        return R.layout.activity_showvoideurl;
    }

    @Override
    protected boolean isBackshow() {
        return true;
    }

    @Override
    protected void initView() {
        //初始化
        parseWebUrlHelper = ParseWebUrlHelper.getInstance().init(this, "https://www.iqiyi.com/");
        //解析网页中视频
        parseWebUrlHelper.setOnParseListener(new ParseWebUrlHelper.OnParseWebUrlListener() {
            @Override
            public void onFindUrl(String url) {
                Log.d("fei", "视频地址为" + url);
            }

            @Override
            public void onError(String errorMsg) {
                //****出错监听
                Log.d("fei", "出错了");
            }
        });
        parseWebUrlHelper.startParse();
    }
}
