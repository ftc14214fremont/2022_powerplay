/*
 * Copyright (c)  3/20/2021. FTC Team 14214 NvyUs
 * This code is very epic
 */

package org.firstinspires.ftc.teamcode.NonRunnable.Functions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.jetbrains.annotations.NotNull;

import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.BLOCKER_CLOSED_POSITION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.BLOCKER_OPEN_POSITION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.leftBlocker;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.rightBlocker;

public final class BlockerFunctions {
    private BlockerFunctions() {
    }

    public static void moveBlockersUp(@NotNull LinearOpMode opMode) {
        leftBlocker.setPosition(BLOCKER_OPEN_POSITION);
        opMode.sleep(0);
        opMode.idle();
        rightBlocker.setPosition(BLOCKER_OPEN_POSITION);
        opMode.sleep(0);
        opMode.idle();
    }

    public static void moveBlockersDown(@NotNull LinearOpMode opMode) {
        leftBlocker.setPosition(BLOCKER_CLOSED_POSITION);
        opMode.sleep(0);
        opMode.idle();
        rightBlocker.setPosition(BLOCKER_CLOSED_POSITION);
        opMode.sleep(0);
        opMode.idle();
    }
}
