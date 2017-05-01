package com.example.ivonneortega.the_news_project.MainActivity;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.annotation.IntDef;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ivonneortega.the_news_project.Article;
import com.example.ivonneortega.the_news_project.data.NYTApiData;
import com.example.ivonneortega.the_news_project.data.NYTSearchQuery;
import com.example.ivonneortega.the_news_project.data.SearchResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArticleRefreshService extends JobService {
    private Call<SearchResponse> mCall;
    private List<String> newsWireList = new ArrayList<>();
    private List<String> topStoriesList = new ArrayList<>();
    private static final String TAG = "ArticleRefreshService";
    public static final String JSON = ".json";

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        if (newsWireList.isEmpty()) {
            newsWireList.add("World");
            newsWireList.add("u.s.");
            newsWireList.add("Business Day");
            newsWireList.add("technology");
            newsWireList.add("science");
            newsWireList.add("Sports");
            newsWireList.add("Movies");
            newsWireList.add("fashion & style");
            newsWireList.add("Food");
            newsWireList.add("Health");
        }

        if (topStoriesList.isEmpty()) {
            topStoriesList.add("World");
            topStoriesList.add("Politics");
            topStoriesList.add("Business");
            topStoriesList.add("Technology");
            topStoriesList.add("Science");
            topStoriesList.add("Sports");
            topStoriesList.add("Movies");
            topStoriesList.add("Fashion");
            topStoriesList.add("Food");
            topStoriesList.add("Health");
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null) {


            searchArticles("url");
        }

        return false;
    }

    private List<String> queryNewsWire(String query) {
        RequestQueue queue = Volley.newRequestQueue(this);
        final List<String> list = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, NYTApiData.URL_NEWS_WIRE + query +JSON+"?"+"api-key"+NYTApiData.API_KEY, null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {
                            JSONObject root = response;
                            JSONArray items = root.getJSONArray("results");
                            List<Article> articleList = new ArrayList<>();
                            for(int i=0;i<items.length();i++)
                            {
                                JSONObject aux = items.getJSONObject(i);
                                String url = aux.getString("url");
                                list.add(url);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onErrorResponse: "+error);
            }
        });
        queue.add(jsonObjectRequest);
        return list;
    }

    private List<String> queryTopStories(String query) {
        RequestQueue queue = Volley.newRequestQueue(this);
        final List<String> list = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, NYTApiData.URL_TOP_STORY + query +JSON+"?"+"api-key"+NYTApiData.API_KEY, null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {
                            JSONObject root = response;
                            JSONArray items = root.getJSONArray("results");
                            List<Article> articleList = new ArrayList<>();
                            for(int i=0;i<items.length();i++)
                            {
                                JSONObject aux = items.getJSONObject(i);
                                String url = aux.getString("url");
                                list.add(url);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onErrorResponse: "+error);
            }
        });
        queue.add(jsonObjectRequest);
        return list;
    }

    public void searchArticles(String url) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NYTApiData.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        NYTSearchQuery searchQuery = retrofit.create(NYTSearchQuery.class);
        mCall = searchQuery.getArticles(NYTApiData.API_KEY, url);

        mCall.enqueue(new Callback<SearchResponse>() {
            @Override
            public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                SearchResponse nytResponse = response.body();
                addArticleToDatabase(nytResponse);
            }

            @Override
            public void onFailure(Call<SearchResponse> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        mCall.cancel();

        return false;
    }

    public void addArticleToDatabase(SearchResponse response) {
        //TODO Add article to database
    }
}
