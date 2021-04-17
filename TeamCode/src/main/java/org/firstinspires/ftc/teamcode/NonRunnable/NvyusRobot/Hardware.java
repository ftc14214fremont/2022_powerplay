/*
 * Copyright (c)  3/21/2021. FTC Team 14214 NvyUs
 * This code is very epic
 */

package org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.NonRunnable.Logic.RingDeterminationPipeline;
import org.jetbrains.annotations.NotNull;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

import java.util.List;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.FLOAT;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.setDriveDirection;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.DriveMode;
import static org.openftc.easyopencv.OpenCvCamera.ViewportRenderingPolicy;

public final class Hardware {
    public static DcMotorEx[] driveMotors;

    public static DcMotorEx FL;
    public static DcMotorEx FR;
    public static DcMotorEx BL;
    public static DcMotorEx BR;

    public static DcMotorEx spinner;
    public static DcMotorEx flywheel;

    public static DcMotorEx wobbleArm;

    public static DcMotor tubeIntake;

    public static Servo guide;
    public static Servo wobbleGrip;
    public static Servo flap;
    public static Servo leftBlocker;
    public static Servo rightBlocker;

    public static BNO055IMU imu;

    public static OpenCvInternalCamera phoneCam;
    public static RingDeterminationPipeline pipeline;

    private Hardware() {
    }

    public static void initializeRobot(@NotNull LinearOpMode opMode) {
        List<LynxModule> allHubs = opMode.hardwareMap.getAll(LynxModule.class);

        for (LynxModule module : allHubs) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }
        initializeHardwareMapForAllMotors(opMode);
        setAllMotorsDirections();
        setAllMotorsZeroPowerBehavior();

        //changed flywheel PID from default for better shooting
        flywheel.setVelocityPIDFCoefficients(50, 0, 0, 15);

        initializeHardwareMapForAllServos(opMode);

        initializeIMU(opMode);
    }

    private static void initializeHardwareMapForAllMotors(@NotNull LinearOpMode opMode) {
        FL = opMode.hardwareMap.get(DcMotorEx.class, "FL");
        FR = opMode.hardwareMap.get(DcMotorEx.class, "FR");
        BL = opMode.hardwareMap.get(DcMotorEx.class, "BL");
        BR = opMode.hardwareMap.get(DcMotorEx.class, "BR");

        driveMotors = new DcMotorEx[]{FL, FR, BL, BR};

        spinner = opMode.hardwareMap.get(DcMotorEx.class, "spinner");
        flywheel = opMode.hardwareMap.get(DcMotorEx.class, "speedy");

        tubeIntake = opMode.hardwareMap.get(DcMotor.class, "tubes");
        wobbleArm = opMode.hardwareMap.get(DcMotorEx.class, "arm");
    }

    private static void setAllMotorsDirections() {
        tubeIntake.setDirection(FORWARD);
        spinner.setDirection(REVERSE);
        flywheel.setDirection(FORWARD);
        wobbleArm.setDirection(FORWARD);
        setDriveDirection(DriveMode.STRAIGHT_FORWARD);
    }

    private static void setAllMotorsZeroPowerBehavior() {
        FL.setZeroPowerBehavior(BRAKE);
        FR.setZeroPowerBehavior(BRAKE);
        BL.setZeroPowerBehavior(BRAKE);
        BR.setZeroPowerBehavior(BRAKE);
        wobbleArm.setZeroPowerBehavior(BRAKE);
        spinner.setZeroPowerBehavior(FLOAT);
        tubeIntake.setZeroPowerBehavior(FLOAT);
        flywheel.setZeroPowerBehavior(FLOAT);
    }

    private static void initializeHardwareMapForAllServos(@NotNull LinearOpMode opMode) {
        guide = opMode.hardwareMap.get(Servo.class, "guide");
        wobbleGrip = opMode.hardwareMap.get(Servo.class, "wobble");
        flap = opMode.hardwareMap.get(Servo.class, "flap");
        leftBlocker = opMode.hardwareMap.get(Servo.class, "leftBlocker");
        rightBlocker = opMode.hardwareMap.get(Servo.class, "rightBlocker");
    }

    private static void initializeIMU(@NotNull LinearOpMode opMode) {
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        imu = opMode.hardwareMap.get(BNO055IMU.class, "imu");
        imu.write8(BNO055IMU.Register.OPR_MODE, 0b00000011);
        imu.initialize(parameters);
    }

    public static void activateOpenCvCamera(@NotNull LinearOpMode opMode) {
        int cameraMonitorViewId = opMode.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId",
                "id",
                opMode.hardwareMap.appContext
                        .getPackageName());
        phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK,
                cameraMonitorViewId);
        pipeline = new RingDeterminationPipeline();
        phoneCam.setPipeline(pipeline);
        phoneCam.setViewportRenderingPolicy(ViewportRenderingPolicy.OPTIMIZE_VIEW);
        phoneCam.openCameraDeviceAsync(() -> phoneCam.startStreaming(320, 240, OpenCvCameraRotation.SIDEWAYS_LEFT));
    }
}