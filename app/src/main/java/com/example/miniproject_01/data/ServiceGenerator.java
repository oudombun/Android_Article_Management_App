package com.example.miniproject_01.data;

import android.text.TextUtils;
import android.util.Log;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {
    private static String BASE_URL = "http://110.74.194.124:3034";

    private static String basic = "Basic %s";
    private static String authToken = "QU1TQVBJQURNSU46QU1TQVBJUEBTU1dPUkQ=";



//    private static OkHttpClient.Builder clients = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
//        @Override
//        public Response intercept(Chain chain) throws IOException {
//            Request request = chain.request().newBuilder()
//                                .addHeader("Authorization",String.format(basic, authToken)).build();
//            return chain.proceed(request);
//        }
//    });
    private static HttpLoggingInterceptor interceptor=new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);
    private static OkHttpClient client = new OkHttpClient.Builder()
            .addInterceptor(new Interceptor() {
                @Override
                public okhttp3.Response intercept(Chain chain) throws IOException {
                    Request newRequest = chain.request().newBuilder()
                            .addHeader("Authorization", String.format(basic, authToken))
                            .build();
                    return chain.proceed(newRequest);
                }
            })
            .addInterceptor(interceptor).build();

    private static Retrofit.Builder builder = new Retrofit.Builder()
            .client(client)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create());

    public static <S> S createService(Class<S> service){
        return builder.build().create(service);
    }


}
