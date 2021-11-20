package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import static org.firstinspires.ftc.teamcode.FreightFrenzy.HardwareConfigs.TankDriveHardware.*;

@TeleOp
public class TankDriveTeleOp extends LinearOpMode {
   @Override
   public void runOpMode() {
      initializeTankDriveHardware(TankDriveTeleOp.this);

      telemetry.addLine("ready");
      telemetry.update();

      waitForStart();

      while (opModeIsActive()) {
         double leftPower;
         double rightPower;


         double turn = gamepad1.right_stick_x;
         double drive = -gamepad1.left_stick_y;
         leftPower = Range.clip(drive + turn, -0.7, 0.7);
         rightPower = Range.clip(drive - turn, -0.7, 0.7);

         FL.setPower(leftPower);
         FR.setPower(rightPower);
         BL.setPower(leftPower);
         BR.setPower(rightPower);
      }
   }
}
