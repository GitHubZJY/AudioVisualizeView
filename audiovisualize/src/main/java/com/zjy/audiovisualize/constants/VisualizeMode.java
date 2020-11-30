package com.zjy.audiovisualize.constants;

/**
 * Date: 2020/11/25
 * Author: Yang
 * Describe: all constants of the visualize mode
 */
public class VisualizeMode {

    /**
     * In HORIZONTAL_LINE_TOP mode, show spectrum base on a horizontal line, with jumping above of the center line , it's also default visualize mode.
     */
    public final static int HORIZONTAL_LINE_TOP = 1;
    /**
     * In HORIZONTAL_LINE_BOTTOM mode, show spectrum base on a horizontal line, with jumping below of the center line , it's also default visualize mode.
     */
    public final static int HORIZONTAL_LINE_BOTTOM = 2;
    /**
     * In CIRCLE mode, show spectrum base on a circle, with jumping around it.
     */
    public final static int CIRCLE = 3;
    /**
     * In REFLECT mode, show spectrum base on a horizontal line, different with HORIZONTAL_LINE mode, it will show both sides of line.
     */
    public final static int REFLECT = 4;
    /**
     * In WAVE mode, show spectrum base on a horizontal line, it will link all points of spectrum so like a wave.
     */
    public final static int WAVE = 5;
    /**
     * In NET mode, show spectrum base on a circle, with jumping around it and link all points of spectrum so like a net.
     */
    public final static int NET = 6;

}
