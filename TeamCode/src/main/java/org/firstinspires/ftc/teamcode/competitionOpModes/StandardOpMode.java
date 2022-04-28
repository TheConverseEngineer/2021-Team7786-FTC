package org.firstinspires.ftc.teamcode.competitionOpModes;

import static java.lang.Math.min;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.controllers.gamepad.GamepadEx;
import org.firstinspires.ftc.teamcode.core.ArmPosition;
import org.firstinspires.ftc.teamcode.core.ChassisController;
import org.firstinspires.ftc.teamcode.core.IntakePosition;
import org.firstinspires.ftc.teamcode.hardware.Potentiometer;

@TeleOp(name = "Standard Tele-Op", group = "competition")
public class StandardOpMode extends OpMode {

    ChassisController controller;
    double slowCounter;
    double minCounter;

    ArmPosition armTargetPosition;
    Potentiometer shoulderSensor;

    GamepadEx gp1, gp2;

    DcMotor duckMotor, shoulderMotor, elbowMotor;
    Servo intakeServo;
    IntakePosition intakePosition;
    ElapsedTime matchTimer;

    boolean rumbledYet = false;
    boolean armRecoveryMode = false;

    /***************** INITIATION METHOD *****************/
    @Override
    public void init() {
        //***************** CHASSIS CONTROLLER *****************//
        controller = ChassisController.getInstance();
        controller.initiate(hardwareMap);

        controller.setSpeed(.75, .25, .25);

        minCounter = 2;
        slowCounter = 0;

        //***************** ARM CONTROLLER *****************//
        armTargetPosition = ArmPosition.INTAKE;
        shoulderSensor = new Potentiometer(hardwareMap, "shoulderSensor");
        shoulderMotor = hardwareMap.get(DcMotor.class, "shoulderMotor");
        elbowMotor = hardwareMap.get(DcMotor.class, "elbowMotor");

        //***************** DUCK AND INTAKE *****************//
        duckMotor = hardwareMap.get(DcMotor.class, "duckMotor");
        intakeServo = hardwareMap.get(Servo.class, "intake");
        intakePosition = IntakePosition.OPEN;
        matchTimer = new ElapsedTime();

        //***************** GAMEPAD CONTROLS *****************//
        gp1 = new GamepadEx(gamepad1);
        gp1.add("INTAKE_TOGGLE(RT)", gp1.new RightTriggerToggleButton() {
            @Override
            public void onToggle(boolean value) {
                intakePosition = intakePosition.flip();
            }
        });

        gp2 = new GamepadEx(gamepad2);
        gp2.add("ARM_TO_INTAKE(B)", gp2.new BToggleButton() {
            @Override
            public void onToggle(boolean value) {
                armTargetPosition = ArmPosition.INTAKE;
            }
        });
        gp2.add("ARM_TO_HIGH(Y)", gp2.new YToggleButton() {
            @Override
            public void onToggle(boolean value) {
                armTargetPosition = ArmPosition.HIGH_GOAL;
            }
        });
        gp2.add("ARM_TO_MID(X)", gp2.new XToggleButton() {
            @Override
            public void onToggle(boolean value) {
                armTargetPosition = ArmPosition.MID_GOAL;
            }
        });
        gp2.add("ARM_TO_LOW(A)", gp2.new AToggleButton() {
            @Override
            public void onToggle(boolean value) {
                armTargetPosition = ArmPosition.LOW_GOAL;
            }
        });
        gp2.add("ARM_RECOVERY_START", gp1.new RightTriggerStandardButton() {
            @Override
            public void run(boolean value) {
                armRecoveryMode = true;
                gamepad2.runRumbleEffect(new Gamepad.RumbleEffect.Builder().
                        addStep(1.0, 1.0, 100).
                        addStep(0.5, 0.5, 10).
                        addStep(1.0, 1.0, 100).build());
            }
        });
        gp2.add("ARM_RECOVERY_END", gp2.new LeftBumperStandardButton() {
            @Override
            public void run(boolean value) {
                armRecoveryMode = false;
            }
        });
    }

    @Override
    public void start() {
        matchTimer.reset();
    }

    /***************** LOOP METHOD *****************/
    @Override
    public void loop() {
        //***************** GAMEPAD UPDATE *****************//
        gp2.update();
        gp1.update();

        //***************** MOVEMENT *****************//
        if(gamepad1.right_bumper) {
            slowCounter = 0;
        } else {
            slowCounter++;
        }

        controller.drive(-gamepad1.left_stick_x, gamepad1.left_stick_y, -gamepad1.right_stick_x, slowCounter <= minCounter);
        telemetry.addData("IsSlow:", slowCounter <= minCounter);

        //***************** ARM CONTROL *****************//
        shoulderSensor.update();
        if (shoulderSensor.getAngleDegrees() < 10 || shoulderSensor.getAngleDegrees() > 270) {
            armRecoveryMode = true;
        }
        if (!armRecoveryMode) {
            shoulderMotor.setPower(-(2 / (1 + Math.exp(-0.025 * (armTargetPosition.position - shoulderSensor.getAngleDegrees()))) - 1));
        } else {
            shoulderMotor.setPower(gamepad2.right_stick_y * .25);
        }

        elbowMotor.setPower(gamepad2.left_stick_y);

        //***************** DUCK AND INTAKE *****************//
        intakeServo.setPosition(intakePosition.position);
        if (matchTimer.seconds() >= 85) {
            if (!rumbledYet) {
                rumbledYet = true;
                gamepad2.runRumbleEffect(new Gamepad.RumbleEffect.Builder().
                        addStep(1.0, 1.0, 500).
                        addStep(0.0, 0.0, 3000).
                        addStep(0.2, 0.2, 500).
                        addStep(0.4, 0.4, 500).
                        addStep(1.0, 1.0, 500).build());
            }
            duckMotor.setPower(gamepad1.right_trigger - gamepad1.left_trigger);
        }
    }
}
