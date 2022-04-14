package org.firstinspires.ftc.teamcode.trajectory.Functions;

/** Link to function: https://www.desmos.com/calculator/zlyb8ugqjm
 * @author TheConverseEngineer
 */
public class AccelQuartic implements ProfileFunction {

    private final double scalarCoefficient;
    private final double velocityFirstCo;
    private final double posFirstCo;
    private final double velocitySecondCo;
    private final double posSecondCo;
    private final double velocityK;
    private final double posK;

    public AccelQuartic(double accelTime, double maxVelocity, double startVelocity, double startPos) {
        this.scalarCoefficient = maxVelocity/0.0833;
        this.velocityFirstCo = -1/(6*cube(accelTime));
        this.posFirstCo = this.velocityFirstCo / 4;
        this.velocitySecondCo = 1 / (4*square(accelTime));
        this.posSecondCo = this.velocitySecondCo / 3;
        this.velocityK = startVelocity;
        this.posK = startPos;
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
