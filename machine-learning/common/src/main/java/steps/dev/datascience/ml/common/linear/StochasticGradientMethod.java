/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steps.dev.datascience.ml.common.linear;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import steps.dev.datascience.function.core.MathFunction;
import steps.dev.datascience.linear.core.Vector;
import steps.dev.datascience.ml.common.core.method.ClusteringMethod;
import steps.dev.datascience.ml.common.core.parameters.CVParameters;

/**
 *
 * @author stepin
 */
public class StochasticGradientMethod<T extends LinearBiClusteringData> implements ClusteringMethod<T, LinearBiClusteringMethodParameters, LinearBiClusteringAlgorithm> {

    private static final Random RND = new Random();
    
    private final double lambda = 50;
    private final int presempleSize = 1000;
    private final int weightsSize;
    
    private final int relativeEmpiricalRiskNumber = 30;    
    private final double empiricalRiskStabilizationValue = 0.05d; 
    private final double[] relativeEmpiricalRisks = new double[relativeEmpiricalRiskNumber]; //in persantage to previous
    
    private final int relativeWeightsNumber = 30;    
    private final double weightsNormStabilizationValue = 0.05d; 
    private final double[] relativeWeightsNorms = new double[relativeWeightsNumber]; //in persantage to previous
    
    private final MathFunction lossFunction;

    private Vector weights;
    private double weightsNorm;
    private double previousWeightsNorm;
    
    private double empiricalRisk;
    private double previousEmpiricalRisk;

    public StochasticGradientMethod(int weightsSize, MathFunction lossFunction) {
        this.weightsSize = weightsSize;
        this.lossFunction = lossFunction;
    }

    @Override
    public <P extends CVParameters> LinearBiClusteringAlgorithm train(List<T> trainingSample, P cvParameters) {
        //LinearBiClusteringMethodParameters arams = new LinearBiClusteringMethodParameters(initWeights(trainingSample));

        //prepare pre sample
        List<T> presample = RND.ints(presempleSize, 0, trainingSample.size())
                .mapToObj(i -> trainingSample.get(i))
                .collect(Collectors.toList());

        //initiate method parameters
        weights = initWeights(trainingSample);
        empiricalRisk = initEmpiticalRisk(presample);

        //learn until stabilization of method parameters (Q, ||w||, ...)
        //which means success in learning
        //or until data end
        //which means fail in learning
        for (int i = 0; i < trainingSample.size(); i ++){
            
            nextStep(nextDatum(i, trainingSample));
            
            if(areObservablesStabilized(i)){
                break;
            }
        }
        
        return new LinearBiClusteringAlgorithm(new LinearBiClusteringMethodParameters(weights), empiricalRisk);
        
    }

    //initiate weigths
    private Vector initWeights(List<T> presample) {
        
        //calculate initial weights
        Vector w = new Vector(weightsSize);
        IntStream.rangeClosed(0, weightsSize)
                .forEach(j -> w.set(j, calculateWieght(j, presample)));
        
        return w;
    }
    
    private double calculateWieght(int j, List<T> presample){
        
        //calculate <fj , y>
        double scalar = IntStream.rangeClosed(0, presample.size())
                .mapToDouble(i -> 
                        presample.get(i).getFeatures().get(j) * presample.get(i).getResponse())
                .sum();
        
        //calculate <fj , fj>
        double norm = IntStream.rangeClosed(0, presample.size())
                .mapToDouble(i -> 
                        presample.get(i).getFeatures().get(j) * presample.get(i).getFeatures().get(j))
                .sum();
        
        // <fj , y> / <fj , fj>
        return scalar/norm;
    }

    //calculate initial ermiricak risk estimation
    private double initEmpiticalRisk(List<T> presample) {
        return presample.stream()
                .mapToDouble(datum -> margin(
                        weights, 
                        datum.getFeatures(), 
                        datum.getResponse()))
                .average()
                .getAsDouble();
    }
    
    private double margin(Vector w, Vector x, double y){
        return y * x.scalarProduct(weights);
    }
    
    private T nextDatum(int i, List<T> trainingSample){
        return trainingSample.get(i);
    }

    private void nextStep(T datum) {

        final Vector x = datum.getFeatures();
        final double y = (double) datum.getResponse();
        final double eta = x.norm();

        double dL = lossFunction
                .getDerivative()
                .applyAsDouble(margin(weights, x, y));
                
        //make a step and recalculate weights
        IntStream.range(0, weights.getRowsNumber())
                .forEach(j -> 
                        weights.set(
                                j, 
                                newWeight(weights.get(j),eta, dL, x.get(j), y)));
        
        double loss = lossFunction
                .getFunction()
                .applyAsDouble(margin(weights, x, y));
        
        empiricalRisk = (1 - lambda) * empiricalRisk + lambda * loss;
        weightsNorm = weights.norm();

    }
    
    private double newWeight(double w, double eta, double dL, double xj, double y){
        return w - eta * dL * xj * y;
    }
    
    private void shiftArray(double [] arr,  double newValue){
        for(int i = 0; i < arr.length - 1; i ++){
            arr[i] = arr[i + 1];
        }
        arr[arr.length] = newValue;
    }
    
    private double relativeValue(double previusValue, double currentValue){
        if(previusValue == 0.0d){
            return 1.0d;
        }else {
            return currentValue/previusValue - 1.0d;
        }
    }
    
    private boolean areObservablesStabilized(int i){
        
        //update observables
        shiftArray(relativeEmpiricalRisks, relativeValue(previousEmpiricalRisk, empiricalRisk));
        shiftArray(relativeWeightsNorms, relativeValue(previousWeightsNorm, weightsNorm));
        
        //check relativeEmpiricalRisk stabilisation
        double averageRelativeEmpiricalRisk = DoubleStream.of(relativeEmpiricalRisks)
                .average()
                .getAsDouble();
        
        //check relativeWeigntsNorm stabilisation        
        double averagerelativeWeigntsNorm = DoubleStream.of(relativeWeightsNorms)
                .average()
                .getAsDouble();
        
        if(
                averageRelativeEmpiricalRisk < empiricalRiskStabilizationValue
                || averagerelativeWeigntsNorm < weightsNormStabilizationValue){
            
            return true;
        }
        
        previousEmpiricalRisk = empiricalRisk;
        previousWeightsNorm = weightsNorm;
        
        return false;
        
    }
    
}
