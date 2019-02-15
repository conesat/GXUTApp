package com.hg.gxutapp.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hg.gxutapp.R;
import com.hg.gxutapp.helper.MainViewHelper;
import com.hg.gxutapp.helper.MyViewHelper;
import com.hg.gxutapp.helper.NewsViewHelper;
import com.hg.gxutapp.helper.StudentHelper;
import com.hg.gxutapp.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private NoScrollViewPager mainViewPager;//不允许滑动的页面容器
    private List<View> mainViewList = new ArrayList<>();//页面
    //主页新闻按钮
    private LinearLayout mainNews;
    private ImageView mainNewsImg;
    private TextView mainNewText;
    //主页按钮
    private LinearLayout mainHome;
    private ImageView mainHomeImg;
    //主页我的界面
    private LinearLayout mainMy;
    private ImageView mainMyImg;
    private TextView mainMyText;


    private SharedPreferences preference;

    public static MainActivity mainActivity;
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window window = this.getWindow();
        //取消状态栏透明
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        //添加Flag把状态栏设为可绘制模式
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        //设置状态栏颜色
        window.setStatusBarColor(Color.argb(255,98,205,241));
        //设置系统状态栏处于可见状态
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        //让view不根据系统窗口来调整自己的布局
        ViewGroup mContentView = (ViewGroup) window.findViewById(Window.ID_ANDROID_CONTENT);
        View mChildView = mContentView.getChildAt(0);
        if (mChildView != null) {
            ViewCompat.setFitsSystemWindows(mChildView, false);
            ViewCompat.requestApplyInsets(mChildView);
        }

        initView();
        mainActivity=this;
    }

    private void initView() {
        mainNews=findViewById(R.id.main_news);
        mainNewsImg=findViewById(R.id.main_news_img);
        mainNewText=findViewById(R.id.main_news_text);

        mainHome=findViewById(R.id.main_home);
        mainHomeImg=findViewById(R.id.main_home_img);

        mainMy=findViewById(R.id.main_my);
        mainMyImg=findViewById(R.id.main_my_img);
        mainMyText=findViewById(R.id.main_my_text);

        mainNews.setOnClickListener(this);
        mainHome.setOnClickListener(this);
        mainMy.setOnClickListener(this);

        mainViewPager=(NoScrollViewPager) findViewById(R.id.main_view_pager);

        mainViewList.add(new NewsViewHelper().init(this));//新闻页面
        mainViewList.add(new MainViewHelper().init(this));//主页面
        mainViewList.add(new MyViewHelper().init(this));//我的页面

        //适配器 将页面载入ciewpager
        PagerAdapter mainpagerAdapter = new PagerAdapter() {
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(mainViewList.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = mainViewList.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public int getCount() {
                return mainViewList.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        };

        mainViewPager.setAdapter(mainpagerAdapter);

        preference=getSharedPreferences("user", MODE_PRIVATE);
        if (!preference.getString("userName","").equals("")){
            (new StudentHelper(this)).getMyClass(MyViewHelper.handler);
        }

    }



    //重置按钮状态
    private void resetMainImg() {
        mainNewsImg.setImageResource(R.drawable.news_0);
        mainNewText.setTextColor(Color.argb(255,100,100,100));

        mainMyImg.setImageResource(R.drawable.my_0);
        mainMyText.setTextColor(Color.argb(255,100,100,100));

        mainHome.setBackgroundResource(R.drawable.gray_r);
        mainHomeImg.setImageResource(R.drawable.main_home);
    }

    @Override
    public void onClick(View v) {
        resetMainImg();
        switch (v.getId()) {
            case R.id.main_news:
                mainNewsImg.setImageResource(R.drawable.news_1);
                mainNewText.setTextColor(Color.argb(255,84,200,241));
                mainViewPager.setCurrentItem(0);
                break;
            case R.id.main_home:

                mainHome.setBackgroundResource(R.drawable.bg_r);
                mainHomeImg.setImageResource(R.drawable.home);
                mainViewPager.setCurrentItem(1);
                break;
            case R.id.main_my:
                if (!preference.getString("userName","").equals("")){
                    mainMyImg.setImageResource(R.drawable.my_1);
                    mainMyText.setTextColor(Color.argb(255,84,200,241));
                    mainViewPager.setCurrentItem(2);
                }else {
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                    MainActivity.this.finish();
                }

                break;
        }
    }

}


