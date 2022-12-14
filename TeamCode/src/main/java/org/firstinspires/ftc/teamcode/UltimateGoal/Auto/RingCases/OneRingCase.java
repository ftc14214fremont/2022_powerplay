/*
 * Copyright (c)  3/21/2021. FTC Team 14214 NvyUs
 * This code is very epic
 */

package org.firstinspires.ftc.teamcode.UltimateGoal.Auto.RingCases;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.UltimateGoal.NonRunnable.Functions.DrivePath;

import static org.firstinspires.ftc.teamcode.UltimateGoal.NonRunnable.Functions.ImuFunctions.correctToHeading;
import static org.firstinspires.ftc.teamcode.UltimateGoal.NonRunnable.Functions.ImuFunctions.turn;
import static org.firstinspires.ftc.teamcode.UltimateGoal.NonRunnable.Functions.WobbleArmFunctions.*;
import static org.firstinspires.ftc.teamcode.UltimateGoal.NonRunnable.NvyusRobot.Constants.DriveMode;

public final class OneRingCase {
    private OneRingCase() {
    }

    public static void doOneRingCase(LinearOpMode opMode) {
        DrivePath strafeToWobbleZone = new DrivePath(0.4, 30, DriveMode.STRAFE_LEFT, opMode);
        DrivePath strafeBackToLaunchLine = new DrivePath(0.4, 6, DriveMode.STRAFE_RIGHT, opMode);
        DrivePath releaseByMovingBack = new DrivePath(0.3, 4, DriveMode.BACKWARD, opMode);

        turn(-90, opMode);
        correctToHeading(-90, opMode);
        strafeToWobbleZone.go();
        moveWobbleArmDown(opMode);
        releaseWobbleGoal(opMode, 400);
        releaseByMovingBack.go();
        moveWobbleArmUp(opMode);
        strafeBackToLaunchLine.go();
        turn(-90, opMode);
        correctToHeading(-90, opMode);
    }
}
