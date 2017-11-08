package com.conan.gankimitation.imageloader;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.BitmapRequestBuilder;
import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;
import com.conan.gankimitation.utils.LogUtil;

import javax.inject.Singleton;

/**
 * Description：Glide图片下载器
 * Created by：JasmineBen
 * Time：2017/11/2
 */
@Singleton
public final class GlideImageFetcher implements IFetcher {

    private static final String TAG = GlideImageFetcher.class.getSimpleName();
    @Override
    public void displayImage(Context context, ImageView imageView, String url, DisplayOptions options,
                             final ImageLoaderListener listener) {
        DrawableTypeRequest request = Glide.with(context).load(url);
        display(imageView, options, listener, request);
    }

    @Override
    public void displayImage(Context context, ImageView imageView, int resourceId, DisplayOptions options, final ImageLoaderListener listener) {
        DrawableTypeRequest request = Glide.with(context).load(resourceId);
        display(imageView, options, listener, request);
    }

    @Override
    public void displayGif(Context context, ImageView imageView, String url, DisplayOptions options, ImageLoaderListener listener) {
        DrawableTypeRequest request = Glide.with(context).load(url);
        displayGif(imageView, options, listener, request);
    }

    private void display(final ImageView imageView, DisplayOptions options, final ImageLoaderListener listener, DrawableTypeRequest request) {
        if (options == null) {
            options = DisplayOptionsCreator.getDefault();
        }
        final int defaultLoadingPic = options.getImageResOnLoading();
        final int defaultLoadingFailPic = options.getImageResOnFail();
        BitmapTypeRequest bitmapTypeRequest = request.asBitmap();
        BitmapRequestBuilder bitmapRequestBuilder = bitmapTypeRequest.diskCacheStrategy(DiskCacheStrategy.ALL);
        if(Bitmap.Config.ARGB_8888 == options.getConfig()) {
            bitmapRequestBuilder.format(DecodeFormat.PREFER_ARGB_8888);
        }
        final boolean isResizeToCircle = options.isResizeToCircle();
        BitmapImageViewTarget target = new BitmapImageViewTarget(imageView) {
            @Override
            protected void setResource(Bitmap resource) {
                if (isResizeToCircle) {
                    getView().setImageDrawable(new CircleDrawable(resource));
                } else {
                    super.setResource(resource);
                }
            }

            @Override
            public void onLoadStarted(Drawable placeholder) {
                LogUtil.i(TAG,"onLoadStarted");
                if (defaultLoadingPic > 0) {
                    super.onLoadStarted(placeholder);
                }
                if (listener != null) {
                    listener.onLoadStart();
                }
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                LogUtil.i(TAG,"onLoadFailed");
                if (defaultLoadingFailPic > 0) {
                    super.onLoadFailed(e, errorDrawable);
                }
                if (listener != null) {
                    listener.onLoadFail(e);
                }
            }

            @Override
            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                LogUtil.i(TAG,"onResourceReady");
                super.onResourceReady(resource, glideAnimation);
                if (listener != null) {
                    listener.onLoadComplete(resource);
                }
            }
        };
        if (options.getTargetHeight() > 0 && options.getTargetWidth() > 0) {
            bitmapRequestBuilder.override(options.getTargetWidth(), options.getTargetHeight());
        }
        if (options.getImageResOnFail() > 0) {
            bitmapRequestBuilder.error(options.getImageResOnFail());
        }
        if (options.getImageResOnLoading() > 0) {
            bitmapRequestBuilder.placeholder(options.getImageResOnLoading());
        }
        bitmapRequestBuilder.into(target);
    }

    private void displayGif(final ImageView imageView, DisplayOptions options, final ImageLoaderListener listener, DrawableTypeRequest request) {
        if (options == null) {
            options = DisplayOptionsCreator.getDefault();
        }
        final int defaultLoadingPic = options.getImageResOnLoading();
        final int defaultLoadingFailPic = options.getImageResOnFail();
        DrawableRequestBuilder gifRequestBuilder = request.diskCacheStrategy(DiskCacheStrategy.ALL);
        GlideDrawableImageViewTarget target = new GlideDrawableImageViewTarget(imageView) {

            @Override
            public void onLoadStarted(Drawable placeholder) {
                super.onLoadStarted(placeholder);
                if (defaultLoadingPic > 0) {
                    super.onLoadStarted(placeholder);
                }
                if (listener != null) {
                    listener.onLoadStart();
                }
            }

            @Override
            public void onLoadFailed(Exception e, Drawable errorDrawable) {
                super.onLoadFailed(e, errorDrawable);
                if (defaultLoadingFailPic > 0) {
                    super.onLoadFailed(e, errorDrawable);
                }
                if (listener != null) {
                    listener.onLoadFail(e);
                }
            }

            @Override
            public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> animation) {
                super.onResourceReady(resource, animation);
                if (listener != null) {
                    listener.onLoadComplete(resource);
                }
            }
        };
        if (options.getTargetHeight() > 0 && options.getTargetWidth() > 0) {
            gifRequestBuilder.override(options.getTargetWidth(), options.getTargetHeight());
        }
        if (options.getImageResOnFail() > 0) {
            gifRequestBuilder.error(options.getImageResOnFail());
        }
        if (options.getImageResOnLoading() > 0) {
            gifRequestBuilder.placeholder(options.getImageResOnLoading());
        }
        gifRequestBuilder.into(target);
    }

    @Override
    public void clearDiskCache(Context context) {
        Glide.get(context).clearDiskCache();
    }

    private class CircleDrawable extends Drawable {
        protected float radius;
        protected final RectF mRect = new RectF();
        protected final RectF mBitmapRect;
        protected final BitmapShader bitmapShader;
        protected final Paint paint;

        public CircleDrawable(Bitmap bitmap) {
            this.radius = (float) (Math.min(bitmap.getWidth(), bitmap.getHeight()) / 2);
            this.bitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
            this.mBitmapRect = new RectF(0.0F, 0.0F, (float) bitmap.getWidth(), (float) bitmap.getHeight());
            this.paint = new Paint();
            this.paint.setAntiAlias(true);
            this.paint.setShader(this.bitmapShader);
            this.paint.setFilterBitmap(true);
            this.paint.setDither(true);
        }

        @Override
        protected void onBoundsChange(Rect bounds) {
            super.onBoundsChange(bounds);
            this.mRect.set(0.0F, 0.0F, (float) bounds.width(), (float) bounds.height());
            this.radius = (float) (Math.min(bounds.width(), bounds.height()) / 2);
            Matrix shaderMatrix = new Matrix();
            shaderMatrix.setRectToRect(this.mBitmapRect, this.mRect, Matrix.ScaleToFit.FILL);
            this.bitmapShader.setLocalMatrix(shaderMatrix);
        }

        @Override
        public void draw(Canvas canvas) {
            canvas.drawCircle(this.radius, this.radius, this.radius, this.paint);
        }

        @Override
        public int getOpacity() {
            return PixelFormat.TRANSLUCENT;
        }

        @Override
        public void setAlpha(int alpha) {
            this.paint.setAlpha(alpha);
        }

        @Override
        public void setColorFilter(ColorFilter cf) {
            this.paint.setColorFilter(cf);
        }
    }

}
