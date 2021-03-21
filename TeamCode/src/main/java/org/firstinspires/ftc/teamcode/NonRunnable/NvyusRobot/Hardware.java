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
import org.firstinspires.ftc.teamcode.NonRunnable.Logic.RingLogic.RingDeterminationPipeline;
import org.jetbrains.annotations.NotNull;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

import java.util.List;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.FLOAT;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.setDriveDirection;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.DriveMode;
import static org.openftc.easyopencv.OpenCvCamera.ViewportRenderingPolicy;

public final class Hardware
{
    public static DcMotorEx FL;
    public static DcMotorEx FR;
    public static DcMotorEx BL;
    public static DcMotorEx BR;
    
    public static DcMotorEx spinner;
    public static DcMotorEx flyWheel;
    
    public static DcMotorEx[] driveMotorsArray = new DcMotorEx[]{FL, FR, BL, BR};
    
    public static DcMotor   tubeIntake;
    public static DcMotorEx wobbleArm;
    
    public static Servo guide;
    public static Servo wobble;
    public static Servo flap;
    public static Servo leftBlocker;
    public static Servo rightBlocker;
    
    public static BNO055IMU imu;
    
    public static OpenCvInternalCamera      phoneCam;
    public static RingDeterminationPipeline pipeline;
    
    private Hardware()
    {
    }
    
    public static void initializeRobot(@NotNull LinearOpMode opMode)
    {
        List<LynxModule> allHubs = opMode.hardwareMap.getAll(LynxModule.class);
        
        for (LynxModule module : allHubs)
        {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }
        
        FL = opMode.hardwareMap.get(DcMotorEx.class, "FL");
        FR = opMode.hardwareMap.get(DcMotorEx.class, "FR");
        BL = opMode.hardwareMap.get(DcMotorEx.class, "BL");
        BR = opMode.hardwareMap.get(DcMotorEx.class, "BR");
        
        driveMotorsArray = new DcMotorEx[]{FL, FR, BL, BR};
        
        spinner = opMode.hardwareMap.get(DcMotorEx.class, "spinner");
        flyWheel = opMode.hardwareMap.get(DcMotorEx.class, "speedy");
        
        tubeIntake = opMode.hardwareMap.get(DcMotor.class, "tubes");
        wobbleArm = opMode.hardwareMap.get(DcMotorEx.class, "arm");
        
        tubeIntake.setDirection(FORWARD);
        spinner.setDirection(REVERSE);
        flyWheel.setDirection(FORWARD);
        wobbleArm.setDirection(FORWARD);
        setDriveDirection(DriveMode.FORWARD);
        
        FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        wobbleArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        spinner.setZeroPowerBehavior(FLOAT);
        tubeIntake.setZeroPowerBehavior(FLOAT);
        
        //changed flywheel PID from default for better shooting
        flyWheel.setVelocityPIDFCoefficients(50, 0, 0, 15);
        
        guide = opMode.hardwareMap.get(Servo.class, "guide");
        wobble = opMode.hardwareMap.get(Servo.class, "wobble");
        flap = opMode.hardwareMap.get(Servo.class, "flap");
        leftBlocker = opMode.hardwareMap.get(Servo.class, "leftBlocker");
        rightBlocker = opMode.hardwareMap.get(Servo.class, "rightBlocker");
        
        //Initialize IMU
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        imu = opMode.hardwareMap.get(BNO055IMU.class, "imu");
        imu.write8(BNO055IMU.Register.OPR_MODE, 0b00000011);
        imu.initialize(parameters);
    }
    
    public static void activateOpenCvCamera(@NotNull LinearOpMode opMode)
    {
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