/*
 * Copyright (c)  3/21/2021. FTC Team 14214 NvyUs
 * This code is very epic
 */

package org.firstinspires.ftc.teamcode.TeleOp.Mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.jetbrains.annotations.NotNull;

import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.setVelocity;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.spinner;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.tubeIntake;

public final class RingIntake {
    private RingIntake() {
    }

    public static void controlIntake(@NotNull LinearOpMode opMode) {
        controlTubes(opMode);
        controlSpinner(opMode);
    }

    private static void controlTubes(@NotNull LinearOpMode opMode) {
        if (opMode.gamepad2.left_bumper) {
            if (opMode.gamepad2.dpad_down) {
                tubeIntake.setPower(-1);
            } else {
                tubeIntake.setPower(1);
            }
        } else {
            tubeIntake.setPower(0);
        }
    }

    private static void controlSpinner(@NotNull LinearOpMode opMode) {
        if (opMode.gamepad2.dpad_up) {
            setVelocity(spinner, 0.3);
        } else if (opMode.gamepad2.dpad_down) {
            setVelocity(spinner, -0.4);
        } else if (!((opMode.gamepad2.right_trigger > 0.5) && opMode.gamepad2.left_bumper)) {
            setVelocity(spinner, 0);
        }

        if (opMode.gamepad2.right_trigger > 0.5 && opMode.gamepad2.left_bumper) {
            setVelocity(spinner, 0.5);
        }
    }
}
