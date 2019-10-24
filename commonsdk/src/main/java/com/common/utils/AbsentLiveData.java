package com.common.utils;

import android.os.Looper;

import androidx.lifecycle.LiveData;

/**
 * @author pm
 * @date 2018/11/27
 * @email puming@zdsoft.cn
 */
public class AbsentLiveData<T> extends LiveData<T> {

    private AbsentLiveData() {
        if(isMainThread()){
            setValue(null);
        }else {
            postValue(null);
        }
    }

    public static <C>LiveData<C> create(Class<C> clazz) {
        return new AbsentLiveData<C>();
    }

    private boolean isMainThread() {
        return Looper.getMainLooper().getThread() == Thread.currentThread();
    }
}
