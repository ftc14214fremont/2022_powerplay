/*
 * Copyright (c)  3/21/2021. FTC Team 14214 NvyUs
 * This code is very epic
 */

package org.firstinspires.ftc.teamcode.Auto.RandomTests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.TelemetryFunctions.showReady;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.TelemetryFunctions.showRunning;
import static org.firstinspires.ftc.teamcode.NonRunnable.Logic.RingDeterminationPipeline.ringStack;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.activateOpenCvCamera;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.initializeRobot;

@Autonomous
public class RingDetectionTest extends LinearOpMode
{
    @Override
    public void runOpMode() throws InterruptedException
    {
        activateOpenCvCamera(this);
        initializeRobot(this);
        showReady(this);
    
        waitForStart();
    
        showRunning(this);
    
        while (opModeIsActive())
        {
            telemetry.addData("rings found:", ringStack);
            telemetry.update();
        }
    }
}
