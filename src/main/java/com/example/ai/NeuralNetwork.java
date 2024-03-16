package com.example.ai;


import java.util.Random;

public class NeuralNetwork {
    private static final int INPUT_NODES = 784; // Пример для MNIST
    private static final int HIDDEN_NODES_1 = 16;
    private static final int HIDDEN_NODES_2 = 16; // Второй скрытый слой
    private static final int OUTPUT_NODES = 10;
    private static final double LEARNING_RATE = 0.001;

    public static double[][] weightsInputHidden; // Веса между входным и первым скрытым слоем
    public static double[] hiddenLayerBias1;
    public static double[][] weightsHiddenHidden; // Веса между двумя скрытыми слоями
    public static double[] hiddenLayerBias2;
    public static double[][] weightsHiddenOutput; // Веса между вторым скрытым и выходным слоем
    public static double[] outputLayerBias;
    // Добавляем поля для хранения активаций скрытых слоев
    public static double[] hiddenInputs1;
    public static double[] hiddenInputs2;

    // Метод forwardPropagation с двумя скрытыми слоями
    public double[] forwardPropagation(double[] inputs) {
        hiddenInputs1 = new double[HIDDEN_NODES_1];
        for (int i = 0; i < HIDDEN_NODES_1; i++) {
            for (int j = 0; j < INPUT_NODES; j++) {
                hiddenInputs1[i] += inputs[j] * weightsInputHidden[i][j];
            }
            hiddenInputs1[i] += hiddenLayerBias1[i];
            hiddenInputs1[i] = MathOperation.sigmoid(hiddenInputs1[i]);
        }

        hiddenInputs2 = new double[HIDDEN_NODES_2];
        for (int i = 0; i < HIDDEN_NODES_2; i++) {
            for (int j = 0; j < HIDDEN_NODES_1; j++) {
                hiddenInputs2[i] += hiddenInputs1[j] * weightsHiddenHidden[i][j];
            }
            hiddenInputs2[i] += hiddenLayerBias2[i];
            hiddenInputs2[i] = MathOperation.sigmoid(hiddenInputs2[i]);
        }

        double[] finalOutputs = new double[OUTPUT_NODES];
        for (int i = 0; i < OUTPUT_NODES; i++) {
            for (int j = 0; j < HIDDEN_NODES_2; j++) {
                finalOutputs[i] += hiddenInputs2[j] * weightsHiddenOutput[i][j];
            }
            finalOutputs[i] += outputLayerBias[i];
            finalOutputs[i] = MathOperation.sigmoid(finalOutputs[i]);
        }

        return finalOutputs;
    }

