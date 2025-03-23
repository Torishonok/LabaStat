/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.torishonok.labastat.Model;

import java.util.Arrays;

/**
 *
 * @author vikus
 */
public class Stat {
    public static double[] calculateMean(double[][] data) {
        int columnCount = data[0].length;
        double[] means = new double[columnCount];

        for (int col = 0; col < columnCount; col++) {
            double sum = 0;
            for (double[] row : data) {
                sum += row[col];
            }
            means[col] = sum / data.length;
        }
        return means;
    }

    public static double[] calculateGeometricMean(double[][] data) {
        int columnCount = data[0].length;
        double[] geometricMeans = new double[columnCount];

        for (int col = 0; col < columnCount; col++) {
            double product = 1;
            for (double[] row : data) {
                product *= row[col];
            }
            geometricMeans[col] = Math.pow(product, 1.0 / data.length);
        }
        return geometricMeans;
    }

    public static double[] calculateStandardDeviation(double[][] data) {
        int columnCount = data[0].length;
        double[] stdDevs = new double[columnCount];

        for (int col = 0; col < columnCount; col++) {
            double mean = calculateMean(data)[col];
            double sum = 0;
            for (double[] row : data) {
                sum += Math.pow(row[col] - mean, 2);
            }
            stdDevs[col] = Math.sqrt(sum / data.length);
        }
        return stdDevs;
    }

    public static double[] calculateRange(double[][] data) {
        int columnCount = data[0].length;
        double[] ranges = new double[columnCount];

        for (int col = 0; col < columnCount; col++) {
            double min = Double.MAX_VALUE;
            double max = Double.MIN_VALUE;
            for (double[] row : data) {
                double value = row[col];
                if (value < min) min = value;
                if (value > max) max = value;
            }
            ranges[col] = max - min;
        }
        return ranges;
    }
     public static double[][] calculateCovariance(double[][] data) {
        int columnCount = data[0].length;
        double[][] covariances = new double[columnCount][columnCount];

        for (int i = 0; i < columnCount; i++) {
            for (int j = 0; j < columnCount; j++) {
                double meanX = calculateMean(data)[i];
                double meanY = calculateMean(data)[j];
                double covariance = 0;
                for (double[] row : data) {
                    covariance += (row[i] - meanX) * (row[j] - meanY);
                }
                covariances[i][j] = covariance / data.length;
            }
        }
        return covariances;
    }

    public static int[] countElements(double[][] data) {
        int[] counts = new int[data[0].length]; // Массив для хранения количества элементов в каждом столбце

    // Инициализируем массив counts
    for (int i = 0; i < counts.length; i++) {
        counts[i] = data.length;
         }

    return counts;
    }
    

    public static double[] calculateCoefficientOfVariation(double[][] data) {
        double[] means = calculateMean(data);
        double[] stdDevs = calculateStandardDeviation(data);
        double[] coefficientsOfVariation = new double[means.length];

        for (int i = 0; i < means.length; i++) {
            coefficientsOfVariation[i] = (stdDevs[i] / means[i]) * 100; // В процентах
        }
        return coefficientsOfVariation;
    }

    public static double[][] calculateConfidenceInterval(double[][] data, double confidenceLevel) {
        double[][] confidenceIntervals = new double[data[0].length][2]; // [0] - нижняя граница, [1] - верхняя граница
        double z = 1.96; // Для 95% доверительного интервала

        double[] means = calculateMean(data);
        double[] stdDevs = calculateStandardDeviation(data);
        int n = data.length;

        for (int i = 0; i < means.length; i++) {
            double marginOfError = z * (stdDevs[i] / Math.sqrt(n));
            confidenceIntervals[i][0] = means[i] - marginOfError; // Нижняя граница
            confidenceIntervals[i][1] = means[i] + marginOfError; // Верхняя граница
        }
        return confidenceIntervals;
    }

    public static double[] calculateVariance(double[][] data) {
        double[] variances = new double[data[0].length];
        for (int i = 0; i < variances.length; i++) {
            double mean = calculateMean(data)[i];
            double sum = 0;
            for (double[] row : data) {
                sum += Math.pow(row[i] - mean, 2);
            }
            variances[i] = sum / data.length; // Оценка дисперсии
        }
        return variances;
    }

    public static double[] calculateMax(double[][] data) {
    double[] maxValues = new double[data[0].length];
    for (int i = 0; i < maxValues.length; i++) {
        double max = Double.NEGATIVE_INFINITY; // Начальное значение
        for (double[] row : data) {
            if (row[i] > max) {
                max = row[i];
            }
        }
        maxValues[i] = max;
    }
    return maxValues;
}

    public static double[] calculateMin(double[][] data) {
    double[] minValues = new double[data[0].length];
    for (int i = 0; i < minValues.length; i++) {
        double min = Double.POSITIVE_INFINITY; // Начальное значение
        for (double[] row : data) {
            if (row[i] < min) {
                min = row[i];
            }
        }
        minValues[i] = min;
    }
    return minValues;
}
    

    
}

