package com.hg.gxutapp.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hg.gxutapp.R;
import com.hg.gxutapp.helper.StudentHelper;
import com.hg.gxutapp.utils.DensityUtil;
import com.hg.gxutapp.view.NoScrollViewPager;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    //加载框变量
    private ProgressDialog progressDialog;

    private NoScrollViewPager loginViewPager;

    private List<View> views = new ArrayList<View>();

    private LinearLayout home;

    private LinearLayout student;
    private ImageView studentImg;
    private TextView studentText;
    private LinearLayout teacher;
    private ImageView teacherImg;
    private TextView teacherText;
    private int loginType = 0;//0学生 1教师

    //student
    private EditText studentUserName;
    private EditText studentPassword;

    //student end

    //teacher
    private EditText teacherUserName;
    private EditText teacherPassword;

    //teacher end

    private StudentHelper studentHelper;

    public static Handler handler;

    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            WindowManager.LayoutParams lp = getWindow().getAttributes();
            lp.layoutInDisplayCutoutMode = WindowManager.LayoutParams.LAYOUT_IN_DISPLAY_CUTOUT_MODE_DEFAULT;
            getWindow().setAttributes(lp);
        } else if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            //设置让应用主题内容占据状态栏和导航栏
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            //设置状态栏和导航栏颜色为透明
            getWindow().setStatusBarColor(Color.TRANSPARENT);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
        }

        setContentView(R.layout.activity_login);
        studentHelper = new StudentHelper(this);
        initView();

    }

    private void initView() {
        student = findViewById(R.id.login_student);
        studentImg = findViewById(R.id.login_student_img);
        studentText = findViewById(R.id.login_student_text);

        teacher = findViewById(R.id.login_teacher);
        teacherImg = findViewById(R.id.login_teacher_img);
        teacherText = findViewById(R.id.login_teacher_text);

        student.setOnClickListener(this);
        teacher.setOnClickListener(this);

        home = findViewById(R.id.home);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, MainActivity.class));
                LoginActivity.this.finish();
            }
        });

        loginViewPager = findViewById(R.id.login_view_pager);
        LayoutInflater layoutInflater = LayoutInflater.from(this);

        View student = layoutInflater.inflate(R.layout.login_student_layout, null);
        View teacher = layoutInflater.inflate(R.layout.login_teacher_layout, null);

        studentUserName = student.findViewById(R.id.login_student_name);
        studentPassword = student.findViewById(R.id.login_student_password);
        login = findViewById(R.id.login_button);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showProgressDialog(LoginActivity.this, "正在登陆...");
                if (loginType == 0) {
                    Pattern pattern = Pattern.compile("[0-9]*");
                    Matcher isNum = pattern.matcher(studentUserName.getText());
                    if (!isNum.matches() || studentUserName.getText().length() < 6 || studentUserName.getText().length() > 20) {
                        Toast.makeText(LoginActivity.this, "非法学号请检查", Toast.LENGTH_LONG).show();
                        dismissProgressDialog();
                    } else if (studentPassword.getText().length() < 6) {
                        Toast.makeText(LoginActivity.this, "密码最少6位", Toast.LENGTH_LONG).show();
                        dismissProgressDialog();
                    } else {
                        studentHelper.login(studentUserName.getText().toString(), studentPassword.getText().toString(), handler);
                    }
                } else {
                    Toast.makeText(LoginActivity.this, "未开放", Toast.LENGTH_LONG).show();
                    dismissProgressDialog();
                }

            }
        });

        views.add(student);
        views.add(teacher);

        PagerAdapter pagerAdapter = new PagerAdapter() {
            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView(views.get(position));
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = views.get(position);
                container.addView(view);
                return view;
            }

            @Override
            public int getCount() {
                return views.size();
            }

            @Override
            public boolean isViewFromObject(View view, Object object) {
                return view == object;
            }
        };


        // loginViewPager.setPageTransformer(false, new AlphaPageTransformer());
        loginViewPager.setAdapter(pagerAdapter);

        handler = new Handler() {
            //这是默认的方法
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle b = msg.getData();
                String succeed = b.getString("succeed");
                dismissProgressDialog();
                if (succeed.equals("0")) {
                    Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                    LoginActivity.this.finish();
                } else {
                    Toast.makeText(LoginActivity.this, "账号或密码错误", Toast.LENGTH_LONG).show();
                }
            }
        };
    }

    @Override
    public void onClick(View v) {
        ViewGroup.LayoutParams lp;
        resetImg();
        switch (v.getId()) {
            case R.id.login_teacher:
                teacher.setBackgroundResource(R.drawable.bg_r);
                lp = teacherImg.getLayoutParams();
                lp.width = DensityUtil.dip2px(this, 25);
                lp.height = DensityUtil.dip2px(this, 25);
                teacherImg.setLayoutParams(lp);
                teacherText.setTextSize(DensityUtil.dip2sp(this, 14));
                loginViewPager.setCurrentItem(1);
                loginType = 1;
                break;
            case R.id.login_student:
                student.setBackgroundResource(R.drawable.bg_r);
                lp = studentImg.getLayoutParams();
                lp.width = DensityUtil.dip2px(this, 25);
                lp.height = DensityUtil.dip2px(this, 25);
                studentImg.setLayoutParams(lp);
                studentText.setTextSize(DensityUtil.dip2sp(this, 14));
                loginViewPager.setCurrentItem(0);
                loginType = 0;
                break;
        }
    }

    private void resetImg() {
        ViewGroup.LayoutParams lp;
        student.setBackground(null);
        lp = studentImg.getLayoutParams();
        lp.width = DensityUtil.dip2px(this, 20);
        lp.height = DensityUtil.dip2px(this, 20);
        studentImg.setLayoutParams(lp);
        studentText.setTextSize(DensityUtil.dip2sp(this, 12));

        teacher.setBackground(null);
        lp = teacherImg.getLayoutParams();
        lp.width = DensityUtil.dip2px(this, 20);
        lp.height = DensityUtil.dip2px(this, 20);
        teacherImg.setLayoutParams(lp);
        teacherText.setTextSize(DensityUtil.dip2sp(this, 12));
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
        }, 10000);//超时时间60秒
    }

    public Boolean dismissProgressDialog() {
        if (progressDialog != null) {
            if (progressDialog.isShowing()) {
                progressDialog.dismiss();
                return true;//取消成功
            }
        }
        return false;//已经取消过了，不需要取消
    }

}
