package org.firstinspires.ftc.teamcode.hardware;

import static org.firstinspires.ftc.teamcode.LogTags.HARDWARE_TAG;

import android.util.Log;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;


public class IMU {

    private BNO055IMU imu;

    private double offset;
    private double lastRead;
    private String id;
    public IMU (HardwareMap hwMap, String id, double heading) {
        Log.i(HARDWARE_TAG, "IMU with id " + id + " being instantiated");
        this.id = id;
        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.RADIANS;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;

        imu = hwMap.get(BNO055IMU.class, id);
        imu.initialize(parameters);

        offset = heading;
        Log.i(HARDWARE_TAG, "IMU " + id + "  instantiated");
    }

    public double getHeading() {
        return offset + imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).firstAngle;
    }

    public void setHeading(double heading) {
        Log.i(HARDWARE_TAG, "IMU " + id + " Heading set to " + heading);
        offset -= heading + getHeading();
        lastRead = heading;
    }

    public BNO055IMU getImu() {
        return imu;
    }
}
