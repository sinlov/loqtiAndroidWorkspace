/*
 * Copyright (c) 2012, Incito Corporation, All Rights Reserved
 */
package cn.com.incito.lighthttpclient;

import com.android.volley.NoConnectionError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

/**
 * @description 
 * @author   tianran
 * @createDate Feb 2, 2015
 * @version  1.0
 */
public abstract class DefalutErrorListener implements ErrorListener {
    
    private static String errorTemp = null;
    
    abstract void onSefeErrorResponse(VolleyError error);
    
    @Override
    public void onErrorResponse(VolleyError error) {
        errorTemp = error.getMessage();
        if (errorTemp == null) {
            if (error instanceof TimeoutError) {
                DefalutVolleyClient.getRequestQueue().cancelAll(getClass());
            }
        }else {
            if (error instanceof NoConnectionError) {
                DefalutVolleyClient.getRequestQueue().cancelAll(getClass());
            }
        }
    }

}
