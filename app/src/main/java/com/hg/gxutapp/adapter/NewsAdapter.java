package com.hg.gxutapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hg.gxutapp.R;
import com.hg.gxutapp.model.News;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends BaseAdapter {

    private List<News> listItems;
    private LayoutInflater listContainer;     //视图容器

    private Context context;

    public NewsAdapter(Context context, List<News> listItems) {
        this.context = context;
        this.listItems = listItems;
        this.listContainer = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listItems.size();
    }

    @Override
    public Object getItem(int position) {
        return listItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            holder = new Holder();
            //获取list_message_item布局文件的视图
            convertView = listContainer.inflate(R.layout.news_item, null);
            //获取控件对象
            holder.time = (TextView) convertView.findViewById(R.id.time);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.content = (TextView) convertView.findViewById(R.id.content);
            holder.img = (ImageView) convertView.findViewById(R.id.img);
            //设置控件集到convertView
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }
        holder.time.setText("发布日期 " + listItems.get(position).getTime());
        holder.title.setText(listItems.get(position).getTitle());
        holder.content.setText(listItems.get(position).getPreviewContent());
        Picasso.with(context).load(listItems.get(position).getImgUrl()).into(holder.img);
        return convertView;
    }

    //自定义控件集合
    public final class Holder {
        public ImageView img;
        public TextView title, content, time;
    }
}
