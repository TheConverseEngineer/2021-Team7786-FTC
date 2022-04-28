package org.firstinspires.ftc.teamcode.core;

public enum IntakePosition {
    CLOSED(0),
    OPEN(90);

    public double position;
    IntakePosition(int pos) { this.position = pos; }

    public IntakePosition flip() {
        return ((this == CLOSED) ? OPEN : CLOSED);
    }
}
