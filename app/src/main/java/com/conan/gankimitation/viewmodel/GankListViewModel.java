package com.conan.gankimitation.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.databinding.Observable;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.conan.gankimitation.bean.GankList;
import com.conan.gankimitation.data.repository.GankRepository;
import com.conan.gankimitation.data.repository.IRepository;

import javax.inject.Inject;

public class GankListViewModel extends AndroidViewModel {

    private final ObservableBoolean mDataLoading = new ObservableBoolean(false);

    private final ObservableField<GankList> mGankList = new ObservableField<>();

    private IRepository mRepository;

    public GankListViewModel(@NonNull Application application) {
        super(application);
    }

    public void setRepository(IRepository repository){
        mRepository = repository;
    }

    public void loadDatas(){

    }

}
