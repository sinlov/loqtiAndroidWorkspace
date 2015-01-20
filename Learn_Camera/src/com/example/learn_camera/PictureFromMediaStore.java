
package com.example.learn_camera;



import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.media.ThumbnailUtils;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
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

public class PictureFromMediaStore extends Activity implements OnClickListener{
    private final static int TAKE_PIC_FROM_SYS_REQUST = 1000;
    private final static int SAFTHANLDER_SAVE_PIC = 10000;
    private Button mBtnTackPicture;
    private Button mBtnAlbumShow;
    private ImageView mImgAlubmShow;
    private Bitmap mBitmap;
    private SaftHandler mHandler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_from_media_store);
        initUI();
    }
    private void initUI() {
        mHandler = new SaftHandler(this);
        this.mBtnTackPicture = (Button)findViewById(R.id.btn_picturefrommediastore_take);
        this.mBtnAlbumShow = (Button)findViewById(R.id.btn_picturefrommediastore_show);
        mBtnAlbumShow.setOnClickListener(this);
        mBtnTackPicture.setOnClickListener(this);
        
        this.mImgAlubmShow = (ImageView)findViewById(R.id.img_picturefromamediastore_show);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.take_picture_from_media_store, menu);
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

        int sClickid = v.getId();
        switch (sClickid) {
            case R.id.btn_picturefrommediastore_take:
                Intent openSYSCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(openSYSCamera, TAKE_PIC_FROM_SYS_REQUST);
                break;
            case R.id.btn_picturefrommediastore_show:
                if (mBitmap != null) {
                    mHandler.obtainMessage(SAFTHANLDER_SAVE_PIC, mBitmap).sendToTarget();
                    mBitmap = ThumbnailUtils.extractThumbnail(mBitmap, 600, 800);
                    mImgAlubmShow.setImageBitmap(mBitmap);
                    int tempBitmapCount = mBitmap.getByteCount();
                    Toast.makeText(getApplication(), "Take picture: " + "\n" + tempBitmapCount, Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(getApplication(), "Take picture error", Toast.LENGTH_SHORT).show();
                }
                
                break;
            default:
                break;
        }
    
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PIC_FROM_SYS_REQUST) {
            if (resultCode == Activity.RESULT_OK) {
                mBitmap = (Bitmap) data.getExtras().get("data");
                if (mBitmap != null) {
//                    mBitmap = ThumbnailUtils.extractThumbnail(mBitmap, 600, 800);
                }
            }
        }
    }
    private static class SaftHandler extends Handler{
        private static WeakReference<PictureFromMediaStore> wrActivity;
        /**
         * set taget Activity to Weak reference and the customize handler be recyle timely.
         *@param bewrMainActivity
         */
        public SaftHandler(PictureFromMediaStore bewrMainActivity) {
            wrActivity = new WeakReference<PictureFromMediaStore>(bewrMainActivity);
        }
        @SuppressWarnings("unused")
        public WeakReference<PictureFromMediaStore> get(){
            return wrActivity;
        }
        @Override
        public void handleMessage(Message msg) {
            // TODO
            super.handleMessage(msg);
            switch (msg.what) {
                case SAFTHANLDER_SAVE_PIC:
                    try {
                        Bitmap tempBitmap = (Bitmap) msg.obj;
                        ByteArrayOutputStream output = new ByteArrayOutputStream();
                        tempBitmap.compress(CompressFormat.PNG, 100, output);
                        byte[] result = output.toByteArray();
                        File file = new File(Environment.getExternalStorageDirectory(),System.currentTimeMillis()+".jpg");
                        
                        FileOutputStream outputStream = new FileOutputStream(file);
                            outputStream.write(result);
                        //              bitmap.compress(CompressFormat.PNG, 100, outputStream);
                        //              因为无法获取相机参数，拍照效果会非常坑爹。
                        outputStream.close();
                        tempBitmap.recycle();
                    } catch (Exception e) {
                        Log.e("Learn_Camera", e.toString());
                    }
                    break;

                default:
                    break;
            }
        }
    }
}
