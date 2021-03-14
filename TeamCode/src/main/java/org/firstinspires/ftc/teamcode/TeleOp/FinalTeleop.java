package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.NonRunnable.Logic.Button;
import org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants;

import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.setDriveDirection;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.setDriveMotorsVelocity;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.GeneralDriveMotorFunctions.setVelocity;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.ImuFunctions.getAngle;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.ImuFunctions.resetAngle;
import static org.firstinspires.ftc.teamcode.NonRunnable.Functions.TelemetryFunctions.showReady;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.FLAP_CLOSED_POSITION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.FLAP_OPEN_POSITION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.GUIDE_CLOSED_POSITION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.GUIDE_OPEN_POSITION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.WOBBLE_CLOSED_POSITION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.WOBBLE_OPEN_POSITION;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.highGoalSpeed;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Constants.powerShotSpeed;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.flap;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.flyWheel;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.guide;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.initHardware;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.spinner;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.tubeIntake;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.wobble;
import static org.firstinspires.ftc.teamcode.NonRunnable.NvyusRobot.Hardware.wobbleArm;
import static org.firstinspires.ftc.teamcode.TeleOp.Functions.Drive.drive;
import static org.firstinspires.ftc.teamcode.TeleOp.Functions.Drive.slowMode;

@TeleOp
public final class FinalTeleop extends LinearOpMode
{
    double currentTime;
    
    Button toggleRingFlow      = new Button();
    Button toggleWobbleServo   = new Button();
    Button zeroAngle           = new Button();
    Button powerShotModeToggle = new Button();
    Button leftPowerShot       = new Button();
    Button rightPowerShot      = new Button();
    
    boolean firstPartDone;
    boolean secondPartDone;
    boolean thirdPartDone;
    
    int     toggleRingFlowCount = 0;
    boolean currentWobblePos    = false;
    boolean powerShotMode       = false;
    
    @Override
    public void runOpMode()
    {
        initHardware(this);
        showReady(this);
        
        waitForStart();
        
        while (opModeIsActive())
        {
            telemetry.addData("slow mode", slowMode);
            telemetry.addData("power shot mode", powerShotMode);
            telemetry.addData("angle", getAngle());
            telemetry.update();
            
            drive(this);
            controlIntake();
            controlShooter();
            controlWobbleArm();
            powerShot();
        }
    }
    
    private void controlIntake()
    {
        if (gamepad2.left_bumper)
        {
            if (gamepad2.dpad_down)
            {
                tubeIntake.setPower(-1);
            }
            else
            {
                tubeIntake.setPower(1);
            }
        }
        else
        {
            tubeIntake.setPower(0);
        }
        
        if (gamepad2.dpad_up)
        {
            setVelocity(spinner, 0.3);
        }
        else if (gamepad2.dpad_down)
        {
            setVelocity(spinner, -0.4);
        }
        else if (!((gamepad2.right_trigger > 0.5) && gamepad2.left_bumper))
        {
            setVelocity(spinner, 0);
        }
    }
    
    private void controlShooter()
    {
        if (gamepad2.right_trigger > 0.5)
        {
            if (powerShotMode)
            {
                setVelocity(flyWheel, powerShotSpeed);
            }
            else
            {
                setVelocity(flyWheel, highGoalSpeed);
            }
            ++toggleRingFlowCount;
            
            if (toggleRingFlowCount == 1)
            {
                firstPartDone = false;
                secondPartDone = false;
                thirdPartDone = false;
            }
            
            if (!firstPartDone)
            {
                flap.setPosition(FLAP_CLOSED_POSITION);
                sleep(0);
                idle();
                firstPartDone = true;
                currentTime = getRuntime();
            }
            else if (!secondPartDone && (getRuntime() > currentTime + 0.3))
            {
                flap.setPosition(FLAP_OPEN_POSITION);
                sleep(0);
                idle();
                secondPartDone = true;
                currentTime = getRuntime();
            }
            else if (!thirdPartDone && (getRuntime() > currentTime + 0.3))
            {
                guide.setPosition(GUIDE_CLOSED_POSITION);
                sleep(0);
                idle();
            }
            
            if (gamepad2.left_bumper)
            {
                setVelocity(spinner, 0.6);
            }
        }
        else
        {
            setVelocity(flyWheel, 0);
            guide.setPosition(GUIDE_OPEN_POSITION);
            sleep(0);
            idle();
            toggleRingFlow.isFinished();
            toggleRingFlowCount = 0;
        }
    }
    
    public void controlWobbleArm()
    {
        if (toggleWobbleServo.isPressed(gamepad2.left_stick_button))
        {
            if (currentWobblePos)
            {
                wobble.setPosition(WOBBLE_OPEN_POSITION);
            }
            else
            {
                wobble.setPosition(WOBBLE_CLOSED_POSITION);
            }
            currentWobblePos = !currentWobblePos;
            sleep(0);
            idle();
        }
        
        if (gamepad2.right_stick_y < -0.3)
        {
            setVelocity(wobbleArm, -0.25);
        }
        else if (gamepad2.right_stick_y > 0.3)
        {
            setVelocity(wobbleArm, 0.25);
        }
        else if (gamepad2.left_stick_y < -0.3)
        {
            setVelocity(wobbleArm, -0.6);
        }
        else if (gamepad2.left_stick_y > 0.3)
        {
            setVelocity(wobbleArm, 0.6);
        }
        else
        {
            setVelocity(wobbleArm, 0);
        }
    }
    
    private void powerShot()
    {
        if (zeroAngle.isPressed(gamepad1.a))
        {
            resetAngle();
        }
        
        if (powerShotModeToggle.isPressed(gamepad2.x))
        {
            powerShotMode = !powerShotMode;
        }
        
        if (leftPowerShot.isPressed(gamepad1.dpad_left))
        {
            setDriveDirection(Constants.DriveMode.ROTATE_CCW);
            setDriveMotorsVelocity(0.4);
            sleep(133);
            setDriveMotorsVelocity(0);
        }
        else if (rightPowerShot.isPressed(gamepad1.dpad_right))
        {
            setDriveDirection(Constants.DriveMode.ROTATE_CCW);
            setDriveMotorsVelocity(-0.4);
            sleep(133);
            setDriveMotorsVelocity(0);
        }
        setDriveDirection(Constants.DriveMode.FORWARD);
    }
}