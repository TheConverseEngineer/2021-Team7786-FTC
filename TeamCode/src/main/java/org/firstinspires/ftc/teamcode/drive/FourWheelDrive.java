package org.firstinspires.ftc.teamcode.drive;

import android.util.Log;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.LogTags;
import org.firstinspires.ftc.teamcode.hardware.DriveMotor;
import org.firstinspires.ftc.teamcode.kinematics.ChassisCommand;


public class FourWheelDrive {

    protected DriveMotor leftFrontDrive, rightFrontDrive, leftRearDrive, rightRearDrive;

    /** Constructor for class FourWheelDrive
     * @param leftFrontDrive        left front motor
     * @param rightFrontDrive       right front motor
     * @param leftRearDrive         left rear motor
     * @param rightRearDrive        right rear motor
     */
    public FourWheelDrive(DriveMotor leftFrontDrive, DriveMotor rightFrontDrive, DriveMotor leftRearDrive, DriveMotor rightRearDrive) {
        Log.i(LogTags.DRIVE_TAG, "Four Wheel Drive being instantiated");
        this.leftFrontDrive = leftFrontDrive;
        this.rightFrontDrive = rightFrontDrive;
        this.leftRearDrive = leftRearDrive;
        this.rightRearDrive = rightRearDrive;

        this.leftFrontDrive.getMotor().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.rightFrontDrive.getMotor().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.leftFrontDrive.getMotor().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.rightRearDrive.getMotor().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        Log.i(LogTags.DRIVE_TAG, "Four Wheel Drive instantiated");
    }

    public void start() {
        Log.v(LogTags.DRIVE_TAG, "Four Wheel Drive being started");
        this.leftFrontDrive.getMotor().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.rightFrontDrive.getMotor().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.leftRearDrive.getMotor().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.rightRearDrive.getMotor().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void runCommand(ChassisCommand cmd) {
        Log.i(LogTags.DRIVE_TAG, "Four Wheel Drive command running");
        leftFrontDrive.setVelocity(cmd.getLF());
        rightFrontDrive.setVelocity(cmd.getRF());
        leftRearDrive.setVelocity(cmd.getLR());
        rightRearDrive.setVelocity(cmd.getRR());
    }
}
