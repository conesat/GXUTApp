package com.hg.gxutapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hg.gxutapp.R;
import com.hg.gxutapp.activity.SignInActivity;
import com.hg.gxutapp.activity.WebViewActivity;
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
    public AppItemHolder onCreateViewHolder(@NonNull ViewGroup viewGroup,int i) {
        View view = layoutInflater.inflate(R.layout.app_item, viewGroup, false);
        AppItemHolder myHolder = new AppItemHolder(view);

        return myHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AppItemHolder myHolder,final int i) {
        myHolder.textView.setText(datas.get(i).getName());
        myHolder.imageView.setImageBitmap(datas.get(i).getImg());
        myHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(i);
                if (datas.get(i).getUrl().equals("signin")){
                    context.startActivity(new Intent(context, SignInActivity.class));
                }else {
                    Intent intent=new Intent(context, WebViewActivity.class);
                    intent.putExtra("title", datas.get(i).getName());
                    intent.putExtra("url", datas.get(i).getUrl());
                    context.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }
}


