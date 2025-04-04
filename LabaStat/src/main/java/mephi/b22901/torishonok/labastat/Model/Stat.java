/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.torishonok.labastat.Model;

import java.util.HashMap;
import java.util.List;
import org.apache.commons.math3.distribution.TDistribution;
import org.apache.commons.math3.linear.MatrixUtils;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.StatUtils;
import org.apache.commons.math3.stat.correlation.Covariance;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.apache.commons.math3.stat.descriptive.moment.GeometricMean;

/**
 *
 * @author vikus
 */
public class Stat {
    
    private double[] gMean;
    private double[] aMean;
    private double[] stDeav;
    private double[] range;
    private double[] elNumber;
    private double[][] confInterval;
    private double[] var;
    private double[] max;
    private double[] min;
    private double[] varCoef;
    private RealMatrix covMatrix;
    
    
    public Stat(List<List<Double>> data) {
        int columns = data.get(0).size();
        int rows = data.size();
        gMean = new double[columns];
        aMean = new double[columns];
        stDeav = new double[columns];
        range = new double[columns];
        elNumber = new double[columns];
        confInterval = new double[columns][2];
        var = new double[columns];
        max = new double[columns];
        min = new double[columns];
        varCoef = new double[columns];

        RealMatrix matForCov = MatrixUtils.createRealMatrix(rows, columns);

        for (int j = 0; j < columns; j++) {
            DescriptiveStatistics stats = new DescriptiveStatistics();
            double[] temp = new double[rows];
            for (int i = 0; i < rows; i++) {
                temp[i] = data.get(i).get(j);
                stats.addValue(data.get(i).get(j));
            }
            matForCov.setColumn(j, temp);

            gMean[j] = new GeometricMean().evaluate(temp);
            aMean[j] = stats.getMean();
            stDeav[j] = stats.getStandardDeviation();
            range[j] = stats.getMax() - stats.getMin();
            elNumber[j] = stats.getN();
            var[j] = stats.getVariance();
            max[j] = stats.getMax();
            min[j] = stats.getMin();
            varCoef[j] = stDeav[j] / Math.abs(aMean[j]) * 100;

            double confLvl = 0.95;
            double alpha = 1 - confLvl;
            TDistribution tDist = new TDistribution(elNumber[j] - 1);
            double tValue = tDist.inverseCumulativeProbability(1 - alpha / 2);

            double marginOfError = tValue * (stDeav[j] / Math.sqrt(elNumber[j]));
            confInterval[j][0] = aMean[j] - marginOfError;
            confInterval[j][1] = aMean[j] + marginOfError;
        }
        Covariance cov = new Covariance(matForCov);
        covMatrix = cov.getCovarianceMatrix();

    }

    public HashMap<String, double[]> returnData() {
        HashMap<String, double[]> data = new HashMap<>();
        data.put("GeoMean", gMean);
        data.put("ArMean", aMean);
        data.put("StDev", stDeav);
        data.put("Range", range);
        data.put("Count", elNumber);
        data.put("Var", var);
        data.put("Max", max);
        data.put("Min", min);
        data.put("VarCoef", varCoef);
        return data;
    }

    public double[][] returnConfInterval() {
        return confInterval;
    }

    public RealMatrix returnCovariance() {
        return covMatrix;
    }


}


