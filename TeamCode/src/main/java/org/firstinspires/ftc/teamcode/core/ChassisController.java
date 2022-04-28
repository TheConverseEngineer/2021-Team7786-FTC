package org.firstinspires.ftc.teamcode.core;

import static org.firstinspires.ftc.teamcode.competitionOpModes.COMPETITION_CONSTANTS.DEGREES_TRAVEL_TO_TICKS;
import static org.firstinspires.ftc.teamcode.competitionOpModes.COMPETITION_CONSTANTS.INCHES_TRAVEL_TO_TICKS;
import static org.firstinspires.ftc.teamcode.core.CONSTANTS.leftFrontDriveName;
import static org.firstinspires.ftc.teamcode.core.CONSTANTS.leftRearDriveName;
import static org.firstinspires.ftc.teamcode.core.CONSTANTS.rightFrontDriveName;
import static org.firstinspires.ftc.teamcode.core.CONSTANTS.rightRearDriveName;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;


public class ChassisController implements HardwareElement{
    private static final ChassisController instance = new ChassisController();

    private double speed, slowMultiplier, slowTurnMultiplier;
    DcMotor lf, lr,rf, rr;

    private ChassisController() { }

    public void initiate(HardwareMap hardwareMap) {
        lf = hardwareMap.get(DcMotor.class, leftFrontDriveName);
        rf = hardwareMap.get(DcMotor.class, rightFrontDriveName);
        lr = hardwareMap.get(DcMotor.class, leftRearDriveName);
        rr = hardwareMap.get(DcMotor.class, rightRearDriveName);

        rr.setDirection(DcMotorSimple.Direction.REVERSE);
        rf.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setSpeed(double speed, double slowMultiplier, double slowTurnMultiplier) {
        this.speed = speed;
        this.slowMultiplier = slowMultiplier;
        this.slowTurnMultiplier = slowTurnMultiplier;
    }

    public static ChassisController getInstance() {
        return instance;
    }

    public void drive(double x, double y, double w, boolean isSlow) {
        double _speed = isSlow ? speed * slowMultiplier : speed;
        double _wSpeed = isSlow ? speed * slowTurnMultiplier : speed;
        lf.setPower(_speed*(x + y) + _wSpeed*w);
        rf.setPower(_speed*(y - x) - _wSpeed*w);
        lr.setPower(_speed*(y - x) + _wSpeed*w);
        rr.setPower(_speed*(x + y) - _wSpeed*w);
    }

    public void stop() {
        lf.setPower(0);
        rf.setPower(0);
        lr.setPower(0);
        rr.setPower(0);
    }


    /** Enter speed as inches per second and dist as inches! */
    public void autoDrive(double speed, double dist) {
        double target = Math.abs((double)lf.getCurrentPosition()) + dist * INCHES_TRAVEL_TO_TICKS;

        lf.setPower(speed);
        rf.setPower(speed);
        lr.setPower(speed);
        rr.setPower(speed);

        while (Math.abs(lf.getCurrentPosition()) < target) { }

        stop();
    }

    public void autoTurn(double speed, double angle) {
        double target = Math.abs((double)lf.getCurrentPosition()) + angle * DEGREES_TRAVEL_TO_TICKS;

        lf.setPower(speed);
        rf.setPower(-speed);
        lr.setPower(speed);
        rr.setPower(-speed);

        while (Math.abs(lf.getCurrentPosition()) < target) { }

        stop();
    }

    /** Only exist for the Hail-Mary opMode to work*/
    public void turn1() {
        lf.setPower(.25);
        rf.setPower(-.25);
        lr.setPower(.25);
        rr.setPower(0);
    }
    public void turn2() {
        lf.setPower(-.25);
        rf.setPower(.25);
        lr.setPower(-.25);
        rr.setPower(0);
    }



}
