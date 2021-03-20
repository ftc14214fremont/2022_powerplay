/*
 * Copyright (c)  3/19/2021. FTC Team 14214 NvyUs
 * This code is very epic
 */

package org.firstinspires.ftc.teamcode.TeleOp.Mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.NonRunnable.Logic.Button;
import org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants;

import java.util.Arrays;

import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.setDriveDirection;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.setDriveMotorsVelocity;

public final class Drivetrain
{
    public static Button toggleSlowMode = new Button();
    public static Button leftPowerShot  = new Button();
    public static Button rightPowerShot = new Button();
    
    public static double[] teleopVelocityArray = new double[4];
    
    public static double forwardComponent;
    public static double strafeComponent;
    public static double rotationComponent;
    
    public static boolean slowMode = false;
    
    private Drivetrain()
    {
    }
    
    public static void drive(LinearOpMode opMode)
    {
        forwardComponent = -opMode.gamepad1.left_stick_y;
        strafeComponent = opMode.gamepad1.left_stick_x;
        rotationComponent = 0.75 * opMode.gamepad1.right_stick_x;
        
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
        double maxSpeed = Math.abs(Arrays.stream(teleopVelocityArray).max().getAsDouble());
        
        for (int i = 0; i < 4; i++)
        {
            if (maxSpeed > 1)
            {
                teleopVelocityArray[i] /= maxSpeed;
            }
    
            if (slowMode)
            {
                teleopVelocityArray[i] /= Math.abs(teleopVelocityArray[i]);
                teleopVelocityArray[i] *= 0.25; //0.4
            }
        }
    }
    
    public static void powerShot(LinearOpMode opMode)
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
