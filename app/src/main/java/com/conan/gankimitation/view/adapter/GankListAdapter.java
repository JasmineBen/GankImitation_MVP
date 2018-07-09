package com.conan.gankimitation.view.adapter;

import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleObserver;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.conan.gankimitation.R;
import com.conan.gankimitation.bean.FlutterWebEvent;
import com.conan.gankimitation.bean.GankEntity;
import com.conan.gankimitation.bean.GankList;
import com.conan.gankimitation.di.qualifier.ImageFetcher;
import com.conan.gankimitation.imageloader.IFetcher;
import com.conan.gankimitation.utils.AppUtil;
import com.conan.gankimitation.utils.LogUtil;
import com.conan.gankimitation.view.activities.BaseActivity;
import com.conan.gankimitation.view.activities.FlutterWebActivity;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.flutter.app.FlutterActivity;

/**
 * Description：Gank列表Adapter
 * Created by：JasmineBen
 * Time：2017/10/31
 */
public class GankListAdapter extends RecyclerView.Adapter {

    private static final String TAG = GankListAdapter.class.getSimpleName();

    private GankList mData;
    private BaseActivity mActivity;
    private Resources mResources;
    private IFetcher mImageLoader;

    @Inject
    public GankListAdapter(BaseActivity activity,@ImageFetcher("ImageLoader") IFetcher fetcher){
        this.mActivity = activity;
        this.mImageLoader = fetcher;
        mResources = mActivity.getResources();
    }

    public void setData(GankList data) {
        this.mData = data;
        notifyDataSetChanged();
        LogUtil.i(TAG, "setData：" + data.toString());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LogUtil.i(TAG,"onBindViewHolder onCreateViewHolder");
        View itemView = LayoutInflater.from(mActivity).inflate(R.layout.gank_item_layout,parent,false);
        return new MainViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        LogUtil.i(TAG,"onBindViewHolder position:"+position);
        GankEntity entity = mData.getGankDatas().get(position);
        MainViewHolder viewHolder = (MainViewHolder)holder;
        viewHolder.mSummaryTv.setText(entity.getDesc());
        String author =! TextUtils.isEmpty(entity.getPublisher())
                ? String.format(mResources.getString(R.string.author_format),entity.getPublisher())
                :entity.getPublisher();
        viewHolder.mAuthorTv.setText(author);
        viewHolder.mPublishTimeTv.setText(AppUtil.parseSimpleDate(entity.getPublishedTime()));
        if(entity.getImages()!=null && entity.getImages().size() >0){
            viewHolder.mPhotoIv.setVisibility(View.VISIBLE);
            String resizeImageUrl = AppUtil.buildRequestImageParam(mActivity,entity.getImages().get(0),72);
            mImageLoader.displayImage(mActivity,viewHolder.mPhotoIv,resizeImageUrl,null,null);
        }else{
            viewHolder.mPhotoIv.setVisibility(View.GONE);
        }
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(entity.getUrl())){
//                    Intent intent = new Intent();
//                    intent.setAction(Intent.ACTION_VIEW);
//                    Uri uri = Uri.parse(entity.getUrl());
//                    intent.setData(uri);
//                    mActivity.startActivity(intent);
                    Intent intent = new Intent(mActivity, FlutterWebActivity.class);
                    intent.putExtra("url",entity.getUrl());
                    mActivity.startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0: mData.size();
    }

    class MainViewHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.photo_iv) ImageView mPhotoIv;
        @BindView(R.id.summary_tv) TextView mSummaryTv;
        @BindView(R.id.author_tv) TextView mAuthorTv;
        @BindView(R.id.publish_time_tv) TextView mPublishTimeTv;

        public MainViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
