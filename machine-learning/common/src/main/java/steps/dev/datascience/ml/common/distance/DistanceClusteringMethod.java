/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steps.dev.datascience.ml.common.distance;

import steps.dev.datascience.ml.common.distance.DistanceClusteringData;
import java.util.List;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import static steps.dev.datascience.ml.common.core.method.Algorithm.COMPARE_BY_EMPIRICAL_RISK;
import steps.dev.datascience.ml.common.core.method.ClusteringMethod;
import steps.dev.datascience.ml.common.core.parameters.CVParameters;
import steps.dev.datascience.ml.common.distance.Distance;
import steps.dev.datascience.ml.common.distance.DistanceBasedAlgorithm;
import steps.dev.datascience.ml.common.distance.DistanceBasedMethodParameters;

/**
 *
 * @author stepin
 *
 * The method is based on the optimisation by parameter k (number of the nearest
 * neighbours used for testing)
 *
 * @param <T> type of data
 * @param <A> type of algorithm contaning certain parameters
 */
public class DistanceClusteringMethod<T extends DistanceClusteringData> implements ClusteringMethod<T, DistanceBasedMethodParameters, DistanceBasedAlgorithm> {

    private final DoubleUnaryOperator parsenWindowKernel;
    private final boolean parallelTtraining;

    public DistanceClusteringMethod(DoubleUnaryOperator parsenWindowKernel, boolean parallelTtraining) {
        this.parsenWindowKernel = parsenWindowKernel;
        this.parallelTtraining = parallelTtraining;
    }

    @Override
    public <P extends CVParameters> DistanceBasedAlgorithm train(final List<T> trainingSample, P cvParameters) {

        //k must be < than the sample element size L
        //beacuse one element u must be excluded from the sample
        //and it cannot have more than L-1 nearest neighbours
        //parsen window based on k + 1 nearest neighbour distance
        IntStream stream;
        if (parallelTtraining) {
            stream = IntStream.range(1, trainingSample.size() - 1).parallel();
        } else {
            stream = IntStream.range(1, trainingSample.size() - 1);
        }

        System.out.println("=====================================================");
        //optimazi the empirical risk by k
        //thus: for each k train an algorithm
        //then choose among the receiving algorithms the one with the minimum empirical risk
        return stream
                .mapToObj(k -> trainForK(trainingSample, k))
                .min(COMPARE_BY_EMPIRICAL_RISK)
                .get();
    }

    //train an algorithm using k neares neighbours
    private DistanceBasedAlgorithm trainForK(final List<T> sample, int k) {

        //Not using general empirical risk class for simplification and acceleration of the algorithm
        double empiricalRisk = sample
                .parallelStream()
                //for each element in the sample get the assessed cluster 
                //and calculate losses
                .mapToDouble(u -> calculateLosses(u, sample, k))
                //then calculate empirical risk
                //as average losses over the sample for the given k
                .average()
                .getAsDouble();

        DistanceBasedAlgorithm a = new DistanceBasedAlgorithm(
                new DistanceBasedMethodParameters(k),
                empiricalRisk);
        
        System.out.println("k=" + k + " \t Er=" + empiricalRisk);
        
        return a;

    }

    private double calculateLosses(T u, List<T> sample, int k) {
        int essesesCluster = test(u, sample, k);
        return lossFunction(essesesCluster, u.getResponse());
    }

    private double lossFunction(int essesment, int fact) {
        if (essesment == fact) {
            return 0.0d;
        } else {
            return 1.0d;
        }

    }

    //@Override
    //public T test(T u, List<T> sample, A algorithm) {        
    public int test(T u, List<T> sample, int k) {

        //take k nearest neighbours
        //final int k = algorithm.getAlgorithmParameters().getK();
        if (k < 1) {
            throw new IllegalArgumentException("Number of the nearest neighbours k < 1 ");
        }

        //sort the sample elements by their distance to element u
        List<Distance<T>> sortedDistances = sample
                .parallelStream()
                .filter(x -> x != u)
                .map(x -> new Distance<>(x, x.distanceTo(u)))
                .sorted()
                .collect(Collectors.toList());

        //!!! Important note
        //k must be < than the sample element size L
        //beacuse one element u must be excluded from the sample
        //and it cannot have more than L-1 nearest neighbours
        //parsen window based on k + 1 nearest neighbour distance
        final double h = sortedDistances.get(k).getDistanceValue();
        if (h == 0.0d) {
            throw new ArithmeticException(" Parsen window h = 0.0 ");
        }

        Map<Integer, Double> clustersClosenesses = sortedDistances
                .parallelStream()
                //Take no more than k nearest elements
                .limit(k)
                //groupe k neighbours by thier cluster
                //and summurize weighted closeneses of the neighbours in the the respective clusters
                .collect(Collectors.groupingBy((Distance<T> distance) -> distance.getOtherElement().getResponse(),
                        Collectors.summingDouble((Distance<T> distance) -> neighbourWeight(distance, h))));

        //choose the cluster with the maximum closenes
        //int cluster = clustersClosenesses
        return clustersClosenesses
                .entrySet()
                .parallelStream()
                .max(this::compareClustersCloseneses)
                .get()
                .getKey();

        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private double neighbourWeight(Distance<T> distance, double h) {
        return parsenWindowKernel.applyAsDouble(distance.getDistanceValue() / h);
    }

    private int compareClustersCloseneses(Map.Entry<Integer, Double> clusterEntry1, Map.Entry<Integer, Double> clusterEntry2) {
        return clusterEntry1.getValue().compareTo(clusterEntry2.getValue());
    }

}
