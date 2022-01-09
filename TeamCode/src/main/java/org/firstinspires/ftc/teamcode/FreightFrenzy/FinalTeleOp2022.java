package org.firstinspires.ftc.teamcode.FreightFrenzy;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.Button;

import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.BRAKE;
import static com.qualcomm.robotcore.hardware.DcMotor.ZeroPowerBehavior.FLOAT;
import static com.qualcomm.robotcore.hardware.Servo.Direction.FORWARD;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.FinalTeleOp2022.BOTTOM_DEPOSIT_STATES.BOTTOM_DEPOSIT_START;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.FinalTeleOp2022.TOP_DEPOSIT_STATES.*;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.Constants.*;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.MotorFunctions.setVelocity;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.NvyusRobotHardware.*;

@TeleOp
public class FinalTeleOp2022 extends LinearOpMode {
    Button toggleSlowMode = new Button();
    Button incrementLevel = new Button();
    Button decrementLevel = new Button();
    Button depositCargo = new Button();
    BOTTOM_DEPOSIT_STATES bottomDepositState = BOTTOM_DEPOSIT_START;
    TOP_DEPOSIT_STATES topDepositState = TOP_DEPOSIT_START;
    ElapsedTime elapsedTime = new ElapsedTime();
    boolean slowMode = false;
    int levelToDeposit = 2;
    int currentArmPosition;
    int currentLinearSlidePosition;

