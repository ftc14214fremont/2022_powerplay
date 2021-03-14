package org.firstinspires.ftc.teamcode.NonRunnable.Logic.RingLogic;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.NonRunnable.Functions.DrivePath;

import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.ImuFunctions.correctToHeading;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.ImuFunctions.turn;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.WobbleArmFunctions.moveWobbleArmDown;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.WobbleArmFunctions.moveWobbleArmUp;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.WobbleArmFunctions.releaseWobbleGoal;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.DriveMode;

public final class NoRingsBehavior
{
    private NoRingsBehavior()
    {
    }
    
    public static void doNoRingsBehavior(LinearOpMode opMode)
    {
        DrivePath moveForwardOntoLaunchLine = new DrivePath(0.4, 34, DriveMode.FORWARD, opMode);
        DrivePath strafeToZone              = new DrivePath(0.4, 8, DriveMode.STRAFE_LEFT, opMode);
        DrivePath releaseByMovingBack       = new DrivePath(0.4, 4, DriveMode.BACKWARD, opMode);
        DrivePath strafeRightForNextWobble  = new DrivePath(0.4, 10, DriveMode.STRAFE_RIGHT, opMode);
        DrivePath moveBackToSecondWobble    = new DrivePath(0.4, 77, DriveMode.BACKWARD, opMode);
        DrivePath moveBackToWobble          = new DrivePath(0.4, 13, DriveMode.BACKWARD, opMode);
        DrivePath strafeToDeliverSecond     = new DrivePath(0.3, 63, DriveMode.STRAFE_LEFT, opMode);
        //        DrivePath moveBackFromWobble = new DrivePath(0.5, 4, DriveMode.BACKWARD, opMode);
        DrivePath strafeToPark  = new DrivePath(0.4, 20, DriveMode.STRAFE_LEFT, opMode);
        DrivePath forwardToPark = new DrivePath(0.4, 15, DriveMode.FORWARD, opMode);
        
        moveForwardOntoLaunchLine.go();
        strafeToZone.go();
        moveWobbleArmDown(opMode);
        releaseWobbleGoal(opMode);
        releaseByMovingBack.go();
        moveWobbleArmUp(opMode);
        strafeRightForNextWobble.go();
        moveBackToSecondWobble.go();
        turn(-90, opMode);
        correctToHeading(-90, opMode);
        moveBackToWobble.go();
        strafeToDeliverSecond.go();
        forwardToPark.go();
        strafeToPark.go();
    }
}
