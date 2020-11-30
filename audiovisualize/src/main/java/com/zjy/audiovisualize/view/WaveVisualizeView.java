package com.zjy.audiovisualize.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

/**
 * Date: 2020/11/30
 * Author: Yang
 * Describe:
 */
public class WaveVisualizeView extends AudioVisualizeView{

    public WaveVisualizeView(Context context) {
        super(context);
    }

    public WaveVisualizeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public WaveVisualizeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void handleAttr(TypedArray typedArray) {

    }

    @Override
    protected void drawChild(Canvas canvas) {
        mStrokeWidth = (mRect.width() - (mSpectrumCount - 1) * mItemMargin) / mSpectrumCount * 1.0f;
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.FILL);
        mPath.moveTo(0, centerY);

        for (int i = 0; i < mSpectrumCount; i++) {
            if (mRawAudioBytes[i] < 0) {
                mRawAudioBytes[i] = 127;
            }
            mPath.lineTo(mRect.width() * i / mSpectrumCount, 2 + mRect.height() / 2 + mRawAudioBytes[i]);
        }
        mPath.lineTo(mRect.width(), centerY);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
        mPath.reset();
    }
}
