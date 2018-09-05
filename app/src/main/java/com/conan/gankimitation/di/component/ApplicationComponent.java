package com.conan.gankimitation.di.component;

import android.content.Context;

import com.conan.gankimitation.data.database.dao.DaoMaster;
import com.conan.gankimitation.data.repository.IRepository;
import com.conan.gankimitation.di.module.ApplicationModule;
import com.conan.gankimitation.view.activities.BaseActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Description：Dragger Application Component
 * Created by：JasmineBen
 * Time：2017/11/6
 */

@Singleton
@Component(modules = ApplicationModule.class)
public interface ApplicationComponent {
    void inject(BaseActivity base);

    //暴露出来给dependencies它的Component使用
    Context applicationContext();
    DaoMaster.OpenHelper openDbHelper();
    IRepository gankRepository();
}
