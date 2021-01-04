package com.zjy.audiovisualize.visualize;

import android.media.audiofx.Visualizer;

/**
 * Date: 2020/11/24
 * Author: Yang
 * Describe: hold the visualizer and callback something
 */
public class VisualizerHelper {


    private Visualizer mVisualizer;
    private VisualizeCallback mCallback;
    private int mCount;

    public void setVisualCount(int count) {
        mCount = count;
    }

    public void setVisualizeCallback(VisualizeCallback callback) {
        mCallback = callback;
    }

    /**
     * Sets the audio session id for the currently playing audio
     *
     * @param audioSessionId of the media to be visualised
     */
    public void setAudioSessionId(int audioSessionId) {
        if (mVisualizer != null)
            release();

        mVisualizer = new Visualizer(audioSessionId);
        mVisualizer.setCaptureSize(Visualizer.getCaptureSizeRange()[1]);

        mVisualizer.setDataCaptureListener(new Visualizer.OnDataCaptureListener() {
            @Override
            public void onWaveFormDataCapture(Visualizer visualizer, byte[] bytes,
                                              int samplingRate) {
            }

            @Override
            public void onFftDataCapture(Visualizer visualizer, byte[] fft,
                                         int samplingRate) {
                float[] model = new float[fft.length / 2 + 1];
                model[0] = (float) Math.abs(fft[1]);
                int j = 1;

                for (int i = 2; i < mCount *2;) {

                    model[j] = (float) Math.hypot(fft[i], fft[i + 1]);
                    i += 2;
                    j++;
                    model[j] = (float) Math.abs(fft[j]);
                }
                if (mCallback != null) {
                    mCallback.onFftDataCapture(model);
                }
            }
        }, Visualizer.getMaxCaptureRate() / 2, false, true);

        mVisualizer.setEnabled(true);
    }

    /**
     * Releases the visualizer
     */
    public void release() {
        if (mVisualizer != null)
            mVisualizer.release();
    }
}
