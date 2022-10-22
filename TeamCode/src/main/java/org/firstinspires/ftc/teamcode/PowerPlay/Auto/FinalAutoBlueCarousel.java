
package org.firstinspires.ftc.teamcode.PowerPlay.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;
import static org.firstinspires.ftc.teamcode.PowerPlay.Auto.ShippingElementDetectionFinal.SkystoneDeterminationPipeline.SkystonePosition.*;
import static org.firstinspires.ftc.teamcode.PowerPlay.Auto.ShippingElementDetectionFinal.SkystoneDeterminationPipeline.getShippingElementPosition;
import static org.firstinspires.ftc.teamcode.PowerPlay.Helpers.Constants.DROPPER_FIT_POSITION;
import static org.firstinspires.ftc.teamcode.PowerPlay.Helpers.ImuFunctions.getAngle;
import static org.firstinspires.ftc.teamcode.PowerPlay.Helpers.MotorFunctions.setVelocity;
import static org.firstinspires.ftc.teamcode.PowerPlay.Helpers.MovementFunctions.moveBackward;
import static org.firstinspires.ftc.teamcode.PowerPlay.Helpers.MovementFunctions.moveForward;
import static org.firstinspires.ftc.teamcode.PowerPlay.Helpers.NvyusRobotHardware.*;
import static org.firstinspires.ftc.teamcode.PowerPlay.Helpers.OuttakeFunctions.*;

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
///*
@Autonomous
public class FinalAutoBlueCarousel extends LinearOpMode {
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

    @Override
    public void runOpMode() throws InterruptedException {
        initializationStuff(this);
        waitForStart();
        phoneCam.closeCameraDevice();

        moveForward(17, 0.3, this);
        turnCW(-120, false);
        spinCarousel();
        telemetry.clearAll();
        telemetry.addLine("currentPos: " + BL.getCurrentPosition());
        telemetry.update();
        moveForward(15, 0.3, this);
        turnCW(-140, true);
        stopSpinningCarousel(3500, this);
        turnCCWBL(-128, this);

        if (getShippingElementPosition() == RIGHT) {
            moveBackward(32, 0.3, this);
            depositCargoOnTopLevel(this);
            moveForward(20, 0.3, this);
            turnCCWBR(-100, this);
            moveBackward(85, 0.5, this);
            //lowerLift(this);
        } else if (getShippingElementPosition() == CENTER) {
            moveBackward(24, 0.3, this);
            depositCargoOnMidLevel(this);
            moveForward(9, 0.3, this);
            turnCCWBR(-100, this);
            moveBackward(85, 0.5, this);
            lowerLift(this);
        } else if (getShippingElementPosition() == LEFT) {
            moveBackward(22, 0.3, this);
            depositCargoOnBotLevel(this);
            moveForward(8, 0.3, this);
            turnCCWBR(-100, this);
            raiseLiftPartially(this);
            moveBackward(85, 0.5, this);
            lowerLift(this);
        }
        turnCW(-90, false);
    }

    private void turnCW(double turnAngle, boolean emergencyStop) {
        ElapsedTime elapsedTime = new ElapsedTime();
        double currentAngle = getAngle();
        BR.setDirection(REVERSE);
        BL.setDirection(REVERSE);

        elapsedTime.reset();
        if (emergencyStop) {
            while (currentAngle > turnAngle && opModeIsActive() && elapsedTime.milliseconds() < 2000) {
                setVelocity(BR, 0.3);
                setVelocity(BL, 0.3);

                telemetry.addLine("angle: " + currentAngle);
                telemetry.update();
                currentAngle = getAngle();
            }
        } else {
            while (currentAngle > turnAngle && opModeIsActive()) {
                setVelocity(BR, 0.3);
                setVelocity(BL, 0.3);

                telemetry.addLine("angle: " + currentAngle);
                telemetry.update();
                currentAngle = getAngle();
            }
        }
        BR.setPower(0);
        BL.setPower(0);
        sleep(1000);
    }

    private void turnAfterTurningCarousel(double turnAngle) {
        BL.setDirection(FORWARD);

        if (getShippingElementPosition() == RIGHT) {
            moveBackward(32, 0.3, this);
            depositCargoOnTopLevel(this);
            moveForward(18, 0.3, this);
            //turnCW(94);
            moveBackward(85, 0.5, this);
            lowerLift(this);

            //raise dropper to make sure we are fully parked
            dropper.setPosition(DROPPER_FIT_POSITION);
            sleep(1000);
            idle();
        } else if (getShippingElementPosition() == CENTER) {
            moveBackward(21, 0.3, this);
            depositCargoOnMidLevel(this);
            moveForward(7, 0.3, this);
            //turnCW(94);
            moveBackward(85, 0.5, this);
            lowerLift(this);

            //raise dropper to make sure we are fully parked
            dropper.setPosition(DROPPER_FIT_POSITION);
            sleep(1000);
            idle();
        } else if (getShippingElementPosition() == LEFT) {
            moveBackward(20, 0.3, this);
            depositCargoOnBotLevel(this);
            moveForward(6, 0.3, this);
            //turnCW(94);
            raiseLiftPartially(this);
            moveBackward(85, 0.5, this);
            lowerLift(this);

            //raise dropper to make sure we are fully parked
            dropper.setPosition(DROPPER_FIT_POSITION);
            sleep(1000);
            idle();
        }
    }
}

