package com.zhang.zhihu;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.zhang.zhihu.data.TitleNews;


import java.util.List;

/**
 * Created by 张 on 2017/3/17.
 */

public class HeaderPagerAdapter extends PagerAdapter {


    List<TitleNews> mTitleNewses;
    List<ImageView> mImageViewList;
    Context mContext;

    public HeaderPagerAdapter(List<TitleNews> titleNewses,List<ImageView> imageViewList){
        mTitleNewses = titleNewses;
        mImageViewList = imageViewList;
   }

    @Override
    public int getCount() {
        return mTitleNewses.size();
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object){
        container.removeView(mImageViewList.get(position));
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position){

        ImageView imageView = mImageViewList.get(position);



        container.addView(imageView);

        return imageView;
    }
}
