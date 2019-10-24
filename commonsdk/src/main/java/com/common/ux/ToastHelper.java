package com.common.ux;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.common.R;

/**
 * @author pm
 * @date 18-5-5
 */
public class ToastHelper {
    private static Toast toast;
    private static boolean isFinishInflate = false;

    public static void showToast(Context context, String text) {
        Toast makeToast = makeToast(context, text);
        if (!isFinishInflate) {
            View view = LayoutInflater.from(context).inflate(R.layout.toast_transparent_layout_text, null);
            TextView textView = view.findViewById(R.id.tv_toast_text);
            textView.setText(text);
            makeToast.setView(view);
            makeToast.setDuration(Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            isFinishInflate = true;
        }
        makeToast.show();
    }

    public static void showToast(Context context, View view) {
        Toast makeToast = makeToast(context, "");
        makeToast.setView(view);
        makeToast.setDuration(Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        isFinishInflate = true;
        makeToast.show();
    }


    public static Toast makeToast(Context context, String text, int length) {
        if (toast == null) {
            synchronized (ToastHelper.class) {
                if (toast == null) {
                    toast = Toast.makeText(context.getApplicationContext(), text, length);
                }
            }
        }
        toast.setText(text);
        toast.setDuration(length);
        return toast;
    }

    public static Toast makeToast(Context context, int ids, int length) {
        return makeToast(context, context.getString(ids), length);
    }

    public static Toast makeToast(Context context, int ids) {
        return makeToast(context, context.getString(ids));
    }

    public static Toast makeToast(Context context, String text) {
        return makeToast(context, text, Toast.LENGTH_SHORT);
    }
}
