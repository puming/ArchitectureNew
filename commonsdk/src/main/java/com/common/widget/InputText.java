package com.common.widget;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.MotionEvent;

import com.common.R;

import androidx.appcompat.widget.AppCompatEditText;

/**
 * @author pm
 * @date 2019/9/5
 * @email puming@zdsoft.cn
 */
public class InputText extends AppCompatEditText {

    private Drawable mDelIcon;

    public InputText(Context context) {
        this(context, null);
    }

    public InputText(Context context, AttributeSet attrs) {
        this(context, attrs, -1);
    }

    public InputText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setFocusable(true);
        setFocusableInTouchMode(true);
        setCompoundDrawablePadding(10);
        setGravity(Gravity.CENTER_VERTICAL);
        //给Drawable赋值
        mDelIcon = getContext().getResources().getDrawable(R.drawable.ic_del_icon, getContext().getTheme());
        //添加文本内容变化监听事件
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                setDrawable();
            }
        });
    }

    /**
     * 绘制删除图片
     */
    private void setDrawable() {
        if (length() < 1) {
            setCompoundDrawablesWithIntrinsicBounds(null, null, null, null);
        } else {
            setCompoundDrawablesWithIntrinsicBounds(null, null, mDelIcon, null);
        }
    }

    /**
     * 当触摸范围在右侧时，触发删除方法，隐藏删除图片
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mDelIcon != null && event.getAction() == MotionEvent.ACTION_UP) {
            int eventX = (int) event.getRawX();
            int eventY = (int) event.getRawY();
            Rect rect = new Rect();
            getGlobalVisibleRect(rect);
            rect.left = rect.right - 100;
            if (rect.contains(eventX, eventY)) {
                setText("");
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
