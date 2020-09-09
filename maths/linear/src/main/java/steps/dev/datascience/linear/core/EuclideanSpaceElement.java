/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steps.dev.datascience.linear.core;

/**
 *
 * @author papa
 */
public interface EuclideanSpaceElement {
    
    /**
    * @param otherElement - other element in the Euclidean space
    * @return scalar product of the elements
    */
    
    public double scalarProduct(EuclideanSpaceElement otherElement);
    //public double distanceSquareTo(EuclideanSpaceElement otherElement);
    public double distanceTo(EuclideanSpaceElement otherElement);
    
}

