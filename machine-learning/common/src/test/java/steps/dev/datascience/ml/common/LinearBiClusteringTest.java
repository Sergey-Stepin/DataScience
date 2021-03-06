/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steps.dev.datascience.ml.common;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import steps.dev.datascience.ml.common.core.method.LossFunctions;
import steps.dev.datascience.ml.common.linear.LinearBiClusteringAlgorithm;
import steps.dev.datascience.ml.common.linear.LinearBiClusteringData;
import steps.dev.datascience.ml.common.linear.StochasticGradientMethod;

/**
 *
 * @author stepin
 *
 */
public class LinearBiClusteringTest {

    private final static int N = 1_000;
    private static final double PRECISION = 10E-9;

    private static final Random RND = new Random();

    private static final StochasticGradientMethod<LinearBiClusteringOneDimData> method
            = new StochasticGradientMethod<>(1, LossFunctions.HEBB_RULE);

    public class LinearBiClusteringOneDimData extends LinearBiClusteringData {

        public LinearBiClusteringOneDimData(int dimensionNum, int cluster) {
            super(dimensionNum, cluster);
        }
    }

    public static class BiDistribution {

        final double sd1;
        final double sd2;
        final double x1;
        final double x2;
        final double[] disributionProbabilities;

        public BiDistribution(double x1, double sd1, double x2, double sd2, double[] disributionProbabilities) {
            this.sd1 = sd1;
            this.sd2 = sd2;
            this.x1 = x1;
            this.x2 = x2;
            this.disributionProbabilities = disributionProbabilities;
        }

        public double getSd1() {
            return sd1;
        }

        public double getSd2() {
            return sd2;
        }

        public double getX1() {
            return x1;
        }

        public double getX2() {
            return x2;
        }

        public double[] getDisributionProbabilities() {
            return disributionProbabilities;
        }

    }
    //@Disabled

    @Test
    @DisplayName("LinearBiClusteringTest. LossFunctions=HEBB_RULE. Two gaussian clusters 50/50:")
    public void twoSimpleClusters(TestInfo testInfo) {

        final double sd1 = 100;
        final double x1 = -sd1;
        final double sd2 = 2 * sd1;
        final double x2 = sd2;
        double[] disributionProbabilities = {0.5d, 0.5d};
        BiDistribution distribution = new BiDistribution(x1, sd1, x2, sd2, disributionProbabilities);

        twoSimpleClusters(distribution);
    }

    private void twoSimpleClusters(BiDistribution distribution) {

        List<LinearBiClusteringOneDimData> data = IntStream.range(0, N)
                .mapToObj(i -> new LinearBiClusteringOneDimData(1, AsymetricRandom.nextInt(distribution.getDisributionProbabilities())))
                .peek(datum -> setGaussianValue(datum, distribution))
                .collect(Collectors.toList());

        System.out.println("----- 50/50 -----------------");

        Map<Integer, Long> map = data.stream()
                .collect(Collectors.groupingBy(LinearBiClusteringOneDimData::getResponse, Collectors.counting()));
        System.out.println("clusters:");
        map.entrySet()
                .stream()
                .forEach(entry -> System.out.println("c: " + entry.getKey() + " \t num=" + entry.getValue()));

        System.out.println("checkX1:");
        System.out.println("mean1=" + data
                .stream()
                .filter(datum -> datum.getResponse() == 0)
                .mapToDouble(datum -> datum.getFeatures().get(0))
                .average()
                .getAsDouble());
        double var1 = data
                .stream()
                .filter(datum -> datum.getResponse() == 0)
                .mapToDouble(datum -> Math.pow((distribution.getX1() - datum.getFeatures().get(0)), 2))
                .sum() / (map.get(0) - 1);
        System.out.println("sd1=" + Math.sqrt(var1));

        System.out.println("checkX2:");
        System.out.println("mean2=" + data
                .stream()
                .filter(datum -> datum.getResponse() == 1)
                .mapToDouble(datum -> datum.getFeatures().get(0))
                .average()
                .getAsDouble());
        double var2 = data
                .stream()
                .filter(datum -> datum.getResponse() == 1)
                .mapToDouble(datum -> Math.pow((distribution.getX2() - datum.getFeatures().get(0)), 2))
                .sum() / (map.get(1) - 1);
        System.out.println("sd2=" + Math.sqrt(var2));

        LinearBiClusteringAlgorithm optimizedAlgorithm = method.train(data, null);
        System.out.println("=================================================");
        System.out.println("=================================================");
        System.out.println("=================================================");
    }

    public static void setGaussianValue(LinearBiClusteringOneDimData datum, BiDistribution distribution) {
        int cluster = datum.getResponse();

        switch (cluster) {
            case 0:
                datum.getFeatures()
                        .set(0, nexGaussian(distribution.getX1(), distribution.getSd1()));
                break;

            case 1:
                datum.getFeatures()
                        .set(0, nexGaussian(distribution.getX2(), distribution.getSd2()));
                break;

            default:
                throw new IllegalArgumentException("Expecetd clusters for the test {0,1} but received:" + cluster);
        }

    }

    public static double nexGaussian(double mean, double standatdDeviation) {
        return mean + RND.nextGaussian() * standatdDeviation;
    }

}
