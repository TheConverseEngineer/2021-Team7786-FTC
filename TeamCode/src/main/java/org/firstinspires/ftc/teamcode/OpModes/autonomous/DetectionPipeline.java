package org.firstinspires.ftc.teamcode.OpModes.autonomous;

import static org.firstinspires.ftc.teamcode.OpModes.autonomous.DETECTION_CSTS.detectColor;
import static org.firstinspires.ftc.teamcode.OpModes.autonomous.DETECTION_CSTS.detectHeight;
import static org.firstinspires.ftc.teamcode.OpModes.autonomous.DETECTION_CSTS.detectWidth;
import static org.firstinspires.ftc.teamcode.OpModes.autonomous.DETECTION_CSTS.detectX1;
import static org.firstinspires.ftc.teamcode.OpModes.autonomous.DETECTION_CSTS.detectY1;

import org.opencv.core.Mat;
import org.opencv.core.Rect;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class DetectionPipeline extends OpenCvPipeline {

    private Mat output = new Mat();
    private Mat yCrCb = new Mat();

    @Override
    public Mat processFrame(Mat input) {
        input.copyTo(output);

        Imgproc.cvtColor(input, yCrCb, Imgproc.COLOR_RGB2YCrCb);

        Imgproc.rectangle(output, new Rect(detectX1, detectY1, detectWidth, detectHeight), detectColor, 2);
        return output;
    }
}
