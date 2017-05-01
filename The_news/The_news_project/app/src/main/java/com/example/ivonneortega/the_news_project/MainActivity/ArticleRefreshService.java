package com.example.ivonneortega.the_news_project.MainActivity;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.example.ivonneortega.the_news_project.data.NYTApiData;
import com.example.ivonneortega.the_news_project.data.NYTSearchQuery;
import com.example.ivonneortega.the_news_project.data.SearchResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ArticleRefreshService extends JobService {
    Call<SearchResponse> mCall;

    @Override
    public boolean onStartJob(JobParameters params) {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(NYTApiData.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            NYTSearchQuery searchQuery = retrofit.create(NYTSearchQuery.class);
            //TODO replace url
            mCall = searchQuery.getArticles(NYTApiData.API_KEY, params.getExtras().getString("url"));

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

        return false;
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
