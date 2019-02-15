package com.hg.gxutapp.helper;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.hg.gxutapp.R;
import com.hg.gxutapp.activity.MusicActivity;
import com.hg.gxutapp.activity.NewsDetailActivity;
import com.hg.gxutapp.adapter.NewsAdapter;
import com.hg.gxutapp.staticvalue.StaticValues;
import com.hg.gxutapp.utils.NewsUtil;
import com.hg.gxutapp.utils.ViewPagerScroller;

import java.util.ArrayList;
import java.util.List;

public class NewsViewHelper implements View.OnClickListener {
    private Context context;
    private LinearLayout newsTabOne;
    private LinearLayout newsTabTow;
    private LinearLayout newsTabThree;
    private LinearLayout newsTabFour;
    private LinearLayout newsTabFive;
    private LinearLayout newsTabSix;
    private LinearLayout newsTabSeven;

    private LinearLayout newsChoiceOne;
    private LinearLayout newsChoiceTow;
    private LinearLayout newsChoiceThree;
    private LinearLayout newsChoiceFour;
    private LinearLayout newsChoiceFive;
    private LinearLayout newsChoiceSix;
    private LinearLayout newsChoiceSeven;

    private ListView newsListViewOne;
    private ListView newsListViewTow;
    private ListView newsListViewThree;
    private ListView newsListViewFour;
    private ListView newsListViewFive;
    private ListView newsListViewSix;
    private ListView newsListViewSeven;

    private HorizontalScrollView titleScrollView;

    private ViewPager newsViewPager;

    private List<View> mViewList = new ArrayList<>();

    public static Handler handler;

    private NewsAdapter newsOneAdapter;

    private NewsAdapter newsTowAdapter;

    private NewsAdapter newsThreeAdapter;


    private View news;

    public View init(final Context context) {
        this.context = context;
        initView();
        initData();

        return news;
    }



    private void initView() {
        handler = new Handler() {
            //这是默认的方法
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle b = msg.getData();
                String type = b.getString("type");
                String result = b.getString("result");
                if (type == "news") {
                    switch (result) {
                        case "0":
                            newsOneAdapter = new NewsAdapter(context, StaticValues.newsOne);    //创建适配器
                            newsListViewOne.setAdapter(newsOneAdapter);
                            break;
                        case "1":
                            newsTowAdapter = new NewsAdapter(context, StaticValues.newsTow);    //创建适配器
                            newsListViewTow.setAdapter(newsTowAdapter);
                            break;
                        case "2":
                            newsThreeAdapter = new NewsAdapter(context, StaticValues.newsThree);    //创建适配器
                            newsListViewThree.setAdapter(newsThreeAdapter);
                            break;

                    }
                }
            }
        };

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        news = layoutInflater.inflate(R.layout.news_layout, null);


