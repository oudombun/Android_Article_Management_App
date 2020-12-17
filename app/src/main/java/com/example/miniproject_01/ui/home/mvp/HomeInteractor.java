package com.example.miniproject_01.ui.home.mvp;

import android.util.Log;

import com.example.miniproject_01.data.ServiceGenerator;
import com.example.miniproject_01.data.service.ArticleService;
import com.example.miniproject_01.entity.Article;
import com.example.miniproject_01.entity.response.ArticleOneResponse;
import com.example.miniproject_01.entity.response.ArticleResponse;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeInteractor implements HomeMvp.Interactor {
    List<Article> articles=new ArrayList<>();
    private ArticleService articleService;

    public HomeInteractor(){
        articleService= ServiceGenerator.createService(ArticleService.class);
    }

    @Override
    public void getArticles(int page, int limit,boolean isRefresh,final OnInteractorListener listener) {
        Call<ArticleResponse> call = articleService.getData(page,limit);
        if(isRefresh) articles.clear();
        call.enqueue(new Callback<ArticleResponse>() {
            @Override
            public void onResponse(Call<ArticleResponse> call, Response<ArticleResponse> response) {
                if(response.isSuccessful()){
                    ArticleResponse responseData = response.body();
                    if (responseData != null) {

                        articles.addAll(responseData.getData());
                        listener.onSuccess(articles,responseData.getPaginations());
                    }
                }
            }

            @Override
            public void onFailure(Call<ArticleResponse> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }
    @Override
    public void deleteArticles(int id, OnInteractorDeleteListener listener) {
        Call<ArticleOneResponse> call= articleService.deleteData(id);
        call.enqueue(new Callback<ArticleOneResponse>() {
            @Override
            public void onResponse(Call<ArticleOneResponse> call, Response<ArticleOneResponse> response) {
                listener.onSuccess(response.body());
            }

            @Override
            public void onFailure(Call<ArticleOneResponse> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }
}
