/*
 * Copyright (c)  3/19/2021. FTC Team 14214 NvyUs
 * This code is very epic
 */

package org.firstinspires.ftc.teamcode.NonRunnable.Functions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.setVelocity;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.*;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.wobble;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.wobbleArm;

public final class WobbleArmFunctions
{
    private WobbleArmFunctions()
    {
    }
    
    public static void moveWobbleArmDown(LinearOpMode opMode)
    {
        wobbleArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        wobbleArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        
        while ((wobbleArm.getCurrentPosition() <= wobbleArmDegreesToCounts(183)) && opMode.opModeIsActive())
        {
            setVelocity(wobbleArm, 0.6);
        }
        setVelocity(wobbleArm, 0);
    }
    
    public static double wobbleArmDegreesToCounts(double degrees)
    {
        return degrees * WOBBLE_ARM_COUNTS_PER_DEG;
    }
    
    public static void moveWobbleArmUp(LinearOpMode opMode)
    {
        wobbleArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        wobbleArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        
        while ((wobbleArm.getCurrentPosition() >= wobbleArmDegreesToCounts(-183)) && opMode.opModeIsActive())
        {
            setVelocity(wobbleArm, -0.6);
        }
        setVelocity(wobbleArm, 0);
    }
    
    public static void releaseWobbleGoal(LinearOpMode opMode)
    {
        wobble.setPosition(WOBBLE_OPEN_POSITION);
        opMode.sleep(400);
        opMode.idle();
    }
    
    public static void gripWobbleGoal(LinearOpMode opMode)
    {
        wobble.setPosition(WOBBLE_CLOSED_POSITION);
        opMode.sleep(0);
        opMode.idle();
    }
}
