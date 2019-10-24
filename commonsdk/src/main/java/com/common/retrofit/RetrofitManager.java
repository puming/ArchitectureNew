package com.common.retrofit;

import android.content.Context;
import android.util.Log;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.Lazy;
import io.reactivex.Observable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * @author pm
 * @date 2018/12/4
 * @email puming@zdsoft.cn
 */
public class RetrofitManager implements IRetrofitManager {
    private static final String TAG = "RetrofitManager";
    @Named("live")
    @Inject
    Lazy<Retrofit> mRetrofit;
    @Named("rx")
    @Inject
    Lazy<Retrofit> mRxRetrofit;

    @Inject
    Lazy<OkHttpClient> mClient;

    @Inject
    public RetrofitManager() {
    }

    @Override
    public <T> T obtainRetrofitService(Class<T> serviceClass) {
//        return wrapperRetrofitService(serviceClass, false);
        return obtainRetrofitService(serviceClass, false);
    }

    @Override
    public <T> T obtainRetrofitService(Class<T> serviceClass, boolean isRxRetrofit) {
//        return wrapperRetrofitService(serviceClass, isRxRetrofit);
        return isRxRetrofit ? createRxRetrofit(serviceClass) : createService(serviceClass);
    }

    public <T> T wrapperRetrofitService(Class<T> serviceClass, boolean isRxRetrofit) {
        return (T) Proxy.newProxyInstance(serviceClass.getClassLoader(), new Class<?>[]{serviceClass},
                (proxy, method, args) -> {
//                    Log.d(TAG, "wrapperRetrofitService: proxy=" + proxy + "method=" + method + "args=" + args);
                    if (method.getReturnType() == Observable.class) {
                        // 如果方法返回值是 Observable 的话，则包一层再返回
                        return Observable.defer(() -> {
                            final T service;
                            if (isRxRetrofit) {
                                service = createRxRetrofit(serviceClass);
                            } else {
                                service = createService(serviceClass);
                            }
                            // 执行真正的 Retrofit 动态代理的方法
                            Observable observable = (Observable) getRetrofitMethod(service, method)
                                    .invoke(service, args);
                            return observable
                                    .subscribeOn(Schedulers.io());
                        }).subscribeOn(Schedulers.single());
                    } else {
                        Log.d(TAG, "wrapperRetrofitService: method=" + method.getReturnType());
                        // 返回值不是 Observable 的话不处理
                        final T service;
                        if (isRxRetrofit) {
                            service = createRxRetrofit(serviceClass);
                        } else {
                            service = createService(serviceClass);
                        }
                        return getRetrofitMethod(service, method).invoke(service, args);
                    }
                });
    }

    /**
     * 根据传入的 Class 获取对应的 Retrofit Service
     *
     * @param serviceClass ApiService class
     * @param <T>          ApiService class
     * @return ApiService
     */
    private <T> T createService(Class<T> serviceClass) {
        return mRetrofit.get().create(serviceClass);
    }

    private <T> T createRxRetrofit(Class<T> serviceClass) {
        return mRxRetrofit.get().create(serviceClass);
    }

    private <T> Method getRetrofitMethod(T service, Method method) throws NoSuchMethodException {
        return service.getClass().getMethod(method.getName(), method.getParameterTypes());
    }

    @Override
    public <T> T obtainCacheService(Class<T> cacheClass) {
        return null;
    }

    @Override
    public void clearAllCache() {

    }

    @Override
    public Context getContext() {
        return null;
    }
}
