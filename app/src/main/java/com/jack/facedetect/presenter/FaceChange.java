package com.jack.facedetect.presenter;

import android.media.AudioManager;
import android.media.SoundPool;
import android.util.Log;

import com.jack.facedetect.FaceApplication;
import com.jack.facedetect.R;

/*
 * author: Jack
 * created time:2020/3/12 21:44
 * description: 人脸变化
 */
public class FaceChange implements IFaceChange {
    private static final String TAG = "FaceChange";
    private final SoundPool soundPool;
    private final int soundID;
    private final int soundID_2;

    public FaceChange() {
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
        soundID = soundPool.load(FaceApplication.getContext(), R.raw.sound_1, 1);
        soundID_2 = soundPool.load(FaceApplication.getContext(), R.raw.sound_2, 1);
    }

    @Override
    public void onShow() {
        Log.d(TAG, "人脸出现");
        //第一个参数soundID
        //第二个参数leftVolume为左侧音量值（范围= 0.0到1.0）
        //第三个参数rightVolume为右的音量值（范围= 0.0到1.0）
        //第四个参数priority 为流的优先级，值越大优先级高，影响当同时播放数量超出了最大支持数时SoundPool对该流的处理
        //第五个参数loop 为音频重复播放次数，0为值播放一次，-1为无限循环，其他值为播放loop+1次
        //第六个参数 rate为播放的速率，范围0.5-2.0(0.5为一半速率，1.0为正常速率，2.0为两倍速率)
        soundPool.play(soundID, 1, 1, 1, 0, 1);
    }

    @Override
    public void onDismiss() {
        Log.d(TAG, "人脸消失");
        soundPool.play(soundID_2, 1, 1, 1, 0, 1);
    }

}
