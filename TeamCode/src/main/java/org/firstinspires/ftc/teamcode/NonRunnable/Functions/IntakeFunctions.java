/*
 * Copyright (c)  3/19/2021. FTC Team 14214 NvyUs
 * This code is very epic
 */

package org.firstinspires.ftc.teamcode.NonRunnable.Functions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.setVelocity;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.*;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.*;

public final class IntakeFunctions
{
    private IntakeFunctions()
    {
    }
    
    public static void preventRingsFromPassing(LinearOpMode opMode)
    {
        guide.setPosition(GUIDE_OPEN_POSITION);
        opMode.sleep(0);
        opMode.idle();
        flap.setPosition(FLAP_OPEN_POSITION);
        opMode.sleep(0);
        opMode.idle();
    }
    
    public static void letRingsPass(LinearOpMode opMode)
    {
        flap.setPosition(FLAP_CLOSED_POSITION);
        opMode.sleep(0);
        opMode.idle();
        
        opMode.sleep(400);
        
        flap.setPosition(FLAP_OPEN_POSITION);
        opMode.sleep(0);
        opMode.idle();
        
        opMode.sleep(400);
        
        guide.setPosition(GUIDE_CLOSED_POSITION);
        opMode.sleep(0);
        opMode.idle();
    }
    
    public static void shoot(LinearOpMode opMode, long sleep)
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
