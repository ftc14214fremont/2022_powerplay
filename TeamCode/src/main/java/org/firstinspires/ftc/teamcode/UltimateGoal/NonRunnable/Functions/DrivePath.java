/*
 * Copyright (c)  3/21/2021. FTC Team 14214 NvyUs
 * This code is very epic
 */

package org.firstinspires.ftc.teamcode.UltimateGoal.NonRunnable.Functions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.UltimateGoal.NonRunnable.Logic.AngleCorrections;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import static org.firstinspires.ftc.teamcode.UltimateGoal.NonRunnable.Functions.GeneralDriveMotorFunctions.*;
import static org.firstinspires.ftc.teamcode.UltimateGoal.NonRunnable.Functions.ImuFunctions.*;
import static org.firstinspires.ftc.teamcode.UltimateGoal.NonRunnable.NvyusRobot.Constants.*;
import static org.firstinspires.ftc.teamcode.UltimateGoal.NonRunnable.NvyusRobot.Hardware.BL;

public class DrivePath {
    private static double currentVelocity;
    private final ElapsedTime runTime = new ElapsedTime();
    private final double velocity;
    private final double targetDistance;
    private final DriveMode driveMode;
    private final LinearOpMode opMode;
    private double distanceTraveled;
    private double inchesError;

    public DrivePath(double velocity, double targetDistance, DriveMode driveMode, LinearOpMode opMode) {
        this.velocity = velocity;
        this.targetDistance = targetDistance;
        this.driveMode = driveMode;
        this.opMode = opMode;
        this.inchesError = targetDistance;
        this.distanceTraveled = 0;
    }

    public static double getCurrentVelocity() {
        return currentVelocity;
    }

    public void go() {
        prepareForStart();

        double[] finalDriveVelocities;
        AngleCorrections correctionArray = new AngleCorrections(this.driveMode);

        while ((this.inchesError > 0.25) && this.opMode.opModeIsActive()) {
            correctionArray.update(getAngle());
            finalDriveVelocities = getCorrectedVelocities(getInitialDriveVelocities(),
                    correctionArray.getCorrectionArray());

            setDriveMotorsVelocity(finalDriveVelocities);
            updateValues();
        }
        stopDrivingRobot(this.opMode);
        correctToHeading(0, opMode);
    }

    private void prepareForStart() {
        setDriveDirection(this.driveMode);
        resetAngle();
        resetDriveEncoders();
        runTime.reset();
    }

    private double[] getCorrectedVelocities(double[] velocityArray, double[] correctionArray) {
        for (int i = 0; i < 4; i++) {
            velocityArray[i] += correctionArray[i];
        }
        return velocityArray;
    }

    @NotNull
    private double[] getInitialDriveVelocities() {
        double[] velocityArray = new double[4];

        if (this.distanceTraveled < INITIAL_SLOW) {
            Arrays.fill(velocityArray, INITIAL_SLOW_VELOCITY);
            currentVelocity = INITIAL_SLOW_VELOCITY;
        } else if (this.distanceTraveled > this.targetDistance - FINAL_SLOW) {
            Arrays.fill(velocityArray, FINAL_SLOW_VELOCITY);
            currentVelocity = FINAL_SLOW_VELOCITY;
        } else {
            Arrays.fill(velocityArray, this.velocity);
            currentVelocity = this.velocity;
        }
        return velocityArray;
    }

    private void updateValues() {
        this.distanceTraveled = BL.getCurrentPosition() / COUNTS_PER_INCH;
        this.inchesError = this.targetDistance - this.distanceTraveled;
    }
}
