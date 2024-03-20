package com.example.ai;


import java.util.Arrays;
import java.util.Random;

public class NeuralNetwork {
    private static final int INPUT_NODES = 784; // Пример для MNIST
    private static final int HIDDEN_NODES_1 = 16;
    private static final int HIDDEN_NODES_2 = 16; // Второй скрытый слой
    private static final int OUTPUT_NODES = 10;
    private static final double LEARNING_RATE = 0.1;

    public static double[][] weightsInputHidden; // Веса между входным и первым скрытым слоем
    public static double[] hiddenLayerBias1;
    public static double[][] weightsHiddenHidden; // Веса между двумя скрытыми слоями
    public static double[] hiddenLayerBias2;
    public static double[][] weightsHiddenOutput; // Веса между вторым скрытым и выходным слоем
    public static double[] outputLayerBias;
    // Добавляем поля для хранения активаций скрытых слоев
    public static double[] hiddenInputs1;
    public static double[] hiddenInputs2;

    public static double[] z1;
    public static double[] z2;

    // Метод forwardPropagation с двумя скрытыми слоями
    public double[] forwardPropagation(double[] inputs) {
        hiddenInputs1 = new double[HIDDEN_NODES_1];
        z1 = new double[HIDDEN_NODES_1];
        for (int i = 0; i < HIDDEN_NODES_1; i++) {
            for (int j = 0; j < INPUT_NODES; j++) {
                hiddenInputs1[i] += inputs[j] * weightsInputHidden[i][j];
                z1[i] += inputs[j] * weightsInputHidden[i][j];
            }
            hiddenInputs1[i] += hiddenLayerBias1[i];
            z1[i] += hiddenLayerBias1[i];
            hiddenInputs1[i] = MathOperation.sigmoid(hiddenInputs1[i]);
        }

        hiddenInputs2 = new double[HIDDEN_NODES_2];
        z2 = new double[HIDDEN_NODES_2];
        for (int i = 0; i < HIDDEN_NODES_2; i++) {
            for (int j = 0; j < HIDDEN_NODES_1; j++) {
                hiddenInputs2[i] += hiddenInputs1[j] * weightsHiddenHidden[i][j];
                z2[i] += hiddenInputs1[j] * weightsHiddenHidden[i][j];
            }
            hiddenInputs2[i] += hiddenLayerBias2[i];
            z2[i] += hiddenLayerBias2[i];
            hiddenInputs2[i] = MathOperation.sigmoid(hiddenInputs2[i]);
        }

        double[] finalOutputs = new double[OUTPUT_NODES];
        for (int i = 0; i < OUTPUT_NODES; i++) {
            for (int j = 0; j < HIDDEN_NODES_2; j++) {
                finalOutputs[i] += hiddenInputs2[j] * weightsHiddenOutput[i][j];
            }
            finalOutputs[i] += outputLayerBias[i];
        }

        return MathOperation.softmax(finalOutputs);
    }

    public void backPropagation(double[] inputs, double[] predicted, int actualDigit) {
        // Получение ожидаемого вывода в формате one-hot encoding
        double[] expectedOutputs = MathOperation.oneHotEncoding(actualDigit);

        // Расчет ошибки на выходном слое
        double[] outputErrors = MathOperation.subtract(predicted, expectedOutputs);

        // Расчет градиента для весов между вторым скрытым слоем и выходным слоем
        double[][] gradientsHiddenOutput = MathOperation.outerProduct(hiddenInputs2, outputErrors);

        // Обновление весов и биасов между вторым скрытым и выходным слоем
        for (int i = 0; i < OUTPUT_NODES; i++) {
            for (int j = 0; j < HIDDEN_NODES_2; j++) {
                weightsHiddenOutput[i][j] -= LEARNING_RATE * gradientsHiddenOutput[j][i];
            }
            outputLayerBias[i] -= LEARNING_RATE * outputErrors[i];
        }

        // Расчет ошибки для второго скрытого слоя
        double[] hiddenLayer2Errors = MathOperation.matmul(MathOperation.transpose(weightsHiddenOutput), outputErrors);

        // Расчет градиента для весов между первым и вторым скрытым слоем
        double[][] gradientsHiddenHidden = MathOperation.outerProduct(hiddenInputs1, hiddenLayer2Errors);

        // Обновление весов и биасов между первым и вторым скрытым слоем
        for (int i = 0; i < HIDDEN_NODES_2; i++) {
            for (int j = 0; j < HIDDEN_NODES_1; j++) {
                weightsHiddenHidden[i][j] -= LEARNING_RATE * gradientsHiddenHidden[j][i];
            }
            hiddenLayerBias2[i] -= LEARNING_RATE * hiddenLayer2Errors[i];
        }

        // Расчет ошибки для первого скрытого слоя
        double[] hiddenLayer1Errors = MathOperation.matmul(MathOperation.transpose(weightsHiddenHidden), hiddenLayer2Errors);

        // Расчет градиента для весов между входным слоем и первым скрытым слоем
        double[][] gradientsInputHidden = MathOperation.outerProduct(inputs, hiddenLayer1Errors);

        // Обновление весов и биасов между входным слоем и первым скрытым слоем
        for (int i = 0; i < HIDDEN_NODES_1; i++) {
            for (int j = 0; j < INPUT_NODES; j++) {
                weightsInputHidden[i][j] -= LEARNING_RATE * gradientsInputHidden[j][i];
            }
            hiddenLayerBias1[i] -= LEARNING_RATE * hiddenLayer1Errors[i];
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

        // Инициализация весов малыми случайными значениями
        for (int i = 0; i < HIDDEN_NODES_1; i++) {
            for (int j = 0; j < INPUT_NODES; j++) {
                weightsInputHidden[i][j] = random.nextGaussian() * 0.01;
            }
        }

        for (int i = 0; i < HIDDEN_NODES_2; i++) {
            for (int j = 0; j < HIDDEN_NODES_1; j++) {
                weightsHiddenHidden[i][j] = random.nextGaussian() * 0.01;
            }
        }

        for (int i = 0; i < OUTPUT_NODES; i++) {
            for (int j = 0; j < HIDDEN_NODES_2; j++) {
                weightsHiddenOutput[i][j] = random.nextGaussian() * 0.01;
            }
        }

        // Инициализация смещений нулями
        Arrays.fill(hiddenLayerBias1, 0);
        Arrays.fill(hiddenLayerBias2, 0);
        Arrays.fill(outputLayerBias, 0);
    }

}
