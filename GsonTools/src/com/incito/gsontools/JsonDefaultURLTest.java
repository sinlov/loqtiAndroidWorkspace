/**
 * All rights Reserved, Designed By sinlov
 * @Title:  JsonDefaultURLTest.java 
 * @Package com.incito.gsontools
 * @Copyright:  incito Co., Ltd. Copyright YYYY-YYYY,  All rights reserved 
 * @Description:  test json 
 * @author:  ErZheng 
 * @data:  Jan 28, 2015 3:30:03 PM 
 * @version:  V1.0 
 */
package com.incito.gsontools;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLStreamHandler;
import java.util.logging.Logger;

/**
 * @ClassName: JsonDefaultURLTest
 * @Description:Defalut URL Test
 * @author: ErZheng
 * @date: Jan 28, 2015 3:30:03 PM
 * 
 */
public class JsonDefaultURLTest {
	/**
	 * 8k
	 */
	private static final int IO_BUFFER_SIZE = 8 * 1024;
	private URL url;
	HttpURLConnection mhttpURLConnection = null;
	BufferedInputStream in = null;
	FlushedInputStream fin = null;
	BufferedOutputStream out = null;
	ByteArrayOutputStream baos = null;

	public boolean downloadToLocalJsonStringByUrl(String urlString,
			String outputString) {
		try {
			url = new URL(urlString);
			mhttpURLConnection = (HttpURLConnection) url.openConnection();
			fin = new FlushedInputStream(new BufferedInputStream(mhttpURLConnection.getInputStream(), IO_BUFFER_SIZE));  
			byte[] buff = new byte[1024];
			int length = 0;
			baos = new ByteArrayOutputStream();
			while ((length = fin.read(buff)) != -1) {
				baos.write(buff, 0, length);
			}
			outputString = new String(baos.toByteArray(), "UTF-8");
			System.out.println("getNetResult成功: "+outputString);
		} catch (Exception e) {
			System.out.println("Error in download - " + urlString + " : " + e);
		} finally {
			if (mhttpURLConnection != null) {
				mhttpURLConnection.disconnect();
			}
			try {
				if (baos != null) {
					baos.flush();
					baos.close();
				}
				if (out != null) {
					out.close();
				}
				if (fin != null) {
					fin.close();
				}
			} catch (final IOException e) {
				System.out.println("Error in close - " + urlString + " : " + e);
			}
		}
		return false;
	}

	public boolean downloadToLocalStreamByUrl(String urlString,
			OutputStream outputStream) {
		try {
			url = new URL(urlString);
			mhttpURLConnection = (HttpURLConnection) url.openConnection();
			in = new BufferedInputStream(mhttpURLConnection.getInputStream(),
					IO_BUFFER_SIZE);
			out = new BufferedOutputStream(outputStream, IO_BUFFER_SIZE);
			int b;
			while ((b = in.read()) != -1) {
				out.write(b);
			}
			return true;
		} catch (Exception e) {
			Logger.getLogger("Error in download - " + urlString + " : " + e);
		} finally {
			if (mhttpURLConnection != null) {
				mhttpURLConnection.disconnect();
			}
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (final IOException e) {
			}
		}
		return false;
	}
	
	public class FlushedInputStream extends FilterInputStream {  
		  
        public FlushedInputStream(InputStream inputStream) {  
            super(inputStream);  
        }  

        @Override  
        public long skip(long n) throws IOException {  
            long totalBytesSkipped = 0L;  
            while (totalBytesSkipped < n) {  
                long bytesSkipped = in.skip(n - totalBytesSkipped);  
                if (bytesSkipped == 0L) {  
                    int by_te = read();  
                    if (by_te < 0) {  
                        break; // we reached EOF  
                    } else {  
                        bytesSkipped = 1; // we read one byte  
                    }  
                }  
                totalBytesSkipped += bytesSkipped;  
            }  
            return totalBytesSkipped;  
        }  
    }
	public static void main(String[] args) {
		JsonDefaultURLTest test = new JsonDefaultURLTest();
		String url = "http://127.0.0.1:8008/index_json.jsp";
		String jsonResult = "";
		test.downloadToLocalJsonStringByUrl(url, jsonResult);
		System.out.println(jsonResult);
	}
}
