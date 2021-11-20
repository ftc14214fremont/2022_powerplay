/*
 * Copyright (c)  3/20/2021. FTC Team 14214 NvyUs
 * This code is very epic
 */

package UltimateGoal.Auto.RandomTests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static UltimateGoal.NonRunnable.Functions.BlockerFunctions.moveBlockersDown;
import static UltimateGoal.NonRunnable.Functions.BlockerFunctions.moveBlockersUp;
import static UltimateGoal.NonRunnable.Functions.TelemetryFunctions.showReady;
import static UltimateGoal.NonRunnable.NvyusRobot.Hardware.initializeRobot;

@Autonomous
public class BlockerTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        initializeRobot(this);

        showReady(this);

        waitForStart();

        moveBlockersUp(this);
        sleep(1000);
        moveBlockersDown(this);
        sleep(1000);
    }
}
