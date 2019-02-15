package com.hg.gxutapp.helper;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hg.gxutapp.R;
import com.hg.gxutapp.activity.LoginActivity;
import com.hg.gxutapp.activity.MainActivity;
import com.hg.gxutapp.adapter.AppItemRecyclerViewAdapter;
import com.hg.gxutapp.adapter.HostClassItemRecyclerViewAdapter;
import com.hg.gxutapp.adapter.RecommendItemRecyclerViewAdapter;
import com.hg.gxutapp.model.AppData;
import com.hg.gxutapp.model.ClassData;
import com.hg.gxutapp.model.Recommend;
import com.hg.gxutapp.transformer.ZoomOutPagerTransformer;
import com.hg.gxutapp.utils.DensityUtil;
import com.hg.gxutapp.utils.ViewPagerScroller;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class MainViewHelper {
    private Context context;


    public static Handler handler;

    private View main;

    //carousel start
    private List<View> carouselViews = new ArrayList<View>();
    private ViewPager carouselViewPager;
    private LinearLayout carouselPointLayout;
    private List<ImageView> carouselPoints = new ArrayList<ImageView>();
    private boolean RUN = true;
    private int current = 0;
    private int carouselType = 0;//翻页方向
    //carousel end

    //app start
    private RecyclerView appRecyclerView;
    private List<AppData> appDatas;
    private AppItemRecyclerViewAdapter appAdapter;
    //app end

    //recommed start
    private RecyclerView recommendRecyclerView;
    private List<Recommend> recommendDatas;
    private RecommendItemRecyclerViewAdapter recommendAdapter;
    //recommend end


    //host_class start
    private RecyclerView hostClassRecyclerView;
    private List<ClassData> hostClassDatas;
    private HostClassItemRecyclerViewAdapter hostClassAdapter;
    //host_class end

    public View init(Context context) {
        this.context = context;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        main = layoutInflater.inflate(R.layout.main_layout, null);

        initAppData();
        initRecommendData();
        initHostClassData();
        initCarousel();
        return main;
    }

    private void initCarousel() {
        String[] datas = new String[]{"https://img.zcool.cn/community/01992f56d6a9e16ac7252ce6e4611b.png@1280w_1l_2o_100sh.png",
                "https://img.zcool.cn/community/017a7a569f486332f875520fdb9967.png@1280w_1l_2o_100sh.png",
                "https://img.zcool.cn/community/01bc42569e02c932f875520fbb1b24.png@1280w_1l_2o_100sh.png",
                "https://img.zcool.cn/community/010b5156976b9632f87574bef6363c.png@1280w_1l_2o_100sh.png"};

        carouselViewPager = main.findViewById(R.id.main_carousel);
        carouselPointLayout = main.findViewById(R.id.main_carousel_ponit_view);

        LayoutInflater layoutInflater = LayoutInflater.from(context);

        carouselPointLayout.removeAllViews();
        for (int i = 0; i < datas.length; i++) {
            View view = layoutInflater.inflate(R.layout.main_carousel_layout, null);
            View point = layoutInflater.inflate(R.layout.main_carousel_point_layout, null);
            ImageView imageView = view.findViewById(R.id.main_carousel_img);

            Picasso.with(context).load(datas[i]).into(imageView);

            carouselPoints.add((ImageView) point.findViewById(R.id.main_carousel_ponit));
            carouselViews.add(view);
            carouselPointLayout.addView(point);
        }

        carouselPoints.get(0).setImageResource(R.drawable.bg_r);

        //适配器
        PagerAdapter adapter = new PagerAdapter() {
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(carouselViews.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = carouselViews.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public int getCount() {
                return carouselViews.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        };

        ViewPagerScroller scroller = new ViewPagerScroller(context);
        scroller.setScrollDuration(2000);//时间越长，速度越慢。
        scroller.initViewPagerScroll(carouselViewPager);


        carouselViewPager.setAdapter(adapter);

        carouselViewPager.setPageTransformer(false, new ZoomOutPagerTransformer(0.7f));
        carouselViewPager.setPageMargin(-DensityUtil.dip2px(context.getApplicationContext(), 90));//设置viewpage两个页面间距,要使间距变小请设置负值或者在Adpter中重写getPageWidth

        carouselViewPager.setOffscreenPageLimit(3);//提前预加载3个,数量最好大于3个

        new Thread(new Runnable() {
            @Override
            public void run() {

                tag:
                while (true) {
                    for (int i = 0; i < 5; i++) {
                        if (!RUN) {
                            continue tag;
                        }
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    Bundle b = new Bundle();
                    Message msg = new Message();
                    b.putInt("current", current);
                    msg.setData(b);
                    handler.sendMessage(msg);

                }
            }
        }).start();


        carouselViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                carouselPoints.get(current).setImageResource(R.drawable.gray_r);
                carouselPoints.get(position).setImageResource(R.drawable.bg_r);
                current = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {
                if (state == 1)
                    RUN = false;
                else if (state == 0)
                    RUN = true;
            }
        });

        handler = new Handler() {
            //这是默认的方法
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle b = msg.getData();
                int current = b.getInt("current");
                carouselPoints.get(current).setImageResource(R.drawable.gray_r);
                if (carouselType == 0) {
                    if (current < carouselViews.size() - 1) {
                        current++;
                    } else {
                        current--;
                        carouselType = 1;
                    }
                } else {
                    if (current > 0) {
                        current--;
                    } else {
                        current++;
                        carouselType = 0;
                    }
                }

                carouselViewPager.setCurrentItem(current);
                carouselPoints.get(current).setImageResource(R.drawable.bg_r);
            }
        };

    }


    //初始化热门课程视图
    private void initHostClassData() {
        hostClassDatas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ClassData classData = new ClassData();
            classData.setTitle(i + "标题");
            classData.setTeacher("张三");
            classData.setSchool("计通学院");
            hostClassDatas.add(classData);
        }



        hostClassRecyclerView = main.findViewById(R.id.main_home_host_class_recview);
        hostClassAdapter = new HostClassItemRecyclerViewAdapter(context, hostClassDatas);
        hostClassRecyclerView.setAdapter(hostClassAdapter);
        hostClassRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        ViewGroup.LayoutParams lp;
        lp = hostClassRecyclerView.getLayoutParams();
        lp.height = DensityUtil.dip2px(context,hostClassDatas.size()*100);
        hostClassRecyclerView.setLayoutParams(lp);
    }

    //初始化推荐视图
    private void initRecommendData() {
        recommendDatas = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            Recommend recommend = new Recommend();
            recommend.setText(i + "名字");
            recommendDatas.add(recommend);
        }
        recommendRecyclerView = main.findViewById(R.id.main_home_recommend_recview);
        recommendAdapter = new RecommendItemRecyclerViewAdapter(context, recommendDatas);
        recommendRecyclerView.setAdapter(recommendAdapter);
        recommendRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
    }

    //初始化应用 视图
    private void initAppData() {
        appDatas = new ArrayList<>();
        AppData appData = new AppData();
        appData.setName("签到");
        appData.setUrl("signin");
        appData.setImg(BitmapFactory.decodeResource(context.getResources(), R.drawable.qiandao));
        appDatas.add(appData);

        appData = new AppData();
        appData.setImg(BitmapFactory.decodeResource(context.getResources(), R.drawable.files));
        appData.setName("文件收集");
        appData.setUrl("http://www.chinahg.top/xsp/gotoIndex");
        appDatas.add(appData);

        appData = new AppData();
        appData.setImg(BitmapFactory.decodeResource(context.getResources(), R.drawable.add));
        appData.setName("添加");
        appData.setUrl("add");
        appDatas.add(appData);

        appRecyclerView = main.findViewById(R.id.main_home_recview);
        appAdapter = new AppItemRecyclerViewAdapter(context, appDatas);
        appRecyclerView.setAdapter(appAdapter);
        if (appDatas.size()<=4){
            appRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.HORIZONTAL));
        }else {
            appRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL));
        }
    }
}
