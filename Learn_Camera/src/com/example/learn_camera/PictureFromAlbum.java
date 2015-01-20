
package com.example.learn_camera;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.media.ThumbnailUtils;
import android.net.Uri;
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

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.lang.ref.WeakReference;

public class PictureFromAlbum extends Activity implements OnClickListener{
//    private final String TAG = "Learn_Camera";
    private final static int TAKE_PIC_FROM_CAMERA_REQUST = 1000;
    private final static int TAKE_PIC_FROM_ALBUM_REQUST = 1001;
    private final static int TAKE_PIC_EXTRA_VIDEO_QUALITY = 10001;
    private final static int SAFTHANLDER_SAVE_PIC = 10000;
    private final static int PIC_ASPECTX = 1;
    private final static int PIC_ASPECTY = 1;
    private final static int PIC_OUTPUTX = 80;
    private final static int PIC_OUTPUTY = 80;
    private final static int BITMAP_WIDTH = 600;
    private final static int BITMAP_HEIGHT = 800;
    private final static int BITMAP_QUALITY = 100;
    private Button mBtnTackPicture;
    private Button mBtnAlbumShow;
    private ImageView mImgAlubmShow;
    private Bitmap mBitmap;
    @SuppressWarnings("unused")
    private SaftHandler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture_from_album);
        initUI();
    }

    private void initUI() {
        mHandler = new SaftHandler(this);
        this.mBtnTackPicture = (Button)findViewById(R.id.btn_picturefromalbum_take);
        this.mBtnAlbumShow = (Button)findViewById(R.id.btn_picturefromalbum_show);
        mBtnAlbumShow.setOnClickListener(this);
        mBtnTackPicture.setOnClickListener(this);
        
        this.mImgAlubmShow = (ImageView)findViewById(R.id.img_picturefromalbum_show);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.picture_from_album, menu);
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
            case R.id.btn_picturefromalbum_take:
                Intent openSYSCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                openSYSCamera.putExtra(MediaStore.EXTRA_OUTPUT,  
                        Uri.fromFile(new File(Environment  
                                .getExternalStorageDirectory(), "camera.jpg")));
                openSYSCamera.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, TAKE_PIC_EXTRA_VIDEO_QUALITY); 
                startActivityForResult(openSYSCamera, TAKE_PIC_FROM_CAMERA_REQUST);
                break;
            case R.id.btn_picturefromalbum_show:
                Intent takePICFromAlbum = new Intent(Intent.ACTION_GET_CONTENT); 
                takePICFromAlbum.addCategory(Intent.CATEGORY_OPENABLE);  
                takePICFromAlbum.setType("image/*");  
                takePICFromAlbum.putExtra("crop", "true");  
                takePICFromAlbum.putExtra("aspectX",PIC_ASPECTX);  
                takePICFromAlbum.putExtra("aspectY",PIC_ASPECTY);  
                takePICFromAlbum.putExtra("outputX", PIC_OUTPUTX);  
                takePICFromAlbum.putExtra("outputY", PIC_OUTPUTY);  
                takePICFromAlbum.putExtra("return-data",true);
                startActivityForResult(takePICFromAlbum, TAKE_PIC_FROM_ALBUM_REQUST);
//                if (mBitmap != null) {
//                    mHandler.obtainMessage(SAFTHANLDER_SAVE_PIC, mBitmap).sendToTarget();
//                    Toast.makeText(getApplication(), "Take picture success ", Toast.LENGTH_SHORT).show();
//                }else {
//                    Toast.makeText(getApplication(), "Take picture error", Toast.LENGTH_SHORT).show();
//                }
                
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case TAKE_PIC_FROM_CAMERA_REQUST:
                    String pathName = new File(
                            Environment.getExternalStorageDirectory(),
                          "camera.jpg").getAbsolutePath();
                    Bitmap tempBitmap = BitmapFactory.decodeFile(pathName);
                    tempBitmap = ThumbnailUtils.extractThumbnail(tempBitmap, BITMAP_WIDTH, BITMAP_HEIGHT);
                    this.mImgAlubmShow.setImageBitmap(tempBitmap);
                    
//                    this.mImgAlubmShow.setImageDrawable(Drawable.createFromPath(
//                            new File(Environment.getExternalStorageDirectory(),
//                            "camera.jpg").getAbsolutePath()
//                                )
//                            );
                    
                    System.out.println("data CAMERA -->"+data);  
                    break;
                case TAKE_PIC_FROM_ALBUM_REQUST:
                    mBitmap = (Bitmap) data.getExtras().get("data");
                    if (mBitmap != null) {
                        mBitmap = ThumbnailUtils.extractThumbnail(mBitmap, BITMAP_WIDTH, BITMAP_HEIGHT);
                        this.mImgAlubmShow.setImageBitmap(mBitmap);
                    }
                    
                    System.out.println("data ALBUM -->"+data);  
                    break;
                default:
                    break;
            }
        }
    }
    
    private static class SaftHandler extends Handler{
        private static WeakReference<PictureFromAlbum> wrActivity;
        /**
         * set taget Activity to Weak reference and the customize handler be recyle timely.
         *@param bewrMainActivity
         */
        public SaftHandler(PictureFromAlbum bewrMainActivity) {
            wrActivity = new WeakReference<PictureFromAlbum>(bewrMainActivity);
        }
        @SuppressWarnings("unused")
        public WeakReference<PictureFromAlbum> get(){
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
                        tempBitmap.compress(CompressFormat.PNG, BITMAP_QUALITY, output);
                        byte[] result = output.toByteArray();
                        File file = new File(Environment.getExternalStorageDirectory(),System.currentTimeMillis()+".jpg");
                        
                        FileOutputStream outputStream = new FileOutputStream(file);
                            outputStream.write(result);
                        //              bitmap.compress(CompressFormat.PNG, 100, outputStream);
                        outputStream.close();
                        
                        tempBitmap = ThumbnailUtils.extractThumbnail(tempBitmap, BITMAP_WIDTH, BITMAP_HEIGHT);
                        wrActivity.get().mImgAlubmShow.setImageBitmap(tempBitmap);
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
