package org.firstinspires.ftc.teamcode.OpModes.Tuning;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.hardware.Potentiometer;

@TeleOp(name = "Arm Tuner", group = "default")
public class ArmTuner extends OpMode {
    Potentiometer test;
    DcMotor shoulder, elbow;
    double armPower;

    double targetPos;

    @Override
    public void init() {
        test = new Potentiometer(hardwareMap, "shoulderSensor");
        shoulder = hardwareMap.get(DcMotor.class, "shoulderMotor");
        elbow = hardwareMap.get(DcMotor.class, "elbowMotor");
    }

    @Override
    public void loop() {
        test.update();
        telemetry.addData("Max Voltage", test.getMaxVoltage());
        telemetry.addData("Rotation", test.getAngleDegrees());
        telemetry.addData("Target Rotation", targetPos);
        elbow.setPower(gamepad2.left_stick_y * .45);
        armPower = 2 / (1+Math.exp(-0.05*(targetPos - test.getAngleDegrees()))) - 1;
        telemetry.addData("Expected arm Power", armPower);
        if (gamepad1.right_bumper) {
            shoulder.setPower(0);
        } else {
            shoulder.setPower(armPower);
        }
        telemetry.addData("Elbow pos:", elbow.getCurrentPosition());
    }
}
