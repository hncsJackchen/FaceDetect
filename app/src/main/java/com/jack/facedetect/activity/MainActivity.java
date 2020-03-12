package com.jack.facedetect.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.jack.facedetect.R;
import com.jack.facedetect.bean.FaceActionInfo;
import com.jack.facedetect.util.ConUtil;
import com.jack.facedetect.util.Util;
import com.megvii.facepp.sdk.Facepp;
import com.megvii.licensemanager.sdk.LicenseManager;


/*
 * author: Jack
 * created time:2020/2/22 15:16
 * description: 主界面
 */
public class MainActivity extends Activity implements View.OnClickListener {

    private Button mBtnStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBtnStart = findViewById(R.id.btn_main_start);
        mBtnStart.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_main_start:
                start();
                break;
        }
    }

    /**
     * 开始检测
     */
    private void start() {
        auth();
    }

    private void auth() {
        //type;1-联网授权，2-非联网授权
        int type = Facepp.getSDKAuthType(ConUtil.getFileContent(this, R.raw.megviifacepp_0_5_2_model));
        if (type == 2) {// 非联网授权
            authState(true, 0, "非联网授权");
            return;
        }

//        againWarrantyBtn.setVisibility(View.GONE);
//        WarrantyText.setText(getResources().getString(R.string.auth_progress));
//        WarrantyBar.setVisibility(View.VISIBLE);

        //联网授权
        final LicenseManager licenseManager = new LicenseManager(this);

//        licenseManager.setExpirationMillis(Facepp.getApiExpirationMillis(this, ConUtil.getFileContent(this, R.raw
//				.megviifacepp_0_5_2_model)));

        String uuid = ConUtil.getUUIDString(MainActivity.this);
        long apiName = Facepp.getApiName();

        licenseManager.setAuthTimeBufferMillis(0);
        licenseManager.takeLicenseFromNetwork(Util.CN_LICENSE_URL, uuid, Util.API_KEY, Util.API_SECRET, apiName,
                "1", new LicenseManager.TakeLicenseCallback() {
                    @Override
                    public void onSuccess() {
                        authState(true, 0, "联网授权-成功");
                    }

                    @Override
                    public void onFailed(int i, byte[] bytes) {
                        if (TextUtils.isEmpty(Util.API_KEY) || TextUtils.isEmpty(Util.API_SECRET)) {
                            if (!ConUtil.isReadKey(MainActivity.this)) {
                                authState(false, 1001, "");
                            } else {
                                authState(false, 1001, "");
                            }
                        } else {
                            String msg = "";
                            if (bytes != null && bytes.length > 0) {
                                msg = new String(bytes);
                            }
                            authState(false, i, msg);
                        }
                    }
                });
    }


    private void authState(boolean isSuccess, int code, String msg) {
        if (isSuccess) {
//            Intent intent = new Intent();
//            intent.setClass(this, FaceppActionActivity.class);
//            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);//If set, and the activity being launched is already running in the current task, then instead of launching a new instance of that activity,all of the other activities on top of it will be closed and this Intent will be delivered to the (now on top) old activity as a new Intent.
//            startActivity(intent);

            openDetectView();
            finish();
        } else {
//            WarrantyBar.setVisibility(View.GONE);
//            againWarrantyBtn.setVisibility(View.VISIBLE);
            if (code == 1001) {
//                WarrantyText.setText(Html.fromHtml("<u>" + getResources().getString(R.string.key_secret) + "</u>"));
//                WarrantyText.setOnClickListener(onlineClick);

                Toast.makeText(this, "请到官网申请并填写API_KEY和API_SECRET", Toast.LENGTH_SHORT).show();
            } else {
//                WarrantyText.setText(Html.fromHtml("<u>" + "code=" + code + "，msg=" + msg + "</u>"));
//                WarrantyText.setOnClickListener(onlineClick);

                Toast.makeText(this, "code=" + code + ",msg=" + msg, Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void openDetectView() {
        FaceActionInfo faceActionInfo = new FaceActionInfo();
        faceActionInfo.isStartRecorder = false;
        faceActionInfo.is3DPose = false;
        faceActionInfo.isdebug = false;
        faceActionInfo.isROIDetect = false;
        faceActionInfo.is106Points = false;
        faceActionInfo.isBackCamera = false;
        faceActionInfo.faceSize = 40;
        faceActionInfo.interval = 30;
        faceActionInfo.resolutionMap = null;
        faceActionInfo.isFaceProperty = false;
        faceActionInfo.isOneFaceTrackig = false;
        faceActionInfo.trackModel = "Fast";
        faceActionInfo.isFaceCompare = false;

//        startActivityForResult(new Intent(MainActivity.this, OpenglActivity.class).putExtra("FaceAction", faceActionInfo), 101);
        startActivity(new Intent(MainActivity.this, OpenglActivity.class).putExtra("FaceAction", faceActionInfo));
    }
}
