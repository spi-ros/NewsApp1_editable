package com.example.android.newsapp1;


import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsViewHolder> {

    private ArrayList<NewsModel> newsList;
    private Context context;

    NewsAdapter(Context context, ArrayList<NewsModel> itemModels) {
        this.context = context;
        this.newsList = itemModels;
    }

    @NonNull
    @Override
    public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View myView = LayoutInflater.from(context).inflate(R.layout.element_layout, parent, false);
        return new NewsViewHolder(myView);
    }

    @Override
    public void onBindViewHolder(@NonNull final NewsViewHolder holder, int position) {
        holder.articleAuthor.setText(newsList.get(position).getAuthorName());
        holder.articleDate.setText(newsList.get(position).getDateOfCreate());
        holder.articleTitle.setText(newsList.get(position).getArticleTitle());
        holder.articleSection.setText(newsList.get(position).getSectionName());
        //get url to open in browser
        //holder.articleUrl.setText(newsList.get(position).getUrl());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("www.google.com"))); // ???.getUrl()
            }
        });


    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public void clearAllData() {
        newsList.clear();
        notifyDataSetChanged();
    }

    public void addAllData(List<NewsModel> news) {
        newsList.clear();
        newsList.addAll(news);
        notifyDataSetChanged();
    }
}
