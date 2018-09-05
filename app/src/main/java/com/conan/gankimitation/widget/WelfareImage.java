package com.conan.gankimitation.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;

import com.bumptech.glide.Glide;
import com.conan.gankimitation.utils.AppUtil;

public class WelfareImage extends AppCompatImageView{
    private int originalWidth;
    private int originalHeight;



    public WelfareImage(Context context) {
        super(context);
    }

    public WelfareImage(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public WelfareImage(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setOriginalSize(int originalWidth, int originalHeight) {
        this.originalWidth = originalWidth;
        this.originalHeight = originalHeight;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (originalWidth > 0 && originalHeight > 0) {
            float ratio = (float) originalWidth / (float) originalHeight;

            int width = MeasureSpec.getSize(widthMeasureSpec);
            int height = MeasureSpec.getSize(heightMeasureSpec);

            if (width > 0) {
                height = (int) ((float) width / ratio);
            }

            setMeasuredDimension(width, height);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }
    }

    public void load(String url){
        int columnWidth = AppUtil.getColumnWidth(getContext(), 2, 16);
        url = AppUtil.buildRequestImageParam(getContext(), url, columnWidth);
        setOriginalSize(columnWidth,columnWidth);
        Glide.with(getContext()).load(url).centerCrop().into(this);
    }
}
