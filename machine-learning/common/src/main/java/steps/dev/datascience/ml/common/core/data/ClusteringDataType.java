/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steps.dev.datascience.ml.common.core.data;


/**
 *
 * @author stepin
 */
public interface ClusteringDataType extends DataType{
    
    //return cluster number of the datum
    public int getResponse();
}
