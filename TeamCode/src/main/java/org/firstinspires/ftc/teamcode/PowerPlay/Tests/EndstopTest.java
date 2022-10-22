package org.firstinspires.ftc.teamcode.PowerPlay.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import static org.firstinspires.ftc.teamcode.PowerPlay.Helpers.MotorFunctions.setVelocity;
import static org.firstinspires.ftc.teamcode.PowerPlay.Helpers.NvyusRobotHardware.initializeNvyusRobotHardware;
import static org.firstinspires.ftc.teamcode.PowerPlay.Helpers.NvyusRobotHardware.linearSlide;

@TeleOp
public class EndstopTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {

        initializeNvyusRobotHardware(EndstopTest.this);
        linearSlide.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        telemetry.addLine("ready");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            //remember, on gamepad stick up = negative, stick down = positive
            if (gamepad2.left_stick_y > 0.3 && linearSlide.getCurrentPosition() < -120) {
                //slow down near endstops
                if (linearSlide.getCurrentPosition() > -900) {
                    setVelocity(linearSlide, 0.5);
                } else {
                    setVelocity(linearSlide, 0.6);
                }
            } else if (gamepad2.left_stick_y < -0.3 && linearSlide.getCurrentPosition() > -1670) {
                if (linearSlide.getCurrentPosition() < -1000) {
                    setVelocity(linearSlide, -0.5);
                } else {
                    setVelocity(linearSlide, -0.6);
                }
            } else {
                setVelocity(linearSlide, 0);
            }
//            if (gamepad2.left_stick_y < -0.3) {
//                setVelocity(linearSlide, 0.2);
//            } else if (gamepad2.left_stick_y > 0.3) {
//                setVelocity(linearSlide, -0.2);
//            } else {
//                setVelocity(linearSlide, 0);
//            }
            telemetry.addLine("currentPosition: " + linearSlide.getCurrentPosition());
            telemetry.update();
        }

    }
}
