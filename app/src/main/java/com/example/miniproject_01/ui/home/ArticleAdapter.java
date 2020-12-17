package com.example.miniproject_01.ui.home;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.miniproject_01.R;
import com.example.miniproject_01.entity.Article;
import com.example.miniproject_01.ui.crud.CrudActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.MyViewHolder> {

    private Context context;
    private List<Article> arrayList;
    private List<Article> copyListFull;
    private OnRecyclerClickListner listener;

    public ArticleAdapter(Context context, List<Article> arrayList, OnRecyclerClickListner listener) {
        this.context = context;
        this.arrayList = arrayList;
        this.listener = listener;
        copyListFull = new ArrayList<>(arrayList);

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                . inflate(R.layout.activity_card_view,parent,false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder,final int position) {
        Article article = arrayList.get(position);
        holder.title.setText(article.getTitle());
        holder.des.setText(article.getDescription());
        holder.date.setText(article.getCreatedDate());
        Log.i(TAG, "onBindViewHolder: "+article.getImage());
        if(article.getImage() ==null||article.getImage().equals("string") ||article.getImage().isEmpty()||article.getImage().equals("")){
            holder.imageView.setImageResource(R.drawable.placeholder1);

        }else{
            Picasso.get().load(article.getImage()).fit().centerCrop().into(holder.imageView);
        }

    }

    private static final String TAG = "ArticleAdapter";

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView title, des,date;
        ImageView imageView;
        ImageButton imageButton;
        public MyViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.txt_title);
            des = itemView.findViewById(R.id.txt_des);
            date= itemView.findViewById(R.id.txt_date);
            imageView = itemView.findViewById(R.id.img);

            imageButton = itemView.findViewById(R.id.btnMore);
            imageButton.setOnClickListener(v->{
                PopupMenu popup = new PopupMenu(context,v);
                MenuInflater inflater = popup.getMenuInflater();
                inflater.inflate(R.menu.more_actions, popup.getMenu());

                popup.setOnMenuItemClickListener(item -> {
                    switch (item.getItemId()) {
                        case R.id.pop_edit:
                            Intent intent_edit = new Intent(context, CrudActivity.class);
                            int id_update = R.layout.activity_crud_update;
                            int id = arrayList.get(getAdapterPosition()).getId();
                            intent_edit.putExtra("MODE",id_update);
                            intent_edit.putExtra("UPDATE_ID",id);
                            context.startActivity(intent_edit);
                            return true;
                        case R.id.pop_delete:
                            listener.onRecyclerViewItemClicked(getAdapterPosition());
                            return true;
                        default:
                            return false;
                    }
                });
                popup.show();
            });
            /*implement onclick*/
            itemView.setOnClickListener(view -> {
                Intent intent1 = new Intent(context, CrudActivity.class);
                int id_view = R.layout.activity_crud_view;
                int id = arrayList.get(getAdapterPosition()).getId();
                intent1.putExtra("MODE",id_view);
                intent1.putExtra("VIEW_ID",id);
                context.startActivity(intent1);
            });
        }
    }

}
