package com.common.imageloader;

import android.content.Context;


/**
 * @author pm
 */
public interface BaseImageLoaderStrategy<T extends BaseImageLoaderConfig> {
    /**
     * 加载图片
     *
     * @param context
     * @param config
     */
    void loadImage(Context context, T config);

    /**
     * 停止加载
     *
     * @param context
     * @param config
     */
    void clear(Context context, T config);
}
