package com.conan.gankimitation.view.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.conan.gankimitation.R;

import butterknife.BindView;
import io.flutter.facade.Flutter;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.view.FlutterView;

public class FlutterWebActivity  extends BaseActivity{

    private String mUrl;
    @BindView(R.id.progress)
    public View mProgress;

    private FlutterView mFlutterView;
    private boolean bFlutterViewAdded;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUrl = getIntent().getStringExtra("url");
        createFlutterView();
    }

    /**
     * 解決黑屏問題
     * 1、給佈局增加一個ProgressBar
     * 2、在onCreate中創建FlutterView, addFirstFrameListener
     * 3、在onResume中根據情況add FlutterView
     * 4、在onFirstFrame中mProgress.setVisibility(View.GONE);
     **/
    @Override
    protected void onResume() {
        super.onResume();
        if(!bFlutterViewAdded){
            addFlutterView();
            bFlutterViewAdded = true;
        }

    }

    @Override
    protected int getLayoutId() {
        return R.layout.flutter_web_layout;
    }

    private void createFlutterView(){
        mFlutterView= Flutter.createView(FlutterWebActivity.this,getLifecycle(),"webfragment://"+mUrl);
        mFlutterView.addFirstFrameListener(new FlutterView.FirstFrameListener() {
            @Override
            public void onFirstFrame() {
                mProgress.setVisibility(View.GONE);
            }
        });
    }

    private void addFlutterView(){
        //close Activity
        new MethodChannel(mFlutterView, "app.channel.closeActivity").setMethodCallHandler(new MethodChannel.MethodCallHandler() {
            @Override
            public void onMethodCall(MethodCall methodCall, MethodChannel.Result result) {
                if (methodCall.method.contentEquals("closeActivity")) {
                    finish();
                    result.success(true);
                }
            }
        });
        FrameLayout frame = findViewById(R.id.frame);
        frame.addView(mFlutterView,new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
    }

    @Override
    protected void onNecessaryPermissionGranted() {

    }


    @Override
    protected void onDestroy() {
        if(mFlutterView != null){
            mFlutterView.destroy();
        }
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.i("zpy","onBackPressed");
    }
}
