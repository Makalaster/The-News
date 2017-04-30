package com.example.ivonneortega.the_news_project.MainActivity.RecyclerViewAdapters;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ivonneortega.the_news_project.CategoryIndividualItem;
import com.example.ivonneortega.the_news_project.DetailView.DetailViewActivity;
import com.example.ivonneortega.the_news_project.R;

import java.util.List;

/**
 * Created by ivonneortega on 4/30/17.
 */

public class TopStoriesRecyclerViewAdapter extends RecyclerView.Adapter<TopStoriesRecyclerViewAdapter.CustomViewHolder>{

    List<CategoryIndividualItem> mList;
    boolean mIsSaveFragment;


    public TopStoriesRecyclerViewAdapter(List<CategoryIndividualItem> list, boolean isSaveFragment) {
        mList = list;
        mIsSaveFragment = isSaveFragment;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new TopStoriesRecyclerViewAdapter.CustomViewHolder(inflater.inflate(R.layout.custom_top_stories,parent,false));
    }

    @Override
    public void onBindViewHolder(CustomViewHolder holder, int position) {
        holder.mTitle.setText(mList.get(position).getTitle());
        holder.mCategory.setText(mList.get(position).getCategory());
        holder.mDate.setText(mList.get(position).getDate());
        if(!mIsSaveFragment) {
            if (mList.get(position).isSaved())
                holder.mHeart.setImageResource(R.mipmap.ic_favorite_black_24dp);
            else
                holder.mHeart.setImageResource(R.mipmap.ic_favorite_border_black_24dp);
        }
        else
        {
            holder.mHeart.setVisibility(View.GONE);
        }

        holder.mRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext().getApplicationContext(), DetailViewActivity.class);
                v.getContext().startActivity(intent);
            }
        });



    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class CustomViewHolder extends RecyclerView.ViewHolder {

        //Setting Views including Recycler View
        TextView mTitle, mDate, mCategory;
        ImageView mHeart, mShare;
        View mRoot;

        public CustomViewHolder(View itemView) {
            super(itemView);
            mTitle = (TextView) itemView.findViewById(R.id.top_stories_title);
            mDate = (TextView) itemView.findViewById(R.id.top_stories_date);
            mCategory = (TextView) itemView.findViewById(R.id.top_stories_category);
            mHeart = (ImageView) itemView.findViewById(R.id.top_stories_heart);
            mShare = (ImageView) itemView.findViewById(R.id.top_stories_share);
            mRoot = itemView.findViewById(R.id.top_stories_root);


        }
    }
}
