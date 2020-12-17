package com.example.miniproject_01.ui.crud.mvp;

import android.content.Context;
import android.net.Uri;

import com.example.miniproject_01.entity.Article;
import com.example.miniproject_01.entity.response.ArticleOneResponse;

public interface CrudMvp {
    interface View{
        void onAddSuccess(ArticleOneResponse article);
        void onFailure(String msg);
        void onFindSuccess(ArticleOneResponse article,boolean isDetail);
        void onUpdateSuccess(ArticleOneResponse article);
        void onUploadSuccess(String url);
        void onShowProgress();
        void onHideProgress();
    }

    interface Presenter{
        void addArticles(Article article);
        void uploadImage(Context context,Uri uri);
        void findArticles(int id,boolean isDetail);
        void updateArticles(int id,Article article);
        void setView(View view);
    }

    interface Interactor{
        void findArticles(int id,boolean isDetail,OnInteractorFindListener listener);
        void updateArticles(int id,Article article,OnInteractorUpdateListener listener);
        void uploadImage(Context context,Uri uri,OnInteractorUploadListener listener);
        void addArticles(Article article,OnInteractorAddListener listener);

        interface OnInteractorAddListener{
            void onSuccess(ArticleOneResponse article);
            void onFailure(String msg);
        }

        interface OnInteractorUploadListener{
            void onSuccess(String url);
            void onFailure(String msg);
        }
        interface OnInteractorFindListener{
            void onSuccess(ArticleOneResponse article,boolean isDetail);
            void onFailure(String msg);
        }
        interface OnInteractorUpdateListener{
            void onSuccess(ArticleOneResponse article);
            void onFailure(String msg);
        }

    }
}
