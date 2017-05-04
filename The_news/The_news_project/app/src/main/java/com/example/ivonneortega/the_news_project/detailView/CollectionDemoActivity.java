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

package com.example.ivonneortega.the_news_project.detailView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ivonneortega.the_news_project.categoryView.CategoryViewActivity;
import com.example.ivonneortega.the_news_project.R;
import com.example.ivonneortega.the_news_project.settings.SettingsActivity;
import com.example.ivonneortega.the_news_project.data.Article;
import com.example.ivonneortega.the_news_project.data.NYTApiData;
import com.example.ivonneortega.the_news_project.database.DatabaseHelper;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class CollectionDemoActivity extends FragmentActivity
implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener{
    //Constants
    public static final String TITLE = "title";
    public static final String CONTENT = "content";
    public static final String DATE = "date";
    public static final String IMAGE = "image";
    public static final String URL = "url";
    public static final String TAG = "this";
    public static final String ID = "id";

    Article mArticle;
    DemoCollectionPagerAdapter mDemoCollectionPagerAdapter;
    ViewPager mViewPager;
    private long mId;
    private int mPosition;

    private List<Article> articleList;
    private ImageButton mHeart;
    private boolean mStartActivity;
    private DrawerLayout drawer;

    //TODO check on unused member variables
    private ActionBarDrawerToggle mDrawerToggle;
    private ImageView mImage;
    private TextView mTitle, mDate, mContent;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme();

        Intent intent = getIntent();
        mId = intent.getLongExtra(DatabaseHelper.COL_ID,-1);

