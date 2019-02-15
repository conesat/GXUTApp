package com.hg.gxutapp.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hg.gxutapp.R;
import com.hg.gxutapp.adapter.holder.HostClassItemHolder;
import com.hg.gxutapp.model.ClassData;

import java.util.List;

public class HostClassItemRecyclerViewAdapter extends RecyclerView.Adapter<HostClassItemHolder> {

    private Context context;
    private LayoutInflater layoutInflater;
    private List<ClassData> datas;

    public HostClassItemRecyclerViewAdapter(Context context, List<ClassData> datas) {
        this.context = context;
        this.datas = datas;
        layoutInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public HostClassItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = layoutInflater.inflate(R.layout.host_class_item, viewGroup, false);
        HostClassItemHolder myHolder = new HostClassItemHolder(view);
        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull HostClassItemHolder myHolder, int i) {
        myHolder.title.setText(datas.get(i).getTitle());
        myHolder.teacher.setText("讲师:"+datas.get(i).getSchool()+"-"+datas.get(i).getTeacher());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}

