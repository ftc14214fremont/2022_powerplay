package org.firstinspires.ftc.teamcode.PowerPlay.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.FLOAT;
import static org.firstinspires.ftc.teamcode.PowerPlay.Helpers.NvyusRobotHardware.*;

@TeleOp
public class RotateTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        initializeNvyusRobotHardware(RotateTest.this);

        telemetry.addLine("ready");
        telemetry.update();

        //set zero behavior
        BL.setZeroPowerBehavior(BRAKE);
        BR.setZeroPowerBehavior(FLOAT);
        FL.setZeroPowerBehavior(FLOAT);
        FR.setZeroPowerBehavior(FLOAT);

        //reset encoders
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        waitForStart();
        while (opModeIsActive()) {
//            BL.setPower(0);
//            setVelocity(BR, 0.4);
            telemetry.addLine("BR:" + BR.getCurrentPosition());
            telemetry.update();
        }
    }
}
