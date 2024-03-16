package com.example.ai;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;
import javax.imageio.ImageIO;

public class Controller {
    private final String MNIST_DIRECTORY_PATH = "C:\\Users\\pivov\\Робочий стіл\\MNIST\\training";
    private final ImageHelper imageHelper = new ImageHelper();


    public static void main(String[] args) {
//        // Инициализация нейронной сети
//        NeuralNetwork network = new NeuralNetwork();
//        network.initializeNetwork();
//
//        // Загрузка данных для обучения (здесь просто пример, нужно загрузить реальные данные)
//        double[][] trainingInputs = new double[][] {
//                // Предположим, что у нас есть обучающие данные MNIST
//        };
//        int[] trainingLabels = new int[] {
//                // Метки для данных MNIST
//        };
//
//        // Процесс обучения
//        int epochs = 1000; // Количество эпох обучения
//        for (int epoch = 0; epoch < epochs; epoch++) {
//            double loss = 0.0;
//            for (int i = 0; i < trainingInputs.length; i++) {
//                double[] inputs = trainingInputs[i];
//                int actualDigit = trainingLabels[i];
//
//                // Прямое распространение
//                double[] outputs = network.forwardPropagation(inputs);
//
//                // Оценка потерь (например, квадратичная ошибка)
//                loss += calculateLoss(outputs, actualDigit);
//
//                // Обратное распространение для обновления весов
//                network.backPropagation(inputs, actualDigit, outputs);
//            }
//
//            // Выводим потери после каждой эпохи, чтобы видеть прогресс
//            System.out.println("Epoch " + epoch + ": Loss = " + (loss / trainingInputs.length));
//        }


    }

    private static double calculateLoss(double[] outputs, int actualDigit) {
        // Реализация функции потерь (например, квадратичная ошибка)
        return 0; // Примерный код, требует реализации
    }

    public void initializeNetwork() {
        // Инициализация весов и структуры сети
    }




}