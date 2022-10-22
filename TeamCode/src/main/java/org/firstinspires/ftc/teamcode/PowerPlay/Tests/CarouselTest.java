package org.firstinspires.ftc.teamcode.PowerPlay.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static org.firstinspires.ftc.teamcode.PowerPlay.Helpers.NvyusRobotHardware.carousel;
import static org.firstinspires.ftc.teamcode.PowerPlay.Helpers.NvyusRobotHardware.initializeNvyusRobotHardware;

@Autonomous
public class CarouselTest extends LinearOpMode {
    @Override
    public void runOpMode() {
        initializeNvyusRobotHardware(CarouselTest.this);

        telemetry.addLine("ready");
        telemetry.update();

        waitForStart();

        carousel.setPower(0.25);
        sleep(5000);
        carousel.setPower(0);
    }
}
