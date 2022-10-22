package org.firstinspires.ftc.teamcode.PowerPlay.Helpers;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.STOP_AND_RESET_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;
import static org.firstinspires.ftc.teamcode.PowerPlay.Helpers.Constants.COUNTS_PER_INCH_TANK_DRIVE;
import static org.firstinspires.ftc.teamcode.PowerPlay.Helpers.ImuFunctions.getAngle;
import static org.firstinspires.ftc.teamcode.PowerPlay.Helpers.MotorFunctions.setVelocity;
import static org.firstinspires.ftc.teamcode.PowerPlay.Helpers.NvyusRobotHardware.*;

public class MovementFunctions {
    public static void turnCCWBL(double turnAngle, LinearOpMode opMode) {
        double currentAngle = getAngle();
        BL.setDirection(DcMotorSimple.Direction.FORWARD);
        BR.setPower(0);
        while (currentAngle < turnAngle && opMode.opModeIsActive()) {
            if (currentAngle < turnAngle * 0.6) {
                setVelocity(BL, 0.4);
            } else {
                setVelocity(BL, 0.2);
            }
            opMode.telemetry.addLine("angle: " + currentAngle);
            opMode.telemetry.update();
            currentAngle = getAngle();
        }

        BL.setPower(0);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);
        opMode.sleep(1000);
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

    public static void moveForward(double inches, double speed, LinearOpMode opMode) {
        //reset all encoders
        BL.setMode(STOP_AND_RESET_ENCODER);
        BR.setMode(STOP_AND_RESET_ENCODER);
        FL.setMode(STOP_AND_RESET_ENCODER);
        FR.setMode(STOP_AND_RESET_ENCODER);

        //set motor directions
        FL.setDirection(REVERSE);
        FR.setDirection(FORWARD);
        BL.setDirection(REVERSE);
        BR.setDirection(FORWARD);

        int position = BL.getCurrentPosition();

        while (position < (inches * COUNTS_PER_INCH_TANK_DRIVE) && opMode.opModeIsActive()) {
            setVelocity(BL, speed);
            setVelocity(BR, speed);
            setVelocity(FL, speed);
            setVelocity(FR, speed);
            position = BL.getCurrentPosition();
            opMode.telemetry.addLine("position: " + position);
            opMode.telemetry.update();
        }
        setVelocity(BL, 0);
        setVelocity(BR, 0);
        setVelocity(FL, 0);
        setVelocity(FR, 0);

        opMode.sleep(100);
    }

    public static void moveBackward(double inches, double speed, LinearOpMode opMode) {
        //reset all encoders
        BL.setMode(STOP_AND_RESET_ENCODER);
        BR.setMode(STOP_AND_RESET_ENCODER);
        FL.setMode(STOP_AND_RESET_ENCODER);
        FR.setMode(STOP_AND_RESET_ENCODER);

        //set motor directions
        FL.setDirection(FORWARD);
        FR.setDirection(REVERSE);
        BL.setDirection(FORWARD);
        BR.setDirection(REVERSE);

        int position = BL.getCurrentPosition();

        while (position < (inches * COUNTS_PER_INCH_TANK_DRIVE) && opMode.opModeIsActive()) {
            setVelocity(BL, speed);
            setVelocity(BR, speed);
            setVelocity(FL, speed);
            setVelocity(FR, speed);
            position = BL.getCurrentPosition();
            opMode.telemetry.addLine("position: " + position);
            opMode.telemetry.update();
        }
        setVelocity(BL, 0);
        setVelocity(BR, 0);
        setVelocity(FL, 0);
        setVelocity(FR, 0);

        opMode.sleep(100);
    }
}
