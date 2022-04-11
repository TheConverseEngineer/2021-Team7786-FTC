package org.firstinspires.ftc.teamcode.tests;

import static org.firstinspires.ftc.teamcode.vision.VISION_DATA.CAMERA_WIDTH;
import static org.firstinspires.ftc.teamcode.vision.VISION_DATA.CAMERA_HEIGHT;

import com.acmerobotics.dashboard.FtcDashboard;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.vision.TSEDetectorCam;
import org.firstinspires.ftc.teamcode.vision.VISION_DATA;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;

@Disabled
@TeleOp(name = "vision", group = "default")
public class visionTest extends OpMode {
    OpenCvCamera cam;
    FtcDashboard dash;

    @Override
    public void init() {
        cam = OpenCvCameraFactory.getInstance().createInternalCamera(OpenCvInternalCamera.CameraDirection.BACK);

        cam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener() {
            @Override
            public void onOpened() {
                cam.startStreaming(CAMERA_WIDTH, CAMERA_HEIGHT, OpenCvCameraRotation.UPRIGHT);
            }

            @Override
            public void onError(int errorCode) {

            }
        });
        dash = FtcDashboard.getInstance();
        telemetry = dash.getTelemetry();
        dash.startCameraStream(cam, 10);
    }

    @Override
    public void loop() {

    }
}
