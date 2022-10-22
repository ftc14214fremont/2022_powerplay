package org.firstinspires.ftc.teamcode.PowerPlay.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import static org.firstinspires.ftc.teamcode.PowerPlay.Helpers.MotorFunctions.setVelocity;
import static org.firstinspires.ftc.teamcode.PowerPlay.Helpers.NvyusRobotHardware.initializeNvyusRobotHardware;
import static org.firstinspires.ftc.teamcode.PowerPlay.Helpers.NvyusRobotHardware.intake;

@TeleOp
public class IntakeTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        initializeNvyusRobotHardware(IntakeTest.this);
        telemetry.addLine("ready");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            if (gamepad1.right_bumper) {
                setVelocity(intake, -0.8);
            } else if (gamepad1.left_bumper) {
                setVelocity(intake, 0.8);
            } else {
                intake.setPower(0);
            }

            telemetry.addLine("intakePos: " + intake.getCurrentPosition());
            telemetry.update();
        }
    }
}
