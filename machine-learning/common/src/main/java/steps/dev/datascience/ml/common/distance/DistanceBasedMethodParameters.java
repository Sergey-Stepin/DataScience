/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steps.dev.datascience.ml.common.distance;

import steps.dev.datascience.ml.common.core.parameters.MethodParameters;

/**
 *
 * @author stepin
 */
public class DistanceBasedMethodParameters implements MethodParameters{
    private int k;

    public DistanceBasedMethodParameters(int k) {
        this.k = k;
    }

    public int getK() {
        return k;
    }
}
