/*
 * Copyright (c)  3/20/2021. FTC Team 14214 NvyUs
 * This code is very epic
 */

package org.firstinspires.ftc.teamcode.NonRunnable.Functions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.jetbrains.annotations.NotNull;

import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.setVelocity;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.GUIDE_CLOSED_POSITION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.GUIDE_OPEN_POSITION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.*;

public final class IntakeFunctions
{
    private IntakeFunctions()
    {
    }
    
    public static void shoot(@NotNull LinearOpMode opMode, long sleep)
    {
        guide.setPosition(GUIDE_CLOSED_POSITION);
        opMode.sleep(300);
        opMode.idle();
        setVelocity(spinner, 0.22);
        
        opMode.sleep(sleep);
        tubeIntake.setPower(0);
        setVelocity(spinner, 0);
        setVelocity(flyWheel, 0);
        guide.setPosition(GUIDE_OPEN_POSITION);
        opMode.sleep(0);
        opMode.idle();
    }
}
