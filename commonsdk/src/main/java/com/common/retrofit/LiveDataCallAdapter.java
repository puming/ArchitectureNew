package com.common.retrofit;

import android.util.Log;


import java.lang.reflect.Type;
import java.util.concurrent.atomic.AtomicBoolean;

import androidx.lifecycle.LiveData;
import retrofit2.Call;
import retrofit2.CallAdapter;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * @author pm
 * @date 2018/11/27
 * @email puming@zdsoft.cn
 */
public class LiveDataCallAdapter<R> implements CallAdapter<R, LiveData<ApiResponse<R>>> {
    private static final String TAG = "LiveDataCallAdapter";
    private Type responseType;

    public LiveDataCallAdapter(Type responseType) {
        this.responseType = responseType;
    }

    @Override
    public Type responseType() {
        return responseType;
    }

    @Override
    public LiveData<ApiResponse<R>> adapt(Call<R> call) {
        return new LiveData<ApiResponse<R>>() {
            private AtomicBoolean started = new AtomicBoolean(false);
            @Override
            protected void onActive() {
                super.onActive();
                if (started.compareAndSet(false, true)) {
                    String url = call.request().url().toString();
                    Log.d(TAG, "onActive: url="+url);
                    call.enqueue(new Callback<R>() {
                        @Override
                        public void onResponse(Call<R> call, Response<R> response) {
                            Log.d(TAG, "onResponse: " + response.body());
                            Log.d(TAG, "onResponse: " + response.code());
                            postValue(ApiResponse.create(response));
                        }

                        @Override
                        public void onFailure(Call<R> call, Throwable t) {
                            Log.d(TAG, "onFailure: " + t.getMessage());
                            postValue(ApiResponse.create(t));
                        }
                    });
                }
            }
        };
    }
}
