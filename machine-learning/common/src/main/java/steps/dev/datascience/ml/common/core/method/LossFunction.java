/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steps.dev.datascience.ml.common.core.method;

import steps.dev.datascience.ml.common.core.data.DataType;
import steps.dev.datascience.ml.common.core.parameters.MethodParameters;

/**
 *
 * @author stepin
 * @param <T>
 */

@FunctionalInterface
public interface LossFunction <T extends DataType>{
    
    //Assesses a loss of the given algorithm (which includes a certain parameter theta) of a given method
    //at a certain datum 
    public <Theta extends MethodParameters, A extends Algorithm<Theta>> double apply(
            T datum, 
            final Method<T, Theta, A> method,
            A algorithm);
}
