package com.common.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
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
public class OptimalAppItemView extends ConstraintLayout {

    private View view;
    public ImageView mIcon;
    public TextView mTitle;

    public OptimalAppItemView(Context context) {
        this(context, null);
    }

    public OptimalAppItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OptimalAppItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setClipChildren(false);
        setFocusable(true);
        setFocusableInTouchMode(true);
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.optimal_app_item_view, this, true);
            mIcon = view.findViewById(R.id.item_card_view);
            mTitle = view.findViewById(R.id.tv_title);
        }

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.OptimalAppItemView, defStyleAttr, 0);
        int iconWidth = (int) typedArray.getDimension(R.styleable.OptimalAppItemView_icon_width,
                48);
        int iconHeight = (int) typedArray.getDimension(R.styleable.OptimalAppItemView_icon_height,
                48);
        int textWidth = (int) typedArray.getDimension(R.styleable.OptimalAppItemView_text_width,
                48);
        int textHeight = (int) typedArray.getDimension(R.styleable.OptimalAppItemView_text_height, LayoutParams.WRAP_CONTENT);
        int topMargin = (int) typedArray.getDimension(R.styleable.OptimalAppItemView_top_margin,
                6);
        int bottomMargin = (int) typedArray.getDimension(R.styleable.OptimalAppItemView_bottom_margin,
                6);
        int defColor = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            defColor = getResources().getColor(R.color.color_white_eighty, null);
        } else {
            defColor = getResources().getColor(R.color.color_white_eighty);
        }
        int textColor = typedArray.getColor(R.styleable.OptimalAppItemView_text_color, defColor);
        float textSize = (int) typedArray.getDimension(R.styleable.OptimalAppItemView_text_size,
                24);
        typedArray.recycle();

        MarginLayoutParams iconParams = (MarginLayoutParams) mIcon.getLayoutParams();
        iconParams.height = iconHeight;
        iconParams.width = iconWidth;
        iconParams.setMargins(0, 0, 0, bottomMargin);
        mIcon.setLayoutParams(iconParams);

        MarginLayoutParams titleParams = (MarginLayoutParams) mTitle.getLayoutParams();
        titleParams.height = textHeight;
        titleParams.width = textWidth;
        titleParams.setMargins(0, topMargin, 0, 0);
        mTitle.setLayoutParams(titleParams);
        mTitle.setTextColor(textColor);
        mTitle.setTextSize(textSize);
    }

    @Override
    public void onHoverChanged(boolean hovered) {
        super.onHoverChanged(hovered);
        if (hovered) {
            mTitle.setSelected(true);
            mTitle.setEllipsize(TextUtils.TruncateAt.MARQUEE);
//            mItemCardView.onHoverChanged(true);
        } else {
            mTitle.setEllipsize(TextUtils.TruncateAt.END);
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
            mTitle.setText(title);
        }
    }
}
