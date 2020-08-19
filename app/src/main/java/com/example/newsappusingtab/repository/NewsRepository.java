package com.example.newsappusingtab.repository;


import com.example.newsappusingtab.data.models.Newsresponse;
import com.example.newsappusingtab.network_manager.ApiModule;
import com.example.newsappusingtab.network_manager.NetworkCom;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsRepository {
    ApiModule apiModule;
    private static NewsRepository newsRepository;
    public NewsRepository() {
        this.apiModule = ApiModule.getInstance();
    }

    public static NewsRepository getInstance() {
        if(newsRepository==null){
            newsRepository = new NewsRepository();
        }
        return newsRepository;
    }

    public void getNews(HashMap<String, String> queryMap, final NetworkCom<Newsresponse> newsresponseNetworkCom) {
        if (newsresponseNetworkCom.isLoading()) {
            return;
        }
        newsresponseNetworkCom.startLoading();
        apiModule.provideService().getNews(queryMap).enqueue(new Callback<Newsresponse>() {
            @Override
            public void onResponse(Call<Newsresponse> call, Response<Newsresponse> response) {
                if (response.isSuccessful()) {
                    newsresponseNetworkCom.publishSuccess(response.body(), response.code(), response.headers());
                } else {
                    newsresponseNetworkCom.publishError(NetworkCom.Error.HTTP, response.message(), response.code(), response.headers());
                }
            }

            @Override
            public void onFailure(Call<Newsresponse> call, Throwable t) {
                NetworkCom.Error error = NetworkCom.Error.OTHER;
                if (t instanceof ApiModule.NotConnectedError) {
                    error = NetworkCom.Error.NETWORK;
                }
                newsresponseNetworkCom.publishError(error, t.getMessage(), -1, null);
            }
        });
    }
}
