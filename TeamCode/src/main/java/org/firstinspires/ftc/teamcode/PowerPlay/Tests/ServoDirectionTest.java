package org.firstinspires.ftc.teamcode.PowerPlay.Tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static com.qualcomm.robotcore.hardware.Servo.Direction.FORWARD;
import static org.firstinspires.ftc.teamcode.PowerPlay.Helpers.Constants.DROPPER_FIT_POSITION;
import static org.firstinspires.ftc.teamcode.PowerPlay.Helpers.NvyusRobotHardware.dropper;
import static org.firstinspires.ftc.teamcode.PowerPlay.Helpers.NvyusRobotHardware.initializeNvyusRobotHardware;

@Autonomous
public class ServoDirectionTest extends LinearOpMode {
   @Override
   public void runOpMode() throws InterruptedException {
      initializeNvyusRobotHardware(this);

      telemetry.addLine("ready");
      telemetry.update();

      waitForStart();

//      dropper.setPosition(DROPPER_CLOSED_POSITION);
//      sleep(1000);
//      idle();


      dropper.setDirection(FORWARD);
      dropper.setPosition(DROPPER_FIT_POSITION);
      sleep(1000);
      idle();
   }
}
