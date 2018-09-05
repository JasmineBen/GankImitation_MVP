package com.conan.gankimitation.di.module;

import android.content.Context;

import com.conan.gankimitation.GankApplication;
import com.conan.gankimitation.data.database.GankOpenDbHelper;
import com.conan.gankimitation.data.database.dao.DaoMaster;
import com.conan.gankimitation.data.repository.GankRepository;
import com.conan.gankimitation.data.repository.IRepository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Description：Dagger Application Module
 * Created by：JasmineBen
 * Time：2017/11/6
 */

@Module
public class ApplicationModule {
    private final GankApplication mApp;

    public ApplicationModule(GankApplication app){
        this.mApp = app;
    }

    @Provides
    @Singleton
    Context provideApplicationContext(){
        return this.mApp;
    }

    @Provides
    @Singleton
    DaoMaster.OpenHelper provideOpenDbHelper(){
       return new GankOpenDbHelper(this.mApp,"gankimitation");
    }

    @Provides
    @Singleton
    IRepository provideGankRepository(GankRepository repository){
        return repository;
    }


}
