package org.firstinspires.ftc.teamcode.OpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.core.ChassisController;

@Autonomous(name = "Safety Auto (duck side)")
public class SafetyAuto extends LinearOpMode {

    ChassisController controller;
    DcMotor duck;
    @Override
    public void runOpMode() throws InterruptedException {

        controller = ChassisController.getInstance();
        controller.initiate(hardwareMap);
        controller.setSpeed(.25, 1, 1);

        duck = hardwareMap.get(DcMotor.class, "duckMotor");
        waitForStart();

        controller.drive(0, 1, 0, false);
        ElapsedTime timer = new ElapsedTime();
        timer.reset();

        while (timer.seconds() < 2) {}

        controller.stop();
        controller.turn1();
        while (timer.seconds() < 4) {}

        controller.stop();
        duck.setPower(-.25);
        while (timer.seconds() < 12) {}

        controller.turn2();
        while (timer.seconds() < 14) {}

        controller.drive(0, -1, 0, false);
        while (timer.seconds() < 18) {}

        controller.turn2();
        while (timer.seconds() < 18.5){}

        controller.drive(0, -1, 0, false);
        while (timer.seconds() < 22){}
        stop();


    }
}
