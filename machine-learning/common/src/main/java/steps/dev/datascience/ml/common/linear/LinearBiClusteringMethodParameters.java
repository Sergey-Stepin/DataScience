/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steps.dev.datascience.ml.common.linear;

import steps.dev.datascience.linear.core.Vector;
import steps.dev.datascience.ml.common.core.parameters.MethodParameters;

/**
 *
 * @author stepin
 */
public class LinearBiClusteringMethodParameters implements MethodParameters{
    
    private final Vector wieghts; 

    public LinearBiClusteringMethodParameters(Vector wieghts) {
        this.wieghts = wieghts;
    }

    public Vector getWieghts() {
        return wieghts;
    }
    
}
