package org.firstinspires.ftc.teamcode.FreightFrenzy.Auto;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import static org.firstinspires.ftc.teamcode.FreightFrenzy.Auto.ShippingElementDetectionFinal.SkystoneDeterminationPipeline.SkystonePosition.*;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Auto.ShippingElementDetectionFinal.SkystoneDeterminationPipeline.getShippingElementPosition;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.Constants.DROPPER_FIT_POSITION;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.MovementFunctions.moveBackward;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.MovementFunctions.moveForward;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.NvyusRobotHardware.dropper;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.NvyusRobotHardware.phoneCam;
import static org.firstinspires.ftc.teamcode.FreightFrenzy.Helpers.OuttakeFunctions.*;

//plan
    /* rotate carousel to deliver duck - 10 pts
    deliver shipping element to correct height - 6 pts + 20 pt bonus if we use a custom marker
    park completely in alliance warehouse - 10 pts


    //teleop:
    6 pts per thing placed on top, 4 for mid, 2 for bottom
    4 pts for freight on your side of shared shipping hub

    endgame
    6 pts per duck delivered
    10 pts - your shipping hub balanced
    20 pts - shared hub tilts on your side
    6 pts - completely in warehouse
    15 pts - "capping"
    */
@Autonomous
public class FinalAutoBlueCarousel extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        initializationStuff(this);
        waitForStart();
        phoneCam.closeCameraDevice();

        spinCarousel();
        moveForward(3, 0.3, this);
        turnCCWBR(96, this);
        stopSpinningCarousel(3500, this);
        //turnAfterTurningCarousel(123);

        if (getShippingElementPosition() == RIGHT) {
            moveBackward(32, 0.3, this);
            depositCargoOnTopLevel(this);
            moveForward(18, 0.3, this);
            //turnCW(94);
            moveBackward(85, 0.5, this);
            lowerLift(this);

            //raise dropper to make sure we are fully parked
            dropper.setPosition(DROPPER_FIT_POSITION);
            sleep(1000);
            idle();
        } else if (getShippingElementPosition() == CENTER) {
            moveBackward(21, 0.3, this);
            depositCargoOnMidLevel(this);
            moveForward(7, 0.3, this);
            //turnCW(94);
            moveBackward(85, 0.5, this);
            lowerLift(this);

            //raise dropper to make sure we are fully parked
            dropper.setPosition(DROPPER_FIT_POSITION);
            sleep(1000);
            idle();
        } else if (getShippingElementPosition() == LEFT) {
            moveBackward(20, 0.3, this);
            depositCargoOnBotLevel(this);
            moveForward(6, 0.3, this);
            //turnCW(94);
            raiseLiftPartially(this);
            moveBackward(85, 0.5, this);
            lowerLift(this);

            //raise dropper to make sure we are fully parked
            dropper.setPosition(DROPPER_FIT_POSITION);
            sleep(1000);
            idle();
        }
    }
}

