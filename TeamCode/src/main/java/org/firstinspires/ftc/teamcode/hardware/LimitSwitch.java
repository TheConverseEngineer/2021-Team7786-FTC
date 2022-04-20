package org.firstinspires.ftc.teamcode.hardware;

import static org.firstinspires.ftc.teamcode.LogTags.HARDWARE_TAG;

import android.util.Log;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.TouchSensor;


public class LimitSwitch {

    private TouchSensor limitSwitch;
    private boolean isPressed;

    public LimitSwitch(HardwareMap hwMap, String id) {
        Log.i(HARDWARE_TAG, "LimitSwitch with id " + id + " being instantiated");
        limitSwitch = hwMap.get(TouchSensor.class, id);
        Log.i(HARDWARE_TAG, "LimitSwitch with id " + id + " instantiated");
    }

    public boolean isActivated() {
        return this.isPressed;
    }

    public void update() {
        this.isPressed = this.limitSwitch.isPressed();
    }
}
