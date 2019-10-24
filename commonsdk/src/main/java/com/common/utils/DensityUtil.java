
package com.common.utils;

import android.app.Activity;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;

import java.util.HashMap;
import java.util.Map;


/**
 * 常用单位转换和屏幕系统参数获取的辅助类
 *
 * @author pm
 */
public class DensityUtil {
    private static final String TAG = "DensityUtil";
    private static final boolean DEBUG = true;

    private DensityUtil() {
        /* cannot be instantiated */
        throw new UnsupportedOperationException("cannot be instantiated");
    }

    /**
     * dp转px
     *
     * @param context
     * @param dpVal
     * @return
     */
    public static int dp2px(Context context, float dpVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, context.getResources().getDisplayMetrics());
    }

    /**
     * sp转px
     *
     * @param context
     * @param spVal
     * @return
     */
    public static int sp2px(Context context, float spVal) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, context.getResources().getDisplayMetrics());
    }

    /**
     * px转dp
     *
     * @param context
     * @param pxVal
     * @return
     */
    public static float px2dp(Context context, float pxVal) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (pxVal / scale);
    }

    /**
     * px转sp
     *
     * @param pxVal
     * @return
     */
    public static float px2sp(Context context, float pxVal) {
        return (pxVal / context.getResources().getDisplayMetrics().scaledDensity);
    }

    public static Map<String, Object> densityInfo(Context context) {
        Map<String, Object> map = new HashMap<>(8);
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        int widthPixels = metrics.widthPixels;
        int heightPixels = metrics.heightPixels;
        int densityDpi = metrics.densityDpi;
        map.put("widthPixels", widthPixels);
        map.put("heightPixels", heightPixels);
        map.put("densityDpi", densityDpi);

        float xdpi = metrics.xdpi;
        float ydpi = metrics.ydpi;
        float scaledDensity = metrics.scaledDensity;
        float density = metrics.density;
        map.put("xdpi", xdpi);
        map.put("ydpi", ydpi);
        map.put("scaledDensity", scaledDensity);
        map.put("density", density);
        if (DEBUG) {
            for (Map.Entry entry : map.entrySet()) {
                Log.d(TAG, "key=" + entry.getKey() + "\t:value=" + entry.getValue());
            }
        }
        return map;
    }

    /**
     * @param context
     * @see #setCustomDensity(Context context, float designWidthPixels)
     */
    public static void setCustomDensity(Context context) {
        setCustomDensity(context, 1920f);
    }

    /**
     * 自定义dp与px的比值
     * {@link DisplayMetrics#density}
     *
     * @param context
     * @param designWidthPixels 设计稿的宽度像素值
     */
    public static void setCustomDensity(Context context, float designWidthPixels) {
        DisplayMetrics activityDisplayMetrics = context.getResources().getDisplayMetrics();
        float targetDensity = (float) activityDisplayMetrics.widthPixels / designWidthPixels;
        int targetDensityDpi = (int) (targetDensity * DisplayMetrics.DENSITY_DEFAULT);
        activityDisplayMetrics.density = targetDensity;
        activityDisplayMetrics.scaledDensity = targetDensity;
        activityDisplayMetrics.densityDpi = targetDensityDpi;
    }


    public static int getScreenHeight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

    public static int getScreenWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }

}
