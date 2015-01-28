package com.incito.gsontools.bean;

import java.io.Serializable;
import java.util.List;

public class JsonBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private List<DataBean> data;

	public List<DataBean> getData() {
		return data;
	}

	public void setData(List<DataBean> data) {
		this.data = data;
	}

}
