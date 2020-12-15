package com.zjy.audiovisualize.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.media.MediaPlayer;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.zjy.audiovisualize.R;
import com.zjy.audiovisualize.media.MediaManager;
import com.zjy.audiovisualize.media.MediaManagerListener;
import com.zjy.audiovisualize.utils.LogUtils;
import com.zjy.audiovisualize.visualize.VisualizeCallback;
import com.zjy.audiovisualize.visualize.VisualizerHelper;

/**
 * Date: 2020/10/16
 * Author: Yang
 * Describe: a view for visualizing audio, showing spectrum with different ui mode
 */
public abstract class AudioVisualizeView extends View implements MediaManagerListener, VisualizeCallback {

    /**
     * the count of spectrum
     */
    protected int mSpectrumCount;
    /**
     * the margin of adjoin spectrum
     */
    protected float mItemMargin;
    /**
     * ratio of spectrum, between 0.0f - 2.0f
     */
    protected float mSpectrumRatio;
    /**
     * the width of every spectrum
     */
    protected float mStrokeWidth;
    /**
     * the color of drawing spectrum
     */
    protected int mColor;
    /**
     * control enable of visualize
     */
    protected boolean isVisualizationEnabled = true;
    /**
     * audio data transform by hypot
     */
    protected byte[] mRawAudioBytes;

    protected RectF mRect;
    protected Paint mPaint;
    protected Path mPath;
    protected float centerX, centerY;

    protected MediaManager mediaManager;
    protected VisualizerHelper visualizerHelper;

    public AudioVisualizeView(Context context) {
        this(context, null);
    }

    public AudioVisualizeView(Context context,
                              @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public AudioVisualizeView(Context context,
                              @Nullable AttributeSet attrs,
                              int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        handleStyleable(context, attrs, defStyleAttr);
        init();
    }

    private void handleStyleable(Context context, AttributeSet attrs, int defStyle) {
        TypedArray ta = context.getTheme().obtainStyledAttributes(attrs, R.styleable.AudioVisualizeView, defStyle, 0);
        try {
            mColor = ta.getColor(R.styleable.AudioVisualizeView_visualize_color, Color.WHITE);
            mSpectrumCount = ta.getInteger(R.styleable.AudioVisualizeView_visualize_count, 60);
            mSpectrumRatio = ta.getFloat(R.styleable.AudioVisualizeView_visualize_ratio, 1.0f);
            mItemMargin = ta.getDimension(R.styleable.AudioVisualizeView_visualize_item_margin, 12f);
            handleAttr(ta);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ta.recycle();
        }
    }


    protected void init() {
        mStrokeWidth = 5;

        mPaint = new Paint();
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setColor(mColor);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setAntiAlias(true);
        mPaint.setMaskFilter(new BlurMaskFilter(5, BlurMaskFilter.Blur.SOLID));

        mRect = new RectF();
        mPath = new Path();

        mediaManager = new MediaManager(getContext());
        mediaManager.setMediaManagerListener(this);

        visualizerHelper = new VisualizerHelper();
        visualizerHelper.setVisualizeCallback(this);
        visualizerHelper.setVisualCount(mSpectrumCount);
    }

    /**
     * start play raw file
     */
    public void doPlay(final int raw) {
        if (mediaManager != null) {
            mediaManager.doPlay(raw);
        }
    }

    /**
     * start play url file
     */
    public void doPlay(final String filePath) {
        if (mediaManager != null) {
            mediaManager.doPlay(filePath);
        }
    }

    /**
     * play with session Id, which visualize need.
     * @param audioSessionId {@link MediaPlayer#getAudioSessionId()}
     */
    public void playWithSessionId(int audioSessionId) {
        try {
            visualizerHelper.setAudioSessionId(audioSessionId);
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
    }

    @Override
    public void onPrepare() {
        try {
            int mediaPlayerId = mediaManager.getMediaPlayerId();
            visualizerHelper.setAudioSessionId(mediaPlayerId);
        } catch (Exception e) {
            LogUtils.e(e.getMessage());
        }
    }

    @Override
    public void onFftDataCapture(byte[] parseData) {
        if (!isVisualizationEnabled) {
            return;
        }
        mRawAudioBytes = parseData;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int finallyWidth;
        int finallyHeight;
        int wSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        int wSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        int hSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        int hSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        if (wSpecMode == MeasureSpec.EXACTLY) {
            finallyWidth = wSpecSize;
        } else {
            finallyWidth = 500;
        }

        if (hSpecMode == MeasureSpec.EXACTLY) {
            finallyHeight = hSpecSize;
        } else {
            finallyHeight = 500;
        }

        setMeasuredDimension(finallyWidth, finallyHeight);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mRect.set(0, 0, getWidth(), getHeight() - 50);
        centerX = mRect.width() / 2;
        centerY = mRect.height() / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mRawAudioBytes == null) {
            return;
        }
        drawChild(canvas);
    }

    protected abstract void handleAttr(TypedArray typedArray);

    protected abstract void drawChild(Canvas canvas);

    public void setColor(int color) {
        this.mColor = color;
        this.mPaint.setColor(this.mColor);
    }

    /**
     * Enable Visualization
     */
    public void show() {
        this.isVisualizationEnabled = true;
    }

    /**
     * Disable Visualization
     */
    public void hide() {
        this.isVisualizationEnabled = false;
    }

    /**
     * release media player and visualizer
     */
    public void release() {
        if (visualizerHelper != null) {
           visualizerHelper.release();
        }
        if (mediaManager != null) {
            mediaManager.release();
        }
    }

}
