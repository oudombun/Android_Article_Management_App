package com.example.miniproject_01.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.miniproject_01.R;
import com.example.miniproject_01.entity.Article;
import com.example.miniproject_01.entity.Pagination;
import com.example.miniproject_01.entity.response.ArticleOneResponse;
import com.example.miniproject_01.ui.crud.CrudActivity;
import com.example.miniproject_01.ui.home.mvp.HomeMvp;
import com.example.miniproject_01.ui.home.mvp.HomePresenter;
import com.google.android.material.bottomappbar.BottomAppBar;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.paginate.Paginate;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements HomeMvp.View {
    private List<Article> mArrayList = new ArrayList<>();
    private RecyclerView mRecyclerView;
    private ArticleAdapter mAdapter;
    private HomeMvp.Presenter presenter;
    private int currentPage= 1;
    private boolean isLoading = false;
    private int totalPage= 0;
    private ImageView imageViews;
    SwipeRefreshLayout swipeRefreshLayout;

    FloatingActionButton floatingActionButton;
    BottomAppBar bottomAppBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        presenter = new HomePresenter();
        presenter.setView(this);

        setupUI();

        setupRecyclerview();

        isLoading=true;
        currentPage=1;
        getData();

        swipeRefreshLayout.setOnRefreshListener(this::refresh);

        floatingActionButton.setOnClickListener(v->{
            Intent intent_add = new Intent(MainActivity.this, CrudActivity.class);
            int id_add = R.layout.activity_crud_add;
            intent_add.putExtra("MODE",id_add);
            startActivity(intent_add);
        });
    }
    private void refresh() {
        currentPage=1;
        mArrayList.clear();
        isLoading=false;
        presenter.getArticles(currentPage,15, true);
        currentPage++;
        swipeRefreshLayout.setRefreshing(false);
    }

    
    private void getData() {
         Paginate.with(mRecyclerView,callback)
        .setLoadingTriggerThreshold(2)
        .addLoadingListItem(true)
        .setLoadingListItemCreator(null)
        .setLoadingListItemSpanSizeLookup(()->1)
        .build();
    }
    
    private Paginate.Callbacks callback=new Paginate.Callbacks() {
        @Override
        public void onLoadMore() {
            if(isLoading){
                presenter.getArticles(currentPage,15, false);
                isLoading=false;
                currentPage++;
            }
        }

        @Override
        public boolean isLoading() {
            return false;
        }

        @Override
        public boolean hasLoadedAllItems() {
            return currentPage==totalPage;
        }
    };


    private void setupUI() {
        mRecyclerView = findViewById(R.id.recyclerView);
        swipeRefreshLayout = findViewById(R.id.swipe);

        floatingActionButton = findViewById(R.id.fab);
        bottomAppBar = findViewById(R.id.bottomAppBar);
        //main line for setting menu in bottom app bar
        setSupportActionBar(bottomAppBar);

    }

    private void setupRecyclerview() {
        mArrayList =new ArrayList<>();
        mAdapter = new ArticleAdapter(this, mArrayList, position -> {
            Article article = mArrayList.get(position);
            deleteArticle(article.getId());
        });
        mAdapter.notifyDataSetChanged();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        mRecyclerView.setItemAnimator( new DefaultItemAnimator());
        mRecyclerView.setAdapter(mAdapter);
    }

    private void deleteArticle(int id) {
        AlertDialog.Builder dialog= new AlertDialog.Builder(this)
                            .setTitle("Delete")
                            .setMessage("do you want to delete this?")
                            .setPositiveButton("ok", (d, which) -> presenter.deleteArticles(id))
                            .setNegativeButton("cancel", (d, which) -> d.dismiss());
        dialog.show();
    }


    @Override
    public void onSuccess(List<Article> articles,Pagination pagination) {
        mArrayList.clear();
        mArrayList.addAll(articles);
        totalPage=pagination.getTotalPage();
        mAdapter.notifyDataSetChanged();
        isLoading=true;

    }

    @Override
    public void onFailure(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_LONG).show();
    }

    @Override
    public void onDeleteSuccess(ArticleOneResponse article) {
        Toast.makeText(this, "delete success", Toast.LENGTH_SHORT).show();
        refresh();
    }

}
