package com.hg.gxutapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hg.gxutapp.R;
import com.hg.gxutapp.adapter.holder.AppItemHolder;
import com.hg.gxutapp.model.AppData;

import java.util.List;

public class AppItemRecyclerViewAdapter extends RecyclerView.Adapter<AppItemHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<AppData> datas;

    public AppItemRecyclerViewAdapter(Context context, List<AppData> datas) {
        this.context = context;
        this.datas = datas;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public AppItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.app_item, viewGroup, false);
        AppItemHolder myHolder = new AppItemHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AppItemHolder myHolder, int i) {
        myHolder.textView.setText(datas.get(i).getName());
        myHolder.imageView.setImageBitmap(datas.get(i).getImg());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}


