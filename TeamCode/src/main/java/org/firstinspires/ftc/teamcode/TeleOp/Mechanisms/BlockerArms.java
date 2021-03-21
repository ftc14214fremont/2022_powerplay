/*
 * Copyright (c)  3/20/2021. FTC Team 14214 NvyUs
 * This code is very epic
 */

package org.firstinspires.ftc.teamcode.TeleOp.Mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.jetbrains.annotations.NotNull;

import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.BlockerFunctions.moveBlockersDown;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.BlockerFunctions.moveBlockersUp;

public final class BlockerArms
{
    private BlockerArms()
    {
    }
    
    public static void controlBlockerArms(@NotNull LinearOpMode opMode)
    {
        if (opMode.gamepad2.right_bumper && !(opMode.gamepad2.right_trigger > 0.5))
        {
            moveBlockersDown(opMode);
        }
        else
        {
            moveBlockersUp(opMode);
        }
    }
}
