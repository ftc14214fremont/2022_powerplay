/*
 * Copyright (c)  3/19/2021. FTC Team 14214 NvyUs
 * This code is very epic
 */

package org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot;

public final class Constants
{
    public static final double highGoalSpeed        = 0.8;
    public static final double powerShotSpeed       = 0.65;
    public static final double WOBBLE_OPEN_POSITION = 0.5;
    
    //Encoder counts
    public static final double MAX_COUNTS_PER_SECOND     = 2800;
    public static final double COUNTS_PER_INCH           = 32.30659664;
    public static final double WOBBLE_ARM_COUNTS_PER_REV = 4275.6;
    public static final double WOBBLE_ARM_COUNTS_PER_DEG = WOBBLE_ARM_COUNTS_PER_REV / 360;
    
    //Servo Positions
    //Open means not in contact with anything, closed means touching
    public static final double FLAP_OPEN_POSITION      = 0.97;
    public static final double FLAP_CLOSED_POSITION    = 0.7;
    public static final double WOBBLE_CLOSED_POSITION  = 1;
    public static final double GUIDE_OPEN_POSITION     = 0.99;
    public static final double GUIDE_CLOSED_POSITION   = 0.87;
    //Start and end deceleration distances
    public static final double INITIAL_SLOW            = 6;
    public static final double FINAL_SLOW              = 16;
    public static final double INITIAL_SLOW_VELOCITY   = 0.46;
    public static final double FINAL_SLOW_VELOCITY     = 0.26;
    public static final long   PAUSE_BETWEEN_MOVEMENTS = 200;
    //angle correction
    public static final double KP_CORRECTION           = 0.01;
    public static final double KI_CORRECTION           = 0;
    public static final double KD_CORRECTION           = 0;
    public static final double FORWARD_OFFSET          = 0;
    public static final double BACKWARD_OFFSET         = 0;
    public static final double STRAFE_LEFT_OFFSET      = 0;
    public static final double STRAFE_RIGHT_OFFSET     = 0;
    
    //restrict instantiation
    private Constants()
    {
    }
    
    public enum DriveMode
    {
        FORWARD, BACKWARD, STRAFE_LEFT, STRAFE_RIGHT, ROTATE_CW, ROTATE_CCW
    }
}
