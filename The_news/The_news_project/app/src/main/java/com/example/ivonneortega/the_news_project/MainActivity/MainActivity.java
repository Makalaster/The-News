package com.example.ivonneortega.the_news_project.MainActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ivonneortega.the_news_project.data.Article;
import com.example.ivonneortega.the_news_project.CategoryView.CategoryViewActivity;
import com.example.ivonneortega.the_news_project.DatabaseHelper;
import com.example.ivonneortega.the_news_project.MainActivity.Fragments.FragmentAdapterMainActivity;
import com.example.ivonneortega.the_news_project.R;
import com.example.ivonneortega.the_news_project.RecyclerViewAdapters.ArticlesVerticalRecyclerAdapter;
import com.example.ivonneortega.the_news_project.Search.SearchActivity;
import com.example.ivonneortega.the_news_project.Settings.SettingsActivity;
import com.example.ivonneortega.the_news_project.data.Category;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, ArticlesVerticalRecyclerAdapter.SaveAndShare{

    FragmentAdapterMainActivity mAdapter;
    public static final String URL = "https://newsapi.org/v1/articles?source=";
    public static final String API_KEY = "b9742f05aeab45e097c3c57a30ccb224";
    List<String> mSourcesByTop, mSourcesByLatest;
    boolean mStartActivity;
    List<Category> categories_by_top;

    public static final int ARTICLE_REFRESH_JOB = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme();
//        setContentView(R.layout.activity_main);

        setSources();
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        mAdapter = new FragmentAdapterMainActivity(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);
        categories_by_top = new ArrayList<>();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        Toolbar toolbar = (Toolbar) findViewById(R.id.root_toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView =  navigationView.getHeaderView(0);
        ImageView nav_user = (ImageView) hView.findViewById(R.id.navigation_image);
/*        Picasso.with(this)
                .load(DatabaseHelper.getInstance(this).getArticlesById(0).getImage())
                .fit()
                .into(nav_user);*/


        ImageButton optionsToolbar = (ImageButton) findViewById(R.id.options_toolbar);
        optionsToolbar.setClickable(true);
        ImageButton searchToolbar = (ImageButton) findViewById(R.id.search_toolbar);
        searchToolbar.setClickable(true);
        optionsToolbar.setOnClickListener(this);
        searchToolbar.setOnClickListener(this);

        JobScheduler jobScheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        ComponentName componentName = new ComponentName(this, ArticleRefreshService.class);

        JobInfo refreshJob = new JobInfo.Builder(ARTICLE_REFRESH_JOB, componentName)
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_ANY)
                .setRequiresCharging(true)
                .build();

        jobScheduler.schedule(refreshJob);

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position==2)
                    mAdapter.notifyDataSetChanged();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

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

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

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

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.options_toolbar:
                moveToSettingsActivity();
                break;
            case R.id.search_toolbar:
                moveToSearchActivity();
                break;
        }

    }

    public void moveToSearchActivity()
    {
        Intent intent = new Intent(this, SearchActivity.class);
        startActivity(intent);
    }

    public void moveToCategoryViewActivity(String category)
    {
        Intent intent = new Intent(this, CategoryViewActivity.class);
        intent.putExtra(DatabaseHelper.COL_CATEGORY,category);
        startActivity(intent);
    }

    public void moveToSettingsActivity()
    {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }


    @Override
    public void saveAndUnsave(Article article) {

    }

