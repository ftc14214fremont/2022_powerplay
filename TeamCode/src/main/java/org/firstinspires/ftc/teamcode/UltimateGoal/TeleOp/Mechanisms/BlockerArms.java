/*
 * Copyright (c)  3/28/2021. FTC Team 14214 NvyUs
 * This code is very epic
 */

package org.firstinspires.ftc.teamcode.UltimateGoal.TeleOp.Mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.UltimateGoal.NonRunnable.Logic.Button;
import org.jetbrains.annotations.NotNull;

import static org.firstinspires.ftc.teamcode.UltimateGoal.NonRunnable.Functions.BlockerFunctions.moveBlockersDown;
import static org.firstinspires.ftc.teamcode.UltimateGoal.NonRunnable.Functions.BlockerFunctions.moveBlockersUp;

public final class BlockerArms {
    private static final Button toggleBlockerArms = new Button();

    private static boolean blockerArmsUp = true;

    private BlockerArms() {
    }

    public static void controlBlockerArms(@NotNull LinearOpMode opMode) {
        if (toggleBlockerArms.isPressed(opMode.gamepad1.b)) {
            if (blockerArmsUp) {
                moveBlockersUp(opMode);
            } else {
                moveBlockersDown(opMode);
            }
            blockerArmsUp = !blockerArmsUp;
        }
    }
}
