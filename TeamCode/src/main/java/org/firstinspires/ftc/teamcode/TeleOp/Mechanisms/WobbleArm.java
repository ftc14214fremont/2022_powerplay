/*
 * Copyright (c)  3/19/2021. FTC Team 14214 NvyUs
 * This code is very epic
 */

package org.firstinspires.ftc.teamcode.TeleOp.Mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.NonRunnable.Logic.Button;

import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.setVelocity;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.WOBBLE_CLOSED_POSITION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.WOBBLE_OPEN_POSITION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.wobble;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.wobbleArm;

public final class WobbleArm
{
    public static Button toggleWobbleServo = new Button();
    
    public static boolean currentWobblePos = false;
    
    private WobbleArm()
    {
    }
    
    public static void controlWobbleArm(LinearOpMode opMode)
    {
        if (toggleWobbleServo.isPressed(opMode.gamepad2.left_stick_button))
        {
            if (currentWobblePos)
            {
                wobble.setPosition(WOBBLE_OPEN_POSITION);
            }
            else
            {
                wobble.setPosition(WOBBLE_CLOSED_POSITION);
            }
            currentWobblePos = !currentWobblePos;
            opMode.sleep(0);
            opMode.idle();
        }
        
        if (opMode.gamepad2.left_stick_y < -0.3)
        {
            setVelocity(wobbleArm, -0.6);
        }
        else if (opMode.gamepad2.left_stick_y > 0.3)
        {
            setVelocity(wobbleArm, 0.6);
        }
        else
        {
            setVelocity(wobbleArm, 0);
        }
    }
}
