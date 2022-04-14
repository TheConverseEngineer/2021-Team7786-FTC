package org.firstinspires.ftc.teamcode.trajectory.Functions;

public class Horizontal implements ProfileFunction {
    private final double velocity;
    private final double startPos;

    public Horizontal(double velocity, double startPos) {
        this.velocity = velocity;
        this.startPos = startPos;
    }


    @Override
    public double getPosition(double t) {
        return velocity *t + startPos;
    }

    @Override
    public double getVelocity(double t) {
        return velocity;
    }
}
