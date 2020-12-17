package com.example.miniproject_01.ui.home.mvp;

import com.example.miniproject_01.entity.Article;
import com.example.miniproject_01.entity.Pagination;
import com.example.miniproject_01.entity.response.ArticleOneResponse;

import java.util.List;


public class HomePresenter implements HomeMvp.Presenter {
    private HomeMvp.View view;
    private HomeMvp.Interactor interactor;
    public HomePresenter(){
        interactor = new HomeInteractor();
    }
    @Override
    public void getArticles(int page,int limit,boolean isRefresh) {
        if(view!=null)
        interactor.getArticles(page,limit,isRefresh,new HomeMvp.Interactor.OnInteractorListener() {
            @Override
            public void onSuccess(List<Article> articles, Pagination pagination) {
                view.onSuccess(articles,pagination);
            }

            @Override
            public void onFailure(String msg) {
                view.onFailure(msg);
            }
        });
    }
    @Override
    public void deleteArticles(int id) {
        if(view!=null){
            interactor.deleteArticles(id, new HomeMvp.Interactor.OnInteractorDeleteListener() {
                @Override
                public void onSuccess(ArticleOneResponse article) {
                    view.onDeleteSuccess(article);
                }

                @Override
                public void onFailure(String msg) {
                    view.onFailure(msg);
                }
            });
        }
    }

    @Override
    public void setView(HomeMvp.View view) {
        this.view = view;
    }
}
