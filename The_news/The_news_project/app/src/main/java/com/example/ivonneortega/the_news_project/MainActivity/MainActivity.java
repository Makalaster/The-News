package com.example.ivonneortega.the_news_project.MainActivity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ivonneortega.the_news_project.Article;
import com.example.ivonneortega.the_news_project.CategoryView.CategoryViewActivity;
import com.example.ivonneortega.the_news_project.DatabaseHelper;
import com.example.ivonneortega.the_news_project.MainActivity.Fragments.FragmentAdapterMainActivity;
import com.example.ivonneortega.the_news_project.R;
import com.example.ivonneortega.the_news_project.RecyclerViewAdapters.ArticlesVerticalRecyclerAdapter;
import com.example.ivonneortega.the_news_project.Search.SearchActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener, ArticlesVerticalRecyclerAdapter.SaveAndShare{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        FragmentAdapterMainActivity adapter = new FragmentAdapterMainActivity(getSupportFragmentManager());
        viewPager.setAdapter(adapter);

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

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.options_toolbar:
                Toast.makeText(this, "Click on options button", Toast.LENGTH_SHORT).show();
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


    @Override
    public void saveAndUnsave(Article article) {
//        if(mList.get(holder.getAdapterPosition()).isSaved())
//        {
//            holder.mHeart.setImageResource(R.mipmap.ic_favorite_border_black_24dp);
//            DatabaseHelper.getInstance(v.getContext()).unSaveArticle(mList.get(holder.getAdapterPosition()).getId());
//            Snackbar.make(holder.mHeart.getContext().findViewById(android.R.id.content), "Article unsaved", Snackbar.LENGTH_LONG)
//                    .setAction("UNDO", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            holder.mHeart.setImageResource(R.mipmap.ic_favorite_black_24dp);
//                            DatabaseHelper.getInstance(v.getContext()).saveArticle(mList.get(holder.getAdapterPosition()).getId());
//                        }
//                    })
//                    .setActionTextColor(v.getResources().getColor(R.color.colorPrimaryDark))
//                    .show();
//        }
//        else
//        {
//            holder.mHeart.setImageResource(R.mipmap.ic_favorite_black_24dp);
//            DatabaseHelper.getInstance(v.getContext()).saveArticle(mList.get(holder.getAdapterPosition()).getId());
//            Snackbar.make(v.findViewById(android.R.id.content), "Article saved", Snackbar.LENGTH_LONG)
//                    .setAction("UNDO", new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            holder.mHeart.setImageResource(R.mipmap.ic_favorite_border_black_24dp);
//                            DatabaseHelper.getInstance(v.getContext()).unSaveArticle(mList.get(holder.getAdapterPosition()).getId());
//                        }
//                    })
//                    .setActionTextColor(v.getResources().getColor(R.color.colorPrimaryDark))
//                    .show();
//        }
    }


//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);
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
}
