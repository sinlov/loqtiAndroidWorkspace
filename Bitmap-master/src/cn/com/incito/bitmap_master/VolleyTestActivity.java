
package cn.com.incito.bitmap_master;

import cn.com.incito.bitmap_master.app.BaseActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class VolleyTestActivity extends BaseActivity implements OnClickListener{
    private final static String TAG = "VolleyTestActivity";

    private TextView tv_result_show;
    private Button btn_getJson;
    private Button btn_postJson;
    private Button btn_imagePost;
    private ProgressBar mPgBar;
    private TextView mTvProgress;
    private EditText mEtIPAddress;
    
    private String IpAddress = null;
    private String urlapi = "";

    private MyTask mTask;
    private RequestQueue mRequestQueue;
    //    private RequestFilter mRequsetFilter;
    private StringRequest mStringRequset;
    private Listener<String> volleyStringListener;
    private ErrorListener volleyErrorListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volley_test);
        initUI();
        initVolley();
    }



    @Override
    protected void onStop() {
        super.onStop();
    }



    private void initVolley() {
        this.mRequestQueue = Volley.newRequestQueue(this);
        this.volleyStringListener = new Listener<String>() {

            @Override
            public void onResponse(String response) {
                // TODO Auto-generated method stub
                Log.d(TAG + " response", response);
                tv_result_show.setText("urlapi: " + urlapi + "\n" + response);
            }
        };
        this.volleyErrorListener = new ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(TAG + " onErrorResponse", "" + error);
                String errorMessage = error.getMessage();
                Log.d(TAG + " onErrorResponse.getCause()", error.getCause() + "");
                if (errorMessage == null) {
                    Toast.makeText(getApplication(), "NetWork Error", Toast.LENGTH_SHORT).show();
                    if (error instanceof TimeoutError) {
                        Toast.makeText(getApplication(), "Error : " + "TimeoutError", Toast.LENGTH_LONG).show();
                        mRequestQueue.cancelAll(getApplication());
                    }
                    if (error instanceof ServerError) {
                        Toast.makeText(getApplication(), "Error : " + "ServerError", Toast.LENGTH_LONG).show();
                        mRequestQueue.cancelAll(getApplication());
                    }
                }else {
                    if (error instanceof NoConnectionError) {
                        Toast.makeText(getApplication(), "Error : " + "NoConnectionError", Toast.LENGTH_LONG).show();
                        mRequestQueue.cancelAll(getApplication());
                        //                        Toast.makeText(getApplication(), "Error statusCode: " + error.networkResponse.statusCode, Toast.LENGTH_SHORT).show();
                    }
                    
                }
            }
        };
    }

    @SuppressWarnings("rawtypes")
    public void addToRequestQueue(Request req, String tag) { 
        // set the default tag if tag is empty 
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag); 

        VolleyLog.d("Adding request to queue: %s", req.getUrl()); 

        getRequestQueue().add(req); 
    } 

    @SuppressWarnings("rawtypes")
    public void addToRequestQueue(Request req) { 
        // set the default tag if tag is empty 
        req.setTag(TAG); 

        getRequestQueue().add(req); 
    } 

    public RequestQueue getRequestQueue() { 
        // lazy initialize the request queue, the queue instance will be 
        // created when it is accessed for the first time 
        if (mRequestQueue == null) { 
            mRequestQueue = Volley.newRequestQueue(getApplicationContext()); 
        } 

        return mRequestQueue; 
    }

    public void cancelPendingRequests(Object tag) { 
        if (mRequestQueue != null) { 
            mRequestQueue.cancelAll(tag); 
        } 
    } 



    private void initUI() {
        this.mEtIPAddress = (EditText)findViewById(R.id.et_volleytest_ip);
        mEtIPAddress.addTextChangedListener(new TextWatcher() {
            
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                IpAddress = s.toString().trim();
            }
            
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                
            }
            
            @Override
            public void afterTextChanged(Editable s) {
                IpAddress = s.toString().trim();
            }
        });
        this.tv_result_show = (TextView)findViewById(R.id.tv_volleytest_resultshow);
        this.btn_getJson = (Button)findViewById(R.id.btn_volleytest_getJson);
        btn_getJson.setOnClickListener(this);
        this.btn_postJson = (Button)findViewById(R.id.btn_volleytest_PostJson);
        btn_postJson.setOnClickListener(this);
        this.btn_imagePost = (Button)findViewById(R.id.btn_volleytest_imagepost);
        btn_imagePost.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.volley_test, menu);
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
            case R.id.btn_volleytest_getJson:
                String url = "http://192.168.50.105:8008/index_json.jsp";
                mStringRequset = new StringRequest(url, volleyStringListener, volleyErrorListener);
                mRequestQueue.add(mStringRequset);
                break;
            case R.id.btn_volleytest_PostJson:
                String temp = "192.168.30.50" ;
                if (null != IpAddress) {
                    temp = IpAddress;
                }
