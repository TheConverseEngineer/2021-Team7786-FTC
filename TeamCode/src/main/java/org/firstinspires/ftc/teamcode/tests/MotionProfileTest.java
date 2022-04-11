package org.firstinspires.ftc.teamcode.tests;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.core.ChassisController;
import org.firstinspires.ftc.teamcode.trajectory.MotionProfile;
import org.firstinspires.ftc.teamcode.trajectory.MovementMotionProfileFactory;

@Disabled
@TeleOp(name = "Motion Profile Test", group = "Test")
public class MotionProfileTest extends OpMode {
    MovementMotionProfileFactory profileFactory;
    MotionProfile profile;
    ElapsedTime timer;

    FtcDashboard dashboard;

    ChassisController controller;
    double speed;

    @Override
    public void init() {
        controller = ChassisController.getInstance();
        controller.initiate(hardwareMap);

        controller.setSpeed(.75, .25, .25);
        profileFactory = MovementMotionProfileFactory.getInstance();
        profile = profileFactory.generateStandardProfile(2);
        dashboard = FtcDashboard.getInstance();
        telemetry = dashboard.getTelemetry();
    }

    @Override
    public void start() {
        timer = new ElapsedTime();
    }

    @Override
    public void loop() {
        telemetry.addData("time", timer.seconds());
        telemetry.addData("Speed", profile.getVelocity(timer.seconds()));
        telemetry.addData("Position", profile.getPosition(timer.seconds()));
        controller.setSpeed(profile.getVelocity(timer.seconds()), 0.5, 0.5);
        controller.drive(0, 1, 0, false);
    }
}
