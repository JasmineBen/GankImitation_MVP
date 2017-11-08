package com.conan.gankimitation.imageloader;

import android.graphics.Bitmap;

/**
 * Description：Option
 * Created by：JasmineBen
 * Time：2017/11/2
 */
final class DisplayOptions {
    private int targetWidth;
    private int targetHeight;
    private int imageResForEmptyUri;
    private int imageResOnLoading;
    private int imageResOnFail;
    private Bitmap.Config config;
    private boolean resizeToCircle;

    private DisplayOptions(Builder builder){
        this.targetWidth = builder.targetWidth;
        this.targetHeight = builder.targetHeight;
        this.imageResForEmptyUri = builder.imageResForEmptyUri;
        this.imageResOnFail = builder.imageResOnFail;
        this.imageResOnLoading = builder.imageResOnLoading;
        this.config = builder.config;
        this.resizeToCircle = builder.resizeToCircle;
    }

    public int getTargetWidth() {
        return targetWidth;
    }

    public int getTargetHeight() {
        return targetHeight;
    }

    public int getImageResForEmptyUri() {
        return imageResForEmptyUri;
    }

    public int getImageResOnLoading() {
        return imageResOnLoading;
    }

    public int getImageResOnFail() {
        return imageResOnFail;
    }

    public Bitmap.Config getConfig() {
        return config;
    }

    public boolean isResizeToCircle() {
        return resizeToCircle;
    }

    public static class Builder{
        private int targetWidth = -1;
        private int targetHeight = -1;
        private int imageResForEmptyUri = -1;
        private int imageResOnLoading = -1;
        private int imageResOnFail = -1;
        private Bitmap.Config config = Bitmap.Config.RGB_565;
        private boolean resizeToCircle = false;

        public Builder showImageOnLoading(int imageRes) {
            this.imageResOnLoading = imageRes;
            return this;
        }

        public Builder showImageForEmptyUri(int imageRes) {
            this.imageResForEmptyUri = imageRes;
            return this;
        }

        public Builder showImageOnFail(int imageRes) {
            this.imageResOnFail = imageRes;
            return this;
        }

        public Builder displayWidth(int width){
            this.targetWidth = width;
            return this;
        }

        public Builder displayHeight(int height){
            this.targetHeight = height;
            return this;
        }

        public Builder configBitmap(Bitmap.Config config){
            if(config != null){
                this.config = config;
            }
            return this;
        }

        public Builder resizeToCircle(boolean resizeToCircle){
            this.resizeToCircle = resizeToCircle;
            return this;
        }

        public DisplayOptions build() {
            return new DisplayOptions(this);
        }
    }

}
