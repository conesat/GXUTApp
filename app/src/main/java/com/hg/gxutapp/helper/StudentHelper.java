
package com.hg.gxutapp.helper;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.hg.gxutapp.staticvalue.StaticValues;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.List;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.content.Context.MODE_PRIVATE;

public class StudentHelper {
    private OkHttpClient okHttpClient;
    private Context context;

    private String sessionid;

    private String userName;
    private String password;

    public StudentHelper(Context context) {
        okHttpClient = new OkHttpClient();
        this.context = context;
        SharedPreferences preference = context.getSharedPreferences("user", MODE_PRIVATE);
        userName = preference.getString("userName", "");
        password = preference.getString("password", "");
    }

    public boolean login(final String userName, final String password, final Handler handler) {
        this.userName = userName;
        this.password = password;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {

                    int r = 1;
                    String html = getHtml(StaticValues.STUDEN_LOGIN_URL);
                    if (html.indexOf("当前用户：") > 0) {
                        String name = "";
                        String className = "";
                        String weekNumber = "";

                        r = 0;
                        SharedPreferences preferences = context.getSharedPreferences("user", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = preferences.edit();
                        editor.putString("userType", "student");
                        editor.putString("userName", userName);
                        editor.putString("password", password);


                        Document doc = Jsoup.parse(html);//解析HTML字符串返回一个Document实现
                        Elements section = doc.select("section").first().children().first().children();//查找第一个a元素
                        className = section.get(0).children().get(1).html();
                        weekNumber = section.get(1).children().get(1).html();
                        name = section.get(2).children().get(1).html();

                        editor.putString("reallyName", name);
                        editor.putString("className", className);
                        editor.putString("weekNumber", weekNumber);

                        editor.commit();//提交数据
                    }
                    Bundle b = new Bundle();
                    b.putString("succeed", r + "");
                    Message msg = new Message();
                    msg.setData(b);
                    handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        return false;
    }


    public void getMyClass(final Handler handler) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                int code = 0;
                try {
                    String html = getHtml(StaticValues.STUDEN_CLASS_URL);
                    Document doc = Jsoup.parse(html);//解析HTML字符串返回一个Document实现
                    Elements tbody = doc.select("tbody").first().children();//查找第一个a元素
                    int ii = 0, jj = 0;
                    for (int i = 1; i < tbody.size(); i++) {
                        jj = 0;
                        for (int j = 0; j < 9; j++) {
                            if (j == 1) {
                                continue;
                            } else {
                                StaticValues.MY_CLASS[ii][jj] = tbody.get(i).children().get(j).html();
                                jj++;
                            }
                        }
                        ii++;
                        System.out.println();
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                    code = 1;
                }
                Bundle b = new Bundle();
                b.putString("code", code + "");
                Message msg = new Message();
                msg.setData(b);
                handler.sendMessage(msg);
            }
        }).start();


    }


    public void getMyScore(final Handler handler) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                String jd = "";
                int code = 0;
                try {
                    Document doc = Jsoup.parse(getHtml(StaticValues.STUDEN_SCORE_URL));//解析HTML字符串返回一个Document实现
                    Elements tbody = doc.select("#biaoge").first().children();//查找第一个a元素

                    int x = 0;
                    for (int i = 1; i < tbody.size(); i++) {
                        x = 0;
                        String[] row = new String[10];
                        for (int j = 2; j < 12; j++) {
                            row[x] = tbody.get(i).children().get(j).html();
                            x++;
                        }
                        StaticValues.MY_SCORE.add(row);
                    }
                    Elements stuInfo = doc.select(".stuInfo");
                    jd = stuInfo.get(1).parent().children().get(1).html();

                } catch (IOException e) {
                    e.printStackTrace();
                    code = 1;
                }
                Bundle b = new Bundle();
                b.putString("code", code + "");
                b.putString("jd", jd);
                Message msg = new Message();
                msg.setData(b);
                handler.sendMessage(msg);
            }
        }).start();


    }


    public String getHtml(String url) throws IOException {

        FormBody formBody = new FormBody.Builder()
                .add("userName", userName)
                .add("password", password)
                .add("submit", "登入")
                .build();

        Request request = new Request.Builder()
                .url(StaticValues.STUDEN_LOGIN_URL)
                .post(formBody)
                .build();

        Response response = okHttpClient.newCall(request).execute();
        String re = response.toString();

        re = re.substring(re.indexOf(", url=") + 6, re.length() - 1);

        List cookies = response.headers().values("Set-Cookie");

        String session = cookies.get(0).toString();

        sessionid = session.substring(0, session.indexOf(";"));


        formBody = new FormBody.Builder()
                .add("userName", userName)
                .add("password", password)
                .add("submit", "登入")
                .build();

        request = new Request.Builder()
                .url(url)
                .addHeader("Cookie", sessionid)
                .post(formBody)
                .build();

        response = okHttpClient.newCall(request).execute();

        return response.body().string();
    }


}