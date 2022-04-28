package org.firstinspires.ftc.teamcode.competitionOpModes;

import static org.firstinspires.ftc.teamcode.competitionOpModes.COMPETITION_CONSTANTS.drive1dist;
import static org.firstinspires.ftc.teamcode.competitionOpModes.COMPETITION_CONSTANTS.drive2dist;
import static org.firstinspires.ftc.teamcode.competitionOpModes.COMPETITION_CONSTANTS.duckdrive1;
import static org.firstinspires.ftc.teamcode.competitionOpModes.COMPETITION_CONSTANTS.duckdrive2;
import static org.firstinspires.ftc.teamcode.competitionOpModes.COMPETITION_CONSTANTS.duckspintime;
import static org.firstinspires.ftc.teamcode.competitionOpModes.COMPETITION_CONSTANTS.duckturn1;
import static org.firstinspires.ftc.teamcode.competitionOpModes.COMPETITION_CONSTANTS.duckturn2;
import static org.firstinspires.ftc.teamcode.competitionOpModes.COMPETITION_CONSTANTS.elbowMove;
import static org.firstinspires.ftc.teamcode.competitionOpModes.COMPETITION_CONSTANTS.housepark;
import static org.firstinspires.ftc.teamcode.competitionOpModes.COMPETITION_CONSTANTS.houseturn;
import static org.firstinspires.ftc.teamcode.competitionOpModes.COMPETITION_CONSTANTS.turn1dist;

import android.util.Log;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.core.ArmPosition;
import org.firstinspires.ftc.teamcode.core.ChassisController;
import org.firstinspires.ftc.teamcode.hardware.Potentiometer;
import org.firstinspires.ftc.teamcode.pipelines.ModernTSEDetectorPipeline;
import org.firstinspires.ftc.teamcode.vision.TSE_POSITION;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;

@Autonomous(name = "Standard Auto v2", group = "default")
public class StandardAuto extends LinearOpMode {

    ChassisController controller;

    DcMotor duck, shoulderMotor, elbowMotor;
    Potentiometer shoulderSensor;

    OpenCvCamera phoneCam;
    ModernTSEDetectorPipeline modernPipeline;

    @Override
    public void runOpMode() throws InterruptedException {
        controller = ChassisController.getInstance();
        controller.initiate(hardwareMap);
        controller.setSpeed(.25, 1, 1);

        duck = hardwareMap.get(DcMotor.class, "duckMotor");
        shoulderMotor = hardwareMap.get(DcMotor.class, "shoulderMotor");
        elbowMotor = hardwareMap.get(DcMotor.class, "elbowMotor");

        shoulderSensor = new Potentiometer(hardwareMap, "shoulderSensor");

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        phoneCam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);

        modernPipeline = new ModernTSEDetectorPipeline(telemetry);
        phoneCam.setPipeline(modernPipeline);

        phoneCam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override public void onOpened() { phoneCam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT); }
            @Override public void onError(int errorCode) { }
        });

        telemetry.addData("waiting for", "start");
        telemetry.update();

        waitForStart();

        ArmPosition targetArmPos = ArmPosition.LOW_GOAL;

        setState("driveForward");
        controller.autoDrive(-.16, drive1dist);

        setState("Scan 1");
        if (modernPipeline.isDetected()) targetArmPos = ArmPosition.MID_GOAL;

        setState("rotate");
        controller.autoTurn(-0.16, turn1dist);

        setState("Scan 2");
        if (modernPipeline.isDetected()) targetArmPos = ArmPosition.HIGH_GOAL;

        telemetry.addData("target level", targetArmPos.name);
        telemetry.addData("stage", "depositing element");
        telemetry.update();
        elbowMotor.setPower(-.5d);
        ElapsedTime timerB = new ElapsedTime();
        timerB.reset();
        while (timerB.seconds() < 1.5) { }
        elbowMotor.setPower(0);

        shoulderSensor.update();
        while (Math.abs(shoulderSensor.getAngleDegrees() - targetArmPos.position) > 5) {
            shoulderMotor.setPower(-(0.5 / (1 + Math.exp(-0.05 * (targetArmPos.position - shoulderSensor.getAngleDegrees()))) - 0.25));
            shoulderSensor.update();
        }
        shoulderMotor.setPower(0);

        controller.autoDrive(-.16, drive2dist);
        // ADD INTAKE RELEASE CODE

        setState("Moving to duck spinner");
        controller.autoDrive(-0.16, duckdrive1);
        controller.autoTurn(0.16, duckturn1);
        controller.autoDrive(-0.16, duckdrive2);
        controller.autoTurn(-0.16, duckturn2);

        setState("Spinning duck");
        duck.setPower(0.25);
        ElapsedTime timer = new ElapsedTime();
        timer.reset();
        while(timer.seconds() < duckspintime) { }
        duck.setPower(0);

        setState("Moving to warehouse");
        controller.autoTurn(-0.16, houseturn);
        controller.autoDrive(0.16, housepark);

        setState("done!");
    }

    void setState(String stage) {
        telemetry.addData("stage", stage);
        telemetry.update();
    }
}
