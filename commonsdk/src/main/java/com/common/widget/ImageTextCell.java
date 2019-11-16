package com.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.common.R;


/**
 * @author pm
 * @date 18-1-10
 */
public class ImageTextCell extends ConstraintLayout {

    private View view;
    public ImageView mIcon;
    public TextView mText;

    public ImageTextCell(Context context) {
        this(context, null);
    }

    public ImageTextCell(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ImageTextCell(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClipChildren(true);
        setFocusable(true);
        setFocusableInTouchMode(true);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.image_text_cell, this, true);
            mIcon = view.findViewById(R.id.icon);
            mText = view.findViewById(R.id.text);
        }

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.ImageTextCell, defStyleAttr, 0);
        Drawable icon = typedArray.getDrawable(R.styleable.ImageTextCell_icon);
        int iconWidth = (int) typedArray.getDimension(R.styleable.ImageTextCell_icon_width,
                dp2px(getContext(), 0));
        int iconHeight = (int) typedArray.getDimension(R.styleable.ImageTextCell_icon_height,
                dp2px(getContext(), 0));
        String text = typedArray.getString(R.styleable.ImageTextCell_text);
        int textMaxWidth = (int) typedArray.getDimension(R.styleable.ImageTextCell_text_max_width,
                dp2px(getContext(), 0));
        int textMaxHeight = (int) typedArray.getDimension(R.styleable.ImageTextCell_text_max_height,
                dp2px(getContext(), 0));
        int topMargin = (int) typedArray.getDimension(R.styleable.ImageTextCell_top_margin,
                dp2px(getContext(), 8));
        int bottomMargin = (int) typedArray.getDimension(R.styleable.ImageTextCell_bottom_margin,
                dp2px(getContext(), 8));
        int defColor = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            defColor = getResources().getColor(R.color.color_white_eighty, null);
        } else {
            defColor = getResources().getColor(R.color.color_white_eighty);
        }
        int textColor = typedArray.getColor(R.styleable.ImageTextCell_text_color, defColor);
        float textSize = typedArray.getDimension(R.styleable.ImageTextCell_text_size,
                14);
        typedArray.recycle();

        MarginLayoutParams iconParams = (MarginLayoutParams) mIcon.getLayoutParams();
        if (iconHeight != 0) {
            iconParams.height = iconHeight;
        }
        if (iconWidth != 0) {
            iconParams.width = iconWidth;
        }
        iconParams.setMargins(0, 0, 0, bottomMargin);
        mIcon.setLayoutParams(iconParams);
        mIcon.setImageDrawable(icon);

        MarginLayoutParams titleParams = (MarginLayoutParams) mText.getLayoutParams();
        titleParams.setMargins(0, topMargin, 0, 0);
        if (textMaxWidth != 0) {
            mText.setMaxWidth(textMaxWidth);
        }
        if (textMaxHeight != 0) {
            mText.setMaxHeight(textMaxHeight);
        }
        mText.setText(text);
        mText.setLayoutParams(titleParams);
        mText.setTextColor(textColor);
        mText.setTextSize(textSize);
    }

    private int dp2px(Context context, int dp) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, context.getResources().getDisplayMetrics()) + 0.5f);
    }

    @Override
    public void onHoverChanged(boolean hovered) {
        super.onHoverChanged(hovered);
        if (hovered) {
            mText.setSelected(true);
            mText.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//            mItemCardView.onHoverChanged(true);
        } else {
            mText.setEllipsize(TextUtils.TruncateAt.END);
//            mItemCardView.onHoverChanged(false);
        }
    }

    @Override
    public boolean onHoverEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_HOVER_ENTER:
                requestFocusFromTouch();
                setHovered(true);
                break;
            case MotionEvent.ACTION_HOVER_MOVE:
                break;
            case MotionEvent.ACTION_HOVER_EXIT:
                setHovered(false);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onFocusChanged(boolean gainFocus, int direction, @Nullable Rect previouslyFocusedRect) {
        super.onFocusChanged(gainFocus, direction, previouslyFocusedRect);
        setHovered(gainFocus);
    }

    @Override
    public boolean onInterceptHoverEvent(MotionEvent event) {
        return true;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return true;
    }

    public void setIcon(Object object) {
        if (object instanceof Integer) {
            int resId = (Integer) object;
            mIcon.setImageResource(resId);
        } else if (object instanceof Bitmap) {
            Bitmap bitmap = (Bitmap) object;
            mIcon.setImageBitmap(bitmap);
        } else if (object instanceof Drawable) {
            Drawable drawable = (Drawable) object;
            mIcon.setImageDrawable(drawable);
        }
    }

    public void setTitle(Object object) {
        if (object instanceof CharSequence) {
            CharSequence title = (CharSequence) object;
            mText.setText(title);
        }
    }
}
