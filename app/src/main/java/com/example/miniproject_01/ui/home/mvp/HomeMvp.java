package com.example.miniproject_01.ui.home.mvp;

import com.example.miniproject_01.entity.Article;
import com.example.miniproject_01.entity.Pagination;
import com.example.miniproject_01.entity.response.ArticleOneResponse;

import java.util.List;

public interface HomeMvp {
    interface View{
        void onSuccess(List<Article> articles,Pagination page);
        void onFailure(String msg);
        void onDeleteSuccess(ArticleOneResponse article);
    }

    interface Presenter{
        void getArticles(int page ,int limit,boolean isRefresh);
        void deleteArticles(int id);
        void setView(View view);
    }

    interface Interactor {
        void deleteArticles(int id,OnInteractorDeleteListener listener);
        void getArticles(int page, int limit,boolean isRefresh,OnInteractorListener listener);
        interface OnInteractorListener{
            void onSuccess(List<Article> articles, Pagination page);
            void onFailure(String msg);
        }
        interface OnInteractorDeleteListener{
            void onSuccess(ArticleOneResponse article);
            void onFailure(String msg);
        }
    }
}
