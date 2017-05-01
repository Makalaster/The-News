package com.example.ivonneortega.the_news_project.Search;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;

import com.example.ivonneortega.the_news_project.Article;
import com.example.ivonneortega.the_news_project.DatabaseHelper;
import com.example.ivonneortega.the_news_project.R;
import com.example.ivonneortega.the_news_project.RecyclerViewAdapters.ArticleRecyclerAdapter;
import com.example.ivonneortega.the_news_project.RecyclerViewAdapters.ArticlesVerticalRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    SearchView mSearchView;
    ImageButton mBackButton;

    ArticlesVerticalRecyclerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


        settingUpTheViews();

        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                InputMethodManager imm = (InputMethodManager)mSearchView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(mSearchView.getWindowToken(), 0);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
               //TODO DO ALL THE STUFF FOR THE SEARCH

                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.search_recycler_view);
                List<Article> articleList = DatabaseHelper.getInstance(recyclerView.getContext()).searchArticles(newText);

                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchActivity.this,
                        LinearLayoutManager.VERTICAL,false);
                recyclerView.setLayoutManager(linearLayoutManager);
                mAdapter = new ArticlesVerticalRecyclerAdapter(articleList,false);
                recyclerView.setAdapter(mAdapter);




                return true;
            }
        });

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.back_toolbar:
                finish();
                break;
        }
    }

    public void settingUpTheViews()
    {
        mSearchView = (SearchView) findViewById(R.id.search_editText);
        mSearchView.onActionViewExpanded();
        mBackButton = (ImageButton) findViewById(R.id.back_toolbar);
        mBackButton.setClickable(true);
        mBackButton.setOnClickListener(this);
    }
}
