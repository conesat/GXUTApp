package com.hg.gxutapp.adapter.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hg.gxutapp.R;
import com.hg.gxutapp.view.RoundImageView;

public class HostClassItemHolder extends RecyclerView.ViewHolder {
    public RoundImageView roundImageView;
    public TextView title;
    public TextView teacher;
    private ImageView more;
    public HostClassItemHolder(@NonNull View itemView) {
        super(itemView);
        roundImageView = (RoundImageView) itemView.findViewById(R.id.host_class_img);
        title = (TextView) itemView.findViewById(R.id.host_class_title);
        teacher = (TextView) itemView.findViewById(R.id.host_class_techer);
        more = (ImageView) itemView.findViewById(R.id.host_class_more);
    }
}
