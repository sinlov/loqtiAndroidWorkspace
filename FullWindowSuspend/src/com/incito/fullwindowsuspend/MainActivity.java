
package com.incito.fullwindowsuspend;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends Activity {
    private ListView ll_main;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.ll_main = (ListView)findViewById(R.id.ll_main);
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
        //         Intent intent = new Intent(MainActivity.this, FloatViewService.class);  
        //         //启动FloatViewService  
        //         startService(intent);  
        super.onStart();
    }

    @Override
    protected void onStop() {
        // 销毁悬浮窗
        //        Intent intent = new Intent(MainActivity.this, FloatViewService.class);  
        //        //终止FloatViewService  
        //        stopService(intent); 
        super.onStop();
    }

}
