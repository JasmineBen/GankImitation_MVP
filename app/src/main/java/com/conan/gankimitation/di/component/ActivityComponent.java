package com.conan.gankimitation.di.component;

import com.conan.gankimitation.di.module.ActivityModule;
import com.conan.gankimitation.di.scope.PerActivity;
import com.conan.gankimitation.view.activities.MainActivity;
import com.conan.gankimitation.view.activities.WelfareActivity;

import dagger.Component;

/**
 * Description：Dragger Activity Component
 * Created by：JasmineBen
 * Time：2017/11/6
 */

@PerActivity
@Component(dependencies = ApplicationComponent.class,modules = ActivityModule.class)
public interface ActivityComponent {
    void inject(MainActivity mainActivity);
    void inject(WelfareActivity welfareActivity);
}
