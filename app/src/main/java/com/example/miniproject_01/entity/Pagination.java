package com.example.miniproject_01.entity;

import com.google.gson.annotations.SerializedName;

public class Pagination {
    @SerializedName("PAGE")
    private int page;
    @SerializedName("LIMIT")
    private int limit;
    @SerializedName("TOTAL_COUNT")
    private int totalCount;
    @SerializedName("TOTAL_PAGES")
    private int totalPage;

    public Pagination() {
    }

    @Override
    public String toString() {
        return "Pagination{" +
                "page=" + page +
                ", limit=" + limit +
                ", totalCount=" + totalCount +
                ", totalPage=" + totalPage +
                '}';
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
}
