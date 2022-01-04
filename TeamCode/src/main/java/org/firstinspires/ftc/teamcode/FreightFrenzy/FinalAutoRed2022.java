package org.firstinspires.ftc.teamcode.FreightFrenzy;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.STOP_AND_RESET_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.FLOAT;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.Constants.COUNTS_PER_INCH_TANK_DRIVE;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.ImuFunctions.getAngle;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.ImuFunctions.resetAngle;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.MotorFunctions.setVelocity;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.NvyusRobotHardware.*;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.ShippingElementDetectionFinal.SkystoneDeterminationPipeline.getShippingElementPosition;

//plan
    /* rotate carousel to deliver duck - 10 pts
    deliver shipping element to correct height - 6 pts + 20 pt bonus if we use a custom marker
    park completely in alliance warehouse - 10 pts


    //teleop:
    6 pts per thing placed on top, 4 for mid, 2 for bottom
    4 pts for freight on your side of shared shipping hub

    endgame
    6 pts per duck delivered
    10 pts - your shipping hub balanced
    20 pts - shared hub tilts on your side
    6 pts - completely in warehouse
    15 pts - "capping"
    */
@Autonomous
public class FinalAutoRed2022 extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        initializationStuff();
        waitForStart();
        phoneCam.closeCameraDevice();

        spinCarousel();
        moveForward(3, 0.3);
        turnToHitCarousel(96);
//        moveForwardSlightly();
        stopSpinningCarousel(3000);
        turnAfterTurningCarousel(118);
        moveForward(36, -0.3);


    }


    private void moveForward(double inches, double speed) {
        //reset all encoders
        BL.setMode(STOP_AND_RESET_ENCODER);
        BR.setMode(STOP_AND_RESET_ENCODER);
        FL.setMode(STOP_AND_RESET_ENCODER);
        FR.setMode(STOP_AND_RESET_ENCODER);
        int position = BR.getCurrentPosition();

        while (Math.abs(position) < (inches * COUNTS_PER_INCH_TANK_DRIVE) && opModeIsActive()) {
            setVelocity(BL, speed);
            setVelocity(BR, speed);
            setVelocity(FL, speed);
            setVelocity(FR, speed);
            position = BL.getCurrentPosition();
            telemetry.addLine("position: " + position);
            telemetry.update();
        }
        setVelocity(BL, 0);
        setVelocity(BR, 0);
        setVelocity(FL, 0);
        setVelocity(FR, 0);

        sleep(100);
    }

    private void stopSpinningCarousel(int spinningTime) {
        sleep(spinningTime);
        carousel.setPower(0);
    }

    private void initializationStuff() {
        initializeNvyusRobotHardware(FinalAutoRed2022.this);
        initializeNvyusRobotCamera(FinalAutoRed2022.this);

        telemetry.addLine("ready");
        telemetry.update();
        telemetry.addLine("position " + getShippingElementPosition());
        telemetry.update();

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

    private void spinCarousel() {
        carousel.setPower(0.9);
    }

    private void moveForwardSlightly() {
        setVelocity(BL, 0.1);
        setVelocity(BR, 0.1);
        sleep(500);
        BL.setPower(0);
        BR.setPower(0);
    }


    private void turnToHitCarousel(double turnAngle) {
        double currentAngle = getAngle();

        while (currentAngle < turnAngle && opModeIsActive()) {
            BL.setPower(0);
            if (currentAngle < turnAngle * 0.6) {
                setVelocity(BR, 0.4);
            } else {
                setVelocity(BR, 0.2);
            }
            telemetry.addLine("angle: " + currentAngle);
            telemetry.update();
            currentAngle = getAngle();
        }
        BR.setPower(0);
        sleep(100);
    }

    private void moveForwardInitially() {
        int BRposition = BR.getCurrentPosition();

        while (BRposition < 15 && opModeIsActive()) {
            setVelocity(BL, 0.1);
            setVelocity(BR, 0.1);
            setVelocity(FL, 0.1);
            setVelocity(FR, 0.1);
            telemetry.addLine("BL:" + BRposition);
            telemetry.update();
            BRposition = BL.getCurrentPosition();
        }

        sleep(800);

        BL.setPower(0);
        BR.setPower(0);
        FL.setPower(0);
        FR.setPower(0);

        sleep(100);


    }

    private void turnAfterTurningCarousel(double turnAngle) {
        double currentAngle = getAngle();

        while (currentAngle < turnAngle && opModeIsActive()) {
            BR.setPower(0);
            setVelocity(BL, -0.6);
            telemetry.addLine("angle: " + currentAngle);
            telemetry.update();
            currentAngle = getAngle();
        }
        BL.setPower(0);
        sleep(100);
    }
}
