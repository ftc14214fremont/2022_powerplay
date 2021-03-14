package org.firstinspires.ftc.teamcode.NonRunnable.Functions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.NonRunnable.Logic.AngleCorrections;

import java.util.Arrays;

import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.resetDriveEncoders;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.setDriveDirection;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.setDriveMotorsVelocity;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.stopDrivingRobot;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.ImuFunctions.correctToHeading;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.ImuFunctions.getAngle;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.ImuFunctions.resetAngle;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.COUNTS_PER_INCH;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.DriveMode;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.FINAL_SLOW;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.FINAL_SLOW_VELOCITY;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.INITIAL_SLOW;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.INITIAL_SLOW_VELOCITY;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.BL;

public class DrivePath
{
    private static double       currentVelocity;
    private final  ElapsedTime  runTime = new ElapsedTime();
    private final  double       velocity;
    private final  double       targetDistance;
    private final  DriveMode    driveMode;
    private final  LinearOpMode opMode;
    private        double       distanceTraveled;
    private        double       inchesError;
    
    public DrivePath(double velocity, double targetDistance, DriveMode driveMode, LinearOpMode opMode)
    {
        this.velocity = velocity;
        this.targetDistance = targetDistance;
        this.driveMode = driveMode;
        this.opMode = opMode;
        this.inchesError = targetDistance;
        this.distanceTraveled = 0;
    }
    
    public static double getCurrentVelocity()
    {
        return currentVelocity;
    }
    
    public void go()
    {
        prepareForStart();
        
        double[]         finalDriveVelocities;
        AngleCorrections correctionArray = new AngleCorrections(this.driveMode);
        
        while ((this.inchesError > 0.25) && this.opMode.opModeIsActive())
        {
            correctionArray.update(getAngle());
            finalDriveVelocities = getCorrectedVelocities(getInitialDriveVelocities(),
                                                          correctionArray.getCorrectionArray());
            
            setDriveMotorsVelocity(finalDriveVelocities);
            updateValues();
        }
        stopDrivingRobot(this.opMode);
        correctToHeading(0, opMode);
    }
    
    private void prepareForStart()
    {
        setDriveDirection(this.driveMode);
        resetAngle();
        resetDriveEncoders();
        runTime.reset();
    }
    
    private double[] getCorrectedVelocities(double[] velocityArray, double[] correctionArray)
    {
        for (int i = 0; i < 4; i++)
        {
            velocityArray[i] += correctionArray[i];
        }
        return velocityArray;
    }
    
    @org.jetbrains.annotations.NotNull
    private double[] getInitialDriveVelocities()
    {
        double[] velocityArray = new double[4];
        
        if (this.distanceTraveled < INITIAL_SLOW)
        {
            Arrays.fill(velocityArray, INITIAL_SLOW_VELOCITY);
            currentVelocity = INITIAL_SLOW_VELOCITY;
        }
        else if (this.distanceTraveled > this.targetDistance - FINAL_SLOW)
        {
            Arrays.fill(velocityArray, FINAL_SLOW_VELOCITY);
            currentVelocity = FINAL_SLOW_VELOCITY;
        }
        else
        {
            Arrays.fill(velocityArray, this.velocity);
            currentVelocity = this.velocity;
        }
        return velocityArray;
    }
    
    private void updateValues()
    {
        this.distanceTraveled = BL.getCurrentPosition() / COUNTS_PER_INCH;
        this.inchesError = this.targetDistance - this.distanceTraveled;
    }
}