//                urlapi = "http://" + temp + "/android/app/login";
                urlapi = "http://192.168.50.100:8080/android/app/login";
                StringRequest sr = new StringRequest(Request.Method.POST, urlapi, volleyStringListener, volleyErrorListener)
                {

                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String, String>();
                        params.put("Content-Type","application/x-www-form-urlencoded");
                        return params;
                    }

                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String,String> params = new HashMap<String, String>();

                        params.put("mac","ccd29bf5342b");
                        return params;
                    }
                    
                };
                    //                    Toast.makeText(getApplication(), sr.getUrl(), Toast.LENGTH_SHORT).show();
                    mRequestQueue.add(sr);
                    break;
            case R.id.btn_volleytest_imagepost:
                String filePath = Environment.getExternalStorageDirectory().getPath() +
                "/image/C_guanxi.jpg";
                String filePath_sec = Environment.getExternalStorageDirectory().getPath() +
                        "/image/G_degang.jpg";
                String urlimagapi = "http://192.168.30.50/gcxz/?app=interface&act=addGood";
                View upView = getLayoutInflater().inflate(R.layout.filebrowser_uploading, null);
                mPgBar = (ProgressBar)upView.findViewById(R.id.pb_filebrowser_uploading);
                mTvProgress = (TextView)upView.findViewById(R.id.tv_filebrowser_uploading);
                new AlertDialog.Builder(VolleyTestActivity.this).setTitle("上传进度").setView(upView).create().show();
                mTask = new MyTask();
                mTask.execute(urlimagapi, filePath, filePath_sec);
                break;
            default:
                break;
        }
    }

    private class MyTask extends AsyncTask<String, Integer, String>{

        @Override
        protected void onPostExecute(String result) {
            mTvProgress.setText(result);    
        }

        @Override
        protected void onPreExecute() {
            mTvProgress.setText("loading...");
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            mPgBar.setProgress(values[0]);
            mTvProgress.setText("loading..." + values[0] + "%");
        }

        @Override
        protected String doInBackground(String... params) {
            String uploadUrl = params[0];
            String filePath = params[1];
            String filePath_sec = params[2];
            String end = "\r\n";
            String twoHyphens = "--";
            String boundary = "******";
            try {
                URL url = new URL(uploadUrl);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url
                        .openConnection();
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.setUseCaches(false);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setConnectTimeout(6*1000);
                httpURLConnection.setRequestProperty("Connection", "Keep-Alive");
                httpURLConnection.setRequestProperty("Charset", "UTF-8");
                httpURLConnection.setRequestProperty("Content-Type",
                        "multipart/form-data;boundary=" + boundary);

                /**
                 * 这里重点注意： name里面的值为服务端需要key 只有这个key 才可以得到对应的文件
                 * filename是文件的名字，包含后缀名的 比如:abc.png
                 */
                DataOutputStream dos = new DataOutputStream(httpURLConnection
                        .getOutputStream());
                dos.writeBytes(twoHyphens + boundary + end);
                dos.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\""
                        + filePath.substring(filePath.lastIndexOf("/") + 1)
                        + "\"" + end);
                dos.writeBytes(end);

                FileInputStream fis = new FileInputStream(filePath);
                long total = fis.available();
                String totalstr = String.valueOf(total);
                Log.d("文件名", filePath);
                Log.d("文件大小", totalstr);
                byte[] buffer = new byte[100000]; // 8k
                int count = 0;
                int length = 0;
                while ((count = fis.read(buffer)) != -1) {
                    dos.write(buffer, 0, count);
                    length += count;
                    publishProgress((int) ((length / (float) total) * 100));
                    //为了演示进度,休眠500毫秒
                    Thread.sleep(500);
                } 
                
                dos.writeBytes("Content-Disposition: form-data; name=\"file\"; filename=\""
                        + filePath_sec.substring(filePath_sec.lastIndexOf("/") + 2)
                        + "\"" + end);
                
                FileInputStream fis_sec = new FileInputStream(filePath_sec);
                long total_sec = fis_sec.available();
                String totalstr_sec = String.valueOf(total_sec);
                Log.d("文件名", filePath_sec);
                Log.d("文件大小", totalstr_sec);
                byte[] buffer_sec = new byte[100000]; // 8k
                int count_sec = 0;
                int length_sec = 0;
                while ((count_sec = fis_sec.read(buffer_sec)) != -1) {
                    dos.write(buffer_sec, 0, count_sec);
                    length_sec += count;
                    publishProgress((int) ((length_sec / (float) total_sec) * 100));
                    //为了演示进度,休眠500毫秒
                    Thread.sleep(500);
                } 

                fis.close();
                fis_sec.close();
                dos.writeBytes(end);
                dos.writeBytes(twoHyphens + boundary + twoHyphens + end);
                dos.flush();

                InputStream is = httpURLConnection.getInputStream();
                InputStreamReader isr = new InputStreamReader(is, "utf-8");
                BufferedReader br = new BufferedReader(isr);
                @SuppressWarnings("unused")
                String result = br.readLine();
                dos.close();
                is.close();
                return "上传成功";
            }catch (Exception e) {
                e.printStackTrace();
                return "上传失败";
            }   
        }
    }
}
