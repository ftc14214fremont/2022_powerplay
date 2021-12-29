package org.firstinspires.ftc.teamcode.FreightFrenzy;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.FLOAT;
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
public class FinalAuto extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        initializeNvyusRobotHardware(FinalAuto.this);
        initializeNvyusRobotCamera(FinalAuto.this);

        telemetry.addLine("ready");
        telemetry.update();
        telemetry.addLine("position " + getShippingElementPosition());
        telemetry.update();

        //reset encoders
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //reset IMU
        resetAngle();

        waitForStart();

        phoneCam.closeCameraDevice();

        //set zero power behavior
        BL.setZeroPowerBehavior(BRAKE);
        BR.setZeroPowerBehavior(BRAKE);
        FL.setZeroPowerBehavior(FLOAT);
        FR.setZeroPowerBehavior(FLOAT);


        double currentAngle = getAngle();
        //move forward to get in right position

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
//
//        while (BRposition < 76 && opModeIsActive()) {
//            setVelocity(BL, 0.2);
//            setVelocity(BR, 0.2);
//            setVelocity(FL, 0.2);
//            setVelocity(FR, 0.2);
//            telemetry.addLine("BL:" + BRposition);
//            telemetry.update();
//            BRposition = BL.getCurrentPosition();
//        }

        sleep(800);

        BL.setPower(0);
        BR.setPower(0);
        FL.setPower(0);
        FR.setPower(0);

        sleep(800);

        while (currentAngle < 110 && opModeIsActive()) {
            BL.setPower(0);
            setVelocity(BR, 0.6);
            telemetry.addLine("angle: " + currentAngle);
            telemetry.update();
            currentAngle = getAngle();
        }
        BR.setPower(0);
        sleep(800);
        setVelocity(BL, 0.1);
        setVelocity(BR, 0.1);
        sleep(800);
        BR.setPower(0);
        BL.setPower(0);
        carousel.setPower(0.25);
        sleep(3000);
        carousel.setPower(0);

//            while (opModeIsActive()) {
//
//
//            if (getShippingElementPosition() == LEFT) {
//                telemetry.addLine("1");
//            } else if (getShippingElementPosition() == CENTER) {
//                telemetry.addLine("2");
//            } else if (getShippingElementPosition() == RIGHT) {
//                telemetry.addLine("3");
//            }
//        }
    }
}
