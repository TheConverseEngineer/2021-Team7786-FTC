package org.firstinspires.ftc.teamcode.vision;


public enum TSE_POSITION {

    RIGHT("Right"),
    MIDDLE("Middle"),
    LEFT("Left"),
    UNKNOWN("No idea");

    public String asString;
    TSE_POSITION(String str) {
        this.asString = str;
    }
}
