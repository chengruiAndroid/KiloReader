package com.wantide.cr_chen.kiloreader;

import android.app.Application;
import android.content.Context;

import com.bugtags.library.Bugtags;
import com.bugtags.library.BugtagsOptions;

/**
 * Created by CR_Chen on 2016/5/25.
 */
public class KiloApplication extends Application {
    public static KiloApplication kiloApplication = null;


    @Override
    public void onCreate() {
        super.onCreate();
        initBugs();
        kiloApplication = this;
    }

    /**
     * 收集BUG
     */
    private void initBugs(){
        BugtagsOptions options = new BugtagsOptions.Builder().
                trackingLocation(true).//是否获取位置
                trackingCrashLog(!BuildConfig.DEBUG).//是否收集crash
                trackingConsoleLog(true).//是否收集console log
                trackingUserSteps(true).//是否收集用户操作步骤
                build();
        Bugtags.start("5bc93d8470b73198b317f4228893124f", this, Bugtags.BTGInvocationEventNone, options);
    }

    public static Context getContext(){
        return kiloApplication;
    }
}
