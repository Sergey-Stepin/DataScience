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
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

/**
 *
 * @author stepin
 */
public class MatrixTest {
    
    private static final double PRECISION = 0.0000000001;

    @Test
    void contructZeroBySuze() {

        //try to create matrises with wrong size
        assertThrows(IllegalArgumentException.class, () -> {
            new Matrix(0, 0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Matrix(-1, -1);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Matrix(3, 0);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Matrix(0, 3);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Matrix(3, -2);
        });
        assertThrows(IllegalArgumentException.class, () -> {
            new Matrix(-2, 3);
        });

        //try a to create a correct one
        int n = 3;
        int m = 4;
        System.out.println("Test matrix :" + n + "x" + m);

        Matrix matrix = new Matrix(n, m);

        assertEquals(n, matrix.getRowsNumber());
        assertEquals(m, matrix.getColumnsNumber());

        assertEquals(0, matrix.get(0, 0));
        assertEquals(0, matrix.get(n - 1, m - 1));
    }

    @RepeatedTest(value = 5, name = "{displayName}{currentRepetition}/{totalRepetitions}")
    @DisplayName(" Fill in with a random value")
    void contructAndFill(TestInfo testInfo) {

        System.out.println(testInfo.getDisplayName());

        int maxN = 10;
        int maxM = 20;

        Random rnd = new Random();
        int n = Math.abs(rnd.nextInt(maxN) + 1);
        int m = Math.abs(rnd.nextInt(maxM) + 1);
        double value = rnd.nextDouble();
        System.out.println("Test matrix :" + n + "x" + m + " filled with value=" + value);

        Matrix matrix = new Matrix(n, m, value);

        assertEquals(n, matrix.getRowsNumber());
        assertEquals(m, matrix.getColumnsNumber());

        //test the last element
        assertEquals(
                value,
                matrix.get(n - 1, m - 1));

        //test a random element
        assertEquals(
                value,
                matrix.get(rnd.nextInt(n), rnd.nextInt(m)));

    }

    @Test
    void constructByArray(TestInfo testInfo) {

        System.out.println(testInfo.getDisplayName());

        //create a random array
        int maxN = 10;
        int maxM = 20;

        double[][] array = createAndFillRandomArray(maxN, maxM);
        int n = array.length;
        int m = array[0].length;
        System.out.println("Test array :" + n + "x" + m);

        //create matrix
        Matrix matrix = new Matrix(array);

        //test matrix
        IntStream.range(0, n)
                .forEach(i -> {
                    IntStream.range(0, m)
                            .forEach(j -> {
                                assertEquals(array[i][j], matrix.get(i, j));
                            });
                });
    }

    @Test
    void copyFromArray(TestInfo testInfo) {

        System.out.println(testInfo.getDisplayName());

        //create a random array
        int maxN = 10;
        int maxM = 20;

        double[][] array = createAndFillRandomArray(maxN, maxM);
        int n = array.length;
        int m = array[0].length;
        System.out.println("Test array :" + n + "x" + m);

        //create matrix
        Matrix matrix = new Matrix(array);

        //coppy to a new array
        double[][] newArray = matrix.copyToArray();

        //test matrix
        IntStream.range(0, n)
                .forEach(i -> {
                    IntStream.range(0, m)
                            .forEach(j -> {
                                assertEquals(matrix.get(i, j), newArray[i][j]);
                            });
                });
    }
    
    @Test
    public void product(){
        Matrix matrixA = new Matrix(new double[][]{{1, 0, -2}, {3, 9, -5}});
        Matrix matrixB = new Matrix(new double[][]{{-1, 2, 4}, {5, 6, 7}, {0, 3, -1}});
        
        assertThrows(IndexOutOfBoundsException.class, () -> {matrixB.product(matrixA);});
        
        System.out.println(" matrix A:" + matrixA);
        System.out.println(" matrix B:"+ matrixB);
        
        System.out.println(" product A x B = " + matrixA.product(matrixB));
    }
    
    @Test
    public void norm (TestInfo testInfo){
        System.out.println(testInfo.getDisplayName());
        
        double [][] array = new double[][]{
            {-9, -77, 15, 95},
            {-44, -8, -74, -57},
            {55, -16, -12, -67},
            {-83, 51, 96, 66}};
        
        Matrix matrix = new Matrix(array);
        System.out.println("matrix:" + matrix);
        System.out.println("\t matrix.norm=:" + matrix.norm());        
        assertTrue(Math.abs(238.6650372383856 - matrix.norm()) <= PRECISION);
    }

    public static Matrix createAndFillRandomMatrix(int maxRowNum, int maxColumnNum) {
        return new Matrix(createAndFillRandomArray(maxRowNum, maxColumnNum));
    }

    public static double[][] createAndFillRandomArray(int maxRowNum, int maxColumnNum) {
        Random rnd = new Random();
        int n = Math.abs(rnd.nextInt(maxRowNum) + 1);
        int m = Math.abs(rnd.nextInt(maxColumnNum) + 1);
        double[][] array = new double[n][m];

        IntStream.range(0, n)
                .forEach(i -> {
                    IntStream.range(0, m)
                            .forEach(j -> {
                                array[i][j] = rnd.nextDouble();
                            });
                });

        return array;
    }
    
    public static Matrix createAndFillRandomSquareMatrix(int maxSize) {
        Random rnd = new Random();
        int n = Math.abs(rnd.nextInt(maxSize) + 1);
        double[][] array = new double[n][n];

        IntStream.range(0, n)
                .forEach(i -> {
                    IntStream.range(0, n)
                            .forEach(j -> {
                                array[i][j] = rnd.nextDouble();
                            });
                });

        return new Matrix(array);
    }

}
