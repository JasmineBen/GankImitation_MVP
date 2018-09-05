package com.conan.gankimitation.view.adapter;

import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.conan.gankimitation.R;
import com.conan.gankimitation.bean.GankList;
import com.conan.gankimitation.databinding.GankItemLayoutBinding;
import com.conan.gankimitation.view.activities.BaseActivity;
import com.conan.gankimitation.view.listener.OnItemClickListener;
import com.conan.gankimitation.viewmodel.GankItemViewModel;
import com.conan.gankimitation.viewmodel.ViewModelFactory;

import javax.inject.Inject;

public class GankListAdapter extends RecyclerView.Adapter<GankListAdapter.BindingHolder>{

    private BaseActivity mActivity;
    private GankList mData;
    private OnItemClickListener mItemClickListener;

    @Inject
    public GankListAdapter(BaseActivity activity) {
        mActivity = activity;
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        this.mItemClickListener = itemClickListener;
    }

    public void setData(GankList data){
        mData = data;
        notifyDataSetChanged();
    }

    @Override
    public BindingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        GankItemLayoutBinding binding = DataBindingUtil.inflate(
                LayoutInflater.from(parent.getContext()), R.layout.gank_item_layout,
                parent, false);
        return new BindingHolder(binding);

    }

    @Override
    public void onBindViewHolder(BindingHolder holder, int position) {
        GankItemLayoutBinding binding = holder.binding;
        GankItemViewModel viewModel = ViewModelFactory.getInstance(mActivity.getApplication()).create(GankItemViewModel.class);
        viewModel.setData(mData.getItem(position));
        binding.setViewmodel(viewModel);
        if(mItemClickListener != null){
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mItemClickListener.onItemClick(mData.getItem(position));
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return mData != null ? mData.size() : 0;
    }



    public static class BindingHolder extends RecyclerView.ViewHolder {
        private GankItemLayoutBinding binding;

        public BindingHolder(GankItemLayoutBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
