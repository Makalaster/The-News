package com.example.ivonneortega.the_news_project.MainActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Intent;
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
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ivonneortega.the_news_project.data.Article;
import com.example.ivonneortega.the_news_project.CategoryView.CategoryViewActivity;
import com.example.ivonneortega.the_news_project.DatabaseHelper;
import com.example.ivonneortega.the_news_project.MainActivity.Fragments.FragmentAdapterMainActivity;
import com.example.ivonneortega.the_news_project.R;
import com.example.ivonneortega.the_news_project.RecyclerViewAdapters.ArticlesVerticalRecyclerAdapter;
import com.example.ivonneortega.the_news_project.Search.SearchActivity;
import com.example.ivonneortega.the_news_project.Settings.SettingsActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, ArticlesVerticalRecyclerAdapter.SaveAndShare{

    FragmentAdapterMainActivity mAdapter;

    public static final int ARTICLE_REFRESH_JOB = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        mAdapter = new FragmentAdapterMainActivity(getSupportFragmentManager());
        viewPager.setAdapter(mAdapter);


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

        if (id == R.id.nav_camera) {
            moveToCategoryViewActivity();
        } else if (id == R.id.nav_gallery) {
            moveToCategoryViewActivity();

        } else if (id == R.id.nav_slideshow) {
            moveToCategoryViewActivity();

        } else if (id == R.id.nav_manage) {
            moveToCategoryViewActivity();

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

    public void moveToCategoryViewActivity()
    {
        Intent intent = new Intent(this, CategoryViewActivity.class);
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
}
