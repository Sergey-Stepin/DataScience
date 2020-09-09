/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steps.dev.datascience.ml.common.distance;

import steps.dev.datascience.ml.common.core.data.ClusteringDataType;
import steps.dev.datascience.linear.core.EuclideanSpaceElement;
import steps.dev.datascience.linear.core.Vector;

/**
 *
 * @author stepin
 */
public class DistanceClusteringData implements ClusteringDataType, EuclideanSpaceElement{

    private final Vector features;
    private final int cluster;

    public DistanceClusteringData(int dimensionNum, int cluster) {
        this.features = new Vector(dimensionNum);
        this.cluster = cluster;
    }

    public Vector getFeatures() {
        return features;
    }
    
    @Override
    public int getResponse() {
        return cluster;
    }

    @Override
    public double scalarProduct(EuclideanSpaceElement otherElement) {
        if( ! (otherElement instanceof DistanceClusteringData)){
            throw new RuntimeException("Both elementsmust be of the same class");
        }
        
        DistanceClusteringData otherDatum = (DistanceClusteringData) otherElement;
        
        if( features.getRowsNumber() != otherDatum.getFeatures().getRowsNumber() ){
            throw new RuntimeException("Both elements must have the same size");
        }
        
        return features.scalarProduct(otherDatum.features);
    }

    @Override
    public double distanceTo(EuclideanSpaceElement otherElement) {
        if( ! (otherElement instanceof DistanceClusteringData)){
            throw new RuntimeException("Both elementsmust be of the same class");
        }
        
        DistanceClusteringData otherDatum = (DistanceClusteringData) otherElement;
        
        if( features.getRowsNumber() != otherDatum.getFeatures().getRowsNumber() ){
            throw new RuntimeException("Both elements must have the same size");
        }
        
        return features.distanceTo(otherDatum.features);

    }
    
}
