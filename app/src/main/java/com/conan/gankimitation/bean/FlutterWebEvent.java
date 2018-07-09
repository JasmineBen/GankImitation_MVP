package com.conan.gankimitation.bean;

import io.flutter.view.FlutterView;

public class FlutterWebEvent {
    private FlutterView mFlutterView;

    public FlutterWebEvent(FlutterView flutterView) {
        this.mFlutterView = flutterView;
    }

    public FlutterView getFlutterView() {
        return mFlutterView;
    }


}
