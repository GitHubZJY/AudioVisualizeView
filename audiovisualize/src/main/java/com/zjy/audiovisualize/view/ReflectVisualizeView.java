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
 * Describe: In REFLECT mode, show spectrum base on a horizontal line, different with HORIZONTAL_LINE mode, it will show both sides of line.
 */
public class ReflectVisualizeView extends AudioVisualizeView{

    public ReflectVisualizeView(Context context) {
        super(context);
    }

    public ReflectVisualizeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ReflectVisualizeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        for (int i = 0; i < mSpectrumCount; i++) {
            if (mRawAudioBytes[i] < 0) {
                mRawAudioBytes[i] = 127;
            }
            canvas.drawLine(mRect.width() * i / mSpectrumCount, mRect.height() / 2,mRect.width() * i / mSpectrumCount, 2 + mRect.height() / 2 - mSpectrumRatio * mRawAudioBytes[i], mPaint);
            canvas.drawLine(mRect.width() * i / mSpectrumCount, mRect.height() / 2,mRect.width() * i / mSpectrumCount, 2 + mRect.height() / 2 + mSpectrumRatio * mRawAudioBytes[i], mPaint);
        }
    }
}
