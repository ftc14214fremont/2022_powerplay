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

public final class NoRingsCase {
    private NoRingsCase() {
    }

    public static void doNoRingsCase(LinearOpMode opMode) {
        DrivePath moveForwardOntoLaunchLine = new DrivePath(0.4, 34, DriveMode.STRAIGHT_FORWARD, opMode);
        DrivePath strafeToZone = new DrivePath(0.4, 8, DriveMode.STRAFE_LEFT, opMode);
        DrivePath releaseByMovingBack = new DrivePath(0.4, 4, DriveMode.BACKWARD, opMode);
        DrivePath strafeRightForNextWobble = new DrivePath(0.4, 10, DriveMode.STRAFE_RIGHT, opMode);
        DrivePath moveBackToSecondWobble = new DrivePath(0.4, 77, DriveMode.BACKWARD, opMode);
        DrivePath moveBackToWobble = new DrivePath(0.4, 13, DriveMode.BACKWARD, opMode);
        DrivePath strafeToDeliverSecond = new DrivePath(0.3, 63, DriveMode.STRAFE_LEFT, opMode);
        //        DrivePath moveBackFromWobble = new DrivePath(0.5, 4, DriveMode.BACKWARD, opMode);
        DrivePath strafeToPark = new DrivePath(0.4, 20, DriveMode.STRAFE_LEFT, opMode);
        DrivePath forwardToPark = new DrivePath(0.4, 15, DriveMode.STRAIGHT_FORWARD, opMode);

        moveForwardOntoLaunchLine.go();
        strafeToZone.go();
        moveWobbleArmDown(opMode);
        releaseWobbleGoal(opMode, 400);
        releaseByMovingBack.go();
        moveWobbleArmUp(opMode);
        strafeRightForNextWobble.go();
        moveBackToSecondWobble.go();
        turn(-90, opMode);
        correctToHeading(-90, opMode);
        moveBackToWobble.go();
        strafeToDeliverSecond.go();
        forwardToPark.go();
        strafeToPark.go();
    }
}
