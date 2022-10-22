package org.firstinspires.ftc.teamcode.PowerPlay.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import static org.firstinspires.ftc.teamcode.PowerPlay.Helpers.NvyusRobotHardware.initializeNvyusRobotHardware;

@TeleOp
public class ButtonTest extends LinearOpMode {
    @Override
    public void runOpMode() {
        initializeNvyusRobotHardware(this);

        telemetry.addLine("ready");
        telemetry.update();

        waitForStart();

//        Button deposit = new Button();
//        boolean depositValue = false;
//        while (opModeIsActive()) {
//            if (deposit.isPressed)
//        }
    }
}
