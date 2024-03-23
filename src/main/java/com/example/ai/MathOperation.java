package com.example.ai;

import java.util.Arrays;

public class MathOperation {

    public static double relu(double x) {
        return Math.max(0, x);
    }

    public static double sigmoid(double x) {
        return 1 / (1 + Math.exp(-x));
    }

    public static double[] derivativeSigmoid(double[] x) {
        double[] result = new double[x.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = derivativeSigmoid(x[i]);
        }
        return result;
    }

    public static double derivativeSigmoid(double x) {
        double sigmoid = sigmoid(x);
        return sigmoid * (1 - sigmoid);
    }

    public static double[] oneHotEncoding(int actualDigit) {
        double[] outputs = new double[10];
        Arrays.fill(outputs, 0d);
        outputs[actualDigit] = 1d;
        return outputs;
    }

    public static double[] softmax(double[] array) {
        double sum = Arrays.stream(array).map(Math::exp).sum();
        return Arrays.stream(array).map(Math::exp).map(exp -> exp / sum).toArray();
    }

    public static double[][] softmaxBatch(double[][] matrix) {
        double[][] result = new double[matrix.length][];

        for (int i = 0; i < matrix.length; i++) {
            double[] row = matrix[i];
            double sum = Arrays.stream(row).map(Math::exp).sum();
            result[i] = Arrays.stream(row).map(Math::exp).map(exp -> exp / sum).toArray();
        }

        return result;
    }

    public static double[] crossEntropyError(double[] predicted, double[] actual) {
        double[] result = new double[predicted.length];
        for (int i = 0; i < actual.length; i++) {
            result[i] = -actual[i] * Math.log(predicted[i]);
        }
        return result;
    }

    public static double[] matmul(double[][] matrix, double[] vector) {
        int rows = matrix.length;
        int columns = matrix[0].length;
        double[] result = new double[rows];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[i] += matrix[i][j] * vector[j];
            }
        }
        return result;
    }

    // Метод для транспонирования матрицы
    public static double[][] transpose(double[][] matrix) {
        int rows = matrix.length;
        int columns = matrix[0].length;
        double[][] transposedMatrix = new double[columns][rows];
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                transposedMatrix[i][j] = matrix[j][i];
            }
        }
        return transposedMatrix;
    }

    // Метод для внешнего произведения
    public static double[][] outerProduct(double[] vector1, double[] vector2) {
        double[][] result = new double[vector1.length][vector2.length];
        for (int i = 0; i < vector1.length; i++) {
            for (int j = 0; j < vector2.length; j++) {
                result[i][j] = vector1[i] * vector2[j];
            }
        }
        return result;
    }

    // Метод для вычитания векторов
    public static double[] subtract(double[] vector1, double[] vector2) {
        double[] result = new double[vector1.length];
        for (int i = 0; i < vector1.length; i++) {
            result[i] = vector1[i] - vector2[i];
        }
        return result;
    }
}
