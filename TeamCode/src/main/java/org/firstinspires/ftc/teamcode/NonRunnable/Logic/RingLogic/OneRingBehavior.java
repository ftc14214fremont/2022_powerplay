/*
 * Copyright (c)  3/19/2021. FTC Team 14214 NvyUs
 * This code is very epic
 */

package org.firstinspires.ftc.teamcode.NonRunnable.Logic.RingLogic;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.NonRunnable.Functions.DrivePath;

import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.ImuFunctions.correctToHeading;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.ImuFunctions.turn;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.WobbleArmFunctions.*;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.DriveMode;

public final class OneRingBehavior
{
    private OneRingBehavior()
    {
    }
    
    public static void doOneRingBehavior(LinearOpMode opMode)
    {
        DrivePath strafeToWobbleZone     = new DrivePath(0.4, 30, DriveMode.STRAFE_LEFT, opMode);
        DrivePath strafeBackToLaunchLine = new DrivePath(0.4, 6, DriveMode.STRAFE_RIGHT, opMode);
        DrivePath releaseByMovingBack    = new DrivePath(0.3, 4, DriveMode.BACKWARD, opMode);
        
        turn(-90, opMode);
        correctToHeading(-90, opMode);
        strafeToWobbleZone.go();
        moveWobbleArmDown(opMode);
        releaseWobbleGoal(opMode);
        releaseByMovingBack.go();
        moveWobbleArmUp(opMode);
        strafeBackToLaunchLine.go();
        turn(-90, opMode);
        correctToHeading(-90, opMode);
    }
}
