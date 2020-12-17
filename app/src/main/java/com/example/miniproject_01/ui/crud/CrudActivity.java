package com.example.miniproject_01.ui.crud;


import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.miniproject_01.R;
import com.example.miniproject_01.entity.Article;
import com.example.miniproject_01.entity.response.ArticleOneResponse;
import com.example.miniproject_01.ui.crud.mvp.CrudMvp;
import com.example.miniproject_01.ui.crud.mvp.CrudPresenter;
import com.example.miniproject_01.ui.home.MainActivity;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.List;

import pub.devrel.easypermissions.AfterPermissionGranted;
import pub.devrel.easypermissions.AppSettingsDialog;
import pub.devrel.easypermissions.EasyPermissions;

public class CrudActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks, CrudMvp.View {

    private CrudMvp.Presenter presenter;

    Button btnChoose;
    Button btnAdd,btnUpdate;
    ImageView imageView;
    EditText mTitle,mDes;
    ProgressBar progressBar;
    TextView vTitle,vDes;
    String imageUrl,Title,Description;
    Uri uri;
    int vid,uid;
    boolean isuploaded=false;
    Article update_article;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int layout = intent.getIntExtra("MODE",0);

        if(layout==R.layout.activity_crud_view){
            setContentView(R.layout.activity_crud_view);
            setupUIView();
            functionView();

        }else if(layout==R.layout.activity_crud_add){
            setContentView(R.layout.activity_crud_add);
            setupUIAdd();
            functionAdd();

        }else if(layout==R.layout.activity_crud_update){
            setContentView(R.layout.activity_crud_update);
            setupUIUpdate();
            functionUpdate();
        }else{
            Intent intent1= new Intent(this, MainActivity.class);
            startActivity(intent1);
        }
    }

    private void setupUIAdd() {
        btnChoose = findViewById(R.id.btnChoose);
        imageView = findViewById(R.id.image);
        btnAdd = findViewById(R.id.btnAdd);
        mTitle = findViewById(R.id.title);
        mDes = findViewById(R.id.des);
        progressBar = findViewById(R.id.progressbar);
        presenter= new CrudPresenter();
        presenter.setView(this);
    }
    private void setupUIUpdate() {
        btnChoose = findViewById(R.id.btnChooseUp);
        imageView = findViewById(R.id.imageUp);
        btnUpdate = findViewById(R.id.btnUpdate);
        mTitle = findViewById(R.id.title);
        mDes = findViewById(R.id.des);
        progressBar = findViewById(R.id.progressbar);
        update_article= new Article();
        presenter= new CrudPresenter();
        presenter.setView(this);
        uid = getIntent().getIntExtra("UPDATE_ID",-1);
        presenter.findArticles(uid,false);
    }

    private void setupUIView() {
        imageView = findViewById(R.id.image);
        vTitle = findViewById(R.id.title);
        vDes = findViewById(R.id.des);
        presenter= new CrudPresenter();
        presenter.setView(this);

    }

    private void functionAdd() {
        progressBar.setVisibility(View.INVISIBLE);
        btnChoose.setOnClickListener(v -> openExternal());
        btnAdd.setOnClickListener(v -> addArticle());
        ImageButton back = findViewById(R.id.btnBack);
        back.setOnClickListener(v->{finish();});
    }


    private void functionView() {
        vid = getIntent().getIntExtra("VIEW_ID",-1);
        presenter.findArticles(vid,true);
        ImageButton back = findViewById(R.id.btnBack);
        back.setOnClickListener(v->{finish();});
    }

    private void functionUpdate() {
        progressBar.setVisibility(View.INVISIBLE);
        btnChoose.setOnClickListener(v -> openExternal());
        btnUpdate.setOnClickListener(v -> updateArticle());
        ImageButton back = findViewById(R.id.btnBack);
        back.setOnClickListener(v->{finish();});
    }

    Article articleupdated;
    private void updateArticle() {
        Title = mTitle.getText().toString();
        Description=  mDes.getText().toString();
        articleupdated = new Article();
        if(isUpchoosing){
            isUpdating=true;
            presenter.uploadImage(this,uri);
        }else{
            articleupdated.setImage(update_article.getImage());
            if(Title.isEmpty()||Description.isEmpty()){
                Toast.makeText(this, "please verify all input", Toast.LENGTH_SHORT).show();
            }else{
                articleupdated.setTitle(Title);
                articleupdated.setDescription(Description);
                articleupdated.setImage(imageUrl);
                presenter.updateArticles(uid,articleupdated);
            }
        }
    }

    boolean isAdding,isUpdating,isUpchoosing;
    private void addArticle() {
        Title = mTitle.getText().toString();
        Description=  mDes.getText().toString();
        if(uri!=null){
            isAdding=true;
            presenter.uploadImage(this,uri);
        }else{
            Toast.makeText(this, "please select image", Toast.LENGTH_SHORT).show();
        }
    }


    private static final String TAG = "CrudActivity";
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 0 && data != null){
            uri = data.getData();
            InputStream inputStream = null;
            try {
                inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imageView.setImageBitmap(bitmap);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        }
    }


    @AfterPermissionGranted(1)
    private void openExternal() {
        isUpchoosing=true;
        String[] perms = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        if(EasyPermissions.hasPermissions(this,perms)){
            Intent intent = new Intent(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent,0);
        }else{
            EasyPermissions.requestPermissions(
                    this,
                    "we need this permission to open your external",
                    1,
                    perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        if(EasyPermissions.somePermissionPermanentlyDenied(this,perms)){
            new AppSettingsDialog.Builder(this).build().show();
        }
    }

    @Override
    public void onAddSuccess(ArticleOneResponse article) {
        Toast.makeText(this, "add success", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(CrudActivity.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onFailure(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onFindSuccess(ArticleOneResponse article,boolean isDetail) {
        if(isDetail){
            vTitle.setText(article.getData().getTitle());
            vDes.setText(article.getData().getDescription());
            if(article.getData().getImage()==null||article.getData().getImage().isEmpty()||article.getData().getImage().equals("")){
                Picasso.get().load(R.drawable.placeholder1).centerCrop().fit().into(imageView);
            }else{
                Picasso.get().load(article.getData().getImage()).centerCrop().fit().into(imageView);
            }
        }else{
            update_article=article.getData();
            update_article.setTitle(article.getData().getTitle());
            update_article.setDescription(article.getData().getDescription());
            update_article.setImage(article.getData().getImage());

            imageUrl = update_article.getImage();
            mTitle.setText(update_article.getTitle());
            mDes.setText(update_article.getDescription());
            if(article.getData().getImage()==null||article.getData().getImage().isEmpty()||article.getData().getImage().equals("")){
                Picasso.get().load(R.drawable.placeholder1).centerCrop().fit().into(imageView);
            }else{
                Picasso.get().load(update_article.getImage()).centerCrop().fit().into(imageView);
            }
        }
    }

    @Override
    public void onUpdateSuccess(ArticleOneResponse article) {
        Toast.makeText(this, "updated success", Toast.LENGTH_SHORT).show();
        Intent intent=new Intent(CrudActivity.this,MainActivity.class);
        startActivity(intent);
    }

    @Override
    public void onUploadSuccess(String url) {
        imageUrl = url;
        if(isAdding){
            if(Title.isEmpty()||Description.isEmpty()){
                Toast.makeText(this, "please verify all input", Toast.LENGTH_SHORT).show();
            }else{
                Article article = new Article(Title,Description,imageUrl);
                presenter.addArticles(article);
                isAdding=false;
            }
        }
        if(isUpdating){
            if(Title.isEmpty()||Description.isEmpty()){
                Toast.makeText(this, "please verify all input", Toast.LENGTH_SHORT).show();
            }else{
                articleupdated.setTitle(Title);
                articleupdated.setDescription(Description);
                articleupdated.setImageUpload(imageUrl);
                presenter.updateArticles(uid,articleupdated);
                isUpdating=false;
                isUpchoosing=false;
            }
        }
    }

    @Override
    public void onShowProgress() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onHideProgress() {
        progressBar.setVisibility(View.INVISIBLE);
    }

}
