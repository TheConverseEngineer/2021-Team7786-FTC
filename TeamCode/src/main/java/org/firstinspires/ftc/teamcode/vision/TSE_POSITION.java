package org.firstinspires.ftc.teamcode.vision;

//TODO: I don't know enough about whats happening here to fix this but that left should be capitalized
public enum TSE_POSITION {
    RIGHT("Right"),
    MIDDLE("Middle"),
    LEFT("left"),
    UNKNOWN("No idea");

    public String asString;
    TSE_POSITION(String str) {
        this.asString = str;
    }
}
