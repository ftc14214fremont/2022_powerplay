package org.firstinspires.ftc.teamcode.FreightFrenzy;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.HardwareConfigs.TankDriveHardware.*;

@Autonomous
public class TankDriveTest extends LinearOpMode {
    @Override
    public void runOpMode() {
        initializeTankDriveHardware(TankDriveTest.this);

        telemetry.addLine("Ready for start");
        telemetry.update();
        waitForStart();

        //intended to move forward 48 inches

        double COUNTS_PER_INCH_TANK_DRIVE = 38.8588684844;

        //set motor directions to move forwards
        FL.setDirection(REVERSE);
        FR.setDirection(FORWARD);
        BL.setDirection(REVERSE);
        BR.setDirection(FORWARD);

        //reset encoders
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        //move a desired distance in inches
        double distance = 48;
        while (FL.getCurrentPosition() < distance * COUNTS_PER_INCH_TANK_DRIVE && opModeIsActive()) {
            FL.setVelocity(2800 * 0.2);
            FR.setVelocity(2800 * 0.2);
            BL.setVelocity(2800 * 0.2);
            BR.setVelocity(2800 * 0.2);
        }
        FL.setVelocity(0);
        FR.setVelocity(0);
        BL.setVelocity(0);
        BR.setVelocity(0);
    }
}
