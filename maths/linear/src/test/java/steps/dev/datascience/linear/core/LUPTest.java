/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package steps.dev.datascience.linear.core;

import java.util.stream.IntStream;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

/**
 *
 * @author stepin
 */
public class LUPTest {

    private static final double PRECISION = 10E-9;
    private static final double SPECIAL_PRECISION = 10E-64;

    @Test
    @DisplayName("solve SLE:")
    void solve(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());

        double[][] array = {
            {2, 1, -5, 1},
            {1, -3, 0, -6},
            {0, 2, -1, 2},
            {1, 4, -7, 6},};

        double[] bArray = {8, 9, -5, 0};

//        double[][] array = {
//            {10, -7, 0},
//            {-3, 6, 2},
//            {5, -1, 5}};
        Matrix matrix = new Matrix(array);
        System.out.println("A:" + matrix);

        Vector b = new Vector(bArray);
        System.out.println("b:" + b);

        LUP lup = LUP.decompose(matrix);
        Vector x = lup.solveWith(b);
        System.out.println("Solution x:" + x);

        double[] expectedX = new double[]{3, -4, -1, 1};
        IntStream.range(0, x.getRowsNumber() - 1)
                .forEach(i -> assertTrue(Math.abs(expectedX[i] - x.get(i)) <= PRECISION));

//        Matrix lu = lup.getLU();
//        System.out.println("L:" + lup.retrieveLower());
//        System.out.println("U:" + lup.restrieveUpper());        
//
//        Matrix pi = lup.getPi();
//        System.out.println("pi:" + pi);
    }

