package com.senzer.mylove.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;

import com.senzer.mylove.util.ServiceUtil;
import com.senzer.mylove.util.ToastHelper;

/**
 * ProjectName: ServiceA
 * Description: 守护服务进程A
 * <p>
 * review by chenpan, wangkang, wangdong 2017/7/11
 * edit by JeyZheng 2017/7/11
 * author: JeyZheng
 * version: 1.0.0
 * created at: 2017/7/11 14:58
 */
public class ServiceA extends Service {
    public static final String SERVICE_NAME = "com.senzer.mylove.service.ServiceA";
    public static final int MSG_WHAT_START_SERVICEB = 0x001;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MSG_WHAT_START_SERVICEB:
                    startServiceB();
                    break;

                default:
                    break;
            }
        }
    };

    /**
     * 使用aidl 启动ServiceB
     */
    private IStrongService startSB = new IStrongService.Stub() {
        @Override
        public void startService() throws RemoteException {
            Intent intent = new Intent(getBaseContext(), ServiceB.class);
            getBaseContext().startService(intent);
        }

        @Override
        public void stopService() throws RemoteException {
            Intent intent = new Intent(getBaseContext(), ServiceB.class);
            getBaseContext().stopService(intent);
        }
    };

    /**
     * 判断ServiceB是否还在运行，如果不是则启动ServiceB
     */
    private void startServiceB() {
        boolean isSWorking = ServiceUtil.isServiceWork(this, ServiceB.SERVICE_NAME);
        if (!isSWorking) {
            try {
                startSB.startService();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 在内存紧张的时候，系统回收内存时，会回调OnTrimMemory， 重写onTrimMemory当系统清理内存时从新启动ServiceB
     */
    @Override
    public void onTrimMemory(int level) {
        /*
		 * 启动serviceB
		 */
        startServiceB();
    }

    @Override
    public void onCreate() {
        ToastHelper.toast(this, "start serviceA ......");
        startServiceB();

        /*
		 * 此线程用监听ServiceB的状态
		 */
        new Thread() {
            @Override
            public void run() {
                while (true) {

                    boolean isSWorking = ServiceUtil.isServiceWork(ServiceA.this, ServiceB.SERVICE_NAME);
                    if (!isSWorking) {

                        // way one
                        Message msg = Message.obtain();
                        msg.what = MSG_WHAT_START_SERVICEB;
                        handler.sendMessage(msg);

                        // way two
//                        handler.sendEmptyMessage(MSG_WHAT_START_SERVICEB);
                    }

                    try {
                        sleep(1);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }.start();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return (IBinder) startSB;
    }
}
