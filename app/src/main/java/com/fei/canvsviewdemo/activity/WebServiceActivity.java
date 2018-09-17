package com.fei.canvsviewdemo.activity;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.fei.canvsviewdemo.R;
import com.fei.canvsviewdemo.bean.LowStartBean;
import com.fei.feilibs_1_0_0.base.ac.BaseActivity;
import com.fei.feilibs_1_0_0.bean.RxBusMsgBean;
import com.fei.feilibs_1_0_0.rxbus.RxBus;

import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class WebServiceActivity extends BaseActivity implements View.OnClickListener {
    private final int MSG = 1000;
    private TextView webService_result;//显示webSerVice的结果
    private String result;

    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            getRemoteInfo();
            RxBusMsgBean bean = new RxBusMsgBean();
            bean.setWhat(MSG);
            RxBus.getInstance().post(bean);
        }
    };

    @Override
    protected void doRxBus(RxBusMsgBean bean) {
        super.doRxBus(bean);
        if(null == bean){
            return;
        }
        switch (bean.getWhat()){
            case MSG :
                webService_result.setText(result);
                break;
        }
    }

    @Override
    protected int initLayout() {
        return R.layout.activity_webservice;
    }

    @Override
    protected void initView() {
        webService_result = findView(R.id.webService_result);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initListener() {
        findView(R.id.webService_start).setOnClickListener(this);
    }

    @Override
    protected boolean isBackshow() {
        return true;
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.webService_start://开始WebSerVice
                new Thread(mRunnable).start();
                break;
        }
    }

    public String getRemoteInfo() {
        String WSDL_URI = "http://www.zqzjz.cn:10002/zjjc/services/webCallService?wsdl";//wsdl 的uri
        String namespace = "http://ws.whrsm.com";////namespace
        String methodName = "sendLowStartMsg";//要调用的方法名称
//        String WSDL_URI = "http://192.168.3.151:8088/mockmgeEnvelopsvrSoapBinding?WSDL";//wsdl 的uri
//        String namespace = "http://ws.whrsm.com";//namespace
//        String methodName = "sendLowStartMsg";//要调用的方法名称

        LowStartBean bean = new LowStartBean();
        SoapObject request = new SoapObject(namespace, methodName);
        request.addProperty("account", "3htest");
        request.addProperty("pwd", "12345678");
        request.addProperty("lowStartjson", bean.getJson());

        //创建SoapSerializationEnvelope 对象，同时指定soap版本号(之前在wsdl中看到的)
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapSerializationEnvelope.VER11);
        envelope.bodyOut = request;//由于是发送请求，所以是设置bodyOut
        envelope.dotNet = false;//由于是.net开发的webservice，所以这里要设置为true

        HttpTransportSE httpTransportSE = new HttpTransportSE(WSDL_URI);
        try {
            httpTransportSE.call(null, envelope);//调用
//            httpTransportSE.getServiceConnection();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        }
        Log.d("fei", envelope.bodyIn.toString());
        // 获取返回的数据
        SoapObject object = (SoapObject) envelope.bodyIn;
        // 获取返回的结果
        if (null != object && null != object.getProperty(0)) {
            result = object.getProperty(0).toString();
        } else {
            result = "";
        }
        Log.d("fei", result);
        return result;
    }
}
