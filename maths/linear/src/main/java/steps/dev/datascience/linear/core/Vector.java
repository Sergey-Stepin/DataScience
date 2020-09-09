/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steps.dev.datascience.linear.core;

import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 *
 * @author papa
 */
public class Vector extends Matrix implements EuclideanSpaceElement {

    public Vector(int rowsNumber) {
        super(rowsNumber, 1);
    }

    public Vector(double[] fromArray) {
        super(fromArray.length, 1);
        for (int i = 0; i < fromArray.length; i++) {
            set(i, fromArray[i]);
        }
    }

    public Vector(int rowsNumber, double value) {
        super(rowsNumber, 1, value);
    }

    public Vector(Vector other) {
        super(other.getRowsNumber(), 1);
        IntStream.iterate(0, i -> i + 1)
                .limit(other.getRowsNumber())
                .parallel()
                .forEach(i -> {
                    set(i, other.get(i));
                });
    }

    public Vector(List<Double> values){
        super(values.size(), 1);
        
        IntStream.iterate(0, i -> i++)
                .limit(values.size())
                .forEach(i -> set(i, values.get(i)));
    }
    
    public double get(int row) {
        return get(row, 0);
    }

    public void set(int row, double value) {
        set(row, 0, value);
    }

    @Override
    public int getColumnsNumber() {
        return 1;
    }

    @Override
    public double squareOfNorm() {
        return Stream.of(array)
                .mapToDouble((double[] column) -> column[0] * column[0])
                .sum();
    }

    /**
     *
     * @param otherElement - other vector to product
     * @return scalar product of the vectors
     */
    @Override
    public double scalarProduct(EuclideanSpaceElement otherElement) {
        if (!(otherElement instanceof Vector)) {
            throw new IllegalArgumentException("Both elements must be vectors. ");
        }
        Vector otherVector = (Vector) otherElement;
        
        return this.transpose().product(otherVector).get(0, 0);
    }

    //@Override
    public double distanceSquareTo(EuclideanSpaceElement otherElement) {
        if (!(otherElement instanceof Vector)) {
            throw new IllegalArgumentException("Both elements must be vectors. ");
        }
        Vector otherVector = (Vector) otherElement;
        
        return IntStream.iterate(0, i -> i + 1)
                .limit(this.getRowsNumber())
                .mapToDouble(i
                        -> (this.get(i) - otherVector.get(i)) * (this.get(i) - otherVector.get(i)))
                .sum();
    }

    @Override
    public double distanceTo(EuclideanSpaceElement otherElement) {
        return Math.sqrt(distanceSquareTo(otherElement));
    }
    
}
