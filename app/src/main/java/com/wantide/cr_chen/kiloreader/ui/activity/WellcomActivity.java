package com.wantide.cr_chen.kiloreader.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.wantide.cr_chen.kiloreader.R;
import com.wantide.cr_chen.kiloreader.presenter.WellcomPresenter;
import com.wantide.cr_chen.kiloreader.presenter.impl.WellcomView;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by CR_Chen on 2016/5/25.
 * 欢迎主界面
 */
public class WellcomActivity extends BaseActivity implements WellcomView {

    @Bind(R.id.wellcom_image)
    ImageView mImage;
    @Bind(R.id.version_text)
    TextView versionText;

    private WellcomPresenter wellcomPresenter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wellcom);
        ButterKnife.bind(this);

        wellcomPresenter = new WellcomPresenter(this, mImage, WellcomActivity.this);
        wellcomPresenter.requestWellcom();

    }

    @Override
    public void onRquestFinish() {
        Intent intent = new Intent(WellcomActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onSuccessVersionStr(String versionStr) {
        versionText.setText(versionStr);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        ButterKnife.unbind(this);
        if(wellcomPresenter != null)
            wellcomPresenter.unsubcrible();
    }
}
