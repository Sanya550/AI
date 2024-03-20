package com.example.ai;

import java.util.Arrays;
import java.util.Random;

public class TestMathOperation {
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

    public static double[] multiplyVectorOnMatrix(double[] vector, double[][] matrix) {
        if (vector.length != matrix.length) {
            throw new IllegalArgumentException("Количество элементов в векторе должно соответствовать количеству строк в матрице.");
        }

        double[] result = new double[matrix[0].length]; // Результат будет размером количества столбцов матрицы
        for (int i = 0; i < matrix[0].length; i++) { // Перебираем столбцы матрицы
            result[i] = 0; // Инициализация элемента результата
            for (int j = 0; j < vector.length; j++) { // Перебираем элементы вектора
                result[i] += vector[j] * matrix[j][i]; // Добавляем произведение соответствующих элементов
            }
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

    public static double[][] addingVectorToMatrix(double[][] matrix, double[] vector) {
        int rows = matrix.length;
        int columns = matrix[0].length;

        double[][] result = new double[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result[i][j] = matrix[i][j] + vector[i];
            }
        }

        return result;
    }

    public static double[] multiplyVectorOnVector(double[] vector1, double[] vector2) {
        int length = vector1.length;
        double[] result = new double[length];
        if (vector1.length != vector2.length) {
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

    public static double[][] subtractMatrices(double[][] matrix1, double[][] matrix2) {
        if (matrix1 == null || matrix2 == null || matrix1.length == 0 || matrix2.length == 0 || matrix1.length != matrix2.length || matrix1[0].length != matrix2[0].length) {
            throw new IllegalArgumentException("Матрицы должны быть не пустыми и одинакового размера");
        }

        double[][] result = new double[matrix1.length][matrix1[0].length];
        for (int i = 0; i < matrix1.length; i++) {
            for (int j = 0; j < matrix1[i].length; j++) {
                result[i][j] = matrix1[i][j] - matrix2[i][j];
            }
        }

        return result;
    }

    public static double[][] multiplyMatrixOnDigit(double[][] matrix, double scalar) {
        if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
            throw new IllegalArgumentException("Матрица не должна быть пустой");
        }

        double[][] result = new double[matrix.length][matrix[0].length];
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length; j++) {
                result[i][j] = matrix[i][j] * scalar;
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

    public static double sigmoidVal(double arr1) {
        return (double) 1 / (1 + Math.pow(Math.E, -arr1));
    }

    public static double[] sigmoid(double[] arr1) {
        double[] sigma = new double[arr1.length];
        for (int i = 0; i < arr1.length; i++) {
            sigma[i] = (double) 1 / (1 + Math.pow(Math.E, -arr1[i]));
        }
        return sigma;
    }

    public static double[] sigmoidPohidna(double[] arr1) {
        double[] sigma = new double[arr1.length];
        for (int i = 0; i < arr1.length; i++) {
            sigma[i] = sigmoidVal(arr1[i]) * (1 - sigmoidVal(arr1[i]));
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
        var sum = Arrays.stream(arr1).boxed().mapToDouble(v->Math.pow(Math.E, v)).sum();
        for (int i = 0; i < arr1.length; i++) {
            softArr[i] = Math.pow(Math.E, arr1[i]) / (sum);
        }
        return softArr;
    }

    public static double sparseCrossEntropy(double[] z, int rightIndex) {
        if (z == null || z.length == 0 || rightIndex < 0 || rightIndex >= z.length) {
            throw new IllegalArgumentException("Проверьте массив и индекс");
        }
        return -Math.log(z[rightIndex]);
    }

    public static double[] oneHotEncoding(int rightIndex, int outputArraySize) {
        var resultArray = new double[outputArraySize];
        for (int i = 0; i < outputArraySize; i++) {
            resultArray[i] = i == rightIndex ? 1d : 0d;
        }
        return resultArray;
    }

    public static double[] ruleDeriv(double[] arr) {
        var result = new double[arr.length];
        for (int i = 0; i < arr.length; i++) {
            result[i] = arr[i] >= 0 ? 1d : 0d;
        }
        return result;
    }

    public static double[] randomNormalArray(int size) {
        Random random = new Random();
        double[] numbers = new double[size];

        for (int i = 0; i < size; i++) {
            numbers[i] = 0d;
//            numbers[i] = (random.nextGaussian()-0.5)*2*Math.sqrt(1d/784d);
        }
        return numbers;
    }

    public static double[][] randomNormalMatrix(int rows, int cols) {
        Random random = new Random();
        double[][] matrix = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                matrix[i][j] =0d;
//                matrix[i][j] = (random.nextGaussian()-0.5)*2*Math.sqrt(1d/784d);
            }
        }

        return matrix;
    }

    public static int argMax(double[] array) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Массив не должен быть пустым");
        }

        int maxIndex = 0;
        double maxValue = array[0];

        for (int i = 1; i < array.length; i++) {
            if (array[i] > maxValue) {
                maxValue = array[i];
                maxIndex = i;
            }
        }

        return maxIndex;
    }

    public static double[] reluDeriv(double[] array) {
        double [] result = new double[array.length];
        for (int i = 0; i < array.length; i++) {
            result[i] = array[i] >= 0 ? 1d : 0d;
        }
        return result;
    }

    public static double[][] outerProduct(double[] h1, double[] t2) {
        double[][] result = new double[h1.length][t2.length];

        for (int i = 0; i < h1.length; i++) {
            for (int j = 0; j < t2.length; j++) {
                result[i][j] = h1[i] * t2[j];// поделить на todo: к-сть batch
            }
        }

        return result;
    }

}
