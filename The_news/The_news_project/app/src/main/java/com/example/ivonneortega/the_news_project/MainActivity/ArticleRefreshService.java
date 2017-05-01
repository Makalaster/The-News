package com.example.ivonneortega.the_news_project.MainActivity;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.ivonneortega.the_news_project.Article;
import com.example.ivonneortega.the_news_project.DatabaseHelper;
import com.example.ivonneortega.the_news_project.data.Doc;
import com.example.ivonneortega.the_news_project.data.Headline;
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
    private List<String> mNewsWireList = new ArrayList<>();
    private List<String> mTopStoriesList = new ArrayList<>();
    private static final String TAG = "ArticleRefreshService";
    public static final String JSON = ".json";

    @Override
    public int onStartCommand(Intent intent,  int flags, int startId) {
        if (mNewsWireList.isEmpty()) {
            mNewsWireList.add("World");
            mNewsWireList.add("u.s.");
            mNewsWireList.add("Business Day");
            mNewsWireList.add("technology");
            mNewsWireList.add("science");
            mNewsWireList.add("Sports");
            mNewsWireList.add("Movies");
            mNewsWireList.add("fashion & style");
            mNewsWireList.add("Food");
            mNewsWireList.add("Health");
        }

        if (mTopStoriesList.isEmpty()) {
            mTopStoriesList.add("World");
            mTopStoriesList.add("Politics");
            mTopStoriesList.add("Business");
            mTopStoriesList.add("Technology");
            mTopStoriesList.add("Science");
            mTopStoriesList.add("Sports");
            mTopStoriesList.add("Movies");
            mTopStoriesList.add("Fashion");
            mTopStoriesList.add("Food");
            mTopStoriesList.add("Health");
        }

        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null) {
            List<String> newsWireUrls = new ArrayList<>();
            for (String topic : mNewsWireList) {
                newsWireUrls.addAll(queryNewsWire(topic));
            }
            for (String url : newsWireUrls) {
                searchArticles(url, 0);
            }

            List<String> topUrls = new ArrayList<>();
            for (String topic : mTopStoriesList) {
                topUrls.addAll(queryTopStories(topic));
            }
            for (String url : topUrls) {
                searchArticles(url, 1);
            }
        }

        return false;
    }

    private List<String> queryNewsWire(String query) {
        RequestQueue queue = Volley.newRequestQueue(this);
        final List<String> list = new ArrayList<>();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET,
                NYTApiData.URL_NEWS_WIRE + query + JSON + "?api-key=" + NYTApiData.API_KEY, null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try
                        {
                            JSONArray items = response.getJSONArray("results");

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
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
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
                            JSONArray items = response.getJSONArray("results");
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
                Toast.makeText(getApplicationContext(), "Error", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "onErrorResponse: "+error);
            }
        });
        queue.add(jsonObjectRequest);
        return list;
    }

    public void searchArticles(String url, final int fromTopStories) {
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
                addArticleToDatabase(nytResponse, fromTopStories);
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

    public void addArticleToDatabase(SearchResponse searchResponse, int fromTopStories) {
        DatabaseHelper db = DatabaseHelper.getInstance(this);

        com.example.ivonneortega.the_news_project.data.Response response = searchResponse.getResponse();
        Doc doc = response.getDocs().get(0);
        String url = doc.getWebUrl();
        Headline headline = doc.getHeadline();
        String mainHeadline = headline.getMain();
        String date = doc.getPubDate();
        String category = doc.getSectionName();
        String body = doc.getLeadParagraph();
        String source = "New York Times";
        int isSaved = 0;

        db.insertArticleIntoDatabase(null, mainHeadline, category, date, body, source, isSaved, fromTopStories, url);
    }
}
