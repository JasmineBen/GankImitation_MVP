package com.conan.gankimitation.view.activities;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.conan.gankimitation.R;
import com.conan.gankimitation.bean.MainTab;
import com.conan.gankimitation.data.repository.IRepository;
import com.conan.gankimitation.databinding.ActivityMainBinding;
import com.conan.gankimitation.utils.AppUtil;
import com.conan.gankimitation.utils.LogUtil;
import com.conan.gankimitation.view.adapter.MainTabPagerAdapter;
import com.conan.gankimitation.widget.CircleDrawable;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Description：主Activity
 * Created by：JasmineBen
 * Time：2017/10/31
 */
public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = MainActivity.class.getSimpleName();

    NavigationView mNavigationView;
    DrawerLayout mDrawerLayout;
    ViewPager mViewPager;
    TabLayout mTabLayout;
    ActivityMainBinding mBinding;

    @Inject
    IRepository mRepository;

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
        mBinding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        initToolbar();
        initNavigationView();
        initDrawerLayout();
        initViewPager();
        initTabLayout();
    }

    private void initToolbar() {
        Toolbar toolbar = mBinding.mainToolbar.toolbar;
        toolbar.setTitle(R.string.app_name);
        setSupportActionBar(toolbar);
    }

    private void initDrawerLayout(){
        mDrawerLayout = mBinding.drawerLeft;
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mBinding.mainToolbar.toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        mDrawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    private void initViewPager() {
        mViewPager = mBinding.viewpager;
        String[] tabs = getResources().getStringArray(R.array.study_tab);
        List<MainTab> mainTabs = new ArrayList<>(tabs.length);
        for (int i = 0; i < tabs.length; i++) {
            MainTab tab = new MainTab(tabs[i], i, AppUtil.parseGankDataType(tabs[i]));
            mainTabs.add(tab);
        }
        mViewPager.setOffscreenPageLimit(mainTabs.size());
        MainTabPagerAdapter adapter = new MainTabPagerAdapter(this, mainTabs);
        mViewPager.setAdapter(adapter);
    }

    private void initTabLayout() {
        mTabLayout = mBinding.tabs;
        mTabLayout.setupWithViewPager(mViewPager);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
    }

    private void initNavigationView(){
        mNavigationView = mBinding.navigationView;
        mNavigationView.setNavigationItemSelectedListener(this);
        View headView = mNavigationView.getHeaderView(0);
        ImageView headImage = (ImageView) headView.findViewById(R.id.header);
        Glide.with(this).load(R.mipmap.navigation_header).asBitmap().into(new BitmapImageViewTarget(headImage) {
            @Override
            protected void setResource(Bitmap resource) {
                    getView().setImageDrawable(new CircleDrawable(resource));

            }
        });
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

    public IRepository getRepository() {
        return mRepository;
    }
}
