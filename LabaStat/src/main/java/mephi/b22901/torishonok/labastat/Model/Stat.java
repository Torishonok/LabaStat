/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package mephi.b22901.torishonok.labastat.Model;

import org.apache.commons.math3.stat.StatUtils;

/**
 *
 * @author vikus
 */
public class Stat {
    
    private double[] data;

    public Stat(double[] data) {
        this.data = data;
    }

    public double calculateMean() {
        return StatUtils.mean(data);
    }

    public double calculateGeometricMean(){
        return StatUtils.geometricMean(data);
    }

    public double calculateStandardDeviation() {
       return Math.sqrt(StatUtils.variance(data));
    }

    public double calculateMax() {
        return StatUtils.max(data);
    }

    public double calculateMin() {
        return StatUtils.min(data);
    }
    
    public double calculateRange(){
        return StatUtils.max(data)-StatUtils.min(data);
    }
}


