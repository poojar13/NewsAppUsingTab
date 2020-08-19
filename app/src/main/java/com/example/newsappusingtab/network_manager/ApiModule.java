package com.example.newsappusingtab.network_manager;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiModule {
    private static ApiModule instance;
    public RestService provideService() {
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);


        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.connectTimeout(10, TimeUnit.SECONDS);
        httpClient.readTimeout(60, TimeUnit.SECONDS);
        httpClient.writeTimeout(30, TimeUnit.SECONDS);
        httpClient.addInterceptor(logging);


         Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://newsapi.org/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient.build())
                .build();
        return retrofit.create(RestService.class);
    }
    private ApiModule () {
       provideService();
    }
    public static ApiModule getInstance() {
        if (instance == null) {
            instance = new ApiModule();
        }
        return instance;
    }
    public static final class NotConnectedError extends IOException {
        public NotConnectedError() {
        }

        public NotConnectedError(String detailMessage) {
            super(detailMessage);
        }

        @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
        public NotConnectedError(String detailMessage, Throwable throwable) {
            super(detailMessage, throwable);
        }

        @RequiresApi(api = Build.VERSION_CODES.GINGERBREAD)
        public NotConnectedError(Throwable throwable) {
            super(throwable);
        }
    }
}
