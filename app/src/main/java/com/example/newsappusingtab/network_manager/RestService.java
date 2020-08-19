package com.example.newsappusingtab.network_manager;


import com.example.newsappusingtab.data.models.Newsresponse;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public interface RestService {

    @GET("v2/top-headlines")
    Call<Newsresponse> getNews(@QueryMap HashMap<String, String> queryMap);

}
