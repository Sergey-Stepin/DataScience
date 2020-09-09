/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steps.dev.datascience.function.core;

import java.util.function.DoubleUnaryOperator;

/**
 *
 * @author stepin
 */

public class MathFunction {
    
    private final DoubleUnaryOperator function;
    private final DoubleUnaryOperator derivative; 

    public MathFunction(DoubleUnaryOperator function, DoubleUnaryOperator derivative) {
        this.function = function;
        this.derivative = derivative;
    }

    public DoubleUnaryOperator getFunction() {
        return function;
    }

    public DoubleUnaryOperator getDerivative() {
        return derivative;
    }
    
}
