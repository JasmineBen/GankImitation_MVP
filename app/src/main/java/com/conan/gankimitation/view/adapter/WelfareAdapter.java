package com.conan.gankimitation.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.conan.gankimitation.R;
import com.conan.gankimitation.bean.GankList;
import com.conan.gankimitation.databinding.ItemWelfareBinding;
import com.conan.gankimitation.view.activities.BaseActivity;
import com.conan.gankimitation.view.listener.OnItemClickListener;
import com.conan.gankimitation.viewmodel.ViewModelFactory;
import com.conan.gankimitation.viewmodel.WelfareViewModel;

import javax.inject.Inject;

/**
 * Description：福利Adapter
 * Created by：JasmineBen
 * Time：2017/2017/11/7
 */

public class WelfareAdapter extends RecyclerView.Adapter<WelfareAdapter.BindingHolder> {

    private GankList mWelfareData;
    private BaseActivity mActivity;

    @Inject
    public WelfareAdapter(BaseActivity activity) {
        this.mActivity = activity;
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
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ItemWelfareBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.item_welfare,
                parent, false);
        return new BindingHolder(binding);

    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        ItemWelfareBinding binding = holder.binding;
        WelfareViewModel viewModel = ViewModelFactory.getInstance(mActivity.getApplication()).create(WelfareViewModel.class);
        viewModel.setData(mWelfareData.getItem(position));
        binding.setViewmodel(viewModel);
    }




    public static class BindingHolder extends RecyclerView.ViewHolder {
        private ItemWelfareBinding binding;

        public BindingHolder(ItemWelfareBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}