//
//    public void addThingsToDatabase()
//    {
//        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(this);
//        databaseHelper.insertArticleIntoDatabase("https://www.transit.dot.gov/sites/fta.dot.gov/files/635847974891062780-425303270_news.jpg","Business 1","Business","Today","This is the body",
//                "New York Times",Article.FALSE,1,"https://www.transit.dot.gov/sites/fta.dot.gov/files/635847974891062780-425303270_news.jpg");
//
//        databaseHelper.insertArticleIntoDatabase("https://www.transit.dot.gov/sites/fta.dot.gov/files/635847974891062780-425303270_news.jpg","Business 2","Business","Today","This is the body",
//                "New York Times",Article.FALSE,1,"https://www.transit.dot.gov/sites/fta.dot.gov/files/635847974891062780-425303270_news.jpg");
//        databaseHelper.insertArticleIntoDatabase("https://www.transit.dot.gov/sites/fta.dot.gov/files/635847974891062780-425303270_news.jpg","Business 3","Business","Today","This is the body",
//                "New York Times",Article.FALSE,0,"https://www.transit.dot.gov/sites/fta.dot.gov/files/635847974891062780-425303270_news.jpg");
//
//        databaseHelper.insertArticleIntoDatabase("https://www.transit.dot.gov/sites/fta.dot.gov/files/635847974891062780-425303270_news.jpg","Business 4","Business","Today","This is the body",
//                "New York Times",Article.FALSE,0,"https://www.transit.dot.gov/sites/fta.dot.gov/files/635847974891062780-425303270_news.jpg");
//
//
//
//        databaseHelper.insertArticleIntoDatabase("https://www.transit.dot.gov/sites/fta.dot.gov/files/635847974891062780-425303270_news.jpg","Tech 1","Tech","Today","This is the body",
//                "New York Times",Article.FALSE,1,"https://www.transit.dot.gov/sites/fta.dot.gov/files/635847974891062780-425303270_news.jpg");
//
//        databaseHelper.insertArticleIntoDatabase("https://www.transit.dot.gov/sites/fta.dot.gov/files/635847974891062780-425303270_news.jpg","Tech 2","Tech","Today","This is the body",
//                "New York Times",Article.FALSE,1,"https://www.transit.dot.gov/sites/fta.dot.gov/files/635847974891062780-425303270_news.jpg");
//        databaseHelper.insertArticleIntoDatabase("https://www.transit.dot.gov/sites/fta.dot.gov/files/635847974891062780-425303270_news.jpg","Tech 3","Tech","Today","This is the body",
//                "New York Times",Article.FALSE,0,"https://www.transit.dot.gov/sites/fta.dot.gov/files/635847974891062780-425303270_news.jpg");
//
//        databaseHelper.insertArticleIntoDatabase("https://www.transit.dot.gov/sites/fta.dot.gov/files/635847974891062780-425303270_news.jpg","Tech 4","Tech","Today","This is the body",
//                "New York Times",Article.FALSE,0,"https://www.transit.dot.gov/sites/fta.dot.gov/files/635847974891062780-425303270_news.jpg");
//
//
//
//
//        databaseHelper.insertArticleIntoDatabase("https://www.transit.dot.gov/sites/fta.dot.gov/files/635847974891062780-425303270_news.jpg","World 1","World","Today","This is the body",
//                "New York Times",Article.FALSE,1,"https://www.transit.dot.gov/sites/fta.dot.gov/files/635847974891062780-425303270_news.jpg");
//
//        databaseHelper.insertArticleIntoDatabase("https://www.transit.dot.gov/sites/fta.dot.gov/files/635847974891062780-425303270_news.jpg","World 2","World","Today","This is the body",
//                "New York Times",Article.FALSE,1,"https://www.transit.dot.gov/sites/fta.dot.gov/files/635847974891062780-425303270_news.jpg");
//        databaseHelper.insertArticleIntoDatabase("https://www.transit.dot.gov/sites/fta.dot.gov/files/635847974891062780-425303270_news.jpg","World 3","World","Today","This is the body",
//                "New York Times",Article.FALSE,0,"https://www.transit.dot.gov/sites/fta.dot.gov/files/635847974891062780-425303270_news.jpg");
//
//        databaseHelper.insertArticleIntoDatabase("https://www.transit.dot.gov/sites/fta.dot.gov/files/635847974891062780-425303270_news.jpg","World 4","World","Today","This is the body",
//                "New York Times",Article.FALSE,0,"https://www.transit.dot.gov/sites/fta.dot.gov/files/635847974891062780-425303270_news.jpg");
//    }

    public void setTheme()
    {
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.ivonneortega.the_news_project.Settings", Context.MODE_PRIVATE);
        String str = sharedPreferences.getString(SettingsActivity.THEME,"DEFAULT"); //Initial value of the String is "Hello"
        Log.d("weqweqweqwe", "setTheme: "+str);
        if(str.equals("dark"))
        {
            Log.d("sdsdfsdfsdfsdf", "setTheme: qweqwdqqwdqwdqwdwd");
            setTheme(R.style.DarkTheme);
            setContentView(R.layout.activity_main);
            findViewById(R.id.root_toolbar).setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkTheme));
        }
        else
        {
            setContentView(R.layout.activity_main);

        }
        mStartActivity=true;

    }


//    public void searchArticlesByTop(final String source) {
//        RequestQueue queue = Volley.newRequestQueue(this);
//        final DatabaseHelper db = DatabaseHelper.getInstance(this);
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
//                URL +source+ "&apiKey=" + API_KEY, null,
//                new com.android.volley.Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            JSONObject root = response;
//                            JSONArray articles = root.getJSONArray("articles");
//                            for(int i=0;i<articles.length();i++)
//                            {
//                                JSONObject article = articles.getJSONObject(i);
//                                String articleString = article.getString("title");
//                                String description = article.getString("description");
//                                String url = article.getString("url");
//                                String image = article.getString("urlToImage");
//                                String date = article.getString("publishedAt");
//                                if(date.length()>5)
//                                    date.substring(0,5);
//                               long insert = db.insertArticleIntoDatabase(
//                                        image,articleString,source,date,description,source,Article.FALSE,
//                                        Article.FALSE,url);
//
//                                Log.d("THIS", "onResponse: "+insert);
//
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new com.android.volley.Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//
//        queue.add(jsonObjectRequest);
//    }
//
//    public void searchArticlesByLatest(final String source) {
//        RequestQueue queue = Volley.newRequestQueue(this);
//        final DatabaseHelper db = DatabaseHelper.getInstance(this);
//
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
//                URL +source+ "&apiKey=" + API_KEY+"&orderBy=latest", null,
//                new com.android.volley.Response.Listener<JSONObject>() {
//                    @Override
//                    public void onResponse(JSONObject response) {
//                        try {
//                            JSONObject root = response;
//                            JSONArray articles = root.getJSONArray("articles");
//                            for(int i=0;i<articles.length();i++)
//                            {
//                                JSONObject article = articles.getJSONObject(i);
//                                String articleString = article.getString("title");
//                                String description = article.getString("description");
//                                String url = article.getString("url");
//                                String image = article.getString("urlToImage");
//                                String date = article.getString("publishedAt");
//                                if(date.length()>5)
//                                    date.substring(0,5);
//                                long insert = db.insertArticleIntoDatabase(
//                                        image,articleString,source,date,description,source,Article.FALSE,
//                                        Article.TRUE,url);
//
//                                Log.d("THIS", "onResponse: "+insert);
//
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                    }
//                }, new com.android.volley.Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//            }
//        });
//
//        queue.add(jsonObjectRequest);
//    }

    public void setSources()
    {
        mSourcesByTop = new ArrayList<>();
        mSourcesByTop.add("associated-press");
        mSourcesByTop.add("bbc-news");
        mSourcesByTop.add("business-insider");
        mSourcesByTop.add("buzzfeed");
        mSourcesByTop.add("cnn");
        mSourcesByTop.add("espn");

        mSourcesByLatest = new ArrayList<>();
        mSourcesByLatest.add("buzzfeed");
    }

}
