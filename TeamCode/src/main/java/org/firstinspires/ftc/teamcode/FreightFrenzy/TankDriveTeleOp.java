package org.firstinspires.ftc.teamcode.FreightFrenzy;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.Button;

import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.NvyusRobotHardware.*;

@TeleOp
public class TankDriveTeleOp extends LinearOpMode {
   @Override
   public void runOpMode() {
       initializeNvyusRobotHardware(TankDriveTeleOp.this);
      Button toggleSlowMode = new Button();
      boolean slowMode = false;
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

         if (toggleSlowMode.isPressed(gamepad1.a)) {
            slowMode = !slowMode;
         }

         if (slowMode) {
            FL.setPower(leftPower * 0.5);
            FR.setPower(rightPower * 0.5);
            BL.setPower(leftPower * 0.5);
            BR.setPower(rightPower * 0.5);
         } else {
            FL.setPower(leftPower);
            FR.setPower(rightPower);
            BL.setPower(leftPower);
            BR.setPower(rightPower);
         }
          telemetry.addLine("slowMode:" + slowMode);
          telemetry.update();
      }
   }
}
