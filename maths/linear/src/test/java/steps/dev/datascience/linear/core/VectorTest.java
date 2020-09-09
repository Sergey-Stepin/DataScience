/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steps.dev.datascience.linear.core;

import java.util.Random;
import java.util.stream.IntStream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;


/**
 *
 * @author stepin
 */
public class VectorTest {
    
    private static final int MAX_SIZE = 100;
    private static final double PRECISION = 0.0000000001;    
    
    private static final Vector vectorA = new Vector(new double[]{-296.2386600, -1, 896.232485, 0, -998.89349, 469.978761, -597.015969, 712.153097, -154.524093, 539.507225});
    private static final Vector vectorB = new Vector(new double[]{-310.3548570, 201.550187, 535.667331, 0, 1, -461.675325, 994.507768, -558.91306, 0, -869.733117});
    
    @Test
    void consrtuct(){
        
        //try to create a wrong vector
        assertThrows(IllegalArgumentException.class, () -> {new Vector(0);});
        
        //try to create correct vectors
       
        int n = 5;
        Vector vector1 = new Vector(n);
        fillInWithRandomValues(vector1);
        assertEquals(1, vector1.getColumnsNumber());
        assertEquals(n, vector1.getRowsNumber());
        System.out.println("v1: size=" + vector1.getRowsNumber() + vector1);
        
        double value2 = new Random().nextDouble();
        Vector vector2 = new Vector(3, value2);
        assertEquals(value2, vector2.get(0));
        assertEquals(value2, vector2.get(1));
        assertEquals(value2, vector2.get(2));
        assertThrows(IndexOutOfBoundsException.class, () -> {vector2.get(3);});
        assertThrows(IndexOutOfBoundsException.class, () -> {vector2.get(-1);});        
    }
    
    @Test
    public void norm(TestInfo testInfo){
        Vector vector = new Vector(new double[]{0, -29, -1, 81});
        System.out.println("vector:" + vector);
        System.out.println("vector.norm=" + vector.norm());        
        assertTrue(Math.abs(86.04068804931770 - vector.norm()) <= PRECISION);
    }

    @Test
    public void scalarProduct(TestInfo testInfo){
        System.out.println("v1:" + vectorA);
        System.out.println("v2:" + vectorB);
        System.out.println("scalar product (v1,v2)=" + vectorA.scalarProduct(vectorB));
        assertTrue(Math.abs(-1107152.4566137176 - vectorA.scalarProduct(vectorB)) <= PRECISION);
    }

    @Test
    public void distance(TestInfo testInfo){
        System.out.println("v1:" + vectorA);
        System.out.println("v2:" + vectorB);
        System.out.println("distance radius vectors v1 to v2 = " + vectorA.distanceTo(vectorB));
        assertTrue(Math.abs(2863.108873340017 - vectorA.distanceTo(vectorB)) <= PRECISION);
    }
    
    private void fillInWithRandomValues(Vector vector){
        Random rnd = new Random();
        IntStream.range(0, vector.getRowsNumber())
                .forEach(i -> { vector.set(i, rnd.nextDouble());});
    }
}

