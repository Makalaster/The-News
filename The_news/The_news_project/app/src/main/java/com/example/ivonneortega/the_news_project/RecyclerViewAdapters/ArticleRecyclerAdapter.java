package com.example.ivonneortega.the_news_project.RecyclerViewAdapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ivonneortega.the_news_project.data.Article;
import com.example.ivonneortega.the_news_project.CategoryView.CategoryViewActivity;
import com.example.ivonneortega.the_news_project.DatabaseHelper;
import com.example.ivonneortega.the_news_project.DetailView.CollectionDemoActivity;
import com.example.ivonneortega.the_news_project.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by ivonneortega on 4/29/17.
 */

public class ArticleRecyclerAdapter extends RecyclerView.Adapter<ArticleRecyclerAdapter.CategoryIndividualItemViewHolder> {

    private static final int VIEW_TYPE_MORE = 1 ;
    private static final int VIEW_TYPE_ARTICLE = 2 ;
    List<Article> mList;


    public ArticleRecyclerAdapter(List<Article> individualItems) {
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
    public void onBindViewHolder(final ArticleRecyclerAdapter.CategoryIndividualItemViewHolder holder, int position) {
        //This is the recycler view that scrolls horizontally

        //If we are not at the last position
        if(position == mList.size()-1)
        {
            holder.mSeeMore.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    goToCategoryView(mList.get(holder.getAdapterPosition()).getCategory(),v);
                }
            });
        }
        //If we are at the last position of the list then show the "SEE MORE" TEXTVIEW
        else
        {
            Picasso.with(holder.mArticleImage.getContext())
                    .load(mList.get(holder.getAdapterPosition()).getImage())
                    .fit()
                    .centerCrop()
                    .into(holder.mArticleImage);


            //This is the title of each article
            holder.mTitleOfArticle.setText(mList.get(position).getTitle());
            holder.mRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                 clickOnProduct(v,mList.get(holder.getAdapterPosition()).getId());
                }
            });


        }
    }

    private void clickOnProduct(View v,long id)
    {
        Intent intent = new Intent(v.getContext().getApplicationContext(), CollectionDemoActivity.class);
        intent.putExtra(DatabaseHelper.COL_ID,id);
        v.getContext().startActivity(intent);
    }

    private void goToCategoryView(String category,View v)
    {
        Intent intent = new Intent(v.getContext().getApplicationContext(), CategoryViewActivity.class);
        intent.putExtra(DatabaseHelper.COL_CATEGORY,category);
        v.getContext().startActivity(intent);
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
