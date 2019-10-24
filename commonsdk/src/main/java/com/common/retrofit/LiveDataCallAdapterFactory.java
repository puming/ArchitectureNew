package com.common.retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

import androidx.lifecycle.LiveData;
import retrofit2.CallAdapter;
import retrofit2.Retrofit;

/**
 * @author pm
 * @date 2018/11/27
 * @email puming@zdsoft.cn
 */
public class LiveDataCallAdapterFactory extends CallAdapter.Factory {

    @Override
    public CallAdapter<?, ?> get(Type returnType, Annotation[] annotations, Retrofit retrofit) {
        if (CallAdapter.Factory.getRawType(returnType) != LiveData.class) {
            return null;
        }
        Type observableType = CallAdapter.Factory.getParameterUpperBound(0, (ParameterizedType) returnType);
        Class<?> rawObservableType = getRawType(observableType);
        if (rawObservableType != ApiResponse.class) {
            throw new IllegalArgumentException("type must be a resource");
        }
        if (!(observableType instanceof ParameterizedType)) {
            throw new IllegalArgumentException("resource must be parameterized");
        }
        Type bodyType = getParameterUpperBound(0, (ParameterizedType) observableType);
        return new LiveDataCallAdapter<>(bodyType);
    }
}
