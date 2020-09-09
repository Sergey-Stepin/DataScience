/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steps.dev.datascience.ml.common.linear;

import steps.dev.datascience.ml.common.distance.*;
import steps.dev.datascience.ml.common.core.method.Algorithm;

/**
 *
 * @author stepin
 */
public class LinearBiClusteringAlgorithm extends Algorithm<LinearBiClusteringMethodParameters>{
    
    public LinearBiClusteringAlgorithm(LinearBiClusteringMethodParameters algorithmParameters, double empiricalRisk) {
        super(algorithmParameters, empiricalRisk);
    }
}
