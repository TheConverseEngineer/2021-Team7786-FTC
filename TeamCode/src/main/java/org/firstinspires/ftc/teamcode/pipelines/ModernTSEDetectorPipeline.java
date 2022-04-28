package org.firstinspires.ftc.teamcode.pipelines;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class ModernTSEDetectorPipeline extends OpenCvPipeline {

    private volatile boolean detected = false;

    private Mat YCrCbMat = new Mat();
    private Mat CutYCrCbMat = new Mat();
    private Mat ChanneledMat = new Mat();

    Rect border = new Rect(140, 245, 45, 50);

    Scalar RGB_WHITE = new Scalar(255.0, 255.0, 255.0);

    public ModernTSEDetectorPipeline(Telemetry telemetry) {

    }


    @Override
    public Mat processFrame(Mat input) {
        Imgproc.cvtColor(input, YCrCbMat, Imgproc.COLOR_RGB2YCrCb);
        //telemetry.addData("OpenCV Running", "True");

        Imgproc.rectangle(input, border, RGB_WHITE, 2);

        CutYCrCbMat = YCrCbMat.submat(border);

        Core.extractChannel(CutYCrCbMat, ChanneledMat, 2);
        double value = Core.mean(ChanneledMat).val[0];

        //telemetry.addData("Mean Score (less that 115 is a TSE)", value);
        //telemetry.update();

        detected = value <= 115;

        return input;
    }

    public boolean isDetected() {
        return detected;
    }
}
