package com.zhang.zhihu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.zhang.zhihu.data.ItemNews;
import com.zhang.zhihu.util.HttpCallbackListener;
import com.zhang.zhihu.util.HttpUtil;
import com.zhang.zhihu.util.Utility;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity  {
    RecyclerView recyclerView;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new Adapter(Utility.mItemNewsList,Utility.mTitleNewsList,MainActivity.this);
        recyclerView.setAdapter(adapter);

        sendRequest();

    }


    private void sendRequest(){
        HttpUtil.sendHttpRequest("http://news-at.zhihu.com/api/4/news/latest", new HttpCallbackListener() {
            @Override
            public void onFinish(final String response) {
                boolean result = false;
                result = Utility.handleNewses(response);

                if(result) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            adapter.notifyDataSetChanged();

                        }
                    });
                }else {
                    Toast.makeText(MainActivity.this,"网络错误",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
    }
    
}
