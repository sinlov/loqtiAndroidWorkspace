package com.incito.gsontools.bean;

/**
 * Copyright (c) 2012, Incito Corporation, All Rights Reserved
 */

import java.io.Serializable;
import java.util.List;

/**
 * @description 
 * @author   tianran
 * @createDate Feb 11, 2015
 * @version  1.0
 */
public class JsonBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Data data;
	public Data getData() {
		return data;
	}
	public void setData(Data data) {
		this.data = data;
	}
}
