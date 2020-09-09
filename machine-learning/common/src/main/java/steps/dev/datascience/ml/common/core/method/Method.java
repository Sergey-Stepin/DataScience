/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steps.dev.datascience.ml.common.core.method;

import java.util.List;
import steps.dev.datascience.ml.common.core.data.DataType;
import steps.dev.datascience.ml.common.core.data.DataService;
import steps.dev.datascience.ml.common.core.parameters.CVParameters;
import steps.dev.datascience.ml.common.core.parameters.MethodParameters;

/**
 * @author stepin
 * 
 * Defines a method of machine learning
 * 
 * @param <T> type of data in samples
 * @param <Theta> type of method parameters
 * @param <A> type of algorithm than contains certain method parameters
 */

public interface Method <T extends DataType, Theta extends MethodParameters, A extends Algorithm<Theta>>{
//public interface Method <T extends DataType, P extends CVParameters>{
    
    public <P extends CVParameters> A train(List<T> trainingSample, P cvParameters);
    
    //public T test(T datum, List<T> sample, A algorithm);
    
}
