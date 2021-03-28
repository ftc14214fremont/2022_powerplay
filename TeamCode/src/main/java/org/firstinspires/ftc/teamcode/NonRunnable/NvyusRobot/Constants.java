/*
 * Copyright (c)  3/28/2021. FTC Team 14214 NvyUs
 * This code is very epic
 */

package org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;

public final class Constants
{
    //shooter speeds
    public static final double HIGH_GOAL_SPEED  = 0.8;
    public static final double POWER_SHOT_SPEED = 0.65;
    public static final double IDLE_SPEED       = -0.1;
    
    //Encoder counts
    public static final double MAX_COUNTS_PER_SECOND     = 2800;
    public static final double COUNTS_PER_INCH           = 32.30659664;
    public static final double WOBBLE_ARM_COUNTS_PER_REV = 4275.6;
    public static final double WOBBLE_ARM_COUNTS_PER_DEG = WOBBLE_ARM_COUNTS_PER_REV / 360;
    
    //Servo Positions
    //Open means not in contact with anything, closed means touching
    public static final double FLAP_OPEN_POSITION      = 0.97;
    public static final double FLAP_CLOSED_POSITION    = 0.7;
    public static final double WOBBLE_OPEN_POSITION    = 0.5;
    public static final double WOBBLE_CLOSED_POSITION  = 1;
    public static final double GUIDE_OPEN_POSITION     = 0.99;
    public static final double GUIDE_CLOSED_POSITION   = 0.85;
    public static final double BLOCKER_OPEN_POSITION   = 0.45;
    public static final double BLOCKER_CLOSED_POSITION = 0;
    
    //Servo times
    public static final int FLAP_MOVEMENT_MS = 300;
    
    //Start and end deceleration distances
    public static final double INITIAL_SLOW            = 6;
    public static final double FINAL_SLOW              = 16;
    public static final double INITIAL_SLOW_VELOCITY   = 0.46;
    public static final double FINAL_SLOW_VELOCITY     = 0.26;
    public static final long   PAUSE_BETWEEN_MOVEMENTS = 200;
    
    //angle correction
    public static final double KP_CORRECTION       = 0.01;
    public static final double KI_CORRECTION       = 0;
    public static final double KD_CORRECTION       = 0;
    public static final double FORWARD_OFFSET      = 0;
    public static final double BACKWARD_OFFSET     = 0;
    public static final double STRAFE_LEFT_OFFSET  = 0;
    public static final double STRAFE_RIGHT_OFFSET = 0;
    
    public enum DriveMode
    {
        STRAIGHT_FORWARD(new Direction[]{FORWARD, REVERSE, FORWARD, REVERSE}),
        BACKWARD(new Direction[]{REVERSE, FORWARD, REVERSE, FORWARD}),
        STRAFE_LEFT(new Direction[]{REVERSE, REVERSE, FORWARD, FORWARD}),
        STRAFE_RIGHT(new Direction[]{FORWARD, FORWARD, REVERSE, REVERSE}),
        @SuppressWarnings ("unused") ROTATE_CW(new Direction[]{FORWARD, FORWARD, FORWARD, FORWARD}),
        ROTATE_CCW(new Direction[]{REVERSE, REVERSE, REVERSE, REVERSE});
        
        private final Direction[] directions;
        
        DriveMode(Direction[] directions)
        {
            this.directions = directions;
        }
        
        public Direction[] getDirections()
        {
            return directions;
        }
    }
    
    private Constants()
    {
    }
    
    public enum ShooterState
    {
        SHOOTER_START, SHOOTER_OPEN_FLAP, SHOOTER_CLOSE_FLAP
    }
}
