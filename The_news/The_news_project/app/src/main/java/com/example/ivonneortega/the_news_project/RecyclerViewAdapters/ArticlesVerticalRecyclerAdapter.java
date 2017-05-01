package com.example.ivonneortega.the_news_project.RecyclerViewAdapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ivonneortega.the_news_project.Article;
import com.example.ivonneortega.the_news_project.DetailView.DetailViewActivity;
import com.example.ivonneortega.the_news_project.R;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by ivonneortega on 4/30/17.
 */

public class ArticlesVerticalRecyclerAdapter extends RecyclerView.Adapter<ArticlesVerticalRecyclerAdapter.ArticlesViewHolder>
implements View.OnClickListener{

    List<Article> mList;
    boolean mIsSaveFragment;


    public ArticlesVerticalRecyclerAdapter(List<Article> list, boolean isSaveFragment) {
        mList = list;
        mIsSaveFragment = isSaveFragment;
    }

    @Override
    public ArticlesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new ArticlesViewHolder(inflater.inflate(R.layout.custom_top_stories,parent,false));
    }

    @Override
    public void onBindViewHolder(ArticlesViewHolder holder, int position) {
        holder.mTitle.setText(mList.get(position).getTitle());
        holder.mCategory.setText(mList.get(position).getCategory());
        holder.mDate.setText(mList.get(position).getDate());
        if(!mIsSaveFragment) {
            if (mList.get(position).isSaved())
                holder.mHeart.setImageResource(R.mipmap.ic_favorite_black_24dp);
            else
                holder.mHeart.setImageResource(R.mipmap.ic_favorite_border_black_24dp);
                holder.mHeart.setOnClickListener(this);

        }
        else
        {
            holder.mHeart.setVisibility(View.GONE);
        }

        //CLICK LISTENER WHEN CLICKING ON A PRODUCT
        holder.mRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOnProduct(v);
            }
        });
        holder.mShare.setOnClickListener(this);



    }

    private void clickOnProduct(View v)
    {
        Intent intent = new Intent(v.getContext().getApplicationContext(), DetailViewActivity.class);
        v.getContext().startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.top_stories_heart:
                //TODO DO SOMETHING WHEN USER CLICKS ON HEART
                Log.d(TAG, "onClick: Clicked on heart");
                break;
            case R.id.top_stories_share:
                //TODO DO SOMETHING WHEN USER CLICKS ON SHARE
                Log.d(TAG, "onClick: Clicked on share");

                break;
        }
    }

    public class ArticlesViewHolder extends RecyclerView.ViewHolder {

        //Setting Views including Recycler View
        TextView mTitle, mDate, mCategory;
        ImageView mHeart, mShare;
        View mRoot;

        public ArticlesViewHolder(View itemView) {
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
