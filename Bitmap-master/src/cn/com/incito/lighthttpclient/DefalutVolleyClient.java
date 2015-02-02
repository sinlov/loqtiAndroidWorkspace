/*
 * Copyright (c) 2012, Incito Corporation, All Rights Reserved
 */
package cn.com.incito.lighthttpclient;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

import android.content.Context;

/**
 * @description 
 * @author   tianran
 * @createDate Feb 1, 2015
 * @version  1.0
 */
public class DefalutVolleyClient extends Volley{
    private static DefalutVolleyClient instance;
    private static RequestQueue mRequsetQueue;
    private DefalutVolleyClient(){
        
    }
    public static DefalutVolleyClient initClient(Context context){
        if (instance == null) {
            instance = new DefalutVolleyClient();
            mRequsetQueue = Volley.newRequestQueue(context);
            return instance;
        }
        return instance;
    }
    public static DefalutVolleyClient getInstance(){
        return instance;
    }
    public static RequestQueue getRequestQueue(){
        if (mRequsetQueue == null) {
            throw new NullPointerException();
        }
        return mRequsetQueue;
    }
}
