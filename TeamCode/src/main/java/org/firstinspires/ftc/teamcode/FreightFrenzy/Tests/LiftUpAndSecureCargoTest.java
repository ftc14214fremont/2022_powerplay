package org.firstinspires.ftc.teamcode.FreightFrenzy.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.Constants;

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

        while (opModeIsActive() && linearSlide.getCurrentPosition() > -1670) {
            setVelocity(linearSlide, -0.5);
        }
        setVelocity(linearSlide, 0);

        dropper.setPosition(Constants.DROPPER_SECURE_POSITION);
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