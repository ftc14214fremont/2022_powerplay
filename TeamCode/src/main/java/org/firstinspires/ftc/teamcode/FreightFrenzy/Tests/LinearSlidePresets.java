package org.firstinspires.ftc.teamcode.FreightFrenzy.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.MotorFunctions.setVelocity;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.NvyusRobotHardware.initializeNvyusRobotHardware;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.NvyusRobotHardware.linearSlide;

@TeleOp
public class LinearSlidePresets extends LinearOpMode {
    @Override
    public void runOpMode() {
        initializeNvyusRobotHardware(LinearSlidePresets.this);

        telemetry.addLine("ready");
        telemetry.update();
        waitForStart();

        while (opModeIsActive()) {
            // you press the button, then you see if the servo is higher or lower than current position. if the servio is higher
            // than you go down until you reach the position
            // negative power = up , positive power = down
            // declaration of variables
            double d_velocity;
            double u_velocity;
            int low_position;
            int mid_position;
            int high_position;

            // set value of variables
            d_velocity = 0.5;
            u_velocity = -0.5;
            low_position = 80;
            mid_position = 400;
            high_position = 900;
            // low
            if (gamepad1.x) {
                // if position higher
                if (linearSlide.getCurrentPosition() < low_position) {
                    setVelocity(linearSlide, d_velocity);

                } else { // if lower
                    setVelocity(linearSlide, u_velocity);
                }
            }
            // mid
            else if (gamepad1.y) {
                // if position higher
                if (linearSlide.getCurrentPosition() < mid_position) {
                    setVelocity(linearSlide, d_velocity);

                } else { // if lower
                    setVelocity(linearSlide, u_velocity);
                }
            }
            // high
            else if (gamepad1.b) {
                // if position higher
                if (linearSlide.getCurrentPosition() < mid_position) {
                    setVelocity(linearSlide, d_velocity);

                } else { // if lower
                    setVelocity(linearSlide, u_velocity);
                }
            } else {
                setVelocity(linearSlide, 0);
            }

            telemetry.addLine("currentPosition: " + linearSlide.getCurrentPosition());
            telemetry.update();
        }
    }
}
