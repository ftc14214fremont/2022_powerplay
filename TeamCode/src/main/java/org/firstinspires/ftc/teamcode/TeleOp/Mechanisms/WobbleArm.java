/*
 * Copyright (c)  3/20/2021. FTC Team 14214 NvyUs
 * This code is very epic
 */

package org.firstinspires.ftc.teamcode.TeleOp.Mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.NonRunnable.Logic.Button;
import org.jetbrains.annotations.NotNull;

import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.setVelocity;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.WOBBLE_CLOSED_POSITION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.WOBBLE_OPEN_POSITION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.wobble;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.wobbleArm;

public final class WobbleArm
{
    private static final Button toggleWobbleServo = new Button();
    
    private static boolean wobbleIsClosed = false;
    
    private WobbleArm()
    {
    }
    
    public static void controlWobbleArm(@NotNull LinearOpMode opMode)
    {
        if (toggleWobbleServo.isPressed(opMode.gamepad2.left_stick_button))
        {
            if (wobbleIsClosed)
            {
                wobble.setPosition(WOBBLE_OPEN_POSITION);
            }
            else
            {
                wobble.setPosition(WOBBLE_CLOSED_POSITION);
            }
            wobbleIsClosed = !wobbleIsClosed;
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
