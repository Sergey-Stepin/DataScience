/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steps.dev.datascience.ml.common.core.method;

import java.util.Comparator;
import steps.dev.datascience.ml.common.core.parameters.MethodParameters;
import lombok.Data;

/**
 *
 * @author stepin
 * @param <Theta>
 */

@Data
public class Algorithm<Theta extends MethodParameters>  {
    
    public static Comparator<Algorithm<? extends MethodParameters>> COMPARE_BY_EMPIRICAL_RISK = 
            (Algorithm<? extends MethodParameters> a1, Algorithm<? extends MethodParameters> a2) -> 
                    Double.compare(a1.getEmpiricalRisk(), a2.empiricalRisk);
    
    private Theta algorithmParameters; //optimized parameters for the algorithm
    private double empiricalRisk; // empirical risk for the optimized parameters 

    public Algorithm(Theta algorithmParameters, double empiricalRisk) {
        this.algorithmParameters = algorithmParameters;
        this.empiricalRisk = empiricalRisk;
    }
    
}
