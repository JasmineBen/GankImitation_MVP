package com.conan.gankimitation.view.adapter;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by baurine on 1/10/17.
 */

public class MultiTypeAdapter extends RecyclerView.Adapter<MultiTypeAdapter.ItemViewHolder> {
    public interface IItem {
        // get the xml layout this type item used in
        int getLayout();

        // get the variable name in the xml
        int getVariableId();
    }

    private List<IItem> items = new ArrayList<>();
    private ItemClickListener mOnItemClickListener;

    @Inject
    public MultiTypeAdapter(){
    }

    public void setItemmClickListener(ItemClickListener listener){
        mOnItemClickListener = listener;
    }

    ////////////////////////////////////////////////////////
    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return ItemViewHolder.create(parent, viewType);
    }

    @Override
    public void onBindViewHolder(ItemViewHolder holder, int position) {
        holder.bindTo(items.get(position),mOnItemClickListener);
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getLayout();
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    ////////////////////////////////////////////////////////
    // default items operation
    // you also can inherit MultiTypeAdapter to implement more methods to operate items
    public List<IItem> getItems() {
        return items;
    }

    public int findPos(IItem item) {
        return items.indexOf(item);
    }

    public void setItem(IItem item) {
        clearItems();
        addItem(item);
    }

    public void setItems(List<? extends IItem> items) {
        clearItems();
        addItems(items);
    }

    public void addItem(IItem item) {
        items.add(item);
    }

    public void addItem(IItem item, int index) {
        items.add(index, item);
    }

    public void addItems(List<? extends IItem> items) {
        this.items.addAll(items);
    }

    public int removeItem(IItem item) {
        int pos = findPos(item);
        items.remove(item);
        return pos;
    }

    public void clearItems() {
        items.clear();
    }

    ////////////////////////////////////////////////////////
    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

        static ItemViewHolder create(ViewGroup parent, int viewType) {
            ViewDataBinding binding =
                    DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                            viewType, parent, false);
            return new ItemViewHolder(binding);
        }

        ItemViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bindTo(IItem item, ItemClickListener listener) {
            binding.setVariable(item.getVariableId(), item);
            binding.executePendingBindings();
            if(listener != null){
                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        listener.onItemClick(item);
                    }
                });
            }
        }
    }

    public interface ItemClickListener{
        void onItemClick(IItem item);
    }
}
