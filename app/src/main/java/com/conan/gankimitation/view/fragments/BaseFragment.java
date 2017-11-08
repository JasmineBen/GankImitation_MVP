package com.conan.gankimitation.view.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.conan.gankimitation.GankApplication;
import com.conan.gankimitation.contract.IContract;
import com.conan.gankimitation.di.component.DaggerFragmentComponent;
import com.conan.gankimitation.di.component.FragmentComponent;
import com.conan.gankimitation.di.module.FragmentModule;

import butterknife.ButterKnife;

/**
 * Description：基类Fragment
 * Created by：JasmineBen
 * Time：2017/10/31
 */

public abstract class BaseFragment<T extends IContract.IPresenter> extends Fragment {

    private View mFragmentView;

    private FragmentComponent mFragmentComponent;

    protected abstract int getLayoutId();

    protected abstract void initViews(View fragmentView);

    protected abstract void injectDagger();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFragmentComponent = DaggerFragmentComponent.builder()
                .applicationComponent(((GankApplication) getActivity().getApplication()).getAppComponent())
                .fragmentModule(new FragmentModule(this))
                .build();
        injectDagger();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mFragmentView == null) {
            mFragmentView = inflater.inflate(getLayoutId(), container, false);
            ButterKnife.bind(this, mFragmentView);
            initViews(mFragmentView);
        }
        return mFragmentView;
    }

    protected FragmentComponent getFragmentComponent(){
        return mFragmentComponent;
    }
}
