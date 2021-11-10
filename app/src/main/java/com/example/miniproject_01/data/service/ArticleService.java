package com.example.miniproject_01.data.service;

import com.example.miniproject_01.entity.Article;
import com.example.miniproject_01.entity.response.ArticleOneResponse;
import com.example.miniproject_01.entity.response.ArticleResponse;
import com.example.miniproject_01.entity.response.ImageResponse;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ArticleService {

    @GET("/api/articles")
    Call<ArticleResponse> getData(@Query("page") int page, @Query("limit") int limit);

    @POST("/api/articles")
    Call<ArticleOneResponse> postData(@Body Article article);

    @Multipart
    @POST("/api/uploadfile/single")
    Call<ImageResponse> uploadImg(@Part MultipartBody.Part file);

    @GET("/api/articles/{id}")
    Call<ArticleOneResponse> getDataById(@Path("id") int id);

    @DELETE("/api/articles/{id}")
    Call<ArticleOneResponse> deleteData(@Path("id") int id);

    @PUT("/api/articles/{id}")
    Call<ArticleOneResponse> updateData(@Path("id") int id,@Body Article article);
}
