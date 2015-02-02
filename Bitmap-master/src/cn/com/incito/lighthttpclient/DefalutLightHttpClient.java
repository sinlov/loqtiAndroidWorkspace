/*
 * Copyright (c) 2012, Incito Corporation, All Rights Reserved
 */
package cn.com.incito.lighthttpclient;

import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;

import java.util.HashMap;
import java.util.logging.Logger;

/**
 * 
 * @description 
 * @author   tianran
 * @createDate Feb 1, 2015
 * @version  1.0
 */
public abstract class DefalutLightHttpClient implements LightHttpClient{
    
    private static DefalutLightHttpClient intensace;
    private static Listener<?> mListener;
    private static DefalutErrorListener mDefalutErrorListener;
    
    private static StringRequest jsonRequest;
    private static JsonObjectRequest jsonGet;
    private static JsonObjectRequest jsonPost;
    private static ImageRequest imageRequest;
    
    private DefalutLightHttpClient (){
    }
    abstract void setListener(Listener<?> listener);
    abstract void setErrorListener(DefalutErrorListener errorListener);

    private String url;
    private HashMap<String, String> params;
    private static int maxWidth;
    private static int maxHeight;
    private static Config decodeConfig;
    
    public void setUrl(String url) {
        this.url = url;
    }
    public void setParams(HashMap<String, String> params) {
        this.params = params;
    }
    public static DefalutLightHttpClient init(){
        if (intensace == null) {
            return intensace = new DefalutLightHttpClient() {
                
                @Override
                void setListener(Listener<?> listener) {
                    mListener = listener;
                }
                
                @Override
                void setErrorListener(DefalutErrorListener errorListener) {
                    mDefalutErrorListener = errorListener;
                }
            };
        }
        return intensace;
    }
    
    
    public DefalutLightHttpClient getIntensace(){
        return intensace;
    }
    
    public void setType(LightHttpTypeEnum ty){
//        LightHttpTypeEnum ty = LightHttpTypeEnum.getType(type);
        if (!checkSet()) {
            throw new NullPointerException();
        }
        switch (ty) {
            case NULL:
                
                break;
            case TYEP_DEFALUT:
                getJsonString(url);
                DefalutVolleyClient.getRequestQueue().add(jsonRequest);
                break;
            case TYPE_JSON_GET:
                sendJsonGet(url);
                DefalutVolleyClient.getRequestQueue().add(jsonGet);
                break;
            case TYPE_JSON_POST:
                if (params == null) {
                    throw new NullPointerException();
                }
                sendJsonPost(url, params);
                DefalutVolleyClient.getRequestQueue().add(jsonPost);
                break;
            case TYPE_BITMAP_DOWN:
                getBitmapImage(url);
                DefalutVolleyClient.getRequestQueue().add(imageRequest);
                break;
            case TYPE_BITMAP_UPLOAD:
                
                break;
            default:
                break;
        }
    }
    
    private boolean checkSet(){
        if (url == null) {
            return false;
        }
        if (intensace ==null) {
            return false;
        }
        return true;
    }
    
    private static void getJsonString(String url){
        try {
            @SuppressWarnings("unchecked")
            Listener<String> lisJson = (Listener<String>) mListener;
            jsonRequest = new StringRequest(url, lisJson, mDefalutErrorListener);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger("Error", "From URL: " + url + " request: is not cast to String");
        }
    }
    
    private static void sendJsonGet(String url){
        try {
            @SuppressWarnings("unchecked")
            Listener<JSONObject> lisJson = (Listener<JSONObject>) mListener;
            jsonGet = new JsonObjectRequest(url, null, lisJson, mDefalutErrorListener);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger("Error", "From URL: " + url + " request: is not cast to JSON Object");
        }
    }
    
    private static void sendJsonPost(String url, HashMap<String, String> params){
        try {
            @SuppressWarnings("unchecked")
            Listener<JSONObject> lisJson = (Listener<JSONObject>) mListener;
            jsonPost = new JsonObjectRequest(url, new JSONObject(params), lisJson, mDefalutErrorListener);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger("Error", "From URL: " + url + " request: is not cast to JSON Object");
        }
    }
    private static void getBitmapImage(String url){
        try {
            @SuppressWarnings("unchecked")
            Listener<Bitmap> lisJson = (Listener<Bitmap>) mListener;
            imageRequest = new ImageRequest(url, lisJson, maxWidth, maxHeight, decodeConfig, mDefalutErrorListener);
        } catch (Exception e) {
            e.printStackTrace();
            Logger.getLogger("Error", "From URL: " + url + " request: is not cast to Bitmap Object");
        }
    }
}
