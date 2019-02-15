package com.hg.gxutapp.adapter.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.hg.gxutapp.R;
import com.hg.gxutapp.view.RoundImageView;

public class RecommendItemHolder extends RecyclerView.ViewHolder {
    public RoundImageView roundImageView;
    public TextView textView;
    public RecommendItemHolder(@NonNull View itemView) {
        super(itemView);
        roundImageView = (RoundImageView) itemView.findViewById(R.id.recommend_img);
        textView = (TextView) itemView.findViewById(R.id.recommend_text);
    }
}
