/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steps.dev.datascience.ml.common.core.method;

import steps.dev.datascience.ml.common.core.data.ClusteringDataType;
import steps.dev.datascience.ml.common.core.parameters.MethodParameters;


/**
 *
 * @author stepin
 * 
 * 
 * @param <T> type of data in samples
 * @param <Theta> type of method parameters
 * @param <A> type of algorithm contaning certain parameters theta
 */
public interface ClusteringMethod <T extends ClusteringDataType, Theta extends MethodParameters, A extends Algorithm<Theta>> extends Method<T, Theta, A>{

}
