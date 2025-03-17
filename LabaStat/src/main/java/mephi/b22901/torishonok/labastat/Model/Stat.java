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

    // 1. Среднее геометрическое
    public static double geometricMean(double[] sample) {
        double product = 1.0;
        for (double num : sample) {
            product *= num;
        }
        return Math.pow(product, 1.0 / sample.length);
    }

    // 2. Среднее арифметическое
    public static double arithmeticMean(double[] sample) {
        double sum = 0.0;
        for (double num : sample) {
            sum += num;
        }
        return sum / sample.length;
    }

    // 3. Оценка стандартного отклонения
    public static double standardDeviation(double[] sample) {
        double mean = arithmeticMean(sample);
        double sum = 0.0;
        for (double num : sample) {
            sum += Math.pow(num - mean, 2);
        }
        return Math.sqrt(sum / (sample.length - 1));
    }

    // 4. Размах выборки
    public static double range(double[] sample) {
        double min = Arrays.stream(sample).min().orElse(Double.NaN);
        double max = Arrays.stream(sample).max().orElse(Double.NaN);
        return max - min;
    }

    // 5. Коэффициенты ковариации для всех пар
    public static double covariance(double[] sample1, double[] sample2) {
        if (sample1.length != sample2.length) {
            throw new IllegalArgumentException("Выборки должны быть одинаковой длины.");
        }
        double mean1 = arithmeticMean(sample1);
        double mean2 = arithmeticMean(sample2);
        double covariance = 0.0;
        for (int i = 0; i < sample1.length; i++) {
            covariance += (sample1[i] - mean1) * (sample2[i] - mean2);
        }
        return covariance / (sample1.length - 1);
    }

    // 6. Количество элементов
    public static int count(double[] sample) {
        return sample.length;
    }

    // 7. Коэффициент вариации
    public static double coefficientOfVariation(double[] sample) {
        return standardDeviation(sample) / arithmeticMean(sample);
    }

    // 8. Доверительный интервал для мат. ожидания
    public static double[] confidenceInterval(double[] sample, double confidenceLevel) {
        double mean = arithmeticMean(sample);
        double stdDev = standardDeviation(sample);
        double tScore = 1.96; // Для 95% доверительного интервала
        double marginError = tScore * stdDev / Math.sqrt(sample.length);
        return new double[]{mean - marginError, mean + marginError};
    }

    // 9. Оценка дисперсии
    public static double varianceEstimate(double[] sample) {
        double mean = arithmeticMean(sample);
        double sum = 0.0;
        for (double num : sample) {
            sum += Math.pow(num - mean, 2);
        }
        return sum / (sample.length - 1);
    }

    // 10. Максимумы и минимумы
    public static double[] minMax(double[] sample) {
        double min = Arrays.stream(sample).min().orElse(Double.NaN);
        double max = Arrays.stream(sample).max().orElse(Double.NaN);
        return new double[]{min, max};
    }

    //public static void main(String[] args) {
        //double[] sample1 = {2.3, 3.5, 1.8, 4.5, 2.8};
        //double[] sample2 = {1.3, 2.4, 2.5, 3.7, 1.9};

        //System.out.println("Среднее геометрическое: " + geometricMean(sample1));
        //System.out.println("Среднее арифметическое: " + arithmeticMean(sample1));
        //System.out.println("Стандартное отклонение: " + standardDeviation(sample1));
        //System.out.println("Размах: " + range(sample1));
        //System.out.println("Коэффициент ковариации: " + covariance(sample1, sample2));
        //System.out.println("Количество элементов: " + count(sample1));
        //System.out.println("Коэффициент вариации: " + coefficientOfVariation(sample1));
        //System.out.println("Доверительный интервал: " + Arrays.toString(confidenceInterval(sample1, 0.95)));
        //System.out.println("Оценка дисперсии: " + varianceEstimate(sample1));
        //System.out.println("Минимум и максимум: " + Arrays.toString(minMax(sample1)));
    //}
}

