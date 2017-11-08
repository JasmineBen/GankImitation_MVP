package com.conan.gankimitation;

import android.app.Application;

import com.conan.gankimitation.di.component.ApplicationComponent;
import com.conan.gankimitation.di.component.DaggerApplicationComponent;
import com.conan.gankimitation.di.module.ApplicationModule;

/**
 * Description：Application
 * Created by：JasmineBen
 * Time：2017/10/28
 */
public class GankApplication extends Application{

    private ApplicationComponent mAppComponent;

    @Override
    public void onCreate() {
        super.onCreate();
        this.initializeInjector();
    }

    private void initializeInjector(){
        mAppComponent = DaggerApplicationComponent.builder()
                .applicationModule(new ApplicationModule(this)).build();
    }

    public ApplicationComponent getAppComponent(){
        return mAppComponent;
    }

}
