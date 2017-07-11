package com.senzer.mylove.service;

import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;

import com.senzer.mylove.util.ServiceUtil;
import com.senzer.mylove.util.ToastHelper;

import static com.senzer.mylove.service.ServiceA.MSG_WHAT_START_SERVICEB;

/**
 * ProjectName: ServiceB
 * Description: 守护服务进程A
 *
 * review by chenpan, wangkang, wangdong 2017/7/11
 * edit by JeyZheng 2017/7/11
 * author: JeyZheng
 * version: 1.0.0
 * created at: 2017/7/11 14:58
 */
public class ServiceB extends Service {
    public static final String SERVICE_NAME = "com.senzer.mylove.service.ServiceB";
    public static final int MSG_WHAT_START_SERVICEA = 0x001;

    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MSG_WHAT_START_SERVICEA:
                    startServiceA();
                    break;

                default:
                    break;
            }
        }
    };

    /**
     * 使用aidl 启动ServiceA
     */
    private IStrongService startSA = new IStrongService.Stub() {
        @Override
        public void startService() throws RemoteException {
            Intent intent = new Intent(getBaseContext(), ServiceA.class);
            getBaseContext().startService(intent);
        }

        @Override
        public void stopService() throws RemoteException {
            Intent intent = new Intent(getBaseContext(), ServiceA.class);
            getBaseContext().stopService(intent);
        }
    };

    /**
     * 判断ServiceA是否还在运行，如果不是则启动ServiceA
     */
    private void startServiceA() {
        boolean isSWorking = ServiceUtil.isServiceWork(this, ServiceA.SERVICE_NAME);
        if (!isSWorking) {
            try {
                startSA.startService();
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 在内存紧张的时候，系统回收内存时，会回调OnTrimMemory， 重写onTrimMemory当系统清理内存时从新启动ServiceA
     */
    @Override
    public void onTrimMemory(int level) {
        /*
		 * 启动serviceA
		 */
        startServiceA();
    }

    @Override
    public void onCreate() {
        ToastHelper.toast(this, "start serviceB ......");
        startServiceA();

        /*
		 * 此线程用监听ServiceB的状态
		 */
        new Thread() {
            @Override
            public void run() {
                while (true) {

                    boolean isSWorking = ServiceUtil.isServiceWork(ServiceB.this, ServiceA.SERVICE_NAME);
                    if (!isSWorking) {

                        // way one
                        Message msg = Message.obtain();
                        msg.what = MSG_WHAT_START_SERVICEA;
                        handler.sendMessage(msg);

                        // way two
//                        handler.sendEmptyMessage(MSG_WHAT_START_SERVICEA);
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
        return (IBinder) startSA;
    }
}
