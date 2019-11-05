package com.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.common.R;

import androidx.constraintlayout.widget.ConstraintLayout;

/**
 * @author pm
 * @date 2019/6/17
 * @email puming@zdsoft.cn
 */
public class AppBar extends ConstraintLayout {
    public static final int DEFAULT_LEFT_ICON = R.drawable.ic_back;
    public static final int DEFAULT_RIGHT_ICON = R.drawable.ic_logout;

    private ImageView mAppbarBackIcon;
    private TextView mAppbarBackText;
    private LinearLayout mAppbarLeftContainer;
    private TextView mAppbarTitle;
    private TextView mAppbarMenuText;
    private ImageView mAppbarMenuIcon;
    private FrameLayout mAppbarRightContainer;

    public AppBar(Context context) {
        this(context, null);
    }

    public AppBar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AppBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.appbar, this, true);
        mAppbarBackIcon = (ImageView) view.findViewById(R.id.appbar_back_icon);
        mAppbarBackText = (TextView) view.findViewById(R.id.appbar_back_text);
        mAppbarLeftContainer = (LinearLayout) view.findViewById(R.id.appbar_left_container);
        mAppbarTitle = (TextView) view.findViewById(R.id.appbar_title);
        mAppbarMenuText = (TextView) view.findViewById(R.id.appbar_menu_text);
        mAppbarMenuIcon = (ImageView) view.findViewById(R.id.appbar_menu_icon);
        mAppbarRightContainer = (FrameLayout) view.findViewById(R.id.appbar_right_container);
        //obtain attr
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.AppBar, defStyleAttr, R.style.AppBar);
        boolean showRight = typedArray.getBoolean(R.styleable.AppBar_showRight, true);
        mAppbarRightContainer.setVisibility(showRight ? VISIBLE : GONE);
        boolean showLeft = typedArray.getBoolean(R.styleable.AppBar_showLeft, true);
        mAppbarLeftContainer.setVisibility(showLeft ? VISIBLE : GONE);
        String backText = typedArray.getString(R.styleable.AppBar_backText);
        mAppbarBackText.setText(backText);
        Drawable backIcon = typedArray.getDrawable(R.styleable.AppBar_backIcon);
        mAppbarBackIcon.setImageDrawable(backIcon);
        String titleText = typedArray.getString(R.styleable.AppBar_android_text);
        mAppbarTitle.setText(titleText);
        String menuText = typedArray.getString(R.styleable.AppBar_menuText);
        mAppbarMenuText.setText(menuText);
        Drawable drawable = typedArray.getDrawable(R.styleable.AppBar_menuIcon);
        mAppbarMenuIcon.setImageDrawable(drawable);
        typedArray.recycle();
    }


    public ViewGroup getAppbarLeftContainer() {
        return mAppbarLeftContainer;
    }

    public ViewGroup getAppbarRightContainer() {
        return mAppbarRightContainer;
    }

    public AppBar setAppbarBackIcon(int resId) {
        mAppbarBackIcon.setImageResource(resId);
        return this;
    }

    public AppBar setAppbarBackText(CharSequence text) {
        mAppbarBackText.setText(text);
        return this;
    }

    public AppBar setAppbarTitle(CharSequence text) {
        mAppbarTitle.setText(text);
        return this;
    }

    public AppBar setAppbarMenuText(CharSequence text) {
        mAppbarMenuText.setText(text);
        return this;
    }

    public AppBar setAppbarMenuIcon(int resId) {
        mAppbarMenuIcon.setBackgroundResource(resId);
        return this;
    }

    /**
     * menuIcon 与 menuText 显示互斥
     *
     * @param show
     * @return
     */
    public AppBar showAppbarMenuIcon(boolean show) {
        if (show) {
            mAppbarMenuIcon.setVisibility(VISIBLE);
            mAppbarMenuText.setVisibility(GONE);
        } else {
            mAppbarMenuIcon.setVisibility(GONE);
            mAppbarMenuText.setVisibility(VISIBLE);
        }
        return this;
    }

    public AppBar showAppbarTitle(boolean show) {
        if (show) {
            mAppbarTitle.setVisibility(VISIBLE);
        } else {
            mAppbarTitle.setVisibility(GONE);
        }
        return this;
    }

    public AppBar showAppbarBackIcon(boolean show) {
        if (show) {
            mAppbarBackIcon.setVisibility(VISIBLE);
        } else {
            mAppbarBackIcon.setVisibility(GONE);
        }
        return this;
    }

    public AppBar showAppbarBackText(boolean show) {
        if (show) {
            mAppbarBackText.setVisibility(VISIBLE);
        } else {
            mAppbarBackText.setVisibility(GONE);
        }
        return this;
    }

    public AppBar showAppbarRightContainer(boolean show) {
        if (show) {
            mAppbarRightContainer.setVisibility(VISIBLE);
        } else {
            mAppbarRightContainer.setVisibility(GONE);
        }
        return this;
    }

    public AppBar showAppbarLeftContainer(boolean show) {
        if (show) {
            mAppbarLeftContainer.setVisibility(VISIBLE);
        } else {
            mAppbarLeftContainer.setVisibility(GONE);
        }
        return this;
    }
}
