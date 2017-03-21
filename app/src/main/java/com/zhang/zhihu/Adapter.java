package com.zhang.zhihu;

import android.content.Context;
import com.squareup.picasso.Picasso;

import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.zhang.zhihu.data.ItemNews;
import com.zhang.zhihu.data.TitleNews;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by 张 on 2017/3/15.
 */

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {


    private final static int VIEW_HEADER = 1, VIEW_ITEM = 0;
    private List<ItemNews> mItemNewses;
    private List<TitleNews> mTitleNewses;
    private Context mContext;
    List<ImageView> imageViewList = new ArrayList<>();

    public Adapter(List<ItemNews> itemNewses, List<TitleNews> titleNewses, Context context){
        mItemNewses = itemNewses;
        mTitleNewses = titleNewses;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if(viewType == VIEW_HEADER){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.viewpager_header, parent, false);
            return new ViewHolder(view,viewType);
        }else {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
            return new ViewHolder(view,viewType);
        }
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        if(position == 0){
            for(int i = 0;i < mTitleNewses.size();i++) {
                ImageView imageView = new ImageView(mContext);
                Picasso.with(mContext).load(mTitleNewses.get(i).getImageId()).fit().into(imageView);
                final int finalI = i;
                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(mContext,mTitleNewses.get(finalI).getNewsId() ,Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(mContext,DetailActivity.class);
                        intent.putExtra("ID",mTitleNewses.get(finalI).getNewsId());
                        mContext.startActivity(intent);
                    }
                });
                imageViewList.add(imageView);

            }

            HeaderPagerAdapter headerPagerAdapter = new HeaderPagerAdapter(mTitleNewses,imageViewList);
            holder.mViewPager.setAdapter(headerPagerAdapter);

            /*设置默认选中第三个*/
            holder.mViewPager.setCurrentItem(2);


            //为viewpager 设置监听事件
            holder.mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {

                    holder.mTitleText.setText(mTitleNewses.get(position).getTitle());

                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        }else {
            /*
            *因为viewpager占了一个位置，所以都要减一
            */
            holder.mTextView.setText(mItemNewses.get(position-1).getTitle());
            Picasso.with(mContext).load(mItemNewses.get(position-1).getImageId()).resize(50, 50).into(holder.mImageView);
        }

    }

    @Override
    public int getItemCount() {
        return mItemNewses.size()+1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView mTextView;
        ImageView mImageView;
        ViewPager mViewPager;
        TextView mTitleText;
        public ViewHolder(View itemView,int viewType) {
            super(itemView);

            if(viewType == VIEW_HEADER){
                mViewPager = (ViewPager)itemView.findViewById(R.id.viewpager);
                mTitleText = (TextView)itemView.findViewById(R.id.news_title);

            }else {
                mTextView = (TextView) itemView.findViewById(R.id.item_textview);
                mImageView = (ImageView) itemView.findViewById(R.id.image_view);

                /*设置点击监听*/
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        mTextView.setTextColor(mContext.getResources().getColor(R.color.gray));
                        Intent intent = new Intent(mContext,DetailActivity.class);
                        intent.putExtra("ID",mItemNewses.get(getLayoutPosition()-1).getNewsId());
                        mContext.startActivity(intent);
                    }
                });
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return VIEW_HEADER;
        } else {
            return VIEW_ITEM;
        }
    }

}
