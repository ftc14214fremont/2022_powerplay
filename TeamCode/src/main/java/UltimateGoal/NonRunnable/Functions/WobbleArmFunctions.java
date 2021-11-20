/*
 * Copyright (c)  3/21/2021. FTC Team 14214 NvyUs
 * This code is very epic
 */

package UltimateGoal.NonRunnable.Functions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.jetbrains.annotations.NotNull;

import static UltimateGoal.NonRunnable.Functions.GeneralDriveMotorFunctions.setVelocity;
import static UltimateGoal.NonRunnable.NvyusRobot.Constants.*;
import static UltimateGoal.NonRunnable.NvyusRobot.Hardware.wobbleArm;
import static UltimateGoal.NonRunnable.NvyusRobot.Hardware.wobbleGrip;

public final class WobbleArmFunctions {
    private WobbleArmFunctions() {
    }

    public static void moveWobbleArmDown(LinearOpMode opMode) {
        wobbleArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        wobbleArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        while ((wobbleArm.getCurrentPosition() <= wobbleArmDegreesToCounts(183)) && opMode.opModeIsActive()) {
            setVelocity(wobbleArm, 0.6);
        }
        setVelocity(wobbleArm, 0);
    }

    public static double wobbleArmDegreesToCounts(double degrees) {
        return degrees * WOBBLE_ARM_COUNTS_PER_DEG;
    }

    public static void moveWobbleArmUp(LinearOpMode opMode) {
        wobbleArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        wobbleArm.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        while ((wobbleArm.getCurrentPosition() >= wobbleArmDegreesToCounts(-183)) && opMode.opModeIsActive()) {
            setVelocity(wobbleArm, -0.6);
        }
        setVelocity(wobbleArm, 0);
    }

    public static void releaseWobbleGoal(@NotNull LinearOpMode opMode, int sleepMs) {
        wobbleGrip.setPosition(WOBBLE_OPEN_POSITION);
        opMode.sleep(sleepMs);
        opMode.idle();
    }

    public static void gripWobbleGoal(@NotNull LinearOpMode opMode, int sleepMs) {
        wobbleGrip.setPosition(WOBBLE_CLOSED_POSITION);
        opMode.sleep(sleepMs);
        opMode.idle();
    }
}
