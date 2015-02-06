
package cn.com.incito.bitmap_master;

import cn.com.incito.bitmap_master.app.BaseActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class VolleyTestActivity extends BaseActivity implements OnClickListener{
    private final static String TAG = "VolleyTestActivity";
    
    private TextView tv_result_show;
    private Button btn_getJson;
    private Button btn_postJson;
    
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
                tv_result_show.setText(response);
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
        this.tv_result_show = (TextView)findViewById(R.id.tv_volleytest_resultshow);
        this.btn_getJson = (Button)findViewById(R.id.btn_volleytest_getJson);
        btn_getJson.setOnClickListener(this);
        this.btn_postJson = (Button)findViewById(R.id.btn_volleytest_PostJson);
        btn_postJson.setOnClickListener(this);
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
                String urlapi = "http://192.168.30.50/gcxz/index.php?app=interface&act=login";
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
                        
                        params.put("user","xucaibing");
                        params.put("password","111111");
                        return params;
                    }};
//                    Toast.makeText(getApplication(), sr.getUrl(), Toast.LENGTH_SHORT).show();
                    mRequestQueue.add(sr);
                break;
            default:
                break;
        }
    }
}
