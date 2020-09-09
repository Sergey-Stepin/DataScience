/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steps.dev.datascience.ml.common.core.method;

import java.util.List;
import java.util.stream.Stream;
import steps.dev.datascience.ml.common.core.data.DataType;
import steps.dev.datascience.ml.common.core.parameters.MethodParameters;

/**
  * @author stepin
  */
public interface EmpiricalRisk {
    
    //Calculates empirical risk
    //as average loss function over all data from the given sample
    //applying an algorithm (which includes a certain parameter theta) of a given method
    public static <T extends DataType, Theta extends MethodParameters, A extends Algorithm<Theta>> double calculate(
            List<T> sample,
            A algorithm,
            final Method<T, Theta, A> method,
            //ToDoubleBiFunction<T, Method<T>> lossFunction,
            LossFunction <T> lossFunction,
            boolean parallel){
        
        Stream<T> stream;
        if (parallel) {
            stream = sample.parallelStream();
        } else {
            stream = sample.stream();
        }
        
        return stream
                
                //apply the algorithm (which includes a certain parameter theta) of a given method
                //to a datum
                .mapToDouble(datum -> lossFunction.apply(datum, method, algorithm))
                
                //averave the loss function over all data from the sample
                .average()
                .getAsDouble();
        
        //throw new RuntimeException("Not supported yet");
    }
    
}
