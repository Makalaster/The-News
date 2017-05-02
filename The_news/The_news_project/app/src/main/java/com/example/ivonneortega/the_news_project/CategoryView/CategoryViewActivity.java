package com.example.ivonneortega.the_news_project.CategoryView;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.ivonneortega.the_news_project.Article;
import com.example.ivonneortega.the_news_project.DatabaseHelper;
import com.example.ivonneortega.the_news_project.RecyclerViewAdapters.ArticlesVerticalRecyclerAdapter;
import com.example.ivonneortega.the_news_project.R;
import com.example.ivonneortega.the_news_project.Search.SearchActivity;

import java.util.ArrayList;
import java.util.List;

public class CategoryViewActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    ImageButton mSearch, mOptions;
    RecyclerView mRecyclerView;
    ArticlesVerticalRecyclerAdapter mAdapter;
    String mCategory;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_view);
        settingUpTheViews();

    }





    //NAVIGATION DRAWER METHOD
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.category_view, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//        if (id == R.id.action_settings) {
//            return true;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    //NAVIGATION DRAWER METHOD
    //HANDLES THE CLICKS INSIDE THE DRAWER
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            Toast.makeText(this, "Category 1", Toast.LENGTH_SHORT).show();
        } else if (id == R.id.nav_gallery) {
            Toast.makeText(this, "Category 2", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_slideshow) {
            Toast.makeText(this, "Category 3", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_manage) {
            Toast.makeText(this, "Category 4", Toast.LENGTH_SHORT).show();

//        } else if (id == R.id.nav_share) {
//            Toast.makeText(this, "Category 5", Toast.LENGTH_SHORT).show();
//
//        } else if (id == R.id.nav_send) {
//            Toast.makeText(this, "Category 6", Toast.LENGTH_SHORT).show();
//
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void settingUpTheViews()
    {
        //TODO SET TITLE FROM INTENT
        Toolbar toolbar = (Toolbar) findViewById(R.id.root_toolbar);
        setSupportActionBar(toolbar);


        Intent intent = getIntent();
        mCategory = intent.getStringExtra(DatabaseHelper.COL_CATEGORY);
        getSupportActionBar().setTitle(mCategory);

        //NAVIGATION DRAWER SET UP
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        //Setting up views and click listener
        mSearch = (ImageButton) findViewById(R.id.search_toolbar);
        mOptions = (ImageButton) findViewById(R.id.options_toolbar);
        mSearch.setClickable(true);
        mOptions.setClickable(true);
        mSearch.setOnClickListener(this);
        mOptions.setOnClickListener(this);


        //Setting up recycler View
        mRecyclerView = (RecyclerView) findViewById(R.id.category_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        List<Article> categoryIndividualItems = new ArrayList<>();
        categoryIndividualItems = DatabaseHelper.getInstance(this).getArticlesByCategory(mCategory);

        mAdapter = new ArticlesVerticalRecyclerAdapter(categoryIndividualItems,false);
        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.search_toolbar:
                moveToSearchActivity();
                break;
            case R.id.options_toolbar:
                Toast.makeText(this, "Clicked on options button", Toast.LENGTH_SHORT).show();
                break;
        }

    }

    public void moveToSearchActivity()
    {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

}
