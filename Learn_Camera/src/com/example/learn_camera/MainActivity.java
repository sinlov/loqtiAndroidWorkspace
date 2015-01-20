package com.example.learn_camera;

import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.os.Bundle;

public class MainActivity extends Activity {
//    private final String TAG = "Learn_Camera";
    private ListView mListView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
        this.mListView = (ListView)findViewById(R.id.lv_main_showdemo);
        ArrayAdapter<String> arraryListAdapter = new ArrayAdapter<String>(this, 
                android.R.layout.simple_list_item_1,
                new String[]{
                "Picture From album",
                "SufaceCamera",
                "Picture From MediaStore"
        });
        mListView.setAdapter(arraryListAdapter);
        mListView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(getApplication(), PictureFromAlbum.class));
                        break;
                    case 1:
                        startActivity(new Intent(getApplication(), SurfaceCamera.class));
                        break;
                    case 2:
                        startActivity(new Intent(getApplication(), PictureFromMediaStore.class));
                        break;
                    default:
                        break;
                }
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
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
