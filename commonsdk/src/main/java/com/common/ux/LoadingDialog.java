package com.common.ux;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.widget.TextView;

import java.util.Objects;

/**
 * @author pm
 * @date 2019/2/21
 * @email puming@zdsoft.cn
 */
public class LoadingDialog extends Dialog {
    private TextView mTextView;
    public static final int STRIP_SHAPE = 1;
    public static final int SQUARE_SHAPE = 2;

    public LoadingDialog(Context context) {
        this(context, SQUARE_SHAPE);
    }

    /**
     *
     * @param context
     * @param style STRIP_SHAPE or SQUARE_SHAPE
     */
    public LoadingDialog(Context context, int style) {
        super(context);
        //设置对话框背景透明
        Objects.requireNonNull(getWindow()).setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        int content = style == STRIP_SHAPE ? com.common.R.layout.loading_layout_strip : com.common.R.layout.loading_layout_square;
        setContentView(content);
        mTextView = (TextView) findViewById(com.common.R.id.tv_loading_text);
        setCanceledOnTouchOutside(false);
    }

    /**
     * 为加载进度个对话框设置不同的提示消息
     *
     * @param message 给用户展示的提示信息
     * @return build模式设计，可以链式调用
     */
    public LoadingDialog setMessage(String message) {
        mTextView.setText(message);
        return this;
    }
}
