package com.zjy.audiovisualize.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.Nullable;

import com.zjy.audiovisualize.R;

import static com.zjy.audiovisualize.constants.VisualizeMode.HORIZONTAL_LINE_BOTTOM;
import static com.zjy.audiovisualize.constants.VisualizeMode.HORIZONTAL_LINE_TOP;

/**
 * Date: 2020/11/30
 * Author: Yang
 * Describe: In GRAIN mode, show spectrum base on a horizontal line, with grain above of the center line.
 */
public class GrainVisualizeView extends AudioVisualizeView{

    private int mOrientation;


    public GrainVisualizeView(Context context) {
        super(context);
    }

    public GrainVisualizeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public GrainVisualizeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void handleAttr(TypedArray typedArray) {
        mOrientation = typedArray.getInteger(R.styleable.AudioVisualizeView_visualize_orientation, HORIZONTAL_LINE_TOP);
    }

    @Override
    protected void drawChild(Canvas canvas) {
        mStrokeWidth = (mRect.width() - (mSpectrumCount - 1) * mItemMargin) / mSpectrumCount * 1.0f;
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setStyle(Paint.Style.FILL);
        for (int i = 0; i < mSpectrumCount; i++) {
            switch (mOrientation) {
                case HORIZONTAL_LINE_TOP:
                    drawGrain(canvas, mRect.width() * i / mSpectrumCount, mRect.height() / 2, 2 + mRect.height() / 2 - mRawAudioBytes[i]);
                    break;
                case HORIZONTAL_LINE_BOTTOM:
                    drawGrain(canvas, mRect.width() * i / mSpectrumCount, mRect.height() / 2, 2 + mRect.height() / 2 + mRawAudioBytes[i]);
                    break;
                default:
                    break;
            }
        }
    }

    private void drawGrain(final Canvas canvas, final float x, float startY, float endY) {
        canvas.drawPoint(x, endY, mPaint);
        canvas.drawPoint(x, mRect.height() / 4 + endY/2, mPaint);
    }
}
