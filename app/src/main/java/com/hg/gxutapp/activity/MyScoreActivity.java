package com.hg.gxutapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hg.gxutapp.R;
import com.hg.gxutapp.helper.StudentHelper;
import com.hg.gxutapp.staticvalue.StaticValues;

public class MyScoreActivity extends AppCompatActivity {

    private LinearLayout befor=null;

    private ImageView back;

    private ProgressDialog progressDialog;

    public static Handler handler;

    private TextView myTile;

    private LinearLayout scoreLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_score);

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

        StaticValues.MY_SCORE.clear();

        initView();


    }

    private void initView() {
        back=findViewById(R.id.my_score_back);
        myTile=findViewById(R.id.my_score_title);
        scoreLayout=findViewById(R.id.my_score_layout);

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyScoreActivity.this.finish();
            }
        });


        showProgressDialog(this,"加载中...");


        handler = new Handler() {
            //这是默认的方法
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                LayoutInflater layoutInflater=LayoutInflater.from(MyScoreActivity.this);
                Bundle b = msg.getData();
                SharedPreferences preferences = MyScoreActivity.this.getSharedPreferences("user", Context.MODE_PRIVATE);
                String jd = b.getString("jd");
                String code = b.getString("code");
                if (code.equals("0")) {
                    myTile.setText("姓名:"+preferences.getString("reallyName","")+"   学号:"+preferences.getString("userName","")+"   绩点:"+jd);
                    for (String[] scores: StaticValues.MY_SCORE){
                        int x=1;
                        View view=layoutInflater.inflate(R.layout.my_score_item_layout,null);
                        LinearLayout  linearLayout=view.findViewById(R.id.my_score_item_layout);
                        for (int i=0;i<scores.length;i++){
                            ((TextView)linearLayout.getChildAt(x)).setText(scores[i]);
                            x+=2;
                        }
                        view.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                v.setBackgroundColor(Color.argb(255,240,240,240));
                                if (befor==null){
                                    befor=(LinearLayout) v;
                                }else{
                                    befor.setBackgroundColor(Color.argb(0,0,0,0));
                                    befor=(LinearLayout) v;
                                }
                            }
                        });
                        scoreLayout.addView(view);
                    }

                } else {
                    Toast.makeText(MyScoreActivity.this, "获取资源错误", Toast.LENGTH_LONG).show();
                }
                dismissProgressDialog();
            }
        };

        (new StudentHelper(this)).getMyScore(handler);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            this.finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


    public void showProgressDialog(Context mContext, String text) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        }
        progressDialog.setMessage(text);    //设置内容
        progressDialog.setCancelable(false);//点击屏幕和按返回键都不能取消加载框
        progressDialog.show();

        //设置超时自动消失
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //取消加载框
                if (dismissProgressDialog()) {
                    //超时处理
                }
            }
        }, 60000);//超时时间60秒
    }

    public Boolean dismissProgressDialog() {
        if (progressDialog != null){
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
                return true;//取消成功
            }
        }
        return false;//已经取消过了，不需要取消
    }
}
