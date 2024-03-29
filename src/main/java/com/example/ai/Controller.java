package com.example.ai;

import javafx.fxml.FXML;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.swing.*;
import java.util.List;

public class Controller {
    @FXML
    private TextField learningRateField;

    @FXML
    private TextField hDimField;

    @FXML
    private TextField quantityEpochField;

    @FXML
    private TextField batchSizeField;

    @FXML
    private Label infoLabel;

    @FXML
    private LineChart lineChart;

    @FXML
    public void result() {
        infoLabel.setText("Процес запущений...");
        initializationData();
        Network.getNetworkResult();
        infoLabel.setText("Процес завершенний!");
    }

    @FXML
    public void clear() {
        lineChart.getData().clear();
        lineChart.layout();
    }

    @FXML
    public void loss() {
        drawGraphicLoss(lineChart);
    }

    @FXML
    public void accuracyTrain() {
        drawGraphicAccuracyTrain(lineChart);
    }

    @FXML
    public void accuracyTest() {
        drawGraphicAccuracyTest(lineChart);
    }

    public void initializationData() {
        Network.ALPHA = Double.parseDouble(learningRateField.getText());
        Network.H_DIM = Integer.parseInt(hDimField.getText());
        Network.NUM_EPOCH = Integer.parseInt(quantityEpochField.getText());
        Network.BATCH_SIZE = Integer.parseInt(batchSizeField.getText());
    }

    public static void drawGraphicLoss(LineChart lineChart){
        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Loss");
        for (int i = 0; i < Network.LOSS_EPOCH_RESULT.size(); i++) {
            series1.getData().add(new XYChart.Data(String.valueOf(i + 1), Network.LOSS_EPOCH_RESULT.get(i)));
        }
        lineChart.getData().addAll(series1);
    }

    public static void drawGraphicAccuracyTest(LineChart lineChart){
        XYChart.Series series2 = new XYChart.Series();
        series2.setName(String.format("Test = %.4f",Network.ACCURACY_TEST.get(Network.ACCURACY_TEST.size()-1)));
        for (int i = 0; i < Network.ACCURACY_TRAIN.size(); i++) {
            series2.getData().add(new XYChart.Data(String.valueOf(i + 1), Network.ACCURACY_TEST.get(i)));
        }
        JOptionPane.showMessageDialog(null, Network.ACCURACY_TEST.get(Network.ACCURACY_TEST.size()-1));
        lineChart.getData().addAll(series2);
    }

    public static void drawGraphicAccuracyTrain(LineChart lineChart){
        XYChart.Series series1 = new XYChart.Series();
        series1.setName(String.format("Train = %.4f",Network.ACCURACY_TRAIN.get(Network.ACCURACY_TRAIN.size()-1)));
        for (int i = 0; i < Network.ACCURACY_TRAIN.size(); i++) {
            series1.getData().add(new XYChart.Data(String.valueOf(i + 1), Network.ACCURACY_TRAIN.get(i)));
        }
        lineChart.getData().addAll(series1);
    }
}