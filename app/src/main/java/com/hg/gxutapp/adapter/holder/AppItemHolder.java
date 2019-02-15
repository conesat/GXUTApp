package com.hg.gxutapp.adapter.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hg.gxutapp.R;

public  class AppItemHolder extends RecyclerView.ViewHolder {
    public ImageView imageView;
    public TextView textView;
    public AppItemHolder(@NonNull View itemView) {
        super(itemView);
        imageView = (ImageView) itemView.findViewById(R.id.app_img);
        textView = (TextView) itemView.findViewById(R.id.app_name);
    }
}
