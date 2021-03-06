/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package detect.interferogramm;

import java.util.Arrays;
import org.bytedeco.javacv.*;
import org.bytedeco.javacpp.*;
import static org.bytedeco.javacpp.opencv_core.*;
import static org.bytedeco.javacpp.opencv_imgproc.*;
import static org.bytedeco.javacpp.opencv_highgui.*;
//import static org.bytedeco.javacpp.opencv_imgcodecs.*;
import static org.bytedeco.javacpp.opencv_calib3d.*;
import static org.bytedeco.javacpp.opencv_objdetect.*;
/**
 *
 * @author user
 */
public class DetectInterferogramm {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        IplImage src = cvLoadImage("image_gallery.jpg",0);
        IplImage tmp = cvLoadImage("fragment.png",0);	

        IplImage result = cvCreateImage(
                cvSize(src.width() - tmp.width() + 1,
                        src.height() - tmp.height() + 1), IPL_DEPTH_32F, src.nChannels());

        //cvZero(result);

        // Match Template Function from OpenCV
        cvMatchTemplate(src, tmp, result, CV_TM_CCORR_NORMED);

        // double[] min_val = new double[2];
        // double[] max_val = new double[2];
        DoublePointer min_val = new DoublePointer();
        DoublePointer max_val = new DoublePointer();

        CvPoint minLoc = new CvPoint();
        CvPoint maxLoc = new CvPoint();

        cvMinMaxLoc(result, min_val, max_val, minLoc, maxLoc, null);

        // Get the Max or Min Correlation Value
        // System.out.println(Arrays.toString(min_val));
        // System.out.println(Arrays.toString(max_val));

        CvPoint point = new CvPoint();
        point.x(maxLoc.x() + tmp.width());
        point.y(maxLoc.y() + tmp.height());
        // cvMinMaxLoc(src, min_val, max_val,0,0,result);

        cvRectangle(src, maxLoc, point, CvScalar.WHITE, 2, 8, 0); // Draw a
                                                                // Rectangle for
                                                                // Matched
                                                                // Region
        /*CvRect rect = new CvRect();
        rect.x(maxLoc.x());
        rect.y(maxLoc.y());
        rect.width(src.width());
        rect.height(src.height());
        cvSetImageROI(src, rect);*/
        IplImage imageNew = cvCreateImage(cvGetSize(src), src.depth(),
                src.nChannels());
        cvCopy(src, imageNew);
        cvSaveImage("Interferograms.jpeg", imageNew);

        cvShowImage("Lena Image", src);
        cvWaitKey(0);
        cvReleaseImage(src);
        cvReleaseImage(tmp);
        cvReleaseImage(result);
    }
    
}
