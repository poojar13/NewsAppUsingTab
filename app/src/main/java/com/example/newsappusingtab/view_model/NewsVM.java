package com.example.newsappusingtab.view_model;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;


import com.example.newsappusingtab.data.models.Newsresponse;
import com.example.newsappusingtab.network_manager.NetworkCom;
import com.example.newsappusingtab.repository.NewsRepository;

import java.util.HashMap;

public class NewsVM extends AndroidViewModel {
    private NewsRepository newsRepository;
    public final NetworkCom<Newsresponse> newsresponseNetworkCom = new NetworkCom.Factory<Newsresponse>().createNonMutableNetworkCom();
    public final NetworkCom<Newsresponse> newsbySourceresponseNetworkCom = new NetworkCom.Factory<Newsresponse>().createNonMutableNetworkCom();

    public NewsVM(@NonNull Application application) {
        super(application);
        newsRepository = NewsRepository.getInstance();
    }

    public void getNews(HashMap<String,String> queryMap){
        newsRepository.getNews(queryMap,newsresponseNetworkCom);
    }
    public void getNewsBySource(HashMap<String,String> queryMap){
        newsRepository.getNews(queryMap,newsbySourceresponseNetworkCom);
    }
}
