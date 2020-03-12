package com.jack.facedetect.presenter;

/*
 * author: Jack
 * created time:2020/3/12 21:36
 * description: 人脸变化
 */
public interface IFaceChange {
    /**
     * 出现人脸
     */
    void onShow();

    /**
     * 人脸消失
     */
    void onDismiss();
}
