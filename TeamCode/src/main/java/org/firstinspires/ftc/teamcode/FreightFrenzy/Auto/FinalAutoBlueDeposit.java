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
public class FinalAutoBlueDeposit extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
        initializationStuff(this);
        waitForStart();
        phoneCam.closeCameraDevice();


        if (getShippingElementPosition() == RIGHT) {// right is left vice versa
            moveForward(10, 0.3, this);
            turnCW(-172, false, this);
            moveBackward(15, 0.3, this);
            depositCargoOnTopLevel(this);
            turnCW(-262, false, this);
            //raise dropper to make sure we are fully parked
            dropper.setPosition(DROPPER_FIT_POSITION);
            sleep(1000);
            idle();
            moveForward(40, 0.5, this);
            lowerLift(this);


        } else if (getShippingElementPosition() == CENTER) {
            moveForward(10, 0.3, this);
            turnCW(166, false, this);
            moveBackward(5, 0.3, this);
            depositCargoOnMidLevel(this);
            moveBackward(7, 0.3, this);
            turnCW(262, false, this);
            //raise dropper to make sure we are fully parked
            dropper.setPosition(DROPPER_FIT_POSITION);
            sleep(1000);
            idle();
            moveForward(60, 0.5, this);
            lowerLift(this);


        } else if (getShippingElementPosition() == LEFT) {//left is right
            moveForward(10, 0.3, this);
            turnCW(166, false, this);
            moveBackward(3, 0.3, this);
            depositCargoOnBotLevel(this);
            moveBackward(8, 0.3, this);
            turnCW(262, false, this);
            //raise dropper to make sure we are fully parked
            dropper.setPosition(DROPPER_FIT_POSITION);
            sleep(1000);
            idle();
            moveForward(60, 0.5, this);


        }
    }
}