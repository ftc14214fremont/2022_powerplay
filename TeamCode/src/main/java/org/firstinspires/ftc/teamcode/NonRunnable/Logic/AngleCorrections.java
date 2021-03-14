package org.firstinspires.ftc.teamcode.NonRunnable.Logic;

import java.util.Arrays;

import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.DrivePath.getCurrentVelocity;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.BACKWARD_OFFSET;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.DriveMode;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.FORWARD_OFFSET;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.KD_CORRECTION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.KI_CORRECTION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.KP_CORRECTION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.STRAFE_LEFT_OFFSET;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.STRAFE_RIGHT_OFFSET;

public final class AngleCorrections
{
    private final DriveMode driveMode;
    private       double    prevError;
    private       double    integral;
    private       double    angleError;
    
    public AngleCorrections(DriveMode driveMode)
    {
        this.angleError = 0;
        this.driveMode = driveMode;
        this.prevError = 0;
        this.integral = 0;
    }
    
    public double[] getCorrectionArray()
    {
        double[] correctionArray = new double[4];
        
        if (Math.abs(this.angleError) > 0.0625)
        {
            if (this.driveMode == DriveMode.STRAFE_LEFT)
            {
                correctionArray[0] = getVelocityOffset() - getPidCorrection();
                correctionArray[1] = getVelocityOffset() - getPidCorrection();
                correctionArray[2] = getVelocityOffset() + getPidCorrection();
                correctionArray[3] = getVelocityOffset() + getPidCorrection();
            }
            else if (this.driveMode == DriveMode.STRAFE_RIGHT)
            {
                correctionArray[0] = -getVelocityOffset() + getPidCorrection();
                correctionArray[1] = -getVelocityOffset() + getPidCorrection();
                correctionArray[2] = getVelocityOffset() - getPidCorrection();
                correctionArray[3] = getVelocityOffset() - getPidCorrection();
            }
            else if (this.driveMode == DriveMode.FORWARD)
            {
                correctionArray[0] = getVelocityOffset() + getPidCorrection();
                correctionArray[1] = getVelocityOffset() - getPidCorrection();
                correctionArray[2] = getVelocityOffset() + getPidCorrection();
                correctionArray[3] = getVelocityOffset() - getPidCorrection();
            }
            else if (this.driveMode == DriveMode.BACKWARD)
            {
                correctionArray[0] = getVelocityOffset() - getPidCorrection();
                correctionArray[1] = getVelocityOffset() + getPidCorrection();
                correctionArray[2] = getVelocityOffset() - getPidCorrection();
                correctionArray[3] = getVelocityOffset() + getPidCorrection();
            }
        }
        else
        {
            Arrays.fill(correctionArray, 0);
        }
        return correctionArray;
    }
    
    private double getVelocityOffset()
    {
        if (this.driveMode == DriveMode.STRAFE_LEFT)
        {
            return getCurrentVelocity() * STRAFE_LEFT_OFFSET;
        }
        else if (this.driveMode == DriveMode.STRAFE_RIGHT)
        {
            return getCurrentVelocity() * STRAFE_RIGHT_OFFSET;
        }
        else if (this.driveMode == DriveMode.FORWARD)
        {
            return getCurrentVelocity() * FORWARD_OFFSET;
        }
        else if (this.driveMode == DriveMode.BACKWARD)
        {
            return getCurrentVelocity() * BACKWARD_OFFSET;
        }
        else
        {
            return 0;
        }
    }
    
    private double getPidCorrection()
    {
        double derivative;
        double correction;
        
        integral += this.angleError;
        derivative = this.angleError - this.prevError;
        this.prevError = this.angleError;
        
        if (this.angleError == 0)
        {
            this.integral = 0;
        }
        if (this.integral > 5)
        {
            this.integral = 5;
        }
        correction = (KP_CORRECTION * this.angleError) + (KI_CORRECTION * this.integral) + (KD_CORRECTION * derivative);
        
        return correction;
    }
    
    public void update(double angleError)
    {
        this.angleError = angleError;
    }
}
