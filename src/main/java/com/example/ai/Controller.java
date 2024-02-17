package com.example.ai;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Controller {

    int INPUT_DIM = 4;
    int OUTPUT_DIM = 3;
    int H_DIM = 5;

    //todo: fill parameters
    double[] vector = new double[INPUT_DIM];
    int right_answer = 0;
    double ALPHA = 0.001;//learning rate
    int NUM_EPOCHS = 100;

    //гиперпараметры:
    //todo: fill parameters
    double[][] w1 = new double[INPUT_DIM][H_DIM];
    double[] b1 = new double[H_DIM];
    double[][] w2 = new double[H_DIM][OUTPUT_DIM];
    double[] b2 = new double[OUTPUT_DIM];


    private void getHyperParameters() {
//        for (int i = 0; i < NUM_EPOCHS; i++) {
//            for (int j = 0; j < dataset; j++) {///?????
//                //forwardPropagation:
//                var t1 = MathOperation.addingVectors(MathOperation.multiplyMatrixOnVector(w1, vector), b1);
//                var h1 = MathOperation.relu(t1);
//                var t2 = MathOperation.addingVectors(MathOperation.multiplyMatrixOnVector(w2, h1), b2);
//                var z = MathOperation.softMax(t2);//вероятности предсказанные моделью
//                var e = MathOperation.sparseCrossEntropy(z, right_answer);
//
//                //backward:
//                var rightVector = MathOperation.oneHotEncoding(right_answer, OUTPUT_DIM);
//                var de_dt2 = MathOperation.subtractVectors(z, rightVector);
//                var de_dw2 = MathOperation.multiplyMatrixOnVector(MathOperation.transposeMatrix(h1), de_dt2);
//                var de_db2 = de_dt2;
//                var de_dh1 = MathOperation.multiplyMatrixOnVector(MathOperation.transposeMatrix(w2), de_dt2);
//                var de_dt1 = MathOperation.multiplyVectorOnVector(de_dh1 * MathOperation.ruleDeriv(t1));
//                var de_dw1 = MathOperation.multiplyVectorOnVector(vector, de_dt1);
//                var de_db1 = de_dt1;
//
//                //update:
//                //todo: w1 не имеет ту же размерность как и de_dw1
//                w1 = MathOperation.minusVectorFromMatrix(w1, MathOperation.multiplyVectorOnDigit(de_dw1, ALPHA));
//                w2 = MathOperation.minusVectorFromMatrix(w2, MathOperation.multiplyVectorOnDigit(de_dw2, ALPHA));
//                b1 = MathOperation.subtractVectors(b1, MathOperation.multiplyVectorOnDigit(de_db1, ALPHA));
//                b2 = MathOperation.subtractVectors(b2, MathOperation.multiplyVectorOnDigit(de_db2, ALPHA));
//            }
//        }
    }

    public static void main(String[] args) {
        try {
            BufferedImage image = ImageIO.read(new File("C:\\Users\\pivov\\Робочий стіл\\input.png")); // Замените "input.jpg" на путь к вашей картинке
            BufferedImage scaledImage = scaleImage(image, 28, 28);
            double[][] matrix = convertToMatrix(scaledImage);
            printMatrix(matrix);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static BufferedImage scaleImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage scaledImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics g = scaledImage.createGraphics();
        g.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g.dispose();
        return scaledImage;
    }

    public static double[][] convertToMatrix(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        double[][] matrix = new double[width][height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(image.getRGB(x, y));
                // Преобразование цвета в градиент (0 - черный, 1 - белый)
                matrix[x][y] = (color.getRed() + color.getGreen() + color.getBlue()) / (3.0 * 255);
                //(1 - черный, 0 - белый):
//                matrix[x][y] = 1 - (color.getRed() + color.getGreen() + color.getBlue()) / (3.0 * 255);
            }
        }
        return matrix;
    }

    public static void printMatrix(double[][] matrix) {
        for (int y = 0; y < matrix[0].length; y++) {
            for (int x = 0; x < matrix.length; x++) {
                System.out.print(matrix[x][y] + " ");
            }
            System.out.println();
        }
    }
}