    @Override
    public void runOpMode() {
        initializeNvyusRobotHardware(FinalTeleOp2022.this);
        telemetry.addLine("ready");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            //update encoders
            updateEncoders();

            //gamepad 1
            controlDrivetrain();
            controlCarousel();

            //gamepad 2
            controlIntake();
            controlOuttake();
//            controlOuttake();

            //telemetry
            showTelemetry();
        }
    }

    private void showTelemetry() {
        telemetry.addLine("slowMode:" + slowMode);
        telemetry.addLine("deposit level: " + levelToDeposit);
        telemetry.addLine("arm pos: " + currentArmPosition);
        telemetry.addLine("currentState: " + topDepositState);
        telemetry.update();
    }

    private void updateEncoders() {
        currentArmPosition = arm.getCurrentPosition();
        currentLinearSlidePosition = linearSlide.getCurrentPosition();
    }

    private void controlIntake() {
        if (gamepad2.right_bumper) {
            setVelocity(intake, 0.8);
        } else if (gamepad2.left_bumper) {
            setVelocity(intake, -0.8);
        } else {
            if (Math.abs(intake.getCurrentPosition()) % 145 < 42) {
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

    ///*
    private void controlOuttake() {
        //which level are we going to deposit
        if (incrementLevel.isPressed(gamepad2.dpad_up)) {
            if (levelToDeposit < 2) {
                levelToDeposit++;
            }
        } else if (decrementLevel.isPressed(gamepad2.dpad_down)) {
            if (levelToDeposit > 0) {
                levelToDeposit--;
            }
        }

        if (levelToDeposit == 2) {
            switch (topDepositState) {
                case TOP_DEPOSIT_START:
                    if (gamepad2.left_stick_y < -0.8) {
                        //raise lift
                        setVelocity(linearSlide, -0.5);
                        topDepositState = TOP_DEPOSIT_STATES.RAISE_LIFT;
                    } else if (currentLinearSlidePosition >= -120) {
                        //stop lift at the bottom
                        linearSlide.setVelocity(0);
                    }
                    break;
                case RAISE_LIFT:
                    //stop lift at the top
                    if (currentLinearSlidePosition <= -1670) {
                        setVelocity(linearSlide, 0);

                        //move to next state
                        topDepositState = SECURE_CARGO;
                    }
                    break;
                case SECURE_CARGO:
                    //secure cargo
                    dropper.setPosition(DROPPER_SECURE_POSITION);
                    sleep(0);
                    idle();

                    //move to next state
                    topDepositState = GO_BACK_DOWN_OR_DEPOSIT;
                    break;
                case GO_BACK_DOWN_OR_DEPOSIT:
                    //can choose to either go back down or deposit

                    //deposit
                    if (gamepad2.right_stick_y < -0.8) {
                        //rotate arm to position
                        setVelocity(arm, 0.4);

                        //move to next state
                        topDepositState = ROTATE_ARM_TO_POSITION;
                    }
                    //go back down
                    else if (gamepad2.left_stick_y > 0.8) {
                        //open servo before going down
                        dropper.setPosition(DROPPER_FIT_POSITION);
                        setVelocity(linearSlide, 0.5);

                        //move back to start state
                        topDepositState = TOP_DEPOSIT_START;
                    }
                    //stop arm at starting position
                    else {
                        //let arm coast near starting position
                        if (currentArmPosition <= 650) {
                            arm.setZeroPowerBehavior(FLOAT);
                            arm.setPower(0);
                        }
                        //stop the arm once it reaches starting position
                        else if (currentArmPosition <= ARM_START_POSITION) {
                            arm.setZeroPowerBehavior(BRAKE);
                            setVelocity(arm, 0);
                        }
                    }
                    break;
                case ROTATE_ARM_TO_POSITION:
                    //stop the arm once it reaches position
                    if (currentArmPosition > (COUNTS_FOR_TOP_LEVEL - 150)) {
                        arm.setZeroPowerBehavior(BRAKE);
                        setVelocity(arm, 0);

                        //move to next state
                        topDepositState = RETURN_ARM_OR_DEPOSIT;
                    }
                    //slow down the arm once it nears position
                    else if (currentArmPosition > COUNTS_FOR_TOP_LEVEL / 2.0) {
                        setVelocity(arm, 0.2);
                    }
                    break;
                case RETURN_ARM_OR_DEPOSIT:
                    //can either return arm or deposit cargo

                    //deposit cargo
                    if (depositCargo.isPressed(gamepad2.x)) {
                        dropper.setDirection(FORWARD);
                        dropper.setPosition(DROPPER_CLOSED_POSITION);
                        sleep(0);
                        idle();

                        //move to next state
                        topDepositState = DEPOSIT_CARGO;
                        elapsedTime.reset();
                    }
                    //return arm
                    else if (gamepad2.right_stick_y > 0.8) {
                        setVelocity(arm, -0.4);

                        //move back to previous decision state
                        topDepositState = GO_BACK_DOWN_OR_DEPOSIT;
                    }
                    break;
                case DEPOSIT_CARGO:
                    if (elapsedTime.milliseconds() >= 1000) {
                        dropper.setDirection(FORWARD);
                        dropper.setPosition(DROPPER_SECURE_POSITION);
                        sleep(0);
                        idle();

                        //return to previous state
                        topDepositState = RETURN_ARM_OR_DEPOSIT;
                        elapsedTime.reset();
                    }
                    break;
            }
        } else if (levelToDeposit == 1) {

        } else if (levelToDeposit == 0) {
            //            switch (bottomDepositState) {
//                case BOTTOM_DEPOSIT_START:
//                    if (gamepad2.left_stick_y < -0.9) {
//                        //reset encoders
//                        arm.setMode(STOP_AND_RESET_ENCODER);
//                        linearSlide.setMode(STOP_AND_RESET_ENCODER);
//
//                        //rotate arm to middle
//                        if (arm.getCurrentPosition() < COUNTS_FOR_BOT_MID) {
//                            setVelocity(arm, 0.25);
//                        } else {
//                            arm.setZeroPowerBehavior(BRAKE);
//                            setVelocity(arm, 0);
//                            bottomDepositState = SECURE_CARGO;
//                        }
//                    }
//                case SECURE_CARGO:
//
//            }
        }
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

    public enum TOP_DEPOSIT_STATES {
        TOP_DEPOSIT_START,
        RAISE_LIFT,
        SECURE_CARGO,
        GO_BACK_DOWN_OR_DEPOSIT,
        ROTATE_ARM_TO_POSITION,
        RETURN_ARM_OR_DEPOSIT,
        DEPOSIT_CARGO,
    }
}
