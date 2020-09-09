/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steps.dev.datascience.ml.common.core.method;

import steps.dev.datascience.function.core.MathFunction;


/**
 *
 * @author stepin
 */
public interface LossFunctions {
    
    public static MathFunction SVM = new MathFunction(
            m -> m <= 1.0 ? 1.0d - m : 0.0d,    //function
            m -> m <= 1.0 ? -1.0d : 0.0d);      //derivative
    
    public static MathFunction  HEBB_RULE = new MathFunction(
            m -> m <= 0.0 ? -m : 0.0d,      //function
            m -> m <= 0.0 ? -1.0d : 0.0d);  //derivative
    
}
