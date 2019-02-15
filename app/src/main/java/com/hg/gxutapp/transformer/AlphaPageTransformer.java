package com.hg.gxutapp.transformer;

import android.support.v4.view.ViewCompat;
import android.view.View;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:15/6/19 上午8:41
 * 描述:
 */
public class AlphaPageTransformer extends BasePageTransformer {
    private float mMinScale = 0f;

    public AlphaPageTransformer() {
    }

    public AlphaPageTransformer(float minScale) {
        setMinScale(minScale);
    }

    @Override
    public void handleInvisiblePage(View view, float position) {
        ViewCompat.setAlpha(view, 0);
    }

    @Override
    public void handleLeftPage(View view, float position) {
        ViewCompat.setTranslationX(view, -view.getWidth() * position);
       /* ViewCompat.setPivotX(view, view.getWidth() * 0.5f);
        ViewCompat.setPivotY(view, view.getHeight() * 0.5f);
        ViewCompat.setScaleX(view, 1 + position);
        ViewCompat.setScaleY(view, 1 + position);
*/
     //   ViewCompat.setAlpha(view, mMinScale + (1 - mMinScale) * (1 + position));

        if (position < -0.95f) {
            ViewCompat.setAlpha(view, 0);
        } else {
            ViewCompat.setAlpha(view, 1);
        }
    }

    @Override
    public void handleRightPage(View view, float position) {
        ViewCompat.setTranslationX(view, -view.getWidth() * position);
      /*  ViewCompat.setPivotX(view, view.getWidth() * 0.5f);
        ViewCompat.setPivotY(view, view.getHeight() * 0.5f);
        ViewCompat.setScaleX(view, 1 - position);
        ViewCompat.setScaleY(view, 1 - position);
        */
       // ViewCompat.setAlpha(view, mMinScale + (1 - mMinScale) * (1 - position));

        if (position > 0.95f) {
            ViewCompat.setAlpha(view, 0);
        } else {
            ViewCompat.setAlpha(view, 1);
        }
    }

    public void setMinScale(float minScale) {
        if (minScale >= 0.0f && minScale <= 1.0f) {
            mMinScale = minScale;
        }
    }
}