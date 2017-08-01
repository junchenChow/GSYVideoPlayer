package com.shuyu.gsyvideoplayer.utils;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.view.OrientationEventListener;

import com.shuyu.gsyvideoplayer.video.GSYBaseVideoPlayer;

public class OrientationUtils {
    private int orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

    private Activity mActivity;
    private GSYBaseVideoPlayer mGsyVideoPlayer;
    private OrientationEventListener mOrientationEventListener;

    private int mIsLand;
    private int mCurrOrientation;
    private boolean mEnable = true;

    public OrientationUtils(Activity activity, GSYBaseVideoPlayer gsyVideoPlayer) {
        this.mActivity = activity;
        this.mGsyVideoPlayer = gsyVideoPlayer;
        init();
    }

    private int convert2Orientation(int rotation) {
        if ((rotation > 45) && (rotation <= 135)) {
            orientation = ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE;
        } else if ((rotation > 225) && (rotation <= 315)) {
            orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
        }
        return orientation;
    }

    private void init() {
        mOrientationEventListener = new OrientationEventListener(mActivity) {
            @Override
            public void onOrientationChanged(int rotation) {
                if (rotation == OrientationEventListener.ORIENTATION_UNKNOWN) {
                    return;
                }
                int orientation = convert2Orientation(rotation);
                if (orientation == mCurrOrientation) {
                    return;
                }
                mCurrOrientation = orientation;
                if (mCurrOrientation != ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
                    mActivity.setRequestedOrientation(mCurrOrientation);
                }
            }
        };
    }

    /**
     * 点击切换的逻辑，比如竖屏的时候点击了就是切换到横屏不会受屏幕的影响
     */
    public void resolveByClick() {
        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    /**
     * 列表返回的样式判断。因为立即旋转会导致界面跳动的问题
     */
    public int backToPortraitVideo() {
        mActivity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        return 0;
    }

    public boolean isEnable() {
        return mEnable;
    }

    public void setEnable(boolean enable) {
        this.mEnable = enable;
        if (mEnable) {
            mOrientationEventListener.enable();
        } else {
            mOrientationEventListener.disable();
        }
    }

    public void releaseListener() {
        if (mOrientationEventListener != null) {
            mOrientationEventListener.disable();
            mOrientationEventListener = null;
        }
    }

    public int getIsLand() {
        return mIsLand;
    }
}

