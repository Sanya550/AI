package com.example.ai;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

public class CSVReader {

    public List<SimpleEntry<Integer, List<Double>>> readCSV(String filePath) {
        List<SimpleEntry<Integer, List<Double>>> resultList = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            // Пропускаем первую строку (заголовок)
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");

                if (values.length > 0) {
                    Integer key = Integer.parseInt(values[0]);
                    List<Double> list = new ArrayList<>();

                    for (int i = 1; i < values.length; i++) {
                        list.add(Double.parseDouble(values[i]));
                    }

                    resultList.add(new SimpleEntry<>(key, list));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    public List<List<Double>> readCSV1(String filePath) {
        List<List<Double>> resultList = new ArrayList<>();

        try (BufferedReader br = Files.newBufferedReader(Paths.get(filePath))) {
            // Пропускаем первую строку (заголовок)
            br.readLine();

            String line;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(",");
                List<Double> rowList = new ArrayList<>();

                for (String value : values) {
                    rowList.add(Double.parseDouble(value));
                }

                resultList.add(rowList);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return resultList;
    }

    public static void main(String[] args) {
        CSVReader reader = new CSVReader();
        List<SimpleEntry<Integer, List<Double>>> data = new CSVReader().readCSV("C:\\Users\\pivov\\Робочий стіл\\mnist_test.csv");
        // Тестовый вывод для проверки
        data.forEach(entry -> {
            System.out.println("Key: " + entry.getKey() + ", Values: " + entry.getValue());
        });
    }
}

