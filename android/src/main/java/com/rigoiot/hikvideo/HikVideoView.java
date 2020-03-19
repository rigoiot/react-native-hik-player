package com.rigoiot.hikvideo;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Gravity;
import android.view.SurfaceView;
import android.widget.FrameLayout;
import android.util.Log;

import com.facebook.react.uimanager.ThemedReactContext;
import com.rigoiot.hikvideo.utils.HikUtil;

public final class HikVideoView extends FrameLayout {
    private static final String TAG = "HikVideoView";
    //----------------------------------------------------------------------------------------------
    SurfaceView surfaceView; 
    //----------------------------------------------------------------------------------------------
    private static final int PLAY_HIK_STREAM_CODE = 1001; 
    //----------------------------------------------------------------------------------------------
 
    private Handler mHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case PLAY_HIK_STREAM_CODE: 
                    hikUtil.playOrStopStream(); 
                default:
                    break;
            }
            return false;
        }
    });

    private Handler iHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case PLAY_HIK_STREAM_CODE:  
                    hikUtil.loginDevice(mHandler, PLAY_HIK_STREAM_CODE); 
                default:
                    break;
            }
            return false;
        }
    });
    
    private HikUtil hikUtil; 
 

    public HikVideoView(final ThemedReactContext themedReactContext) {
        super(themedReactContext);  
        surfaceView = new SurfaceView(themedReactContext);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT);
        params.gravity = Gravity.CENTER;
        surfaceView.setLayoutParams(params);
        addView(surfaceView);
    }


    public void onDropView() { 
        if (hikUtil != null){
            hikUtil.playOrStopStream();
        }
    }

    public void loadView(String ip, int port, String user, String psd, int channel) {  

        // Log.e(TAG, " -------!"+ip+ port+ user+ psd+ channel);
        HikUtil.initSDK();
        hikUtil = new HikUtil();
        hikUtil.initView(surfaceView, iHandler, PLAY_HIK_STREAM_CODE);
        hikUtil.setDeviceData(ip, port, user, psd, channel); 
        // hikUtil.loginDevice(mHandler, PLAY_HIK_STREAM_CODE);  
    }

    public void ptzControl(String command, int dwStop, int dwSpeed) {
        if (hikUtil != null){
            hikUtil.ptzControl(command, dwStop, dwSpeed);
        }
    }
}
