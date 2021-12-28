package org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.Servo;
import org.firstinspires.ftc.teamcode.FreightFrenzy.ShippingElementDetectionFinal;
import org.jetbrains.annotations.NotNull;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

import java.util.List;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.FLOAT;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;

public class NvyusRobotHardware {
    public static DcMotorEx FL;
    public static DcMotorEx FR;
    public static DcMotorEx BL;
    public static DcMotorEx BR;

    public static final double DROPPER_CLOSED_POSITION = 0.83;
    public static DcMotorEx linearSlide;
    public static DcMotorEx intake;
    public static DcMotorEx arm;
    public static DcMotor carousel;
    public static Servo dropper;
    public static BNO055IMU imu;
    public static OpenCvInternalCamera phoneCam;
    public static ShippingElementDetectionFinal.SkystoneDeterminationPipeline pipeline;

    public static void initializeNvyusRobotHardware(@NotNull LinearOpMode opMode) {
        //altering some settings on rev hub, supposedly makes encoders update quicker
        List<LynxModule> allHubs = opMode.hardwareMap.getAll(LynxModule.class);

        for (LynxModule module : allHubs) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

        //initialize motor hardware map
        FL = opMode.hardwareMap.get(DcMotorEx.class, "FL");
        FR = opMode.hardwareMap.get(DcMotorEx.class, "FR");
        BL = opMode.hardwareMap.get(DcMotorEx.class, "BL");
        BR = opMode.hardwareMap.get(DcMotorEx.class, "BR");

        linearSlide = opMode.hardwareMap.get(DcMotorEx.class, "linearSlide");
        intake = opMode.hardwareMap.get(DcMotorEx.class, "intake");
        arm = opMode.hardwareMap.get(DcMotorEx.class, "arm");
        carousel = opMode.hardwareMap.get(DcMotor.class, "carousel");

        //initialize servo hardware map
        dropper = opMode.hardwareMap.get(Servo.class, "dropper");

        //set zero power behavior
        FL.setZeroPowerBehavior(BRAKE);
        FR.setZeroPowerBehavior(BRAKE);
        BL.setZeroPowerBehavior(BRAKE);
        BR.setZeroPowerBehavior(BRAKE);

        linearSlide.setZeroPowerBehavior(BRAKE);
        intake.setZeroPowerBehavior(FLOAT);
        arm.setZeroPowerBehavior(BRAKE);
        carousel.setZeroPowerBehavior(FLOAT);

        //set motors to move forward
        FL.setDirection(REVERSE);
        FR.setDirection(FORWARD);
        BL.setDirection(REVERSE);
        BR.setDirection(FORWARD);

        linearSlide.setDirection(FORWARD);
        intake.setDirection(FORWARD);

        //initialize IMU
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.loggingEnabled = true;
        parameters.loggingTag = "IMU";
        imu = opMode.hardwareMap.get(BNO055IMU.class, "imu");
        imu.write8(BNO055IMU.Register.OPR_MODE, 0b00000011);
        imu.initialize(parameters);
    }

    public static void initializeNvyusRobotCamera(LinearOpMode opMode) {
        int cameraMonitorViewId = opMode.hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", opMode.hardwareMap.appContext.getPackageName());
        phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        pipeline = new ShippingElementDetectionFinal.SkystoneDeterminationPipeline();
        phoneCam.setPipeline(pipeline);
        phoneCam.setViewportRenderingPolicy(OpenCvCamera.ViewportRenderingPolicy.OPTIMIZE_VIEW);
        phoneCam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                phoneCam.startStreaming(320, 240, OpenCvCameraRotation.SIDEWAYS_LEFT);
            }

            public void onError(int errorCode) {
                /*
                 * This will be called if the camera could not be opened
                 */
            }
        });
    }
}
