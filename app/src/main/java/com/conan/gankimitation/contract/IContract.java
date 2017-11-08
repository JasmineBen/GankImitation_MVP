package com.conan.gankimitation.contract;

/**
 * Description：MVP的连接V P的接口
 * Created by：JasmineBen
 * Time：2017/11/1
 */
public interface IContract {

    interface IView{

    }

    interface IPresenter<V extends IView>{
        void attachView(V view);
        void detachView();
    }
}
