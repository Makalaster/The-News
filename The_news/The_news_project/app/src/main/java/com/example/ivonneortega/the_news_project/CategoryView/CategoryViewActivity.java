package com.example.ivonneortega.the_news_project.CategoryView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ivonneortega.the_news_project.Settings.SettingsActivity;
import com.example.ivonneortega.the_news_project.data.Article;
import com.example.ivonneortega.the_news_project.DatabaseHelper;
import com.example.ivonneortega.the_news_project.RecyclerViewAdapters.ArticlesVerticalRecyclerAdapter;
import com.example.ivonneortega.the_news_project.R;
import com.example.ivonneortega.the_news_project.Search.SearchActivity;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme();
//        setContentView(R.layout.activity_category_view);
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

        if(mStartActivity == true){
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
        List<String> categories = new ArrayList<>();

        if (id == R.id.nav_world) {
            moveToCategoryViewActivity("World");
        } else if (id == R.id.nav_politics) {
            moveToCategoryViewActivity("Politics");
        } else if (id == R.id.nav_business) {
            moveToCategoryViewActivity("Business");
        } else if (id == R.id.nav_technology) {
            moveToCategoryViewActivity("Technology");
        }
        else if (id == R.id.nav_science) {
            moveToCategoryViewActivity("Science");
        }
        else if (id == R.id.nav_sports) {
            moveToCategoryViewActivity("Sports");
        }
        else if (id == R.id.nav_movies) {
            moveToCategoryViewActivity("Movies");
        }
        else if (id == R.id.nav_fashion) {
            moveToCategoryViewActivity("Fashion");
        }
        else if (id == R.id.nav_food) {
            moveToCategoryViewActivity("Food");
        }
        else if (id == R.id.nav_health) {
            moveToCategoryViewActivity("Health");
        }
        else if (id == R.id.nav_miscellaneous) {
            moveToCategoryViewActivity("Miscellaneous");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void moveToCategoryViewActivity(String category)
    {
        finish();
        Intent intent = new Intent(this,CategoryViewActivity.class);
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

        getList(mCategory);

        //NAVIGATION DRAWER SET UP
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        View hView =  navigationView.getHeaderView(0);
        ImageView nav_user = (ImageView) hView.findViewById(R.id.navigation_image);
        Picasso.with(this)
                .load(mList.get(mList.size()/2).getImage())
                .fit()
                .into(nav_user);

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

//        List<Article> categoryIndividualItems = new ArrayList<>();
//        categoryIndividualItems = DatabaseHelper.getInstance(this).getArticlesByCategory(mCategory);

        mAdapter = new ArticlesVerticalRecyclerAdapter(mList,false);
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
        if (category.equals("World")) {
            categories.add("World");
        } else if (category.equals("Politics")) {
            categories.add("u.s");
            categories.add("Politics");
        } else if (category.equals("Business")) {
            categories.add("Business Day");
        } else if (category.equals("Technology")) {
            categories.add("Technology");
        }
        else if (category.equals("Science")) {
            categories.add("Science");
        }
        else if (category.equals("Sports")) {
            categories.add("Sports");
        }
        else if (category.equals("Movies")) {
            categories.add("Movies");
            categories.add("Teather");
        }
        else if (category.equals("Fashion")) {
            categories.add("Fashion");
            categories.add("Style");
        }
        else if (category.equals("Food")) {
            categories.add("food");
        }
        else if (category.equals("Health")) {
            categories.add("Health");
            categories.add("Well");
        }
        else if (category.equals("Miscellaneous")) {
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
        }
        Log.d("THE CATEGORY IS", "getList: "+category);
        getListWithArticlesByCategory(categories);
    }



    public void getListWithArticlesByCategory(List<String> categories)
    {
        DatabaseHelper db = DatabaseHelper.getInstance(this);
        List<Article> articles = new ArrayList<>();
        List<Article> aux = new ArrayList<>();
        for(int i=0;i<categories.size();i++)
        {
            aux = db.getArticlesByCategory(categories.get(i));
            articles = copyOneListIntoAnother(articles,aux);
        }
        mList = articles;
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
