/*
 * Copyright (c)  3/20/2021. FTC Team 14214 NvyUs
 * This code is very epic
 */

package org.firstinspires.ftc.teamcode.UltimateGoal.Auto.RandomTests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static org.firstinspires.ftc.robotcontroller.external.samples.ConceptScanServo.*;
import static org.firstinspires.ftc.teamcode.UltimateGoal.NonRunnable.Functions.TelemetryFunctions.showReady;
import static org.firstinspires.ftc.teamcode.UltimateGoal.NonRunnable.NvyusRobot.Hardware.initializeRobot;
import static org.firstinspires.ftc.teamcode.UltimateGoal.NonRunnable.NvyusRobot.Hardware.rightBlocker;

@Autonomous
public class ServoVibeCheck extends LinearOpMode {
    double position = (MAX_POS - MIN_POS) / 2;
    boolean rampUp = true;

    @Override
    public void runOpMode() throws InterruptedException {
        initializeRobot(this);

        showReady(this);

        waitForStart();

        while (opModeIsActive()) {
            if (rampUp) {
                position += INCREMENT;
                if (position >= MAX_POS) {
                    position = MAX_POS;
                    rampUp = false;
                }
            } else {
                position -= INCREMENT;
                if (position <= MIN_POS) {
                    position = MIN_POS;
                    rampUp = true;
                }
            }

            telemetry.addData("Servo Position", "%5.2f", position);
            telemetry.addData(">", "Press Stop to end test.");
            telemetry.update();

            rightBlocker.setPosition(position);
            sleep(CYCLE_MS);
            idle();
        }
    }
}
