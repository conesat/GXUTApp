package com.hg.gxutapp.helper;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.hg.gxutapp.R;
import com.hg.gxutapp.activity.LoginActivity;
import com.hg.gxutapp.activity.MainActivity;
import com.hg.gxutapp.activity.MyScoreActivity;
import com.hg.gxutapp.staticvalue.StaticValues;

import static android.content.Context.MODE_PRIVATE;

public class MyViewHelper {
    private Context context;
    private View my;
    private LinearLayout loginOut;
    private TextView name;
    private TextView classWeek;
    private TextView myClass;

    private LinearLayout myScore;


    public static Handler handler;

    public View init(final Context context) {
        this.context = context;
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        my = layoutInflater.inflate(R.layout.my_layout, null);
        name = my.findViewById(R.id.my_name);
        classWeek = my.findViewById(R.id.my_class_week);
        loginOut = my.findViewById(R.id.my_login_out);
        myClass = my.findViewById(R.id.my_class);

        myScore=my.findViewById(R.id.my_score);
        myScore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, MyScoreActivity.class));
            }
        });

        loginOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences preference = context.getSharedPreferences("user", MODE_PRIVATE);
                SharedPreferences.Editor editor = preference.edit();
                editor.putString("userName", null);
                editor.commit();
                context.startActivity(new Intent(context, LoginActivity.class));
                MainActivity.mainActivity.finish();
            }
        });
        SharedPreferences preference = context.getSharedPreferences("user", MODE_PRIVATE);
        name.setText(preference.getString("reallyName", ""));
        classWeek.setText("课程表  当前为第" + preference.getString("weekNumber", "") + "周");
        myClass.setText("我的班级-" + preference.getString("className", ""));


        handler = new Handler() {
            //这是默认的方法
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Bundle b = msg.getData();
                String code = b.getString("code");
                int ii = 0, jj = 0;
                if (code.equals("0")) {
                    LinearLayout linearLayout = my.findViewById(R.id.my_class_linear_layout);
                    for (int i = 2; i < linearLayout.getChildCount(); i += 2) {
                        jj = 0;
                        for (int j = 1; j < ((LinearLayout) linearLayout.getChildAt(i)).getChildCount(); j += 2) {
                            ((TextView) ((LinearLayout) linearLayout.getChildAt(i)).getChildAt(j)).setText(StaticValues.MY_CLASS[ii][jj]);
                            jj++;
                        }
                        ii++;
                    }
                } else {
                    Toast.makeText(context, "获取资源错误", Toast.LENGTH_LONG).show();
                }
            }
        };

        return my;
    }
}
