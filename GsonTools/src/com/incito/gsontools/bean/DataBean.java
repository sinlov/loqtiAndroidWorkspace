package com.incito.gsontools.bean;


import java.io.Serializable;

public class DataBean implements Serializable{

	/**   
	 * @Fields serialVersionUID  
	 */ 
	private static final long serialVersionUID = 1L;
	private int isimage;
	private String path;
	private String img_id;
	private String img_format;
	private String img_name_zh;
	private int img_width, img_height;

	
	public String getImg_name_zh() {
		return img_name_zh;
	}

	public void setImg_name_zh(String img_name_zh) {
		this.img_name_zh = img_name_zh;
	}

	public int getWidth() {
		return img_width;
	}

	public void setWidth(int width) {
		this.img_width = width;
	}

	public int getHeight() {
		return img_height;
	}

	public void setHeight(int height) {
		this.img_height = height;
	}

	public int getIsimage() {
		return isimage;
	}

	public void setIsimage(int isimage) {
		this.isimage = isimage;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getImg_id() {
		return img_id;
	}

	public void setImg_id(String img_id) {
		this.img_id = img_id;
	}

	public String getImg_format() {
		return img_format;
	}

	public void setImg_format(String img_format) {
		this.img_format = img_format;
	}

	public int getImg_width() {
		return img_width;
	}

	public void setImg_width(int img_width) {
		this.img_width = img_width;
	}

	public int getImg_height() {
		return img_height;
	}

	public void setImg_height(int img_height) {
		this.img_height = img_height;
	}
	
}