        mArticle = DatabaseHelper.getInstance(this).getArticlesById(mId);
        articleList = DatabaseHelper.getInstance(this).getArticlesByCategory(mArticle.getCategory());
        mPosition = Article.getArticlePosition(mId,articleList);
        creatingViews();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mStartActivity){
            mStartActivity = false;
        } else {
            finish();
            startActivity(getIntent());
        }
    }

    public void setTheme() {
        SharedPreferences sharedPreferences = getSharedPreferences("com.example.ivonneortega.the_news_project.Settings", Context.MODE_PRIVATE);
        String str = sharedPreferences.getString(SettingsActivity.THEME,"DEFAULT"); //Initial value of the String is "Hello"
        Log.d("weqweqweqwe", "setTheme: "+str);
        if(str.equals("dark")) {
            Log.d("sdsdfsdfsdfsdf", "setTheme: qweqwdqqwdqwdqwdwd");
            setTheme(R.style.DarkTheme);
            setContentView(R.layout.activity_detail_view);
            findViewById(R.id.root_toolbar).setBackgroundColor(getResources().getColor(R.color.colorPrimaryDarkTheme));
        }
        else {
            setContentView(R.layout.activity_detail_view);
        }
        mStartActivity = true;
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

        if (id == R.id.nav_world) {
            moveToCategoryViewActivity("World");
        } else if (id == R.id.nav_politics) {
            moveToCategoryViewActivity("Politics");
        } else if (id == R.id.nav_business) {
            moveToCategoryViewActivity("Business");
        } else if (id == R.id.nav_technology) {
            moveToCategoryViewActivity("Technology");
        } else if (id == R.id.nav_science) {
            moveToCategoryViewActivity("Science");
        } else if (id == R.id.nav_sports) {
            moveToCategoryViewActivity("Sports");
        } else if (id == R.id.nav_movies) {
            moveToCategoryViewActivity("Movies");
        } else if (id == R.id.nav_fashion) {
            moveToCategoryViewActivity("Fashion");
        } else if (id == R.id.nav_food) {
            moveToCategoryViewActivity("Food");
        } else if (id == R.id.nav_health) {
            moveToCategoryViewActivity("Health");
        } else if (id == R.id.nav_miscellaneous) {
            moveToCategoryViewActivity("Miscellaneous");
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void moveToCategoryViewActivity(String category) {
        Intent intent = new Intent(this, CategoryViewActivity.class);
        intent.putExtra(DatabaseHelper.COL_CATEGORY,category);
        startActivity(intent);
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.options_toolbar:
                moveToSettingsActivity();
                break;
            case R.id.share_toolbar:
                share();
                break;
            case R.id.heart_toolbar:
                saveArticle();
                break;
        }
    }

    public void share() {
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, mArticle.getUrl());

        sendIntent.setType("text/plain");
        startActivity(Intent.createChooser(sendIntent, "Share this mArticle using.."));
    }

    public void moveToSettingsActivity() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }


    public void saveArticle() {
        if(articleList.get(mPosition).isSaved()) {
            mHeart.setImageResource(R.mipmap.ic_favorite_border_black_24dp);
            DatabaseHelper.getInstance(this).unSaveArticle(articleList.get(mPosition).getId());
            articleList.get(mPosition).setSaved(false);
            Snackbar.make(mHeart, "Article unsaved", Snackbar.LENGTH_LONG)
                    .setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mHeart.setImageResource(R.mipmap.ic_favorite_black_24dp);
                            articleList.get(mPosition).setSaved(true);
                            DatabaseHelper.getInstance(v.getContext()).saveArticle(articleList.get(mPosition).getId());
                        }
                    })
                    .setActionTextColor(getResources().getColor(R.color.colorPrimaryDark))
                    .show();
        }
        else {
            mHeart.setImageResource(R.mipmap.ic_favorite_black_24dp);
            articleList.get(mPosition).setSaved(true);
            DatabaseHelper.getInstance(this).saveArticle(articleList.get(mPosition).getId());

            Snackbar.make(mHeart, "Article saved", Snackbar.LENGTH_LONG)
                    .setAction("UNDO", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            mHeart.setImageResource(R.mipmap.ic_favorite_border_black_24dp);
                            articleList.get(mPosition).setSaved(false);
                            DatabaseHelper.getInstance(v.getContext()).unSaveArticle(articleList.get(mPosition).getId());
                        }
                    })
                    .setActionTextColor(getResources().getColor(R.color.colorPrimaryDark))
                    .show();
        }
    }

    public void creatingViews() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.root_toolbar);

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View hView =  navigationView.getHeaderView(0);
        ImageView nav_user = (ImageView) hView.findViewById(R.id.navigation_image);
        Picasso.with(this)
                .load(mArticle.getImage())
                .fit()
                .into(nav_user);

        ImageButton optionsToolbar = (ImageButton) findViewById(R.id.options_toolbar);
        optionsToolbar.setClickable(true);
        ImageButton shareToolbar = (ImageButton) findViewById(R.id.share_toolbar);
        shareToolbar.setClickable(true);
        optionsToolbar.setOnClickListener(this);
        shareToolbar.setOnClickListener(this);
        mHeart = (ImageButton) findViewById(R.id.heart_toolbar);
        mHeart.setClickable(true);
        mHeart.setOnClickListener(this);

        mImage = (ImageView) findViewById(R.id.detail_image);
        mTitle = (TextView) findViewById(R.id.detail_title);
        mDate = (TextView) findViewById(R.id.detail_date);
        mContent = (TextView) findViewById(R.id.detail_content);

        if(articleList.get(mPosition).isSaved())
            mHeart.setImageResource(R.mipmap.ic_favorite_black_24dp);
        else
            mHeart.setImageResource(R.mipmap.ic_favorite_border_black_24dp);

        //Setting on click listeners
        findViewById(R.id.heart_toolbar).setOnClickListener(this);
        findViewById(R.id.share_toolbar).setOnClickListener(this);

        mDemoCollectionPagerAdapter = new DemoCollectionPagerAdapter(getSupportFragmentManager(),articleList);

        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mDemoCollectionPagerAdapter);
        mViewPager.setCurrentItem(mPosition);
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
            args.putString(URL,mList.get(i).getUrl());
            args.putLong(ID, mList.get(i).getId());
            Log.d(TAG, "getItem URL: "+mList.get(i).getUrl());
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public int getCount() {
            return mList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return "OBJECT " + (position + 1);
        }
    }

    //THIS IS WHERE ALL THE VIEWS ARE INFLATED
    public static class DemoObjectFragment extends Fragment {

        public static final String ARG_OBJECT = "object";

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_detail_view, container, false);
            Bundle args = getArguments();
            ((TextView) rootView.findViewById(R.id.detail_title)).setText((args.getString(TITLE)));
            //((TextView)rootView.findViewById(R.id.detail_content)).setText(args.getString(CONTENT));
            searchArticlesByTop(args.getString(URL),((TextView)rootView.findViewById(R.id.detail_content)),args.getLong(ID));
            ((TextView) rootView.findViewById(R.id.detail_date)).setText((args.getString(DATE)));
            ImageView image = (ImageView) rootView.findViewById(R.id.detail_image);

            Picasso.with(image.getContext())
                    .load(args.getString(IMAGE))
                    .fit()
                    .centerCrop()
                    .into(image);

            return rootView;
        }

        public void searchArticlesByTop(String url, final TextView view, final long id) {

            final DatabaseHelper db = DatabaseHelper.getInstance(view.getContext());
            String paragraph = db.isThereAParagraph(id);
            if (paragraph != null) {
                view.setText(paragraph);
            } else {
                RequestQueue queue = Volley.newRequestQueue(view.getContext());

                JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                        NYTApiData.URL_SEARCH + "?api-key=" + NYTApiData.API_KEY + "&fq=web_url:(\"" + url + "\")", null,
                        new com.android.volley.Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    String paragraph;
                                    JSONObject root = response;
                                    JSONObject searchResponse = root.getJSONObject("response");
                                    JSONArray docs = searchResponse.getJSONArray("docs");
                                    JSONObject article = docs.getJSONObject(0);
                                    Log.d(TAG, "onResponse: " + article.getString("web_url"));
                                    paragraph = article.getString("lead_paragraph");
                                    db.addParagraph(id,paragraph);
                                    view.setText(paragraph);
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                });

                queue.add(jsonObjectRequest);
            }
        }
    }
}
