package com.example.ivonneortega.the_news_project.MainActivity.RecyclerViewAdapters;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ivonneortega.the_news_project.Category;
import com.example.ivonneortega.the_news_project.R;

import java.util.List;

/**
 * Created by ivonneortega on 4/29/17.
 */

public class RecyclerViewMainActivityAdapter extends RecyclerView.Adapter<RecyclerViewMainActivityAdapter.AllStoriesViewHolder>  {

    private static final String TAG = "StockRecyclerViewAdapte";
    List<Category> mList;


    public RecyclerViewMainActivityAdapter(List<Category> categoriesList) {
        mList = categoriesList;

    }


    @Override
    public AllStoriesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        return new AllStoriesViewHolder(inflater.inflate(R.layout.custom_recyclerview_all_stories,parent,false));
    }

    @Override
    public void onBindViewHolder(AllStoriesViewHolder holder, int position) {

        //Title of the Category
        holder.mTitleOfCategory.setText(mList.get(position).getCategoryName());




        //Setting Layout Manager for Recycler View
        //This is the main recycler view that scroll vertically and has all the categories
        // Or at least the important ones
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(holder.mTitleOfCategory.getContext(),LinearLayoutManager.HORIZONTAL,false);
        holder.mRecyclerView.setLayoutManager(linearLayoutManager);

        CategoryIndividualItemRecyclerViewAdapter adapter = new CategoryIndividualItemRecyclerViewAdapter(mList.get(position).getList());
        holder.mRecyclerView.setAdapter(adapter);

    }


    @Override
    public int getItemCount() {
        return mList.size();
    }

    public class AllStoriesViewHolder extends RecyclerView.ViewHolder {

        //Setting Views including Recycler View
        TextView mTitleOfCategory;
        RecyclerView mRecyclerView;

        public AllStoriesViewHolder(View itemView) {
            super(itemView);
            mTitleOfCategory = (TextView) itemView.findViewById(R.id.title_category_main_activity);
            mRecyclerView = (RecyclerView) itemView.findViewById(R.id.recyclerView_each_category);


        }
    }
}
