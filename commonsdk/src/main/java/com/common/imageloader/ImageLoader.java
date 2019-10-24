package com.common.imageloader;

import android.content.Context;

/**
 * @author pm
 */
//@Singleton
public class ImageLoader {
    private BaseImageLoaderStrategy mImageLoaderStrategy;

    public ImageLoader(BaseImageLoaderStrategy imageLoaderStrategy) {
        this.mImageLoaderStrategy = imageLoaderStrategy;
    }

    public void setImageLoaderStrategy(BaseImageLoaderStrategy imageLoaderStrategy) {
        this.mImageLoaderStrategy = imageLoaderStrategy;
    }

    /**
     * 加载图片
     *
     * @param context
     * @param config
     * @param <T>
     */
    public <T extends BaseImageLoaderConfig> void loadImage(Context context, T config) {
        mImageLoaderStrategy.loadImage(context, config);
    }

    /**
     * 停止加载或清理缓存
     *
     * @param context
     * @param config
     * @param <T>
     */
    public <T extends BaseImageLoaderConfig> void clear(Context context, T config) {
        this.mImageLoaderStrategy.clear(context, config);
    }

}
