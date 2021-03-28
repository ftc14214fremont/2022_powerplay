/*
 * Copyright (c)  3/28/2021. FTC Team 14214 NvyUs
 * This code is very epic
 */

package org.firstinspires.ftc.teamcode.NonRunnable.Functions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants;

import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.setDriveDirection;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.setDriveMotorsVelocity;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.imu;

public final class ImuFunctions
{
    /*
     * These methods work by finding the delta between the last angle(which starts at zero) and current
     * measured angle and adding it to the global angle which starts at zero. This makes it possible to
     * reset the angle because we're not returning the actual IMU reading, which is impossible to
     * reset, but the delta. The resetAngle() method works by setting the globalAngle to 0 and the
     * lastAngle equal to the current angle, making the delta angle 0. Then, the next time we call getAngle(), the
     * deltaAngle will be added to the global angle, and since both are zero, it will return 0, which
     * effectively resets the angle.
     */
    
    private static Orientation lastAngles = new Orientation();
    
    private static double globalAngle;
    private static double angleError;
    
    private static int direction;
    
    private ImuFunctions()
    {
    }
    
    public static void resetAngle()
    {
        lastAngles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        globalAngle = 0;
    }
    
    public static void correctToHeading(double heading, LinearOpMode opMode)
    {
        setDriveDirection(Constants.DriveMode.ROTATE_CCW);
        updateAngleError(heading);
    
        while (Math.abs(angleError) > 0.25 && opMode.opModeIsActive())
        {
            setDriveMotorsVelocity(0.2 * direction);
            updateAngleError(heading);
        }
        setDriveMotorsVelocity(0);
    }
    
    public static void updateAngleError(double heading)
    {
        angleError = heading - getAngle();
        direction = (int) (angleError / Math.abs(angleError));
    }
    
    //Changes angle from 0 to ±180 to 0 to ±360
    public static double getAngle()
    {
        Orientation angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES);
        
        double deltaAngle = angles.firstAngle - lastAngles.firstAngle;
        
        if (deltaAngle < -180)
        {
            deltaAngle += 360;
        }
        else if (deltaAngle > 180)
        {
            deltaAngle -= 360;
        }
        globalAngle += deltaAngle;
        lastAngles = angles;
        
        return globalAngle;
    }
    
    public static void turn(double heading, LinearOpMode opMode)
    {
        setDriveDirection(Constants.DriveMode.ROTATE_CCW);
        updateAngleError(heading);
    
        while (Math.abs(angleError) > 1 && opMode.opModeIsActive())
        {
            setDriveMotorsVelocity(0.35 * direction);
            updateAngleError(heading);
        }
        setDriveMotorsVelocity(0);
    }
}
