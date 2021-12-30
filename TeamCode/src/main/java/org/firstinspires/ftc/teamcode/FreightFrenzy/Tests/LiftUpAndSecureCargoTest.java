package org.firstinspires.ftc.teamcode.FreightFrenzy.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.Constants;

import static com.qualcomm.robotcore.hardware.DcMotor.RunMode.STOP_AND_RESET_ENCODER;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.Constants.COUNTS_FOR_TOP_LEVEL;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.MotorFunctions.setVelocity;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.NvyusRobotHardware.*;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.ShippingElementDetectionFinal.SkystoneDeterminationPipeline.getShippingElementPosition;

@Autonomous
public class LiftUpAndSecureCargoTest extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        initializeNvyusRobotHardware(this);

        telemetry.addLine("ready");
        telemetry.update();
        telemetry.addLine("position " + getShippingElementPosition());
        telemetry.update();


        waitForStart();
        //series of commands to get the arm ready

        //reset encoder value
        arm.setMode(STOP_AND_RESET_ENCODER);
        linearSlide.setMode(STOP_AND_RESET_ENCODER);
        //move linear slide up
        while (opModeIsActive() && linearSlide.getCurrentPosition() > -1670) {
            setVelocity(linearSlide, -0.5);
        }
        setVelocity(linearSlide, 0);

        //close servo on cargo
        dropper.setPosition(Constants.DROPPER_SECURE_POSITION);
        sleep(1000);
        idle();

        //rotate arm to position
        while (arm.getCurrentPosition() < COUNTS_FOR_TOP_LEVEL) {
            if (arm.getCurrentPosition() < COUNTS_FOR_TOP_LEVEL / 2.0) {
                setVelocity(arm, 0.4);
            } else {
                setVelocity(arm, 0.1);
            }
            telemetry.addLine("arm: " + arm.getCurrentPosition());
            telemetry.update();
        }
        setVelocity(arm, 0);

        //fully drop cargo
        dropper.setPosition(Constants.DROPPER_CLOSED_POSITION);
        sleep(1000);
        idle();
    }
}

/* //cool servo function
int counter = 0
if gamepad1.x is pressed {
   ++counter
   if counter % 2 = 0 {
      dropper.setPosition(Constants.DROPPER_SECURE_POSITION
   }
   else {
      do this
   }

 */