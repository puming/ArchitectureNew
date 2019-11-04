package com.common.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

/**
 * @author pm
 * @date 2019/11/4
 * @email puming@zdsoft.cn
 */
public class Tile extends LinearLayout {
    ImageView mLeading;
    ImageView mTrailing;
    TextView mTitle;

    public Tile(Context context) {
        this(context, null);
    }

    public Tile(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public Tile(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, -1);
    }

    public Tile(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }


}
