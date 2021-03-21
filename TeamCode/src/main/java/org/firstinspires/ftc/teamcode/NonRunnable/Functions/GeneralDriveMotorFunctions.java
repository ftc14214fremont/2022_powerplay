/*
 * Copyright (c)  3/21/2021. FTC Team 14214 NvyUs
 * This code is very epic
 */

package org.firstinspires.ftc.teamcode.NonRunnable.Functions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.DriveMode;
import org.jetbrains.annotations.NotNull;

import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.MAX_COUNTS_PER_SECOND;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.PAUSE_BETWEEN_MOVEMENTS;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.driveMotors;

public final class GeneralDriveMotorFunctions
{
    private GeneralDriveMotorFunctions()
    {
    }
    
    /*
     * - When looking at the front of the goBILDA drive shaft, clockwise is
     *   FORWARD, counter-clockwise is REVERSE
     * - These directions only work assuming a bevel gear configuration on the drivetrain
     */
    
    public static void setDriveDirection(DriveMode driveMode)
    {
        for (int i = 0; i < 4; i++)
        {
            driveMotors[i].setDirection(driveMode.getDirections()[i]);
        }
    }
    
    public static void stopDrivingRobot(@NotNull LinearOpMode opMode)
    {
        setDriveMotorsVelocity(0);
        opMode.sleep(PAUSE_BETWEEN_MOVEMENTS);
    }
    
    public static void setDriveMotorsVelocity(double velocity)
    {
        for (DcMotorEx dcMotorEx : driveMotors)
        {
            setVelocity(dcMotorEx, velocity);
        }
    }
    
    public static void setDriveMotorsVelocity(@NotNull double[] velocityArray)
    {
        for (int i = 0; i < velocityArray.length; i++)
        {
            setVelocity(driveMotors[i], velocityArray[i]);
        }
    }
    
    public static void setVelocity(@NotNull DcMotorEx motor, double velocity)
    {
        motor.setVelocity(velocity * MAX_COUNTS_PER_SECOND);
    }
    
    public static void resetDriveEncoders()
    {
        for (DcMotorEx dcMotorEx : driveMotors)
        {
            dcMotorEx.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    }
}