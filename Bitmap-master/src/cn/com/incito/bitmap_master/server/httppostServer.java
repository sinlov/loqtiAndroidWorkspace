/*
 * Copyright (c) 2012, Incito Corporation, All Rights Reserved
 */
package cn.com.incito.bitmap_master.server;

import cn.com.incito.bitmap_master.httpup.HttpFileUpTool;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @description 
 * @author   tianran
 * @createDate Feb 9, 2015
 * @version  1.0
 */
public class httppostServer extends Service {
    private final IBinder binder = new MyBinder();
    public class MyBinder extends Binder {
        
    }
    @Override
    public IBinder onBind(Intent intent) {
        return binder;
    }
    

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        String actionUrl = intent.getStringExtra("actionURL");
        Map<String, String> params = new HashMap<String, String>();
        params.put("site", intent.getStringExtra("site"));
        Map<String, File> files = new HashMap<String, File>();
        files.put(intent.getStringExtra("file0"), new File("/sdcard/image/" + intent.getStringExtra("file0")));
        files.put(intent.getStringExtra("file1"), new File("/sdcard/image/" + intent.getStringExtra("file1")));
        //                files.put("photo3.jpg", new File("/sdcard/photo3.jpg"));
        //                files.put("photo4.jpg", new File("/sdcard/photo4.jpg"));
        //                files.put("photo5.jpg", new File("/sdcard/photo5.jpg"));
        //                files.put("photo6.jpg", new File("/sdcard/photo6.jpg"));
        //                files.put("photo7.jpg", new File("/sdcard/photo7.jpg"));
        try {
            Log.v("--------上传提示信息！----------", "上传开始！");
           HttpFileUpTool.post(actionUrl, params, files);
            Log.v("--------上传提示信息！----------", "上传成功！");
        } catch (IOException e) {
            e.printStackTrace();
            Log.v("有没有错误看这里：", e.toString());
        }
        return super.onStartCommand(intent, flags, startId);
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }
    
}
