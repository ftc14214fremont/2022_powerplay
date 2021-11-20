/*
 * Copyright (c)  3/28/2021. FTC Team 14214 NvyUs
 * This code is very epic
 */

package UltimateGoal.Auto;

import UltimateGoal.NonRunnable.Functions.DrivePath;
import UltimateGoal.NonRunnable.NvyusRobot.Constants;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static UltimateGoal.Auto.RingCases.FourRingsCase.doFourRingsCase;
import static UltimateGoal.Auto.RingCases.NoRingsCase.doNoRingsCase;
import static UltimateGoal.Auto.RingCases.OneRingCase.doOneRingCase;
import static UltimateGoal.NonRunnable.Functions.BlockerFunctions.moveBlockersUp;
import static UltimateGoal.NonRunnable.Functions.GeneralDriveMotorFunctions.setVelocity;
import static UltimateGoal.NonRunnable.Functions.ImuFunctions.correctToHeading;
import static UltimateGoal.NonRunnable.Functions.ShooterFunctions.shoot;
import static UltimateGoal.NonRunnable.Functions.TelemetryFunctions.showReady;
import static UltimateGoal.NonRunnable.Functions.TelemetryFunctions.showRunning;
import static UltimateGoal.NonRunnable.Functions.WobbleArmFunctions.gripWobbleGoal;
import static UltimateGoal.NonRunnable.Logic.RingDeterminationPipeline.RingPosition.*;
import static UltimateGoal.NonRunnable.Logic.RingDeterminationPipeline.getRingStack;
import static UltimateGoal.NonRunnable.NvyusRobot.Constants.HIGH_GOAL_SPEED;
import static UltimateGoal.NonRunnable.NvyusRobot.Hardware.*;

@Autonomous
public class FinalAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        activateOpenCvCamera(FinalAuto.this);
        initializeRobot(FinalAuto.this);

        showReady(this);

        waitForStart();

        showRunning(this);

        DrivePath strafeRightAtBeginning = new DrivePath(0.5, 19, Constants.DriveMode.STRAFE_RIGHT, this);
        DrivePath advanceToShootingLine = new DrivePath(0.5, 48.5, Constants.DriveMode.STRAIGHT_FORWARD, this);
        DrivePath strafeToAim = new DrivePath(0.5, 23.5, Constants.DriveMode.STRAFE_LEFT, this);

        phoneCam.closeCameraDevice();
        moveBlockersUp(this);
        gripWobbleGoal(this, 0);

        if (getRingStack() == NONE) {
            DrivePath strafeToAimNone = new DrivePath(0.4, 4.5, Constants.DriveMode.STRAFE_LEFT, this);

            setVelocity(flywheel, HIGH_GOAL_SPEED);
            advanceToShootingLine.go();
            strafeToAimNone.go();
        } else {
            strafeRightAtBeginning.go();
            setVelocity(flywheel, HIGH_GOAL_SPEED);
            advanceToShootingLine.go();
            strafeToAim.go();
        }

        shoot(this, 3300);
        correctToHeading(0, this);

        if (getRingStack() == ONE_RING) {
            doOneRingCase(this);
        } else if (getRingStack() == FOUR_RINGS) {
            doFourRingsCase(this);
        } else {
            doNoRingsCase(this);
        }
    }
}

