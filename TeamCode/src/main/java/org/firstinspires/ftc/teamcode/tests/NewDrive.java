package org.firstinspires.ftc.teamcode.tests;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Disabled
@TeleOp(name = "Better Drive?2.0", group = "Great")
public class NewDrive extends OpMode {
    DcMotor lf, lr,rf, rr;

    DcMotor shoulder, elbow;

    double speed;

    @Override
    public void init() {
        lf = hardwareMap.get(DcMotor.class, "leftFrontDrive");
        rf = hardwareMap.get(DcMotor.class, "rightFrontDrive");
        lr = hardwareMap.get(DcMotor.class, "leftRearDrive");
        rr = hardwareMap.get(DcMotor.class, "rightRearDrive");

        rr.setDirection(DcMotorSimple.Direction.REVERSE);
        rf.setDirection(DcMotorSimple.Direction.REVERSE);

        shoulder = hardwareMap.get(DcMotor.class, "shoulder");
        elbow = hardwareMap.get(DcMotor.class, "elbow");
    }

    @Override
    public void loop() {

        if (gamepad1.right_bumper) {
            speed = 0.25;
        } else {
            speed = 0.75;
        }

        //Move
        double x = -gamepad1.left_stick_x;
        double y = gamepad1.left_stick_y;
        double w = -speed * gamepad1.right_stick_x;

        lf.setPower(speed*(x + y) + w);
        rf.setPower(speed*(y - x) - w);
        lr.setPower(speed*(y - x) + w);
        rr.setPower(speed*(x + y) - w);

        telemetry.addData("speed", 0.5*(x+5));

        if (gamepad1.dpad_left) { shoulder.setPower(0.5); }
        else if (gamepad1.dpad_right) { shoulder.setPower(-0.5); }
        else { shoulder.setPower(0); }

        if (gamepad1.dpad_up) { elbow.setPower(0.5); }
        else if (gamepad1.dpad_down) { elbow.setPower(-0.5); }
        else { elbow.setPower(0); }

        if (gamepad1.a) {
            lf.setPower(1);
            rf.setPower(-1);
            lr.setPower(1);
            rr.setPower(-1);
        }
    }
}
