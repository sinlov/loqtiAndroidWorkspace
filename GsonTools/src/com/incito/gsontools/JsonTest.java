package com.incito.gsontools;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;
import com.incito.gsontools.bean.DataBean;
import com.incito.gsontools.bean.JsonBean;


@SuppressWarnings("deprecation")
public class JsonTest {

	JsonBean bean;
	List<String> list = new ArrayList<String>();

	Thread jsonThread = new Thread(){

		@SuppressWarnings({ "resource" })
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				DefaultHttpClient client = new DefaultHttpClient();
				String url = "http://127.0.0.1:8008/index_json.jsp";
				HttpGet httpGet = new HttpGet(url);
				HttpResponse response = client.execute(httpGet);
				int index = response.getStatusLine().getStatusCode();
				if (index == 200) {
					String result = getNetResult(response);
					Gson gson = new Gson();
					bean = gson.fromJson(result, JsonBean.class);

//					for (DataBean dataBean : bean.getData()) {
//						String urlPath = dataBean.getImg_name_zh();
//						if (urlPath != null) {
////							Bitmap bitmap = MainActivity.this.createBitmapByUrl(urlPath, img_width, img_height);
//							list.add(urlPath);
//						}else{
//
//						}
//					}
					System.out.println("网络通讯成功  list: " + list);
				}else{
					System.out.println( "网络通讯失败  " + index);
				}
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("解析错误: " + e);
			}
		}

	};
	/**
	 * get net result 
	 * @Title: getNetResult   
	 * @Description: get net result   
	 * @param: @param response
	 * @param: @return
	 * @param: @throws Exception      
	 * @return: String      
	 * @throws
	 */
	private static String getNetResult(HttpResponse response)throws Exception{
		InputStream ins = response.getEntity().getContent();
		byte[] buff = new byte[1024];
		int length = 0;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		while ((length = ins.read(buff)) != -1) {
			baos.write(buff, 0, length);
		}
		String result = new String(baos.toByteArray(), "UTF-8");
		baos.flush();
		baos.close();
		ins.close();
		System.out.println("getNetResult成功: "+result);
		return result;
	}

	private void getJsonString (){
		jsonThread.run();
	}
	
	public static void main(String[] args) {
		JsonTest test = new JsonTest();
		test.getJsonString();
	}
}
