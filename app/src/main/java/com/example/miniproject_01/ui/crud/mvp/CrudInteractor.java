package com.example.miniproject_01.ui.crud.mvp;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import android.util.Log;

import com.example.miniproject_01.data.ServiceGenerator;
import com.example.miniproject_01.data.service.ArticleService;
import com.example.miniproject_01.entity.Article;
import com.example.miniproject_01.entity.response.ArticleOneResponse;
import com.example.miniproject_01.entity.response.ImageResponse;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CrudInteractor implements CrudMvp.Interactor {

    ArticleService articleService;

    public CrudInteractor(){
        articleService= ServiceGenerator.createService(ArticleService.class);
    }

    @Override
    public void updateArticles(int id, Article article, OnInteractorUpdateListener listener) {
        Call<ArticleOneResponse> call= articleService.updateData(id,article);
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

    @Override
    public void addArticles(Article article, OnInteractorAddListener listener) {
        Call<ArticleOneResponse> call= articleService.postData(article);
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

    private static final String TAG = "CrudInteractor";


    @Override
    public void findArticles(int id,boolean isDetail, OnInteractorFindListener listener) {
        Call<ArticleOneResponse> call= articleService.getDataById(id);
        call.enqueue(new Callback<ArticleOneResponse>() {
            @Override
            public void onResponse(Call<ArticleOneResponse> call, Response<ArticleOneResponse> response) {
                listener.onSuccess(response.body(),isDetail);
            }

            @Override
            public void onFailure(Call<ArticleOneResponse> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });

    }

    @Override
    public void uploadImage(Context context, Uri uri,OnInteractorUploadListener listener){
        String img_path =getRealPathFromUri(context,uri);
        File file = new File(img_path);
        /* create request body and multipart body */
        RequestBody requestBody = RequestBody.create(MediaType.parse("image/*"),file);
        MultipartBody.Part multipartBody = MultipartBody.Part.createFormData("file",file.getName(),requestBody);
        Log.d(TAG, "uploadImage: fuck file "+file.getName());
        Call<ImageResponse> call= articleService.uploadImg(multipartBody);
        call.enqueue(new Callback<ImageResponse>() {
            @Override
            public void onResponse(Call<ImageResponse> call, Response<ImageResponse> response) {
                listener.onSuccess(response.body().getData());
            }

            @Override
            public void onFailure(Call<ImageResponse> call, Throwable t) {
                listener.onFailure(t.getMessage());
            }
        });
    }

    public static String getRealPathFromUri(Context context, Uri contentUri) {
        Cursor cursor = null;
        try {
            String[] proj = { MediaStore.Images.Media.DATA };
            cursor = context.getContentResolver().query(contentUri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }
}
