package com.zhang.zhihu.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by 张 on 2017/3/15.
 */

public class HttpUtil {
    public static void sendHttpRequest(final String address,final HttpCallbackListener listener){
        //开启线程发起请求
        new Thread(new Runnable() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                BufferedReader bufferedReader = null;
                try{
                    URL url = new URL(address);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(8000);
                    connection.setReadTimeout(8000);
                    connection.setDoInput(true);
                    InputStream in = connection.getInputStream();
                    //下面对获取到的输入流进行读取
                    bufferedReader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while((line = bufferedReader.readLine()) != null){
                        response.append(line);
                    }

                    if(listener != null){
                        //回调onFinish方法
                        listener.onFinish(response.toString());
                    }
                }catch (Exception e){
                    if(listener != null) {
                        //回调onError方法
                        listener.onError(e);
                    }
                    e.printStackTrace();
                }finally {
                    if(bufferedReader != null){
                        try{
                            bufferedReader.close();
                        }catch (IOException e1){
                            e1.printStackTrace();
                        }
                    }
                    if(connection != null){
                        connection.disconnect();
                    }
                }
            }
        }).start();
    }

}
