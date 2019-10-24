package com.common.imageloader;

import android.widget.ImageView;

/**
 * @author pm
 */
public class ImageConfig {
    protected String url;
    protected ImageView imageView;
    /**
     * 占位符
     */
    protected int placeholder;
    /**
     * 错误占位符
     */
    protected int errorPic;


    public String getUrl() {
        return url;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int getPlaceholder() {
        return placeholder;
    }

    public int getErrorPic() {
        return errorPic;
    }
}