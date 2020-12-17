package com.example.miniproject_01.entity.response;

import com.example.miniproject_01.entity.Article;
import com.google.gson.annotations.SerializedName;

public class ArticleOneResponse {
    @SerializedName("data")
    private Article data;

    public ArticleOneResponse(Article data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "ArticleOneResponse{" +
                "data=" + data +
                '}';
    }

    public Article getData() {
        return data;
    }

    public void setData(Article data) {
        this.data = data;
    }
}
