
package cn.com.incito.bitmap_master;

import cn.com.incito.bitmap_master.app.BaseActivity;

import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiscCache;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;

@SuppressLint("SdCardPath")
public class CompressImageActivity extends BaseActivity implements OnClickListener{
    private static final String TAG = "CompressImageActivity";
    private static final int MSG_SAVE_IMAGE = 1000;
    private static final int MSG_COMPRESS_IMAGELOADER = 1001;
    @SuppressWarnings("unused")
    private static final int BITMAP_QUALITY = 100;
    private String sdpath = Environment.getExternalStorageDirectory().getAbsolutePath();
    private Bitmap bitmapCompressTaget;
    private int heightBitmapCompressTaget;
    private int widthBitmapCompressTaget;
    private Button btn_beign_compress_by_thumbnailUtils;
    private Button btn_beign_compress_by_imageloader;
    private ImageView mImageView;
    private SaftHandler myHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compress_image);
        initImageLoader();
        initUI();
        initData();
    }

    private void initData() {
        this.bitmapCompressTaget = BitmapFactory.decodeFile(sdpath + "/camera.jpg");
        if (bitmapCompressTaget != null) {
            this.heightBitmapCompressTaget = bitmapCompressTaget.getHeight();
            this.widthBitmapCompressTaget = bitmapCompressTaget.getWidth();
            //            Log.d(TAG, "bitmapCompressTaget.getByteCount: " + bitmapCompressTaget.getByteCount());
        }
    }

    private void initUI() {
        myHandler = new SaftHandler(this);
        this.btn_beign_compress_by_thumbnailUtils = (Button)
                findViewById(R.id.btn_compressimage_begin_thumbnailutils);
        btn_beign_compress_by_thumbnailUtils.setOnClickListener(this);
        this.btn_beign_compress_by_imageloader = (Button)findViewById(R.id.btn_compressimage_by_imageloader);
        btn_beign_compress_by_imageloader.setOnClickListener(this);
        this.mImageView = (ImageView)findViewById(R.id.img_compressimage_show);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.compress_image, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_compressimage_begin_thumbnailutils:
                if (bitmapCompressTaget != null) {
                    myHandler.obtainMessage(MSG_SAVE_IMAGE, bitmapCompressTaget).sendToTarget();
                }else {
                    Toast.makeText(this, "bitmapget Error", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.btn_compressimage_by_imageloader:
                if (bitmapCompressTaget != null) {
                    myHandler.obtainMessage(MSG_COMPRESS_IMAGELOADER).sendToTarget();
                }
                else {
                    Toast.makeText(this, "bitmapget Error", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }
    private static class SaftHandler extends Handler{
        private static WeakReference<CompressImageActivity> wrActitiy;
        public SaftHandler(CompressImageActivity bewrActivity){
            wrActitiy = new WeakReference<CompressImageActivity>(bewrActivity);
        }
        @SuppressWarnings("unused")
        public WeakReference<CompressImageActivity> get(){
            return wrActitiy;
        }
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int width = wrActitiy.get().widthBitmapCompressTaget;
            int height = wrActitiy.get().heightBitmapCompressTaget;
            switch (msg.what) {
                case MSG_SAVE_IMAGE:
                    try {
                        Bitmap temp = (Bitmap) msg.obj;
                        //压缩图片
                        //分辨率压缩，文件直接减少为原来的1/4，可用插算法补全
                        temp = ThumbnailUtils.extractThumbnail(temp, width/2, height/2);
                        saveImag(temp, wrActitiy.get(),100);
                    } catch (Exception e) {
                        Log.e(TAG, e.toString());
                    }
                    break;
                case MSG_COMPRESS_IMAGELOADER:
                    try {
                        String uri = wrActitiy.get().sdpath + "/camera.jpg";
                        ImageView tempView = wrActitiy.get().mImageView;
                        ImageLoader imageloader = ImageLoader.getInstance();
                        imageloader.displayImage("file://" + uri,tempView);
                        Bitmap tempBitmap  = imageloader.loadImageSync("file://" + uri);
                        if (tempBitmap != null) {
                            tempBitmap = ThumbnailUtils.extractThumbnail(tempBitmap, width/2, height/2);
                            saveImag(tempBitmap, wrActitiy.get(),50);
                        }else{
                            Log.d(TAG, "ImageLoader.loadImageSync Error");
                        }

                    } catch (Exception e) {
                        Log.e(TAG, e.toString());
                    }
                    break;
                default:
                    break;
            }
        }

    }

    @SuppressWarnings("deprecation")
    public void initImageLoader() {
        DisplayImageOptions options = new DisplayImageOptions.Builder()

        .cacheInMemory(true).cacheOnDisc(true)

        .imageScaleType(ImageScaleType.IN_SAMPLE_INT)

        .bitmapConfig(Bitmap.Config.RGB_565)
        // 防止内存溢出的，图片太多就这这个。还有其他设置
        // 如Bitmap.Config.ARGB_8888

        //                .showImageOnLoading(R.drawable.empty_photo) // 默认图片

        //                .showImageForEmptyUri(R.drawable.img_hanging_hd) // url爲空會显示该图片，自己放在drawable里面的

        //                .showImageOnFail(R.drawable.img_hanging_hd)// 加载失败显示的图片

        //                .displayer(new RoundedBitmapDisplayer(5)) // 圆角，不需要请删除
        .build();

        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)

        .memoryCacheExtraOptions(1504, 1000)// 缓存在内存的图片的宽和高度

        .memoryCache(new WeakMemoryCache())

        .memoryCacheSize(4 * 1024 * 1024) // 缓存到内存的最大数据

        .discCache(
                new UnlimitedDiscCache(new File("/mnt/sdcard/image")))

                .discCacheSize(50 * 1024 * 1024). // 缓存到文件的最大数据

                discCacheFileCount(1000) // 文件数量

                .defaultDisplayImageOptions(options). // 上面的options对象，一些属性配置

                build();

        ImageLoader.getInstance().init(config); // 初始化
    }

    private static void saveImag(Bitmap temp, CompressImageActivity activity ,int quality){
        try {
            ByteArrayOutputStream output = new ByteArrayOutputStream();
            temp.compress(CompressFormat.JPEG, quality, output);
            byte[] result = output.toByteArray();
            File tempfile = new File(activity.sdpath + "/zImageCompress");
            if (!tempfile.exists()) {
                tempfile.mkdirs();
            }
            File file = new File(activity.sdpath + "/zImageCompress",System.currentTimeMillis()+".jpg");
            FileOutputStream outputStream = new FileOutputStream(file);
            outputStream.write(result);
            outputStream.close();
            SystemClock.sleep(200);
            //            temp = ThumbnailUtils.extractThumbnail(tempBitmap, BITMAP_WIDTH, BITMAP_HEIGHT);
            //            wrActivity.get().mImgAlubmShow.setImageBitmap(tempBitmap);
            int tagetByteCount = activity.bitmapCompressTaget.getByteCount();
            int compressByteCount = temp.getByteCount();
            Toast.makeText(activity, "图片压制成功 " + "\n"
                    +"tagetByteCount: " + tagetByteCount + "\n"
                    + "compressByteCount: " + compressByteCount + "\n"
                    + "Compress: " + ((float)compressByteCount/(float)tagetByteCount)* 100  +" %"
                    , Toast.LENGTH_LONG).show();
            temp = ThumbnailUtils.extractThumbnail(temp, 400, 300);
            activity.mImageView.setImageBitmap(temp);
        } catch (Exception e) {
            Log.d(TAG, "Error info: " + e.toString());
        }
    }

}
