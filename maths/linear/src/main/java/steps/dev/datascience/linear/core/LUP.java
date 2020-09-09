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
 * 
 * LUP decomposition of matrix A : P*A = L*U 
 * 
 *  
 * 
 */
public class LUP {
    
    private final  Matrix lu;    // LU- factorisation of a matrix A
    private final Vector pi;    // pi - mapper of the permutation matrix P
    private final int swapsNumber;

    private LUP( Matrix lu, Vector pi, int swapsNumber) {
        this.lu = lu;
        this.pi = pi;
        this.swapsNumber = swapsNumber;
    }
    
    public static LUP decompose(Matrix a){
        
        if (! a.isSquare()){
            throw new IllegalArgumentException("Cannot decompose a non-squate matrix. At first the matrix mast be truncated to a square one. ");
        }
        
        int n = a.getRowsNumber();
        
        int swapsNumber = 0;
        
        //Permutation mapapper pi
        Vector pi = new Vector(n, 1);
        for(int i  = 0; i < n; i ++){
            pi.set(i, i);
        }
        
        //LU-factorisation
        Matrix lu = new  Matrix(a);
        for (int k = 0; k < n ; k ++){
            
            //Select leading element
            double absValue;
            double p = 0.0d;
            int leadingRow = k;
            for(int i = k; i < n; i++){
                if ( (absValue = Math.abs(lu.get(i, k))) > p){
                    p = absValue;
                    leadingRow = i;
                }
            }
            
            if(p == 00.d){  // degenerate matrix (determinant = 0)
                throw new IllegalArgumentException("Determinant = 0, cannot decompose degenerate matrix. ");
            }
            
            //Swap LU rows and pi rows
            if (leadingRow > k){
                swapsNumber ++;
                lu.swapRows(k, leadingRow);
                pi.swapRows(k, leadingRow);
            }
            
            //Form L part 
            for (int i = k + 1; i < n ; i ++){
                lu.set(i, k, 
                        lu.get(i, k) / lu.get(k, k));
            }
            
            //Form U part 
            for (int i = k + 1; i < n ; i ++){
                for (int j = k + 1; j < n ; j ++){
                    lu.set(i, j, 
                            lu.get(i, j) - lu.get(i, k) * lu.get(k, j));
                }
            }

        }
        
        return new LUP(lu, pi, swapsNumber);
    }

    public Matrix getLU() {
        return lu;
    }

    public Matrix getPi() {
        return pi;
    }
    
    public TriangularMatrix retrieveLower(){
        return TriangularMatrix.createFrom(lu, TriangularMatrix.Category.LOWERUNI);
    }

    public TriangularMatrix restrieveUpper(){
        return TriangularMatrix.createFrom(lu, TriangularMatrix.Category.UPPER);
    }

    /**
     * @return the swapsNumber
     */
    public int swapsNumber() {
        return swapsNumber;
    }
    
    public double determinant(){
        double det = 1.0d;
        int n = lu.getRowsNumber();
        
        for (int i = 0; i < n ; i++){
            det = det * lu.get(i, i);
        }
        
        det = det * Math.pow(1, swapsNumber);  // when swap then det = -det
        
        return det;
    }
    
    public Vector solveWith(Vector b){
        if (lu == null){
            throw new AssertionError();
        }
        
        int n = lu.getRowsNumber();
        int bSize = b.getRowsNumber();
        
        if ( bSize != n){
            throw new IllegalArgumentException("Vector b (constatnt terms) has a size = " + bSize + " . Expected size =  " + n);
        }
        
        Vector y = new Vector(n);
        Vector x = new Vector(n);

        //Solve L*y = P*b
        IntStream.iterate(0, i -> i + 1)
                .limit(n)
                .forEach(i -> {
                    double sum = IntStream.iterate(0, j -> j + 1)
                            .limit(i)
                            .mapToDouble(j -> lu.get(i, j) * y.get(j))
                            .sum();
                    int bi = (int) pi.get(i);
                    y.set(i, b.get(bi) - sum);
                });
        
        //Solve U*x = y
        IntStream.iterate(0, i -> i + 1)
                .limit(n)
                .forEach((int i) -> {
                    int ic = (n - i - 1);
                    double sum = IntStream.iterate(0, j -> j + 1)
                            .limit(i)
                            .map(j -> (n - j - 1))
                            .mapToDouble(jc -> lu.get(ic, jc) * x.get(jc))
                            .sum();
                    x.set(ic, (y.get(ic) - sum) / lu.get(ic, ic) );
                });
        
        return x;
    }
}
