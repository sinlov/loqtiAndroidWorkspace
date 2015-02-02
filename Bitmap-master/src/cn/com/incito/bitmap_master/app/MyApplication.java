/*
 * Copyright (c) 2012, Incito Corporation, All Rights Reserved
 */
package cn.com.incito.bitmap_master.app;



import cn.com.incito.lighthttpclient.DefalutVolleyClient;

import com.android.volley.RequestQueue;

import android.app.Application;
import android.os.Environment;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @description 
 * @author   tianran
 * @createDate Feb 2, 2015
 * @version  1.0
 */
public class MyApplication extends Application {
    private static final String TAG = "cn.com.incito.bitmap_master";
    private static RequestQueue qequestQueue;
    private static MyApplication myAppliaction;
    public static String appPath = Environment.getExternalStorageDirectory().getPath()
                                   + "/Android/data/%s";
    public List<BaseActivity> list = new ArrayList<BaseActivity>();
    public MyApplication() {
        super();
        Log.v(TAG, "MyApplaction new!");
        myAppliaction = this;
    }
    public static MyApplication getInstance(){
        if (myAppliaction == null) {
            myAppliaction = new MyApplication();
        }
        return myAppliaction;
    }
    
    @Override
    public void onCreate() {
        super.onCreate();
        initHttpResponseQueue();
    }
    
   private void initHttpResponseQueue() {
       DefalutVolleyClient.initClient(this);
       qequestQueue = DefalutVolleyClient.getRequestQueue();
    }
 public void quitApp(){
        try {
            for (BaseActivity baseActivity : list) {
                if (baseActivity != null) {
                    baseActivity.finish();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            System.exit(0);//normal exit
            Log.v(TAG, "close app");
        }
    }
}
