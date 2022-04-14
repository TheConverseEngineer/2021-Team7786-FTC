package org.firstinspires.ftc.teamcode.drive;

import com.qualcomm.robotcore.hardware.DcMotor;

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
        this.leftFrontDrive = leftFrontDrive;
        this.rightFrontDrive = rightFrontDrive;
        this.leftRearDrive = leftRearDrive;
        this.rightRearDrive = rightRearDrive;

        this.leftFrontDrive.getMotor().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.rightFrontDrive.getMotor().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.leftFrontDrive.getMotor().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.rightRearDrive.getMotor().setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void start() {
        this.leftFrontDrive.getMotor().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.rightFrontDrive.getMotor().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.leftRearDrive.getMotor().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        this.rightRearDrive.getMotor().setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void runCommand(ChassisCommand cmd) {
        leftFrontDrive.setVelocity(cmd.getLF());
        rightFrontDrive.setVelocity(cmd.getRF());
        leftRearDrive.setVelocity(cmd.getLR());
        rightRearDrive.setVelocity(cmd.getRR());
    }
}
