package org.firstinspires.ftc.teamcode.NonRunnable.Functions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.MAX_COUNTS_PER_SECOND;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.PAUSE_BETWEEN_MOVEMENTS;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.BL;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.BR;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.FL;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.FR;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.driveMotorsArray;

public final class GeneralDriveMotorFunctions
{
    private GeneralDriveMotorFunctions()
    {
    }
    
    /*
    - When looking at the front of the goBILDA drive shaft, clockwise is
      FORWARD, counter-clockwise is REVERSE
    - These directions only work assuming a bevel gear configuration on the drivetrain
    */
    public static void setDriveDirection(Constants.DriveMode driveMode)
    {
        if (driveMode == Constants.DriveMode.STRAFE_LEFT)
        {
            setMotorDirections(REVERSE, REVERSE, FORWARD, FORWARD);
        }
        else if (driveMode == Constants.DriveMode.STRAFE_RIGHT)
        {
            setMotorDirections(FORWARD, FORWARD, REVERSE, REVERSE);
        }
        else if (driveMode == Constants.DriveMode.FORWARD)
        {
            setMotorDirections(FORWARD, REVERSE, FORWARD, REVERSE);
        }
        else if (driveMode == Constants.DriveMode.BACKWARD)
        {
            setMotorDirections(REVERSE, FORWARD, REVERSE, FORWARD);
        }
        else if (driveMode == Constants.DriveMode.ROTATE_CW)
        {
            setMotorDirections(FORWARD, FORWARD, FORWARD, FORWARD);
        }
        else if (driveMode == Constants.DriveMode.ROTATE_CCW)
        {
            setMotorDirections(REVERSE, REVERSE, REVERSE, REVERSE);
        }
    }
    
    private static void setMotorDirections(DcMotorSimple.Direction FLDirection, DcMotorSimple.Direction FRDirection,
                                           DcMotorSimple.Direction BLDirection, DcMotorSimple.Direction BRDirection)
    {
        FL.setDirection(FLDirection);
        FR.setDirection(FRDirection);
        BL.setDirection(BLDirection);
        BR.setDirection(BRDirection);
    }
    
    public static void stopDrivingRobot(LinearOpMode opMode)
    {
        setDriveMotorsVelocity(0);
        opMode.sleep(PAUSE_BETWEEN_MOVEMENTS);
    }
    
    public static void setDriveMotorsVelocity(double velocity)
    {
        for (DcMotorEx dcMotorEx : driveMotorsArray)
        {
            setVelocity(dcMotorEx, velocity);
        }
    }
    
    public static void setVelocity(DcMotorEx motor, double velocity)
    {
        motor.setVelocity(velocity * MAX_COUNTS_PER_SECOND);
    }
    
    public static void setDriveMotorsVelocity(double[] velocityArray)
    {
        for (int i = 0; i < velocityArray.length; i++)
        {
            setVelocity(driveMotorsArray[i], velocityArray[i]);
        }
    }
    
    public static void resetDriveEncoders()
    {
        for (DcMotorEx dcMotorEx : driveMotorsArray)
        {
            dcMotorEx.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
    }
}