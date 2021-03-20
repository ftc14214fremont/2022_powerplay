/*
 * Copyright (c)  3/19/2021. FTC Team 14214 NvyUs
 * This code is very epic
 */

package org.firstinspires.ftc.teamcode.TeleOp.Mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.NonRunnable.Logic.Button;

import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.setVelocity;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.*;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.*;

public final class Shooter
{
    public static Button  powerShotModeToggle = new Button();
    public static double  currentTime;
    public static int     toggleRingFlowCount = 0;
    public static boolean powerShotMode       = false;
    public static boolean firstPartDone;
    public static boolean secondPartDone;
    public static boolean thirdPartDone;
    
    private Shooter()
    {
    }
    
    public static void controlShooter(LinearOpMode opMode)
    {
        if (powerShotModeToggle.isPressed(opMode.gamepad2.x))
        {
            powerShotMode = !powerShotMode;
        }
        
        if (opMode.gamepad2.right_trigger > 0.5)
        {
            if (powerShotMode)
            {
                setVelocity(flyWheel, powerShotSpeed);
            }
            else
            {
                setVelocity(flyWheel, highGoalSpeed);
            }
            ++toggleRingFlowCount;
            
            if (toggleRingFlowCount == 1)
            {
                firstPartDone = false;
                secondPartDone = false;
                thirdPartDone = false;
            }
            
            if (!firstPartDone)
            {
                flap.setPosition(FLAP_CLOSED_POSITION);
                opMode.sleep(0);
                opMode.idle();
                firstPartDone = true;
                currentTime = opMode.getRuntime();
            }
            else if (!secondPartDone && (opMode.getRuntime() > currentTime + 0.3))
            {
                flap.setPosition(FLAP_OPEN_POSITION);
                opMode.sleep(0);
                opMode.idle();
                secondPartDone = true;
                currentTime = opMode.getRuntime();
            }
            else if (!thirdPartDone && (opMode.getRuntime() > currentTime + 0.3))
            {
                guide.setPosition(GUIDE_CLOSED_POSITION);
                opMode.sleep(0);
                opMode.idle();
            }
            
            if (opMode.gamepad2.left_bumper)
            {
                setVelocity(spinner, 0.6);
            }
        }
        else
        {
            setVelocity(flyWheel, 0);
            guide.setPosition(GUIDE_OPEN_POSITION);
            opMode.sleep(0);
            opMode.idle();
            toggleRingFlowCount = 0;
        }
    }
}
