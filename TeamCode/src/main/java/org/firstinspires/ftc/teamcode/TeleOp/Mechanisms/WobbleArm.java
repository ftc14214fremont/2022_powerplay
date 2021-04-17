/*
 * Copyright (c)  3/21/2021. FTC Team 14214 NvyUs
 * This code is very epic
 */

package org.firstinspires.ftc.teamcode.TeleOp.Mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.NonRunnable.Logic.Button;
import org.jetbrains.annotations.NotNull;

import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.setVelocity;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.WobbleArmFunctions.gripWobbleGoal;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.WobbleArmFunctions.releaseWobbleGoal;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.wobbleArm;

public final class WobbleArm {
    private static final Button toggleWobbleServo = new Button();

    private static boolean wobbleIsClosed = false;

    private WobbleArm() {
    }

    public static void controlWobbleArm(@NotNull LinearOpMode opMode) {
        controlWobbleGrip(opMode);

        if (opMode.gamepad2.left_stick_y < -0.3) {
            setVelocity(wobbleArm, -0.6);
        } else if (opMode.gamepad2.left_stick_y > 0.3) {
            setVelocity(wobbleArm, 0.6);
        } else {
            setVelocity(wobbleArm, 0);
        }
    }

    private static void controlWobbleGrip(@NotNull LinearOpMode opMode) {
        if (toggleWobbleServo.isPressed(opMode.gamepad2.left_stick_button)) {
            if (wobbleIsClosed) {
                releaseWobbleGoal(opMode, 0);
            } else {
                gripWobbleGoal(opMode, 0);
            }
            wobbleIsClosed = !wobbleIsClosed;
        }
    }
}
