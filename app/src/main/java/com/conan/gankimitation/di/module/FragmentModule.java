package com.conan.gankimitation.di.module;

import com.conan.gankimitation.view.activities.BaseActivity;
import com.conan.gankimitation.di.scope.PerFragment;
import com.conan.gankimitation.view.fragments.BaseFragment;

import dagger.Module;
import dagger.Provides;

/**
 * Description：Dagger Fragment Module
 * Created by：JasmineBen
 * Time：2017/11/6
 */

@Module
public class FragmentModule {

    private BaseFragment mFragment;

    public FragmentModule(BaseFragment fragment) {
        mFragment = fragment;
    }

    @Provides
    @PerFragment
    public BaseActivity provideActivity() {
        return (BaseActivity)mFragment.getActivity();
    }

    @Provides
    @PerFragment
    public BaseFragment provideFragment() {
        return mFragment;
    }

}
