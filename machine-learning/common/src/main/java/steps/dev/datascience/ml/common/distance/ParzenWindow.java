/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steps.dev.datascience.ml.common.distance;

import java.util.function.DoubleUnaryOperator;

/**
 *
 * @author stepin
 */
public interface ParzenWindow {
    
    public static final DoubleUnaryOperator TRIANGLE_KERNEL  = (double arg) -> {
        if(arg >= 1 || arg < 0){
            return 0.0d;
        }else{
            return 1 - arg;
        }
    };
    
}
