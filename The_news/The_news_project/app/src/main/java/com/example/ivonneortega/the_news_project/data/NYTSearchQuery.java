package com.example.ivonneortega.the_news_project.data;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Makalaster on 5/1/17.
 */

public interface NYTSearchQuery {
    @GET("articlesearch.json")
    Call<SearchResponse> getArticles(@Query("api-key") String apiKey, @Query("fq") String url);
}
