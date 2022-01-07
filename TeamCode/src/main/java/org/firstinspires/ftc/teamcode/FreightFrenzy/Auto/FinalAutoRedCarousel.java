package org.firstinspires.ftc.teamcode.FreightFrenzy.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.STOP_AND_RESET_ENCODER;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.FLOAT;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Auto.ShippingElementDetectionFinal.SkystoneDeterminationPipeline.SkystonePosition.*;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Auto.ShippingElementDetectionFinal.SkystoneDeterminationPipeline.getShippingElementPosition;
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
public class FinalAutoRedCarousel extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        initializationStuff();
        waitForStart();
        phoneCam.closeCameraDevice();

        spinCarousel();
        moveForward(3, 0.3, this);
        turnCCWBR(96);
        stopSpinningCarousel(3500, this);
        turnAfterTurningCarousel(123);

        if (getShippingElementPosition() == RIGHT) {
            moveBackward(32, 0.3, this);
            depositCargoOnTopLevel(this);
            moveForward(18, 0.3, this);
            turnCW(94);
            moveBackward(85, 0.5, this);
            lowerLift(this);
        } else if (getShippingElementPosition() == CENTER) {
            moveBackward(21, 0.3, this);
            depositCargoOnMidLevel(this);
            moveForward(7, 0.3, this);
            turnCW(94);
            moveBackward(85, 0.5, this);
            lowerLift(this);
        } else if (getShippingElementPosition() == LEFT) {
            moveBackward(20, 0.3);
            depositCargoOnBotLevel();
            moveForward(6, 0.3);
            turnCW(94);
            raiseLiftPartially(this);
            moveBackward(85, 0.5, this);
            lowerLift(this);
        }
    }

    private void initializationStuff() {
        initializeNvyusRobotHardware(FinalAutoRedCarousel.this);
        initializeNvyusRobotCamera(FinalAutoRedCarousel.this);

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

    private void turnCCWBR(double turnAngle) {
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
        sleep(1000);
    }

    private void turnCW(double turnAngle) {
        double currentAngle = getAngle();
        BR.setDirection(REVERSE);
        BL.setDirection(REVERSE);

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
