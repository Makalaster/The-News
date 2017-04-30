package com.example.ivonneortega.the_news_project.MainActivity.RecyclerViewAdapters;

import android.content.Intent;
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
 * Created by ivonneortega on 4/29/17.
 */

public class CategoryIndividualItemRecyclerViewAdapter extends RecyclerView.Adapter<CategoryIndividualItemRecyclerViewAdapter.CategoryIndividualItemViewHolder> {

    private static final int VIEW_TYPE_MORE = 1 ;
    private static final int VIEW_TYPE_ARTICLE = 2 ;
    List<CategoryIndividualItem> mList;


    public CategoryIndividualItemRecyclerViewAdapter(List<CategoryIndividualItem> individualItems) {
        mList = individualItems;

    }


    @Override
    public CategoryIndividualItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        //IF THE TYPE OF VIEW IS A TYPE ARTICLE INFLATE THE ARTICLES XML
        if(viewType == VIEW_TYPE_ARTICLE)
        {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new CategoryIndividualItemViewHolder(inflater.inflate(R.layout.custom_recyclerview_all_stories_horizontal,parent,false));
        }
        //IF THE TYPE OF VIEW IS A TYPE SEE MORE INFLATE THE SEE MORE XML
        else
        {
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            return new CategoryIndividualItemViewHolder(inflater.inflate(R.layout.see_more_custom,parent,false));
        }

    }

    @Override
    public void onBindViewHolder(CategoryIndividualItemRecyclerViewAdapter.CategoryIndividualItemViewHolder holder, int position) {
        //This is the recycler view that scrolls horizontally

        //If we are not at the last position
        if(position == mList.size()-1)
        {

        }
        //If we are at the last position of the list then show the "SEE MORE" TEXTVIEW
        else
        {
            //TODO SET IMAGES FROM ARTICLES USING PICASSO
            //holder.mTitleOfCategory =

            //This is the title of each article
            holder.mTitleOfArticle.setText(mList.get(position).getTitle());
            holder.mRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext().getApplicationContext(), DetailViewActivity.class);
                    v.getContext().startActivity(intent);
                }
            });

        }




    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class CategoryIndividualItemViewHolder extends RecyclerView.ViewHolder {

        TextView mTitleOfArticle;
        ImageView mArticleImage;
        View mSeeMore;
        View mRoot;


        public CategoryIndividualItemViewHolder(View itemView) {
            super(itemView);
            mTitleOfArticle = (TextView) itemView.findViewById(R.id.all_stories_article_title);
            mArticleImage = (ImageView) itemView.findViewById(R.id.all_stories_article_images);
            mSeeMore = itemView.findViewById(R.id.see_more_layout);
            mRoot = itemView.findViewById(R.id.root_category_individual_item);


        }
    }

    @Override
    public int getItemViewType(int position) {
        return (position == mList.size()-1) ? VIEW_TYPE_MORE : VIEW_TYPE_ARTICLE;
    }
}
