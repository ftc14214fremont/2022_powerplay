package org.firstinspires.ftc.teamcode.PowerPlay.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.FLOAT;
import static org.firstinspires.ftc.teamcode.PowerPlay.Helpers.NvyusRobotHardware.arm;
import static org.firstinspires.ftc.teamcode.PowerPlay.Helpers.NvyusRobotHardware.initializeNvyusRobotHardware;

@TeleOp
public class ArmTest extends LinearOpMode {
    @Override
    public void runOpMode() {
        initializeNvyusRobotHardware(ArmTest.this);
        arm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        telemetry.addLine("ready");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
//            if (gamepad1.left_stick_y < -0.3 && arm.getCurrentPosition() < 1600) {
//                //go up
//                if (arm.getCurrentPosition() < 1100) {
//                    setVelocity(arm, 0.6);
//                } else {
//                    setVelocity(arm, 0.1);
//                }
//            } else if (gamepad1.left_stick_y > 0.3 && arm.getCurrentPosition() > 120) {
//                //go down
//                if (arm.getCurrentPosition() > 650) {
//                    setVelocity(arm, -0.6);
//                } else {
//                    arm.setZeroPowerBehavior(FLOAT);
//                    arm.setPower(0);
//                }
//            } else {
//                //do nothing
//                arm.setZeroPowerBehavior(BRAKE);
//                setVelocity(arm, 0);
//            }
            arm.setZeroPowerBehavior(FLOAT);
            arm.setPower(0);

            telemetry.addLine("arm: " + arm.getCurrentPosition());
            telemetry.update();
        }
    }

}
