package org.firstinspires.ftc.teamcode.core;

public enum ArmPosition {
    INTAKE(240, "INTAKE"),
    LOW_GOAL(12, "LOW"),
    MID_GOAL(38, "MID"),
    HIGH_GOAL(56, "HIGH");

    public double position;
    public String name;
    ArmPosition(double pos, String name) {
        this.position = pos;
        this.name = name;
    }
}
