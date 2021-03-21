/*
 * Copyright (c)  3/20/2021. FTC Team 14214 NvyUs
 * This code is very epic
 */

package org.firstinspires.ftc.teamcode.TeleOp.Mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.NonRunnable.Logic.Button;
import org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants;
import org.jetbrains.annotations.NotNull;

import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.setDriveDirection;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.setDriveMotorsVelocity;

public final class Drivetrain
{
    private static final Button   toggleSlowMode      = new Button();
    private static final Button   leftPowerShot       = new Button();
    private static final Button   rightPowerShot      = new Button();
    private static final double[] teleopVelocityArray = new double[4];
    
    private Drivetrain()
    {
    }
    
    private static boolean slowMode = false;
    
    public static boolean getSlowMode()
    {
        return slowMode;
    }
    
    public static void drive(@NotNull LinearOpMode opMode)
    {
        double forwardComponent  = -opMode.gamepad1.left_stick_y;
        double strafeComponent   = opMode.gamepad1.left_stick_x;
        double rotationComponent = 0.75 * opMode.gamepad1.right_stick_x;
        
        teleopVelocityArray[0] = forwardComponent + strafeComponent + rotationComponent;
        teleopVelocityArray[1] = forwardComponent - strafeComponent - rotationComponent;
        teleopVelocityArray[2] = forwardComponent - strafeComponent + rotationComponent;
        teleopVelocityArray[3] = forwardComponent + strafeComponent - rotationComponent;
        
        normalizeVelocities();
        
        if (toggleSlowMode.isPressed(opMode.gamepad1.y))
        {
            slowMode = !slowMode;
        }
        
        if (forwardComponent == 0 && strafeComponent == 0 && rotationComponent == 0)
        {
            if (!(opMode.gamepad1.dpad_left || opMode.gamepad1.dpad_up || opMode.gamepad1.dpad_right))
            {
                setDriveMotorsVelocity(0);
            }
        }
        else
        {
            setDriveMotorsVelocity(teleopVelocityArray);
        }
    }
    
    private static void normalizeVelocities()
    {
        double maxSpeed = teleopVelocityArray[0];
    
        for (double velocity : teleopVelocityArray)
        {
            if (velocity > maxSpeed)
            {
                maxSpeed = velocity;
            }
        }
    
        for (int i = 0; i < 4; i++)
        {
            if (maxSpeed > 1)
            {
                teleopVelocityArray[i] /= maxSpeed;
            }
        
            if (slowMode)
            {
                teleopVelocityArray[i] = 0.25 * Math.signum(teleopVelocityArray[i]);
            }
        }
    }
    
    public static void powerShot(@NotNull LinearOpMode opMode)
    {
        if (leftPowerShot.isPressed(opMode.gamepad1.dpad_left))
        {
            setDriveDirection(Constants.DriveMode.ROTATE_CCW);
            setDriveMotorsVelocity(0.4);
            opMode.sleep(133);
            setDriveMotorsVelocity(0);
        }
        else if (rightPowerShot.isPressed(opMode.gamepad1.dpad_right))
        {
            setDriveDirection(Constants.DriveMode.ROTATE_CCW);
            setDriveMotorsVelocity(-0.4);
            opMode.sleep(133);
            setDriveMotorsVelocity(0);
        }
        setDriveDirection(Constants.DriveMode.FORWARD);
    }
}
