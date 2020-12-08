package com.common.data;

import android.util.Log;

import androidx.collection.ArrayMap;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Set;

/**
 * @author pm
 * @date 2019/9/9
 * @email puming@zdsoft.cn
 * LiveData管理类，主要用来夸组件化通信。
 */
public class LiveDataManager {
    private static final String TAG = "LiveDataManager";
    private static final int MAX_SIZE = 0;

    private final ArrayMap<String, MutableLiveData<Object>> map;

    private LiveDataManager() {
        this.map = new ArrayMap<>();
    }

    public static LiveDataManager getInstance() {
        return InstanceHolder.LIVE_DATA_MANAGER;
    }

    private static class InstanceHolder {
        private static final LiveDataManager LIVE_DATA_MANAGER = new LiveDataManager();
    }

    /**
     * 根据key获取一个LiveData
     *
     * @param key
     * @param type
     * @param <T>
     * @return
     */
    public <T> LiveData<T> getLiveData(String key, Class<T> type) {
        boolean has = map.containsKey(key);
        if (!has) {
            int size = map.size();
            if (size > MAX_SIZE) {
                Set<String> keys = map.keySet();
                for (String k : keys) {
                    Log.d(TAG, "getLiveData: k=" + k);
                }
            }
            map.put(key, new MutableLiveData<Object>());
        }
        return (LiveData<T>) map.get(key);
    }

    /**
     * 根据key清除LiveData
     *
     * @param key
     */
    public void clear(String key) {
        map.remove(key);
    }

    /**
     * 清除所有LiveData
     */
    public void clearAll() {
        map.clear();
    }
}
