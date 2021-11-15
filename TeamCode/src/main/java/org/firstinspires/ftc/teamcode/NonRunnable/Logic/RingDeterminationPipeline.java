/* * Copyright (c)  3/28/2021. FTC Team 14214 NvyUs * This code is very epic */package org.firstinspires.ftc.teamcode.NonRunnable.Logic;import org.opencv.core.*;import org.opencv.imgproc.Imgproc;import org.openftc.easyopencv.OpenCvPipeline;public class RingDeterminationPipeline extends OpenCvPipeline {    static final Scalar BLUE = new Scalar(0, 0, 0);    static final Scalar GREEN = new Scalar(0, 255, 0);    //decreasing x moves the box to the right    static final Point REGION1_TOPLEFT_ANCHOR_POINT = new Point(185, 98);    //Values which define the location and size of the sample regions    static final int REGION_WIDTH = 45;    static final int REGION_HEIGHT = 45;    public static int avg1;    // Volatile since accessed by OpMode thread w/o synchronization    private static volatile RingPosition ringStack = RingPosition.FOUR_RINGS;    final int FOUR_RING_THRESHOLD = 151;    final int ONE_RING_THRESHOLD = 143;    Point region1_pointA = new Point(REGION1_TOPLEFT_ANCHOR_POINT.x, REGION1_TOPLEFT_ANCHOR_POINT.y);    Point region1_pointB = new Point(REGION1_TOPLEFT_ANCHOR_POINT.x + REGION_WIDTH,            REGION1_TOPLEFT_ANCHOR_POINT.y + REGION_HEIGHT);    Mat region1_Cb;    Mat YCrCb = new Mat();    Mat Cb = new Mat();    public static RingPosition getRingStack() {        return ringStack;    }    @Override    public void init(Mat firstFrame) {        inputToCb(firstFrame);        region1_Cb = Cb.submat(new Rect(region1_pointA, region1_pointB));    }    /*     * This function takes the RGB frame, converts to YCrCb,     * and extracts the Cb channel to the 'Cb' variable     */    void inputToCb(Mat input) {        Imgproc.cvtColor(input, YCrCb, Imgproc.COLOR_RGB2YCrCb);        Core.extractChannel(YCrCb, Cb, 1);    }    @Override    public Mat processFrame(Mat input) {        inputToCb(input);        avg1 = (int) Core.mean(region1_Cb).val[0];        Imgproc.rectangle(input, // Buffer to draw on                region1_pointA, // First point which defines the rectangle                region1_pointB, // Second point which defines the rectangle                BLUE, // The color the rectangle is drawn in                2); // Thickness of the rectangle lines        ringStack = RingPosition.FOUR_RINGS;        if (avg1 > FOUR_RING_THRESHOLD) {            ringStack = RingPosition.FOUR_RINGS;        } else if (avg1 > ONE_RING_THRESHOLD) {            ringStack = RingPosition.ONE_RING;        } else {            ringStack = RingPosition.NONE;        }        Imgproc.rectangle(input, // Buffer to draw on                region1_pointA, // First point which defines the rectangle                region1_pointB, // Second point which defines the rectangle                GREEN, // The color the rectangle is drawn in                -1); // Negative thickness means solid fill        return input;    }    public enum RingPosition {        FOUR_RINGS, ONE_RING, NONE    }}