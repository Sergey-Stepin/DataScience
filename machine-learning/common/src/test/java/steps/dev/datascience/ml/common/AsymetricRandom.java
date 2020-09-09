/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steps.dev.datascience.ml.common;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;
import steps.dev.datascience.linear.core.EuclideanSpaceElement;
import steps.dev.datascience.ml.common.distance.DistanceClusteringData;

/**
 *
 * @author stepin
 *
 */

@Disabled
public class AsymetricRandom {

    private final static Random RND = new Random();
    private final static int N = 1000;
    private static final double PRECISION = 10E-9;

    @Test
    @DisplayName("50/50:")
    public void testAsymetricRandom_50_50(TestInfo testInfo) {
        
        double[] disribution = {0.5d, 0.5d};
        
        System.out.println("--------- 50/50 --------------");
        Map<Integer, Long> map = IntStream.range(0, N)
                .map(i -> nextInt(disribution))
                .boxed()
                .collect(Collectors.groupingBy(Function.identity(),
                        Collectors.counting()));
        System.out.println("------------");
        map.entrySet()
                .stream()
                .forEach(entry -> System.out.println("c: " + entry.getKey() + " \t num=" + entry.getValue()));
    }    

    @Test
    @DisplayName("20/70/10:")
    public void testAsymetricRandom_20_70_10(TestInfo testInfo) {
        
        double[] disribution = {0.2d, 0.7d, 0.1d};
        
        System.out.println("--------- 20/70/10 --------------");
        Map<Integer, Long> map = IntStream.range(0, N)
                .map(i -> nextInt(disribution))
                .boxed()
                .collect(Collectors.groupingBy(Function.identity(),
                        Collectors.counting()));
        System.out.println("------------");
        map.entrySet()
                .stream()
                .forEach(entry -> System.out.println("c: " + entry.getKey() + " \t num=" + entry.getValue()));
    }    

    @Test
    @DisplayName("40/5/50/5")
    public void testAsymetricRandom_40_5_50_5(TestInfo testInfo) {
        
        double[] disribution = {0.4d, 0.05d, 0.5d, 0.05d};
        
        System.out.println("--------- 40/5/50/5 --------------");
        Map<Integer, Long> map = IntStream.range(0, N)
                .map(i -> nextInt(disribution))
                .boxed()
                .collect(Collectors.groupingBy(Function.identity(),
                        Collectors.counting()));
        System.out.println("------------");
        map.entrySet()
                .stream()
                .forEach(entry -> System.out.println("c: " + entry.getKey() + " \t num=" + entry.getValue()));
    }    
    
    //returns a andom int 
    //disribution - array presenting the probability distribution
    //Excamples:
    //disribution = {0.5, 0.5}; return either 0 or 1 with probability 50% / 50%
    //disribution = {0.3, 0.5, 0.2}; return either 0 or 1 or 2 with probability 30% / 50% / 20% respectively
    //note: sum of disribution must be = 1
    public static int nextInt(double[] disribution) {

        double disributionArraySum = IntStream.range(0, disribution.length)
                .mapToDouble(i -> disribution[i])
                .sum();
        
        if ( Math.abs(disributionArraySum - 1.0d) > PRECISION) {
            throw new IllegalArgumentException("The sum of elements in the disribution array must be 1.0 ");
        }

        final double randomValue = RND.nextDouble();
        double cummulativeProbability = 0.0d;
        for (int i = 0; i < disribution.length; i ++){
            
            cummulativeProbability += disribution[i];
            if(randomValue < cummulativeProbability){
                return i;
            }
        }
        
        return -1;
    }

}
