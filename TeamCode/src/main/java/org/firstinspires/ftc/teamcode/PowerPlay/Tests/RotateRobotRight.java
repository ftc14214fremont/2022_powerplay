package org.firstinspires.ftc.teamcode.PowerPlay.Tests;


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static org.firstinspires.ftc.teamcode.PowerPlay.Helpers.ImuFunctions.getAngle;
import static org.firstinspires.ftc.teamcode.PowerPlay.Helpers.MotorFunctions.setVelocity;
import static org.firstinspires.ftc.teamcode.PowerPlay.Helpers.NvyusRobotHardware.*;

@Autonomous
public class RotateRobotRight extends LinearOpMode {
   @Override
   public void runOpMode() {
      initializeNvyusRobotHardware(RotateRobotRight.this);

      telemetry.addLine("ready");
      waitForStart();

      int degrees = 45;
      double forward_v = -0.5;
      double backward_v = 0.5;

      if (getAngle() < degrees) { // right
         setVelocity(BL, forward_v); // forwards
         setVelocity(BR, backward_v); // backwards
      }
   }
}
