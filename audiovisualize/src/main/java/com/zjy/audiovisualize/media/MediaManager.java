package com.zjy.audiovisualize.media;

import android.content.Context;
import android.media.MediaPlayer;

import com.zjy.audiovisualize.utils.LogUtils;

/**
 * Date: 2020/11/24
 * Author: Yang
 * Describe: manager all about the media play
 */
public class MediaManager {

    private MediaPlayer mediaPlayer;
    private Context mContext;
    private MediaManagerListener mListener;

    private MediaManager(){}

    public MediaManager(Context context) {
        mContext = context;
    }


    /**
     * 播放音频
     *
     * @param raw 资源文件id
     */
    public void doPlay(final int raw) {
        try {
            mediaPlayer = MediaPlayer.create(mContext, raw);
            if (mediaPlayer == null) {
                LogUtils.d("mediaPlayer is null");
                return;
            }

            mediaPlayer.setOnErrorListener(null);
            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mediaPlayer) {
                    if (mListener != null) {
                        mListener.onPrepare();
                    }
                }
            });
            mediaPlayer.start();
        } catch (Exception e) {
            LogUtils.d(e.getMessage());
        }
    }

    public int getMediaPlayerId() {
        return mediaPlayer.getAudioSessionId();
    }

    public void setMediaManagerListener(MediaManagerListener listener) {
        mListener = listener;
    }

}
