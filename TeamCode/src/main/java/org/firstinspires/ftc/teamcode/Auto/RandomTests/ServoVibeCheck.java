package org.firstinspires.ftc.teamcode.Auto.RandomTests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static org.firstinspires.ftc.robotcontroller.external.samples.ConceptScanServo.CYCLE_MS;
import static org.firstinspires.ftc.robotcontroller.external.samples.ConceptScanServo.INCREMENT;
import static org.firstinspires.ftc.robotcontroller.external.samples.ConceptScanServo.MAX_POS;
import static org.firstinspires.ftc.robotcontroller.external.samples.ConceptScanServo.MIN_POS;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.flap;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.initHardware;

@Autonomous
public class ServoVibeCheck extends LinearOpMode
{
    double  position = (MAX_POS - MIN_POS) / 2;
    boolean rampUp   = true;
    
    @Override
    public void runOpMode()
    {
        initHardware(ServoVibeCheck.this);
        telemetry.addData(">", "Press Start to scan Servo.");
        telemetry.update();
        waitForStart();
        
        while (opModeIsActive())
        {
            if (rampUp)
            {
                position += INCREMENT;
                if (position >= MAX_POS)
                {
                    position = MAX_POS;
                    rampUp = false;
                }
            }
            else
            {
                position -= INCREMENT;
                if (position <= MIN_POS)
                {
                    position = MIN_POS;
                    rampUp = true;
                }
            }
            
            telemetry.addData("Servo Position", "%5.2f", position);
            telemetry.addData(">", "Press Stop to end test.");
            telemetry.update();
            
            flap.setPosition(position);
            sleep(CYCLE_MS);
            idle();
        }
        telemetry.addData(">", "Done");
        telemetry.update();
    }
}
