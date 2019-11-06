package com.common.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.common.R;

/**
 * @author pm
 * @date 2019/11/4
 * @email puming@zdsoft.cn
 */
public class Tile extends ConstraintLayout {
    ViewGroup mLeadingContainer;
    ImageView mLeading;
    TextView mTitle;
    ViewGroup mTrailingContainer;
    TextView mTrailingText;
    ImageView mTrailingIcon;

    DividerMode mDividerMode = DividerMode.LACK;
    Paint mPaint;

    public Tile(Context context) {
        this(context, null);
    }

    public Tile(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public Tile(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.tile, this, true);
        mLeadingContainer = view.findViewById(R.id.tile_leading_container);
        mLeading = view.findViewById(R.id.tile_leading_icon);
        mTitle = view.findViewById(R.id.tile_title);
        mTrailingContainer = view.findViewById(R.id.tile_trailing_container);
        mTrailingText = view.findViewById(R.id.tile_trailing_text);
        mTrailingIcon = view.findViewById(R.id.tile_trailing_icon);


        setClipToPadding(false);
        setClickable(true);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setWillNotDraw(false);
        setBackgroundResource(R.drawable.tile_bg_selector);
        setMinHeight(dp2px(context, 48));
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.RED);
    }

    private int dp2px(Context context, int dp) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dp, context.getResources().getDisplayMetrics()) + 0.5f);
    }

    public ViewGroup getLeadingContainer() {
        return mLeadingContainer;
    }

    public ViewGroup getTrailingContainer() {
        return mTrailingContainer;
    }

    public Tile setLeading(int resIconId) {
        mLeading.setImageResource(resIconId);
        return this;
    }

    public Tile setTitle(CharSequence title) {
        mTitle.setText(title);
        return this;
    }

    public Tile setTrailingText(CharSequence title) {
        mTrailingText.setText(title);
        return this;
    }

    public Tile setTrailingIcon(int resIconId) {
        mTrailingIcon.setImageResource(resIconId);
        return this;
    }

    public Tile setCustomLeading(View view) {
        mLeadingContainer.removeAllViews();
        mLeadingContainer.addView(view);
        return this;
    }

    public Tile setCustomTrailing(View view) {
        mTrailingContainer.removeAllViews();
        mTrailingContainer.addView(view);
        return this;
    }

    public Tile showLeading(boolean visibility) {
        mLeadingContainer.setVisibility(visibility ? VISIBLE : GONE);
        return this;
    }

    public Tile showTrailing(boolean visibility) {
        mTrailingContainer.setVisibility(visibility ? VISIBLE : GONE);
        return this;
    }

    public Tile showTrailingText(boolean visibility) {
        mTrailingText.setVisibility(visibility ? VISIBLE : GONE);
        return this;
    }

    public Tile showTrailingIcon(boolean visibility) {
        mTrailingIcon.setVisibility(visibility ? VISIBLE : GONE);
        return this;
    }

    public Tile setDividerMode(DividerMode mode) {
        mDividerMode = mode;
        return this;
    }

    enum DividerMode {
        /**
         * 无分割线
         */
        NONE,
        /**
         * 分割线充满整个宽度
         */
        WHOLE,
        /**
         * 分割线充满右边部分
         */
        LACK
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mDividerMode == DividerMode.WHOLE) {
            canvas.drawLine(0, getHeight() - 1, getWidth(), getHeight() - 1, mPaint);
        } else if (mDividerMode == DividerMode.LACK) {
            canvas.drawLine(20, getHeight() - 1, getWidth(), getHeight() - 1, mPaint);
        }
        super.onDraw(canvas);
    }
}
