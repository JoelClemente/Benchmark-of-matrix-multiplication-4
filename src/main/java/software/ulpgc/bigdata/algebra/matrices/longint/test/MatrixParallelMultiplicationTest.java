package software.ulpgc.bigdata.algebra.matrices.longint.test;

import org.junit.jupiter.api.Test;
import software.ulpgc.bigdata.algebra.matrices.longint.Matrix;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.CoordinateMatrix;
import software.ulpgc.bigdata.algebra.matrices.longint.matrix.DenseMatrix;
import software.ulpgc.bigdata.algebra.matrices.longint.operators.matrixmultiplication.DenseMatrixMultiplication;
import software.ulpgc.bigdata.algebra.matrices.longint.operators.matrixparallelmultiplication.MatrixParallelMultiplier;
import software.ulpgc.bigdata.algebra.matrices.longint.reader.MatrixFileReader;

import java.io.IOException;
import java.util.concurrent.Semaphore;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

public class MatrixParallelMultiplicationTest {

    @Test
    public void testMatrixParallelMultiplier() throws InterruptedException, IOException {
        String fileNameA = "spaceStation_6.mtx";
        String fileNameB = "spaceStation_6.mtx";

        CoordinateMatrix A = MatrixFileReader.readMatrixFromFile(fileNameA);
        CoordinateMatrix B = MatrixFileReader.readMatrixFromFile(fileNameB);

        DenseMatrix denseA = new DenseMatrix(A);
        DenseMatrix denseB = new DenseMatrix(B);

        double[][] doubleArrayA = MatrixFileReader.convertToDoubleArray(A);
        double[][] doubleArrayB = MatrixFileReader.convertToDoubleArray(B);

        double[][] resultArray = new double[denseA.getNumRows()][denseB.getNumCols()];

        int tileSize = 250;

        Semaphore semaphore = new Semaphore(1);
        long startTimeParallel = System.currentTimeMillis();
        MatrixParallelMultiplier parallelMultiplier = new MatrixParallelMultiplier(doubleArrayA, doubleArrayB, resultArray, tileSize, semaphore);
        parallelMultiplier.multiplyInParallel();
        long endTimeParallel = System.currentTimeMillis();
        long executionTimeParallel = (endTimeParallel - startTimeParallel);
        System.out.println("Execution time ParallelMultiplication: " + executionTimeParallel + " ms");

        long startTimeDense = System.currentTimeMillis();
        Matrix sequentialResult = DenseMatrixMultiplication.multiply(denseA, denseB);
        long endTimeDense = System.currentTimeMillis();
        long executionTimeDense = (endTimeDense - startTimeDense) / 1000;
        System.out.println("Execution time DenseMultiplication: " + executionTimeDense + " s");

        System.out.println("Result Matrix C:");
        printMatrix(resultArray);

        assertArrayEquals(sequentialResult.toArray(), resultArray);
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
