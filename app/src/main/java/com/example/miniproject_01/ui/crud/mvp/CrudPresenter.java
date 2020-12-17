package com.example.miniproject_01.ui.crud.mvp;

import android.content.Context;
import android.net.Uri;

import com.example.miniproject_01.entity.Article;
import com.example.miniproject_01.entity.response.ArticleOneResponse;

public class CrudPresenter implements CrudMvp.Presenter {
    private CrudMvp.View view;
    private CrudMvp.Interactor interactor;
    public CrudPresenter(){
        interactor= new CrudInteractor();
    }
    @Override
    public void addArticles(Article article) {
        if(view!=null)
            view.onShowProgress();
        if(view!=null)
        interactor.addArticles(article, new CrudMvp.Interactor.OnInteractorAddListener() {
            @Override
            public void onSuccess(ArticleOneResponse article) {
                view.onAddSuccess(article);
                view.onHideProgress();
            }

            @Override
            public void onFailure(String msg) {
                view.onFailure(msg);
                view.onHideProgress();
            }
        });
    }

    @Override
    public void uploadImage(Context context, Uri uri) {
        if(view!=null)
        view.onShowProgress();
        if(view!=null)
            interactor.uploadImage(context, uri, new CrudMvp.Interactor.OnInteractorUploadListener() {
                @Override
                public void onSuccess(String url) {
                    view.onUploadSuccess(url);
                    view.onHideProgress();
                }

                @Override
                public void onFailure(String msg) {
                    view.onFailure(msg);
                    view.onHideProgress();
                }
            });
    }

    @Override
    public void findArticles(int id,boolean isDetail) {
        if(view!=null)
            interactor.findArticles(id,isDetail, new CrudMvp.Interactor.OnInteractorFindListener() {
                @Override
                public void onSuccess(ArticleOneResponse article,boolean isDetail) {
                    view.onFindSuccess(article,isDetail);
                }

                @Override
                public void onFailure(String msg) {
                    view.onFailure(msg);
                }
            });
    }

    @Override
    public void updateArticles(int id, Article article) {
        if(view!=null){
            interactor.updateArticles(id, article, new CrudMvp.Interactor.OnInteractorUpdateListener() {
                @Override
                public void onSuccess(ArticleOneResponse article) {
                    view.onUpdateSuccess(article);
                }

                @Override
                public void onFailure(String msg) {
                    view.onFailure(msg);
                }
            });
        }
    }


    @Override
    public void setView(CrudMvp.View view) {
        this.view=view;
    }
}
