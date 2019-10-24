package com.common.imageloader.glide;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.GlideBuilder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.common.imageloader.BaseImageLoaderStrategy;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * @author pm
 */
public class GlideImageLoaderStrategy implements BaseImageLoaderStrategy<GlideImageLoaderConfig> {
    public static final int CACHE_ALL = 0;
    public static final int CACHE_NONE = 1;
    public static final int CACHE_SOURCE = 2;
    public static final int CACHE_DATA = 4;
    public static final int CACHE_AUTOMATIC = 5;

    @Override
    public void loadImage(Context ctx, GlideImageLoaderConfig config) {
        if (ctx == null) {
            throw new NullPointerException("Context is required");
        }
        if (config == null) {
            throw new NullPointerException("ImageConfigImpl is required");
        }
        if (TextUtils.isEmpty(config.getUrl())) {
            throw new NullPointerException("Url is required");
        }
        if (config.getImageView() == null) {
            throw new NullPointerException("Imageview is required");
        }

        //如果context是activity则自动使用Activity的生命周期
        GlideRequests requests = GlideApp.with(ctx);
        GlideRequest<Drawable> glideRequest = requests.load(config.getUrl());
        //缓存策略
        switch (config.getCacheStrategy()) {
            case CACHE_ALL:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.ALL);
                break;
            case CACHE_NONE:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.NONE);
                break;
            case CACHE_SOURCE:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.RESOURCE);
                break;
            case CACHE_DATA:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.DATA);
                break;
            case CACHE_AUTOMATIC:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.AUTOMATIC);
                break;
            default:
                glideRequest.diskCacheStrategy(DiskCacheStrategy.ALL);
                break;
        }

        if (config.isCrossFade()) {
            glideRequest.transition(DrawableTransitionOptions.withCrossFade());
        }

        if (config.isCropCenter()) {
            glideRequest.centerCrop();
        }

        if (config.isCropCircle()) {
            glideRequest.circleCrop();
        }

        if (config.isImageRadius()) {
            glideRequest.transform(new RoundedCorners(config.getImageRadius()));
        }

        if (config.isBlurImage()) {
            // TODO: 2018/11/2  
        }

        if (config.getTransformation() != null) {
            //glide用它来改变图形的形状
            glideRequest.transform(config.getTransformation());
        }

        if (config.getPlaceholder() != 0) {
            //设置占位符
            glideRequest.placeholder(config.getPlaceholder());
        }

        if (config.getErrorPic() != 0) {
            //设置错误的图片
            glideRequest.error(config.getErrorPic());
        }

        if (config.getFallback() != 0) {
            //设置请求 url 为空图片
            glideRequest.fallback(config.getFallback());
        }
        glideRequest.into(config.getImageView());
    }

    @Override
    public void clear(final Context ctx, GlideImageLoaderConfig config) {
        if (ctx == null) {
            throw new NullPointerException("Context is required");
        }
        if (config == null) {
            throw new NullPointerException("ImageConfigImpl is required");
        }

        if (config.getImageViews() != null && config.getImageViews().length > 0) {
            //取消在执行的任务并且释放资源
            for (ImageView imageView : config.getImageViews()) {
                GlideApp.get(ctx).getRequestManagerRetriever().get(ctx).clear(imageView);
            }
        }

        if (config.isClearDiskCache()) {
            //清除本地缓存
            Observable.just(0)
                    .observeOn(Schedulers.io())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(@NonNull Integer integer) throws Exception {
                            GlideApp.get(ctx).clearDiskCache();
                        }
                    });
        }

        if (config.isClearMemory()) {
            //清除内存缓存
            Observable.just(0)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<Integer>() {
                        @Override
                        public void accept(@NonNull Integer integer) throws Exception {
                            GlideApp.get(ctx).clearMemory();
                        }
                    });
        }

    }


    public void applyGlideOptions(Context context, GlideBuilder builder) {
    }
}
