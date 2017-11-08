package com.conan.gankimitation.view.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.conan.gankimitation.R;
import com.conan.gankimitation.bean.GankList;
import com.conan.gankimitation.di.qualifier.ImageFetcher;
import com.conan.gankimitation.imageloader.IFetcher;
import com.conan.gankimitation.utils.AppUtil;
import com.conan.gankimitation.view.activities.BaseActivity;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Description：福利Adapter
 * Created by：JasmineBen
 * Time：2017/2017/11/7
 */

public class WelfareAdapter extends RecyclerView.Adapter {

    private GankList mWelfareData;
    private BaseActivity mActivity;
    private IFetcher mImageFetcher;

    @Inject
    public WelfareAdapter(BaseActivity activity, @ImageFetcher("ImageLoader") IFetcher fetcher) {
        this.mActivity = activity;
        this.mImageFetcher = fetcher;
    }

    public void setData(GankList data) {
        this.mWelfareData = data;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return mWelfareData == null ? 0 : mWelfareData.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mActivity).inflate(R.layout.item_welfare, parent, false);
        return new WelfareViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        int columnWidth = AppUtil.getColumnWidth(mActivity, 2, 16);
        String url = AppUtil.buildRequestImageParam(mActivity, mWelfareData.getGankDatas().get(position).getUrl(), columnWidth);
        ImageView photo = ((WelfareViewHolder) holder).mPhotoIv;
        photo.setLayoutParams(new FrameLayout.LayoutParams(columnWidth, columnWidth));
        mImageFetcher.displayImage(mActivity, photo, url, null, null);
    }

    class WelfareViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.photo_iv)
        ImageView mPhotoIv;

        public WelfareViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
