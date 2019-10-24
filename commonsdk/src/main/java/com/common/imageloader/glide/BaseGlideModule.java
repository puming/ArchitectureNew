package com.common.imageloader.glide;

import android.content.Context;
import android.util.Log;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.annotation.GlideModule;
import com.bumptech.glide.load.engine.bitmap_recycle.LruBitmapPool;
import com.bumptech.glide.load.engine.cache.InternalCacheDiskCacheFactory;
import com.bumptech.glide.load.engine.cache.LruResourceCache;
import com.bumptech.glide.load.engine.cache.MemorySizeCalculator;
import com.bumptech.glide.module.AppGlideModule;

/**
 * @author pm
 * @Description 统一管理glide内存大小
 */
@GlideModule
public class BaseGlideModule extends AppGlideModule {
    private static final String TAG = BaseGlideModule.class.getSimpleName();

    @Override
    public void applyOptions(Context context, GlideBuilder builder) {
        Log.i(TAG, "BaseGlideModule applyOptions");
        //Glide官方写法
        //内存缓存
        MemorySizeCalculator calculator = new MemorySizeCalculator.Builder(context)
                .setMemoryCacheScreens(2)
                .build();
        int memoryCacheSize = calculator.getMemoryCacheSize();
        Log.i(TAG, "ram memory size:" + memoryCacheSize);
        builder.setMemoryCache(new LruResourceCache(memoryCacheSize));

        //bitmap池
        MemorySizeCalculator calculatorPool = new MemorySizeCalculator.Builder(context)
                .setBitmapPoolScreens(3)
                .build();
        int bitmapPoolSize = calculatorPool.getBitmapPoolSize();
        Log.i(TAG, "bitmapPool size:" + bitmapPoolSize);
        builder.setBitmapPool(new LruBitmapPool(bitmapPoolSize));

        //磁盘缓存 100 MB
        int diskCacheSizeBytes = 1024 * 1024 * 100;
        builder.setDiskCache(new InternalCacheDiskCacheFactory(context, diskCacheSizeBytes));


        //30M
        //int memoryCacheSizeBytes = 1024 * 1024 * 30;
        //builder.setMemoryCache(new LruResourceCache(memoryCacheSizeBytes));

        //bitmap池
        //int bitmapPoolSizeBytes = 1024 * 1024 * 30;
        //builder.setBitmapPool(new LruBitmapPool(bitmapPoolSizeBytes));
    }
}
