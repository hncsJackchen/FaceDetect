package com.jack.facedetect;

import android.app.Application;
import android.content.Context;

/*
 * author: Jack
 * created time:2020/3/12 22:02
 * description:
 */
public class FaceApplication extends Application {
    private static Context mContext;
    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }
}
