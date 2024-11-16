import org.opencv.core.*;
import org.opencv.dnn.*;
import org.opencv.videoio.VideoCapture;
import org.opencv.highgui.HighGui;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class ItemRecognition {
    static { System.loadLibrary(Core.NATIVE_LIBRARY_NAME); }

    public static void main(String[] args) throws IOException {
        // Paths to the YOLO files
        String modelWeights = "C:\\Users\\jacob\\Downloads\\yolov3.weights";
        String modelConfig = "C:\\Users\\jacob\\Downloads\\darknet-master\\darknet-master\\cfg\\yolov3.cfg";
        String classesFile = "C:\\Users\\jacob\\Downloads\\darknet-master\\darknet-master\\data\\coco.names";

        // Load the class labels
        List<String> classes = Files.readAllLines(Paths.get(classesFile));
        // Recycling items (items that can be recycled through curbside or specialized programs)
        Set<String> recyclingItems = new HashSet<>(Arrays.asList(
                "Bicycle", "Car", "Motorbike", "Aeroplane", "Bus", "Train",
                "Truck", "Boat", "Traffic light", "Fire hydrant", "Stop sign",
                "Parking meter", "Bench", "Backpack", "Umbrella", "Handbag",
                "Tie", "Suitcase", "Frisbee", "Skis", "Snowboard", "Sports ball",
                "Kite", "Baseball bat", "Baseball glove", "Skateboard", "Surfboard",
                "Tennis racket", "Bottle", "Wine glass", "Cup", "Fork", "Knife",
                "Spoon", "Bowl", "Chair", "Sofa", "Pottedplant", "Bed", "Diningtable",
                "TV monitor", "Laptop", "Mouse", "Remote", "Keyboard", "Cell phone",
                "Microwave", "Oven", "Toaster", "Sink", "Refrigerator", "Book", "Clock",
                "Vase"
        ));

        // Define a list of waste items based on coco.names
        Set<String> wasteItems = new HashSet<>(Arrays.asList(
                "banana", "apple", "sandwich", "orange", "brocolli",
                "carrot", "hot dog", "pizza", "donut", "cake"
        ));

        // Garbage items (items that cannot be recycled or composted and are typically disposed of as waste)
        Set<String> garbageItems = new HashSet<>(Arrays.asList(
                "Person", "Bird", "Cat", "Dog", "Horse", "Sheep", "Cow", "Elephant",
                "Bear", "Zebra", "Giraffe", "Scissors", "Teddy bear", "Hair dryer",
                "Toothbrush"
        ));

        // Initialize the YOLO neural network
        Net net = Dnn.readNetFromDarknet(modelConfig, modelWeights);
        net.setPreferableBackend(Dnn.DNN_BACKEND_OPENCV);
        net.setPreferableTarget(Dnn.DNN_TARGET_CPU);

        VideoCapture camera = new VideoCapture(0);
        if (!camera.isOpened()) {
            System.out.println("Error: Camera could not be opened");
            return;
        }

        Mat frame = new Mat();
        while (camera.read(frame)) {
            // Prepare the image as input for YOLO
            Mat blob = Dnn.blobFromImage(frame, 1 / 255.0, new Size(416, 416), new Scalar(0, 0, 0), true, false);
            net.setInput(blob);

            // Forward pass to get output from YOLO
            List<Mat> outputs = new ArrayList<>();
            net.forward(outputs, net.getUnconnectedOutLayersNames());

            float confidenceThreshold = 0.5f;
            for (Mat output : outputs) {
                for (int i = 0; i < output.rows(); i++) {
                    // Each row in the output represents a detected object
                    Mat row = output.row(i);
                    Mat scores = row.colRange(5, output.cols());  // Class scores start from the 5th column
                    Core.MinMaxLocResult result = Core.minMaxLoc(scores);
                    float confidence = (float) result.maxVal;
                    int classId = (int) result.maxLoc.x;

                    // Filter detections by confidence threshold
                    if (confidence > confidenceThreshold) {
                        int centerX = (int) (row.get(0, 0)[0] * frame.cols());
                        int centerY = (int) (row.get(0, 1)[0] * frame.rows());
                        int width = (int) (row.get(0, 2)[0] * frame.cols());
                        int height = (int) (row.get(0, 3)[0] * frame.rows());
                        int left = centerX - width / 2;
                        int top = centerY - height / 2;

                        // Draw a bounding box around the detected object
                        Imgproc.rectangle(frame, new Point(left, top), new Point(left + width, top + height), new Scalar(0, 255, 0), 2);

                        // Get the label for the class ID
                        String label = classes.get(classId) + ": " + String.format("%.2f", confidence);

                        // Check if the detected object is recyclable
                        String objectType = "";
                        if(recyclingItems.contains(classes.get(classId)))
                            objectType = "Recyclable";
                        else if (wasteItems.contains(classes.get(classId))) {
                            objectType = "Compost";
                        }
                        else
                            objectType = "Garbage";

                        // Display the result on the frame
                        Imgproc.putText(frame, label + " (" + objectType + ")", new Point(left, top - 10), Imgproc.FONT_HERSHEY_SIMPLEX, 0.5, new Scalar(0, 255, 0), 1);
                    }
                }
            }

            // Display the frame with the detected objects and classification
            HighGui.imshow("YOLO Object Detection", frame);

            if (HighGui.waitKey(30) == 'q') {
                break;
            }
        }

        // Release resources
        camera.release();
        HighGui.destroyAllWindows();
    }
}
