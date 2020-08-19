package com.example.newsappusingtab.screen;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.viewpager.widget.ViewPager;

import com.example.newsappusingtab.R;
import com.example.newsappusingtab.data.adapters.NewsPagerAdapter;
import com.example.newsappusingtab.data.models.Newsresponse;
import com.example.newsappusingtab.databinding.ActivityMainBinding;
import com.example.newsappusingtab.network_manager.NetworkCom;
import com.example.newsappusingtab.view_model.NewsVM;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private NewsVM newsVM;
    private HashMap<String, String> hashMap = new HashMap<>();
    private ProgressDialog progressDialog;
    private NewsPagerAdapter newsPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Loading News");
        newsVM = ViewModelProviders.of(this).get(NewsVM.class);
        hashMap.put("country", "us");
        hashMap.put("category", "business");
        hashMap.put("apiKey", "124bba32fc9d41d1ac0817f5a34a41e4");
        newsVM.getNews(hashMap);
        observeNewsData();
    }

    private void observeNewsData() {
        newsVM.newsresponseNetworkCom.getState().observe(this, new Observer<NetworkCom.States>() {
            @Override
            public void onChanged(NetworkCom.States states) {
                switch (states) {
                    case SUCCESS:
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        showNewsInTab(newsVM.newsresponseNetworkCom.getData().getValue().articlesArrayList);
                        break;
                    case FAILED:
                        if (progressDialog != null && progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }
                        break;
                    case LOADING:
                        if (progressDialog != null && progressDialog.isShowing()) {
                            return;
                        }
                        progressDialog.show();
                        break;
                }
            }
        });
    }

    private void showNewsInTab(ArrayList<Newsresponse.Articles> articlesArrayList) {
        final HashSet<Newsresponse.Articles.NewsSource> newsSources = new HashSet<>();
        final ArrayList<String> arrayList = new ArrayList<>();
        if (articlesArrayList.size() > 0) {
            for (int i = 0; i < articlesArrayList.size(); i++) {
                if (newsSources.size() == 10) {
                    break;
                }
                newsSources.add(articlesArrayList.get(i).source);

            }
            for (final Newsresponse.Articles.NewsSource source : newsSources) {
                arrayList.add(source.id);
                binding.tablayout.addTab(binding.tablayout.newTab().setText(source.name));
            }
            newsPagerAdapter = new NewsPagerAdapter(getSupportFragmentManager(), arrayList);
            binding.viewPager.setAdapter(newsPagerAdapter);
            binding.viewPager.setOffscreenPageLimit(1);
            binding.viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(binding.tablayout));
            binding.tablayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(binding.viewPager));
        }

    }


}