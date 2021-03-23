/*
 * Copyright (c)  3/22/2021. FTC Team 14214 NvyUs
 * This code is very epic
 */

package org.firstinspires.ftc.teamcode.NonRunnable.Functions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.jetbrains.annotations.NotNull;

import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.setVelocity;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.*;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.*;

public final class ShooterFunctions
{
    private ShooterFunctions()
    {
    }
    
    public static void shoot(@NotNull LinearOpMode opMode, int sleepMs)
    {
        closeGuide(opMode, 300);
        setVelocity(spinner, 0.22);
    
        opMode.sleep(sleepMs);
        stopAndResetShooter(opMode);
    }
    
    public static void closeGuide(@NotNull LinearOpMode opMode, int sleepMs)
    {
        guide.setPosition(GUIDE_CLOSED_POSITION);
        opMode.sleep(sleepMs);
        opMode.idle();
    }
    
    private static void stopAndResetShooter(@NotNull LinearOpMode opMode)
    {
        tubeIntake.setPower(0);
        setVelocity(spinner, 0);
        setVelocity(flywheel, IDLE_SPEED);
        guide.setPosition(GUIDE_OPEN_POSITION);
        opMode.sleep(0);
        opMode.idle();
    }
    
    public static void closeFlap(@NotNull LinearOpMode opMode, int sleepMs)
    {
        flap.setPosition(FLAP_CLOSED_POSITION);
        opMode.sleep(sleepMs);
        opMode.idle();
    }
    
    public static void openFlap(@NotNull LinearOpMode opMode, int sleepMs)
    {
        flap.setPosition(FLAP_OPEN_POSITION);
        opMode.sleep(sleepMs);
        opMode.idle();
    }
    
    public static void openGuide(@NotNull LinearOpMode opMode, int sleepMs)
    {
        guide.setPosition(GUIDE_OPEN_POSITION);
        opMode.sleep(sleepMs);
        opMode.idle();
    }
}
