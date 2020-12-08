package com.common.data;

import android.content.Context;
import android.os.Process;
import android.util.Size;
import android.view.Display;

import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.*;

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
    public void testLiveData(){
        LiveDataManager.getInstance().getLiveData("800", Process.class);
        LiveDataManager.getInstance().getLiveData("100", Thread.class);
        LiveDataManager.getInstance().getLiveData("200", Size.class);
        LiveDataManager.getInstance().getLiveData("300", Display.class);
//        assertEquals(4, 2 + 2);
    }
}