package org.firstinspires.ftc.teamcode.competitionOpModes;

import com.acmerobotics.dashboard.config.Config;

@Config
public class COMPETITION_CONSTANTS {

    public static final double INCHES_TRAVEL_TO_TICKS = 384.5 / (Math.PI * 3.77952756);
    public static final double DEGREES_TRAVEL_TO_TICKS = 12d * Math.PI * INCHES_TRAVEL_TO_TICKS / 360d;

    public static double drive1dist = 8;
    public static double turn1dist = 15;
    public static double  elbowMove = 5000;
    public static double drive2dist = 25;
    public static double duckdrive1 = 25;
    public static double duckturn1 = 75;
    public static double duckdrive2 = 25;
    public static double duckturn2 = 10;
    public static double duckspintime = 8;
    public static double houseturn = 12;
    public static double housepark = 96;
}
