package com.fei.feilibs_100.rxbus;

import javax.security.auth.Subject;

/**
 * RxBus的核心类
 */
public class RxBus {

    public static RxBus getDefault() {
        return RxBusInstance.rxBus;
    }
    //内部类
    private static class RxBusInstance {
        private static final RxBus rxBus = new RxBus();
    }
    // 主题，Subject是非线程安全的
    public final Subject bus;

    /**
     * 单例
     * PublishSubject只会把在订阅发生的时间点之后来自原始Observable的数据发射给观察者
     * 序列化主题
     * 将 Subject转换为一个 SerializedSubject ，类中把线程非安全的PublishSubject包装成线程安全的Subject
     */
    private RxBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
    }
}
