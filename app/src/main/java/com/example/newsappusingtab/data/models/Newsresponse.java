package com.example.newsappusingtab.data.models;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Newsresponse {
    @SerializedName("articles")
    public ArrayList<Articles> articlesArrayList;

    public static class Articles {
        @SerializedName("source")
        public NewsSource source;

        public class NewsSource {
            @SerializedName("id")
            public String id;
            @SerializedName("name")
            public String name;
        }

        @SerializedName("author")
        public String author;
        @SerializedName("title")
        public String title;
        @SerializedName("description")
        public String description;
        @SerializedName("url")
        public String url;
        @SerializedName("urlToImage")
        public String urlToImage;
        @SerializedName("publishedAt")
        public String publishedAt;
        @SerializedName("content")
        public String content;
    }
}
