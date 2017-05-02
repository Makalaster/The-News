package com.example.ivonneortega.the_news_project.MainActivity;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ivonneortega.the_news_project.DatabaseHelper;
import com.example.ivonneortega.the_news_project.data.Doc;
import com.example.ivonneortega.the_news_project.data.Headline;
import com.example.ivonneortega.the_news_project.data.NYTApiData;
import com.example.ivonneortega.the_news_project.data.NYTSearchQuery;
import com.example.ivonneortega.the_news_project.data.SearchResponse;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArticleRefreshService extends JobService {
    private Call<SearchResponse> mCall;
    private List<String> mNewsWireList = new ArrayList<>();
    private List<String> mTopStoriesList = new ArrayList<>();
    private static final String TAG = "ArticleRefreshService";
    public static final String JSON = ".json";

    @Override
    public void onCreate() {
        super.onCreate();

        if (mNewsWireList.isEmpty()) {
            mNewsWireList.add("World");
            mNewsWireList.add("u.s.");
            mNewsWireList.add("Business Day");
            mNewsWireList.add("technology");
            mNewsWireList.add("science");
            mNewsWireList.add("Sports");
            mNewsWireList.add("Movies");
            mNewsWireList.add("fashion+&+style");
            mNewsWireList.add("Food");
            mNewsWireList.add("Health");
        }

        if (mTopStoriesList.isEmpty()) {
            mTopStoriesList.add("world");
            mTopStoriesList.add("politics");
            mTopStoriesList.add("business");
            mTopStoriesList.add("technology");
            mTopStoriesList.add("science");
            mTopStoriesList.add("sports");
            mTopStoriesList.add("movies");
            mTopStoriesList.add("fashion");
            mTopStoriesList.add("food");
            mTopStoriesList.add("health");
        }

    }

    @Override
    public boolean onStartJob(JobParameters params) {
        Log.d(TAG, "onStartJob: REFRESH JOB STARTED");

        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null) {
            for (String topic : mTopStoriesList) {
                AsyncTask<String, Void, Void> topTask = new AsyncTask<String, Void, Void>() {
                    @Override
                    protected Void doInBackground(String... params) {
                        queryTopStories(params[0]);
                        try {
                            Thread.sleep(250);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                };
                topTask.execute(topic);
            }

            for (String topic : mNewsWireList) {
                queryNewsWire(topic);
            }

            jobFinished(params, true);
        }

        return true;
    }

    private void queryTopStories(String query) {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                NYTApiData.URL_TOP_STORY + query + JSON+"?api-key=" + NYTApiData.API_KEY, null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {
                            JSONArray results = response.getJSONArray("results");
                            for(int i = 0; i < results.length(); i++)
                            {
                                JSONObject article = results.getJSONObject(i);
                                addArticleToDatabase(article, 1);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onErrorResponse: " + error);
            }
        });

        queue.add(jsonObjectRequest);
    }

    private void queryNewsWire(String query) {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                NYTApiData.URL_NEWS_WIRE + query + JSON + "?api-key=" + NYTApiData.API_KEY, null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {
                            JSONArray results = response.getJSONArray("results");

                            for(int i = 0; i < results.length(); i++)
                            {
                                JSONObject article = results.getJSONObject(i);
                                addArticleToDatabase(article, 1);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onErrorResponse: "+error);
            }
        });
        queue.add(jsonObjectRequest);
    }

    public void addArticleToDatabase(JSONObject article, int fromTopStories) {
        DatabaseHelper db = DatabaseHelper.getInstance(this);

        String url = null;
        String title = null;
        String date = null;
        String category = null;
        String image = null;
        try {
            url = article.getString("url");
            title = article.getString("title");
            date = article.getString("published_date");
            category = article.getString("section");
            JSONArray multimedia = article.getJSONArray("multimedia");
            for (int i = 0; i < multimedia.length(); i++) {
                JSONObject pic = multimedia.getJSONObject(i);
                if (pic.getString("format").equals("Normal") && pic.getString("type").equals("image")) {
                    image = pic.getString("url");
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        String source = "New York Times";
        int isSaved = 0;

        if (db.getArticleByUrl(url) == null) {
            db.insertArticleIntoDatabase(image, title, category, date, null, source, isSaved, fromTopStories, url);
        }
    }

    public void searchArticles(String url, final int fromTopStories) {
        RequestQueue queue = Volley.newRequestQueue(this);

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                NYTApiData.URL_SEARCH + "?api-key=" + NYTApiData.API_KEY + "&fq=web_url(\"" + url + "\")", null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        SearchResponse searchResponse = gson.fromJson(response.toString(), SearchResponse.class);
                        //addArticleToDatabase(searchResponse, fromTopStories);
                    }
                }, new com.android.volley.Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        queue.add(jsonObjectRequest);
    }



    @Override
    public boolean onStopJob(JobParameters params) {
        mCall.cancel();

        Log.d(TAG, "onStopJob: JOB STOPPED");
        return false;
    }
}
