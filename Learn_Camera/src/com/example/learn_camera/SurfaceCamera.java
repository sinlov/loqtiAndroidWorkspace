
package com.example.learn_camera;

import com.example.learn_camera.view.MaskSurfaceViewg;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PixelFormat;
import android.graphics.Bitmap.CompressFormat;
import android.hardware.Camera;
import android.hardware.Camera.PictureCallback;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.Method;

public class SurfaceCamera extends Activity {
    private final String TAG = "Learn_Camera";
    private SurfaceView surface_caream;
    public Camera main_camera;
    private boolean isPreview;
    private Activity activity = this;
    private MaskSurfaceViewg mMaskSurfaceViewwg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window activitiyWindow = getWindow(); //get window
        requestWindowFeature(Window.FEATURE_NO_TITLE); //set no title
        activitiyWindow.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //set full screen
        activitiyWindow.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);//set screen on
        setContentView(R.layout.activity_surface_camera);
        initUI();
    }
    /**
     * 初始化主界面
     * @Title: initUI   
     * @Description: 初始化主界面  
     * @param:       
     * @return: void      
     * @throws
     */
    private void initUI() {
        Toast.makeText(getApplication(), "点击画面对焦" + "\n" +
                "音量减少按钮拍摄" + "\n" +
                "拍摄照片在SD卡根目录： 当前时间整数.jpg" + "\n"+ 
                "使用默认相机参数，效果很很垃圾",
                Toast.LENGTH_LONG).show();
        this.surface_caream = (SurfaceView)findViewById(R.id.surface_surfacecamera_camera);
        this.mMaskSurfaceViewwg = (MaskSurfaceViewg)findViewById(R.id.masksurface_surfacecamera_camera);
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);//得到窗口管理器
        Display display  = wm.getDefaultDisplay();//得到当前屏幕

        surface_caream.getHolder().setFixedSize(600, 800);
        surface_caream.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS); //set surface don't keep buffers to wait UI push content to there.
        surface_caream.getHolder().addCallback(new SurfaceCallback());
