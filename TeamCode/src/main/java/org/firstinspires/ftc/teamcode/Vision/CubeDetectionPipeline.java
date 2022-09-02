package org.firstinspires.ftc.teamcode.Vision;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvPipeline;

public class CubeDetectionPipeline extends OpenCvPipeline {
    Mat mat = new Mat();
    Rect upperROI = new Rect(new Point(240, 120), new Point(304, 145));
    Rect lowerROI = new Rect(new Point(240, 145), new Point(304, 170));
    Mat upperMat;
    Mat lowerMat;

    @Override
    public Mat processFrame(Mat input) {
        //TODO: Filter/thresholding
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGBA2RGB);
        Imgproc.cvtColor(input, mat, Imgproc.COLOR_RGB2HSV);
        Scalar lowerBound = new Scalar(61.0 / 2, 100, 100);
        Scalar upperBound = new Scalar(120.0 / 2, 255, 255);
        Core.inRange(mat, lowerBound, upperBound, mat);
        //TODO: Divide
        upperMat = mat.submat(upperROI);
        lowerMat = mat.submat(lowerROI);
        //TODO: Average
        double upperValue = (Core.mean(upperMat).val[2] / 255);
        double lowerValue = (Core.mean(lowerMat).val[2] / 255);
//        upperMat.release();
//        lowerMat.release();
//        mat.release();
        //TODO: Compare
        final double THRESHOLD = 10;
        if (upperValue > THRESHOLD) {

        }
        return null;


    }

}
