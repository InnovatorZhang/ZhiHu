package com.zhang.zhihu;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.zhang.zhihu.util.HttpCallbackListener;
import com.zhang.zhihu.util.HttpUtil;
import com.zhang.zhihu.util.Utility;

public class DetailActivity extends AppCompatActivity {

    private String mId;
    TextView mTextView;
    ImageView mImageView;
    TextView mTitleView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        mImageView = (ImageView) findViewById(R.id.detail_image);
        mTextView = (TextView)findViewById(R.id.detail_textview);
        mTitleView = (TextView)findViewById(R.id.detail_title);

        mId = getIntent().getStringExtra("ID");
        sendRequest();

    }

    private void sendRequest(){
        String address = "http://news-at.zhihu.com/api/4/news/" + mId;

        HttpUtil.sendHttpRequest(address, new HttpCallbackListener() {

            boolean result = false;
            @Override
            public void onFinish(String response) {
                result = Utility.handleNews(response);
                if(result){
                    /*
                    * 在主线程中修改UI*/
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            /*
                            *加载html格式文件
                            */
                            mTextView.setText(Html.fromHtml(Utility.body));

                            mTitleView.setText("  " + Utility.title);
                            /*加载图片
                            * 用开源库picsso
                            * */
                            Picasso.with(DetailActivity.this).load(Utility.imageId).fit().into(mImageView);
                        }
                    });
                }else{
                    /*
                    网络错误
                     */
                    Toast.makeText(DetailActivity.this,"网络错误",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Exception e) {

            }
        });
    }
}