//        surface_caream.setOnTouchListener(new OnTouchListener() {
//            
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                int action = event.getAction();
//                switch (action) {
//                    case MotionEvent.ACTION_DOWN:
//                        if (main_camera != null) {
//                            main_camera.autoFocus(null);
//                        }
//                        break;
//
//                    default:
//                        break;
//                }
//                return false;
//            }
//        });
        mMaskSurfaceViewwg.setOnTouchListener(new OnTouchListener() {
            
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
              switch (action) {
                  case MotionEvent.ACTION_DOWN:
                      if (main_camera != null) {
                          main_camera.autoFocus(null);
                      }
                      break;

                  default:
                      break;
              }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Toast.makeText(getApplication(), "点击画面对焦" + "\n" +
                    "音量减少按钮拍摄" + "\n" +
                    "拍摄照片在SD卡根目录： 当前时间整数.jpg" + "\n"+ 
                    "使用默认相机参数，效果很很垃圾",
                    Toast.LENGTH_LONG).show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(main_camera!=null&&event.getRepeatCount()==0)//代表只按了一下
        {
            switch(keyCode){
                case KeyEvent.KEYCODE_VOLUME_UP://如果是音量向上
//                    main_camera.autoFocus(null);//自动对焦
                    break;
                case KeyEvent.KEYCODE_VOLUME_DOWN://如果是音量向下
                    main_camera.takePicture(null, null, new TakePictureCallback());//将拍到的照片给第三个对象中
                    //              main_camera.takePicture(null, new TakePictureCallback(), null);
                    break;
                case KeyEvent.KEYCODE_BACK:
                    finish();
                    break;
            }
        }
        return true;

        //      return super.onKeyDown(keyCode, event);
    }
    /**
     * 打开摄像头，并预览摄像头图像
     * <li> setParameters(parameters) 真机测试时，这里有不兼容问题，解决中
     * @ClassName:  SurfaceCallback   
     * @Description:打开摄像头，并预览摄像头图像   
     * @author: ErZheng  
     * @date:   Jan 4, 2015 11:44:36 AM   
     *
     */
    public class SurfaceCallback implements SurfaceHolder.Callback{
        @SuppressWarnings({
                "unused", "deprecation"
        })
        @Override
        public void surfaceCreated(SurfaceHolder holder) {
            try {
                main_camera = Camera.open();//打开硬件摄像头，这里导包得时候一定要注意是android.hardware.Camera
                getCameraClass(main_camera.getClass());
                
                WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);//得到窗口管理器
                Display display  = wm.getDefaultDisplay();//得到当前屏幕
                Camera.Parameters parameters = main_camera.getParameters();//得到摄像头的参数
                Log.d("this", "getPreviewSize Height: " + parameters.getPreviewSize().height + "\n"
                        + "getPreviewSize width : " + parameters.getPreviewSize().width 
                        );
                //              parameters.setPreviewSize(600, 800);//设置预览照片的大小
                //              parameters.setPreviewFrameRate(3);//设置每秒3帧
                parameters.setPictureFormat(PixelFormat.JPEG);//设置照片的格式
                parameters.setJpegQuality(100);//设置照片的质量

                //              parameters.setPictureSize(720, 960);//设置照片的大小，默认是和     屏幕一样大
                main_camera.setParameters(parameters); // 真机测试时，这里有不兼容问题，解决中
                Log.d("this", "getPictureSize Height: " + parameters.getPictureSize().height + "\n"
                        + "getPictureSize width: " + parameters.getPictureSize().width 
                        );
                //              int camera = Camera.getNumberOfCameras();
                setCameraDisplayOrientation(activity, 0, main_camera);//0表示摄像头列表里的第一个摄像头，预留切换摄像头功能
                main_camera.setPreviewDisplay(surface_caream.getHolder());//通过SurfaceView显示取景画面
                main_camera.startPreview();//开始预览
                isPreview = true;//设置是否预览参数为真
            } catch (IOException e) {
                Log.e(TAG, e.toString());
            }
        }

        @Override
        public void surfaceChanged(SurfaceHolder holder, int format, int width,
                int height) {
        }

        @Override
        public void surfaceDestroyed(SurfaceHolder holder) {
            if(main_camera!=null){
                if(isPreview){//如果正在预览 
                    main_camera.stopPreview();
                    main_camera.release();
                }
            }
        }

    }   
    /**
     * 拍照功能实现
     * @ClassName:  TakePictureCallback   
     * @Description:Take picture when callback   
     * @author: ErZheng  
     * @date:   Jan 4, 2015 11:46:07 AM   
     *
     */
    private final class TakePictureCallback implements PictureCallback{
        public void onPictureTaken(byte[] data, Camera camera) {
            try {
                Bitmap bitmap = BitmapFactory.decodeByteArray(data, 0, data.length);
                File file = new File(Environment.getExternalStorageDirectory(),System.currentTimeMillis()+".jpg");
                FileOutputStream outputStream = new FileOutputStream(file);
                //              bitmap.compress(CompressFormat.PNG, 100, outputStream);
                bitmap.compress(CompressFormat.JPEG, 100, outputStream);
                //              因为无法获取相机参数，拍照效果会非常坑爹。
                outputStream.close();
                camera.stopPreview();
                camera.startPreview();//处理完数据之后可以预览
            } catch (Exception e) {
                Log.e("Learn_Camera", e.toString());
            }
        }
    }

    @SuppressWarnings("unused")
    private final class TakePictureCallback2Raw implements PictureCallback{

        @Override
        public void onPictureTaken(byte[] data, Camera camera) {

        }

    }

    /**
     * 根据横竖屏自动调节preview方向。
     * <li>Starting from API level 14, this method can be called when preview is active.
     * @Title: setCameraDisplayOrientation   
     * @Description: auto set preview    
     * @param: @param activity
     * @param: @param cameraId
     * @param: @param camera      
     * @return: void      
     * @throws
     */
    private static void setCameraDisplayOrientation(Activity activity,int cameraId, Camera camera) 
    {    
        Camera.CameraInfo info = new Camera.CameraInfo(); 
        Camera.getCameraInfo(cameraId, info);      
        int rotation = activity.getWindowManager().getDefaultDisplay().getRotation();

        //degrees  the angle that the picture will be rotated clockwise. Valid values are 0, 90, 180, and 270. 
        //The starting position is 0 (landscape). 

        int degrees = 0;
        switch (rotation) 
        {   
            case Surface.ROTATION_0: degrees = 0; break;         
            case Surface.ROTATION_90: degrees = 90; break;    
            case Surface.ROTATION_180: degrees = 180; break; 
            case Surface.ROTATION_270: degrees = 270; break;  
        }      
        int result;  
        if (info.facing == Camera.CameraInfo.CAMERA_FACING_FRONT)
        {        
            result = (info.orientation + degrees) % 360;     
            result = (360 - result) % 360;  // compensate the mirror   
        } 
        else 
        {  
            // back-facing       
            result = (info.orientation - degrees + 360) % 360;   
        }     
        camera.setDisplayOrientation(result);  
    } 

    public void getCameraClass(Class<?> mClass){
//        List<Object> list = new ArrayList<Object>();
        try {
            Object o = mClass.newInstance();
            if (!(o instanceof Camera)) {
                Log.d(TAG, o.getClass().toString());
                throw new IllegalAccessError("this class isn't Camera");
            }
            AccessibleObject ao = (AccessibleObject) mClass.newInstance();
            ao.setAccessible(false);
            Class<?> c = ao.getClass();
            
            Method[] a = c.getDeclaredMethods();
            for (int i = 0; i < a.length; i++) {
                String temp = a[i].getName();
                Log.d(TAG, temp);
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
