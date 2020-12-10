package com.common.data;

import android.app.Instrumentation;
import android.content.Context;
import android.os.Process;
import android.util.DisplayMetrics;
import android.util.Size;

import androidx.lifecycle.Observer;
import androidx.test.espresso.base.MainThread;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    @Test
    public void useAppContext() {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getInstrumentation().getTargetContext();
        assertEquals("com.common.data.test", appContext.getPackageName());
    }

    @Test
    @MainThread
    public void testLiveData() {
        Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();
        Context context = instrumentation.getContext();
        LiveDataManager.getInstance().getLiveData("800", Process.class).postValue(new Process());
        LiveDataManager.getInstance().getLiveData("100", Thread.class).postValue(Thread.currentThread());
        LiveDataManager.getInstance().getLiveData("200", Size.class).postValue(new Size(100, 100));
        LiveDataManager.getInstance().getLiveData("300", DisplayMetrics.class).postValue(context.getResources().getDisplayMetrics());

        //observer
    }
}