package software.ulpgc.bigdata.algebra.matrices.longint.test;

import org.junit.jupiter.api.Test;
import software.ulpgc.bigdata.algebra.matrices.longint.Matrix;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.CoordinateMatrix;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.DenseMatrix;
import software.ulpgc.bigdata.algebra.matrices.longint.operators.matrixmultiplication.DenseMatrixMultiplication;
import software.ulpgc.bigdata.algebra.matrices.longint.operators.matrixparallelmultiplication.MatrixParallelMultiplier;
import software.ulpgc.bigdata.algebra.matrices.longint.reader.MatrixFileReader;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class MatrixParallelMultiplicationTest {

    @Test
    public void testMatrixParallelMultiplication() throws IOException {
        String fileNameA = "A.mtx";
        String fileNameB = "B.mtx";

        CoordinateMatrix A = MatrixFileReader.readMatrixFromFile(fileNameA);
        CoordinateMatrix B = MatrixFileReader.readMatrixFromFile(fileNameB);

        DenseMatrix denseA = new DenseMatrix(A);
        DenseMatrix denseB = new DenseMatrix(B);

        double[][] doubleArrayA = MatrixFileReader.convertToDoubleArray(A);
        double[][] doubleArrayB = MatrixFileReader.convertToDoubleArray(B);

        Matrix expectedResult = DenseMatrixMultiplication.multiply(denseA, denseB);

        double[][] resultArray = new double[A.getNumRows()][B.getNumCols()];

        MatrixParallelMultiplier.multiplyInParallel(doubleArrayA, doubleArrayB, resultArray);

        System.out.println("Result Matrix C:");
        printMatrix(resultArray);
        assertArrayEquals(expectedResult.toArray(), resultArray);
    }

    private void printMatrix(double[][] matrix) {
        int numRows = matrix.length;
        int numCols = matrix[0].length;

        for (int i = 0; i < numRows; i++) {
            for (int j = 0; j < numCols; j++) {
                System.out.print(matrix[i][j] + " ");
            }
            System.out.println();
        }
    }
}
