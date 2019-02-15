package com.hg.gxutapp.utils;

import android.os.Bundle;
import android.os.Message;

import com.hg.gxutapp.activity.NewsDetailActivity;
import com.hg.gxutapp.helper.NewsViewHelper;
import com.hg.gxutapp.model.News;
import com.hg.gxutapp.staticvalue.StaticValues;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NewsUtil {
    private OkHttpClient okHttpClient;

    /**
     * 单例工具类
     */
    public NewsUtil() {
        okHttpClient = new OkHttpClient();
    }

    public void getDetail(final String url, final int w) {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        final Call call = okHttpClient.newCall(request);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String html = "";
                    Response response = call.execute();
                    String re = response.body().string();
                    if (re != null) {
                        html = getBodyDetail(re, w);
                    }
                    //发送消息给ＵＩ线程的handle,消息里面包含结果，和消息type，
                    Bundle b = new Bundle();
                    b.putString("type", "news");
                    b.putString("html", html);
                    Message msg = new Message();
                    msg.setData(b);
                    NewsDetailActivity.handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }


    /*
     * 爬取网页信息
     */
    public void pickData(final String url, final int w) {
        final Request request = new Request.Builder()
                .url(url)
                .build();
        final Call call = okHttpClient.newCall(request);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = call.execute();
                    String re = response.body().string();
                    if (re != null) {
                        switch (w) {
                            case 0:
                                StaticValues.newsOne = getOne(re, url, "Newsgundong");
                            case 1:
                                StaticValues.newsTow = getOne(re, url, "News2gundong");
                            case 2:
                                StaticValues.newsThree = getOne(re, url, "News3gundong");
                            case 3:
                                //  return getOne(EntityUtils.toString(entity));
                            case 4:
                                //   return getOne(EntityUtils.toString(entity));
                            case 5:
                                //  return getOne(EntityUtils.toString(entity));
                            case 6:
                                //   return getOne(EntityUtils.toString(entity));

                        }

                    }
                    //发送消息给ＵＩ线程的handle,消息里面包含结果，和消息type，
                    Bundle b = new Bundle();
                    b.putString("type", "news");
                    b.putString("result", w + "");
                    Message msg = new Message();
                    msg.setData(b);
                    NewsViewHelper.handler.sendMessage(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    private String getBodyDetail(String html, int w) {
        String rehtml = StaticValues.html;
        Element body = Jsoup.parse(html).body();
        Element element = body.getElementsByClass("contBody").first();
        String title = element.getElementsByTag("table").first().child(0).child(0).child(0).html();
        String title2 = element.getElementsByTag("table").first().child(0).child(3).child(0).html();
        element.getElementsByTag("table").remove();


        Elements esd = element.getElementsByTag("img");
        Iterator<Element> iterator = esd.iterator();
        while (iterator.hasNext())
        {
            Element etemp = iterator.next();
            etemp.removeAttr("style");
            etemp.attr("style", "width: 100%; height: auto;");
        }
        String re = body.getElementsByClass("contBody").first().html();
        re = re.replaceAll("src=\"/public/", "src=\"http://www.gxut.edu.cn/public/");
        //System.out.println(re);
        rehtml = rehtml.replace("NEWS_ONE", title);
        rehtml = rehtml.replace("NEWS_TOW", title2);
        rehtml = rehtml.replace("NEWS_CONTENT", re);
        return rehtml;
    }

    private static List<News> getOne(String html, String url, String id) {
        String imgUrl = "", title = "", content = "", previewContent = "", time = "", nodeurl = "";
        Element body = Jsoup.parse(html).body();
        List<News> list = new ArrayList<>();
        Element eLunarDate = body.getElementById(id);
        boolean start = false;
        for (Element element : eLunarDate.getElementsByTag("div")) {
            if (!start) {
                start = true;
            } else {
                imgUrl = "";
                title = "";
                content = "";
                previewContent = "";
                time = "";
                nodeurl = "";
                News news = new News();
                imgUrl = url + element.getElementsByTag("a").first().getElementsByTag("img").first().attr("src");
                title = element.getElementsByTag("h4").first().getElementsByTag("a").first().html();
                previewContent = element.getElementsByTag("p").first().html();
                previewContent = previewContent.substring(0, previewContent.indexOf("<span"));
                time = element.getElementsByTag("p").first().getElementsByTag("span").html();
                time = time.substring(1, time.length() - 1);
                nodeurl = url + element.getElementsByTag("a").first().attr("href");
                news.setContent(content);
                news.setImgUrl(imgUrl);
                news.setPreviewContent(previewContent);
                news.setTime(time);
                news.setTitle(title);
                news.setUrl(nodeurl);
                list.add(news);
            }
        }
        return list;
    }

}
