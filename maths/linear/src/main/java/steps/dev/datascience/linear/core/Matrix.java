/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steps.dev.datascience.linear.core;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.Stream;


/**
 *
 * @author papa
 */
public class Matrix{
    
    protected final double [][] array;
    private final int rowsNumber, columnsNumber;

    public Matrix(int rowsNumber, int columnsNumber) {
        if(rowsNumber < 1){
            throw new IllegalArgumentException(" number of rows cannot be less than 1 ");
        }

        if(columnsNumber < 1){
            throw new IllegalArgumentException(" number of columns cannot be less than 1 ");
        }
        
        this.array = new double [rowsNumber][columnsNumber];
        this.rowsNumber = rowsNumber;
        this.columnsNumber = columnsNumber;
    }
    
    public Matrix(int rowsNumber, int columnsNumber, double value) {    
        this(rowsNumber, columnsNumber);
        fillWithValue(value);
    }
    
    public Matrix(double [][] arr){
        this.rowsNumber = arr.length;
        
        if (this.rowsNumber == 0){ 
            this.columnsNumber = 0; // array: 0x0 
            
        } else {
            this.columnsNumber = arr[0].length;
        }
        
        this.array = arr;
    }

    public Matrix(Matrix a) {   
        this(a.getRowsNumber(), a.getColumnsNumber());
        
        IntStream.iterate(0, i -> i + 1)
                .limit(rowsNumber)
                .parallel()
                .forEach(i -> {
                    IntStream.iterate(0, j -> j + 1)
                            .limit(columnsNumber)
                            .parallel()
                            .forEach(j -> {
                                array[i][j] = a.get(i, j);
                            });
                });
    }
    
    public double [][] copyToArray(){
        
        double [][] toAarray = new double [rowsNumber][columnsNumber];
        
        IntStream.iterate(0, i -> i + 1)
                .limit(rowsNumber)
                .parallel()
                .forEach(i -> {
                    System.arraycopy(toAarray[i], 0, this.array[i], 0, columnsNumber);
                });
        
        return toAarray;
    }
    
    public int getRowsNumber() {
        return this.rowsNumber;
    }

    public int getColumnsNumber() {
        return this.columnsNumber;
    }
    
    public boolean isSquare(){
        return rowsNumber == columnsNumber;
    }

    public double get(int row, int column) {
        return array[row][column];
    }

    public void set(int row, int column, double value) {
        array[row][column] = value;
    }
    
    @Override
    public String toString(){
        
        return Stream.of(array)
                .sequential()
                .map((double[] row) -> {
                    return DoubleStream.of(row)
                            .sequential()
                            //.mapToObj((double value) -> String.format("%d", value))
                            .mapToObj(Double::toString)
                            .collect(Collectors.joining(" ; "));
                })
                .collect(Collectors.joining("\n", "\n", ""));
    }
    
    public void swapRows(int row1, int row2){
        double [] tmpRow = array[row1];
        array[row1] = array[row2];
        array[row2] = tmpRow;
    } 
    
    public Matrix transpose() {
        Matrix transposedMatrix = new Matrix(columnsNumber, rowsNumber);
        
        IntStream.iterate(0, j -> j + 1)
                .limit(columnsNumber)
                .parallel()
                .forEach((int j) -> {
                    IntStream.iterate(0, i -> i + 1)
                            .limit(rowsNumber)
                            .parallel()
                            .forEach((int i) -> {
                                transposedMatrix.set(j, i, array[i][j]);
                            });});
                            
        return transposedMatrix;
    }
    
    public double sumOflElements(){
        return IntStream.iterate(0, i -> i + 1)
                .parallel()                
                .limit(rowsNumber)
                .mapToDouble(this::sumOfElementsInRow)
                .sum();
    }
    
    public double sumOfElementsInRow(int i){
        return IntStream.iterate(0, j -> j + 1)
                .parallel()                                
                .limit(columnsNumber)
                .mapToDouble((int j) -> array[i][j])
                .sum();
    }

    public double squareOfNorm() {
        return IntStream.iterate(0, i -> i + 1)
                .limit(rowsNumber)
                .parallel()
                .mapToDouble((int i) -> {
                    return IntStream.iterate(0, j -> j + 1)
                            .limit(columnsNumber)
                            .parallel()
                            .mapToDouble((int j) -> array[i][j] * array[i][j])
                            .sum();})
                .sum();
    }
    
    public double norm() {
        return Math.sqrt(squareOfNorm());
    }

    public Matrix product(Matrix other) {
        
        if(other == null){
            throw new NullPointerException(" Matrix to product with is null ");
        }
        
        int n = this.getRowsNumber();
        int m = this.getColumnsNumber();
        int p = other.getRowsNumber();
        int q = other.getColumnsNumber();
        
        if (m!=p){
            
            throw new IndexOutOfBoundsException(" Cannot calculate a product for matrixes of non-corresponding sizes. "
                    + " This matrix is: " + this.getRowsNumber() + "x" + this.getColumnsNumber()
                    + " , whilist the other matrix is: " + other.getRowsNumber() + "x" + other.getColumnsNumber());
        }
        
        Matrix matrixProduct = new Matrix(n, q);
        
        IntStream.iterate(0, i -> i + 1)
                .limit(n)
                .parallel()
                .forEach(i -> {
                    IntStream.iterate(0, j -> j + 1)
                            .limit(q)
                            .parallel()
                            .forEach(j -> {
                                double kElement =  IntStream.iterate(0, k -> k + 1)
                                        .limit(m)
                                        .mapToDouble((int k) -> array[i][k] * other.get(k, j))
                                        .sum();
                                matrixProduct.array[i][j] = kElement;
                            });});
        
        return matrixProduct;
    }

    protected void fillWithValue(double value){
        for (double [] row: array){
            Arrays.fill(row, value);
        }
    }
    
    public Matrix truncateToSquare(){
        int n  = rowsNumber;
        int m = columnsNumber;
        int size = n > m ? m : n;
        
        Matrix squareMatrix = new Matrix(size, size);
        
        IntStream.iterate(0, i -> i + 1)
                .limit(size)
                .parallel()
                .forEach(i -> {
                    IntStream.iterate(0, j -> j + 1)
                            .limit(size)
                            .parallel()
                            .forEach(j -> {
                                squareMatrix.set(i, j, get(i, j));
                            });
                });
        
        return squareMatrix;
    }

//    public static Matrix generateRandom(final int rowsNumber, final int columnsNumber){
//        
//        final Random rnd = new Random();
//        
////        List<double[]> values = IntStream.iterate(1, i -> i++)
////                .limit(rowsNumber)
////                .mapToObj(i -> Matrix.generateRandomArray(columnsNumber, rnd))
////                .collect(Collectors.toList());
//                       
//        values.toArray(new double[values.size()][columnsNumber]);
//        
//        return new Matrix(values.toArray(new double[values.size()][columnsNumber]));
//    }
//    
//    private static double[] generateRandomArray(int size, Random rnd){
//        return DoubleStream.generate(rnd::nextDouble)
//                .limit(size)
//                .toArray();
//    }
    
}
