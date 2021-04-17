/*
 * Copyright (c)  3/21/2021. FTC Team 14214 NvyUs
 * This code is very epic
 */

package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.TelemetryFunctions.*;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.initializeRobot;
import static org.firstinspires.ftc.teamcode.TeleOp.Mechanisms.BlockerArms.controlBlockerArms;
import static org.firstinspires.ftc.teamcode.TeleOp.Mechanisms.Drivetrain.controlDrivetrain;
import static org.firstinspires.ftc.teamcode.TeleOp.Mechanisms.RingIntake.controlIntake;
import static org.firstinspires.ftc.teamcode.TeleOp.Mechanisms.Shooter.controlShooter;
import static org.firstinspires.ftc.teamcode.TeleOp.Mechanisms.WobbleArm.controlWobbleArm;

@TeleOp
public final class FinalTeleop extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        initializeRobot(this);
        showReady(this);

        waitForStart();

        showRunning(this);

        while (opModeIsActive()) {
            showTeleopTelemetry(this);

            controlDrivetrain(this);
            controlIntake(this);
            controlShooter(this);
            controlWobbleArm(this);
            controlBlockerArms(this);
        }
    }
}