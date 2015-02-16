/**
 * All rights Reserved, Designed By sinlov
 * @Title:  TestJson.java 
 * @Package com.incito.gsontools.test
 * @Copyright:  incito Co., Ltd. Copyright,  All rights reserved 
 * @Description:  TODO<Plase input what is this doing> 
 * @author:  ErZheng 
 * @data:  Feb 12, 2015 12:29:22 AM 
 * @version:  V1.0 
 */
package com.incito.gsontools.test;

import com.google.gson.Gson;
import com.incito.gsontools.bean.JsonBean;

/**   
 * @ClassName:  TestJson   
 * @Description:TODO(what to do)   
 * @author: ErZheng  
 * @date:   Feb 12, 2015 12:29:22 AM   
 *      
 */
public class TestJson {
	
	/**   
	 * @Title: main   
	 * @Description: TODO(what to do)   
	 * @param: @param args      
	 * @return: void      
	 * @throws   
	 */
	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		String json = "{\"code\":0,\"data\":{\"apps\":[{\"code\":300,\"ctime\":1423655323000,\"description\":\"爱的爱的\",\"id\":2,\"name\":\"NineOldAndroids演示日志\",\"version\":\"v1.1.1\"}],\"ctime\":1423655831000,\"description\":\"tess\",\"id\":2,\"isDel\":0,\"project\":{\"category\":1,\"description\":\"互动课堂\",\"id\":0},\"projectId\":1,\"projectName\":\"dreamclass\"}}";
		System.out.println(json);
		Gson gson = new Gson();
		JsonBean jsonBean = gson.fromJson(json, JsonBean.class);
		System.out.println(jsonBean.getData().getApps().size());
	}

}
