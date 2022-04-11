package org.firstinspires.ftc.teamcode.tests;

import static org.firstinspires.ftc.teamcode.tests.ROBOT_DATA.LEFT_FRONT_DIRECTION;
import static org.firstinspires.ftc.teamcode.tests.ROBOT_DATA.LEFT_FRONT_DRIVE_ID;
import static org.firstinspires.ftc.teamcode.tests.ROBOT_DATA.LEFT_REAR_DIRECTION;
import static org.firstinspires.ftc.teamcode.tests.ROBOT_DATA.LEFT_REAR_DRIVE_ID;
import static org.firstinspires.ftc.teamcode.tests.ROBOT_DATA.RIGHT_FRONT_DIRECTION;
import static org.firstinspires.ftc.teamcode.tests.ROBOT_DATA.RIGHT_FRONT_DRIVE_ID;
import static org.firstinspires.ftc.teamcode.tests.ROBOT_DATA.RIGHT_REAR_DIRECTION;
import static org.firstinspires.ftc.teamcode.tests.ROBOT_DATA.RIGHT_REAR_DRIVE_ID;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.drive.RamseteFollower;
import org.firstinspires.ftc.teamcode.hardware.DriveMotor;
import org.firstinspires.ftc.teamcode.trajectory.Trajectory;
import org.firstinspires.ftc.teamcode.trajectory.markers.Marker;
import org.firstinspires.ftc.teamcode.trajectory.markers.TemporalMarker;
import org.firstinspires.ftc.teamcode.utils.Pose2D;

@Disabled
@Autonomous(name = "Trajectory Test 1", group = "Tests")
public class TrajectoryTest1 extends LinearOpMode {
    private Trajectory trajectory;
    private Marker marker;
    private RamseteFollower drive;

    DriveMotor leftFrontDrive, rightFrontDrive, leftRearDrive, rightRearDrive;

    private double foo = 0;

    @Override
    public void runOpMode() throws InterruptedException {
        marker = new TemporalMarker(2) {
            @Override
            public void run() {
                foo = 5;
            }
        };
        trajectory = new Trajectory.Builder(0, 0, 0)
                .splineTo(10, 30, 45, 90, 90)
                .addMarker(marker)
                .build();

        leftFrontDrive = new DriveMotor(hardwareMap, LEFT_FRONT_DRIVE_ID, LEFT_FRONT_DIRECTION);
        rightFrontDrive = new DriveMotor(hardwareMap, RIGHT_FRONT_DRIVE_ID, RIGHT_FRONT_DIRECTION);
        leftRearDrive = new DriveMotor(hardwareMap, LEFT_REAR_DRIVE_ID, LEFT_REAR_DIRECTION);
        rightRearDrive = new DriveMotor(hardwareMap, RIGHT_REAR_DRIVE_ID, RIGHT_REAR_DIRECTION);

        drive = new RamseteFollower(hardwareMap, new Pose2D(0, 0, 0), leftFrontDrive, rightFrontDrive, leftRearDrive, rightRearDrive);

        waitForStart();

        drive.start();
        drive.executeTrajectory(trajectory, telemetry);
    }
}
