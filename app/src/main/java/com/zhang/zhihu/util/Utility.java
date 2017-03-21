package com.zhang.zhihu.util;

import android.text.TextUtils;

import com.zhang.zhihu.data.ItemNews;
import com.zhang.zhihu.data.TitleNews;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


import java.util.ArrayList;
import java.util.List;


/**
 * Created by 张 on 2017/3/15.
 */

public class Utility {

    public static List<ItemNews> mItemNewsList = new ArrayList<>();
    public static List<TitleNews> mTitleNewsList = new ArrayList<>();
    public static String body;
    public static String imageId;
    public static String title;
    
    public static boolean handleNewses(String response)  {
        try{
        if(!TextUtils.isEmpty(response)) {
            JSONObject jsonObject = new JSONObject(response);

            /*
            *得到viewpager中的数据
            */
            JSONArray titleJsonArray = jsonObject.getJSONArray("top_stories");
            readTitleJson(titleJsonArray);

            /*
            * 得到recyclerview中的数据
            */
            JSONArray itemJsonArray = jsonObject.getJSONArray("stories");
            readItemJson(itemJsonArray);

            return true;
        }
        }catch (JSONException e){
            e.printStackTrace();
        }
        return false;
    }

   public static boolean handleNews(String response){
       try{
           if(!TextUtils.isEmpty(response)) {
               JSONObject jsonObject = new JSONObject(response);
                body = jsonObject.getString("body");
                title = jsonObject.getString("title");
                imageId = jsonObject.getString("image");

               return true;
           }
       }catch (JSONException e){
           e.printStackTrace();
       }
       return false;
   }

    /*
    * 解析数据
    */
   private static void readItemJson(JSONArray jsonArray){
       try {
           for (int i = 0;i < jsonArray.length();i++){
               JSONObject jsonObject = jsonArray.getJSONObject(i);
               ItemNews news = new ItemNews();
               news.setTitle(jsonObject.getString("title"));
               news.setNewsId(jsonObject.getString("id"));

               JSONArray imagesArray = jsonObject.getJSONArray("images");//取得新闻的图片id数组
               news.setImageId(imagesArray.getString(0));//只要数组中第一张图片的id;

               mItemNewsList.add(news);
           }

       }catch (Exception e){
           e.printStackTrace();
       }
   }

    /*
     * 解析数据
     */
    private static void readTitleJson(JSONArray jsonArray){
        try {
            for (int i = 0;i < jsonArray.length();i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                TitleNews news = new TitleNews();
                news.setTitle(jsonObject.getString("title"));
                news.setNewsId(jsonObject.getString("id"));
                news.setImageId(jsonObject.getString("image"));

                mTitleNewsList.add(news);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
