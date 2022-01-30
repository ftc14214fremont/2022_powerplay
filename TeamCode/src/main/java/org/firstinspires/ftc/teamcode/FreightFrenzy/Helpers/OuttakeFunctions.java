package org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.STOP_AND_RESET_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.FLOAT;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;
import static com.qualcomm.robotcore.hardware.Servo.Direction.FORWARD;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Auto.ShippingElementDetectionFinal.SkystoneDeterminationPipeline.getShippingElementPosition;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.Constants.*;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.ImuFunctions.getAngle;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.ImuFunctions.resetAngle;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.MotorFunctions.setVelocity;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.NvyusRobotHardware.*;

public class OuttakeFunctions {

    public static void turnCW(double turnAngle, boolean emergencyStop, LinearOpMode opMode) {
        ElapsedTime elapsedTime = new ElapsedTime();
        double currentAngle = getAngle();
        BR.setDirection(REVERSE);
        BL.setDirection(REVERSE);

        elapsedTime.reset();
        if (emergencyStop) {
            while (currentAngle > turnAngle && opMode.opModeIsActive() && elapsedTime.milliseconds() < 2000) {
                setVelocity(BR, 0.3);
                setVelocity(BL, 0.3);

                opMode.telemetry.addLine("angle: " + currentAngle);
                opMode.telemetry.update();
                currentAngle = getAngle();
            }
        } else {
            while (currentAngle > turnAngle && opMode.opModeIsActive()) {
                setVelocity(BR, 0.3);
                setVelocity(BL, 0.3);

                opMode.telemetry.addLine("angle: " + currentAngle);
                opMode.telemetry.update();
                currentAngle = getAngle();
            }
        }
        BR.setPower(0);
        BL.setPower(0);
        opMode.sleep(1000);
    }

    public static void spinCarousel() {
        carousel.setPower(0.9);
    }

    public static void turnCCWBR(double turnAngle, LinearOpMode opMode) {
        double currentAngle = getAngle();

        while (currentAngle < turnAngle && opMode.opModeIsActive()) {
            BL.setPower(0);
            if (currentAngle < turnAngle * 0.6) {
                setVelocity(BR, 0.4);
            } else {
                setVelocity(BR, 0.2);
            }
            opMode.telemetry.addLine("angle: " + currentAngle);
            opMode.telemetry.update();
            currentAngle = getAngle();
        }
        BR.setPower(0);
        opMode.sleep(1000);
    }

    public static void stopSpinningCarousel(int spinningTime, LinearOpMode opMode) {
        opMode.sleep(spinningTime);
        carousel.setPower(0);
    }

    public static void initializationStuff(LinearOpMode opMode) {
        initializeNvyusRobotHardware(opMode);
        initializeNvyusRobotCamera(opMode);

        opMode.telemetry.addLine("ready");
        opMode.telemetry.update();
        opMode.telemetry.addLine("position " + getShippingElementPosition());
        opMode.telemetry.update();

        //reset encoders
        BR.setMode(STOP_AND_RESET_ENCODER);
        BL.setMode(STOP_AND_RESET_ENCODER);

        //set zero power behavior
        BL.setZeroPowerBehavior(BRAKE);
        BR.setZeroPowerBehavior(BRAKE);
        FL.setZeroPowerBehavior(FLOAT);
        FR.setZeroPowerBehavior(FLOAT);
        //reset IMU
        resetAngle();
    }

    public static void raiseLiftFully(LinearOpMode opMode) {
        linearSlide.setMode(STOP_AND_RESET_ENCODER);
        //move linear slide up
        double currentPosition = linearSlide.getCurrentPosition();
        while (opMode.opModeIsActive() && currentPosition > -1670) {

            setVelocity(linearSlide, -0.5);
            opMode.telemetry.addLine("position:" + currentPosition);
            opMode.telemetry.update();
            currentPosition = linearSlide.getCurrentPosition();
        }
        setVelocity(linearSlide, 0);
    }

    public static void depositCargoOnMidLevel(LinearOpMode opMode) {
        //move linear slide up
        raiseLiftFully(opMode);

        //close servo on cargo
        secureCargo(opMode);

        //rotate arm to position
        while (arm.getCurrentPosition() < COUNTS_FOR_MID_LEVEL && opMode.opModeIsActive()) {
            if (arm.getCurrentPosition() < COUNTS_FOR_MID_LEVEL / 2.0) {
                setVelocity(arm, 0.4);
            } else {
                setVelocity(arm, 0.2);
            }
            opMode.telemetry.addLine("arm: " + arm.getCurrentPosition());
            opMode.telemetry.update();
        }
        arm.setZeroPowerBehavior(BRAKE);
        setVelocity(arm, 0);

        //fully drop cargo
        dropCargo(opMode);


        //move arm back to start
        moveArmBackToStart(opMode);

        //move dropper back to open position
        resetDropper(opMode);
    }

