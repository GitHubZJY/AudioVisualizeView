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
 * Describe: In NET mode, show spectrum base on a circle, with jumping around it and link all points of spectrum so like a net.
 */
public class NetVisualizeView extends AudioVisualizeView{


    public NetVisualizeView(Context context) {
        super(context);
    }

    public NetVisualizeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public NetVisualizeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void handleAttr(TypedArray typedArray) {

    }

    @Override
    protected void drawChild(Canvas canvas) {
        float radius = 150;
        mStrokeWidth = (float) ((Math.PI * 2 * radius - (mSpectrumCount - 1) * mItemMargin) / mSpectrumCount * 1.0f);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(2);
        canvas.drawCircle(centerX, centerY, radius, mPaint);

        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.FILL);
        mPath.moveTo(0, centerY);
        for (int i = 0; i < mSpectrumCount; i++) {
            if (mRawAudioBytes[i] < 0) {
                mRawAudioBytes[i] = 127;
            }

            double angel = ((360d/ mSpectrumCount *1.0d) * (i+1));
            double startX = centerX + (radius + mStrokeWidth/2) * Math.sin(Math.toRadians(angel));
            double startY = centerY + (radius + mStrokeWidth/2) * Math.cos(Math.toRadians(angel));
            double stopX = centerX + (radius + mStrokeWidth/2 + mSpectrumRatio * mRawAudioBytes[i]) * Math.sin(Math.toRadians(angel));
            double stopY = centerY + (radius + mStrokeWidth/2 + mSpectrumRatio * mRawAudioBytes[i]) * Math.cos(Math.toRadians(angel));
            canvas.drawLine((float) startX, (float) startY, (float) stopX, (float) stopY, mPaint);
            if (i == 0) {
                mPath.moveTo((float) startX, (float) startY);
            }
            mPath.lineTo((float) stopX, (float) stopY);
        }
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawPath(mPath, mPaint);
        mPath.reset();
    }
}
