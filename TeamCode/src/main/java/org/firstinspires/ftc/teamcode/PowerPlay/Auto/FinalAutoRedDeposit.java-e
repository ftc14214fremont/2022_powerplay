package org.firstinspires.ftc.teamcode.FreightFrenzy.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.STOP_AND_RESET_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.FLOAT;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Auto.ShippingElementDetectionFinal.SkystoneDeterminationPipeline.SkystonePosition.*;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Auto.ShippingElementDetectionFinal.SkystoneDeterminationPipeline.getShippingElementPosition;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.Constants.COUNTS_PER_INCH_TANK_DRIVE;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.Constants.DROPPER_FIT_POSITION;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.ImuFunctions.getAngle;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.ImuFunctions.resetAngle;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.MotorFunctions.setVelocity;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.MovementFunctions.moveBackward;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.MovementFunctions.moveForward;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.NvyusRobotHardware.*;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.OuttakeFunctions.*;

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
public class FinalAutoRedDeposit extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        initializationStuff();
        waitForStart();
        phoneCam.closeCameraDevice();


        if (getShippingElementPosition() == RIGHT) {// right is left vice versa
            moveForward(10, 0.3, this);
            turnCCWBR(172);
            moveBackward(15, 0.3, this);
            depositCargoOnTopLevel(this);
            turnCCWBR(262);
            //raise dropper to make sure we are fully parked
            dropper.setPosition(DROPPER_FIT_POSITION);
            sleep(1000);
            idle();
            moveForward(40, 0.5, this);
            lowerLift(this);


        } else if (getShippingElementPosition() == CENTER) {
            moveForward(10, 0.3, this);
            turnCCWBR(166);
            moveBackward(5, 0.3, this);
            depositCargoOnMidLevel(this);
            moveBackward(7, 0.3, this);
            turnCCWBR(262);
            //raise dropper to make sure we are fully parked
            dropper.setPosition(DROPPER_FIT_POSITION);
            sleep(1000);
            idle();
            moveForward(60, 0.5, this);
            lowerLift(this);


        } else if (getShippingElementPosition() == LEFT) {//left is right
            moveForward(10, 0.3, this);
            turnCCWBR(166);
            moveBackward(3, 0.3, this);
            depositCargoOnBotLevel(this);
            moveBackward(8, 0.3, this);
            turnCCWBR(262);
            //raise dropper to make sure we are fully parked
            dropper.setPosition(DROPPER_FIT_POSITION);
            sleep(1000);
            idle();
            moveForward(60, 0.5, this);


        }
    }

    private void moveBackSpecial(double speed, double inches) {
        //set motor directions
        FL.setDirection(FORWARD);
        FR.setDirection(REVERSE);
        BL.setDirection(FORWARD);
        BR.setDirection(REVERSE);

        //reset all encoders
        BL.setMode(STOP_AND_RESET_ENCODER);
        BR.setMode(STOP_AND_RESET_ENCODER);
        FL.setMode(STOP_AND_RESET_ENCODER);
        FR.setMode(STOP_AND_RESET_ENCODER);
        int position = BL.getCurrentPosition();

        while (Math.abs(position) < (inches * COUNTS_PER_INCH_TANK_DRIVE) && opModeIsActive()) {
            if (Math.abs(position) < (inches * COUNTS_PER_INCH_TANK_DRIVE * 0.6)) {
                setVelocity(BL, speed);
                setVelocity(BR, speed);
                setVelocity(FL, speed);
                setVelocity(FR, speed);
                position = BL.getCurrentPosition();
                telemetry.addLine("position: " + position);
                telemetry.update();
            } else {
                setVelocity(BL, speed / 2);
                setVelocity(BR, speed / 2);
                setVelocity(FL, speed / 2);
                setVelocity(FR, speed / 2);
                position = BL.getCurrentPosition();
                telemetry.addLine("position: " + position);
                telemetry.update();
            }

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
        initializeNvyusRobotHardware(FinalAutoRedDeposit.this);
        initializeNvyusRobotCamera(FinalAutoRedDeposit.this);

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


    private void turnCCWBR(double turnAngle) {
        double currentAngle = getAngle();
        BR.setDirection(FORWARD);

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
        sleep(1000);
    }

    private void turnCWBR(double turnAngle) {
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
        BR.setDirection(FORWARD);
        sleep(1000);
    }

    private void turnCW(double turnAngle) {
        double currentAngle = getAngle();
        BR.setDirection(REVERSE);
        BL.setDirection(REVERSE);

        while (Math.abs(currentAngle) > turnAngle && opModeIsActive()) {
            setVelocity(BR, 0.2);
            setVelocity(BL, 0.2);

            telemetry.addLine("angle: " + currentAngle);
            telemetry.update();
            currentAngle = getAngle();
        }
        BR.setPower(0);
        BL.setPower(0);
        sleep(1000);
    }

    private void turnCC(double turnAngle) {
        double currentAngle = getAngle();
        BR.setDirection(FORWARD);
        BL.setDirection(FORWARD);

        while (currentAngle > turnAngle && opModeIsActive()) {
            setVelocity(BR, 0.2);
            setVelocity(BL, 0.2);

            telemetry.addLine("angle: " + currentAngle);
            telemetry.update();
            currentAngle = getAngle();
        }
        BR.setPower(0);
        BL.setPower(0);
        sleep(1000);

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
