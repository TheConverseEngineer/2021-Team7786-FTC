package org.firstinspires.ftc.teamcode.OpModes;

import static org.firstinspires.ftc.teamcode.core.CONSTANTS.shoulderK;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.controllers.gamepad.GamepadEx;
import org.firstinspires.ftc.teamcode.core.ChassisController;
import org.firstinspires.ftc.teamcode.hardware.Potentiometer;

@TeleOp(name = "Old Tele-Op", group = "default")
public class OldTeleOp extends OpMode {

    DcMotor shoulder, elbow, duck;

    CRServo intake;
    ChassisController controller;
    double speed;
    double armSlow;
    Potentiometer sensor;

    double desiredPos;

    GamepadEx gamepad;

    @Override
    public void init() {
        controller = ChassisController.getInstance();
        controller.initiate(hardwareMap);

        controller.setSpeed(.75, .25, .25);

        shoulder = hardwareMap.get(DcMotor.class, "shoulderMotor");
        elbow = hardwareMap.get(DcMotor.class, "elbowMotor");
        duck = hardwareMap.get(DcMotor.class, "duckMotor");
        intake = hardwareMap.get(CRServo.class, "intake");

        sensor = new Potentiometer(hardwareMap, "shoulderSensor");

        desiredPos = 84;

        gamepad = new GamepadEx(gamepad2);

        gamepad.add("b toggle", gamepad.new BToggleButton() {
            @Override
            public void onToggle(boolean value) {
                desiredPos = 254;
            }
        });

        gamepad.add("a toggle", gamepad.new AToggleButton() {
            @Override
            public void onToggle(boolean value) {
                desiredPos = 56;
            }
        });
        gamepad.add("x toggle", gamepad.new XToggleButton() {
            @Override
            public void onToggle(boolean value) {
                desiredPos = 12;
            }
        });
    }



    @Override
    public void loop() {

        gamepad.update();

        sensor.update();

        telemetry.addData("Sensor pos", sensor.getAngleDegrees());
        telemetry.addData("desired pos", desiredPos);
        shoulder.setPower(-(2 / (1 + Math.exp(-0.025 * (desiredPos - sensor.getAngleDegrees()))) - 1));

        // Move
        controller.drive(-gamepad1.left_stick_x, gamepad1.left_stick_y, -gamepad1.right_stick_x, gamepad1.right_bumper);

        // Duck
        if (gamepad1.right_trigger > 0.05) { duck.setPower(gamepad1.right_trigger); }
        else if (gamepad1.left_trigger > 0.05) { duck.setPower(-gamepad1.left_trigger); }
        else { duck.setPower(0); }

        // Arm
        if (gamepad2.right_trigger > .85) {
            armSlow = .5;
        } else {
            armSlow = 1;
        }
        elbow.setPower(gamepad2.left_stick_y * armSlow);
        //shoulder.setPower(gamepad2.right_stick_y * armSlow);
        telemetry.addData("Elbow pos:", elbow.getCurrentPosition());


        if (gamepad2.right_bumper) {
            intake.setPower(1);
        } else if (gamepad2.left_bumper) {
            intake.setPower(-1);
        } else {
            intake.setPower(0);
        }
    }
}
