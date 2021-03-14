package org.firstinspires.ftc.teamcode.NonRunnable.Functions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.setVelocity;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.FLAP_CLOSED_POSITION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.FLAP_OPEN_POSITION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.GUIDE_CLOSED_POSITION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.GUIDE_OPEN_POSITION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.flap;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.flyWheel;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.guide;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.spinner;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.tubeIntake;

public final class IntakeFunctions
{
    private IntakeFunctions()
    {
    }
    
    public static void preventRingsFromPassing(LinearOpMode opMode)
    {
        guide.setPosition(GUIDE_OPEN_POSITION);
        opMode.sleep(0);
        opMode.idle();
        flap.setPosition(FLAP_OPEN_POSITION);
        opMode.sleep(0);
        opMode.idle();
    }
    
    public static void letRingsPass(LinearOpMode opMode)
    {
        flap.setPosition(FLAP_CLOSED_POSITION);
        opMode.sleep(0);
        opMode.idle();
        
        opMode.sleep(400);
        
        flap.setPosition(FLAP_OPEN_POSITION);
        opMode.sleep(0);
        opMode.idle();
        
        opMode.sleep(400);
        
        guide.setPosition(GUIDE_CLOSED_POSITION);
        opMode.sleep(0);
        opMode.idle();
    }
    
    public static void shoot(LinearOpMode opMode, long sleep)
    {
        guide.setPosition(GUIDE_CLOSED_POSITION);
        opMode.sleep(300);
        opMode.idle();
        setVelocity(spinner, 0.22);
        
        opMode.sleep(sleep);
        tubeIntake.setPower(0);
        setVelocity(spinner, 0);
        setVelocity(flyWheel, 0);
        guide.setPosition(GUIDE_OPEN_POSITION);
        opMode.sleep(0);
        opMode.idle();
    }
}
