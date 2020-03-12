package com.jack.facedetect.presenter;

import com.megvii.facepp.sdk.Facepp;

/*
 * author: Jack
 * created time:2020/3/12 21:40
 * description: 处理人脸
 */
public class HandleFace {
    private FaceChange mFaceChange = new FaceChange();
    private boolean hasFace = false;

    /**
     * 处理人脸
     *
     * @param faces 视频流中的每一帧图片包含的人脸数据
     */
    public void handle(Facepp.Face[] faces) {
        if (faces != null && faces.length > 0) {
            if (!hasFace) {
                hasFace = true;
                mFaceChange.onShow();
            }
        } else {
            if (hasFace) {
                hasFace = false;
                mFaceChange.onDismiss();
            }
        }
    }
}
