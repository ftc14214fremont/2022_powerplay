package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.NonRunnable.Functions.DrivePath;
import org.firstinspires.ftc.teamcode.NonRunnable.Logic.RingLogic.RingDeterminationPipeline;
import org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants;

import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.setVelocity;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.ImuFunctions.correctToHeading;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.IntakeFunctions.shoot;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.WobbleArmFunctions.gripWobbleGoal;
import static org.firstinspires.ftc.teamcode.NonRunnable.Logic.RingLogic.FourRingsBehavior.doFourRingsBehavior;
import static org.firstinspires.ftc.teamcode.NonRunnable.Logic.RingLogic.NoRingsBehavior.doNoRingsBehavior;
import static org.firstinspires.ftc.teamcode.NonRunnable.Logic.RingLogic.OneRingBehavior.doOneRingBehavior;
import static org.firstinspires.ftc.teamcode.NonRunnable.Logic.RingLogic.RingDeterminationPipeline.position;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.highGoalSpeed;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.activateOpenCvCamera;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.flyWheel;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.initHardware;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.phoneCam;

@Autonomous
public class FinalAuto extends LinearOpMode
{
    @Override
    public void runOpMode()
    {
        activateOpenCvCamera(FinalAuto.this);
        initHardware(FinalAuto.this);
        
        telemetry.addData("Mode", "Ready");
        telemetry.update();
        
        waitForStart();
        
        DrivePath strafeRightAtBeginning = new DrivePath(0.5, 19, Constants.DriveMode.STRAFE_RIGHT, this);
        DrivePath advanceToShootingLine  = new DrivePath(0.5, 47.5, Constants.DriveMode.FORWARD, this);
        DrivePath strafeToAim            = new DrivePath(0.5, 22, Constants.DriveMode.STRAFE_LEFT, this);
        
        phoneCam.closeCameraDevice();
        gripWobbleGoal(this);
        
        if (position == RingDeterminationPipeline.RingPosition.NONE)
        {
            DrivePath strafeToAimNone = new DrivePath(0.4, 3, Constants.DriveMode.STRAFE_LEFT, this);
            
            setVelocity(flyWheel, highGoalSpeed);
            advanceToShootingLine.go();
            strafeToAimNone.go();
        }
        else
        {
            strafeRightAtBeginning.go();
            setVelocity(flyWheel, highGoalSpeed);
            advanceToShootingLine.go();
            strafeToAim.go();
        }
        
        shoot(this, 3000);
        correctToHeading(0, this);
        
        if (position == RingDeterminationPipeline.RingPosition.ONE)
        {
            doOneRingBehavior(this);
        }
        else if (position == RingDeterminationPipeline.RingPosition.FOUR)
        {
            doFourRingsBehavior(this);
        }
        else
        {
            doNoRingsBehavior(this);
        }
    }
}

