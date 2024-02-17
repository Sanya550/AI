package com.example.ai;

import java.util.Arrays;

public class MathOperation {

    public static double[][] transposeMatrix(double[][] matrix) {
        int rows = matrix.length;
        int cols = matrix[0].length;

        double[][] transposed = new double[cols][rows];

        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                transposed[i][j] = matrix[j][i];
            }
        }

        return transposed;
    }

    public static double[] multiplyMatrixOnVector(double[][] matrix, double[] vector) {
        int matrixRows = matrix.length;
        int matrixCols = matrix[0].length;

        if (matrixCols != vector.length) {
            throw new IllegalArgumentException("Number of columns in the matrix must be equal to the length of the vector.");
        }

        double[] result = new double[matrixRows];

        for (int i = 0; i < matrixRows; i++) {
            double sum = 0.0;
            for (int j = 0; j < matrixCols; j++) {
                sum += matrix[i][j] * vector[j];
            }
            result[i] = sum;
        }

        return result;
    }

    public static double[][] minusVectorFromMatrix(double[][] matrix, double[] vector) {
        int rows = matrix.length;
        int columns = matrix[0].length;

        double[][] result = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[i][j] = matrix[i][j] - vector[i];
            }
        }

        return result;
    }

    public static double[] multiplyVectorOnVector(double[] vector1, double[] vector2) {
        int length = vector1.length;
        double[] result = new double[length];
        if (vector1.length != vector2.length){
            throw new IllegalArgumentException("vector1 != vector2");
        }

        for (int i = 0; i < length; i++) {
            result[i] = vector1[i] * vector2[i];
        }

        return result;
    }

    public static double[] multiplyVectorOnDigit(double[] vector, double scalar) {
        int length = vector.length;
        double[] result = new double[length];

        for (int i = 0; i < length; i++) {
            result[i] = vector[i] * scalar;
        }

        return result;
    }

    public static double[][] multiplyMatrixOnMatrix(double[][] a, double[][] b) {
        double[][] result = new double[a.length][b[0].length];
        for (int i = 0; i < a.length; i++) {
            for (int j = 0; j < b[0].length; j++) {
                for (int k = 0; k < a[0].length; k++) {
                    result[i][j] += a[i][k] * b[k][j];
                }
            }
        }
        return result;
    }

    public static double[] addingVectors(double[] vector1, double[] vector2) {
        if (vector1.length != vector2.length) {
            throw new IllegalArgumentException("Vectors must have the same length for addition.");
        }

        int length = vector1.length;
        double[] result = new double[length];

        for (int i = 0; i < length; i++) {
            result[i] = vector1[i] + vector2[i];
        }

        return result;
    }

    public static double[] subtractVectors(double[] vector1, double[] vector2) {
        if (vector1.length != vector2.length) {
            throw new IllegalArgumentException("Vectors must have the same length for subtraction.");
        }

        int length = vector1.length;
        double[] result = new double[length];

        for (int i = 0; i < length; i++) {
            result[i] = vector1[i] - vector2[i];
        }

        return result;
    }

    public static double[] sigmoid(double[] arr1) {
        double[] sigma = new double[arr1.length];
        for (int i = 0; i < arr1.length; i++) {
            sigma[i] = (double) 1 / (1 + Math.pow(Math.E, -arr1[i]));
        }
        return sigma;
    }

    public static double[] relu(double[] arr1) {
        double[] reluArr = new double[arr1.length];
        for (int i = 0; i < arr1.length; i++) {
            reluArr[i] = arr1[i] <= 0 ? 0 : arr1[i];
        }
        return reluArr;
    }

    public static double[] softMax(double[] arr1) {
        var softArr = new double[arr1.length];
        var sum = Arrays.stream(arr1).sum();
        for (int i = 0; i < arr1.length; i++) {
            softArr[i] = Math.pow(Math.E, arr1[i]) / sum;
        }
        return softArr;
    }

    public static double sparseCrossEntropy(double [] z, int rightIndex) {
        return -Math.log(z[rightIndex]);
    }

    public static double[] oneHotEncoding(int rightIndex, int outputArraySize){
        var resultArray = new double[outputArraySize];
        for (int i = 0; i < outputArraySize; i++) {
            resultArray[i] = i == rightIndex ? 1d : 0d;
        }
        return resultArray;
    }

    public static double[] ruleDeriv(double [] arr) {
        var result = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i]>=0 ? 1d : 0d;
        }
        return result;
    }
}
