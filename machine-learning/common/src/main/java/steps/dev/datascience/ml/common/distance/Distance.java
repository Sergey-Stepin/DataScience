/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steps.dev.datascience.ml.common.distance;

import steps.dev.datascience.linear.core.EuclideanSpaceElement;


/**
 *
 * @author stepin
 * @param <T>
 */
public class Distance <T extends EuclideanSpaceElement> implements Comparable<Distance<T>>{
    
    private final double distanceValue;
    private final T otherElement;

    public Distance(T datum, double value) {
        this.distanceValue = value;
        this.otherElement = datum;
    }
    
    @Override
    public String toString(){
        return " Distance " + distanceValue;
    }

    @Override
    public int compareTo(Distance<T> other) {
        
        if(other == null){
            return -1;
        }
        
        if (distanceValue > other.distanceValue){
            return 1;
        } else if (distanceValue < other.distanceValue){
            return -1;
        }
        return 0;
    }

    public double getDistanceValue() {
        return distanceValue;
    }

    public T getOtherElement() {
        return otherElement;
    }
}
