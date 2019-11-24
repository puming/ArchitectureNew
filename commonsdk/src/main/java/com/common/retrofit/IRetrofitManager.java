package com.common.retrofit;

import android.content.Context;

/**
 * @author pm
 * @date 2018/12/4
 * @email puming@zdsoft.cn
 */
public interface IRetrofitManager {
    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     *
     * @param serviceClass
     * @param <T>
     * @return
     */
    <T> T obtainRetrofitService(Class<T> serviceClass);

    /**
     * 支持Rx 的 Retrofit
     *
     * @param serviceClass
     * @param isRxRetrofit
     * @param <T>
     * @return
     */
    @Deprecated
    <T> T obtainRetrofitService(Class<T> serviceClass, boolean isRxRetrofit);

    /**
     * @param serviceClass
     * @param type
     * @param <T>
     * @return
     */
    <T> T obtainRetrofitService(Class<T> serviceClass, AdapterType type);

    /**
     * 根据传入的 Class 获取对应的 RxCache service
     *
     * @param cacheClass
     * @param <T>
     * @return
     */
    <T> T obtainCacheService(Class<T> cacheClass);

    /**
     * 清理所有缓存
     */
    void clearAllCache();

    /**
     * Context
     *
     * @return
     */
    Context getContext();

    enum AdapterType {
        LIVE,
        RX,
        DEFAULT
    }
}
