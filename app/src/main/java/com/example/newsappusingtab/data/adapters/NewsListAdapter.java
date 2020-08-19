package com.example.newsappusingtab.data.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;


import com.example.newsappusingtab.R;
import com.example.newsappusingtab.data.models.Newsresponse;
import com.example.newsappusingtab.databinding.NewsListItemBinding;
import com.example.newsappusingtab.screen.WebViewActivity;

import java.util.ArrayList;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsViewHolder> {
    ArrayList<Newsresponse.Articles> articles = new ArrayList<>();
    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        NewsListItemBinding newsListItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()), R.layout.news_list_item,null,false);
        return new NewsViewHolder(newsListItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
        holder.bindView(articles.get(position));
    }

    @Override
    public int getItemCount() {
        return articles.size();
    }

    public void addArticles(ArrayList<Newsresponse.Articles> articles){
        if(articles!=null){
            this.articles.clear();
            this.articles.addAll(articles);
            notifyDataSetChanged();
        }
    }


    public static class NewsViewHolder extends RecyclerView.ViewHolder{
        NewsListItemBinding binding;
        public NewsViewHolder(@NonNull NewsListItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bindView(final Newsresponse.Articles articles) {
            binding.title.setText(articles.title);
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(binding.getRoot().getContext(), WebViewActivity.class);
                    intent.putExtra("loadUrl",articles.url);
                    binding.getRoot().getContext().startActivity(intent);
                }
            });
        }
    }
}
