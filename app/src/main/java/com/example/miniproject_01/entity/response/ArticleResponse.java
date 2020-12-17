package com.example.miniproject_01.entity.response;


import com.example.miniproject_01.entity.Article;
import com.example.miniproject_01.entity.Pagination;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ArticleResponse {

    @SerializedName("data")
    private List<Article> data;

    @SerializedName("pagination")
    private Pagination paginations;

    public ArticleResponse(List<Article> data, Pagination paginations) {
        this.data = data;
        this.paginations = paginations;
    }

    @Override
    public String toString() {
        return "ArticleResponse{" +
                "data=" + data +
                ", paginations=" + paginations +
                '}';
    }

    public List<Article> getData() {
        return data;
    }

    public void setData(List<Article> data) {
        this.data = data;
    }

    public Pagination getPaginations() {
        return paginations;
    }

    public void setPaginations(Pagination paginations) {
        this.paginations = paginations;
    }
}
