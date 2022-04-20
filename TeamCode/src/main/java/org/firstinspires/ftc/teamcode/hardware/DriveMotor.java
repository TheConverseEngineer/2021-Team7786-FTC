package org.firstinspires.ftc.teamcode.hardware;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import static org.firstinspires.ftc.teamcode.LogTags.*;
import static org.firstinspires.ftc.teamcode.tests.ROBOT_DATA.*;

import android.util.Log;


public class DriveMotor {

    private DcMotorEx motor;

    // Stored as encoder ticks
    private int lastPos;
    private String id;

    public DriveMotor(HardwareMap hwMap, String id, DcMotor.Direction direction) {
        Log.i(HARDWARE_TAG, "DriveMotor with id " + id + " being instantiated");
        this.id = id;
        this.motor = hwMap.get(DcMotorEx.class, id);
        this.motor.setDirection(direction);
        this.motor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        this.motor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        this.motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        lastPos = 0;
        Log.i(HARDWARE_TAG, "DriveMotor with id " + id + " instantiated");
    }

    public DcMotorEx getMotor() {
        return motor;
    }

    public void setPower(double power) {
        this.motor.setPower(power);
        Log.v(HARDWARE_TAG, "DriveMotor " + this.id + " power set to " + power);
    }

    /** Returns the change in position since last call
     * @return  the change in ROTATIONS (not ticks)
     */
    public double getDPos() {
        int newPos = this.motor.getCurrentPosition();
        int ret = newPos - lastPos;
        lastPos = newPos;
        return ((double) ret) / TICKS_PER_REV;
    }

    /** Sets the velocity of the motor
     * @param velocity  the velocity in ROTATIONS/SECOND
     */
    public void setVelocity(double velocity) {
        this.motor.setVelocity(velocity * TICKS_PER_REV);
        Log.v(HARDWARE_TAG, "DriveMotor " + this.id + " velocity set to " + velocity);
    }
}
