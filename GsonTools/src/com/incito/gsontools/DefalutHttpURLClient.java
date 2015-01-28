/**
 * All rights Reserved, Designed By sinlov
 * @Title:  DefalutHttpURLClint.java 
 * @Package com.incito.gsontools
 * @Copyright:  incito Co., Ltd. Copyright,  All rights reserved 
 * @Description:  TODO<Plase input what is this doing> 
 * @author:  ErZheng 
 * @data:  Jan 28, 2015 5:10:28 PM 
 * @version:  V1.0 
 */
package com.incito.gsontools;

import java.io.InputStream;
import java.io.OutputStream;

/**
 * @ClassName: DefalutHttpURLClint
 * @Description:TODO(what to do)
 * @author: ErZheng
 * @date: Jan 28, 2015 5:10:28 PM
 * 
 */
public interface DefalutHttpURLClient {
	public boolean downloadToLocalJsonStringByUrl(String urlString,
			String outputString);
	public boolean downloadToLocalStreamByUrl(String urlString,
			OutputStream outputStream);
	public boolean uploadToServerJsonString(String urlString, String jsonString);
	public boolean uploadToServerSteam(String urlString, InputStream inputStream);
}
