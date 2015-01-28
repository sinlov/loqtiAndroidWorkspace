/*
 * Copyright (c) 2012, Incito Corporation, All Rights Reserved
 */
package cn.com.incito.bitmap_master;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration.Builder;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.media.ThumbnailUtils;
import android.os.SystemClock;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

/**
 * @description 
 * @author   tianran
 * @createDate Jan 28, 2015
 * @version  1.0
 */
public class CompressBitmap {
    private static CompressBitmap compressBitmap;
    private static Context mcontext;
    
    private static final String TAG = "CompressBitmap";
    
    private CompressBitmap(){
        
    }
    public static CompressBitmap getIntence(){
        if (compressBitmap == null) {
            return new CompressBitmap();
        }else {
            return compressBitmap;
        }
    }
    /**
     * use it must be initImageLoader by initImageLoader() method, 
     * <li> your must give image uri and tempImageView
     * <li>ImageLoader.getInstance().displayImage("file://" + uri,tempImageView); 
     * get bitmap use this way
     * <li>ImageLoader.getInstance().loadImageSync("file://" + uri);
     * @description 
     * @author   tianran
     * @createDate Jan 28, 2015
     * @param context
     */
    public static void Build(Context context){
        mcontext = context;
    }

    /**
     * Initialize image loader and set image catch
     * @description 
     * @author   tianran
     * @createDate Jan 28, 2015
     * @param isCathed open catch or not 
     * @param catchpath
     */
    @SuppressWarnings("deprecation")
    public void initImageLoader(boolean isCathed, String catchpath) {
        boolean defaultCathSet = false;
        defaultCathSet = isCathed;
        DisplayImageOptions options = new DisplayImageOptions.Builder()
        .cacheInMemory(true).cacheOnDisc(true)
        .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
        // 防止内存溢出的，图片太多就这这个。还有其他设置
        // 如Bitmap.Config.ARGB_8888
        .bitmapConfig(Bitmap.Config.RGB_565)

        // 默认图片
//      .showImageOnLoading(R.drawable.empty_photo) 

        // url为空会显示该图片，自己放在drawable里面的
//      .showImageForEmptyUri(R.drawable.img_hanging_hd) 

        // 加载失败显示的图片
//      .showImageOnFail(R.drawable.img_hanging_hd)

        // 图片圆角，不需要请屏蔽
//      .displayer(new RoundedBitmapDisplayer(5)) 
        .build();
        
        ImageLoaderConfiguration.Builder builder = new Builder(mcontext);
        builder
        .memoryCacheExtraOptions(1504, 1000)// 缓存在内存的图片的宽和高度   
        .memoryCache(new WeakMemoryCache())
        .memoryCacheSize(4 * 1024 * 1024); // 缓存到内存的最大数据
        if (defaultCathSet) {
            builder.discCache(
                    new UnlimitedDiscCache(new File(catchpath)))
                    // 缓存到文件的最大数据
                    .discCacheSize(50 * 1024 * 1024). 
                    // 文件数量
                    discCacheFileCount(1000); 
        }
     // 上面的options对象，一些属性配置
        ImageLoaderConfiguration config =  builder.defaultDisplayImageOptions(options).build();
        ImageLoader.getInstance().init(config); // 初始化
    }
    /**
     * save bitmap when compress
     * @description 
     * @author   tianran
     * @createDate Jan 28, 2015
     * @param tagetBitmap object
     * @param format CompressFormat.JPEG
     * @param quality 0-100
     * @param multiple 0-100
     * @param savePath 
     * @param saveName like System.currentTimeMillis() + "-" + multiple + ".jpg"
     * @param waitTime wait for IO need 20
     * @return is save success 
     */
    @SuppressWarnings("unused")
    private static boolean saveBitmapAfterCompress(Bitmap tagetBitmap, CompressFormat format, int quality, int multiple, String savePath , String saveName ,long waitTime){
        boolean isSaveSuccess = false;
        long defalutWaitTime = 1;
        if (tagetBitmap != null) {
            float fMultiple = (float)multiple / 100 ;
            int width = (int) (tagetBitmap.getWidth() * fMultiple);
            int height = (int) (tagetBitmap.getHeight() * fMultiple);
            try {
                tagetBitmap = ThumbnailUtils.extractThumbnail(tagetBitmap, width, height);
                ByteArrayOutputStream output = new ByteArrayOutputStream();
                tagetBitmap.compress(format, quality, output);
                byte[] result = output.toByteArray();
                File tempfile = new File(savePath);
                if (!tempfile.exists()) {
                    tempfile.mkdirs();
                }
                
                File file = new File(savePath, saveName);
                FileOutputStream outputStream = new FileOutputStream(file);
                outputStream.write(result);
                outputStream.close();
                if (defalutWaitTime < waitTime) {
                    defalutWaitTime = waitTime;
                }
                SystemClock.sleep(defalutWaitTime);
                isSaveSuccess = true;
            } catch (Exception e) {
                Log.e(TAG, "Error info: " + e.toString());
            }
        }
        return isSaveSuccess;
    }
}
