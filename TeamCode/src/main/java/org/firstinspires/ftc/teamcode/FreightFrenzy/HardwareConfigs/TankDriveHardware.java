package org.firstinspires.ftc.teamcode.FreightFrenzy.HardwareConfigs;

import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import org.jetbrains.annotations.NotNull;

import java.util.List;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.FORWARD;
import static com.qualcomm.robotcore.hardware.DcMotorSimple.Direction.REVERSE;

public class TankDriveHardware {
    public static DcMotorEx FL;
    public static DcMotorEx FR;
    public static DcMotorEx BL;
    public static DcMotorEx BR;

    public static void initializeTankDriveHardware(@NotNull LinearOpMode opMode) {
        //altering some settings on rev hub, supposedly makes encoders update quicker
        List<LynxModule> allHubs = opMode.hardwareMap.getAll(LynxModule.class);

        for (LynxModule module : allHubs) {
            module.setBulkCachingMode(LynxModule.BulkCachingMode.AUTO);
        }

        //initialize motor hardware map
        FL = opMode.hardwareMap.get(DcMotorEx.class, "FL");
        FR = opMode.hardwareMap.get(DcMotorEx.class, "FR");
        BL = opMode.hardwareMap.get(DcMotorEx.class, "BL");
        BR = opMode.hardwareMap.get(DcMotorEx.class, "BR");

        //set zero power behavior
        FL.setZeroPowerBehavior(BRAKE);
        FR.setZeroPowerBehavior(BRAKE);
        BL.setZeroPowerBehavior(BRAKE);
        BR.setZeroPowerBehavior(BRAKE);

        //set motors to move forward
        FL.setDirection(REVERSE);
        FR.setDirection(FORWARD);
        BL.setDirection(REVERSE);
        BR.setDirection(FORWARD);
    }
}
