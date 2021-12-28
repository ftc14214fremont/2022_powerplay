/*
 * Copyright (c)  3/20/2021. FTC Team 14214 NvyUs
 * This code is very epic
 */

package org.firstinspires.ftc.teamcode.UltimateGoal.NonRunnable.Functions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.jetbrains.annotations.NotNull;

import static org.firstinspires.ftc.teamcode.UltimateGoal.TeleOp.Mechanisms.Drivetrain.getSlowMode;
import static org.firstinspires.ftc.teamcode.UltimateGoal.TeleOp.Mechanisms.Shooter.getPowerShotMode;

public final class TelemetryFunctions {
    private TelemetryFunctions() {
    }

    public static void showReady(@NotNull LinearOpMode opMode) {
        opMode.telemetry.addData("Mode", "Ready");
        opMode.telemetry.update();
    }

    public static void showTeleopTelemetry(@NotNull LinearOpMode opMode) {
        opMode.telemetry.addData("SLOW MODE", getSlowMode());
        opMode.telemetry.addData("POWERSHOT MODE", getPowerShotMode());
        opMode.telemetry.update();
    }

    public static void showRunning(@NotNull LinearOpMode opMode) {
        opMode.telemetry.addData("Mode", "Running");
        opMode.telemetry.update();
    }
}
