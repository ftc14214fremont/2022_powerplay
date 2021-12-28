package org.firstinspires.ftc.teamcode.FreightFrenzy.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.NvyusRobotHardware.initializeNvyusRobotHardware;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.NvyusRobotHardware.intake;

@TeleOp
public class StopAtSpecificPosition extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        initializeNvyusRobotHardware(StopAtSpecificPosition.this);
        telemetry.addLine("ready");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.right_bumper) {
                intake.setPower(0.4);
            } else if (gamepad1.left_bumper) {
                intake.setPower(-0.4);
            } else if (intake.getCurrentPosition() % 145 < 14) {
                intake.setPower(0);
            } else {
                intake.setPower(0.4);
            }

            telemetry.addLine("intakePos: " + intake.getCurrentPosition());
            telemetry.update();
        }
    }
}
