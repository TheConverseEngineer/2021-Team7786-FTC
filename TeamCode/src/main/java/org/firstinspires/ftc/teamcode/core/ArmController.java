package org.firstinspires.ftc.teamcode.core;

import static org.firstinspires.ftc.teamcode.core.CONSTANTS.elbowMotorName;
import static org.firstinspires.ftc.teamcode.core.CONSTANTS.shoulderK;
import static org.firstinspires.ftc.teamcode.core.CONSTANTS.shoulderMotorName;
import static org.firstinspires.ftc.teamcode.core.CONSTANTS.shoulderSensorName;

import android.util.Log;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.LogTags;
import org.firstinspires.ftc.teamcode.utils.MathFunctions;


public class ArmController implements HardwareElement{

    private DcMotorEx elbow;
    private DcMotor shoulder;
    private AnalogInput potentiometer;

    private double shoulderPos;
    private ArmPosition target;

    private boolean snapActive;

    public ArmController() {

        snapActive = false;
        Log.i(LogTags.HARDWARE_TAG, "Arm Controller created");
    }


    @Override
    public void initiate(HardwareMap hardwareMap) {
        Log.i(LogTags.HARDWARE_TAG, "Arm Controller being initiated");
        elbow = hardwareMap.get(DcMotorEx.class, elbowMotorName);
        shoulder = hardwareMap.get(DcMotor.class, shoulderMotorName);
        potentiometer = hardwareMap.get(AnalogInput.class, shoulderSensorName);
        Log.i(LogTags.HARDWARE_TAG, "Arm Controller initiated");
    }

    public void goToPos(ArmPosition position) {
        snapActive = true;
        target = position;
        Log.v(LogTags.HARDWARE_TAG, "Arm Controller pos set to " + position);
    }

    public void stopTravel() {
        snapActive = false;
        Log.v(LogTags.HARDWARE_TAG, "Arm Controller stopped");
    }

    public void update() {
        updateShoulderPos();
        if (shoulderPos >= 265) {
            shoulder.setPower(-1);
        } else if (shoulderPos <= 5) {shoulder.setPower(1);}

        if (snapActive) {
            shoulder.setPower(2 / (1 + Math.pow(Math.E, shoulderK * (target.position) - shoulderPos)) - 1);
        } else {
            shoulder.setPower(0);
        }
    }


    private void updateShoulderPos() {
        double voltage = potentiometer.getVoltage();
        if (MathFunctions.epsEquals(voltage, 0)) {
            shoulderPos = 270d;
        } else {
            double output = (-234*( Math.sqrt(Math.pow(voltage, 2)-(1.1*voltage)+0.908) - (0.577*(voltage+1.65)))) / voltage;
            shoulderPos = MathFunctions.clamp(output, 270, 0);
        }
    }

}
