/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steps.dev.datascience.ml.common.distance;

import steps.dev.datascience.ml.common.core.method.Algorithm;

/**
 *
 * @author stepin
 */
public class DistanceBasedAlgorithm extends Algorithm<DistanceBasedMethodParameters>{
    
    public DistanceBasedAlgorithm(DistanceBasedMethodParameters algorithmParameters, double empiricalRisk) {
        super(algorithmParameters, empiricalRisk);
    }
    
}
