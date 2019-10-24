package com.common.utils;

/**
 * @author pm
 * @date 2019/9/9
 * @email puming@zdsoft.cn
 * 创建单例类的工具类
 */
public abstract class Singleton<T> {
    private T mInstance;

    protected abstract T create();

    public final T getInstance() {
        synchronized (this) {
            if (mInstance == null) {
                mInstance = create();
            }
            return mInstance;
        }
    }
}