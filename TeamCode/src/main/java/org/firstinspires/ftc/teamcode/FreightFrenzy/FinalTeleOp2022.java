package org.firstinspires.ftc.teamcode.FreightFrenzy;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.Button;

import static org.firstinspires.ftc.teamcode.FreightFrenzy.FinalTeleOp2022.BOTTOM_DEPOSIT_STATES.BOTTOM_DEPOSIT_START;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.MotorFunctions.setVelocity;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.NvyusRobotHardware.*;

@TeleOp
public class FinalTeleOp2022 extends LinearOpMode {
    Button toggleSlowMode = new Button();
    Button botLevel = new Button();
    Button midLevel = new Button();
    Button topLevel = new Button();
    BOTTOM_DEPOSIT_STATES bottomDepositState = BOTTOM_DEPOSIT_START;
    boolean slowMode = false;
    int levelToDeposit = 0;

    @Override
    public void runOpMode() {
        initializeNvyusRobotHardware(FinalTeleOp2022.this);
        telemetry.addLine("ready");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            //gamepad 1
            controlDrivetrain();
            controlCarousel();

            //gamepad 2
            controlIntake();
//            controlOuttake();

            //telemetry
            showTelemetry();
        }
    }

    private void showTelemetry() {
        telemetry.addLine("slowMode:" + slowMode);
        telemetry.addLine("deposit level: " + levelToDeposit);
        telemetry.update();
    }

    private void controlIntake() {
        if (gamepad2.right_bumper) {
            setVelocity(intake, 1);
        } else if (gamepad2.left_bumper) {
            setVelocity(intake, -1);
        } else {
            if (Math.abs(intake.getCurrentPosition()) % 145 < 21) {
                setVelocity(intake, 0);
            } else {
                setVelocity(intake, 0.1);
            }
        }
    }

    private void controlDrivetrain() {
        double leftPower;
        double rightPower;

        double turn = gamepad1.right_stick_x;
        double drive = -gamepad1.left_stick_y;
        leftPower = Range.clip(drive + turn, -1, 1);
        rightPower = Range.clip(drive - turn, -1, 1);

        if (toggleSlowMode.isPressed(gamepad1.a)) {
            slowMode = !slowMode;
        }

        if (slowMode) {
            FL.setPower(leftPower * 0.5);
            FR.setPower(rightPower * 0.5);
            BL.setPower(leftPower * 0.5);
            BR.setPower(rightPower * 0.5);
        } else {
            FL.setPower(leftPower);
            FR.setPower(rightPower);
            BL.setPower(leftPower);
            BR.setPower(rightPower);
        }
    }

     /*
    private void controlOuttake() {
        //which level are we going to deposit
        if (botLevel.isPressed(gamepad2.x)) {
            levelToDeposit = 1;
        } else if (botLevel.isPressed(gamepad2.y)) {
            levelToDeposit = 2;
        } else if (topLevel.isPressed(gamepad2.b)) {
            levelToDeposit = 3;
        }

        if (levelToDeposit == 1) {
            switch (bottomDepositState) {
                case BOTTOM_DEPOSIT_START:
                    if (gamepad2.left_stick_y < -0.9) {
                        //reset encoders
                        arm.setMode(STOP_AND_RESET_ENCODER);
                        linearSlide.setMode(STOP_AND_RESET_ENCODER);

                        //rotate arm to middle
                        if (arm.getCurrentPosition() < COUNTS_FOR_BOT_MID) {
                            setVelocity(arm, 0.25);
                        } else {
                            arm.setZeroPowerBehavior(BRAKE);
                            setVelocity(arm, 0);
                            bottomDepositState = SECURE_CARGO;
                        }
                    }
                case SECURE_CARGO:

            }
        } else if (levelToDeposit == 2) {

        } else if (levelToDeposit == 3) {

        }

        //close servo on cargo
        dropper.setPosition(DROPPER_SECURE_POSITION);
        opMode.sleep(1000);
        opMode.idle();

        //rotate arm to position
        while (arm.getCurrentPosition() < COUNTS_FOR_BOT_LEVEL && opMode.opModeIsActive()) {
            if (arm.getCurrentPosition() < COUNTS_FOR_BOT_LEVEL / 2) {
                setVelocity(arm, 0.2);
            } else {

                setVelocity(arm, 0.1);
            }
            opMode.telemetry.addLine("arm: " + arm.getCurrentPosition());
            opMode.telemetry.update();
        }
        arm.setZeroPowerBehavior(BRAKE);
        setVelocity(arm, 0);

        //fully drop cargo
        dropper.setPosition(Constants.DROPPER_CLOSED_POSITION);
        opMode.sleep(1000);
        opMode.idle();

        //move arm back to mid
        while (arm.getCurrentPosition() > COUNTS_FOR_BOT_MID && opMode.opModeIsActive()) {
            setVelocity(arm, -0.2);
            opMode.telemetry.addLine("arm: " + arm.getCurrentPosition());
            opMode.telemetry.update();
        }
        arm.setZeroPowerBehavior(BRAKE);
        setVelocity(arm, 0);

        //move dropper back to open position
        dropper.setPosition(Constants.DROPPER_OPEN_POSITION);
        opMode.sleep(1000);
        opMode.idle();

        //move arm back to start
        while (arm.getCurrentPosition() > ARM_START_POSITION && opMode.opModeIsActive()) {
            if (arm.getCurrentPosition() > 650) {
                setVelocity(arm, -0.4);
            } else {
                arm.setZeroPowerBehavior(FLOAT);
                arm.setPower(0);
            }
            opMode.telemetry.addLine("arm: " + arm.getCurrentPosition());
            opMode.telemetry.update();
        }
        arm.setZeroPowerBehavior(BRAKE);
        setVelocity(arm, 0);
    }


//*/

    private void controlCarousel() {
        if (gamepad1.left_trigger > 0.3) {
            carousel.setPower(0.9);
        } else {
            carousel.setPower(0);
        }
    }

    public enum BOTTOM_DEPOSIT_STATES {
        BOTTOM_DEPOSIT_START,
        SECURE_CARGO
    }

    public enum MID_DEPOSIT_STATES {
        SECURE_CARGO
    }
}
