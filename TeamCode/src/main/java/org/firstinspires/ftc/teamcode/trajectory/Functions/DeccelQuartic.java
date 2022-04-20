package org.firstinspires.ftc.teamcode.trajectory.Functions;


public class DeccelQuartic implements ProfileFunction{

    private final double scalarCoefficient;
    private final double velocityFirstCo;
    private final double posFirstCo;
    private final double velocitySecondCo;
    private final double posSecondCo;
    private final double velocityK;
    private final double posK;

    public DeccelQuartic(double accelTime, double maxVelocity, double endVelocity, double startPos) {
        this.scalarCoefficient = maxVelocity/0.0833;
        this.velocityFirstCo = 1/(6*cube(accelTime));
        this.posFirstCo = this.velocityFirstCo / 4;
        this.velocitySecondCo = -1 / (4*square(accelTime));
        this.posSecondCo = this.velocitySecondCo / 3;
        this.velocityK = endVelocity + maxVelocity;
        this.posK = startPos - 0.499799919968*maxVelocity*accelTime;
    }

    @Override
    public double getVelocity(double t) {
        return scalarCoefficient *((velocityFirstCo * cube(t)) + (velocitySecondCo * square(t))) + velocityK;
    }

    @Override
    public double getPosition(double t) {
        return scalarCoefficient *((posFirstCo * fourth(t)) + (posSecondCo * cube(t))) + velocityK *t + posK;
    }

    private double fourth(double x) { return Math.pow(x, 4); }
    private double cube(double x) { return x * x * x; }
    private double square(double x) { return x * x; }
}