        ImageView imageView = news.findViewById(R.id.imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, MusicActivity.class));
            }
        });

        titleScrollView = news.findViewById(R.id.titleScrollView);


        newsViewPager = (ViewPager) news.findViewById(R.id.view_pager);

        newsTabOne = (LinearLayout) news.findViewById(R.id.newsTabOne);
        newsTabTow = (LinearLayout) news.findViewById(R.id.newsTabTow);
        newsTabThree = (LinearLayout) news.findViewById(R.id.newsTabThree);
        newsTabFour = (LinearLayout) news.findViewById(R.id.newsTabFour);
        newsTabFive = (LinearLayout) news.findViewById(R.id.newsTabFive);
        newsTabSix = (LinearLayout) news.findViewById(R.id.newsTabSix);
        newsTabSeven = (LinearLayout) news.findViewById(R.id.newsTabSeven);

        newsTabOne.setOnClickListener(this);
        newsTabTow.setOnClickListener(this);
        newsTabThree.setOnClickListener(this);
        newsTabFour.setOnClickListener(this);
        newsTabFive.setOnClickListener(this);
        newsTabSix.setOnClickListener(this);
        newsTabSeven.setOnClickListener(this);

        newsChoiceOne = (LinearLayout) news.findViewById(R.id.newsChoiceOne);
        newsChoiceTow = (LinearLayout) news.findViewById(R.id.newsChoiceTow);
        newsChoiceThree = (LinearLayout) news.findViewById(R.id.newsChoiceThree);
        newsChoiceFour = (LinearLayout) news.findViewById(R.id.newsChoiceFour);
        newsChoiceFive = (LinearLayout) news.findViewById(R.id.newsChoiceFive);
        newsChoiceSix = (LinearLayout) news.findViewById(R.id.newsChoiceSix);
        newsChoiceSeven = (LinearLayout) news.findViewById(R.id.newsChoiceSeven);

        View newsOne = layoutInflater.inflate(R.layout.newsone, null);
        View newsTow = layoutInflater.inflate(R.layout.newstow, null);
        View newsThree = layoutInflater.inflate(R.layout.newsthree, null);
        View newsFour = layoutInflater.inflate(R.layout.newsfour, null);
        View newsFive = layoutInflater.inflate(R.layout.newsfive, null);
        View newsSix = layoutInflater.inflate(R.layout.newssix, null);
        View newsSeven = layoutInflater.inflate(R.layout.newsseven, null);

        newsListViewOne = (ListView) newsOne.findViewById(R.id.news_one_list);
        newsListViewTow = (ListView) newsTow.findViewById(R.id.news_tow_list);
        newsListViewThree = (ListView) newsThree.findViewById(R.id.news_three_list);
        newsListViewFour = (ListView) newsOne.findViewById(R.id.news_one_list);
        newsListViewFive = (ListView) newsOne.findViewById(R.id.news_one_list);
        newsListViewSeven = (ListView) newsOne.findViewById(R.id.news_one_list);
        newsListViewSix = (ListView) newsOne.findViewById(R.id.news_one_list);

        mViewList.add(newsOne);
        mViewList.add(newsTow);
        mViewList.add(newsThree);
        mViewList.add(newsFour);
        mViewList.add(newsFive);
        mViewList.add(newsSix);
        mViewList.add(newsSeven);

        newsListViewOne.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(context, NewsDetailActivity.class);
                intent.putExtra("url",StaticValues.newsOne.get(position).getUrl());
                context.startActivity(intent);
            }
        });

        newsListViewTow.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(context,NewsDetailActivity.class);
                intent.putExtra("url",StaticValues.newsTow.get(position).getUrl());
                context.startActivity(intent);
            }
        });

        newsListViewThree.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(context,NewsDetailActivity.class);
                intent.putExtra("url",StaticValues.newsThree.get(position).getUrl());
                context.startActivity(intent);
            }
        });


        PagerAdapter pagerAdapter = new PagerAdapter() {
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mViewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = mViewList.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public int getCount() {
                return mViewList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        };

        ViewPagerScroller scroller = new ViewPagerScroller(context);
        scroller.setScrollDuration(1000);//时间越长，速度越慢。
        scroller.initViewPagerScroll(newsViewPager);

        newsViewPager.setAdapter(pagerAdapter);
    }

    private void initData(){
        newsViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                resetNewsImg();
                switch (position) {
                    case 0:
                        titleScrollView.smoothScrollTo(0, 0);
                        newsChoiceOne.setVisibility(View.VISIBLE);
                        break;
                    case 1:
                        titleScrollView.smoothScrollTo(newsTabTow.getLeft() - newsTabOne.getWidth(), 0);
                        newsChoiceTow.setVisibility(View.VISIBLE);
                        break;
                    case 2:
                        titleScrollView.smoothScrollTo(newsTabThree.getLeft() - newsTabTow.getWidth(), 0);
                        newsChoiceThree.setVisibility(View.VISIBLE);
                        break;
                    case 3:
                        titleScrollView.smoothScrollTo(newsTabFour.getLeft() - newsTabThree.getWidth(), 0);
                        newsChoiceFour.setVisibility(View.VISIBLE);
                        break;
                    case 4:
                        titleScrollView.smoothScrollTo(newsTabFive.getLeft() - newsTabFour.getWidth(), 0);
                        newsChoiceFive.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        titleScrollView.smoothScrollTo(newsTabSix.getLeft() - newsTabFive.getWidth(), 0);
                        newsChoiceSix.setVisibility(View.VISIBLE);
                        break;
                    case 6:
                        titleScrollView.smoothScrollTo(newsTabSeven.getLeft(), 0);
                        newsChoiceSeven.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });


        NewsUtil util = new NewsUtil();
        try {
            util.pickData("http://www.gxut.edu.cn/", 0);
            util.pickData("http://www.gxut.edu.cn/", 1);
            util.pickData("http://www.gxut.edu.cn/", 2);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void resetNewsImg() {
        newsChoiceOne.setVisibility(View.GONE);
        newsChoiceTow.setVisibility(View.GONE);
        newsChoiceThree.setVisibility(View.GONE);
        newsChoiceFour.setVisibility(View.GONE);
        newsChoiceFive.setVisibility(View.GONE);
        newsChoiceSix.setVisibility(View.GONE);
        newsChoiceSeven.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        resetNewsImg();
        switch (v.getId()) {

            case R.id.newsTabOne:
                newsChoiceOne.setVisibility(View.VISIBLE);
                newsViewPager.setCurrentItem(0);
                break;
            case R.id.newsTabTow:
                newsChoiceTow.setVisibility(View.VISIBLE);
                newsViewPager.setCurrentItem(1);
                break;
            case R.id.newsTabThree:
                newsChoiceThree.setVisibility(View.VISIBLE);
                newsViewPager.setCurrentItem(2);
                break;
            case R.id.newsTabFour:
                newsChoiceFour.setVisibility(View.VISIBLE);
                newsViewPager.setCurrentItem(3);
                break;
            case R.id.newsTabFive:
                newsChoiceFive.setVisibility(View.VISIBLE);
                newsViewPager.setCurrentItem(4);
                break;
            case R.id.newsTabSix:
                newsChoiceSix.setVisibility(View.VISIBLE);
                newsViewPager.setCurrentItem(5);
                break;
            case R.id.newsTabSeven:
                newsChoiceSeven.setVisibility(View.VISIBLE);
                newsViewPager.setCurrentItem(6);
                break;
        }
    }
}
