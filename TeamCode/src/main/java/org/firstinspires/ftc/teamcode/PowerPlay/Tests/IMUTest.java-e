package org.firstinspires.ftc.teamcode.FreightFrenzy.Tests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.ImuFunctions.getAngle;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.NvyusRobotHardware.initializeNvyusRobotHardware;

@TeleOp
public class IMUTest extends LinearOpMode {
   @Override
   public void runOpMode() throws InterruptedException {
      initializeNvyusRobotHardware(this);

      telemetry.addLine("ready");
      telemetry.update();

      waitForStart();

      while (opModeIsActive()) {
         telemetry.addLine("angle: " + getAngle());
         telemetry.update();
      }
   }
}
