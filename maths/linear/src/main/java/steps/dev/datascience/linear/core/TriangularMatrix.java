/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steps.dev.datascience.linear.core;

import java.util.stream.IntStream;

/**
 *
 * @author papa
 */
//public class TriangularMatrix extends SquareMatrix{
public class TriangularMatrix extends Matrix{
    
    public static enum Category {LOWER, UPPER, LOWERUNI, UPPERUNI};
    
    public final Category category;
    
    public TriangularMatrix(int size, Category category) {
        //super(size);
        super(size, size);
        this.category = category;
    }
    
//    private TriangularMatrix(double [][] arr, Category category){
//        super(arr);
//        this.category = category;
//    }
    
    public TriangularMatrix(double [][] arr, Category category) {    
        super(TriangularMatrix.triangularFrom(arr, category));
        this.category = category;
    }    
    
    public TriangularMatrix(Matrix a, Category category) {    
        super(TriangularMatrix.triangularFrom(a.array, category));
        this.category = category;
    }    
    
    public int size(){
        return getRowsNumber();
    }
    
    public static TriangularMatrix createFrom(Matrix a,  TriangularMatrix.Category category){
        return new TriangularMatrix(triangularFrom(a.array,  category), category);
    }    
    
    private static double [][] triangularFrom(double [][] arr,  TriangularMatrix.Category category){
        int n = arr.length;
        int m = arr.length != 0 ? arr[0].length : 0;
        
        if (n != m){
            throw new IllegalArgumentException("Cannot triangulate non-squate matrix. At first the matrix mast be truncated to a square one. ");
        }
        
        double [][] array = new double [n][n];
       
        // Fill diaganal elements
        IntStream.iterate(0, i -> i + 1)
                .limit(n)
                .parallel()
                .forEach(i -> {
                    if (category.equals(TriangularMatrix.Category.UPPER)
                            || category.equals(TriangularMatrix.Category.LOWER) ){
                        
                        array[i][i] = arr[i][i];
                    } else {
                        
                        array[i][i] = 1.0d;
                    }
                });
        
        // Fill non-diaganal elements
        IntStream.iterate(0, i -> i + 1)
                .limit(n)
                .parallel()
                .forEach(i -> {
                    IntStream.iterate(i + 1, j -> j + 1)
                            .limit(n - i - 1)
                            .parallel()
                            .forEach(j -> {
                                
                                //for Upper- and UpperUni-triangle matrix                                
                                if (category.equals(TriangularMatrix.Category.UPPER)
                                        || category.equals(TriangularMatrix.Category.UPPERUNI) ){
                                    
                                    array[i][j] = arr[i][j];
                                
                                //for Lower- and LowerUni-triangle matrix
                                } else{
                                    
                                    array[j][i] = arr[j][i];
                                }
                                
                            });});

        return array;
    }
        
    
}
