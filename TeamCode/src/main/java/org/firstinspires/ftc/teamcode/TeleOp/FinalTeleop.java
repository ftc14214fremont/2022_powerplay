/*
 * Copyright (c)  3/19/2021. FTC Team 14214 NvyUs
 * This code is very epic
 */

package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.TelemetryFunctions.showReady;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.initHardware;
import static org.firstinspires.ftc.teamcode.TeleOp.Mechanisms.Drivetrain.*;
import static org.firstinspires.ftc.teamcode.TeleOp.Mechanisms.RingIntake.controlIntake;
import static org.firstinspires.ftc.teamcode.TeleOp.Mechanisms.Shooter.controlShooter;
import static org.firstinspires.ftc.teamcode.TeleOp.Mechanisms.Shooter.powerShotMode;
import static org.firstinspires.ftc.teamcode.TeleOp.Mechanisms.WobbleArm.controlWobbleArm;

@TeleOp
public final class FinalTeleop extends LinearOpMode
{
    
    @Override
    public void runOpMode()
    {
        initHardware(this);
        showReady(this);
        
        waitForStart();
        
        while (opModeIsActive())
        {
            telemetry.addData("SLOW MODE", slowMode);
            telemetry.addData("POWERSHOT MODE", powerShotMode);
            telemetry.update();
            
            drive(this);
            controlIntake(this);
            controlShooter(this);
            controlWobbleArm(this);
            powerShot(this);
        }
    }
}