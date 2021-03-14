package org.firstinspires.ftc.teamcode.Auto.RandomTests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static org.firstinspires.ftc.teamcode.NonRunnable.Logic.RingLogic.RingDeterminationPipeline.position;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.activateOpenCvCamera;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.initHardware;

@Autonomous
public class RingDetectionTest extends LinearOpMode
{
    @Override
    public void runOpMode()
    {
        activateOpenCvCamera(this);
        initHardware(this);
        
        telemetry.addData("Mode", "Ready");
        telemetry.update();
        
        waitForStart();
        
        telemetry.addData("rings found:", position);
        telemetry.update();
        while (opModeIsActive())
        {
        
        }
    }
}
