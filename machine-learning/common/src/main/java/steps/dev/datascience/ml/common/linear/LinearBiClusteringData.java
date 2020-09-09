/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steps.dev.datascience.ml.common.linear;

import steps.dev.datascience.ml.common.core.data.ClusteringDataType;
import steps.dev.datascience.linear.core.EuclideanSpaceElement;
import steps.dev.datascience.linear.core.Vector;

/**
 *
 * @author stepin
 */
public class LinearBiClusteringData implements ClusteringDataType{

    private final Vector features;
    private final int cluster;

    public LinearBiClusteringData(int dimensionNum, int cluster) {
        super();
        this.features = new Vector(dimensionNum);
        
        if(cluster != 1 || cluster != -1 ){
            throw new IllegalArgumentException(" Linear clustering data response must be in {-1, +1}, received:" + cluster);
        }
        this.cluster = cluster;
    }

    public Vector getFeatures() {
        return features;
    }
    
    @Override
    public int getResponse() {
        return cluster;
    }

}
