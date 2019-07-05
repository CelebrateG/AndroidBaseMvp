package com.hazz.baselibs;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.hazz.baselibs.net.download.DownLoadManager;
import com.hazz.baselibs.net.download.ProgressCallBack;

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
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.fulumbia.base.test", appContext.getPackageName());
    }

    @Test
    public void testDownload(){
        String dir = "C:\\Users\\admin\\Documents\\";
        String fileName = "testApk";
        DownLoadManager.getInstance().load("http://rap2.zongs365.net/organization", new ProgressCallBack(dir,fileName) {
            @Override
            public void onSuccess(Object o) {
                System.out.println("下载成功！");
            }

            @Override
            public void progress(long progress, long total) {
                System.out.println("下载进度："+progress);
            }

            @Override
            public void onError(Throwable e) {
                System.out.println("下载失败！");

            }
        });
    }
}
