/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steps.dev.datascience.linear.core;

import java.util.stream.IntStream;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

/**
 *
 * @author stepin
 */
public class TriangularMatrixTest {

    private static int MAX_SIZE = 100;

    private final Matrix matrix = MatrixTest.createAndFillRandomSquareMatrix(MAX_SIZE);

    @Test
    void constructTiangularMatrix() {
        
        //try construct a wrong triangular matrix
        assertThrows(IllegalArgumentException.class, () -> {
            new TriangularMatrix(new Matrix(10, 20), TriangularMatrix.Category.LOWER);
        });
                
    }
    
    
    @Test
    void constructLowerFromMatrix() {
        System.out.println("Test triangulars matrix :" + matrix.getRowsNumber() + "x" + matrix.getColumnsNumber());
        TriangularMatrix trianguar = new TriangularMatrix(matrix, TriangularMatrix.Category.LOWER);
        
        //test values
        IntStream.range(0, matrix.getRowsNumber())
                .forEach(i -> {
                    IntStream.range(0, matrix.getColumnsNumber())
                            .forEach(j -> {
                                if(i < j){
                                    assertEquals(0, trianguar.get(i, j));
                                }else {
                                    assertEquals(matrix.get(i, j), trianguar.get(i, j));
                                }
                            });
                });
    }

    @Test
    void constructLowerUmiFromMatrix() {
        System.out.println("Test triangulars matrix :" + matrix.getRowsNumber() + "x" + matrix.getColumnsNumber());
        TriangularMatrix trianguar = new TriangularMatrix(matrix, TriangularMatrix.Category.LOWERUNI);
        
        //test values
        IntStream.range(0, matrix.getRowsNumber())
                .forEach(i -> {
                    IntStream.range(0, matrix.getColumnsNumber())
                            .forEach(j -> {
                                if(i < j){
                                    assertEquals(0, trianguar.get(i, j));
                                }else if(i == j){
                                    assertEquals(1, trianguar.get(i, j));
                                }else {
                                    assertEquals(matrix.get(i, j), trianguar.get(i, j));
                                }
                            });
                });
    }

    @Test
    void constructUpperFromMatrix() {
        System.out.println("Test triangulars matrix :" + matrix.getRowsNumber() + "x" + matrix.getColumnsNumber());
        TriangularMatrix trianguar = new TriangularMatrix(matrix, TriangularMatrix.Category.UPPER);
        
        //test values
        IntStream.range(0, matrix.getRowsNumber())
                .forEach(i -> {
                    IntStream.range(0, matrix.getColumnsNumber())
                            .forEach(j -> {
                                if(i > j){
                                    assertEquals(0, trianguar.get(i, j));
                                }else {
                                    assertEquals(matrix.get(i, j), trianguar.get(i, j));
                                }
                            });
                });
    }

    @Test
    void constructUpperUmiFromMatrix() {
        System.out.println("Test triangulars matrix :" + matrix.getRowsNumber() + "x" + matrix.getColumnsNumber());
        TriangularMatrix trianguar = new TriangularMatrix(matrix, TriangularMatrix.Category.UPPERUNI);
        
        //test values
        IntStream.range(0, matrix.getRowsNumber())
                .forEach(i -> {
                    IntStream.range(0, matrix.getColumnsNumber())
                            .forEach(j -> {
                                if(i > j){
                                    assertEquals(0, trianguar.get(i, j));
                                }else if(i == j){
                                    assertEquals(1, trianguar.get(i, j));
                                }else {
                                    assertEquals(matrix.get(i, j), trianguar.get(i, j));
                                }
                            });
                });
    }
}
