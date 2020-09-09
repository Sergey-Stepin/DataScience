/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steps.dev.datascience.ml.common.core.data;

import java.util.List;
import java.util.Random;

/**
 *
 * @author stepin
 * @param <T> type of data in samples* 
 */

public class DataSamples <T extends DataType>{
    
    private final static Random random = new Random();
    
    private final List<T> trainingSample;
    private final List<T> validatingSample;

    public DataSamples(List<T> trainingSample, List<T> validatingSample) {
        this.trainingSample = trainingSample;
        this.validatingSample = validatingSample;
    }

    public static Random getRandom() {
        return random;
    }

    public List<T> getTrainingSample() {
        return trainingSample;
    }

    public List<T> getValidatingSample() {
        return validatingSample;
    }
    
}