    public static void depositCargoOnTopLevel(LinearOpMode opMode) {
        //move linear slide up
        raiseLiftFully(opMode);

        //close servo on cargo
        secureCargo(opMode);

        //rotate arm to position
        while (arm.getCurrentPosition() < COUNTS_FOR_TOP_LEVEL && opMode.opModeIsActive()) {
            if (arm.getCurrentPosition() < COUNTS_FOR_TOP_LEVEL / 2.0) {
                setVelocity(arm, 0.4);
            } else {
                setVelocity(arm, 0.2);
            }
            opMode.telemetry.addLine("arm: " + arm.getCurrentPosition());
            opMode.telemetry.update();
        }
        arm.setZeroPowerBehavior(BRAKE);
        setVelocity(arm, 0);

        //fully drop cargo
        dropCargo(opMode);

        //move arm back to start
        moveArmBackToStart(opMode);

        //reset dropper
        resetDropper(opMode);

        //move lift back down
//        lowerLift(opMode);
    }

    public static void depositCargoOnBotLevel(LinearOpMode opMode) {
        //rotate arm to position and secure cargo whilst doing so
        while (arm.getCurrentPosition() < COUNTS_FOR_BOT_LEVEL && opMode.opModeIsActive()) {
            dropper.setDirection(FORWARD);
            dropper.setPosition(DROPPER_SECURE_POSITION);
            opMode.sleep(0);
            opMode.idle();
            if (arm.getCurrentPosition() < COUNTS_FOR_BOT_LEVEL / 2) {
                setVelocity(arm, 0.2);
            } else {

                setVelocity(arm, 0.1);
            }
        }
        arm.setZeroPowerBehavior(BRAKE);
        setVelocity(arm, 0);

        //close servo on cargo
        secureCargo(opMode);

        //rotate arm to position
        while (arm.getCurrentPosition() < COUNTS_FOR_BOT_LEVEL && opMode.opModeIsActive()) {
            if (arm.getCurrentPosition() < COUNTS_FOR_BOT_LEVEL / 2) {
                setVelocity(arm, 0.2);
            } else {

                setVelocity(arm, 0.1);
            }
            opMode.telemetry.addLine("arm: " + arm.getCurrentPosition());
            opMode.telemetry.update();
        }
        arm.setZeroPowerBehavior(BRAKE);
        setVelocity(arm, 0);

        //fully drop cargo
        dropCargo(opMode);

        //move arm back to mid
        while (arm.getCurrentPosition() > COUNTS_FOR_BOT_MID && opMode.opModeIsActive()) {
            setVelocity(arm, -0.2);
            opMode.telemetry.addLine("arm: " + arm.getCurrentPosition());
            opMode.telemetry.update();
        }
        arm.setZeroPowerBehavior(BRAKE);
        setVelocity(arm, 0);

        //move dropper back to open position
        resetDropper(opMode);

        //move arm back to start
        moveArmBackToStart(opMode);
    }

    private static void moveArmBackToStart(LinearOpMode opMode) {
        while (arm.getCurrentPosition() > ARM_START_POSITION && opMode.opModeIsActive()) {
            if (arm.getCurrentPosition() > 650) {
                setVelocity(arm, -0.4);
            } else {
                arm.setZeroPowerBehavior(FLOAT);
                arm.setPower(0);
            }
            opMode.telemetry.addLine("arm: " + arm.getCurrentPosition());
            opMode.telemetry.update();
        }
        arm.setZeroPowerBehavior(BRAKE);
        setVelocity(arm, 0);
    }

    public static void lowerLift(LinearOpMode opMode) {
        //Move linear slide back down
        double currentPosition = linearSlide.getCurrentPosition();
        while (opMode.opModeIsActive() && currentPosition < -120) {
            setVelocity(linearSlide, 0.5);
            currentPosition = linearSlide.getCurrentPosition();
        }
        setVelocity(linearSlide, 0);
    }

    public static void raiseLiftPartially(LinearOpMode opMode) {
        //move linear slide up
        double currentPosition = linearSlide.getCurrentPosition();
        while (opMode.opModeIsActive() && currentPosition > -1670 / (2.0)) {

            setVelocity(linearSlide, -0.5);
            opMode.telemetry.addLine("position:" + currentPosition);
            opMode.telemetry.update();
            currentPosition = linearSlide.getCurrentPosition();
        }
        setVelocity(linearSlide, 0);
    }

    public static void resetDropper(LinearOpMode opMode) {
        dropper.setDirection(FORWARD);
        dropper.setPosition(DROPPER_FIT_POSITION);
        opMode.sleep(1000);
        opMode.idle();
    }

    public static void dropCargo(LinearOpMode opMode) {
        dropper.setDirection(FORWARD);
        dropper.setPosition(DROPPER_CLOSED_POSITION);
        opMode.sleep(500);
        opMode.idle();
    }

    public static void secureCargo(LinearOpMode opMode) {
        dropper.setDirection(FORWARD);
        dropper.setPosition(DROPPER_SECURE_POSITION);
        opMode.sleep(1000);
        opMode.idle();
    }
}
