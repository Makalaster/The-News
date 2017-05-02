/*
 * Copyright 2012 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.ivonneortega.the_news_project.DetailView;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ivonneortega.the_news_project.Article;
import com.example.ivonneortega.the_news_project.CategoryView.CategoryViewActivity;
import com.example.ivonneortega.the_news_project.DatabaseHelper;
import com.example.ivonneortega.the_news_project.R;

import java.util.ArrayList;
import java.util.List;


public class CollectionDemoActivity extends FragmentActivity
implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{

    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String DATE = "date";
    public static final String IMAGE = "image";

    List<String> list;
    DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
    ViewPager mViewPager;
    private long mId;
    private ImageView mImage;
    private TextView mTitle, mDate, mContent;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_view);

        creatingViews();

        Intent intent = getIntent();
        mId = intent.getLongExtra(DatabaseHelper.COL_ID,-1);
        Article article = DatabaseHelper.getInstance(this).getArticlesById(mId);
        List<Article> articleList = DatabaseHelper.getInstance(this).getArticlesByCategory(article.getCategory());

        int position = Article.getArticlePosition(mId,articleList);

        mDemoCollectionPagerAdapter = new DemoCollectionPagerAdapter(getSupportFragmentManager(),articleList);
//
//        final ActionBar actionBar = getActionBar();
//        actionBar.setDisplayHomeAsUpEnabled(true);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);
        mViewPager.setCurrentItem(position);

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

    public void moveToCategoryViewActivity()
    {
        Intent intent = new Intent(this, CategoryViewActivity.class);
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.options_toolbar:
                Toast.makeText(this, "Click on options button", Toast.LENGTH_SHORT).show();
                break;
            case R.id.share_toolbar:
                Toast.makeText(this, "Click on share button", Toast.LENGTH_SHORT).show();
                break;
            case R.id.heart_toolbar:
                saveArticle();
                break;
        }
    }

    public void saveArticle()
    {

    }

    public void creatingViews()
    {
        Toolbar toolbar = (Toolbar) findViewById(R.id.root_toolbar);
        //setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ImageButton optionsToolbar = (ImageButton) findViewById(R.id.options_toolbar);
        optionsToolbar.setClickable(true);
        ImageButton shareToolbar = (ImageButton) findViewById(R.id.share_toolbar);
        shareToolbar.setClickable(true);
        optionsToolbar.setOnClickListener(this);
        shareToolbar.setOnClickListener(this);
        ImageButton heartToolbar = (ImageButton) findViewById(R.id.heart_toolbar);
        heartToolbar.setClickable(true);
        heartToolbar.setOnClickListener(this);

        mImage = (ImageView) findViewById(R.id.detail_image);
        mTitle = (TextView) findViewById(R.id.detail_title);
        mDate = (TextView) findViewById(R.id.detail_date);
        mContent = (TextView) findViewById(R.id.detail_content);
    }

    public void settingUpViews()
    {
        Intent intent = getIntent();
        mId = intent.getLongExtra(DatabaseHelper.COL_ID,-1);
        Article article = DatabaseHelper.getInstance(this).getArticlesById(mId);
    }


    /**
     * A {@link FragmentStatePagerAdapter} that returns a fragment
     * representing an object in the collection.
     */
    public static class DemoCollectionPagerAdapter extends FragmentStatePagerAdapter {

        List<Article> mList;

        public DemoCollectionPagerAdapter(FragmentManager fm, List<Article> list) {
            super(fm);
            mList = list;
        }


        @Override
        public void setPrimaryItem(ViewGroup container, int position, Object object) {
            super.setPrimaryItem(container, position, object);
        }

        @Override
        public Fragment getItem(int i) {
            Fragment fragment = new DemoObjectFragment();
            Bundle args = new Bundle();
            args.putString(TITLE,mList.get(i).getTitle());
            args.putString(CONTENT,mList.get(i).getBody());
            args.putString(DATE,mList.get(i).getDate());
            args.putString(IMAGE,mList.get(i).getImage());
           // args.putString(DemoObjectFragment.ARG_OBJECT, mList.get(i)); // Our object is just an integer :-P
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            // For this contrived example, we have a 100-object collection.
            return mList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "OBJECT " + (position + 1);
        }
    }

    /**
     * A dummy fragment representing a section of the app, but that simply displays dummy text.
     */
    public static class DemoObjectFragment extends Fragment {

        public static final String ARG_OBJECT = "object";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail_view, container, false);
            Bundle args = getArguments();
            ((TextView) rootView.findViewById(R.id.detail_title)).setText((args.getString(TITLE)));
            ((TextView) rootView.findViewById(R.id.detail_content)).setText((args.getString(CONTENT)));
            ((TextView) rootView.findViewById(R.id.detail_date)).setText((args.getString(DATE)));
            ((TextView) rootView.findViewById(R.id.detail_image)).setText((args.getString(IMAGE)));


            return rootView;
        }
    }
}
