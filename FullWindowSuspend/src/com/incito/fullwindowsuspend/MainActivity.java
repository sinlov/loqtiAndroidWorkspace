
package com.incito.fullwindowsuspend;

import com.incito.fullwindowsuspend.app.MyApplication;
import com.incito.fullwindowsuspend.tools.DisplayUtil;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
	private static final String TAG = MyApplication.TAG;
    private ListView ll_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.ll_main = (ListView)findViewById(R.id.ll_main);
        
		Log.d(TAG, "du.getDensity(): " + DisplayUtil.getDensity());
		Log.d(TAG, "du.getDensityDpi(): " + DisplayUtil.getDensityDpi());
		Log.d(TAG, "du.getScaledDensity(): " + DisplayUtil.getScaledDensity());
		Log.d(TAG, "du.getWidth(): " + DisplayUtil.getWidth());
		Log.d(TAG, "du.getHeight(): " + DisplayUtil.getHeight());
		Log.d(TAG, "du.getWidthPixels(): " + DisplayUtil.getWidthPixels());
		Log.d(TAG, "du.getHeightPixels(): " + DisplayUtil.getHeightPixels());
		Log.d(TAG, "du.getXdpi(): " + DisplayUtil.getXdpi());
		Log.d(TAG, "du.getYdpi(): " + DisplayUtil.getYdpi());
        
        ArrayAdapter<String> adapater = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,
                new String[]{
                "Open Suspend Button",
                "Close Suspend Button"
        });
        this.ll_main.setAdapter(adapater);
        ll_main.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, FloatViewService.class);  
                switch (position) {
                    case 0:
                        //启动FloatViewService  
                        startService(intent);  
                        break;
                    case 1:
                        // 销毁悬浮窗
                        //终止FloatViewService  
                        stopService(intent); 
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

}
