package org.firstinspires.ftc.teamcode.core;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.hardware.DcMotor;

@Config
public class CONSTANTS {
    // Some hardware maps!
    public static final String leftFrontDriveName = "leftFrontDrive";
    public static final String rightFrontDriveName = "rightFrontDrive";
    public static final String leftRearDriveName = "leftRearDrive";
    public static final String rightRearDriveName = "rightRearDrive";
    public static final String shoulderMotorName = "shoulderMotor";
    public static final String elbowMotorName = "elbowMotor";
    public static final String shoulderSensorName = "shoulderSensor";

    // tuners!
    public static double shoulderK;
}
