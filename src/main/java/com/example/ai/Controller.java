package com.example.ai;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public class Controller {
    private static final int EPOCHS = 10; // Количество эпох обучения
    private static final String MNIST_DIRECTORY_PATH = "C:\\Users\\pivov\\Робочий стіл\\MNIST\\training";
    private static final ImageHelper imageHelper = new ImageHelper();


    public static void main(String[] args) {
        //процесс обучение:
        NeuralNetwork network = new NeuralNetwork();
        network.initializeWeights();
        try {
            Map<Integer, List<double[]>> imagesAndLabels = imageHelper.loadImagesAndConvert(MNIST_DIRECTORY_PATH);

            for (int epoch = 0; epoch < EPOCHS; epoch++) {
                for (Map.Entry<Integer, List<double[]>> entry : imagesAndLabels.entrySet()) {
                    int label = entry.getKey();
                    List<double[]> images = entry.getValue();

                    for (double[] imagePixels : images) {
                        double[] outputDataFromForwardPropagation = network.forwardPropagation(imagePixels);
                        network.backPropagation(imagePixels, outputDataFromForwardPropagation, label);
                    }
                }
                // Здесь можно добавить логику для оценки производительности сети после каждой эпохи
                System.out.println("Epoch " + epoch + " completed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        //testing:
        try {
            BufferedImage testImage = ImageIO.read(new File("C:\\Users\\pivov\\Робочий стіл\\MNIST\\testing\\5\\15.jpg"));
            var actualResult = network.forwardPropagation(imageHelper.convertImageToArray(testImage));
            int a = 1 + 2;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}