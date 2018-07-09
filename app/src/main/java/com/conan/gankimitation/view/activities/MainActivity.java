package com.conan.gankimitation.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.conan.gankimitation.R;
import com.conan.gankimitation.bean.MainTab;
import com.conan.gankimitation.di.qualifier.ImageFetcher;
import com.conan.gankimitation.imageloader.DisplayOptionsCreator;
import com.conan.gankimitation.imageloader.IFetcher;
import com.conan.gankimitation.utils.AppUtil;
import com.conan.gankimitation.utils.LogUtil;
import com.conan.gankimitation.view.adapter.MainTabPagerAdapter;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

/**
 * Description：主Activity
 * Created by：JasmineBen
 * Time：2017/10/31
 */
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    @BindView(R.id.navigation_view)
    NavigationView mNavigationView;
    @BindView(R.id.drawer_left)
    DrawerLayout mDrawerLayout;
    @BindView(R.id.viewpager)
    ViewPager mViewPager;
    @BindView(R.id.tabs)
    TabLayout mTabLayout;
    @Inject
    @ImageFetcher("ImageLoader")
    IFetcher mImageLoader;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onNecessaryPermissionGranted() {
        getActivityComponent().inject(this);
        initViews();
    }

    protected void initViews() {
        initNavigationView();
        initDrawerLayout();
        initViewPager();
        initTabLayout();
    }

    private void initDrawerLayout(){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, getToolbar(), R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initViewPager() {
        String[] tabs = getResources().getStringArray(R.array.study_tab);
        List<MainTab> mainTabs = new ArrayList<>(tabs.length);
        for (int i = 0; i < tabs.length; i++) {
            MainTab tab = new MainTab(tabs[i], i, AppUtil.parseGankDataType(tabs[i]));
            mainTabs.add(tab);
        }
        MainTabPagerAdapter adapter = new MainTabPagerAdapter(this, mainTabs);
        mViewPager.setAdapter(adapter);
    }

    private void initTabLayout() {
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    private void initNavigationView(){
        mNavigationView.setNavigationItemSelectedListener(this);
        View headView = mNavigationView.getHeaderView(0);
        ImageView headImage = (ImageView) headView.findViewById(R.id.header);
        mImageLoader.displayImage(this,headImage,
                R.mipmap.navigation_header, DisplayOptionsCreator.getAvatarOptions(),null);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        LogUtil.i(TAG, "onNavigationItemSelected :" + item.getItemId());
        mDrawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()){
            case R.id.welfare:
                startActivity(new Intent(this,WelfareActivity.class));
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

}
