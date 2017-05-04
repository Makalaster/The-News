package com.example.ivonneortega.the_news_project.recyclerViewAdapters;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.ivonneortega.the_news_project.detailView.CollectionDemoActivity;
import com.example.ivonneortega.the_news_project.R;
import com.example.ivonneortega.the_news_project.swipe.SaveSwipeLeft;
import com.example.ivonneortega.the_news_project.data.Article;
import com.example.ivonneortega.the_news_project.database.DatabaseHelper;
import com.squareup.picasso.Picasso;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by ivonneortega on 4/30/17.
 */

public class ArticlesVerticalRecyclerAdapter extends RecyclerView.Adapter<ArticlesVerticalRecyclerAdapter.ArticlesViewHolder>
implements View.OnClickListener, SaveSwipeLeft{

    private List<Article> mList;
    private boolean mIsSaveFragment;
    private int mPosition;
    Context mContext;
    View view;

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
    public void onBindViewHolder(final ArticlesViewHolder holder, int position) {
        //TODO ask why position was saved
        //mPosition = position;
        view = holder.mRoot;
        mContext = holder.mCategory.getContext();
        holder.mTitle.setText(mList.get(position).getTitle());
        holder.mCategory.setText(mList.get(position).getCategory());
        holder.mDate.setText(mList.get(position).getDate());
        Picasso.with(holder.mImage.getContext())
                .load(mList.get(holder.getAdapterPosition()).getImage())
                .fit()
                .centerCrop()
                .into(holder.mImage);

        if(!mIsSaveFragment) {
            if (mList.get(position).isSaved())
                holder.mHeart.setImageResource(R.mipmap.ic_favorite_black_24dp);
            else
            {
                holder.mHeart.setImageResource(R.mipmap.ic_favorite_border_black_24dp);
            }
            holder.mHeart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mList.get(holder.getAdapterPosition()).isSaved())
                    {
                        holder.mHeart.setImageResource(R.mipmap.ic_favorite_border_black_24dp);
                        DatabaseHelper.getInstance(v.getContext()).unSaveArticle(mList.get(holder.getAdapterPosition()).getId());
                        mList.get(holder.getAdapterPosition()).setSaved(false);
                        Snackbar.make(holder.mRoot, "Article unsaved", Snackbar.LENGTH_LONG)
                                .setAction("UNDO", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        holder.mHeart.setImageResource(R.mipmap.ic_favorite_black_24dp);
                                        mList.get(holder.getAdapterPosition()).setSaved(true);
                                        DatabaseHelper.getInstance(v.getContext()).saveArticle(mList.get(holder.getAdapterPosition()).getId());
                                    }
                                })
                                .setActionTextColor(v.getResources().getColor(R.color.colorPrimaryDark))
                                .show();
                    }
                    else
                    {
                        holder.mHeart.setImageResource(R.mipmap.ic_favorite_black_24dp);
                        mList.get(holder.getAdapterPosition()).setSaved(true);
                        DatabaseHelper.getInstance(v.getContext()).saveArticle(mList.get(holder.getAdapterPosition()).getId());

                        Snackbar.make(holder.mRoot, "Article saved", Snackbar.LENGTH_LONG)
                                .setAction("UNDO", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        holder.mHeart.setImageResource(R.mipmap.ic_favorite_border_black_24dp);
                                        mList.get(holder.getAdapterPosition()).setSaved(false);
                                        DatabaseHelper.getInstance(v.getContext()).unSaveArticle(mList.get(holder.getAdapterPosition()).getId());
                                    }
                                })
                                .setActionTextColor(v.getResources().getColor(R.color.colorPrimaryDark))
                                .show();
                    }
                }

            });
        }
        else
        {
            holder.mHeart.setVisibility(View.GONE);
        }

        holder.mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mList.get(holder.getAdapterPosition()).getUrl();

                // Intent intent  = v.getContext().getPackageManager().getLaunchIntentForPackage(application);
                Intent sendIntent = new Intent();
                sendIntent.setAction(Intent.ACTION_SEND);
                sendIntent.putExtra(Intent.EXTRA_TEXT, mList.get(holder.getAdapterPosition()).getUrl());

                sendIntent.setType("text/plain");
                //startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));
                v.getContext().startActivity(Intent.createChooser(sendIntent, "Share this article using.."));
            }
        });

        //CLICK LISTENER WHEN CLICKING ON A PRODUCT
        holder.mRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOnProduct(v,mList.get(holder.getAdapterPosition()).getId());
            }
        });
        //holder.mShare.setOnClickListener(this);
    }

    private void clickOnProduct(View v, long id)
    {
        Intent intent = new Intent(v.getContext().getApplicationContext(), CollectionDemoActivity.class);
        intent.putExtra(DatabaseHelper.COL_ID,id);
        v.getContext().startActivity(intent);
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    //TODO SAM YOU CAN DELETE THIS
    //TODO IS NOT BEING USED
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.top_stories_heart:

                break;
            case R.id.top_stories_share:
                //TODO DO SOMETHING WHEN USER CLICKS ON SHARE
                Log.d(TAG, "onClick: Clicked on share");
                break;
        }
    }

    @Override
    public void onItemDismiss(final int position) {
        DatabaseHelper.getInstance(mContext).unSaveArticle(mList.get(position).getId());
        final Article article = mList.get(position);
        mList.remove(position);
        notifyItemRemoved(position);
        Snackbar.make(view, "Article unsaved", Snackbar.LENGTH_LONG)
                .setAction("UNDO", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DatabaseHelper.getInstance(mContext).saveArticle(article.getId());
                        mList.add(position,article);
                        notifyItemInserted(position);
                    }
                })
                .setActionTextColor(view.getResources().getColor(R.color.colorPrimaryDark))
                .show();
    }

    public void swapData(List<Article> newList) {
        mList = newList;
        notifyDataSetChanged();
    }

    public class ArticlesViewHolder extends RecyclerView.ViewHolder {

        //Setting Views including Recycler View
        TextView mTitle, mDate, mCategory;
        ImageView mImage;
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
            mImage = (ImageView) itemView.findViewById(R.id.top_stories_image);
        }
    }
}
