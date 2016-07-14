package com.wantide.cr_chen.kiloreader.ui.activity;

import android.Manifest;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.transition.Slide;
import android.view.Gravity;
import android.view.MenuItem;

import com.wantide.cr_chen.kiloreader.R;
import com.wantide.cr_chen.kiloreader.model.bean.FragmentBean;
import com.wantide.cr_chen.kiloreader.ui.fragment.BaiDeJieNewsFragment;
import com.wantide.cr_chen.kiloreader.ui.fragment.MeiZiNewsFragment;
import com.wantide.cr_chen.kiloreader.ui.fragment.ZhiHuNewsFragment;
import com.wantide.cr_chen.kiloreader.utils.DoubleClickExitDetector;

import java.util.ArrayList;
import java.util.HashSet;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener{
    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.ctl_main)
    CoordinatorLayout ctlMain;
    @Bind(R.id.nav_view)
    NavigationView navView;
    @Bind(R.id.drawer_layout)
    DrawerLayout drawerLayout;

    private DoubleClickExitDetector detector;
    private ArrayList<FragmentBean> mFragments;//装载所有的fragment切换页面
    private Fragment mCurFragment;//当前fragment

    private static final int READ_EXTERNAL_STORAGE_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navView.setNavigationItemSelectedListener(this);
        initMenu();

        overridePendingTransition(R.anim.hold, 0);//切换动画

        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, READ_EXTERNAL_STORAGE_REQUEST_CODE);

        //监听双击返回键
        detector = new DoubleClickExitDetector(MainActivity.this);
    }



    /**
     * 初始化fragment管理
     */
    private void initMenu(){
        mFragments = new ArrayList<>();
        mFragments.add(new FragmentBean(new ZhiHuNewsFragment(), "知乎热闻"));
        mFragments.add(new FragmentBean(new BaiDeJieNewsFragment(), "百思不得姐"));
        mFragments.add(new FragmentBean(new MeiZiNewsFragment(), "豆瓣妹子"));

        switchFragment(mFragments.get(0).getFragment(), mFragments.get(0).getTitle());
    }

    /**
     * 切换fragment
     * @param fragment
     * @param title
     */
    private void switchFragment(Fragment fragment, String title) {
        Slide slideTransition;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//切换动画
            slideTransition = new Slide(Gravity.RIGHT);
            slideTransition.setDuration(700);
            fragment.setEnterTransition(slideTransition);
        }
        if (mCurFragment == null || !mCurFragment.getClass().getName().equals(fragment.getClass().getName())) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.replace, fragment).commit();
            mCurFragment = fragment;
            ActionBar actionBar = getSupportActionBar();
            assert actionBar != null;
            actionBar.setTitle(title);
        }
    }



    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.zhihu_module:
                switchFragment(mFragments.get(0).getFragment(), mFragments.get(0).getTitle());
                break;

            case R.id.baidejie_module:
                switchFragment(mFragments.get(1).getFragment(), mFragments.get(1).getTitle());
                break;

            case R.id.meizi_module:
                switchFragment(mFragments.get(2).getFragment(), mFragments.get(2).getTitle());

            default:
                break;
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        assert drawer != null;
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackPressed() {
        if (detector.click()) {
            //杀死进程
            Process.killProcess(Process.myPid());
            System.exit(0);
        }
    }
}
