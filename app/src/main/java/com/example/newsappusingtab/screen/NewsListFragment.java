package com.example.newsappusingtab.screen;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.newsappusingtab.R;
import com.example.newsappusingtab.data.adapters.NewsListAdapter;
import com.example.newsappusingtab.databinding.NewsListFragmentBinding;
import com.example.newsappusingtab.network_manager.NetworkCom;
import com.example.newsappusingtab.view_model.NewsVM;

import java.util.HashMap;

public class NewsListFragment extends Fragment {
    private String sourceId;
    private NewsListFragmentBinding binding;
    private NewsVM newsVM;
    private ProgressDialog progressDialog;

    public static Fragment newInstance(String sourceId) {
        NewsListFragment fragment = new NewsListFragment();
        Bundle args = new Bundle();
        args.putSerializable("sourceId", sourceId);
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()), R.layout.news_list_fragment, container, false);
        sourceId =  getArguments().getString("sourceId");
        newsVM = ViewModelProviders.of(this).get(NewsVM.class);
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Loading News");
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("sources",sourceId);
        hashMap.put("apiKey","124bba32fc9d41d1ac0817f5a34a41e4");
        newsVM.getNewsBySource(hashMap);
        observeNewsBySourceData();
        return binding.getRoot();
    }


    private void observeNewsBySourceData(){
        newsVM.newsbySourceresponseNetworkCom.getState().observe(this, new Observer<NetworkCom.States>() {
            @Override
            public void onChanged(NetworkCom.States states) {
                switch (states){
                    case SUCCESS:
                        if(progressDialog!=null && progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                        NewsListAdapter newsListAdapter = new NewsListAdapter();
                        if(newsVM.newsbySourceresponseNetworkCom.getData().getValue().articlesArrayList.isEmpty()){
                            binding.noNewsText.setVisibility(View.VISIBLE);
                        }
                        else {
                            binding.noNewsText.setVisibility(View.GONE);
                        }
                        newsListAdapter.addArticles(newsVM.newsbySourceresponseNetworkCom.getData().getValue().articlesArrayList);
                        binding.newsRecyclerView.setAdapter(newsListAdapter);
                        break;
                    case FAILED:
                        if(progressDialog!=null && progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                        break;
                    case LOADING:
                        if(progressDialog!=null && progressDialog.isShowing()){
                            return;
                        }
                        progressDialog.show();
                        break;
                }
            }
        });
    }
}
