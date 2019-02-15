package com.hg.gxutapp.transformer;


import android.support.v4.view.ViewPager;
import android.view.View;

/**
 * 实现ViewPager左右滑动放大缩小的效果
 */
public class ZoomOutPagerTransformer implements ViewPager.PageTransformer {
    private float MIN_SCALE = 0.70f;

    public ZoomOutPagerTransformer() {

    }

    public ZoomOutPagerTransformer(float MIN_SCALE) {
        this.MIN_SCALE = MIN_SCALE;
    }


    @Override
    public void transformPage(View page, float position) {
        if (position < -1 || position > 1) {
            page.setScaleX(MIN_SCALE);
            page.setScaleY(MIN_SCALE);
        } else if (position <= 1) { // [-1,1]
            float scaleFactor = Math.max(MIN_SCALE, 1 - Math.abs(position));
            if (position < 0) {
                float scaleX = 1 + 0.3f * position;
                page.setScaleX(scaleX);
                page.setScaleY(scaleX);
            } else {
                float scaleX = 1 - 0.3f * position;
                page.setScaleX(scaleX);
                page.setScaleY(scaleX);
            }
        }
    }

}