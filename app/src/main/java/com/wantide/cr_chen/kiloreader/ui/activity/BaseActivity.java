package com.wantide.cr_chen.kiloreader.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MotionEvent;
import android.view.Window;

import com.bugtags.library.Bugtags;

/**
 * Created by CR_Chen on 2016/5/27.
 */
public class BaseActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override protected void onPause() {
        super.onPause();
        Bugtags.onPause(this);
    }

    @Override protected void onResume() {
        super.onResume();
        Bugtags.onResume(this);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Bugtags.onDispatchTouchEvent(this, event);
        return super.dispatchTouchEvent(event);
    }






}
