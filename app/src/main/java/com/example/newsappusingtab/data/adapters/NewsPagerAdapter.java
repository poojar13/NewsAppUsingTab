package com.example.newsappusingtab.data.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import com.example.newsappusingtab.screen.NewsListFragment;

import java.util.ArrayList;

public class NewsPagerAdapter extends FragmentStatePagerAdapter {
    ArrayList<String> articles = new ArrayList<>();
    public NewsPagerAdapter(FragmentManager fm, ArrayList<String> articlesArrayList) {
        super(fm);
        addArticles(articlesArrayList);
    }
    @NonNull
    @Override
    public Fragment getItem(int position) {
        return NewsListFragment.newInstance(articles.get(position));
    }

    @Override
    public int getCount() {
        return articles.size();
    }

    public void addArticles(ArrayList<String> articlesArrayList){
        if(articlesArrayList!=null){
            articles.clear();
            articles.addAll(articlesArrayList);
            notifyDataSetChanged();
        }
    }

}