    public void backPropagation(double[] inputs, double[] finalOutputs, int actualDigit) {
        double[] expectedOutputs = MathOperation.oneHotEncoding(actualDigit);

        // Ошибка на выходе
        double[] outputErrors = new double[OUTPUT_NODES];
        for (int i = 0; i < OUTPUT_NODES; i++) {
            outputErrors[i] = expectedOutputs[i] - finalOutputs[i];
        }

        // Градиент для весов между вторым скрытым и выходным слоем
        double[][] gradientsHiddenOutput = new double[OUTPUT_NODES][HIDDEN_NODES_2];
        for (int i = 0; i < OUTPUT_NODES; i++) {
            for (int j = 0; j < HIDDEN_NODES_2; j++) {
                gradientsHiddenOutput[i][j] = outputErrors[i] * finalOutputs[i] * (1 - finalOutputs[i]); // Производная сигмоиды
            }
        }

        // Расчет ошибки для второго скрытого слоя
        double[] hiddenLayer2Errors = new double[HIDDEN_NODES_2];
        for (int i = 0; i < HIDDEN_NODES_2; i++) {
            for (int j = 0; j < OUTPUT_NODES; j++) {
                hiddenLayer2Errors[i] += outputErrors[j] * weightsHiddenOutput[j][i];
            }
        }

        // Градиент для весов между первым и вторым скрытым слоем
        double[][] gradientsHiddenHidden = new double[HIDDEN_NODES_2][HIDDEN_NODES_1];
        for (int i = 0; i < HIDDEN_NODES_2; i++) {
            for (int j = 0; j < HIDDEN_NODES_1; j++) {
                // Предположим, что hiddenInputs2[j] - это активация j-го нейрона во втором скрытом слое
                gradientsHiddenHidden[i][j] = hiddenLayer2Errors[i] * hiddenInputs2[j] * (1 - hiddenInputs2[j]); // Производная ReLU (или другой функции)
            }
        }

        // Расчет ошибки для первого скрытого слоя
        double[] hiddenLayer1Errors = new double[HIDDEN_NODES_1];
        for (int i = 0; i < HIDDEN_NODES_1; i++) {
            for (int j = 0; j < HIDDEN_NODES_2; j++) {
                hiddenLayer1Errors[i] += hiddenLayer2Errors[j] * weightsHiddenHidden[j][i];
            }
        }

        // Обновление весов и биасов для всех слоев
        for (int i = 0; i < OUTPUT_NODES; i++) {
            for (int j = 0; j < HIDDEN_NODES_2; j++) {
                weightsHiddenOutput[i][j] += LEARNING_RATE * gradientsHiddenOutput[i][j];
            }
            outputLayerBias[i] += LEARNING_RATE * outputErrors[i];
        }

        for (int i = 0; i < HIDDEN_NODES_2; i++) {
            for (int j = 0; j < HIDDEN_NODES_1; j++) {
                weightsHiddenHidden[i][j] += LEARNING_RATE * gradientsHiddenHidden[i][j];
            }
            hiddenLayerBias2[i] += LEARNING_RATE * hiddenLayer2Errors[i];
        }

        double[][] gradientsInputHidden = new double[HIDDEN_NODES_1][INPUT_NODES];
        for (int i = 0; i < HIDDEN_NODES_1; i++) {
            for (int j = 0; j < INPUT_NODES; j++) {
                // Предположим, что hiddenInputs1[i] - это активация i-го нейрона в первом скрытом слое
                // Производная функции активации для ReLU: f'(x) = x > 0 ? 1 : 0
                // Для сигмоиды или другой функции здесь может быть другая производная
                double derivative = hiddenInputs1[i] > 0 ? 1 : 0;
                gradientsInputHidden[i][j] = hiddenLayer1Errors[i] * derivative * inputs[j];
            }
        }

// Обновление весов и биасов для первого скрытого слоя
        for (int i = 0; i < HIDDEN_NODES_1; i++) {
            for (int j = 0; j < INPUT_NODES; j++) {
                weightsInputHidden[i][j] += LEARNING_RATE * gradientsInputHidden[i][j];
            }
            hiddenLayerBias1[i] += LEARNING_RATE * hiddenLayer1Errors[i];
        }
    }

    public void initializeWeights() {
        Random random = new Random();

        weightsInputHidden = new double[HIDDEN_NODES_1][INPUT_NODES];
        weightsHiddenHidden = new double[HIDDEN_NODES_2][HIDDEN_NODES_1];
        weightsHiddenOutput = new double[OUTPUT_NODES][HIDDEN_NODES_2];

        hiddenLayerBias1 = new double[HIDDEN_NODES_1];
        hiddenLayerBias2 = new double[HIDDEN_NODES_2];
        outputLayerBias = new double[OUTPUT_NODES];

        // Инициализация весов между входным слоем и первым скрытым слоем
        for (int i = 0; i < HIDDEN_NODES_1; i++) {
            for (int j = 0; j < INPUT_NODES; j++) {
                weightsInputHidden[i][j] = random.nextGaussian() * Math.sqrt(2.0 / INPUT_NODES);
            }
            hiddenLayerBias1[i] = random.nextGaussian() * Math.sqrt(2.0 / INPUT_NODES);
        }

        // Инициализация весов между первым и вторым скрытым слоем
        for (int i = 0; i < HIDDEN_NODES_2; i++) {
            for (int j = 0; j < HIDDEN_NODES_1; j++) {
                weightsHiddenHidden[i][j] = random.nextGaussian() * Math.sqrt(2.0 / HIDDEN_NODES_1);
            }
            hiddenLayerBias2[i] = random.nextGaussian() * Math.sqrt(2.0 / HIDDEN_NODES_1);
        }

        // Инициализация весов между вторым скрытым слоем и выходным слоем
        for (int i = 0; i < OUTPUT_NODES; i++) {
            for (int j = 0; j < HIDDEN_NODES_2; j++) {
                weightsHiddenOutput[i][j] = random.nextGaussian() * Math.sqrt(2.0 / HIDDEN_NODES_2);
            }
            outputLayerBias[i] = random.nextGaussian() * Math.sqrt(2.0 / HIDDEN_NODES_2);
        }
    }

}
