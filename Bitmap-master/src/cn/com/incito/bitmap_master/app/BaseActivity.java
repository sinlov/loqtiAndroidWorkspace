/*
 * Copyright (c) 2012, Incito Corporation, All Rights Reserved
 */
package cn.com.incito.bitmap_master.app;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**
 * @description 
 * @author   tianran
 * @createDate Feb 2, 2015
 * @version  1.0
 */
public class BaseActivity extends FragmentActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        MyApplication.getInstance().list.add(this);
    }
    
}
