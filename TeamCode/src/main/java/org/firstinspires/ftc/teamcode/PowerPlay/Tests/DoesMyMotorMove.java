package org.firstinspires.ftc.teamcode.PowerPlay.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static org.firstinspires.ftc.teamcode.PowerPlay.Helpers.NvyusRobotHardware.initializeNvyusRobotHardware;
import static org.firstinspires.ftc.teamcode.PowerPlay.Helpers.NvyusRobotHardware.intake;

@Autonomous
public class DoesMyMotorMove extends LinearOpMode {
    @Override
    public void runOpMode() {
        initializeNvyusRobotHardware(DoesMyMotorMove.this);

        telemetry.addLine("ready");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            intake.setPower(1);
        }
    }
}
