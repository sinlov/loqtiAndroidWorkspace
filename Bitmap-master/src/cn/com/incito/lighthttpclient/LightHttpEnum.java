/*
 * Copyright (c) 2012, Incito Corporation, All Rights Reserved
 */
package cn.com.incito.lighthttpclient;

import java.util.Map;
import java.util.TreeMap;

/**
 * @description 
 * @author   tianran
 * @createDate Feb 1, 2015
 * @version  1.0
 */
public enum LightHttpEnum {
    NULL,
    TYEP_DEFALUT,
    TYPE_JSON_GET,
    TYPE_JSON_POST,
    TYPE_BITMAP_DOWN,
    TYPE_BITMAP_UPLOAD,
    MSG_ERROR_TIMEOUT,
    MSG_ERROR_NOCONNECTION,
    MSG_ERROR_PARSE,
    MSG_ERROR_SERVER,
    MSG_ERROR_AUTHFAILURE,
    MSG_ERROR_NETWORK
    ;
    private static final int START_VALUE = 1000;
    private static Map<Integer, LightHttpEnum> ss = new TreeMap<Integer, LightHttpEnum>();
    private int value;
    static {
        for (int i = 0; i < values().length; i++) {
            values()[i].value = START_VALUE + i;
            ss.put(values()[i].value, values()[i]);
        }
    }

    public static LightHttpEnum fromInt(int i) {
        return ss.get(i);
    }

    public int intValue() {
        return value;
    }
}
