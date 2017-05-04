package com.example.ivonneortega.the_news_project.categoryView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.example.ivonneortega.the_news_project.R;
import com.example.ivonneortega.the_news_project.recyclerViewAdapters.ArticlesVerticalRecyclerAdapter;
import com.example.ivonneortega.the_news_project.search.SearchActivity;
import com.example.ivonneortega.the_news_project.settings.SettingsActivity;
import com.example.ivonneortega.the_news_project.data.Article;
import com.example.ivonneortega.the_news_project.database.DatabaseHelper;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class CategoryViewActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    ImageButton mSearch, mOptions;
    RecyclerView mRecyclerView;
    ArticlesVerticalRecyclerAdapter mAdapter;
    String mCategory;
    List<Article> mList;
    boolean mStartActivity;
    View hView;
    ImageView nav_user;
    NavigationView navigationView;
    ProgressBar mProgressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme();
        settingUpTheViews();
        mList = new ArrayList<>();
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(mAdapter!=null)
        {
            mAdapter.notifyDataSetChanged();
        }

        if(mStartActivity){
            mStartActivity = false;

        } else {
            finish();
            startActivity(getIntent());
        }
    }

    public void setTheme()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.ivonneortega.the_news_project.Settings", Context.MODE_PRIVATE);
        String str = sharedPreferences.getString(SettingsActivity.THEME,"DEFAULT"); //Initial value of the String is "Hello"
        if(str.equals("dark"))
        {
            setTheme(R.style.DarkTheme);
            setContentView(R.layout.activity_category_view);
            findViewById(R.id.root_toolbar).setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkTheme));
        }
        else
        {
            setContentView(R.layout.activity_category_view);

        }
        mStartActivity=true;
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

    //NAVIGATION DRAWER METHOD
    //HANDLES THE CLICKS INSIDE THE DRAWER
    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        String category = null;
        switch (id) {
            case R.id.nav_world:
                category = "World";
                break;
            case R.id.nav_politics:
                category = "Politics";
                break;
            case R.id.nav_business:
                category = "Business Day";
                break;
            case R.id.nav_technology:
                category = "Technology";
                break;
            case R.id.nav_science:
                category = "Science";
                break;
            case R.id.nav_sports:
                category = "Sports";
                break;
            case R.id.nav_movies:
                category = "Movies";
                break;
            case R.id.nav_fashion:
                category = "Fashion";
                break;
            case R.id.nav_food:
                category = "Food";
                break;
            case R.id.nav_health:
                category = "Health";
                break;
            case R.id.nav_miscellaneous:
                category = "Miscellaneous";
                break;
            }

        moveToCategoryViewActivity(category);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //TODO check if both finishes are correct
    public void moveToCategoryViewActivity(String category)
    {
        finish();
        Intent intent = new Intent(this, CategoryViewActivity.class);
        intent.putExtra(DatabaseHelper.COL_CATEGORY,category);
        startActivity(intent);
        finish();
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

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);






        //Setting up views and click listener
        mSearch = (ImageButton) findViewById(R.id.search_toolbar);
        mOptions = (ImageButton) findViewById(R.id.options_toolbar);
        mProgressBar = (ProgressBar) findViewById(R.id.category_progress);
        mSearch.setClickable(true);
        mOptions.setClickable(true);
        mSearch.setOnClickListener(this);
        mOptions.setOnClickListener(this);


        //Setting up recycler View
        mRecyclerView = (RecyclerView) findViewById(R.id.category_recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        mRecyclerView.setLayoutManager(linearLayoutManager);

        List<Article> categoryIndividualItems = new ArrayList<>();
//        categoryIndividualItems = DatabaseHelper.getInstance(this).getArticlesByCategory(mCategory);

        mAdapter = new ArticlesVerticalRecyclerAdapter(categoryIndividualItems,false);
        mRecyclerView.setAdapter(mAdapter);

        getList(mCategory);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.search_toolbar:
                moveToSearchActivity();
                break;
            case R.id.options_toolbar:
                moveToSettingsActivity();
                break;
        }

    }

    public void moveToSearchActivity()
    {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void moveToSettingsActivity()
    {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    public void getList(String category)
    {
        List<String> categories = new ArrayList<>();
        switch (category) {
            case "World":
                categories.add("World");
                break;
            case "Politics":
                categories.add("u.s");
                categories.add("Politics");
                break;
            case "Business Day":
                categories.add("Business Day");
                break;
            case "Technology":
                categories.add("Technology");
                break;
            case "Science":
                categories.add("Science");
                break;
            case "Sports":
                categories.add("Sports");
                break;
            case "Movies":
                categories.add("Movies");
                categories.add("Teather");
                break;
            case "Fashion":
                categories.add("Fashion");
                categories.add("Style");
                break;
            case "Food":
                categories.add("food");
                break;
            case "Health":
                categories.add("Health");
                categories.add("Well");
                break;
            case "Miscellaneous":
                categories.add("Climate");
                categories.add("Real");
                categories.add("Arts");
                categories.add("The Upshot");
                categories.add("Opinion");
                categories.add("Times");
                categories.add("Technology");
                categories.add("Magazine");
                categories.add("N.Y./Region");
                categories.add("T Magazine Travel");
                break;
        }
        Log.d("THE CATEGORY IS", "getList: "+category);
        getListWithArticlesByCategory(categories);
    }



    public void getListWithArticlesByCategory(final List<String> categories)
    {
        final DatabaseHelper db = DatabaseHelper.getInstance(this);

        AsyncTask<List<String>,Void,List<Article>> asyncTask = new AsyncTask<List<String>, Void, List<Article>>() {
            @Override
            protected List<Article> doInBackground(List<String>... params) {
                List<Article> articles = new ArrayList<>();
                List<Article> aux;
                for(int i=0;i<categories.size();i++)
                {
                    aux = db.getArticlesByCategory(categories.get(i));
                    articles = copyOneListIntoAnother(articles,aux);
                }
                return articles;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                mProgressBar.setVisibility(View.VISIBLE);


            }

            @Override
            protected void onPostExecute(List<Article> list) {
                super.onPostExecute(list);
                mProgressBar.setVisibility(View.GONE);
                mAdapter.swapData(list);
                hView =  navigationView.getHeaderView(0);
                nav_user = (ImageView) hView.findViewById(R.id.navigation_image);
                Picasso.with(hView.getContext())
                        .load(list.get(list.size()/2).getImage())
                        .fit()
                        .into(nav_user);
            }
        }.execute(categories);

    }

    public List<Article> copyOneListIntoAnother(List<Article> list1, List<Article> list2)
    {
        for(int i=0;i<list2.size();i++)
        {
            list1.add(list2.get(i));
        }
        return list1;
    }

}
