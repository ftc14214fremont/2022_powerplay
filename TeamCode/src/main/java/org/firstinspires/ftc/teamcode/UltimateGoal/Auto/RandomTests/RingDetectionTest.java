/*
 * Copyright (c)  3/28/2021. FTC Team 14214 NvyUs
 * This code is very epic
 */

package org.firstinspires.ftc.teamcode.UltimateGoal.Auto.RandomTests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static org.firstinspires.ftc.teamcode.UltimateGoal.NonRunnable.Functions.TelemetryFunctions.showReady;
import static org.firstinspires.ftc.teamcode.UltimateGoal.NonRunnable.Functions.TelemetryFunctions.showRunning;
import static org.firstinspires.ftc.teamcode.UltimateGoal.NonRunnable.Logic.RingDeterminationPipeline.getRingStack;
import static org.firstinspires.ftc.teamcode.UltimateGoal.NonRunnable.NvyusRobot.Hardware.initializeRobot;

@Autonomous
public class RingDetectionTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
//        activateOpenCvCamera(this);
        initializeRobot(this);
        showReady(this);

        waitForStart();

        showRunning(this);

        while (opModeIsActive()) {
            telemetry.addData("rings found:", getRingStack());
            telemetry.update();
        }
    }
}
