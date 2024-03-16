package com.example.ai;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ImageHelper {
    private static final int TARGET_WIDTH_IMAGE = 28;
    private static final int TARGET_HEIGHT_IMAGE = 28;

    public Map<Integer, List<double[]>> loadImagesAndConvert(String directoryPath) throws IOException {
        Map<Integer, List<double[]>> imagesMap = new HashMap<>();

        Files.walk(Paths.get(directoryPath), 1)
                .filter(Files::isDirectory)
                .filter(path -> path.getFileName().toString().matches("\\d"))
                .forEach(path -> {
                    int digit = Integer.parseInt(path.getFileName().toString());
                    File[] files = path.toFile().listFiles();

                    List<double[]> imagesValue = new ArrayList<>();
                    if (files != null) {
                        for (File file : files) {
                            if (file.isFile()) {
                                try {
                                    BufferedImage image = ImageIO.read(file);
                                    BufferedImage scaledImage = scaleImage(image, TARGET_WIDTH_IMAGE, TARGET_HEIGHT_IMAGE);
                                    double [] convertedImage = convertImageToArray(scaledImage);
                                    imagesValue.add(convertedImage);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }

                    imagesMap.put(digit, imagesValue);
                });

        return imagesMap;
    }

    private BufferedImage scaleImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        BufferedImage scaledImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        Graphics g = scaledImage.createGraphics();
        g.drawImage(originalImage, 0, 0, targetWidth, targetHeight, null);
        g.dispose();
        return scaledImage;
    }

    private double[] convertImageToArray(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();
        double[] array = new double[width * height];

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Color color = new Color(image.getRGB(x, y));
                // Преобразование цвета в градацию серого (0 - черный, 1 - белый)
                array[y * width + x] = (color.getRed() + color.getGreen() + color.getBlue()) / (3.0 * 255);
            }
        }
        return array;
    }
}
