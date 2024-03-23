package com.example.ai;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static com.example.ai.TestMathOperation.*;

public class Test1 {
    private static final double ALPHA = 0.002; //learning rate
    public static int INPUT_DIM = 784;
    public static int H_DIM = 100;
    public static int OUTPUT_DIM = 10;
    public static int NUM_EPOCH = 100;
    public static int BATCH_SIZE = 10;

    public static double[][] W1;
    public static double[][] W2;
    public static double[] B1;
    public static double[] B2;


    public Test1() {
        initialization();
    }

    private void initialization() {
        W1 = randomNormalMatrix(INPUT_DIM, H_DIM);
        W2 = randomNormalMatrix(H_DIM, OUTPUT_DIM);
        B1 = randomNormalArray(H_DIM);
        B2 = randomNormalArray(OUTPUT_DIM);
    }

    private double[] predict(double[] inputX) {
        double[] t1 = addingVectors(multiplyVectorOnMatrix(inputX, W1), B1);
        double[] h1 = sigmoid(t1);
        double[] t2 = addingVectors(multiplyVectorOnMatrix(h1, W2), B2);
        double[] z = softMax(t2);
        return z;
    }

    public static void main(String[] args) {
        Test1 test1 = new Test1();

        List<AbstractMap.SimpleEntry<Integer, List<Double>>> trainingImages = new CSVReader().readCSV("C:\\Users\\pivov\\Робочий стіл\\mnist_train.csv");
        for (int i = 0; i < NUM_EPOCH; i++) {
            Collections.shuffle(trainingImages);
            int iteration = trainingImages.size()/BATCH_SIZE;
            for (int j = 0; j < iteration; j++) {
                List<AbstractMap.SimpleEntry<Integer, List<Double>>> tempList = new ArrayList<>();
                for (int k = 0; k < BATCH_SIZE; k++) {
                    tempList.add(trainingImages.get(j*BATCH_SIZE+k));
                }
                test1.oneCycleBatch(tempList);
            }
        }


        List<AbstractMap.SimpleEntry<Integer, List<Double>>> testImages = new CSVReader().readCSV("C:\\Users\\pivov\\Робочий стіл\\mnist_test.csv");
        int counter = 0;
        for (int i = 0; i < testImages.size(); i++) {
            double[] dataArray = testImages.get(i).getValue().stream().mapToDouble(Double::doubleValue).toArray();
            int key = testImages.get(i).getKey();
            if(argMax(test1.predict(dataArray)) == key){
                counter++;
            }
        }
        System.out.println(counter);
        System.out.println(testImages.size());
        System.out.println(testImages.size()/counter);
    }

    private void oneCycle(double[] x, int y) {
        //forward
        double[] t1 = addingVectors(multiplyVectorOnMatrix(x, W1), B1);
        double[] h1 = sigmoid(t1);
        double[] t2 = addingVectors(multiplyVectorOnMatrix(h1, W2), B2);
        double[] z = softMax(t2);
        double E = sparseCrossEntropy(z, y);

        //backward
        double[] yFull = oneHotEncoding(y, OUTPUT_DIM);
        double[] de_dt2 = subtractVectors(z, yFull);
        double[][] de_dw2 = outerProduct(h1, de_dt2);
        double[] de_db2 = de_dt2;
        double[] de_dh1 = multiplyVectorOnMatrix(de_dt2, transposeMatrix(W2));
        double[] de_dt1 = multiplyVectorOnVector(de_dh1, sigmoidPohidna(t1));
        double[][] de_dw1 = outerProduct(x, de_dt1);
        double[] de_db1 = de_dt1;

        //update
        W1 = subtractMatrices(W1, multiplyMatrixOnDigit(de_dw1, ALPHA));
        W2 = subtractMatrices(W2, multiplyMatrixOnDigit(de_dw2, ALPHA));
        B1 = subtractVectors(B1, multiplyVectorOnDigit(de_db1, ALPHA));
        B2 = subtractVectors(B2, multiplyVectorOnDigit(de_db2, ALPHA));

    }

    private void oneCycleBatch(List<AbstractMap.SimpleEntry<Integer, List<Double>>> list) {
        double[][] x = new double[list.size()][list.get(0).getValue().size()];
        int[] y = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            AbstractMap.SimpleEntry<Integer, List<Double>> entry = list.get(i);
            y[i] = entry.getKey(); // Заполняем массив y ключами
            List<Double> values = entry.getValue();
            for (int j = 0; j < values.size(); j++) {
                x[i][j] = values.get(j)/255d; // Заполняем строки матрицы x значениями
            }
        }

        //forward
        double[][] t1 = addingVectorToMatrix(multiplyMatrixOnMatrix(x, W1), B1);
        double[][] h1 = sigmoidBatch(t1);
        double[][] t2 = addingVectorToMatrix(multiplyMatrixOnMatrix(h1, W2), B2);
        double[][] z = softmaxBatch(t2);
//        double E = sparseCrossEntropy(z, y);

        //backward
        double[][] yFull = oneHotEncodingBatch(y, OUTPUT_DIM);
        double[][] de_dt2 = subtractMatrices(z, yFull);
        double[][] de_dw2 = outerProductBatch(transposeMatrix(h1), de_dt2);
        double[] de_db2 = sumColumns(de_dt2);
        double[][] de_dh1 = multiplyMatrixOnMatrix(de_dt2, transposeMatrix(W2));
        double[][] de_dt1 = multiplyMatrixOnMatrixByElementOnly(de_dh1, sigmoidPohidnaBatch(t1));
        double[][] de_dw1 = outerProductBatch(transposeMatrix(x), de_dt1);
        double[] de_db1 = sumColumns(de_dt1);

        //update
        W1 = subtractMatrices(W1, multiplyMatrixOnDigit(de_dw1, ALPHA));
        W2 = subtractMatrices(W2, multiplyMatrixOnDigit(de_dw2, ALPHA));
        B1 = subtractVectors(B1, multiplyVectorOnDigit(de_db1, ALPHA));
        B2 = subtractVectors(B2, multiplyVectorOnDigit(de_db2, ALPHA));

    }

}
