
package cn.com.incito.bitmap_master;

import cn.com.incito.bitmap_master.app.BaseActivity;
import cn.com.incito.lighthttpclient.DefalutLightHttpClient;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class LightHttpClientActivity extends BaseActivity implements OnClickListener{
    private final static String TAG = "LightHttpClientActivity ";
    
    private Button btn_jsonGet;
    private Button btn_jsonPost;
    private Button btn_jsonBitmapGet;
    private Button btn_jsonBitmapPost;
    private ImageView img_showBitmap;
    private TextView tv_showJson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_light_http_client);
        initUI();
        
    }

    private void initUI() {
        this.tv_showJson = (TextView)findViewById(R.id.tv_lightHttpClient_show_json);
        this.img_showBitmap = (ImageView)findViewById(R.id.img_lightHttpClient_show);
        this.btn_jsonGet = (Button)findViewById(R.id.btn_lightHttpClient_jsonGet);
        btn_jsonGet.setOnClickListener(this);
        this.btn_jsonPost = (Button)findViewById(R.id.btn_lightHttpClient_jsonPost);
        this.btn_jsonBitmapGet = (Button)findViewById(R.id.btn_lightHttpClient_bitmapGet);
        this.btn_jsonBitmapPost = (Button)findViewById(R.id.btn_lightHttpClient_bitmapPost);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.light_http_client, menu);
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
            case R.id.btn_lightHttpClient_jsonGet:
                
                break;
            default:
                break;
        }
    }
}
