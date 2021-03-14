package org.firstinspires.ftc.teamcode.TeleOp.Functions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.NonRunnable.Logic.Button;

import java.util.Arrays;

import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.setDriveMotorsVelocity;

public final class Drive
{
    public static double forwardComponent;
    public static double strafeComponent;
    public static double rotationComponent;
    
    public static double[] teleopVelocityArray = new double[4];
    
    public static boolean slowMode = false;
    
    public static Button toggleSlowMode = new Button();
    
    private Drive()
    {
    }
    
    public static void drive(LinearOpMode opMode)
    {
        forwardComponent = -opMode.gamepad1.left_stick_y;
        strafeComponent = opMode.gamepad1.left_stick_x;
        rotationComponent = 0.75 * opMode.gamepad1.right_stick_x; //0.5
        
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
}
