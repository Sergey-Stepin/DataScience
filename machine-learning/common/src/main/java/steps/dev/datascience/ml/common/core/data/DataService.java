/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steps.dev.datascience.ml.common.core.data;

import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 *
 * @author stepin
 * @param <T> type of data in samples
  */
public interface DataService <T extends DataType>{
    
    //public DataSamples <T> getDataSamples();
    public List<T> getAllData();
    
    public static <T extends DataType> DataSamples <T> getRandomDivision(DataService<T> dataService){
        Map<Boolean, List<T>> randomDivision = dataService
                .getAllData()
                .parallelStream()
                .collect(Collectors.partitioningBy((T t) -> DataSamples.getRandom().nextBoolean()));
        
        return new DataSamples<>(
                randomDivision.get(true),
                randomDivision.get(false));
        
    }
}
