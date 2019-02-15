package com.hg.gxutapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hg.gxutapp.R;
import com.hg.gxutapp.adapter.holder.RecommendItemHolder;
import com.hg.gxutapp.model.Recommend;

import java.util.List;

public class RecommendItemRecyclerViewAdapter extends RecyclerView.Adapter<RecommendItemHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<Recommend> datas;

    public RecommendItemRecyclerViewAdapter(Context context, List<Recommend> datas) {
        this.context = context;
        this.datas = datas;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public RecommendItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.recommend_item, viewGroup, false);
        RecommendItemHolder myHolder = new RecommendItemHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecommendItemHolder myHolder, int i) {
        myHolder.textView.setText(datas.get(i).getText());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}

