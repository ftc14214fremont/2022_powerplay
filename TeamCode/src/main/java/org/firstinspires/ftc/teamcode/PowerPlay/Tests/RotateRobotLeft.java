package org.firstinspires.ftc.teamcode.PowerPlay.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static org.firstinspires.ftc.teamcode.PowerPlay.Helpers.ImuFunctions.getAngle;
import static org.firstinspires.ftc.teamcode.PowerPlay.Helpers.MotorFunctions.setVelocity;
import static org.firstinspires.ftc.teamcode.PowerPlay.Helpers.NvyusRobotHardware.*;

@Autonomous
public class RotateRobotLeft extends LinearOpMode {
    @Override
    public void runOpMode() {
        initializeNvyusRobotHardware(RotateRobotLeft.this);

        telemetry.addLine("ready");
        waitForStart();

        rotate();
    }

    private void rotate() {
        int degrees = 45; // desired degrees
        double forward_v = -0.5;
        double backward_v = 0.5;

        if (getAngle() < degrees) { // left
            setVelocity(BL, backward_v); //backwards
            setVelocity(BR, forward_v); // forwards
        }
    }
}
