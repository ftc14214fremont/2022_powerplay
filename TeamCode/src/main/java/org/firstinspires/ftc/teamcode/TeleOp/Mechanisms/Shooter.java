/*
 * Copyright (c)  3/21/2021. FTC Team 14214 NvyUs
 * This code is very epic
 */

package org.firstinspires.ftc.teamcode.TeleOp.Mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.NonRunnable.Logic.Button;
import org.jetbrains.annotations.NotNull;

import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.setVelocity;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.ShooterFunctions.*;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.*;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.ShooterState.*;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.flywheel;

public final class Shooter
{
    private static final Button powerShotModeToggle = new Button();
    
    private static final ElapsedTime shooterTime = new ElapsedTime();
    
    private static ShooterState shooterState = SHOOTER_START;
    
    private static boolean powerShotMode = false;
    
    private Shooter()
    {
    }
    
    public static boolean getPowerShotMode()
    {
        return powerShotMode;
    }
    
    public static void controlShooter(@NotNull LinearOpMode opMode)
    {
        togglePowerShotMode(opMode);
        
        switch (shooterState)
        {
            case SHOOTER_START:
                doCaseShooterStart(opMode);
                break;
            case SHOOTER_CLOSE_FLAP:
                doCaseShooterCloseFlap(opMode);
                break;
            case SHOOTER_OPEN_FLAP:
                doCaseShooterOpenFlap(opMode);
                break;
        }
    }
    
    private static void togglePowerShotMode(@NotNull LinearOpMode opMode)
    {
        if (powerShotModeToggle.isPressed(opMode.gamepad2.x))
        {
            powerShotMode = !powerShotMode;
        }
    }
    
    private static void doCaseShooterStart(@NotNull LinearOpMode opMode)
    {
        if (opMode.gamepad2.right_trigger > 0.5)
        {
            adjustShooterSpeed();
            closeFlap(opMode, 0);
            
            shooterTime.reset();
            shooterState = SHOOTER_CLOSE_FLAP;
        }
        else
        {
            setVelocity(flywheel, IDLE_SPEED);
            openGuide(opMode, 0);
        }
    }
    
    private static void doCaseShooterCloseFlap(@NotNull LinearOpMode opMode)
    {
        if (shooterTime.milliseconds() >= FLAP_MOVEMENT_MS)
        {
            openFlap(opMode, 0);
            
            shooterTime.reset();
            shooterState = SHOOTER_OPEN_FLAP;
        }
    }
    
    private static void doCaseShooterOpenFlap(@NotNull LinearOpMode opMode)
    {
        if (shooterTime.milliseconds() >= FLAP_MOVEMENT_MS)
        {
            closeGuide(opMode, 0);
            
            if (opMode.gamepad2.right_trigger <= 0.5)
            {
                shooterState = SHOOTER_START;
            }
        }
    }
    
    private static void adjustShooterSpeed()
    {
        if (powerShotMode)
        {
            setVelocity(flywheel, POWER_SHOT_SPEED);
        }
        else
        {
            setVelocity(flywheel, HIGH_GOAL_SPEED);
        }
    }
}
