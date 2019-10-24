package com.common.imageloader.glide;

import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.common.imageloader.BaseImageLoaderConfig;

/**
 * @author pm
 */
public class GlideImageLoaderConfig extends BaseImageLoaderConfig {
    /**
     * 0对应DiskCacheStrategy.all,
     * 1对应DiskCacheStrategy.NONE,
     * 2对应DiskCacheStrategy.SOURCE,
     * 3对应DiskCacheStrategy.RESULT
     */
    private int cacheStrategy;
    /**
     * 请求 url 为空,则使用此图片作为占位符
     */
    private int fallback;
    /**
     * glide用它来改变图形的形状
     */
    private BitmapTransformation transformation;
    private ImageView[] imageViews;
    /**
     * 清理内存缓存
     */
    private boolean isClearMemory;
    /**
     * 清理本地缓存
     */
    private boolean isClearDiskCache;
    private Drawable placeholderDrawable;
    private int resizeX;
    private boolean isCropCenter;
    private boolean isCropCircle;
    private boolean isFitCenter;
    /**
     * 图片格式
     */
    private DecodeFormat format;
    private int resizeY;
    /**
     * 图片每个圆角的大小
     */
    private int imageRadius;
    /**
     * 高斯模糊值, 值越大模糊效果越大
     */
    private int blurValue;
    /**
     * 是否使用淡入淡出过渡动画
     */
    private boolean isCrossFade;

    private GlideImageLoaderConfig(Builder builder) {
        this.url = builder.url;
        this.imageView = builder.imageView;
        this.placeholder = builder.placeholder;
        this.placeholderDrawable = builder.placeholderDrawable;
        this.errorPic = builder.errorPic;
        this.fallback = builder.fallback;
        this.cacheStrategy = builder.cacheStrategy;
        this.transformation = builder.transformation;
        this.imageViews = builder.imageViews;
        this.isClearMemory = builder.isClearMemory;
        this.isClearDiskCache = builder.isClearDiskCache;
        this.resizeX = builder.resizeX;
        this.resizeY = builder.resizeY;
        this.isCropCenter = builder.isCropCenter;
        this.isCropCircle = builder.isCropCircle;
        this.format = builder.format;
        this.isFitCenter = builder.isFitCenter;
        this.isCrossFade = builder.isCrossFade;
        this.imageRadius = builder.imageRadius;
        this.blurValue = builder.blurValue;
    }

    public int getCacheStrategy() {
        return this.cacheStrategy;
    }

    public BitmapTransformation getTransformation() {
        return this.transformation;
    }

    public ImageView[] getImageViews() {
        return this.imageViews;
    }

    public boolean isClearMemory() {
        return this.isClearMemory;
    }

    public boolean isClearDiskCache() {
        return this.isClearDiskCache;
    }

    public int getFallback() {
        return this.fallback;
    }

    public Drawable getPlaceHolderDrawble() {
        return this.placeholderDrawable;
    }

    public int getResizeX() {
        return this.resizeX;
    }

    public int getResizeY() {
        return this.resizeY;
    }

    public boolean isCropCenter() {
        return this.isCropCenter;
    }

    public boolean isCropCircle() {
        return this.isCropCircle;
    }

    public DecodeFormat decodeFormat() {
        return this.format;
    }

    public boolean isFitCenter() {
        return this.isFitCenter;
    }

    public boolean isCrossFade() {
        return this.isCrossFade;
    }

    public int getBlurValue() {
        return this.blurValue;
    }

    public boolean isBlurImage() {
        return this.blurValue > 0;
    }

    public int getImageRadius() {
        return this.imageRadius;
    }

    public boolean isImageRadius() {
        return this.imageRadius > 0;
    }

    public static Builder builder() {
        return new Builder();
    }


    public static final class Builder {
        private int resizeX;
        private String url;
        private ImageView imageView;
        private int placeholder;
        private Drawable placeholderDrawable;
        private int errorPic;
        /**
         * 请求 url 为空,则使用此图片作为占位符
         */
        private int fallback;
        /**
         * 0对应DiskCacheStrategy.all,
         * 1对应DiskCacheStrategy.NONE,
         * 2对应DiskCacheStrategy.SOURCE,
         * 3对应DiskCacheStrategy.RESULT
         */
        private int cacheStrategy;
        /**
         * 图片每个圆角的大小
         */
        private int imageRadius;
        /**
         * 高斯模糊值, 值越大模糊效果越大
         */
        private int blurValue;
        /**
         * glide用它来改变图形的形状
         */
        private BitmapTransformation transformation;
        private ImageView[] imageViews;
        /**
         * 清理内存缓存
         */
        private boolean isClearMemory;
        /**
         * 清理本地缓存
         */
        private boolean isClearDiskCache;
        /**
         * 裁剪居中
         */
        private boolean isCropCenter;
        private boolean isCropCircle;
        /**
         * 是否使用淡入淡出过渡动画
         */
        private boolean isCrossFade;
        public DecodeFormat format;
        public boolean isFitCenter;
        private int resizeY;

        private Builder() {
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder placeholder(int placeholder) {
            this.placeholder = placeholder;
            return this;
        }

        public Builder errorPic(int errorPic) {
            this.errorPic = errorPic;
            return this;
        }

        public Builder fallback(int fallback) {
            this.fallback = fallback;
            return this;
        }

        public Builder imageView(ImageView imageView) {
            this.imageView = imageView;
            return this;
        }

        public Builder cacheStrategy(int cacheStrategy) {
            this.cacheStrategy = cacheStrategy;
            return this;
        }

        public Builder imageRadius(int imageRadius) {
            this.imageRadius = imageRadius;
            return this;
        }

        public Builder blurValue(int blurValue) { //blurValue 建议设置为 15
            this.blurValue = blurValue;
            return this;
        }

        public Builder isCrossFade(boolean isCrossFade) {
            this.isCrossFade = isCrossFade;
            return this;
        }

        public Builder transformation(BitmapTransformation transformation) {
            this.transformation = transformation;
            return this;
        }

        public Builder imageViews(ImageView... imageViews) {
            this.imageViews = imageViews;
            return this;
        }

        public Builder isClearMemory(boolean isClearMemory) {
            this.isClearMemory = isClearMemory;
            return this;
        }

        public Builder isClearDiskCache(boolean isClearDiskCache) {
            this.isClearDiskCache = isClearDiskCache;
            return this;
        }

        public Builder placeholderDrawble(Drawable placeholderDrawble) {
            this.placeholderDrawable = placeholderDrawble;
            return this;
        }

        public Builder resize(int resizeX, int resizeY) {
            this.resizeX = resizeX;
            this.resizeY = resizeY;
            return this;
        }

        public Builder isCropCenter(boolean isCropCenter) {
            this.isCropCenter = isCropCenter;
            return this;
        }

        public Builder isCropCircle(boolean isCropCircle) {
            this.isCropCircle = isCropCircle;
            return this;
        }

        public Builder setDecodeFormate(DecodeFormat decodeFormate) {
            format = decodeFormate;
            return this;
        }

        public Builder isFitCenter(boolean isFitCenter) {
            this.isFitCenter = isFitCenter;
            return this;
        }

        public GlideImageLoaderConfig build() {
            return new GlideImageLoaderConfig(this);
        }
    }
}
