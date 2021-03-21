/*
 * Copyright (c)  3/21/2021. FTC Team 14214 NvyUs
 * This code is very epic
 */

package org.firstinspires.ftc.teamcode.TeleOp.Mechanisms;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.NonRunnable.Logic.Button;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.setDriveDirection;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.setDriveMotorsVelocity;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.DriveMode.FORWARD;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.DriveMode.ROTATE_CCW;

public final class Drivetrain
{
    private static final Button toggleSlowMode = new Button();
    private static final Button leftPowerShot  = new Button();
    private static final Button rightPowerShot = new Button();
    
    private static final double[] teleopVelocityArray = new double[4];
    
    private static double forwardComponent;
    private static double strafeComponent;
    private static double rotationComponent;
    
    private static boolean slowMode = false;
    
    private Drivetrain()
    {
    }
    
    public static boolean getSlowMode()
    {
        return slowMode;
    }
    
    public static void controlDrivetrain(@NotNull LinearOpMode opMode)
    {
        setDriveDirection(FORWARD);
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
        
        sendVelocities(opMode);
        
        controlPowershot(opMode);
    }
    
    private static void normalizeVelocities()
    {
        double maxSpeed = Arrays.stream(teleopVelocityArray).max().getAsDouble();
    
        for (int i = 0; i < 4; i++)
        {
            if (maxSpeed > 1)
            {
                teleopVelocityArray[i] /= maxSpeed;
            }
        
            if (slowMode)
            {
                teleopVelocityArray[i] /= Math.abs(teleopVelocityArray[i]);
                teleopVelocityArray[i] *= 0.25;
            }
        }
    }
    
    private static void sendVelocities(@NotNull LinearOpMode opMode)
    {
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
    
    private static void controlPowershot(@NotNull LinearOpMode opMode)
    {
        if (leftPowerShot.isPressed(opMode.gamepad1.dpad_left))
        {
            setDriveDirection(ROTATE_CCW);
            setDriveMotorsVelocity(0.4);
            opMode.sleep(133);
            setDriveMotorsVelocity(0);
            setDriveDirection(FORWARD);
        }
        else if (rightPowerShot.isPressed(opMode.gamepad1.dpad_right))
        {
            setDriveDirection(ROTATE_CCW);
            setDriveMotorsVelocity(-0.4);
            opMode.sleep(133);
            setDriveMotorsVelocity(0);
            setDriveDirection(FORWARD);
        }
    }
}