//    @Test
//    @DisplayName("solve another SLE:")
//    void solveAnother(TestInfo testInfo) {
//        System.out.println(testInfo.getDisplayName());
//
//        double[][] array = {
//            {1.000011447364379, 1.0000228948598002, 1.0000343424862652, 1.0000457902437752, 1.0000572381323318},
//            {1.0000228948598002, 1.0000457902437752, 1.0000686861519368, 1.0000915825842969, 1.0001144795408674},
//            {1.0000343424862652, 1.0000686861519368, 1.000103030997055, 1.0001373770216608, 1.0001717242257946},
//            {1.0000457902437752, 1.0000915825842969, 1.0001373770216608, 1.0001831735559634, 1.0002289721873003},
//            {1.0000572381323318, 1.0001144795408674, 1.0001717242257946, 1.0002289721873003, 1.0002862234255725}
//        };
//
//        double[] bArray = {
//            1,
//            1001,
//            0.0000001,
//            0.001,
//            10101
//        };
//
////        double[][] array = {
////            {10, -7, 0},
////            {-3, 6, 2},
////            {5, -1, 5}};
//        Matrix matrix = new Matrix(array);
//        System.out.println("A:" + matrix);
//
//        Vector b = new Vector(bArray);
//        System.out.println("b:" + b);
//
//        LUP lup = LUP.decompose(matrix);
//        Vector x = lup.solveWith(b);
//        System.out.println("Solution x:" + x);
//
//        double[] expectedX = new double[]{
//            7.5699347155323776E17,
//            -3.4926328747064556E18,
//            5.9358981316770345E18,
//            -4.4218715264971356E18,
//            1.221612797973312E18
//        };
//        IntStream.range(0, x.getRowsNumber() - 1)
//                .forEach(i -> assertTrue(Math.abs(expectedX[i] - x.get(i)) <= PRECISION));
//
//        Matrix m;
//        double size;
//        do{
//            m = MatrixTest.createAndFillRandomSquareMatrix(5);
//            size = m.getRowsNumber();
//            
//        }while (size < 5);
//            System.out.println("###################################Matrix:" + m);
//            System.out.println("\n");
//        
//    }
    @Test
    @DisplayName("solve another SLE:")
    void solveAnother(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());

        double[][] array = {
            {0.2148364810915181, 0.04632960315708523, 0.7715306847986326, 0.4191169095011903, 0.5778467616528813},
            {0.645350018644352, 0.5276654121790672, 0.4485341837003357, 0.011244962428809901, 0.16887720947292117},
            {0.009376717528683298, 0.7948476487885776, 0.47494177559085726, 0.13819533338606282, 0.010764395993805298},
            {0.35803252612220815, 0.26274076342090513, 0.912822932218009, 0.6559511176232194, 0.28135162077259457},
            {0.934031661991611, 0.590460414963297, 0.196813436313232, 0.8421613390253633, 0.749569960368088}
        };

        double[] bArray = {
            1,
            1001,
            0.0000001,
            0.001,
            10101
        };

        Matrix matrix = new Matrix(array);
        System.out.println("A:" + matrix);

        Vector b = new Vector(bArray);
        System.out.println("b:" + b);

        LUP lup = LUP.decompose(matrix);
        Vector x = lup.solveWith(b);
        System.out.println("Solution x:" + x);

        double[] expectedX = new double[]{
            72830868639053933253576416944502012520526172924868516998468108030018905943878125000000d / 28926639037918940880296814829235113569069770076521922059151797499000902446728306889d,
            88254963762567459541919310820548567884444205741111775457393210534899908824812500000000d/28926639037918940880296814829235113569069770076521922059151797499000902446728306889d,
            (-193141237707196036272557184117670258781160885655480728818208847839618434197478125000000d/28926639037918940880296814829235113569069770076521922059151797499000902446728306889d),
            141806735941939138485067157189680076629555952893820086201790975122890824679531250000000d/28926639037918940880296814829235113569069770076521922059151797499000902446728306889d,
            120921629697819408565468053053889872714787237322835339051713751963179851251703125000000d/28926639037918940880296814829235113569069770076521922059151797499000902446728306889d
        };
        
        IntStream.range(0, x.getRowsNumber() - 1)
                .forEach(i -> assertTrue(Math.abs(expectedX[i] - x.get(i)) <= PRECISION));
    }

    @Test
    @DisplayName("calculate Det:")
    public void detTest(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());

        Matrix m = new Matrix(new double[][]{
            {-4, -2, -7, 8},
            {2, 7, 4, 9},
            {2, 0, 6, -3},
            {6, 4, -10, -4}
        });

        System.out.println("Matrix m:" + m);
        LUP lup = LUP.decompose(m);
        System.out.println("Det=" + lup.determinant());

    }

    @Test
    @DisplayName("Ill-conditioned matrix test:")
    public void illConditionedMatrixTest(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
        Matrix m = new Matrix(new double[][]{
            {0.00009143, 0, 0, 0},
            {0.8762, 0.00007156, 0, 0},
            {0.7943, 0.8143, 0.00009504, 0},
            {0.8017, 0.6123, 0.7165, 0.000007123},});

        System.out.println("Matrix m:" + m);
        LUP lup = LUP.decompose(m);
        double det = lup.determinant();
        System.out.println("Det=" + det);
        assertTrue(Math.abs(-4.429231946257536E-18 - det) <= SPECIAL_PRECISION);
    }

    @Test
    @DisplayName("Hilbert-Matrix (Ill-conditioned):")
    public void HilbertMatrixTest(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
        int hilbertSize = 10;
        Matrix hilberMatrix = new Matrix(hilbertSize, hilbertSize);
        for (int i = 0; i < hilbertSize; i++) {
            for (int j = 0; j < hilbertSize; j++) {
                double value = 1.0 / ((i + 1) + (j + 1) - 1);
                hilberMatrix.set(i, j, value);
            }
        }
        System.out.println("hilberMatrix=" + hilberMatrix);
        LUP lupIll = LUP.decompose(hilberMatrix);
        double det = lupIll.determinant();
        System.out.println("HilberMatrix det=" + det);
        assertTrue(Math.abs(2.1644016701584915E-53 - det) <= SPECIAL_PRECISION);

    }

    @Test
    @DisplayName("Another ill-conditioned Matrix: a[i][j] = e(i*j*h) , with h = 10E-6")
    public void anotherConditionedMatrixTest(TestInfo testInfo) {
        System.out.println(testInfo.getDisplayName());
        double h = 10E-6;
        int illtSize = 5;
        Matrix illMatrix = new Matrix(illtSize, illtSize);
        for (int i = 0; i < illtSize; i++) {
            for (int j = 0; j < illtSize; j++) {
                double value = Math.pow(Math.PI, ((i + 1) * (j + 1) * h));
                illMatrix.set(i, j, value);
            }
        }
        System.out.println("matrix:" + illMatrix);
        LUP lupIll = LUP.decompose(illMatrix);
        double det = lupIll.determinant();
        System.out.println("det=" + det);
        assertTrue(Math.abs(-1.2958504685599923E-43 - det) <= SPECIAL_PRECISION);
    }

//    private Matrix illConditionedMatrix() {
//        return new Matrix(new double[][]{
//            {5, 4, 7, 5, 6, 7, 5},
//            {4, 12, 8, 7, 8, 8, 6},
//            {7, 8, 10, 9, 8, 7, 7},
//            {5, 7, 9, 11, 9, 7, 5},
//            {6, 8, 8, 9, 10, 8, 9},
//            {7, 8, 7, 7, 8, 10, 10},
//            {5, 6, 7, 5, 9, 10, 10}
//        });
//    }
}
